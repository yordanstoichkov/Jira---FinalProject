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
import com.jira.model.employee.IEmployeeDAO;
import com.jira.model.exceptions.EmployeeException;
import com.jira.model.exceptions.ProjectException;
import com.jira.model.project.IProjectDAO;
import com.jira.model.project.Issue;
import com.jira.model.project.Project;
import com.jira.model.project.Sprint;
import com.jira.model.project.WorkFlow;

@Scope("session")
@Component
@Controller
public class ProjectController {
	@Autowired
	private IEmployeeDAO empDAO;
	@Autowired
	private IProjectDAO projectDAO;

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
			return "projects";
		} catch (EmployeeException e) {
			e.printStackTrace();
			return "redirect:index";
		}catch (Exception e) {
			return "error";
		}
	}

	@RequestMapping(value = "/projects", method = RequestMethod.POST)
	public String addProjects(@ModelAttribute Project project, Model model, HttpServletRequest request) {
		if (request.getSession(false) == null) {
			return "redirect:index";
		}
		HttpSession session = request.getSession();
		Employee emp = (Employee) session.getAttribute("user");
		try {
			projectDAO.createProject(project, emp);
			return "redirect:projects";
		} catch (ProjectException e) {
			return "redirect:projects";
		}catch (Exception e) {
			return "error";
		}

	}

	@RequestMapping(value = "/deleteProject", method = RequestMethod.POST)
	public String deleteProjects(@RequestParam("projectId") int projectId, Model model, HttpServletRequest request) {
		if (request.getSession(false) == null) {
			return "redirect:index";
		}
		HttpSession session = request.getSession();
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
			return "redirect:projects";
		} catch (EmployeeException e) {
			return "redirect:projects";
		} catch (ProjectException e) {
			return "redirect:projects";
		}catch (Exception e) {
			return "error";
		}

	}

	@RequestMapping(value = "/projectmain", method = RequestMethod.GET)
	public String getProject(@RequestParam("projectId") int projectId, Model model, HttpServletRequest request) {
		if (request.getSession(false) == null) {
			return "redirect:index";
		}
		HttpSession session = request.getSession();
		Employee emp = (Employee) session.getAttribute("user");
		model.addAttribute("user", emp);
		List<Project> projects = new ArrayList<Project>();
		List<Integer> managers = new ArrayList<Integer>();
		int manager = 0;
		try {
			managers.addAll(empDAO.getManagers(projectId));
			projects.addAll(empDAO.giveMyProjects(emp));

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
			model.addAttribute("emptysprint", new Sprint());

			return "yourProject";
		} catch (EmployeeException e) {
			return "redirect:projects";
		}catch (Exception e) {
			return "error";
		}
	}

	@RequestMapping(value = "/overview", method = RequestMethod.GET)
	public String projectOverView(Model model, HttpServletRequest request) {
		if (request.getSession(false) == null) {
			return "error";
		}
		HttpSession session = request.getSession();
		Project project = (Project) session.getAttribute("project");
	model.addAttribute("user", session.getAttribute("user"));
		int toDo = 0;
		int progress = 0;
		int done = 0;
		
		for (Sprint sprint : project.getSprints()) {
			if (sprint.getStatus().equals(WorkFlow.TO_DO)) {
				toDo++;
			}
			if (sprint.getStatus().equals(WorkFlow.IN_PROGRESS)) {
				progress++;
			}
			if (sprint.getStatus().equals(WorkFlow.DONE)) {
				done++;
			}

		}
	
		model.addAttribute("project", project);
		model.addAttribute("toDo", toDo);
		model.addAttribute("progress", progress);
		model.addAttribute("done", done);
		
		return "overview";
	}
}
