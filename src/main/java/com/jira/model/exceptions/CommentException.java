package com.jira.model.exceptions;

public class CommentException extends Exception {
	private static final long serialVersionUID = 72281317040350262L;

	public CommentException() {
		super();
	}

	public CommentException(String message, Throwable cause) {
		super(message, cause);
	}

	public CommentException(String message) {
		super(message);
	}

	public CommentException(Throwable cause) {
		super(cause);
	}

}
