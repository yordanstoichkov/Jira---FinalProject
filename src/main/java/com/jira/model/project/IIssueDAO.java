package com.jira.model.project;

import com.jira.model.exceptions.IsssueExeption;

public interface IIssueDAO {

	int createIssue(Issue issue) throws IsssueExeption;

	void addDescriptionToIssue(Issue issue) throws IsssueExeption;

	void addIssueToSprint(Issue issue, Sprint sprint) throws IsssueExeption;

	int getIssueCount() throws IsssueExeption;

	Issue getIssue(int issueID) throws IsssueExeption;

}