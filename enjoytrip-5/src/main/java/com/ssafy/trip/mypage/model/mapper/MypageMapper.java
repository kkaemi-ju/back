package com.ssafy.trip.mypage.model.mapper;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.member.model.MemberDto;
import com.ssafy.trip.mypage.model.MypageDto;

@Mapper
public interface MypageMapper {
	MypageDto getMypageInfo(String userId) throws SQLException;

	void modifyMypage(MemberDto memberDto);
	void modifyPwd(MemberDto memberDto);
	
	void deleteUser(String userId);
	
}
