<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<script type="text/javascript" src="jquery-3.1.1.min.js"></script>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
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
	<div class="header"></div>
	<input id="employee" list="suggestions" onkeyup="getEmployeeName()"
		onblur="typefunction()">
	<datalist id="suggestions">
	<option>Red</option>
	</datalist>
	<div id="names"></div>

	<form method="POST" enctype="multipart/form-data" action="./home">
		<table>
			<tr>
				<td>File to upload:</td>
				<td><input type="file" id="file" name="file" accept="image/*" /></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" value="Upload" /></td>
			</tr>
		</table>
	</form>

	<img src="${picture}">
</body>
</html>