<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
</head>
<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/imageBoard.js"></script>
<body>
<div class="container">
	<div>
		<jsp:include page="/WEB-INF/views/navbar.jsp" flush="false"/>
	</div>
	
	<div class="form-row float-right mb-3">
		<c:choose>
			<c:when test="${sessionScope.id eq boardDetail.userId}">
				<button class="btn btn-outline-info btn-sm" id="modify" type="button">수정</button>
			</c:when>
			<c:when test="${sessionScope.id eq 'admin'}">
				<button class="btn btn-outline-info btn-sm" id="delete" type="button">삭제</button>
			</c:when>
		</c:choose>
	</div>
	
	<div class="form-group">
		<label>제목</label>
		<p>${boardDetail.imageTitle}</p>
	</div>
	<div class="form-group">
		<label>작성자</label>
		<p>${boardDetail.userId}</p>
	</div>
	<div class="form-group">
		<label>작성일</label>
		<p>${boardDetail.imageDate}</p>
	</div>
	<div class="form-group">
		<label>내용</label>
		<div class="mt-4">
			<c:forEach var="image" items="${boardDetail.imageList}">
				<div class="mb-4">
					<img id="boardImg" src="/imageBoard/display?image=${image}" style="width: 300px; height: 300px;"/>
				</div>
			</c:forEach>
		</div>
		<p>${boardDetail.imageContent}</p>
	</div>
	
	<input type="hidden" id="imageNo" name="imageNo" value="${boardDetail.imageNo}">
	<div>
		<jsp:include page="/WEB-INF/views/comment.jsp" flush="false"/>
	</div>
</div>

<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx" crossorigin="anonymous"></script>
</body>
</html>