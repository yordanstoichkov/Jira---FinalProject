<%@page errorPage="error.jsp"%>
<%@include file="menu.jsp"%>
<%@page session="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<link href="bootstrap.min.css" rel="stylesheet">
<link href="sb-admin-2.css" rel="stylesheet">
<link href="iconic.css" rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css">
<script type="text/javascript" src="jquery-3.1.1.min.js"></script>

</head>
<body>
	<p style="float: left">
	<div class="row" style="padding-top: 70px; padding-left: 50px">
		<div class="zaglavie">
			<h1 class="page-header" style="color: #1b5c9e;">
				<c:out value="${friend.firstName}"></c:out>
				<c:out value="  "></c:out>
				<c:out value="${friend.lastName}"></c:out>
			</h1>
		</div>
	</div>
	<p style="float: left;">
		<img src="${friend.avatarPath}" width="300" height="300"
			style="padding-left: 70px;"></img>
	</p>
	<p>
	<div class="zaglavie" style="margin-left: 50px; padding-left: 100px;">
		<h4 class="page-header" style="color: #1b5c9e;">
			&nbsp;&nbsp;&nbsp;Email:
			<c:out value="${friend.email}"></c:out>

		</h4>
	</div>

	<div class="zaglavie">
		<h3 class="page-header" style="color: #1b5c9e; margin-left: 100px;">

			<c:if test="${ friend.job=='MANAGER'}">

				<span style="color: #1b5c9e; display: inline; font-size: 80%">&nbsp;&nbsp;&nbsp;Job:
				</span>
				<img src="manager.png" width=30px; height=30px;
					style="color: #1b5c9e; display: inline" />

				<span style="font-size: 70%"> Manager</span>
			</c:if>

			<c:if test="${  friend.job=='DEVELOPER'}">
				<span style="color: #1b5c9e; display: inline; font-size: 80%">&nbsp;&nbsp;&nbsp;Job:
				</span>

				<img src="developer.png" width=35px; height=35px;
					style="color: #1b5c9e; display: inline" />
				<span style="font-size: 70%">Developer</span>

			</c:if>
			<c:if test="${ friend.job=='REVIEWER'}">
				<span style="color: #1b5c9e; display: inline; font-size: 80%">&nbsp;&nbsp;&nbsp;Job:
				</span>

				<img src="reviewer.png" width=35px; height=35px;
					style="color: #1b5c9e; display: inline" />
				<span style="font-size: 70%"> Reviewer</span>

			</c:if>
		</h3>
	</div>
	</p>

				
	<div class="logo"><img src="logo.png" width=610px; height=460px;
					style="color: #1b5c9e; display: inline" /></div>



</body>
</html>