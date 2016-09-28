package project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import controller.DBConnection;
import exceptions.ProjectException;

public class SprintDAO {
	private static final String INSERT_SPRINT_SQL = "INSERT INTO sprints VALUES (null, null, null, ?, ?, ?, NULL);";
	private static final String SELECT_SPRINT_STATUS_SQL = "SELECT status_id FROM statuses WHERE status = ?";

	public int createSprint(Sprint sprint) throws ProjectException {
		Connection connection = DBConnection.getConnection();
		try {
			connection.setAutoCommit(false);
			PreparedStatement ps = connection.prepareStatement(SELECT_SPRINT_STATUS_SQL);
			ps.setString(1, sprint.getStatus().toString());
			ResultSet rs = ps.executeQuery();
			rs.next();
			int statusId = rs.getInt(1);
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
}
