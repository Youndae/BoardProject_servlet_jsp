package boardProject_servlet_jsp_ver.com.service.dao;


import java.util.List;

import boardProject_servlet_jsp_ver.com.domain.dto.Criteria;
import boardProject_servlet_jsp_ver.com.domain.dto.hierarchicalBoard.HierarchicalBoardDeleteDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.hierarchicalBoard.HierarchicalBoardDetailDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.hierarchicalBoard.HierarchicalBoardInsertDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.hierarchicalBoard.HierarchicalBoardModifyDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.hierarchicalBoard.HierarchicalBoardReplyDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.hierarchicalBoard.HierarchicalBoardReplyProcDTO;
import boardProject_servlet_jsp_ver.com.domain.entity.HierarchicalBoard;

public interface HierarchicalBoardDao {
	
	public List<HierarchicalBoard> boardList(Criteria cri);
	
	public int countList(Criteria cri);
	
	public long boardInsert(HierarchicalBoardInsertDTO dto);
	
	public HierarchicalBoardDetailDTO boardDetail(long boardNo);
	
	public String boardDelete(List<Long> deleteNoList);
	
	public String checkWriter(long boardNo);
	
	public String deleteBoardGroup(long boardGroupNo);
	
	public HierarchicalBoardDeleteDTO checkDeleteNo(long boardNo);
	
	public List<HierarchicalBoardDeleteDTO> getDeleteGroup(long boardGroupNo);

	public HierarchicalBoardModifyDTO getBoardModifyData(long boardNo);
	
	public long boardModify(HierarchicalBoardModifyDTO dto);
	
	public HierarchicalBoardReplyDTO getBoardReply(long boardNo);
	
	public long boardReply(HierarchicalBoardReplyProcDTO dto);
	
	
}
