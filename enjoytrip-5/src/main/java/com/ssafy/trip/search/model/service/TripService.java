package com.ssafy.trip.search.model.service;

import java.util.List;


import com.ssafy.trip.search.model.TripDto;

public interface TripService {
	List<TripDto> siDoSearch(int sido) throws Exception;

	List<TripDto> siDoTypeSearch(int sido, int type) throws Exception;

	List<TripDto> siDoTypeTitleSearch(int sido, int type, String title) throws Exception;

	List<TripDto> siDoTitleSearch(int sido, String title) throws Exception;

}
