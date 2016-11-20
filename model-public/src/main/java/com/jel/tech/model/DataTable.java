package com.jel.tech.model;


import java.util.ArrayList;
import java.util.List;

/**
 *  DataTable后台返回前台数据模型
 * 
 */
public class DataTable<T> implements java.io.Serializable {

	private static final long serialVersionUID = 6063089265957963178L;
	
	private Long sEcho = 0L;
	private Long iTotalRecords = 0L;
	private Long iTotalDisplayRecords = 0L;
	private List<T> aaData = new ArrayList<T>();
	
	public Long getsEcho() {
		return sEcho;
	}
	public void setsEcho(Long sEcho) {
		this.sEcho = sEcho;
	}
	public Long getiTotalRecords() {
		return iTotalRecords;
	}
	public void setiTotalRecords(Long iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}
	public Long getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}
	public void setiTotalDisplayRecords(Long iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}
	public List<T> getAaData() {
		return aaData;
	}
	public void setAaData(List<T> aaData) {
		this.aaData = aaData;
	}
	
}
