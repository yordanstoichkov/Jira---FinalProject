package controller.projects;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import model.employee.Employee;
import model.employee.EmployeeDAO;
import model.exceptions.EmployeeException;
import model.exceptions.ProjectException;
import model.project.Issue;
import model.project.Project;
import model.project.ProjectDAO;
import model.project.Sprint;
import model.project.WorkFlow;

@Controller
public class ProjectController {

	@RequestMapping(value = "/projects", method = RequestMethod.GET)
	public String getProject(Model model, HttpServletRequest request) {

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
			int toDo = 0, inProgres = 0, done = 0;
			for (Sprint sprint : project.getSprints()) {
				for (Issue issue : sprint.getIssues()) {
					if (issue.isAsigneed(emp.getEmployeeID())) {

						if (issue.getStatus().equals(WorkFlow.TO_DO)) {
							toDo++;
						}
						if (issue.getStatus().equals(WorkFlow.IN_PROGRESS)) {
							inProgres++;
						}
						if (issue.getStatus().equals(WorkFlow.DONE)) {
							done++;
						}
					}
				}
			}
			project.setDone(done);
			project.setInProgress(inProgres);
			project.setToDo(toDo);
		}
		model.addAttribute("projects", projects);

		// sending to projects.jsp
		return "projects";

	}

}
