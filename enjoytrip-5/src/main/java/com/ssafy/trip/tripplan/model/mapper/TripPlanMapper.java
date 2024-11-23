package com.ssafy.trip.tripplan.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.search.model.TripDto;
import com.ssafy.trip.triplan.model.DayPlanAttractionsDto;
import com.ssafy.trip.triplan.model.DayPlanDto;
import com.ssafy.trip.triplan.model.TripPlanDto;

@Mapper
public interface TripPlanMapper {
	List<TripPlanDto> getTripPlansByUserId(String userId) throws SQLException;
	void insertTripPlan(TripPlanDto tripPlan) throws SQLException;

	void insertDayPlan(DayPlanDto dayPlan) throws SQLException;

	void insertDayPlanAttraction(DayPlanAttractionsDto dayPlanAttractions) throws SQLException;
	Integer getLastInsertedId();// Auto-increment된 ID를 반환
	TripPlanDto getTripPlanById(int tripPlanId);

    List<DayPlanDto> getDayPlansByTripPlanId(int tripPlanId);

    List<DayPlanAttractionsDto> getAttractionsByTripPlanId(int tripPlanId);

    TripDto getAttractionDetails(int attractionsNo);
	
}
