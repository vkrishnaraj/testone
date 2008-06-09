/*
 * Created on Sep 14, 2004
 *
 * Administrator
 */
package com.bagnet.nettracer.cronjob;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.commons.httpclient.HttpClient;
import org.apache.log4j.Logger;

import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles;
import com.bagnet.nettracer.tracing.utils.StringUtils;
import com.bagnet.nettracer.wt.WorldTracerUtils;

/**
 * @author Administrator
 * 
 * create date - Sep 14, 2004
 */
public class RetrieveWTActionFiles extends Thread {

	private static ArrayList threads;
	private static Logger logger = Logger.getLogger(RetrieveWTActionFiles.class);

	// db
	public int dbtype;
	private static String dbdrivername;
	private static String mbr_dburl;
	private static String mbr_dbuid;
	private static String mbr_dbpwd;
	private Connection mbr_conn;

	// db types
	public static int MSSQL = 1;
	public static int MYSQL = 2;
	public static int ORACLE = 3;

	private String company;

	public RetrieveWTActionFiles(Properties properties) {
		try {
			Connection conn = null;

			try {
				dbdrivername = properties.getProperty("hibernate.connection.driver_class");
				mbr_dburl = properties.getProperty("hibernate.connection.url");
				mbr_dbuid = properties.getProperty("hibernate.connection.username");
				mbr_dbpwd = properties.getProperty("hibernate.connection.password");
				this.company = properties.getProperty("company.code");

				if (mbr_dburl.indexOf("mysql") > 0)
					dbtype = MYSQL;
				else if (mbr_dburl.indexOf("sqlserver") > 0)
					dbtype = MSSQL;
				else if (mbr_dburl.indexOf("oracle") > 0) dbtype = ORACLE;

			} catch (Exception e) {
				logger.fatal("unable to read the properties." + e);
				return;
			}

			Class.forName(dbdrivername).newInstance();

		} catch (Exception e) {
			logger.fatal("unable to start move to lz thread: " + e);
		}

	}

	public RetrieveWTActionFiles(String path, String company) {
		try {
			Connection conn = null;
			this.company = company;

			Properties properties = new Properties();
			try {
				properties.load(new FileInputStream(path + "/tracing.cfg.properties"));
				dbdrivername = properties.getProperty("db.driver_class");

				String dbt = properties.getProperty("db.type");
				if (dbt.equals("mssql")) dbtype = MSSQL;
				if (dbt.equals("mysql")) dbtype = MYSQL;
				if (dbt.equals("oracle")) dbtype = ORACLE;

				mbr_dburl = properties.getProperty("mbr.db.url");
				mbr_dbuid = properties.getProperty("mbr.db.username");
				mbr_dbpwd = properties.getProperty("mbr.db.password");
				
				this.initDBConn();
				
				

			} catch (IOException e) {
				logger.fatal("unable to read the tracing.cfg.properties file." + e);
				return;
			}

			Class.forName(dbdrivername).newInstance();

		} catch (Exception e) {
			logger.fatal("unable to start move to lz thread: " + e);
		}

	}
	
	public void initDBConn() {
		try {
			mbr_conn  = DriverManager.getConnection(mbr_dburl, mbr_dbuid, mbr_dbpwd);
		} catch (Exception e) {
			logger.fatal("unable to connect to database: " + e);
		}
	}

	public void run() {
		while (true) {
			try {

				company = "BA";

				for (int i = 1; i < 8; i++) {
					parsewt_actionfiles("FW", Integer.toString(i), company, "LHR");
					parsewt_actionfiles("AA", Integer.toString(i), company, "LHR");
					parsewt_actionfiles("WM", Integer.toString(i), company, "LHR");
					parsewt_actionfiles("SP", Integer.toString(i), company, "LHR");
					parsewt_actionfiles("AP", Integer.toString(i), company, "LHR");
					parsewt_actionfiles("EM", Integer.toString(i), company, "LHR");
					parsewt_actionfiles("CM", Integer.toString(i), company, "LHR");
					parsewt_actionfiles("LM", Integer.toString(i), company, "LHR");
					parsewt_actionfiles("PR", Integer.toString(i), company, "LHR");
				}

				logger.info("waiting for 24 hours to retrieve action files again...");

			} catch (Exception e) {
				logger.fatal("cron thread error: " + e);

			}

			pause(86400);

		}

	}

