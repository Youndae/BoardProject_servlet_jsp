package boardProject_servlet_jsp_ver.com.service.dao;

import java.util.List;

import boardProject_servlet_jsp_ver.com.domain.dto.Criteria;
import boardProject_servlet_jsp_ver.com.domain.dto.imageBoard.ImageBoardDetailDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.imageBoard.ImageBoardInsertDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.imageBoard.ImageBoardListDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.imageBoard.ImageBoardModifyDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.imageBoard.ImageBoardModifyGetDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.imageBoard.ImageDataDTO;

public interface ImageBoardDao {
	
	public List<ImageBoardListDTO> imageList(Criteria cri);
	
	public int countList(Criteria cri);
	
	public ImageBoardDetailDTO getDetail(long imageNo);
	
	public ImageBoardModifyGetDTO getModifyData(long imageNo);
	
	public List<ImageDataDTO> getAttachList(long imageNo);
	
	public long insert(ImageBoardInsertDTO dto, List<ImageDataDTO> imageDTOList);
	
	public int getStep(long imageNo);
	
	public long modify(ImageBoardModifyDTO dto, List<ImageDataDTO> imageDTOList, List<String> deleteFileList);
	
	public List<String> getDeleteFileList(long imageNo);
	
	public String deleteBoard(long imageNo);

}
