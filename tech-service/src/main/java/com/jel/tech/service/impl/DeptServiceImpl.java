package com.jel.tech.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jel.tech.dao.DeptDao;
import com.jel.tech.model.Dept;
import com.jel.tech.model.QueryVo;
import com.jel.tech.model.Tree;
import com.jel.tech.service.DeptService;

@Service("deptService")
public class DeptServiceImpl implements DeptService {

	@Autowired
	private DeptDao deptDao;
	
	@Override
	public List<Tree> queryDepts() {

		List<Dept> deptList = deptDao.queryDepts();
		
		//辅助工具
		Map<Long, Tree> map = new HashMap<Long, Tree>();
		List<Long> rootDeptIds = new ArrayList<Long>();
		List<Tree> treeList = new ArrayList<Tree>();
		
		Tree tree = null;
		for(Dept dept : deptList) {
			tree = new Tree();
			tree.setId(dept.getDeptId());
			tree.setText(dept.getDeptName());
			tree.setIcon(dept.getIcon());
			tree.setOrder(dept.getRank());
			tree.setParentId(dept.getParentId());
			
			map.put(tree.getId(), tree);
			
			if(tree.getParentId() == -1) {
				rootDeptIds.add(tree.getId());
			}
			treeList.add(tree);
		}
		
		for(Tree tr : treeList) {
			Long parentId = tr.getParentId();
			Tree pTree = map.get(parentId);
			if(pTree != null) {
				pTree.getChildren().add(tr);
			}
		}
		
		List<Tree> list = new ArrayList<Tree>();
		for(Long deptId : rootDeptIds) {
			list.add(map.get(deptId));
		}
		
		return list;
	}
	
	public List<Dept> queryDeptList() {
		return deptDao.queryDepts();
	}
	
	public List<Dept> queryDeptByName(Map<String, Object> map) {
		return deptDao.queryDeptByName(map);
	}
	@Override
	public int queryDeptCount(String keywords) {
		return deptDao.queryDeptCount(keywords);
	}

	@Override
	public void saveCollege(Dept dept) throws Exception {
		deptDao.saveCollege(dept);
	}

	@Override
	public void saveCollegeSelective(Dept dept) throws Exception {
		deptDao.saveCollegeSelective(dept);
		
	}
	
	@Override
	public List<Dept> queryDeptsByKeywords(QueryVo vo) {
		return deptDao.queryDeptsByKeywords(vo);
	}

}
