package boardProject_servlet_jsp_ver.com.controller.imageBoard;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import boardProject_servlet_jsp_ver.com.service.service.ImageBoardService;
import boardProject_servlet_jsp_ver.com.service.service.ImageBoardServiceImpl;

@WebServlet(urlPatterns = "/imageBoard/imageDelete")
public class ImageBoardDeleteServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	
	private ImageBoardService imageBoardService = new ImageBoardServiceImpl();
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String result = imageBoardService.boardDelete(req);
		
		System.out.println("delete result : " + result);
		
		PrintWriter out = resp.getWriter();
		out.print(result);
	}
	
	
	

}
