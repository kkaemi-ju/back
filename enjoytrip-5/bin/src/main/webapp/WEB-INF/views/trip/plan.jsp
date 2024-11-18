<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Travel Plan</title>
    <!-- Bootstrap CSS -->
    <link rel="shortcut icon" href="./assets/img/favicon.ico" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" />
    <script src="./assets/js/key.js"></script>
    <style>
        body {
            background-color: #f8f9fa;
        }

        .header-container {

            margin-bottom: 30px;
            text-align: center;
        }

        .header-container h1 {
            font-size: 2rem;
            font-weight: bold;
        }

        .input-container {
            margin-bottom: 20px;
        }

        .input-container label {
            font-weight: bold;
        }

        .map-container {
            background-color: #e9ecef;
            height: 400px;
        }
        .main-container {
            margin-top: 120px;
            margin-left: 80px;
            margin-right: 80px;
        }
        /* 커스텀 오버레이 스타일 */
        .distanceOverlay {
            position: relative;
            padding: 5px;
            background-color: white;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.1);
            font-size: 12px;
            color: #333;
            white-space: nowrap;
        }

        .distanceOverlay:after {
            content: '';
            position: absolute;
            margin-left: -5px;
            bottom: -5px;
            left: 50%;
            border-width: 5px;
            border-style: solid;
            border-color: white transparent transparent transparent;
        }

        .register-button {
            position: absolute;
            top: 20px;
            right: 20px;
        }

        #register-btn {
            margin-top: 120px;

            margin-right: 80px;
        }
    </style>
</head>

