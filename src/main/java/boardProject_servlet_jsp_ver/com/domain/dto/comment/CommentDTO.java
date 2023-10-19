package boardProject_servlet_jsp_ver.com.domain.dto.comment;

import java.util.List;

import boardProject_servlet_jsp_ver.com.domain.dto.PageDTO;
import boardProject_servlet_jsp_ver.com.domain.entity.Comment;

public class CommentDTO {
	
	private List<Comment> commentList;
	
	private PageDTO pageDTO;
	
	private String uid;

	public List<Comment> getCommentList() {
		return commentList;
	}

	public PageDTO getPageDTO() {
		return pageDTO;
	}

	public String getUid() {
		return uid;
	}
	
	public CommentDTO() {
		
	}

	@Override
	public String toString() {
		return "CommentDTO [commentList=" + commentList + ", pageDTO=" + pageDTO + ", uid=" + uid + "]";
	}

	private CommentDTO(CommentDTOBuilder builder) {
		super();
		this.commentList = builder.commentList;
		this.pageDTO = builder.pageDTO;
		this.uid = builder.uid;
	}
	
	
	
	public static class CommentDTOBuilder {
		private List<Comment> commentList;
		
		private PageDTO pageDTO;
		
		private String uid;
		
		public CommentDTOBuilder commentList(List<Comment> commentList) {
			this.commentList = commentList;
			return this;
		}
		
		public CommentDTOBuilder pageDTO(PageDTO pageDTO) {
			this.pageDTO = pageDTO;
			return this;
		}
		
		public CommentDTOBuilder uid(String uid) {
			this.uid = uid;
			return this;
		}
		
		public CommentDTO build() {
			return new CommentDTO(this);
		}
	}
}
