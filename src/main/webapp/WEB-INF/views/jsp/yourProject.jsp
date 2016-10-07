<%@include file="navigation.jsp"%>
<link href="issues.css" rel="stylesheet">
<script type="text/javascript" src="jquery-3.1.1.min.js"></script>
</head>
<body>

	<h1 style="padding-left: 280px; padding-top: 35px; font-size: 250%">
		<c:out value="${project.title}"></c:out>
	</h1>
	<div class="panel-heading">
		<h3 class="panel-title">Developers</h3>
		<div class="pull-right">
			<span class="clickable filter" data-toggle="tooltip"
				title="Toggle table filter" data-container="body"> <i
				class="glyphicon glyphicon-filter"></i>
			</span>
		</div>
	</div>
	<div class="panel-body">
		<input type="text" class="form-control" id="dev-table-filter"
			data-action="filter" data-filters="#dev-table"
			placeholder="Filter Developers" />
	</div>
	<div class="wrapper">

		<div class="table">


			<table class="table table-hover" id="dev-table">
				<thead>
					<tr>
						<th>#</th>
						<th>Issue name</th>
						<th>Last Name</th>
						<th>Priority</th>
						<th>Type</th>
					</tr>
				</thead>
				<tbody>

					<c:forEach items="${project.sprints}" var="sprint">
						<c:forEach items="${sprint.issues}" var="issue">
							<tr>
								<td>${issue.title}</td>
								<td>Kilgore</td>
								<td>${issue.priority}</td>
								<td>${issue.type}</td>
							</tr>
						</c:forEach>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<script src="issues.js"></script>
</body>
</html>