package controller.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import model.employee.Employee;
import model.employee.EmployeeDAO;
import model.exceptions.EmployeeException;

@RestController
public class ServiceController {

	@RequestMapping(value = "/checkemail", method = RequestMethod.GET)
	public String getProjects(@RequestParam("email") String email, Model model) {
		int check = 0;
		if (!Employee.isEmailValid(email)) {
			return "no.png";
		}
		EmployeeDAO dao;
		try {
			check = new EmployeeDAO().validEmail(email);
		} catch (EmployeeException e) {
			return "{}";
		}
		if (check != 0) {
			return "no.png";
		} else {
			return "yes.png";
		}

	}
}
