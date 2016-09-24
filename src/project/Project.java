package project;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import exceptions.ProjectException;

public class Project extends PartOfProject {
	private List<Sprint> sprints = new ArrayList<>();
	private LocalDate releaseDate;
	private int projectId;

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

}
