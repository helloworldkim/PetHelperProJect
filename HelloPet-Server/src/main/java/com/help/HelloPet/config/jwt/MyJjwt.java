package com.help.HelloPet.config.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class MyJjwt {
	
	
	public String createToken(int id, String username) {
	    String SECRET = JwtProperties.SECRET;
	    
	    Map<String, Object> headers = new HashMap<>();    
	    headers.put("typ", "JWT");
	    headers.put("alg", "HS512");
	     
	    Map<String, Object> payloads = new HashMap<>();
	    Long expiredTime = 1000 * 60l; // 만료기간 1분    
//	    Date now = new Date();
//	    now.setTime(now.getTime() + expiredTime);    
//	    payloads.put("exp", now);
	    payloads.put("id", id);
	    payloads.put("username", username);
	    Date ext = new Date(); // 토큰 만료 시간
        ext.setTime(ext.getTime() + expiredTime);
	    
	    String jwt = Jwts.builder()
	            .setHeader(headers)
	            .setClaims(payloads)
	            .setExpiration(ext)
	            .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
	            .compact();
	    return jwt;
	    
	}
	public String getTokenFromJwtString(String jwtTokenString)  {
		String SECRET = JwtProperties.SECRET;
		try {
			Claims claims = Jwts.parser()
			        .setSigningKey(SECRET.getBytes())
			        .parseClaimsJws(jwtTokenString)
			        .getBody();
			if(claims!=null) {
				return"정상토큰";
			}
		} catch (ExpiredJwtException  e) { //토큰만료시 오류
			System.out.println(e);
			return "토큰만료";
		}catch(Exception e) {
			System.out.println(e);
		}
		
		return null;
		
	}
	
}
