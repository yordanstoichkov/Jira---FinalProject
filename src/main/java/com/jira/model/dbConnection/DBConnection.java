package com.jira.model.dbConnection;

import java.sql.Connection;
import java.sql.DriverManager;

import org.springframework.stereotype.Component;

public class DBConnection {
	private static Connection connection;
	private static DBConnection theChosenOneDBConnection;

	private static final String DB_HOSTNAME = "localhost";
	private static final String DB_PORT = "3306";
	private static final String DB_USER = "root";
	private static final String DATABASE = "jira";
	private static final String ADDITION_SETTINGS = "?autoReconnect=true&useSSL=false";
//	 private static final String DB_PASSWORD = "klavqtura";

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
		}
		return theChosenOneDBConnection.connection;
	}

}