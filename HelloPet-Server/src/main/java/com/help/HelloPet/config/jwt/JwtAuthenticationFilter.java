package com.help.HelloPet.config.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.help.HelloPet.config.auth.PrincipalDetails;
import com.help.HelloPet.model.User;

import lombok.RequiredArgsConstructor;

//스크링 시큐리티에 UsernamePasswordAuthenticationFilter 해당필터가 존재함
//login 요청해서 username,password를 전송하면 (post)
// UsernamePasswordAuthenticationFilter가 동작을함

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	
	// /login 요청을 하면 로그인시도를 위해서 실행되는 함수
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		System.out.println("JwtAuthenticationFilter:로그인시도중");
		
		//1. username, password 받아서
		try {
//			BufferedReader br = request.getReader();
//			String input = null;
//			while((input=br.readLine()) !=null) {
//				System.out.println(input);
//			}
			ObjectMapper om = new ObjectMapper();
			User user = om.readValue(request.getInputStream(), User.class);
			System.out.println(user);
			System.out.println("===============================================");
			UsernamePasswordAuthenticationToken authenticationToken = 
					new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
			
//			System.out.println("authenticationToken토큰"+authenticationToken);
			// PrincipalDetailsService의 loadUserByUsername() 함수가 실행됨!
			//db에 있는 username과 password가 일치한다는것!
			Authentication authentication = 
					authenticationManager.authenticate(authenticationToken);
			
			//authentication객체가 session영역에 저장됨 -> 그뜻은 로그인이 되었다는 뜻!
			PrincipalDetails principalDetails =  (PrincipalDetails) authentication.getPrincipal();
			System.out.println("로그인 완료됨:"+principalDetails.getUser().getUsername());
			System.out.println("1======================================");
			//굳이 JWT토큰을 사용하면서 session을 만들이유는 없음. 권한처리때문에 사용!
			
			return authentication;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//attemptAuthentication위의 함수가 정상적으로 실행되고 난뒤에!!! successfulAuthentication 해당함수가 실행이됨
	//JWT토큰을 만들어서 응답해줄거임
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		System.out.println("successfulAuthentication실행됨:인증완료!");
		PrincipalDetails principalDetails =  (PrincipalDetails) authResult.getPrincipal();
		System.out.println(principalDetails.getUser().getId());
		System.out.println(principalDetails.getUser().getUsername());
	//RSA방식 아니고 HASH 암호방식!
		String jwtToken = MyJjwt.createToken(principalDetails.getUser().getId(), principalDetails.getUser().getUsername());
		response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX+jwtToken);
		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    //하드코딩으로 json형식 데이터로 만들어서 보내줌
	    response.getWriter().write(
	            "{\"" + JwtProperties.HEADER_STRING + "\":\"" + JwtProperties.TOKEN_PREFIX+jwtToken+"\"}"
	    );
		
	}
}