package com.jira.controller.home;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jira.model.employee.Employee;
import com.jira.model.employee.IEmployeeDAO;
import com.jira.model.exceptions.EmployeeException;
import com.jira.model.exceptions.IssueException;
import com.jira.model.exceptions.ProjectException;
import com.jira.model.project.IIssueDAO;
import com.jira.model.project.IProjectDAO;

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
	public String showIndex(Model model, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int cookie = 0; cookie < cookies.length; cookie++) {
				if (cookies[cookie].getName().equals("userId")) {
					int id = Integer.parseInt(cookies[cookie].getValue());
					try {
						Employee emp = empDAO.getEmployeeById(id);
						model.addAttribute("user", emp);
						return "home";
					} catch (EmployeeException e) {
						return "index";
					}
				}
			}
		}
		if (request.getSession(false) != null) {
			return "redirect:home";
		}
		try {
			int usersCount = empDAO.getUserCount();
			int projectsCount = projectDAO.getProjectCount();
			int issuesCount = issueDAO.getIssueCount();
			model.addAttribute("usersCount", usersCount);
			model.addAttribute("projectsCount", projectsCount);
			model.addAttribute("issuesCount", issuesCount);

			return "index";
		} catch (EmployeeException e) {
			return "redirect:index";
		} catch (ProjectException e) {
			return "redirect:index";
		} catch (IssueException e) {
			return "redirect:index";
		} catch (Exception e) {
			return "error";
		}

	}

	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public String showContact(Model model, HttpServletRequest request) {
		if (request.getSession(false) != null) {
			model.addAttribute("user", request.getSession().getAttribute("user"));
		}
		return "contact";
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String showHome(Model model, HttpServletRequest request) {
		if (request.getSession(false) != null) {
			model.addAttribute("user", request.getSession().getAttribute("user"));
		}
		return "home";
	}

}
