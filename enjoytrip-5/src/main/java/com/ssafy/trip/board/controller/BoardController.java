package com.ssafy.trip.board.controller;

import com.ssafy.trip.board.model.BoardDto;
import com.ssafy.trip.board.model.service.BoardService;
import com.ssafy.trip.util.PageNavigation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    // 게시글 목록 조회
    @GetMapping("load/{boardType}")
    public ResponseEntity<Map<String, Object>> list(
            @PathVariable("boardType") String boardType,
            @RequestParam Map<String, String> params) {
        try {

            System.out.println("boardType: " + boardType); // 디버깅
           System.out.println("params " + params);
           params.put("boardTypeId", boardType);
            
            // 서비스 호출
            List<BoardDto> list = boardService.listArticle(params);
            PageNavigation pageNavigation = boardService.makePageNavigation(params);

            System.out.print("list" + list);
            return ResponseEntity.ok(Map.of(
                "articles", list,
                "navigation", pageNavigation,
                "pgno", params.get("pgno"),
                "key", params.get("key"),
                "word", params.get("word")
            ));
        } catch (Exception e) {
            log.error("Error fetching articles", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "게시글 목록 조회 중 오류 발생"));
        }
    }

    // 게시글 상세 조회
    @GetMapping("/{articleno}")
    public ResponseEntity<?> view(@PathVariable("articleno") int articleNo) {
        try {
            boardService.updateHit(articleNo);
            BoardDto boardDto = boardService.getArticle(articleNo);
            return ResponseEntity.ok(boardDto);
        } catch (Exception e) {
            log.error("Error fetching article", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "게시글 조회 중 오류 발생"));
        }
    }

    // 게시글 작성
    @PostMapping
    public ResponseEntity<?> write(
            @SessionAttribute(name = "userinfo", required = false) String userId,
            @RequestBody BoardDto boardDto) {
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "로그인이 필요합니다."));
        }
        try {
            boardDto.setUserId(userId);
            boardService.writeArticle(boardDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "게시글 작성 성공"));
        } catch (Exception e) {
            log.error("Error creating article", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "게시글 작성 중 오류 발생"));
        }
    }

    // 게시글 수정
    @PutMapping("/{articleno}")
    public ResponseEntity<?> modify(
            @PathVariable("articleno") int articleNo,
            @RequestBody BoardDto boardDto) {
        try {
            boardDto.setBoardId(articleNo);
            boardService.modifyArticle(boardDto);
            return ResponseEntity.ok(Map.of("message", "게시글 수정 성공"));
        } catch (Exception e) {
            log.error("Error updating article", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "게시글 수정 중 오류 발생"));
        }
    }

    // 게시글 삭제
    @DeleteMapping("/{articleno}")
    public ResponseEntity<?> delete(@PathVariable("articleno") int articleNo) {
        try {
            boardService.deleteArticle(articleNo);
            return ResponseEntity.ok(Map.of("message", "게시글 삭제 성공"));
        } catch (Exception e) {
            log.error("Error deleting article", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "게시글 삭제 중 오류 발생"));
        }
    }
}