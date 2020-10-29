package com.studentcrm.dao;

import com.studentcrm.entity.Role;

public interface RoleDao {
	
	public Role[] findRoleByName(String[] name);
	
}
