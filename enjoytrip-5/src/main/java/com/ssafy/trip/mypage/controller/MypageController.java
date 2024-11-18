package com.ssafy.trip.mypage.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssafy.trip.member.model.MemberDto;
import com.ssafy.trip.mypage.model.service.MypageService;
import com.ssafy.trip.mypage.model.MypageDto;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/mypage")
public class MypageController {
	private final MypageService mypageService;

	public MypageController(MypageService mypageService) {
		super();
		this.mypageService = mypageService;
	}
	
	@GetMapping("/")
	public String mypage(HttpSession session, Model model) {
	    MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
	    if (memberDto != null) {
	        try {
	            MypageDto mypageDto = mypageService.getMypageInfo(memberDto.getUserId());
	            model.addAttribute("mypageinfo", mypageDto);  // "mypageinfo"로 mypageDto 추가
	            return "/user/mypage";
	        } catch (Exception e) {
	            e.printStackTrace();
	            return "/error/error";
	        }
	    } else {
	        return "redirect:/";
	    }
	}
	
	
	@PostMapping("/update")
	public String update(@RequestParam Map<String, String> map, String saveid, Model model, HttpSession session,
			HttpServletResponse response) {
	    try {
	    	mypageService.modifyMypage(map);
	    	return "redirect:/mypage/";
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	return "/error/error";
	    }
	}
	
	
	@PostMapping("/modifypwd")
	public String modifypwd(@RequestParam Map<String, String> map, String saveid, Model model, 
			HttpServletResponse response) {
	    try {
	    	mypageService.modifyPwd(map);
	    	return "redirect:/mypage/";
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	return "/error/error";
	    }
	}
	
	@PostMapping("/delete")
	public String delete(@RequestParam Map<String, String> map, String saveid, Model model, 
			HttpServletResponse response) {
	    try {
	    	mypageService.deleteUser(map);
	    	return "redirect:/user/logout";
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	return "/error/error";
	    }
	}
}
