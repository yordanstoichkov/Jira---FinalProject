package com.jira.model.project;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import com.jira.model.employee.IValidator;
import com.jira.model.exceptions.ProjectException;

public class Project {
	@Autowired
	private IValidator validator;

	@NotNull
	private String title;
	private List<Sprint> sprints = new ArrayList<Sprint>();
	private LocalDate releaseDate;
	private int projectId;
	private int toDo;
	private int inProgress;
	private int done;
	private LocalDate startDate;

	public Project() {
	}

	public Project(String title) throws ProjectException {
		this.setTitle(title);
	}
// Setters
	public void setTitle(String title) throws ProjectException {
		if (validator.stringValidator(title)) {
			this.title = title;
		} else {
			throw new ProjectException("Invalid title");
		}
	}
	
	public void setProjectId(int projectId) throws ProjectException {
		if (validator.positiveNumberValidator(projectId)) {
			this.projectId = projectId;
		} else
			throw new ProjectException("You entered invalid project id. Please, try again!");
	}
	
	public void setStartDate(LocalDate startDate) throws ProjectException {
		if (validator.objectValidator(startDate)) {
			this.startDate = startDate;
		} else
			throw new ProjectException("You entered invalid start date. Please, try again!");
	}
	
	public void setReleaseDate(LocalDate releaseDate) throws ProjectException {
		if (validator.objectValidator(releaseDate)) {
			this.releaseDate = releaseDate;
		} else
			throw new ProjectException("You entered invalid release date. Please, try again!");
	}
	
//	Getters
	public LocalDate getReleaseDate() {
		return releaseDate;
	}
	
	public int getProjectId() {
		return projectId;
	}
	
	public List<Sprint> getSprints() {
		return Collections.unmodifiableList(sprints);
	}
	
	public int getToDo() {
		return toDo;
	}
	
	public int getInProgress() {
		return inProgress;
	}
	
	public int getDone() {
		return done;
	}
	
	public String getTitle() {
		return title;
	}
	
	public LocalDate getStartDate() {
		return startDate;
	}
// Adding project's sprint
	public void addSprint(Sprint sprint) throws ProjectException {
		if (validator.objectValidator(sprint)) {
			this.sprints.add(sprint);
		} else {
			throw new ProjectException("You entered invalid sprint. Please, try again!");
		}
	}
	
	// Getting number of project's issues by status
	public void setIssueStatus(Issue issue) throws ProjectException {
		if (validator.objectValidator(issue)) {
			if (issue.getStatus().equals(WorkFlow.TO_DO)) {
				toDo++;
			}
			if (issue.getStatus().equals(WorkFlow.IN_PROGRESS)) {
				inProgress++;
			}
			if (issue.getStatus().equals(WorkFlow.DONE)) {
				done++;
			}
		} else {
			throw new ProjectException("Invalid issue");
		}
	}
}
