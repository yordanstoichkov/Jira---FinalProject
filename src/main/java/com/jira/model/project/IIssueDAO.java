package com.jira.model.project;

import java.util.List;
import com.jira.model.comment.Comment;
import com.jira.model.exceptions.IssueException;

public interface IIssueDAO {

	int createIssue(Issue issue) throws IssueException;

	void addDescriptionToIssue(Issue issue) throws IssueException;

	void addIssueToSprint(Issue issue, Sprint sprint) throws IssueException;

	int getIssueCount() throws IssueException;

	Issue getIssue(int issueID) throws IssueException;

	public int updateIssueStatus(int issueId) throws IssueException;
	
	List<Comment> getComments(int issueId) throws IssueException;

	void commentIssue(Comment comment) throws IssueException;

	void deleteIssue(int issueId) throws IssueException;

	void addIssueFile(Issue issue) throws IssueException;
}