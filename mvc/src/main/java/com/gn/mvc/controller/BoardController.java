package com.gn.mvc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gn.mvc.dto.BoardDto;
import com.gn.mvc.dto.SearchDto;
import com.gn.mvc.entity.Board;
import com.gn.mvc.service.BoardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BoardController {
	
	private Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	// 1. 필드 주입 -> 순환 참조
//	@Autowired
//	BoardService service;
	
	// 2. 메소드(Setter) 주입
//	private BoardService service;
//	
//	@Autowired
//	public void setBoardService(BoardService service) {
//		this.service = service;
//	}
	
	// 3. 생성자 주입 + final
	private final BoardService service;
	
//	@Autowired
//	public BoardController(BoardService service) {
//		this.service = service;
//	}
	
	
	@GetMapping("/board/create")
	
	@PostMapping("/board")
	@ResponseBody
	public Map<String,String> createBoardApi(
//		@RequestParam("board_title") ,String boardTitle,
//		@RequestParam("board_content") String boardContent);
//		@RequestParam Map<String,String> parma	
		BoardDto dto
	){ 
		Map<String,String> resultMap = new HashMap<String,String>();
		resultMap.put("res_code", "500");
		resultMap.put("res_msg", "게시글 등록중 오류가 발생하였습니다");
		                                                               
		System.out.println(dto);
		// Service가 가지고 있는 createBoard 메소드 호출
		BoardDto result = service.createBoard(dto);
		
		// 스프링에서 출력문? 위에 로깅 설정해야함
		// application.properties 에서 logging.level.root=debug 레벨설정
		// logback-spring.xml 에서도 설정가능함 둘중 하나만 설정해야함
		logger.debug("1 : "+result.toString());
		logger.info("2 : "+result.toString());
		logger.warn("3 : "+result.toString());
		logger.error("4 : "+result.toString());
		
		return resultMap;
	}
	
	@GetMapping("/board")
	public String selectBoardAll(Model model, SearchDto searchdto) {
		// 1. DB에서 목록 SELECT
		List<Board> resultList = service.selectBoardAll(searchdto);	
		// 2. 목륵을 Model에 등록
		model.addAttribute("boardList",resultList);
		// 3. list.html에 데이터 셋팅
		return "board/list";
	}
	
	
	
}
