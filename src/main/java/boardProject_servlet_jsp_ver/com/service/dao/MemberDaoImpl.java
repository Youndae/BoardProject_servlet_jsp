package boardProject_servlet_jsp_ver.com.service.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import boardProject_servlet_jsp_ver.com.domain.ResultProperties;
import boardProject_servlet_jsp_ver.com.domain.dto.member.JoinDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.member.LoginDTO;
import boardProject_servlet_jsp_ver.com.jdbc.JDBCTemplate;

public class MemberDaoImpl implements MemberDao{
	
	Connection con = null;

	@Override
	public int login(LoginDTO dto) {
		
		con = JDBCTemplate.getConnection();
		String sql = "SELECT count(*) FROM member WHERE userId=? and userPw=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getUserPw());
			
			rs = pstmt.executeQuery();
			
			if(rs.next())
				result = rs.getInt(1);
		}catch(SQLException e){
			e.printStackTrace();
			return ResultProperties.FAIL_INTEGER;
		}finally {
			JDBCTemplate.close(con);
			JDBCTemplate.close(pstmt);
			JDBCTemplate.close(rs);
		}
		
		return result;
	}

	@Override
	public String join(JoinDTO dto) {
		
		con = JDBCTemplate.getConnection();
		String sql = "INSERT INTO Member("
				+ "userId"
				+ ", userPw"
				+ ", userName) "
				+ "VALUES("
				+ "?"
				+ ", ?"
				+ ", ?)";
		
		String authSQL = "INSERT INTO auth("
				+ "userId"
				+ ", auth) "
				+ "VALUES("
				+ "?"
				+ ", ?)";
		
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getUserPw());
			pstmt.setString(3, dto.getUserName());
			
			pstmt.executeUpdate();
			
			pstmt = con.prepareStatement(authSQL);
			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, "ROLE_MEMBER");
			
			pstmt.executeUpdate();
			
			JDBCTemplate.commit(con);
		}catch(SQLException e) {
			e.printStackTrace();
			JDBCTemplate.rollback(con);
			return ResultProperties.FAIL;
		}finally {
			JDBCTemplate.close(con);
			JDBCTemplate.close(pstmt);
		}
		
		return ResultProperties.SUCCESS;
	}

	@Override
	public int checkUserId(String userId) {
		
		con = JDBCTemplate.getConnection();
		String sql = "SELECT count(*) "
				+ "FROM member "
				+ "WHERE userId=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next())
				result = rs.getInt(1);
		}catch(SQLException e) {
			e.printStackTrace();
			return ResultProperties.FAIL_INTEGER;
		}finally {
			JDBCTemplate.close(con);
			JDBCTemplate.close(pstmt);
			JDBCTemplate.close(rs);
		}
		
		return result;
	}

	
}
