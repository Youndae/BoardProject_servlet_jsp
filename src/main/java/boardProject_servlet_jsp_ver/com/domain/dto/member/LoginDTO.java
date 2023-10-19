package boardProject_servlet_jsp_ver.com.domain.dto.member;

public class LoginDTO {

	private String userId;
	
	private String userPw;
	
	public String getUserId() {
		return userId;
	}
	
	public String getUserPw() {
		return userPw;
	}

	@Override
	public String toString() {
		return "LoginDTO [userId=" + userId + ", userPw=" + userPw + "]";
	}
	
	public LoginDTO() {
		
	}
	
	private LoginDTO(LoginDTOBuilder builder) {
		super();
		this.userId = builder.userId;
		this.userPw = builder.userPw;
	}
	
	
	public static class LoginDTOBuilder{
		private String userId;
		
		private String userPw;
		
		public LoginDTOBuilder userId(String userId) {
			this.userId = userId;
			return this;
		}
		
		public LoginDTOBuilder userPw(String userPw) {
			this.userPw = userPw;
			return this;
		}
		
		public LoginDTO build() {
			return new LoginDTO(this);
		}
	}
}
