package boardProject_servlet_jsp_ver.com.service.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import boardProject_servlet_jsp_ver.com.domain.ResultProperties;
import boardProject_servlet_jsp_ver.com.domain.dto.Criteria;
import boardProject_servlet_jsp_ver.com.domain.dto.imageBoard.ImageBoardDetailDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.imageBoard.ImageBoardInsertDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.imageBoard.ImageBoardListDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.imageBoard.ImageBoardModifyDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.imageBoard.ImageBoardModifyGetDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.imageBoard.ImageDataDTO;
import boardProject_servlet_jsp_ver.com.jdbc.JDBCTemplate;

public class ImageBoardDaoImpl implements ImageBoardDao{
	
	private Connection con = null;

	@Override
	public List<ImageBoardListDTO> imageList(Criteria cri) {
		con = JDBCTemplate.getConnection();
		
		String sql = "SELECT ib.imageNo"
				+ ", ib.imageTitle"
				+ ", id.imageName "
				+ "FROM imageBoard ib "
				+ "INNER JOIN imageData id "
				+ "ON ib.imageNo = id.imageNo ";
		
		if(cri.getKeyword() != null)
			sql = setSearchSQL(cri.getSearchType(), cri.getKeyword(), sql);
		
		sql = sql.concat("ORDER BY imageNo DESC LIMIT ?, ?");
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ImageBoardListDTO> resultDTO = new ArrayList<ImageBoardListDTO>();
		
		try {	
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, (cri.getPageNum() - 1) * cri.getImageAmount());
			pstmt.setInt(2, cri.getImageAmount());
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				resultDTO.add(new ImageBoardListDTO.ImageBoardListDTOBuilder()
						.imageNo(rs.getLong("imageNo"))
						.imageName(rs.getString("imageName"))
						.imageTitle(rs.getString("imageTitle"))
						.build());
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}finally {
			JDBCTemplate.close(con);
			JDBCTemplate.close(pstmt);
			JDBCTemplate.close(rs);
		}
		
