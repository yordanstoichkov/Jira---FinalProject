<%@include file="navigation.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="false"%>
<link href="sprint.css" rel="stylesheet">
<link href="dataTables.bootstrap.css" rel="stylesheet">
<link href="dataTables.responsive.css" rel="stylesheet">
<link href="font-awesome.min.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="jquery-3.1.1.min.js"></script>
    <link href="bootstrap.min.css" rel="stylesheet">

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, shrink-to-fit=no, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

</head>
<body>

	<div class="row">
		<div class="col-lg-6">
			<div class="panel panel-default">
				<c:if test="${not empty activeSprint}">
					<!-- /.panel-heading -->
					<div class="table-responsive">
						<table class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th colspan="3"><b><c:out value="${activeSprint.title}"></c:out></b></th>

								</tr>
								<tr>
									<th><div class="col-xs-3">
											<i class="fa fa-times" style="font-size: 1.0em; color: red"></i>
										</div> <b>To Do</b></th>
									<th>
										<div class="col-xs-3">
											<i class="fa fa-cog" style="font-size: 1.0em; color: orange"></i>
										</div> <b>In Progress</b>
									</th>
									<th><div class="col-xs-3">
											<i class="fa fa-check" style="font-size: 1.0em; color: green">
											</i>
										</div> <b>Done</b></th>
								</tr>
							</thead>
							<tbody>

								<c:forEach items="${activeSprint.issues}" var="issue">
									<c:if test="${issue.status=='TO_DO'}">
										<tr>
											<td class="danger"><form action="./issue">
													<a data-toggle="tooltip" title="Click for more information"><input
														type="image" src="info.png" width=17px; height=17px;
														align="left" ; name="issueId" class="btTxt submit"
														id="issueId" value="${issue.issueId}" /></a>

												</form>

												<form action="./active" method="post">
													<c:out value="${issue.title}"></c:out>
													<c:forEach items="${issue.asignees}" var="asignee">
														<c:if test="${asignee==userId}">
															<a href="#" data-toggle="tooltip"
																title="Move to In Progress"> <input type="image"
																src="red_arrow.png" width=17px; height=17px;
																align="right" ; name="issueId" class="btTxt submit"
																id="issueId" value="${issue.issueId}" /></a>
														</c:if>
													</c:forEach>

												</form></td>
											<td></td>
											<td></td>
										</tr>
									</c:if>
									<c:if test="${issue.status=='IN_PROGRESS'}">
										<tr>
											<td></td>
											<td class="warning"><form action="./issue">
													<a data-toggle="tooltip" title="Click for more information">
														<input type="image" src="info.png" width=17px;
														height=17px; align="left" ; name="issueId"
														class="btTxt submit" id="issueId" value="${issue.issueId}" />
													</a>
												</form>

												<form action="./active" method="post">
													<c:out value="${issue.title}"></c:out>
													<c:forEach items="${issue.asignees}" var="asignee">
														<c:if test="${asignee==userId}">
															<a href="#" data-toggle="tooltip" title="Move to Done">
																<input type="image" src="orange_arrow.png" width=20px;
																height=20px; align="right" ; name="issueId"
																class="btTxt submit" id="saveForm"
																value="${issue.issueId}" />
															</a>
														</c:if>
													</c:forEach>
												</form></td>

											<td></td>
										</tr>
									</c:if>
									<c:if test="${issue.status=='DONE'}">
										<tr>
											<td></td>
											<td></td>
											<td class="success"><form action="./issue">
													<a data-toggle="tooltip" title="Click for more information">
														<input type="image" src="info.png" width=17px;
														height=17px; align="left" ; name="issueId"
														class="btTxt submit" id="issueId" value="${issue.issueId}" />
													</a>
												</form> <c:out value="${issue.title}"></c:out> <c:forEach
													items="${issue.asignees}" var="asignee">
													<c:if test="${asignee==userId}">
														<a href="#" data-toggle="tooltip" title="You made it!">
															<input type="image" src="done.png" width=20px;
															height=20px; align="right" ; name="issueId"
															class="btTxt submit" id="saveForm" />
														</a>
													</c:if>
												</c:forEach></td>
										</tr>
									</c:if>
								</c:forEach>

							</tbody>
						</table>


					</div>
					<!-- /.table-responsive -->
				</c:if>

			</div>
			<!-- /.panel-body -->
		</div>
		<!-- /.panel -->
	</div>
	<div style="margin-left: 350px">
		<c:if test="${not empty message}">
			<h2>
				<c:out value="${message}"></c:out>
			</h2>
		</c:if>
	</div>
	<script>
		$(document).ready(function() {
			$('[data-toggle="tooltip"]').tooltip();
		});
	</script>
</body>
</html>