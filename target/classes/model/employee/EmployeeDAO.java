package model.employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.dbConnection.DBConnection;
import model.exceptions.EmployeeException;

public class EmployeeDAO {
	private static final String DELETE_USER_SQL = "DELETE from employees where employee_id = ?;";
	private static final String REGISTER_USER_TO_DB_SQL = "INSERT into employees VALUES(NULL,?,?,?,?,md5(?));";
	private static final String LOGIN_USER_SQL = "SELECT employee_id , first_name FROM employees WHERE email = ? AND password = md5(?);";
	private static final String JOB_ID_SQL = "SELECT job_id FROM jobs WHERE job_title = ?";
	private static final String GET_EMPLOYEE_ID_SQL = "SELECT employee_id FROM employees WHERE email = ? ";
	private static final String SELECT_USERS_COUNT = "SELECT count(*) as 'employee_count' FROM employees";

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
			throw new EmployeeException("User cannot be registered now, please try again later.", e);
		}
		return id;

	}

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
}
