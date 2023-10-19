package boardProject_servlet_jsp_ver.com.service.service;



import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;

import boardProject_servlet_jsp_ver.com.domain.dto.Criteria;
import boardProject_servlet_jsp_ver.com.domain.dto.PageDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.comment.CommentDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.comment.CommentDeleteDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.comment.CommentInsertDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.comment.CommentReplyDTO;
import boardProject_servlet_jsp_ver.com.service.dao.CommentDao;
import boardProject_servlet_jsp_ver.com.service.dao.CommentDaoImpl;

public class CommentServiceImpl implements CommentService{
	
	private CommentDao commentDAO = new CommentDaoImpl();

	@Override
	public CommentDTO getBoardComment(HttpServletRequest request) {
		
		Criteria cri = new Criteria();
		
		String pageNum = request.getParameter("pageNum");
		
		if(pageNum != null)
			cri.setPageNum(Integer.parseInt(pageNum));
		
		long boardNo = Long.parseLong(request.getParameter("boardNo"));
		
		HttpSession session = request.getSession();
		
		CommentDTO dto = new CommentDTO.CommentDTOBuilder()
				.commentList(commentDAO.getBoardComment(boardNo, cri))
				.pageDTO(new PageDTO(cri, (int) Math.ceil((commentDAO.commentListCount(boardNo, 0) * 1.0) / cri.getBoardAmount())))
				.uid(session.getId())
				.build();
		
		return dto;
	}

	@Override
	public CommentDTO getImageComment(HttpServletRequest request) {
		Criteria cri = new Criteria();
		String pageNum = request.getParameter("pageNum");
		
		if(pageNum != null) 
			cri.setPageNum(Integer.parseInt(pageNum));
		
		
		long imageNo = Long.parseLong(request.getParameter("imageNo"));
		
		HttpSession session = request.getSession();
		
		CommentDTO dto = new CommentDTO.CommentDTOBuilder()
				.commentList(commentDAO.getImageComment(imageNo, cri))
				.pageDTO(new PageDTO(cri, (int) Math.ceil((commentDAO.commentListCount(0, imageNo) * 1.0) / cri.getBoardAmount())))
				.uid(session.getId())
				.build();
		
		return dto;
		
	}

	@Override
	public String commentInsert(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		String uid = (String) session.getAttribute("id");
		CommentInsertDTO dto = new CommentInsertDTO();
		
		if(request.getParameter("boardNo") == null) {
			dto = new CommentInsertDTO.CommentInsertDTOBuilder()
					.commentContent(request.getParameter("commentContent"))
					.userId(uid)
					.commentIndent(0)
					.imageNo(Long.parseLong(request.getParameter("imageNo")))
					.build();
		}else if(request.getParameter("boardNo") != null) {
			dto = new CommentInsertDTO.CommentInsertDTOBuilder()
					.commentContent(request.getParameter("commentContent"))
					.userId(uid)
					.commentIndent(0)
					.boardNo(Long.parseLong(request.getParameter("boardNo")))
					.build();
		}

		return commentDAO.commentInsert(dto);
	}

	@Override
	public String commentReply(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		String uid = (String) session.getAttribute("id");
		CommentReplyDTO dto = new CommentReplyDTO();
		
		System.out.println("content : " + request.getParameter("commentContent"));
		System.out.println("indent : " + request.getParameter("commentIndent"));
		
		if(request.getParameter("boardNo") == null) {
			dto = new CommentReplyDTO.CommentReplyDTOBuilder()
					.commentContent(request.getParameter("commentContent"))
					.userId(uid)
					.commentIndent(Integer.parseInt(request.getParameter("commentIndent")) + 1)
					.commentGroupNo(Long.parseLong(request.getParameter("commentGroupNo")))
					.commentUpperNo(request.getParameter("commentUpperNo"))
					.imageNo(Long.parseLong(request.getParameter("imageNo")))
					.build();
		}else if(request.getParameter("boardNo") != null) {
			dto = new CommentReplyDTO.CommentReplyDTOBuilder()
					.commentContent(request.getParameter("commentContent"))
					.userId(uid)
					.commentIndent(Integer.parseInt(request.getParameter("commentIndent")) + 1)
					.commentGroupNo(Long.parseLong(request.getParameter("commentGroupNo")))
					.commentUpperNo(request.getParameter("commentUpperNo") + ",")
					.boardNo(Long.parseLong(request.getParameter("boardNo")))
					.build();
		}
		
		return commentDAO.commentReply(dto);
	}

	@Override
	public String commentDelete(HttpServletRequest request) {
		long commentNo = Long.parseLong(request.getParameter("commentNo"));
		HttpSession session = request.getSession();
		String uid = (String) session.getAttribute("id");
		
		//해당 comment indent 체크 후 0이라면 같은 commentGroupNo 전체 삭제
		
		//indent가 0이 아니라면 같은 groupNo의 데이터 조회
		
		//group 데이터 중에서 upperNo의 배열 중 indent의 위치에 commentNo가 존재한다면 list에 add
		
		//list를 전송해 삭제 요청.
		
		
		if(!commentDAO.checkWriter(commentNo).equals(uid))
			return null;
		
		CommentDeleteDTO deleteData = commentDAO.checkDeleteNo(commentNo);
		
		if(deleteData.getCommentIndent() == 0)
			commentDAO.commentDeleteGroup(commentNo);
		else {
			List<CommentDeleteDTO> deleteGroupDTO = commentDAO.getCommentGroup(deleteData.getCommentGroupNo());
			
			List<Long> deleteNoList = new ArrayList<Long>();
			
			getDeleteCommentData(deleteGroupDTO, commentNo, deleteData.getCommentIndent(), deleteNoList);
			
			commentDAO.commentDelete(deleteNoList);
		}
		
		
		return "success";
	}
	
	public void getDeleteCommentData(List<CommentDeleteDTO> deleteGroupDTO, long commentNo
										, int commentIndent, List<Long> deleteNoList) {
	
		for(int i = 0; i < deleteGroupDTO.size(); i++) {
			String upperNo = deleteGroupDTO.get(i).getCommentUpperNo();
			String[] upperArr = upperNo.split(",");
			
			if(upperArr.length > commentIndent && commentNo == Long.parseLong(upperArr[commentIndent])) 
				deleteNoList.add(deleteGroupDTO.get(i).getCommentNo());
			
		}		
	}
	
	

}
