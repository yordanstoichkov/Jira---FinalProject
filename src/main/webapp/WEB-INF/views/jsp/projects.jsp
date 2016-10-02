<%@include file="menu.jsp"%>
<%@ page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="bootstrap.min.css" rel="stylesheet">
<link href="metisMenu.min.css" rel="stylesheet">
<link href="project.css" rel="stylesheet">
<link href="font-awesome.min.css" rel="stylesheet" type="text/css">
</head>
<body>
	<%
		ArrayList<String> names = (ArrayList<String>) request.getAttribute("names");
		for (String name : names) {
	%>
	<div class="container">
		<div id="absolute">

			<div class="col-lg-12">
				<div class="panel panel-default">

					<div class="panel-body">


						<div id="projectName">

							<div class="timeline-panel">
								<div class="timeline-body">
									<h3><%=name%></h3>
								</div>
							</div>


						</div>
						<div class="col-lg-3 col-md-2">

							<button type="button" id="track"
								class="btn btn-outline btn-primary btn-lg" float="left">Track
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
												<font size="4">12</font>
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
												<font size="4">12</font>
											</div>
											<div id="status">
												<h3>
													<b>Progress</b>
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
												<font size="4">12</font>
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
			</div>

		</div>
	</div>


	<%
		}
	%>
</body>
</html>
