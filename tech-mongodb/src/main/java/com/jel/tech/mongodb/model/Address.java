package com.jel.tech.mongodb.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

public class Address implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;//既然有@Id注解，那名称就可以改为任意了，不一定叫做id了
	
	private String cellphone;
	private String weichat;
	private String qq;
	private String email;
	
	private String detailAddr;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getCellphone() {
		return cellphone;
	}

	public String getWeichat() {
		return weichat;
	}

	public String getQq() {
		return qq;
	}

	public String getEmail() {
		return email;
	}

	public String getDetailAddr() {
		return detailAddr;
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", cellphone=" + cellphone + ", weichat=" + weichat + ", qq=" + qq + ", email="
				+ email + ", detailAddr=" + detailAddr + "]";
	}

	public Address(String cellphone, String weichat, String qq, String email, String detailAddr) {
		this.cellphone = cellphone;
		this.weichat = weichat;
		this.qq = qq;
		this.email = email;
		this.detailAddr = detailAddr;
	}

	public Address(String cellphone, String detailAddr) {
		this.cellphone = cellphone;
		this.detailAddr = detailAddr;
	}

	public Address(String cellphone, String weichat, String qq) {
		this.cellphone = cellphone;
		this.weichat = weichat;
		this.qq = qq;
	}

	public Address() {
	}
	
}
