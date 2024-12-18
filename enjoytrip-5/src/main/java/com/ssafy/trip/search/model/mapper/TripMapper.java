package com.ssafy.trip.search.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.trip.search.model.FavoritDto;
import com.ssafy.trip.search.model.TripDto;

@Mapper
public interface TripMapper {
	List<TripDto> siDoSearch(int sido) throws SQLException;

	List<TripDto> siDoTypeSearch(int sido, int type) throws SQLException;

	List<TripDto> siDoTypeTitleSearch(int sido, int type, String title) throws SQLException;

	List<TripDto> siDoTitleSearch(int sido, String title) throws SQLException;


	void createFavorite(FavoritDto favoritDto) throws SQLException;

	void deleteFavorite(FavoritDto favoritDto) throws SQLException;

	List<FavoritDto> getFavorite(String userId) throws SQLException;


	List<TripDto> getTopAttractions() throws SQLException;
//	List<TripDto> getInfos(Map<String, Object> params) throws SQLException;
	List<TripDto> getInfos(List<Integer> noList) throws SQLException;

	List<FavoritDto> getFavoriteAtt(String userId) throws SQLException;


}
