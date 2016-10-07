package com.jira.model.project;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.jira.model.exceptions.ProjectException;

public class Project {
	@NotNull
	private String title;
	private List<Sprint> sprints = new ArrayList<Sprint>();
	private LocalDate releaseDate;
	private int projectId;
	private int toDo;
	private int inProgress;
	private int done;

	public Project() {
	}

	public Project(String title) throws ProjectException {
		this.setTitle(title);
	}

	public void setReleaseDate(LocalDate releaseDate) throws ProjectException {
		// if (objectValidator(releaseDate)) {
		// this.releaseDate = releaseDate;
		// } else
		// throw new ProjectException("You entered invalid release date. Please,
		// try again!");
	}

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) throws ProjectException {
		if (projectId > 0) {
			this.projectId = projectId;
		} else
			throw new ProjectException("You entered invalid project id. Please, try again!");

	}

	public void addSprint(Sprint sprint) throws ProjectException {
		if (sprint != null) {
			this.sprints.add(sprint);
		} else {
			throw new ProjectException("You entered invalid sprint. Please, try again!");
		}
	}

	public List<Sprint> getSprints() {
		return Collections.unmodifiableList(sprints);
	}
	// VALIDATION!

	public int getToDo() {
		return toDo;
	}

	public int getInProgress() {
		return inProgress;
	}

	public int getDone() {
		return done;
	}

	public void setIssueStatus(Issue issue) {
		if (issue.getStatus().equals(WorkFlow.TO_DO)) {
			toDo++;
		}
		if (issue.getStatus().equals(WorkFlow.IN_PROGRESS)) {
			inProgress++;
		}
		if (issue.getStatus().equals(WorkFlow.DONE)) {
			done++;
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
