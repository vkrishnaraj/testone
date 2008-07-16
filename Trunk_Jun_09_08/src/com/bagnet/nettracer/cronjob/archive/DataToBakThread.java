package com.bagnet.nettracer.cronjob.archive;

/*
 * Created on Sep 14, 2004
 *
 * Administrator
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;


import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.LostFoundBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Address;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Articles;
import com.bagnet.nettracer.tracing.db.Company_Specific_Variable;
import com.bagnet.nettracer.tracing.db.ControlLog;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Claim;
import com.bagnet.nettracer.tracing.db.Incident_Claimcheck;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.Item_Inventory;
import com.bagnet.nettracer.tracing.db.Item_Photo;
import com.bagnet.nettracer.tracing.db.Itinerary;
import com.bagnet.nettracer.tracing.db.LostAndFoundIncident;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.OHD_Address;
import com.bagnet.nettracer.tracing.db.OHD_Inventory;
import com.bagnet.nettracer.tracing.db.OHD_Itinerary;
import com.bagnet.nettracer.tracing.db.OHD_Passenger;
import com.bagnet.nettracer.tracing.db.OHD_Photo;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.db.Remark;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.Task;
import com.bagnet.nettracer.tracing.forms.LostFoundIncidentForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.BagService;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.wt.WTIncident;
import com.bagnet.nettracer.wt.WTOHD;
import com.bagnet.nettracer.wt.WorldTracerUtils;
import java.util.ListIterator;

/**
 * @author Administrator
 * 
 * create date - Sep 14, 2004
 */
public class DataToBakThread extends Thread {

	public final static int MBR = 0;
	public final static int OHD = 1;

	private static ArrayList threads;
	private static Logger logger = Logger.getLogger(DataToBakThread.class);
    private static Incident arginc= null;

	
	// db
	public int dbtype;
	private static String dbdrivername;
	private static String bak_dburl;
	private static String bak_dbuid;
	private static String bak_dbpwd;

	private String company;
	int retrieve;

