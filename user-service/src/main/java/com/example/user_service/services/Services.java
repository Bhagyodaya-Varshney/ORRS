package com.example.user_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.user_service.repositories.UserRepository;


@Service
public class Services {
	
	@Autowired
	UserRepository userRepository;
	
		public UserDetailsService userDetailService() {
			return new UserDetailsService() {
				@Override
				public UserDetails loadUserByUsername(String username) {
					System.out.println(userRepository.findByEmail(username).get().getEmail());
					return userRepository.findByEmail(username)
							.orElseThrow(() -> new UsernameNotFoundException("User not found"));
				}
			};
		}

}
