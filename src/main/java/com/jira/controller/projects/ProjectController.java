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

import com.jira.model.employee.Employee;
import com.jira.model.employee.EmployeeDAO;
import com.jira.model.employee.IEmployeeDAO;
import com.jira.model.exceptions.EmployeeException;
import com.jira.model.exceptions.IsssueExeption;
import com.jira.model.exceptions.ProjectException;
import com.jira.model.exceptions.SprintException;
import com.jira.model.project.IIssueDAO;
import com.jira.model.project.IProjectDAO;
import com.jira.model.project.ISprintDAO;
import com.jira.model.project.Issue;
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
		System.out.println(project.getTitle());
		return "redirect:/projects";
	}

	@RequestMapping(value = "/projectmain", method = RequestMethod.GET)
	public String getProject(@RequestParam("projectId") int projectId, Model model, HttpSession session) {
		Employee emp = (Employee) session.getAttribute("user");
		List<Project> projects = new ArrayList<Project>();
		try {
			projects.addAll(empDAO.giveMyProjects(emp));
		} catch (EmployeeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Project result = null;
		for (Project project : projects) {
			if (project.getProjectId() == projectId) {
				result = project;
			}
		}
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
						issue.setStatus(WorkFlow.IN_PROGRESS);
						try {
							issueDAO.updateIssueInProgress(issueId);
						} catch (IsssueExeption e) {

						}
					}
				}
			}

		}

		return "redirect:active";
	}

	@RequestMapping(value = "/actives", method = RequestMethod.GET)
	public String issueDone(@RequestParam("issueId") int issueId, Model model, HttpSession session) {
		Project project = (Project) session.getAttribute("project");
		if (project != null) {
			model.addAttribute("project", project);
			if (project.getSprints() != null) {
				for (Sprint sprint : project.getSprints()) {
					if (sprint.getStatus() == WorkFlow.IN_PROGRESS) {
						if (sprint.getIssues() != null) {
							for (Issue issue : sprint.getIssues()) {
								if (issue.getIssueId() == issueId) {
									issue.setStatus(WorkFlow.DONE);
									try {
										issueDAO.updateIssueInProgress(issueId);
									} catch (IsssueExeption e) {

									}
								}
							}
						}
					}
				}

			}
		}
		return "redirect:active";
	}
}
