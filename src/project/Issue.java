package project;

import java.time.LocalDate;

import exceptions.ProjectException;

public class Issue extends PartOfProject {
	private Sprint sprint;
	// private issueType type;
	// private Status status;
	private int issueId;
	private LocalDate lastUpdate;

	public Issue(String title) throws ProjectException {
		super(title);
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

}
