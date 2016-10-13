package com.jira.model.project;

import java.util.List;

import com.jira.model.comment.Comment;
import com.jira.model.exceptions.IssueExeption;

public interface IIssueDAO {

	int createIssue(Issue issue) throws IssueExeption;

	void addDescriptionToIssue(Issue issue) throws IssueExeption;

	void addIssueToSprint(Issue issue, Sprint sprint) throws IssueExeption;

	int getIssueCount() throws IssueExeption;

	Issue getIssue(int issueID) throws IssueExeption;

	public int updateIssueStatus(int issueId) throws IssueExeption;
	
	List<Comment> getComments(int issueId) throws IssueExeption;

	void commentIssue(Comment comment) throws IssueExeption;

	void deleteIssue(int issueId) throws IssueExeption;
}