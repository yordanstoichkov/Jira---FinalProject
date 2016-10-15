package com.jira.model.employee;

import org.springframework.beans.factory.annotation.Autowired;
import com.jira.model.exceptions.EmployeeException;

public class Employee {
	private IValidator validator = new Validator();

	private static final int MIN_SIZE_OF_PASSWORD = 6;
	private static final String DEFAULT_AVATAR_URL = "https://s3.amazonaws.com/avatars-jira/default.png";
	private static final String NAME_REGEX = "/^[A-Za-z ,.'-]+$/i";
	private static final String EMAIL_REGEX = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String avatarPath;
	private int employeeID;
	private Jobs job;

	public Employee(String email, String password) throws EmployeeException {
		this.setEmail(email);
		this.setPassword(password);
		this.avatarPath = DEFAULT_AVATAR_URL;
	}

	public Employee(String firstName, String lastName, String email, String password) throws EmployeeException {
		this(email, password);
		this.setFirstName(firstName);
		this.setLastName(lastName);
	}

	public Employee(String firstName, String lastName, Jobs job, String email, String password)
			throws EmployeeException {
		this(firstName, lastName, email, password);
		this.setJob(job);
	}

	public void setFirstName(String firstName) throws EmployeeException {
		if (validator.stringValidator(firstName)) {
			this.firstName = firstName;
		} else {
			throw new EmployeeException("Illegal first name");
		}
	}

	public void setLastName(String lastName) throws EmployeeException {
		if (validator.stringValidator(lastName)) {
			this.lastName = lastName;
		} else {
			throw new EmployeeException("Illegal last name");
		}
	}

	public void setEmail(String email) throws EmployeeException {
		if (validator.stringValidator(email) && isEmailValid(email)) {
			this.email = email;
		} else {
			throw new EmployeeException("Illegal email");
		}
	}

	public void setPassword(String password) throws EmployeeException {
		if (validator.stringValidator(password) && (password.length() >= MIN_SIZE_OF_PASSWORD)) {
			this.password = password;
		} else {
			throw new EmployeeException("Illegal password");
		}
	}

	public void setAvatarPath(String avatarPath) throws EmployeeException {
		if (validator.stringValidator(avatarPath)) {
			this.avatarPath = avatarPath;
		} else {
			throw new EmployeeException("Illegal avatar path");
		}
	}

	public void setEmployeeID(int employeeID) throws EmployeeException {
		if (validator.positiveNumberValidator(employeeID)) {
			this.employeeID = employeeID;
		} else {
			throw new EmployeeException("Illegal employee id");
		}
	}

	public void setJob(Jobs job) throws EmployeeException {
		if (validator.objectValidator(job)) {
			this.job = job;
		} else {
			throw new EmployeeException("Illegal job");
		}
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getAvatarPath() {
		return avatarPath;
	}

	public int getEmployeeID() {
		return employeeID;
	}

	public Jobs getJob() {
		return job;
	}

	public static boolean isNameValid(String name) {
		return name.matches(NAME_REGEX);
	}

	public static boolean isEmailValid(String email) {
		return email.matches(EMAIL_REGEX);
	}

	// Inner enum Jobs
	public enum Jobs {
		MANAGER, DEVELOPER, QA, REVIEWER;
		// Getting Job by String
		static Jobs getJob(String job) throws EmployeeException {
			if ((job != null) && (job.trim().length() > 0)) {
				if (job.equals(Jobs.DEVELOPER.toString())) {
					return Jobs.DEVELOPER;
				}
				if (job.equals(Jobs.MANAGER.toString())) {
					return Jobs.MANAGER;
				}
				if (job.equals(Jobs.QA.toString())) {
					return Jobs.QA;
				}
			}
			throw new EmployeeException("Invalid job.");
		}
	}

}
