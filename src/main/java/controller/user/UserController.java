package controller.user;

import javax.servlet.http.Cookie;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import configuration.SpringWebConfig;
import model.employee.Employee;
import model.employee.EmployeeDAO;
import model.employee.IEmployeeDAO;
import model.employee.Employee.Jobs;
import model.exceptions.EmployeeException;
//@ContextConfiguration(classes = SpringWebConfig.class)
//@Component
@Controller
public class UserController {
//	@Autowired
//	private EmployeeDAO empDao;
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(Model model, HttpServletRequest request) {
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
			loginID = new EmployeeDAO().loginUser(login);
		} catch (EmployeeException e) {
			request.setAttribute("message", e.getMessage());
			return "index";
		}

		if (loginID > 0) {
			HttpSession session = request.getSession();
			session.setMaxInactiveInterval(1000);
			session.setAttribute("username", login.getFirstName());
			session.setAttribute("userId", loginID);
			session.setAttribute("user", login);
			if (rememberMe != null && rememberMe.equals("Remember Me")) {
				Cookie remMe = new Cookie("email", email);
				response.addCookie(remMe);
			}

		} else {
			request.setAttribute("message", "Wrong username or password");
		}
		return "index";
	}

	@RequestMapping(value = "/reg", method = RequestMethod.POST)
	public String register(Model model, HttpServletRequest request, HttpServletResponse response) {
		String firstName = request.getParameter("firstname");
		String lastName = request.getParameter("lastname");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String password2 = request.getParameter("passwordrepeat");
		if (!password.equals(password2)) {
			try {
				throw new EmployeeException("Passwords don't match");
			} catch (EmployeeException e) {
				request.setAttribute("message", e.getMessage());
				return "register";
			}
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
		try {
			regUser = new Employee(firstName, lastName, job, email, password);
			empID = new EmployeeDAO().registerUser(regUser);
		} catch (EmployeeException e) {
			request.setAttribute("message", e.getMessage());
			return "register";
		}

		if (empID != 0) {
			request.setAttribute("message", "You are registered now login");
			return "redirect:index";
		}
		return "register";
	}

	@RequestMapping(value = "/reg", method = RequestMethod.GET)
	public String register(Model model) {
		return "register";
	}

}
