package com.ssafy.trip.triplan.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.trip.search.controller.TripController;
import com.ssafy.trip.triplan.model.DayPlanRequest;
import com.ssafy.trip.triplan.model.TripPlanRequest;
import com.ssafy.trip.triplan.model.service.TripPlanService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/tripplan")
public class TripPlanController {
	private final TripPlanService tripPlanService;

    public TripPlanController(TripPlanService tripPlanService) {
        this.tripPlanService = tripPlanService;
    }
    @GetMapping
    public ResponseEntity<String> tripPlanTest() {
        
        return ResponseEntity.ok("Test!");
    }
    
    @PostMapping
    public ResponseEntity<?> createTripPlan(@RequestBody TripPlanRequest tripPlanRequest) {
    	System.out.println(tripPlanRequest);
//    	for (DayPlanRequest dayPlanRequest : tripPlanRequest.getDays()) {
//    		System.out.println(dayPlanRequest);
//    	}
        //tripPlanService.saveTripPlan(tripPlanRequest);
        return ResponseEntity.ok("Trip plan saved successfully!");
    }
}
