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
	private Integer attractionNo;
    private Integer visitOrder;
    private String memo;
}
