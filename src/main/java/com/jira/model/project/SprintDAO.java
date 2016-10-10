package com.jira.model.project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jira.model.dbConnection.DBConnection;
import com.jira.model.exceptions.IsssueExeption;
import com.jira.model.exceptions.ProjectException;
import com.jira.model.exceptions.SprintException;

@Component
public class SprintDAO implements ISprintDAO {
	private static final String INSERT_SPRINT_SQL = "INSERT INTO sprints VALUES (null, null, null, ?, ?, ?, NULL);";
	private static final String SELECT_SPRINT_STATUS_SQL = "SELECT status_id FROM statuses WHERE status = ?";
	private static final String SELECT_SPRINT_SQL = "SELECT * FROM sprints WHERE sprint_id = ?";
	private static final String SELECT_ISSUES_SQL = "SELECT issue_id " + "FROM issues " + "WHERE sprint_id=?";

	@Autowired
	private IIssueDAO issueDAO;
	@Autowired
	private IPartOfProjectDAO partDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jira.model.project.ISprintDAO#createSprint(com.jira.model.project.
	 * Sprint)
	 */
	@Override
	public int createSprint(Sprint sprint) throws SprintException {
		if (sprint == null) {
			throw new SprintException("Invalid sprint entered");
		}
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
				throw new SprintException("You can not make your sprint right now! Please,try again later!", e1);

			}
			throw new SprintException("You can not make your sprint right now! Please,try again later!", e);
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				throw new SprintException("You can not make your sprint right now! Please,try again later!", e);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jira.model.project.ISprintDAO#getSprint(int)
	 */
	@Override
	public Sprint getSprint(int sprintID) throws SprintException {
		if (sprintID <= 0) {
			throw new SprintException("Invalid sprint id entered");
		}
		Connection connection = DBConnection.getConnection();
		Sprint result = null;
		try {
			PreparedStatement sprintPS = connection.prepareStatement(SELECT_SPRINT_SQL);
			sprintPS.setInt(1, sprintID);
			ResultSet sprintRS = sprintPS.executeQuery();
			sprintRS.next();
			String title = sprintRS.getString("title");
			int statusID = sprintRS.getInt("status_id");
			WorkFlow status = partDAO.getStatus(statusID);
			result = new Sprint(title);
			result.setSprintId(sprintID);
			result.setStatus(status);
			PreparedStatement issuePS = connection.prepareStatement(SELECT_ISSUES_SQL);
			issuePS.setInt(1, sprintID);
			ResultSet issuesRS = issuePS.executeQuery();
			while (issuesRS.next()) {
				int issueID = issuesRS.getInt(1);
				result.addIssue(issueDAO.getIssue(issueID));
			}
		} catch (SQLException e) {
			throw new SprintException("Something went wrong can't get your sprint", e);
		} catch (ProjectException e) {
			throw new SprintException("Something went wrong can't get your sprint", e);
		} catch (PartOfProjectException e) {
			throw new SprintException("Something went wrong can't get your sprint", e);
		} catch (IsssueExeption e) {
			throw new SprintException("Something went wrong can't get your sprint", e);
		}
		return result;
	}
	public void startSprint(Sprint sprint) throws SprintException {
		if (sprint == null) {
			throw new SprintException("Invalid sprint entered");
		}

		Connection connection = DBConnection.getConnection();
		try {
			int statusId = partDAO.getStatusID(sprint.getStatus());
			PreparedStatement sprintStPS = connection.prepareStatement(START_SPRINT_SQL);
			sprintStPS.setDate(1, Date.valueOf(sprint.getStartDate()));
			sprintStPS.setDate(2, Date.valueOf(sprint.getEndDate()));
			sprintStPS.setString(3, sprint.getSprintGoal());
			sprintStPS.setInt(4, statusId);
			sprintStPS.setInt(5, sprint.getSprintId());
			sprintStPS.executeUpdate();

		} catch (SQLException e) {
			throw new SprintException("Something went wrong can't start your sprint", e);
		} catch (PartOfProjectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
