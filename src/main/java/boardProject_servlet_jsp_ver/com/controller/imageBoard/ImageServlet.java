package boardProject_servlet_jsp_ver.com.controller.imageBoard;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.http.HttpHeaders;
import java.nio.file.Files;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import boardProject_servlet_jsp_ver.com.service.service.ImageBoardService;
import boardProject_servlet_jsp_ver.com.service.service.ImageBoardServiceImpl;

@WebServlet(urlPatterns = "/imageBoard/display")
public class ImageServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	private ImageBoardService imageBoardService = new ImageBoardServiceImpl();

	//get byte
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.setContentType("image/jpeg");
		
		ServletOutputStream out;
		
		out = resp.getOutputStream();
		
		FileInputStream fis = new FileInputStream("E:\\upload\\boardProject\\" + req.getParameter("image"));
		
		BufferedInputStream bin = new BufferedInputStream(fis);
		BufferedOutputStream bout = new BufferedOutputStream(out);
		
		int ch = 0;
		
		while((ch = bin.read()) != -1)
			bout.write(ch);
		
		bin.close();
		fis.close();
		bout.close();
		out.close();
	}
	
	

}
