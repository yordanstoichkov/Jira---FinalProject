<%@include file="navigation.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page session="false"%>
<script type="text/javascript" src="jquery-3.1.1.min.js"></script>

<link rel="stylesheet" href="sprActivate.css">
</head>
<body>
<script>
 $("#date_retour").datepicker(
     {
        changeMonth: true,
        changeYear: true,
        dateFormat: 'dd/mm/yy',
        minDate: $("#date_depart").val(),
        maxDate: $("#date_depart").val()+1m,
        defaultDate: $("#date_depart").val()
    });
 </script>
	<h1 style="font-size: 150%; padding-left: 300px; padding-top: 35px;">Sprint
		name :</h1>
	<h1
		style="font-size: 150%; color: blue; padding-left: 300px; padding-top: 45px;">${sprint.title }</h1>
	<div class="col-lg-12" style="padding-left: 250px; padding-top: 30px">
		<hr style="width: 90%;">
	</div>
	<div class="sprfrm">
		<form:form name="form" commandName="sprint">
			<fieldset class="contact-inner">
				<label>Sprint start date:</label>
				<p class="contact-input">
					<form:input type="date" path="startDate" id="myDate"
						></form:input>
				</p>
				<br /> <label style="padding-bottom: 15px">Sprint goals:</label>
				<p class="contact-input">

					<form:input cols='60' rows='8' type="textarea" path="sprintGoal" />
				</p>
				<p id="demo"></p>
				<p class="contact-submit">
					<button type="submit" value="Start Sprint"> Start sprint</button>
				</p>
			</fieldset>
		</form:form>
	</div>
</body>
</html>