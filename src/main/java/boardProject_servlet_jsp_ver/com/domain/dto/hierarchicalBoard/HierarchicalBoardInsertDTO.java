package boardProject_servlet_jsp_ver.com.domain.dto.hierarchicalBoard;

import java.sql.Date;

public class HierarchicalBoardInsertDTO {

	private long boardNo;
	
	private String boardTitle;
	
	private String userId;
	
	private String boardContent;
	
	private Date boardDate;
	
	private long boardGroupNo;
	
	private int boardIndent;
	
	private String boardUpperNo;

	public long getBoardNo() {
		return boardNo;
	}

	public String getBoardTitle() {
		return boardTitle;
	}

	public String getUserId() {
		return userId;
	}

	public String getBoardContent() {
		return boardContent;
	}

	public Date getBoardDate() {
		return boardDate;
	}

	public long getBoardGroupNo() {
		return boardGroupNo;
	}

	public int getBoardIndent() {
		return boardIndent;
	}

	public String getBoardUpperNo() {
		return boardUpperNo;
	}

	@Override
	public String toString() {
		return "HierarchicalBoardInsertDTO [boardNo=" + boardNo + ", boardTitle=" + boardTitle + ", userId=" + userId
				+ ", boardContent=" + boardContent + ", boardDate=" + boardDate + ", boardGroupNo=" + boardGroupNo
				+ ", boardIndent=" + boardIndent + ", boardUpperNo=" + boardUpperNo + "]";
	}
	
	public HierarchicalBoardInsertDTO() {
		
	}
	
	private HierarchicalBoardInsertDTO(HierarchicalBoardInsertDTOBuilder builder) {
		super();
		this.boardNo = builder.boardNo;
		this.boardTitle = builder.boardTitle;
		this.boardContent = builder.boardContent;
		this.userId = builder.userId;
		this.boardDate = builder.boardDate;
		this.boardGroupNo = builder.boardGroupNo;
		this.boardIndent = builder.boardIndent;
		this.boardUpperNo = builder.boardUpperNo;
	}
	
	public static class HierarchicalBoardInsertDTOBuilder{
		private long boardNo;
		
		private String boardTitle;
		
		private String userId;
		
		private String boardContent;
		
		private Date boardDate;
		
		private long boardGroupNo;
		
		private int boardIndent;
		
		private String boardUpperNo;
		
		public HierarchicalBoardInsertDTOBuilder boardNo(long boardNo) {
			this.boardNo = boardNo;
			return this;
		}
		
		public HierarchicalBoardInsertDTOBuilder boardTitle(String boardTitle) {
			this.boardTitle = boardTitle;
			return this;
		}
		
		public HierarchicalBoardInsertDTOBuilder userId(String userId) {
			this.userId = userId;
			return this;
		}
		
		public HierarchicalBoardInsertDTOBuilder boardContent(String boardContent) {
			this.boardContent = boardContent;
			return this;
		}
		
		public HierarchicalBoardInsertDTOBuilder boardDate(Date boardDate) {
			this.boardDate = boardDate;
			return this;
		}
		
		public HierarchicalBoardInsertDTOBuilder boardGroupNo(long boardGroupNo) {
			this.boardGroupNo = boardGroupNo;
			return this;
		}
		
		public HierarchicalBoardInsertDTOBuilder boardIndent(int boardIndent) {
			this.boardIndent = boardIndent;
			return this;
		}
		
		public HierarchicalBoardInsertDTOBuilder boardUpperNo(String boardUpperNo) {
			this.boardUpperNo = boardUpperNo;
			return this;
		}
		
		public HierarchicalBoardInsertDTO build() {
			return new HierarchicalBoardInsertDTO(this);
		}
	}
}
