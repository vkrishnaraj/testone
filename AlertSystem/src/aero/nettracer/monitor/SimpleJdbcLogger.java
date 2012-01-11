package aero.nettracer.monitor;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.sql.*;

public class SimpleJdbcLogger {
	private static final String DB_DRIVER = "org.gjt.mm.mysql.Driver";
	private static final String DB_CONNECTION_STRING = "jdbc:mysql://localhost:3306/monitoring";
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = "nettracer";


	public static void main(String[] args) {
//		SimpleJdbcLogger.log("", "", new GregorianCalendar(), new GregorianCalendar(), 0, true);

	}

	public static void log(String label, String url,
			Calendar startTime, Calendar stopTime, long responseTime, boolean alertSent) {
		Connection con = getConnection();

		String createString;
		if (responseTime == 0) {
			createString = "insert into monitoring (label, url, startTime, stopTime, alertSent) values (?, ?, ?, ?, ?)";
		} else {
			createString = "insert into monitoring (label, url, startTime, stopTime, alertSent, responseTime) values (?, ?, ?, ?, ?, ?)";
		}
		try {
			PreparedStatement  ps = con.prepareStatement(createString);
			ps.setString(1, label);
			ps.setString(2, url);
			Timestamp ts1 = new Timestamp(startTime.getTime().getTime());
			
			Timestamp ts2 = null;
			if (stopTime != null) {
				ts2 = new Timestamp(stopTime.getTime().getTime());
			}
			ps.setTimestamp(3, ts1);
			ps.setTimestamp(4, ts2);
			if (responseTime > 0) {
				ps.setLong(6, responseTime);
			}
			
			ps.setBoolean(5, alertSent);
			ps.executeUpdate();
			ps.close();
			con.close();

		} catch (SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
		}

	}

	public static Connection getConnection() {
		Connection con = null;
		try {
			Class.forName(DB_DRIVER); // Class.forName("myDriver.ClassName"); ?

		} catch (java.lang.ClassNotFoundException e) {
			System.err.print("ClassNotFoundException: ");
			System.err.println(e.getMessage());
		}

		try {
			con = DriverManager.getConnection(DB_CONNECTION_STRING,
					DB_USERNAME, DB_PASSWORD);

		} catch (SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
		}

		return con;
	}

}
