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

	public Comment(String comment, Employee writer) {
		this.comment = comment;
		this.writer = writer;
		this.date=LocalDate.now();
		
	}
	public Comment(String comment, Employee writer,LocalDate date) {
		this.comment = comment;
		this.writer = writer;
		this.date =date;
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

}
