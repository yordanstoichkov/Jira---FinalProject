<%@page errorPage="error.jsp"%>
<%@include file="menu.jsp"%>
<%@page session="false"%>
<html>
<head>
<link href="bootstrap.min.css" rel="stylesheet">
<link href="sb-admin-2.css" rel="stylesheet">
</head>
<body>
	<div class="row" style="padding-top:70px; padding-left:50px">
		<div class="zaglavie">
			<h1 class="page-header" style="color: #1b5c9e;">
				<c:out value="${employee.firstName}"></c:out><c:out value="  "></c:out><c:out value="${employee.lastName}"></c:out>
			</h1>
		</div>
	</div>
</body>
</html>