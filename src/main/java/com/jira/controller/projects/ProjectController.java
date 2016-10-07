package com.jira.controller.projects;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.jira.model.exceptions.ProjectException;
import com.jira.model.project.IProjectDAO;
import com.jira.model.project.Issue;
import com.jira.model.project.Project;
import com.jira.model.project.ProjectDAO;
import com.jira.model.project.Sprint;
import com.jira.model.project.WorkFlow;

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
		// sending to projects.jsp
		return "projects";

	}

	@RequestMapping(value = "/projects", method = RequestMethod.POST)
	public String addProjects(@ModelAttribute Project project, HttpSession session) {
		try {
			projectDAO.createProject(project, (Employee) session.getAttribute("user"));
		} catch (ProjectException e) {
			e.printStackTrace();
		}
		System.out.println(project.getTitle());
		return "redirect:/projects";
	}

	@RequestMapping(value = "/projectmain", method = RequestMethod.GET)
	public String getProject(@RequestParam("projectId") int projectId, Model model, HttpSession session) {
		List<Project> projects = (List<Project>) session.getAttribute("projects");
		Project result = null;
		for (Project project : projects) {
			if (project.getProjectId() == projectId) {
				result = project;
			}
		}
		session.setAttribute("project", result);
		model.addAttribute("project", result);
		return "yourProject";
	}

}
