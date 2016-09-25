package employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import controller.DBConnection;
import exceptions.EmployeeException;

public class EmployeeDAO {
	private static final String DELETE_USER_SQL = "DELETE from employees where employee_id = ?;";
	private static final String REGISTER_USER_TO_DB_SQL = "INSERT into employees VALUES(NULL,?,?,NULL,?,md5(?));";
	private static final String LOGIN_USER_SQL = "SELECT employee_id FROM employees WHERE email = ? AND password = md5(?);";

	public int registerUser(Employee emp) throws EmployeeException {
		Connection connection = DBConnection.getConnection();

		int id = 0;
		try {
			PreparedStatement ps = connection.prepareStatement(REGISTER_USER_TO_DB_SQL, Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, emp.getFirst_name());
			ps.setString(2, emp.getLast_name());
			ps.setString(3, emp.getEmail());
			ps.setString(4, emp.getPassword());
			ps.executeUpdate();
			
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			id = rs.getInt(1);
			
		} catch (SQLException e) {
			throw new EmployeeException("User cannot be registered now, please try again later.", e);
		}
		return id;
	}

	public int loginUser(Employee emp) throws EmployeeException {
		Connection connection = DBConnection.getConnection();
		int id = 0;
		try {
			PreparedStatement ps = connection.prepareStatement(LOGIN_USER_SQL);
			ps.setString(1, emp.getEmail());
			ps.setString(2, emp.getPassword());
			ResultSet rs = ps.executeQuery();
			rs.next();
			id = rs.getInt("employee_id");

			if (id == 0) {
				throw new EmployeeException("Wrong password or username");
			}

		} catch (SQLException e) {
			throw new EmployeeException("User cannot be registered now, please try again later.", e);
		}
		return id;

	}

	public int removeUser(Employee emp) throws EmployeeException {
		Connection connection = DBConnection.getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement(DELETE_USER_SQL);
			ps.setInt(1, emp.getEmployeeID());
			return ps.executeUpdate();

		} catch (SQLException e) {
			throw new EmployeeException("User cannot be deleted now, please try again later.", e);
		}
	}

}
