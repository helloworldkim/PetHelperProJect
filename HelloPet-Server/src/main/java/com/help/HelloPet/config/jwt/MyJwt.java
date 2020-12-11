package com.help.HelloPet.config.jwt;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Service
public class MyJwt {
	 //토큰 생성
    public String createToken(int id, String username) {
    	//RSA방식 아니고 HASH 암호방식!
		String jwtToken = JWT.create()
				//헤드
				.withSubject("cos토큰")
				//페이로드
				.withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.EXPIRATION_TIME))
				.withClaim("id", id)
				.withClaim("email", username)
				//서명
				.sign(Algorithm.HMAC512(JwtProperties.SECRET));	//시크릿코드 사인하는부분 HMAC512은 특징적으로 시크릿값을 가지고 있어야함
        return jwtToken;
    }
    
//    //토큰 검증
//    public Map<String, Object> verifyJWT(String jwt) throws UnsupportedEncodingException {
//        Map<String, Object> claimMap = null;
//        try {
//            Claims claims = Jwts.parser()
//                    .setSigningKey(key.getBytes("UTF-8")) // Set Key
//                    .parseClaimsJws(jwt) // 파싱 및 검증, 실패 시 에러
//                    .getBody();
//
//            claimMap = claims;
//
//            //Date expiration = claims.get("exp", Date.class);
//            //String data = claims.get("data", String.class);
//            
//        } catch (ExpiredJwtException e) { // 토큰이 만료되었을 경우
//            System.out.println(e);
//            ...
//        } catch (Exception e) { // 그외 에러났을 경우
//            System.out.println(e);
//            ...
//        }
//        return claimMap;
//    }    
}
