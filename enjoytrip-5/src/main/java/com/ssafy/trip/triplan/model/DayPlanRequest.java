package com.ssafy.trip.triplan.model;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@NoArgsConstructor
@ToString
public class DayPlanRequest {
	private Integer dayNumber;
    private String date;
    private List<AttractionRequest> attractions;

}
