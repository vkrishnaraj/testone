package com.bagnet.nettracer.cronjob;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.HttpClient;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.BDO;
import com.bagnet.nettracer.tracing.db.Company_Specific_Variable;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Message;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.Recipient;
import com.bagnet.nettracer.tracing.db.WT_Queue;
import com.bagnet.nettracer.tracing.db.WT_FWD_Log;
import com.bagnet.nettracer.tracing.db.WT_ROH;
import com.bagnet.nettracer.tracing.db.WT_TTY;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.wt.WTIncident;
import com.bagnet.nettracer.wt.WTOHD;
import com.bagnet.nettracer.wt.WTBDO;
import com.bagnet.nettracer.wt.WorldTracerUtils;
import com.bagnet.nettracer.wt.WorldTracerQueueUtils;
import com.bagnet.nettracer.hibernate.HibernateWrapper;

public class WorldTracerActionQueue extends Thread {
	
	private static Configuration cfg = new Configuration();
	private static String hibernate_main_path = HibernateWrapper.class.getResource("/hibernate_main.cfg.xml").getPath();
	
	private static Logger logger = Logger.getLogger(WorldTracerActionQueue.class);
    
	private static String company;
	private static HttpClient client;
	private WorldTracerQueueUtils wtqu = new WorldTracerQueueUtils();
	private static WorldTracerActionQueue wtaction;



	public static void main(String args[]) {

		try {


			cfg.configure(new File(hibernate_main_path)).buildSessionFactory();

			wtaction = new WorldTracerActionQueue(cfg.getProperties());
		
			client =
				WorldTracerUtils.connectWT(WorldTracerUtils.getWt_suffix_airline(company) +
				 "/",company);
			wtaction.run();
			Company_Specific_Variable csv = AdminUtils.getCompVariable(company);
			if (csv != null) {
			int wt_write_enabled = csv.getWt_write_enabled();
			if (wt_write_enabled == 1){
			WorldTracerActionQueue wtaction = new WorldTracerActionQueue(cfg.getProperties());
			wtaction.run();
			}
			}

		} catch (Exception e) {

		}
	}
	
	public WorldTracerActionQueue(Properties properties) {
		try {
			
			company = properties.getProperty("company.code");
			client = WorldTracerUtils.connectWT(WorldTracerUtils.getWt_suffix_airline(company) + "/",company);
		} catch (Exception e) {
			logger.fatal("unable to read the properties." + e);
			return;
		}
	}

	public WorldTracerActionQueue() {

	}
	
