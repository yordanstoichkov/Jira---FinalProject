package employee;

import exceptions.EmployeeException;

public class Employee {
	private String first_name;
	private String last_name;
	private String email;
	private String password;
	private String avatarPath;
	private int employeeID;

	public Employee(String first_name, String last_name, String email, String password, String avatarPath)
			throws EmployeeException {
		if (!stringValidate(avatarPath) || !stringValidate(last_name) || !stringValidate(first_name)
				|| !stringValidate(email) || !stringValidate(password)) {
			throw new EmployeeException("This account cannot be created");
		}

		this.avatarPath = avatarPath;
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.password = password;
	}

	private boolean stringValidate(String string) {
		return (string != null && string.trim().length() > 0);
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
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

}
