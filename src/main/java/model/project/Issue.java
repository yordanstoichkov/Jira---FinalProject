package model.project;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import model.employee.Employee;
import model.exceptions.EmployeeException;
import model.exceptions.ProjectException;

public class Issue extends PartOfProject {
	private Sprint sprint;
	private IssueType type;
	private WorkFlow status;
	private int issueId;
	private LocalDate lastUpdate;
	private PriorityLevel priority;
	private String description;
	private List<Employee> asignees = new ArrayList<Employee>();

	public Issue(String title, PriorityLevel priority, IssueType type, List<Employee> asignees)
			throws ProjectException, EmployeeException {
		super(title);
		this.status = WorkFlow.TO_DO;
		this.priority = priority;
		this.type = type;
		if (asignees != null) {
			this.setAsignees(asignees);
		} else {
			throw new EmployeeException("Sorry there is a problem creating this issue");
		}
	}

	public Sprint getSprint() {
		return sprint;
	}

	public void setSprint(Sprint sprint) {
		this.sprint = sprint;
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

	public List<Employee> getAsignees() {
		return Collections.unmodifiableList(asignees);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setAsignees(List<Employee> asignees) {
		this.asignees = asignees;
	}

}
