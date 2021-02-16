package com.help.HelloPet.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.help.HelloPet.mapper.ReplyMapper;
import com.help.HelloPet.model.Reply;

@RestController
public class ReplyController {

	@Autowired
	ReplyMapper replyMapper;
	
	@GetMapping("/reply")
	public Map<String,Object> getReplyDetails(@RequestParam(value = "boardid") int boardid){
		System.out.println("getReplyDetails 메소드 호출");
		System.out.println(boardid);
		List<Reply> list = new ArrayList<>();
		list = replyMapper.getReplyList(boardid);
		int replyCount =  replyMapper.getReplyCount(boardid);
		System.out.println(list);
		Map<String,Object> map = new HashMap<>();
		map.put("replyList", list);
		map.put("replyCount", replyCount);
		
		return map;
	}
}
