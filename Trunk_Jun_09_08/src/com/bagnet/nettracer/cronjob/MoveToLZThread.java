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
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.utils.DateUtils;

/**
 * @author Administrator
 * 
 * create date - Sep 14, 2004
 */
public class MoveToLZThread extends Thread {

	public final static int MBR = 0;
	public final static int OHD = 1;

	private static ArrayList threads;
	private static Logger logger = Logger.getLogger(MoveToLZThread.class);

	// db
	public int dbtype;
	private static String dbdrivername;
	private static String mbr_dburl;
	private static String mbr_dbuid;
	private static String mbr_dbpwd;
	
	// db types
	public static int MSSQL = 1;
	public static int MYSQL = 2;
	public static int ORACLE = 3;
	
	private int type = MBR;

	private final static String MBR_MSG = "REPORT MOVED TO ";
	private final static String AGENT_MSG = "; ASSIGNED AGENT REMOVED FOR RE-ASSIGNMENT AT ";
	private final static String OHD_MSG = "OHD MOVED TO ";

	private String company;

	public MoveToLZThread(Properties properties,int type) {
		try {
			Connection conn = null;
			this.type = type;
			
			try {
				dbdrivername = properties.getProperty("hibernate.connection.driver_class");
				mbr_dburl = properties.getProperty("hibernate.connection.url");
				mbr_dbuid = properties.getProperty("hibernate.connection.username");
				mbr_dbpwd = properties.getProperty("hibernate.connection.password");
				this.company = properties.getProperty("company.code");
				
				if (mbr_dburl.indexOf("mysql") > 0) dbtype = MYSQL;
				else if (mbr_dburl.indexOf("sqlserver") > 0) dbtype = MSSQL;
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
	
	
	public MoveToLZThread(String path,int type,String company) {
		try {
			Connection conn = null;
			this.type = type;
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

			} catch (IOException e) {
				logger.fatal("unable to read the tracing.cfg.properties file." + e);
				return;
			}

			Class.forName(dbdrivername).newInstance();

		} catch (Exception e) {
			logger.fatal("unable to start move to lz thread: " + e);
		}

	}
	

	public void run() {
		try {

			while (true) {

				// rotate through companies
				String sql = "select * from company_specific_variable order by companycode_ID";
				if (company.length() > 0) sql = "select * from company_specific_variable where companycode_ID = '" + company + "'";
				
				int mbr = 0;	// days to move lost/delay
				int damaged = 0;	// days to move damaged
				int missing = 0;	// days to move missing articles
				int ohd = 0;
				
				
				
				Connection conn = DriverManager.getConnection(mbr_dburl, mbr_dbuid, mbr_dbpwd);
				Statement st = conn.createStatement();
				ResultSet rs = st
						.executeQuery(sql);

				while (rs.next()) {
					mbr = rs.getInt("mbr_to_lz_days");
					damaged = rs.getInt("damaged_to_lz_days");
					missing = rs.getInt("miss_to_lz_days");
					ohd = rs.getInt("ohd_to_lz_days");
					company = rs.getString("companycode_ID");

					// only move if days is greater than 0
					if (this.type == MBR && mbr > 0) moveMBRToLZ(mbr, damaged, missing, company);
					else if (this.type == OHD && ohd > 0) moveOHDToLZ(ohd, company);
				}
				
				if (this.type == MBR )
					logger.info("waiting for 24 hours to move mbr again...");
				else
					logger.info("waiting for 24 hours to move ohd again...");
				// pause a day
				
				// fix data corruptions
				
				st.executeUpdate("update item set bdo_id = null,status_ID=47 WHERE (BDO_ID NOT IN (SELECT bdo_id FROM bdo))");
				st.executeUpdate("delete from message_copies where (message_id not in (select message_id from message))");
				
				sql = "SELECT station.companycode_id,incident.* FROM incident join station on incident.stationcreated_id = station.station_id WHERE (Incident_ID NOT IN (SELECT report_num FROM billing))";
				st = conn.createStatement();
				rs = st.executeQuery(sql);
				
				Statement st2 = conn.createStatement();

				while (rs.next()) {
					try {
						st2.executeUpdate("insert into billing (companyCode,station_id,report_num,create_date_time,status_change_time,agent_id) values ('" 
								+ rs.getString("companycode_id") + "'," + rs.getInt("stationcreated_id") + ",'" + rs.getString("incident_id") + "','" + rs.getString("createdate") + "','" + rs.getString("createdate") + "'," + rs.getInt("agent_id") + ")");
					
						logger.info("insert into billing (companyCode,station_id,report_num,create_date_time,status_change_time,agent_id) values ('" 
								+ rs.getString("companycode_id") + "'," + rs.getInt("stationcreated_id") + ",'" + rs.getString("incident_id") + "','" + rs.getString("createdate") + "','" + rs.getString("createdate") + "'," + rs.getInt("agent_id") + ")");
					} catch (Exception e) {
						logger.error("unable to update billing: " + e);
					}
				}

				pause(86400);

			}
		} catch (Exception e) {
			logger.fatal("cron thread error: " + e);

		}
	}

	private void pause(double seconds) {
		try {
			Thread.sleep(Math.round(1000.0 * seconds));
		} catch (InterruptedException ie) {
		}
	}

	public synchronized void moveMBRToLZ(int mbr_move_days, int damaged_move_days, int missing_move_days, String company) throws Exception {
		Statement stmt = null;
		Statement stmt2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		
		String central_station = "LZ";

		// create connection
		Connection mbr_conn = DriverManager.getConnection(mbr_dburl, mbr_dbuid, mbr_dbpwd);

		stmt = mbr_conn.createStatement();
		stmt2 = mbr_conn.createStatement();

		String sql = null;

		long ld_datediff = mbr_move_days;
		long dg_datediff = damaged_move_days;
		long ma_datediff = missing_move_days;

		// get lz station id for current company
		sql = "select distinct station_ID,stationcode,is_lz from station where (stationcode = 'CLAIM' or is_lz = 1) and companycode_ID = '"
				+ company + "'";
		rs = stmt.executeQuery(sql);
		int lz = 0, claim = 0;
		int og_agent = 0;

		while (rs.next()) {
			if (rs.getInt("is_lz") == 1) {
				lz = rs.getInt("station_ID");
				central_station = rs.getString("stationcode");
			}

			if (rs.getString("stationcode").equals("CLAIM")) claim = rs.getInt("station_ID");
		}
		rs.close();

		// find ogadmin agent id
		sql = "select agent_ID from agent where username = 'ogadmin' and companycode_ID = 'OW'";
		rs = stmt.executeQuery(sql);
		if (rs.next()) {
			og_agent = rs.getInt("agent_ID");
		}

		if (lz > 0) { 
			if (ld_datediff > 0) {
				doMBRMove(TracingConstants.LOST_DELAY, ld_datediff,claim,lz,central_station,rs,stmt,stmt2,og_agent);
				
			}
			if (dg_datediff > 0) {
				doMBRMove(TracingConstants.DAMAGED_BAG, dg_datediff,claim,lz,central_station,rs,stmt,stmt2,og_agent);
			}
			if (ma_datediff > 0) {
				doMBRMove(TracingConstants.MISSING_ARTICLES, ma_datediff,claim,lz,central_station,rs,stmt,stmt2,og_agent);
			}
			
		}
		stmt.close();
		stmt2.close();
		mbr_conn.close();

	}
	
	private synchronized void doMBRMove(int rtype, long datediff,int claim,int lz,String central_station,ResultSet rs,Statement stmt,Statement stmt2,int og_agent) throws Exception {
//	 calculate dates
		Date now = new Date();
		long nowl = now.getTime();
		datediff *= 86400000;
		nowl = nowl - datediff;
		Date righttime = new Date(nowl);
		String dt = DateUtils.formatDate(righttime, TracingConstants.DB_DATEFORMAT, null, null);
		
		// get all incidents need to be moved for this company
		String sql = "select incident_ID, agentassigned_ID from incident,station " + " where stationassigned_ID <> " + claim
				+ " and stationassigned_ID <> " + lz + " and createdate < '" + dt
				+ "' and stationassigned_ID = station_ID and station.companycode_ID = '" + company + "'"
				+ " and itemtype_ID = " + rtype + " and status_ID <> " + TracingConstants.MBR_STATUS_CLOSED;

		rs = stmt.executeQuery(sql);

		while (rs.next()) {
			String inc = rs.getString("incident_ID");
			String agent = rs.getString("agentassigned_ID");

			logger.info("***** update Incident type " + rtype + ": " + inc);

			// move all incidents with dates greater than company assigned and
			// station not in claim
			sql = "update incident set stationassigned_ID = " + lz + ", agentassigned_ID = null where incident_ID = '" + inc
					+ "'";
			stmt2.execute(sql);
			
			String nowtime = "now()";
			if (dbtype == MoveToLZThread.MSSQL) nowtime = "getdate()";
			
			// update remark
			if (og_agent == 0) og_agent = 13;
			
			if (agent != null) {
				sql = "insert into remark (agent_ID,createtime,incident_ID,remarktext,remarktype) values (" + og_agent
				+ "," + nowtime + ",'" + inc + "','" + MBR_MSG + central_station + AGENT_MSG + central_station + "',1)";
			} else {
				sql = "insert into remark (agent_ID,createtime,incident_ID,remarktext,remarktype) values (" + og_agent
				+ "," + nowtime + ",'" + inc + "','" + MBR_MSG + central_station + "',1)";
			}

			stmt2.execute(sql);
		}

		rs.close();
		
	}
	
	public synchronized void moveOHDToLZ(int ohd_move_days, String company) throws Exception {
		Statement stmt = null;
		Statement stmt2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;

		// create connection
		Connection mbr_conn = DriverManager.getConnection(mbr_dburl, mbr_dbuid, mbr_dbpwd);

		stmt = mbr_conn.createStatement();
		stmt2 = mbr_conn.createStatement();

		String sql = null;

		long datediff = ohd_move_days;

		// get lz station id for current company
		sql = "select distinct station_ID,stationcode from station where (is_lz = 1 or stationcode = 'CLAIM') and companycode_ID = '"
				+ company + "'";
		rs = stmt.executeQuery(sql);
		int lz = 0, claim = 0;
		int og_agent = 0;

		while (rs.next()) {
			if (rs.getString("stationcode").equals("LZ")) lz = rs.getInt("station_ID");

			if (rs.getString("stationcode").equals("CLAIM")) claim = rs.getInt("station_ID");
		}
		rs.close();

		// find ogadmin agent id
		sql = "select agent_ID from agent where username = 'ogadmin' and firstname = 'system'";
		rs = stmt.executeQuery(sql);
		if (rs.next()) {
			og_agent = rs.getInt("agent_ID");
		}

		if (lz > 0 && datediff > 0) {

			// calculate dates
			Date now = new Date();
			long nowl = now.getTime();
			datediff *= 86400000;
			nowl = nowl - datediff;
			Date righttime = new Date(nowl);
			String dt = DateUtils.formatDate(righttime, TracingConstants.DB_DATEFORMAT, null, null);

			// get all incidents need to be moved for this company
			sql = "select OHD_ID from ohd,station " + " where holding_station_ID <> " + claim
					+ " and holding_station_ID <> " + lz + " and founddate < '" + dt
					+ "' and holding_station_ID = station_ID and station.companycode_ID = '" + company + "'";

			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				String inc = rs.getString("OHD_ID");

				logger.info("***** update OHD ID: " + inc);

				// move all incidents with dates greater than company assigned and
				// station not in claim
				sql = "update OHD set holding_station_ID = " + lz + " where OHD_ID = '" + inc
						+ "'";
				stmt2.execute(sql);

				// update remark
				
				String nowtime = "now()";
				if (dbtype == MoveToLZThread.MSSQL) nowtime = "getdate()";
				
				// update remark
				if (og_agent == 0) og_agent = 13;
				sql = "insert into remark (agent_ID,createtime,OHD_ID,remarktext,remarktype) values (" + og_agent
						+ "," + nowtime + ",'" + inc + "','" + OHD_MSG + "',1)";
				stmt2.execute(sql);
			}

			rs.close();
		}
		stmt.close();
		stmt2.close();
		mbr_conn.close();

	}

}

