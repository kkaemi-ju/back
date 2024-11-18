package com.ssafy.trip.mypage.model;


public class MypageDto {
	private String userName;
	private String userId;
	private String userPwd;
	private String userMail;
	private String sido;
	private String gugun;

	public MypageDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MypageDto(String userName, String userId, String userPwd, String userMail, String sido, String gugun) {
		super();
		this.userName = userName;
		this.userId = userId;
		this.userPwd = userPwd;
		this.userMail = userMail;
		this.sido = sido;
		this.gugun = gugun;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getUserMail() {
		return userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	public String getSido() {
		return sido;
	}

	public void setSido(String sido) {
		this.sido = sido;
	}

	public String getGugun() {
		return gugun;
	}

	public void setGugun(String gugun) {
		this.gugun = gugun;
	}

	@Override
	public String toString() {
		return "MypageDto [userName=" + userName + ", userId=" + userId + ", userPwd=" + userPwd + ", userMail="
				+ userMail + ", sido=" + sido + ", gugun=" + gugun + "]";
	}

}
