package model.employee;

import model.exceptions.EmployeeException;

public class Employee {
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

	public Employee(String firstName, String lastName, String email, String password, Jobs job)
			throws EmployeeException {
		if (!stringValidate(lastName) || !stringValidate(firstName) || !stringValidate(email)
				|| !stringValidate(password)) {
			throw new EmployeeException("This account cannot be created");
		}
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.setJob(job);
	}

	private boolean stringValidate(String string) {
		return (string != null && string.trim().length() > 0);
	}

	public String getFirst_name() {
		return firstName;
	}

	public void setFirst_name(String firstName) {
		this.firstName = firstName;
	}

	public String getLast_name() {
		return lastName;
	}

	public void setLast_name(String lastName) {
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
