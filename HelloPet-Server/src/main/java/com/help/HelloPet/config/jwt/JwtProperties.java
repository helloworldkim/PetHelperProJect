package com.help.HelloPet.config.jwt;

public interface JwtProperties {
	String SECRET = "cos";	//우리서버만 알고있는 비밀값
	int EXPIRATION_TIME = (60000*30); //30분		//10일 (1/1000초) //864000000
	String TOKEN_PREFIX = "Bearer ";
	String HEADER_STRING = "Authorization";
	String TOKEN_SUCCESS = "정상토큰";
	String TOKEN_EXPIRED = "토큰만료";
	String TOKEN_UNKMOWN = "비정상적인접근";

}
