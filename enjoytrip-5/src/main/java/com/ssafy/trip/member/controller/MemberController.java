package com.ssafy.trip.member.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssafy.trip.member.model.MemberDto;
import com.ssafy.trip.member.model.service.MemberService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/user")
public class MemberController {
	private final MemberService memberService;

	public MemberController(MemberService memberService) {
		super();
		this.memberService = memberService;
	}

	@GetMapping("/{userid}")
	@ResponseBody
	public Map<String, Object> idCheck(@PathVariable("userid") String userId) throws Exception {
		log.debug("idCheck userid : {}", userId);
		int cnt = memberService.idCheck(userId);
		return Map.of("checkid", userId, "cnt", cnt);
	}

	@PostMapping("/join")
	public String join(MemberDto memberDto, Model model) {
		log.debug("memberDto info: {}", memberDto);
		String fullEmail = memberDto.getUserMail() + "@" + memberDto.getUserDomain();
		memberDto.setUserMail(fullEmail);
		try {
			memberService.joinMember(memberDto);
			return "redirect:/";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("msg", "회원가입 중 문제 발생");
			return "error/error";
		}
	}

	@GetMapping("/login")
	public String aaa() {

		return "index";
	}

	@PostMapping("/login")
	public String login(@RequestParam Map<String, String> map,
			@RequestParam(name = "saveid", required = false) String saveid, Model model, HttpSession session,
			HttpServletResponse response) {
		MemberDto memberDto;
		try {
			memberDto = memberService.loginMember(map);
			
			if (memberDto != null) {
				System.out.println(memberDto);
				if(memberDto.getIsWithdraw()==1) {
					model.addAttribute("msg", "탈퇴한 회원입니다!");
					return "redirect:/";
				}
				session.setAttribute("userinfo", memberDto);

				Cookie cookie = new Cookie("ssafy_id", map.get("userid"));
				cookie.setPath("/board");
				if ("ok".equals(saveid)) {
					cookie.setMaxAge(60 * 60 * 24 * 365 * 40);
				} else {
					cookie.setMaxAge(0);
				}
				response.addCookie(cookie);
				return "redirect:/";
			} else {
				model.addAttribute("msg", "아이디 또는 비밀번호 확인 후 다시 로그인하세요!");
				return "redirect:/";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error/error";
		}

	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
}
