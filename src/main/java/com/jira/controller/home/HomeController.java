package com.jira.controller.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jira.model.employee.EmployeeDAO;
import com.jira.model.employee.IEmployeeDAO;
import com.jira.model.exceptions.EmployeeException;
import com.jira.model.exceptions.IsssueExeption;
import com.jira.model.exceptions.ProjectException;
import com.jira.model.project.IIssueDAO;
import com.jira.model.project.IProjectDAO;
import com.jira.model.project.IssueDAO;
import com.jira.model.project.ProjectDAO;

@Component
@Controller
public class HomeController {

	@Autowired
	private IEmployeeDAO empDAO;
	@Autowired
	private IProjectDAO projectDAO;
	@Autowired
	private IIssueDAO issueDAO;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String showIndex(Model model) {
		try {
			int usersCount = empDAO.getUserCount();
			int projectsCount = projectDAO.getProjectCount();
			int issuesCount = issueDAO.getIssueCount();
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