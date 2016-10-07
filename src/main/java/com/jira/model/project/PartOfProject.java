package com.jira.model.project;

import java.time.LocalDate;

import com.jira.model.exceptions.ProjectException;

public abstract class PartOfProject {
	private String title;
	private LocalDate startDate;

	public PartOfProject(String title) throws ProjectException {
		if (stringValidator(title)) {
			this.title = title;
		} else
			throw new ProjectException("You entered invalid title. Please, try again!");
	}

	public String getTitle() {
		return title;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) throws ProjectException {
		if (objectValidator(startDate)) {
			this.startDate = startDate;
		} else
			throw new ProjectException("You entered invalid starting date. Please, try again!");

	}

	public boolean stringValidator(String string) {
		return string != null && string.trim().length() > 0;
	}

	public boolean objectValidator(Object object) {
		return object != null;
	}

}
