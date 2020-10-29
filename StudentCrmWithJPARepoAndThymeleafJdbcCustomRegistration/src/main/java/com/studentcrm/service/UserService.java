package com.studentcrm.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.studentcrm.entity.User;
import com.studentcrm.model.CrmUser;


public interface UserService  extends UserDetailsService{

	public User findUserByUserName(String name);
	public void save(CrmUser crmUser);

}
