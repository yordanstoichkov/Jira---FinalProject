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
import com.jira.model.exceptions.IsssueExeption;
import com.jira.model.project.Issue;
import com.jira.model.project.IssueDAO;

@Controller
public class UploadController {
	@Autowired
	private S3Connection s3Con;
	@Autowired
	private IssueDAO issueDAO;

	@RequestMapping(value = "/issue", method = RequestMethod.POST)
	public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("issueId") int issueId,
			Model model, HttpSession session) throws IsssueExeption {
		try {
			Issue issue = issueDAO.getIssue(issueId);

			Employee emp = (Employee) session.getAttribute("user");
			String[] path = file.getOriginalFilename().split("\\\\");
			String fileName = path[path.length - 1];
			File picture = new File(WebInitializer.LOCATION + fileName);
			FileCopyUtils.copy(file.getBytes(), picture);
			String url = s3Con.s3Upload(fileName, issue.getTitle() + "" + issue.getIssueId());
			issue.setFilePath(url);	
			issueDAO.addIssueFile(issue);
		} catch (IOException e) {
			throw new IsssueExeption("We can not upload this file right now. Try again later!", e);
		} catch (IsssueExeption e1) {
			throw new IsssueExeption("We can not upload this file right now. Try again later!", e1);
		}

		return "redirect:issue?issueId=" + issueId;
	}

}