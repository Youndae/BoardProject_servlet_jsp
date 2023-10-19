package boardProject_servlet_jsp_ver.com.controller.imageBoard;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import boardProject_servlet_jsp_ver.com.domain.ViewPathProperties;
import boardProject_servlet_jsp_ver.com.domain.dto.imageBoard.ImageBoardModifyGetDTO;
import boardProject_servlet_jsp_ver.com.service.service.ImageBoardService;
import boardProject_servlet_jsp_ver.com.service.service.ImageBoardServiceImpl;

@WebServlet(urlPatterns = "/imageBoard/imageModify")
public class imageBoardModifyServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	private ImageBoardService imageBoardService = new ImageBoardServiceImpl();

	//get Modify
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ImageBoardModifyGetDTO resultDTO = imageBoardService.getModify(req);
		
		RequestDispatcher dispatcher;
		
		if(resultDTO == null)
			dispatcher = req.getRequestDispatcher(ViewPathProperties.accessErrorViewPath);
		else {
			req.setAttribute("list", resultDTO);
			dispatcher = req.getRequestDispatcher(ViewPathProperties.imageViewPath + "imageModify.jsp");
		}
		
		dispatcher.forward(req, resp);
		
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		long result = imageBoardService.modify(req);
		
		PrintWriter out = resp.getWriter();
		out.print(result);
		
	}
	
	
	

}
