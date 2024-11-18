package com.ssafy.trip.member.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(title = "MemberDto (회원정보)", description = "회원의 아이디, 비번, 이름을 가진 Domain Class")
public class MemberDto {
	@Schema(description = "회원아이디", requiredMode = Schema.RequiredMode.REQUIRED, example = "ssafy")
	private String userId;
	@Schema(description = "회원비밀번호")
	private String userPwd;
	@Schema(description = "회원이름", example = "하이쌤")
	private String userName;
	@Schema(description = "이메일")
	private String userMail;
	@Schema(description = "도메인")
	private String userDomain;
	@Schema(description = "회원나이")
	private int userAge;
	@Schema(description = "탈퇴유무")
	private int isWithdraw;
	@Schema(description = "성별")
	private String gender;
	@Schema(description = "가입일", defaultValue = "현재시간")
	private String createdAt;
	@Schema(description = "구군넘버")
	private int gugunsNo;
	@Schema(description = "시넘버")
	private int sidosNo;

	public MemberDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserMail() {
		return userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	public int getUserAge() {
		return userAge;
	}

	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}

	public int getIsWithdraw() {
		return isWithdraw;
	}

	public void setIsWithdraw(int isWithdraw) {
		this.isWithdraw = isWithdraw;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public int getGugunsNo() {
		return gugunsNo;
	}

	public void setGugunsNo(int gugunsNo) {
		this.gugunsNo = gugunsNo;
	}

	public int getSidosNo() {
		return sidosNo;
	}

	public void setSidosNo(int sidosNo) {
		this.sidosNo = sidosNo;
	}

	public String getUserDomain() {
		return userDomain;
	}

	public void setUserDomain(String userDomain) {
		this.userDomain = userDomain;
	}

	@Override
	public String toString() {
		return "MemberDto [userId=" + userId + ", userPwd=" + userPwd + ", userName=" + userName + ", userMail="
				+ userMail + ", userAge=" + userAge + ", isWithdraw=" + isWithdraw + ", gender=" + gender
				+ ", createdAt=" + createdAt + ", gugunsNo=" + gugunsNo + ", sidosNo=" + sidosNo + "]";
	}

}
