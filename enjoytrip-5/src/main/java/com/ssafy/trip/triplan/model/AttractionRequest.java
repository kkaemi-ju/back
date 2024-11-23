package com.ssafy.trip.triplan.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AttractionRequest {
	private int attractionsNo;
    private int visitOrder;
    private String memo;
}
