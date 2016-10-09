package com.jira.model.project;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jira.model.dbConnection.DBConnection;
import com.jira.model.employee.Employee;
import com.jira.model.exceptions.ProjectException;
import com.jira.model.exceptions.SprintException;

@Component
public class ProjectDAO implements IProjectDAO {
	private static final String INSERT_PROJECT_OF_MANAGER_SQL = "INSERT INTO project_managers VALUES (?, ?);";
	private static final String INSERT_PROJECT_SQL = "INSERT INTO projects VALUES (null,null,null, ?);";
	private static final String SET_RELEASE_DATE_SQL = "UPDATE projects SET release_date= ? WHERE project_id = ?";
	private static final String SET_START_DATE_SQL = "UPDATE projects SET start_date= ? WHERE project_id = ?";
	private static final String SELECT_PROJECT_COUNT = "SELECT count(*) as 'project_count' FROM projects";
	private static final String SELECT_PROJECTS_ID_SQL = "SELECT project_id FROM project_managers where manager_id= ? ";
	private static final String SELECT_PROJECTS_NAME_SQL = "SELECT title FROM projects where project_id = ? ";
	private static final String SELECT_PROJECT_SQL = "SELECT * FROM projects WHERE project_id = ?";
	private static final String SELECT_SPRINTS_SQL = "SELECT s.sprint_id " + "FROM sprints s "
			+ "JOIN projects p ON (p.project_id=s.project_id) " + "WHERE p.project_id=?;";
	@Autowired
	private ISprintDAO sprintDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jira.model.project.IProjectDAO#createProject(com.jira.model.project.
	 * Project, com.jira.model.employee.Employee)
	 */
	@Override
	public int createProject(Project project, Employee employee) throws ProjectException {
		if (project == null || employee == null) {
			throw new ProjectException("You entered invalid parameters");
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jira.model.project.IProjectDAO#setReleaseDate(com.jira.model.project.
	 * Project, java.time.LocalDate)
	 */
	@Override
	public void setReleaseDate(Project project, LocalDate releaseDate) throws ProjectException {
		if (project == null) {
			throw new ProjectException("This is illegal project");
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
					"You can not set release date to your project right now! Please,try again later!");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jira.model.project.IProjectDAO#setStartDate(com.jira.model.project.
	 * Project, java.time.LocalDate)
	 */
	@Override
	public void setStartDate(Project project, LocalDate startDate) throws ProjectException {
		if (project == null) {
			throw new ProjectException("This is illegal project");
		}
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jira.model.project.IProjectDAO#getProjectCount()
	 */
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
			throw new ProjectException("there was a problem getting the number");
		}
		return projectCount;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jira.model.project.IProjectDAO#getProject(int)
	 */
	@Override
	public Project getProject(int projectid) throws ProjectException {
		if (projectid <= 0) {
			throw new ProjectException("This is illegal project");
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
			throw new ProjectException("Something went wrong can't get your project", e);
		} catch (SprintException e) {
			throw new ProjectException("Something went wrong can't get your project", e);
		}
		return result;
	}

}
