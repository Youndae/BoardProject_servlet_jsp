# BoardProject_Servlet&JSP

# 프로젝트 요약
> 계층형을 표현하는 텍스트 기반의 게시판과 텍스트와 이미지 업로드가 가능한 게시판으로 구성된 CRUD 중심의 프로젝트입니다.   
> 다양한 기능 구현보다 기본에 충실한 프로젝트입니다.   
> 기본적인 개념에 대한 프로젝트인만큼 새로운 기술이나 언어, 환경에 대한 기본 CRUD 테스트에 주로 사용하고 있어 다양한 버전이 존재합니다.   
> 해당 프로젝트는 Servlet & JSP 버전이며, JSP, REST-API, Kotlin 버전이 추가로 존재합니다.   
> Servlet & JSP를 통해 JDBC Template의 사용과 구조 개념을 이해하기 위해 진행한 프로젝트입니다.
> 버전이 다르더라도 기능에는 차이가 거의 없으며 각 환경에 대한 구조의 차이 정도만 있습니다.

### 타 버전 GitHub

- JSP 기본 버전
	- https://github.com/Youndae/BoardProject
- REST-API 서버 및 SSR Frontend
	- https://github.com/Youndae/rest-api-project
- SPA Frontend
	- https://github.com/Youndae/boardProject_client_react
- Kotlin
	- https://github.com/Youndae/boardProject_kt

<br/>

# 목차

