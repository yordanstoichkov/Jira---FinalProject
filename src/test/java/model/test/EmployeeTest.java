package model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jira.model.employee.Employee;
import com.jira.model.employee.IEmployeeDAO;
import com.jira.model.employee.Employee.Jobs;
import com.jira.model.employee.EmployeeDAO;
import com.jira.model.exceptions.EmployeeException;
import com.jira.model.project.WorkFlow;

public class EmployeeTest {
	private IEmployeeDAO dao = new EmployeeDAO();

	@Test
	public void test() throws EmployeeException {
		Employee emp1 = new Employee("Yordan", "Petrov", Jobs.QA, "dakaatad@mail.bg", "Aq1234");
		System.out.println(WorkFlow.TO_DO);
		int id1 = dao.registerUser(emp1);
		emp1.setEmployeeID(id1);

		assertNotEquals(id1, 0);
		int id = dao.loginUser(emp1);

		assertEquals(id, id1);
		int result = dao.removeUser(emp1);
		assertNotEquals(result, 0);
	}

}
