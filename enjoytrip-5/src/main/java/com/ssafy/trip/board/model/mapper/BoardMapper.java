package com.ssafy.trip.board.model.mapper;

import com.ssafy.trip.board.model.BoardDto;
import com.ssafy.trip.board.model.CommentDto;
import com.ssafy.trip.board.model.FileDto;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface BoardMapper {
    int writeArticle(BoardDto boardDto);
    void insertBoardFiles(List<FileDto> fileDtos);
    List<BoardDto> listArticle(Map<String, Object> param);
    int getTotalArticleCount(Map<String, Object> param);
    BoardDto getArticle(int articleNo);
    void updateHit(int articleNo);
    void modifyArticle(BoardDto boardDto);
    void deleteArticle(int articleNo);
    void uploadFile(List<FileDto> fileDtos);
    List<String> getFiles(int articleno);
    void deleteFile(int articleno);
    List<CommentDto> getComments(int articleno);
    void writeComment(CommentDto commentDto);
    void deleteComment(int commentId);
}