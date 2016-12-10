package com.jel.tech.mongodb.model;

public abstract class Concact {

	private String weichat;

	public String getWeichat() {
		return weichat;
	}

	public Concact() {
		super();
	}

	public Concact(String weichat) {
		this.weichat = weichat;
	}

	@Override
	public String toString() {
		return "Concact [weichat=" + weichat + "]";
	}
	
}
