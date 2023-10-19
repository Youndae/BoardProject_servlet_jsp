package boardProject_servlet_jsp_ver.com.controller.imageBoard;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import boardProject_servlet_jsp_ver.com.domain.ResultProperties;
import boardProject_servlet_jsp_ver.com.domain.ViewPathProperties;
import boardProject_servlet_jsp_ver.com.service.service.ImageBoardService;
import boardProject_servlet_jsp_ver.com.service.service.ImageBoardServiceImpl;

@WebServlet(urlPatterns = "/imageBoard/imageInsert")
public class imageBoardInsertServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	private ImageBoardService imageBoardService = new ImageBoardServiceImpl();

	//get insert
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		String uid = (String) session.getAttribute("id");
		RequestDispatcher dispatcher;
		
		if(uid == null) 
			dispatcher = req.getRequestDispatcher(ViewPathProperties.accessErrorViewPath);
		else
			dispatcher = req.getRequestDispatcher(ViewPathProperties.imageViewPath + "imageInsert.jsp");
		
		dispatcher.forward(req, resp);
		
	}
	
	//post insert
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		long result = imageBoardService.insert(req);
		PrintWriter out = resp.getWriter();	
		out.print(result);
	}
	
	
	

}
