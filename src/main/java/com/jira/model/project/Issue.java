package com.jira.model.project;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.jira.model.employee.Employee;
import com.jira.model.exceptions.EmployeeException;
import com.jira.model.exceptions.IsssueExeption;
import com.jira.model.exceptions.ProjectException;

public class Issue {
	private Sprint sprint;
	private IssueType type;
	private WorkFlow status;
	private int issueId;
	private LocalDate lastUpdate;
	private PriorityLevel priority;
	private String description;
	private List<Integer> asignees = new ArrayList<Integer>();
	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Issue(String title, PriorityLevel priority, IssueType type, List<Integer> asignees)
			throws ProjectException, EmployeeException, IsssueExeption {
		this.title = title;
		this.status = WorkFlow.TO_DO;
		this.priority = priority;
		this.type = type;
		if (asignees != null) {
			this.setAsignees(asignees);
		} else {
			throw new EmployeeException("Sorry there is a problem creating this issue");
		}
	}

	private void setAsignees(List<Integer> asignees) throws IsssueExeption {
		if (asignees != null) {
			this.asignees.addAll(asignees);
		} else {
			throw new IsssueExeption("Please enter exsisting employee for this issue");
		}
	}

	public Issue(String title, WorkFlow status) throws ProjectException {
		this.title=title;
		this.status = status;
	}

	public Sprint getSprint() {
		return sprint;
	}

	public void setSprint(Sprint sprint) throws IsssueExeption {
		if (sprint != null) {
			this.sprint = sprint;
		} else {
			throw new IsssueExeption("Please enter exsisting sprint for this issue");
		}
	}

	public IssueType getType() {
		return type;
	}

	public void setType(IssueType type) {
		this.type = type;
	}

	public WorkFlow getStatus() {
		return status;
	}

	public void setStatus(WorkFlow status) {
		this.status = status;
	}

	public PriorityLevel getPriority() {
		return priority;
	}

	public void setPriority(PriorityLevel priority) {
		this.priority = priority;
	}

	public int getIssueId() {
		return issueId;
	}

	public void setIssueId(int issueId) throws ProjectException {
		if (issueId > 0) {
			this.issueId = issueId;
		} else
			throw new ProjectException("You entered invalid issue id. Please, try again!");

	}

	public LocalDate getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(LocalDate lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public List<Integer> getAsignees() {
		return Collections.unmodifiableList(asignees);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) throws ProjectException {
		if (description != null && !description.trim().equals("")) {
			this.description = description;
		} else {
			throw new ProjectException("You entered invalid description!");

		}
	}

	public void setAsignee(int asigneeID) throws ProjectException {
		if (asigneeID > 0) {
			this.asignees.add(asigneeID);
		} else {
			throw new ProjectException("You entered invalid employee id. ");
		}
	}

	public boolean isAsigneed(int employeeID) {
		for (Integer id : asignees) {
			if (employeeID == id) {
				return true;
			}
		}
		return false;
	}

}
