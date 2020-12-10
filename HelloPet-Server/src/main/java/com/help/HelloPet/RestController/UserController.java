package com.help.HelloPet.RestController;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.help.HelloPet.config.jwt.JwtProperties;
import com.help.HelloPet.model.User;
import com.help.HelloPet.repository.UserRepository;
import com.help.HelloPet.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;
	
//	@Autowired
//	BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping("/user/j1")
	public String halo() {
//		String encodedPassword= passwordEncoder.encode("1111");
//		User newUser = User.builder()
//				.username("localkey@naver.com")
//				.password(encodedPassword)
//				.phone("010-1111-2222")
//				.role("ROLE_USER")
//				.emailVerification(false)
//				.build();
//		//이메일 토큰생성
//		newUser.generateEmailCheckToken();
//		System.out.println(newUser);
//		//회원가입
//		userRepository.save(newUser);
		return "회원가입 ok";
	}
	@GetMapping("/hello")
	public String siba(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("인증이나 권한이 필요한 주소요청 됨");
		System.out.println(request);
		String jwtHeader = request.getHeader("Authorization");
		System.out.println("jwtHeader :"+jwtHeader);
		//header가 있는지 확인
		if(jwtHeader ==null || !jwtHeader.startsWith("Bearer")) {
//			chain.doFilter(request, response);
			return null;
		}
		String jwtToken = request.getHeader(JwtProperties.HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX, "");
		
		String email = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(jwtToken).getClaim("email").asString();
		if(email != null) {
			User userEntity = userRepository.findByUsername(email);
		}
			
	
		return "체크체크 헤더체크";
	}
	@PostMapping("/user/login")
	public Map<String, String> login(HttpServletRequest request, HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException {
		 System.out.println(request);
			ObjectMapper om = new ObjectMapper();
			User user = om.readValue(request.getInputStream(), User.class);
			System.out.println(user.getUsername());
			System.out.println(user.getPassword());
			User userEntity = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
			Map<String, String> map = new HashMap<>();
			if(userEntity != null) {
				//RSA방식 아니고 HASH 암호방식!
				String jwtToken = JWT.create()
						//헤드
						.withSubject("cos토큰")
						//페이로드
						.withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.EXPIRATION_TIME))
						.withClaim("id", userEntity.getId())
						.withClaim("email", userEntity.getUsername())
						//서명
						.sign(Algorithm.HMAC512(JwtProperties.SECRET));	//시크릿코드 사인하는부분 HMAC512은 특징적으로 시크릿값을 가지고 있어야함
				map.put("Authorization", JwtProperties.TOKEN_PREFIX+jwtToken);
				response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX+jwtToken);
				return map;
			}
			System.out.println("===============================================");
			return null;
	}
	
	//join주소로 POST요청
	@PostMapping(value = "/user/join")
	public String registerUser(@RequestBody User user) {
//		String encodedPassword= passwordEncoder.encode(user.getPassword());
		User newUser = User.builder()
				.username(user.getUsername())
				.password(user.getPassword())
				.phone(user.getPhone())
				.role("ROLE_USER")
				.emailVerification(false)
				.build();
		//이메일 토큰생성
		newUser.generateEmailCheckToken();
		System.out.println(newUser);
		//회원가입
//		userRepository.save(newUser);
		 //이메일 전송
//		userService.signUpEmailSender(newUser);
		return "정상적으로 가입되었습니다";
		
	}
	
	//이메일 인증 체크주소
	@GetMapping("/check_email")
	public String checkEmailToken(String token, String email) {
		System.out.println("이메일인증시 확인되는값들");
		System.out.println(email);
		System.out.println(token);
		User user = userRepository.findByUsername(email);
		//이메일 불일치
		if(user ==null) {
			return "인증실패";
		}
		//토큰값 불일치
		if(!user.getEmailCheckToken().equals(token)){  
	      
			 return "인증실패";  
		}
		 //이메일  인증 완료시, 인증 여부 저장  
		  user.setEmailVerification(true);
		  userRepository.save(user);
		return "인증성공";
	}
}
