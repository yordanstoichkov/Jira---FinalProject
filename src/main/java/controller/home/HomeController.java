package controller.home;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import model.employee.EmployeeDAO;
import model.exceptions.EmployeeException;
import model.exceptions.IsssueExeption;
import model.exceptions.ProjectException;
import model.project.IssueDAO;
import model.project.ProjectDAO;

@Component
@Controller
public class HomeController {

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String showIndex(Model model) {
		try {
			int usersCount = new EmployeeDAO().getUserCount();
			int projectsCount = new ProjectDAO().getProjectCount();
			int issuesCount = new IssueDAO().getIssueCount();
			model.addAttribute("usersCount", usersCount);
			model.addAttribute("projectsCount", projectsCount);
			model.addAttribute("issuesCount", issuesCount);

		} catch (EmployeeException e) {
			e.printStackTrace();
		} catch (ProjectException e) {
			e.printStackTrace();
		} catch (IsssueExeption e) {
			e.printStackTrace();
		}
		return "index";
	}

}
