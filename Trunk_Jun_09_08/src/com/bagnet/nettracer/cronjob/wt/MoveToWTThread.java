/*
 * Created on Sep 14, 2004
 *
 * Administrator
 */
package com.bagnet.nettracer.cronjob.wt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.HttpClient;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Company_Specific_Variable;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.wt.WTIncident;
import com.bagnet.nettracer.wt.WTOHD;
import com.bagnet.nettracer.wt.WorldTracerUtils;
import com.bagnet.nettracer.wt.connector.BetaWtConnector;

/**
 * @author Administrator
 * 
 * create date - Sep 14, 2004
 */
public class MoveToWTThread extends Thread {

	public final static int MBR = 0;
	public final static int OHD = 1;

	private static ArrayList threads;
	private static Logger logger = Logger.getLogger(MoveToWTThread.class);

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

	private final static String MBR_MSG = "REPORT SENT TO ";
	private final static String OHD_MSG = "OHD SENT TO ";

	private Agent user;
	private String company;
	int retrieve;
	
	public MoveToWTThread(Properties properties) {
		try {
			this.company = properties.getProperty("company.code");
		} catch (Exception e) {
			logger.fatal("unable to read the properties." + e);
			return;
		}
	}
	

	public void run() {
		try {

			while (true) {
				
				try {
					Company_Specific_Variable csv = AdminUtils.getCompVariable(company);
					
					if (csv != null) {
						int mbr = csv.getMbr_to_wt_days();	
						int ohd = csv.getOhd_to_wt_hours();
						int wt_write_enabled = csv.getWt_enabled();
						int retrieve = csv.getRetrieve_actionfile_interval();
						if (wt_write_enabled == 1){
						if (mbr > 0) moveMBRToWT(mbr);
						if (ohd > 0) moveOHDToWT(ohd);
						}
					}
				} catch (Exception e) {
					logger.fatal("cron move to wt thread error: " + e);
				}

				logger.info("waiting for 24 hours to send mbr/ohd to WT again...");


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

	public synchronized void moveMBRToWT(int mbr_move_days) throws Exception {

//		Session sess = null;
//		try {
//			sess = HibernateWrapper.getNtSession().openSession();
//			// get mbrs without wt_id after x days
//			
//			Date now = new Date();
//			long nowl = now.getTime();
//			mbr_move_days *= 86400000;
//			nowl = nowl - mbr_move_days;
//			Date righttime = new Date(nowl);
//			String dt = DateUtils.formatDate(righttime,TracingConstants.getDBDateFormat(HibernateWrapper.getNtConfig().getProperties()),null,null);
//			
//	
//			String sql = "select incident from com.bagnet.nettracer.tracing.db.Incident incident where "
//				+ " (incident.wt_id is null or incident.wt_id = '') and incident.createdate < :dt and incident.stationassigned.company.companyCode_ID = :companyCode_ID"
//				+ " and incident.status.status_ID = :status_ID "
//				+ " and incident.itemtype.itemType_ID = :itemtype_ID";
//			
//		
//			Query q = sess.createQuery(sql);
//			q.setParameter("companyCode_ID", company);
//			q.setParameter("dt", righttime);
//			q.setParameter("status_ID", new Integer(TracingConstants.MBR_STATUS_OPEN));
//			q.setParameter("itemtype_ID", new Integer(TracingConstants.LOST_DELAY));
//	
//			List list = q.list();
//			WTIncident wt = null;
//			Incident inc = null;
//			
//			BetaWtConnector.getInstance(company);
//			HttpClient client = BetaWtConnector.connectWT(WorldTracerUtils.getWt_suffix_airline(company) + "/",company);
//			
//			if (list != null && list.size() > 0) {
//				for (int i=0;i<list.size();i++) {
//					
//					inc = (Incident) list.get(i);
//					wt = new WTIncident();
//					String result = wt.insertIncident(client, company, inc.getIncident_ID());
//					if (result == null) result = wt.getError();
//					else {
//						logger.info("inserted into wt: mbr: " + result);
//					}
//					logger.error("insert incident into wt: " + result);
//				}
//			}
//		} catch (Exception e) {
//			logger.fatal("error incident into wt: " + e);
//		}
	}
	
	public synchronized void moveOHDToWT(int ohd_move_days) throws Exception {

/*		Session sess = null;
		try {
			sess = HibernateWrapper.getNtSession().openSession();
			// get mbrs without wt_id after x days
			
			Date now = new Date();
			long nowl = now.getTime();
			ohd_move_days *= 86400000;
			nowl = nowl - ohd_move_days;
			Date righttime = new Date(nowl);
			String dt = DateUtils.formatDate(righttime,TracingConstants.getDBDateFormat(HibernateWrapper.getNtConfig().getProperties()),null,null);
			
	
			String sql = "select ohd from com.bagnet.nettracer.tracing.db.OHD ohd where "
				+ " (ohd.wt_id is null or ohd.wt_id = '') and ohd.founddate < :dt and ohd.holdingStation.company.companyCode_ID = :companyCode_ID"
				+ " and ohd.status.status_ID = :status_ID ";
			
		
			Query q = sess.createQuery(sql);
			q.setParameter("companyCode_ID", company);
			q.setParameter("dt", righttime);
			q.setParameter("status_ID", new Integer(TracingConstants.OHD_STATUS_OPEN));
	
			List list = q.list();
			WTOHD wt = null;
			OHD inc = null;
			HttpClient client = BetaWtConnector.connectWT(WorldTracerUtils.getWt_suffix_airline(company) + "/",company);
			
			if (list != null && list.size() > 0) {
				for (int i=0;i<list.size();i++) {
					
					inc = (OHD) list.get(i);
					wt = new WTOHD();
					String result = wt.insertOHD(client, company, inc.getOHD_ID());
					if (result == null) result = wt.getError();
					else {
						logger.info("inserted into wt: ohd: " + result);
					}
					logger.error("error insert ohd into wt: " + result);
				}
			}
		} catch (Exception e) {
			logger.fatal("error insert ohd into wt: " + e);
		}*/
	}
}

