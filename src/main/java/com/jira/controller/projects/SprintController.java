package com.jira.controller.projects;

import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jira.model.exceptions.SprintException;
import com.jira.model.project.ISprintDAO;
import com.jira.model.project.Project;
import com.jira.model.project.Sprint;
import com.jira.model.project.WorkFlow;

@Scope("session")
@Component
@Controller
public class SprintController {
	@Autowired
	private ISprintDAO sprintDAO;

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

	@RequestMapping(value = "/beginsprint", method = RequestMethod.POST)
	public String beginSprint(@RequestParam("sprintGoal") String sprintGoal,
			@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
			@RequestParam("sprintId") int sprintId, Model model, HttpSession session) {
		try {
			Project project = (Project) session.getAttribute("project");
			Sprint curSprint = null;
			for (Sprint sprint : project.getSprints()) {
				if (sprint.getSprintId() == sprintId) {
					curSprint = sprint;
					break;
				}
			}
			String[] sDate = startDate.split("-");
			String[] eDate = endDate.split("-");
			int month = 0, year = 0, day = 0;
			int endMonth = 0, endYear = 0, endDay = 0;
			for (int num = 0; num < sDate.length; num++) {
				if (num == 0) {
					year = Integer.parseInt(sDate[num]);
					endYear = Integer.parseInt(eDate[num]);
				}
				if (num == 1) {
					month = Integer.parseInt(sDate[num]);
					endMonth = Integer.parseInt(eDate[num]);
				}
				if (num == 2) {
					day = Integer.parseInt(sDate[num]);
					endDay = Integer.parseInt(eDate[num]);
				}

			}
			curSprint.setStartDate(LocalDate.of(year, month, day));
			curSprint.setEndDate(LocalDate.of(endYear, endMonth, endDay));
			curSprint.setSprintGoal(sprintGoal);
			System.out.println(curSprint.getStartDate().toEpochDay());
			curSprint.setStatus(WorkFlow.IN_PROGRESS);
			sprintDAO.startSprint(curSprint);
		} catch (SprintException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:active";
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String startSprint(Model model, HttpSession session) {
		return "TestjspAjax";
	}

}
