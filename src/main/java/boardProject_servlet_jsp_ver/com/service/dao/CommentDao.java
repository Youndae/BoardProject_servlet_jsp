package boardProject_servlet_jsp_ver.com.service.dao;

import java.util.List;

import boardProject_servlet_jsp_ver.com.domain.dto.Criteria;
import boardProject_servlet_jsp_ver.com.domain.dto.comment.CommentDeleteDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.comment.CommentInsertDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.comment.CommentReplyDTO;
import boardProject_servlet_jsp_ver.com.domain.entity.Comment;

public interface CommentDao {
	
	public List<Comment> getBoardComment(long boardNo, Criteria cri);
	
	public List<Comment> getImageComment(long imageNo, Criteria cri);
	
	public int commentListCount(long boardNo, long imageNo);
	
	public String commentDelete(List<Long> deleteCommentNoList);
	
	public String commentDeleteGroup(long commentGroupNo);
	
	public List<CommentDeleteDTO> getCommentGroup(long commentGroupNo);
	
	public String checkWriter(long commentNo);
	
	public CommentDeleteDTO checkDeleteNo(long commentNo);
	
	public String commentInsert(CommentInsertDTO dto);
	
	public String commentReply(CommentReplyDTO dto);
	
	

}
