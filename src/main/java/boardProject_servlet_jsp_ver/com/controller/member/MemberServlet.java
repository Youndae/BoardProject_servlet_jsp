package boardProject_servlet_jsp_ver.com.controller.member;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import boardProject_servlet_jsp_ver.com.domain.ResultProperties;
import boardProject_servlet_jsp_ver.com.domain.ViewPathProperties;
import boardProject_servlet_jsp_ver.com.service.service.MemberService;
import boardProject_servlet_jsp_ver.com.service.service.MemberServiceImpl;

@WebServlet(urlPatterns = "/member/*")
public class MemberServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private MemberService memberService = new MemberServiceImpl();

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String uri = req.getRequestURI();
		String path = uri.substring(uri.lastIndexOf("/"));
		RequestDispatcher dispatcher = null;

		HttpSession session = req.getSession();
		String uid = (String) session.getAttribute("id");

		resp.setContentType("text/html; charset=UTF-8");
		req.setCharacterEncoding("UTF-8");

		if (path.equals("/login")) {

			if (uid == null) {
				dispatcher = req.getRequestDispatcher(ViewPathProperties.memberViewPath + "login.jsp");
				dispatcher.forward(req, resp);
			} else {
				dispatcher = req.getRequestDispatcher(ViewPathProperties.accessErrorViewPath);
				dispatcher.forward(req, resp);
			}
		} else if (path.equals("/loginProc")) {
			if (uid == null)
				doPostLogin(req, resp);
			else {
				dispatcher = req.getRequestDispatcher(ViewPathProperties.accessErrorViewPath);
				dispatcher.forward(req, resp);
			}
		} else if (path.equals("/join")) {
			if (uid == null) {
				dispatcher = req.getRequestDispatcher(ViewPathProperties.memberViewPath + "join.jsp");
				dispatcher.forward(req, resp);
			} else {
				dispatcher = req.getRequestDispatcher(ViewPathProperties.accessErrorViewPath);
				dispatcher.forward(req, resp);
			}
		} else if (path.equals("/joinProc")) {
			if (uid == null)
				doPostJoin(req, resp);
			else {
				dispatcher = req.getRequestDispatcher(ViewPathProperties.accessErrorViewPath);
				dispatcher.forward(req, resp);
			}
		} else if (path.equals("/checkUserId")) {
			if (uid == null)
				doGetCheckUserId(req, resp);
			else {
				dispatcher = req.getRequestDispatcher(ViewPathProperties.accessErrorViewPath);
				dispatcher.forward(req, resp);
			}
		} else if (path.equals("/logout")) {
			if (uid != null)
				doPostLogout(req, resp);
			else {
				dispatcher = req.getRequestDispatcher(ViewPathProperties.accessErrorViewPath);
				dispatcher.forward(req, resp);
			}
		}
	}

	protected void doPostJoin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String result = memberService.join(req);
		RequestDispatcher dispatcher;

		if (result.equals(ResultProperties.FAIL)) {
			dispatcher = req.getRequestDispatcher(ViewPathProperties.accessErrorViewPath);
			dispatcher.forward(req, resp);
		} else if (result.equals(ResultProperties.SUCCESS)) {
			dispatcher = req.getRequestDispatcher(ViewPathProperties.memberViewPath + "login.jsp");
			dispatcher.forward(req, resp);
		}

	}

	protected void doPostLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		int result = memberService.login(req);

		if (result == 1) {
			HttpSession session = req.getSession();
			session.setAttribute("id", req.getParameter("username"));
			resp.sendRedirect("/board/boardList");
		} else {
			RequestDispatcher dispatcher = req
					.getRequestDispatcher(ViewPathProperties.memberViewPath + "loginFail.jsp");
			dispatcher.forward(req, resp);

		}

	}

	protected void doGetCheckUserId(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		int result = memberService.checkUserId(req);

		PrintWriter out = resp.getWriter();
		out.print(result);

	}

	protected void doPostLogout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();

		session.invalidate();

		resp.sendRedirect("/board/boardList");
	}

}
