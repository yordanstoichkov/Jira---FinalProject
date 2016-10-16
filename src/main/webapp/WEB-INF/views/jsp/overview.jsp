<%@include file="navigation.jsp"%>
<%@page session="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<link href="iconic.css" rel="stylesheet">
<link href="sprint.css" rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css">
<script type="text/javascript" src="jquery-3.1.1.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<link href="sb-admin-2.css" rel="stylesheet">
<link href="morris.css" rel="stylesheet">
<link href="font-awesome.min.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div class="row">
		<div class="zaglavie">
			<h1 class="page-header" style="color: #1b5c9e;">
				<c:out value="${project.title}"></c:out>
				<br>

			</h1>

		</div>
	</div>

	<script type="text/javascript">
		window.onload = function() {

			var chart = new CanvasJS.Chart("chartContainer", {
				theme : "theme2",//theme1
				animationEnabled : true, // change to true
				data : [ {
					// Change type to "bar", "area", "spline", "pie",etc.
					type : "pie",
					dataPoints : [ {
						label : "In Progress",
						y : "${progress}"
					}, {
						label : "To Do",
						y : "${toDo}"
					}, {
						label : "Done",
						y : "${done}"
					} ]
				} ]
			});
			chart.render();
		}
	</script>

	<div id="chartContainer"
		style="padding-top: 0px; height: 300px; width: 100%; margin-left: 60px;"></div>

	<div class="row">
		<div class="col-lg-6">
			<div class="panel panel-default">
				<div class="panel-heading" style="float: center">Sprints</div>
				<!-- /.panel-heading -->
				<div class="panel-body">
					<div class="table-responsive">
						<table class="table table-striped">
							<thead>
								<tr>
									<th><i class="fa fa-times"
										style="font-size: 1.0em; color: red; display: inline"></i> <c:out
											value="   "></c:out>To Do</th>
									<th><i class="fa fa-cog"
										style="font-size: 1.0em; color: orange"></i> <c:out
											value="   "></c:out>In Progress</th>
									<th><i class="fa fa-check"
										style="font-size: 1.0em; color: green"></i> <c:out value="   "></c:out>Done</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td><c:out value="${toDo}"></c:out></td>
									<td><c:out value="${progress}"></c:out></td>
									<td><c:out value="${done}"></c:out></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>