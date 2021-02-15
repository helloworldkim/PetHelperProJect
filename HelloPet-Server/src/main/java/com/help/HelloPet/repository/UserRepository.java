package com.help.HelloPet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.help.HelloPet.model.User;

//repository 어노테이션 생략가능
public interface UserRepository extends JpaRepository<User, Integer> {
	
	public User findByUsername(String email);
	
	public User findByUsernameAndPassword(String email,String password);
	
}
