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
	<form method="post" id="replyFrm">
		<div>
			<label for="boardTitle">제목</label>
			<input type="text" id="boardTitle" name="boardTitle" placeholder="제목을 입력해주세요">
		</div>
		<div class="mb-4">
			<label for="boardContent">내용</label>
			<textarea id="boardContent" name="boardContent" placeholder="내용을 입력해주세요" style="width: 300px; height: 300px;">
			</textarea>
		</div>
		<button class="btn btn-outline-info btn-sm" id="replyProc" type="button">등록</button>
		<input type="hidden" name="boardNo" value="${boardReply.boardNo}">
		<input type="hidden" name="boardGroupNo" value="${boardReply.boardGroupNo}">
		<input type="hidden" name="boardIndent" value="${boardReply.boardIndent}">
		<input type="hidden" name="boardUpperNo" value="${boardReply.boardUpperNo}">
	</form>
</div>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx" crossorigin="anonymous"></script>
</body>
</html>