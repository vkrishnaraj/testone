/*
 * Created on Sep 14, 2004
 *
 * Administrator
 */
package com.bagnet.nettracer.cronjob.lz;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
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
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Company_Specific_Variable;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.IncidentControl;
import com.bagnet.nettracer.tracing.db.Lz;
import com.bagnet.nettracer.tracing.db.Remark;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.audit.Audit_Incident;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.LzUtils;
import com.bagnet.nettracer.tracing.utils.SmsEmailService;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.audit.AuditIncidentUtils;

/**
 * @author Administrator
 * 
 * create date - Sep 14, 2004
 */
public class MoveToLZThread {

	public final static int MBR = 0;
	public final static int OHD = 1;

	private static final Logger logger = Logger.getLogger(MoveToLZThread.class);

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

	private String company;
	
	public MoveToLZThread(int type) {
		
		try {
			Configuration foo = HibernateWrapper.getConfig();
			Properties properties = foo.getProperties();
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
	
	public MoveToLZThread(Properties properties,int type) {
		try {
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
			this.type = type;
			this.company = company;
			logger.info("Starting move to lz...");
			
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
	

	@SuppressWarnings("unchecked")
	public void moveToLz() {
		Session sess = null;
		try {

				// rotate through companies

				sess = HibernateWrapper.getDirtySession().openSession();
				String sql = "select mbr_to_lz_days, damaged_to_lz_days, miss_to_lz_days, ohd_to_lz_days, companycode_ID from company_specific_variable order by companycode_ID";
				if (company.length() > 0) sql = "select mbr_to_lz_days, damaged_to_lz_days, miss_to_lz_days, ohd_to_lz_days, companycode_ID from company_specific_variable where companycode_ID = '" + company + "'";
				
				int mbr = 0;	// days to move lost/delay
				int damaged = 0;	// days to move damaged
				int missing = 0;	// days to move missing articles
				int ohd = 0;
				
				
				
				Connection conn = DriverManager.getConnection(mbr_dburl, mbr_dbuid, mbr_dbpwd);
				Statement st = conn.createStatement();

				SQLQuery q = sess.createSQLQuery(sql);
//				ResultSet rs = st
//						.executeQuery(sql);
				@SuppressWarnings("unchecked")
				List<Object[]> results=(List<Object[]>)q.list();

				Object[] row;
				//while (rs.next()) {
				for(int i=0;i<results.size();++i){
					row=(Object[])results.get(i);
					mbr = (Integer)row[0];//rs.getInt("mbr_to_lz_days");
					damaged = (Integer)row[1];//rs.getInt("damaged_to_lz_days");
					missing =(Integer)row[2]; //rs.getInt("miss_to_lz_days");
					ohd =(Integer)row[3]; //rs.getInt("ohd_to_lz_days");
					company =(String)row[4]; //rs.getString("companycode_ID");
					

					// only move if days is greater than 0
					if (this.type == MBR && mbr > 0) moveMBRToLZ(company);
				}
				
				if (this.type == MBR )
					logger.info("waiting for 24 hours to move mbr again...");
				else
					logger.info("waiting for 24 hours to move ohd again...");
				// pause a day
				

				// fix data corruptions
//				st.executeUpdate("update item set bdo_id = null,status_ID=47 WHERE (BDO_ID NOT IN (SELECT bdo_id FROM bdo))");
				st.executeUpdate("delete from message_copies where (message_id not in (select message_id from message))");
				
				sql = "SELECT station.companycode_id,incident.stationcreated_id,incident.incident_id,incident.createdate, incident.agent_id FROM incident join station on incident.stationcreated_id = station.station_id WHERE (Incident_ID NOT IN (SELECT report_num FROM billing))";
				//st = conn.createStatement();
				//rs = st.executeQuery(sql);
				
				Statement st2 = conn.createStatement();
				q = sess.createSQLQuery(sql);
//				ResultSet rs = st
//						.executeQuery(sql);
				results=q.list();

				//while (rs.next()) {
				for(int i=0;i<results.size();++i){
					row=(Object[])results.get(i);
					try {
						st2.executeUpdate("insert into billing (companyCode,station_id,report_num,create_date_time,status_change_time,agent_id) values ('" 
								+ ((String)row[0]) + "'," + ((Integer)row[1]) + ",'" + ((String)row[2])+ "','" + ((String)row[3]) + "','" + ((String)row[3]) + "'," + ((Integer)row[4]) + ")");
					
						logger.info("insert into billing (companyCode,station_id,report_num,create_date_time,status_change_time,agent_id) values ('" 
								+ ((String)row[0]) + "'," + ((Integer)row[1]) + ",'" + ((String)row[2])+ "','" + ((String)row[3]) + "','" + ((String)row[3]) + "'," + ((Integer)row[4]) + ")");
					} catch (Exception e) {
						logger.error("unable to update billing: " + e);
					}
				}

		} catch (Exception e) {
			logger.fatal("cron thread error: " + e);
		} finally {
			if (sess != null) sess.close();
		}
	}

	public synchronized void moveMBRToLZ(String companyCode) throws Exception {

		// Get settings
		Company_Specific_Variable csv = AdminUtils.getCompVariable(companyCode);
		int mode = csv.getLz_mode();
		long ld_days = csv.getMbr_to_lz_days();
		long dg_days = csv.getDamaged_to_lz_days();
		long ma_days = csv.getMiss_to_lz_days();

		// Get LZ Stations
		List<Lz> lzList = LzUtils.getIncidentLzStations(companyCode);

		if (ld_days + dg_days + ma_days > -3) {
			
			Session sess = null;
			try {
				
				sess = HibernateWrapper.getSession().openSession();
				
				ArrayList<Incident> incidentList = new ArrayList<Incident>();
				incidentList.addAll(getIncidentsToMove(company, ld_days, TracingConstants.LOST_DELAY, sess, lzList));
				incidentList.addAll(getIncidentsToMove(company, ma_days, TracingConstants.MISSING_ARTICLES, sess, lzList));
				incidentList.addAll(getIncidentsToMove(company, dg_days, TracingConstants.DAMAGED_BAG, sess, lzList));
	
				ArrayList<Bucket> buckets = null;

				if (incidentList.size() > 0) {
					if (mode == TracingConstants.MOVETOLZ_MODE_ASSIGNMENT) {
						buckets = assignmentDistribute(incidentList, lzList);
						
					} else if (mode == TracingConstants.MOVETOLZ_MODE_PERCENTAGE) {
						buckets = percentDistribute(incidentList, lzList);
					}
				}
				
				if (buckets != null) {
					moveIncidents(buckets, sess);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (sess != null) sess.close();
			}
			
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Incident> getIncidentsToMove(String company, long datediff, int itemtype, Session sess, List<Lz> lzList) {
		if (datediff == -1) {
			return new ArrayList<Incident>();
		}
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
			queryStr += " and incident.stationassigned.station_ID <> " + lzList.get(i).getStation().getStation_ID();
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
				
				// TODO: Update incident newFangledAssignedDate - Byron
				//get the id of the IncidentControl obj
				IncidentControl myIncidentControl = inc.getIncidentControl();
				
				if (myIncidentControl == null) {
					myIncidentControl = new IncidentControl();
					myIncidentControl.setAssignedDate(inc.getLastupdated());
					myIncidentControl.setIncident(inc);
					sess.save(myIncidentControl);
				} else {
					myIncidentControl.setAssignedDate(TracerDateTime.getGMTDate());
					//sess.saveOrUpdate(myIncidentControl);
					sess.update(myIncidentControl);	
				}
				
				
				// Save the incident
				inc.setLastupdated(TracerDateTime.getGMTDate());
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
				
				//TODO: sms and email trigger
				// move to LZ - transfer to HDQ - triggers SMS and email message 
				// sent to pax
				SmsEmailService smsEmailService = new SmsEmailService();
				smsEmailService.sendMoveToLzMessage(inc);
				
			}
		}
	}
	

	@SuppressWarnings("unchecked")
	public static ArrayList<Bucket> assignmentDistribute(ArrayList<Incident> incidentList, List<Lz> lzList) {

		HashMap<Lz, Bucket> buckets = new HashMap<Lz, Bucket>();
		ArrayList<Bucket> retValue= new ArrayList<Bucket>();
		Lz key = null;
		
		// Create buckets
		for (Lz lz: lzList) {
			key = lz;
			Bucket b = new Bucket();
			b.initialize(key);
			buckets.put((Lz) b.getKey(), b);
		}

		// Populate buckets
		for (Incident inc: incidentList) {

			// Look up the station's assigned LZ
			String stationId = inc.getStation_ID() + "";
			Station station = StationBMO.getStation(stationId);
			Lz stationLz = LzUtils.getLz(station);
			
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
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Bucket> percentDistribute(ArrayList<Incident> toSort, List<Lz> lzList) {

		ArrayList<Bucket> buckets = new ArrayList<Bucket>();
		Lz key = null;
		int initialSize = 0;
		int toSortIndex = 0;
		
		// Create buckets
		Iterator<Lz> iter = lzList.iterator();
		while (iter.hasNext()) {
			key = (Lz) iter.next();
			Bucket b = new Bucket();
			initialSize = b.initialize(key, toSort.size(), (Double)key.getPercent_load());
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
	/**
	 * 
	 */
	private static final long serialVersionUID = 8150811945279528887L;
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
