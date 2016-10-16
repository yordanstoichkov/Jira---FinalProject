package com.jira.model.comment;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import com.jira.model.employee.Employee;
import com.jira.model.employee.IValidator;
import com.jira.model.employee.Validator;
import com.jira.model.exceptions.IssueException;

public class Comment {
	private IValidator validator = new Validator();

	private String comment;
	private Employee writer;
	private int commentID;
	private LocalDate date;
	private int issueId;

	public Comment() {
	}

	public Comment(String comment, Employee writer) throws IssueException {
		this.setComment(comment);
		this.setWriter(writer);
	}

	public Comment(int issueId, Employee writer) throws IssueException {
		this.setWriter(writer);
		this.setIssueId(issueId);
	}

	public Comment(String comment, Employee writer, LocalDate date, int issueId) throws IssueException {
		this(comment, writer);
		this.setDate(date);
		this.setIssueId(issueId);
	}

	// Setters
	public void setComment(String comment) throws IssueException {
		if (validator.stringValidator(comment)) {
			this.comment = comment;
		} else {
			throw new IssueException("Invalid comment");
		}
	}

	public void setDate(LocalDate date) throws IssueException {
		if (validator.objectValidator(date)) {
			this.date = date;
		} else {
			throw new IssueException("invalid date");
		}
	}

	public void setIssueId(int issueId) throws IssueException {
		if (validator.positiveNumberValidator(issueId)) {
			this.issueId = issueId;
		} else {
			throw new IssueException("Invalid issue id");
		}
	}

	public void setWriter(Employee emp) throws IssueException {
		if (validator.objectValidator(emp)) {
			this.writer = emp;
		} else {
			throw new IssueException("Invalid user");
		}
	}

	// Getters
	public String getComment() {
		return comment;
	}

	public Employee getWriter() {
		return writer;
	}

	public LocalDate getDate() {
		return date;
	}

	public int getIssueId() {
		return issueId;
	}
}
