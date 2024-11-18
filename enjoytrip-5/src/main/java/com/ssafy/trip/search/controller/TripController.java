package com.ssafy.trip.search.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssafy.trip.search.model.TripDto;
import com.ssafy.trip.search.model.service.TripService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/trip")
public class TripController {

	private final TripService tripService;

	public TripController(TripService tripService) {
		this.tripService = tripService;
	}

	@GetMapping("/")
	public String mainPage() {
		return "/trip/search";
	}

	@PostMapping("/sidosearch")
	@ResponseBody
	public ResponseEntity<List<TripDto>> siDoSearch(@RequestBody Map<String, Object> params) {
		Integer sido = (Integer) params.get("areaCode");
		log.debug("SiDoSearch sido : {}", sido);
		try {
			List<TripDto> result = tripService.siDoSearch(sido);
			System.out.println(result);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			log.error("오류 발생", e);
			return ResponseEntity.status(500).build();
		}
	}

	@PostMapping("/sidotypesearch")
	@ResponseBody
	public ResponseEntity<List<TripDto>> siDoTypeSearch(@RequestBody Map<String, Object> params) {
		Integer sido = (Integer) params.get("areaCode");
		Integer type = (Integer) params.get("contentTypeId");
		log.debug("SiDoTypeSearch sido : {}, type : {}", sido, type);
		try {
			List<TripDto> result = tripService.siDoTypeSearch(sido, type);
			System.out.println(result);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			log.error("오류 발생", e);
			return ResponseEntity.status(500).build();
		}
	}

	@PostMapping("/sidotypetitlesearch")
	@ResponseBody
	public ResponseEntity<List<TripDto>> siDoTypeTitleSearch(@RequestBody Map<String, Object> params) {
		Integer sido = (Integer) params.get("areaCode");
		Integer type = (Integer) params.get("contentTypeId");
		String title = (String) params.get("keyword");
		log.debug("SiDoTypeTitleSearch sido : {}, type : {}, title : {}", sido, type, title);
		try {
			List<TripDto> result = tripService.siDoTypeTitleSearch(sido, type, title);
			System.out.println(result);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			log.error("오류 발생", e);
			return ResponseEntity.status(500).build();
		}
	}

	@PostMapping("/sidotitlesearch")
	@ResponseBody
	public ResponseEntity<List<TripDto>> siDoTitleSearch(@RequestBody Map<String, Object> params) {
		Integer sido = (Integer) params.get("areaCode");
		String title = (String) params.get("keyword");
		log.debug("SiDoTitleSearch sido : {}, title : {}", sido, title);
		try {
			List<TripDto> result = tripService.siDoTitleSearch(sido, title);
			System.out.println(result);
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			log.error("오류 발생", e);
			return ResponseEntity.status(500).build();
		}
	}

//	// 지역만 검색
//	@PostMapping("/sidosearch")
//	public String siDoSearch(@RequestBody Map<String, Object> params, Model model) {
//		Integer sido = (Integer) params.get("areaCode");
//		System.out.println("지역!");
//		log.debug("SiDoSearch sido : {}", sido);
//		try {
//			List<TripDto> result = tripService.siDoSearch(sido);
//			model.addAttribute("result", result);
//			System.out.println(result);
//			return "trip/search";
//		} catch (Exception e) {
//			log.error("오류 발생", e);
//			model.addAttribute("msg", "오류 발생");
//			return "error/error";
//		}
//	}
//
//	// 지역과 유형 검색
//	@PostMapping("/sidotypesearch")
//	public String siDoTypeSearch(@RequestBody Map<String, Object> params, Model model) {
//		Integer sido = (Integer) params.get("areaCode");
//		Integer type = (Integer) params.get("contentTypeId");
//		System.out.println("지역 & 유형");
//		log.debug("SiDoTypeSearch sido : {}, type : {}", sido, type);
//		try {
//			List<TripDto> result = tripService.siDoTypeSearch(sido, type);
//			model.addAttribute("result", result);
//			System.out.println(result);
//			return "trip/search";
//		} catch (Exception e) {
//			log.error("오류 발생", e);
//			model.addAttribute("msg", "오류 발생");
//			return "error/error";
//		}
//	}
//
//	// 지역, 유형, 검색어로 검색
//	@PostMapping("/sidotypetitlesearch")
//	public String siDoTypeTitleSearch(@RequestBody Map<String, Object> params, Model model) {
//		Integer sido = (Integer) params.get("areaCode");
//		Integer type = (Integer) params.get("contentTypeId");
//		System.out.println("지역, 유형, 검색어");
//		String title = (String) params.get("keyword");
//		log.debug("SiDoTypeTitleSearch sido : {}, type : {}, title : {}", sido, type, title);
//		try {
//			List<TripDto> result = tripService.siDoTypeTitleSearch(sido, type, title);
//			model.addAttribute("result", result);
//			System.out.println(result);
//			return "trip/search";
//		} catch (Exception e) {
//			log.error("오류 발생", e);
//			model.addAttribute("msg", "오류 발생");
//			return "error/error";
//		}
//	}
//
//	// 지역과 검색어로 검색
//	@PostMapping("/sidotitlesearch")
//	public String siDoTitleSearch(@RequestBody Map<String, Object> params, Model model) {
//		Integer sido = (Integer) params.get("areaCode");
//		String title = (String) params.get("keyword");
//		System.out.println("지역, 검색어!");
//		log.debug("SiDoTitleSearch sido : {}, title : {}", sido, title);
//		try {
//			List<TripDto> result = tripService.siDoTitleSearch(sido, title);
//			model.addAttribute("result", result);
//			System.out.println(result);
//			return "trip/search";
//		} catch (Exception e) {
//			log.error("오류 발생", e);
//			model.addAttribute("msg", "오류 발생");
//			return "error/error";
//		}
//
//	}
}
