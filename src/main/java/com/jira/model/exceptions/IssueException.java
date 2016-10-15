package com.jira.model.exceptions;

public class IssueException extends Exception {

	private static final long serialVersionUID = 8658128060710710706L;

	public IssueException() {
		super();
	}

	public IssueException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public IssueException(String message, Throwable cause) {
		super(message, cause);
	}

	public IssueException(String message) {
		super(message);
	}

	public IssueException(Throwable cause) {
		super(cause);
	}

}
