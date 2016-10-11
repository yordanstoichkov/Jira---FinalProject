//package com.jira.controller.upload;
//
//import java.io.File;
//import java.io.IOException;
//
//import org.springframework.ui.ModelMap;
//import org.springframework.util.FileCopyUtils;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.jira.WebInitializer;
//
//public class UploadController {
//	@RequestMapping(value = "/uploadFile", method = RequestMethod.GET)
//	public String showUploadPage() {
//		return "fileUpload";
//	}
//
//	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
//	public String singleFileUpload(@RequestParam("file") MultipartFile multipartFile,
//			// @RequestParam("artist") String artist,
//			ModelMap model) throws IOException {
//		// if (System.getenv().get("OS").equals("Windows")) {.getClass()..
//		String[] path = multipartFile.getOriginalFilename().split("/");
//		String fileName = path[path.length - 1];
//
//		FileCopyUtils.copy(multipartFile.getBytes(), new File(WebInitializer.LOCATION + fileName));
//
//		return "newIssue";
//	}
//
//}
