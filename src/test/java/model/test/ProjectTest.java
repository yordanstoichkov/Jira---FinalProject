package model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import model.employee.Employee;
import model.employee.Employee.Jobs;
import model.employee.EmployeeDAO;
import model.employee.IEmployeeDAO;
import model.exceptions.EmployeeException;
import model.exceptions.ProjectException;
import model.project.Project;
import model.project.ProjectDAO;
import model.project.Sprint;
import model.project.SprintDAO;

public class ProjectTest {
	private IEmployeeDAO daoE = new EmployeeDAO();
	ProjectDAO dao = new ProjectDAO();
	SprintDAO sdao = new SprintDAO();

	@Test
	public void test() throws EmployeeException, ProjectException {
		// Employee pesho = new Employee("Pesho", "Ivanov", Jobs.DEVELOPER,
		// "Pesho11!", "novoto12@abv.bg");
		// int peshoId = daoE.registerUser(pesho);
		// pesho.setEmployeeID(peshoId);
		// Employee pepi = new Employee("dakaatad@mail.bg", "Aq1234");

		// daoE.loginUser(pepi);

		// dao.createProject(project, pesho);
		// Sprint sprint= new Sprint("narisuvai dyga", project);
		// sdao.createSprint(sprint);
		List<String> allProjectsNameOfUser = new ArrayList<String>();

		allProjectsNameOfUser = dao.openYourPage(23);
		Employee geri = new Employee("geri@mail.com", "Geri11");
		geri.setEmployeeID(daoE.loginUser(geri));

		assertNotEquals(0, geri.getEmployeeID());

		// Project project = new Project("na pesho noviq proekt");
		// Project project1 = new Project("proekt");
		// Project project2 = new Project("gotovo");
		// int id = dao.createProject(project, geri);
		// int id1 = dao.createProject(project1, geri);
		// int id2 = dao.createProject(project2, geri);
		//
		// assertNotEquals(0, id);
		// assertNotEquals(0, id1);
		// assertNotEquals(0, id2);
		// allProjectsNameOfUser.addAll(dao.openYourPage(geri.getEmployeeID()));
		//
		// for (String name : allProjectsNameOfUser) {
		// System.out.println(name);
		// }

		List<Project> projects = new EmployeeDAO().giveMyProjects(geri);
		

	}

	@Test
	public void testGetProjects() throws EmployeeException {
		Employee tempEmp = new Employee("Gosho", "Todorov", Jobs.DEVELOPER, "golqmata@abv.bg", "gosheto");

		tempEmp.setEmployeeID(daoE.loginUser(tempEmp));
		System.out.println(tempEmp.getEmployeeID());
		List<Project> projects = daoE.giveMyProjects(tempEmp);
		assertNotEquals(null, projects);
		System.out.println(projects);

	}

}
