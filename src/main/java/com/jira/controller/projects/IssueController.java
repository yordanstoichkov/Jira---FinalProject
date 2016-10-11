package com.jira.controller.projects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jira.model.exceptions.SprintException;
import com.jira.model.project.IIssueDAO;
import com.jira.model.project.ISprintDAO;
import com.jira.model.project.Sprint;

@Scope("session")
@Component
@Controller
public class IssueController {

	@Autowired
	private ISprintDAO sprintDAO;

	@RequestMapping(value = "/newIssue", method = RequestMethod.POST)
	public String addNewIssue(@RequestParam("sprintId") int sprintId, Model model, HttpSession session) {
		Sprint sprint = null;
		try {
			sprint = sprintDAO.getSprint(sprintId);
		} catch (SprintException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("sprint", sprint);
		return "newIssue";
	}

	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	public void addComent(@ModelAttribute Comment comment) {
		
	}

}
