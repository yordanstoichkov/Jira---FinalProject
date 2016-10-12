
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="menu.jsp"%>
<%@page errorPage="error.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="bootstrap.min.css" rel="stylesheet">
<link href="dataTables.bootstrap.css" rel="stylesheet">
<link href="dataTables.responsive.css" rel="stylesheet">
<link href="sb-admin-2.css" rel="stylesheet">
<link href="font-awesome.min.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="jquery-3.1.1.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body style="padding-top: 100px; padding-left: 150px; width: 90%">
	<div id="page-wrap">
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">All your issues</div>
					<!-- /.panel-heading -->
					<div class="panel-body">
						<table width="100%"
							class="table table-striped table-bordered table-hover"
							id="dataTables-example">
							<thead>
								<tr>
									<th>Issue name</th>
									<th>Last modiefied</th>
									<th>Status</th>
									<th>Priority</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${issues}" var="issue">
									<tr class="odd gradeX">
										<td><c:if test="${issue.type == 'TASK'}">
												<img src="task.png" height="20" width="20">
											</c:if> <c:if test="${issue.type == 'BUG'}">
												<img src="bug.png" height="20" width="20">
											</c:if>${issue.title}</td>
										<td>${issue.lastUpdate}</td>
										<td>${issue.status}</td>
										<td class="center"><c:if
												test="${issue.priority == 'MEDIUM'}">
												<img src="medium.png" height="20" width="20">
											</c:if> <c:if test="${issue.priority == 'HIGH'}">
												<img src="high.png" height="20" width="20">
											</c:if> <c:if test="${issue.priority == 'LOW'}">
												<img src="low.png" height="20" width="20">
											</c:if>${issue.priority}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="jquery.dataTables.min.js"></script>
	<script src="dataTables.bootstrap.min.js"></script>
	<script src="dataTables.responsive.js"></script>
	<script>
		$(document).ready(function() {
			$('#dataTables-example').DataTable({
				responsive : true
			});
		});
	</script>
</body>
</html>