	public DataToBakThread(){
		
	}
	public DataToBakThread(Properties properties) {
		try {
			Connection conn = null;
			
			
			try {
				dbdrivername = properties.getProperty("connection.driver_class");
				bak_dburl = properties.getProperty("connection.url");
				bak_dbuid = properties.getProperty("connection.username");
				bak_dbpwd = properties.getProperty("connection.password");
				this.company = properties.getProperty("company.code");
				

			} catch (Exception e) {
				logger.fatal("unable to read the properties." + e);
				return;
			}

			Class.forName(dbdrivername).newInstance();

		} catch (Exception e) {
			logger.fatal("unable to start move to bak thread: " + e);
		}

	}
	public String getcompany(){
		return company;
	}
	public void run() {
		try {

			while (true) {
				
				try {
					Company_Specific_Variable csv = this.getArchiveCompVariable(company);
					
					if (csv != null) {
						int bakohddays = csv.getBak_nttracer_ohd_data_days();
						int baklostdays = csv.getBak_nttracer_lostfound_data_days();
						int bakincidentdays = csv.getBak_nttracer_data_days();
						retrieve = csv.getRetrieve_actionfile_interval();
			
						
						//if (mbr > 0) 
							//moveIncidentToBak(5);
					 
					
                    //System.out.println("here");
					if(bakohddays>0)
					//this.moveOHDToBak(bakohddays);
					if(baklostdays>0){
                    this.moveLostandFound(baklostdays);
                    //this.delLostandFoundToBak(baklostdays);
					}
                    if(bakincidentdays>0){
					// this.moveIncidentToBak(bakincidentdays);
					//this.delIncidentToBak(bakincidentdays);
                    }
				    //System.out.println(baklostdays);
                    //this.delLostandFoundToBak(baklostdays);
					//this.delIncidentToBak(bakincidentdays);
					
					}
				} catch (Exception e) {
					logger.fatal("move to bak thread error: " + e);
				}

				logger.info("waiting for 24 hours to send incident to Bak again...");


				pause(retrieve * 60 * 1000);

			}
		} catch (Exception e) {
			logger.fatal("cron thread error: " + e);

		}
	}
    public int getretrieve(){
    	Company_Specific_Variable csv = this.getArchiveCompVariable(company);
    	retrieve = csv.getRetrieve_actionfile_interval();
    	return retrieve;
    }
	private void pause(double seconds) {
		try {
			Thread.sleep(Math.round(1000.0 * seconds));
		} catch (InterruptedException ie) {
		}
	}
	//
	public static void save(Object obj) {
		Session Asess = null;
		Transaction t = null;
		try {
			Asess = HibernateWrapper.getNtBakSession().openSession();
			t = Asess.beginTransaction();
			
			Asess.saveOrUpdate(obj);
			t.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			if (t != null) {				
					t.rollback();		
			}
		} finally {
			if (Asess != null) {
				try {
					Asess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static boolean delete(Object obj) {
		
		Session sess = null;
		Transaction t = null;
		try {
			sess = HibernateWrapper.getNtSession().openSession();
			t = sess.beginTransaction();
            sess.delete(obj);
            t.commit();       
		} catch (Exception e) {
			e.printStackTrace();
			if (t != null) {
				try {
					t.rollback();					
				} catch (Exception ex) {
				}
			}
			return false;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		//System.out.println("delete");
		return true;
	}
	
	
	public synchronized void moveLostandFound(int mbr_move_days) throws Exception {

		Session sess = null;
		try {
			
			sess = HibernateWrapper.getNtSession().openSession();				
			Date now = new Date();
			long nowl = now.getTime();
			mbr_move_days *= 86400000;
			nowl = nowl - mbr_move_days;
			Date righttime = new Date(nowl);
			String dt = DateUtils.formatDate(righttime,TracingConstants.getDBDateFormat(HibernateWrapper.getNtConfig().getProperties()),null,null);
			
	
			String sql = "from com.bagnet.nettracer.tracing.db.LostAndFoundIncident lostandfound where lostandfound.dateFoundLost < :dt";
			
			if(sess.isOpen())
		    {
			Query q = sess.createQuery(sql);

			q.setParameter("dt", righttime);

			List list = q.list();
			
			//if (sess.isOpen() == true)
			sess.close();
			
			LostAndFoundIncident inc = null;
            System.out.println("begin bak LostAndFoundIncident....");
			if (list != null && list.size() > 0) {
				for (int i=0;i<list.size();i++) {
					
					inc = (LostAndFoundIncident) list.get(i);
							
					
					

					//this.clearIncidentIds(inc);
				
					if(findLostandFoundByID(inc.getFile_ref_number()) == null){
				    DataToBakThread.save(inc);
					}
				    
								    
				}
			
			}
			System.out.println("end bak LostAndFoundIncident....");
		  }	
		} catch (Exception e) {
			logger.fatal("error lostandfoundincident into bak: " + e);
		}
	}
	
	public  List QueryIncident(int mbr_move_days) throws Exception{
        Session sess = null;       
		Incident inc = new Incident();
		List list = null;
		try {			
			sess = HibernateWrapper.getNtSession().openSession();					
			Date now = new Date();
			long nowl = now.getTime();
			mbr_move_days *= 86400000;
			nowl = nowl - mbr_move_days;
			Date righttime = new Date(nowl);
			String dt = DateUtils.formatDate(righttime,TracingConstants.getDBDateFormat(HibernateWrapper.getNtConfig().getProperties()),null,null);
			
	
			String sql = "from com.bagnet.nettracer.tracing.db.Incident incident where incident.createdate > :dt";
			
		    if(sess.isOpen())
		    {
			Query q = sess.createQuery(sql);		    
			q.setParameter("dt", righttime);	     
			list = q.list();           
			sess.close();		
		    }
		   
		} catch (Exception e) {
			logger.fatal("error query incident: " + e);
		}
		 return list;
	}
	public  List QueryOhd(int mbr_move_days) throws Exception{
        Session sess = null;       
		OHD inc = new OHD();
		List list = null;
		try {			
			sess = HibernateWrapper.getNtSession().openSession();					
			Date now = new Date();
			long nowl = now.getTime();
			mbr_move_days *= 86400000;
			nowl = nowl - mbr_move_days;
			Date righttime = new Date(nowl);
			String dt = DateUtils.formatDate(righttime,TracingConstants.getDBDateFormat(HibernateWrapper.getNtConfig().getProperties()),null,null);
			
	
			String sql = "from com.bagnet.nettracer.tracing.db.OHD ohd where ohd.close_date < :dt";
			
		    if(sess.isOpen())
		    {
			Query q = sess.createQuery(sql);		    
			q.setParameter("dt", righttime);	     
			list = q.list();           
			sess.close();		
		    }
		   
		} catch (Exception e) {
			logger.fatal("error query incident: " + e);
		}
		 return list;
	}
	public  List QueryLostandFound(int mbr_move_days) throws Exception{
        Session sess = null;       
		LostAndFoundIncident inc = new LostAndFoundIncident();
		List list = null;
		try {			
			sess = HibernateWrapper.getNtSession().openSession();					
			Date now = new Date();
			long nowl = now.getTime();
			mbr_move_days *= 86400000;
			nowl = nowl - mbr_move_days;
			Date righttime = new Date(nowl);
			String dt = DateUtils.formatDate(righttime,TracingConstants.getDBDateFormat(HibernateWrapper.getNtConfig().getProperties()),null,null);
			
	
			String sql = "from com.bagnet.nettracer.tracing.db.LostAndFoundIncident lostandfoundincident where lostandfoundincident.dateFoundLost < :dt";
			
		    if(sess.isOpen())
		    {
			Query q = sess.createQuery(sql);		    
			q.setParameter("dt", righttime);	     
			list = q.list();           
			sess.close();		
		    }
		   
		} catch (Exception e) {
			logger.fatal("error query lostandfoundincident: " + e);
		}
		 return list;
	}
	public  synchronized void  moveIncidentToBak(int mbr_move_days) throws Exception {

        
        Session sess = null;       
		

		
		try {
			sess = HibernateWrapper.getNtSession().openSession();	
			
			Date now = new Date();
			long nowl = now.getTime();
			mbr_move_days *= 86400000;
			nowl = nowl - mbr_move_days;
			Date righttime = new Date(nowl);
			String dt = DateUtils.formatDate(righttime,TracingConstants.getDBDateFormat(HibernateWrapper.getNtConfig().getProperties()),null,null);
			
	
			String sql = "from com.bagnet.nettracer.tracing.db.Incident incident where incident.createdate > :dt";
			
		    if(sess.isOpen())
		    {
			Query q = sess.createQuery(sql);		    
			q.setParameter("dt", righttime);	     
			List list = q.list();           
			sess.close();	
			Incident inc = null;
			if (list != null && list.size() > 0) {

				
				for (int i=0;i<list.size();i++) {
					this.clearIncidentIds(inc);
					inc = (Incident) list.get(i);		       
					

				    if(findIncidentByID(inc.getIncident_ID()) == null){
					    DataToBakThread.save(inc);
					    

                    }				   
			    }

		    }
			
			System.out.println("begin bak Incident....");
			System.out.println("end bak Incident....");
		   } 
		} catch (Exception e) {
			logger.fatal("error incident into bak: " + e);
		}
	
			
	}
	
	public synchronized void delIncidentToBak(int mbr_move_days) throws Exception {

		Incident inc = new Incident();

		List list = this.QueryIncident(mbr_move_days);
		System.out.println("delete incident begin:");
		try {
			
			if (list != null && list.size() > 0) {

				
				for (int i=0;i<list.size();i++) {
					
					inc = (Incident) list.get(i);
			    if(findIncidentByID(inc.getIncident_ID()) != null){			    	
				    	DataToBakThread.delete(inc);				    					    	
				    }	
				 }
			}
		} catch (Exception e) {
			logger.fatal("error incident into bak: " + e);
		}
		
	}
	public synchronized void delOhdtoBak(int mbr_move_days) throws Exception {

		OHD inc = new OHD();

		List list = this.QueryIncident(mbr_move_days);
		System.out.println("delete ohd begin:");
		try {
			
			if (list != null && list.size() > 0) {

				
				for (int i=0;i<list.size();i++) {
					
					inc = (OHD) list.get(i);
			    if(findIncidentByID(inc.getOHD_ID()) != null){			    	
				    	DataToBakThread.delete(inc);				    					    	
				    }	
				 }
			}
		} catch (Exception e) {
			logger.fatal("error ohd into bak: " + e);
		}
		
	}
	public synchronized void delLostandFoundToBak(int mbr_move_days) throws Exception {

		LostAndFoundIncident inc = new LostAndFoundIncident();

		List list = this.QueryLostandFound(mbr_move_days);
		System.out.println("delete lostandfoundincident begin:");
		try {
			
			if (list != null && list.size() > 0) {

				
				for (int i=0;i<list.size();i++) {
					
					inc = (LostAndFoundIncident) list.get(i);
			    if(findIncidentByID(inc.getFile_ref_number()) != null){			    	
				    	DataToBakThread.delete(inc);				    					    	
				    }	
				 }
			}
		} catch (Exception e) {
			logger.fatal("error lostandfoundincident into bak: " + e);
		}
		
	}
	
	public synchronized void moveOHDToBak(int mbr_move_days) throws Exception {

		Session sess = null;
		
		OHD inc = new OHD();
		List list = null;

		try {
			
			sess = HibernateWrapper.getNtSession().openSession();
	
			
			Date now = new Date();
			long nowl = now.getTime();
			mbr_move_days *= 86400000;
			nowl = nowl - mbr_move_days;
			Date righttime = new Date(nowl);
			String dt = DateUtils.formatDate(righttime,TracingConstants.getDBDateFormat(HibernateWrapper.getNtConfig().getProperties()),null,null);


			String sql = "from com.bagnet.nettracer.tracing.db.OHD  ohd where ohd.close_date < :dt";
			
		
			Query q = sess.createQuery(sql);

			q.setParameter("dt", righttime);

			list = q.list();
			sess.close();
			
			//OHD inc = null;
			
			
			System.out.println("begin bak OHD....");
			if (list != null && list.size() > 0) {
				for (int i=0;i<list.size();i++) {
					
					inc = (OHD) list.get(i);
					this.clearOHDIds(inc);
					if(findOhdByID(inc.getOHD_ID()) == null){	
				    DataToBakThread.save(inc);
					}
								    
				}
		
			}
			System.out.println("end bak OHD....");
		} catch (Exception e) {
			logger.fatal("error incident into bak: " + e);
		}
	}
	public Incident clearIncidentIds(Incident inc) {
		if (inc.getPassengers() != null && inc.getPassengers().size() > 0) {
			for (Iterator i = inc.getPassengers().iterator(); i.hasNext();) {
				Passenger o = (Passenger) i.next();
				o.setPassenger_ID(0);
				if (o.getMembership() != null) {
					o.getMembership().setMembership_ID(0);
				}

				if (o.getAddresses() != null && o.getAddresses().size() > 0) {
					for (Iterator j = o.getAddresses().iterator(); j.hasNext();) {
						Address o2 = (Address) j.next();
						o2.setAddress_ID(0);
					}
				}
			}
		}

		if (inc.getItinerary() != null && inc.getItinerary().size() > 0) {
			for (Iterator i = inc.getItinerary().iterator(); i.hasNext();) {
				Itinerary o = (Itinerary) i.next();
				o.setItinerary_ID(0);
			}
		}

		if (inc.getClaimchecks() != null && inc.getClaimchecks().size() > 0) {
			for (Iterator i = inc.getClaimchecks().iterator(); i.hasNext();) {
				Incident_Claimcheck o = (Incident_Claimcheck) i.next();
				o.setClaimcheck_ID(0);
			}
		}

		if (inc.getItemlist() != null && inc.getItemlist().size() > 0) {
			for (Iterator i = inc.getItemlist().iterator(); i.hasNext();) {
				Item o = (Item) i.next();
				o.setItem_ID(0);

				if (o.getInventory() != null && o.getInventory().size() > 0) {
					for (Iterator j = o.getInventory().iterator(); j.hasNext();) {
						Item_Inventory o2 = (Item_Inventory) j.next();
						o2.setInventory_ID(0);
					}
				}

				if (o.getPhotoes() != null && o.getPhotoes().size() > 0) {
					for (Iterator j = o.getPhotoes().iterator(); j.hasNext();) {
						Item_Photo o2 = (Item_Photo) j.next();
						o2.setPhoto_ID(0);
					}
				}

			}
		}

		if (inc.getArticles() != null && inc.getArticles().size() > 0) {
			for (Iterator i = inc.getArticles().iterator(); i.hasNext();) {
				Articles o = (Articles) i.next();
				o.setArticles_ID(0);
			}
		}

		if (inc.getRemarks() != null && inc.getRemarks().size() > 0) {
			for (Iterator i = inc.getRemarks().iterator(); i.hasNext();) {
				Remark o = (Remark) i.next();
				o.setRemark_ID(0);
			}
		}

		return inc;
	}
	
	public Incident findIncidentByID(String incident_ID) {
		Session sess = null;

		try {
			sess = HibernateWrapper.getNtBakSession().openSession();
			Query q = sess.createQuery("from com.bagnet.nettracer.tracing.db.Incident incident where incident.incident_ID= :incident_ID");
			q.setParameter("incident_ID", incident_ID);
			List list = q.list();

			if (list.size() == 0) {
				logger.debug("unable to find incident: " + incident_ID);
				return null;
			}
			Incident iDTO = (Incident) list.get(0);

			return iDTO;
		} catch (Exception e) {
			logger.error("unable to retrieve incident: " + e);
			e.printStackTrace();
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.error("unable to close connection: " + e);
				}
			}
		}
	}
	
	
	public OHD clearOHDIds(OHD inc) {
		if (inc.getMembership() != null) {
			inc.getMembership().setMembership_ID(0);
		}
		
		if (inc.getPassenger() != null && inc.getPassengers().size() > 0) {
			for (Iterator i = inc.getPassengers().iterator(); i.hasNext();) {
				OHD_Passenger o = (OHD_Passenger) i.next();
				o.setPassenger_id(0);
				
				
				if (o.getAddresses() != null && o.getAddresses().size() > 0) {
					for (Iterator j = o.getAddresses().iterator(); j.hasNext();) {
						OHD_Address o2 = (OHD_Address) j.next();
						o2.setAddress_ID(0);
					}
				}
			}
		}

		if (inc.getItinerary() != null && inc.getItinerary().size() > 0) {
			for (Iterator i = inc.getItinerary().iterator(); i.hasNext();) {
				OHD_Itinerary o = (OHD_Itinerary) i.next();
				o.setItinerary_ID(0);
			}
		}

		
		if (inc.getItems() != null && inc.getItems().size() > 0) {
			for (Iterator i = inc.getItems().iterator(); i.hasNext();) {
				OHD_Inventory o = (OHD_Inventory) i.next();
				o.setOHD_Inventory_ID(0);
			}
		}
		
		if (inc.getPhotos() != null && inc.getPhotos().size() > 0) {
			for (Iterator i = inc.getPhotos().iterator(); i.hasNext();) {
				OHD_Photo o = (OHD_Photo) i.next();
				o.setPhoto_ID(0);
			}
		}
		
		if (inc.getTasks() != null && inc.getTasks().size() > 0) {
			for (Iterator i = inc.getTasks().iterator(); i.hasNext();) {
				Task o = (Task) i.next();
				o.setTask_id(0);
			}
		}
		
		if (inc.getControlLog() != null && inc.getControlLog().size() > 0) {
			for (Iterator i = inc.getControlLog().iterator(); i.hasNext();) {
				ControlLog o = (ControlLog) i.next();
				o.setControl_id(0);
			}
		}
		
		if (inc.getRemarks() != null && inc.getRemarks().size() > 0) {
			for (Iterator i = inc.getRemarks().iterator(); i.hasNext();) {
				Remark o = (Remark) i.next();
				o.setRemark_ID(0);
			}
		}
		
		return inc;
	}
	public OHD findOhdByID(String ohd_ID) {
		Session sess = null;

		try {
			sess = HibernateWrapper.getNtBakSession().openSession();
			Query q = sess.createQuery("from com.bagnet.nettracer.tracing.db.OHD ohd where ohd.OHD_ID= :ohd_ID");
			q.setParameter("ohd_ID", ohd_ID);
			List list = q.list();

			if (list.size() == 0) {
				logger.debug("unable to find ohd: " + ohd_ID);
				return null;
			}
			OHD iDTO = (OHD) list.get(0);

			return iDTO;
		} catch (Exception e) {
			logger.error("unable to retrieve ohd: " + e);
			e.printStackTrace();
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.error("unable to close connection: " + e);
				}
			}
		}
	}
	public LostAndFoundIncident findLostandFoundByID(String LostandFound_ID) {
		Session sess = null;

		try {
			sess = HibernateWrapper.getNtBakSession().openSession();
			Query q = sess.createQuery("from com.bagnet.nettracer.tracing.db.LostAndFoundIncident lostandfoundincident where lostandfoundincident.file_ref_number = :LostandFound_ID");
			q.setParameter("LostandFound_ID", LostandFound_ID);
			List list = q.list();

			if (list.size() == 0) {
				logger.debug("unable to find lostandfound: " + LostandFound_ID);
				return null;
			}
			LostAndFoundIncident iDTO = (LostAndFoundIncident) list.get(0);

			return iDTO;
		} catch (Exception e) {
			logger.error("unable to retrieve lostandfound: " + e);
			e.printStackTrace();
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.error("unable to close connection: " + e);
				}
			}
		}
	}
	public static Company_Specific_Variable getArchiveCompVariable(String companycode) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Company_Specific_Variable.class).add(Expression.eq("companyCode_ID", companycode));
			return (Company_Specific_Variable) cri.list().get(0);
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}
}


