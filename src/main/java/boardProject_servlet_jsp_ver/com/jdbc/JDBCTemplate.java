package boardProject_servlet_jsp_ver.com.jdbc;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCTemplate {
	
	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		String url = "jdbc:mysql://localhost:3306/boardproject?serverTimezone=UTC&characterEncoding=UTF-8&autoReconnection=true";
		String id = "root";
		String pw = "1234";
		
		Connection con = null;
		
		try {
			//뭔지 알아보기.
			con = DriverManager.getConnection(url, id, pw);
			
			con.setAutoCommit(false);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return con;
	}
	
	//연결 상태라면 true, 닫혀있다면 false 리턴.
	public static boolean isConnection(Connection con) {
		boolean valid = true;
		
		try {
			// con이 null이거나 con이 close 상태라면
			if(con == null || con.isClosed()) {
				valid = false;
			}
		}catch(SQLException e) {
			valid = true;
			e.printStackTrace();
		}
		
		return valid;
	}
	
	//연결 상태인지 아닌지 확인 후 연결 상태라면 close
	public static void close(Connection con) {
		if(isConnection(con)) {
			try {
				con.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//statement가 null이 아닐 때 close
	public static void close(Statement stmt) {
		if(stmt != null) {
			try {
				stmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//rs가 null이 아닐 때 close
	public static void close(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//연결 상태라면 commit
	public static void commit(Connection con) {
		if(isConnection(con)) {
			try {
				con.commit();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//연결 상태라면 rollback
	public static void rollback(Connection con) {
		if(isConnection(con)) {
			try {
				con.rollback();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	

}
