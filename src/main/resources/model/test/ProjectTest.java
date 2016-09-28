package model.test;

import org.junit.Test;

import model.employee.Employee;
import model.employee.Employee.Jobs;
import model.employee.EmployeeDAO;
import model.exceptions.EmployeeException;
import model.exceptions.ProjectException;
import model.project.Project;
import model.project.ProjectDAO;
import model.project.Sprint;
import model.project.SprintDAO;

public class ProjectTest {
	private EmployeeDAO daoE = new EmployeeDAO();
	ProjectDAO dao = new ProjectDAO();
	SprintDAO sdao= new SprintDAO();
	@Test
	public void test() throws EmployeeException, ProjectException {
		Employee pesho = new Employee("Pesho", "Ivanov", "novoto12@abv.bg", "Pesho11!", Jobs.DEVELOPER);
		int peshoId = daoE.registerUser(pesho);
		pesho.setEmployeeID(peshoId);

		Project project = new Project("na pesho noviq proekt");
		dao.createProject(project, pesho);
		Sprint sprint= new Sprint("narisuvai dyga", project);
		sdao.createSprint(sprint);
		


	}

}
