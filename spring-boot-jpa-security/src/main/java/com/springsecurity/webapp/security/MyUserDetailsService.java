package com.springsecurity.webapp.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springsecurity.webapp.jpa.UserData;
import com.springsecurity.webapp.jpa.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// return new MyUserDetails(username);
		Optional<UserData> user = userRepository.findByUsername(username);
		
		user.orElseThrow(() -> new UsernameNotFoundException("Not Found: " + username));
		
		return user.map(MyUserDetails::new).get();
	}

}
