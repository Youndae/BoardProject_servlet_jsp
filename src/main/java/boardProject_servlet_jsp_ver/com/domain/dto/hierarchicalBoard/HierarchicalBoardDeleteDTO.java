package boardProject_servlet_jsp_ver.com.domain.dto.hierarchicalBoard;

public class HierarchicalBoardDeleteDTO {
	
	private long boardNo;
	
	private String boardUpperNo;
	
	private long boardGroupNo;
	
	private int boardIndent;

	public long getBoardNo() {
		return boardNo;
	}

	public String getBoardUpperNo() {
		return boardUpperNo;
	}

	public long getBoardGroupNo() {
		return boardGroupNo;
	}

	public int getBoardIndent() {
		return boardIndent;
	}

	@Override
	public String toString() {
		return "HierarchicalBoardDeleteDTO [boardNo=" + boardNo + ", boardUpperNo=" + boardUpperNo + ", boardGroupNo="
				+ boardGroupNo + ", boardIndent=" + boardIndent + "]";
	}
	
	public HierarchicalBoardDeleteDTO() {
		
	}
	
	private HierarchicalBoardDeleteDTO(HierarchicalBoardDeleteDTOBuilder builder) {
		super();
		this.boardNo = builder.boardNo;
		this.boardUpperNo = builder.boardUpperNo;
		this.boardGroupNo = builder.boardGroupNo;
		this.boardIndent = builder.boardIndent;
	}
	
	
	public static class HierarchicalBoardDeleteDTOBuilder{
		private long boardNo;
		
		private String boardUpperNo;
		
		private long boardGroupNo;
		
		private int boardIndent;
		
		public HierarchicalBoardDeleteDTOBuilder boardNo(long boardNo) {
			this.boardNo = boardNo;
			return this;
		}
		
		public HierarchicalBoardDeleteDTOBuilder boardUpperNo(String boardUpperNo) {
			this.boardUpperNo = boardUpperNo;
			return this;
		}
		
		public HierarchicalBoardDeleteDTOBuilder boardGroupNo(long boardGroupNo) {
			this.boardGroupNo = boardGroupNo;
			return this;
		}

		public HierarchicalBoardDeleteDTOBuilder boardIndent(int boardIndent) {
			this.boardIndent = boardIndent;
			return this;
		}
		
		public HierarchicalBoardDeleteDTO build() {
			return new HierarchicalBoardDeleteDTO(this);
		}
	}
	
	

}
