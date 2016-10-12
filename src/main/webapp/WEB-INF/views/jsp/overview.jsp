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
<link href="bootstrap.min.css" rel="stylesheet">
<link href="metisMenu.min.css" rel="stylesheet">
<link href="sb-admin-2.css" rel="stylesheet">
<link href="morris.css" rel="stylesheet">
<link href="font-awesome.min.css" rel="stylesheet" type="text/css">
</head>
<body>
	<script src="jquery.min.js"></script>
	<script src="bootstrap.min.js"></script>
	<script src="metisMenu.min.js"></script>
	<script src="excanvas.min.js"></script>
	<script src="jquery.flot.js"></script>
	<script src="jquery.flot.pie.js"></script>
	<script src="jquery.flot.resize.js"></script>
	<script src="jquery.flot.time.js"></script>
	<script src="jquery.flot.tooltip.min.js"></script>
	<script src="flot-data.js"></script>
	<script src="sb-admin-2.js"></script>

	<div class="row">
		<div class="zaglavie">
			<h1 class="page-header" style="color: #1b5c9e;">
				<c:out value="${project.title}"></c:out>
			</h1>
		</div>
	</div>
	<div class="col-lg-6" style="padding-left: 280px">

		<div class="panel-body">
			<div class="flot-chart">
				<div class="flot-chart-content" id="flot-pie-chart"></div>
			</div>

		</div>
	</div>
</body>
</html>