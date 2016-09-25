package test;

import static org.junit.Assert.*;

import org.junit.Test;

import exceptions.ProjectException;
import project.Issue;
import project.IssueDAO;
import project.IssueType;
import project.PriorityLevel;
import sun.nio.cs.ext.ISCII91;

public class IssueTest {
	private IssueDAO dao = new IssueDAO();
			

	@Test
	public void test() throws ProjectException {
		Issue testIssue = new Issue("OOP Hierachy", PriorityLevel.MEDIUM, IssueType.TASK);
		
		dao.createIssue(testIssue);
	}

}
