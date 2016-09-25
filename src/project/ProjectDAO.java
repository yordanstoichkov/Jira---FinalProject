package project;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import controller.DBConnection;
import employee.Employee;
import exceptions.ProjectException;
import javafx.util.converter.LocalDateStringConverter;

public class ProjectDAO {
	private static final String INSERT_PROJECT_OF_MANAGER_SQL = "INSERT INTO project_managers VALUES (?, ?);";
	private static final String INSERT_PROJECT_SQL = "INSERT INTO projects VALUES (null,null,null, ?);";
	private static final String SET_RELEASE_DATE_SQL = "UPDATE projects SET release_date= ? WHERE project_id = ?";
	private static final String SET_START_DATE_SQL = "UPDATE projects SET start_date= ? WHERE project_id = ?";

	public int createProject(Project project, Employee employee) throws ProjectException {
		Connection connection = DBConnection.getConnection();
		try {
			connection.setAutoCommit(false);
			PreparedStatement ps = connection.prepareStatement(INSERT_PROJECT_SQL, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, project.getTitle());
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			project.setProjectId(rs.getInt(1));

			PreparedStatement ps2 = connection.prepareStatement(INSERT_PROJECT_OF_MANAGER_SQL);
			ps2.setInt(1, employee.getEmployeeID());
			ps2.setInt(2, project.getProjectId());
			ps2.executeUpdate();
			connection.commit();
			return project.getProjectId();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new ProjectException("You can not make your project right now! Please,try again later!", e1);
			}
			throw new ProjectException("You can not make your project right now! Please,try again later!", e);
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				throw new ProjectException("You can not make your project right now! Please,try again later!", e);
			}
		}
	}

	public void setReleaseDate(Project project, LocalDate releaseDate) throws ProjectException {
		Connection connection = DBConnection.getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement(SET_RELEASE_DATE_SQL);
			Date date = Date.valueOf(releaseDate);
			ps.setDate(1, date);
			ps.setInt(2, project.getProjectId());
			ps.executeUpdate();

		} catch (SQLException e) {
			throw new ProjectException(
					"You can not set release date to your project right now! Please,try again later!");
		}
	}

	public void setStartDate(Project project, LocalDate startDate) throws ProjectException {
		Connection connection = DBConnection.getConnection();
		try {
			Date date = Date.valueOf(startDate);
			PreparedStatement ps = connection.prepareStatement(SET_START_DATE_SQL);
			ps.setDate(1, date);
			ps.setInt(2, project.getProjectId());
			ps.executeUpdate();

		} catch (SQLException e) {
			throw new ProjectException("You can not set start date to your project right now! Please,try again later!");
		}
	}
}
