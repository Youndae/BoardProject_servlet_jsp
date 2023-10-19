package boardProject_servlet_jsp_ver.com.domain.entity;

public class Auth {

	private int authNo;
	
	private String userId;
	
	private String auth;

	public int getAuthNo() {
		return authNo;
	}

	public String getUserId() {
		return userId;
	}

	public String getAuth() {
		return auth;
	}

	@Override
	public String toString() {
		return "Auth [authNo=" + authNo + ", userId=" + userId + ", auth=" + auth + "]";
	}
	
}
