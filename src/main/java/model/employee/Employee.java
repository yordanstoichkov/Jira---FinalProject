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
		if (!stringValidate(lastName) || !stringValidate(firstName) || job == null) {
			throw new EmployeeException("You should give legal information");
		}
		this.firstName = firstName;
		this.lastName = lastName;
		this.setJob(job);
	}

	public Employee(String email, String password) throws EmployeeException {
		if (!isEmailValid(email) || !stringValidate(password)) {
			throw new EmployeeException("Illegal email or password");
		}
		this.email = email;
		this.password = password;
	}

	public static boolean isEmailValid(String email) {
		return email.matches(EMAIL_REGEX);
	}

	private boolean stringValidate(String string) {
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
		if(employeeID>0){
		this.employeeID = employeeID;
		}else{
			throw new EmployeeException("Ilegal id");
		}
	}

	public Jobs getJob() {
		return job;
	}

	public void setJob(Jobs job) {
		this.job = job;
	}

}
