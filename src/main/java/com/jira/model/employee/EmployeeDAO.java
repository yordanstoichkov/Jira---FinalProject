package com.jira.model.employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.jira.model.connections.DBConnection;
import com.jira.model.employee.Employee.Jobs;
import com.jira.model.exceptions.EmployeeException;
import com.jira.model.exceptions.IsssueExeption;
import com.jira.model.exceptions.ProjectException;
import com.jira.model.project.IIssueDAO;
import com.jira.model.project.IProjectDAO;
import com.jira.model.project.Issue;
import com.jira.model.project.Project;
import com.jira.model.project.ProjectDAO;

@Component
public class EmployeeDAO implements IEmployeeDAO {
	private static final String DELETE_USER_SQL = "DELETE from employees where employee_id = ?;";
	private static final String REGISTER_USER_TO_DB_SQL = "INSERT into employees VALUES(NULL,?,?,?,?,md5(?),?);";
	private static final String LOGIN_USER_SQL = "SELECT * FROM employees WHERE email = ? AND password = md5(?);";
	private static final String JOB_ID_SQL = "SELECT job_id FROM jobs WHERE job_title = ?";
	private static final String GET_EMPLOYEE_ID_SQL = "SELECT employee_id FROM employees WHERE email = ? ";
	private static final String GET_EMPLOYEE_SQL = "SELECT * FROM employees WHERE employee_id = ? ";
	private static final String SELECT_USERS_COUNT = "SELECT count(*) as 'employee_count' FROM employees";
	private static final String SELECT_NUMBER_WITH_THIS_EMAIL_SQL = "SELECT count(*) as 'employee' FROM employees WHERE email = ? ";
	private static final String SELECT_ALL_USER_PROJECTS = "SELECT DISTINCT(p.project_id) " + "FROM projects p "
			+ "JOIN sprints s ON (s.project_id=p.project_id) " + "JOIN issues i ON (i.sprint_id = s.sprint_id) "
			+ "JOIN issues_developers id ON (id.issue_id=i.issue_id) " + "WHERE id.developer_id=?;";
	private static final String SELECT_ALL_PROJECT_MANAGERS = "SELECT manager_id FROM project_managers where project_id= ? ";
	private static final String SELECT_ALL_MANAGER_PROJECTS = "SELECT project_id FROM project_managers where manager_id= ? ";
	private static final String JOB_BY_ID_SQL = "SELECT job_title FROM jobs WHERE job_id = ?";
	private static final String SELECT_DEVELOPERS_OF_ISSUE_SQL = "SELECT developer_id FROM issues_developers WHERE issue_id = ?";
	private static final String SELECT_REVIEWERS_OF_ISSUE_SQL = "SELECT reviewer_id FROM issue_reviewers WHERE issue_id = ?";
	private static final String GET_EMPLOYEE_NAMES = "SELECT first_name, last_name FROM employees";
	private static final String GET_EMPLOYEES_ISSUES = "SELECT DISTINCT(issue_id) FROM issues_developers WHERE developer_id=?";

