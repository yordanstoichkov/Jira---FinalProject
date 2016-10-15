<%@page errorPage="error.jsp"%>
<%@include file="menu.jsp"%>
<%@page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<head>
<link href="font-awesome.min.css" rel="stylesheet" type="text/css">
<link href="<c:url value="background.css"></c:url>" rel="stylesheet"
	type="text/css">
<link href="iconic.css" rel="stylesheet">
<link href="bootstrap.min.css" rel="stylesheet">
<link href="sb-admin-2.css" rel="stylesheet">
<script src="jquery.min.js"></script>
<script src="bootstrap.min.js"></script>
<script src="metisMenu.min.js"></script>
<script src="sb-admin-2.js"></script>

<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="Sat, 01 Dec 2001 00:00:00 GMT">
</head>
<body>

	<div class="container">
		<div class="row">
			<div class="col-md-4 col-md-offset-4">

				<div class="forma">
					<div class="panel-heading">
						<h1 class="panel-title" style="color: #5c85d6; font-size: 250%;">Login</h1>
					</div>
					<div class="panel-body">
						<c:if test="${not empty message }">
							<p>
								<span style="color: #ff3333; font-size: 140%"><c:out
										value="${message}"></c:out></span>
							</p>
						</c:if>

						<form:form>
							<fieldset>

								<div class="form-group">
									<input class="form-control" placeholder="E-mail" name="email"
										type="email" autofocus>
								</div>
								<div class="form-group">
									<input class="form-control" placeholder="Password"
										name="password" type="password" value="">
								</div>

								<div class="checkbox">
									<label> <input name="remember" type="checkbox"
										value="Remember Me"><span style="color: #5c85d6;">Remember
											Me</span>
									</label>
								</div>
								<!-- Change this to a button or input when using this as a form -->
								<button type="submit" class="btn btn-lg btn-success btn-block">Login</button>
							</fieldset>
						</form:form>
					</div>
				</div>
			</div>

		</div>
	</div>
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
</body>
</html>