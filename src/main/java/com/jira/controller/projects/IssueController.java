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
import com.jira.model.exceptions.SprintException;
import com.jira.model.project.IIssueDAO;
import com.jira.model.project.ISprintDAO;
import com.jira.model.project.Issue;
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

	@RequestMapping(value = "/newIssue", method = RequestMethod.POST)
	public String addNewIssue(@RequestParam("sprintId") int sprintId, Model model, HttpSession session) {
		Employee emp = (Employee) session.getAttribute("user");
		Sprint sprint = null;
		try {
			sprint = sprintDAO.getSprint(sprintId);
		} catch (SprintException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("user", emp);
		model.addAttribute("sprint", sprint);
		return "newIssue";
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

}
