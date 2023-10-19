package boardProject_servlet_jsp_ver.com.domain.entity;

public class ImageData {
	
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
		return "ImageData [imageName=" + imageName + ", imageNo=" + imageNo + ", oldName=" + oldName + ", imageStep="
				+ imageStep + "]";
	}
	
	public ImageData() {
		
	}
	
	private ImageData(ImageDataBuilder builder) {
		super();
		this.imageName = builder.imageName;
		this.imageNo = builder.imageNo;
		this.oldName = builder.oldName;
		this.imageStep = builder.imageStep;
	}
	
	public static class ImageDataBuilder {
		private String imageName;
		
		private long imageNo;
		
		private String oldName;
		
		private int imageStep;
		
		public ImageDataBuilder imageName(String imageName) {
			this.imageName = imageName;
			return this;
		}
		
		public ImageDataBuilder imageNo(long imageNo) {
			this.imageNo = imageNo;
			return this;
		}
		
		public ImageDataBuilder oldName(String oldName) {
			this.oldName = oldName;
			return this;
		}

		public ImageDataBuilder imageStep(int imageStep) {
			this.imageStep = imageStep;
			return this;
		}
		
		public ImageData build() {
			return new ImageData(this);
		}
	}

}
