package com.jira.model.employee;

import java.util.List;

import org.springframework.stereotype.Component;

import com.jira.model.exceptions.EmployeeException;
import com.jira.model.project.Issue;
import com.jira.model.project.Project;

@Component
public interface IEmployeeDAO {

	int registerUser(Employee emp) throws EmployeeException;

	int loginUser(Employee emp) throws EmployeeException;

	int removeUser(Employee emp) throws EmployeeException;

	int getEmployeeID(Employee emp) throws EmployeeException;

	int getUserCount() throws EmployeeException;

	int validEmail(String email) throws EmployeeException;

	List<Project> giveMyProjects(Employee emp) throws EmployeeException;

	List<Integer> getReviewers(int issueId) throws EmployeeException;

	List<Integer> getDevelopers(int issueId) throws EmployeeException;

	List<Integer> getManagers(int projectId) throws EmployeeException;

	Employee getEmployeeById(int employeeId);

	public List<String> getEmployeesNames();

	public List<Issue> getEmployeesIssues(Employee emp) throws EmployeeException;
}