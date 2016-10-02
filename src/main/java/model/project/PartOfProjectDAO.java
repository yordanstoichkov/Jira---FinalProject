package model.project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import model.dbConnection.DBConnection;

@Component
public class PartOfProjectDAO {
	private static final String SELECT_STATUS_ID = "SELECT status_id FROM statuses WHERE status = ?;";
	private static final String SELECT_TYPE_ID = "SELECT type_id FROM issue_types WHERE type = ?;";
	private static final String SELECT_PRIORITY_ID = "SELECT priority_id FROM priority_level WHERE priority = ?;";
	private static final String SELECT_STATUS_SQL = "SELECT * FROM statuses WHERE status_id = ?";

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
			throw new PartOfProjectException("There was a problem gitting this id");
		}
		return typeID;

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

	public WorkFlow getStatusID(int statusID) throws PartOfProjectException {
		Connection connection = DBConnection.getConnection();

		WorkFlow status = null;
		try {
			PreparedStatement statusPS = connection.prepareStatement(SELECT_STATUS_SQL);
			statusPS.setInt(1, statusID);
			ResultSet statusRS = statusPS.executeQuery();
			statusRS.next();
			String stat = statusRS.getString("status");
			if (stat.equals(WorkFlow.DONE)) {
				status = WorkFlow.DONE;
			}
			if (stat.equals(WorkFlow.TO_DO)) {
				status = WorkFlow.TO_DO;
			}
			if (stat.equals(WorkFlow.IN_PROGRESS)) {
				status = WorkFlow.IN_PROGRESS;
			}
			if (stat.equals(WorkFlow.CODE_REVIEW)) {
				status = WorkFlow.CODE_REVIEW;
			}
		} catch (SQLException e) {
			throw new PartOfProjectException("Could not find the searched status");
		}
		return status;
	}

}
