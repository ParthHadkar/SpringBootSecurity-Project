package com.studentcrm.dao;

import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.studentcrm.entity.Role;
import com.studentcrm.entity.User;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	@Qualifier("securityEntityManagerFactory")
	private EntityManager entityManager;
	
	@Override
	public User findUserByUserName(String name) {
		User user = null;
		try {
			// get current hibernate session
			Session session = entityManager.unwrap(Session.class);
			// create query to fetch user by username
			Query<User> query = session.createQuery("FROM User u WHERE u.username=:uname", User.class);
			query.setParameter("uname", name);
			user = (User) query.getSingleResult();
		} 
		catch (NoResultException e) {
			user = null;
			//e.printStackTrace();
		}
		catch (Exception e) {
			user = new User();
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public void save(User user) {
		try {
			// get current hibernate session
			Session session = entityManager.unwrap(Session.class);
			System.out.println(user.getId()+"roles "+user);
			// save or update user to db
			/*if(user.getId() == 0) {
				session.save(user);
			}
			else {
				//session.saveOrUpdate(user);
				session.update(user);
			}*/
			session.saveOrUpdate(user);
		} 
		catch (NoResultException e) {
			user = null;
			e.printStackTrace();
		}
		catch (Exception e) {
			user = null;
			e.printStackTrace();
		}
	}

}
