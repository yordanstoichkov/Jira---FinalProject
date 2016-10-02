<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
<link href='http://fonts.googleapis.com/css?family=Droid+Sans'
	rel='stylesheet' type='text/css'>
<meta charset="utf-8">
<title>JiraSoftware</title>
<link href="style.css" media="screen" rel="stylesheet" type="text/css" />
<link href="iconic.css" media="screen" rel="stylesheet" type="text/css" />
<script src="prefix-free.js"></script>
</head>

<body>
	<div class="wrap">

		<nav>
			<ul class="menu">
				<li><a href="./"><span class="iconic home"></span> LOGO</a></li>
				<li><a href="./"><span class="iconic home"></span> Home</a></li>
				<li><a href="./projects"><span class="iconic document"></span>
						Your page</a></li>
				<li><a href="#"><span class="iconic mail"></span> Contact</a></li>
				<%
					if (request.getSession(false) == null) {
				%>
				<li style="float: right"><a class="active" href="./reg">Register</a></li>
				<%
					} else {
				%>
				<li style="float: right"><a class="active" href="./logout">LogOut</a></li>
				<li style="float: right"><a class="active" href="#">Hi,<%=request.getSession().getAttribute("username")%></a></li>
				<%
					}
				%>

			</ul>
			<div class="clearfix"></div>
		</nav>
	</div>