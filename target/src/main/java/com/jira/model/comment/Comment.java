package com.jira.model.comment;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.jira.model.employee.Employee;

public class Comment {
	private String comment;
	private Employee writer;
	private int commentID;
	private LocalDate date;
	private int issueId;

	public Comment(String comment, Employee writer) {
		this.comment = comment;
		this.writer = writer;
		this.date = LocalDate.now();

	}

	public Comment(int issueId, Employee writer) {

		this.writer = writer;
		this.issueId = issueId;

	}

	public Comment(String comment, Employee writer, LocalDate date, int issueId) {
		this.comment = comment;
		this.writer = writer;
		this.date = date;
		this.issueId = issueId;
	}

	public Comment() {
	}

	public String getComment() {
		return comment;
	}

	public Employee getWriter() {
		return writer;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getIssueId() {
		return issueId;
	}

	public void setIssueId(int issueId) {
		this.issueId = issueId;
	}

	public void setWriter(Employee emp) {
		this.writer = emp;

	}

}
