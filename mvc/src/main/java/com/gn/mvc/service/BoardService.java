package com.gn.mvc.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.gn.mvc.dto.BoardDto;
import com.gn.mvc.dto.PageDto;
import com.gn.mvc.dto.SearchDto;
import com.gn.mvc.entity.Board;
import com.gn.mvc.repository.BoardRepository;
import com.gn.mvc.specification.BoardSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
	
//	@Autowired
//	BoardRepository repository;
	
	private final BoardRepository repository;
	
	public BoardDto createBoard(BoardDto dto) {
		// 1. 매개변수 dto -> entity
//		Board parm = Board.builder()
//				.boardTitle(dto.getBoard_title())
//				.boardContent(dto.getBoard_content())
//				.build();
		Board param = dto.toEntity();
		// 2. Repository의 save() 메소드 호출
		Board result = repository.save(param);
		// 3. 결과 entity -> dto
		return new BoardDto().toDto(result);
		
	}

	public Page<Board> selectBoardAll(SearchDto searchDto ,PageDto pageDto) {
//		List<Board> list = new ArrayList<Board>();
//		if(searchDto.getSearch_type() == 1) {
//			// 제목 기준으로 검색
//			list = repository.findByBoardTitleContaining(searchDto.getSearch_text());
//		}else if(searchDto.getSearch_type() == 2) {
//			// 내용 기준으로 검색
//			list = repository.findByBoardContentContaining(searchDto.getSearch_text());
//		}else if(searchDto.getSearch_type() == 3) {
//			// 제목 또는 내용 기준으로 검색
//			list = repository.findByBoardTitleContainingOrBoardContentContaining(searchDto.getSearch_text(),searchDto.getSearch_text());
//		}else {
//			// WHERE절 없이 검색
//			list = repository.findAll();
//		}
		
//		Sort sort = Sort.by("regDate").descending();
//		if(searchDto.getOrder_type() == 2) {
//			sort = Sort.by("regDate").ascending();
//		}
		// 시작페이지 몇번 , 한페이지 몇개씩 보여줄건지 , 
		Pageable pageable = PageRequest.of(pageDto.getNowPage()-1, pageDto.getNumPerPage(), Sort.by("regDate").descending());
		if(searchDto.getOrder_type() == 2) {
			pageable = PageRequest.of(pageDto.getNowPage()-1, pageDto.getNumPerPage(), Sort.by("regDate").ascending());
		}
		
		Specification<Board> spec = (root,query,criteriaBuilder) -> null;
		if(searchDto.getSearch_type() == 1) {
			spec = spec.and(BoardSpecification.boardTitleContains(searchDto.getSearch_text()));
		}else if(searchDto.getSearch_type() == 2) {
			spec = spec.and(BoardSpecification.boardContentContains(searchDto.getSearch_text()));
		}else if(searchDto.getSearch_type() == 3) {
			spec = spec.and(BoardSpecification.boardContentContains(searchDto.getSearch_text()))
					.or(BoardSpecification.boardTitleContains(searchDto.getSearch_text()));
		}
		Page<Board> list = repository.findAll(spec,pageable);
		return list;
	}

	public Board selectBoardOne(Long id) {
		return repository.findById(id).orElse(null);
	}

	public BoardDto updateBoard(BoardDto dto) {
		// 내풀이
		Board param = dto.toEntity();
		Board result = repository.save(param);
		return new BoardDto().toDto(result);
		
		// 강사님 풀이
//		Board result = null;
//		// 1. @Id를 쓴 필드를 기준으로 타겟 조회
//		Board target = repository.findById(dto.getBoard_no()).orElse(null);
//		// 2. 타겟이 존재하는 경우 업데이트
//		if(target != null) {
//			result = repository.save(dto.toEntity());
//		}
//		return result;
	}
	public int deleteBoard(Long id) {
		int result = 0;
		try {
			Board target = repository.findById(id).orElse(null);
			if(target != null) {
				repository.deleteById(id);
			}
			result = 1;
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
}
