package model.project;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import model.controller.DBConnection;
import model.exceptions.ProjectException;

public class SprintDAO {
	private static final String INSERT_SPRINT_SQL = "INSERT INTO sprints VALUES (null, null, null, ?, ?, ?, null);";
	private static final String SELECT_SPRINT_STATUS_SQL = "SELECT status_id FROM statuses WHERE status = ?";
	private static final String SET_START_DATE_SQL = "UPDATE sprints SET start_date= ? WHERE sprint_id = ?";
	private static final String SET_END_DATE_SQL = "UPDATE sprints SET end_date= ? WHERE sprint_id = ?";
	private static final String SET_STATUS_SQL = "UPDATE sprints SET status_id = ? WHERE sprint_id = ?";

	public int getStatusId(WorkFlow status) throws ProjectException {
		Connection connection = DBConnection.getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement(SELECT_SPRINT_STATUS_SQL);
			ps.setString(1, status.toString());
			ResultSet rs = ps.executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			throw new ProjectException("You can not set  sprint status right now! Please,try again later!", e);
		}
	}

	public int createSprint(Sprint sprint) throws ProjectException {
		Connection connection = DBConnection.getConnection();
		try {
			connection.setAutoCommit(false);
			int statusId = getStatusId(sprint.getStatus());
			PreparedStatement ps2 = connection.prepareStatement(INSERT_SPRINT_SQL, Statement.RETURN_GENERATED_KEYS);
			ps2.setInt(1, sprint.getProject().getProjectId());
			ps2.setInt(2, statusId);
			ps2.setString(3, sprint.getTitle());
			ps2.executeUpdate();
			ResultSet rs2 = ps2.getGeneratedKeys();
			rs2.next();
			sprint.setSprintId(rs2.getInt(1));
			connection.commit();
			return sprint.getSprintId();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new ProjectException("You can not make your sprint right now! Please,try again later!", e1);

			}
			throw new ProjectException("You can not make your sprint right now! Please,try again later!", e);
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				throw new ProjectException("You can not make your sprint right now! Please,try again later!", e);
			}
		}
	}
	public void setStartDate(Sprint sprint, LocalDate startDate) throws ProjectException {
		Connection connection = DBConnection.getConnection();
		try {
			Date date = Date.valueOf(startDate);
			PreparedStatement ps = connection.prepareStatement(SET_START_DATE_SQL);
			ps.setDate(1, date);
			ps.setInt(2, sprint.getSprintId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new ProjectException("You can not set start date to your sprint right now! Please,try again later!");

		}
	}

	public void setEndDate(Sprint sprint, LocalDate endDate) throws ProjectException {
		Connection connection = DBConnection.getConnection();
		try {
			Date date = Date.valueOf(endDate);
			PreparedStatement ps = connection.prepareStatement(SET_END_DATE_SQL);
			ps.setDate(1, date);
			ps.setInt(2, sprint.getSprintId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new ProjectException("You can not set end date to your sprint right now! Please,try again later!");

		}
	}

	public void setStatus(Sprint sprint, WorkFlow status) throws ProjectException {
		Connection connection = DBConnection.getConnection();
		try {
			connection.setAutoCommit(false);
			int statusId = getStatusId(status);
			PreparedStatement ps2 = connection.prepareStatement(SET_STATUS_SQL);
			ps2.setInt(1, statusId);
			ps2.setInt(2, sprint.getSprintId());
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new ProjectException("You can not set  sprint status right now! Please,try again later!", e);

			}
			throw new ProjectException("You can not set  sprint status right now! Please,try again later!", e);

		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				throw new ProjectException("You can not set  sprint status right now! Please,try again later!", e);
			}
		}
	}

}
