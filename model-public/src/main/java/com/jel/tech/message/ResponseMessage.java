package com.jel.tech.message;

public class ResponseMessage<T> {

	private ResponseStatus status;
	private String msg;
	private T others;
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getOthers() {
		return others;
	}
	public void setOthers(T others) {
		this.others = others;
	}
	public ResponseStatus getStatus() {
		return status;
	}
	public void setStatus(ResponseStatus status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "ResponseMessage [status=" + status + ", msg=" + msg + ", others=" + others + "]";
	}
	
}
