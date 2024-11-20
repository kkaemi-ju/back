package com.ssafy.trip.mypage.model.service;

import java.util.Map;

import com.ssafy.trip.member.model.MemberDto;
import com.ssafy.trip.mypage.model.MypageDto;

public interface MypageService {
	MypageDto getMypageInfo(String userId) throws Exception;

	void modifyMypage(MemberDto memberDto);
	void modifyPwd(MemberDto memberDto);
	void deleteUser(String userId);
}
