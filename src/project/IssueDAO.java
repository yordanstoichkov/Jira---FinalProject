package project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import controller.DBConnection;
import exceptions.ProjectException;

public class IssueDAO {

	private static final String CREATE_ISSUE_SQL = "INSERT INTO issues VALUES(NULL , NULL, NULL, ?, ? , ? , NULL, NULL, ?);";
	private static final String SELECT_STATUS_ID = "SELECT status_id FROM statuses WHERE status = ?;";
	private static final String SELECT_TYPE_ID = "SELECT type_id FROM issue_types WHERE type = ?;";
	private static final String SELECT_PRIORITY_ID = "SELECT priority_id FROM priority_level WHERE priority = ?;";

	public int createIssue(Issue issue) throws ProjectException {
		Connection connection = DBConnection.getConnection();

		int id = 0;
		try {
			connection.setAutoCommit(false);
			PreparedStatement statusPS = connection.prepareStatement(SELECT_STATUS_ID);
			statusPS.setString(1, issue.getStatus().toString());
			ResultSet statusRS = statusPS.executeQuery();
			statusRS.next();
			int statusID = statusRS.getInt("status_id");

			PreparedStatement typePS = connection.prepareStatement(SELECT_TYPE_ID);
			typePS.setString(1, issue.getType().toString());
			ResultSet typeRS = typePS.executeQuery();
			typeRS.next();
			int typeID = typeRS.getInt("type_id");

			PreparedStatement priorityPS = connection.prepareStatement(SELECT_PRIORITY_ID);
			priorityPS.setString(1, issue.getPriority().toString());
			ResultSet priorityRS = priorityPS.executeQuery();
			priorityRS.next();
			int priorityID = priorityRS.getInt("priority_id");

			PreparedStatement ps = connection.prepareStatement(CREATE_ISSUE_SQL, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, statusID);
			ps.setInt(2, typeID);
			ps.setString(3, issue.getTitle());
			ps.setInt(4, priorityID);
			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			id = rs.getInt(1);

			connection.commit();

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
		return id;
	}

}
