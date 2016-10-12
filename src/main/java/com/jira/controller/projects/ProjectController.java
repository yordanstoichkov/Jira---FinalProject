package com.jira.controller.projects;

import java.util.ArrayList;
import java.util.List;

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

import com.jira.model.comment.Comment;
import com.jira.model.employee.Employee;
import com.jira.model.employee.EmployeeDAO;
import com.jira.model.employee.IEmployeeDAO;
import com.jira.model.exceptions.EmployeeException;
import com.jira.model.exceptions.IsssueExeption;
import com.jira.model.exceptions.ProjectException;
import com.jira.model.exceptions.SprintException;
import com.jira.model.project.IIssueDAO;
import com.jira.model.project.IPartOfProjectDAO;
import com.jira.model.project.IProjectDAO;
import com.jira.model.project.ISprintDAO;
import com.jira.model.project.Issue;
import com.jira.model.project.PartOfProjectException;
import com.jira.model.project.Project;
import com.jira.model.project.ProjectDAO;
import com.jira.model.project.Sprint;
import com.jira.model.project.WorkFlow;

@Scope("session")
@Component
@Controller
public class ProjectController {
	@Autowired
	private IEmployeeDAO empDAO;
	@Autowired
	private IIssueDAO issueDAO;
	@Autowired
	private IProjectDAO projectDAO;
	@Autowired
	private ISprintDAO sprintDAO;
	@Autowired
	private IPartOfProjectDAO partDAO;

	@RequestMapping(value = "/projects", method = RequestMethod.GET)
	public String getProjects(Model model, HttpServletRequest request) {

		if (request.getSession(false) == null) {
			return "redirect:index";
		}
		HttpSession session = request.getSession();
		Employee emp = (Employee) session.getAttribute("user");
		List<Project> projects = new ArrayList<Project>();
		try {
			projects.addAll(empDAO.giveMyProjects(emp));

		} catch (EmployeeException e) {
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
			return "redirect:index";
		}
		for (Project project : projects) {
			for (Sprint sprint : project.getSprints()) {
				for (Issue issue : sprint.getIssues()) {
					if (issue.isAsigneed(emp.getEmployeeID())) {
						project.setIssueStatus(issue);
					}
				}
			}
		}
		model.addAttribute("emptyproject", new Project());
		session.setAttribute("projects", projects);
		model.addAttribute("projects", projects);
		model.addAttribute("user", emp);
		// sending to projects.jsp
		return "projects";

	}

	@RequestMapping(value = "/projects", method = RequestMethod.POST)
	public String addProjects(@ModelAttribute Project project, Model model, HttpSession session) {
		Employee emp = (Employee) session.getAttribute("user");
		try {
			projectDAO.createProject(project, emp);
		} catch (ProjectException e) {
			e.printStackTrace();
		}
		return "redirect:/projects";
	}