	public void run() {
		try {

			while (true) {

				try { 

					if (this.existincident()) {
						System.out.println("begin forward incident to wt in 1s:");
						this.ForwardIncidenttoWT();
						pause(1);
					} else {
						System.out.println("begin forward incident to wt in 10s:");
						pause(10);
					}
					
					if (this.existohd()) {
						System.out.println("begin forward ohd to wt in 1s:");
						this.ForwardOHDtoWT();
						pause(1);
					} else {
						System.out.println("begin forward ohd to wt in 10s:");
						pause(10);
					}
					
					
					if (this.existcloseIncident()) {
						System.out.println("begin forward closeIncident to wt in 1s:");
						this.ForwardCloseIncidenttoWT();
						pause(1);
					} else {
						System.out.println("begin forward closeIncident to wt in 10s:");
						pause(10);
					}
					
					
					if (this.existcloseOHD()) {
						System.out.println("begin forward closeOHD to wt in 1s:");
						this.ForwardCloseOHDtoWT();
						pause(1);
					} else {
						System.out.println("begin forward closeOHD to wt in 10s:");
						pause(10);
					}
					
					  if(this.existroh())
					  { 
						  System.out.println("begin forward roh to wt in 1s:"); 
						  this.ForwardROHtoWT(); 
						  pause(1); 
						  } 
					  else{
					      System.out.println("begin forward roh to wt in 10s:");
					      pause(10); 
					  } 
					  
					  if(this.existfwd())
					  { 
						  System.out.println("begin forward fwd to wt in 1s:"); 
						  this.ForwardFWDtoWT(); 
					      pause(1); 
					      }
					  else{ 
						  System.out.println("begin forward fwd to wt in 10s:"); 
						  pause(10); } 
					
					  if(this.existtty())
					  {
					      System.out.println("begin forward tty to wt in 1s:");
					      this.ForwardTTYtoWT(); 
					      pause(1); 
					  } 
					  else{
					      System.out.println("begin forward tty to wt in 10s:");
					      pause(10); 
					  }
					
					 
					 
				} catch (Exception e) {
					logger.fatal("cron move to wtthread error: " + e);
				}

				logger.info("waiting for 10s to send mbr/ohd to WT again...");

				// pause(1);
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





	

	public void ForwardIncidenttoWT() throws Exception {

		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();

			String wtsql = "select wtqueue from com.bagnet.nettracer.tracing.db.WT_Queue  wtqueue where (wtqueue.queue_status != -1 and wtqueue.queue_status <3 and wtqueue.type = 'Incident')";
			String sql = "select incident from com.bagnet.nettracer.tracing.db.Incident   incident where "
					+ "incident.incident_ID = :type_id";

			Query q = sess.createQuery(sql);
			Query wtq = sess.createQuery(wtsql);
			wtq.setFirstResult(0);
			wtq.setMaxResults(5);
			List wtlist = wtq.list();

			WTIncident wt = null;
			WT_Queue wtqueue = null;
			Incident inc = null;
			


			if (wtlist != null && wtlist.size() > 0) {
				for (int i = 0; i < wtlist.size(); i++) {

					wtqueue = (WT_Queue) wtlist.get(i);
					String type_id = wtqueue.getType_id();
					
					q.setParameter("type_id", wtqueue.getType_id());
					List list = q.list();
					for (int j = 0; j < list.size(); j++) {
						inc = (Incident) list.get(j);

						wt = new WTIncident();
						if (inc != null) {
							//System.out.println(inc.getStationcode());
							this.sendmessage(inc.getStationcreated(), "incident",
									inc.getAgent(), "ok", inc.getIncident_ID(),
							"");

							String result = wt.insertIncident(client, company, inc.getIncident_ID()); 
							if (result == null) {
								result =wt.getError();
								logger.error(String.format("unable to input WT incident %s with following response:\n%s", inc.getIncident_ID(), result));
							}
							else {
								Transaction tx = sess.beginTransaction();
								wtqueue.setQueue_status(-1);
								sess.update(wtqueue);
								tx.commit();
								logger.info(String.format("inserted NT Incident: %s into WT as DAH: %s", inc.getIncident_ID(), result)); 
							} 
						}
					}
				}
			}
		} catch (Exception e) {
			logger.fatal("error incident into wt: " + e);
		}
	}
	

	public void ForwardCloseIncidenttoWT() throws Exception {

		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();

			String wtsql = "select wtqueue from com.bagnet.nettracer.tracing.db.WT_Queue  wtqueue where (wtqueue.queue_status != -1 and wtqueue.queue_status < 3 and wtqueue.type = 'closeIncident')";
			String sql = "select incident from com.bagnet.nettracer.tracing.db.Incident   incident where "
					+ "incident.incident_ID = :type_id";
			
			Query q = sess.createQuery(sql);
			Query wtq = sess.createQuery(wtsql);
			wtq.setFirstResult(0);
			wtq.setMaxResults(5);
			List wtlist = wtq.list();

			WTIncident wt = null;
			WT_Queue wtqueue = null;
			Incident inc = null;
			/*
		    HttpClient client =
			WorldTracerUtils.connectWT(WorldTracerUtils.wt_suffix_airline +
			"/",company);
            */
			if (wtlist != null && wtlist.size() > 0) {
				for (int i = 0; i < wtlist.size(); i++) {

					wtqueue = (WT_Queue) wtlist.get(i);
					String type_id = wtqueue.getType_id();
					// System.out.println(type_id);
					q.setParameter("type_id", wtqueue.getType_id());
					List list = q.list();
					for (int j = 0; j < list.size(); j++) {
						inc = (Incident) list.get(j);

						wt = new WTIncident();
						if (inc != null) {
							//System.out.println(inc.getStationcode());
							this.sendmessage(inc.getStationcreated(),
											"closeincident", inc.getAgent(),
											"closeIncidentok", inc
													.getIncident_ID(), "");
							/*
							for (int k = 0; k < 3; k++) {
								String result = "failcloseincident";
								wtaciton.updatequeue(wtqueue.getType_id(), wtqueue.getType(), result);
							}
                            */
							  String result = wt.closeIncident(client, company,inc); 
							  if (result == null) 
								 result = wt.getError(); 
							  else 
							  { 
								  logger.info("inserted into wt: mbr: " +result); 
							  } 
							  logger.error("insert incident into wt: " +result);
						}
					}
					

					 
				}
				
			}
		
		} catch (Exception e) {
			logger.fatal("error incident into wt: " + e);
		}
	}
	

	public void ForwardOHDtoWT() throws Exception {

		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();

			String wtsql = "select wtqueue from com.bagnet.nettracer.tracing.db.WT_Queue  wtqueue where (wtqueue.queue_status != -1 and wtqueue.queue_status <3 and wtqueue.type = 'OHD')";
			String sql = "select ohd from com.bagnet.nettracer.tracing.db.OHD   ohd where "
					+ "ohd.OHD_ID = :type_id";

			Query q = sess.createQuery(sql);
			Query wtq = sess.createQuery(wtsql);
			wtq.setFirstResult(0);
			wtq.setMaxResults(5);
			List wtlist = wtq.list();

			WTOHD wtohd = null;
			WT_Queue wtqueue = null;
			OHD ohd = null;
			/*
			HttpClient client =
			WorldTracerUtils.connectWT(WorldTracerUtils.wt_suffix_airline +
			"/",company);
            */
			if (wtlist != null && wtlist.size() > 0) {
				for (int i = 0; i < wtlist.size(); i++) {

					wtqueue = (WT_Queue) wtlist.get(i);
					String type_id = wtqueue.getType_id();
					// System.out.println(type_id);
					q.setParameter("type_id", wtqueue.getType_id());
					List list = q.list();
					for (int j = 0; j < list.size(); j++) {
						ohd = (OHD) list.get(j);

						wtohd = new WTOHD();
						if (ohd != null) {

							
							this.sendmessage(ohd.getFoundAtStation(), "ohd", ohd
									.getAgent(), "ok", "", ohd.getOHD_ID());
			                /*
							for (int k = 0; k < 3; k++) {
								String result = "failohd";
								
								wtaciton.updatequeue(wtqueue.getType_id(), wtqueue.getType(), result);
							}
							*/
							 String result = wtohd.insertOHD(client, company,ohd.getOHD_ID()); 
							 if (result == null) 
								 result = wtohd.getError();
							 else {
								 Transaction tx = sess.beginTransaction();
								 wtqueue.setQueue_status(-1);
								 sess.update(wtqueue);
								 tx.commit();
								 logger.info(String.format("inserted NT OHD: %s into WT as DOH: %s", ohd.getOHD_ID(), result));  
							 } 
						}
					}
					
					

                     
				}
			}
		} catch (Exception e) {
			logger.fatal("error ohd into wt: " + e);
		}
	}
	

