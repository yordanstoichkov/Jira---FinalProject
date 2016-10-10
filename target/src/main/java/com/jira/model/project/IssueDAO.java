package com.jira.model.project;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jira.model.comment.Comment;
import com.jira.model.dbConnection.DBConnection;
import com.jira.model.employee.Employee;
import com.jira.model.employee.EmployeeDAO;
import com.jira.model.employee.IEmployeeDAO;
import com.jira.model.exceptions.EmployeeException;
import com.jira.model.exceptions.IsssueExeption;
import com.jira.model.exceptions.ProjectException;

@Component
public class IssueDAO implements IIssueDAO {

	private static final int STATUS_ID_OF_DONE = 4;
	private static final int STATUS_ID_OF_IN_PROGRESS = 2;
	private static final int STATUS_ID_OF_TO_DO = 1;
	private static final String CREATE_ISSUE_SQL = "INSERT INTO issues VALUES(NULL , NULL, NULL, ?, ? , ? , NULL, NULL, ?);";
	private static final String INSERT_ISSUE_ASSIGNEE = "INSERT INTO issues_developers VALUES(?,?)";
	private static final String UPDATE_ISSUE_DECRIPTION_SQL = "UPDATE issues SET description = ? WHERE issue_id=?";
	private static final String GET_SPRINT_ID_SQL = "SELECT sprint_id FROM sprints WHERE sprin";
	private static final String SET_SPRINT_TO_ISSUE_SQL = "UPDATE issues SET sprint_id = ? WHERE issue_id = ?";
	private static final String GET_ISSUE_COUNT_SQL = "SELECT count(*) as 'issue_count' FROM issues";
	private static final String GET_ISSUE_SQL = "SELECT * FROM issues WHERE issue_id = ?";
	private static final String SELECT_DEVELOPERS_OF_ISSUE_SQL = "SELECT developer_id FROM issues_developers WHERE issue_id = ?";
	private static final String UPDATE_STATUS_ID = "UPDATE issues SET status_id= ? WHERE issue_id = ?;";
	private static final String SELECT_COMMENTS_OF_ISSUE_SQL = "SELECT * FROM comments WHERE issue_id = ?";

