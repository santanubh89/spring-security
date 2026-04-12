package com.springsecurity.webapp.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.springsecurity.webapp.jpa.UserData;

public class MyUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;

	private String username;

	private String password;

	private List<SimpleGrantedAuthority> authorities;

	private boolean active;

	public MyUserDetails(String username) {
		this.username = username;
	}

	public MyUserDetails(UserData user) {
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.authorities = Arrays.stream(user.getRoles().split(",")).map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
		this.active = user.isActive();

	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.active;
	}

}
