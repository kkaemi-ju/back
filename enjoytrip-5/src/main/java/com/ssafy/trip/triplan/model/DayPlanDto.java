package com.ssafy.trip.triplan.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class DayPlanDto {
	private int dayPlanId;

    private int tripPlanId;
    private int dayNumber;
    private String date;
}
