package com.studentcrm.dao;

import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.studentcrm.entity.Role;
import com.studentcrm.entity.User;

@Repository
public class RoleDaoImpl implements RoleDao {

	@Autowired
	@Qualifier("securityEntityManagerFactory")
	private EntityManager entityManager;
	
	@Override
	public Role[] findRoleByName(String[] name) {
		Role[] roles = new Role[name.length];
		Role role = null;
		try {
			// get current hibernate session
			Session session = entityManager.unwrap(Session.class);
			// create query to fetch user by username
			for(int i=0; i<name.length; i++) {
			Query<Role> query = session.createQuery("FROM Role r WHERE r.name=:rname", Role.class);
			query.setParameter("rname", name[i]);
			role = (Role) query.getSingleResult();
			roles[i] =role;
			}
			System.out.println("roles findRoleByName "+Arrays.asList(roles));
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return roles;
	}

}
