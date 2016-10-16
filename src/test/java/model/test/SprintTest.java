package model.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jira.model.employee.Employee;
import com.jira.model.exceptions.EmployeeException;
import com.jira.model.exceptions.ProjectException;
import com.jira.model.exceptions.SprintException;
import com.jira.model.project.IProjectDAO;
import com.jira.model.project.ISprintDAO;
import com.jira.model.project.Project;
import com.jira.model.project.ProjectDAO;
import com.jira.model.project.Sprint;
import com.jira.model.project.SprintDAO;

public class SprintTest {
	private ISprintDAO sprintDAO = new SprintDAO();
	private IProjectDAO projectDAO = new ProjectDAO();

	@Test
	public void test() throws SprintException, ProjectException, EmployeeException {
		Project project = new Project("Jira");
		Employee emp = new Employee("abv@abv.bg", "abc123");
		emp.setEmployeeID(63);
		int projectId = projectDAO.createProject(project, emp);

		assertNotEquals(0, projectId);

		Sprint sprint = new Sprint("OOP Hierarchy");
		sprint.setProject(project);
		int sprintId = sprintDAO.createSprint(sprint);

		assertNotEquals(0, sprintId);
	}

}
