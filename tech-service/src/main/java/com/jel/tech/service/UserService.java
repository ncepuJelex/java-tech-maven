package com.jel.tech.service;

import java.util.List;

import com.jel.tech.model.User;


/**
 * 用户service接口
 * @author Administrator
 *
 */
public interface UserService {

	public User login(User user);

	public int save(User newUser);

	public List<User> queryAll();
}
