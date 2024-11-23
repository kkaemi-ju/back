package com.ssafy.trip.board.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.trip.board.model.BoardDto;
import com.ssafy.trip.board.model.CommentDto;
import com.ssafy.trip.board.model.FileDto;
import com.ssafy.trip.board.model.service.BoardService;
import com.ssafy.trip.util.PageNavigation;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.core.io.UrlResource;
import org.springframework.core.io.Resource;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.ByteArrayInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
            System.out.println(boardDto);
            return ResponseEntity.ok(boardDto);
        } catch (Exception e) {
            log.error("Error fetching article", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "게시글 조회 중 오류 발생"));
        }
    }

    
    @GetMapping("/loadfile/{boardId}")
    public ResponseEntity<List<String>> getFileUrls(@PathVariable("boardId") int boardId) {
        try {
            String userName = System.getProperty("user.name");

            // 파일 URL 리스트 가져오기
            List<String> fileUrls = boardService.getfiles(boardId);

            // 파일이 없을 경우 빈 리스트 반환
            if (fileUrls == null || fileUrls.isEmpty()) {
                return ResponseEntity.ok(Collections.emptyList());
            }

            // 실제 파일 경로로 변환 (C:/사용자명/enjoytrip/uploads/boardId/파일명)
            String baseDirectory = "/Users/" + userName + "/enjoytrip/uploads/" + boardId + "/";
            List<String> accessibleUrls = fileUrls.stream()
                    .map(filePath -> baseDirectory + new File(filePath).getName())
                    .collect(Collectors.toList());

            System.out.println(accessibleUrls);
            return ResponseEntity.ok(accessibleUrls);
        } catch (Exception e) {
            log.error("파일 URL 로드 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }
    
    @GetMapping("/file/{boardId}/{fileName}")
    public ResponseEntity<Resource> serveFile(@PathVariable String boardId, @PathVariable String fileName) {
        try {
            String userName = System.getProperty("user.name");
            String baseDirectory = "/Users/" + userName + "/enjoytrip/uploads/" + boardId;

            // 파일 경로 설정
            File file = new File(baseDirectory, fileName);
            if (!file.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            // 파일을 리소스로 변환
            Resource resource = new UrlResource(file.toURI());

            // 헤더 설정
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(file.toPath()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getName() + "\"")
                    .body(resource);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    // 게시글 작성
    @PostMapping
    public ResponseEntity<?> write(@RequestBody BoardDto boardDto) {
        try {
        System.out.println("!1!!!");
            
            System.out.println("게시글 데이터: " + boardDto);
            if (boardDto.getUserId() == null || boardDto.getUserId().isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "로그인이 필요합니다."));
            }

            // 게시글 작성 서비스 호출
            int boardId = boardService.writeArticle(boardDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("message", "게시글 작성 성공", "boardId", boardId));
        } catch (Exception e) {
            log.error("Error creating article", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "게시글 작성 중 오류 발생"));
        }
    }
    
 //파일 업로드 
    @PostMapping("/fileUpload")
    public ResponseEntity<?> uploadFile(@RequestBody List<FileDto> fileDtos) {
        try {
            
            System.out.println("파일  데이터: " + fileDtos);

            boardService.saveFilestoDatabase(fileDtos);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("message", "파일 저장 성공 "));
        } catch (Exception e) {
            log.error("Error uploading files", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "파일 저장 중 오류 발생"));
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
    
 //댓글 조회 
    @GetMapping("/comment/{articleno}")
    public ResponseEntity<?> getComments(@PathVariable("articleno") int articleNo) {
        try {
            // 댓글 목록 조회
            List<CommentDto> comments = boardService.getComments(articleNo);
            System.out.println(comments);
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            log.error("Error fetching comments", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "댓글 조회 중 오류 발생"));
        }
    }
    
    // 댓글 작성 
    @PostMapping("/comment")
    public ResponseEntity<?> writeComment(@RequestBody CommentDto commentDto) {
        try {
            
            System.out.println("게시글 데이터: " + commentDto);
            if (commentDto.getUserId() == null || commentDto.getUserId().isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "로그인이 필요합니다."));
            }

            boardService.writeComment(commentDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("message", "댓글 작성 성공"));
        } catch (Exception e) {
            log.error("Error creating comment", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "댓글 작성 중 오류 발생"));
        }
    }
    
 //댓글 삭제
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable("commentId") int commentId) {
        try {
            boardService.deleteComment(commentId);
            return ResponseEntity.ok(Map.of("message", "댓글 삭제 성공"));
        } catch (Exception e) {
            log.error("Error deleting comment", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "댓글 삭제 중 오류 발생"));
        }
    }
}