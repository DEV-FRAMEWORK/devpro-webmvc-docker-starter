<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>Sample::사용자 사용자 정보 관리</title>

    <script src="${pageContext.request.contextPath}/js/jquery/jquery-3.2.0.min.js" type="text/javascript"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap/bootstrap-theme.css">

    <script type="text/javascript">

        var webBaseURL = "${pageContext.request.contextPath}/sample/";
        var restBaseURL = "http://localhost:8888${pageContext.request.contextPath}/rest/sample/";

        Service = {

            view1 : function(userId) {
                $.ajax({
                    url : webBaseURL + "viewSampleUser",
                    type : "POST",
                    dataType : "json",
                    data: {userId: userId},
                    success : function(data) {

                        $("#user1").html("<u>H2 Database</u><br/>" + "ID:" + data.data.userId + ", Name:" + data.data.userName + ", Password:" + data.data.password);
                    },
                    error: function(data){
                        $("#result").html(data);
                    }
                });
            },

            view2 : function(userId) {
                $.ajax({
                    url : restBaseURL + "viewSampleUser2pc",
                    type : "POST",
                    dataType : "json",
                    data: {userId: userId},
                    success : function(data) {
                        $("#user2").html("<u>H2 Database</u><br/>" + "ID:" + data.data.userId + ", Name:" + data.data.userName + ", Password:" + data.data.password);
                    },
                    error: function(data){
                        $("#result").html(data);
                    }
                });
            },

            modify : function() {
                $.ajax({
                    url : restBaseURL + "modifySampleUser2pc",
                    type : "POST",
                    dataType : "text",
                    data: $("#frm01").serializeArray(),
                    success : function(data) {
                        $("#result").empty();

                        var rawJsonData = JSON.parse(data);
                        $("#result").html(rawJsonData.message);

                        if(rawJsonData.returnCode == "OK"){
                            Service.view1($("#userId").val());
                            Service.view2($("#userId2").val());
                        }
                    },
                    error: function(data){
                        $("#result").empty();
                        var rawJsonData = JSON.parse(data.responseText);
                        $("#result").html(rawJsonData.message);
                    }
                });
            },

            regist : function() {
                $.ajax({
                    url : restBaseURL + "registSampleUser2pc",
                    type : "POST",
                    dataType : "text",
                    data: $("#frm01").serializeArray(),
                    success : function(data) {
                        $("#result").empty();
                        var rawJsonData = JSON.parse(data);
                        $("#result").html(rawJsonData.message);

                        $("#resistBtn").hide();
                        $("#modifyBtn").show();

                        if(rawJsonData.returnCode == "OK"){
                            Service.view1($("#userId").val());
                            Service.view2($("#userId2").val());
                        }
                    },
                    error: function(data){
                        console.log(data);
                        $("#result").empty();
                        var rawJsonData = JSON.parse(data.responseText);
                        $("#result").html(rawJsonData.message);
                    }
                });
            },

            cancel : function () {
                document.frm01.reset();
                $("#resistBtn").show();
                $("#modifyBtn").hide();
                $("#user1").empty();
                $("#user2").empty();
                $("#result").empty();
            }

        }

        $(document).ready(function(){

            $("#modifyBtn").hide();

            $("#userId").keyup(function() {
                $("#userId2").val($("#userId").val());
            });

            $("#userName").keyup(function() {
                $("#userName2").val($("#userName").val());
            });

            $("#password").keyup(function() {
                $("#password2").val($("#password").val());
            });
        });


    </script>
</head>

<body style="margin-left: 10px;">

<a href="${pageContext.request.contextPath}/sample/user"><h1>Sample page</h1></a>
<hr>
<textarea style="width:700px; height: 140px; border:none;" >
1. 사용자 등록
    : 왼쪽(H2 Database) 입력칸에 사용자 정보 작성 후 저장
2. 사용자 정보 변경(2 PC 정상 테스트)
    : 왼쪽(H2 Database)에서 사용자 정보 변경 후 저장(왼쪽과 오른쪽 정보가 일치할 경우 변경 성)
3. 사용자 정보 변경(2 PC 실패 테스트)
    : 오른쪽(HSQL Database)에서 사용자 정보 변경 후 저장(왼쪽과 오른쪽 정보가 다를 경우 변경 실패)
</textarea><br/>

<form id="frm01" name="frm01">
<table>
    <tr>
        <td>
            <u>H2 Database</u><br/>
                &nbsp; ID:<input type="text" id="userId" name="userId" style="border-top:none;border-left:none;border-right:none;"/><br/>
                &nbsp; Name:<input type="text" id="userName" name="userName" style="border-top:none;border-left:none;border-right:none;"/><br/>
                &nbsp; Password:<input type="text" id="password" name="password" style="border-top:none;border-left:none;border-right:none;"/><br/>
        </td>
        <td>
            <u>HSQL Database</u><br/>

                &nbsp; ID:<input type="text" id="userId2" name="userId2" style="border-top:none;border-left:none;border-right:none;"/><br/>
                &nbsp; Name:<input type="text" id="userName2" name="userName2" style="border-top:none;border-left:none;border-right:none;"/><br/>
                &nbsp; Password:<input type="text" id="password2" name="password2" style="border-top:none;border-left:none;border-right:none;"/><br/>
        </td>
    </tr>
</table>
</form>
<br/>
&nbsp; <input type="button" id="resistBtn" value="Regist" onclick="Service.regist();"/>
<input type="button" id="modifyBtn" value="Modify" onclick="Service.modify();"/>
<input type="button" id="cancelBtn" value="Reset" onclick="Service.cancel();"/>

<div style="height: 20px;"></div>
<div id="result" style="height:40px;"></div>

<div id="user1"></div>
<div id="user2"></div>



</body>
</html>