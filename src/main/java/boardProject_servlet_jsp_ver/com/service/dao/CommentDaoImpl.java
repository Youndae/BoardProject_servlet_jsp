package boardProject_servlet_jsp_ver.com.service.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import boardProject_servlet_jsp_ver.com.domain.dto.Criteria;
import boardProject_servlet_jsp_ver.com.domain.dto.comment.CommentDeleteDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.comment.CommentInsertDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.comment.CommentReplyDTO;
import boardProject_servlet_jsp_ver.com.domain.entity.Comment;
import boardProject_servlet_jsp_ver.com.jdbc.JDBCTemplate;

public class CommentDaoImpl implements CommentDao {
	
	private Connection con = null;

	@Override
	public List<Comment> getBoardComment(long boardNo, Criteria cri) {
		String sql = "SELECT commentNo"
				+ ", userId"
				+ ", commentDate"
				+ ", CASE "
				+ "WHEN (commentStatus > 0) THEN null "
				+ "ELSE commentContent "
				+ "END AS commentContent "
				+ ", commentGroupNo"
				+ ", commentUpperNo"
				+ ", commentIndent"
				+ ", commentStatus "
				+ ", boardNo "
				+ "FROM comment "
				+ "WHERE boardNo = ? "
				+ "ORDER BY commentGroupNo desc, commentUpperNo asc "
				+ "LIMIT ?, ?";
		
		con = JDBCTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<Comment> commentList=  new ArrayList<Comment>();
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, boardNo);
			pstmt.setInt(2, (cri.getPageNum() - 1) * cri.getBoardAmount());
			pstmt.setInt(3, cri.getBoardAmount());
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				commentList.add(new Comment.CommentBuilder()
						.commentNo(rs.getLong("commentNo"))
						.userId(rs.getString("userId"))
						.commentDate(rs.getDate("commentDate"))
						.commentContent(rs.getString("commentContent"))
						.commentGroupNo(rs.getLong("commentGroupNo"))
						.commentUpperNo(rs.getString("commentUpperNo"))
						.commentIndent(rs.getInt("commentIndent"))
						.commentStatus(rs.getInt("commentStatus"))
						.boardNo(rs.getLong("boardNo"))
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
		
		return commentList;
	}

	@Override
	public List<Comment> getImageComment(long imageNo, Criteria cri) {
		
		String sql = "SELECT commentNo"
				+ ", userId"
				+ ", commentDate"
				+ ", CASE "
				+ "WHEN (commentStatus > 0) THEN null "
				+ "ELSE commentContent "
				+ "END AS commentContent "
				+ ", commentGroupNo"
				+ ", commentUpperNo"
				+ ", commentIndent"
				+ ", commentStatus "
				+ ", imageNo "
				+ "FROM comment "
				+ "WHERE imageNo = ? "
				+ "ORDER BY commentGroupNo desc, commentUpperNo asc "
				+ "LIMIT ?, ?";
		
		con = JDBCTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<Comment> commentList=  new ArrayList<Comment>();
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, imageNo);
			pstmt.setInt(2, (cri.getPageNum() - 1) * cri.getBoardAmount());
			pstmt.setInt(3, cri.getBoardAmount());
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				commentList.add(new Comment.CommentBuilder()
						.commentNo(rs.getLong("commentNo"))
						.userId(rs.getString("userId"))
						.commentDate(rs.getDate("commentDate"))
						.commentContent(rs.getString("commentContent"))
						.commentGroupNo(rs.getLong("commentGroupNo"))
						.commentUpperNo(rs.getString("commentUpperNo"))
						.commentIndent(rs.getInt("commentIndent"))
						.commentStatus(rs.getInt("commentStatus"))
						.imageNo(rs.getLong("imageNo"))
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
		
		return commentList;
	}

