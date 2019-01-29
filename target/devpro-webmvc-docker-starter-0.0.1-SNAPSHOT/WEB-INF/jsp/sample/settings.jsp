<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<meta id="_csrf" name="_csrf" content="${_csrf.token}"/>
	<meta id="_csrf_header" name="_csrf_header" content="${_csrf.headerName}"/>
    <title>Sample::목록</title>

    <script src="${pageContext.request.contextPath}/js/jquery/jquery-3.2.0.min.js" type="text/javascript"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap/bootstrap-theme.css">

    <script type="text/javascript">

        Service = {

            createTable : function () {
                $.ajax({
                    url : "${pageContext.request.contextPath}/sample/initTable",
                    success : function(data) {
                        $("#result").empty();
                        $("#result").html(data.message);
                    },
                    error: function(data){
                        $("#result").empty();
                        $("#result").html(data.message);
                    }
                });
            },

            createData : function () {
                $.ajax({
                    url : "${pageContext.request.contextPath}/sample/initData",
                    success : function(data) {
                        $("#result").empty();
                        $("#result").html(data.message);
                    },
                    error: function(data){
                        $("#result").empty();
                        $("#result").html(data.message);
                    }
                });
            }
        }

    </script>

</head>

<body style="margin-left: 10px;">

<a href="${pageContext.request.contextPath}/sample/settings"><h1>Framework Settings</h1></a>
<hr>

<u>1.샘플 Database 생성하기</u><br/><br/>

테이블 생성(초기화)
<input type="button" id="createBtn" value="Create Table" onclick="Service.createTable();"/>
<br/>
<br/>
데이터 생성(초기화)
<input type="button" id="dataBtn" value="Create Data" onclick="Service.createData();"/>

<div style="height: 20px;"></div>
<div id="result" style="height:40px;"></div>

</body>
</html>