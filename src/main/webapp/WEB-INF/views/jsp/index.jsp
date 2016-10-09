<%@page errorPage="error.jsp"%>
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
<link href="sb-admin-2.css" rel="stylesheet">


</head>
<body>
	<%
		if (request.getSession(false) == null) {
	%>
	<div class="counts">
		<h1>
			<span class="user"><i class="fa fa-group"
				style="font-size: 1.0em;"></i>&nbsp;Jira users:
				${usersCount}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
			<span class="project"><i class="fa fa-folder-open"
				style="font-size: 1.0em;"></i>&nbsp;Jira projects:
				${projectsCount}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
			<span class="issue"><i class="fa fa-thumb-tack"
				style="font-size: 1.0em;"></i>&nbsp;Jira issues: ${issuesCount}</span>


		</h1>

	</div>
	<%
		}
	%>
</body>
</html>