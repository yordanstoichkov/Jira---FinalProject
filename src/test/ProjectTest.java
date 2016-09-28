package test;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import employee.Employee;
import employee.Employee.Jobs;
import employee.EmployeeDAO;
import exceptions.EmployeeException;
import exceptions.ProjectException;
import project.Project;
import project.ProjectDAO;
import project.Sprint;
import project.SprintDAO;

public class ProjectTest {
	private EmployeeDAO daoE = new EmployeeDAO();
	ProjectDAO dao = new ProjectDAO();
	SprintDAO sdao= new SprintDAO();
	@Test
	public void test() throws EmployeeException, ProjectException {
		Employee pesho = new Employee("Pesho", "Ivanov", "novo12@abv.bg", "Pesho11!", "pesho", Jobs.DEVELOPER);
		int peshoId = daoE.registerUser(pesho);
		pesho.setEmployeeID(peshoId);

		Project project = new Project("na pesho noviq proekt");
		dao.createProject(project, pesho);
		Sprint sprint= new Sprint("narisuvai dyga", project);
		sdao.createSprint(sprint);
		


	}

}
