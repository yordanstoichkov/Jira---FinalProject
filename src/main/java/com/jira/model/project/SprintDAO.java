package com.jira.model.project;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.jira.model.connections.DBConnection;
import com.jira.model.employee.IValidator;
import com.jira.model.employee.Validator;
import com.jira.model.exceptions.IssueException;
import com.jira.model.exceptions.PartOfProjectException;
import com.jira.model.exceptions.SprintException;

@Component
public class SprintDAO implements ISprintDAO {
	private IValidator validator = new Validator();

	@Autowired
	private IIssueDAO issueDAO;
	@Autowired
	private IPartOfProjectDAO partDAO;

	private static final int STATUS_ID_OF_DONE = 4;
	private static final int STATUS_ID_OF_IN_PROGRESS = 2;
	private static final int STATUS_ID_OF_TO_DO = 1;
	private static final String INSERT_SPRINT_SQL = "INSERT INTO sprints VALUES (null, null, null, ?, ?, ?, NULL);";
	private static final String SELECT_SPRINT_STATUS_SQL = "SELECT status_id FROM statuses WHERE status = ?";
	private static final String SELECT_SPRINT_SQL = "SELECT * FROM sprints WHERE sprint_id = ?";
	private static final String SELECT_ISSUES_SQL = "SELECT issue_id " + "FROM issues " + "WHERE sprint_id=?";
	private static final String START_SPRINT_SQL = "UPDATE sprints SET start_date=?, end_date=?, sprint_goal=?, status_id=? WHERE sprint_id=?";
	private static final String UPDATE_SPRINT_STATUS_SQL = "UPDATE sprints SET status_id=? WHERE sprint_id=?";

	// Creating new sprint
	@Override
	public int createSprint(Sprint sprint) throws SprintException {
		if (!validator.objectValidator(sprint)) {
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

	// Getting sprint by sprint id
	@Override
	public Sprint getSprint(int sprintID) throws SprintException {
		if (!validator.positiveNumberValidator(sprintID)) {
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
		} catch (PartOfProjectException e) {
			throw new SprintException("Something went wrong can't get your sprint", e);
		} catch (IssueException e) {
			throw new SprintException("Something went wrong can't get your sprint", e);
		}
		return result;
	}

	// Setting start date, end date, goal,status and id of sprint
	@Override
	public void startSprint(Sprint sprint) throws SprintException {
		if (!validator.objectValidator(sprint)) {
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
			throw new SprintException("Something went wrong and can't start your sprint now.", e);
		} catch (PartOfProjectException e) {
			throw new SprintException("Something went wrong and can't start your sprint now.", e);
		}
	}

	// Changing sprint status by sprint id
	@Override
	public void updateSprintStatus(int sprintId) throws SprintException {
		if (!validator.positiveNumberValidator(sprintId)) {
			throw new SprintException("Invalid issue given");
		}
		Connection connection = DBConnection.getConnection();
		int newStatusId = 1;
		try {
			connection.setAutoCommit(false);
			PreparedStatement ps = connection.prepareStatement(SELECT_SPRINT_SQL);
			ps.setInt(1, sprintId);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int statusId = rs.getInt("status_id");
			PreparedStatement ps2 = connection.prepareStatement(UPDATE_SPRINT_STATUS_SQL);
			if (statusId == STATUS_ID_OF_TO_DO) {
				ps2.setInt(1, STATUS_ID_OF_IN_PROGRESS);
				newStatusId = STATUS_ID_OF_IN_PROGRESS;
			}
			if (statusId == STATUS_ID_OF_IN_PROGRESS) {
				ps2.setInt(1, STATUS_ID_OF_DONE);
				newStatusId = STATUS_ID_OF_DONE;
			}

			ps2.setInt(2, sprintId);
			ps2.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new SprintException("You can not change the status of issue right now! Try again later!", e1);
			}
			throw new SprintException("You can not change the status of issue right now! Try again later!", e);
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				throw new SprintException("You can not change the status of issue right now! Try again later!", e);

			}
		}
	}
}
