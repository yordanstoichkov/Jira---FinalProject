package comment;

import java.time.LocalDateTime;

import employee.Employee;
import exceptions.CommentException;
import project.Issue;

public class Comment {
	private final String comment;
	private final Employee writer;
	private int commentID;
	private final LocalDateTime date;
	private final Issue where;

	public Comment(String comment, Employee writer, Issue where) throws CommentException {
		if ((comment.trim().length() == 0 || comment == null) || (where == null) || (writer == null)) {
			throw new CommentException("There was a problem creating this comment");
		}
		this.comment = comment;
		this.writer = writer;

		this.where = where;

		this.date = LocalDateTime.now();
	}

	public String getComment() {
		return comment;
	}

	public Employee getWriter() {
		return writer;
	}

	public int getCommentID() {
		return commentID;
	}

	public void setCommentID(int commentID) {
		if (commentID > 0) {
			this.commentID = commentID;
		}
	}

	public LocalDateTime getDate() {
		return date;
	}

}
