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
<body style="margin-left: 10px;">

<h1><a href="${pageContext.request.contextPath}/sample/listBoard">Sample page</a></h1>
<hr>
	<table class="board_view">
		<colgroup>
			<col width="15%"/>
			<col width="35%"/>
			<col width="15%"/>
			<col width="35%"/>
		</colgroup>
		<caption>게시글 상세</caption>
		<tbody>
			<tr>
				<th scope="row">글 번호</th>
				<td><c:out value="${map.idx }"/></td>
				<th scope="row">조회수</th>
				<td><c:out value="${map.hitCnt }"/></td>
			</tr>
			<tr>
				<th scope="row">작성자</th>
				<td><c:out value="${map.creaId }"/></td>
				<th scope="row">작성시간</th>
				<td><c:out value="${map.creaDtm }"/></td>
			</tr>
			<tr>
				<th scope="row">제목</th>
				<td colspan="3"><c:out value="${map.title }"/></td>
			</tr>
			<tr>
				<td colspan="4"><c:out value="${map.contents }"/></td>
			</tr>
			<tr>
                <th scope="row">첨부파일</th>
                <td colspan="3">
                    <c:forEach var="row" items="${list}">
                        <input type="hidden" id="idx" value="<c:out value="${row.idx }"/>">
                        <a href="#this" id="file" ><c:out value="${row.originalFileName }"/></a>
                        (${row.fileSize }kb)
                    </c:forEach>
                </td>
            </tr>
		</tbody>
	</table>
	
	<a href="#this" class="btn" id="list">목록으로</a>
	<a href="#this" class="btn" id="update">수정하기</a>
	
<form id="commonForm" name="commonForm">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

</form>
	<script type="text/javascript">
		$(document).ready(function(){
			$("#list").on("click", function(e){ //목록으로 버튼
				e.preventDefault();
				fn_boardList();
			});
			
			$("#update").on("click", function(e){ //수정하기 버튼
				e.preventDefault();
				fn_boardUpdate();
			});
			
			$("#file").on("click", function(e){ //파일 이름
                e.preventDefault();
				fn_downloadFile($(this));
            });



		});
		
		function fn_boardList(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/sample/listBoard' />");
			comSubmit.submit();
		}
		
		function fn_boardUpdate(){
			var idx = "${map.idx}";
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/sample/updateBoard' />");
			comSubmit.addParam("idx", idx);
			comSubmit.submit();
		}
		
		function fn_downloadFile(obj){
			var idx = obj.parent().find("#idx").val();
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/sample/downloadFile' />");
			comSubmit.addParam("idx", idx);
			comSubmit.submit();
		}
	</script>
</body>
</html>