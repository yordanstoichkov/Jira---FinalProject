package com.jira.model.project;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.jira.model.employee.Employee;
import com.jira.model.employee.IValidator;
import com.jira.model.exceptions.IssueException;
import com.jira.model.exceptions.ProjectException;

public class Issue {
	@Autowired
	private IValidator validator;

	private Sprint sprint;
	private IssueType type;
	private WorkFlow status;
	private int issueId;
	private LocalDate lastUpdate;
	private PriorityLevel priority;
	private String description;
	private List<Integer> asignees = new ArrayList<Integer>();
	private List<Employee> employees = new ArrayList<Employee>();
	private String asigneesInfo;
	private String title;
	private String filePath;

	public Issue() {
		this.status = WorkFlow.TO_DO;
	}

	public Issue(Sprint sprint) throws IssueException {
		this.status = WorkFlow.TO_DO;
		this.setSprint(sprint);
	}

	public Issue(String title, WorkFlow status) throws IssueException {
		this.setTitle(title);
		this.setStatus(status);
	}

	public Issue(String title, PriorityLevel priority, IssueType type, List<Integer> asignees, String description)
			throws IssueException {
		this.setTitle(title);
		this.setPriority(priority);
		this.setType(type);
		this.setAsignees(asignees);
	}
// Setters
	public void setTitle(String title) throws IssueException {
		if (validator.stringValidator(title)) {
			this.title = title;
		} else {
			throw new IssueException("Invalid title of issue");
		}
	}

	private void setAsignees(List<Integer> asignees) throws IssueException {
		if (validator.objectValidator(asignees)) {
			this.asignees.addAll(asignees);
		} else {
			throw new IssueException("Please, enter exsisting employees for this issue");
		}
	}

	public void setSprint(Sprint sprint) throws IssueException {
		if (validator.objectValidator(sprint)) {
			this.sprint = sprint;
		} else {
			throw new IssueException("Please enter exsisting sprint for this issue");
		}
	}

	public void setType(IssueType type) throws IssueException {
		if (validator.objectValidator(type)) {
			this.type = type;
		} else {
			throw new IssueException("Invalid issue type");
		}
	}

	public void setStatus(WorkFlow status) throws IssueException {
		if (validator.objectValidator(status)) {
			this.status = status;
		} else {
			throw new IssueException("Invalid issue status");
		}
	}

	public void setPriority(PriorityLevel priority) throws IssueException {
		if (validator.objectValidator(priority)) {
			this.priority = priority;
		} else {
			throw new IssueException("Invalid issue priority");
		}
	}

	public void setIssueId(int issueId) throws ProjectException {
		if (validator.positiveNumberValidator(issueId)) {
			this.issueId = issueId;
		} else
			throw new ProjectException("You entered invalid issue id. Please, try again!");
	}

	public void setLastUpdate(LocalDate lastUpdate) throws IssueException {
		if (validator.objectValidator(lastUpdate)) {
			this.lastUpdate = lastUpdate;
		} else {
			throw new IssueException("Invalid date entered.");
		}
	}

	public void setDescription(String description) throws ProjectException {
		if (validator.stringValidator(description)) {
			this.description = description;
		} else {
			throw new ProjectException("You entered invalid description!");

		}
	}

	public void setAsignee(int asigneeID) throws IssueException {
		if (validator.positiveNumberValidator(asigneeID)) {
			this.asignees.add(asigneeID);
		} else {
			throw new IssueException("You entered invalid employee id.");
		}
	}

	public void setFilePath(String filePath) throws IssueException {
		if (validator.stringValidator(filePath)) {
			this.filePath = filePath;
		} else {
			throw new IssueException("You entered invalid file path.");
		}
	}

	public void setAsigneesInfo(String asigneesInfo) throws IssueException {
		if (validator.stringValidator(asigneesInfo)) {
			this.asigneesInfo = asigneesInfo;
		} else {
			throw new IssueException("You entered invalid asignee information.");
		}
	}

	public void setEmployees(Employee employees) throws IssueException {
		if (validator.objectValidator(employees)) {
			this.employees.add(employees);
		} else {
			throw new IssueException("Invalid employees given.");
		}
	}
// Getters
	public String getTitle() {
		return title;
	}

	public Sprint getSprint() {
		return sprint;
	}

	public IssueType getType() {
		return type;
	}

	public WorkFlow getStatus() {
		return status;
	}

	public PriorityLevel getPriority() {
		return priority;
	}

	public int getIssueId() {
		return issueId;
	}

	public LocalDate getLastUpdate() {
		return lastUpdate;
	}

	public List<Integer> getAsignees() {
		return Collections.unmodifiableList(asignees);
	}

	public String getDescription() {
		return description;
	}

	public String getFilePath() {
		return filePath;
	}

	public String getAsigneesInfo() {
		return asigneesInfo;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	// Checking if a user is asigneed on an issue
	public boolean isAsigneed(int employeeID) throws IssueException {
		if (validator.positiveNumberValidator(employeeID)) {
			for (Integer id : asignees) {
				if (employeeID == id) {
					return true;
				}
			}
			return false;
		} else {
			throw new IssueException("You entered invalid employee id.");
		}
	}
}
