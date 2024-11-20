package com.ssafy.trip.board.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Schema(title = "BoardDto (게시판 정보)", description = "게시 정보 ")
public class BoardDto {
	@Schema(description = "게시글 아이디")
	private int boardId;
	@Schema(description = "게시글 종류 아이디 ")
	private String boardTypeId;
	@Schema(description = "게시글 내용")
	private String content;
	@Schema(description = "조회수")
	private int view;
	@Schema(description = "작성일")
	private String createdAt;
	@Schema(description ="사용자아이디")
	private String userId;
	@Schema(description = "관광지 링크 ")
	private String attractionUrl;
	@Schema(description = "관광지 이름")
	private String attractionName;
	@Schema(description = "게시글 제목")
	private String title;

}
