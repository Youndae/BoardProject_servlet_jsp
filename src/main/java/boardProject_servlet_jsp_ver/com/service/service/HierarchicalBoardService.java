package boardProject_servlet_jsp_ver.com.service.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import boardProject_servlet_jsp_ver.com.domain.dto.Criteria;
import boardProject_servlet_jsp_ver.com.domain.dto.PageDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.hierarchicalBoard.HierarchicalBoardDetailDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.hierarchicalBoard.HierarchicalBoardModifyDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.hierarchicalBoard.HierarchicalBoardReplyDTO;
import boardProject_servlet_jsp_ver.com.domain.entity.HierarchicalBoard;

public interface HierarchicalBoardService {
	
	public List<HierarchicalBoard> boardList(HttpServletRequest request, HttpServletResponse response);
	
	public PageDTO setPageDTO(HttpServletRequest request);
	
	public long boardInsert(HttpServletRequest request, HttpServletResponse response);
	
	public HierarchicalBoardDetailDTO boardDetail(HttpServletRequest request, HttpServletResponse response);
	
	public String boardDelete(HttpServletRequest request, HttpServletResponse response);
	
	public HierarchicalBoardModifyDTO getBoardModify(HttpServletRequest request, HttpServletResponse response);
	
	public long boardModify(HttpServletRequest request, HttpServletResponse response);
	
	public HierarchicalBoardReplyDTO getBoardReply(HttpServletRequest requset, HttpServletResponse response);
	
	public long boardReply(HttpServletRequest requset, HttpServletResponse response);
}
