package com.ssafy.trip.tripplan.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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
	Integer getLastInsertedId() throws SQLException;// Auto-increment된 ID를 반환
	TripPlanDto getTripPlanById(int tripPlanId) throws SQLException;

    List<DayPlanDto> getDayPlansByTripPlanId(int tripPlanId) throws SQLException;

    List<DayPlanAttractionsDto> getAttractionsByTripPlanId(int tripPlanId) throws SQLException;

    TripDto getAttractionDetails(int attractionsNo) throws SQLException;
	void deleteTripPlan(int tripPlanId) throws SQLException;
	void updateTripPlan(TripPlanDto tripPlan) throws SQLException;
	void deleteDayPlansByTripPlanId(int tripPlanId) throws SQLException;
	List<Map<String, Object>> getTopVisitedSidoCodes() throws SQLException;
	
}
