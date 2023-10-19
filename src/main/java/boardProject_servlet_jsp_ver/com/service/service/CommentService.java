package boardProject_servlet_jsp_ver.com.service.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import boardProject_servlet_jsp_ver.com.domain.dto.Criteria;
import boardProject_servlet_jsp_ver.com.domain.dto.comment.CommentDTO;

public interface CommentService {

	public CommentDTO getBoardComment(HttpServletRequest request);
	
	public CommentDTO getImageComment(HttpServletRequest request);
	
	public String commentInsert(HttpServletRequest request);
	
	public String commentReply(HttpServletRequest request);
	
	public String commentDelete(HttpServletRequest request);
}
