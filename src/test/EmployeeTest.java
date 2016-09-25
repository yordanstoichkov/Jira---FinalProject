package test;

import static org.junit.Assert.*;

import org.junit.Test;

import employee.Employee;
import employee.EmployeeDAO;
import exceptions.EmployeeException;

public class EmployeeTest {
	private EmployeeDAO dao = new EmployeeDAO();

	@Test
	public void test() throws EmployeeException {
		Employee emp1 = new Employee("Yordan", "Petrov", "dakata12121@mail.bg", "Aq1234", "dakata");

		int id1 = dao.registerUser(emp1);
		emp1.setEmployeeID(id1);

		assertNotEquals(id1, 0);
		int id = dao.loginUser(emp1);
		//
		assertEquals(id, id1);
		int result = dao.removeUser(emp1);
		assertNotEquals(result, 0);
	}

}
