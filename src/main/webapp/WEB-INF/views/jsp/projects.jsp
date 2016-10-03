<%@include file="menu.jsp"%>
<%@ page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="bootstrap.min.css" rel="stylesheet">
<link href="iconic.css" rel="stylesheet">
<link href="metisMenu.min.css" rel="stylesheet">
<link href="project.css" rel="stylesheet">
<link href="font-awesome/css/font-awesome.min.css" rel="stylesheet"
	type="text/css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css">

</head>
<body>



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

								<button type="button" id="track"
									class="btn btn-outline btn-primary btn-lg">Track
									project</button>
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

</body>
</html>
