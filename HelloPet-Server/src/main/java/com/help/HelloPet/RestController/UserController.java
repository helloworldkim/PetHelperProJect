package com.help.HelloPet.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.help.HelloPet.config.jwt.JwtProperties;
import com.help.HelloPet.config.jwt.MyJjwt;
import com.help.HelloPet.config.jwt.MyJwt;
import com.help.HelloPet.model.User;
import com.help.HelloPet.repository.UserRepository;
import com.help.HelloPet.service.UserService;

@RestController
public class UserController {
	@Autowired
	MyJwt jwt;
	@Autowired
	MyJjwt jjwt;
	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping("/redi")
	public void redi(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.sendRedirect("http://localhost:3000/login");
	}
	@GetMapping("/api/v1/user")
	public String user() {
		return"유저!";
	}
	@GetMapping("/api/v1/manager")
	public String manager() {
		return"manager!";
	}
	@GetMapping("/api/v1/admin")
	public String admin() {
		return"admin!";
	}
	@GetMapping("/user/j1")
	public String halo() {
		String encodedPassword= passwordEncoder.encode("1111");
		User newUser = User.builder()
				.username("localkey@naver.com")
				.password(encodedPassword)
				.phone("010-1111-2222")
				.role("ROLE_USER")
				.emailVerification(false)
				.build();
		//이메일 토큰생성
		newUser.generateEmailCheckToken();
		System.out.println(newUser);
		//회원가입
		userRepository.save(newUser);
		return "회원가입 ok";
	}
	@GetMapping("/hello")
	public String siba(HttpServletRequest request, HttpServletResponse response) throws InterruptedException, IOException {
		System.out.println("인증이나 권한이 필요한 주소요청 됨");
		String jwtHeader = request.getHeader("Authorization");
		System.out.println("jwtHeader :"+jwtHeader);
		//header가 있는지 확인
		if(jwtHeader ==null || !jwtHeader.startsWith("Bearer")) {
//			chain.doFilter(request, response);
			return null;
		}
		String jwtToken = request.getHeader(JwtProperties.HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX, "");
		//유효기간 만료 아니면 정상토근이라고 반환됨 만료시 토큰만료 라고 String값 반환
//		정상토큰인지 아닌지 확인! 
		String result = jjwt.getTokenFromJwtString(jwtToken); 
//		토큰이 정상이면 result값으로 정상토큰 만료되었을 경우 만료토큰
		return result;
	}
	@PostMapping("/user/login")
	public Map<String, String> login(HttpServletRequest request, HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException {
			ObjectMapper om = new ObjectMapper();
			User user = om.readValue(request.getInputStream(), User.class);
			//해당 유저객체가 있을경우
			User userEntity = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
			Map<String, String> map = new HashMap<>();
			if(userEntity != null) {
				//jjwt 라이브러리
				//토큰생성
				String jwtToken = jjwt.createToken(userEntity.getId(), userEntity.getUsername());
				map.put(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX+jwtToken);	//Authorization : Bearer 토큰값 형식
				map.put("result", "success");
//				response.addHeader("Authorization", jwtToken);
				return map; //데이터 body에 정보전달 해서 react에서 session스토리지에 저장할예정
			}
			System.out.println("===============================================");
			map.put("result", "fail");
			return map;
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
