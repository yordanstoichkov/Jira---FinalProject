package model.project;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import model.exceptions.ProjectException;

public class Sprint extends PartOfProject {
	private Project project;
	public void setProject(Project project) {
		this.project = project;
	}

	private List<Issue> issues = new ArrayList<Issue>();
	private LocalDate endDate;
	private int sprintId;
	private WorkFlow status;

	public Sprint(String title) throws ProjectException {
		super(title);
		this.status = WorkFlow.TO_DO;

	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) throws ProjectException {
		if (objectValidator(endDate)) {
			this.endDate = endDate;
		} else
			throw new ProjectException("You entered invalid end date. Please, try again!");

	}

	public int getSprintId() {
		return sprintId;
	}

	public void setSprintId(int sprintId) throws ProjectException {
		if (sprintId > 0) {
			this.sprintId = sprintId;
		} else
			throw new ProjectException("You entered invalid sprint id. Please, try again!");

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

	public void addIssue(Issue issue) {
		this.issues.add(issue);
	}

	public List<Issue> getIssues() {
		return Collections.unmodifiableList(issues);
	}
	
}
