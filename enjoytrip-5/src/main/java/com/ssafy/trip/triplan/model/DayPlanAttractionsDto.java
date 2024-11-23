package com.ssafy.trip.triplan.model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class DayPlanAttractionsDto {
	private int attractionsNo;

    private int planDayId;
    private int visitOrder;
    private String memo;
}
