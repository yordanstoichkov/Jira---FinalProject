<%@include file="navigation.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false"%>
<head>
<script type="text/javascript" src="jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="plan.js"></script>
<link rel="stylesheet" href="addtypes.css">
<link href="plan.css" rel="stylesheet">
<link href="iconic.css" rel="stylesheet">
</head>
<body>
	<script>
		$(document).ready(function() {
			$('[data-toggle="tooltip"]').tooltip();
		});
	</script>


	<c:if test="${user.job=='MANAGER'}">
		<div class="createSP">
			<button type="button" class="sprintButton" id="sprintBtn">Create
				Sprint</button>
		</div>
	</c:if>
	<div class="col-lg-12" style="padding-left: 300px">
		<hr style="width: 90%;">
	</div>
	<c:forEach items="${project.sprints}" var="sprint">
		<c:if test="${sprint.status == 'TO_DO'}">
			<div class="sprintTable">
				<table>
					<thead>
						<tr>
							<th colspan="4"><h1 style="font-size: 130%">Sprint:
									${sprint.title}</h1></th>
							<c:if test="${not empty activeSprint}">
								<th><span style="padding-right: 8px; padding-top: 3px;">

										<button style="float: right;" data-toggle="tooltip"
											title="There is active sprint" class="btn" disabled>Start
											sprint</button>
								</span></th>
							</c:if>
							<c:if test="${empty activeSprint}">
								<c:if test="${empty activeSprint.issues}">
									<th><span style="padding-right: 8px; padding-top: 3px;">

											<button style="float: right;" data-toggle="tooltip"
												title="There are not issues in this sprint" class="btn" disabled>Start
												sprint</button>
									</span></th>
								</c:if>
									<th><form action="./startsprint" method="put">
											<span style="padding-right: 8px; padding-top: 3px;">
												<button style="float: right" name="sprintId"
													value="${sprint.sprintId}" type="submit"
													class="btn btn-warning">Start sprint</button>
											</span>
										</form></th>
							</c:if>
						</tr>
						<tr>
							<th>#</th>
							<th colspan="4">Issues</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${sprint.issues}" var="issue">
							<tr>
								<td>${issue.issueId}</td>
								<td><c:if test="${issue.type == 'TASK'}">
										<img src="task.png" height="20" width="20">
									</c:if> <c:if test="${issue.type == 'BUG'}">
										<img src="bug.png" height="20" width="20">
									</c:if> ${issue.title}</td>
								<td></td>
								<c:if test="${issue.priority == 'MEDIUM'}">
									<td><img src="medium.png" height="20" width="20"></td>
								</c:if>
								<c:if test="${issue.priority == 'HIGH'}">
									<td><img src="high.png" height="20" width="20"></td>
								</c:if>
								<c:if test="${issue.priority == 'LOW'}">
									<td><img src="low.png" height="20" width="20"></td>
								</c:if>
								<td><form action="./issue" method="put">
										<button style="paddling-right: 3px; font-size: 20px;"
											class="fa fa-pencil" name="issueId" value="${issue.issueId}"></button>
									</form>
									<button style="font-size: 20px;" class="fa fa-trash-o"></button></td>
							</tr>
						</c:forEach>
						<tr>
							<td style="text-align: left" colspan="5"><form
									action="./newIssue" >
									<button type="submit" class="myButton" id="myBtn"
										name="sprintId" value="${sprint.sprintId}">
										<i class="fa fa-plus-circle"></i> Add issue
									</button>
								</form></td>
						</tr>
					</tbody>
				</table>
			</div>


		</c:if>

	</c:forEach>

	<!-- The Modal -->
	<div id="sprintModal" class="modal">

		<!-- Modal content -->
		<div class="sprintModal-content">
			<span class="close1">x</span>
			<h1
				style="padding-left: 200px; padding-bottom: 30px; padding-top: 15px; font-size: 150%">Create
				Sprint</h1>
			<form:form name="form" commandName="emptysprint"
				onsubmit="return validateForm()">
				<fieldset class="contact-inner">
					<p class="contact-input">
						<form:input type="text" path="title" placeholder="Title" />
					</p>
					<p id="demo"></p>
					<p class="contact-submit">
						<input type="submit" value="Create Sprint">
					</p>
				</fieldset>
			</form:form>
		</div>

	</div>

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