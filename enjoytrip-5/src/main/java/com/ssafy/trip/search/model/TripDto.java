package com.ssafy.trip.search.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Schema(title = "TripDto (관광지 정보)", description = "관광지 정보 ")
public class TripDto {
	@Schema(description ="관광지 id", requiredMode = Schema.RequiredMode.REQUIRED)
	private int no;
	@Schema(description = "content id")
	private int contentId;
	@Schema(description = "관광지 이름 ")
	private String title;
	@Schema(description = "관광지 종류 ")
	private int contentTypeId;
	@Schema(description = "지역 코드 ")
	private int areaCode;
	@Schema(description = "시군구 ")
	private int siGunGuCode;
	@Schema(description = "이미지1" )
	private String firstImage1;
	@Schema(description = "이미지2 ")
	private String firstImage2;
	@Schema(description = "maplevel" )
	private int mapLevel;
	@Schema(description = "위도 ")
	private double latitude;
	@Schema(description = "경도 ")
	private double longitude;
	@Schema(description = " 전화 ")
	private String tel;
	@Schema(description = "주소1 ")
	private String addr1;
	@Schema(description = "주소2 ")
	private String addr2;
	@Schema(description = "홈페이지")
	private String homepage;
	@Schema(description ="미리보기")
	private String overview;

}
