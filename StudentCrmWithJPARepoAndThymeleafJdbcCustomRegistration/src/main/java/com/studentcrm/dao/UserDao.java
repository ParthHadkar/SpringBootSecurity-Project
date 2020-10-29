package com.studentcrm.dao;

import com.studentcrm.entity.User;

public interface UserDao {

	public User findUserByUserName(String name);
	public void save(User user);
	
}
