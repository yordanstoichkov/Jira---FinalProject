<%@page errorPage="error.jsp"%>
<%@page session="false"%>
<%@include file="menu.jsp"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="bootstrap.min.css" rel="stylesheet">
<link href="sb-admin-2.css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<p style="float: left">
	<div class="row" style="padding-top: 70px; padding-left: 50px">
		<div class="zaglavie">
			<h1 class="page-header" style="color: #1b5c9e;">
				<c:out value="Idea Tracker"></c:out>
			</h1>
		</div>
	</div>
	<p style="float: left;">
		<img src="siteAvatar.png" width="300" height="300"
			style="padding-left: 70px;"></img>
	</p>
	<p>
	<div class="zaglavie" style="margin-left: 50px; padding-left: 100px;">
		<h4 class="page-header" style="color: #1b5c9e;">
			&nbsp;&nbsp;&nbsp;Email:
			<c:out value="ideatracker@gmail.com"></c:out>

		</h4>
	</div>

	<div class="zaglavie">
		<h4 class="page-header" style="color: #1b5c9e;">
			&nbsp;&nbsp;&nbsp;
			<spring:message code="contactus" />
			:<br /> <span style="padding-left: 110px">Gergana Barilska,</span><br />
			<span style="padding-left: 110px">Yordan Stoichkov</span>
		</h4>
		<h4 class="page-header" style="color: #1b5c9e;">
			<spring:message code="slogan" />
			!
		</h4>
	</div>
	</p>
	<div class="logo">
		<img src="logo.png" width=610px; height=460px;
			style="color: #1b5c9e; display: inline" />
	</div>
</body>
</html>