package com.jira.model.project;

public enum IssueType {
	TASK, BUG;
	public static IssueType getIssueType(String issueType){
		if(issueType.equals(IssueType.TASK.toString())){
			return IssueType.TASK;
		}
		if(issueType.equals(IssueType.BUG.toString())){
			return IssueType.BUG;
		}
		return null;
	}
}
