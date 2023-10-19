package boardProject_servlet_jsp_ver.com.domain.dto.hierarchicalBoard;

public class HierarchicalBoardReplyProcDTO {
	
	private long boardNo;
	
	private String boardTitle;
	
	private String userId;
	
	private String boardContent;
	
	private int boardIndent;
	
	private long boardGroupNo;
	
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

	public int getBoardIndent() {
		return boardIndent;
	}

	public long getBoardGroupNo() {
		return boardGroupNo;
	}
	
	public String getBoardUpperNo() {
		return boardUpperNo;
	}
	
	public HierarchicalBoardReplyProcDTO() {
		
	}
	
	
	
	@Override
	public String toString() {
		return "HierarchicalBoardReplyDTO [boardNo=" + boardNo + ", boardTitle=" + boardTitle 
				+ ", userId=" + userId + ", boardContent=" + boardContent + ", boardIndent=" + boardIndent + ", boardGroupNo="
				+ boardGroupNo + ", boardUpperNo=" + boardUpperNo + "]";
	}

	private HierarchicalBoardReplyProcDTO(HierarchicalBoardReplyProcDTOBuilder builder) {
		super();
		this.boardNo = builder.boardNo;
		this.boardTitle = builder.boardTitle;
		this.userId = builder.userId;
		this.boardContent = builder.boardContent;
		this.boardIndent = builder.boardIndent;
		this.boardGroupNo = builder.boardGroupNo;
		this.boardUpperNo = builder.boardUpperNo;
	}
	
	
	public static class HierarchicalBoardReplyProcDTOBuilder {
		private long boardNo;
		
		private String boardTitle;
		
		private String userId;
		
		private String boardContent;
		
		private int boardIndent;
		
		private long boardGroupNo;
		
		private String boardUpperNo;
		
		
		public HierarchicalBoardReplyProcDTOBuilder boardNo(long boardNo) {
			this.boardNo = boardNo;
			return this;
		}
		
		public HierarchicalBoardReplyProcDTOBuilder boardTitle(String boardTitle) {
			this.boardTitle = boardTitle;
			return this;
		}
		
		public HierarchicalBoardReplyProcDTOBuilder userId(String userId) {
			this.userId = userId;
			return this;
		}
		
		public HierarchicalBoardReplyProcDTOBuilder boardContent(String boardContent) {
			this.boardContent = boardContent;
			return this;
		}
		
		public HierarchicalBoardReplyProcDTOBuilder boardIndent(int boardIndent) {
			this.boardIndent = boardIndent;
			return this;
		}
		
		public HierarchicalBoardReplyProcDTOBuilder boardGroupNo(long boardGroupNo) {
			this.boardGroupNo = boardGroupNo;
			return this;
		}
		
		public HierarchicalBoardReplyProcDTOBuilder boardUpperNo(String boardUpperNo) {
			this.boardUpperNo = boardUpperNo;
			return this;
		}
		
		
		public HierarchicalBoardReplyProcDTO build() {
			return new HierarchicalBoardReplyProcDTO(this);
		}
	}

}
