<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%@ include file="/WEB-INF/jsp/sample/include/include-header.jsp" %>
    
  <script src="${pageContext.request.contextPath}/js/jquery/jquery-3.2.0.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/pagenation.js" type="text/javascript"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap/bootstrap-theme.css">
    <title>Sample::게시판 등록(파일업로드 다운로드용)</title>

</head>
<body style="margin-left: 1px;">

<h1><a href="${pageContext.request.contextPath}/sample/listBoard">Sample page</a></h1>
<hr>
	<h2>게시판 목록</h2>
	<table class="board_list">
		<colgroup>
			<col width="10%"/>
			<col width="*"/>
			<col width="15%"/>
			<col width="20%"/>
		</colgroup>
		<thead>
			<tr>
				<th scope="col">글번호</th>
				<th scope="col">제목</th>
				<th scope="col">조회수</th>
				<th scope="col">작성일</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${fn:length(list) > 0}">
					<c:forEach items="${list}" var="row">
						<tr>
							<td><c:out value="${row.idx }"/></td>
							<td class="title">
								<a href="#this" id="title"><c:out value="${row.title }"/></a>
								<input type="hidden" id="idx" value="${row.idx }">
							</td>
							<td><c:out value="${row.hitCnt }"/></td>
							<td><c:out value="${row.creaDtm }"/></td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
						<td colspan="4">조회된 결과가 없습니다.</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>
	<br/>
	<a href="#this" class="btn" id="write">글쓰기</a>
<form id="commonForm" name="commonForm">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

	<script type="text/javascript">
		$(document).ready(function(){
			$("#write").on("click", function(e){ //글쓰기 버튼
				
				e.preventDefault();
				fn_openBoardWrite();
			});	
			
			$("a[id='title']").on("click", function(e){ //제목 
				e.preventDefault();
				fn_openBoardDetail($(this));
			});
		});
		
		
		function fn_openBoardWrite(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/sample/writeBoard' />");
			comSubmit.submit();
		}
		
		function fn_openBoardDetail(obj){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/sample/detailBoard' />");
			comSubmit.addParam("idx", obj.parent().find("#idx").val());
		
			comSubmit.submit();
		}
	</script>	
</body>
</html>