package boardProject_servlet_jsp_ver.com.domain.dto.imageBoard;

public class ImageBoardModifyDTO {

	private long imageNo;
	
	private String imageTitle;
	
	private String imageContent;
	
	private String userId;

	public long getImageNo() {
		return imageNo;
	}

	public String getImageTitle() {
		return imageTitle;
	}

	public String getImageContent() {
		return imageContent;
	}
	
	public String getUserId() {
		return userId;
	}

	@Override
	public String toString() {
		return "ImageBoardModifyDTO [imageNo=" + imageNo + ", imageTitle=" + imageTitle + ", imageContent="
				+ imageContent + ", userId=" + userId + "]";
	}
	
	public ImageBoardModifyDTO() {
		
	}
	
	private ImageBoardModifyDTO(ImageBoardModifyGetDTOBuilder builder) {
		super();
		this.imageNo = builder.imageNo;
		this.imageTitle = builder.imageTitle;
		this.imageContent = builder.imageContent;
		this.userId = builder.userId;
	}
	
	public static class ImageBoardModifyGetDTOBuilder{
		private long imageNo;
		
		private String imageTitle;
		
		private String imageContent;
		
		private String userId;
		
		public ImageBoardModifyGetDTOBuilder imageNo(long imageNo) {
			this.imageNo = imageNo;
			return this;
		}
		
		public ImageBoardModifyGetDTOBuilder imageTitle(String imageTitle) {
			this.imageTitle = imageTitle;
			return this;
		}
		
		public ImageBoardModifyGetDTOBuilder imageContent(String imageContent) {
			this.imageContent = imageContent;
			return this;
		}
		
		public ImageBoardModifyGetDTOBuilder userId(String userId) {
			this.userId = userId;
			return this;
		}
		
		public ImageBoardModifyDTO build() {
			return new ImageBoardModifyDTO(this);
		}
	}
}
