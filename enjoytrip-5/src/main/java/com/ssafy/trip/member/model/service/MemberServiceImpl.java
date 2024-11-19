package com.ssafy.trip.member.model.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.trip.member.model.MemberDto;
import com.ssafy.trip.member.model.mapper.MemberMapper;
@Service
public class MemberServiceImpl implements MemberService {

	private final MemberMapper memberMapper;

	public MemberServiceImpl(MemberMapper memberMapper) {
		super();
		this.memberMapper = memberMapper;
	}

	@Override
	public void joinMember(MemberDto memberDto) throws Exception {
		memberMapper.joinMember(memberDto);

	}

	@Override
	public int idCheck(String userId) throws Exception {
		return memberMapper.idCheck(userId);
	}
	@Override
	public MemberDto loginMember(MemberDto memberDto) throws Exception {
//		return sqlSession.getMapper(MemberMapper.class).loginMember(map);
		return memberMapper.loginMember(memberDto);
	}
}
