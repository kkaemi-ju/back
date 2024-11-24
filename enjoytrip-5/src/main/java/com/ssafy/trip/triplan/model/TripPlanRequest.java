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
public class TripPlanRequest {
	private String tripName;
    private String startDate;
    private String endDate;
    private String userId;
    private int sidoCode;
    private List<DayPlanRequest> days;
}
