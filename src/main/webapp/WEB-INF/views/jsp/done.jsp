<%@include file="navigation.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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
<script type="text/javascript" src="jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="plan.js"></script>
<link rel="stylesheet" href="addtypes.css">
<link href="donee.css" rel="stylesheet">
<link href="iconic.css" rel="stylesheet">
<link href="bootstrap.min.css" rel="stylesheet">

</head>
<body>

	<c:forEach items="${project.sprints}" var="sprint">
		<c:if test="${sprint.status == 'DONE'}">
			<div class="sprintTable">
				<table style="border: 2px solid #008040">
					<thead>
						<tr style="background: #72b674; color: #fff;">
							<th colspan="6" style="background: #72b674; color: #fff;"><h2
									style="font-size: 90%">Sprint: ${sprint.title}</h2></th>
						</tr>
						<tr>
							<th colspan="2"><spring:message code="issues" /></th>
							<th><spring:message code="issues.priority" /></th>
							<th><spring:message code="issues.asignees" /></th>
							<th></th>

						</tr>
					</thead>
					<tbody>
						<c:forEach items="${sprint.issues}" var="issue">
							<tr>
								<td><c:if test="${issue.type == 'TASK'}">
										<img src="task.png" height="20" width="20">
									</c:if> <c:if test="${issue.type == 'BUG'}">
										<img src="bug.png" height="20" width="20">
									</c:if> ${issue.title}</td>
								<td></td>
								<c:if test="${issue.priority == 'MEDIUM'}">
									<td><img src="medium.png" title="Medium" height="20"
										width="20"></td>
								</c:if>
								<c:if test="${issue.priority == 'HIGH'}">
									<td><img src="high.png" title="High" height="20"
										width="20"></td>
								</c:if>
								<c:if test="${issue.priority == 'LOW'}">
									<td><img src="low.png" title="Low" height="20" width="20"></td>
								</c:if>
								<td><c:forEach items="${issue.employees}" var="employee">
										<a href="./friend?id=${employee.employeeID}"><img
											title="${employee.firstName}" height="20" width="20"
											src="${employee.avatarPath}"></a>
									</c:forEach></td>
								<td><form action="./issue" method="put"
										style="display: inline">

										<input type="image" src="info.png" width=20px height=20px
											class="fa fa-pencil" name="issueId" value="${issue.issueId}"
											title="Open issue details" />
									</form></td>
							</tr>
						</c:forEach>

					</tbody>
				</table>
			</div>


		</c:if>

	</c:forEach>


	<script>
		var modal1 = document.getElementById('sprintModal');
		var btn1 = document.getElementById("sprintBtn");
		var span1 = document.getElementsByClassName("close1")[0];
		btn1.onclick = function() {
			modal1.style.display = "block";
		}
		span1.onclick = function() {
			modal1.style.display = "none";
		}
		window.onclick = function(event) {
			if (event.target == modal1) {
				modal1.style.display = "none";
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