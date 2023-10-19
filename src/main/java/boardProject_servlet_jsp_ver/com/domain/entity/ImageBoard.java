package boardProject_servlet_jsp_ver.com.domain.entity;

import java.sql.Date;

public class ImageBoard {

	private long imageNo;
	
	private String imageTitle;
	
	private String userId;
	
	private Date imageDate;
	
	private String imageContent;

	public long getImageNo() {
		return imageNo;
	}

	public String getImageTitle() {
		return imageTitle;
	}

	public String getUserId() {
		return userId;
	}

	public Date getImageDate() {
		return imageDate;
	}

	public String getImageContent() {
		return imageContent;
	}

	@Override
	public String toString() {
		return "ImageBoard [imageNo=" + imageNo + ", imageTitle=" + imageTitle + ", userId=" + userId + ", imageDate="
				+ imageDate + ", imageContent=" + imageContent + "]";
	}
	
	public ImageBoard() {
		
	}
	
	private ImageBoard(ImageBoardBuilder builder) {
		super();
		this.imageNo = builder.imageNo;
		this.imageTitle = builder.imageTitle;
		this.userId = builder.userId;
		this.imageDate = builder.imageDate;
		this.imageContent = builder.imageContent;
	}
	
	public static class ImageBoardBuilder{
		private long imageNo;
		
		private String imageTitle;
		
		private String userId;
		
		private Date imageDate;
		
		private String imageContent;
		
		public ImageBoardBuilder imageNo (long imageNo) {
			this.imageNo = imageNo;
			return this;
		}
		
		public ImageBoardBuilder imageTitle (String imageTitle) {
			this.imageTitle = imageTitle;
			return this;
		}
		
		public ImageBoardBuilder userId (String userId) {
			this.userId = userId;
			return this;
		}
		
		public ImageBoardBuilder imageDate (Date imageDate) {
			this.imageDate = imageDate;
			return this;
		}
		
		public ImageBoardBuilder imageContent (String imageContent) {
			this.imageContent = imageContent;
			return this;
		}
		
		public ImageBoard build() {
			return new ImageBoard(this);
		}
	}
}
