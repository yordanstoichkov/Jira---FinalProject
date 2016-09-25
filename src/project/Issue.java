package project;

import java.time.LocalDate;

import exceptions.ProjectException;

public class Issue extends PartOfProject {
	private Sprint sprint;
	private IssueType type;
	private WorkFlow status;
	private int issueId;
	private LocalDate lastUpdate;
	private PriorityLevel priority;

	public Issue(String title, PriorityLevel priority, IssueType type) throws ProjectException {
		super(title);
		this.status = WorkFlow.TO_DO;
		this.priority = priority;
		this.type = type;
	}

	public Sprint getSprint() {
		return sprint;
	}

	public void setSprint(Sprint sprint) {
		this.sprint = sprint;
	}

	public IssueType getType() {
		return type;
	}

	public void setType(IssueType type) {
		this.type = type;
	}

	public WorkFlow getStatus() {
		return status;
	}

	public void setStatus(WorkFlow status) {
		this.status = status;
	}

	public PriorityLevel getPriority() {
		return priority;
	}

	public void setPriority(PriorityLevel priority) {
		this.priority = priority;
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
