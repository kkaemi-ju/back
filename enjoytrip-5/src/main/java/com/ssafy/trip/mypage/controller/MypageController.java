package com.ssafy.trip.mypage.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.trip.member.model.MemberDto;
import com.ssafy.trip.member.model.service.MemberService;
import com.ssafy.trip.mypage.model.MypageDto;
import com.ssafy.trip.mypage.model.service.MypageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/mypage")
public class MypageController {
    private final MypageService mypageService;
    private final MemberService memberService;
    public MypageController(MemberService memberService, MypageService mypageService) {
    	this.memberService = memberService;
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
    public ResponseEntity<?> update(@RequestBody MemberDto memberDto) {
        try {
            mypageService.modifyMypage(memberDto);
           System.out.println(memberDto);
            MemberDto members = memberService.userInfo(memberDto.getUserId());
            return ResponseEntity
                    .status(HttpStatus.OK) // HTTP 상태 코드 설정
                    .body(Map.of(
                            "message", "정보가 성공적으로 업데이트되었습니다.",
                            "userInfo", members
                        ));
        } catch (Exception e) {
            log.error("Error updating mypage info", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) // 에러 상태 코드
                    .body(Map.of("error", "정보를 업데이트하는 중 문제가 발생했습니다."));
        }
    }

    @PostMapping("/modifypwd")
    public ResponseEntity<?> modifyPwd(@RequestBody MemberDto memberDto) {
        try {
        	System.out.println(memberDto);
            mypageService.modifyPwd(memberDto);
            return ResponseEntity.ok(Map.of("message", "비밀번호가 성공적으로 변경되었습니다."));
        } catch (Exception e) {
            log.error("Error modifying password", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "비밀번호를 변경하는 중 문제가 발생했습니다."));
        }
    }

    @Operation(summary="비번체크 ", description="id, 비밀번호 입력 ")
    @PostMapping("/verify-password")
    public ResponseEntity<Map<String, Object>> login(
            @RequestBody @Parameter(description = "비밀번호 체 시 필요한 회원정보(아이디, 비밀번호).", required = true) MemberDto memberDto) {
        log.debug("login user : {}", memberDto);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.ACCEPTED;
        try {
            MemberDto loginUser = memberService.loginMember(memberDto);
            if(loginUser != null) {
    
                status = HttpStatus.CREATED;
            } else {
                resultMap.put("message", "비밀번호 불일치");
                status = HttpStatus.UNAUTHORIZED;
            } 
            
        } catch (Exception e) {
            log.debug("에러 발생 : {}", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    
    
    @PostMapping("/delete")
    public ResponseEntity<Map<String, String>> delete(@RequestBody MemberDto memberDto) {
        try {
            memberService.deleRefreshToken(memberDto.getUserId());
            mypageService.deleteUser(memberDto.getUserId());
            return ResponseEntity
                    .status(HttpStatus.OK) // HTTP 상태 코드 설정
                    .body(Map.of("message", "계정이 성공적으로 삭제되었습니다."));
        } catch (Exception e) {
            log.error("Error deleting user", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR) // 에러 상태 코드
                    .body(Map.of("error", "계정을 삭제하는 중 문제가 발생했습니다."));
        }
    }

}
