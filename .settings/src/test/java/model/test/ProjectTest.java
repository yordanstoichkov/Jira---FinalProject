package model.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.amazonaws.services.simpleworkflow.flow.annotations.Workflow;
import com.jira.model.employee.Employee;
import com.jira.model.employee.EmployeeDAO;
import com.jira.model.employee.IEmployeeDAO;
import com.jira.model.employee.Employee.Jobs;
import com.jira.model.exceptions.EmployeeException;
import com.jira.model.exceptions.ProjectException;
import com.jira.model.project.IProjectDAO;
import com.jira.model.project.ISprintDAO;
import com.jira.model.project.Project;
import com.jira.model.project.ProjectDAO;
import com.jira.model.project.Sprint;
import com.jira.model.project.SprintDAO;
import com.jira.model.project.WorkFlow;

public class ProjectTest {
	private IEmployeeDAO daoE = new EmployeeDAO();
	IProjectDAO dao = new ProjectDAO();
	ISprintDAO sdao = new SprintDAO();

	@Test
	public void test() throws EmployeeException, ProjectException {
		System.out.println(WorkFlow.TO_DO);
		// Employee pesho = new Employee("Pesho", "Ivanov", Jobs.DEVELOPER,
		// "Pesho11!", "novoto12@abv.bg");
		// int peshoId = daoE.registerUser(pesho);
		// pesho.setEmployeeID(peshoId);
		// Employee pepi = new Employee("dakaatad@mail.bg", "Aq1234");

		// daoE.loginUser(pepi);

		// dao.createProject(project, pesho);
		// Sprint sprint= new Sprint("narisuvai dyga", project);
		// sdao.createSprint(sprint);

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
