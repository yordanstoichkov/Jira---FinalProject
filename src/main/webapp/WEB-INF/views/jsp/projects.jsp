<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@include file="menu.jsp"%>
<%@page errorPage="error.jsp"%>
<%@page session="false"%>
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
	<div class="main" style="padding-top: 75px">
		<div class="button">
			<button type="button" id="mybtn" class="btn btn-primary btn-lg"
				style="margin-left: 80px; width: 300px;">
				<i class="fa fa-plus"
					style="font-size: 1.5em; padding-right: 15px; padding-top: 5px"></i>
				<spring:message code="project.create" />
			</button>
		</div>

		<hr style="width: 90%; color: black">

		<!-- The Modal -->
		<div id="myModal" class="modal">

			<!-- Modal content -->
			<div class="modal-content">
				<span class="close">×</span>
				<form:form name="form" commandName="emptyproject"
					onsubmit="return validateForm()">
					<label><spring:message code="project.title" />*</label>
					<form:input type="text" name="title" path="title"
						cssErrorClass="error" />
					<p id="demo"></p>
					<form:errors path="title" cssClass="error"></form:errors>
					<br />
					<input type="submit"
						value="<spring:message code="project.create" />" />
				</form:form>
			</div>

		</div>


		<div class="container">
			<div class="col-lg-12">



				<c:forEach var="project" items="${projects}">


					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#accordion"
									href="#collapseOne"> <c:out value="${project.title}" />

								</a>
							</h4>
						</div>
						<div id="collapseOne" class="panel-collapse collapse in">
							<div class="panel-body">
								<div class="col-lg-3 col-md-2" style="padding-top: 30px;">
									<form action="./projectmain">
										<a href="#" data-toggle="tooltip" title="Open project">
											<button type="submit" id="track" name="projectId"
												value="${project.projectId}"
												class="btn btn-outline btn-primary btn-lg">
												<img src="eye.png" width=17px; height=17px;>
											</button>
										</a>
									</form>
									<form action="./deleteProject" method="post">
										<a href="#" data-toggle="tooltip" title="Delete project">
											<button type="submit" class="btn btn-outline btn-danger"
												value="${project.projectId}" id="delete" name="projectId">
												<img src="bin.png" width=17px; height=17px;>
											</button>
										</a>

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
															<b><spring:message code="to" /></b>
														</h3>
													</div>
												</div>
											</div>
										</div>
										<div class="panel-footer">
											<a href="./projectmain?projectId=${project.projectId}"><span
												class="pull-left"><spring:message code="details" /></span></a>
											<span class="pull-right"><i
												class="fa fa-arrow-circle-right"></i></span>
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
														<font size="4"> <c:out
																value="${project.inProgress}" />
														</font>
													</div>
													<div id="status">
														<h3>
															<b><spring:message code="inprogres" /></b>
														</h3>
													</div>
												</div>
											</div>
										</div>

										<div class="panel-footer">
											<a href="./projectmain?projectId=${project.projectId}"><span
												class="pull-left"><spring:message code="details" /></span></a>
											<span class="pull-right"><i
												class="fa fa-arrow-circle-right"></i></span>
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
															<b><spring:message code="done" /></b>
														</h3>
													</div>
												</div>
											</div>
										</div>
										<div class="panel-footer">
											<a href="./projectmain?projectId=${project.projectId}"><span
												class="pull-left"><spring:message code="details" /></span>
											</a><span class="pull-right"><i
												class="fa fa-arrow-circle-right"></i></span>
											<div class="clearfix"></div>
										</div>

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
		var btn = document.getElementById("mybtn");
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
	<script>
		$(document).ready(function() {
			$('[data-toggle="tooltip"]').tooltip();
		});
	</script>
</body>
</html>
