package com.studentcrm.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.studentcrm.service.UserService;

@Configuration
@EnableWebSecurity
public class MySecurityAppConfig extends WebSecurityConfigurerAdapter {

	@Autowired
    private UserService userService;
	
    @Autowired
    private CustomAutenticationSuccessHandler customAutenticationSuccessHandler;
    
  //beans
  	//bcrypt bean definition
  	@Bean
  	public BCryptPasswordEncoder passwordEncoder() {
  		return new BCryptPasswordEncoder();
  	}

  	//authenticationProvider bean definition
  	@Bean
  	public DaoAuthenticationProvider authenticationProvider() {
  		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
  		auth.setUserDetailsService(userService); //set the custom user details service
  		auth.setPasswordEncoder(passwordEncoder()); //set the password encoder - bcrypt
  		return auth;
  	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
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
		.successHandler(customAutenticationSuccessHandler)
		.permitAll()
		.and()
		.logout().permitAll()
		.and()
		.exceptionHandling().accessDeniedPage("/access-denied");
	}
	
	
}
