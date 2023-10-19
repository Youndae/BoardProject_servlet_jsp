package boardProject_servlet_jsp_ver.com.domain.dto.imageBoard;

public class ImageDataDTO {
	
	private String imageName;
	
	private long imageNo;
	
	private String oldName;
	
	private int imageStep;

	public String getImageName() {
		return imageName;
	}

	public long getImageNo() {
		return imageNo;
	}

	public String getOldName() {
		return oldName;
	}

	public int getImageStep() {
		return imageStep;
	}
	
	@Override
	public String toString() {
		return "ImageDataDTO [imageName=" + imageName + ", imageNo=" + imageNo + ", oldName=" + oldName + ", imageStep="
				+ imageStep + "]";
	}
	
	public ImageDataDTO() {
		
	}
	
	private ImageDataDTO(ImageDataDTOBuilder builder) {
		super();
		this.imageName = builder.imageName;
		this.imageNo = builder.imageNo;
		this.oldName = builder.oldName;
		this.imageStep = builder.imageStep;
	}
	
	public static class ImageDataDTOBuilder{
		private String imageName;
		
		private long imageNo;
		
		private String oldName;
		
		private int imageStep;
		
		public ImageDataDTOBuilder imageName(String imageName) {
			this.imageName = imageName;
			return this;
		}
		
		public ImageDataDTOBuilder imageNo(long imageNo) {
			this.imageNo = imageNo;
			return this;
		}
		
		public ImageDataDTOBuilder oldName(String oldName) {
			this.oldName = oldName;
			return this;
		}
		
		public ImageDataDTOBuilder imageStep(int imageStep) {
			this.imageStep = imageStep;
			return this;
		}
		
		public ImageDataDTO build() {
			return new ImageDataDTO(this);
		}
	}

}
