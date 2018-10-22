<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	
    <title>Sample 목록입니다.</title>

    <script src="${pageContext.request.contextPath}/js/jquery/jquery-3.2.0.min.js" type="text/javascript"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap/bootstrap-theme.css">

</head>

<body style="margin-left: 10px;">

<h1>Sample page</h1>
<hr>

<a href="${pageContext.request.contextPath}/sample/main">1. 사용자 정보 등록/목록/수정/삭제</a><br/>
<a href="${pageContext.request.contextPath}/sample/listBoard">2.게시판 테스트(파일업로드/다운로드)</a><br/>
<a href="${pageContext.request.contextPath}/sample/twoPc">3. Two Phase Commit(2pc 설정 후 테스트 하세요)</a><br/>


</body>
</html>