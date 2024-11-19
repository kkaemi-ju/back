package com.ssafy.trip.board.controller;

import java.io.File;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ssafy.trip.board.model.BoardDto;
import com.ssafy.trip.board.model.service.BoardService;
import com.ssafy.trip.member.model.MemberDto;
import com.ssafy.trip.util.PageNavigation;
import com.ssafy.trip.util.ParameterCheck;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/article")
public class BoardController {

    @Autowired
    private BoardService boardService;

    
    public BoardController(BoardService boardService) {
		super();
		this.boardService = boardService;
	}

	@GetMapping("/list")
    public ModelAndView list(@RequestParam Map<String, String> map) throws Exception {
		ModelAndView mav = new ModelAndView();
		List<BoardDto> list = boardService.listArticle(map);
		PageNavigation pageNavigation = boardService.makePageNavigation(map);
		mav.addObject("articles", list);
		mav.addObject("navigation", pageNavigation);
		mav.addObject("pgno", map.get("pgno"));
		mav.addObject("key", map.get("key"));
		mav.addObject("word", map.get("word"));
		mav.setViewName("board/list");
		return mav;
    }

	@GetMapping("/view")
	public String view(@RequestParam("articleno") int articleNo, @RequestParam Map<String, String> map, Model model)
			throws Exception {
		boardService.updateHit(articleNo);
		BoardDto boardDto = boardService.getArticle(articleNo);
	
		System.out.println(boardDto);
		model.addAttribute("article", boardDto);
		model.addAttribute("pgno", map.get("pgno"));
		model.addAttribute("key", map.get("key"));
		model.addAttribute("word", map.get("word"));
		return "board/view";
	}

	@GetMapping("/write")
	public String write(@RequestParam Map<String, String> map, Model model) {
		model.addAttribute("pgno", map.get("pgno"));
		model.addAttribute("key", map.get("key"));
		model.addAttribute("word", map.get("word"));
		return "board/write";
	}


	@PostMapping("/write")
    public ModelAndView write(
            @SessionAttribute(name = "userinfo", required = false) MemberDto memberDto,
            @RequestParam("subject") String subject,
            @RequestParam("content") String content,
            Model model) {
        if (memberDto != null) {
            BoardDto boardDto = new BoardDto();
            boardDto.setUserId(memberDto.getUserId());
            boardDto.setBoardSubject(subject);
            boardDto.setContent(content);

            try {
                boardService.writeArticle(boardDto);
                return new ModelAndView("redirect:/article/list?pgno=1&key=&word=");
            } catch (Exception e) {
                e.printStackTrace();
                model.addAttribute("msg", "글작성 중 문제 발생!!!");
                return new ModelAndView("error/error");
            }
        } else {
            return new ModelAndView("redirect:/");
        }
    }


	@GetMapping("/modify/{articleno}")
	public String modify(@PathVariable("articleno") int articleNo, 
			@RequestParam Map<String, String> map, Model model)
			throws Exception {
		BoardDto boardDto = boardService.getArticle(articleNo);
		model.addAttribute("article", boardDto);
		model.addAttribute("pgno", map.get("pgno"));
		model.addAttribute("key", map.get("key"));
		model.addAttribute("word", map.get("word"));
		return "board/modify";
	}
	@PostMapping("/modify")
	public String modify(BoardDto boardDto, @RequestParam Map<String, String> map,
			RedirectAttributes redirectAttributes) throws Exception {
		System.out.println("DFSD");
		System.out.println(boardDto.getContent());
		boardService.modifyArticle(boardDto);
		redirectAttributes.addAttribute("pgno", map.get("pgno"));
		redirectAttributes.addAttribute("key", map.get("key"));
		redirectAttributes.addAttribute("word", map.get("word"));
		return "redirect:/article/list";
	}


	@GetMapping("/delete/{articleno}")
	public String delete(@PathVariable("articleno") int articleNo, 
			@RequestParam Map<String, String> map,
			RedirectAttributes redirectAttributes) throws Exception {
		boardService.deleteArticle(articleNo);
		redirectAttributes.addAttribute("pgno", map.get("pgno"));
		redirectAttributes.addAttribute("key", map.get("key"));
		redirectAttributes.addAttribute("word", map.get("word"));
		System.out.println("!!@!@!@");
		System.out.println(map.get("pgno"));
		System.out.println(map.get("key"));
		System.out.println(map.get("word"));
		return "redirect:/article/list";
	}
}