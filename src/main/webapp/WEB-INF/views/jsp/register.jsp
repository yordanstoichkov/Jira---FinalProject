<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
	<%@page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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
	<a href="<spring:message code="lang"/>"> <img height="30"
		width="30" src="<spring:message code="pic"/>"></a>
	<div class="container" style="padding-top: 100px;">
		<div class="row">
			<div class="col-md-4 col-md-offset-4">

				<div class="panel-heading">
					<h1 class="panel-title" style="color: #2e5cb8;; font-size: 250%;">
						<spring:message code="register.register" />
					</h1>
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
								<input class="form-control"
									placeholder="<spring:message code="regist.firstName" />"
									name="firstname" type="text" autofocus>
							</div>
							<div class="form-group">
								<input class="form-control"
									placeholder="<spring:message code="register.lastName" />"
									name="lastname" type="text" autofocus>
							</div>
							<div id="answer"></div>
							<div class="form-group">

								<input class="form-control" id="email" placeholder="E-mail"
									name="email" type="email" autofocus onblur="checkEmail()">

							</div>

							<div class="form-group">
								<input class="form-control"
									placeholder="<spring:message code="password" />"
									name="password" type="password" value="">
							</div>
							<div class="form-group">
								<input class="form-control"
									placeholder="<spring:message code="password" />"
									name="passwordrepeat" type="password" value="">
							</div>
							<div class="form-group">
								<label style="color: #2e5cb8;" for="username"><spring:message
										code="register.job" />: </label> <select class="form-control"
									name="job">
									<option value="MANAGER"><spring:message code="manager" /></option>
									<option value="DEVELOPER"><spring:message
											code="developer" /></option>
									<option value="QA"><spring:message code="qa" /></option>
								</select>
							</div>
							<div class="checkbox"></div>
							<!-- Change this to a button or input when using this as a form -->
							<button type="submit" class="btn btn-lg btn-success btn-block">
								<spring:message code="register.register" />
							</button>
							<span style="color: #5c85d6;"><a href="./"><spring:message
										code="login.login"></spring:message></a></span>
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
