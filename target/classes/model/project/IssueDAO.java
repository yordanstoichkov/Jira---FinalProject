package model.project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import model.dbConnection.DBConnection;
import model.employee.Employee;
import model.employee.EmployeeDAO;
import model.exceptions.EmployeeException;
import model.exceptions.IsssueExeption;
import model.exceptions.ProjectException;

public class IssueDAO {

	private static final String CREATE_ISSUE_SQL = "INSERT INTO issues VALUES(NULL , NULL, NULL, ?, ? , ? , NULL, NULL, ?);";
	private static final String INSERT_ISSUE_ASSIGNEE = "INSERT INTO issues_developers VALUES(?,?)";
	private static final String UPDATE_ISSUE_DECRIPTION_SQL = "UPDATE issues SET description = ? WHERE issue_id=?";
	private static final String GET_SPRINT_ID_SQL = "SELECT sprint_id FROM sprints WHERE sprin";
	private static final String SET_SPRINT_TO_ISSUE_SQL = "UPDATE issues SET sprint_id = ? WHERE issue_id = ?";
	private static final String GET_ISSUE_COUNT_SQL = "SELECT count(*) as 'issue_count' FROM issues";

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

			List<Employee> asignees = issue.getAsignees();

			for (Employee employee : asignees) {

				int assigneeID = new EmployeeDAO().getEmployeeID(employee);

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
		} catch (EmployeeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

		int issueCount = 0 ;
		try {
			PreparedStatement statusPS = connection.prepareStatement(GET_ISSUE_COUNT_SQL);
			ResultSet result = statusPS.executeQuery();
			result.next();
			issueCount= result.getInt("issue_count");

		} catch (SQLException e) {
			throw new IsssueExeption("There was a problem getting the number",e);
		}
		return issueCount; 
				
	}
}
