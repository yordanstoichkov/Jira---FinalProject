package com.jira.model.employee;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.jira.model.employee.Employee.Jobs;
import com.jira.model.exceptions.EmployeeException;
import com.jira.model.project.WorkFlow;

public class Employee {
	private static final String DEFAULT_AVATAR_URL = "https://s3.amazonaws.com/avatars-jira/default.png";
	private static final String NAME_REGEX = "/^[a-z ,.'-]+$/i";
	private static final String EMAIL_REGEX = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String avatarPath;
	private int employeeID;
	private Jobs job;
	private Map<Integer, Map<Integer, Integer>> projectsIssues = new HashMap<Integer, Map<Integer, Integer>>();
	private int toDo;
	private int inProgres;
	private int done;

	public int getToDo(int projectId) {
		return projectsIssues.get(projectId).get(1);
	}

	public int getInProgres() {
		return inProgres;
	}

	public int getDone() {
		return done;
	}

	public enum Jobs {
		MANAGER, DEVELOPER, QA, REVIEWER;

		static Jobs getJob(String job) {
			Jobs result = null;
			if (job.equals(Jobs.DEVELOPER.toString())) {
				result = Jobs.DEVELOPER;
			}
			if (job.equals(Jobs.MANAGER.toString())) {
				result = Jobs.MANAGER;
			}
			if (job.equals(Jobs.QA.toString())) {
				result = Jobs.QA;
			}
			return result;

		}
	}

	public Employee(String email, String password) throws EmployeeException {
		if (!isEmailValid(email) || !stringValidate(password)) {
			throw new EmployeeException("Illegal email or password");
		}
		this.avatarPath=DEFAULT_AVATAR_URL;
		this.email = email;
		this.password = password;
	}

	public Employee(String firstName, String lastName, String email, String password) throws EmployeeException {
		this(email, password);
		if (!isNameValid(lastName) || !isNameValid(firstName)) {
			throw new EmployeeException("You should give legal personal information");
		}
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Employee(String firstName, String lastName, Jobs job, String email, String password)
			throws EmployeeException {
		this(firstName, lastName, email, password);
		this.setJob(job);
	}

	public static boolean isNameValid(String name) {
		if (name != null && name.trim().length() > 0 && !name.matches(NAME_REGEX)) {
			return true;
		}
		return false;
	}

	public static boolean isEmailValid(String email) {
		return email.matches(EMAIL_REGEX);
	}

	private static boolean stringValidate(String string) {
		return (string != null && string.trim().length() > 0);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) throws EmployeeException {
		if (stringValidate(firstName)) {
			this.firstName = firstName;
		} else {
			throw new EmployeeException("Ilegal first name");
		}
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) throws EmployeeException {
		if (stringValidate(lastName)) {
			this.lastName = lastName;
		} else {
			throw new EmployeeException("Ilegal last name");
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) throws EmployeeException {
		if (stringValidate(email)) {
			this.email = email;
		} else {
			throw new EmployeeException("Ilegal email");
		}
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) throws EmployeeException {
		if (stringValidate(password)) {
			this.password = password;
		} else {
			throw new EmployeeException("Ilegal password");
		}
	}

	public String getAvatarPath() {
		return avatarPath;
	}

	public void setAvatarPath(String avatarPath) throws EmployeeException {
		if (stringValidate(avatarPath)) {
			this.avatarPath = avatarPath;
		} else {
			throw new EmployeeException("Ilegal avatar path");
		}
	}

	public int getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(int employeeID) throws EmployeeException {
		if (employeeID > 0) {
			this.employeeID = employeeID;
		} else {
			throw new EmployeeException("Ilegal id");
		}
	}

	public Jobs getJob() {
		return job;
	}

	public void setJob(Jobs job) {
		this.job = job;
	}

	public int getToDoTasks(int projectId) {
		return projectsIssues.get(projectId).get(WorkFlow.TO_DO);
	}

	public int getDoneTasks(int projectId) {
		return projectsIssues.get(projectId).get(WorkFlow.DONE);
	}

	public int getInProgressTasks(int projectId) {
		return projectsIssues.get(projectId).get(WorkFlow.IN_PROGRESS);
	}

	public int getCodeReviewTasks(int projectId) {
		return projectsIssues.get(projectId).get(WorkFlow.CODE_REVIEW);
	}

	public void setProjectsIssues(int projectID, WorkFlow issueStatus, int numTasks) throws EmployeeException {
		if (projectID <= 0 || numTasks < 0) {
			throw new EmployeeException("This is not a vlid number of tasks");
		}
		if (!projectsIssues.containsKey(projectID)) {
			this.projectsIssues.put(projectID, new HashMap<Integer, Integer>());
		}
		Map<Integer, Integer> issues = projectsIssues.get(projectID);
		int status = 0;
		switch (issueStatus) {
		case TO_DO:
			status = 1;
			break;
		case CODE_REVIEW:
			status = 2;
			break;
		case IN_PROGRESS:
			status = 3;
			break;
		case DONE:
			status = 4;
			break;
		}
		issues.put(status, numTasks);
	}

}
