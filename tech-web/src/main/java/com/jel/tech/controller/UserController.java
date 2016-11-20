package com.jel.tech.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jel.tech.common.json.JsonUtils;
import com.jel.tech.message.ResponseMessage;
import com.jel.tech.message.ResponseStatus;
import com.jel.tech.model.Tree;
import com.jel.tech.model.User;
import com.jel.tech.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/login.do",method=RequestMethod.GET)
	public String loginUI() {
		return "login";
	}
	
	@ResponseBody
	@RequestMapping(value="/login.do", method=RequestMethod.POST)
	public void login(User user, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		User resultUser = userService.login(user);
		
		if(resultUser == null) {
			request.setAttribute("user", user);
			request.setAttribute("errorMsg", "用户名或密码错误！");
			response.getWriter().print("login");
			return;
		}
		HttpSession session = request.getSession();
		session.setAttribute("currentUser", resultUser);
		response.getWriter().print("home");
	}
	
	@RequestMapping(value="/regist.do", method=RequestMethod.GET)
	public String regist(Model model) {
		User newUser = new User();
		model.addAttribute("newUser", newUser);
		return "registUI";
	}
	
	@RequestMapping(value="/add.do", method=RequestMethod.POST)
	public String add(User newUser, HttpServletRequest request) {
		int result = userService.save(newUser);
		if(result != 1) {
			request.setAttribute("errorMsg", "保存失败！");
			return "registUI";
		}
		HttpSession session = request.getSession();
		session.setAttribute("currentUser", newUser);
		return "redirect:/success.jsp";
	}
	
	@RequestMapping(value="/queryAll.do", method=RequestMethod.GET)
	public String queryAll(Model model) {
		model.addAttribute("userList", userService.queryAll());
		return "userList";
	}
	
	@RequestMapping("/htmllogin.do")
	public String htmllogin() {
		return "login";
	}
	
	@ResponseBody
	@RequestMapping("/resume.do")
	public ResponseMessage<String> queryResume() {
		
		List<User> userList = userService.queryAll();
		ResponseMessage<String> message = new ResponseMessage<String>();
		message.setMsg("请求成功");
		message.setStatus(ResponseStatus.STATUS_OK);
//		message.setOthers("{\"id\":19,\"name\":\"Jel\",\"age\":\"24\"}");
		message.setOthers(JsonUtils.toJson(userList));
		return message;
	}
	
	@ResponseBody
	@RequestMapping("/jstree.do")
	public ResponseMessage<Tree> jstree() {
		
		ResponseMessage<Tree> message = new ResponseMessage<>();
		message.setMsg("请求成功！");
		message.setStatus(ResponseStatus.STATUS_OK);
		
		Tree root = new Tree();
		root.setId(1L);
		root.setText("根节点");
		root.setIcon("http://localhost:8080/tech-web/img/cart.gif");
		
		List<Tree> children = new ArrayList<>();
		
		Tree levelTwo = new Tree();
		levelTwo.setId(11L);
		levelTwo.setText("第二级节点1");
		levelTwo.setParentId(1L);
		levelTwo.setOrder(3);
		
		Tree levelTwo2 = new Tree();
		levelTwo2.setId(12L);
		levelTwo2.setText("第二级节点2");
		levelTwo2.setParentId(1L);
		levelTwo2.setOrder(2);
		
		children.add(levelTwo);
		children.add(levelTwo2);
		
		Tree levelThree = new Tree();
		levelThree.setId(101L);
		levelThree.setText("第三级节点");
		levelThree.setParentId(11L);
		
		root.setChildren(children);
		
		children = new ArrayList<>();
		children.add(levelThree);
		levelTwo.setChildren(children);
		
		message.setOthers(root);
		
		return message;
	}
	
}
