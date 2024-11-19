package com.ssafy.trip.member.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Schema(title = "MemberDto (회원정보)", description = "회원의 아이디, 비번, 이름을 가진 Domain Class")
public class MemberDto {
	@Schema(description = "회원아이디", requiredMode = Schema.RequiredMode.REQUIRED, example = "ssafy")
	private String userId;
	@Schema(description = "회원비밀번호")
	private String userPwd;
	@Schema(description = "회원이름", example = "하이쌤")
	private String userName;
	@Schema(description = "이메일")
	private String userEmail;
	@Schema(description = "탈퇴유무")
	private int isWithdraw;
	@Schema(description = "가입일", defaultValue = "현재시간")
	private String createdAt;
	@Schema(description = "권한 ")
	private int role;


}
