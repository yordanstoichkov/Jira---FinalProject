<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@include file="menu.jsp"%>
<%@page errorPage="error.jsp"%>
<%@ page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="iconic.css" rel="stylesheet">
<link href="metisMenu.min.css" rel="stylesheet">
<link href="bootstrap.min.css" rel="stylesheet">

<link href="project.css" rel="stylesheet">
<link href="createProject.css" rel="stylesheet">
<link href="font-awesome/css/font-awesome.min.css" rel="stylesheet"
	type="text/css">

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css">
<script type="text/javascript" src="jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="apprise.js"></script>
</head>
<body>


	<div class="button">
		<button class="myButton" id="myBtn">
			<i class="fa fa-plus"
				style="font-size: 1.5em; padding-right: 15px; padding-top: 5px"></i>Create
			new project
		</button>
	</div>
	<!-- The Modal -->
	<div id="myModal" class="modal">

		<!-- Modal content -->
		<div class="modal-content">
			<span class="close">×</span>
			<form:form name="form" commandName="emptyproject"
				onsubmit="return validateForm()">
				<label>Enter title*</label>
				<form:input type="text" name="title" path="title"
					cssErrorClass="error" />
				<p id="demo"></p>
				<form:errors path="title" cssClass="error"></form:errors>
				<br />
				<input type="submit" value="Create project" />
			</form:form>
		</div>

	</div>


	<div class="container">
		<div id="absolute">
			<div class="col-lg-12">
				<c:forEach var="project" items="${projects}">
					<div class="panel panel-default">
						<div class="panel-body">
							<div id="projectName">
								<div class="timeline-panel">
									<div class="timeline-body">
										<h3>
											<c:out value="${project.title}" />
										</h3>
									</div>
								</div>
							</div>
							<div class="col-lg-3 col-md-2">
								<form action="./projectmain">
									<button type="submit" id="track" name="projectId"
										value="${project.projectId}"
										class="btn btn-outline btn-primary btn-lg">
										<img src="eye.png" width=17px; height=17px;>
									</button>
								</form>
								<form action="./deleteProject" method="post">
									<button type="submit" class="btn btn-outline btn-danger"
										value="${project.projectId}" id="delete" name="projectId">
										<img src="bin.png" width=17px; height=17px;>
									</button>

								</form>

							</div>


							<div class="col-lg-3 col-md-6" id="kvadrat">
								<div class="panel panel-red">
									<div class="panel-heading">
										<div class="row">
											<div class="col-xs-3">
												<i class="fa fa-times" style="font-size: 5.0em;"></i>
											</div>
											<div class="col-xs-9 text-right">
												<div class="huge">
													<font size="4"> <c:out value="${project.toDo}" />
													</font>
												</div>
												<div id="status">
													<h3>
														<b>To Do</b>
													</h3>
												</div>
											</div>
										</div>
									</div>
									<div class="panel-footer">
										<a href="#"><span class="pull-left">View Details</span></a> <span
											class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
										<div class="clearfix"></div>
									</div>
								</div>
							</div>
							<div class="col-lg-3 col-md-6" id="kvadrat">
								<div class="panel panel-yellow">
									<div class="panel-heading">
										<div class="row">
											<div class="col-xs-3">
												<i class="fa fa-cog" style="font-size: 5.0em;"></i>
											</div>
											<div class="col-xs-9 text-right">
												<div class="huge">
													<font size="4"> <c:out value="${project.inProgress}" />
													</font>
												</div>
												<div id="status">
													<h3>
														<b>In Progress</b>
													</h3>
												</div>
											</div>
										</div>
									</div>

									<div class="panel-footer">
										<a href="#"><span class="pull-left">View Details</span></a> <span
											class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
										<div class="clearfix"></div>
									</div>
								</div>
							</div>
							<div class="col-lg-3 col-md-6" id="kvadrat">
								<div class="panel panel-green">
									<div class="panel-heading">
										<div class="row">
											<div class="col-xs-3">
												<i class="fa fa-check" style="font-size: 5.0em;"> </i>
											</div>
											<div class="col-xs-9 text-right">
												<div class="huge">
													<font size="4"> <c:out value="${project.done}" />
													</font>
												</div>
												<div>
													<h3>
														<b>Done</b>
													</h3>
												</div>
											</div>
										</div>
									</div>
									<div class="panel-footer">
										<a href="#"><span class="pull-left">View Details</span> </a><span
											class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
										<div class="clearfix"></div>
									</div>

								</div>
							</div>
						</div>

					</div>
				</c:forEach>
			</div>

		</div>
	</div>
	<script>
		var modal = document.getElementById('myModal');
		var btn = document.getElementById("myBtn");
		var span = document.getElementsByClassName("close")[0];
		btn.onclick = function() {
			modal.style.display = "block";
		}
		span.onclick = function() {
			modal.style.display = "none";
		}
		window.onclick = function(event) {
			if (event.target == modal) {
				modal.style.display = "none";
			}
		}

		function validateForm() {
			$("#demo").empty();
			var text;
			var x = trim(document.forms["form"]["title"].value);
			if (x == null || x == "") {
				text = "Name must be filled out";
				$("#demo").append(text);
				return false;
			}

		}
		function trim(value) {
			return value.replace(/^\s+|\s+$/g, "");
		}
	</script>

</body>
</html>
