package boardProject_servlet_jsp_ver.com.controller.comment;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import boardProject_servlet_jsp_ver.com.domain.dto.Criteria;
import boardProject_servlet_jsp_ver.com.domain.dto.PageDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.comment.CommentDTO;
import boardProject_servlet_jsp_ver.com.domain.entity.Comment;
import boardProject_servlet_jsp_ver.com.jdbc.JDBCTemplate;
import boardProject_servlet_jsp_ver.com.service.service.CommentService;
import boardProject_servlet_jsp_ver.com.service.service.CommentServiceImpl;



@WebServlet(urlPatterns = "/comment/*")
public class CommentServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	private CommentService commentService = new CommentServiceImpl();

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String uri = req.getRequestURI();
		String path = uri.substring(uri.lastIndexOf("/"));
		
		System.out.println("commentServlet");
		
		if(path.equals("/boardComment")) {
			doGetBoardComment(req, resp);
		}else if(path.equals("/imageComment")) {
			doGetImageComment(req, resp);
		}else if(path.equals("/commentInsert")) {
			System.out.println("commentInsert");
			doPostInsertComment(req, resp);
		}else if(path.equals("/commentDelete")) {
			System.out.println("commentDelete");
			doDeleteComment(req, resp);
		}else if(path.equals("/commentReply")) {
			System.out.println("commentReply");
			doPostReplyComment(req, resp);
		}
		
		
	}
	
	protected void doPostReplyComment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String result = commentService.commentReply(req);
		
		PrintWriter out = resp.getWriter();
		out.print(result);
	}
	
	protected void doPostInsertComment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
		String result = commentService.commentInsert(req);
		
		PrintWriter out = resp.getWriter();
		out.print(result);
		
	}
	
	protected void doDeleteComment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
		String result = commentService.commentDelete(req);
		
		PrintWriter out = resp.getWriter();
		out.print(result);
		
	}
	
	
	protected void doGetBoardComment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		CommentDTO dto = commentService.getBoardComment(req);
		
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
	
		String gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create().toJson(dto);
		
		resp.getWriter().write(gson);
		
	}
	
	protected void doGetImageComment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
		CommentDTO dto = commentService.getImageComment(req);
		
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
	
		String gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create().toJson(dto);
		
		resp.getWriter().write(gson);
		
	}

	
	
}
