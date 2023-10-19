package boardProject_servlet_jsp_ver.com.controller.imageBoard;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.GsonBuilder;

import boardProject_servlet_jsp_ver.com.domain.dto.imageBoard.ImageDataDTO;
import boardProject_servlet_jsp_ver.com.service.service.ImageBoardService;
import boardProject_servlet_jsp_ver.com.service.service.ImageBoardServiceImpl;

@WebServlet(urlPatterns = "/imageBoard/attachList")
public class ImageAttachServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	private ImageBoardService imageBoardService = new ImageBoardServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		List<ImageDataDTO> resultDTO = imageBoardService.getAttachList(req);
		
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
	
		String gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create().toJson(resultDTO);
		
		resp.getWriter().write(gson);
		
	}
	
	

	
}
