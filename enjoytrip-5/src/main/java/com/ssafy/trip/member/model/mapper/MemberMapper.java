package com.ssafy.trip.member.model.mapper;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.member.model.MemberDto;

@Mapper
public interface MemberMapper {

	void joinMember(MemberDto memberDto) throws SQLException;

	int idCheck(String userId) throws SQLException;
	MemberDto loginMember(MemberDto memberDto) throws SQLException;
}
