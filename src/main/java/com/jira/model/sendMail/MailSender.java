package com.jira.model.sendMail;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class MailSender {

	public static void sendMail(String sendTo, String firstName, String issueTitle, String sprintEndDate) {
		String to = sendTo;
		String from = "ideatracker@gmail.com";
		String host = "localhost";
		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", host);
		Session session = Session.getDefaultInstance(properties);

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			message.setSubject("Issue deadline coming!");

			message.setText("Hello, " + firstName + " \n your issue with title " + issueTitle
					+ " is running out of time.The sprint end date is " + sprintEndDate
					+ ". So please do it.\nHave a nice day,\nIdea Tracker");

			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.getMessage();
		}
	}
}
