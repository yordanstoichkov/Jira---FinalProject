package model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import model.employee.Employee;
import model.employee.Employee.Jobs;
import model.employee.IEmployeeDAO;
import model.exceptions.EmployeeException;

public class EmployeeTest {
	@Autowired
	private IEmployeeDAO dao;

	@Test
	public void test() throws EmployeeException {
		Employee emp1 = new Employee("Yordan", "Petrov", Jobs.QA, "dakaatad@mail.bg", "Aq1234");

		int id1 = dao.registerUser(emp1);
		emp1.setEmployeeID(id1);

		assertNotEquals(id1, 0);
		int id = dao.loginUser(emp1);

		assertEquals(id, id1);
		int result = dao.removeUser(emp1);
		assertNotEquals(result, 0);
	}

}
