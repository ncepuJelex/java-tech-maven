package com.jel.tech.dao;

import java.util.List;

import com.jel.tech.model.Dept;
import com.jel.tech.model.QueryVo;


public interface DeptDao {

	public List<Dept> queryDepts();
	public List<Dept> queryDeptsByKeywords(QueryVo vo);
	public int queryDeptCount(String keywords);
	public void saveCollege(Dept dept);
	public void saveCollegeSelective(Dept dept);
	
}