<body>
    <%@ include file="/header.jsp"  %>
    <div class="main-container">
        <form action="">
            <div class="header-container">
                <h1>여행계획</h1>
                <button id="register-btn" class="btn btn-primary register-button">일정등록하기</button>
            </div>

            <div class="row input-container">
                <div class="col-md-6 mb-3">
                    <label for="travel-title" class="form-label">제목</label>
                    <input type="text" id="travel-title" class="form-control" placeholder="여행 제목 입력">
                </div>
                <div class="col-md-3 mb-3">
                    <label for="travel-cost" class="form-label">경비</label>
                    <input type="number" id="travel-cost" class="form-control" placeholder="경비 입력">
                </div>
                <div class="col-md-3 mb-3">
                    <label for="travel-distance" class="form-label">거리</label>
                    <input type="text" id="travel-distance" class="form-control" readonly>
                </div>
            </div>
        </form>


        <div class="row">
            <!-- 지도 영역 -->
            <div id="map" class="col-md-8 map-container">
                <!-- 지도를 구현할 div -->
            </div>

            <!-- 검색 영역 -->
            <div class="col-md-4">
                <div class="mb-3">
                    <label for="select-area" class="form-label">검색 할 지역 선택</label>
                    <select id="search-area" class="form-select">
                        <option value="0" selected>검색 할 지역 선택</option>
                    </select>
                </div>
                <div class="mb-3">
                    <label for="select-type" class="form-label">관광지 유형</label>
                    <select id="search-content-id" class="form-select">
                        <option value="0" selected>관광지 유형</option>
                        <option value="12">관광지</option>
                        <option value="14">문화시설</option>
                        <option value="15">축제공연행사</option>
                        <option value="25">여행코스</option>
                        <option value="28">레포츠</option>
                        <option value="32">숙박</option>
                        <option value="38">쇼핑</option>
                        <option value="39">음식점</option>
                    </select>
                </div>
                <div class="mb-3">
                    <label for="search-keyword" class="form-label">검색어 입력</label>
                    <input type="text" id="search-keyword" class="form-control" placeholder="검색어 입력">
                </div>
                <div class="d-grid">
                    <button id="btn-search" type="button" class="btn btn-primary">
                        검색
                    </button>
                </div>
            </div>
        </div>
    </div>
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
    <%@ include file="/footer.jsp"%>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>

    <script type="text/javascript"
        src="//dapi.kakao.com/v2/maps/sdk.js?appkey=a2b7c667171b4fb6ad6457d33a4a3ade&libraries=services,clusterer,drawing"></script>
    <script>
        // index page 로딩 후 전국의 시도 설정.
        let serviceKey = "%2BvLZs0HM7xwdJC76SMbdVzLMXbzGRby%2FngxFqxjWvMLGzvxbVaUS53zbQaGEMFJp9V1R51NbyXQuEhhbDxHcJg%3D%3D";
        let areaUrl =
            "https://apis.data.go.kr/B551011/KorService1/areaCode1?serviceKey=" +
            serviceKey +
            "&numOfRows=20&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json";
        const registerBtn = document.querySelector("#register-btn");
        registerBtn.addEventListener("click", function(event) {
            alert("일정등록완료!");
            location.href="planlist.html"
        })
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
        document
            .getElementById("btn-search")
            .addEventListener("click", () => {
                let baseUrl = `https://apis.data.go.kr/B551011/KorService1/`;

                let queryString = `serviceKey=${serviceKey}&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&_type=json&listYN=Y&arrange=A`;
                let areaCode = document.getElementById("search-area").value;
                let contentTypeId =
                    document.getElementById("search-content-id").value;
                let keyword =
                    document.getElementById("search-keyword").value;

                if (parseInt(areaCode))
                    queryString += `&areaCode=${areaCode}`;
                if (parseInt(contentTypeId))
                    queryString += `&contentTypeId=${contentTypeId}`;

                let service = ``;
                if (keyword) {
                    service = `searchKeyword1`;
                    queryString += `&keyword=${keyword}`;
                } else {
                    service = `areaBasedList1`;
                }
                let searchUrl = baseUrl + service + "?" + queryString;

                fetch(searchUrl)
                    .then((response) => response.json())
                    .then((data) => makeList(data));
            });

        var positions; // marker 배열.
        function makeList(data) {
            document
                .querySelector("table")
                .setAttribute("style", "display: ;");
            let trips = data.response.body.items.item;
            let tripList = ``;
            positions = [];
            trips.forEach((area) => {
                tripList += `
                    <tr onclick="moveCenter(${area.mapy}, ${area.mapx});">
                      <td><img src="${area.firstimage}" width="100px"></td>
                      <td>${area.title}</td>
                      <td>${area.addr1} ${area.addr2}</td>
                      <td>${area.mapy}</td>
                      <td>${area.mapx}</td>
                    </tr>
                  `;

                let markerInfo = {
                    title: area.title,
                    latlng: new kakao.maps.LatLng(area.mapy, area.mapx),
                };
                positions.push(markerInfo);
            });
            document.getElementById("trip-list").innerHTML = tripList;
        }

        // 카카오지도
        var mapContainer = document.getElementById("map"), // 지도를 표시할 div
            mapOption = {
                center: new kakao.maps.LatLng(37.500613, 127.036431), // 지도의 중심좌표
                level: 5, // 지도의 확대 레벨
            };

        // 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
        var map = new kakao.maps.Map(mapContainer, mapOption);

        var markers = []; // 마커를 저장할 배열
        var polylines = []; // 폴리라인을 저장할 배열
        var distanceOverlays = []; // 거리 정보를 표시할 커스텀 오버레이를 저장할 배열
        var totalDistance = 0; // 총 거리를 저장할 변수

        function addMarker(position) {
            // 새로운 마커를 생성합니다
            var marker = new kakao.maps.Marker({
                position: position
            });

            // 생성된 마커를 지도에 표시합니다
            marker.setMap(map);

            // 생성된 마커를 배열에 추가합니다
            markers.push(marker);

            // 마커들끼리 선으로 연결할 좌표 배열을 만듭니다
            var path = markers.map(function (marker) {
                return marker.getPosition();
            });

            // 기존의 폴리라인이 있다면 지도에서 제거합니다
            if (polylines.length > 0) {
                polylines.forEach(function (line) {
                    line.setMap(null);
                });
                polylines = [];
            }

            // 거리 오버레이도 제거합니다
            if (distanceOverlays.length > 0) {
                distanceOverlays.forEach(function (overlay) {
                    overlay.setMap(null);
                });
                distanceOverlays = [];
            }

            // 새로운 폴리라인을 생성하여 마커들 사이에 선을 그립니다
            var polyline = new kakao.maps.Polyline({
                path: path, // 선을 그릴 좌표 배열
                strokeWeight: 3, // 선의 두께
                strokeColor: '#db4040', // 선의 색깔
                strokeOpacity: 1, // 선의 불투명도
                strokeStyle: 'solid' // 선의 스타일
            });

            // 생성된 폴리라인을 지도에 표시합니다
            polyline.setMap(map);

            // 폴리라인을 배열에 추가합니다
            polylines.push(polyline);

            // 각 마커 사이의 거리 계산 및 표시
            for (var i = 0; i < path.length - 1; i++) {
                var segment = new kakao.maps.Polyline({
                    path: [path[i], path[i + 1]], // 선의 경로 설정
                });

                var distance = segment.getLength() / 1000; // 두 점 사이의 거리 계산 (km 단위)
                totalDistance += distance; // 총 거리 업데이트

                var midPosition = new kakao.maps.LatLng(
                    (path[i].getLat() + path[i + 1].getLat()) / 2,
                    (path[i].getLng() + path[i + 1].getLng()) / 2
                );

                var distanceOverlay = new kakao.maps.CustomOverlay({
                    map: map,
                    content: '<div class="distanceOverlay">거리 <span class="number">' + distance.toFixed(2) + '</span>km</div>',
                    position: midPosition,
                    xAnchor: 0.5,
                    yAnchor: 0,
                    zIndex: 3
                });

                distanceOverlays.push(distanceOverlay);
            }

            // 총 거리를 travel-distance input에 표시
            document.getElementById("travel-distance").value = totalDistance.toFixed(2) + " km";
        }

        function moveCenter(lat, lng) {
            map.setCenter(new kakao.maps.LatLng(lat, lng));
            addMarker(new kakao.maps.LatLng(lat, lng))
        }
        document
                .getElementById("tripInfoShare")
                .addEventListener("click", function () {
                    window.open("./list.html", "_self");
                });
    </script>
</body>

</html>