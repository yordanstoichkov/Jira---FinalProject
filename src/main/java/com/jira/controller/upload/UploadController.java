package com.jira.controller.upload;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jira.WebInitializer;
import com.jira.model.connections.S3Connection;
import com.jira.model.employee.Employee;
import com.jira.model.employee.EmployeeDAO;
import com.jira.model.employee.IEmployeeDAO;
import com.jira.model.exceptions.EmployeeException;
import com.jira.model.exceptions.IssueException;
import com.jira.model.project.Issue;
import com.jira.model.project.IssueDAO;
import com.jira.model.project.Project;
import com.jira.model.project.Sprint;

@Controller
public class UploadController {
	private static final String PATH_SEPARATOR = "\\\\";
	@Autowired
	private S3Connection s3Con;
	@Autowired
	private IssueDAO issueDAO;
	@Autowired
	private IEmployeeDAO employeeDAO;

	// Uploads pdf file for an issue into S3
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("issueId") int issueId,
			Model model, HttpSession session) {

		Issue issue = null;
		Project project = (Project) session.getAttribute("project");
		for (Sprint sprint : project.getSprints()) {
			for (Issue oneIssue : sprint.getIssues()) {
				if (oneIssue.getIssueId() == issueId) {
					issue = oneIssue;
					break;
				}
			}
		}
		Employee emp = (Employee) session.getAttribute("user");
		model.addAttribute("user", emp);
		String[] path = file.getOriginalFilename().split(PATH_SEPARATOR);
		String fileName = path[path.length - 1];
		File picture = new File(WebInitializer.LOCATION + fileName);
		try {
			FileCopyUtils.copy(file.getBytes(), picture);
			String url = s3Con.s3Upload(fileName, issue.getTitle() + "" + issue.getIssueId());
			issue.setFilePath(url);
			issueDAO.addIssueFile(issue);
			return "redirect:issue?issueId=" + issueId;
		} catch (IOException e) {
			return "error";
		} catch (IssueException e1) {
			return "error";
		} finally {
			picture.delete();
		}

	}

	// Uploads and updates image avatar of the user.
	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	public String updateAvatar(@RequestParam("file") MultipartFile file, Model model, HttpSession session) {
		Employee user = (Employee) session.getAttribute("user");
		model.addAttribute("user", user);
		String[] path = file.getOriginalFilename().split(PATH_SEPARATOR);
		String fileName = path[path.length - 1];
		File avatar = new File(WebInitializer.LOCATION + fileName);
		try {
			FileCopyUtils.copy(file.getBytes(), avatar);
			String url = s3Con.s3Upload(fileName, user.getEmail());
			user.setAvatarPath(url);
			employeeDAO.updateAvatar(url, user.getEmployeeID());

			return "profile";
		} catch (IOException e) {
			return "error";

		} catch (EmployeeException e) {
			return "error";
		} finally {
			avatar.delete();
		}

	}
}
