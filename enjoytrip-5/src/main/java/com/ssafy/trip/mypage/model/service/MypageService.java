package com.ssafy.trip.mypage.model.service;

import java.util.Map;

import com.ssafy.trip.mypage.model.MypageDto;

public interface MypageService {
	MypageDto getMypageInfo(String userId) throws Exception;

	void modifyMypage(Map<String, String> map);
	void modifyPwd(Map<String, String> map);
	void deleteUser(Map<String, String> map);
}
