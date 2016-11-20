package com.jel.tech.common.restful;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.entity.ContentType;

/**
 * Restful请求实体
 * @author Jelex
 *
 */
public class RestfulRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Content-Type
	 */
	public static String CONTENT_TYPE = "Content-Type";
	
	public static String APPLICATION_JSON = ContentType.APPLICATION_JSON.getMimeType();
	
	public static String APPLICATION_XML = ContentType.APPLICATION_XML.getMimeType();
	/**
	 * 请求地址URL
	 */
	private String requestURL;
	/**
	 * http request header infos
	 */
	private Map<String, String> headers;
	/**
	 * 请求实体(JSON形式)
	 */
	private String requestJSON;
	
	public String getRequestURL() {
		return requestURL;
	}
	public void setRequestURL(String requestURL) {
		this.requestURL = requestURL;
	}
	public Map<String, String> getHeaders() {
		return headers;
	}
	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
	
	public void addHeader(String key, String value) {
		synchronized(this) {
			if(headers == null) {
				headers = new HashMap<String, String>();
			}
			headers.put(key, value);
		}
	}
	
	public String getRequestJSON() {
		return requestJSON;
	}
	public void setRequestJSON(String requestJSON) {
		this.requestJSON = requestJSON;
	}
	
}
