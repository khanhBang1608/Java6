package com.java6.demoJV6.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.java6.demoJV6.entity.UserEntity;
import com.java6.demoJV6.jpa.UserJPA;


@Service
public class CustomUserDetailsService implements UserDetailsService {
	private final UserJPA userRepository;

    public CustomUserDetailsService(UserJPA userRepository) {
        this.userRepository = userRepository;
    }
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + email));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword()) // Ensure password is encoded
                .roles(user.getRoleEnum().name()) // Use the enum name as the role
                .build();
	}

}