	public void ForwardCloseOHDtoWT() throws Exception {

		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();

			String wtsql = "select wtqueue from com.bagnet.nettracer.tracing.db.WT_Queue  wtqueue where (wtqueue.queue_status != -1 and wtqueue.queue_status < 3 and wtqueue.type = 'closeOHD')";
			String sql = "select ohd from com.bagnet.nettracer.tracing.db.OHD   ohd where "
					+ "ohd.OHD_ID = :type_id";

			Query q = sess.createQuery(sql);
			Query wtq = sess.createQuery(wtsql);
			wtq.setFirstResult(0);
			wtq.setMaxResults(5);
			List wtlist = wtq.list();

			WTOHD wtohd = null;
			WT_Queue wtqueue = null;
			OHD ohd = null;
			// HttpClient client =
			// WorldTracerUtils.connectWT(WorldTracerUtils.wt_suffix_airline +
			// "/",company);

			if (wtlist != null && wtlist.size() > 0) {
				for (int i = 0; i < wtlist.size(); i++) {

					wtqueue = (WT_Queue) wtlist.get(i);
					String type_id = wtqueue.getType_id();
					// System.out.println(type_id);
					q.setParameter("type_id", wtqueue.getType_id());
					List list = q.list();
					for (int j = 0; j < list.size(); j++) {
						ohd = (OHD) list.get(j);

						wtohd = new WTOHD();
						if (ohd != null) {
							
							//System.out.println(wtqueue.getType());
							this.sendmessage(ohd.getFoundAtStation(), "closeohd",
									ohd.getAgent(), "ok", "", ohd
											.getOHD_ID());
							/*
							for (int k = 0; k < 3; k++) {
								String result = "failcloseohd";
								wtaciton.updatequeue(wtqueue.getType_id(), wtqueue.getType(), result);
							}
                            */
							  String result = wtohd.closeOHD(client, company,ohd); 
							  if (result == null) 
								  result = wtohd.getError(); 
							  else { 
								  logger.info("inserted into wt:mbr: " + result); 
							  } 
							  logger.error("insert ohd into wt: " + result);
						}
					}
					
					

					 
				}
			}
		} catch (Exception e) {
			logger.fatal("error ohd into wt: " + e);
		}
	}


	public void ForwardFWDtoWT() {
		Session sess = null;
		Transaction tx = null;
		try {
			sess = HibernateWrapper.getSession().openSession();

			String sql = "select fwd from com.bagnet.nettracer.tracing.db.WT_FWD_Log as fwd where fwd_status != -1 and fwd_status < 3";

			Query q = sess.createQuery(sql);
			q.setFirstResult(0);
			q.setMaxResults(5);
			List list = q.list();

			WT_FWD_Log wtfwd = null;
			// HttpClient client =
			// WorldTracerUtils.connectWT(WorldTracerUtils.wt_suffix_airline +
			// "/",company);

			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {

					wtfwd = (WT_FWD_Log) list.get(i);
					// System.out.println(TracerUtils.getStation(wtfwd.getFwd_station_id()));
					if (wtfwd != null) {
						//System.out.println(TracerUtils.getStation(wtfwd
						//		.getFwd_station_id()));
                       
						this.sendmessage(StationBMO.getStation(wtfwd
								.getFwd_station_id()), "fwd", wtfwd
								.getForwarding_agent(), "ok", "", "");

						// a.updatefwdstatus(wtfwd.getWt_fwd_log_id());
                        /*
						for (int k = 0; k < 3; k++) {
							String result = "okfwd";
							System.out.println(result);
							wtaciton.updatefwd((wtfwd.getWt_fwd_log_id()), result);
						}
                        */
						  String result = wtqu.insertfwd(client, company, wtfwd, wtfwd.getWt_fwd_log_id());
						  /*
						  if (result ==null) 
							  result = wt.getError(); 
						  else {
						  logger.info("inserted into wt: mbr: " + result); 
						  }
						  logger.error("insert incident into wt: " + result);
						  */
					}
					

				}
			}

		} catch (Exception e) {
			tx.rollback();
			logger.fatal("error fwd into wt: " + e);
		}
	}
	
	
	public void ForwardROHtoWT() {
		Session sess = null;
		Transaction tx = null;
		try {
			sess = HibernateWrapper.getSession().openSession();

			String sql = "select roh from com.bagnet.nettracer.tracing.db.WT_ROH as roh where roh_status != -1 and roh_status<3";

			Query q = sess.createQuery(sql);
			q.setFirstResult(0);
			q.setMaxResults(5);
			List list = q.list();

			WT_ROH wtroh = null;
			// HttpClient client =
			// WorldTracerUtils.connectWT(WorldTracerUtils.wt_suffix_airline +
			// "/",company);

			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {

					wtroh = (WT_ROH) list.get(i);

					 //String result = wtqu.postWTROH(wtroh);

					// logger.error("insert roh into wt: " + result);
					if (wtroh != null) {
						/*
						System.out.println(TracerUtils.getStation(wtroh
								.getRoh_station_id()));
						*/
						tx = sess.beginTransaction();
						this.sendmessage(StationBMO.getStation(wtroh
								.getRoh_station_id()), "roh", wtroh
								.getRoh_agent(), "ok", "", "");
						/*
						for (int k = 0; k < 3; k++) {
							String result = "failedroh";
							wtaciton.updateroh((wtroh.getWt_roh_id()), result);
						}
						*/
						String result = wtqu.postWTROH(client,wtroh);
						tx.commit();
					}
				}
			}

		} catch (Exception e) {
			tx.rollback();
			logger.fatal("error roh into wt: " + e);
		}
	}

	public void ForwardTTYtoWT() {
		Session sess = null;
		Transaction tx = null;
		try {
			sess = HibernateWrapper.getSession().openSession();

			String sql = "select tty from com.bagnet.nettracer.tracing.db.WT_TTY as tty where tty_status != -1 and tty_status < 3";

			Query q = sess.createQuery(sql);
			q.setFirstResult(0);
			q.setMaxResults(5);
			List list = q.list();

			WT_TTY wttty = null;
			// HttpClient client =
			// WorldTracerUtils.connectWT(WorldTracerUtils.wt_suffix_airline +
			// "/",company);

			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {

					wttty = (WT_TTY) list.get(i);

					// String result = wtqu.postWTTTY(inc);

					// logger.error("insert tty into wt: " + result);
					if (wttty != null) {

						tx = sess.beginTransaction();
						this.sendmessage(StationBMO.getStation(wttty
								.getTty_station_id()), "tty", wttty
								.getTty_agent(), "ok", "", "");
						/*
						for (int k = 0; k < 3; k++) {
							String result = "failedtty";
							// System.out.println(result);
							wtaciton.updatetty((wttty.getTty_id()), result);
						}
						*/
						String result = wtqu.postWTTTY(client,wttty);
						tx.commit();
					}
				}
			}

		} catch (Exception e) {
			tx.rollback();
			logger.fatal("error tty into wt: " + e);
		}
	}
	public void ForwardBDOtoWT() throws Exception {

		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();

			String wtsql = "select wtqueue from com.bagnet.nettracer.tracing.db.WT_Queue  wtqueue where (wtqueue.queue_status != -1 and wtqueue.queue_status <3 and wtqueue.type = 'bdo')";
			String sql = "select bdo from com.bagnet.nettracer.tracing.db.BDO   bdo where "
					+ "bdo.BDO_ID = :type_id";

			Query q = sess.createQuery(sql);
			Query wtq = sess.createQuery(wtsql);
			wtq.setFirstResult(0);
			wtq.setMaxResults(5);
			List wtlist = wtq.list();

			WTBDO wt = null;
			WT_Queue wtqueue = null;
			BDO inc = null;
			


			if (wtlist != null && wtlist.size() > 0) {
				for (int i = 0; i < wtlist.size(); i++) {

					wtqueue = (WT_Queue) wtlist.get(i);
					String type_id = wtqueue.getType_id();
					
					q.setParameter("type_id", wtqueue.getType_id());
					List list = q.list();
					for (int j = 0; j < list.size(); j++) {
						inc = (BDO) list.get(j);

						wt = new WTBDO();
						if (inc != null) {
							//this.sendmessage(inc.getStation(), "bdo",
							//		inc.getAgent(), "ok", inc.getBDO_ID(),
							//		"");
				
	                        /*
							for (int k = 0; k < 3; k++) {
								String result = "failincident";
								
								wtaciton.updatequeue((wtqueue.getType_id()), wtqueue.getType(),result);
							}
							*/
							
							  String result = wt.insertBDO(client, company,inc.getBDO_ID()); 
							  if (result == null) 
								  result =wt.getError(); 
							  else { 
								  logger.info("inserted into wt: mbr: " + result); 
								  } 
							  logger.error("insert bdo into wt: " +result);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.fatal("error incident into wt: " + e);
		}
	}
	
	public void sendmessage(Station station, String subject, Agent user,
			String message, String incident_id, String ohd_id) {
		Message msg = new Message();
		msg.setSend_date(TracerDateTime.getGMTDate());

		msg.setAgent(user);
		msg.setSend_station(station);

		String body = message;
		msg.setMessage(body);
		msg.setSubject(subject + incident_id);
		if (incident_id == "" && ohd_id == "") {
			msg.setFile_type(-1);
			msg.setFile_ref_number("");
		}

		if (incident_id != "") {
			msg.setFile_ref_number(incident_id);
			msg.setFile_type(1);
		}
		if (ohd_id != "") {
			msg.setFile_type(0);
			msg.setFile_ref_number(ohd_id);
		}
		Recipient rec = new Recipient();
		rec.setCompany_code(station.getCompany().getCompanyCode_ID());
		rec.setStation(station);
		rec.setMessage(msg);
		List newRecpList = new ArrayList();
		newRecpList.add(rec);

		msg.setRecipients(new HashSet(newRecpList));
		HibernateUtils.save(msg);
	}



	public void updatequeue(String type_id, String type, String result) {
		String theresult = result;
		Session sess = HibernateWrapper.getSession().openSession();
		if (theresult == ("ok")) {
			System.out.println("delete queue(incident,ohd,close):");
			
			this.deletequeue(type_id, type);
            
			
			return;
		}
		System.out.println(type_id);
		System.out.println(type);
		
		if (this.queryqueuestatus(type_id, type) < 3) {
			System.out.println("send "+(this.queryqueuestatus(type_id,type)+1));
			String s = "update wt_queue set queue_status= queue_status+1 where '"+type_id+"' = type_id and type = '"+type+"'";

		    Transaction t = sess.beginTransaction();

		    Query q = sess.createSQLQuery(s);
		    int i = q.executeUpdate();

		    t.commit();
       
		}
		
 
	}
	
	public int queryqueuestatus(String type_id, String type) {
		Session sess = HibernateWrapper.getSession().openSession();
		Transaction tx = null;
		int queuestatus = 0;
		String s = "select wtqueue from com.bagnet.nettracer.tracing.db.WT_Queue  wtqueue where (type_id= '"+type_id+"' and type = '"+type+"') ";

		Query q = sess.createQuery(s);

		List list = q.list();

		WT_Queue wt_queue = null;

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {

				wt_queue = (WT_Queue) list.get(i);

				if (wt_queue != null) {

					tx = sess.beginTransaction();
					queuestatus = wt_queue.getQueue_status();

					tx.commit();
				}

			}
		}
		return queuestatus;
	}
	
	public void updatefwd(int wt_fwd_log_id, String result) {
		String theresult = result;
		Session sess = HibernateWrapper.getSession().openSession();
		if (theresult == ("ok")) {
			System.out.println("update fwd_status = -1 :");
			System.out.println("forward fwd to wt successed.");
			this.updatefwdstatus(wt_fwd_log_id);
			return;
		}
		if (this.queryfwdstatus(wt_fwd_log_id) < 3) {
			System.out.println("send "+(this.queryfwdstatus(wt_fwd_log_id)+1));
		

			String s = "update wt_fwd_log set fwd_status= fwd_status+1 where (wt_fwd_log_id= '"
					+ wt_fwd_log_id + "')";

			Transaction t = sess.beginTransaction();

			Query q = sess.createSQLQuery(s);
			int i = q.executeUpdate();

			t.commit();

		}

	}

	public int queryfwdstatus(int wt_fwd_log_id) {
		Session sess = HibernateWrapper.getSession().openSession();
		Transaction tx = null;
		int fwdstatus = 0;
		String s = "select wtfwd from com.bagnet.nettracer.tracing.db.WT_FWD_Log  wtfwd where (wt_fwd_log_id= '"
				+ wt_fwd_log_id + "')";

		Query q = sess.createQuery(s);

		List list = q.list();

		WT_FWD_Log wt_fwd = null;

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {

				wt_fwd = (WT_FWD_Log) list.get(i);

				if (wt_fwd != null) {

					tx = sess.beginTransaction();
					fwdstatus = wt_fwd.getFwd_status();

					tx.commit();
				}

			}
		}
		return fwdstatus;
	}

	public void updatefwdstatus(int wt_fwd_log_id) {
		String s = "update wt_fwd_log set fwd_status= -1 where (wt_fwd_log_id= '"
				+ wt_fwd_log_id + "')";

		Session sess = HibernateWrapper.getSession().openSession();

		Transaction t = sess.beginTransaction();

		Query q = sess.createSQLQuery(s);
		int i = q.executeUpdate();

		t.commit();
	}

	public void updateroh(int wt_roh_id, String result) {
		String theresult = result;
		Session sess = HibernateWrapper.getSession().openSession();
		if (theresult == ("ok")) {
			System.out.println("update roh_status = -1 :");
			System.out.println("forward roh to wt successed.");
			this.updaterohstatus(wt_roh_id);
			return;
		}
		if (this.queryrohstatus(wt_roh_id) < 3) {
			System.out.println("send "+(this.queryrohstatus(wt_roh_id)+1));

			String s = "update wt_roh set roh_status= roh_status+1 where (wt_roh_id= '"
					+ wt_roh_id + "')";

			Transaction t = sess.beginTransaction();

			Query q = sess.createSQLQuery(s);
			int i = q.executeUpdate();

			t.commit();

		}

	}

	public int queryrohstatus(int wt_roh_id) {
		Session sess = HibernateWrapper.getSession().openSession();
		Transaction tx = null;
		int rohstatus = 0;
		String s = "select wtroh from com.bagnet.nettracer.tracing.db.WT_ROH  wtroh where (wt_roh_id= '"
				+ wt_roh_id + "')";

		Query q = sess.createQuery(s);

		List list = q.list();

		WT_ROH wt_roh = null;

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {

				wt_roh = (WT_ROH) list.get(i);

				if (wt_roh != null) {

					tx = sess.beginTransaction();
					rohstatus = wt_roh.getRoh_status();

					tx.commit();
				}

			}
		}
		return rohstatus;
	}

	public void updaterohstatus(int wt_roh_id) {
		String s = "update wt_roh set roh_status= -1 where (wt_roh_id = '"+wt_roh_id+"' )";

		Session sess = HibernateWrapper.getSession().openSession();

		Transaction t = sess.beginTransaction();

		Query q = sess.createSQLQuery(s);
		int i = q.executeUpdate();

		t.commit();
	}

	public void updatetty(int tty_id, String result) {
		String theresult = result;
		Session sess = HibernateWrapper.getSession().openSession();
		if (theresult == ("ok")) {
			System.out.println("update tty_status = -1 :");
			System.out.println("forward tty to wt successed.");
			this.updatettystatus(tty_id);
			return;
		}
		if (this.queryttystatus(tty_id) < 3) {
			System.out.println("send "+(this.queryttystatus(tty_id)+1));

			String s = "update wt_tty set tty_status= tty_status+1 where (tty_id= '"
					+ tty_id + "')";

			Transaction t = sess.beginTransaction();

			Query q = sess.createSQLQuery(s);
			int i = q.executeUpdate();

			t.commit();

		}

	}

	public int queryttystatus(int tty_id) {
		Session sess = HibernateWrapper.getSession().openSession();
		Transaction tx = null;
		int ttystatus = 0;
		String s = "select wttty from com.bagnet.nettracer.tracing.db.WT_TTY  wttty where (tty_id= '"
				+ tty_id + "')";

		Query q = sess.createQuery(s);

		List list = q.list();

		WT_TTY wt_tty = null;

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {

				wt_tty = (WT_TTY) list.get(i);

				if (wt_tty != null) {

					tx = sess.beginTransaction();
					ttystatus = wt_tty.getTty_status();

					tx.commit();
				}

			}
		}
		return ttystatus;
	}

	public void updatettystatus(int tty_id) {
		String s = "update wt_tty set tty_status= -1 where (tty_id= '" + tty_id
				+ "')";

		Session sess = HibernateWrapper.getSession().openSession();

		Transaction t = sess.beginTransaction();

		Query q = sess.createSQLQuery(s);
		int i = q.executeUpdate();

		t.commit();
	}

	public String existqueue() {
		Session sess = null;
		String type = null;
		try {
			sess = HibernateWrapper.getSession().openSession();

			String wtsql = "select wtqueue from com.bagnet.nettracer.tracing.db.WT_Queue  wtqueue where (wtqueue.queue_status < 3)";

			Query wtq = sess.createQuery(wtsql);

			List wtlist = wtq.list();

			WT_Queue wtqueue = null;

			if (wtlist != null && wtlist.size() > 0) {
				for (int i = 0; i < wtlist.size(); i++) {
					wtqueue = (WT_Queue) wtlist.get(i);
					type = wtqueue.getType();
				}
				return type;
			} else
				return type;
		} catch (Exception e) {
			logger.fatal("error incident into wt: " + e);
			return type;
		}

	}
	
	public boolean existcloseIncident() {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();

			String wtsql = "select wtqueue from com.bagnet.nettracer.tracing.db.WT_Queue  wtqueue where (wtqueue.queue_status != -1 and wtqueue.type = 'closeIncident' and wtqueue.queue_status != 3)";

			Query wtq = sess.createQuery(wtsql);

			List wtlist = wtq.list();

			if (wtlist != null && wtlist.size() > 0)// {
				return true;
			// }
			else
				return false;
		} catch (Exception e) {
			logger.fatal("error incident into wt: " + e);
			return false;
		}

	}
	
	public boolean existcloseOHD() {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();

			String wtsql = "select wtqueue from com.bagnet.nettracer.tracing.db.WT_Queue  wtqueue where (wtqueue.queue_status != -1 and wtqueue.type = 'closeOHD' and wtqueue.queue_status != 3)";

			Query wtq = sess.createQuery(wtsql);

			List wtlist = wtq.list();

			if (wtlist != null && wtlist.size() > 0)// {
				return true;
			// }
			else
				return false;
		} catch (Exception e) {
			logger.fatal("error incident into wt: " + e);
			return false;
		}

	}
	
	public boolean existincident() {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();

			String wtsql = "select wtqueue from com.bagnet.nettracer.tracing.db.WT_Queue  wtqueue where (wtqueue.queue_status != -1 and wtqueue.type = 'Incident' and wtqueue.queue_status != 3)";

			Query wtq = sess.createQuery(wtsql);

			List wtlist = wtq.list();

			if (wtlist != null && wtlist.size() > 0)// {
				return true;
			// }
			else
				return false;
		} catch (Exception e) {
			logger.fatal("error incident into wt: " + e);
			return false;
		}

	}

	public boolean existohd() {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();

			String wtsql = "select wtqueue from com.bagnet.nettracer.tracing.db.WT_Queue  wtqueue where (wtqueue.queue_status != -1 and wtqueue.type = 'OHD' and wtqueue.queue_status != 3)";

			Query wtq = sess.createQuery(wtsql);

			List wtlist = wtq.list();

			if (wtlist != null && wtlist.size() > 0)// {
				return true;
			// }
			else
				return false;
		} catch (Exception e) {
			logger.fatal("error ohd into wt: " + e);
			return false;
		}

	}
	
	public boolean existfwd() {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();

			String wtsql = "select wtfwd from com.bagnet.nettracer.tracing.db.WT_FWD_Log wtfwd where (wtfwd.fwd_status != -1) and wtfwd.fwd_status != 3";

			Query wtq = sess.createQuery(wtsql);

			List wtlist = wtq.list();

			if (wtlist != null && wtlist.size() > 0)// {
				return true;
			// }
			else
				return false;
		} catch (Exception e) {
			logger.fatal("error exist fwd: " + e);
			return false;
		}

	}

	public boolean existroh() {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();

			String wtsql = "select wtroh from com.bagnet.nettracer.tracing.db.WT_ROH wtroh where (wtroh.roh_status != -1 and wtroh.roh_status != 3)";

			Query wtq = sess.createQuery(wtsql);

			List wtlist = wtq.list();

			if (wtlist != null && wtlist.size() > 0)// {
				return true;
			// }
			else
				return false;
		} catch (Exception e) {
			logger.fatal("error exist roh : " + e);
			return false;
		}

	}

	public boolean existtty() {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();

			String wtsql = "select wttty from com.bagnet.nettracer.tracing.db.WT_TTY wttty where (wttty.tty_status != -1 and wttty.tty_status != 3)";

			Query wtq = sess.createQuery(wtsql);

			List wtlist = wtq.list();

			if (wtlist != null && wtlist.size() > 0)// {
				return true;
			// }
			else
				return false;
		} catch (Exception e) {
			logger.fatal("error exist tty: " + e);
			return false;
		}

	}
	
	public void deletequeue(String type_id,String type) {
		Session sess = null;
		try {
		sess = HibernateWrapper.getSession().openSession();
		String s = "delete from wt_queue  where (type_id= '"+type_id+"' and type = '"+type+"')";

        Transaction t = sess.beginTransaction();

        Query q = sess.createSQLQuery(s);
        int i = q.executeUpdate();

        t.commit();
		} catch (Exception e) {
			logger.fatal("error delete wtqueue: " + e);
			
		}
	}
	
}
