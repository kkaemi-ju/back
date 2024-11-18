<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>ENJOY TRIP</title>
<link rel="shortcut icon" href="./assets/img/favicon.ico" />
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous" />
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" />
<link rel="stylesheet" href="/assets/css/triplist.css" />
<script src="./assets/js/key.js"></script>
</head>

<body>
	<%@ include file="/header.jsp"%>
	<!-- 중앙 content start -->
	<div class="container">
		<div style="height: 70px"></div>

		<!-- 중앙 left content end -->
		<!-- 중앙 center content end -->
		<div class="col-md-9" style="margin: 0 auto;">
			<div class="alert alert-primary mt-3 text-center fw-bold"
				role="alert">전국 관광지 정보</div>
			<!-- 관광지 검색 start -->
			<form class="d-flex my-3" onsubmit="return false;" role="search">
				<select id="search-area" class="form-select me-2">
					<option value="0" selected>검색 할 지역 선택</option>
				</select> <select id="search-content-id" class="form-select me-2">
					<option value="0" selected>관광지 유형</option>
					<option value="12">관광지</option>
					<option value="14">문화시설</option>
					<option value="15">축제공연행사</option>
					<option value="25">여행코스</option>
					<option value="28">레포츠</option>
					<option value="32">숙박</option>
					<option value="38">쇼핑</option>
					<option value="39">음식점</option>
				</select> <input id="search-keyword" class="form-control me-2" type="search"
					placeholder="검색어" aria-label="검색어" />
				<button id="btn-search" class="btn btn-outline-success"
					type="button">검색</button>
			</form>
			<!-- kakao map start -->
			<div id="map" class="mt-3" style="width: 100%; height: 400px"></div>
			<!-- kakao map end -->
			<div class="row">
				<table class="table table-striped" style="display: none">
					<thead>
						<tr>
							<th>대표이미지</th>
							<th>관광지명</th>
							<th>주소</th>
							<th>위도</th>
							<th>경도</th>
						</tr>
					</thead>
					<tbody id="trip-list"></tbody>
				</table>
			</div>
			<!-- 관광지 검색 end -->
		</div>
	</div>

	<%@ include file="/footer.jsp"%>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
		crossorigin="anonymous"></script>
	<script src="./assets/js/main.js"></script>
	<script type="text/javascript"
		src="//dapi.kakao.com/v2/maps/sdk.js?appkey=ac8cafcc4f97ea5726e254d511bea128&libraries=services,clusterer,drawing"></script>
	<script>
    // index page 로딩 후 전국의 시도 설정.
    let serviceKey = "%2BvLZs0HM7xwdJC76SMbdVzLMXbzGRby%2FngxFqxjWvMLGzvxbVaUS53zbQaGEMFJp9V1R51NbyXQuEhhbDxHcJg%3D%3D";
    let areaUrl =
      "https://apis.data.go.kr/B551011/KorService1/areaCode1?serviceKey=" +
      serviceKey +
      "&numOfRows=20&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json";

    // fetch(areaUrl, { method: "GET" }).then(function (response) { return response.json() }).then(function (data) { makeOption(data); });
    fetch(areaUrl, { method: "GET" })
      .then((response) => response.json())
      .then((data) => makeOption(data));

    function makeOption(data) {
      let areas = data.response.body.items.item;
      let sel = document.getElementById("search-area");
      areas.forEach((area) => {
        let opt = document.createElement("option");
        opt.setAttribute("value", area.code);
        opt.appendChild(document.createTextNode(area.name));

        sel.appendChild(opt);
        
      });
    }

    // 검색 버튼을 누르면..
    // 지역, 유형, 검색어 얻기.
    // 위 데이터를 가지고 공공데이터에 요청.
    // 받은 데이터를 이용하여 화면 구성.
    document.getElementById("btn-search").addEventListener("click", () => {
		let areaCode = document.getElementById("search-area").value;
		let contentTypeId = document.getElementById("search-content-id").value;
		let keyword = document.getElementById("search-keyword").value;
		let url = "";
		if(parseInt(areaCode)==0) alert("지역을 선택해주세요");
		else {
			if(parseInt(contentTypeId)==0) {
			
				if(keyword) {
					url = "<%= request.getContextPath() %>/trip?action=sidotitlesearch&sido="+parseInt(areaCode)+"&title="+keyword;
				} else {
					url = "<%= request.getContextPath() %>/trip?action=sidosearch&sido="+parseInt(areaCode);
				}
			} else {
				if(keyword) {
					url = "<%= request.getContextPath() %>/trip?action=sidotypetitlesearch&sido="+parseInt(areaCode)+"&type="+parseInt(contentTypeId)+"&title="+keyword;
				} else {
					url = "<%= request.getContextPath() %>/trip?action=sidotypesearch&sido="+parseInt(areaCode)+"&type="+parseInt(contentTypeId);
				}
			}
			fetch(url)
	          .then((response)=> response.json())
	          .then(data => makeList(data));
		}

    });

    var positions; // marker 배열.
    function makeList(data) {

    	  // 테이블을 표시하기
    	  document.querySelector("table").style.display = "table";

    	  let tripList = ``;
    	  positions = [];
    	  
    	  // 데이터가 배열인지 확인
    	  const limitedData = data.length>10 ? data.slice(0, 10) : [];
    	  
    	  if (limitedData.length === 0) {
    	   	alert("검색 내역이 존재하지 않습니다.")
    	    return;
    	  }
    	  limitedData.forEach((area) => {

    	    // 테이블에 추가할 HTML 구성
    	   tripList +=
				  '<tr onclick="moveCenter(' + area.latitude + ', ' + area.longitude + ');">' +
				  '<td><img src="' + area.firstImage1 + '" width="100px"></td>' +
				  '<td>' + area.title + '</td>' +
				  '<td>' + area.addr1 + ' ' + area.addr2 + '</td>' +
				  '<td>' + area.latitude + '</td>' +
				  '<td>' + area.longitude + '</td>' +
				  '</tr>';
    	    let markerInfo = {
    	      title: area.title,
    	      latlng: new kakao.maps.LatLng(area.latitude, area.longitude),
    	      img: area.firstImage1,
    	      addr1: area.addr1,
    	      addr2: area.addr2
    	    };
    	    
    	    positions.push(markerInfo); 
    	  });

    	  // 데이터를 테이블에 추가
    	  document.getElementById("trip-list").innerHTML = tripList;
    	  // 지도에 마커 표시
    	  displayMarker();
    	}


    // 카카오지도
    var mapContainer = document.getElementById("map"), // 지도를 표시할 div
      mapOption = {
        center: new kakao.maps.LatLng(37.500613, 127.036431), // 지도의 중심좌표
        level: 5, // 지도의 확대 레벨
      };

    // 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
    var map = new kakao.maps.Map(mapContainer, mapOption);

    function displayMarker() {
      // 마커 이미지의 이미지 주소입니다
      var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";

      for (var i = 0; i < positions.length; i++) {
        // 마커 이미지의 이미지 크기 입니다
        var imageSize = new kakao.maps.Size(24, 35);

        // 마커 이미지를 생성합니다
        var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

        // 마커를 생성합니다
        var marker = new kakao.maps.Marker({
          map: map, // 마커를 표시할 지도
          position: positions[i].latlng, // 마커를 표시할 위치
          title: positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
          image: markerImage, // 마커 이미지
        });

        var content = 
            '<div class="wrap">' + 
              '<div class="info">' + 
                '<div class="title">' + positions[i].title + '</div>' + 
                '<div class="body">' + 
                  '<div class="img"><img src="' + positions[i].img + '" width="80" height="70"></div>' + 
                  
                '</div>' + 
              '</div>' + 
            '</div>';

        // 마커 위에 커스텀오버레이를 표시합니다
        // 마커를 중심으로 커스텀 오버레이를 표시하기위해 CSS를 이용해 위치를 설정했습니다
        var overlay = new kakao.maps.CustomOverlay({
          content: content,
          map: map,
          position: marker.getPosition()
        });
        // 마커를 클릭했을 때 커스텀 오버레이를 표시합니다
        kakao.maps.event.addListener(marker, 'click', function () {
          overlay.setMap(map);
        });
      }

      // 첫번째 검색 정보를 이용하여 지도 중심을 이동 시킵니다
      map.setCenter(positions[0].latlng);
    }

    function moveCenter(lat, lng) {
      map.setCenter(new kakao.maps.LatLng(lat, lng));
    }
  </script>

	

</body>

</html>