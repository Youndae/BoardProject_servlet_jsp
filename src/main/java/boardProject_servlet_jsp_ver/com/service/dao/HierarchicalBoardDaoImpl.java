package boardProject_servlet_jsp_ver.com.service.dao;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import boardProject_servlet_jsp_ver.com.domain.dto.Criteria;
import boardProject_servlet_jsp_ver.com.domain.dto.hierarchicalBoard.HierarchicalBoardDeleteDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.hierarchicalBoard.HierarchicalBoardDetailDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.hierarchicalBoard.HierarchicalBoardInsertDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.hierarchicalBoard.HierarchicalBoardModifyDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.hierarchicalBoard.HierarchicalBoardReplyDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.hierarchicalBoard.HierarchicalBoardReplyProcDTO;
import boardProject_servlet_jsp_ver.com.domain.entity.HierarchicalBoard;
import boardProject_servlet_jsp_ver.com.jdbc.JDBCTemplate;


public class HierarchicalBoardDaoImpl implements HierarchicalBoardDao {

	private Connection con = null;
	
	@Override
	public List<HierarchicalBoard> boardList(Criteria cri) {
		
		con = JDBCTemplate.getConnection();
		
		String sql = "SELECT * "
				+ "FROM hierarchicalBoard";
		
		if(cri.getSearchType() != null)
			sql = setSearchSQL(cri.getSearchType(), cri.getKeyword(), sql);
		
		
		sql = sql.concat(" ORDER BY boardGroupNo desc, boardUpperNo asc "
							+ "limit " + ((cri.getPageNum() - 1) * cri.getBoardAmount()) + ", " + cri.getBoardAmount());
		
		List<HierarchicalBoard> boardList = new ArrayList<HierarchicalBoard>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery(sql);
			
			while(rs.next()) {
				
				boardList.add(new HierarchicalBoard.HierarchicalBoardBuilder()
						.boardNo(rs.getLong("boardNo"))
						.boardTitle(rs.getString("boardTitle"))
						.userId(rs.getString("userId"))
						.boardContent(rs.getString("boardContent"))
						.boardDate(rs.getDate("boardDate"))
						.boardGroupNo(rs.getLong("boardGroupNo"))
						.boardIndnet(rs.getInt("boardIndent"))
						.boardUpperNo(rs.getString("boardupperNo"))
						.build()
						);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}finally {
			JDBCTemplate.close(con);
			JDBCTemplate.close(pstmt);
			JDBCTemplate.close(rs);
		}
		
		
		return boardList;
	}

