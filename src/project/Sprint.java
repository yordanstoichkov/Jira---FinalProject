package project;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import exceptions.ProjectException;

public class Sprint extends PartOfProject {
	private Project project;
	private List<Issue> issues = new ArrayList<>();
	private LocalDate endDate;
	private int sprintId;
	private WorkFolw status;

	public Sprint(String title, Project project) throws ProjectException {
		super(title);
		if (objectValidator(project)) {
			this.project = project;
			this.status=WorkFolw.TO_DO;
		} else
			throw new ProjectException("You entered invalid project. Please, try again!");

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

	public WorkFolw getStatus() {
		return status;
	}

	public void setStatus(WorkFolw status) {
		this.status = status;
	}

}
