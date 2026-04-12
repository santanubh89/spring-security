package com.springsecurity.webapp.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class AuthenticationConfiguration extends WebSecurityConfigurerAdapter {
	
	private final String AUTHENTICATION_QUERY = "select username, password, enabled from users where username = ?";
	private final String AUTHORIZATION_QUERY = "select username, authority from authorities where username = ?";
	
	@Autowired
	DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		/**
		auth.jdbcAuthentication()
		.dataSource(dataSource)
		.withDefaultSchema()
		.withUser(User.withUsername("user1").password("pass1").roles("USER"))
		.withUser(User.withUsername("admin1").password("pass1").roles("ADMIN"))
		;
		*/
		
		auth.jdbcAuthentication()
		.dataSource(dataSource)
		.usersByUsernameQuery(AUTHENTICATION_QUERY)
		.authoritiesByUsernameQuery(AUTHORIZATION_QUERY)
		;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/admin").hasRole("Admin")
		.antMatchers("/user").hasAnyRole("User", "Admin")
		.antMatchers("/").permitAll()
		.and().formLogin()
		.and().httpBasic()
		;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance(); 
	}

}