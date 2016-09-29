<%@include file="menu.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page errorPage="errorPage/error.jsp" %>
<link href="templateCSS/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<link href="templateCSS/vendor/metisMenu/metisMenu.min.css"
	rel="stylesheet">
<link href="templateCSS/dist/css/sb-admin-2.css" rel="stylesheet">
<link href="templateCSS/vendor/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-md-4 col-md-offset-4">
				<div class="login-panel panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">Register</h3>
					</div>
					<div class="panel-body">

						<%
							if (request.getAttribute("message") != null) {
						%>
						<p>
							<%=request.getAttribute("message")%>
						</p>
						<%
							}
						%>
						<form role="form" method="post" action="./Register">
							<fieldset>
								<div class="form-group">
									<input class="form-control" placeholder="First name"
										name="firstname" type="text" autofocus>
								</div>
								<div class="form-group">
									<input class="form-control" placeholder="Last name"
										name="lastname" type="text" autofocus>
								</div>
								<div class="form-group">
									<input class="form-control" placeholder="E-mail" name="email"
										type="email" autofocus>
								</div>
								<div class="form-group">
									<input class="form-control" placeholder="Password"
										name="password" type="password" value="">
								</div>
								<div class="form-group">
									<input class="form-control" placeholder="Password"
										name="passwordrepeat" type="password" value="">
								</div>
								<div class="form-group">
									<label for="username">Choose your job: </label> <select
										class="form-control" name="job">
										<option value="MANAGER">Manager</option>
										<option value="DEVELOPER">Developer</option>
										<option value="QA">QA</option>
									</select>
								</div>
								<div class="checkbox">
								</div>
								<!-- Change this to a button or input when using this as a form -->
								<button type="submit" class="btn btn-lg btn-success btn-block">Register</button>
							</fieldset>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- jQuery -->
	<script src="../vendor/jquery/jquery.min.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="../vendor/bootstrap/js/bootstrap.min.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script src="../vendor/metisMenu/metisMenu.min.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="../dist/js/sb-admin-2.js"></script>

</body>

</html>
