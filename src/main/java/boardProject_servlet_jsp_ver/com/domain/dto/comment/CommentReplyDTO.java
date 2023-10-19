package boardProject_servlet_jsp_ver.com.domain.dto.comment;

import java.sql.Date;

public class CommentReplyDTO {
	
	private String userId;
	
	private String commentContent;
	
	private int commentIndent;
	
	private long commentGroupNo;
	
	private String commentUpperNo;
	
	private long imageNo;
	
	private long boardNo;

	public String getUserId() {
		return userId;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public int getCommentIndent() {
		return commentIndent;
	}
	
	public long getCommentGroupNo() {
		return commentGroupNo;
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
	
	@Override
	public String toString() {
		return "CommentReplyDTO [userId=" + userId + ", commentContent=" + commentContent + ", commentIndent="
				+ commentIndent + ", commentGroupNo=" + commentGroupNo + ", commentUpperNo=" + commentUpperNo
				+ ", imageNo=" + imageNo + ", boardNo=" + boardNo + "]";
	}

	public CommentReplyDTO() {
		
	}
	
	private CommentReplyDTO(CommentReplyDTOBuilder builder) {
		super();
		this.userId = builder.userId;
		this.commentContent = builder.commentContent;
		this.commentIndent = builder.commentIndent;
		this.commentGroupNo = builder.commentGroupNo;
		this.commentUpperNo = builder.commentUpperNo;
		this.imageNo = builder.imageNo;
		this.boardNo = builder.boardNo;
	}
	
	public static class CommentReplyDTOBuilder {
		private String userId;
		
		private String commentContent;
		
		private int commentIndent;
		
		private long commentGroupNo;
		
		private String commentUpperNo;
		
		private long imageNo;
		
		private long boardNo;
		
		public CommentReplyDTOBuilder userId(String userId) {
			this.userId = userId;
			return this;
		}
		
		public CommentReplyDTOBuilder commentContent(String commentContent) {
			this.commentContent = commentContent;
			return this;
		}
		
		public CommentReplyDTOBuilder commentIndent(int commentIndent) {
			this.commentIndent = commentIndent;
			return this;
		}
		
		public CommentReplyDTOBuilder commentGroupNo(long commentGroupNo) {
			this.commentGroupNo = commentGroupNo;
			return this;
		}
		
		public CommentReplyDTOBuilder commentUpperNo(String commentUpperNo) {
			this.commentUpperNo = commentUpperNo;
			return this;
		}
		
		public CommentReplyDTOBuilder imageNo(long imageNo) {
			this.imageNo = imageNo;
			return this;
		}
		
		public CommentReplyDTOBuilder boardNo(long boardNo) {
			this.boardNo = boardNo;
			return this;
		}
		
		
		public CommentReplyDTO build() {
			return new CommentReplyDTO(this);
		}
	}


}
