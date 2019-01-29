<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<meta id="_csrf" name="_csrf" content="${_csrf.token}"/>
	<meta id="_csrf_header" name="_csrf_header" content="${_csrf.headerName}"/>
	
    <title>Sample::사용자 사용자 정보 관리</title>

    <script src="${pageContext.request.contextPath}/js/jquery/jquery-3.2.0.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/js/pagenation.js" type="text/javascript"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap/bootstrap-theme.css">

    <script type="text/javascript">
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

        Service = {

            // 페이징 처리
            page : new pager(),

            list : function () {

                if($("#currentPage").val() == ""){
                    $("#currentPage").val(1);
                }

                $.ajax({
                    url : "listSampleUser",
                    type : "POST",
                    dataType : "json",
                    data: $("#searchForm").serializeArray(),
                    beforeSend: function(xhr) {
                        xhr.setRequestHeader(header, token);
                    },

                    success : function(data) {
                    	
                        $("#list").empty();
                        Service.registState();

                        var html = "";
                        for(i in data.data){

                            html = html+ "<div><a href=\"javascript:Service.view('"+data.data[i].userId+"'); \">" +
                                "사용자 아이디 = " +data.data[i].userId+
                                ", 사용자 이름 = " +data.data[i].userName+
                                ", 패스워드 = " + data.data[i].password+
                                ", 가입일 = " + data.data[i].createDate+ "<div>";
                        }

                        $("#list").html(html);
                        Service.paging();

                        setTimeout(Service.clearResult, 1000);
                    },
                    error: function(data){
                        $("#list").empty();
                    }
                });
            },

            paging : function () {
                $.ajax({
                    url : "listSampleUserCount",
                    type : "POST",
                    dataType : "json",
                    data: $("#searchForm").serializeArray(),
                    beforeSend: function(xhr) {
                        xhr.setRequestHeader(header, token);
                    },

                    success : function(data) {
                        Service.page.opts.maxListCount = $("#pageSize").val();
                        Service.page.opts.currentPage = $("#currentPage").val();
                        Service.page.renderpager(data.data);
                    },
                    error: function(data){
                        console.log(data);
                    }
                });
            },

            changePage : function (currentPage){
                $("#currentPage").val(currentPage);
                Service.list();
            },

            view : function(userId) {
                $.ajax({
                    url : "viewSampleUser",
                    type : "POST",
                    dataType : "json",
                    data: {userId: userId},
                    beforeSend: function(xhr) {
                        xhr.setRequestHeader(header, token);
                    },

                    success : function(data) {
                        $("#result").empty();
                        Service.modifyState();
                        $("#userId").val(data.data.userId);
                        $("#userName").val(data.data.userName);
                        $("#password").val(data.data.password);
                    },
                    error: function(data){
                        $("#list").empty();
                    }
                });
            },

            modify : function() {
                $.ajax({
                    url : "modifySampleUser",
                    type : "POST",
                    dataType : "text",
                    data: $("#frm01").serializeArray(),
                    beforeSend: function(xhr) {
                        xhr.setRequestHeader(header, token);
                    },

                    success : function(data) {
                        $("#result").empty();
                        var rawJsonData = JSON.parse(data);
                        $("#result").html(rawJsonData.message);

                        Service.list();
                    },
                    error: function(data){
                        $("#result").empty();
                    }
                });
            },

            regist : function() {
                $.ajax({
                    url : "registSampleUser",
                    type : "POST",
                    dataType : "text",
                    data: $("#frm01").serializeArray(),
                    beforeSend: function(xhr) {
                        xhr.setRequestHeader(header, token);
                    },

                    success : function(data) {
                        $("#result").empty();
                        var rawJsonData = JSON.parse(data);
                        $("#result").html(rawJsonData.message);
                        Service.registState();
                        Service.list();
                    },
                    error: function(data){
                        $("#result").empty();
                    }
                });
            },

            delete : function() {
                $.ajax({
                    url : "deleteSampleUser",
                    type : "POST",
                    dataType : "text",
                    data: $("#frm01").serializeArray(),
                    beforeSend: function(xhr) {
                        xhr.setRequestHeader(header, token);
                    },

                    success : function(data) {
                    	xhr.setRequestHeader(header, token);
                        $("#result").empty();
                        var rawJsonData = JSON.parse(data);
                        $("#result").html(rawJsonData.message);
                        Service.registState();
                        Service.list();
                    },
                    error: function(data){
                        $("#result").empty();
                    }
                });
            },

            cancel : function() {
                document.frm01.reset();
                $("#resistBtn").show();
                $("#modifyBtn").hide();
                $("#deleteBtn").hide();
            },
            
            registState : function () {
                document.frm01.reset();
                $("#resistBtn").show();
                $("#modifyBtn").hide();
                $("#deleteBtn").hide();
            },
            
            modifyState : function() {
                $("#resistBtn").hide();
                $("#modifyBtn").show();
                $("#deleteBtn").show();
            },

            clearResult: function(){
                $("#result").empty();
            }
        }

        $(document).ready(function(){
            Service.registState();
        });


    </script>
</head>

<body style="margin-left: 10px;">

<a href="${pageContext.request.contextPath}/sample/user"><h1>Sample page</h1></a>
<hr>

<u>1.정보 등록/수정하기</u><br/>
<form id="frm01" name="frm01">
&nbsp; ID:<input type="text" id="userId" name="userId" style="border-top:none;border-left:none;border-right:none;"/><br/>
&nbsp; Name:<input type="text" id="userName" name="userName" style="border-top:none;border-left:none;border-right:none;"/><br/>
&nbsp; Password:<input type="text" id="password" name="password" style="border-top:none;border-left:none;border-right:none;"/><br/><br/>
&nbsp; <input type="button" id="resistBtn" value="Regist" onclick="Service.regist();"/>
       <input type="button" id="modifyBtn" value="Modify" onclick="Service.modify();"/>
       <input type="button" id="deleteBtn" value="Delete" onclick="Service.delete();"/>
       <input type="button" id="cancelBtn" value="Cancel" onclick="Service.cancel();"/>
</form>

<div style="height: 20px;"></div>
<div id="result" style="height:40px;"></div>


<u>2.리스트 조회하기</u><br/>
<form id="searchForm" name="searchForm">
    <table>
        <tr>
            <td>
                검색조건<br/>
                <input type="button" value="Search" onclick="Service.list();"/>
            </td>
            <td>
                &nbsp; ID:<input type="text" id="userId" name="userId" style="border-top:none;border-left:none;border-right:none;"/><br/>
                &nbsp; Name:<input type="text" id="userName" name="userName" style="border-top:none;border-left:none;border-right:none;"/><br/>
                &nbsp; 페이지당 건수:<input type="text" id="pageSize" name="pageSize" style="border-top:none;border-left:none;border-right:none;" value="10"/><br/>
                <input type="hidden" id="currentPage" name="currentPage" />
            </td>
        </tr>
    </table>
    <br/>
</form>
<div id="list"></div>
<div class="container">
    <ul id="paging" class="pagination">

    </ul>
</div>
</body>
</html>