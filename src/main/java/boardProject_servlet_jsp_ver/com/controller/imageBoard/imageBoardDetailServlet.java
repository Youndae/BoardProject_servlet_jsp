package boardProject_servlet_jsp_ver.com.controller.imageBoard;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import boardProject_servlet_jsp_ver.com.domain.ViewPathProperties;
import boardProject_servlet_jsp_ver.com.domain.dto.imageBoard.ImageBoardDetailDTO;
import boardProject_servlet_jsp_ver.com.service.service.ImageBoardService;
import boardProject_servlet_jsp_ver.com.service.service.ImageBoardServiceImpl;

@WebServlet(urlPatterns = "/imageBoard/imageDetail")
public class imageBoardDetailServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	private ImageBoardService imageBoardService = new ImageBoardServiceImpl();
	
	//get Detail
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		ImageBoardDetailDTO resultDTO = imageBoardService.getDetail(req);
		
		RequestDispatcher dispatcher;
		
		if(resultDTO == null) 
			dispatcher = req.getRequestDispatcher(ViewPathProperties.accessErrorViewPath);
		else {
			req.setAttribute("boardDetail", resultDTO);
			
			dispatcher = req.getRequestDispatcher(ViewPathProperties.imageViewPath + "imageDetail.jsp");
		}
		
		dispatcher.forward(req, resp);
		
		
	}
	
	
	

}
