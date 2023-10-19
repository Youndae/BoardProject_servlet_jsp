package boardProject_servlet_jsp_ver.com.domain.dto;

public class Criteria {
	
	private int pageNum;
	
	private int boardAmount;
	
	private int imageAmount;
	
	private String keyword;
	
	private String searchType;
	
	public Criteria() {
		this(1, 20, 15);
	}
	
	public Criteria(int pageNum, int boardAmount, int imageAmount) {
		this.pageNum = pageNum;
		this.boardAmount = boardAmount;
		this.imageAmount = imageAmount;
	}

	public Criteria(int pageNum, int boardAmount, int imageAmount, String keyword, String searchType) {
		super();
		this.pageNum = pageNum;
		this.boardAmount = boardAmount;
		this.imageAmount = imageAmount;
		this.keyword = keyword;
		this.searchType = searchType;
	}

	@Override
	public String toString() {
		return "Criteria [pageNum=" + pageNum + ", boardAmount=" + boardAmount + ", imageAmount=" + imageAmount
				+ ", keyword=" + keyword + ", searchType=" + searchType + "]";
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getBoardAmount() {
		return boardAmount;
	}

	public void setBoardAmount(int boardAmount) {
		this.boardAmount = boardAmount;
	}

	public int getImageAmount() {
		return imageAmount;
	}

	public void setImageAmount(int imageAmount) {
		this.imageAmount = imageAmount;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	
	

}
