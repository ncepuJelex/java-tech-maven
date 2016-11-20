package com.jel.tech.common.restful;

import java.io.Serializable;

public class RestfulResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 响应状态码
	 */
	private int statusCode;
	/**
	 * 响应状态
	 */
	private String status;
	/**
	 * 响应实体(JSON形式)
	 */
	private String responseJSON;
	
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getResponseJSON() {
		return responseJSON;
	}
	public void setResponseJSON(String responseJSON) {
		this.responseJSON = responseJSON;
	}
	
}
