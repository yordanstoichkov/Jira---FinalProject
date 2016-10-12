package com.jira.model.project;

public interface IPartOfProjectDAO {

	int getStatusID(WorkFlow status) throws PartOfProjectException;

	int getTypeID(IssueType type) throws PartOfProjectException;

	IssueType getType(int issueId) throws PartOfProjectException;

	PriorityLevel getPriority(int priorityID) throws PartOfProjectException;

	int getPriorityID(PriorityLevel priority) throws PartOfProjectException;

	WorkFlow getStatus(int statusID) throws PartOfProjectException;

}