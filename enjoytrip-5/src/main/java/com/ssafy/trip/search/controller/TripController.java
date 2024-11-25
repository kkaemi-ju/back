package com.ssafy.trip.search.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.trip.search.model.FavoritDto;
import com.ssafy.trip.search.model.TripDto;
import com.ssafy.trip.search.model.service.OpenAIService;
import com.ssafy.trip.search.model.service.TripService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/attraction")
public class TripController {

	private final OpenAIService openAIService;
    private final TripService tripService;

    public TripController(TripService tripService, OpenAIService openAIService) {
        this.tripService = tripService;
        this.openAIService = openAIService;
    }

    @GetMapping("/")
    public ResponseEntity<String> mainPage() {
    	// 원래 지역 리스트 필
        return ResponseEntity.ok("trip 준비완료!");
    }

    // 도시
    @PostMapping("/sidosearch")
    public ResponseEntity<List<TripDto>> siDoSearch(@RequestBody Map<String, Object> params) {
    	System.out.println("도시 검색!!!!!");
        Integer sido = (Integer) params.get("areaCode");
        log.debug("SiDoSearch sido : {}", sido);
        try {
            List<TripDto> result = tripService.siDoSearch(sido);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("오류 발생", e);
            return ResponseEntity.status(500).build();
        }
    }
    // 도시, 타입
    @PostMapping("/sidotypesearch")
    public ResponseEntity<List<TripDto>> siDoTypeSearch(@RequestBody Map<String, Object> params) {
        Integer sido = (Integer) params.get("areaCode");
        Integer type = (Integer) params.get("contentTypeId");
        log.debug("SiDoTypeSearch sido : {}, type : {}", sido, type);
        try {
            List<TripDto> result = tripService.siDoTypeSearch(sido, type);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("오류 발생", e);
            return ResponseEntity.status(500).build();
        }
    }

    // 도시, 타입, 검색어
    @PostMapping("/sidotypetitlesearch")
    public ResponseEntity<List<TripDto>> siDoTypeTitleSearch(@RequestBody Map<String, Object> params) {
        Integer sido = (Integer) params.get("areaCode");
        Integer type = (Integer) params.get("contentTypeId");
        String title = (String) params.get("keyword");
        log.debug("SiDoTypeTitleSearch sido : {}, type : {}, title : {}", sido, type, title);
        try {
            List<TripDto> result = tripService.siDoTypeTitleSearch(sido, type, title);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("오류 발생", e);
            return ResponseEntity.status(500).build();
        }
    }
    // 도시, 검색어
    @PostMapping("/sidotitlesearch")
    public ResponseEntity<List<TripDto>> siDoTitleSearch(@RequestBody Map<String, Object> params) {
        Integer sido = (Integer) params.get("areaCode");
        String title = (String) params.get("keyword");
        log.debug("SiDoTitleSearch sido : {}, title : {}", sido, title);
        try {
            List<TripDto> result = tripService.siDoTitleSearch(sido, title);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("오류 발생", e);
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/favorite/{userid}")
    public ResponseEntity<List<FavoritDto>> getFavorite(@PathVariable("userid") String userId){
    	List<FavoritDto> favorites = null;
    	try {
    		favorites = tripService.getFavorite(userId);

    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return ResponseEntity.ok(favorites);
    }
    
    @GetMapping("/favorite/att/{userid}")
    public ResponseEntity<List<FavoritDto>> getFavoriteAtt(@PathVariable("userid") String userId){
    	List<FavoritDto> favorites = null;
    	try {
    		favorites = tripService.getFavoriteAtt(userId);

    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return ResponseEntity.ok(favorites);
    }
    
    @PostMapping("/favorite")
    public ResponseEntity<?> createFavorite(@RequestBody FavoritDto favoritDto){
    	try {
    		tripService.createFavorite(favoritDto);
    		return ResponseEntity.ok("Favorit inserted successfully!");
    	} catch (Exception e) {
    		e.printStackTrace();
    		return ResponseEntity.status(500).body("Failed to insert favorite");
    	}
    }

    @DeleteMapping("/favorite")
    public ResponseEntity<?> deleteFavorite(
    		@RequestParam int attractionsNo,
    		@RequestParam String userId){
    	try {
    		FavoritDto favoritDto = new FavoritDto();
    		favoritDto.setAttractionsNo(attractionsNo);
    		favoritDto.setUserId(userId);
    		tripService.deleteFavorite(favoritDto);
    		return ResponseEntity.ok("Favorit deleted successfully!");
    	} catch (Exception e) {
    		e.printStackTrace();
    		return ResponseEntity.status(500).body("Failed to insert favorite");
    	}
    }
    // 랭킹 여행지
    @GetMapping("/top")
    public ResponseEntity<List<TripDto>> getAttractions(){
 	   try {
 		   List<TripDto> result = tripService.getTopAttractions();
 		   System.out.println("result" + result);
 		   return ResponseEntity.ok(result);
 	   }catch(Exception e) {
 		   e.printStackTrace();
 		   log.error("오류 발생", e);
            return ResponseEntity.status(500).build();
 	   }

    }

    @PostMapping("/info") // POST로 변경 (리스트를 전달받을 때 적합)
    public ResponseEntity<List<TripDto>> getInfos(@RequestBody List<Integer> noList) {
        try {

            List<TripDto> result = tripService.getInfos(noList);
            System.out.println("Result: " + result);

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("오류 발생", e);
            return ResponseEntity.status(500).build();
        }

    }
    
    @Value("${openai.api.key}")
    private String openaiApiKey;
    
    @PostMapping("/ai-recommendations")
    public ResponseEntity<?> getAIRecommendations(@RequestBody Map<String, String> request) {
        String region = request.get("region");
        // OpenAI API 호출 구현
        // OpenAI 클라이언트 초기화 및 API 호출
        // 결과 반환
        // OpenAI 서비스 호출
        List<String> recommendations = openAIService.getRecommendations(region);
       System.out.println(recommendations);
        
        return ResponseEntity.ok().body(Map.of("recommendations", recommendations));
    }
    
}