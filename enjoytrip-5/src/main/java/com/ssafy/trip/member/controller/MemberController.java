package com.ssafy.trip.member.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.trip.member.model.MemberDto;
import com.ssafy.trip.member.model.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/user")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @Operation(summary="사용자 id 확인", description="사용자 아이디가 db에 존재하는지 확인")
    @GetMapping("/{userid}")
    public ResponseEntity<Map<String, Object>> idCheck(@PathVariable("userid") String userId) {
        log.debug("idCheck userid : {}", userId);
        try {
            int cnt = memberService.idCheck(userId);
            return ResponseEntity.ok(Map.of("checkid", userId, "cnt", cnt));
        } catch (Exception e) {
            log.error("Error during ID check", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to check user ID"));
        }
    }

    @Operation(summary="회원가입 ", description="사용자 회원가입 ")
    @PostMapping("/join")
    public ResponseEntity<Map<String, String>> join(@RequestBody MemberDto memberDto) {
        log.debug("memberDto info: {}", memberDto);
        String fullEmail = memberDto.getUserEmail();
        memberDto.setUserEmail(fullEmail);
        try {
            memberService.joinMember(memberDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("message", "회원가입 성공"));
        } catch (Exception e) {
            log.error("Error during member registration", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "회원가입 중 문제 발생"));
        }
    }
    @Operation(summary="로그인 ", description="id, 비밀번호 입력 ")
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(
            @RequestBody @Parameter(description = "로그인 시 필요한 회원정보(아이디, 비밀번호).", required = true) MemberDto memberDto) {
        log.debug("login user : {}", memberDto);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.ACCEPTED;
        try {
            MemberDto loginUser = memberService.loginMember(memberDto);
            if(loginUser != null) {
//                String accessToken = jwtUtil.createAccessToken(loginUser.getUserId());
//                String refreshToken = jwtUtil.createRefreshToken(loginUser.getUserId());
//                log.debug("access token : {}", accessToken);
//                log.debug("refresh token : {}", refreshToken);
                
//                발급받은 refresh token 을 DB에 저장.
//                memberService.saveRefreshToken(loginUser.getUserId(), refreshToken);
                
//                JSON 으로 token 전달.
//                resultMap.put("access-token", accessToken);
//                resultMap.put("refresh-token", refreshToken);
//                
                status = HttpStatus.CREATED;
            } else {
                resultMap.put("message", "아이디 또는 패스워드를 확인해 주세요.");
                status = HttpStatus.UNAUTHORIZED;
            } 
            
        } catch (Exception e) {
            log.debug("로그인 에러 발생 : {}", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }



    @Operation(summary="로그아웃 ", description="로그아웃 버튼 클릭 시 로그아웃 처")
    @GetMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(Map.of("message", "로그아웃 성공"));
    }
}
