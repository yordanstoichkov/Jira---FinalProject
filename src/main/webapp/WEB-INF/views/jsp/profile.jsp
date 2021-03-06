<%@page errorPage="error.jsp"%>
<%@include file="menu.jsp"%>
<%@page session="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<link href="bootstrap.min.css" rel="stylesheet">
<link href="sb-admin-2.css" rel="stylesheet">
<link href="iconic.css" rel="stylesheet">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css">
<script type="text/javascript" src="jquery-3.1.1.min.js"></script>

</head>
<body>
	<script>
		function checkFile() {
			//check whether browser fully supports all File API
			if (window.File && window.FileReader && window.FileList
					&& window.Blob) {
				//get the file size and file type from file input field
				if ($('#file')[0].files[0] == null) {
					return false;
				}
				var fsize = $('#file')[0].files[0].size;
				if (fsize > 5242880) //do something if file size more than 1 mb (1048576)
				{
					text = "Too big file";
					$("#demo").append(text);
					return false;
				}
			} else {
				alert("Please upgrade your browser, because your current browser lacks some new features we need!");
			}
		}
	</script>
	<p style="float: left">
	<div class="row" style="padding-top: 70px; padding-left: 50px">
		<div class="zaglavie">
			<h1 class="page-header" style="color: #1b5c9e;">
				<c:out value="${user.firstName}"></c:out>
				<c:out value="  "></c:out>
				<c:out value="${user.lastName}"></c:out>
			</h1>
		</div>
	</div>
	<p style="float: left;">
		<img src="${user.avatarPath}" width="300" height="300"
			style="padding-left: 70px;"></img>
	</p>
	<p>
	<div class="zaglavie" style="margin-left: 50px; padding-left: 100px;">
		<h4 class="page-header" style="color: #1b5c9e;">
			&nbsp;&nbsp;&nbsp;Email:
			<c:out value="${user.email}"></c:out>

		</h4>
	</div>

	<div class="zaglavie">
		<h3 class="page-header" style="color: #1b5c9e; margin-left: 100px;">

			<c:if test="${ user.job=='MANAGER'}">

				<span style="color: #1b5c9e; display: inline; font-size: 80%">&nbsp;&nbsp;&nbsp;<spring:message
						code="job" />:
				</span>
				<img src="manager.png" width=30px; height=30px;
					style="color: #1b5c9e; display: inline" />

				<span style="font-size: 70%"> <spring:message code="manager" /></span>
			</c:if>

			<c:if test="${  user.job=='DEVELOPER'}">
				<span style="color: #1b5c9e; display: inline; font-size: 80%">&nbsp;&nbsp;&nbsp;<spring:message
						code="job" />:
				</span>

				<img src="developer.png" width=35px; height=35px;
					style="color: #1b5c9e; display: inline" />
				<span style="font-size: 70%"><spring:message code="developer" /></span>

			</c:if>
			<c:if test="${ user.job=='REVIEWER'}">
				<span style="color: #1b5c9e; display: inline; font-size: 80%">&nbsp;&nbsp;&nbsp;<spring:message
						code="job" />:
				</span>

				<img src="reviewer.png" width=35px; height=35px;
					style="color: #1b5c9e; display: inline" />
				<span style="font-size: 70%"> <spring:message code="reviewer" /></span>

			</c:if>
		</h3>
	</div>
	</p>

	<form method="POST" enctype="multipart/form-data"
		onsubmit="return checkFile()">

		<div class="form-group"
			style="margin-left: 70px; margin-top: 20px; width: 200px; color: #1b5c9e;">
			<input type="file" id="file" name="file" accept="image/*" />
		</div>

		<div class="form-group"
			style="width: 200px; margin-left: 70px; color: #1b5c9e;">

			<input type="submit" value="<spring:message code="update" />" />

		</div>
		<span id="demo"
			style="padding-left: 25px; color: #ff3333; font-size: 140%"></span>
	</form>

	<div class="logo">
		<img src="logo.png" width=610px; height=460px;
			style="color: #1b5c9e; display: inline" />
	</div>



</body>
</html>