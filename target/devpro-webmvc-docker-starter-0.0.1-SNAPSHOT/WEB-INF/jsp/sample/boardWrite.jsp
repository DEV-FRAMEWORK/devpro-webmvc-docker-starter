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

<h1><a href="${pageContext.request.contextPath}/sample/boardList">Sample page</a></h1>
<hr>
	<form id="frm" name="frm" enctype="multipart/form-data" method="POST">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	
		<table class="board_view">
			<colgroup>
				<col width="15%">
				<col width="*"/>
			</colgroup>
			<caption>게시글 작성</caption>
			<tbody>
				<tr>
					<th scope="row">제목</th>
					<td><input type="text" id="title" name="title" class="wdp_90"></input></td>
				</tr>
				<tr>
					<td colspan="2" class="view_text">
						<textarea rows="20" cols="100" title="내용" id="contents" name="contents"></textarea>
					</td>
				</tr>
			</tbody>
		</table>
		<div id="fileDiv">
            <p>
                <input type="file" id="file" name="file_0">
                <a href="#this" class="btn" id="delete">삭제</a>
            </p>
        </div>
         
        <br/><br/>
        <a href="#this" class="btn" id="addFile">파일 추가</a>
		<a href="#this" class="btn" id="write">작성하기</a>
		<a href="#this" class="btn" id="list">목록으로</a>
	</form>
	
<form id="commonForm" name="commonForm">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
	<script type="text/javascript">
	 var gfv_count = 1;
		$(document).ready(function(){
			$("#list").on("click", function(e){ //목록으로 버튼
				e.preventDefault();
				fn_boardList();
			});
			
			$("#write").on("click", function(e){ //작성하기 버튼
				e.preventDefault();
				fn_registBoard();
			});
			
			$("#addFile").on("click", function(e){ //파일 추가 버튼
                e.preventDefault();
                fn_addFile();
            });
			
			$("#delete").on("click", function(e){ //삭제 버튼
                e.preventDefault();
                fn_deleteFile($(this));
            });



		});
		
		function fn_boardList(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/sample/listBoard' />");
			comSubmit.submit();
		}
		
		function fn_registBoard(){
			var comSubmit = new ComSubmit("frm");
			comSubmit.setUrl("<c:url value='/sample/registBoard' />");
			comSubmit.submit();
		}
		
		function fn_deleteFile(obj){
			obj.parent().remove();
		}
		
		function fn_addFile(){
			var str = "<p><input type='file' name='file_"+(gfv_count++)+"'><a href='#this' class='btn' name='delete'>삭제</a></p>";
            $("#fileDiv").append(str);
            $("a[name='delete']").on("click", function(e){ //삭제 버튼
                e.preventDefault();
                fn_deleteFile($(this));
            });


		}
		
	</script>
</body>
</html>