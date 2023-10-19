package boardProject_servlet_jsp_ver.com.domain.dto.imageBoard;

import java.sql.Date;
import java.util.List;

import boardProject_servlet_jsp_ver.com.domain.dto.PageDTO;
import boardProject_servlet_jsp_ver.com.domain.entity.Comment;

public class ImageBoardDetailDTO {
	
	private long imageNo;
	
	private String userId;
	
	private String imageTitle;
	
	private Date imageDate;
	
	private List<String> imageList;
	
	private String imageContent;
	
	private List<Comment> commentList;
	
	private PageDTO pageMaker;

	public long getImageNo() {
		return imageNo;
	}

	public String getUserId() {
		return userId;
	}

	public String getImageTitle() {
		return imageTitle;
	}

	public Date getImageDate() {
		return imageDate;
	}

	public List<String> getImageList() {
		return imageList;
	}

	public String getImageContent() {
		return imageContent;
	}

	public List<Comment> getCommentList() {
		return commentList;
	}

	public PageDTO getPageMaker() {
		return pageMaker;
	}

	@Override
	public String toString() {
		return "ImageBoardDetailDTO [imageNo=" + imageNo + ", userId=" + userId + ", imageTitle=" + imageTitle
				+ ", imageDate=" + imageDate + ", imageList=" + imageList + ", imageContent=" + imageContent
				+ ", commentList=" + commentList + ", pageMaker=" + pageMaker + "]";
	}
	
	public ImageBoardDetailDTO() {
		
	}
	
	private ImageBoardDetailDTO(ImageBoardDetailDTOBuilder builder) {
		super();
		
		this.imageNo = builder.imageNo;
		this.userId = builder.userId;
		this.imageTitle = builder.imageTitle;
		this.imageDate = builder.imageDate;
		this.imageList = builder.imageList;
		this.imageContent = builder.imageContent;
		this.commentList = builder.commentList;
		this.pageMaker = builder.pageMaker;
	}
	
	public static class ImageBoardDetailDTOBuilder{
		private long imageNo;
		
		private String userId;
		
		private String imageTitle;
		
		private Date imageDate;
		
		private List<String> imageList;
		
		private String imageContent;
		
		private List<Comment> commentList;
		
		private PageDTO pageMaker;
		
		public ImageBoardDetailDTOBuilder imageNo(long imageNo) {
			this.imageNo = imageNo;
			return this;
		}
		
		public ImageBoardDetailDTOBuilder userId(String userId) {
			this.userId = userId;
			return this;
		}
		
		public ImageBoardDetailDTOBuilder imageTitle(String imageTitle) {
			this.imageTitle = imageTitle;
			return this;
		}
		
		public ImageBoardDetailDTOBuilder imageDate(Date imageDate) {
			this.imageDate = imageDate;
			return this;
		}
		
		public ImageBoardDetailDTOBuilder imageList(List<String> imageList) {
			this.imageList = imageList;
			return this;
		}
		
		public ImageBoardDetailDTOBuilder imageContent(String imageContent) {
			this.imageContent = imageContent;
			return this;
		}
		
		public ImageBoardDetailDTOBuilder commentList(List<Comment> commentList) {
			this.commentList = commentList;
			return this;
		}
		
		public ImageBoardDetailDTOBuilder pageMaker(PageDTO pageMaker) {
			this.pageMaker = pageMaker;
			return this;
		}
		
		public ImageBoardDetailDTO build() {
			return new ImageBoardDetailDTO(this);
		}
	}
	

}
