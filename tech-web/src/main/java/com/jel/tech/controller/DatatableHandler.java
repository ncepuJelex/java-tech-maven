package com.jel.tech.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONPObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jel.tech.common.json.JsonUtils;
import com.jel.tech.model.Dept;
import com.jel.tech.model.QueryVo;
import com.jel.tech.model.datatables.DatatableOrder;
import com.jel.tech.model.datatables.DatatableRequest;
import com.jel.tech.model.datatables.DatatableResponse;
import com.jel.tech.service.DeptService;

@RequestMapping("/datatables")
@Controller
public class DatatableHandler {
	private static final Logger logger = LoggerFactory.getLogger(DatatableHandler.class);
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
		int totalCount = deptService.queryDeptCount(keywords);  
		if(length == -1) {
			length = totalCount;
		}
	    Map<String, Object> map = new HashMap<>();  
	    PageRequest pageable = new PageRequest((start / length), length);  

	    QueryVo vo = new QueryVo();
	    vo.setKeywords(keywords);
	    vo.setPageable(pageable);
	    
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
	    logger.info(obj.toString());
	    return obj.toString();
	  } 
	
	@ResponseBody
	@RequestMapping(value = "/getJsonData3.do", method = RequestMethod.POST,produces="application/json; charset=UTF-8")
	public String getDataTable(String callback,@RequestBody DatatableRequest request) {

		DatatableResponse<Dept> response = new DatatableResponse<Dept>();
		response.setDraw(request.getDraw());

		Integer start = request.getStart();
		Integer length = request.getLength();

		int totalCount = deptService.queryDeptCount(null);
		if (length == -1) {
			length = totalCount;
		}
		PageRequest pageable = new PageRequest((start / length), length);

		QueryVo vo = new QueryVo();
		// vo.setKeywords(keywords);
		vo.setPageable(pageable);

		List<Dept> results = deptService.queryDeptsByKeywords(vo);

		response.setRecordsTotal(totalCount); 
		response.setRecordsFiltered(totalCount);
		response.setData(results);
		String json = JsonUtils.toJson(response);
		logger.info(json);
		return callback+"("+json+")";
	}
	
	@ResponseBody
	@RequestMapping(value = "/getJsonData4.do", method = RequestMethod.POST,produces="application/json; charset=UTF-8")
	public String getDataTables(String callback,@RequestBody DatatableRequest request) {
		
		DatatableResponse<Dept> response = new DatatableResponse<Dept>();
		response.setDraw(request.getDraw());
		//分页
		Integer start = request.getStart();
		Integer length = request.getLength();
		PageHelper.startPage(start, length);
		//对应数据库中的列名称
		String [] columnNames = {"dept_id","dept_name","parent_id","icon","rank"};
		//排序
		/*
		 * request.getOrder()中的数据可能如下：
		 * [DatatableOrder [column=0, dir=asc], DatatableOrder [column=2, dir=desc]]
		 * 经过for循环处理后：
		 * [DatatableOrder [column=0, dir=dept_id asc], DatatableOrder [column=2, dir=parent_id desc]]
		 * 此时，orderBy = dept_id asc,dir=parent_id desc
		 * 至此组成完整的sql语句：
		 * select * from tableName 
		 * where condition 
		 * limit start, length 
		 * order by dept_id asc,dir=parent_id desc
		 */
		for(DatatableOrder order : request.getOrder()) {
			order.setDir(StringUtils.join(Arrays.asList(columnNames[order.getColumn()], order.getDir()), " "));
		}
		String orderBy = StringUtils.join(request.getOrder().stream().map(DatatableOrder::getDir).toArray(), ",");
		PageHelper.orderBy(orderBy);
		
		List<Dept> depts = deptService.queryDeptByName(request.getParamMap());
		PageInfo<Dept> pageInfo = new PageInfo<Dept>(depts);
		
		response.setRecordsTotal((int)pageInfo.getTotal()); 
		response.setRecordsFiltered((int)pageInfo.getTotal());
		response.setData(pageInfo.getList());
		String json = JsonUtils.toJson(response);
		logger.info(json);
		/*
		 * jQuery111305123673508038207_1482069843425({"draw":1,"recordsTotal":12,"recordsFiltered":12,"data":[{"deptId":1,"deptName":"大学","parentId":-1,"icon":"http://localhost:8080/tech-web/images/cart.gif","rank":1},{"deptId":10,"deptName":"综合大学","parentId":1,"icon":"http://localhost:8080/tech-web/images/cart.gif","rank":10},{"deptId":11,"deptName":"专业大学","parentId":1,"icon":"http://localhost:8080/tech-web/images/cart.gif","rank":11},{"deptId":101,"deptName":"清华大学","parentId":10,"icon":"http://localhost:8080/tech-web/images/cart.gif","rank":101},{"deptId":102,"deptName":"北京大学","parentId":10,"icon":"http://localhost:8080/tech-web/images/cart.gif","rank":102},{"deptId":103,"deptName":"华北电力大学","parentId":11,"icon":"http://localhost:8080/tech-web/images/cart.gif","rank":103},{"deptId":104,"deptName":"河海大学","parentId":10,"icon":"http://localhost:8080/tech-web/images/cart.gif","rank":104},{"deptId":105,"deptName":"野鸡大学","parentId":11,"icon":"http://localhost:8080/tech-web/images/cart.gif","rank":105},{"deptId":110,"deptName":"耶鲁大学","parentId":11,"icon":"/tech-web/images/cart.gif","rank":17},{"deptId":111,"deptName":"剑桥大学","parentId":11,"icon":"/tech-web/images/cart.gif","rank":24}]})
		 */
		return callback.concat("(").concat(json).concat(")");
	}
}
