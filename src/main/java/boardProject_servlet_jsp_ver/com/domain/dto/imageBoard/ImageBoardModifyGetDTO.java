package boardProject_servlet_jsp_ver.com.domain.dto.imageBoard;

public class ImageBoardModifyGetDTO {

	private long imageNo;
	
	private String imageTitle;
	
	private String imageContent;

	public long getImageNo() {
		return imageNo;
	}

	public String getImageTitle() {
		return imageTitle;
	}

	public String getImageContent() {
		return imageContent;
	}

	@Override
	public String toString() {
		return "ImageBoardModifyDTO [imageNo=" + imageNo + ", imageTitle=" + imageTitle + ", imageContent="
				+ imageContent + "]";
	}
	
	public ImageBoardModifyGetDTO() {
		
	}
	
	private ImageBoardModifyGetDTO(ImageBoardModifyGetDTOBuilder builder) {
		super();
		this.imageNo = builder.imageNo;
		this.imageTitle = builder.imageTitle;
		this.imageContent = builder.imageContent;
	}
	
	public static class ImageBoardModifyGetDTOBuilder{
		private long imageNo;
		
		private String imageTitle;
		
		private String imageContent;
		
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
		
		public ImageBoardModifyGetDTO build() {
			return new ImageBoardModifyGetDTO(this);
		}
	}
}
