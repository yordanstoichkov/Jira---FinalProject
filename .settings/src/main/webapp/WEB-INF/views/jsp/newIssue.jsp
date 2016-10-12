<%@include file="navigation.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false"%>
<head>
<link href="iconic.css" rel="stylesheet">
<link href="sprint.css" rel="stylesheet">
<link href="metisMenu.min.css" rel="stylesheet">
<link href="bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css">
<script type="text/javascript" src="jquery-3.1.1.min.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<meta charset="utf-8">

<script type="text/javascript" src="jquery-3.1.1.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body>
	<script>
		function getEmployeeName() {
			var name = $("#employee").val();
			$.get("http://localhost:8080/JiraProject/givenames?start=" + name,
					function(data) {
						$("#suggestions").empty();
						for ( var index in data) {

							var item = data[index];
							var p = document.createElement("option");
							p.innerHTML = item;
							$("#suggestions").append(p);
						}
					});

		}
		function typefunction() {
			var name = $("#employee").val();
			var p = document.createElement("p");
			p.innerHTML = name;
			$("#names").append(p);
			$("#employee").empty()
		}
	</script>
	<div class="row">
		<div class="zaglavie">
			<h1 class="page-header" style="color: #1b5c9e;">
				<c:out value="${sprint.title}"></c:out>
			</h1>
		</div>
	</div>
	<form:form commandName="emptyIssue">
		<div class="form-group"
			style="width: 400px; margin-left: 280px; color: #1b5c9e;">
			<label>Enter issue title: </label>
			<form:input path="title" class="form-control" style="color: #1b5c9e;"
				placeholder="Enter title..." />
		</div>
		<div class="form-group"
			style="width: 200px; margin-left: 280px; color: #1b5c9e; display: inline-block;">
			<label>Issue type: </label>
			<form:select path="type" class="form-control">
				<form:option value="TASK">Task</form:option>
				<form:option value="BUG">Bug</form:option>
			</form:select>
		</div>
		<div class="form-group"
			style="width: 200px; margin-left: 50px; color: #1b5c9e; display: inline-block;">

			<label>Issue prioity: </label>
			<form:select path="priority" class="form-control">
				<form:option value="LOW">Low</form:option>
				<form:option value="MIDDLE">Middle</form:option>
				<form:option value="HIGH">High</form:option>
			</form:select>
		</div>

		<div class="form-group"
			style="width: 500px; margin-left: 280px; color: #1b5c9e;">
			<label>Description:</label>
			<form:textarea path="description" class="form-control" rows="4"
				placeholder="Enter description..."></form:textarea>
		</div>
		<div class="form-group input-group"
			style="width: 500px; margin-left: 280px; color: #1b5c9e; padding-top: 20px;">

			<label>Assign employees:</label>
		</div>
		<div class="form-group input-group"
			style="width: 500px; margin-left: 280px; color: #1b5c9e;">

			<input name="assignee" id="employee" type="text" class="form-control"
				list="suggestions" onkeyup="getEmployeeName()" /> <span
				class="input-group-btn"> <datalist id="suggestions"
					onclick="typefunction()">

				</datalist>

			</span>
		</div>
		<button type="submit" class="btn btn-primary btn-lg"
			style="width: 500px; margin-left: 280px; margin-bottom: 50px; margin-top: 20px;"
			name="sprintId" value="${sprint.sprintId}">
			<i class="fa fa-plus"
				style="font-size: 1.em; padding-right: 15px; padding-top: 5px"></i>Create
			issue
		</button>
	</form:form>

	<script type="text/javascript">
		function Validatebodypanelbumper(theForm) {
			var regexp;
			var extension = new FormData(theForm).get("file").value
					.lastIndexOf('.');
			if ((extension.toLowerCase() != ".gif")
					&& (extension.toLowerCase() != ".jpg") && (extension != "")) {
				alert("The \"FileUpload\" field contains an unapproved filename.");
				theForm.file.focus();
				return false;
			}
			return true;
		}
	</script>

</body>
</html>