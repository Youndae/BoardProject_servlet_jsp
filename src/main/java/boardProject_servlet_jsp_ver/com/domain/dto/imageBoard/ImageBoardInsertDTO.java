package boardProject_servlet_jsp_ver.com.domain.dto.imageBoard;

public class ImageBoardInsertDTO {
	
	private String imageTitle;
	
	private String userId;
	
	private String imageContent;

	public String getImageTitle() {
		return imageTitle;
	}

	public String getUserId() {
		return userId;
	}

	public String getImageContent() {
		return imageContent;
	}

	@Override
	public String toString() {
		return "ImageBoardInsertDTO [imageTitle=" + imageTitle + ", userId=" + userId + ", imageContent=" + imageContent
				+ "]";
	}
	
	
	public ImageBoardInsertDTO() {
		
	}
	
	private ImageBoardInsertDTO(ImageBoardInsertDTOBuilder builder) {
		super();
		this.imageTitle = builder.imageTitle;
		this.userId = builder.userId;
		this.imageContent = builder.imageContent;
	}
	
	public static class ImageBoardInsertDTOBuilder {
		private String imageTitle;
		
		private String userId;
		
		private String imageContent;
		
		public ImageBoardInsertDTOBuilder imageTitle(String imageTitle) {
			this.imageTitle = imageTitle;
			return this;
		}
		
		public ImageBoardInsertDTOBuilder userId(String userId) {
			this.userId = userId;
			return this;
		}
		
		public ImageBoardInsertDTOBuilder imageContent(String imageContent) {
			this.imageContent = imageContent;
			return this;
		}
		
		public ImageBoardInsertDTO build() {
			return new ImageBoardInsertDTO(this);
		}
	}

}
