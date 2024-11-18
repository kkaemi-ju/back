<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Travel Plan List</title>
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

        .card {
            border: none;
            border-radius: 15px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease;
            margin-bottom: 30px;
        }

        .card:hover {
            transform: translateY(-10px);
        }

        .card img {
            border-top-left-radius: 15px;
            border-top-right-radius: 15px;
            height: 200px;
            object-fit: cover;
        }

        .card-title {
            font-size: 1.2rem;
            font-weight: bold;
        }

        .card-text {
            color: #6c757d;
        }

        .card-price {
            font-size: 1.2rem;
            color: #007bff;
            font-weight: bold;
        }

        .card-distance {
            font-size: 1rem;
            color: #6c757d;
        }

        .header-container {
            margin-top: 30px;
            margin-bottom: 30px;
            text-align: center;
        }

        .header-container h1 {
            font-size: 2rem;
            font-weight: bold;
            margin-bottom: 20px;
        }

        .add-button-container {
            text-align: left;
            margin-bottom: 20px;
        }

        .add-button-container button {
            font-size: 1rem;
            font-weight: bold;
        }

        .main-container {
            margin-top: 120px;
            margin-left: 80px;
            margin-right: 80px;
        }
    </style>
</head>

<body>
    <%@ include file="/header.jsp"  %>
    <div class="main-container">
        <div class="header-container">
            <h1>여행계획리스트</h1>
        </div>
        <div class="add-button-container d-flex justify-content-end">
            <button id="list-add-btn" type="button" class="btn btn-primary">
                + 추가
            </button>
        </div>

        <div class="row">
            <!-- 첫 번째 카드 -->
            <div class="col-md-4">
                <div class="card">
                    <img src="<%= request.getContextPath() %>/assets/map.PNG" class="card-img-top" alt="Map Image">
                    <div class="card-body">
                        <h5 class="card-title">2박 3일 강릉 여행</h5>
                        <p class="card-text card-price">230,000원</p>
                        <p class="card-text card-distance">230km</p>
                    </div>
                </div>
            </div>

            <!-- 두 번째 카드 -->
            <div class="col-md-4">
                <div class="card">
                    <img src="<%= request.getContextPath() %>/assets/map.PNG" class="card-img-top" alt="Map Image">
                    <div class="card-body">
                        <h5 class="card-title">3박 4일 제주도 여행</h5>
                        <p class="card-text card-price">350,000원</p>
                        <p class="card-text card-distance">450km</p>
                    </div>
                </div>
            </div>

            <!-- 세 번째 카드 -->
            <div class="col-md-4">
                <div class="card">
                    <img src="<%= request.getContextPath() %>/assets/map.PNG" class="card-img-top" alt="Map Image">
                    <div class="card-body">
                        <h5 class="card-title">1박 2일 부산 여행</h5>
                        <p class="card-text card-price">150,000원</p>
                        <p class="card-text card-distance">300km</p>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
    <script>
        const listAddBtn = document.querySelector("#list-add-btn")
        listAddBtn.addEventListener("click", function(event) {
            location.href="plan.jsp";
        })
        document
                .getElementById("tripInfoShare")
                .addEventListener("click", function () {
                    window.open("./list.html", "_self");
                });
    </script>
    <%@ include file="/footer.jsp"%>
</body>

</html>