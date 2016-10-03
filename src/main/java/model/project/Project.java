package model.project;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.exceptions.ProjectException;

public class Project extends PartOfProject {
	private List<Sprint> sprints = new ArrayList<Sprint>();
	private LocalDate releaseDate;
	private int projectId;
	private int toDo;
	private int inProgress;
	private int done;
	public Project(String title) throws ProjectException {
		super(title);
	}

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDate releaseDate) throws ProjectException {
		if (objectValidator(releaseDate)) {
			this.releaseDate = releaseDate;
		} else
			throw new ProjectException("You entered invalid release date. Please, try again!");
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

	public void addSprint(Sprint sprint) {
		this.sprints.add(sprint);
	}

	public List<Sprint> getSprints() {
		return Collections.unmodifiableList(sprints);
	}
//	VALIDATION!

	public int getToDo() {
		return toDo;
	}

	public void setToDo(int toDo) {
		this.toDo = toDo;
	}

	public int getInProgress() {
		return inProgress;
	}

	public void setInProgress(int inProgress) {
		this.inProgress = inProgress;
	}

	public int getDone() {
		return done;
	}

	public void setDone(int done) {
		this.done = done;
	}

	
	
	

}
