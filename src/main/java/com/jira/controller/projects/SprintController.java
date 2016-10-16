package com.jira.controller.projects;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.jira.WebInitializer;
import com.jira.model.connections.S3Connection;
import com.jira.model.employee.Employee;
import com.jira.model.exceptions.ProjectException;
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
	public String startSprint(@RequestParam("sprintId") int sprintId, Model model, HttpServletRequest request) {
		if (request.getSession(false) == null) {
			return "redirect:index";
		}
		HttpSession session = request.getSession();
		Project project = (Project) session.getAttribute("project");
		model.addAttribute("project", project);
		try {
			for (Sprint sprint : project.getSprints()) {
				if (sprint.getSprintId() == sprintId) {
					model.addAttribute("sprint", sprint);
				}
			}
			model.addAttribute("user", session.getAttribute("user"));
			return "startSprint";
		} catch (Exception e) {
			return "error";
		}
	}

	@RequestMapping(value = "/beginsprint", method = RequestMethod.POST)
	public String beginSprint(@RequestParam("sprintGoal") String sprintGoal,
			@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
			@RequestParam("sprintId") int sprintId, Model model, HttpServletRequest request) {
		if (request.getSession(false) == null) {
			return "redirect:index";
		}
		HttpSession session = request.getSession();
		Project project = (Project) session.getAttribute("project");
		Sprint curSprint = null;
		model.addAttribute("user", session.getAttribute("user"));
		model.addAttribute("project", project);
		for (Sprint sprint : project.getSprints()) {
			if (sprint.getSprintId() == sprintId) {
				curSprint = sprint;
				break;
			}
		}
		try {
			if(startDate==null||endDate==null||startDate.trim().equals("")||endDate.trim().equals("")){
				throw new SprintException("Dates must be given, to begin spring");
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
			return "redirect:active";
		} catch (SprintException e) {
			model.addAttribute("sprint", curSprint);
			model.addAttribute("message", e.getMessage());
			return "startSprint";
		} catch (Exception e) {
			return "error";
		}

	}

	@RequestMapping(value = "/active", method = RequestMethod.GET)
	public String getActiveSprintOfProject(Model model, HttpServletRequest request) {
		if (request.getSession(false) == null) {
			return "redirect:index";
		}
		HttpSession session = request.getSession();
		Project project = (Project) session.getAttribute("project");
		model.addAttribute("project", project);
		try {
			boolean isActiveSprint = false;
			for (Sprint sprint : project.getSprints()) {
				if (sprint.getStatus() == WorkFlow.IN_PROGRESS) {
					isActiveSprint = true;
					session.setAttribute("activeSprint", sprint);
					model.addAttribute("activeSprint", sprint);
				} else {
					model.addAttribute("activeSprint", null);
				}
			}
			if (!isActiveSprint) {
				model.addAttribute("message", "There is no active sprint. You can start a new one.");
			}
			model.addAttribute("user", session.getAttribute("user"));
			int userId = (int) session.getAttribute("userId");
			model.addAttribute("userId", userId);
			return "active";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/projectmain", method = RequestMethod.POST)
	public String addSprint(@ModelAttribute Sprint sprint, HttpServletRequest request) {
		if (request.getSession(false) == null) {
			return "redirect:index";
		}
		HttpSession session = request.getSession();
		Project project = (Project) session.getAttribute("project");
		try {
			sprint.setProject(project);
			project.addSprint(sprint);
			sprintDAO.createSprint(sprint);
			return "redirect:/projectmain?projectId=" + project.getProjectId();
		} catch (SprintException e) {
			return "redirect:/projectmain?projectId=" + project.getProjectId();
		} catch (ProjectException e) {
			return "redirect:/projectmain?projectId=" + project.getProjectId();
		}catch (Exception e) {
			return "error";
		}

	}

	@RequestMapping(value = "done", method = RequestMethod.GET)
	public String getDoneSprints(Model model, HttpServletRequest request) {
		if (request.getSession(false) == null) {
			return "redirect:index";
		}
		HttpSession session = request.getSession();
		model.addAttribute("user", session.getAttribute("user"));
		Project project = (Project) session.getAttribute("project");
		model.addAttribute("project", project);
		return "done";
	}

}
