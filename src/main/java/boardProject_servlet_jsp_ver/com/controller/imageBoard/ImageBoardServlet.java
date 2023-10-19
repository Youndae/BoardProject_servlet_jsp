package boardProject_servlet_jsp_ver.com.controller.imageBoard;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import boardProject_servlet_jsp_ver.com.domain.ViewPathProperties;
import boardProject_servlet_jsp_ver.com.domain.dto.PageDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.imageBoard.ImageBoardListDTO;
import boardProject_servlet_jsp_ver.com.service.service.ImageBoardService;
import boardProject_servlet_jsp_ver.com.service.service.ImageBoardServiceImpl;

//list
@WebServlet(urlPatterns = "/imageBoard/imageList")
public class ImageBoardServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	private ImageBoardService imageBoardService = new ImageBoardServiceImpl();

	
	//getList
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		System.out.println("list doGet");
		
		List<ImageBoardListDTO> resultDTO = imageBoardService.boardList(req);
		
		PageDTO pageMaker = imageBoardService.setPageDTO(req);
		
		RequestDispatcher dispatcher;
		
		if(resultDTO == null || pageMaker == null) 
			dispatcher = req.getRequestDispatcher(ViewPathProperties.accessErrorViewPath);
		else {
			req.setAttribute("imageList", resultDTO);
			
			req.setAttribute("pageMaker", pageMaker);
			
			dispatcher = req.getRequestDispatcher(ViewPathProperties.imageViewPath + "imageList.jsp");
		}
		
		dispatcher.forward(req, resp);
		
	}
	
	

}
