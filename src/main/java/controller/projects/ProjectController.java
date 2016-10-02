package controller.projects;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import model.employee.Employee;
import model.exceptions.EmployeeException;
import model.exceptions.ProjectException;
import model.project.ProjectDAO;

@Controller
public class ProjectController {

	@RequestMapping(value = "/projects", method = RequestMethod.GET)
	public String getProject(Model model, HttpServletRequest request) {
		if (request.getSession(false) == null) {
			return "index";
		}
		HttpSession session = request.getSession();
		int userId = (int) session.getAttribute("userId");
		List<String> names = new ArrayList<String>();
		try {
			names.addAll(new ProjectDAO().openYourPage(userId));

		} catch (ProjectException e) {
			model.addAttribute("message", e.getMessage());
			return "index";
		}
		model.addAttribute("names", names);
		return "projects";

	}

	

}
