package boardProject_servlet_jsp_ver.com.service.service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import boardProject_servlet_jsp_ver.com.domain.FileProperties;
import boardProject_servlet_jsp_ver.com.domain.ResultProperties;
import boardProject_servlet_jsp_ver.com.domain.dto.Criteria;
import boardProject_servlet_jsp_ver.com.domain.dto.PageDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.imageBoard.ImageBoardDetailDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.imageBoard.ImageBoardInsertDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.imageBoard.ImageBoardListDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.imageBoard.ImageBoardModifyDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.imageBoard.ImageBoardModifyGetDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.imageBoard.ImageDataDTO;
import boardProject_servlet_jsp_ver.com.domain.entity.Comment;
import boardProject_servlet_jsp_ver.com.service.dao.CommentDao;
import boardProject_servlet_jsp_ver.com.service.dao.CommentDaoImpl;
import boardProject_servlet_jsp_ver.com.service.dao.ImageBoardDao;
import boardProject_servlet_jsp_ver.com.service.dao.ImageBoardDaoImpl;

public class ImageBoardServiceImpl implements ImageBoardService{

	private ImageBoardDao imageBoardDAO = new ImageBoardDaoImpl();
	
	private CommentDao commentDAO = new CommentDaoImpl();

	@Override
	public List<ImageBoardListDTO> boardList(HttpServletRequest request) {
		
		Criteria cri = new Criteria();
		
		String pageNum = request.getParameter("pageNum");
		String keyword = request.getParameter("keyword");
		
		if(pageNum != null) 
			cri.setPageNum(Integer.parseInt(pageNum));
		
		if(keyword != null) {
			cri.setKeyword(keyword);
			cri.setSearchType(request.getParameter("searchType"));
		}

		return imageBoardDAO.imageList(cri);
	}

	@Override
	public PageDTO setPageDTO(HttpServletRequest request) {
		Criteria cri = setCriteria(request);
		
		int totalPages = (int) (Math.ceil((imageBoardDAO.countList(cri) * 1.0) / cri.getImageAmount()));
		
		if(totalPages == 0)
			return null;
		
		PageDTO dto = new PageDTO(cri, totalPages);
		
		return dto;
	}
	
	public Criteria setCriteria(HttpServletRequest request) {
		Criteria cri = new Criteria();
		
		String pageNum = request.getParameter("pageNum");
		String keyword = request.getParameter("keyword");
		String searchType = request.getParameter("searchType");
		
		if(pageNum != null)
			cri.setPageNum(Integer.parseInt(pageNum));
		
		if(keyword != null) {
			cri.setKeyword(keyword);
			cri.setSearchType(searchType);
		}
		
		return cri;
	}

	@Override
	public ImageBoardDetailDTO getDetail(HttpServletRequest request) {
		
		Criteria cri = new Criteria();
		long imageNo = Long.parseLong(request.getParameter("imageNo"));
		ImageBoardDetailDTO dto = imageBoardDAO.getDetail(imageNo);
		
		List<Comment> commentList = commentDAO.getImageComment(imageNo, cri);
		
		int totalPages = (int) Math.ceil((commentDAO.commentListCount(0, imageNo) * 1.0) / cri.getBoardAmount());
		
		ImageBoardDetailDTO resultDTO = new ImageBoardDetailDTO.ImageBoardDetailDTOBuilder()
				.imageNo(dto.getImageNo())
				.userId(dto.getUserId())
				.imageTitle(dto.getImageTitle())
				.imageDate(dto.getImageDate())
				.imageList(dto.getImageList())
				.imageContent(dto.getImageContent())
				.commentList(commentList)
				.pageMaker(new PageDTO(cri, totalPages))
				.build();
		
		return resultDTO;
	}

	@Override
	public ImageBoardModifyGetDTO getModify(HttpServletRequest request) {
		
		long imageNo = Long.parseLong(request.getParameter("imageNo"));
		
		return imageBoardDAO.getModifyData(imageNo);
	}

	@Override
	public List<ImageDataDTO> getAttachList(HttpServletRequest request) {
		
		long imageNo = Long.parseLong(request.getParameter("imageNo"));
		
		System.out.println("attachList imageNo : " + imageNo);
		
		return imageBoardDAO.getAttachList(imageNo);
	}

