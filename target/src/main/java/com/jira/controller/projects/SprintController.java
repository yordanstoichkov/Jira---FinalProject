package com.jira.controller.projects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jira.model.project.Project;
import com.jira.model.project.Sprint;

@Scope("session")
@Component
@Controller
public class SprintController {

	@RequestMapping(value = "/startsprint", method = RequestMethod.POST)
	public String startSprint(@RequestParam("sprintId") int sprintId, Model model, HttpSession session) {
		Project project = (Project) session.getAttribute("project");
		model.addAttribute("project", project);
		for (Sprint sprint : project.getSprints()) {
			if (sprint.getSprintId() == sprintId) {
				model.addAttribute("sprint", sprint);
			}
		}
		return "startSprint";
	}

}
