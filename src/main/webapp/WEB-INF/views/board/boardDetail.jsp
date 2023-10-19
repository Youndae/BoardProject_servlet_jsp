<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
</head>
<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/hierarchicalBoard.js"></script>
<body>
<div class="container">
	<div>
		<jsp:include page="/WEB-INF/views/navbar.jsp" flush="false"/>
	</div>
	<div>
		<div class="form-row float-right mb-3">
			<c:if test="${sessionScope.id ne null}">
				<button class="btn btn-outline-info btn-sm" id="reply">답글</button>
			</c:if>
			<c:if test="${sessionScope.id eq boardDetail.userId}">
				<button class="btn btn-outline-info btn-sm" id="modify">수정</button>
				<button class="btn btn-outline-info btn-sm" id="deleteBoard">삭제</button>
			</c:if>
		</div>
		<div class="form-group">
			<label>제목</label>
			<p>${boardDetail.boardTitle}</p>
		</div>
		<div class="form-group">
			<label>작성자</label>
			<p>${boardDetail.userId}</p>
		</div>
		<div class="form-group">
			<label>작성일</label>
			<p><c:out value="${boardDetail.boardDate}"/></p>
		</div>
		<div class="form-group">
			<label>내용</label>
			<p>${boardDetail.boardContent}</p>
		</div>
	</div>
	
	<input type="hidden" id="boardNo" name="boardNo" value="${boardDetail.boardNo}">
	
	<div>
		<jsp:include page="/WEB-INF/views/comment.jsp" flush="false"/>
	</div>
</div>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx" crossorigin="anonymous"></script>
</body>
</html>