package com.jira.model.exceptions;

public class EmployeeException extends Exception {

	private static final long serialVersionUID = -1339086678336748980L;

	public EmployeeException() {
		super();
	}

	public EmployeeException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public EmployeeException(String arg0) {
		super(arg0);
	}

	public EmployeeException(Throwable arg0) {
		super(arg0);
	}

}
