package com.jel.tech.model;

import org.springframework.web.multipart.MultipartFile;

/**
 * vo object for college entity
 * @author Jelex
 *
 */
public class College {

	private String collegeName;
	private Long parentId;
	private MultipartFile collegeImg;
	private int order;
	
	
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getCollegeName() {
		return collegeName;
	}
	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}
	public MultipartFile getCollegeImg() {
		return collegeImg;
	}
	public void setCollegeImg(MultipartFile collegeImg) {
		this.collegeImg = collegeImg;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	
	
}
