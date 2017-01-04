package com.jel.tech.common;

import org.junit.Test;

import com.jel.tech.common.restful.RestfulRequest;
import com.jel.tech.common.restful.RestfulResponse;
import com.jel.tech.common.restful.RestfulUtils;
/**
 * 
 * @author winnie.wang 王冰韬
 * date：2017-01-04
 * description：测试restful服务
 */
public class RestfulUtilsTest {
	
	@Test
	public void restful(){
		
		RestfulRequest   request=null;
		RestfulResponse  response=null;
		request=new RestfulRequest();
		request.setRequestURL("http://localhost:8080/tech-web/datatables/getJsonData.do");
		
		String s = "{\"draw\":1,\"start\":0,\"length\":10,\"search\":{\"value\":\"\",\"regex\":false},\"columns\":[{\"data\":\"deptId\",\"name\":\"\",\"searchable\":true,\"orderable\":true,\"search\":{\"value\":\"\",\"regex\":false}},{\"data\":\"deptName\",\"name\":\"\",\"searchable\":true,\"orderable\":true,\"search\":{\"value\":\"\",\"regex\":false}},{\"data\":\"parentId\",\"name\":\"\",\"searchable\":true,\"orderable\":true,\"search\":{\"value\":\"\",\"regex\":false}},{\"data\":\"icon\",\"name\":\"\",\"searchable\":true,\"orderable\":true,\"search\":{\"value\":\"\",\"regex\":false}},{\"data\":\"rank\",\"name\":\"\",\"searchable\":true,\"orderable\":true,\"search\":{\"value\":\"\",\"regex\":false}},{\"data\":\"deptId\",\"name\":\"\",\"searchable\":true,\"orderable\":true,\"search\":{\"value\":\"\",\"regex\":false}}],\"order\":[{\"column\":0,\"dir\":\"asc\"}]}";
		request.setRequestJSON(s);
		response=RestfulUtils.fireRequest(request);
		System.err.println(response);
		System.err.println(response.getResponseJSON());
		
	}
	
	
	

}
