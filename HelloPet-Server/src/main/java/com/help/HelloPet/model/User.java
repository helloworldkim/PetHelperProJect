package com.help.HelloPet.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	//이메일을 아이디로 사용할 예정
	private String username;		
	private String password;
	private String phone;
	//권한 받는곳
	private String role;	// ROLE_USER
	// 이메일 인증 여부 확인 기본값 false
	private boolean emailVerification;
	// 이메일 토큰	임의의값
	private String emailCheckToken;
	
	//이메일 인증용 임의의 값 만들어주는 부분
	public void generateEmailCheckToken() {  
	    this.emailCheckToken = UUID.randomUUID().toString();  
	}

}

