# BoardProejct Servlet&JSP ver.

## 의도
Servlet&JSP로 구현해본적이 없기에 간단한 게시판 형태의 구현을 목표로 진행.

## 프로젝트 기능
* 계층형 게시판
	* 계층형 구조로 텍스트만으로 구성된 게시판
	* 게시글 검색, 페이징, 작성, 수정, 삭제, 답글, 댓글 기능 구현
	* 계층형 구조이므로 게시글 삭제 시 해당 게시글 하위 계층에 위치하는 게시글도 같이 삭제되도록 구현
* 이미지 게시판
	* 이미지와 텍스트를 같이 업로드 할 수 있는 게시판
	* 최대 5장의 이미지 파일 업로드 가능
	* 검색, 페이징, 작성, 수정, 삭제, 댓글 기능 구현
	* 여러 방법으로 수행해보기 위해 삭제는 관리자만 가능하도록 구현
* 댓글
	* 계층형 구조로 구현
	* 계층형 게시판과 마찬가지로 삭제 시 하위 계층에 위치하는 모든 댓글도 같이 삭제
	* 댓글의 경우 delete 처리를 하는것이 아닌 update로 status값을 변경해 '삭제된 댓글입니다'라는 문구를 출력하도록 구현
	* status 값에 다른 content의 내용은 Front에서 parsing하는 것이 아닌 Query로 처리.
 

## 개발 환경
* IDE - Eclipse
* Java 8
* MySQL 8.0
* JDBC
* BootStrap
* Library
	* gson-2.8.9
	* commons-fileupload-1.5
	* commons-io-2.11.0
	* jstl lib
		* taglibs-standard-impl-1.2.5
		* taglibs-standard-spec-1.2.5



## 기능

### Servlet 구조

처음에는 Sevlet에서 doGet, doPost, doPut등 메소드들을 오버라이드해서 처리하고자 했으나 그렇게 처리하는 경우 기능별로 Servlet을 분리해서 나누는 것이 좋을지,   
아니면 Spring에서 Controller처럼 하나의 큰 틀에서 기능을 메소드별로 나누는 것이 좋을지에 대한 정보가 부족해 확신이 없었습니다.   
그래서 여러 방법으로 Sevlet 구조를 설계해 봤습니다.

CommentServlet과 MemberServlet은 doGet, doPost 메소드들을 일체 사용하지 않고, service 메소드에서 요청 uri에 따라 직접 작성한 메소드를 호출하는 형태로 처리합니다.
```java
@WebServlet(urlPatterns = "/comment/*")
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private CommentService comemntService = new CommentServiceImpl();

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

		resp.setContentType("application/json")
		resp.setCharacterEncoding("UTF-8");

		String gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create().toJson(dto);

		resp.getWriter().write(gson);
	}

	...
}
```

계층형 게시판인 HierarchicalBoardServlet은 Comment와 Member같이 service 메소드를 통해 요청을 먼저 받게 되지만 모든 메소드를 직접 작성하는 것이 아닌   
doGet, doPost 메소드를 오버라이드해 처리하면서 추가적으로 필요한 기능에 대한 메소드를 직접 작성해 호출하도록 처리했습니다.
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

	...
}
```

마지막으로 이미지 게시판은 기능별로 Servlet을 모두 분리하고 service 메소드를 일체 사용하지 않은 구조로 doGet, doPost, doPut 등의 메소드들을 오버라이드해 구현하는 방법으로 처리했습니다.

|Servlet|Method|
|---|---|
|ImageAttachServlet|doGet - 이미지 수정 페이지에서 해당 게시글의 이미지 데이터를 반환|
|ImageBoardDeleteServlet|doDelete - 게시글 삭제 처리|
|ImageBoardDetailServlet|doGet - 이미지 게시판 상세페이지 데이터를 담아 호출|
|ImageBoardInsertServlet|doGet - 이미지 게시판 작성 페이지 호출 <br/> doPost - 이미지 게시판 작성 요청 처리|
|ImageBoardModifyServlet|doGet - 이미지 게시판 수정 페이지 데이터를 담아 호출 <br/> doPut - 이미지 게시판 수정 요청 처리|
|ImageBoardServlet|doGet - 이미지 게시판 리스트(메인) 페이지 요청|
|ImageServlet|doGet - 이미지 출력을 위한 처리|

Servlet을 이렇게 분리함으로써 Servlet이 어떤 기능을 담당하고 있는지, 이 Servlet에서는 어떤 Http Method 요청을 처리하고 있는지 좀 더 명확하고 빠르게 알 수 있다는 장점이 있었습니다.

아쉽게도 여러가지 방법으로 Servlet을 분리해봤지만 아직은 어떤 방법이 더 좋을지에 대한 확신이 서지 않았습니다.   
작은 프로젝트들을 개인 프로젝트로 수행하고 리팩토링하고 있다보니 감을 잡는데 어려움이 있지만,   
이 경험으로 느낄 수 있었던 것은 다른 Servlet들 처럼 큰 틀의 Servlet을 하나 만들어두고 사용하는 것 보다는 좀 더 분리하는 것이 좋다는 생각이 들었습니다.   

대신 이미지 게시판처럼 완전한 분리를 통한 설계보다는 좀 더 Servlet의 네이밍을 명확하게 하고 관심사별로 나눌 수 있도록 설계하는 방법이 좋지 않을까 라는 생각을 할 수 있었습니다.

### JDBC

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

### 계층형 게시판

* 테이블 구조
	



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
