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
<title>SB Admin 2 - Bootstrap Admin Theme</title>
<link href="../vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="../vendor/metisMenu/metisMenu.min.css" rel="stylesheet">
<link href="../dist/css/sb-admin-2.css" rel="stylesheet">
<link href="../vendor/morrisjs/morris.css" rel="stylesheet">
<link href="../vendor/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">
</head>
<body onload="giveComments()">

	<div class="row">
		<div class="zaglavie">
			<h1 class="page-header" style="color: #1b5c9e;">
				<c:out value="${sprint.title}"></c:out>
			</h1>
		</div>
	</div>
	<div class="row">
		<div class="zaglavie1">

			<h3 class="page-header" style="color: #1b5c9e">
				<c:if test="${issue.type=='TASK'}">
					<img src="task.png" width=25px; height=25px;
						style="color: #1b5c9e; display: inline" />

				</c:if>
				<c:if test="${issue.type=='BUG'}">
					<img src="bug.png" width=25px; height=25px;
						style="color: #1b5c9e; display: inline" />

				</c:if>
				<c:out value="${issue.title}"></c:out>
				<div class="podravni">
					<c:if test="${ issue.status=='TO_DO'}">
						<span style="color: #1b5c9e; display: inline; font-size: 80%">Workflow:
						</span>
						<i class="fa fa-times"
							style="font-size: 1.0em; color: red; display: inline"></i>
						<span style="font-size: 70%"> To Do</span>
					</c:if>

					<c:if test="${ issue.status=='IN_PROGRESS'}">
						<span style="color: #1b5c9e; display: inline; font-size: 80%">Workflow:
						</span>

						<i class="fa fa-cog" style="font-size: 1.0em; color: orange"></i>
						<span style="font-size: 70%"> In Progress</span>

					</c:if>
					<c:if test="${ issue.status=='DONE'}">
						<span style="color: #1b5c9e; display: inline; font-size: 80%">Workflow:
						</span>

						<i class="fa fa-check" style="font-size: 1.0em; color: green">
							<span style="font-size: 70%"> Done</span>

						</i>
					</c:if>
				</div>

				<c:if test="${ issue.priority=='LOW'}">
					<span style="color: #1b5c9e; display: inline; font-size: 80%">
						&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;
						&nbsp; &nbsp;Priority: </span>
					<img src="low.png" width=25px; height=25px;
						style="color: #1b5c9e; display: inline" />
					<span style="font-size: 70%"> Low</span>
				</c:if>

				<c:if test="${ issue.priority=='MEDIUM'}">
					<span style="color: #1b5c9e; display: inline; font-size: 80%">
						&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;
						&nbsp; &nbsp;Priority: </span>

					<img src="medium.png" width=25px; height=25px;
						style="color: #1b5c9e; display: inline" />
					<span style="font-size: 70%"> Medium</span>

				</c:if>
				<c:if test="${ issue.priority=='HIGH'}">
					<span style="color: #1b5c9e; display: inline; font-size: 80%">
						&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;
						&nbsp; &nbsp;Priority: </span>

					<img src="high.png" width=25px; height=25px;
						style="color: #1b5c9e; display: inline" />
					<span style="font-size: 70%"> High</span>


				</c:if>
			</h3>

		</div>
	</div>

	<div class="row">
		<div class="description">
			<div class="col-lg-4">
				<div class="panel panel-info">
					<div class="panel-heading">
						<b>Description</b>
					</div>
					<div class="panel-body">
						<c:out value="${issue.description}"></c:out>

					</div>
					<div class="panel-footer"></div>
				</div>
			</div>
		</div>

	</div>
	<form method="POST" enctype="multipart/form-data" action="./upload"
		style="margin-left: 300px;" onsubmit="Validatebodypanelbumper()">

		<div class="form-group" style="width: 200px; color: #1b5c9e;">
			<label>Choose pdf file to upload</label><input type="file" id="file" name="file"
				accept="application/pdf" />
		</div>

		<div class="form-group" style="width: 200px; color: #1b5c9e;">

			<input type="submit" name="issueId" value="${issue.issueId}" />

		</div>
	</form>
	<div style="padding-left: 300px">
		<a href="${issue.filePath}">Get issue file</a>
	</div>
	<div class="row">
		<div class="col-lg-6">
			<div class="panel panel-defaultt">
				<div class="panel-heading">Team</div>
				<!-- /.panel-heading -->
				<div class="panel-body">
					<div class="alert alert-info">
						<img src="manager.png" width=30px; height=30px;
							style="color: #1b5c9e; display: inline" /><span>&nbsp;Manager:
							<c:forEach items="${namesOfManagers}" var="name">
								<c:out value="${name}"></c:out>,

							</c:forEach>
						</span>
					</div>
					<div class="alert alert-info">
						<img src="team.png" width=35px; height=35px;
							style="color: #1b5c9e; display: inline" /><span>&nbsp;Developers:
							<c:forEach items="${namesOfDevelopers}" var="name">
								<c:out value="${name}"></c:out>

							</c:forEach>

						</span>
					</div>
					<div class="alert alert-info">
						<img src="reviewer.png" width=35px; height=35px;
							style="color: #1b5c9e; display: inline" /><span>&nbsp;Reviewers:
							<c:forEach items="${namesOfReviewers}" var="name">
								<c:out value="${name}"></c:out>

							</c:forEach>
						</span>
					</div>
				</div>
				<!-- .panel-body -->
			</div>
			<!-- /.panel -->
		</div>
	</div>
	<div class="chats" style="width: 1300px; padding-left: 300px;">
		<div class="chat-panel panel panel-defaultt">
			<div class="panel-heading">
				<i class="fa fa-comments fa-fw"></i> Comments
				<div class="btn-group pull-right"></div>
			</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
				<ul class="chat">
					<c:forEach items="${commentsOfIssue}" var="comment">
						<li class="left clearfix">
							<div class="chat-body clearfix">
								<div class="header">
									<span class="chat-img pull-left"> <img src="manager.png"
										alt="User Avatar" class="img-circle"
										style="width: 30px; height: 30px" />
									</span><strong class="primary-font"><br> <c:out
											value="${comment.writer.firstName}"></c:out> <c:out
											value="${comment.writer.lastName}"></c:out></strong> <small
										class="pull-right text-muted"> <i
										class="fa fa-clock-o fa-fw"></i> <c:out
											value="${comment.date}" />
									</small>
								</div>

								<br>
								<c:out value="${comment.comment}" />
							</div>
						</li>
						<hr>
					</c:forEach>

				</ul>
			</div>
			<!-- /.panel-body -->
			<div class="panel-footer">
				<div class="input-group">
					<form:form commandName="emptycomment">
						<span class="input-group-btn"> <form:input path="comment"
								id="btn-input" type="text" class="form-control input-sm"
								placeholder="Type your message here..." style="width: 800px;" />
							<button type="submit" class="btn btn-warning btn-sm"
								name="issueId" value="${issue.issueId}" id="btn-chat">Send</button>
						</span>
					</form:form>
				</div>
			</div>
			<!-- /.panel-footer -->
		</div>
	</div>
	<script>
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