	@RequestMapping(value = "/deleteProject", method = RequestMethod.POST)
	public String deleteProjects(@RequestParam("projectId") int projectId, Model model, HttpSession session) {

		try {
			Employee emp = (Employee) session.getAttribute("user");
			List<Project> projects = new ArrayList<Project>();
			projects.addAll(empDAO.giveMyProjects(emp));
			Project result = null;
			for (Project project : projects) {
				if (project.getProjectId() == projectId) {
					result = project;
				}
			}
			projectDAO.deleteProject(result);
		} catch (EmployeeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProjectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/projects";
	}

	@RequestMapping(value = "/projectmain", method = RequestMethod.GET)
	public String getProject(@RequestParam("projectId") int projectId, Model model, HttpSession session) {
		Employee emp = (Employee) session.getAttribute("user");
		model.addAttribute("user", emp);
		List<Project> projects = new ArrayList<Project>();
		List<Integer> managers = new ArrayList<Integer>();
		int manager = 0;
		try {
			managers.addAll(empDAO.getManagers(projectId));
			projects.addAll(empDAO.giveMyProjects(emp));
		} catch (EmployeeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Integer id : managers) {
			if (emp.getEmployeeID() == id) {
				manager = 1;
			}
		}
		Project result = null;
		for (Project project : projects) {
			if (project.getProjectId() == projectId) {
				result = project;
			}

		}
		for (Sprint sprint : result.getSprints()) {
			if (sprint.getStatus().equals(WorkFlow.IN_PROGRESS)) {
				model.addAttribute("activeSprint", sprint);
			}
		}
		model.addAttribute("manager", manager);
		session.setAttribute("project", result);
		model.addAttribute("project", result);
		model.addAttribute("issueempty", new Issue());
		model.addAttribute("emptysprint", new Sprint());
		model.addAttribute("user", emp);

		return "yourProject";
	}

	@RequestMapping(value = "/projectmain", method = RequestMethod.POST)
	public String addSprint(@ModelAttribute Sprint sprint, HttpSession session) {
		Project project = (Project) session.getAttribute("project");
		try {
			sprint.setProject(project);
			sprintDAO.createSprint(sprint);
		} catch (SprintException e) {

		}

		return "redirect:/projectmain?projectId=" + project.getProjectId();
	}

	@RequestMapping(value = "/active", method = RequestMethod.GET)
	public String getActiveSprintOfProject(Model model, HttpSession session) {
		Project project = (Project) session.getAttribute("project");
		model.addAttribute("project", project);
		for (Sprint sprint : project.getSprints()) {
			if (sprint.getStatus() == WorkFlow.IN_PROGRESS) {
				session.setAttribute("activeSprint", sprint);
				model.addAttribute(sprint);
			}
		}
		model.addAttribute("user", session.getAttribute("user"));
		int userId = (int) session.getAttribute("userId");
		model.addAttribute("userId", userId);
		return "active";
	}

	@RequestMapping(value = "/activy", method = RequestMethod.GET)
	public String issueInProgress(@RequestParam("issueId") int issueId, Model model, HttpSession session) {
		Project project = (Project) session.getAttribute("project");
		model.addAttribute("project", project);
		for (Sprint sprint : project.getSprints()) {
			if (sprint.getStatus() == WorkFlow.IN_PROGRESS) {
				for (Issue issue : sprint.getIssues()) {
					if (issue.getIssueId() == issueId) {
						try {
							int newIssueId = issueDAO.updateIssueStatus(issueId);
							WorkFlow status = partDAO.getStatus(newIssueId);
							issue.setStatus(status);
						} catch (IsssueExeption e) {

						} catch (PartOfProjectException e) {

						}
					}
				}
			}

		}

		return "redirect:active";
	}

	@RequestMapping(value = "/issue", method = RequestMethod.GET)
	public String showIssueInfo(@RequestParam("issueId") int issueId, Model model, HttpSession session) {
		System.out.println(issueId);
		Project project = (Project) session.getAttribute("project");

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
		try {
			developersId.addAll(empDAO.getDevelopers(issueId));
		} catch (EmployeeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// model.addAttribute("developersId", developersId);
		List<Integer> reviewersId = new ArrayList<Integer>();
		try {
			reviewersId.addAll(empDAO.getReviewers(issueId));
		} catch (EmployeeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// model.addAttribute("reviewersId", reviewersId);
		List<Integer> managersId = new ArrayList<Integer>();
		try {
			managersId.addAll(empDAO.getManagers(project.getProjectId()));
		} catch (EmployeeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		try {
			commentsOfIssue.addAll(issueDAO.getComments(issueId));
		} catch (IsssueExeption e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("emptycomment", new Comment());
		model.addAttribute("commentsOfIssue", commentsOfIssue);
		System.out.println(commentsOfIssue);
		return "issue";
	}
	@RequestMapping(value="/overview", method=RequestMethod.GET)
	public String projectOverView(Model model, HttpSession session) {
		Project project=(Project) session.getAttribute("project");
		model.addAttribute("project", project);
		return "overview";
	}
}
