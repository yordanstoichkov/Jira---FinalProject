<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page errorPage="errorPage/error.jsp"%>
<link href="bootstrap.min.css" rel="stylesheet">
<link href="metisMenu.min.css" rel="stylesheet">
<link href="sb-admin-2.css" rel="stylesheet">
<link href="font-awesome.min.css" rel="stylesheet" type="text/css">
<link href="<c:url value="background.css"></c:url>" rel="stylesheet"
	type="text/css">

</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-md-4 col-md-offset-4">
				<div class="forma">
					<div class="panel-heading">
						<h1 class="panel-title" style="color:white;  font-size:250%;" >Login</h1>
					</div>
					<div class="panel-body">
						<c:if test="${not empty message }">
							<p>
								<span style="color:#ff3333; font-size:140%"><c:out value="${message}"></c:out></span>
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
										value="Remember Me"><span style="color:white;">Remember Me</span>
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
	<script src="../vendor/jquery/jquery.min.js"></script>
	<script src="../vendor/bootstrap/js/bootstrap.min.js"></script>
	<script src="../vendor/metisMenu/metisMenu.min.js"></script>
	<script src="../dist/js/sb-admin-2.js"></script>

</body>

</html>
