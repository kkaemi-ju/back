package com.ssafy.trip.member.model.service;

import java.util.Map;

import com.ssafy.trip.member.model.MemberDto;

public interface MemberService {

	void joinMember(MemberDto memberDto) throws Exception;

	int idCheck(String userId) throws Exception;
	public MemberDto loginMember(Map<String, String> map) throws Exception;
}
