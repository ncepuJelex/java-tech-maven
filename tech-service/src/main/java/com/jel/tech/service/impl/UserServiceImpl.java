package com.jel.tech.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jel.tech.dao.UserDao;
import com.jel.tech.model.User;
import com.jel.tech.service.UserService;


@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	public User login(User user) {
		return userDao.login(user);
	}

	public int save(User newUser) {
		return userDao.save(newUser);
	}

	public List<User> queryAll() {
		return userDao.queryAll();
	}

}
