package com.help.HelloPet.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.help.HelloPet.config.auth.PrincipalDetails;
import com.help.HelloPet.mapper.ReplyMapper;
import com.help.HelloPet.model.Board;
import com.help.HelloPet.model.Reply;
import com.help.HelloPet.repository.ReplyRepository;

@RestController
public class ReplyController {

	@Autowired
	ReplyMapper replyMapper;
	
	@Autowired
	ReplyRepository replyRepository;
	//해당글을 삭제하는 메서드 deletedReply 를 1로 만든다
	@DeleteMapping("/reply/delete")
	public void ReplyDelete(@RequestBody Reply reply) {
			System.out.println("ReplyDelete 메서드 호출");
			System.out.println("전달받은 reply값:"+reply);
			int replyid = reply.getId();
			
			//삭제수행
			replyMapper.ReplyDelete(replyid);

	}
	@PutMapping("/reply/update")
	public void ReplyUpdate(@RequestBody Reply reply) {
			System.out.println("ReplyUpdate 메서드 호출");
			System.out.println("전달받은 reply값:"+reply);
			
			//삭제수행
			replyMapper.ReplyUpdate(reply);

	}
	
	//해당 글의 댓글들을 특정 갯수만큼 반환하는 메서드
	@GetMapping("/reply")
	public Map<String,Object> getReplyList(@RequestParam(value = "boardid") int boardid,
			@RequestParam(value = "pageNum",defaultValue = "1") int pageNum){
		System.out.println("getReplyList 메소드 호출");
		System.out.println(boardid);
		//기본값 1
		System.out.println("replyPageNum값 :"+pageNum);
		pageNum = pageNum*5;
		List<Reply> list = new ArrayList<>();
		list = replyMapper.getReplyList(boardid,pageNum);
		int replyCount =  replyMapper.getReplyCount(boardid);
		System.out.println(list);
		Map<String,Object> map = new HashMap<>();
		map.put("replyList", list);
		map.put("replyCount", replyCount);
		
		return map;
	}
	
	//글 작성때 사용되는 메소드
		@PostMapping("/reply/write")
		public String reply_write(@RequestBody Reply reply,Authentication authentication) {
			PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
			System.out.println("전달받은 reply값:"+reply);
			System.out.println(principalDetails.getUser());
			//글 작성한 유저정보 session에 위치한것 받아서 저장
			int userid = principalDetails.getUser().getId();
			reply.setUserid(userid);
			System.out.println(reply);
			
			//댓글 저장
			replyRepository.save(reply);
//			replyMapper.ReplyWrite(reply);	
			
			return "댓글접근";
		}
}
