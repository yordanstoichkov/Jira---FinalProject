package com.jira.model.project;

import com.jira.model.exceptions.IssueException;

public enum IssueType {
	TASK, BUG;

	// Getting IssueType by String
	static IssueType getIssueType(String issueType) throws IssueException {
		if ((issueType != null) && (issueType.trim().length() > 0)) {
			if (issueType.equals(IssueType.TASK.toString())) {
				return IssueType.TASK;
			}
			if (issueType.equals(IssueType.BUG.toString())) {
				return IssueType.BUG;
			}
		}
		throw new IssueException("Invald issue type.");
	}

}
