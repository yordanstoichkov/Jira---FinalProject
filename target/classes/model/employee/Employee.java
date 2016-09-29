package model.employee;

import model.exceptions.EmployeeException;

public class Employee {
	private static final String EMAIL_REGEX = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String avatarPath;
	private int employeeID;
	private Jobs job;

	public enum Jobs {
		MANAGER, DEVELOPER, QA, REVIEWER;
	}

	public Employee(String firstName, String lastName, Jobs job, String email, String password)
			throws EmployeeException {
		this(email, password);
		if (!stringValidate(lastName) || !stringValidate(firstName)) {
			throw new EmployeeException("This account cannot be created");
		}
		this.firstName = firstName;
		this.lastName = lastName;
		this.setJob(job);
	}

	public Employee(String email, String password) throws EmployeeException {
		if (!isEmailValid(email) || !stringValidate(password)) {
			throw new EmployeeException("This account cannot be created");
		}
		this.email = email;
		this.password = password;
	}

	static boolean isEmailValid(String email) {
		return email.matches(EMAIL_REGEX);
	}

	private boolean stringValidate(String string) {
		return (string != null && string.trim().length() > 0);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAvatarPath() {
		return avatarPath;
	}

	public void setAvatarPath(String avatarPath) {
		this.avatarPath = avatarPath;
	}

	public int getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}

	public Jobs getJob() {
		return job;
	}

	public void setJob(Jobs job) {
		this.job = job;
	}

}
