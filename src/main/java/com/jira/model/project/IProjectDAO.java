package com.jira.model.project;

import java.time.LocalDate;

import com.jira.model.employee.Employee;
import com.jira.model.exceptions.ProjectException;

public interface IProjectDAO {

	int createProject(Project project, Employee employee) throws ProjectException;

	void setReleaseDate(Project project, LocalDate releaseDate) throws ProjectException;

	void setStartDate(Project project, LocalDate startDate) throws ProjectException;

	int getProjectCount() throws ProjectException;

	Project getProject(int projectid) throws ProjectException;

	int deleteProject(Project project) throws ProjectException;
}