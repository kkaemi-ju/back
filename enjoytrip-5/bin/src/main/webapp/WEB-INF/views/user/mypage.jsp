<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<%@ include file="/WEB-INF/views/header.jsp" %>


<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>My Page</title>
<!-- Bootstrap CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
    rel="stylesheet"
    integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
    crossorigin="anonymous" />
<link rel="stylesheet"
    href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" />
<style>
body {
    background-color: #f0f8ff;
    font-family: Arial, sans-serif;
}

.main-container {
    margin-top: 100px;
}

.profile-img {
    width: 150px;
    height: 150px;
    object-fit: cover;
    border-radius: 50%;
}

.profile-section {
    background-color: #ffffff;
    padding: 20px;
    border-radius: 10px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.btn-custom {
    background-color: #007bff;
    color: white;
    border-radius: 20px;
}

.btn-custom:hover {
    background-color: #0056b3;
}

.input-group-text {
    background-color: #007bff;
    color: white;
}

.form-group {
    margin-top: 15px;
}

.action-buttons {
    margin-top: 20px;
    display: flex;
    justify-content: flex-end;
}

.action-buttons .btn {
    margin-left: 10px;
}
</style>
</head>

<body>
    <div class="main-container">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="profile-section">
                    <form id="mypageForm" action="" method="POST">
                        <div class="row">
                            <!-- Profile Image -->
                            <div class="col-md-4 text-center">
                                <img src="<%= request.getContextPath() %>/assets/cat2.jpg" alt="Profile Image" class="profile-img"> <br>
                                <br>
                                <button type="button" class="btn btn-custom">사진 변경</button>
                            </div>

                            <!-- Profile Information -->
                            <div class="col-md-8">
                                <div class="form-group">
                                    <label for="username">이름</label> 
                                    <input type="text" class="form-control" id="username" name="username" value="${mypageinfo.userName }">
                                </div>
                                <div class="form-group">
                                    <label for="userId">아이디</label> 
                                    <input type="text" class="form-control" id="userId" name="userId" value="${mypageinfo.userId }" readonly>
                                </div>
                                <div class="form-group">
                                    <label for="password">패스워드</label> 
                                    <input type="password" class="form-control" id="password" name="password" value="${mypageinfo.userPwd }">
                                </div>
                                <div class="form-group">
                                    <button type="button" class="btn btn-custom" id="change-password-btn">패스워드 변경</button>
                                </div>
                                <div class="form-group">
                                    <label for="email">이메일</label> 
                                    <input type="email" class="form-control" id="email" name="email" value="${mypageinfo.userMail }">
                                </div>
                                <div class="form-group">
                                    <label for="region">지역</label>
                                    <div class="input-group">
                                        <input type="text" class="form-control" id="region-state" name="regionState"
                                            placeholder="시도" value="${mypageinfo.sido }">
                                        <div class="input-group-append">
                                            <span class="input-group-text">/</span>
                                        </div>
                                        <input type="text" class="form-control" id="region-city" name="regionCity"
                                            placeholder="구군" value="${mypageinfo.gugun }">
                                    </div>
                                </div>

                              
                                <div class="action-buttons">
                                    <button type="button" class="btn btn-danger" id="info-delete">삭제</button>
                                    <button type="button" class="btn btn-success" id="info-edit">수정</button>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script>
        document.getElementById('change-password-btn').addEventListener('click', function() {
        	let form = document.querySelector("#mypageForm");
			form.setAttribute("action", "modifypwd");
			form.submit();
        });
        
        document.getElementById('info-delete').addEventListener('click', function() {
        	let form = document.querySelector("#mypageForm");
			form.setAttribute("action", "delete");
			form.submit();
        });
        
        document.getElementById('info-edit').addEventListener('click', function() {
        	let form = document.querySelector("#mypageForm");
			form.setAttribute("action", "update");
			form.submit();
        });
        
        
    </script>
    <script src="<%= request.getContextPath() %>/assets/js/mypage.js"></script>
    <%@ include file="/WEB-INF/views/footer.jsp"%>
</body>
</html>
