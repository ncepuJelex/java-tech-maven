package com.jel.tech.message;

public enum ResponseStatus {

	STATUS_OK(0,"成功"),
	STATUS_99(99,"系统异常"),
	STATUS_19(19,"系统繁忙");
	
	private int status;
	private String statusDesc;
	
	private ResponseStatus(int status, String statusDesc) {
		this.status = status;
		this.statusDesc = statusDesc;
	}
	public int getStatus() {
		return status;
	}
	public String getStatusDesc() {
		return statusDesc;
	}
	
	
}
