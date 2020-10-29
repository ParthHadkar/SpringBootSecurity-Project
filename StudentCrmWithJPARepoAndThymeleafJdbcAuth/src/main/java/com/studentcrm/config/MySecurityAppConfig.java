package com.studentcrm.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;

@Configuration
@EnableWebSecurity
public class MySecurityAppConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("securtityDataSource")
	private DataSource securtityDataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(securtityDataSource);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()//.anyRequest().authenticated()
		.antMatchers("/customer/list").hasAnyRole("EMPLOYEE","MANAGER","ADMIN")
		.antMatchers("/customer/showCustomerFormForAdd").hasAnyRole("MANAGER","ADMIN")
		.antMatchers("/customer/showCustomerFormForUpdate").hasAnyRole("MANAGER","ADMIN")
		.antMatchers("/customer/deleteCustomer").hasRole("ADMIN")
		.antMatchers("/customer/**").hasRole("EMPLOYEE")
		.antMatchers("/resources/**").permitAll()
		.and()
		.formLogin()
		.loginPage("/Login")
		.loginProcessingUrl("/authenticateUser")
		.permitAll()
		.and()
		.logout().permitAll()
		.and()
		.exceptionHandling().accessDeniedPage("/access-denied");
	}
	
	
}
