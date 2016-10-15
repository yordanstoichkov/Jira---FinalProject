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
			$("#demo").empty();
			var y = trim(document.forms["form"]["sprintGoal"].value);
			var startDate = new Date($('#from').val());
			var endDate = new Date($('#to').val());
			if (startDate > endDate || endDate == null || endDate == ""
					|| startDate == null || startDate == "") {
				text = "Start date must be before end date";
				$("#demo").append(text);
				return false;
			}
			if (y == null || y == "") {
				text = "All inputs must be filled out";
				$("#demo").append(text);
				return false;
			}
		}
		function trim(value) {
			return value.replace(/^\s+|\s+$/g, "");
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
		style="font-size: 150%; padding-left: 300px; padding-top: 35px;"><spring:message code="sprint.name" /> :</h1>
	<h1 class="sprinth1"
		style="font-size: 150%; color: blue; padding-left: 300px; padding-top: 45px;">${sprint.title}</h1>
	<div class="col-lg-12" style="padding-left: 250px; padding-top: 30px">
		<hr style="width: 90%;">
		<span id="demo"
			style="color: #ff3333; font-size: 140%; padding-left: 365px">
			<c:if test="${not empty message }">
				<p>
					<span style="color: #ff3333; font-size: 100%; padding-left: 350px">
						<c:out value="${message}"></c:out>
					</span>
				</p>
			</c:if>
		</span>
	</div>
	<div class="sprfrm">
		<form name="form" id="form" action="./beginsprint" method="post"
			onsubmit="return compareDates()">
			<fieldset class="contact-inner">
				<p class="contact-input">
					<label><spring:message code="startdate" />:</label> <input type="date"
						name="startDate" id="from" min='2000-13-13'></input>
				</p>
				<p class="contact-input">
					<label><spring:message code="enddate" />:</label> <input type="date" name="endDate"
						id="to"></input>
				</p>

				<br /> <label style="padding-bottom: 15px"><spring:message code="goal" />:</label>
				<p class="contact-input">

					<input type="textarea" name="sprintGoal" />
				</p>

				<p class="contact-submit">
					<button type="submit" name="sprintId" value="${sprint.sprintId}"><spring:message code="start.sprint" /></button>
				</p>
			</fieldset>
		</form>
	</div>
</body>
</html>