package boardProject_servlet_jsp_ver.com.domain.dto.comment;

import java.sql.Date;

public class CommentInsertDTO {
	
	private String userId;
	
	private String commentContent;
	
	private int commentIndent;
	
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

	public long getImageNo() {
		return imageNo;
	}

	public long getBoardNo() {
		return boardNo;
	}
	
	
	@Override
	public String toString() {
		return "CommentInsertDTO [userId=" + userId + ", commentContent=" + commentContent + ", commentIndent="
				+ commentIndent + ", imageNo=" + imageNo + ", boardNo=" + boardNo + "]";
	}

	public CommentInsertDTO() {
		
	}
	
	private CommentInsertDTO(CommentInsertDTOBuilder builder) {
		super();
		this.userId = builder.userId;
		this.commentContent = builder.commentContent;
		this.commentIndent = builder.commentIndent;
		this.imageNo = builder.imageNo;
		this.boardNo = builder.boardNo;
	}
	
	public static class CommentInsertDTOBuilder {
		private String userId;
		
		private String commentContent;
		
		private int commentIndent;
		
		private long imageNo;
		
		private long boardNo;
		
		public CommentInsertDTOBuilder userId(String userId) {
			this.userId = userId;
			return this;
		}
		
		public CommentInsertDTOBuilder commentContent(String commentContent) {
			this.commentContent = commentContent;
			return this;
		}
		
		public CommentInsertDTOBuilder commentIndent(int commentIndent) {
			this.commentIndent = commentIndent;
			return this;
		}
		
		public CommentInsertDTOBuilder imageNo(long imageNo) {
			this.imageNo = imageNo;
			return this;
		}
		
		public CommentInsertDTOBuilder boardNo(long boardNo) {
			this.boardNo = boardNo;
			return this;
		}
		
		
		public CommentInsertDTO build() {
			return new CommentInsertDTO(this);
		}
	}


}
