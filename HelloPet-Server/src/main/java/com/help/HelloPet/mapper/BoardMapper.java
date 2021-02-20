package com.help.HelloPet.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.help.HelloPet.model.Board;
import com.help.HelloPet.model.Reply;

@Mapper
public interface BoardMapper {
	//offset 설정 (페이지번호 - 1) * 10(개씩)
																		//10개 가져오고 	//위치0번째부터(첫번째부터)
//	@Select({"SELECT t1.* from (SELECT * FROM board order by createDate desc) t1 LIMIT 10 OFFSET 0"})
//	@Select({"SELECT t1.* from (SELECT * FROM board order by createDate desc) t1 LIMIT 0,10"})
//	@Select({"SELECT t1.* from (SELECT * FROM board order by createDate desc) t1 LIMIT #{pageNum},10"})
//	@Select({"select b.id,b.title,b.content,b.count,b.userid,b.createDate,COUNT(r.id) replyCount from board b left join reply r on b.id = r.boardid where b.deletedBoard = 0 group by b.id, b.title,b.content, b.count,b.userid,b.createDate order by b.createDate desc LIMIT #{pageNum},10"})
	@Select({"select b.id,b.title,b.content,b.count,b.userid,b.createDate, if( isnull(r.replyCount),0,r.replyCount) replyCount, u.username\r\n"
			+ "from board b left join \r\n"
			+ "(select r.*, count(r.deletedReply) replyCount \r\n"
			+ "from reply r\r\n"
			+ "where deletedReply=0\r\n"
			+ "group by r.boardid) r \r\n"
			+ "on b.id = r.boardid \r\n"
			+ "left join user u on b.userid = u.id\r\n"
			+ "where b.deletedBoard = 0 \r\n"
			+ "group by b.id, b.title,b.content, b.count,b.userid,b.createDate \r\n"
			+ "order by b.createDate desc LIMIT #{pageNum},10;"})
	List<Board> getBoardList(@Param("pageNum") int pageNum);
	
	@Select({"SELECT count(*) FROM board"})
	int getTotalBoard();
	
//	@Select({"SELECT * FROM board where id=#{boardid}"})
	@Select({"select b.*,u.username from board b left join user u on b.userid = u.id where b.id=#{boardid}"})
	Board getBoardDetails(@Param("boardid") int boardid);

	@Update({"UPDATE board SET count = count+1 where id = #{boardid}"})
	void updateBoardCount(@Param("boardid")int boardid);
	
	
	@Delete({"UPDATE board SET deletedBoard=1 where id=#{id}"})
	void boardDelete(@Param(value = "id") int boardid);

	@Update({"UPDATE board set title=#{board.title},content=#{board.content} where id=#{board.id}"})
	void boardUpdate(@Param(value ="board") Board board);
}
