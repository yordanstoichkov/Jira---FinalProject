<%@include file="navigation.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	<div class="row">
		<div class="zaglavie">
			<h1 class="page-header" style="color: #1b5c9e;">
				<c:out value="${sprint.title}"></c:out>
			</h1>
		</div>
	</div>
	<div class="form-group"
		style="width: 400px; margin-left: 280px; color: #1b5c9e;">
		<label>Enter issue title: </label> <input class="form-control"
			style="color: #1b5c9e;" placeholder="Enter title...">
	</div>
	<div class="form-group"
		style="width: 200px; margin-left: 280px; color: #1b5c9e; display: inline-block;">
		<label>Issue type: </label> <select class="form-control">
			<option>Task</option>
			<option>Bug</option>
		</select>
	</div>
	<div class="form-group"
		style="width: 200px; margin-left: 50px; color: #1b5c9e; display: inline-block;">

		<label>Issue prioity: </label> <select class="form-control">
			<option>Low</option>
			<option>Middle</option>
			<option>High</option>
		</select>
	</div>

	<form method="POST" enctype="multipart/form-data"
		style="margin-left: 280px;" onsubmit="Validatebodypanelbumper()">

		<div class="form-group"
			style="width: 200px;color: #1b5c9e; padding-top: 20px;">
			<label>Choose file</label><input type="file" id="file" name="file"
				accept="image/*" />
		</div>

		<div class="form-group"
			style="width: 200px; color: #1b5c9e;">

			<input type="submit" value="Upload file" />

		</div>
	</form>

	<div class="form-group"
		style="width: 500px; margin-left: 280px; color: #1b5c9e;">
		<label>Description:</label>
		<textarea class="form-control" rows="4"
			placeholder="Enter description..."></textarea>
	</div>
	<div class="form-group input-group"
		style="width: 500px; margin-left: 280px; color: #1b5c9e; padding-top: 20px;">

		<label>Assign employees:</label>
	</div>
	<div class="form-group input-group"
		style="width: 500px; margin-left: 280px; color: #1b5c9e;">

		<input type="text" class="form-control"> <span
			class="input-group-btn">
			<button class="btn btn-default" type="button">
				<i class="fa fa-search"></i>
			</button>
		</span>
	</div>

	<button type="button" class="btn btn-primary btn-lg"
		style="width: 500px; margin-left: 280px; margin-bottom: 50px; margin-top: 20px;">
		<i class="fa fa-plus"
			style="font-size: 1.em; padding-right: 15px; padding-top: 5px"></i>Create
		issue
	</button>

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