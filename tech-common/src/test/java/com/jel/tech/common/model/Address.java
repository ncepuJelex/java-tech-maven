package com.jel.tech.common.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Address {

	private Integer id;
	private String province;
	private String city;
	private String area;
	private String others;
	
	@XmlAttribute
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@XmlElement
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	@XmlElement
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	@XmlElement
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	@XmlElement
	public String getOthers() {
		return others;
	}
	public void setOthers(String others) {
		this.others = others;
	}
	@Override
	public String toString() {
		return "Address [id=" + id + ", province=" + province + ", city=" + city + ", area=" + area + ", others="
				+ others + "]";
	}
	
	
}
