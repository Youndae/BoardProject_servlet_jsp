package boardProject_servlet_jsp_ver.com.domain.dto;

public class PageDTO {
	
	private int startPage;
	
	private int endPage;
	
	private boolean prev, next;
	
	private Criteria cri;
	
	public PageDTO(Criteria cri, int totalPages) {
		this.cri = cri;
		
		this.endPage = (int) (Math.ceil(cri.getPageNum() / 5.0) * 5);
		
		this.startPage = this.endPage - 4;
		
		int realEnd = totalPages;
		
		if(realEnd < this.endPage)
			this.endPage = realEnd;
		
		this.prev = this.startPage > 1;
		
		this.next = this.endPage < realEnd;
	}

	public int getStartPage() {
		return startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public boolean isPrev() {
		return prev;
	}

	public boolean isNext() {
		return next;
	}

	public Criteria getCri() {
		return cri;
	}

	@Override
	public String toString() {
		return "PageDTO [startPage=" + startPage + ", endPage=" + endPage + ", prev=" + prev + ", next=" + next
				+ ", cri=" + cri + "]";
	}
	
	
	
}
