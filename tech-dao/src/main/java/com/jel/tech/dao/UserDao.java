package com.jel.tech.dao;

import java.util.List;

import com.jel.tech.model.User;


public interface UserDao {

	public User login(User user);

	public int save(User newUser);

	public List<User> queryAll();
}
