<%@include file="navigation.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false"%>
<script type="text/javascript" src="jquery-3.1.1.min.js"></script>

<link rel="stylesheet" href="sprActivate.css">
</head>
<body>
	<script>
		function customRange(input) {
			if (input.id == 'to') {
				var x = $('#from').datepicker("getDate");
				$(".to").datepicker("option", "minDate", x);
			} else if (input.id == 'from') {
				var x = $('#to').datepicker("getDate");
				$(".from").datepicker("option", "maxDate", x);
			}
		}
	</script>
	<h1 class="sprinth1"
		style="font-size: 150%; padding-left: 300px; padding-top: 35px;">Sprint
		name :</h1>
	<h1 class="sprinth1"
		style="font-size: 150%; color: blue; padding-left: 300px; padding-top: 45px;">${sprint.title}</h1>
	<div class="col-lg-12" style="padding-left: 250px; padding-top: 30px">
		<hr style="width: 90%;">
	</div>
	<div class="sprfrm">
		<form name="form" action="./beginsprint" method="post">
			<fieldset class="contact-inner">

				<p class="contact-input">
					<label>Sprint start date:</label> <input type="date"
						name="startDate" id="from" onclick="customRange('from')"></input>
				</p>
				<p class="contact-input">
					<label>Sprint end date:</label> <input type="date" name="to"
						id="date_depart" onclick="customRange('to')"></input>
				</p>

				<br /> <label style="padding-bottom: 15px">Sprint goals:</label>
				<p class="contact-input">

					<input type="textarea" name="sprintGoal" />
				</p>
				<p id="demo"></p>
				<p class="contact-submit">
					<button type="submit" name="sprintId" value="${sprint.sprintId}">Start
						sprint</button>
				</p>
			</fieldset>
		</form>
	</div>
</body>
</html>