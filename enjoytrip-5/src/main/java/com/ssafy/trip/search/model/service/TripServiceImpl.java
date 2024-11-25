package com.ssafy.trip.search.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.trip.search.model.FavoritDto;
import com.ssafy.trip.search.model.TripDto;
import com.ssafy.trip.search.model.mapper.TripMapper;

@Service
public class TripServiceImpl implements TripService {
	private final TripMapper tripMapper;

	public TripServiceImpl(TripMapper tripMapper) {
		super();
		this.tripMapper = tripMapper;
	}

	@Override
	public List<TripDto> siDoSearch(int sido) throws Exception {
		return tripMapper.siDoSearch(sido);
	}

	@Override
	public List<TripDto> siDoTypeSearch(int sido, int type) throws Exception {
		return tripMapper.siDoTypeSearch(sido, type);
	}

	@Override
	public List<TripDto> siDoTypeTitleSearch(int sido, int type, String title) throws Exception {
		return tripMapper.siDoTypeTitleSearch(sido, type, title);
	}

	@Override
	public List<TripDto> siDoTitleSearch(int sido, String title) throws Exception {
		return tripMapper.siDoTitleSearch(sido, title);
	}

	@Override

	public void createFavorite(FavoritDto favoritDto) throws Exception {
		tripMapper.createFavorite(favoritDto);

	}

	@Override
	public void deleteFavorite(FavoritDto favoritDto) throws Exception {
		tripMapper.deleteFavorite(favoritDto);

	}

	@Override
	public List<FavoritDto> getFavorite(String userId) throws Exception {
		return tripMapper.getFavorite(userId);
	}

	public List<TripDto> getTopAttractions() throws Exception {

		return tripMapper.getTopAttractions();
	}

	@Override
	public List<TripDto> getInfos(List<Integer> noList) throws Exception {
		return tripMapper.getInfos(noList);
	}


}
