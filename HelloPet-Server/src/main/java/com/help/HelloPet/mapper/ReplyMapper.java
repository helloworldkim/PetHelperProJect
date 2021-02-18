package com.help.HelloPet.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.help.HelloPet.model.Reply;

@Mapper
public interface ReplyMapper {
	
	// 입력받은 pageNum값을 넣어서 댓글을 무한정으로 부르는 형태로 만들예정 ex) pageNum=1 기본 댓글값5 limit 1*5;
	@Select({"SELECT id,content,boardid,userid,createDate FROM reply where boardid=#{boardid} order by createDate desc LIMIT #{pageNum}"})
	List<Reply> getReplyList(@Param(value = "boardid") int boardid,
			@Param("pageNum") int pageNum);
	
	@Select({"SELECT count(*) FROM reply where boardid=#{boardid}"})
	int getReplyCount(@Param(value = "boardid") int boardid);
	
//	@Insert({"INSERT INTO reply(content,boardid,userid,createDate) VALUES(#{obj.content},#{obj.boardid},#{obj.userid},now())"})
//	void ReplyWrite(@Param("obj") Reply reply);
}
