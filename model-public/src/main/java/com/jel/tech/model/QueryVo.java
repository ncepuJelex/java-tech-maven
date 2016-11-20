package com.jel.tech.model;

/**
 * datatable 查询VO类
 * @author zhenhua
 *
 */
public class QueryVo {

	private String keywords;
	private int offset;
	private int pageSize;
	
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	
}
