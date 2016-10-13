<%@include file="navigation.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false"%>
<script type="text/javascript" src="jquery-3.1.1.min.js"></script>
<link rel="stylesheet" href="sprActivate.css">
</head>
<body>
	<script>
		function compareDates() {
			var startDate = new Date($('#from').val());
			var endDate = new Date($('#to').val());
			if (startDate > endDate) {
				alert('End date is before start date');
				return false;
			}
		}

		var today = new Date();
		var dd = today.getDate();
		var mm = today.getMonth() + 1; //January is 0!
		var yyyy = today.getFullYear();
		if (dd < 10) {
			dd = '0' + dd
		}
		if (mm < 10) {
			mm = '0' + mm
		}

		today = yyyy + '-' + mm + '-' + dd;
		document.getElementById("datefield").setAttribute("min", today);
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
		<form name="form" id="form" action="./beginsprint" method="post"
			onsubmit="return compareDates()">
			<fieldset class="contact-inner">
				<p class="contact-input">
					<label>Sprint start date:</label> <input type="date"
						name="startDate" id="from" min='2000-13-13'></input>
				</p>
				<p class="contact-input">
					<label>Sprint end date:</label> <input type="date" name="endDate"
						id="to"></input>
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