package com.help.HelloPet.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.help.HelloPet.model.User;
import com.help.HelloPet.repository.UserRepository;

import lombok.RequiredArgsConstructor;

// http://localhost:8080/login 시큐리티 설정에서 formlogin을 disable 해둬서 login으로 와도 따로 동작을 안함

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService{
	
	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("loadUserByUsername");
		User userEntity = userRepository.findByUsername(username);
		System.out.println("유저객체 확인!");
		System.out.println(userEntity.toString());
		return new PrincipalDetails(userEntity);
	}

	
}
