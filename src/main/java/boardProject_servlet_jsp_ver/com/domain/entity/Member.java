package boardProject_servlet_jsp_ver.com.domain.entity;

import java.util.List;

public class Member {

	private String userId;
	
	private String userpw;
	
	private String userName;
	
	private List<Auth> authList;

	public String getUserId() {
		return userId;
	}

	public String getUserpw() {
		return userpw;
	}

	public String getUserName() {
		return userName;
	}

	public List<Auth> getAuthList() {
		return authList;
	}

	@Override
	public String toString() {
		return "Member [userId=" + userId + ", userpw=" + userpw + ", userName=" + userName 
				+ ", authList=" + authList + "]";
	}
	
	
	public Member(MemberBuilder builder) {
		super();
		this.userId = builder.userId;
		this.userpw = builder.userPw;
		this.userName = builder.userName;
	}
	
	
	public static class MemberBuilder {
		
		private String userId;
		
		private String userPw;
		
		private String userName;
		
		public MemberBuilder userid(String userId) {
			this.userId = userId;
			return this;
		}
		
		public MemberBuilder userPw(String userPw) {
			this.userPw = userPw;
			return this;
		}
		
		public MemberBuilder userName(String userName) {
			this.userName = userName;
			return this;
		}
		
		public Member build() {
			return new Member(this);
		}
	}
}
