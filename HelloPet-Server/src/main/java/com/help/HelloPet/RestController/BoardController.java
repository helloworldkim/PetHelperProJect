package com.help.HelloPet.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.help.HelloPet.config.auth.PrincipalDetails;
import com.help.HelloPet.mapper.BoardMapper;
import com.help.HelloPet.model.Board;
import com.help.HelloPet.repository.BoardRepository;


@RestController
public class BoardController {

	@Autowired
	BoardRepository boardRepository;
	
	@Autowired
	BoardMapper boardMapper;
	
	//글 작성때 사용되는 메소드
	@PostMapping("/board/write")
	public String board_write(@RequestBody Board board,Authentication authentication) {
		PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
		System.out.println(board);
		System.out.println(principalDetails.getUser());
		//글 작성한 유저정보 session에 위치한것 받아서 저장
		int userid = principalDetails.getUser().getId();
		board.setUserid(userid);
		
		//글쓰기 저장
		boardRepository.save(board);
		
		return "글쓰기접근";
	}
	
	//게시판의 글 리스트를 반환하는 메소드
	@GetMapping("/board/list")
	public Map<String,Object> board_list(@RequestParam(value = "pageNum",defaultValue ="1") int pageNum) {
		System.out.println("board_list 호출");
		System.out.println(pageNum);
		List<Board> boardList = new ArrayList<>();
//		(페이지번호 - 1) * 10
		int offset = (pageNum-1) *10;
		boardList = boardMapper.getBoardList(offset);
		int boardCount = boardMapper.getTotalBoard();
		Map<String,Object> map = new HashMap<>();
		map.put("boardList", boardList);
		map.put("boardCount", boardCount);
		
		return map;
	}
}
