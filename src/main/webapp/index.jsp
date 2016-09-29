<%@include file="menu.jsp"%>
<%
	if (request.getSession(false) == null) {
%>
<%@include file="login.jsp"%>
<%
	}
%>
</body>
</html>
