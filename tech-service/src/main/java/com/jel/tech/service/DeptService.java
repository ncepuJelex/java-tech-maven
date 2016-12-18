package com.jel.tech.service;

import java.util.List;
import java.util.Map;

import com.jel.tech.model.Dept;
import com.jel.tech.model.QueryVo;
import com.jel.tech.model.Tree;

/**
 * 大学部门接口
 * @author Administrator
 *
 */
public interface DeptService {

	public List<Tree> queryDepts();
	public List<Dept> queryDeptByName(Map<String, Object> map);
	public List<Dept> queryDeptList();
	public int queryDeptCount(String keywords);
	public List<Dept> queryDeptsByKeywords(QueryVo vo);
	public void saveCollege(Dept dept) throws Exception;
	public void saveCollegeSelective(Dept dept) throws Exception;
}
