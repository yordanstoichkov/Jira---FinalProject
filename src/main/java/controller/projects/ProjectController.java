package controller.projects;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import model.employee.Employee;
import model.employee.EmployeeDAO;
import model.exceptions.EmployeeException;
import model.exceptions.ProjectException;
import model.project.Issue;
import model.project.Project;
import model.project.ProjectDAO;
import model.project.Sprint;
import model.project.WorkFlow;

@Component
@Controller
public class ProjectController {

	@RequestMapping(value = "/projects", method = RequestMethod.GET)
	public String getProjects(Model model, HttpServletRequest request) {

		if (request.getSession(false) == null) {
			return "redirect:index";
		}
		HttpSession session = request.getSession();
		Employee emp = (Employee) session.getAttribute("user");
		List<Project> projects = new ArrayList<Project>();
		try {
			projects.addAll(new EmployeeDAO().giveMyProjects(emp));

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
			new ProjectDAO().createProject(project, (Employee) session.getAttribute("user"));
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
