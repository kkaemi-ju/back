package com.ssafy.trip.triplan.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.search.controller.TripController;
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
    
}
