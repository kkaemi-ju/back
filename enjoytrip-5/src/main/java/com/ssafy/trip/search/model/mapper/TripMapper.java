package com.ssafy.trip.search.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.search.model.TripDto;

@Mapper
public interface TripMapper {
	List<TripDto> siDoSearch(int sido) throws SQLException;

	List<TripDto> siDoTypeSearch(int sido, int type) throws SQLException;

	List<TripDto> siDoTypeTitleSearch(int sido, int type, String title) throws SQLException;

	List<TripDto> siDoTitleSearch(int sido, String title) throws SQLException;

}
