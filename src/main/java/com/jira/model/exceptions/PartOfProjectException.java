package com.jira.model.exceptions;

public class PartOfProjectException extends Exception {

	private static final long serialVersionUID = 3709238121802407942L;

	public PartOfProjectException() {
		super();
	}

	public PartOfProjectException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public PartOfProjectException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public PartOfProjectException(String arg0) {
		super(arg0);
	}

	public PartOfProjectException(Throwable arg0) {
		super(arg0);
	}

}
