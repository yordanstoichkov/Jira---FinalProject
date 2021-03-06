package com.jira.model.project;

import java.sql.Connection;
import com.jira.model.exceptions.PartOfProjectException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.jira.model.comment.Comment;
import com.jira.model.connections.DBConnection;
import com.jira.model.employee.Employee;
import com.jira.model.employee.EmployeeDAO;
import com.jira.model.employee.IEmployeeDAO;
import com.jira.model.employee.IValidator;
import com.jira.model.employee.Validator;
import com.jira.model.exceptions.EmployeeException;
import com.jira.model.exceptions.IssueException;
import com.jira.model.exceptions.ProjectException;

@Component
public class IssueDAO implements IIssueDAO {
	private IValidator validator = new Validator();

	private IPartOfProjectDAO partDAO = new PartOfProjectDAO();
	private IEmployeeDAO employeeDAO = new EmployeeDAO();

	private static final int STATUS_ID_OF_DONE = 4;
	private static final int STATUS_ID_OF_IN_PROGRESS = 2;
	private static final int STATUS_ID_OF_TO_DO = 1;
	private static final String CREATE_ISSUE_SQL = "INSERT INTO issues VALUES(NULL , NULL, ?, ?, ? , ? , NULL, ?, ?, NULL);";
	private static final String INSERT_ISSUE_ASSIGNEE = "INSERT INTO issues_developers VALUES(?,?)";
	private static final String UPDATE_ISSUE_DECRIPTION_SQL = "UPDATE issues SET description = ? WHERE issue_id=?";
	private static final String SET_SPRINT_TO_ISSUE_SQL = "UPDATE issues SET sprint_id = ? WHERE issue_id = ?";
	private static final String GET_ISSUE_COUNT_SQL = "SELECT count(*) as 'issue_count' FROM issues";
	private static final String GET_ISSUE_SQL = "SELECT * FROM issues WHERE issue_id = ?";
	private static final String SELECT_DEVELOPERS_OF_ISSUE_SQL = "SELECT developer_id FROM issues_developers WHERE issue_id = ?";
	private static final String UPDATE_STATUS_ID = "UPDATE issues SET status_id= ? WHERE issue_id = ?;";
	private static final String SELECT_COMMENTS_OF_ISSUE_SQL = "SELECT * FROM comments WHERE issue_id = ?";
	private static final String INSERT_ISSUE_COMMENT_SQL = "INSERT INTO  comments VALUES(NULL , ? , ? , ? , ?)";
	private static final String ADD_ISSUE_FILE_SQL = "UPDATE issues SET file_path=? WHERE issue_id= ?";
	private static final String DELETE_ISSUE_SQL = "DELETE FROM issues WHERE issue_id=?";

