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
		var menu = document.querySelector('.menu')
		var menuPosition = menu.getBoundingClientRect().top;
		window.addEventListener('scroll', function() {
			if (window.pageYOffset >= menuPosition) {
				menu.style.position = 'fixed';
				menu.style.top = '0px';
			} else {
				menu.style.position = 'static';
				menu.style.top = '';
			}
		});
	</script>
	<div class="wrap">

		<nav>
			<ul class="menu">
				<li><a href="./"><i class="fa fa-home"
						style="font-size: 2.0em;"></i>LOGO</a></li>
				<li><a href="./"><i class="fa fa-home"
						style="font-size: 2.0em;"></i> Home</a></li>
				<li><a href="./projects"><i class="fa fa-paste"
						style="font-size: 2.0em;"></i>Projects</a></li>
				<li><a href="./error"><i class="fa fa-phone"
						style="font-size: 2.0em;"></i> Contact</a></li>


				<%
					if (request.getSession(false) == null) {
				%>
				<li style="float: right"><a class="active" href="./reg">Register</a></li>
				<%
					} else {
				%>
				<li><a href="#"><i class="fa fa-pencil"
						style="font-size: 2.0em;"></i>Your issues</a></li>
				<li style="float: right"><a class="active" href="./logout">LogOut</a></li>
				<li style="float: right"><a class="active" href="#">Hi, <%=request.getSession().getAttribute("username")%>
				</a></li>
				<%
					}
				%>
			</ul>
			<div class="clearfix"></div>
		</nav>
	</div>