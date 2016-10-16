package com.jira.model.connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.jira.model.employee.Employee;
import com.jira.model.exceptions.SprintException;
import com.jira.model.project.ISprintDAO;
import com.jira.model.project.Issue;
import com.jira.model.project.Sprint;
import com.jira.model.project.SprintDAO;
import com.jira.model.project.WorkFlow;
import com.jira.model.sendMail.MailSender;

public class DBConnection {
	private static Connection connection;
	private static DBConnection theChosenOneDBConnection;

	private static final String DB_HOSTNAME = "localhost";
	private static final String DB_PORT = "3306";
	private static final String DB_USER = "root";
	private static final String DATABASE = "jira";
	private static final String ADDITION_SETTINGS = "?autoReconnect=true&useSSL=false";
	// private static final String DB_PASSWORD = "klavqtura";

	private static final String DB_PASSWORD = "pecataetup";

	private DBConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.connection = DriverManager.getConnection(
					"jdbc:mysql://" + DB_HOSTNAME + ":" + DB_PORT + "/" + DATABASE + ADDITION_SETTINGS, DB_USER,
					DB_PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public synchronized static Connection getConnection() {
		if (theChosenOneDBConnection == null) {
			theChosenOneDBConnection = new DBConnection();
			
			
			
			Thread mailThread = new Thread(new Runnable() {
				private ISprintDAO sprintDAO = new SprintDAO();
				private Period twoDays = Period.ofDays(2);
				private long oneDay = 21600000;

				@Override
				public void run() {
					while (true) {
						System.err.println("Hi");
						try {
							List<Sprint> activeSprints = sprintDAO.getActiveSprint();
							for (Sprint sprint : activeSprints) {
								System.err.println(sprint.getEndDate());
								if (sprint.getEndDate().isBefore(LocalDate.now().plus(twoDays))) {
									for (Issue issue : sprint.getIssues()) {
										if (!issue.getStatus().equals(WorkFlow.DONE)) {
											for (Employee emp : issue.getEmployees()) {
												String sendTo = emp.getEmail();
												String firstName = emp.getFirstName();
												String issueTitle = issue.getTitle();
												String sprintEndDate = sprint.getEndDate().toString();
												System.err.println(sendTo);
												MailSender.sendMail(sendTo, firstName, issueTitle, sprintEndDate);
											}
										}
									}
								}
							}
						} catch (SprintException e) {
							e.printStackTrace();
						}
						try {
							Thread.sleep(oneDay);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			});
			mailThread.setDaemon(true);
			mailThread.start();
		}
		return theChosenOneDBConnection.connection;
	}

}