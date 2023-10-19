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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/paging.js"></script>
<body>
<div class="container">
	<div>
		<jsp:include page="/WEB-INF/views/navbar.jsp" flush="false"/>
	</div>
	
	<div class="wrapper">
		<div class="header">
			<h1>사진 첨부</h1>
		</div>
		<div class="body">
			<form id="uploadForm">
				<div>
					<label for="imageTitle">제목</label>
					<input type="text" id="imageTitle" name="imageTitle" placeholder="제목을 입력하세요">
				</div>
				<div>
					<label for="imageContent">내용</label>
					<textarea id="imageContent" name="imageContent" placeholder="사진을 설명해주세요" style="width: 300px; height: 300px;"></textarea>
				</div>
			</form>
			<div id="attach">
				<input type="file" id="uploadInputBox" name="fileData" value="사진 첨부" multiple />
			</div>
			<div class="content" id="preivew"></div>
		</div>
		<div class="footer">
			<button class="btn btn-outline-info btn-sm" id="imageInsert">등록</button>
		</div>
	</div>
</div>

<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx" crossorigin="anonymous"></script>
</body>
</html>