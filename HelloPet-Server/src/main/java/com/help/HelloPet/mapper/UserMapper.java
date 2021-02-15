package com.help.HelloPet.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
//	@Insert({"INSERT INTO user(email, password, name, RegDt, ModDt) VALUES (#{user.email}, #{user.password}, #{user.name}, now(), now())"})
//	int insertUser(@Param("user") UserVO user);
//
//	@Select({"SELECT count(*) FROM user WHERE email=#{id}"})
//	int isUser(@Param("id") String id);
//
//	@Select({"SELECT count(*) FROM user WHERE email=#{user.email} AND password=#{user.password}"})
//	int getUser(@Param("user") UserVO user);
//	
//	@Select({"SELECT email,name,RegDt,ModDt from user where id=#{user_id}"})
//	UserVO getUserInfoById(long user_id);
//	
//	UserVO getUserInfo(UserVO user);
//	UserVO getUserInfoForFacebook(String email);
}