	private void pause(double seconds) {
		try {
			Thread.sleep(Math.round(1000.0 * seconds));
		} catch (InterruptedException ie) {
		}
	}

	public void parsewt_actionfiles(String wt_type, String day, String airline, String station) {
		try {
			System.out.println("******** retrieving: " + wt_type + ", day: " + day + "*************");
			
			Statement stmt = mbr_conn.createStatement();
			ResultSet rs = null;

			HttpClient client = WorldTracerUtils.connectWT("fl/", airline);
			String result = WorldTracerUtils.getActionFiles(client, airline, station, wt_type, day);

			Worldtracer_Actionfiles wa = null;
			String parsed = "";

			String sql = "";

			String ac_start = "<input type=\"hidden\" name=\"menuITEM\" value=\"";
			String ac_end = "\">";
			String ahl_start = " AHL ";
			String ahl_start2 = "\nAHL ";
			String ahl_start3 = " A/";

			String ohd_start = " OHD ";
			String ohd_start2 = "\nOHD ";
			String ohd_start3 = " O/";

			String wt_ahl_id = "";
			String wt_ohd_id = "";

			// because action files change very frequently
			// delete current action files first
			sql = "delete from worldtracer_actionfiles where day = '" + day + "' and station = '" + station + "' and airline = '" + airline + "' and action_file_type = '" + wt_type + "'";
			stmt.executeUpdate(sql);

			while ((parsed = StringUtils.ParseWTString2(result, ac_start, ac_end)) != null) {

				wt_ahl_id = StringUtils.ParseWTString(parsed, ahl_start, 10);
				if (wt_ahl_id == null) wt_ahl_id = StringUtils.ParseWTString(parsed, ahl_start2, 10);
				if (wt_ahl_id == null) wt_ahl_id = StringUtils.ParseWTString(parsed, ahl_start3, 10);
				if (wt_ahl_id != null) {
					if (wt_ahl_id.length() < 10)
						wt_ahl_id = null;
					else {
						// need to be alphanumeric
						if (!StringUtils.isAlphanumeric(wt_ahl_id)) wt_ahl_id = null;
					}
				}
				if (wt_ahl_id == null) wt_ahl_id = "";

				wt_ohd_id = StringUtils.ParseWTString(parsed, ohd_start, 10);
				if (wt_ohd_id == null) wt_ohd_id = StringUtils.ParseWTString(parsed, ohd_start2, 10);
				if (wt_ohd_id == null) wt_ohd_id = StringUtils.ParseWTString(parsed, ohd_start3, 10);
				if (wt_ohd_id != null) {
					if (wt_ohd_id.length() < 10)
						wt_ohd_id = null;
					else {
						// need to be alphanumeric
						if (!StringUtils.isAlphanumeric(wt_ohd_id)) wt_ohd_id = null;
					}
				}
				if (wt_ohd_id == null) wt_ohd_id = "";

				sql = "insert into worldtracer_actionfiles (action_file_type,action_file_text,day,station,airline,wt_incident_id,wt_ohd_id) values " + "('" + wt_type.toUpperCase() + "','" + parsed.replace("'", "''") + "','" + day + "','" + station + "','" + airline + "','" + wt_ahl_id + "','" + wt_ohd_id
						+ "')";
				stmt.executeUpdate(sql);

				int loc = result.indexOf("<input type=\"hidden\" name=\"menuITEM\" value=\"");
				result = result.substring(loc + 1);

			}

		} catch (Exception e) {
			System.out.println("error retriving wt data: " + e.toString());

		}

	}

}
