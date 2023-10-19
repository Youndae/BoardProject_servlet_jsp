package boardProject_servlet_jsp_ver.com.domain.dto.member;

public class JoinDTO {

	private String userId;
	
	private String userPw;
	
	private String userName;
	
	public String getUserId() {
		return userId;
	}
	
	public String getUserPw() {
		return userPw;
	}
	
	public String getUserName() {
		return userName;
	}

	@Override
	public String toString() {
		return "JoinDTO [userId=" + userId + ", userPw=" + userPw + ", userName=" + userName + "]";
	}
	
	public JoinDTO() {
		
	}
	
	private JoinDTO(JoinDTOBuilder builder) {
		super();
		this.userId = builder.userId;
		this.userPw = builder.userPw;
		this.userName = builder.userName;
	}
	
	public static class JoinDTOBuilder {
		private String userId;
		
		private String userPw;
		
		private String userName;
		
		public JoinDTOBuilder userId(String userId) {
			this.userId = userId;
			return this;
		}
		
		public JoinDTOBuilder userPw(String userPw) {
			this.userPw = userPw;
			return this;
		}

		public JoinDTOBuilder userName(String userName) {
			this.userName = userName;
			return this;
		}
		
		public JoinDTO build() {
			return new JoinDTO(this);
		}
	}
}
