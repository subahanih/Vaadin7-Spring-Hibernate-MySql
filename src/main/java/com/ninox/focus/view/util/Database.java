package com.ninox.focus.view.util;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class Database {
	@Autowired
	private static DataSource datasource;
	private static Logger logger = Logger.getLogger(Database.class);
	
	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/focus", "root", "root");
			System.out.println("Test----1");
			return con;
		}
		catch (Exception ex) {
			logger.error("Error =>", ex);
			return null;
		}
	}
	
	public static void close(Connection con) {
		try {
			con.close();
		}
		catch (Exception ex) {
		}
	}
}
