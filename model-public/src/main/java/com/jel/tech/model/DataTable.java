package com.jel.tech.model;


import java.util.ArrayList;
import java.util.List;

/**
 *  DataTable后台返回前台数据模型
 * 
 */
public class DataTable<T> implements java.io.Serializable {

	private static final long serialVersionUID = 6063089265957963178L;
	
	private String callback;
	private String draw;
	private Integer iDisplayStart;
	private Integer iDisplayLength;
	
	private Long sEcho = 0L;
	private Integer iTotalRecords = 0;
	private Integer iTotalDisplayRecords = 0;
	private List<T> aaData = new ArrayList<T>();
	
	public String getCallback() {
		return callback;
	}
	public void setCallback(String callback) {
		this.callback = callback;
	}
	public String getDraw() {
		return draw;
	}
	public void setDraw(String draw) {
		this.draw = draw;
	}
	public Integer getiDisplayStart() {
		return iDisplayStart;
	}
	public void setiDisplayStart(Integer iDisplayStart) {
		this.iDisplayStart = iDisplayStart;
	}
	public Integer getiDisplayLength() {
		return iDisplayLength;
	}
	public void setiDisplayLength(Integer iDisplayLength) {
		this.iDisplayLength = iDisplayLength;
	}
	public Long getsEcho() {
		return sEcho;
	}
	public void setsEcho(Long sEcho) {
		this.sEcho = sEcho;
	}
	public Integer getiTotalRecords() {
		return iTotalRecords;
	}
	public void setiTotalRecords(Integer iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}
	public Integer getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}
	public void setiTotalDisplayRecords(Integer iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}
	public List<T> getResultData() {
		return aaData;
	}
	public void setResultData(List<T> resultData) {
		this.aaData = resultData;
	}
	
	
}
