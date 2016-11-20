package com.jel.tech.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONPObject;
import com.jel.tech.model.Dept;
import com.jel.tech.model.QueryVo;
import com.jel.tech.service.DeptService;

@RequestMapping("/datatable")
@Controller
public class DatatableHandler {

	@Autowired
	private DeptService deptService;
	
	 @RequestMapping(value = "/getJsonData.do",method=RequestMethod.GET, produces = "text/plain;charset=utf-8")  
	  @ResponseBody  
	  public String getJsonData(HttpServletRequest req,   
		      @RequestParam(required = false) String callback,  
		      @RequestParam(required = false) String keywords,  
		      @RequestParam(required = false) String draw,   
		      @RequestParam(required = false) Integer start,  
		      @RequestParam(required = false) Integer length) {  
	  
		if(start == null ) {
			start = 0;
		}
		if(length == null) {
			length = 5;
		}
		 
	    Map<String, Object> map = new HashMap<>();  
	    int totalCount = deptService.queryDeptCount(keywords);  

	    QueryVo vo = new QueryVo();
	    vo.setKeywords(keywords);
	    vo.setOffset(start / length);
	    vo.setPageSize(length);
	    List<Dept> results = deptService.queryDeptsByKeywords(vo);
	    
	   /**
	    * recordsTotal 总记录数
	    *	recordsFiltered 过滤后的总记录数
	    *	data 具体的数据对象数组，用于前台展现
	    *	当serverside=true的情况下，并且以ajax的方式提交请求，
	    *	那么这四个参数必须在后台中必须全部提供，缺一不可，否则jquery table无法显示数据
	    **/
	    
	    map.put("draw", draw);
	    map.put("recordsTotal", totalCount);
	    map.put("recordsFiltered", totalCount);
	    map.put("data", results);
	    
	    JSONPObject obj = new JSONPObject(callback);
	    obj.addParameter(map);
	    
	    return obj.toString();
	  }  

}
