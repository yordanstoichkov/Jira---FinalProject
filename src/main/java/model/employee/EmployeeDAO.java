package model.employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import model.dbConnection.DBConnection;
import model.exceptions.EmployeeException;
import model.exceptions.ProjectException;
import model.project.Project;
import model.project.ProjectDAO;

@Component
public class EmployeeDAO implements IEmployeeDAO {
	private static final String DELETE_USER_SQL = "DELETE from employees where employee_id = ?;";
	private static final String REGISTER_USER_TO_DB_SQL = "INSERT into employees VALUES(NULL,?,?,?,?,md5(?));";
	private static final String LOGIN_USER_SQL = "SELECT employee_id , first_name FROM employees WHERE email = ? AND password = md5(?);";
	private static final String JOB_ID_SQL = "SELECT job_id FROM jobs WHERE job_title = ?";
	private static final String GET_EMPLOYEE_ID_SQL = "SELECT employee_id FROM employees WHERE email = ? ";
	private static final String SELECT_USERS_COUNT = "SELECT count(*) as 'employee_count' FROM employees";
	private static final String SELECT_NUMBER_WITH_THIS_EMAIL_SQL = "SELECT count(*) as 'employee' FROM employees WHERE email = ? ";
	private static final String SELECT_ALL_USER_PROJECTS = "SELECT DISTINCT(p.project_id) " + "FROM projects p "
			+ "JOIN sprints s ON (s.project_id=p.project_id) " + "JOIN issues i ON (i.sprint_id = s.sprint_id) "
			+ "JOIN issues_developers id ON (id.issue_id=i.issue_id) " + "WHERE id.developer_id=?;";

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
			emp.setFirstName(firstName);
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
		try {
			PreparedStatement projectsPS = connection.prepareStatement(SELECT_ALL_USER_PROJECTS);
			projectsPS.setInt(1, emp.getEmployeeID());
			ResultSet projectRS = projectsPS.executeQuery();
			while (projectRS.next()) {
				int projectid = projectRS.getInt(1);
				result.add(new ProjectDAO().getProject(projectid));
			}

		} catch (SQLException e) {
			throw new EmployeeException("We have problems and can't get your projects");
		} catch (ProjectException e) {
			throw new EmployeeException("We have problems and can't get your projects");
		}
		return result;
	}
}
