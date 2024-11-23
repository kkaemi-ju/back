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
@Schema(title = "CommentDto (댓글 정보)", description = "댓글 정보 ")
public class CommentDto {
	@Schema(description = "댓글아이디 ")
private int commentId;
	@Schema(description = "사용자아이디 ")
	private String userId;
	@Schema(description = "내용 ")
	private String content;
	@Schema(description = "게시판아이디 ")
	private int boardId;
	@Schema(description = "작성일자 ")
	private String createdAt;
}
