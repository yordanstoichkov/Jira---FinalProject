package com.jira.model.project;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.jira.model.employee.IValidator;
import com.jira.model.exceptions.SprintException;

public class Sprint {
	@Autowired
	private IValidator validator;

	private Project project;
	private List<Issue> issues = new ArrayList<Issue>();
	private LocalDate endDate;
	private int sprintId;
	private WorkFlow status;
	private LocalDate startDate;
	private String title;
	private String sprintGoal;

	public Sprint() {
		this.status = WorkFlow.TO_DO;
	}

	public Sprint(String title) throws SprintException {
		this();
		this.setTitle(title);
	}

	// Setters
	public void setTitle(String title) throws SprintException {
		if (validator.stringValidator(title)) {
			this.title = title;
		} else {
			throw new SprintException("Invalid title.");
		}
	}

	public void setStartDate(LocalDate startDate) throws SprintException {
		if (validator.objectValidator(startDate)) {
			this.startDate = startDate;
		} else
			throw new SprintException("You entered invalid start date. Please, try again!");
	}

	public void setEndDate(LocalDate endDate) throws SprintException {
		if (validator.objectValidator(endDate)) {
			this.endDate = endDate;
		} else
			throw new SprintException("You entered invalid end date. Please, try again!");
	}

	public void setSprintId(int sprintId) throws SprintException {
		if (validator.positiveNumberValidator(sprintId)) {
			this.sprintId = sprintId;
		} else
			throw new SprintException("You entered invalid sprint id. Please, try again!");
	}

	public void setProject(Project project) throws SprintException {
		if (validator.objectValidator(project)) {
			this.project = project;
		} else {
			throw new SprintException("This is invalid project");
		}
	}

	public void setStatus(WorkFlow status) throws SprintException {
		if (validator.objectValidator(status)) {
			this.status = status;
		} else {
			throw new SprintException("Invalid status of sprint");
		}
	}

	public void setSprintGoal(String sprintGoal) throws SprintException {
		if (validator.stringValidator(sprintGoal)) {
			this.sprintGoal = sprintGoal;
		} else {
			throw new SprintException("Invalid sprint goal.");
		}
	}

	// Getters
	public String getSprintGoal() {
		return sprintGoal;
	}

	public String getTitle() {
		return title;
	}

	public List<Issue> getIssues() {
		return Collections.unmodifiableList(issues);
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public Project getProject() {
		return project;
	}

	public WorkFlow getStatus() {
		return status;
	}

	public int getSprintId() {
		return sprintId;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	// Adding sprint's issue
	public void addIssue(Issue issue) throws SprintException {
		if (validator.objectValidator(issue)) {
			this.issues.add(issue);
		} else {
			throw new SprintException("This is invalid issue");
		}
	}
}
