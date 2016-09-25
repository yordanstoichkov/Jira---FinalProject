package test;

import static org.junit.Assert.*;

import org.junit.Test;

import employee.Employee;
import exceptions.EmployeeException;
import exceptions.ProjectException;
import project.Project;
import project.ProjectDAO;

public class ProjectTest {
	ProjectDAO dao= new ProjectDAO();

	@Test
	public void test() throws EmployeeException, ProjectException {
		Employee pesho= new Employee("Pesho", "Ivanov", "pesho@abv.bg", "Pesho11!", "pesho");
		Project project= new Project("na pesho proekta"); 
		int id=dao.createProject(project, pesho);
		assertNotEquals(0, id);
	}

}
