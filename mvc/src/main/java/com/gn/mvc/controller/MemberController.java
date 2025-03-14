package com.gn.mvc.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gn.mvc.dto.MemberDto;
import com.gn.mvc.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {

	private final MemberService service;
	
	@GetMapping("/member/create")
	public String createMemberView() {
		return "/member/create";
	}
	
	@PostMapping("/member")
	@ResponseBody // json 형식으로 받을때 필수
	public Map<String,String> createMemberApi(
		MemberDto dto
	){
		Map<String,String> resultMap = new HashMap<String,String>();
		resultMap.put("res_code", "500");
		resultMap.put("res_msg", "회원가입중 오류가 발생하였습니다.");
		
		System.out.println(dto);
		MemberDto md = service.createMember(dto);
		if(md.getMember_no() != null) {
			resultMap.put("res_code", "200");
			resultMap.put("res_msg", "회원가입 성공하였습니다.");
		}
		
		return resultMap;
	}
	
}
