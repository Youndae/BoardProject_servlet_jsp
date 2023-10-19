<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
</head>
<script src="//code.jquery.com/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/comment.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/comment.css"></link>
<body>
	<div class="container">
		<form method="post" id="commentFrm">
			<div>
				<c:choose>
					<c:when test="${sessionScope.id ne null}">
						<input type="text" id="commentContent" name="commentContent" placeholder="댓글을 작성해주세요">
						<button class="btn btn-outline-info btn-sm" id="commentInsert" type="button">작성</button>
					</c:when>
					<c:otherwise>
						<input type="text" id="commentContent" name="commentContent" placeholder="댓글을 작성하시려면 로그인해주세요" disabled="disabled">
						<button class="btn btn-outline-info btn-sm" disabled="disabled">작성</button>
					</c:otherwise>
				</c:choose>
			</div>
		</form>
		
		<div class="comment-area">
			<c:forEach var="clist" items="${boardDetail.commentList}">
				<div id="comment">
					<div class="comment-box" id="comment-box" value="${clist.commentNo}">
						<table class="table table-hover">
							<tr>
								<c:choose>
									<c:when test="${clist.commentIndent == 0}">
										<td>
											<span class="comment_userId">${clist.userId}</span>
											<span class="comment_date"><c:out value="${clist.commentDate}"/></span>
											<c:choose>
												<c:when test="${clist.commentcontent == null}">
													<p class="comment_content_delete">삭제된 댓글입니다.</p>
												</c:when>
												<c:otherwise>
													<p class="comment_content">${clist.commentcontent}</p>
												</c:otherwise>
											</c:choose>
											<c:if test="${sessionScope.id ne null}">
												<button class="btn btn-outline-info btn-sm" type="button" id="cReply" value="${clist.commentNo}" onclick="cReply(this)">답글</button>
												<button class="btn btn-outline-info btn-sm" type="button" id="deleteComment" value="${clist.commentNo}" onclick="delComment(this)">삭제</button>
											</c:if>
										</td>
									</c:when>
									<c:when test="${clist.commentIndent == 1}">
										<td>
											<span class="comment_userId indent_size_1">${clist.userId}</span>
											<span class="comment_date"><c:out value="${clist.commentDate}"/></span>
											<c:choose>
												<c:when test="${clist.commentcontent == null}">
													<p class="comment_content_delete indent_size_1">삭제된 댓글입니다.</p>
												</c:when>
												<c:otherwise>
													<p class="comment_content indent_size_1">${clist.commentcontent}</p>
												</c:otherwise>
											</c:choose>
											<c:if test="${sessionScope.id ne null}">
												<button class="btn btn-outline-info btn-sm" type="button" id="cReply" value="${clist.commentNo}" onclick="cReply(this)">답글</button>
												<button class="btn btn-outline-info btn-sm" type="button" id="deleteComment" value="${clist.commentNo}" onclick="delComment(this)">삭제</button>
											</c:if>
										</td>
									</c:when>
									<c:when test="${clist.commentIndent == 2}">
										<td>
											<span class="comment_userId indent_size_2">${clist.userId}</span>
											<span class="comment_date"><c:out value="${clist.commentDate}"/></span>
											<c:choose>
												<c:when test="${clist.commentcontent == null}">
													<p class="comment_content_delete indent_size_2">삭제된 댓글입니다.</p>
												</c:when>
												<c:otherwise>
													<p class="comment_content indent_size_2">${clist.commentcontent}</p>
												</c:otherwise>
											</c:choose>
											<c:if test="${sessionScope.id ne null}">
												<button class="btn btn-outline-info btn-sm" type="button" id="cReply" value="${clist.commentNo}" onclick="cReply(this)">답글</button>
												<button class="btn btn-outline-info btn-sm" type="button" id="deleteComment" value="${clist.commentNo}" onclick="delComment(this)">삭제</button>
											</c:if>
										</td>
									</c:when>
									<c:when test="${clist.commentIndent == 3}">
										<td>
											<span class="comment_userId indent_size_3">${clist.userId}</span>
											<span class="comment_date"><c:out value="${clist.commentDate}"/></span>
											<c:choose>
												<c:when test="${clist.commentcontent == null}">
													<p class="comment_content_delete indent_size_3">삭제된 댓글입니다.</p>
												</c:when>
												<c:otherwise>
													<p class="comment_content indent_size_3">${clist.commentcontent}</p>
												</c:otherwise>
											</c:choose>
											<c:if test="${sessionScope.id ne null}">
												<button class="btn btn-outline-info btn-sm" type="button" id="cReply" value="${clist.commentNo}" onclick="cReply(this)">답글</button>
												<button class="btn btn-outline-info btn-sm" type="button" id="deleteComment" value="${clist.commentNo}" onclick="delComment(this)">삭제</button>
											</c:if>
										</td>
									</c:when>
									<c:when test="${clist.commentIndent == 4}">
										<td>
											<span class="comment_userId indent_size_4">${clist.userId}</span>
											<span class="comment_date"><c:out value="${clist.commentDate}"/></span>
											<c:choose>
												<c:when test="${clist.commentcontent == null}">
													<p class="comment_content_delete indent_size_4">삭제된 댓글입니다.</p>
												</c:when>
												<c:otherwise>
													<p class="comment_content indent_size_4">${clist.commentcontent}</p>
												</c:otherwise>
											</c:choose>
											<c:if test="${sessionScope.id ne null}">
												<button class="btn btn-outline-info btn-sm" type="button" id="cReply" value="${clist.commentNo}" onclick="cReply(this)">답글</button>
												<button class="btn btn-outline-info btn-sm" type="button" id="deleteComment" value="${clist.commentNo}" onclick="delComment(this)">삭제</button>
											</c:if>
										</td>
									</c:when>
								</c:choose>
								<input type="hidden" id="commentNo" value="${clist.commentNo}">
								<input type="hidden" class="commentGroupNo" value="${clist.commentGroupNo}">
								<input type="hidden" class="commentIndent" value="${clist.commentIndent}">
								<input type="hidden" class="commentUpperNo" value="${clist.commentUpperNo}">
							</tr>
						</table>
					</div>
				</div>
			</c:forEach>
		</div>
		<div class="comment-paging">
		<input type="hidden" name="pagePrev" value="${boardDetail.pageMaker.prev}">
		<input type="hidden" name="pageNext" value="${boardDetail.pageMaker.next}">
		<input type="hidden" name="pagesp" value="${boardDetail.pageMaker.startPage}">
		<input type="hidden" name="pageep" value="${boardDetail.pageMaker.endPage}">
			<ul>
				<c:if test="${boardDetail.pageMaker.prev}">
					<li>
						<a href="#" onclick="commentPaging(${boardDetail.pageMaker.startPage} - 1)">이전</a>
					</li>
				</c:if>
				<c:forEach begin="${boardDetail.pageMaker.startPage}" end="${boardDetail.pageMaker.endPage}" var="idx">
					<li>
						<a href="#" onclick="commentPaging(${idx})">${idx}</a>
					</li>
				</c:forEach>
				<c:if test="${boardDetail.pageMaker.next && boardDetail.pageMaker.endPage > 0}">
					<li>
						<a href="#" onclick="commentPaging(${boardDetail.pageMaker.endPage} + 1)">다음</a>
					</li>
				</c:if>
			</ul>
		</div>
	</div>
</body>
</html>