	@Autowired
	private IPartOfProjectDAO partDAO;
	@Autowired
	private IEmployeeDAO employeeDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jira.model.project.IIssueDAO#createIssue(com.jira.model.project.
	 * Issue)
	 */
	@Override
	public int createIssue(Issue issue) throws IsssueExeption {
		if (issue == null) {
			throw new IsssueExeption("Invalid issue given");
		}
		Connection connection = DBConnection.getConnection();
		int issueID = 0;
		try {
			connection.setAutoCommit(false);

			int statusID = partDAO.getStatusID(issue.getStatus());

			int typeID = partDAO.getTypeID(issue.getType());

			int priorityID = partDAO.getPriorityID(issue.getPriority());

			PreparedStatement ps = connection.prepareStatement(CREATE_ISSUE_SQL, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, statusID);
			ps.setInt(2, typeID);
			ps.setString(3, issue.getTitle());
			ps.setInt(4, priorityID);
			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			issueID = rs.getInt(1);

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
				throw new IsssueExeption("This issue cannot be created right now", e);
			}
			throw new IsssueExeption("This issue cannot be created right now", e);
		} catch (Exception e) {
			throw new IsssueExeption("This issue cannot be created right now", e);

		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				throw new IsssueExeption("This issue cannot be created right now", e);
			}
		}
		return issueID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jira.model.project.IIssueDAO#addDescriptionToIssue(com.jira.model.
	 * project.Issue)
	 */
	@Override
	public void addDescriptionToIssue(Issue issue) throws IsssueExeption {
		if (issue == null) {
			throw new IsssueExeption("Invalid issue given");
		}
		Connection connection = DBConnection.getConnection();

		try {
			PreparedStatement updateIssue = connection.prepareStatement(UPDATE_ISSUE_DECRIPTION_SQL);
			updateIssue.setString(1, issue.getDescription());
			updateIssue.setInt(2, issue.getIssueId());
			updateIssue.executeUpdate();
		} catch (SQLException e) {
			throw new IsssueExeption("This issue description couldn't be added");
		}

	}

	public int updateIssueStatus(int issueId) throws IsssueExeption {
		if (issueId <= 0) {
			throw new IsssueExeption("Invalid issue given");
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
				throw new IsssueExeption("You can not change the status of issue right now! Try again later!");
			}
			throw new IsssueExeption("You can not change the status of issue right now! Try again later!");
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				throw new IsssueExeption("You can not change the status of issue right now! Try again later!");

			}
		}
		return newStatusId;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jira.model.project.IIssueDAO#addIssueToSprint(com.jira.model.project.
	 * Issue, com.jira.model.project.Sprint)
	 */
	@Override
	public void addIssueToSprint(Issue issue, Sprint sprint) throws IsssueExeption {
		if (issue == null) {
			throw new IsssueExeption("Invalid issue given");
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
				throw new IsssueExeption("This issue cannot be created right now", e);
			}
			throw new IsssueExeption("This issue cannot be created right now", e);
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				throw new IsssueExeption("This issue cannot be created right now", e);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jira.model.project.IIssueDAO#getIssueCount()
	 */
	@Override
	public int getIssueCount() throws IsssueExeption {
		Connection connection = DBConnection.getConnection();

		int issueCount = 0;
		try {
			PreparedStatement statusPS = connection.prepareStatement(GET_ISSUE_COUNT_SQL);
			ResultSet result = statusPS.executeQuery();
			result.next();
			issueCount = result.getInt("issue_count");

		} catch (SQLException e) {
			throw new IsssueExeption("There was a problem getting the number", e);
		}
		return issueCount;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jira.model.project.IIssueDAO#getIssue(int)
	 */
	@Override
	public Issue getIssue(int issueID) throws IsssueExeption {
		if (issueID <= 0) {
			throw new IsssueExeption("Invalid issue given");
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
			int typeID = issueRS.getInt("type_id");
			Date lastUpdate = issueRS.getDate("last_update");
			int priorityID = issueRS.getInt("priority_id");
			PriorityLevel priority = partDAO.getPriority(priorityID);
			IssueType type = partDAO.getType(typeID);
			WorkFlow status = partDAO.getStatus(statusID);
			result = new Issue(title, status);
			result.setIssueId(issueID);
			if (description != null) {
				result.setDescription(description);
			}
			result.setType(type);
			result.setPriority(priority);
			PreparedStatement asigneesPS = connection.prepareStatement(SELECT_DEVELOPERS_OF_ISSUE_SQL);
			asigneesPS.setInt(1, issueID);
			ResultSet asigneesRS = asigneesPS.executeQuery();
			while (asigneesRS.next()) {
				result.setAsignee(asigneesRS.getInt("developer_id"));
			}
		} catch (SQLException e) {
			throw new IsssueExeption("Unfortunately your issue couln't be found", e);
		} catch (ProjectException e) {
			throw new IsssueExeption("Unfortunately your issue couln't be found", e);
		} catch (PartOfProjectException e) {
			throw new IsssueExeption("Unfortunately your issue couln't be found", e);
		}
		return result;
	}

	public static java.sql.Date localTimeToDate(LocalDateTime lt) {
		return new java.sql.Date(lt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
	}

	public List<Comment> getComments(int issueId) throws IsssueExeption {
		Connection connection = DBConnection.getConnection();
		List<Comment> commentsOfIssue = new ArrayList<>();
		try {
			PreparedStatement ps = connection.prepareStatement(SELECT_COMMENTS_OF_ISSUE_SQL);
			ps.setInt(1, issueId);
			ResultSet rs = ps.executeQuery();
			Comment comment = null;
			while (rs.next()) {
				String text = rs.getString("comment");
				int employeeId = rs.getInt("employee_id");
				Date date = rs.getDate("date");
				Employee emp = employeeDAO.getEmployeeById(employeeId);
				LocalDate localDate = date.toLocalDate();
				comment = new Comment(text, emp, localDate);
				commentsOfIssue.add(comment);
			}

		} catch (SQLException e) {
			throw new IsssueExeption("We can get comments right now. Please, try again later!");
		}
		return commentsOfIssue;
	}

}
