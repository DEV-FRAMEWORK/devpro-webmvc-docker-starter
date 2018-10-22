package com.ktds.devpro.sample;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SampleUserDetailServiceImpl implements UserDetailsService {
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		
		SampleUserVo user = findUserbyUername(username);
		UserBuilder builder = null;

		if(user !=null) {
			builder = User.withUsername(username);
			builder.password(new BCryptPasswordEncoder().encode(user.getPassword()));
			builder.roles(user.getRoles());
			
		}else {
			throw new UsernameNotFoundException("User not found!");
		}
		return builder.build();
	}

	
	
	private SampleUserVo findUserbyUername(String username) {
	    if(username.equalsIgnoreCase("admin")) {
	      return new SampleUserVo(username, "1234", "ADMIN");
	    }
	    return null;
	  }
}
