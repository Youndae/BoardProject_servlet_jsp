package boardProject_servlet_jsp_ver.com.service.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import boardProject_servlet_jsp_ver.com.domain.dto.PageDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.imageBoard.ImageBoardDetailDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.imageBoard.ImageBoardListDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.imageBoard.ImageBoardModifyGetDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.imageBoard.ImageDataDTO;

public interface ImageBoardService {
	
	public List<ImageBoardListDTO> boardList(HttpServletRequest request);
	
	public PageDTO setPageDTO(HttpServletRequest request);
	
	public ImageBoardDetailDTO getDetail(HttpServletRequest request);
	
	public ImageBoardModifyGetDTO getModify(HttpServletRequest request);
	
	public List<ImageDataDTO> getAttachList(HttpServletRequest request);
	
	public long insert(HttpServletRequest request);
	
	public long modify(HttpServletRequest request);
	
	public String boardDelete(HttpServletRequest request);

}
