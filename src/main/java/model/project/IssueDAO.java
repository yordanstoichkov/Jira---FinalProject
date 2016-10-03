package model.project;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import model.dbConnection.DBConnection;
import model.employee.Employee;
import model.employee.EmployeeDAO;
import model.exceptions.EmployeeException;
import model.exceptions.IsssueExeption;
import model.exceptions.ProjectException;

@Component
public class IssueDAO {

	private static final String CREATE_ISSUE_SQL = "INSERT INTO issues VALUES(NULL , NULL, NULL, ?, ? , ? , NULL, NULL, ?);";
	private static final String INSERT_ISSUE_ASSIGNEE = "INSERT INTO issues_developers VALUES(?,?)";
	private static final String UPDATE_ISSUE_DECRIPTION_SQL = "UPDATE issues SET description = ? WHERE issue_id=?";
	private static final String GET_SPRINT_ID_SQL = "SELECT sprint_id FROM sprints WHERE sprin";
	private static final String SET_SPRINT_TO_ISSUE_SQL = "UPDATE issues SET sprint_id = ? WHERE issue_id = ?";
	private static final String GET_ISSUE_COUNT_SQL = "SELECT count(*) as 'issue_count' FROM issues";
	private static final String GET_ISSUE_SQL = "SELECT * FROM issues WHERE issue_id = ?";
	private static final String SELECT_DEVELOPERS_OF_ISSUE_SQL = "SELECT developer_id FROM issues_developers WHERE issue_id = ?";

	public int createIssue(Issue issue) throws ProjectException, PartOfProjectException {
		Connection connection = DBConnection.getConnection();
		PartOfProjectDAO idDAO = new PartOfProjectDAO();
		int issueID = 0;
		try {
			connection.setAutoCommit(false);

			int statusID = idDAO.getStatusID(issue.getStatus());

			int typeID = idDAO.getTypeID(issue.getType());

			int priorityID = idDAO.getPriorityID(issue.getPriority());

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
				throw new ProjectException("This issue cannot be created right now", e);
			}
			throw new ProjectException("This issue cannot be created right now", e);
		} catch (Exception e) {
			throw new ProjectException("This issue cannot be created right now", e);

		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				throw new ProjectException("This issue cannot be created right now", e);
			}
		}
		return issueID;
	}

	public void addDescriptionToIssue(Issue issue) throws IsssueExeption {
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

	public void addIssueToSprint(Issue issue, Sprint sprint) throws ProjectException {
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
				throw new ProjectException("This issue cannot be created right now", e);
			}
			throw new ProjectException("This issue cannot be created right now", e);
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				throw new ProjectException("This issue cannot be created right now", e);
			}
		}
	}

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

	public Issue getIssue(int issueID) throws IsssueExeption {
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
			PriorityLevel priority = new PartOfProjectDAO().getPriority(priorityID);
			IssueType type = new PartOfProjectDAO().getType(typeID);
			WorkFlow status = new PartOfProjectDAO().getStatus(statusID);
			result = new Issue(title, status);
			result.setDescription(description);
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
}