	@Override
	public int countList(Criteria cri) {
		con = JDBCTemplate.getConnection();
		String sql = "SELECT count(distinct(boardNo)) FROM hierarchicalBoard";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		if(cri.getSearchType() != null)
			sql = setSearchSQL(cri.getSearchType(), cri.getKeyword(), sql);
		
		
		int result = 0;
		
		try {
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
			}
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
	
	public String setSearchSQL(String searchType, String keyword, String sql) {
		
		if(searchType != null) {
			switch(searchType) {
			case "t" :
				sql = sql.concat(" WHERE boardTitle LIKE '%" + keyword + "%'");
				break;
			case "c" :
				sql = sql.concat(" WHERE boardContent LIKE '%" + keyword + "%'");
				break;
			case "w" :
				sql = sql.concat(" WHERE userId LIKE '%" + keyword + "%'");
				break;
			case "tc" :
				sql = sql.concat(" WHERE boardTitle LIKE '%" + keyword + "%' or boardContent LIKE '%" + keyword + "%'");
				break;
			}
		}
		
		return sql;
	}

	@Override
	public long boardInsert(HierarchicalBoardInsertDTO dto) {
		
		con = JDBCTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String insertSql = "INSERT INTO hierarchicalBoard("
											+ "boardTitle"
											+ ", userId"
											+ ", boardContent"
											+ ", boardDate"
											+ ", boardGroupNo"
											+ ", boardIndent"
											+ ", boardUpperNo) "
								+ "VALUES("
											+ "?"
											+ ", ?"
											+ ", ?"
											+ ", sysdate()"
											+ ", ?"
											+ ", ?"
											+ ", ?)";
		
		String getPrimarykey = "SELECT max(boardNo) FROM hierarchicalBoard WHERE userId=?";
		
		String updateSql = "UPDATE hierarchicalBoard SET "
				+ "boardGroupNo = ?"
				+ ", boardUpperNo = ?"
				+ "WHERE boardNo = ?";
		
		long insertBoardNo = 0;
		
		try {
			pstmt = con.prepareStatement(insertSql);
			pstmt.setString(1, dto.getBoardTitle());
			pstmt.setString(2, dto.getUserId());
			pstmt.setString(3, dto.getBoardContent());
			pstmt.setLong(4, 0);
			pstmt.setInt(5, 0);
			pstmt.setString(6, "");
			
			pstmt.executeUpdate();
			
			pstmt = con.prepareStatement(getPrimarykey);
			pstmt.setString(1, dto.getUserId());
			
			rs = pstmt.executeQuery();
			
			if(rs.next())
				insertBoardNo = rs.getLong(1);
			
			JDBCTemplate.close(rs);
			
			System.out.println("insertNo : " + insertBoardNo);
			
			pstmt = con.prepareStatement(updateSql);
			pstmt.setLong(1, insertBoardNo);
			pstmt.setString(2, String.valueOf(insertBoardNo));
			pstmt.setLong(3, insertBoardNo);
			
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
		
		return insertBoardNo;
	}

	@Override
	public HierarchicalBoardDetailDTO boardDetail(long boardNo) {
		
		String sql = "SELECT boardNo"
				+ ", boardTitle"
				+ ", boardContent"
				+ ", userId"
				+ ", boardDate "
				+ "FROM hierarchicalBoard "
				+ "WHERE boardNo = ?";
		
		con = JDBCTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		HierarchicalBoardDetailDTO dto = new HierarchicalBoardDetailDTO();
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, boardNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new HierarchicalBoardDetailDTO.HierarchicalBoardDetailDTOBuilder()
						.boardNo(rs.getLong("boardNo"))
						.boardTitle(rs.getString("boardTitle"))
						.userId(rs.getString("userId"))
						.boardContent(rs.getString("boardContent"))
						.boardDate(rs.getDate("boardDate"))
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
	public String boardDelete(List<Long> deleteNoList) {
		String sql = "DELETE FROM hierarchicalBoard WHERE boardNo IN (%s)";
		String inSql = String.join(",", Collections.nCopies(deleteNoList.size(), "?"));
		sql = String.format(sql, inSql);
		con = JDBCTemplate.getConnection();
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			for(int i = 1; i <= deleteNoList.size(); i++) 
				pstmt.setLong(i, deleteNoList.get(i - 1));
			
			pstmt.executeUpdate();
			
			JDBCTemplate.commit(con);
			
		}catch(SQLException e) {
			e.printStackTrace();
			
			JDBCTemplate.rollback(con);
			
			return null;
		}finally {
			JDBCTemplate.close(con);
			JDBCTemplate.close(pstmt);
		}
		
		
		return "success";
		
	}

	@Override
	public String checkWriter(long boardNo) {
		
		String sql = "SELECT userId FROM hierarchicalBoard WHERE boardNo=?";
		String result = null;
		con = JDBCTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, boardNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) 
				result = rs.getString(1);
			
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}finally {
			JDBCTemplate.close(con);
			JDBCTemplate.close(pstmt);
			JDBCTemplate.close(rs);
		}
		
		
		return result;
	}

	@Override
	public String deleteBoardGroup(long boardGroupNo) {
		String sql = "DELETE FROM hierarchicalBoard "
				+ "WHERE boardGroupNo=?";
		PreparedStatement pstmt = null;
		con = JDBCTemplate.getConnection();
		
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, boardGroupNo);
			
			pstmt.executeUpdate();
			
			JDBCTemplate.commit(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			JDBCTemplate.rollback(con);
			
			return null;
		}finally {
			JDBCTemplate.close(con);
			JDBCTemplate.close(pstmt);
		}
		
