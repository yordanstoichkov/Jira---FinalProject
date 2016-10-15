package com.jira.model.exceptions;

public class ProjectException extends Exception {

	private static final long serialVersionUID = 709917832750172607L;

	public ProjectException() {
		super();
	}

	public ProjectException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public ProjectException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ProjectException(String arg0) {
		super(arg0);
	}

	public ProjectException(Throwable arg0) {
		super(arg0);
	}

}
