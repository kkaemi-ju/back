package com.ssafy.trip.board.model;

public class BoardDto {
	private int boardId;
	private String boardSubject;
	private String content;
	private int view;
	private String createdAt;
	private String contentType;
	private String userId;

	public BoardDto(int boardId, String boardSubject, String content, int view, String createdAt, String contentType,
			String userId) {
		super();
		this.boardId = boardId;
		this.boardSubject = boardSubject;
		this.content = content;
		this.view = view;
		this.createdAt = createdAt;
		this.contentType = contentType;
		this.userId = userId;
	}

	public BoardDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public String getBoardSubject() {
		return boardSubject;
	}

	public void setBoardSubject(String boardSubject) {
		this.boardSubject = boardSubject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getView() {
		return view;
	}

	public void setView(int view) {
		this.view = view;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "BoardDto [boardId=" + boardId + ", boardSubject=" + boardSubject + ", content=" + content + ", view="
				+ view + ", createdAt=" + createdAt + ", contentType=" + contentType + ", userId=" + userId + "]";
	}
}
