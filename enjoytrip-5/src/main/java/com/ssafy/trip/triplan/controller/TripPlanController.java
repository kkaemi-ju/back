package com.ssafy.trip.triplan.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.search.controller.TripController;
import com.ssafy.trip.search.model.TripDto;
import com.ssafy.trip.triplan.model.DayPlanRequest;
import com.ssafy.trip.triplan.model.TripPlanDto;
import com.ssafy.trip.triplan.model.TripPlanRequest;
import com.ssafy.trip.triplan.model.service.TripPlanService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/tripplan")
public class TripPlanController {
	private final TripPlanService tripPlanService;

    public TripPlanController(TripPlanService tripPlanService) {
        this.tripPlanService = tripPlanService;
    }
    @Operation(summary="get tripplan list ", description="TripPlanDto Check")
    @GetMapping("/{userid}")
    public ResponseEntity<List<TripPlanDto>> getTripPlansByUserId(@PathVariable("userid") String userId) {
        List<TripPlanDto> tripPlans=null;
		try {
			tripPlans = tripPlanService.getTripPlansByUserId(userId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return ResponseEntity.ok(tripPlans);
    }
    @Operation(summary="create trip plan", description="tripPlan ")
    @PostMapping
    public ResponseEntity<?> createTripPlan(@RequestBody TripPlanRequest tripPlanRequest) {
    	System.out.println(tripPlanRequest);
//    	for (DayPlanRequest dayPlanRequest : tripPlanRequest.getDays()) {
//    		System.out.println(dayPlanRequest);
//    	}
        try {
			tripPlanService.saveTripPlan(tripPlanRequest);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return ResponseEntity.ok("Trip plan saved successfully!");
    }
    @GetMapping("/details/{tripPlanId}")
    public ResponseEntity<Map<String, Object>> getTripPlanDetails(
            @PathVariable int tripPlanId) {
        Map<String, Object> planDetails=null;
		try {
			planDetails = tripPlanService.getTripPlanDetails(tripPlanId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return ResponseEntity.ok(planDetails);
    }
    @PutMapping("/{tripPlanId}")
    public ResponseEntity<?> updateTripPlan(
            @PathVariable int tripPlanId, 
            @RequestBody TripPlanRequest tripPlanRequest) {
        try {
            tripPlanService.updateTripPlan(tripPlanId, tripPlanRequest);
            return ResponseEntity.ok("Trip plan updated successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to update trip plan");
        }
    }
    @DeleteMapping("/{tripPlanId}")
    public ResponseEntity<Map<String, Object>> deleteTripPlan(
    		@PathVariable("tripPlanId") int tripPlanId) {
 
		try {
			tripPlanService.deleteTripPlan(tripPlanId);
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
    
    @GetMapping("/top-sido")
    public ResponseEntity<List<Map<String, Object>>> getTopVisitedSidoCodes() {
        try {
            List<Map<String, Object>> topSidoCodes = tripPlanService.getTopVisitedSidoCodes();
            return ResponseEntity.ok(topSidoCodes); // 정상적으로 데이터를 반환
        } catch (Exception e) {
            // 에러 발생 시 로그 출력 및 빈 리스트 반환
            e.printStackTrace();
            return ResponseEntity.ok(new ArrayList<>()); // 빈 리스트 반환
        }
    }
    
    @GetMapping("/top-att/{sidoCode}")
    public ResponseEntity<List<Map<String, Object>>> getTopAttractionsBySidoCode(@PathVariable("sidoCode") int sidoCode) {
        try {
            List<Map<String, Object>> topSidoCodes = tripPlanService.getTopAttractionsBySidoCode(sidoCode);
            return ResponseEntity.ok(topSidoCodes); // 정상적으로 데이터를 반환
        } catch (Exception e) {
            // 에러 발생 시 로그 출력 및 빈 리스트 반환
            e.printStackTrace();
            return ResponseEntity.ok(new ArrayList<>()); // 빈 리스트 반환
        }
    }
}
