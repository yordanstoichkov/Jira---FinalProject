<%@include file="menu.jsp"%>
<%@page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, shrink-to-fit=no, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link href="<c:url value="sidebar.css"/>" rel="stylesheet"
	type="text/css">

</head>

<body>

	<div id="wrapper">

		<!-- Sidebar -->
		<div id="sidebar-wrapper">
			<ul class="sidebar-nav">
				<li><h1 style="font-size: 125%; color: green">${project.title}</h1>
				<li>
				<li><a href="./projectmain?projectId=${project.projectId}"><spring:message code="nav.plan" /></a></li>
				<li><a href="./active"><spring:message code="nav.active" /></a></li>
				<li><a href="./done"><spring:message code="nav.done" /></a></li>
				<li><a href="./overview"><spring:message code="nav.overvie" /></a></li>
			</ul>
		</div>
		<!-- /#sidebar-wrapper -->

	</div>