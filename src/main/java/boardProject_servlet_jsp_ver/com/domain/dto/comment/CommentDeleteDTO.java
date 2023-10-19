package boardProject_servlet_jsp_ver.com.domain.dto.comment;

public class CommentDeleteDTO {
	
	private long commentNo;
	
	private long commentGroupNo;
	
	private int commentIndent;
	
	private String commentUpperNo;

	public long getCommentNo() {
		return commentNo;
	}

	public long getCommentGroupNo() {
		return commentGroupNo;
	}

	public int getCommentIndent() {
		return commentIndent;
	}

	public String getCommentUpperNo() {
		return commentUpperNo;
	}

	@Override
	public String toString() {
		return "CommentDeleteDTO [commentNo=" + commentNo + ", commentGroupNo=" + commentGroupNo + ", commentIndent="
				+ commentIndent + ", commentUpperNo=" + commentUpperNo + "]";
	}
	
	public CommentDeleteDTO() {
		
	}
	
	private CommentDeleteDTO(CommentDeleteDTOBuilder builder) {
		super();
		this.commentNo = builder.commentNo;
		this.commentGroupNo = builder.commentGroupNo;
		this.commentIndent = builder.commentIndent;
		this.commentUpperNo = builder.commentUpperNo;
	}
	
	public static class CommentDeleteDTOBuilder {
		private long commentNo;
		
		private long commentGroupNo;
		
		private int commentIndent;
		
		private String commentUpperNo;
		
		public CommentDeleteDTOBuilder commentNo(long commentNo) {
			this.commentNo = commentNo;
			return this;
		}
		
		public CommentDeleteDTOBuilder commentGroupNo(long commentGroupNo) {
			this.commentGroupNo = commentGroupNo;
			return this;
		}
		
		public CommentDeleteDTOBuilder commentIndent(int commentIndent) {
			this.commentIndent = commentIndent;
			return this;
		}
		
		public CommentDeleteDTOBuilder commentUpperNo(String commentUpperNo) {
			this.commentUpperNo = commentUpperNo;
			return this;
		}
		
		public CommentDeleteDTO build() {
			return new CommentDeleteDTO(this);
		}
	}

}
