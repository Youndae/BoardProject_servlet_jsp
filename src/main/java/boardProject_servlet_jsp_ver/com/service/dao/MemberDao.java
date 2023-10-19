package boardProject_servlet_jsp_ver.com.service.dao;import javax.servlet.http.HttpServletRequest;

import boardProject_servlet_jsp_ver.com.domain.dto.member.JoinDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.member.LoginDTO;

public interface MemberDao {
	
	public int login(LoginDTO dto);
	
	public String join(JoinDTO dto);
	
	public int checkUserId(String userId);

}
