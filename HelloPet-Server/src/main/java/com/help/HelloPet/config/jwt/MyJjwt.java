package com.help.HelloPet.config.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@Service
public class MyJjwt {
	
	
	public static  String createToken(Integer id, String username) {
	    String SECRET = JwtProperties.SECRET;
	    int expiredTime = JwtProperties.EXPIRATION_TIME;
	    
	    Map<String, Object> headers = new HashMap<>();    
	    headers.put("typ", "JWT");
	    headers.put("alg", "HS512");
	     
	    Map<String, Object> payloads = new HashMap<>();
//	    Long expiredTime = 1000 * 60l*30; // 만료기간 30분
	    payloads.put("id", id);
	    payloads.put("username", username);
	    Date ext = new Date(); // 토큰 만료 시간
        ext.setTime(ext.getTime() + expiredTime);
	    
	    String jwt = Jwts.builder()
	            .setHeader(headers)
	            .setSubject("cos")
	            .setClaims(payloads)
	            .setExpiration(ext)
	            .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
	            .compact();
	    return jwt;
	    
	}
	
	//토큰이 정상인지 만료인지 체크하는 메서드
	public static String getTokenFromJwtString(String jwtTokenString)  {
		String SECRET = JwtProperties.SECRET;
		try {
			Claims claims = Jwts.parser()
			        .setSigningKey(SECRET.getBytes())
			        .parseClaimsJws(jwtTokenString)
			        .getBody();
			if(claims!=null) {
				return JwtProperties.TOKEN_SUCCESS;
			}
		} catch (ExpiredJwtException  e) { //토큰만료시 오류
			System.out.println(e);
			return JwtProperties.TOKEN_EXPIRED;
		}catch(SignatureException e) {
			System.out.println("JWT토큰 서명이 잘못되었습니다.");
			return JwtProperties.TOKEN_UNKMOWN;
		}
		catch(Exception e) {
			System.out.println(e);
		}
		
		return null;
		
	}
	//클레임(페이로드 내용들)을 리턴하는 메서드
	public static Claims getClaims(String jwtTokenString) {
		String SECRET = JwtProperties.SECRET;
		Claims claims = Jwts.parser()
		        .setSigningKey(SECRET.getBytes())
		        .parseClaimsJws(jwtTokenString)
		        .getBody();
		return claims;
	}
//	클레임에서 유저네임만 가져오는 메서드
	public static String getUsernameFromClaim(Claims claims) {
		String username = claims.get("username").toString();
		return username;
	}
	
}
