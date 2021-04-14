
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
		<h2>${message}</h2>
		<br>

		<c:if test="${not empty workplaces}">

			<ul id="main-list">
				<c:forEach var="workplace" items="${workplaces}">

					<c:if test="${workplaces.indexOf(workplace) == 0}">
						<ul>
							<li>${workplace.partition}</li>
					</c:if>

					<c:if test="${workplaces.indexOf(workplace) != 0
						&& workplace.partition != workplaces.get(workplaces.indexOf(workplace) - 1).partition}">

						</ul>
						<ul>
							<li>${workplace.partition}</li>
					</c:if>
							<li>
								<a class="button large-size" href="/${workplace.post}">${workplace.post}</a>
							</li>
				</c:forEach>
						</ul>
			</ul>

		</c:if>

	</div>

</body>
</html>