1. [프로젝트 구조](#프로젝트-구조)
2. [개발 환경](#개발-환경)
3. [ERD](#ERD)
4. [페이지별 기능 상세](#페이지별-기능-상세)
5. [기능 및 개선 내역](#기능-및-개선-내역)

<br/>

# 프로젝트 구조
<img src="./README_IMG/project_structure.jpg"/>

Servlet은 Controller 하위에 위치하고 있으며, 다양한 구조의 Servlet을 구현했습니다.   
Servlet 구조에 대한 상세한 내용은 기능 및 개선 내역 부분에 정리했습니다.   
Servlet & JSP 특성 상 Maven 또는 Gradle 같은 빌드 툴을 사용하지 않았기에 필요한 라이브러리는 webapp/WEB-INF/lib 하위에 jar 파일을 직접 받아 배치했습니다.

<br />

# 개발 환경

|Category| Tech Stack|
|---|---|
|Backend| - JDK 8 <br/> - JDBC Template|
|Frontend| - JSP <br/> - JQuery <br/> - Ajax <br/> - BootStrap|
|Database| - MySQL|
| Environment| - Eclipse <br/> - GitHub|
|Library| - gson-2.8.9 <br/> - commons-fileupload-1.5 <br/> - commons-io-2.11.0 <br/> - jstl library ( taglibs-standard-impl-1.2.5, taglibs-standard-spec-1.2.5 ) |

<br/>

# ERD

<img src="./README_IMG/boardProject_erd.jpg"/>

<br/>

# 페이지별 기능 상세

<details>
    <summary><strong>계층형 게시판</strong></summary>

- 계층형 목록
- 검색 ( 제목, 내용, 작성자, 제목 + 내용 기반 )
- Pagination
- 게시글 작성
- 게시글 상세 정보
	- 작성자인 경우 수정, 삭제( 삭제하는 경우 하위 계층 게시글 삭제 )
	- 답글 작성
	- 댓글 작성 ( 대댓글 작성 가능 )
</details>

<br/>

<details>
    <summary><strong>이미지 게시판</strong></summary>

- 목록
- 게시글 작성
	- 텍스트 및 이미지 업로드 ( 최대 5장 제한 )
- 검색 ( 제목, 내용, 작성자, 제목 + 내용 기반 )
- Pagination
- 게시글 상세 정보
	- 작성자인 경우 수정, 삭제
	- 댓글 작성 ( 대댓글 작성 가능 )
</details>

<br/>

<details>
    <summary><strong>로그인</strong></summary>

- 로그인
- 회원가입
</details>

<br/>

# 기능 및 개선 내역

1. [Servlet 설계](#Servlet-설계)
2. [JDBC Template](#JDBC-Template)
3. [이미지 파일 처리](#이미지-파일-처리)

<br/>

### Servlet 설계

<br/>

프로젝트를 진행하며 Servlet 구조에 대해 고민이 많았습니다.   
Servlet을 배울 때 기본적으로 doGet, doPost, doPut 메소드들을 오버라이드해서 처리하는 방법으로 배웠습니다.   
하지만 하나의 Servlet에서 여러번의 get 요청이 발생할 수도 있기 때문에 이 경우 기능별로 Servlet을 분리할지, 아니면 Spring에서의 Controller 처럼 하나의 큰 틀에서 기능을 메소드별로 나눌지에 대한 고민이었습니다. 
이 설계에 대해 고민이 많았으나 정보가 부족해 확신을 가질 수 없었고, 결과적으로는 모든 케이스에 대한 경험을 해보고자 다양한 구조로 설계하게 되었습니다.   

CommentServlet과 MemberServlet은 doGet, doPost 등의 메소드들을 일체 사용하지 않고, service 메소드에서 요청 URI에 따라 직접 작성한 메소드를 호출하는 형태로 처리했습니다.

```java
@WebServlet(urlPatterns = "/comment/*")
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private CommentService commentService = new CommentServiceImpl();

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uri = req.getRequestURI();
		String path = uri.substring(uri.lastIndexOf("/"));

		if(path.equals("/boardComment"))
			doGetBoardComment(req, resp);
		else if(path.equals("/imageComment"))
			doGetImageBoardComment(req, resp);
		else if(path.equals("/commentInsert"))
			doPostInsertComment(req, resp);
		else if(path.equals("/commentDelete"))
			doDeleteComment(req, resp);
		else if(path.equals("/commentReply"))
			doPostReplyComment(req, resp);
	}

	protected void doPostReplyComment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String result = commentService.commentReply(req);

		PrintWriter out = resp.getWriter();
		out.print(result);
	}

	protected void doGetBoardComment(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CommentDTO dto = commentService.getBoardComment(req);

		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");

		String gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create().toJson(dto);

		resp.getWriter().write(gson);
	}

	//...
}
```

이 방법은 Spring Controller 처럼 큰 틀 내에서 요청을 받는 Service 메소드 하나를 두고 분리해서 작성해 확장성이 높으며 한곳으로 요청이 집중되어 공통 로직의 처리가 용이하다는 장점이 있습니다.   
하지만 URI 매핑이 하드코딩되는 만큼 많은 요청을 처리하는 Servlet이라면 가독성과 유지보수성이 떨어질 수 있습니다.   
또한, 규모가 커질수록 비대해지고 복잡해진다는 단점도 존재합니다.

계층형 게시판인 HierarchicalBoard의 Servlet은 Service로 요청을 받되, doGet, doPost 등의 HttpServlet 메소드를 최대한 사용하고, 중복되는 처리의 경우 메소드를 직접 작성해 호출하도록 처리했습니다.   

```java
@WebServlet(urlPatterns = "/board/*")
public class HierarchicalBoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private HierarchicalBoardService boardService = new HierarchicalBoardServiceImpl();

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uri = req.getRequestURI();
		String path = uri.substring(uri.lastIndexOf("/"));

		if(path.equals("/boardList"))
			doGet(req, resp);
		else if(path.equals("/boardDetail"))
			doGetDetail(req, resp);
		else if(path.equals("/boardModifyProc"))
			doPut(req, resp);
		else if(path.equals("/boardDelete"))
			doDelete(req, resp);
		else if(path.equals("/boardInsertProc"))
			doPost(req, resp);
		else if(path.equals("/boardInsert"))
			doGetInsert(req, resp);
		else if(path.equals("/boardModify"))
			doGetModify(req, resp);
		else if(path.equals("/boardReply"))
			doGetReply(req, resp);
		else if(path.equals("/boardReplyProc"))
			doPostReply(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher;

		List<HierarchicalBoard> resultList = boardService.boardList(req, resp);
		
		if(resultList == null){
			req.setAttribute("list", null);
			dispatcher = req.getRequestDispatcher(ViewPathProperties.accessErrorViewPath);
		}else {
			req.setAttribute("list", resultList);
			req.setAttribute("pageMaker", boardService.setPageDTO(req));
			dispatcher = req.getRequestDispatcher(ViewPathProperties.hierarchicalViewPath + "boardList.jsp");
		}
		dispatcher.forward(req, resp);
	}

	protected void doPostReply(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher;

		long result = boardService.boardReply(req, resp);

		if(result == 0){
			dispatcher = req.getRequestDispatcher(ViewPathProperties.accessErrorViewPath);
			dispatcher.forward(req, resp);
		}else
			resp.sendRedirect("/board/boardDetail?boardNo=" + result);
	}

	//...
}
```
이 방법의 경우 역시 메소드 직접 작성 방식과 같은 장단점을 갖고 있습니다.

마지막으로 이미지 게시판인 ImageServlet의 경우 기능별로 모두 분리하고 doGet, doPost 등의 메소드들만 사용해 구현하는 방법으로 처리했습니다.   
이 방법은 기능별로 책임이 분리되면서 유지보수가 용이하며 응집도가 높다는 장점이 있지만, 기능이 많아질수록 Servlet 파일 개수가 증가한다는 단점이 있습니다.   
또한, 공통 로직을 매번 중복해서 구현하게 될 수 있다는 단점 또한 존재합니다.

아쉽게도 여러가지 방법으로 Servlet을 분리해봤지만 아직은 어떤 방법이 더 좋을지에 대한 확신이 서지 않았습니다.   
작은 프로젝트들을 개인 프로젝트로 수행하고 리팩토링하고 있다보니 감을 잡는데 어려움이 있지만,   
이 경험으로 느낄 수 있었던 것은 다른 Servlet들 처럼 큰 틀의 Servlet을 하나 만들어두고 사용하는 것 보다는 좀 더 분리하는 것이 좋다는 생각이 들었습니다.   

대신 이미지 게시판처럼 완전한 분리를 통한 설계보다는 좀 더 Servlet의 네이밍을 명확하게 하고 관심사별로 나눌 수 있도록 설계하는 방법이 좋지 않을까 라는 생각을 할 수 있었습니다.

### JDBC Template

이 프로젝트에서는 데이터베이스 연동을 위해 JDBC Template을 사용했습니다.

일정한 Connection을 반환 받기 위해 JDBCTemplate 클래스를 생성하고 Connection을 반환하도록 했으며,   
close, rollback, commit을 처리하는 메소드와 ResultSet, Statement의 close를 처리하는 메소드를 작성해 두었습니다.


```java
public class JDBCTemplate {
	
	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		String url = "jdbc:mysql://localhost:3306/boardproject?serverTimezone=UTC&characterEncoding=UTF-8&autoReconnection=true";
		String id = "root";
		String pw = "1234";
		
		Connection con = null;
		
		try {
			//뭔지 알아보기.
			con = DriverManager.getConnection(url, id, pw);
			
			con.setAutoCommit(false);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return con;
	}
	
	//연결 상태라면 true, 닫혀있다면 false 리턴.
	public static boolean isConnection(Connection con) {
		boolean valid = true;
		
		try {
			// con이 null이거나 con이 close 상태라면
			if(con == null || con.isClosed()) {
				valid = false;
			}
		}catch(SQLException e) {
			valid = true;
			e.printStackTrace();
		}
		
		return valid;
	}
	
	//연결 상태인지 아닌지 확인 후 연결 상태라면 close
	public static void close(Connection con) {
		if(isConnection(con)) {
			try {
				con.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//statement가 null이 아닐 때 close
	public static void close(Statement stmt) {
		if(stmt != null) {
			try {
				stmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//rs가 null이 아닐 때 close
	public static void close(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//연결 상태라면 commit
	public static void commit(Connection con) {
		if(isConnection(con)) {
			try {
				con.commit();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
```

DAOImpl에서는 getConnection()을 통해 데이터베이스 연결을 확보하고, 처리 완료 후 close()를 호출하여 연결을 종료했습니다.

<br/>


### 이미지 파일 처리

<br/>

Spring에서 파일 처리는 Multipart로 받아 쉽게 처리할 수 있었지만 Servlet에서는 사용할 수 없었기에 다른 방법이 필요했습니다.   
문제 해결을 위해 여러 방법을 찾아보게 되었고, 자바에서 파일 처리 방식과 DiskFileItemFactory, FileItem을 통한 처리 방식을 찾을 수 있었습니다.   
그 중에서 DiskFileItemFactory와 FileItem을 통한 처리 방법을 택해 문제를 해결할 수 있었습니다.
  
```java
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
```

DiskFileItemFactory와 FileItem을 통해 게시글 정보와 파일을 꺼낼 수 있었고, 저장 및 삭제 데이터에 대해서는 리스트화 한 뒤 DAO에 요청하게 됩니다.   

```java
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
```
리스트를 전달해 처리하는만큼 여기서도 동적 쿼리로 수행할 수 있어야 했습니다.   
계층형 게시판의 Delete와는 다른 점으로 이미지 데이터의 삽입 과정을 IN 절로 처리할 수 없었기 때문에 동일하게 String.join을 통해 values 구문 부분을 만들어주도록 했습니다.   
그리고 값에 대해서는 PreparedStatement.set 을 통해 담아주도록 했고, 여러건을 처리하기 위해 반복문을 통해 처리했습니다.

처리 과정 중에 게시글 정보, 이미지 데이터 추가, 이미지 데이터 삭제 중 하나라도 문제가 발생한다면 전체적인 롤백이 필요하다고 판단해 하나의 메소드에서 처리하도록 했고,   
문제가 발생하는 경우 catch문을 통해 전체 롤백이 수행되도록 했습니다.

만약 롤백이 되는 경우 저장된 파일이 삭제되었다면 문제가 발생하기 때문에 비즈니스 로직에서 파일 삭제 메소드 호출은 데이터 처리가 완료된 후에 수행하도록 했습니다.


## 프로젝트 특징과 이유, 느낀점
1. Servelet의 분리
	* servlet의 경우 HierarchicalBoardServlet와 CommentServlet, MemberServlet은 하나의 서블릿에서 HierarchicalBoard에 대한 모든 처리를 담당한다.
	* 하지만 ImageBoard의 경우 기능별로 servlet을 생성해 처리하는 방법으로 구현.
	* Servlet에서는 Service와 do*으로 나눠서 구분할 수 있다.
	* 하나의 Servlet에서 모든 기능을 처리하도록 Service로 받아서 처리하는 방법과 각 기능별 서블릿을 구분해 do*으로 처리하는 방법 두가지를 모두 다 해보기 위해 구조를 이렇게 처리.
	* 느낀점으로는 각 기능별 servlet을 만들게 되면 요청 메소드별로 알아서 잡아주기 때문에 좀 더 RESTful한 개발이 가능할 것 같다는 생각이 들었다.
	* 하지만 만약 공통적으로 사용해야 하는 객체가 존재하게 된다면, 혹은 기능별 공유해야 하는 객체가 존재하는 경우가 된다면 모든 기능을 통합해 관리하는 Servlet이 좀 더 이점이 있을것이라고 느꼈다.

2. DTO의 분리
	* 최근 Entity와 DTO의 분리에 대해 고민하다 보니 당연하게 분리하면서 처리하게 되었는데 각 get, post에 대한 DTO까지 분리하다보니 DTO 객체의 양이 많아져서 과한것 아닌가 하는 느낌도 들긴했다.
	* 하지만 최근 계속 분리해서 사용해본 결과 좀 더 명확하고 필요한 필드만 골라 처리할 수 있다는 점에서는 안전하고 명확하게 사용할 수 있는 것 같다고 느꼈다.

3. 모든 DTO와 Entity의 setter 지양 및 Builder 사용
	* 모든 DTO와 Entity에서는 setter를 전혀 사용하지 않았고 Builder Pattern을 활용해 처리했다.
	* Spring에서는 @Builder Annotation하나로 처리가 가능해 편하게 사용했지만 실제 패턴을 작성해 처리해본 적이 없었기에 기회다 싶어서 사용했다.
	* 작성하는데에 있어서 코드가 점점 귀찮아지는 면이 없지 않아 있었지만 그래도 Setter를 통해 처리하는것 보다는 깔끔하게 처리가 가능해 점점 편하고 익숙해져 간다고 느꼈다.

4. JDBCTemplate의 사용
	* ConnectionPool에 대해 공부는 했지만 프로젝트에 직접 사용한적이 없었는데 이번에 처음 사용해봤고 그렇기에 블로그 포스팅들을 참고해 구현했다.
	* 다른 메소드들은 사용해봤지만 isConnection의 경우 전혀 사용이 없었기에 조금 아쉽다.
	

## 구현하면서 발생한 문제와 해결책 또는 새로 알게된 점과 부족한 점
1. @WebServlet 어노테이션 매핑 문제
	* 가장 먼저 구현한것이 HierarchicalBoard였기 때문에 매핑 설정 시 /board/* 형태로 처리하고자 했다.   
	그래서 urlPattern 속성 역시 "/board" 이렇게만 처리했으나 전혀 연결이 되지 않았다.
	사용하는 urlPattern으로는 /board/boardList, /board/boardDetail 형태였고 service에서 path 변수를 통해 받아오고 있었기 때문에 당연하게 생각했지만 전혀 안됨.
	이 경우에는 "/board/*" 형태로 설정을 해줘야 /board/~~~ 에 대한 url을 받아 처리할 수 있게 된다.

2. Servlet을 매핑하는 방법은 @WebServlet과 web.xml에 작성하는 방법 두가지가 있다.
	* @WebServlet으로 다 처리했으니 @WebServlet은 생략한다.   
	web.xml에서 <servlet-mapping>으로 매핑 처리를 할 수 있다.   
	하지만 Servlet 3.0부터는 @WebServlet으로 가능하기 때문에 오히려 복잡하게 web.xml에서 매핑할 필요가 없지 않을까 싶었다.   
	방법이 있다는 정도로 알고 어떻게 하는지 알고 있으면 괜찮지 않을까 싶다.

3. Library 문제
	* Spring 기반으로 구현하다보면 라이브러리 추가에 대해서는 pom.xml이나 gradle로 편하게 추가가 가능했다.   
	하지만 여기서는 maven이나 gradle을 사용하지 않기 때문에 직접 jar 파일을 받아 lib 디렉터리에 담아 줄 필요가 있었다.   
	사실 mavenRepository에 왠만한 라이브러리 jar파일은 있기 때문에 크게 어렵지 않았지만 JSTL의 경우가 조금 애매했다.   
	JSTL 라이브러리의 경우 maven이나 gradle로 추가하는 경우 하나만 추가해주면 사용할 수 있었지만 직접 jar 파일을 추가하는 것은 그렇게 할 수 없었고,   
	총 4개의 jar 파일 중 impl과 spec 파일은 존재해야 한다는 것을 이번 기회에 알 수 있었다.   

4. SQL 작성
	* MyBatis나 JPA를 사용하지 않고 직접 String으로 쿼리를 작성해 실행시키는 것은 처음 해 봤다.   
	그런만큼 쿼리 작성에서 변수가 많았다.   
	보통 동적쿼리로 처리하게 되는 경우 MyBatis도 그렇고 JPA도 그렇고 제공해주는 방법이 있었기에 좀 더 수월하게 처리할 수 있었다.   
	하지만 JDBC만을 이용해 처리하다보니 동적쿼리에 대한 부분 역시 문자열 처리로 해결해야 했다.   
	그래서 이번에 새로 알게된 String의 속성이 concat과 join이었다.   
	concat은 해당 문자열 뒤쪽에 추가로 연결해주는 속성으로 문자열 변수.concat("추가하고자 하는 문자열") 형태로 사용할 수 있다.   
	join의 경우는 내가 원하는 문자열을 특정 횟수만큼 추가할 수 있었다.   
	String.join("추가되는 문자열 사이에 들어갈 문자열", Collections.nCopies(list.size(), "추가할 문자열"));   
	형태로 사용할 수 있었다.   
	이 경우 list의 size만큼 뒷 문자열이 추가가 되는데 그 사이에 가장 앞에 작성한 문자열이 들어가게 된다.   
	그래서 IN 절을 사용할 때 원하는 만큼의 ?를 추가할 수 있었다.   
	이 join의 경우는 그냥 붙인다고 되는것은 아니고 printf를 사용할 때 처럼 %s를 통해 처리해야 한다.   
	String sql = "DELETE FROM board WHERE boardNo IN (%s)";   
	여기에서 join을 통해 처리하고자 한다면    
	String inSql = String.join(",", Collections.nCopies(list.size(), "?"));   
	sql = String.format(sql, insql);   
	이렇게 String.format으로 담아줘야 원하는 형태로 사용할 수 있다.   
	join의 경우 Collections를 통해 처리한만큼 다르게 사용했을 때는 어떤 결과를 볼 수 있는지 확인이 필요하다.
