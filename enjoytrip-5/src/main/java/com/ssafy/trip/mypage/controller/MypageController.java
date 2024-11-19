package com.ssafy.trip.mypage.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.trip.member.model.MemberDto;
import com.ssafy.trip.mypage.model.MypageDto;
import com.ssafy.trip.mypage.model.service.MypageService;

import jakarta.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/mypage")
public class MypageController {
    private final MypageService mypageService;

    public MypageController(MypageService mypageService) {
        this.mypageService = mypageService;
    }

    @GetMapping("/")
    public ResponseEntity<?> mypage(HttpSession session) {
        MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
        if (memberDto != null) {
            try {
                MypageDto mypageDto = mypageService.getMypageInfo(memberDto.getUserId());
                return ResponseEntity.ok(mypageDto);
            } catch (Exception e) {
                log.error("Error fetching mypage info", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("error", "마이페이지 정보를 가져오는 중 문제가 발생했습니다."));
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "로그인이 필요합니다."));
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody Map<String, String> map) {
        try {
            mypageService.modifyMypage(map);
            return ResponseEntity.ok(Map.of("message", "정보가 성공적으로 업데이트되었습니다."));
        } catch (Exception e) {
            log.error("Error updating mypage info", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "정보를 업데이트하는 중 문제가 발생했습니다."));
        }
    }

    @PostMapping("/modifypwd")
    public ResponseEntity<?> modifyPwd(@RequestBody Map<String, String> map) {
        try {
            mypageService.modifyPwd(map);
            return ResponseEntity.ok(Map.of("message", "비밀번호가 성공적으로 변경되었습니다."));
        } catch (Exception e) {
            log.error("Error modifying password", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "비밀번호를 변경하는 중 문제가 발생했습니다."));
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody Map<String, String> map, HttpSession session) {
        try {
            mypageService.deleteUser(map);
            session.invalidate();
            return ResponseEntity.ok(Map.of("message", "계정이 성공적으로 삭제되었습니다."));
        } catch (Exception e) {
            log.error("Error deleting user", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "계정을 삭제하는 중 문제가 발생했습니다."));
        }
    }
}
