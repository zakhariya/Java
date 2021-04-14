<%--
  Created by IntelliJ IDEA.
  User: zakhar
  Date: 09.05.2018
  Time: 13:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>${title}</title>

    <meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=2.0, user-scalable=yes"/>

    <link rel="icon" href="<c:url value="/resources/img/favicon.ico" />" type="image/x-icon">
    <link rel="shortcut icon" href="<c:url value="/resources/img/favicon.ico" />" type="image/x-icon">

    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css" />">
    <link rel="stylesheet" href="<c:url value="/resources/css/common.css" />">
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css" />">

</head>
<body>

<div id="wrapper">

    <h1>${title}</h1>
    <p>${message}</p>
    <br>

    <hr>

    <a class="button medium-size" href="${url}">Назад</a>

    <hr>

    <a class="button medium-size" href="/">На главную</a>

</div>

</body>
</html>
