<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
				<li><a href="./home"><i class="fa fa-home"
						style="font-size: 2.0em;"></i> Home</a></li>
				<li><a href="./projects"><i class="fa fa-paste"
						style="font-size: 2.0em;"></i>Projects</a></li>
				<li><a href="./contact"><i class="fa fa-phone"
						style="font-size: 2.0em;"></i> Contact</a></li>


				<%
					if (request.getSession(false) == null) {
				%>
				<li style="float: right"><a class="active" href="./reg">Register</a></li>
				<%
					} else {
				%>
				<li><a href="./myIssues"><i class="fa fa-pencil"
						style="font-size: 2.0em;"></i>Your issues</a></li>
				<li style="float: right; padding-right:20px;padding-top:5px"><div class="dropdown">
						<a onclick="myFunction()" class="dropbtn"><img
							src="${user.avatarPath}" width="25" height="30" /> Hi, <%=request.getSession().getAttribute("username")%></a>
						<div id="myDropdown" class="dropdown-content">
							<a href="./profile" ">Profile</a>  
							<a class="active" href="./logout">LogOut</a>
						</div>
					</div></li>


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

				<%
					}
				%>
			</ul>
			<div class="clearfix"></div>
		</nav>
	</div>