		return "success";
		
	}
	
	
	@Override
	public HierarchicalBoardDeleteDTO checkDeleteNo(long boardNo) {
		
		HierarchicalBoardDeleteDTO dto = new HierarchicalBoardDeleteDTO();
		
		String sql = "SELECT boardNo"
				+ ", boardUpperNo"
				+ ", boardGroupNo"
				+ ", boardIndent "
				+ "FROM hierarchicalBoard "
				+ "WHERE boardNo=?";
		
		con = JDBCTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, boardNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new HierarchicalBoardDeleteDTO.HierarchicalBoardDeleteDTOBuilder()
						.boardNo(rs.getLong("boardNo"))
						.boardUpperNo(rs.getString("boardUpperNo"))
						.boardGroupNo(rs.getLong("boardGroupNo"))
						.boardIndent(rs.getInt("boardIndent"))
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
	public List<HierarchicalBoardDeleteDTO> getDeleteGroup(long boardGroupNo){
		
		List<HierarchicalBoardDeleteDTO> deleteGroupList = new ArrayList<HierarchicalBoardDeleteDTO>();
		String sql = "SELECT boardNo"
				+ ", boardGroupNo"
				+ ", boardUpperNo"
				+ ", boardIndent "
				+ "FROM hierarchicalBoard "
				+ "WHERE boardGroupNo=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		con = JDBCTemplate.getConnection();
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, boardGroupNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				deleteGroupList.add(new HierarchicalBoardDeleteDTO.HierarchicalBoardDeleteDTOBuilder()
											.boardNo(rs.getLong("boardNo"))
											.boardUpperNo(rs.getString("boardUpperNo"))
											.boardGroupNo(rs.getLong("boardGroupNo"))
											.boardIndent(rs.getInt("boardIndent"))
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
		
		return deleteGroupList;
		
	}

	@Override
	public HierarchicalBoardModifyDTO getBoardModifyData(long boardNo) {
		String sql = "SELECT boardNo"
				+ ", boardTitle"
				+ ", boardContent "
				+ "FROM hierarchicalBoard "
				+ "WHERE boardNo=?";
		
		con = JDBCTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		HierarchicalBoardModifyDTO dto = new HierarchicalBoardModifyDTO();
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, boardNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new HierarchicalBoardModifyDTO.HierarchicalBoardModifyDTOBuilder()
						.boardNo(rs.getLong("boardNo"))
						.boardTitle(rs.getString("boardTitle"))
						.boardContent(rs.getString("boardContent"))
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
	public long boardModify(HierarchicalBoardModifyDTO dto) {
		String sql = "UPDATE hierarchicalBoard SET "
				+ "boardTitle = ?"
				+ ", boardContent = ? "
				+ "WHERE boardNo = ?";
		
		con = JDBCTemplate.getConnection();
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getBoardTitle());
			pstmt.setString(2, dto.getBoardContent());
			pstmt.setLong(3, dto.getBoardNo());
			
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
		
		return dto.getBoardNo();
	}

	@Override
	public HierarchicalBoardReplyDTO getBoardReply(long boardNo) {
		String sql = "SELECT boardNo"
				+ ", boardIndent"
				+ ", boardGroupNo"
				+ ", boardUpperNo "
				+ "FROM hierarchicalBoard "
				+ "WHERE boardNo=?";
		
		con = JDBCTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		HierarchicalBoardReplyDTO dto = new HierarchicalBoardReplyDTO();
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, boardNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new HierarchicalBoardReplyDTO.HierarchicalBoardReplyDTOBuilder()
						.boardNo(rs.getLong("boardNo"))
						.boardIndent(rs.getInt("boardIndent"))
						.boardGroupNo(rs.getLong("boardGroupNo"))
						.boardUpperNo(rs.getString("boardUpperNo"))
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
	public long boardReply(HierarchicalBoardReplyProcDTO dto) {
		String insertSql = "INSERT INTO hierarchicalBoard("
				+ "boardTitle"
				+ ", userId"
				+ ", boardContent"
				+ ", boardDate"
				+ ", boardGroupNo"
				+ ", boardIndent"
				+ ", boardUpperNo) "
				+ "VALUES("
				+ "?"
				+ ", ?"
				+ ", ?"
				+ ", sysdate()"
				+ ", ?"
				+ ", ?"
				+ ", ?)";
		
		con = JDBCTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String getPrimarykey = "SELECT max(boardNo)"
				+ "FROM hierarchicalBoard "
				+ "WHERE userId = ?";
		
		String updateSql = "UPDATE hierarchicalBoard SET "
				+ "boardUpperNo = ? "
				+ "WHERE boardNo = ?";
		
		long boardNo = 0;
		
		
		try {
			pstmt = con.prepareStatement(insertSql);
			pstmt.setString(1, dto.getBoardTitle());
			pstmt.setString(2, dto.getUserId());
			pstmt.setString(3, dto.getBoardContent());
			pstmt.setLong(4, dto.getBoardGroupNo());
			pstmt.setInt(5, dto.getBoardIndent());
			pstmt.setString(6, dto.getBoardUpperNo());
			
			pstmt.executeUpdate();
			
			pstmt = con.prepareStatement(getPrimarykey);
			pstmt.setString(1, dto.getUserId());
			
			rs = pstmt.executeQuery();
			
			if(rs.next())
				boardNo = rs.getLong(1);
			
			JDBCTemplate.close(rs);
			
			pstmt = con.prepareStatement(updateSql);
			pstmt.setString(1, dto.getBoardUpperNo() + boardNo);
			pstmt.setLong(2, boardNo);
			
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
		
		
		return boardNo;
	}

	

}
