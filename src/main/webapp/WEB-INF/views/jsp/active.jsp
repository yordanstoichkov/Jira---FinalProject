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
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, shrink-to-fit=no, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Insert title here</title>
</head>
<body>

	<c:forEach items="${project.sprints}" var="sprint">
		<c:if test="${sprint.status == 'IN_PROGRESS'}">

			<div class="row">
				<div class="col-lg-6">
					<div class="panel panel-default">

						<!-- /.panel-heading -->
						<div class="table-responsive">
							<table class="table table-striped table-bordered table-hover">
								<thead>
									<tr>
										<th colspan="3"><b><c:out value="${sprint.title}"></c:out></b></th>

									</tr>
									<tr>
										<th><div class="col-xs-3">
												<i class="fa fa-times" style="font-size: 1.0em; color: red"></i>
											</div> <b>To Do</b></th>
										<th>
											<div class="col-xs-3">
												<i class="fa fa-cog" style="font-size: 1.0em; color: orange"></i>
											</div>In Progress
										</th>
										<th><div class="col-xs-3">
												<i class="fa fa-check"
													style="font-size: 1.0em; color: green"> </i>
											</div>Done</th>
									</tr>
								</thead>
								<tbody>

									<c:forEach items="${sprint.issues}" var="issue">
										<c:if test="${issue.status=='TO_DO'}">
											<tr>
												<td class="danger"><form action="./info">
														<input type="image" src="info.png" width=17px;
															height=17px; align="left" ; name="issueId"
															class="btTxt submit" id="issueId"
															value="${issue.issueId}" />
													</form>

													<form action="./activy">
														<c:out value="${issue.title}"></c:out>
														<c:forEach items="${issue.asignees}" var="asignee">
															<c:if test="${asignee==userId}">
																<input type="image" src="red_arrow.png" width=17px;
																	height=17px; align="right" ; name="issueId"
																	class="btTxt submit" id="issueId"
																	value="${issue.issueId}" />
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
												<td class="warning"><form action="./info">
														<input type="image" src="info.png" width=17px;
															height=17px; align="left" ; name="issueId"
															class="btTxt submit" id="issueId"
															value="${issue.issueId}" />
													</form>

													<form action="./activy">
														<c:out value="${issue.title}"></c:out>
														<c:forEach items="${issue.asignees}" var="asignee">
															<c:if test="${asignee==userId}">
																<input type="image" src="orange_arrow.png" width=20px;
																	height=20px; align="right" ; name="issueId"
																	class="btTxt submit" id="saveForm"
																	value="${issue.issueId}" />
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
												<td class="success"><form action="./info">
														<input type="image" src="info.png" width=17px;
															height=17px; align="left" ; name="issueId"
															class="btTxt submit" id="issueId"
															value="${issue.issueId}" />
													</form>
														<c:out value="${issue.title}"></c:out>
														<c:forEach items="${issue.asignees}" var="asignee">
															<c:if test="${asignee==userId}">
																<input type="image" src="done.png" width=20px;
																	height=20px; align="right" ; name="issueId"
																	class="btTxt submit" id="saveForm" />
															</c:if>
														</c:forEach>
													</td>
											</tr>
										</c:if>
									</c:forEach>

								</tbody>
							</table>
						</div>
						<!-- /.table-responsive -->
					</div>
					<!-- /.panel-body -->
				</div>
				<!-- /.panel -->
			</div>
			<!-- /.col-lg-6 -->

		</c:if>
	</c:forEach>

</body>
</html>