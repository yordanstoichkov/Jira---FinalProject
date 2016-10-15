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
import com.jira.model.exceptions.IssueExeption;
import com.jira.model.project.Issue;
import com.jira.model.project.IssueDAO;
import com.jira.model.project.Project;
import com.jira.model.project.Sprint;

@Controller
public class UploadController {
	@Autowired
	private S3Connection s3Con;
	@Autowired
	private IssueDAO issueDAO;
	@Autowired
	private IEmployeeDAO employeeDAO;

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("issueId") int issueId,
			Model model, HttpSession session) throws IssueExeption {
		try {
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
			String[] path = file.getOriginalFilename().split("\\\\");
			String fileName = path[path.length - 1];
			File picture = new File(WebInitializer.LOCATION + fileName);
			FileCopyUtils.copy(file.getBytes(), picture);
			String url = s3Con.s3Upload(fileName, issue.getTitle() + "" + issue.getIssueId());
			issue.setFilePath(url);	
			picture.delete();
			issueDAO.addIssueFile(issue);
		} catch (IOException e) {
			throw new IssueExeption("We can not upload this file right now. Try again later!", e);
		} catch (IssueExeption e1) {
			throw new IssueExeption("We can not upload this file right now. Try again later!", e1);
		}

		return "redirect:issue?issueId=" + issueId;
	}

	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	public String updateAvatar(@RequestParam("file") MultipartFile file, Model model, HttpSession session)
			throws IssueExeption {
		Employee user = (Employee) session.getAttribute("user");
		model.addAttribute("user", user);
		String[] path = file.getOriginalFilename().split("\\\\");
		String fileName = path[path.length - 1];
		File avatar = new File(WebInitializer.LOCATION + fileName);
		try {
			FileCopyUtils.copy(file.getBytes(), avatar);
			String url = s3Con.s3Upload(fileName, user.getEmail());
			user.setAvatarPath(url);
			employeeDAO.updateAvatar(url, user.getEmployeeID());
			
		} catch (IOException e) {
			throw new IssueExeption("We can not upload this picture right now. Try again later!", e);

		} catch (EmployeeException e) {
			throw new IssueExeption("We can not upload this picture right now. Try again later!", e);

		}
		avatar.delete();
		return "profile";
	}
}
