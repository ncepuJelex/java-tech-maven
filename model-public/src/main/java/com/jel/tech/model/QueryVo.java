package com.jel.tech.model;

import org.springframework.data.domain.PageRequest;

public class QueryVo {

	private String keywords;
	
	private PageRequest pageable;

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public PageRequest getPageable() {
		return pageable;
	}

	public void setPageable(PageRequest pageable) {
		this.pageable = pageable;
	}
	
	
}
