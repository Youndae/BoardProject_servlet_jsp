package boardProject_servlet_jsp_ver.com.domain.entity;

import java.sql.Date;

public class HierarchicalBoard {
	
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
	
	public HierarchicalBoard() {
		
	}
	
	
	private HierarchicalBoard(HierarchicalBoardBuilder builder) {
		super();
		this.boardNo = builder.boardNo;
		this.boardTitle = builder.boardTitle;
		this.userId = builder.userId;
		this.boardContent = builder.boardContent;
		this.boardDate = builder.boardDate;
		this.boardGroupNo = builder.boardGroupNo;
		this.boardIndent = builder.boardIndent;
		this.boardUpperNo = builder.boardUpperNo;
	}


	@Override
	public String toString() {
		return "HierarchicalBoard [boardNo=" + boardNo + ", boardTitle=" + boardTitle + ", userId=" + userId
				+ ", boardContent=" + boardContent + ", boardDate=" + boardDate + ", boardGroupNo=" + boardGroupNo
				+ ", boardIndent=" + boardIndent + ", boardUpperNo=" + boardUpperNo + "]";
	}







	public static class HierarchicalBoardBuilder {
		private long boardNo;
		
		private String boardTitle;
		
		private String userId;
		
		private String boardContent;
		
		private Date boardDate;
		
		private long boardGroupNo;
		
		private int boardIndent;
		
		private String boardUpperNo;
		
		public HierarchicalBoardBuilder boardNo(long boardNo) {
			this.boardNo = boardNo;
			return this;
		}
		
		public HierarchicalBoardBuilder boardTitle(String boardTitle) {
			this.boardTitle = boardTitle;
			return this;
		}

		public HierarchicalBoardBuilder userId(String userId) {
			this.userId = userId;
			return this;
		}
		
		public HierarchicalBoardBuilder boardContent(String boardContent) {
			this.boardContent = boardContent;
			return this;
		}
		
		public HierarchicalBoardBuilder boardDate(Date boardDate) {
			this.boardDate = boardDate;
			return this;
		}
		
		public HierarchicalBoardBuilder boardGroupNo(long boardGroupNo) {
			this.boardGroupNo = boardGroupNo;
			return this;
		}
		
		public HierarchicalBoardBuilder boardIndnet(int boardIndent) {
			this.boardIndent = boardIndent;
			return this;
		}
		
		public HierarchicalBoardBuilder boardUpperNo(String boardUpperNo) {
			this.boardUpperNo = boardUpperNo;
			return this;
		}
		
		public HierarchicalBoard build() {
			return new HierarchicalBoard(this);
		}
	}
}
