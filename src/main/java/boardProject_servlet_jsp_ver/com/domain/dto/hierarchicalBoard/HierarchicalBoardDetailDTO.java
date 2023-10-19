package boardProject_servlet_jsp_ver.com.domain.dto.hierarchicalBoard;

import java.sql.Date;
import java.util.List;

import boardProject_servlet_jsp_ver.com.domain.dto.PageDTO;
import boardProject_servlet_jsp_ver.com.domain.entity.Comment;

public class HierarchicalBoardDetailDTO {
	
	private long boardNo;
	
	private String boardTitle;
	
	private String boardContent;
	
	private String userId;
	
	private Date boardDate;
	
	private List<Comment> commentList;
	
	private PageDTO pageMaker;

	public long getBoardNo() {
		return boardNo;
	}

	public String getBoardTitle() {
		return boardTitle;
	}

	public String getBoardContent() {
		return boardContent;
	}

	public String getUserId() {
		return userId;
	}

	public Date getBoardDate() {
		return boardDate;
	}
	
	public List<Comment> getCommentList(){
		return commentList;
	}
	
	public PageDTO getPageMaker() {
		return pageMaker;
	}

	@Override
	public String toString() {
		return "HierarchicalBoardDetailDTO [boardNo=" + boardNo + ", boardTitle=" + boardTitle + ", boardContent="
				+ boardContent + ", userId=" + userId + ", boardDate=" + boardDate + ", commentList=" + commentList
				+ ", pageMaker=" + pageMaker + "]";
	}
	
	
	
	public HierarchicalBoardDetailDTO() {
		
	}

	private HierarchicalBoardDetailDTO(HierarchicalBoardDetailDTOBuilder builder) {
		super();
		this.boardNo = builder.boardNo;
		this.boardTitle = builder.boardTitle;
		this.boardContent = builder.boardContent;
		this.userId = builder.userId;
		this.boardDate = builder.boardDate;
		this.commentList = builder.commentList;
		this.pageMaker = builder.pageMaker;
	}
	
	
	public static class HierarchicalBoardDetailDTOBuilder{
		private long boardNo;
		
		private String boardTitle;
		
		private String boardContent;
		
		private String userId;
		
		private Date boardDate;
		
		private List<Comment> commentList;
		
		private PageDTO pageMaker;
		
		public HierarchicalBoardDetailDTOBuilder boardNo(long boardNo) {
			this.boardNo = boardNo;
			return this;
		}
		
		public HierarchicalBoardDetailDTOBuilder boardTitle(String boardTitle) {
			this.boardTitle = boardTitle;
			return this;
		}
		
		public HierarchicalBoardDetailDTOBuilder boardContent(String boardContent) {
			this.boardContent = boardContent;
			return this;
		}
		
		public HierarchicalBoardDetailDTOBuilder userId(String userId) {
			this.userId = userId;
			return this;
		}
		
		public HierarchicalBoardDetailDTOBuilder boardDate(Date boardDate) {
			this.boardDate = boardDate;
			return this;
		}
		
		public HierarchicalBoardDetailDTOBuilder commentList(List<Comment> commentList) {
			this.commentList = commentList;
			return this;
		}
		
		public HierarchicalBoardDetailDTOBuilder pageMaker(PageDTO pageMaker) {
			this.pageMaker = pageMaker;
			return this;
		}
		
		public HierarchicalBoardDetailDTO build() {
			return new HierarchicalBoardDetailDTO(this);
		}
	}

}
