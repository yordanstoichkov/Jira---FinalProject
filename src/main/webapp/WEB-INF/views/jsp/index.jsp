<%@page errorPage="./error.jsp"%>
<%@include file="menu.jsp"%>
<%@page session="false"%>

<%
	if (request.getSession(false) == null) {
%>
<%@include file="login.jsp"%>
<%
	}
%>
<head>
<link href="bootstrap.min.css" rel="stylesheet">
</head>
<body>

</body>
</html>
