package com.ssafy.trip.member.model.service;

import java.util.Map;

import com.ssafy.trip.member.model.MemberDto;

public interface MemberService {

	void joinMember(MemberDto memberDto) throws Exception;

	int idCheck(String userId) throws Exception;
	public MemberDto loginMember(MemberDto memberDto) throws Exception;
	MemberDto userInfo(String userId) throws Exception ;
	void saveRefreshToken(String userId, String refreshToken) throws Exception;
    Object getRefreshToken(String userId) throws Exception;
    void deleRefreshToken(String userId) throws Exception;
}
