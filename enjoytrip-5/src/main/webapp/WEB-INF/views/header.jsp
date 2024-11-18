
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> --%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:set var="root" value="${pageContext.request.contextPath}" />


<c:if test="${cookie.ssafy_id.value ne null}">

	<c:set var="idck" value="checked" />
	<c:set var="saveid" value="${cookie.ssafy_id.value}" />

</c:if>
<c:out value="${cookie.ssafy_id.value}" />
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
	crossorigin="anonymous" />
<link href="${root}/assets/css/app.css" rel="stylesheet" />
<title>SSAFY</title>
</head>
<body>
	<!-- 상단 navbar start -->
	<nav
		class="navbar navbar-expand-lg navbar-light bg-light shadow fixed-top">
		<div class="container">
			<a class="navbar-brand text-primary fw-bold" href="${root }/">
				ENJOY TRIP </a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
				aria-controls="navbarSupportedContent" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul class="navbar-nav me-auto mb-lg-0">
					<li class="nav-item"><a class="nav-link" aria-current="page"
						href="#">공지사항</a></li>

				</ul>
				<!-- 로그인 전 -->
				<c:if test="${empty userinfo}">
					<ul class="navbar-nav mb-2 me-2 mb-lg-0">
						<li class="nav-item"><a class="nav-link" aria-current="page"
							href="#" data-bs-toggle="modal" data-bs-target="#signupmodal">회원가입</a>
						</li>
						<li class="nav-item"><a class="nav-link" aria-current="page"
							href="#" data-bs-toggle="modal" data-bs-target="#loginmodal">로그인</a>
						</li>
					</ul>
				</c:if>
				<c:if test="${not empty userinfo}">
					<!-- 로그인 후 -->
					<ul class="navbar-nav mb-2 me-2 mb-lg-0">
						<li class="nav-item"><a class="nav-link" aria-current="page"
							href="${root}/trip/">관광지정보조회</a>
						</li>
						<li class="nav-item"><a class="nav-link" aria-current="page"
							href="${root}/trip/planlist">나만의
								여행계획</a></li>
						<li class="nav-item"><a class="nav-link" aria-current="page"
							href="${root}/trip/hotplace">핫플자랑하기</a>
						</li>
						<li class="nav-item"><a class="nav-link" aria-current="page"
							href="${root}/article/list?pgno=1&key=&word=">게시판</a></li>


						<li class="nav-item"><a class="nav-link" aria-current="page"
							href="${root }/mypage/">마이페이지</a>
						</li>
						<li class="nav-item"><a class="nav-link" aria-current="page"
							href="${root }/user/logout" id="logout">로그아웃</a></li>
					</ul>
				</c:if>
			</div>
		</div>
	</nav>
	<!-- 상단 navbar end -->

	<!-- 로그인 Modal -->
	<div class="modal fade" id="loginmodal" data-bs-backdrop="static"
		data-bs-keyboard="false" tabindex="-1"
		aria-labelledby="staticBackdropLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<!-- Modal Header -->
				<div class="modal-header">
					<h5 class="modal-title">로그인</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>

				<!-- Modal body -->
				<div class="modal-body">
					<form id="form-login" method="POST" action="">
						<div class="row mb-3 mt-3">
							<div class="col-md-6" style="margin: 0 auto;">
								<div style="display: flex; justify-content: space-between;">
									<label for="login-id" class="form-label" style="width: 75px;">아이디
										:</label>
									<div>

										<input type="checkbox" value="ok" id="saveid" name="saveid"
											${idck}> 아이디저장
									</div>
								</div>
								<input type="text" class="form-control" id="userid"
									name="userid" value="${saveid}" required />
							</div>
						</div>
						<div class="row mb-3 mt-3">
							<div class="col-md-6" style="margin: 0 auto;">
								<label for="login-pw" class="form-label" style="width: 100px;">비밀번호
									:</label> <input type="password" class="form-control" id="userpwd"
									name="userpwd" required />
							</div>
						</div>
					</form>
				</div>

				<!-- Modal footer -->
				<div class="modal-footer" style="margin-right: 0;">
					<button type="button" id="btn-login"
						class="btn btn-outline-primary btn-sm">로그인</button>

					<button type="button" class="btn btn-outline-dark btn-sm"
						data-bs-dismiss="modal">비밀번호찾기</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 회원가입 Modal -->
	<div class="modal fade" id="signupmodal" data-bs-backdrop="static"
		data-bs-keyboard="false" tabindex="-1"
		aria-labelledby="staticBackdropLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<!-- Modal Header -->
				<div class="modal-header">
					<h4 class="modal-title">
						<i class="bi bi-chat-left-dots-fill text-info"> 회원가입</i>
					</h4>
					<button type="button" class="btn-close" data-bs-dismiss="modal"></button>
				</div>

				<!-- Modal body -->
				<div class="modal-body">
					<form id="form-join" method="POST" action="">
						<input type="hidden" name="action" value="join" />
						<div class="row mb-3 mt-3">
							<div class="col-md-6"
								style="display: flex; margin: 0 auto; flex-direction: column;">
								<label for="signup-name" class="form-label" style="width: 75px;">이름
									:</label> <input type="text" class="form-control" id="username"
									name="userName" placeholder="이름..." required />
							</div>
						</div>
						<div class="row mb-3 mt-3">
							<div class="col-md-6"
								style="display: flex; margin: 0 auto; flex-direction: column;">
								<label for="userid2" class="form-label" style="width: 100px;">아이디
									:</label> <input type="text" class="form-control" id="userid2"
									name="userId" placeholder="아이디..." required />
							</div>
							<div id="result-view" class="mb-3"></div>
						</div>
						<div class="row mb-3 mt-3">
							<div class="col-md-6"
								style="display: flex; margin: 0 auto; flex-direction: column;">
								<label for="userpwd2" class="form-label" style="width: 100px;">비밀번호
									:</label> <input type="password" class="form-control" id="userpwd2"
									name="userPwd" placeholder="비밀번호..." required />
							</div>
						</div>
						<div class="row mb-3 mt-3">
							<div class="col-md-6"
								style="display: flex; margin: 0 auto; flex-direction: column;">
								<label for="pwdcheck" class="form-label" style="width: 170px;">비밀번호확인
									:</label> <input type="password" class="form-control" id="pwdcheck"
									name="pwdcheck" placeholder="비밀번호확인..." required />
							</div>
						</div>
						<div class="row mb-3 mt-3">
							<div class="col-md-6"
								style="display: flex; margin: 0 auto; flex-direction: column;">
								<label for="signup-email" class="form-label"
									style="width: 100px;">이메일 :</label>
								<div class="input-group">
									<input type="text" class="form-control" id="useremail"
										name="userMail" placeholder="이메일아이디" aria-label="이메일 아이디"
										required /> <span class="input-group-text">@</span> <select
										class="form-select" id="userdomain" name="userDomain"
										aria-label="이메일 도메인">
										<option value="gmail.com" selected>gmail.com</option>
										<option value="naver.com">naver.com</option>
									</select>
								</div>
							</div>
						</div>
						<div class="row mb-3 mt-3">
							<div class="col-md-6"
								style="display: flex; margin: 0 auto; flex-direction: column;">
								<label for="region" class="form-label" style="width: 200px;">지역
									:</label>
								<div class="d-flex">
									<select class="form-select me-2" id="province" name="province"
										aria-label="시도 선택">
										<option selected>시도선택</option>
										<option value="대구">대구</option>
										<option value="서울">서울</option>
										<option value="경북">경북</option>
									</select> <select class="form-select" id="district" name="district"
										aria-label="구군 선택">
										<option selected>구군선택</option>
										<option value="동구">동구</option>
										<option value="중구">중구</option>
										<option value="서구">서구</option>
									</select>
								</div>
							</div>
						</div>
					</form>
				</div>

				<!-- Modal footer -->
				<div class="modal-footer">
					<button type="button" id="btn-join" class="btn btn-primary btn-sm">
						회원가입</button>
					<button type="button" class="btn btn-outline-success btn-sm"
						onclick="resetForm()">초기화</button>
					<button type="button" class="btn btn-outline-dark btn-sm"
						data-bs-dismiss="modal">닫기</button>
				</div>
			</div>
		</div>
	</div>

	<!--script-->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
		crossorigin="anonymous"></script>
	<script>

	//회원가입
	
	
	let isUseId = false;
