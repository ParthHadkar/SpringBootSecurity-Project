package com.studentcrm.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.studentcrm.dao.RoleDao;
import com.studentcrm.dao.UserDao;
import com.studentcrm.entity.Role;
import com.studentcrm.entity.User;
import com.studentcrm.model.CrmUser;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	@Transactional("securityTransactionManager")
	public User findUserByUserName(String name) {
		return userDao.findUserByUserName(name);
	}

	@Override
	@Transactional("securityTransactionManager")
	
	public void save(CrmUser crmUser) {
		User user = new User();
		user.setUsername(crmUser.getUsername());
		user.setPassword(passwordEncoder.encode(crmUser.getPassword()));
		user.setFirstname(crmUser.getFirstname());
		user.setLastName(crmUser.getLastName());
		user.setEmailId(crmUser.getEmailId());
		user.setRoles(Arrays.asList(roleDao.findRoleByName(crmUser.getRoles())));
		/*User user = new User(crmUser.getUsername(),passwordEncoder.encode(crmUser.getPassword())
				,crmUser.getFirstname(),crmUser.getLastName(),crmUser.getEmailId()
				,Arrays.asList(roleDao.findRoleByName(crmUser.getRoles())));*/
		/*for(Role tempRole:Arrays.asList(roleDao.findRoleByName(crmUser.getRoles()))) {
			user.addRole(tempRole);
		}*/
		System.out.println("roles save "+user.getRoles());
		userDao.save(user);
	}
	
	@Override
	@Transactional("securityTransactionManager")
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findUserByUserName(username);
		if(user == null) {
			throw new UsernameNotFoundException("Invalids usernames or passwords");
		}
        List<GrantedAuthority > authorities = AuthorityUtils.createAuthorityList();
        
        // if the user selected role other than employee, 
        // then add that one too (multiple roles)
        //Collection<Role> formRole = user.getRoles();
        List<Role> formRole = user.getRoles();
        System.out.println(">>>> user roles "+user.getRoles());
        for ( Role formRoles:formRole) {
        		authorities.add(new SimpleGrantedAuthority(formRoles.getName()));
        }
        /*return new org.springframework.security.core.userdetails.User(user.getUsername(),
				user.getPassword(), mapRolesToAuthorities(user.getRoles()));*/
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
		user.getPassword(),authorities);
		
		
	}
	
	private Collection<? extends GrantedAuthority>mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new
		SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}
	
}
