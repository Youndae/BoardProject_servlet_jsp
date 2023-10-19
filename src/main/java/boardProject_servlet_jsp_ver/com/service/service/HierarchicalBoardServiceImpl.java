package boardProject_servlet_jsp_ver.com.service.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import boardProject_servlet_jsp_ver.com.domain.dto.Criteria;
import boardProject_servlet_jsp_ver.com.domain.dto.PageDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.hierarchicalBoard.HierarchicalBoardDeleteDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.hierarchicalBoard.HierarchicalBoardDetailDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.hierarchicalBoard.HierarchicalBoardInsertDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.hierarchicalBoard.HierarchicalBoardModifyDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.hierarchicalBoard.HierarchicalBoardReplyDTO;
import boardProject_servlet_jsp_ver.com.domain.dto.hierarchicalBoard.HierarchicalBoardReplyProcDTO;
import boardProject_servlet_jsp_ver.com.domain.entity.HierarchicalBoard;
import boardProject_servlet_jsp_ver.com.service.dao.CommentDao;
import boardProject_servlet_jsp_ver.com.service.dao.CommentDaoImpl;
import boardProject_servlet_jsp_ver.com.service.dao.HierarchicalBoardDao;
import boardProject_servlet_jsp_ver.com.service.dao.HierarchicalBoardDaoImpl;

public class HierarchicalBoardServiceImpl implements HierarchicalBoardService {
	
	private HierarchicalBoardDao boardDAO = new HierarchicalBoardDaoImpl();
	
	private CommentDao commentDAO = new CommentDaoImpl();

	@Override
	public List<HierarchicalBoard> boardList(HttpServletRequest request, HttpServletResponse response) {

		
		Criteria cri = setCriteria(request);
		
		return boardDAO.boardList(cri);
	}
	
