package com.ssafy.trip.board.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.trip.board.model.BoardDto;
import com.ssafy.trip.board.model.CommentDto;
import com.ssafy.trip.board.model.FileDto;
import com.ssafy.trip.util.PageNavigation;

public interface BoardService {

	int writeArticle(BoardDto boardDto) throws Exception;
	void uploadFile(List<FileDto> fileDtos) throws Exception;
	List<BoardDto> listArticle(Map<String, String> map) throws Exception;
	PageNavigation makePageNavigation(Map<String, String> map) throws Exception;
	BoardDto getArticle(int articleNo) throws Exception;
	void updateHit(int articleNo) throws Exception;	
	List<String> saveFiles(List<FileDto> fileDtos) throws Exception;
	void modifyArticle(BoardDto boardDto) throws Exception;
	void deleteArticle(int articleNo) throws Exception;
	void saveFilestoDatabase(List<FileDto> fileDtos) throws Exception;
	List<String> getfiles(int articleno) throws Exception;
	List<CommentDto> getComments(int articleno) throws Exception;
	void writeComment(CommentDto commentDto) throws Exception;
	void deleteComment(int commentId) throws Exception;
}
