package com.ssafy.trip.triplan.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.trip.triplan.model.TripPlanDto;
import com.ssafy.trip.triplan.model.TripPlanRequest;

public interface TripPlanService {
	void saveTripPlan(TripPlanRequest tripRequest) throws Exception;

	List<TripPlanDto> getTripPlansByUserId(String userId) throws Exception;

	Map<String, Object> getTripPlanDetails(int tripPlanId) throws Exception;
}
