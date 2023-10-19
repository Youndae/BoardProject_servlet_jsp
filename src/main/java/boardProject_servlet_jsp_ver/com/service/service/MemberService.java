package boardProject_servlet_jsp_ver.com.service.service;

import javax.servlet.http.HttpServletRequest;

public interface MemberService {

	public int login(HttpServletRequest request);
	
	public String join(HttpServletRequest request);
	
	public int checkUserId(HttpServletRequest request);
}
