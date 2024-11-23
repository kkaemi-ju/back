package com.ssafy.trip.triplan.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.trip.search.model.TripDto;
import com.ssafy.trip.triplan.model.AttractionRequest;
import com.ssafy.trip.triplan.model.DayPlanAttractionsDto;
import com.ssafy.trip.triplan.model.DayPlanDto;
import com.ssafy.trip.triplan.model.DayPlanRequest;
import com.ssafy.trip.triplan.model.TripPlanDto;
import com.ssafy.trip.triplan.model.TripPlanRequest;
import com.ssafy.trip.tripplan.model.mapper.TripPlanMapper;

@Service
public class TripPlanServiceImpl implements TripPlanService {
	private final TripPlanMapper tripPlanMapper;
	
	public TripPlanServiceImpl(TripPlanMapper tripPlanMapper) {
		super();
		this.tripPlanMapper = tripPlanMapper;
	}
	@Override
	public List<TripPlanDto> getTripPlansByUserId(String userId) throws Exception {
		return tripPlanMapper.getTripPlansByUserId(userId);
	}
	@Transactional
	@Override
	public void saveTripPlan(TripPlanRequest tripRequest) throws Exception {
		// 1. Insert into trip_plan
		TripPlanDto tripPlan = new TripPlanDto();
        tripPlan.setTripName(tripRequest.getTripName());
        tripPlan.setStartDate(tripRequest.getStartDate());
        tripPlan.setEndDate(tripRequest.getEndDate());
        tripPlan.setUserId(tripRequest.getUserId());
        tripPlanMapper.insertTripPlan(tripPlan);
        
        Integer tripPlanId = tripPlan.getTripPlanId(); // INSERT된 TripPlan ID 가져오기

        // 2. Insert into day_plan and day_plan_attractions
        for (DayPlanRequest dayPlanRequest : tripRequest.getDays()) {
            DayPlanDto dayPlan = new DayPlanDto();
            dayPlan.setTripPlanId(tripPlanId);
            dayPlan.setDayNumber(dayPlanRequest.getDayNumber());
            dayPlan.setDate(dayPlanRequest.getDate());
            tripPlanMapper.insertDayPlan(dayPlan);
            
            Integer dayPlanId = dayPlan.getDayPlanId(); // INSERT된 DayPlan ID 가져오기

            for (AttractionRequest attraction : dayPlanRequest.getAttractions()) {
                DayPlanAttractionsDto dayPlanAttractions = new DayPlanAttractionsDto();
                dayPlanAttractions.setPlanDayId(dayPlanId);
                dayPlanAttractions.setAttractionsNo(attraction.getAttractionsNo());
                dayPlanAttractions.setVisitOrder(attraction.getVisitOrder());
                dayPlanAttractions.setMemo(attraction.getMemo());
                tripPlanMapper.insertDayPlanAttraction(dayPlanAttractions);
            }
        }
        
	}
	@Override
	public Map<String, Object> getTripPlanDetails(int tripPlanId) throws Exception {
		TripPlanDto tripPlan = tripPlanMapper.getTripPlanById(tripPlanId);
        List<DayPlanDto> dayPlans = tripPlanMapper.getDayPlansByTripPlanId(tripPlanId);
        List<DayPlanAttractionsDto> attractions = tripPlanMapper.getAttractionsByTripPlanId(tripPlanId);
//
        // Join attractions with their details
        List<Map<String, Object>> enrichedAttractions = attractions.stream().map(attraction -> {
            TripDto tripDto = tripPlanMapper.getAttractionDetails(attraction.getAttractionsNo());
            Map<String, Object> attractionMap = new HashMap<>();
            attractionMap.put("no", tripDto.getNo());
            attractionMap.put("title", tripDto.getTitle());
            System.out.println(tripDto);
            attractionMap.put("firstImage1", tripDto.getFirstImage1());
            attractionMap.put("addr1", tripDto.getAddr1());
            attractionMap.put("addr2", tripDto.getAddr2());
            attractionMap.put("latitude", tripDto.getLatitude());
            attractionMap.put("longitude", tripDto.getLongitude());
            
         // Include DayPlan and Attraction Info
            attractionMap.put("visitOrder", attraction.getVisitOrder());
            dayPlans.stream()
            .filter(dayPlan -> dayPlan.getDayPlanId() == attraction.getPlanDayId())
            .findFirst()
            .ifPresent(dayPlan -> attractionMap.put("dayNumber", dayPlan.getDayNumber()));

            return attractionMap;
        }).toList();

        Map<String, Object> result = new HashMap<>();
        result.put("tripName", tripPlan.getTripName());
        result.put("startDate", tripPlan.getStartDate());
        result.put("endDate", tripPlan.getEndDate());
        result.put("attractions", enrichedAttractions);

        return result;
	}
	

}
