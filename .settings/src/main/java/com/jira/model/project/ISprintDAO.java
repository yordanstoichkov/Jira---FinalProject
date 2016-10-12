package com.jira.model.project;

import com.jira.model.exceptions.SprintException;

public interface ISprintDAO {

	int createSprint(Sprint sprint) throws SprintException;

	Sprint getSprint(int sprintID) throws SprintException;

		public void startSprint(Sprint sprint) throws SprintException;

}