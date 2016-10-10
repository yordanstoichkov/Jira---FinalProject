<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@include file="menu.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<link href="bootstrap.min.css" rel="stylesheet">
<link href="metisMenu.min.css" rel="stylesheet">

<link href="sb-admin-2.css" rel="stylesheet">
<link href="font-awesome.min.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="jquery-3.1.1.min.js"></script>
</head>
<body>
	<script>
		function checkEmail() {
			var email = $("#email").val();
			$.get(
					"http://localhost:8080/JiraProject/checkemail?email="
							+ email, function(data) {
						$("#answer").empty();
						var obj = data;
						var img = document.createElement("img");
						img.src = obj;
						img.width = 20;
						img.height = 20;
						img.id = "em";
						$("#answer").append(img)
					});
		}
	</script>
	<div class="container">
		<div class="row">
			<div class="col-md-4 col-md-offset-4">

				<div class="panel-heading">
					<h1 class="panel-title" style="color: white; font-size: 250%;">Register</h1>
				</div>
				<div class="panel-body">

					<c:if test="${not empty message }">
						<p>
							<c:out value="${message}"></c:out>
						</p>
					</c:if>
					<form role="form" method="post" action="./reg">
						<fieldset>
							<div class="form-group">
								<input class="form-control" placeholder="First name"
									name="firstname" type="text" autofocus>
							</div>
							<div class="form-group">
								<input class="form-control" placeholder="Last name"
									name="lastname" type="text" autofocus>
							</div>
							<div id="answer"></div>
							<div class="form-group">

								<input class="form-control" id="email" placeholder="E-mail"
									name="email" type="email" autofocus onblur="checkEmail()">

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
								<label style="color: white;" for="username">Choose your
									job: </label> <select class="form-control" name="job">
									<option value="MANAGER">Manager</option>
									<option value="DEVELOPER">Developer</option>
									<option value="QA">QA</option>
								</select>
							</div>
							<div class="checkbox"></div>
							<!-- Change this to a button or input when using this as a form -->
							<button type="submit" class="btn btn-lg btn-success btn-block">Register</button>
						</fieldset>
					</form>
				</div>
			</div>
		</div>
	</div>

	<!-- jQuery -->
	<script src="jquery.min.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="bootstrap.min.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script src="metisMenu.min.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="sb-admin-2.js"></script>

</body>

</html>
