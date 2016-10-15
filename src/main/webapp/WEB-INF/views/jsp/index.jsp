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
	<img src="teamBack.png" style="float: right; margin-top: -80px;">
	<div class="counts">
		<h3>
			<span class="user" style="color: #2e5cb8"><i
				class="fa fa-group" style="font-size: 1.0em; color: #2e5cb8"></i>&nbsp;IdeaTracker
				users: ${usersCount}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span> <span
				class="project" style="color: #2e5cb8"><i
				class="fa fa-folder-open" style="font-size: 1.0em; color: #2e5cb8"></i>&nbsp;IdeaTracker
				projects: ${projectsCount}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span> <span
				class="issue" style="color: #2e5cb8"><i
				class="fa fa-thumb-tack" style="font-size: 1.0em; color: #2e5cb8"></i>&nbsp;IdeaTracker
				issues: ${issuesCount}</span>


		</h3>

	</div>
	<%
		}
	%>

	<%
		if (request.getSession(false) != null) {
	%>
	<div class="row">
		<div class="zaglavie">
			<h1 class="page-header"
				style="color: #1b5c9e; padding-left: 50px; padding-top: 250px">
				Create your projects here!

			</h1>

		</div>
	</div>

	<div class="logo">
		<img src="logo.png" width=610px; height=460px;
			style="color: #1b5c9e; display: inline" />
	</div>
	<%
		}
	%>



</body>
</html>