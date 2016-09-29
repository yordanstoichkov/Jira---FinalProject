package model.test;


import static org.junit.Assert.*;

import org.junit.Test;

import model.employee.Employee;
import model.employee.Employee.Jobs;
import model.employee.EmployeeDAO;
import model.exceptions.EmployeeException;

public class EmployeeTest {
	private EmployeeDAO dao = new EmployeeDAO();

	@Test
	public void test() throws EmployeeException {
		Employee emp1 = new Employee("Yordan", "Petrov", Jobs.QA, "danich@abv.bg", "Abc123$");

		int id1 = dao.registerUser(emp1);
		emp1.setEmployeeID(id1);

		

//		int result = dao.removeUser(emp1);
//		assertNotEquals(result, 0);
	}

}
