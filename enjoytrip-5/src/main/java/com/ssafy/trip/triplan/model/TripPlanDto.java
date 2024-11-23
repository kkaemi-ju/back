package com.ssafy.trip.triplan.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class TripPlanDto {
	private int tripPlanId;
    private String tripName;
    private String startDate;
    private String endDate;
    private String createdAt;
    private String updatedAt;
    private String userId;
}