	@Autowired
	private IProjectDAO projectDAO;
	@Autowired
	private IIssueDAO issueDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.employee.IEmployeeDAO#registerUser(model.employee.Employee)
	 */
	@Override
	public int registerUser(Employee emp) throws EmployeeException {
		if (!isEmployeeValid(emp)) {
			throw new EmployeeException("This user is not valid");
		}
		Connection connection = DBConnection.getConnection();

		int id = 0;
		try {
			connection.setAutoCommit(false);
			PreparedStatement jobPS = connection.prepareStatement(JOB_ID_SQL);
			jobPS.setString(1, emp.getJob().toString());

			ResultSet jobRS = jobPS.executeQuery();
			jobRS.next();
			int jobID = jobRS.getInt("job_id");

			PreparedStatement ps = connection.prepareStatement(REGISTER_USER_TO_DB_SQL,
					Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, emp.getFirstName());
			ps.setString(2, emp.getLastName());
			ps.setInt(3, jobID);
			ps.setString(4, emp.getEmail());
			ps.setString(5, emp.getPassword());
			ps.setString(6, emp.getAvatarPath());
			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			id = rs.getInt(1);

			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new EmployeeException("User cannot be registered now, please try again later.", e);
			}
			throw new EmployeeException("User cannot be registered now, please try again later.", e);
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.getMessage();
			}
		}
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.employee.IEmployeeDAO#loginUser(model.employee.Employee)
	 */
	@Override
	public int loginUser(Employee emp) throws EmployeeException {
		if (!isEmployeeValid(emp)) {
			throw new EmployeeException("This user is not valid");
		}
		Connection connection = DBConnection.getConnection();
		int id = 0;
		try {
			PreparedStatement ps = connection.prepareStatement(LOGIN_USER_SQL);
			ps.setString(1, emp.getEmail());
			ps.setString(2, emp.getPassword());
			ResultSet rs = ps.executeQuery();
			rs.next();
			id = rs.getInt("employee_id");
			String firstName = rs.getString("first_name");
			String lastName = rs.getString("last_name");
			int jobID = rs.getInt("job_id");
			String avatarPath = rs.getString("avatar_path");
			emp.setLastName(lastName);
			emp.setFirstName(firstName);
			emp.setEmployeeID(id);
			emp.setAvatarPath(avatarPath);
			PreparedStatement jobPS = connection.prepareStatement(JOB_BY_ID_SQL);
			jobPS.setInt(1, jobID);

			ResultSet jobRS = jobPS.executeQuery();
			jobRS.next();
			String jobStr = jobRS.getString("job_title");
			Jobs job = Jobs.getJob(jobStr);
			emp.setJob(job);
			if (id == 0) {
				throw new EmployeeException("Wrong password or username");
			}

		} catch (SQLException e) {
			throw new EmployeeException("Wrong username or password.", e);
		}
		return id;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.employee.IEmployeeDAO#removeUser(model.employee.Employee)
	 */
	@Override
	public int removeUser(Employee emp) throws EmployeeException {
		if (!isEmployeeValid(emp)) {
			throw new EmployeeException("This user is not valid");
		}
		Connection connection = DBConnection.getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement(DELETE_USER_SQL);
			ps.setInt(1, emp.getEmployeeID());
			return ps.executeUpdate();

		} catch (SQLException e) {
			throw new EmployeeException("User cannot be deleted now, please try again later.", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.employee.IEmployeeDAO#getEmployeeID(model.employee.Employee)
	 */
	@Override
	public int getEmployeeID(Employee emp) throws EmployeeException {
		if (!isEmployeeValid(emp)) {
			throw new EmployeeException("This user is not valid");
		}
		Connection connection = DBConnection.getConnection();

		int assigneeID = 0;
		try {
			PreparedStatement asigneePS = connection.prepareStatement(GET_EMPLOYEE_ID_SQL);
			asigneePS.setString(1, emp.getEmail());
			ResultSet asigneeRS = asigneePS.executeQuery();

			asigneeRS.next();
			assigneeID = asigneeRS.getInt("employee_id");
		} catch (SQLException e) {
			throw new EmployeeException("There is no such user");
		}
		return assigneeID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.employee.IEmployeeDAO#getUserCount()
	 */
	@Override
	public int getUserCount() throws EmployeeException {
		Connection connection = DBConnection.getConnection();

		int userCount = 0;
		try {
			PreparedStatement usersPS = connection.prepareStatement(SELECT_USERS_COUNT);
			ResultSet users = usersPS.executeQuery();
			users.next();
			userCount = users.getInt("employee_count");

		} catch (SQLException e) {
			throw new EmployeeException("Couldn't get the count", e);
		}
		return userCount;

	}

	private boolean isEmployeeValid(Employee emp) {
		return emp != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see model.employee.IEmployeeDAO#validEmail(java.lang.String)
	 */
	@Override
	public int validEmail(String email) throws EmployeeException {
		Connection connection = DBConnection.getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement(SELECT_NUMBER_WITH_THIS_EMAIL_SQL);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			rs.next();
			return rs.getInt("employee");
		} catch (SQLException e) {
			throw new EmployeeException("Can not check for this email right now! ", e);

		}
	}

	@Override
	public List<Project> giveMyProjects(Employee emp) throws EmployeeException {
		Connection connection = DBConnection.getConnection();
		List<Project> result = new ArrayList<Project>();
		Set<Integer> projectsID = new HashSet<Integer>();
		try {
			PreparedStatement projectsPS = connection.prepareStatement(SELECT_ALL_USER_PROJECTS);
			projectsPS.setInt(1, emp.getEmployeeID());
			ResultSet projectRS = projectsPS.executeQuery();
			while (projectRS.next()) {
				int projectid = projectRS.getInt(1);
				projectsID.add(projectid);
			}
			if (emp.getJob() == Jobs.MANAGER) {
				PreparedStatement projectManPS = connection.prepareStatement(SELECT_ALL_MANAGER_PROJECTS);
				projectManPS.setInt(1, emp.getEmployeeID());
				ResultSet projectManRS = projectManPS.executeQuery();
				while (projectManRS.next()) {
					int projectid = projectManRS.getInt(1);
					projectsID.add(projectid);
				}
			}
			for (Integer projectID : projectsID) {
				result.add(projectDAO.getProject(projectID));
			}

		} catch (SQLException e) {
			throw new EmployeeException("We have problems and can't get your projects", e);
		} catch (ProjectException e) {
			throw new EmployeeException("We have problems and can't get your projects", e);
		}
		return result;
	}

	public List<Integer> getDevelopers(int issueId) throws EmployeeException {
		List<Integer> developersId = new ArrayList<>();
		Connection connection = DBConnection.getConnection();
		PreparedStatement ps;
		try {
			ps = connection.prepareStatement(SELECT_DEVELOPERS_OF_ISSUE_SQL);
			ps.setInt(1, issueId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				developersId.add(rs.getInt(1));
			}

		} catch (SQLException e1) {
			throw new EmployeeException("We have problems and can't get developers now!", e1);

		}
		return developersId;

	}

	public List<Integer> getReviewers(int issueId) throws EmployeeException {
		List<Integer> reviewersId = new ArrayList<>();
		Connection connection = DBConnection.getConnection();
		PreparedStatement ps;
		try {
			ps = connection.prepareStatement(SELECT_REVIEWERS_OF_ISSUE_SQL);
			ps.setInt(1, issueId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				reviewersId.add(rs.getInt(1));
			}

		} catch (SQLException e1) {
			throw new EmployeeException("We have problems and can't get reviewers now!", e1);

		}
		return reviewersId;

	}

	public List<Integer> getManagers(int projectId) throws EmployeeException {
		List<Integer> managersId = new ArrayList<>();
		Connection connection = DBConnection.getConnection();
		PreparedStatement ps;
		try {
			ps = connection.prepareStatement(SELECT_ALL_PROJECT_MANAGERS);
			ps.setInt(1, projectId);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				managersId.add(rs.getInt(1));
			}

		} catch (SQLException e1) {
			throw new EmployeeException("We have problems and can't get managers now!", e1);

		}
		return managersId;

	}

	public Employee getEmployeeById(int employeeId) {
		Connection connection = DBConnection.getConnection();
		Employee employee = null;
		try {
			PreparedStatement ps = connection.prepareStatement(GET_EMPLOYEE_SQL);
			ps.setInt(1, employeeId);
			ResultSet rs = ps.executeQuery();
			rs.next();
			String firstName = rs.getString("first_name");
			String lastName = rs.getString("last_name");
			String email = rs.getString("email");
			String password = rs.getString("password");

			employee = new Employee(firstName, lastName, email, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EmployeeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return employee;

	}

	public List<String> getEmployeesNames() {
		Connection connection = DBConnection.getConnection();
		List<String> names = new ArrayList<String>();
		try {
			PreparedStatement ps = connection.prepareStatement(GET_EMPLOYEE_NAMES);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				names.add(firstName + " " + lastName);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return names;

	}

	public List<Issue> getEmployeesIssues(Employee emp) throws EmployeeException {
		Connection connection = DBConnection.getConnection();
		List<Integer> issuesId = new ArrayList<Integer>();
		List<Issue> issues = new ArrayList<Issue>();
		try {
			PreparedStatement ps = connection.prepareStatement(GET_EMPLOYEES_ISSUES);
			ps.setInt(1, emp.getEmployeeID());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				issuesId.add(rs.getInt("issue_id"));
			}
			for (Integer id : issuesId) {
				issues.add(issueDAO.getIssue(id));
			}

		} catch (SQLException e) {
			throw new EmployeeException("Currently we have a problem getting your issues", e);
		} catch (IsssueExeption e) {
			throw new EmployeeException("Currently we have a problem getting your issues", e);
		}
		return issues;

	}
}