	@Override
	public long insert(HttpServletRequest request) {
		
		ImageBoardInsertDTO dto = new ImageBoardInsertDTO();
		List<ImageDataDTO> imageDTOList = new ArrayList<ImageDataDTO>();
		String title = null;
		String content = null;
		HttpSession session = request.getSession();
		String uid = (String) session.getAttribute("id");
		
		try {
			DiskFileItemFactory diskFactory = new DiskFileItemFactory();
			diskFactory.setSizeThreshold(4096);
			diskFactory.setRepository(new File(FileProperties.TEMP_PATH));
			
			ServletFileUpload upload = new ServletFileUpload(diskFactory);
			
			upload.setSizeMax(FileProperties.FILE_SIZE);
			
			List<FileItem> items = upload.parseRequest(request);
			
			Iterator<FileItem> iter = items.iterator();
			
			int step = 0;
			
			while(iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				
				if(item.isFormField()) {
					String fieldName = item.getFieldName();
					
					if(fieldName.equals("imageTitle"))
						title = item.getString("UTF-8");
					else if(fieldName.equals("imageContent"))
						content = item.getString("UTF-8");
					
				}else {
					if(item.getSize() > 0) {
						/*String name = item.getFieldName();
						String fileName = item.getName();
						
						StringBuffer sb = new StringBuffer();
						String saveName = sb.append(new SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis()))
								.append(UUID.randomUUID())
								.append(fileName.substring(fileName.lastIndexOf("."))).toString();
						
						Path filePath = Paths.get(FileProperties.FILE_PATH + "/" + saveName);
						File uploadFile = filePath.toFile();
						item.write(uploadFile);
								
						
						imageDTOList.add(new ImageDataDTO.ImageDataDTOBuilder()
								.imageName(saveName)
								.oldName(fileName)
								.imageStep(step)
								.build());
						step++;*/
						saveFile(item, imageDTOList, step);
					}
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
		
		dto = new ImageBoardInsertDTO.ImageBoardInsertDTOBuilder()
				.imageTitle(title)
				.imageContent(content)
				.userId(uid)
				.build();
		
		System.out.println("service list : " + imageDTOList);
		
		
		long result = imageBoardDAO.insert(dto, imageDTOList);
		
		
		
		return result;
	}
	
	public void saveFile(FileItem item, List<ImageDataDTO> imageDTOList, int step) {
		
		try {
			String name = item.getFieldName();
			String fileName = item.getName();
			
			StringBuffer sb = new StringBuffer();
			String saveName = sb.append(new SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis()))
					.append(UUID.randomUUID())
					.append(fileName.substring(fileName.lastIndexOf("."))).toString();
			
			Path filePath = Paths.get(FileProperties.FILE_PATH + "/" + saveName);
			File uploadFile = filePath.toFile();
			item.write(uploadFile);
			
			imageDTOList.add(new ImageDataDTO.ImageDataDTOBuilder()
					.imageName(saveName)
					.oldName(fileName)
					.imageStep(step)
					.build());
			step++;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public long modify(HttpServletRequest request) {
		
		String title = null;
		String content = null;
		String imageNo = null;
		List<String> deleteFileList = new ArrayList<String>();
		List<ImageDataDTO> imageDTOList = new ArrayList<ImageDataDTO>();
		ImageBoardModifyDTO dto = new ImageBoardModifyDTO();
		HttpSession session = request.getSession();
		String uid = (String) session.getAttribute("id");
		
		int step = 0;
		
		
		try {
			DiskFileItemFactory diskFactory = new DiskFileItemFactory();
			diskFactory.setSizeThreshold(4096);
			diskFactory.setRepository(new File(FileProperties.TEMP_PATH));
			
			ServletFileUpload upload = new ServletFileUpload(diskFactory);
			
			upload.setSizeMax(FileProperties.FILE_SIZE);
			
			List<FileItem> items = upload.parseRequest(request);
			
			Iterator<FileItem> iter = items.iterator();
			
			while(iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				
				if(item.isFormField()) {
					String fieldName = item.getFieldName();
					
					if(fieldName.equals("imageTitle"))
						title = item.getString("UTF-8");
					else if(fieldName.equals("imageContent"))
						content = item.getString("UTF-8");
					else if(fieldName.equals("imageNo")) {
						imageNo = item.getString("UTF-8");
						step = imageBoardDAO.getStep(Long.parseLong(imageNo)) + 1;
						
						if(step == 1)
							throw new Exception();
					}else if(fieldName.equals("deleteFiles"))
						deleteFileList.add(item.getString("UTF-8"));
					
				}else {
					if(item.getSize() > 0) 
						saveFile(item, imageDTOList, step);
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
		
		dto = new ImageBoardModifyDTO.ImageBoardModifyDTOBuilder()
				.imageNo(Long.parseLong(imageNo))
				.imageTitle(title)
				.imageContent(content)
				.userId(uid)
				.build();
		
		long result = imageBoardDAO.modify(dto, imageDTOList, deleteFileList);
		
		if(result == 0)
			return 0;
		else {
			deleteFiles(deleteFileList);
			return result;
		}
		
	}
	
	public void deleteFiles(List<String> deleteFileList) {
		String filePath = FileProperties.FILE_PATH;
		
		if(deleteFileList.size() != 0) {
			for(int i = 0; i < deleteFileList.size(); i++) {
				String deleteFileName = deleteFileList.get(i);
				File file = new File(filePath + deleteFileName);
				if(file.exists())
					file.delete();
			}
		}
			
	}

	@Override
	public String boardDelete(HttpServletRequest request) {
		long imageNo = Long.parseLong(request.getParameter("imageNo"));
		HttpSession session = request.getSession();
		String uid = (String) session.getAttribute("id");
		
		System.out.println("uid : " + uid);
		
		if(!uid.equals("admin")) {
			System.out.println("auth fail");
			return ResultProperties.FAIL;
		}
			
		
		List<String> deleteFileList = imageBoardDAO.getDeleteFileList(imageNo);
		
		String deleteResult = imageBoardDAO.deleteBoard(imageNo);
		
		if(deleteResult.equals(ResultProperties.FAIL) || deleteFileList == null) {
			System.out.println("getData Fail");
			return ResultProperties.FAIL;
		}else {
			deleteFiles(deleteFileList);
			System.out.println("delete Fail");
			return ResultProperties.SUCCESS;
		}
		
	}
	
	
	
	
}
