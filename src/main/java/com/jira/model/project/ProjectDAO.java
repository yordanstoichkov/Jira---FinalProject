package com.jira.model.project;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.jira.model.connections.DBConnection;
import com.jira.model.employee.Employee;
import com.jira.model.employee.IValidator;
import com.jira.model.employee.Validator;
import com.jira.model.exceptions.ProjectException;
import com.jira.model.exceptions.SprintException;

@Component
public class ProjectDAO implements IProjectDAO {
	private IValidator validator = new Validator();

	@Autowired
	private ISprintDAO sprintDAO;

	private static final String DELETE_PROJECT_SQL = "DELETE FROM projects WHERE project_id=?";
	private static final String INSERT_PROJECT_OF_MANAGER_SQL = "INSERT INTO project_managers VALUES (?, ?);";
	private static final String INSERT_PROJECT_SQL = "INSERT INTO projects VALUES (null,null,null, ?);";
	private static final String SET_RELEASE_DATE_SQL = "UPDATE projects SET release_date= ? WHERE project_id = ?";
	private static final String SET_START_DATE_SQL = "UPDATE projects SET start_date= ? WHERE project_id = ?";
	private static final String SELECT_PROJECT_COUNT = "SELECT count(*) as 'project_count' FROM projects";
	private static final String SELECT_PROJECT_SQL = "SELECT * FROM projects WHERE project_id = ?";
	private static final String SELECT_SPRINTS_SQL = "SELECT s.sprint_id " + "FROM sprints s "
			+ "JOIN projects p ON (p.project_id=s.project_id) " + "WHERE p.project_id=?;";

	// Creating new project
	@Override
	public int createProject(Project project, Employee employee) throws ProjectException {
		if ((!validator.objectValidator(project)) || (!validator.objectValidator(employee))) {
			throw new ProjectException("You entered invalid project or employee");
		}
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

	// Deleting project
	@Override
	public int deleteProject(Project project) throws ProjectException {
		if (!validator.objectValidator(project)) {
			throw new ProjectException("This is invalid project");
		}
		Connection connection = DBConnection.getConnection();
		int result = 0;
		try {
			PreparedStatement projectPS = connection.prepareStatement(DELETE_PROJECT_SQL,
					Statement.RETURN_GENERATED_KEYS);
			projectPS.setInt(1, project.getProjectId());
			projectPS.executeUpdate();
			ResultSet projectRS = projectPS.getGeneratedKeys();
			projectRS.next();
			result = projectRS.getInt(1);
		} catch (SQLException e) {
			throw new ProjectException("Something went wrong this project cannot be deleted", e);
		}
		return result;
	}

	// Getting project by project id
	@Override
	public Project getProject(int projectid) throws ProjectException {
		if (!validator.positiveNumberValidator(projectid)) {
			throw new ProjectException("This is invalid project id");
		}
		Connection connection = DBConnection.getConnection();
		Project result = null;
		try {
			PreparedStatement projectPS = connection.prepareStatement(SELECT_PROJECT_SQL);
			projectPS.setInt(1, projectid);
			ResultSet projectRS = projectPS.executeQuery();
			projectRS.next();
			result = new Project(projectRS.getString("title"));
			result.setProjectId(projectid);
			PreparedStatement sprintsPS = connection.prepareStatement(SELECT_SPRINTS_SQL);
			sprintsPS.setInt(1, projectid);
			ResultSet sprintsRS = sprintsPS.executeQuery();
			while (sprintsRS.next()) {
				int sprintID = sprintsRS.getInt(1);
				result.addSprint(sprintDAO.getSprint(sprintID));
			}
		} catch (SQLException e) {
			throw new ProjectException("Something went wrong. Can't get your project", e);
		} catch (SprintException e) {
			throw new ProjectException("Something went wrong. Can't get your project", e);
		}
		return result;
	}

	// Setting start date of project
	@Override
	public void setStartDate(Project project, LocalDate startDate) throws ProjectException {
		if ((!validator.objectValidator(project)) || (!validator.objectValidator(startDate))) {
			throw new ProjectException("This is invalid project or date");
		}
		Connection connection = DBConnection.getConnection();
		try {
			Date date = Date.valueOf(startDate);
			PreparedStatement ps = connection.prepareStatement(SET_START_DATE_SQL);
			ps.setDate(1, date);
			ps.setInt(2, project.getProjectId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new ProjectException("You can not set start date to your project right now! Please,try again later!",
					e);
		}
	}

	// Setting release date of project
	@Override
	public void setReleaseDate(Project project, LocalDate releaseDate) throws ProjectException {
		if ((!validator.objectValidator(project)) || (!validator.objectValidator(releaseDate))) {
			throw new ProjectException("This is invalid project or date");
		}
		Connection connection = DBConnection.getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement(SET_RELEASE_DATE_SQL);
			Date date = Date.valueOf(releaseDate);
			ps.setDate(1, date);
			ps.setInt(2, project.getProjectId());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new ProjectException(
					"You can not set release date to your project right now! Please,try again later!", e);
		}
	}

	// Getting IdeaTracker's projects count
	@Override
	public int getProjectCount() throws ProjectException {
		Connection connection = DBConnection.getConnection();
		int projectCount = 0;
		try {
			PreparedStatement projectPS = connection.prepareStatement(SELECT_PROJECT_COUNT);
			ResultSet result = projectPS.executeQuery();
			result.next();
			projectCount = result.getInt("project_count");
		} catch (SQLException e) {
			throw new ProjectException("there was a problem getting the number", e);
		}
		return projectCount;
	}
}
