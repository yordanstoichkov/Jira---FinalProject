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
		var i = 0;
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
		function getEmployeeName1() {
			var name = $("#employee" + i).val();
			$.get("http://localhost:8080/JiraProject/givenames?start=" + name,
					function(data) {
						$("#suggestions" + i).empty();
						for ( var index in data) {

							var item = data[index];
							var p = document.createElement("option");
							p.innerHTML = item;
							$("#suggestions" + i).append(p);
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
		function setanotherfield() {
			if (i < 4) {
				i++;
				$('<br/>').appendTo('#myform')
				$('<input/>').attr({
					type : 'text',
					name : 'asignee' + i,
					id : 'employee' + i,
					class : 'form-control',
					list : 'suggestions' + i
				}).appendTo('#myform' + i).keyup(getEmployeeName1());
			}

		}
		function validateForm() {
			$("#demo").empty();
			var text;
			var x = trim(document.forms["form"]["title"].value);
			var y = trim(document.forms["form"]["description"].value);
			var z = trim(document.forms["form"]["assignee"].value);
			if (x == null || x == "" || z == null || z == "" || y == null
					|| y == "") {
				text = "All inputs must be filled out";
				$("#demo").append(text);
				return false;
			}

		}
		function trim(value) {
			return value.replace(/^\s+|\s+$/g, "");
		}
	</script>
	<div class="row">
		<div class="zaglavie">
			<h1 class="page-header" style="color: #1b5c9e;">
				<c:out value="${sprint.title}"></c:out>
			</h1>
		</div>
		<c:if test="${not empty message }">
			<p>
				<span style="color: #ff3333; font-size: 140%"><c:out
						value="${message}"></c:out></span>
			</p>
		</c:if>
		<span id="demo" style="color: #ff3333; font-size: 140%"></span>
	</div>
	<form:form name="form" commandName="emptyIssue"
		onsubmit="return validateForm()">

		<div class="form-group"
			style="width: 400px; margin-left: 280px; color: #1b5c9e;">
			<label><spring:message code="issue.title" />: </label>
			<form:input name="title" path="title" class="form-control"
				style="color: #1b5c9e;" placeholder="Enter title..." />
			<form:errors path="title" cssClass="error" />
		</div>
		<div class="form-group"
			style="width: 200px; margin-left: 280px; color: #1b5c9e; display: inline-block;">
			<label><spring:message code="issue.type" />: </label>
			<form:select path="type" class="form-control">
				<form:option value="TASK"><spring:message code="task" /></form:option>
				<form:option value="BUG"><spring:message code="bug" /></form:option>
			</form:select>
		</div>
		<div class="form-group"
			style="width: 200px; margin-left: 50px; color: #1b5c9e; display: inline-block;">

			<label><spring:message code="issue.priority" />: </label>
			<form:select path="priority" class="form-control">
				<form:option value="LOW"><spring:message code="low" /></form:option>
				<form:option value="MIDDLE"><spring:message code="medium" /></form:option>
				<form:option value="HIGH"><spring:message code="high" /></form:option>
			</form:select>
		</div>

		<div class="form-group"
			style="width: 500px; margin-left: 280px; color: #1b5c9e;">
			<label><spring:message code="description" />:</label>
			<form:textarea name="description" path="description"
				class="form-control" rows="4" placeholder="Enter description..."></form:textarea>
		</div>
		<div class="form-group input-group"
			style="width: 500px; margin-left: 280px; color: #1b5c9e; padding-top: 20px;">

			<label><spring:message code="issue.asign" />:</label>
		</div>
		<div id="myform" class="form-group input-group"
			style="width: 500px; margin-left: 280px; color: #1b5c9e;">

			<input name="assignee" id="employee" type="text" class="form-control"
				list="suggestions" onkeyup="getEmployeeName()" /> <span
				class="input-group-btn"> <img src="plus.png" width="20"
				height="20" onclick="setanotherfield()" /> <datalist
					id="suggestions">

				</datalist>
			</span>
		</div>
		<div id="myform1" class="form-group input-group"
			style="width: 500px; margin-left: 280px; color: #1b5c9e;">

			<span class="input-group-btn"> <datalist id="suggestions1"
					onclick="typefunction()">

				</datalist>

			</span>
		</div>
		<div id="myform2" class="form-group input-group"
			style="width: 500px; margin-left: 280px; color: #1b5c9e;">

			<span class="input-group-btn"> <datalist id="suggestions2"
					onclick="typefunction()">

				</datalist>

			</span>
		</div>
		<div id="myform3" class="form-group input-group"
			style="width: 500px; margin-left: 280px; color: #1b5c9e;">

			<span class="input-group-btn"> <datalist id="suggestions3"
					onclick="typefunction()">

				</datalist>

			</span>
		</div>
		<div id="myform4" class="form-group input-group"
			style="width: 500px; margin-left: 280px; color: #1b5c9e;">

			<span class="input-group-btn"> <datalist id="suggestions4"
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