let input = document.querySelector("#userid2");
console.log(input);
let resultDiv = document.querySelector("#result-view");

input.addEventListener("keyup", function () {
    let checkid = input.value;
    let len = checkid.length;
    
    if (len < 4 || len > 16) {
        isUseId = false;
        resultDiv.setAttribute("class", "mb-3 fw-bold text-dark");
        resultDiv.innerHTML = "아이디는 4자이상 16자이하입니다.";
    } else {
    	let url = "/user/" + checkid;
         
        fetch(url)
            .then((response) => response.json())
            .then(data => resultViewJSON(data));
    }
});

function resultViewText(data) {
    let val = data.split(",");
    let id = val[0];
    let cnt = val[1];
    if (cnt == 0) {
        isUseId = true;
        resultDiv.setAttribute("class", "mb-3 text-success");
        resultDiv.innerHTML = "<span class='fw-bold'>" + id + "</span>은 사용할 수 있습니다.";
    } else {
        isUseId = false;
        resultDiv.setAttribute("class", "mb-3 text-danger");
        resultDiv.innerHTML = "<span class='fw-bold'>" + id + "</span>은 사용할 수 없습니다.";
    }
}

function resultViewJSON(data) {
	console.log(data);
    if (data.cnt == 0) {
        isUseId = true;
        resultDiv.setAttribute("class", "mb-3 text-primary");
        resultDiv.innerHTML = "<span class='fw-bold'>" + data.checkid + "</span>은 사용할 수 있습니다.";
    } else {
        isUseId = false;
        resultDiv.setAttribute("class", "mb-3 text-warning");
        resultDiv.innerHTML = "<span class='fw-bold'>" + data.checkid + "</span>은 사용할 수 없습니다.";
    }
}


      
	document.querySelector("#btn-join").addEventListener("click", function () {
        if (!document.querySelector("#username").value) {
          alert("이름 입력!!");
          return;
        } else if (!document.querySelector("#userid2").value) {
          alert("아이디 입력!!");
          return;
        } else if (!isUseId) {
          alert("아이디 중복 확인!!");
          return;
        } else if (!document.querySelector("#userpwd2").value) {
          alert("비밀번호 입력!!");
          return;
        } else if (
          document.querySelector("#userpwd2").value != document.querySelector("#pwdcheck").value
        ) {
          alert("비밀번호 확인!!");
          return;
        } else if(!document.querySelector("#useremail").value) {
        	alert("비밀번호 입력!!");
            return;
		}
        else {
          let form = document.querySelector("#form-join");
          form.setAttribute("action", "${root}/user/join");
          form.submit();
        }
      });
	
	//로그인
	document.querySelector("#btn-login").addEventListener("click", function () {
		if (!document.querySelector("#userid").value) {
			alert("아이디 입력1111!!");
	      	return;
	    } else if (!document.querySelector("#userpwd").value) {
			alert("비밀번호 입력!!");
			return;
	    } else {
			let form = document.querySelector("#form-login");
			form.setAttribute("action", "${root}/user/login");
			form.submit();
	    }
	});
    /* const signup = document.querySelector("#signupmodal");
    const signupBtn = document.querySelector("#btn-signup");
    const logoutBtn = document.querySelector("#logout");
    const loginBtn = document.querySelector("#btn-login");

    
    signupBtn.addEventListener('click', function () {
        alert('회원가입 되었습니다!');
        const modal = bootstrap.Modal.getInstance(document.getElementById('signupmodal'));
        modal.hide(); // Close the modal
    });

    function resetForm() {
        const form = document.querySelector('#signupmodal form');
        form.reset();
        const emailDomain = document.getElementById('email-domain');
        emailDomain.selectedIndex = 0;
        const regionSelects = document.querySelectorAll('select');
        regionSelects.forEach(select => {
            select.selectedIndex = 0;
        });
    }

    logoutBtn.addEventListener('click', function () {
        alert('로그아웃 되었습니다!');
    });

    loginBtn.addEventListener('click', function () {
        alert('로그인 되었습니다!');
        const modal = bootstrap.Modal.getInstance(document.getElementById('loginmodal'));
        modal.hide(); // Close the modal
    }); */
</script>

</body>