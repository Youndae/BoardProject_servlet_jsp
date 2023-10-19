package boardProject_servlet_jsp_ver.com.domain.entity;

import java.sql.Date;

public class Comment {
	
	private long commentNo;
	
	private String userId;
	
	private String commentContent;
	
	private Date commentDate;
	
	private long commentGroupNo;
	
	private int commentIndent;
	
	private String commentUpperNo;
	
	private long imageNo;
	
	private long boardNo;
	
	private int commentStatus;

	public long getCommentNo() {
		return commentNo;
	}

	public String getUserId() {
		return userId;
	}
	
	public String getCommentcontent() {
		return commentContent;
	}

	public Date getCommentDate() {
		return commentDate;
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

	public long getImageNo() {
		return imageNo;
	}

	public long getBoardNo() {
		return boardNo;
	}
	
	public int getCommentStatus() {
		return commentStatus;
	}

	public Comment(CommentBuilder builder) {
		super();
		this.commentNo = builder.commentNo;
		this.userId = builder.userId;
		this.commentContent = builder.commentContent;
		this.commentDate = builder.commentDate;
		this.commentGroupNo = builder.commentGroupNo;
		this.commentIndent = builder.commentIndent;
		this.commentUpperNo = builder.commentUpperNo;
		this.imageNo = builder.imageNo;
		this.boardNo = builder.boardNo;
		this.commentStatus = builder.commentStatus;
	}

	@Override
	public String toString() {
		return "Comment [commentNo=" + commentNo + ", userId=" + userId + ", commentContent=" + commentContent 
				+ ", commentDate=" + commentDate + ", commentGroupNo=" + commentGroupNo 
				+ ", commentIndent=" + commentIndent + ", commentUpperNo=" 
				+ commentUpperNo + ", imageNo=" + imageNo + ", boardNo=" + boardNo + ", commentStatus=" + commentStatus + "]";
	}
	
	
	
	public static class CommentBuilder {
		private long commentNo;
		
		private String userId;
		
		private String commentContent;
		
		private Date commentDate;
		
		private long commentGroupNo;
		
		private int commentIndent;
		
		private String commentUpperNo;
		
		private long imageNo;
		
		private long boardNo;
		
		private int commentStatus;
		
		public CommentBuilder commentNo(long commentNo) {
			this.commentNo = commentNo;
			return this;
		}
		
		public CommentBuilder userId(String userId) {
			this.userId = userId;
			return this;
		}
		
		public CommentBuilder commentContent(String commentContent) {
			this.commentContent = commentContent;
			return this;
		}
		
		public CommentBuilder commentDate(Date commentDate) {
			this.commentDate = commentDate;
			return this;
		}
		
		public CommentBuilder commentGroupNo(long commentGroupNo) {
			this.commentGroupNo = commentGroupNo;
			return this;
		}
		
		public CommentBuilder commentIndent(int commentIndent) {
			this.commentIndent = commentIndent;
			return this;
		}
		
		public CommentBuilder commentUpperNo(String commentUpperNo) {
			this.commentUpperNo = commentUpperNo;
			return this;
		}
		
		public CommentBuilder imageNo(long imageNo) {
			this.imageNo = imageNo;
			return this;
		}
		
		public CommentBuilder boardNo(long boardNo) {
			this.boardNo = boardNo;
			return this;
		}
		
		public CommentBuilder commentStatus(int commentStatus) {
			this.commentStatus = commentStatus;
			return this;
		}
		
		public Comment build() {
			return new Comment(this);
		}
	}

}
