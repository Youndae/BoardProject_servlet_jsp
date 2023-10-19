<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/nav.css">
</head>
<body>
	<nav class="navbar navbar-expand navbar-dark bg-dark mb-3">
		<div class="collapse navbar-collapse">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item active">
					<a href="/imageBoard/imageList" class="nav-link">사진
						<span class="sr-only"></span>
					</a>
				</li>
				<li class="nav-item">
					<a href="/board/boardList" class="nav-link">게시판</a>
				</li>
			</ul>
			<div class="form-inline my-2 my-md-0">
				<c:choose>
					<c:when test="${sessionScope.id eq null}">
						<li>
							<a href="/member/login" class="nav-link" style="color:white;">로그인</a>
						</li>
					</c:when>
					<c:otherwise>
						<form id="logoutForm" action="/member/logout" method="post">
							<li>
								<a class="nav-link" style="color: white">
									<button style="all: unset">로그아웃</button>
								</a>
							</li>
						</form>
					</c:otherwise>
				</c:choose>
				
			</div>
		</div>
	</nav>
</body>
</html>