package model.test;

import java.util.Arrays;

import org.junit.Test;

import model.employee.Employee;
import model.employee.Employee.Jobs;
import model.exceptions.EmployeeException;
import model.exceptions.IsssueExeption;
import model.exceptions.ProjectException;
import model.exceptions.SprintException;
import model.project.Issue;
import model.project.IssueDAO;
import model.project.IssueType;
import model.project.PartOfProjectException;
import model.project.PriorityLevel;
import model.project.Project;
import model.project.Sprint;

public class IssueTest {
	private IssueDAO dao = new IssueDAO();

	@Test
	public void test() throws ProjectException, EmployeeException, IsssueExeption, PartOfProjectException, SprintException {
	
		Issue testIssue = new Issue("Opalq", PriorityLevel.MEDIUM, IssueType.TASK, Arrays.asList(72));

		testIssue.setIssueId(dao.createIssue(testIssue));
		testIssue.setDescription("AKO ZARABOTI EKSTRA");
		dao.addDescriptionToIssue(testIssue);

		Sprint testSprint = new Sprint("eha");
		testSprint.setProject(new Project("eha2"));
		testSprint.setSprintId(2);

		dao.addIssueToSprint(testIssue, testSprint);
	}

}
