package com.jira.controller.upload;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jira.WebInitializer;
import com.jira.model.connections.S3Connection;
import com.jira.model.employee.Employee;

public class UploadController {
	@Autowired
	private S3Connection s3Con;
	
	@RequestMapping(value = "/home", method = RequestMethod.POST)
	public String oploadFiile(@RequestParam("file") MultipartFile file, Model model, HttpSession session) {
		Employee emp = (Employee) session.getAttribute("user");
		String[] path = file.getOriginalFilename().split("\\\\");
		String fileName = path[path.length-1];
		File picture = new File(WebInitializer.LOCATION + fileName);
		try {
			FileCopyUtils.copy(file.getBytes(),picture );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String url = s3Con.s3Upload(fileName, emp.getEmail());
		model.addAttribute("picture", url);
		System.out.println(url);
		return "TestjspAjax";
	}

}
