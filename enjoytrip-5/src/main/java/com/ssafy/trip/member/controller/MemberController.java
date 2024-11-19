package com.ssafy.trip.member.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.trip.member.model.MemberDto;
import com.ssafy.trip.member.model.service.MemberService;

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

    @PostMapping("/join")
    public ResponseEntity<Map<String, String>> join(@RequestBody MemberDto memberDto) {
        log.debug("memberDto info: {}", memberDto);
        String fullEmail = memberDto.getUserMail() + "@" + memberDto.getUserDomain();
        memberDto.setUserMail(fullEmail);
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

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(
            @RequestBody Map<String, String> map,
            @RequestParam(name = "saveid", required = false) String saveid,
            HttpSession session,
            HttpServletResponse response) {
        try {
            MemberDto memberDto = memberService.loginMember(map);
            if (memberDto != null) {
                if (memberDto.getIsWithdraw() == 1) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body(Map.of("error", "탈퇴한 회원입니다"));
                }
                session.setAttribute("userinfo", memberDto);

                Cookie cookie = new Cookie("ssafy_id", map.get("userid"));
                cookie.setPath("/");
                if ("ok".equals(saveid)) {
                    cookie.setMaxAge(60 * 60 * 24 * 365 * 40);
                } else {
                    cookie.setMaxAge(0);
                }
                response.addCookie(cookie);
                return ResponseEntity.ok(Map.of("message", "로그인 성공"));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "아이디 또는 비밀번호 확인 후 다시 로그인하세요"));
            }
        } catch (Exception e) {
            log.error("Error during login", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "로그인 중 문제 발생"));
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(Map.of("message", "로그아웃 성공"));
    }
}
