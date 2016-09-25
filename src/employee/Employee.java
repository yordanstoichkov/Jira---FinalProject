package employee;

import exceptions.EmployeeException;

public class Employee {
	private static final String EMAIL_REGEX = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
	private static final int MIN_LENGTH_FOR_PASSWORD = 6;
	private String first_name;
	private String last_name;
	private String email;
	private String password;
	private String avatarPath;
	private int employeeID;

	public Employee(String first_name, String last_name, String email, String password, String avatarPath)
			throws EmployeeException {
		if (!stringValidate(avatarPath) || !stringValidate(last_name) || !stringValidate(first_name)
				|| !isEmailValid(email) || !isPasswordStrong(password)) {
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
	
	static boolean isEmailValid(String email) {
		return email.matches(EMAIL_REGEX);
	}

	static boolean isPasswordStrong(String password) {
		boolean hasSmallCase = false, hasUpperCase = false, hasDigit = false;
		if (password != null && password.length() >= MIN_LENGTH_FOR_PASSWORD) {
			for (int symbol = 0; symbol < password.length(); symbol++) {
				if ((password.charAt(symbol) >= 'a') && (password.charAt(symbol) <= 'z')) {
					hasSmallCase = true;
				}
				if ((password.charAt(symbol) >= 'A') && (password.charAt(symbol) <= 'Z')) {
					hasUpperCase = true;
				}
				if ((password.charAt(symbol) >= '0') && (password.charAt(symbol) <= '9')) {
					hasDigit = true;
				}
			}
		}
		return hasDigit && hasSmallCase && hasUpperCase;
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
