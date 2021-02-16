package com.help.HelloPet.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.help.HelloPet.model.Reply;

@Mapper
public interface ReplyMapper {

	@Select({"SELECT id,content,boardid,userid,createDate,count(*) FROM reply where boardid=#{boardid}"})
	List<Reply> getReplyList(@Param(value = "boardid") int boardid);
	
	@Select({"SELECT count(*) FROM reply where boardid=#{boardid}"})
	int getReplyCount(@Param(value = "boardid") int boardid);
}
