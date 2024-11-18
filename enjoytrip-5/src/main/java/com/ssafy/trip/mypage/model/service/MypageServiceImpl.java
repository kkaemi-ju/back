package com.ssafy.trip.mypage.model.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.trip.member.model.mapper.MemberMapper;
import com.ssafy.trip.mypage.model.MypageDto;
import com.ssafy.trip.mypage.model.mapper.MypageMapper;

@Service
public class MypageServiceImpl implements MypageService {
	private final MypageMapper mypageMapper;
	
	
	public MypageServiceImpl(MypageMapper mypageMapper) {
		super();
		this.mypageMapper = mypageMapper;
	}


	@Override
	public MypageDto getMypageInfo(String userId) throws Exception {
		return mypageMapper.getMypageInfo(userId);
	}


	@Override
	public void modifyMypage(Map<String, String> map) {
		mypageMapper.modifyMypage(map);
		
	}
	@Override
	public void modifyPwd(Map<String, String> map) {
		mypageMapper.modifyPwd(map);
		
	}
	
	@Override
	public void deleteUser(Map<String, String> map) {
		mypageMapper.deleteUser(map);
		
	}

}
