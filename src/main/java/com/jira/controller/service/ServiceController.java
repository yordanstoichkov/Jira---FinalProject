package com.jira.controller.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jira.model.employee.Employee;
import com.jira.model.employee.EmployeeDAO;
import com.jira.model.employee.IEmployeeDAO;
import com.jira.model.exceptions.EmployeeException;

@RestController
public class ServiceController {
	@Autowired
	private IEmployeeDAO empDAO;

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

	@RequestMapping(value = "/givenames", method = RequestMethod.GET)
	public List<String> getNames(@RequestParam("start") String name, Model model) {
		try {
			List<String> names = new ArrayList<String>();
			List<String> result = new ArrayList<String>();
			name = name.toLowerCase();
			names.addAll(empDAO.getEmployeesNames());
			for (String str : names) {
				String[] twoNames = str.split(" ");
				if (twoNames[0].toLowerCase().startsWith(name) || twoNames[1].toLowerCase().startsWith(name)) {
					result.add(str);
				}
			}
			return result;
		} catch (EmployeeException e) {
			return null;
		}
	}

}
