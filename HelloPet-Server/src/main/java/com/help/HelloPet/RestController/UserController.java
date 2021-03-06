package com.help.HelloPet.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.help.HelloPet.config.auth.PrincipalDetails;
import com.help.HelloPet.config.jwt.JwtProperties;
import com.help.HelloPet.config.jwt.MyJjwt;
import com.help.HelloPet.model.User;
import com.help.HelloPet.repository.UserRepository;
import com.help.HelloPet.service.UserService;

@RestController
public class UserController {
	@Autowired
	MyJjwt jjwt;
	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	//해당 유저 정보를 반환해주는 메서드
	@GetMapping("/user/details")
	public Map<String, Object> userDetails(Authentication authentication) {
		PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
		User user = principalDetails.getUser();
		System.out.println("userDetails 메서드 호출");
		user.setPassword("");
		System.out.println("JWT에 저장된 user의 정보:"+user);
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userDetails", user);
		
		return map;
	}
	
	@GetMapping("/user/jwtcheck")
	public String jwtcheck() {
		//JWT토큰 유효성 검사 뒤 정상적으로 필터체인 통과했다면 성공값을 리턴해줌
		return JwtProperties.TOKEN_SUCCESS;
	}
	//회원가입 요청 왔을경우 해당 객체 생성 후 db저장 jwt토큰을 ResponseEntity에 담아서 리턴해준다.
	@PostMapping(value = "/join")
	public ResponseEntity<Map<String,String>> registerUser(@RequestBody User user) {
		String encodedPassword= passwordEncoder.encode(user.getPassword());
		User newUser = User.builder()
				.username(user.getUsername())
				.password(encodedPassword)
				.phone(user.getPhone())
				.role("ROLE_USER")
				.emailVerification(false)
				.build();
		//이메일 토큰생성
		newUser.generateEmailCheckToken();
		System.out.println(newUser);
		//회원가입
		userRepository.save(newUser);
		 //이메일 전송
//		userService.signUpEmailSender(newUser);
//		Authorization
		Map<String, String> map =  new HashMap<>();
		String jwt = jjwt.createToken(newUser.getId(), newUser.getUsername());
		map.put(JwtProperties.HEADER_STRING,JwtProperties.TOKEN_PREFIX+jwt);
		return new ResponseEntity<>(map, null, HttpStatus.OK);
//		return "정상적으로 가입되었습니다";
		
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
