package com.jira.controller.user;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jira.model.employee.Employee;
import com.jira.model.employee.Employee.Jobs;
import com.jira.model.employee.IEmployeeDAO;
import com.jira.model.exceptions.EmployeeException;

@Controller
public class UserController {
	@Autowired
	private IEmployeeDAO empDAO;

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(Model model, HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		request.getSession().invalidate();
		return "redirect:index";
	}

	@RequestMapping(value = "/index", method = RequestMethod.POST)
	public String login(Model model, HttpServletRequest request, HttpServletResponse response) {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String rememberMe = request.getParameter("remember");
		Employee login = null;
		int loginID = 0;
		try {
			login = new Employee(email, password);
			loginID = empDAO.loginUser(login);

			model.addAttribute("user", login);
			if (loginID > 0) {
				HttpSession session = request.getSession();
				session.setMaxInactiveInterval(100000);
				session.setAttribute("username", login.getFirstName());
				session.setAttribute("userId", loginID);
				session.setAttribute("user", login);
				if (rememberMe != null && rememberMe.equals("Remember Me")) {
					Cookie remMe = new Cookie("userId", ""+loginID);
					response.addCookie(remMe);
				}
			} else {
				request.setAttribute("message", "Wrong username or password");
			}
			return "home";
		} catch (EmployeeException e) {
			request.setAttribute("message", e.getMessage());
			return "index";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/reg", method = RequestMethod.POST)
	public String register(Model model, HttpServletRequest request, HttpServletResponse response) {
		String firstName = request.getParameter("firstname");
		String lastName = request.getParameter("lastname");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String password2 = request.getParameter("passwordrepeat");
		try {
			if (!password.equals(password2)) {
				throw new EmployeeException("Passwords don't match");
			}

			String jobPar = request.getParameter("job");
			Jobs job = null;
			if (jobPar.equals(Jobs.DEVELOPER.toString())) {
				job = Jobs.DEVELOPER;
			}
			if (jobPar.equals(Jobs.MANAGER.toString())) {
				job = Jobs.MANAGER;
			}
			if (jobPar.equals(Jobs.QA.toString())) {
				job = Jobs.QA;
			}

			Employee regUser;
			int empID = 0;

			regUser = new Employee(firstName, lastName, job, email, password);
			empID = empDAO.registerUser(regUser);

			model.addAttribute("user", regUser);
			if (empID != 0) {
				request.setAttribute("message", "You are registered now login");
				return "redirect:index";
			}

			return "register";
		} catch (EmployeeException e) {
			request.setAttribute("message", e.getMessage());
			return "register";
		} catch (Exception e) {
			return "error";
		}
	}

	@RequestMapping(value = "/reg", method = RequestMethod.GET)
	public String register(Model model) {
		return "register";
	}

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String getProfile(Model model, HttpServletRequest request) {
		if (request.getSession(false) == null) {
			return "redirect:index";
		}
		HttpSession session = request.getSession();
		Employee user = (Employee) session.getAttribute("user");
		model.addAttribute("user", user);
		return "profile";
	}

	@RequestMapping(value = "/friend", method = RequestMethod.GET)
	public String getFriendProfile(@RequestParam("id") int id, Model model, HttpServletRequest request) {
		if (request.getSession(false) == null) {
			return "redirect:index";
		}
		HttpSession session = request.getSession();
		Employee emp = (Employee) session.getAttribute("user");
		if (emp.getEmployeeID() == id) {
			return "redirect:profile";
		}
		model.addAttribute("user", emp);
		Employee friend;
		try {
			friend = empDAO.getEmployeeById(id);

			model.addAttribute("friend", friend);

			return "friend";
		} catch (EmployeeException e) {
			return "index";
		}
	}

}
