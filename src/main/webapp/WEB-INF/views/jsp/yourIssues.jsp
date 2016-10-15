
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
									<th>Type</th>
									<th>Status</th>
									<th>Priority</th>

								</tr>
							</thead>
							<tbody>
								<c:forEach items="${issues}" var="issue">
									<tr class="odd gradeX">
										<td>${issue.title}</td>
										<td><c:if test="${issue.type == 'TASK'}">
												<img src="task.png" height="20" width="20">
												<c:out value=" Task"></c:out>
											</c:if> <c:if test="${issue.type == 'BUG'}">
												<img src="bug.png" height="20" width="20">
												<c:out value=" Bug"></c:out>
											</c:if></td>
										<td><c:if test="${issue.status == 'IN_PROGRESS'}">
												<i class="fa fa-cog" style="font-size: 1.5em; color: orange"></i>
												<c:out value=" In Progress"></c:out>
											</c:if> <c:if test="${issue.status == 'DONE'}">
												<i class="fa fa-check"
													style="font-size: 1.5em; color: green"> </i>
												<c:out value=" Done"></c:out>
											</c:if> <c:if test="${issue.status == 'TO_DO'}">
												<i class="fa fa-times" style="font-size: 1.5em; color: red"></i>
												<c:out value=" To Do"></c:out>
											</c:if></td>
										<td class="center"><c:if
												test="${issue.priority == 'MEDIUM'}">
												<img src="medium.png" height="20" width="20">
												<c:out value=" Medium"></c:out>
											</c:if> <c:if test="${issue.priority == 'HIGH'}">
												<img src="high.png" height="20" width="20">
												<c:out value=" High"></c:out>
											</c:if> <c:if test="${issue.priority == 'LOW'}">
												<img src="low.png" height="20" width="20">
												<c:out value=" Low"></c:out>
											</c:if></td>

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