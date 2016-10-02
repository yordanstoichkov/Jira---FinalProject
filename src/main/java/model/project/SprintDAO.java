package model.project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Component;

import model.dbConnection.DBConnection;
import model.exceptions.IsssueExeption;
import model.exceptions.ProjectException;
import model.exceptions.SprintException;

@Component
public class SprintDAO {
	private static final String INSERT_SPRINT_SQL = "INSERT INTO sprints VALUES (null, null, null, ?, ?, ?, NULL);";
	private static final String SELECT_SPRINT_STATUS_SQL = "SELECT status_id FROM statuses WHERE status = ?";
	private static final String SELECT_SPRINT_SQL = "SELECT * FROM sprints WHERE sprint_id = ?";
	private static final String SELECT_ISSUES_SQL = "SELECT issue_id " + "FROM issues " + "WHERE sprint_id=?";

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

	public Sprint getSprint(int sprintID) throws SprintException {
		Connection connection = DBConnection.getConnection();
		Sprint result = null;
		try {
			PreparedStatement sprintPS = connection.prepareStatement(SELECT_SPRINT_SQL);
			sprintPS.setInt(1, sprintID);
			ResultSet sprintRS = sprintPS.executeQuery();
			sprintRS.next();
			String title = sprintRS.getString("title");
			int statusID = sprintRS.getInt("status_id");
			WorkFlow status = new PartOfProjectDAO().getStatusID(statusID);
			result = new Sprint(title);
			result.setSprintId(sprintID);
			result.setStatus(status);
			PreparedStatement issuePS = connection.prepareStatement(SELECT_ISSUES_SQL);
			issuePS.setInt(1, sprintID);
			ResultSet issuesRS = issuePS.executeQuery();
			while (issuesRS.next()) {
				int issueID = issuesRS.getInt(1);
				result.addIssue(new IssueDAO().getIssue(issueID));
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
}
