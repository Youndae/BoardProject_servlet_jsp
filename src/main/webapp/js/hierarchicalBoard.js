$(document).ready(function(){
	
	$("#keywordInput").keydown(function(key){
		if(key.keyCode == 13){
			$("#searchBtn").click();
		}
	})
})


$(function(){
	$("#modify").click(function(){
		var boardNo = $("#boardNo").val();

		location.href = "/board/boardModify?boardNo=" + boardNo;
	})
})

$(function() {
	$("#insertBtn").click(function() {
		location.href = "/board/boardInsert";
	})
})

$(function(){
	$("#deleteBoard").click(function(){
		var boardNo = $("#boardNo").val();

		$.ajax({
			url: '/board/boardDelete?boardNo=' + boardNo,
			method: 'delete',
			success: function(result){
				if(result == 1)
					location.href = "/board/boardList";
			},
			error: function (request, status, error) {
				alert("code:" + request.status + "\n"
					+ "message : " + request.responseText
					+ "\n" + "error : " + error);
			}
		});
	})
})

$(function(){
	$("#reply").click(function(){
		var indent = $("#boardIndent").val();
		if(indent == 4){
			alert("더이상 답글을 작성할 수 없습니다.");
		}else{
			var boardNo = $("#boardNo").val();
			location.href= "/board/boardReply?boardNo=" + boardNo;
		}
	})
})

$(function(){
	$("#insertBoard").click(function(){
		var form = $("#insertBoardFrm");
		
		form.action = "<c:url value='/boardInsertproc'/>";
		form.submit();
	})
})

$(function(){
	$("#modifyProc").click(function(){
		var form = document.getElementById("insertBoardFrm");
		
		form.action = "/board/boardModifyProc";
		form.submit();
	})
})

$(function(){
	$("#replyProc").click(function(){
		var form = document.getElementById("replyFrm");
		
		form.action = "/board/boardReplyProc";
		form.submit();
	})
})


$(function() {
	$('#searchBtn').click(function() {

		var sType = $("select option:selected").val();

		console.log("sType : " + sType);

		$("#searchType").val(sType);
	});
});


$(function(){
	$('.pagination a').on('click', function(e) {
		e.preventDefault();

		var page_form = $('#page_action');

		page_form.find("input[name='pageNum']").val($(this).attr("href"));
		page_form.submit();
	});
})

