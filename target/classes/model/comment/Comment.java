package model.comment;

import java.time.LocalDateTime;

import model.employee.Employee;

public class Comment {
	private String comment;
	private Employee writer;
	private int commentID;
	private LocalDateTime date;

	public Comment(String comment, Employee writer) {
		this.comment = comment;
		this.writer = writer;
		this.date = LocalDateTime.now();
	}

}
