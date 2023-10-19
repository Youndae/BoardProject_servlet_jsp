package boardProject_servlet_jsp_ver.com.service.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;

import boardProject_servlet_jsp_ver.com.domain.ResultProperties;
import boardProject_servlet_jsp_ver.com.domain.dto.member.JoinDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.member.LoginDTO;
import boardProject_servlet_jsp_ver.com.service.dao.MemberDao;
import boardProject_servlet_jsp_ver.com.service.dao.MemberDaoImpl;

public class MemberServiceImpl implements MemberService{
	
	private MemberDao memberDAO = new MemberDaoImpl();

	@Override
	public int login(HttpServletRequest request) {
		
		LoginDTO dto = new LoginDTO();
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update((request.getParameter("password") + ResultProperties.SALT).getBytes());
			byte[] pwdSalt = md.digest();
			
			StringBuffer sb = new StringBuffer();
			
			for(byte b : pwdSalt)
				sb.append(String.format("%02x", b));
			
			dto = new LoginDTO.LoginDTOBuilder()
					.userId(request.getParameter("username"))
					.userPw(sb.toString())
					.build();
		}catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
			return 0;
		}
		
		
		return memberDAO.login(dto);
	}

	@Override
	public String join(HttpServletRequest request) {
		
		JoinDTO dto = new JoinDTO();
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update((request.getParameter("userPw") + ResultProperties.SALT).getBytes());
			byte[] pwdSalt = md.digest();
			
			StringBuffer sb = new StringBuffer();
			
			for(byte b : pwdSalt)
				sb.append(String.format("%02x", b));
			
			dto = new JoinDTO.JoinDTOBuilder()
					.userId(request.getParameter("userId"))
					.userPw(sb.toString())
					.userName(request.getParameter("userName"))
					.build();
		}catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
			return ResultProperties.FAIL;
		}
		
		return memberDAO.join(dto);
	}

	@Override
	public int checkUserId(HttpServletRequest request) {
		
		String userId = request.getParameter("userId");
		
		return memberDAO.checkUserId(userId);
	}
	
	

}
