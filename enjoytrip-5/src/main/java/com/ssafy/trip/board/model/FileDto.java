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
@Schema(title = "FileDto (파일 정보)", description = "파일 정 ")

public class FileDto {
	@Schema(description = "파일아이디 ")
	private int fileId;
	
	@Schema(description = "파일경로 " )
	private String fileUrl;
	
	@Schema(description = "게시글아이디 ")
	private int boardId;
}
