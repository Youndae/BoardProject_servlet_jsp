package boardProject_servlet_jsp_ver.com.controller.hierarchicalBoard;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import boardProject_servlet_jsp_ver.com.domain.ViewPathProperties;
import boardProject_servlet_jsp_ver.com.domain.dto.Criteria;
import boardProject_servlet_jsp_ver.com.domain.dto.PageDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.comment.CommentDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.hierarchicalBoard.HierarchicalBoardDetailDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.hierarchicalBoard.HierarchicalBoardModifyDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.hierarchicalBoard.HierarchicalBoardReplyDTO;
import boardProject_servlet_jsp_ver.com.domain.entity.Comment;
import boardProject_servlet_jsp_ver.com.domain.entity.HierarchicalBoard;
import boardProject_servlet_jsp_ver.com.jdbc.JDBCTemplate;
import boardProject_servlet_jsp_ver.com.service.dao.HierarchicalBoardDao;
import boardProject_servlet_jsp_ver.com.service.dao.HierarchicalBoardDaoImpl;
import boardProject_servlet_jsp_ver.com.service.service.CommentService;
import boardProject_servlet_jsp_ver.com.service.service.CommentServiceImpl;
import boardProject_servlet_jsp_ver.com.service.service.HierarchicalBoardService;
import boardProject_servlet_jsp_ver.com.service.service.HierarchicalBoardServiceImpl;



/**
 * HierarchicalBoardServlet은 하나의 서블릿으로 모든 CRUD를 처리.
 * ImageBoardServlet은 각각의 서블릿으로 CRUD를 처리.
 */

@WebServlet(urlPatterns = "/board/*")
public class HierarchicalBoardServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private HierarchicalBoardService boardService = new HierarchicalBoardServiceImpl();
	

	//boardDelete
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		System.out.println("doDelete");
		
		String result = boardService.boardDelete(req, resp);
		
		if(result == null) { 
			RequestDispatcher dispatcher = req.getRequestDispatcher(ViewPathProperties.accessErrorViewPath);
			dispatcher.forward(req, resp);
		}else {
			PrintWriter out = resp.getWriter();
			out.print(1);
		}
			
		
	}

	//boardList
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		RequestDispatcher dispatcher;
		
		List<HierarchicalBoard> resultList = boardService.boardList(req, resp);
		 
		if(resultList == null) {
			req.setAttribute("list", null);
			dispatcher = req.getRequestDispatcher(ViewPathProperties.accessErrorViewPath);
		}else {
			req.setAttribute("list", resultList);
			req.setAttribute("pageMaker", boardService.setPageDTO(req));
			dispatcher = req.getRequestDispatcher(ViewPathProperties.hierarchicalViewpath + "boardList.jsp");
		}
		
		
		dispatcher.forward(req, resp);
	}

	//boardInsert
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		RequestDispatcher dispatcher;
		
		long result = boardService.boardInsert(req, resp);
		
		if(result == 0) {
			dispatcher = req.getRequestDispatcher(ViewPathProperties.accessErrorViewPath);
			dispatcher.forward(req, resp);
		}else 
			resp.sendRedirect("/board/boardDetail?boardNo=" + result);
		
	}

	//boardModify
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		RequestDispatcher dispatcher;
		
		long result = boardService.boardModify(req, resp);
		
		if(result == 0) {
			dispatcher = req.getRequestDispatcher(ViewPathProperties.accessErrorViewPath);
			dispatcher.forward(req, resp);
		}else 
			resp.sendRedirect("/board/boardDetail?boardNo=" + result);
		
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String uri = req.getRequestURI();
		String path = uri.substring(uri.lastIndexOf("/"));
		
		if(path.equals("/boardList")) {
			doGet(req, resp);
		}else if(path.equals("/boardDetail")) {
			doGetDetail(req, resp);
		}else if(path.equals("/boardModifyProc")) {
			doPut(req, resp);
		}else if(path.equals("/boardDelete")) {
			doDelete(req, resp);
		}else if(path.equals("/boardInsertProc")) {
			doPost(req, resp);
		}else if(path.equals("/boardInsert")) {
			doGetInsert(req, resp);
		}else if(path.equals("/boardModify")) {
			doGetModify(req, resp);
		}else if(path.equals("/boardReply")) {
			doGetReply(req, resp);
		}else if(path.equals("/boardReplyProc")) {
			doPostReply(req, resp);
		}
		
		
		
		
		
	}
	
	
	//replyProc
	private void doPostReply(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		RequestDispatcher dispatcher;
		
		long result = boardService.boardReply(req, resp);
		
		if(result == 0) {
			dispatcher = req.getRequestDispatcher(ViewPathProperties.accessErrorViewPath);
			dispatcher.forward(req, resp);
		}else 
			resp.sendRedirect("/board/boardDetail?boardNo=" + result);
		
	}

	//reply
	private void doGetReply(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HierarchicalBoardReplyDTO dto = boardService.getBoardReply(req, resp);
		RequestDispatcher dispatcher;
		
		if(dto == null) {
			dispatcher = req.getRequestDispatcher(ViewPathProperties.accessErrorViewPath);
			dispatcher.forward(req, resp);
		}else {
			dispatcher = req.getRequestDispatcher(ViewPathProperties.hierarchicalViewpath + "boardReply.jsp");
			
			req.setAttribute("boardReply", dto);
			
			dispatcher.forward(req, resp);
		}
		
	}

	//modify
	private void doGetModify(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HierarchicalBoardModifyDTO dto = boardService.getBoardModify(req, resp);
		RequestDispatcher dispatcher;
		
		System.out.println("modifyDTO : " + dto);
		
		if(dto == null) {
			dispatcher = req.getRequestDispatcher(ViewPathProperties.accessErrorViewPath);
			dispatcher.forward(req, resp);
		}else {
			dispatcher = req.getRequestDispatcher(ViewPathProperties.hierarchicalViewpath + "boardModify.jsp");
			
			req.setAttribute("boardModify", dto);
			
			dispatcher.forward(req, resp);
		}
		
	}

	private void doGetInsert(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		HttpSession session = req.getSession();
		String uid = (String) session.getAttribute("id");
		RequestDispatcher dispatcher;
		
		
		if(uid == null) {
			dispatcher = req.getRequestDispatcher(ViewPathProperties.accessErrorViewPath);
			dispatcher.forward(req, resp);
		}else {
			String viewPath = ViewPathProperties.hierarchicalViewpath + "boardInsert.jsp";
			dispatcher = req.getRequestDispatcher(viewPath);
			
			dispatcher.forward(req, resp);
		}
		
	}

	protected void doGetDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
				
		String viewPath = ViewPathProperties.hierarchicalViewpath + "/boardDetail.jsp";
		RequestDispatcher dispatcher = req.getRequestDispatcher(viewPath);
		
		HierarchicalBoardDetailDTO boardDetail = boardService.boardDetail(req, resp);
		
		req.setAttribute("boardDetail", boardDetail);
		
		dispatcher.forward(req, resp);
	}
	
	
}