		return resultDTO;
	}
	
	public String setSearchSQL(String searchType, String keyword, String sql) {
		
		if(searchType != null) {
			switch(searchType) {
			case "t" :
				sql = sql.concat(" WHERE imageTitle LIKE '%" + keyword + "%'");
				break;
			case "c" :
				sql = sql.concat(" WHERE imageContent LIKE '%" + keyword + "%'");
				break;
			case "w" :
				sql = sql.concat(" WHERE userId LIKE '%" + keyword + "%'");
				break;
			case "tc" :
				sql = sql.concat(" WHERE imageTitle LIKE '%" + keyword + "%' or imageContent LIKE '%" + keyword + "%'");
				break;
			}
		}
		
		return sql;
	}

	@Override
	public int countList(Criteria cri) {
		con = JDBCTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT count(distinct(imageNo)) "
				+ "FROM imageBoard ";
		
		if(cri.getKeyword() != null)
			sql = setSearchSQL(cri.getSearchType(), cri.getKeyword(), sql);
		
		int result = 0;
		
		try {
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next())
				result = rs.getInt(1);
		}catch(SQLException e) {
			e.printStackTrace();
			return 0;
		}finally {
			JDBCTemplate.close(con);
			JDBCTemplate.close(pstmt);
			JDBCTemplate.close(rs);
		}
		
		return result;
	}

	@Override
	public ImageBoardDetailDTO getDetail(long imageNo) {
		
		con = JDBCTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT ib.userId"
				+ ", ib.imageTitle"
				+ ", ib.imageDate"
				+ ", ib.imageContent"
				+ ", id.imageName "
				+ "FROM imageBoard ib "
				+ "INNER JOIN imageData id "
				+ "ON ib.imageNo = id.imageNo "
				+ "WHERE ib.imageNo=?";
		
		ImageBoardDetailDTO resultDTO = null;
		
		
		String userId = null;
		String imageTitle = null;
		Date imageDate = null;
		String imageContent = null;
		List<String> imageList = new ArrayList<String>();
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, imageNo);
			
			rs = pstmt.executeQuery();
			
			int count = 0;
			while(rs.next()) {
				if(count == 0) {
					userId = rs.getString("userId");
					imageTitle = rs.getString("imageTitle");
					imageDate = rs.getDate("imageDate");
					imageContent = rs.getString("imageContent");
					
					imageList.add(rs.getString("imageName"));
					count = 1;
				}else {
					imageList.add(rs.getString("imageName"));
				}
			}
			
			resultDTO = new ImageBoardDetailDTO.ImageBoardDetailDTOBuilder()
					.imageNo(imageNo)
					.userId(userId)
					.imageTitle(imageTitle)
					.imageDate(imageDate)
					.imageContent(imageContent)
					.imageList(imageList)
					.build();
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}finally {
			JDBCTemplate.close(con);
			JDBCTemplate.close(pstmt);
			JDBCTemplate.close(rs);
		}
		
		return resultDTO;
	}

	@Override
	public ImageBoardModifyGetDTO getModifyData(long imageNo) {
		
		con = JDBCTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT imageNo"
				+ ", imageTitle"
				+ ", imageContent "
				+ "FROM imageboard "
				+ "WHERE imageNo=?";
		
		ImageBoardModifyGetDTO dto = new ImageBoardModifyGetDTO();
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, imageNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new ImageBoardModifyGetDTO.ImageBoardModifyGetDTOBuilder()
						.imageNo(imageNo)
						.imageTitle(rs.getString("imageTitle"))
						.imageContent(rs.getString("imageContent"))
						.build();
			}
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}finally {
			JDBCTemplate.close(con);
			JDBCTemplate.close(pstmt);
			JDBCTemplate.close(rs);
		}
		
		return dto;
	}

	@Override
	public List<ImageDataDTO> getAttachList(long imageNo) {
		
		con = JDBCTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT imageName"
				+ ", imageNo"
				+ ", oldName"
				+ ", imageStep "
				+ "FROM imageData "
				+ "WHERE imageNo=?";
		
		List<ImageDataDTO> dtoList = new ArrayList<ImageDataDTO>();
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, imageNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				dtoList.add(new ImageDataDTO.ImageDataDTOBuilder()
						.imageName(rs.getString("imageName"))
						.imageNo(rs.getLong("imageNo"))
						.oldName(rs.getString("oldName"))
						.imageStep(rs.getInt("imageStep"))
						.build());
			}
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}finally {
			JDBCTemplate.close(con);
			JDBCTemplate.close(pstmt);
			JDBCTemplate.close(rs);
		}
		
		return dtoList;
	}

	@Override
	public long insert(ImageBoardInsertDTO dto, List<ImageDataDTO> imageDTOList) {
		
		con = JDBCTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String boardSQL = "INSERT INTO imageBoard("
				+ "imageTitle"
				+ ", imageContent"
				+ ", userId"
				+ ", imageDate) "
				+ "VALUES("
				+ "?"
				+ ", ?"
				+ ", ?"
				+ ", sysdate())";
		
		String getImageNoSQL = "SELECT max(imageNo) "
				+ "FROM imageBoard "
				+ "WHERE userId=?"; 
		
		String imageSQL = "INSERT INTO imageData("
				+ "imageName"
				+ ", imageNo"
				+ ", oldName"
				+ ", imageStep) "
				+ "VALUES ";
		String imageValSQL = String.join(",", Collections.nCopies(imageDTOList.size(), "(?, ?, ?, ?)"));
		
		System.out.println("valSQL : " + imageValSQL);
		
		imageSQL = imageSQL.concat(imageValSQL);
		
		System.out.println("imageInsert DAO data SQL : " + imageSQL);
		System.out.println("list size : " + imageDTOList.size());
		
		long imageNo = 0;
		int columnCount = 1;
		
		try {
			pstmt = con.prepareStatement(boardSQL);
			pstmt.setString(1, dto.getImageTitle());
			pstmt.setString(2, dto.getImageContent());
			pstmt.setString(3, dto.getUserId());
			
			pstmt.executeUpdate();
			
			pstmt = con.prepareStatement(getImageNoSQL);
			pstmt.setString(1, dto.getUserId());
			
			rs = pstmt.executeQuery();
			
			if(rs.next())
				imageNo = rs.getInt(1);
			
			pstmt = con.prepareStatement(imageSQL);
			
			for(int i = 1; i <= imageDTOList.size(); i++) {
				pstmt.setString(columnCount++, imageDTOList.get(i - 1).getImageName());
				pstmt.setLong(columnCount++, imageNo);
				pstmt.setString(columnCount++, imageDTOList.get(i -1).getOldName());
				pstmt.setInt(columnCount++, imageDTOList.get(i - 1).getImageStep());
			}
			
			pstmt.executeUpdate();
			
			
			JDBCTemplate.commit(con);
			
		}catch(SQLException e) {
			e.printStackTrace();
			JDBCTemplate.rollback(con);
			return 0;
		}finally {
			JDBCTemplate.close(con);
			JDBCTemplate.close(pstmt);
			JDBCTemplate.close(rs);
		}
		
		return imageNo;
	}

	@Override
	public int getStep(long imageNo) {
		con = JDBCTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT max(imageStep) "
				+ "FROM imageData "
				+ "WHERE imageNo=?";
		
		int result = 0;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, imageNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next())
				result = rs.getInt(1);
			
		}catch(SQLException e) {
			e.printStackTrace();
			return 0;
		}finally {
			JDBCTemplate.close(con);
			JDBCTemplate.close(pstmt);
			JDBCTemplate.close(rs);
		}
		
		return result;
	}

	@Override
	public long modify(ImageBoardModifyDTO dto, List<ImageDataDTO> imageDTOList, List<String> deleteFileList) {
		con = JDBCTemplate.getConnection();
		PreparedStatement pstmt = null;
		
		String modifySQL = "UPDATE imageBoard SET "
				+ "imageTitle=?"
				+ ", imageContent=? "
				+ "WHERE imageNo=?";
		
		String imageSQL = "INSERT INTO imageData("
				+ "imageName"
				+ ", imageNo"
				+ ", oldName"
				+ ", imageStep) "
				+ "VALUES ";
		String imageValSQL = String.join(",", Collections.nCopies(imageDTOList.size(), "(?, ?, ?, ?)"));
		
		imageSQL = imageSQL.concat(imageValSQL);
		
		
		String deleteImageDataSQL = "DELETE FROM imageData "
				+ "WHERE imageName IN (%s)";
		
		String delteValSQL = String.join(",", Collections.nCopies(deleteFileList.size(), "?"));
		
		deleteImageDataSQL = String.format(deleteImageDataSQL, delteValSQL);
		
		int columnCount = 1;
		
		try {
			pstmt = con.prepareStatement(modifySQL);
			pstmt.setString(1, dto.getImageTitle());
			pstmt.setString(2, dto.getImageContent());
			pstmt.setLong(3, dto.getImageNo());
			
			pstmt.executeUpdate();
			
			pstmt = con.prepareStatement(imageSQL);
			for(int i = 1; i <= imageDTOList.size(); i++) {
				pstmt.setString(columnCount++, imageDTOList.get(i - 1).getImageName());
				pstmt.setLong(columnCount++, dto.getImageNo());
				pstmt.setString(columnCount++, imageDTOList.get(i -1).getOldName());
				pstmt.setInt(columnCount++, imageDTOList.get(i - 1).getImageStep());
			}
			
			pstmt.executeUpdate();
			
			pstmt = con.prepareStatement(deleteImageDataSQL);
			for(int i = 1; i <= deleteFileList.size(); i++)
				pstmt.setString(i, deleteFileList.get(i - 1));
			
			pstmt.executeUpdate();
			
			JDBCTemplate.commit(con);
			
		}catch(SQLException e) {
			e.printStackTrace();
			JDBCTemplate.rollback(con);
			
			return 0;
		}finally {
			JDBCTemplate.close(con);
			JDBCTemplate.close(pstmt);
		}
		
		
		return dto.getImageNo();
	}

	@Override
	public List<String> getDeleteFileList(long imageNo) {
		con = JDBCTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT imageName "
				+ "FROM imageData "
				+ "WHERE imageNo=?";
		
		List<String> deleteFileList = new ArrayList<String>();
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, imageNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				deleteFileList.add(rs.getString(1));
			}
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}finally {
			JDBCTemplate.close(con);
			JDBCTemplate.close(pstmt);
			JDBCTemplate.close(rs);
		}
		
		return deleteFileList;
	}

	@Override
	public String deleteBoard(long imageNo) {
		con = JDBCTemplate.getConnection();
		PreparedStatement pstmt = null;
		
		String sql = "DELETE FROM imageBoard "
				+ "WHERE imageNo=?";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, imageNo);
			
			pstmt.executeUpdate();
			
			JDBCTemplate.commit(con);
		}catch(SQLException e) {
			e.printStackTrace();
			JDBCTemplate.rollback(con);
			return ResultProperties.FAIL;
		}finally {
			JDBCTemplate.close(con);
			JDBCTemplate.close(pstmt);
		}
		return ResultProperties.SUCCESS;
	}
	
	
	
	

}
