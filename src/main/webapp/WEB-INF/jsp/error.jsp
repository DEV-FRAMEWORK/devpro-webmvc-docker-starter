<%--

Class Name 403.jsp

Description: 403에러 페이지

Modification Information

수정일            수정자             수정내용

----------  -----------  ---------------------------------------

2017.05.31  gilgams      최초작성

author: gilgams

since:2017.05.31

--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html lang="ko-KR">

<head>

    <meta charset="utf-8">

    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <title>Template 에러페이지</title>

 
</head>


 

<body >

    <div class="login-area">

        <div class="column">

            <div class="inner">

                    <fieldset>

                        <h1>Custom Error Page!!!!!!</h1>
                        <h1>${errorMsg}</h1>

                    </fieldset>

            </div>

        </div>

    </div>

</body>

</html>