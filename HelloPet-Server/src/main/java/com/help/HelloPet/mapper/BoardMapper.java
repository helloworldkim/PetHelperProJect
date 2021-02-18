package com.help.HelloPet.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.help.HelloPet.model.Board;

@Mapper
public interface BoardMapper {
	//offset 설정 (페이지번호 - 1) * 10(개씩)
																		//10개 가져오고 	//위치0번째부터(첫번째부터)
//	@Select({"SELECT t1.* from (SELECT * FROM board order by createDate desc) t1 LIMIT 10 OFFSET 0"})
//	@Select({"SELECT t1.* from (SELECT * FROM board order by createDate desc) t1 LIMIT 0,10"})
//	@Select({"SELECT t1.* from (SELECT * FROM board order by createDate desc) t1 LIMIT #{pageNum},10"})
	@Select({"select b.id,b.title,b.content,b.count,b.userid,b.createDate,COUNT(r.id) replyCount from board b left join reply r on b.id = r.boardid group by b.id, b.title,b.content, b.count,b.userid,b.createDate order by b.createDate desc LIMIT #{pageNum},10"})
	List<Board> getBoardList(@Param("pageNum") int pageNum);
	
	@Select({"SELECT count(*) FROM board"})
	int getTotalBoard();
	
	@Select({"SELECT * FROM board where id=#{boardid}"})
	Board getBoardDetails(@Param("boardid") int boardid);

	@Update({"UPDATE board SET count = count+1 where id = #{boardid}"})
	void updateBoardCount(@Param("boardid")int boardid);
}
