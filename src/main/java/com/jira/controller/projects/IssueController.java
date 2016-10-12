package com.jira.controller.projects;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jira.model.comment.Comment;
import com.jira.model.employee.Employee;
import com.jira.model.employee.IEmployeeDAO;
import com.jira.model.exceptions.EmployeeException;
import com.jira.model.exceptions.IsssueExeption;
import com.jira.model.exceptions.ProjectException;
import com.jira.model.exceptions.SprintException;
import com.jira.model.project.IIssueDAO;
import com.jira.model.project.ISprintDAO;
import com.jira.model.project.Issue;
import com.jira.model.project.Project;
import com.jira.model.project.Sprint;

@Scope("session")
@Component
@Controller
public class IssueController {
	@Autowired
	private IEmployeeDAO empDAO;

	@Autowired
	private ISprintDAO sprintDAO;
	@Autowired
	private IIssueDAO issueDAO;

	@RequestMapping(value = "/newIssue", method = RequestMethod.GET)
	public String addNewIssue(@RequestParam("sprintId") int sprintId, Model model, HttpSession session) {
		Employee emp = (Employee) session.getAttribute("user");
		Sprint sprint = null;
		try {
			sprint = sprintDAO.getSprint(sprintId);
		} catch (SprintException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("emptyIssue", new Issue(sprint));
		model.addAttribute("user", emp);
		model.addAttribute("sprint", sprint);
		return "newIssue";
	}

	@RequestMapping(value = "/newIssue", method = RequestMethod.POST)
	public String createIssue(@ModelAttribute Issue issue, @RequestParam("sprintId") int sprintId,
			@RequestParam("assignee") String assignee, Model model, HttpSession session) {
		Project project = (Project) session.getAttribute("project");
		Sprint sprint = null;
		for (Sprint oneSprint : project.getSprints()) {
			if (oneSprint.getSprintId() == sprintId) {
				sprint = oneSprint;
			}
		}

		try {
			issue.setSprint(sprint);
		} catch (IsssueExeption e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] employeeNames = assignee.split(",");
		String email = employeeNames[employeeNames.length - 1].trim();
		System.out.println(issue.getPriority());
		int employeeId = 0;
		try {
			employeeId = empDAO.getEmployeeIdByEmail(email);
			issue.setAsignee(employeeId);
			issueDAO.createIssue(issue);
		} catch (IsssueExeption e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (EmployeeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProjectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			sprint.addIssue(issue);
		} catch (SprintException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:issue?issueId=" + issue.getIssueId();
	}

	@RequestMapping(value = "/myIssues", method = RequestMethod.GET)
	public String getMyIssues(Model model, HttpSession session) {
		Employee emp = (Employee) session.getAttribute("user");
		try {
			List<Issue> issues = empDAO.getEmployeesIssues(emp);
			Set<Issue> orderedIssues = new TreeSet<Issue>(new Comparator<Issue>() {
				@Override
				public int compare(Issue issue1, Issue issue2) {
					if (issue1.getPriority().compareTo(issue2.getPriority()) == 0) {
						return issue1.getIssueId() - issue2.getIssueId();
					}
					return issue2.getPriority().compareTo(issue1.getPriority());
				}
			});

			for (Issue issue : issues) {
				orderedIssues.add(issue);
			}
			model.addAttribute("user", emp);
			model.addAttribute("issues", orderedIssues);
		} catch (EmployeeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "yourIssues";
	}

	@RequestMapping(value = "/issue", method = RequestMethod.POST)
	public String addComent(@ModelAttribute Comment comment, Model model, @RequestParam("issueId") int issueId,
			HttpSession session) {
		Issue issue = null;
		try {
			comment.setIssueId(issueId);
			comment.setWriter(((Employee) session.getAttribute("user")));
			comment.setDate(LocalDate.now());
			issueDAO.commentIssue(comment);
		} catch (IsssueExeption e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			issue = issueDAO.getIssue(issueId);
		} catch (IsssueExeption e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("issue", issue);
		return "redirect:issue?issueId=" + issueId;
	}

	@RequestMapping(value = "/deleteissue", method = RequestMethod.POST)
	public String deleteIssue(Model model, @RequestParam("issueId") int issueId, HttpSession session) {
		Project project = (Project) session.getAttribute("project");
		System.out.println(issueId);
		try {
			issueDAO.deleteIssue(issueId);
		} catch (IsssueExeption e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:projectmain?projectId=" + project.getProjectId();
	}

}
