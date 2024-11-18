<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>SSAFY BOOK CAFE</title>
<link rel="shortcut icon" href="./assets/img/favicon.ico" />
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
	crossorigin="anonymous" />
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" />
<link rel="stylesheet" href="./assets/css/triplist.css" />
<script src="./assets/js/key.js"></script>
</head>
<body>

	<!-- 상단 navbar include -->
	<jsp:include page="header.jsp" />

	<!-- 중앙 content start -->
	<div class="container"
		style="min-height: 100vh; background-image: url(./assets/trip2.jpg); background-size: cover; background-attachment: fixed;">
		<div style="height: 100px"></div>
		<div class="col-md-9" style="margin: 0 auto;">
			<h1 style="display: flex; justify-content: center;">ENJOYTRIP</h1>
		</div>
	</div>

	<!-- 하단 footer include -->
	<jsp:include page="footer.jsp" />

	<!-- Bootstrap JS -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
		crossorigin="anonymous"></script>
	<script src="./assets/js/key.js"></script>

</body>
</html>
