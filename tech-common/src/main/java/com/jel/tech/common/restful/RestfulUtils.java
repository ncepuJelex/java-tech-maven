package com.jel.tech.common.restful;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLException;

import org.apache.commons.io.IOUtils;
import org.apache.http.Consts;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

public class RestfulUtils {

	public static String APPLICATION_JSON = ContentType.APPLICATION_JSON.getMimeType();

	private RestfulUtils() {}
	
	/**
	 * Request retry handler:a custom exception recovery mechanism
	 */
	private static HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {

		public boolean retryRequest(IOException exception, int executionCount,
				HttpContext context) {
			if (executionCount >= 5) {
				return false;// Do not retry if over max retry count
			}
			if (exception instanceof InterruptedIOException) {
				return false;// Timeout
			}
			if (exception instanceof UnknownHostException) {
				return false;// Unknown host
			}
			if (exception instanceof ConnectTimeoutException) {
				return false;// Connection refused
			}
			if (exception instanceof SSLException) {
				return false;// SSL handshake exception
			}
			HttpClientContext clientContext = HttpClientContext.adapt(context);
			HttpRequest request = clientContext.getRequest();
			boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
			if (idempotent) {
				return true;// Retry if the request is considered idempotent
			}
			return false;
		}
	};

	/**
	 * Connection keep alive strategy
	 */
	private static ConnectionKeepAliveStrategy myStrategy = new ConnectionKeepAliveStrategy() {

		public long getKeepAliveDuration(HttpResponse response,
				HttpContext context) {
			// Honor 'keep-alive' header
			HeaderElementIterator it = new BasicHeaderElementIterator(
					response.headerIterator(HTTP.CONN_KEEP_ALIVE));
			while (it.hasNext()) {
				HeaderElement he = it.nextElement();
				String param = he.getName();
				String value = he.getValue();
				if (value != null && param.equalsIgnoreCase("timeout")) {
					try {
						return Long.parseLong(value) * 1000;
					} catch (NumberFormatException ignore) {
					}
				}
			}
			HttpHost target = (HttpHost) context
					.getAttribute(HttpClientContext.HTTP_TARGET_HOST);
			/*
			 * 可以使用正则表达式匹配想要特殊处理的网站地址
			 */
			if ("www.naughty-server.com".equalsIgnoreCase(target.getHostName())) {
				// Keep alive for 5 seconds only
				return 5 * 1000;
			} else {
				// otherwise keep alive for 30 seconds
				return 30 * 1000;
			}
		}
	};

	/**
	 * Request execution: transmit the request to the target server return a
	 * corresponding response object
	 */
	private static HttpClientBuilder  httpclientBuilder = HttpClients.custom()
			.setRetryHandler(myRetryHandler).setKeepAliveStrategy(myStrategy);

	/**
	 * the main method to fire restful request and get your expected response
	 * entity data.
	 * 
	 * @param request
	 * @return
	 */
	public static RestfulResponse fireRequest(final RestfulRequest request) {

		CloseableHttpClient httpClient = httpclientBuilder.build();
		HttpPost httpPost = new HttpPost(request.getRequestURL());
		boolean hasContentType = false;

		Map<String, String> headers = request.getHeaders();// 请求头
		
		if (headers != null && !headers.isEmpty()) {
			
			Iterator<Entry<String, String>> iterator = headers.entrySet().iterator();
			while (iterator.hasNext()) {
				httpPost.setHeader(iterator.next().getKey(), iterator.next().getValue());
			}

			if (headers.containsKey(RestfulRequest.CONTENT_TYPE)) {
				hasContentType = true;
			}
		}
		if (!hasContentType) {
			httpPost.setHeader("Content-Type",ContentType.create(APPLICATION_JSON, Consts.UTF_8).toString());
		}

		if (request.getRequestJSON()!=null && !request.getRequestJSON().trim().isEmpty()) {

			HttpEntity requestEntity = new StringEntity(request.getRequestJSON(), Consts.UTF_8);
			httpPost.setEntity(requestEntity);
		}
		/**
		 * use the ResponseHandler interface to handle responses
		 */
		ResponseHandler<RestfulResponse> rh = new ResponseHandler<RestfulResponse>() {
			public RestfulResponse handleResponse(final HttpResponse response) throws IOException {
				
				StatusLine statusLine = response.getStatusLine();
				HttpEntity entity = response.getEntity();

				if (statusLine.getStatusCode() >= 300) {
					throw new HttpResponseException(statusLine.getStatusCode(),
							statusLine.getReasonPhrase());
				}
				if (entity == null) {
					throw new ClientProtocolException("Response contains no content");
				}
				RestfulResponse resp = new RestfulResponse();
				resp.setStatus(statusLine.getReasonPhrase());
				resp.setStatusCode(statusLine.getStatusCode());
				resp.setResponseJSON(IOUtils.toString(entity.getContent(),Consts.UTF_8));
				return resp;
			}
		};
		
		try {
			return httpClient.execute(httpPost, rh);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
}
