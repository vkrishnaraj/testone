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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Company_Specific_Variable;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Lz;
import com.bagnet.nettracer.tracing.db.Remark;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.audit.Audit_Incident;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.LzUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.audit.AuditIncidentUtils;

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
	int retrieve;
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
					retrieve = rs.getInt("retrieve_actionfile_interval");

					// only move if days is greater than 0
					if (this.type == MBR && mbr > 0) moveMBRToLZ(company);
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

				pause(retrieve * 60 * 1000);

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

	public synchronized void moveMBRToLZ(String company) throws Exception {

		// Get settings
		Company_Specific_Variable csv = AdminUtils.getCompVariable(company);
		int mode = csv.getLz_mode();
		long ld_days = csv.getMbr_to_lz_days();
		long dg_days = csv.getDamaged_to_lz_days();
		long ma_days = csv.getMiss_to_lz_days();

		// Get LZ Stations
		List<Lz> lzList = (List<Lz>) LzUtils.getIncidentLzStations();

		if (ld_days + dg_days + ma_days > 0) {
			
			Session sess = null;
			try {
				
				sess = HibernateWrapper.getSession().openSession();
				
				ArrayList<Incident> incidentList = null;
				incidentList = (ArrayList) getIncidentsToMove(company, ld_days, TracingConstants.LOST_DELAY, sess, lzList);
				incidentList.addAll(getIncidentsToMove(company, dg_days, TracingConstants.MISSING_ARTICLES, sess, lzList));
				incidentList.addAll(getIncidentsToMove(company, ma_days, TracingConstants.DAMAGED_BAG, sess, lzList));
	
				ArrayList<Bucket> buckets = null;

				if (mode == TracingConstants.MOVETOLZ_MODE_ASSIGNMENT) {
					buckets = assignmentDistribute(incidentList, lzList);
					
				} else if (mode == TracingConstants.MOVETOLZ_MODE_PERCENTAGE) {
					buckets = percentDistribute(incidentList, lzList);
				}
				
				moveIncidents(buckets, sess);
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				sess.close();
			}
			
		}
	}
	
	public List<Incident> getIncidentsToMove(String company, long datediff, int itemtype, Session sess, List<Lz> lzList) {
		
		Date now = new Date();
		long nowl = now.getTime();
		datediff *= 86400000;
		nowl = nowl - datediff;
		Date righttime = new Date(nowl);
		//String dt = DateUtils.formatDate(righttime, TracingConstants.DB_DATEFORMAT, null, null);
		
		String queryStr = "from com.bagnet.nettracer.tracing.db.Incident incident where 1 = 1 "
			+ "and incident.createdate < :createdate "
			+ "and incident.status.status_ID <> " + TracingConstants.MBR_STATUS_CLOSED + " "
			+ "and incident.itemtype.itemType_ID = " + itemtype + " "
			+ " and incident.stationassigned.company.companyCode_ID = '" + company + "' ";
			
		for (int i=0; i<lzList.size(); ++i) {
			queryStr += "and incident.stationassigned.station_ID <> " + lzList.get(i).getLz_ID();
		}
		
		// logger.info("Creating query... ");
			
		Query q = sess.createQuery(queryStr);
		q.setParameter("createdate", righttime);
		return q.list();
	}
	
	
	private void moveIncidents(ArrayList<Bucket> buckets, Session sess) {
		
		Transaction t = null;
		Agent ogadmin = AdminUtils.getAgentBasedOnUsername("ogadmin", "OW");
		
		// For each bucket
		for (int x=0; x<buckets.size(); ++x) {
			Bucket bucket = buckets.get(x);
			
			// Get the station to assign the incidents to
			Lz lz = (Lz) bucket.getKey();
			Station assignTo = lz.getStation();
			
			
		  // For each incident
			for (int y=0; y<bucket.size(); ++y) {
				// Get incident
				Incident inc = (Incident) bucket.get(y);
				Agent assignedAgent = inc.getAgentassigned();
				String centralStation = assignTo.getStationcode();
				
				// Update the station code
				inc.setStationassigned(assignTo);
				
				// Prepare Remark
				String remarkText = MBR_MSG + centralStation;
				if (assignedAgent != null) {
					inc.setAgentassigned(null);
					remarkText += AGENT_MSG + centralStation;
				}
				
				Remark r = new Remark();
				r.setAgent(ogadmin);
				r.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(TracerDateTime.getGMTDate()));
				r.setRemarktext(remarkText);
				r.setIncident(inc);
				r.setRemarktype(TracingConstants.REMARK_REGULAR);
				
				Set<Remark> remarks = inc.getRemarks();
				remarks.add(r);
				
				// Save the incident
				t = sess.beginTransaction();
				sess.saveOrUpdate(inc);
				t.commit();
				
				// Audit the incident
				if ((inc.getItemtype().getItemType_ID() == TracingConstants.LOST_DELAY && inc.getAgent().getStation().getCompany().getVariable()
						.getAudit_lost_delayed() == 1)
						|| (inc.getItemtype().getItemType_ID() == TracingConstants.DAMAGED_BAG && inc.getAgent().getStation().getCompany().getVariable()
								.getAudit_damaged() == 1)
						|| (inc.getItemtype().getItemType_ID() == TracingConstants.MISSING_ARTICLES && inc.getAgent().getStation().getCompany().getVariable()
								.getAudit_missing_articles() == 1)) {
					Audit_Incident audit_dto = AuditIncidentUtils.getAuditIncident(inc, ogadmin);

					if (audit_dto != null) {
						t = sess.beginTransaction();
						sess.save(audit_dto);
						t.commit();
					}
				}
			}
		}
	}
	

	public static ArrayList<Bucket> assignmentDistribute(ArrayList<Incident> incidentList, List<Lz> lzList) {

		HashMap<Lz, Bucket> buckets = new HashMap();
		ArrayList<Bucket> retValue= new ArrayList();
		Lz key = null;
		
		// Create buckets
		Iterator iter = lzList.iterator();
		while (iter.hasNext()) {
			key = (Lz) iter.next();
			Bucket b = new Bucket();
			b.initialize(key);
			buckets.put((Lz) b.getKey(), b);
		}

		for (int x = 0; x < incidentList.size(); ++x) {
			// Get incident
			Incident inc = incidentList.get(x);
			
			// Look up the station's assigned LZ
			String stationId = inc.getStation_ID() + "";
			Station station = AdminUtils.getStation(stationId);
			Lz stationLz = station.getLz();
			
			// Look up the bucket for the assigned LZ and add incident to bucket
			Bucket bucket = buckets.get(stationLz);
			bucket.add(inc);
		}

		Object[] keySet = buckets.keySet().toArray();
		for (int x=0; x <keySet.length; ++x) {
			retValue.add(buckets.get((Lz)keySet[x]));
		}

		return retValue;
	}
	
	public static ArrayList<Bucket> percentDistribute(ArrayList<Incident> toSort, List<Lz> lzList) {

		ArrayList buckets = new ArrayList();
		Lz key = null;
		int initialSize = 0;
		int toSortIndex = 0;
		
		double itemPercent = 100 / toSort.size();
		
		// Create buckets
		Iterator iter = lzList.iterator();
		while (iter.hasNext()) {
			key = (Lz) iter.next();
			Bucket b = new Bucket();
			initialSize = b.initialize(key, toSort.size(), (Double)key.getPercent());
			buckets.add(b);
			
			// Fill bucket by capacity
			for (int x=0; x<initialSize; ++x) {
				if (x+toSortIndex < toSort.size())
					b.add(toSort.get(x+toSortIndex));
			}
			toSortIndex += initialSize;
		}
		
		// Top off buckets
		while (toSortIndex < toSort.size()) {
			double largestCapacity = 0;
			Bucket largestBucket = null;
			for (int i=0; i < buckets.size(); ++i) {
				Bucket currentBucket = (Bucket) buckets.get(i);
				double currentCapacity = currentBucket.getRemainingCapacity();
				if (currentCapacity > largestCapacity || largestBucket == null) {
					largestBucket = currentBucket;
					largestCapacity = currentCapacity;
				}
			}
			
			largestBucket.add(toSort.get(toSortIndex));
			++toSortIndex;
		}
			
		return buckets;
	}
}

class Bucket extends ArrayList {
	private Object key;
	private double percentOfWhole;
	private int totalItems;
	
	// For use by assignment algorithm.
	public void initialize(Object key) {
		this.key = key;
	}
	
	// For use by percentage algorithm
	public int initialize (Object key, int totalItems, Double percentOfWhole) {
		this.key = key;
		this.totalItems = totalItems;
		this.percentOfWhole = percentOfWhole.doubleValue();
		return (int) (percentOfWhole / (100 / totalItems));
	}
	
	public double getRemainingCapacity() {
		return 100 - (100 / totalItems * this.size());
	}
	
	public Object getKey() {
		return this.key;
	}
}