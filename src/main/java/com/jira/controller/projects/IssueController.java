package com.jira.controller.projects;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
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

import com.amazonaws.http.HttpRequest;
import com.jira.model.comment.Comment;
import com.jira.model.employee.Employee;
import com.jira.model.employee.IEmployeeDAO;
import com.jira.model.exceptions.EmployeeException;
import com.jira.model.exceptions.IssueExeption;
import com.jira.model.exceptions.ProjectException;
import com.jira.model.exceptions.SprintException;
import com.jira.model.project.IIssueDAO;
import com.jira.model.project.IPartOfProjectDAO;
import com.jira.model.project.ISprintDAO;
import com.jira.model.project.Issue;
import com.jira.model.project.PartOfProjectException;
import com.jira.model.project.Project;
import com.jira.model.project.Sprint;
import com.jira.model.project.WorkFlow;

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
	@Autowired
	private IPartOfProjectDAO partDAO;

	@RequestMapping(value = "/newIssue", method = RequestMethod.GET)
	public String addNewIssue(@RequestParam("sprintId") int sprintId, Model model, HttpServletRequest request) {
		if (request.getSession(false) == null) {
			return "redirect:index";
		}
		HttpSession session = request.getSession();
		Project project = (Project) session.getAttribute("project");
		try {
			Employee emp = (Employee) session.getAttribute("user");
			Sprint sprint = null;

			for (Sprint oneSprint : project.getSprints()) {
				if (oneSprint.getSprintId() == sprintId) {
					sprint = oneSprint;
				}
			}
			model.addAttribute("emptyIssue", new Issue(sprint));
			model.addAttribute("user", emp);
			model.addAttribute("project", session.getAttribute("project"));
			model.addAttribute("sprint", sprint);
			return "newIssue";
		} catch (Exception e) {
			return "error";
		}
	}

	@RequestMapping(value = "/newIssue", method = RequestMethod.POST)
	public String createIssue(@ModelAttribute Issue issue, @RequestParam("sprintId") int sprintId,
			@RequestParam("assignee") String assignee, Model model, HttpServletRequest request) {
		if (request.getSession(false) == null) {
			return "redirect:index";
		}
		HttpSession session = request.getSession();
		Project project = (Project) session.getAttribute("project");
		Employee emp = (Employee) session.getAttribute("user");
		Sprint sprint = null;
		try {
			Set<String> asignees = new HashSet<String>();
			asignees.add(assignee);
			asignees.add(request.getParameter("asignee1"));
			asignees.add(request.getParameter("asignee2"));
			asignees.add(request.getParameter("asignee3"));
			asignees.add(request.getParameter("asignee4"));
			for (Sprint oneSprint : project.getSprints()) {
				if (oneSprint.getSprintId() == sprintId) {
					sprint = oneSprint;
				}
			}

			issue.setSprint(sprint);
			for (String str : asignees) {
				if (str != null && !str.trim().equals("")) {
					String[] employeeNames = str.split(",");
					String email = employeeNames[employeeNames.length - 1].trim();
					int employeeId = 0;

					employeeId = empDAO.getEmployeeIdByEmail(email);
					if (employeeId > 0) {
						issue.setAsignee(employeeId);
					}
				}
			}

			issueDAO.createIssue(issue);
			sprint.addIssue(issue);
			return "redirect:issue?issueId=" + issue.getIssueId();
		} catch (SprintException e) {
			model.addAttribute("emptyIssue", new Issue(sprint));
			model.addAttribute("user", emp);
			model.addAttribute("project", session.getAttribute("project"));
			model.addAttribute("sprint", sprint);
			model.addAttribute("message", e.getMessage());
			return "newIssue";
		} catch (IssueExeption e) {
			model.addAttribute("emptyIssue", new Issue(sprint));
			model.addAttribute("user", emp);
			model.addAttribute("project", session.getAttribute("project"));
			model.addAttribute("sprint", sprint);
			model.addAttribute("message", e.getMessage());
			return "newIssue";
		} catch (EmployeeException e) {
			model.addAttribute("emptyIssue", new Issue(sprint));
			model.addAttribute("user", emp);
			model.addAttribute("project", session.getAttribute("project"));
			model.addAttribute("sprint", sprint);
			model.addAttribute("message", e.getMessage());
			return "newIssue";
		} catch (Exception e) {
			return "error";
		}
	}

	@RequestMapping(value = "/myIssues", method = RequestMethod.GET)
	public String getMyIssues(Model model, HttpServletRequest request) {
		if (request.getSession(false) == null) {
			return "redirect:index";
		}
		HttpSession session = request.getSession();
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
			return "yourIssues";
		} catch (EmployeeException e) {
			return "redirect:index";
		} catch (Exception e) {
			return "error";
		}

	}

	@RequestMapping(value = "/issue", method = RequestMethod.POST)
	public String addComent(@ModelAttribute Comment comment, Model model, @RequestParam("issueId") int issueId,
			HttpServletRequest request) {
		if (request.getSession(false) == null) {
			return "redirect:index";
		}
		HttpSession session = request.getSession();
		Issue issue = null;
		Project project = (Project) session.getAttribute("project");
		try {
			comment.setIssueId(issueId);
			comment.setWriter(((Employee) session.getAttribute("user")));
			comment.setDate(LocalDate.now());
			issueDAO.commentIssue(comment);

			issue = issueDAO.getIssue(issueId);
			model.addAttribute("issue", issue);
			return "redirect:issue?issueId=" + issueId;
		} catch (IssueExeption e) {
			return "redirect:issue?issueId=" + issueId;
		} catch (Exception e) {
			return "error";
		}

	}

	@RequestMapping(value = "/deleteissue", method = RequestMethod.POST)
	public String deleteIssue(Model model, @RequestParam("issueId") int issueId, HttpServletRequest request) {
		if (request.getSession(false) == null) {
			return "redirect:index";
		}
		HttpSession session = request.getSession();
		Project project = (Project) session.getAttribute("project");
		try {
			issueDAO.deleteIssue(issueId);
			return "redirect:projectmain?projectId=" + project.getProjectId();
		} catch (IssueExeption e) {
			return "redirect:projectmain?projectId=" + project.getProjectId();
		} catch (Exception e) {
			return "error";
		}

	}

	@RequestMapping(value = "/issue", method = RequestMethod.GET)
	public String showIssueInfo(@RequestParam("issueId") int issueId, Model model, HttpServletRequest request) {
		if (request.getSession(false) == null) {
			return "redirect:index";
		}
		HttpSession session = request.getSession();
		Project project = (Project) session.getAttribute("project");
		try {
			model.addAttribute("project", project);
			int userId = (int) session.getAttribute("userId");
			model.addAttribute("userId", userId);
			for (Sprint sprint : project.getSprints()) {
				for (Issue issue : sprint.getIssues()) {
					if (issue.getIssueId() == issueId) {
						model.addAttribute(issue);
						model.addAttribute("sprint", sprint);
						break;
					}
				}
			}
			model.addAttribute("user", session.getAttribute("user"));
			List<Integer> developersId = new ArrayList<Integer>();

			developersId.addAll(empDAO.getDevelopers(issueId));

			// model.addAttribute("developersId", developersId);
			List<Integer> reviewersId = new ArrayList<Integer>();

			reviewersId.addAll(empDAO.getReviewers(issueId));
			// model.addAttribute("reviewersId", reviewersId);
			List<Integer> managersId = new ArrayList<Integer>();
			managersId.addAll(empDAO.getManagers(project.getProjectId()));

			// model.addAttribute("managersId", managersId);
			List<String> namesOfManagers = new ArrayList<String>();
			if (managersId != null) {
				for (Integer empId : managersId) {
					Employee emp = empDAO.getEmployeeById(empId);
					if (emp != null) {
						namesOfManagers.add(emp.getFirstName() + " " + emp.getLastName());
					}
				}
			}
			model.addAttribute("namesOfManagers", namesOfManagers);
			List<String> namesOfDevelopers = new ArrayList<String>();
			if (developersId != null) {
				for (Integer empId : developersId) {
					Employee emp = empDAO.getEmployeeById(empId);
					if (emp != null) {
						namesOfDevelopers.add(emp.getFirstName() + " " + emp.getLastName());
					}
				}
			}
			model.addAttribute("namesOfDevelopers", namesOfDevelopers);
			List<String> namesOfReviewers = new ArrayList<String>();
			if (reviewersId != null) {
				for (Integer empId : reviewersId) {
					Employee emp = empDAO.getEmployeeById(empId);
					if (emp != null) {
						namesOfReviewers.add(emp.getFirstName() + " " + emp.getLastName());
					}
				}
			}
			model.addAttribute("namesOfReviewers", namesOfReviewers);
			List<Comment> commentsOfIssue = new ArrayList<Comment>();
			commentsOfIssue.addAll(issueDAO.getComments(issueId));

			model.addAttribute("emptycomment", new Comment());
			model.addAttribute("commentsOfIssue", commentsOfIssue);
			return "issue";
		} catch (IssueExeption e) {
			return "redirect:projectmain?projectId=" + project.getProjectId();
		} catch (EmployeeException e) {
			return "redirect:projectmain?projectId=" + project.getProjectId();
		} catch (Exception e) {
			return "error";
		}

	}

	@RequestMapping(value = "/active", method = RequestMethod.POST)
	public String updateIssueStatus(@RequestParam("issueId") int issueId, Model model, HttpServletRequest request) {
		if (request.getSession(false) == null) {
			return "redirect:index";
		}
		HttpSession session = request.getSession();
		Project project = (Project) session.getAttribute("project");
		try {
			model.addAttribute("project", project);
			Employee user = (Employee) session.getAttribute("user");
			model.addAttribute("userId", user.getEmployeeID());
			model.addAttribute("user", user);
			Sprint activeSprint = (Sprint) session.getAttribute("activeSprint");

			for (Issue issue : activeSprint.getIssues()) {
				if (issue.getIssueId() == issueId) {

					int newIssueId = issueDAO.updateIssueStatus(issueId);
					WorkFlow status = partDAO.getStatus(newIssueId);
					issue.setStatus(status);

				}
			}

			boolean isSprintDone = true;
			for (Issue issue : activeSprint.getIssues()) {
				if ((issue.getStatus().equals(WorkFlow.TO_DO)) || (issue.getStatus().equals(WorkFlow.IN_PROGRESS))) {
					isSprintDone = false;
					break;
				}
			}
			if (isSprintDone) {
				sprintDAO.updateSprintStatus(activeSprint.getSprintId());

				activeSprint.setStatus(WorkFlow.DONE);
				model.addAttribute("activeSprint", null);
				model.addAttribute("message", "Your sprint is already done. You can start a new one.");
			} else {
				model.addAttribute("activeSprint", activeSprint);
			}
			return "active";
		} catch (IssueExeption e) {
			return "redirect:active";
		} catch (PartOfProjectException e) {
			return "redirect:active";
		} catch (SprintException e) {
			return "redirect:active";
		} catch (Exception e) {
			return "error";
		}

	}

}
