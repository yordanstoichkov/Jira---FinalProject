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
	private static final String EMPTY_JSON = "{}";
	private static final String ALLOW_IMAGE = "yes.png";
	private static final String REJECT_IMAGE = "no.png";
	@Autowired
	private IEmployeeDAO empDAO;

	// Check the email and returs if it is free and allowed or not with an image
	@RequestMapping(value = "/checkemail", method = RequestMethod.GET)
	public String getProjects(@RequestParam("email") String email, Model model) {
		int check = 0;
		if (!Employee.isEmailValid(email)) {
			return REJECT_IMAGE;
		}
		EmployeeDAO dao;
		try {
			check = empDAO.validEmail(email);
		} catch (EmployeeException e) {
			return EMPTY_JSON;
		}
		if (check != 0) {
			return REJECT_IMAGE;
		} else {
			return ALLOW_IMAGE;
		}

	}

	// Get's employees names and email from the data base starting with a prefix
	@RequestMapping(value = "/givenames", method = RequestMethod.GET)
	public List<String> getNames(@RequestParam("start") String name, Model model) {
		try {
			List<String> names = new ArrayList<String>();
			List<String> result = new ArrayList<String>();
			name = name.toLowerCase();
			names.addAll(empDAO.getEmployeesNames());
			for (String str : names) {
				if (str.toLowerCase().startsWith(name)) {
					result.add(str);
				}
			}
			return result;
		} catch (EmployeeException e) {
			return null;
		}
	}

}
