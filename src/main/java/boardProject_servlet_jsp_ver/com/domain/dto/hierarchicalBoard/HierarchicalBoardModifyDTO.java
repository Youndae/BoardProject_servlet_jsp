package boardProject_servlet_jsp_ver.com.domain.dto.hierarchicalBoard;

public class HierarchicalBoardModifyDTO {
	
	private long boardNo;
	
	private String boardTitle;
	
	private String boardContent;

	public long getBoardNo() {
		return boardNo;
	}

	public String getBoardTitle() {
		return boardTitle;
	}

	public String getBoardContent() {
		return boardContent;
	}

	@Override
	public String toString() {
		return "HierarchicalBoardModifyDTO [boardNo=" + boardNo + ", boardTitle=" + boardTitle + ", boardContent="
				+ boardContent + "]";
	}
	
	public HierarchicalBoardModifyDTO() {
		
	}
	
	private HierarchicalBoardModifyDTO(HierarchicalBoardModifyDTOBuilder builder) {
		super();
		this.boardNo = builder.boardNo;
		this.boardTitle = builder.boardTitle;
		this.boardContent = builder.boardContent;
	}
	
	public static class HierarchicalBoardModifyDTOBuilder{
		private long boardNo;
		
		private String boardTitle;
		
		private String boardContent;
		
		public HierarchicalBoardModifyDTOBuilder boardNo(long boardNo) {
			this.boardNo = boardNo;
			return this;
		}
		
		public HierarchicalBoardModifyDTOBuilder boardTitle(String boardTitle) {
			this.boardTitle = boardTitle;
			return this;
		}
		
		public HierarchicalBoardModifyDTOBuilder boardContent(String boardContent) {
			this.boardContent = boardContent;
			return this;
		}
		
		public HierarchicalBoardModifyDTO build() {
			return new HierarchicalBoardModifyDTO(this);
		}
	}

}
