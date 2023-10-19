package boardProject_servlet_jsp_ver.com.domain.dto.imageBoard;

public class ImageBoardListDTO {
	
	private long imageNo;
	
	private String imageName;
	
	private String imageTitle;

	public long getImageNo() {
		return imageNo;
	}

	public String getImageName() {
		return imageName;
	}

	public String getImageTitle() {
		return imageTitle;
	}

	@Override
	public String toString() {
		return "ImageBoardListDTO [imageNo=" + imageNo + ", imageName=" + imageName + ", imageTitle=" + imageTitle
				+ "]";
	}
	
	public ImageBoardListDTO() {
		
	}
	
	private ImageBoardListDTO(ImageBoardListDTOBuilder builder) {
		super();
		this.imageNo = builder.imageNo;
		this.imageName = builder.imageName;
		this.imageTitle = builder.imageTitle;
	}
	
	public static class ImageBoardListDTOBuilder {
		private long imageNo;
		
		private String imageName;
		
		private String imageTitle;
		
		public ImageBoardListDTOBuilder imageNo(long imageNo) {
			this.imageNo = imageNo;
			return this;
		}
		
		public ImageBoardListDTOBuilder imageName(String imageName) {
			this.imageName = imageName;
			return this;
		}
		
		public ImageBoardListDTOBuilder imageTitle(String imageTitle) {
			this.imageTitle = imageTitle;
			return this;
		}
		
		public ImageBoardListDTO build() {
			return new ImageBoardListDTO(this);
		}
	}

}
