package model.test;

import java.util.Arrays;

import org.junit.Test;

import com.jira.model.employee.Employee;
import com.jira.model.employee.Employee.Jobs;
import com.jira.model.exceptions.EmployeeException;
import com.jira.model.exceptions.IssueExeption;
import com.jira.model.exceptions.ProjectException;
import com.jira.model.exceptions.SprintException;
import com.jira.model.project.IIssueDAO;
import com.jira.model.project.Issue;
import com.jira.model.project.IssueDAO;
import com.jira.model.project.IssueType;
import com.jira.model.project.PartOfProjectException;
import com.jira.model.project.PriorityLevel;
import com.jira.model.project.Project;
import com.jira.model.project.Sprint;

public class IssueTest {
	private IIssueDAO dao = new IssueDAO();

	@Test
	public void test() throws ProjectException, EmployeeException, IssueExeption, PartOfProjectException, SprintException {
	
		Issue testIssue = new Issue();

		testIssue.setIssueId(dao.createIssue(testIssue));
		testIssue.setDescription("AKO ZARABOTI EKSTRA");
		dao.addDescriptionToIssue(testIssue);

		Sprint testSprint = new Sprint("eha");
		testSprint.setProject(new Project("eha2"));
		testSprint.setSprintId(2);

		dao.addIssueToSprint(testIssue, testSprint);
	}

}
