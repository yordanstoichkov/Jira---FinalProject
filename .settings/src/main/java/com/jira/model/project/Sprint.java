package com.jira.model.project;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.jira.model.exceptions.ProjectException;
import com.jira.model.exceptions.SprintException;

public class Sprint {
	private Project project;
	private List<Issue> issues = new ArrayList<Issue>();
	private LocalDate endDate;
	private int sprintId;
	private WorkFlow status;
	private LocalDate startDate;
	private String title;
	private String sprintGoal;

	public Sprint(String title) throws ProjectException {
		this.setTitle(title);
		this.status = WorkFlow.TO_DO;

	}

	public Sprint() {
		this.status = WorkFlow.TO_DO;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) throws SprintException {
		this.endDate = endDate;

	}

	public int getSprintId() {
		return sprintId;
	}

	public void setSprintId(int sprintId) throws SprintException {
		if (sprintId > 0) {
			this.sprintId = sprintId;
		} else
			throw new SprintException("You entered invalid sprint id. Please, try again!");

	}

	public void setProject(Project project) throws SprintException {
		if (project != null) {
			this.project = project;
		} else {
			throw new SprintException("This is illigal project");
		}
	}

	public Project getProject() {
		return project;
	}

	public WorkFlow getStatus() {
		return status;
	}

	public void setStatus(WorkFlow status) {
		this.status = status;
	}

	public void addIssue(Issue issue) throws SprintException {
		if (issue != null) {
			this.issues.add(issue);
		} else {
			throw new SprintException("This is illigal issue");
		}
	}

	public List<Issue> getIssues() {
		return Collections.unmodifiableList(issues);
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSprintGoal() {
		return sprintGoal;
	}

	public void setSprintGoal(String sprintGoal) {
		this.sprintGoal = sprintGoal;
	}

}
