<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page session="false"%>
<%@page errorPage="error.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<link href="<c:url value = "font.css"/>" rel="stylesheet"
	type="text/css">
<meta charset="utf-8">
<title>JiraSoftware</title>
<link href="style.css" media="screen" rel="stylesheet" type="text/css" />
<link href="<c:url value="iconic.css"/>" media="screen" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css">

</head>

<body>
	<script>
		$(document).ready(function() {
			$("#sticker").sticky({
				topSpacing : 0
			});
		});
	</script>
	<div clas="sticky" id="sticker">
		<nav>

			<ul class="menu">
				<li><img src="logo.png" width=90px; height=57px;></li>
				<li><a href="<spring:message code="lang"/>"> <img
						height="30" width="30" src="<spring:message code="pic"/>"></a></li>
				<li><a href="./home"><i class="fa fa-home"
						style="font-size: 2.0em;"></i> <spring:message code="menu.home" /></a></li>
				<li><a href="./projects"><i class="fa fa-paste"
						style="font-size: 2.0em;"></i> <spring:message
							code="menu.projects" /></a></li>
				<li><a href="./contact"><i class="fa fa-phone"
						style="font-size: 2.0em;"></i> <spring:message code="menu.contact" /></a></li>




				<li><a href="./myIssues"><i class="fa fa-pencil"
						style="font-size: 2.0em;"></i> <spring:message
							code="menu.yourIssues" /></a></li>
				<li style="float: right; padding-right: 20px; padding-top: 5px"><div
						class="dropdown">
						<a onclick="myFunction()" class="dropbtn"><img
							src="${user.avatarPath}" width="25" height="30" /> <spring:message
								code="menu.greeting" />, <%=request.getSession().getAttribute("username")%></a>
						<div id="myDropdown" class="dropdown-content">
							<a href="./profile""><spring:message code="profile" /></a> <a
								class="active" href="./logout"><spring:message code="logout" /></a>
						</div>
					</div></li>

			</ul>
			<script>
				/* When the user clicks on the button,
				 toggle between hiding and showing the dropdown content */
				function myFunction() {
					document.getElementById("myDropdown").classList
							.toggle("show");
				}

				// Close the dropdown if the user clicks outside of it
				window.onclick = function(event) {
					if (!event.target.matches('.dropbtn')) {

						var dropdowns = document
								.getElementsByClassName("dropdown-content");
						var i;
						for (i = 0; i < dropdowns.length; i++) {
							var openDropdown = dropdowns[i];
							if (openDropdown.classList.contains('show')) {
								openDropdown.classList.remove('show');
							}
						}
					}
				}
			</script>



			<div class="clearfix"></div>
		</nav>
	</div>