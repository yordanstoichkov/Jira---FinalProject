package employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import controller.DBConnection;
import exceptions.EmployeeException;

public class EmployeeDAO {
	private static final String DELETE_USER = "DELETE from users where idUsers = ?;";
	private static final String REGISTER_USER_TO_DB = "INSERT into users VALUES(NULL,?,?,?,?,md5(?))";
	private static final String LOGIN_USER = "SELECT idUsers, password FROM users WHERE user_name = ?;";

	public int registerUser(Employee emp) throws EmployeeException {
		Connection connection = DBConnection.getConnection();

		int id = 0;
		try {
			PreparedStatement ps = connection.prepareStatement(REGISTER_USER_TO_DB, Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, emp.getFirst_name());
			ps.setString(2, emp.getLast_name());
			ps.setString(4, emp.getEmail());
			ps.setString(5, emp.getPassword());
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			id = rs.getInt(1);
			
			emp.setEmployeeID(id);
		} catch (SQLException e) {
			throw new EmployeeException("User cannot be registered now, please try again later.", e);
		}
		return id;
	}

	public int loginUser(Employee emp) throws EmployeeException {
		Connection connection = DBConnection.getInstance().getConnection();
		int id = 0;
		try {
			PreparedStatement ps = connection.prepareStatement(LOGIN_USER);
			ps.setString(1, emp.getUserName());
			ResultSet rs = ps.executeQuery();
			rs.next();
			id = rs.getInt("idUsers");
			String pass = rs.getString("password");
			if (!pass.equals(emp.getPassword())) {
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
			PreparedStatement ps = connection.prepareStatement(DELETE_USER);
			ps.setInt(1, emp.getId());
			return ps.executeUpdate();

		} catch (SQLException e) {
			throw new EmployeeException("User cannot be deleted now, please try again later.", e);
		}
	}

}
