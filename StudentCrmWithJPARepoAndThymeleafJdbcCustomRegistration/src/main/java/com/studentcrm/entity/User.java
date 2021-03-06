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
import javax.persistence.UniqueConstraint;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;

	@Column(name = "first_name")
	private String firstname;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "email")
	private String emailId;
	
	@ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinTable(name = "users_roles"
	,joinColumns = {@JoinColumn(name="user_id", referencedColumnName = "id")}
	,inverseJoinColumns = {@JoinColumn(name="role_id", referencedColumnName = "id")})//,uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role_id"}
	//private Collection<Role> roles;
	private List<Role> roles;
	
	public User() {}
	
	

	public User(String username, String password, String firstname, String lastName, String emailId,
			List<Role> roles) {
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastName = lastName;
		this.emailId = emailId;
		this.roles = roles;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/*public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}*/
	
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public void addRole(Role role) {
		if(roles == null) {
			roles = new ArrayList<Role>();
		}
		roles.add(role);
		role.getUsers().add(this);
    }
 
    public void removeRole(Role role) {
    	roles.remove(role);
    	role.getUsers().remove(this);
    }
 
	
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", firstname=" + firstname
				+ ", lastName=" + lastName + ", emailId=" + emailId + ", roles=" + roles + "]";
	}

	
	
}
