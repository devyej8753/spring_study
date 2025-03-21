package com.gn.mvc.specification;

import org.springframework.data.jpa.domain.Specification;

import com.gn.mvc.entity.ChatRoom;
import com.gn.mvc.entity.Member;

import jakarta.persistence.criteria.CriteriaBuilder;

public class ChatRoomSpecification {
	
	// WHERE from_member = (SELECT * FROM member WHERE = 회원번호) 와 비슷함
	public static Specification<ChatRoom> fromMemberEquals(Member member){
		return (root,query, CriteriaBuilder) ->
			CriteriaBuilder.equal(root.get("fromMember"),member);
	}
	public static Specification<ChatRoom> toMemberEquals(Member member){
		return (root,query, CriteriaBuilder) ->
			CriteriaBuilder.equal(root.get("toMember"),member);
	}
	
}