	@Override
	public int commentListCount(long boardNo, long imageNo) {
		String sql = "SELECT count(distinct(commentNo)) "
				+ "FROM comment "
				+ "WHERE ";
		
		con = JDBCTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int result = 0;
		
		try {
			if(boardNo > 0) {
				sql = sql.concat("boardNo=?");
				pstmt = con.prepareStatement(sql);
				pstmt.setLong(1, boardNo);
			}else if(imageNo > 0) {
				sql = sql.concat("imageNo=?");
				pstmt = con.prepareStatement(sql);
				pstmt.setLong(1, imageNo);
			}
			
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
	public String commentDelete(List<Long> deleteCommentNoList) {
		
		String sql = "UPDATE comment SET "
				+ "commentStatus=1 "
				+ "WHERE commentNo IN (%s)";
		String inSql = String.join(",", Collections.nCopies(deleteCommentNoList.size(), "?"));
		sql = String.format(sql, inSql);
		con = JDBCTemplate.getConnection();
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			
			for(int i = 1; i <= deleteCommentNoList.size(); i++)
				pstmt.setLong(i, deleteCommentNoList.get(i - 1));
			
			pstmt.executeUpdate();
			
			JDBCTemplate.commit(con);
			
		}catch(SQLException e) {
			e.printStackTrace();
			JDBCTemplate.rollback(con);
			return "fail";
		}finally {
			JDBCTemplate.close(con);
			JDBCTemplate.close(pstmt);
		}
		
		return "success";
	}

	@Override
	public String commentDeleteGroup(long commentGroupNo) {
		String sql = "UPDATE comment SET "
				+ "commentStatus=1 "
				+ "WHERE commentGroupNo=?";
		con = JDBCTemplate.getConnection();
		PreparedStatement pstmt = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, commentGroupNo);
			
			pstmt.executeUpdate();
			
			JDBCTemplate.commit(con);
			
		}catch(SQLException e) {
			e.printStackTrace();
			JDBCTemplate.rollback(con);
			return "fail";
		}finally {
			JDBCTemplate.close(con);
			JDBCTemplate.close(pstmt);
		}
		return "success";
	}

	@Override
	public List<CommentDeleteDTO> getCommentGroup(long commentGroupNo) {
		
		String sql = "SELECT commentNo"
				+ ", commentGroupNo"
				+ ", commentIndent"
				+ ", commentUpperNo "
				+ "FROM comment "
				+ "WHERE commentGroupNo=?";
		
		con = JDBCTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<CommentDeleteDTO> resultList = new ArrayList<CommentDeleteDTO>();
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, commentGroupNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				resultList.add(new CommentDeleteDTO.CommentDeleteDTOBuilder()
						.commentNo(rs.getLong("commentNo"))
						.commentGroupNo(rs.getLong("commentGroupNo"))
						.commentUpperNo(rs.getString("commentUpperNo"))
						.commentIndent(rs.getInt("commentIndent"))
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
		
		return resultList;
	}

	@Override
	public String checkWriter(long commentNo) {
		String sql = "SELECT userId "
				+ "FROM comment "
				+ "WHERE commentNo=?";
		con = JDBCTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = null;
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, commentNo);
			
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
	public CommentDeleteDTO checkDeleteNo(long commentNo) {
		String sql = "SELECT commentNo"
				+ ", commentGroupNo"
				+ ", commentIndent"
				+ ", commentUpperNo "
				+ "FROM comment "
				+ "WHERE commentNo=?";
		
		con = JDBCTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		CommentDeleteDTO resultDTO = new CommentDeleteDTO();
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, commentNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				resultDTO = new CommentDeleteDTO.CommentDeleteDTOBuilder()
						.commentNo(rs.getLong("commentNo"))
						.commentGroupNo(rs.getLong("commentGroupNo"))
						.commentIndent(rs.getInt("commentIndent"))
						.commentUpperNo(rs.getString("commentUpperNo"))
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
		
		return resultDTO;
	}
	
	@Override
	public String commentInsert(CommentInsertDTO dto) {
		
		String insertSql = null;
		long bno = 0;
		
		if(dto.getBoardNo() != 0) {
			insertSql = "INSERT INTO comment("
					+ "userId"
					+ ", commentContent"
					+ ", commentIndent"
					+ ", boardNo) "
					+ "values("
					+ "?"
					+ ", ?"
					+ ", ?"
					+ ", ?)";
			
			bno = dto.getBoardNo();
		}else if(dto.getImageNo() != 0) {
			insertSql = "INSERT INTO comment("
					+ "userId"
					+ ", commentContent"
					+ ", commentIndent"
					+ ", imageNo) "
					+ "values("
					+ "?"
					+ ", ?"
					+ ", ?"
					+ ", ?)";
			
			bno = dto.getImageNo();
		}
		
		String commentNoSql = "SELECT max(commentNo) FROM comment WHERE userId=?";
		long commentNo = 0;
		
		String updateSql = "UPDATE comment SET "
				+ "commentGroupNo=?"
				+ ", commentUpperNo=?"
				+ "WHERE commentNo=?";
		
		con = JDBCTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		System.out.println("dao method");
		
		try {
			System.out.println("try start");
			pstmt = con.prepareStatement(insertSql);
			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getCommentContent());
			pstmt.setInt(3, dto.getCommentIndent());
			pstmt.setLong(4, bno);
			
			pstmt.executeUpdate();
			
			pstmt = con.prepareStatement(commentNoSql);
			pstmt.setString(1, dto.getUserId());
			
			rs = pstmt.executeQuery();
			
			if(rs.next())
				commentNo = rs.getLong(1);
			
			JDBCTemplate.close(rs);
			
			pstmt = con.prepareStatement(updateSql);
			pstmt.setLong(1, commentNo);
			pstmt.setString(2, String.valueOf(commentNo));
			pstmt.setLong(3, commentNo);
			
			pstmt.executeUpdate();
			
			JDBCTemplate.commit(con);
			
		}catch(SQLException e) {
			e.printStackTrace();
			
			JDBCTemplate.rollback(con);
			
			return "fail";
		}finally {
			JDBCTemplate.close(con);
			JDBCTemplate.close(pstmt);
		}
		
		return "success";
	}
	
	@Override
	public String commentReply(CommentReplyDTO dto) {
		String insertSql = null;
		long bno = 0;
		if(dto.getBoardNo() != 0) {
			insertSql = "INSERT INTO comment("
					+ "userId"
					+ ", commentContent"
					+ ", commentGroupNo"
					+ ", commentIndent"
					+ ", boardNo)"
					+ "VALUES("
					+ "?"
					+ ", ?"
					+ ", ?"
					+ ", ?"
					+ ", ?)";
			
			bno = dto.getBoardNo();
		}else if(dto.getImageNo() != 0) {
			insertSql = "INSERT INTO comment("
					+ "userId"
					+ ", commentContent"
					+ ", commentGroupNo"
					+ ", commentIndent"
					+ ", imageNo)"
					+ "VALUES("
					+ "?"
					+ ", ?"
					+ ", ?"
					+ ", ?"
					+ ", ?)";
			
			bno = dto.getImageNo();
		}
		
		String commentNoSql = "SELECT max(commentNo) "
				+ "FROM comment "
				+ "WHERE userId=?";
		
		String updateSql = "UPDATE comment SET "
				+ "commentUpperNo=? "
				+ "WHERE commentNo=?";
		
		con = JDBCTemplate.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		long commentNo = 0;
		
		try {
			pstmt = con.prepareStatement(insertSql);
			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getCommentContent());
			pstmt.setLong(3, dto.getCommentGroupNo());
			pstmt.setInt(4, dto.getCommentIndent());
			pstmt.setLong(5, bno);
			
			pstmt.executeUpdate();
			
			pstmt = con.prepareStatement(commentNoSql);
			pstmt.setString(1, dto.getUserId());
			
			rs = pstmt.executeQuery();
			
			if(rs.next())
				commentNo = rs.getLong(1);
			
			JDBCTemplate.close(rs);
			
			pstmt = con.prepareStatement(updateSql);
			pstmt.setString(1, dto.getCommentUpperNo() + commentNo);
			pstmt.setLong(2, commentNo);
			
			pstmt.executeUpdate();
			
			JDBCTemplate.commit(con);
			
			
		}catch(SQLException e) {
			e.printStackTrace();
			JDBCTemplate.rollback(con);
			
			return "fail";
		}finally {
			JDBCTemplate.close(con);
			JDBCTemplate.close(pstmt);
		}
		
		
		
		return "success";
	}

}
