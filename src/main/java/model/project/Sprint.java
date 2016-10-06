package model.project;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import model.exceptions.ProjectException;
import model.exceptions.SprintException;

public class Sprint extends PartOfProject {
	private Project project;
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

	public void setEndDate(LocalDate endDate) throws SprintException {
		if (objectValidator(endDate)) {
			this.endDate = endDate;
		} else
			throw new SprintException("You entered invalid end date. Please, try again!");

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

}