	@Override
	public PageDTO setPageDTO(HttpServletRequest request) {
		
		Criteria cri = setCriteria(request);
		
		int totalPages = (int) (Math.ceil((boardDAO.countList(cri) * 1.0) / cri.getBoardAmount()));
		
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
	public long boardInsert(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		String uid = (String) session.getAttribute("id");
		
		if(uid == null)
			return 0;
		
		HierarchicalBoardInsertDTO dto = new HierarchicalBoardInsertDTO.HierarchicalBoardInsertDTOBuilder()
				.boardTitle(request.getParameter("boardTitle"))
				.userId(uid)
				.boardContent(request.getParameter("boardContent"))
				.boardIndent(0)
				.build();
		
		
		long result = boardDAO.boardInsert(dto);
		
		return result;
	}

	@Override
	public HierarchicalBoardDetailDTO boardDetail(HttpServletRequest request, HttpServletResponse response) {

		long boardNo = Long.parseLong(request.getParameter("boardNo"));
		
		HierarchicalBoardDetailDTO dto = boardDAO.boardDetail(boardNo);
		
		Criteria cri = new Criteria();
		
		HierarchicalBoardDetailDTO result = new HierarchicalBoardDetailDTO.HierarchicalBoardDetailDTOBuilder()
				.boardNo(dto.getBoardNo())
				.boardTitle(dto.getBoardTitle())
				.boardContent(dto.getBoardContent())
				.userId(dto.getUserId())
				.boardDate(dto.getBoardDate())
				.commentList(commentDAO.getBoardComment(boardNo, cri))
				.pageMaker(new PageDTO(cri, (int) Math.ceil((commentDAO.commentListCount(boardNo, 0) * 1.0) / cri.getBoardAmount())))
				.build();
		
		
		return result;
	}

	@Override
	public String boardDelete(HttpServletRequest request, HttpServletResponse response) {
		
		long boardNo = Long.parseLong(request.getParameter("boardNo"));
		HttpSession session = request.getSession();
		String uid = (String) session.getAttribute("id");
		
		if(!boardDAO.checkWriter(boardNo).equals(uid))
			return null;
		
		//삭제요청 게시글의 no, gno, upper, indent를 요청
		HierarchicalBoardDeleteDTO deleteDTO = boardDAO.checkDeleteNo(boardNo);
		
		//삭제 요청 게시글의 indent가 0이라면 boardNo를 gno로 넘겨 해당 그룹 전체 삭제
		if(deleteDTO.getBoardIndent() == 0)
			boardDAO.deleteBoardGroup(boardNo);
		else { //indent가 0이 아닌 경우 답글이기 때문에
			//같은 그룹의 모든 데이터를 요청하고
			List<HierarchicalBoardDeleteDTO> deleteGroupDTO = boardDAO.getDeleteGroup(deleteDTO.getBoardGroupNo());
			
			//그룹 내에서 upperNo에 boardNo가 들어있는 리스트를 만들어서
			List<Long> deleteNoList = new ArrayList<Long>();
			getDeleteData(deleteGroupDTO, boardNo, deleteDTO.getBoardIndent(), deleteNoList);
			
			//list를 삭제 요청
			boardDAO.boardDelete(deleteNoList);
			
		}
		
		return "success";
	}
	
	public void getDeleteData(List<HierarchicalBoardDeleteDTO> deleteGroupDTO, long boardNo
								, int boardIndent, List<Long> deleteNoList) {
		
		for(int i = 0; i < deleteGroupDTO.size(); i++) {
			String upperNo = deleteGroupDTO.get(i).getBoardUpperNo();
			String[] upperArr = upperNo.split(",");
			
			if(upperArr.length > boardIndent && boardNo == Long.parseLong(upperArr[boardIndent])) 
				deleteNoList.add(deleteGroupDTO.get(i).getBoardNo());
			
		}		
		
	}

	@Override
	public HierarchicalBoardModifyDTO getBoardModify(HttpServletRequest request, HttpServletResponse response) {
		long boardNo = Long.parseLong(request.getParameter("boardNo"));
		HttpSession session = request.getSession();
		
		String writer = boardDAO.checkWriter(boardNo);
		
		if(!session.getAttribute("id").equals(writer)) 
			return null;
		else 
			return boardDAO.getBoardModifyData(boardNo);
		
	}

	@Override
	public long boardModify(HttpServletRequest request, HttpServletResponse response) {
		
		long boardNo = Long.parseLong(request.getParameter("boardNo"));
		HttpSession session = request.getSession();
		String uid = (String) session.getAttribute("id");
		
		String writer = boardDAO.checkWriter(boardNo);
		long result = 0;
		
		if(!uid.equals(writer)) 
			return 0;
			
		HierarchicalBoardModifyDTO dto = new HierarchicalBoardModifyDTO.HierarchicalBoardModifyDTOBuilder()
				.boardNo(boardNo)
				.boardTitle(request.getParameter("boardTitle"))
				.boardContent(request.getParameter("boardContent"))
				.build();
		
		result = boardDAO.boardModify(dto);
	
		return result;
	}

	@Override
	public HierarchicalBoardReplyDTO getBoardReply(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		String uid = (String) session.getAttribute("id");
		
		if(uid == null)
			return null;
		
		long boardNo = Integer.parseInt(request.getParameter("boardNo"));
		
		return boardDAO.getBoardReply(boardNo);
	}

	@Override
	public long boardReply(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		String uid = (String) session.getAttribute("id");
		
		if(uid == null)
			return 0;
		
		long boardNo = Long.parseLong(request.getParameter("boardNo"));
		
		HierarchicalBoardReplyProcDTO dto = new HierarchicalBoardReplyProcDTO.HierarchicalBoardReplyProcDTOBuilder()
				.boardTitle(request.getParameter("boardTitle"))
				.boardContent(request.getParameter("boardContent"))
				.userId(uid)
				.boardGroupNo(Long.parseLong(request.getParameter("boardGroupNo")))
				.boardIndent(Integer.parseInt(request.getParameter("boardIndent")) + 1)
				.boardUpperNo(request.getParameter("boardUpperNo") + ",")
				.build();
		
		long result = boardDAO.boardReply(dto);
		
		return result;
	}

	
	
}
