package model.project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import ch.qos.logback.core.subst.Token.Type;
import model.dbConnection.DBConnection;

@Component
public class PartOfProjectDAO {
	private static final String SELECT_STATUS_ID = "SELECT status_id FROM statuses WHERE status = ?;";
	private static final String SELECT_TYPE_ID = "SELECT type_id FROM issue_types WHERE type = ?;";
	private static final String SELECT_PRIORITY_ID = "SELECT priority_id FROM priority_level WHERE priority = ?;";
	private static final String SELECT_STATUS_SQL = "SELECT * FROM statuses WHERE status_id = ?";
	private static final String SELECT_ISSUE_TYPE_SQL = "SELECT type FROM issue_types WHERE type_id = ?;";
	private static final String SELECT_PRIORITY_LEVEL_SQL = "SELECT priority FROM priority_level WHERE priority_id= ?";

	public int getStatusID(WorkFlow status) throws PartOfProjectException {
		Connection connection = DBConnection.getConnection();

		int statusID = 0;
		try {
			PreparedStatement statusPS = connection.prepareStatement(SELECT_STATUS_ID);
			statusPS.setString(1, status.toString());
			ResultSet statusRS = statusPS.executeQuery();
			statusRS.next();
			statusID = statusRS.getInt("status_id");
		} catch (SQLException e) {
			throw new PartOfProjectException("There was a problem gitting this id");
		}
		return statusID;

	}

	public int getTypeID(IssueType type) throws PartOfProjectException {
		Connection connection = DBConnection.getConnection();

		int typeID = 0;
		try {
			PreparedStatement typePS = connection.prepareStatement(SELECT_TYPE_ID);
			typePS.setString(1, type.toString());
			ResultSet typeRS = typePS.executeQuery();
			typeRS.next();
			typeID = typeRS.getInt("type_id");
		} catch (SQLException e) {
			throw new PartOfProjectException("There was a problem getting this id");
		}
		return typeID;

	}

	public IssueType getType(int issueId) throws PartOfProjectException {
		Connection connection = DBConnection.getConnection();
		IssueType type = null;
		try {
			PreparedStatement ps = connection.prepareStatement(SELECT_ISSUE_TYPE_SQL);
			ps.setInt(1, issueId);
			ResultSet rs = ps.executeQuery();
			rs.next();
			String issueType = rs.getString("type");
			type = IssueType.getIssueType(issueType);
			return type;
		} catch (SQLException e) {
			throw new PartOfProjectException("There was a problem getting this issue type!");
		}
	}

	public PriorityLevel getPriority(int priorityID) throws PartOfProjectException {
		Connection connection = DBConnection.getConnection();
		PriorityLevel priority = null;
		try {
			PreparedStatement ps = connection.prepareStatement(SELECT_PRIORITY_LEVEL_SQL);
			ps.setInt(1, priorityID);
			ResultSet rs = ps.executeQuery();
			rs.next();
			String priorityString = rs.getString("priority");
			priority = PriorityLevel.valueOf(priorityString);
			return priority;
		} catch (SQLException e) {
			throw new PartOfProjectException("There was a problem getting priority!");
		}
	}

	public int getPriorityID(PriorityLevel priority) throws PartOfProjectException {
		Connection connection = DBConnection.getConnection();

		int priorityID = 0;
		try {
			PreparedStatement priorityPS = connection.prepareStatement(SELECT_PRIORITY_ID);
			priorityPS.setString(1, priority.toString());
			ResultSet priorityRS = priorityPS.executeQuery();
			priorityRS.next();
			priorityID = priorityRS.getInt("priority_id");
		} catch (SQLException e) {
			throw new PartOfProjectException("There was a problem gitting this id");
		}
		return priorityID;

	}

	public WorkFlow getStatus(int statusID) throws PartOfProjectException {
		Connection connection = DBConnection.getConnection();

		WorkFlow status = null;
		try {
			PreparedStatement statusPS = connection.prepareStatement(SELECT_STATUS_SQL);
			statusPS.setInt(1, statusID);
			ResultSet statusRS = statusPS.executeQuery();
			statusRS.next();
			String stat = statusRS.getString("status");
			status = WorkFlow.valueOf(stat);
		} catch (SQLException e) {
			throw new PartOfProjectException("Could not find the searched status");
		}
		return status;
	}

}
