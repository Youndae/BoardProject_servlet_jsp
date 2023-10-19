package boardProject_servlet_jsp_ver.com.domain.dto.hierarchicalBoard;

public class HierarchicalBoardReplyDTO {
	
	private long boardNo;
	
	private int boardIndent;
	
	private long boardGroupNo;
	
	private String boardUpperNo;

	public long getBoardNo() {
		return boardNo;
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
	
	public HierarchicalBoardReplyDTO() {
		
	}
	
	
	
	@Override
	public String toString() {
		return "HierarchicalBoardReplyDTO [boardNo=" + boardNo + ", boardIndent=" + boardIndent + ", boardGroupNo="
				+ boardGroupNo + ", boardUpperNo=" + boardUpperNo + "]";
	}

	private HierarchicalBoardReplyDTO(HierarchicalBoardReplyDTOBuilder builder) {
		super();
		this.boardNo = builder.boardNo;
		this.boardIndent = builder.boardIndent;
		this.boardGroupNo = builder.boardGroupNo;
		this.boardUpperNo = builder.boardUpperNo;
	}
	
	
	public static class HierarchicalBoardReplyDTOBuilder {
		private long boardNo;
		
		private int boardIndent;
		
		private long boardGroupNo;
		
		private String boardUpperNo;
		
		public HierarchicalBoardReplyDTOBuilder boardNo(long boardNo) {
			this.boardNo = boardNo;
			return this;
		}
		
		public HierarchicalBoardReplyDTOBuilder boardIndent(int boardIndent) {
			this.boardIndent = boardIndent;
			return this;
		}
		
		public HierarchicalBoardReplyDTOBuilder boardGroupNo(long boardGroupNo) {
			this.boardGroupNo = boardGroupNo;
			return this;
		}
		
		public HierarchicalBoardReplyDTOBuilder boardUpperNo(String boardUpperNo) {
			this.boardUpperNo = boardUpperNo;
			return this;
		}
		
		public HierarchicalBoardReplyDTO build() {
			return new HierarchicalBoardReplyDTO(this);
		}
	}

}