	// Creating new issue
	@Override
	public int createIssue(Issue issue) throws IssueException {
		if (!validator.objectValidator(issue)) {
			throw new IssueException("Invalid issue given");
		}
		Connection connection = DBConnection.getConnection();
		int issueID = 0;
		try {
			connection.setAutoCommit(false);
			int statusID = partDAO.getStatusID(issue.getStatus());
			int typeID = partDAO.getTypeID(issue.getType());
			int priorityID = partDAO.getPriorityID(issue.getPriority());
			PreparedStatement ps = connection.prepareStatement(CREATE_ISSUE_SQL, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, issue.getSprint().getSprintId());
			ps.setInt(2, statusID);
			ps.setInt(3, typeID);
			ps.setString(4, issue.getTitle());
			ps.setString(5, issue.getDescription());
			ps.setInt(6, priorityID);
			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			issueID = rs.getInt(1);
			issue.setIssueId(issueID);

			List<Integer> asignees = issue.getAsignees();
			for (Integer assigneeID : asignees) {
				if (assigneeID != 0) {
					PreparedStatement insertAsignee = connection.prepareStatement(INSERT_ISSUE_ASSIGNEE);
					insertAsignee.setInt(1, assigneeID);
					insertAsignee.setInt(2, issueID);
					insertAsignee.executeUpdate();
				}
			}
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new IssueException("This issue cannot be created right now", e1);
			}
			throw new IssueException("This issue cannot be created right now", e);
		} catch (Exception e) {
			throw new IssueException("This issue cannot be created right now", e);
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				throw new IssueException("This issue cannot be created right now", e);
			}
		}
		return issueID;
	}

	// Set issue's sprint
	@Override
	public void addIssueToSprint(Issue issue, Sprint sprint) throws IssueException {
		if ((!validator.objectValidator(issue) || (!validator.objectValidator(sprint)))) {
			throw new IssueException("Invalid issue or sprint given");
		}
		Connection connection = DBConnection.getConnection();
		try {
			PreparedStatement statusPS = connection.prepareStatement(SET_SPRINT_TO_ISSUE_SQL);
			statusPS.setInt(1, sprint.getSprintId());
			statusPS.setInt(2, issue.getIssueId());
			statusPS.executeUpdate();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new IssueException("This issue cannot be created right now", e1);
			}
			throw new IssueException("This issue cannot be created right now", e);
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				throw new IssueException("This issue cannot be created right now", e);
			}
		}
	}

	// Deleting issue by issue id
	@Override
	public void deleteIssue(int issueId) throws IssueException {
		if (!validator.positiveNumberValidator(issueId)) {
			throw new IssueException("Invalid issue id");
		}
		Connection connection = DBConnection.getConnection();
		try {
			PreparedStatement issueDelPS = connection.prepareStatement(DELETE_ISSUE_SQL);
			issueDelPS.setInt(1, issueId);
			issueDelPS.executeUpdate();
		} catch (SQLException e) {
			throw new IssueException("We can not delete issue right now. Please, try again later!", e);
		}
	}

	// Getting issue by issue id
	@Override
	public Issue getIssue(int issueID) throws IssueException {
		if (!validator.positiveNumberValidator(issueID)) {
			throw new IssueException("Invalid issue id");
		}
		Connection connection = DBConnection.getConnection();
		Issue result = null;
		try {
			PreparedStatement issuePS = connection.prepareStatement(GET_ISSUE_SQL);
			issuePS.setInt(1, issueID);
			ResultSet issueRS = issuePS.executeQuery();
			issueRS.next();
			String title = issueRS.getString("title");
			int statusID = issueRS.getInt("status_id");
			String description = issueRS.getString("description");
			Date startDate = issueRS.getDate("start_date");
			String pathFile = issueRS.getString("file_path");
			int typeID = issueRS.getInt("type_id");
			Date lastUpdate = issueRS.getDate("last_update");
			int priorityID = issueRS.getInt("priority_id");
			PriorityLevel priority = partDAO.getPriority(priorityID);
			IssueType type = partDAO.getType(typeID);
			WorkFlow status = partDAO.getStatus(statusID);
			result = new Issue(title, status);
			result.setIssueId(issueID);
			if (validator.objectValidator(pathFile)) {
				result.setFilePath(pathFile);
			}
			if (validator.objectValidator(description)) {
				result.setDescription(description);
			}
			result.setType(type);
			result.setPriority(priority);
			PreparedStatement asigneesPS = connection.prepareStatement(SELECT_DEVELOPERS_OF_ISSUE_SQL);
			asigneesPS.setInt(1, issueID);
			ResultSet asigneesRS = asigneesPS.executeQuery();
			List<Integer> asignees = new ArrayList<Integer>();
			while (asigneesRS.next()) {
				int asignee = asigneesRS.getInt("developer_id");
				asignees.add(asignee);
				result.setAsignee(asignee);
			}
			for (Integer asigneeId : asignees) {
				result.setEmployees(employeeDAO.getEmployeeById(asigneeId));
			}
		} catch (SQLException e) {
			throw new IssueException("Unfortunately your issue couln't be found", e);
		} catch (ProjectException e) {
			throw new IssueException("Unfortunately your issue couln't be found", e);
		} catch (PartOfProjectException e) {
			throw new IssueException("Unfortunately your issue couln't be found", e);
		} catch (EmployeeException e) {
			throw new IssueException("Unfortunately your issue couln't be found", e);
		}
		return result;
	}

	// Getting all comments of issue by issue id
	@Override
	public List<Comment> getComments(int issueId) throws IssueException {
		if (!validator.positiveNumberValidator(issueId)) {
			throw new IssueException("Invalid issue id.");
		}
		Connection connection = DBConnection.getConnection();
		List<Comment> commentsOfIssue = new ArrayList<>();
		try {
			PreparedStatement ps = connection.prepareStatement(SELECT_COMMENTS_OF_ISSUE_SQL);
			ps.setInt(1, issueId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String text = rs.getString("comment");
				int employeeId = rs.getInt("employee_id");
				Date date = rs.getDate("date");
				Employee emp = employeeDAO.getEmployeeById(employeeId);
				LocalDate localDate = date.toLocalDate();
				Comment comment = new Comment(text, emp);
				comment.setDate(localDate);
				commentsOfIssue.add(comment);
			}
		} catch (SQLException e) {
			throw new IssueException("We can get comments right now. Please, try again later!", e);
		} catch (EmployeeException e) {
			throw new IssueException("We can get comments right now. Please, try again later!", e);
		}
		return commentsOfIssue;
	}

	// Adding comment of issue
	@Override
	public void commentIssue(Comment comment) throws IssueException {
		if (!validator.objectValidator(comment)) {
			throw new IssueException("Invalid comment");
		}
		Connection connection = DBConnection.getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement(INSERT_ISSUE_COMMENT_SQL);
			ps.setDate(1, Date.valueOf(comment.getDate()));
			ps.setString(2, comment.getComment());
			ps.setInt(3, comment.getIssueId());
			ps.setInt(4, comment.getWriter().getEmployeeID());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new IssueException("We can insert comment right now. Please, try again later!", e);
		}
	}

	// Adding issue's file
	@Override
	public void addIssueFile(Issue issue) throws IssueException {
		if (!validator.objectValidator(issue)) {
			throw new IssueException("Invalid issue");
		}
		Connection connection = DBConnection.getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement(ADD_ISSUE_FILE_SQL);
			ps.setString(1, issue.getFilePath());
			ps.setInt(2, issue.getIssueId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new IssueException("We can insert file right now. Please, try again later!", e);
		}
	}

	// Adding description of issue
	@Override
	public void addDescriptionToIssue(Issue issue) throws IssueException {
		if (!validator.objectValidator(issue)) {
			throw new IssueException("Invalid issue given");
		}
		Connection connection = DBConnection.getConnection();
		try {
			PreparedStatement updateIssue = connection.prepareStatement(UPDATE_ISSUE_DECRIPTION_SQL);
			updateIssue.setString(1, issue.getDescription());
			updateIssue.setInt(2, issue.getIssueId());
			updateIssue.executeUpdate();
		} catch (SQLException e) {
			throw new IssueException("This issue description couldn't be added", e);
		}
	}

	// Changing issue status by issue id
	@Override
	public int updateIssueStatus(int issueId) throws IssueException {
		if (!validator.positiveNumberValidator(issueId)) {
			throw new IssueException("Invalid issue id");
		}
		int newStatusId = 1;
		Connection connection = DBConnection.getConnection();
		try {
			connection.setAutoCommit(false);
			PreparedStatement ps = connection.prepareStatement(GET_ISSUE_SQL);
			ps.setInt(1, issueId);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int statusId = rs.getInt("status_id");
			PreparedStatement updateIssue = connection.prepareStatement(UPDATE_STATUS_ID);
			if (statusId == STATUS_ID_OF_TO_DO) {
				updateIssue.setInt(1, STATUS_ID_OF_IN_PROGRESS);
				newStatusId = STATUS_ID_OF_IN_PROGRESS;
			}
			if (statusId == STATUS_ID_OF_IN_PROGRESS) {
				updateIssue.setInt(1, STATUS_ID_OF_DONE);
				newStatusId = STATUS_ID_OF_DONE;
			}
			updateIssue.setInt(2, issueId);
			updateIssue.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new IssueException("You can not change the status of issue right now! Try again later!", e1);
			}
			throw new IssueException("You can not change the status of issue right now! Try again later!", e);
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				throw new IssueException("You can not change the status of issue right now! Try again later!", e);

			}
		}
		return newStatusId;
	}

	// Getting IdeaTracker issues count
	@Override
	public int getIssueCount() throws IssueException {
		Connection connection = DBConnection.getConnection();
		int issueCount = 0;
		try {
			PreparedStatement statusPS = connection.prepareStatement(GET_ISSUE_COUNT_SQL);
			ResultSet result = statusPS.executeQuery();
			result.next();
			issueCount = result.getInt("issue_count");
		} catch (SQLException e) {
			throw new IssueException("There was a problem getting issues number", e);
		}
		return issueCount;
	}

}
