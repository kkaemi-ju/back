package com.ssafy.trip.board.model.mapper;

import com.ssafy.trip.board.model.BoardDto;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface BoardMapper {
    void writeArticle(BoardDto boardDto);
    List<BoardDto> listArticle(Map<String, Object> param);
    int getTotalArticleCount(Map<String, Object> param);
    BoardDto getArticle(int articleNo);
    void updateHit(int articleNo);
    void modifyArticle(BoardDto boardDto);
    void deleteArticle(int articleNo);
}