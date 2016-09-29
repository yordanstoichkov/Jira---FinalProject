<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<link href='http://fonts.googleapis.com/css?family=Droid+Sans'
	rel='stylesheet' type='text/css'>
<%@page session="false"%>
<meta charset="utf-8">
<title>Pure CSS3 Menu</title>
<link href="menuCSS/style.css" media="screen" rel="stylesheet"
	type="text/css" />
<link href="menuCSS/iconic.css" media="screen" rel="stylesheet"
	type="text/css" />
<script src="prefix-free.js"></script>
</head>

<body>
	<div class="wrap">

		<nav>
			<ul class="menu">
				<li><a href="#"><span class="iconic home"></span> LOGO</a></li>
				<li><a href="#"><span class="iconic home"></span> Home</a></li>
				<li><a href="#"><span class="iconic document"></span> Your
						page</a></li>
				<li><a href="#"><span class="iconic mail"></span> Contact</a></li>



					<li style="float: right"><a class="active" href="#">Register</a></li>

			</ul>
			<div class="clearfix"></div>
		</nav>
	</div>