package com.jira.controller.projects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Scope("session")
@Component
@Controller
public class IssueController {
	@RequestMapping(value = "/changeIssue", method = RequestMethod.POST)
	public String changeIssue(@RequestParam("issueId") int issueId, HttpServletRequest request) {
		
		return "changeIssue";
	}

}
