package com.ssafy.trip.search.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class FavoritDto {
	private int favoriteId;
	private int attractionsNo;
	private String userId;
	private String createdAt;
}
