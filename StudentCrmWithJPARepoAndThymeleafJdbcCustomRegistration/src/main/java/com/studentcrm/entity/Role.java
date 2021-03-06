package com.studentcrm.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="role")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@Column(name = "name")
	private String name;
	
	@ManyToMany(mappedBy = "roles",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	/*@JoinTable(name = "users_roles"
	,joinColumns = {@JoinColumn(name="role_id", referencedColumnName = "id")}
	,inverseJoinColumns = {@JoinColumn(name="user_id", referencedColumnName = "id")})*/
	//private Collection<User> users;
	private List<User> users;

	public Role() {}

	public Role(String name) {
		super();
		this.name = name;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/*public Collection<User> getUsers() {
		return users;
	}

	public void setUsers(Collection<User> users) {
		this.users = users;
	}*/
	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	public void addUser(User user) {
		if(users == null) {
			users = new ArrayList<User>();
		}
		users.add(user);
		user.getRoles().add(this);
    }
 
    public void removeUser(User user) {
    	users.remove(user);
    	user.getRoles().remove(this);
    }
 

	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name +"]";
	}

	
}
