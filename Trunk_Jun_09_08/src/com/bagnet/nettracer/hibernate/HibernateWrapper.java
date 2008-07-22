package com.bagnet.nettracer.hibernate;

import java.io.File;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


import com.bagnet.nettracer.cronjob.NettracerCron;
import com.bagnet.nettracer.tracing.db.Address;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Agent_Logger;
import com.bagnet.nettracer.tracing.db.AirlineMembership;
import com.bagnet.nettracer.tracing.db.Airport;
import com.bagnet.nettracer.tracing.db.Articles;
import com.bagnet.nettracer.tracing.db.BDO;
import com.bagnet.nettracer.tracing.db.BDO_Passenger;
import com.bagnet.nettracer.tracing.db.Billing;
import com.bagnet.nettracer.tracing.db.Claim;
import com.bagnet.nettracer.tracing.db.ClaimProrate;
import com.bagnet.nettracer.tracing.db.Company;
import com.bagnet.nettracer.tracing.db.Company_Specific_Variable;
import com.bagnet.nettracer.tracing.db.Company_specific_irregularity_code;
import com.bagnet.nettracer.tracing.db.ControlLog;
import com.bagnet.nettracer.tracing.db.CountryCode;
import com.bagnet.nettracer.tracing.db.Currency;
import com.bagnet.nettracer.tracing.db.DbLocale;
import com.bagnet.nettracer.tracing.db.DeliverCo_Station;
import com.bagnet.nettracer.tracing.db.DeliverCompany;
import com.bagnet.nettracer.tracing.db.Deliver_ServiceLevel;
import com.bagnet.nettracer.tracing.db.ExpensePayout;
import com.bagnet.nettracer.tracing.db.ExpenseType;
import com.bagnet.nettracer.tracing.db.GroupComponentPolicy;
import com.bagnet.nettracer.tracing.db.IATA_irregularity_code;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Incident_Assoc;
import com.bagnet.nettracer.tracing.db.Incident_Claimcheck;
import com.bagnet.nettracer.tracing.db.Incident_Range;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.ItemType;
import com.bagnet.nettracer.tracing.db.Item_Inventory;
import com.bagnet.nettracer.tracing.db.Item_Photo;
import com.bagnet.nettracer.tracing.db.Itinerary;
import com.bagnet.nettracer.tracing.db.LogEvent;
import com.bagnet.nettracer.tracing.db.LogEventThrowable;
import com.bagnet.nettracer.tracing.db.LostAndFoundIncident;
import com.bagnet.nettracer.tracing.db.LostAndFound_Photo;
import com.bagnet.nettracer.tracing.db.LostAndFound_Range;
import com.bagnet.nettracer.tracing.db.Lz;
import com.bagnet.nettracer.tracing.db.Manufacturer;
import com.bagnet.nettracer.tracing.db.Match;
import com.bagnet.nettracer.tracing.db.Match_Detail;
import com.bagnet.nettracer.tracing.db.Message;
import com.bagnet.nettracer.tracing.db.MessageCopy;
import com.bagnet.nettracer.tracing.db.NTDateFormat;
import com.bagnet.nettracer.tracing.db.NTTimeFormat;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.OHDRequest;
import com.bagnet.nettracer.tracing.db.OHD_Address;
import com.bagnet.nettracer.tracing.db.OHD_CategoryType;
import com.bagnet.nettracer.tracing.db.OHD_Inventory;
import com.bagnet.nettracer.tracing.db.OHD_ItemType;
import com.bagnet.nettracer.tracing.db.OHD_Itinerary;
import com.bagnet.nettracer.tracing.db.OHD_Log;
import com.bagnet.nettracer.tracing.db.OHD_Log_Itinerary;
import com.bagnet.nettracer.tracing.db.OHD_Passenger;
import com.bagnet.nettracer.tracing.db.OHD_Photo;
import com.bagnet.nettracer.tracing.db.OHD_Range;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.db.Priority;
import com.bagnet.nettracer.tracing.db.Prorate_Itinerary;
import com.bagnet.nettracer.tracing.db.Recipient;
import com.bagnet.nettracer.tracing.db.Remark;
import com.bagnet.nettracer.tracing.db.Resolution;
import com.bagnet.nettracer.tracing.db.State;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.SystemComponent;
import com.bagnet.nettracer.tracing.db.Task;
import com.bagnet.nettracer.tracing.db.TimeZone;
import com.bagnet.nettracer.tracing.db.UserGroup;
import com.bagnet.nettracer.tracing.db.Webservice_Session;
import com.bagnet.nettracer.tracing.db.Work_Shift;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles;
import com.bagnet.nettracer.tracing.db.XDescElement;
import com.bagnet.nettracer.tracing.db.audit.Audit_Address;
import com.bagnet.nettracer.tracing.db.audit.Audit_Agent;
import com.bagnet.nettracer.tracing.db.audit.Audit_AirlineMembership;
import com.bagnet.nettracer.tracing.db.audit.Audit_Airport;
import com.bagnet.nettracer.tracing.db.audit.Audit_Articles;
import com.bagnet.nettracer.tracing.db.audit.Audit_Claim;
import com.bagnet.nettracer.tracing.db.audit.Audit_ClaimProrate;
import com.bagnet.nettracer.tracing.db.audit.Audit_Company;
import com.bagnet.nettracer.tracing.db.audit.Audit_Company_Specific_Variable;
import com.bagnet.nettracer.tracing.db.audit.Audit_Company_specific_irregularity_code;
import com.bagnet.nettracer.tracing.db.audit.Audit_ControlLog;
import com.bagnet.nettracer.tracing.db.audit.Audit_DeliverCo_Station;
import com.bagnet.nettracer.tracing.db.audit.Audit_DeliverCompany;
import com.bagnet.nettracer.tracing.db.audit.Audit_Deliver_ServiceLevel;
import com.bagnet.nettracer.tracing.db.audit.Audit_ExpensePayout;
import com.bagnet.nettracer.tracing.db.audit.Audit_GroupComponentPolicy;
import com.bagnet.nettracer.tracing.db.audit.Audit_Incident;
import com.bagnet.nettracer.tracing.db.audit.Audit_Incident_Claimcheck;
import com.bagnet.nettracer.tracing.db.audit.Audit_Item;
import com.bagnet.nettracer.tracing.db.audit.Audit_Item_Inventory;
import com.bagnet.nettracer.tracing.db.audit.Audit_Item_Photo;
import com.bagnet.nettracer.tracing.db.audit.Audit_Itinerary;
import com.bagnet.nettracer.tracing.db.audit.Audit_LostAndFoundIncident;
import com.bagnet.nettracer.tracing.db.audit.Audit_LostAndFound_Photo;
import com.bagnet.nettracer.tracing.db.audit.Audit_OHD;
import com.bagnet.nettracer.tracing.db.audit.Audit_OHD_Address;
import com.bagnet.nettracer.tracing.db.audit.Audit_OHD_Inventory;
import com.bagnet.nettracer.tracing.db.audit.Audit_OHD_Itinerary;
import com.bagnet.nettracer.tracing.db.audit.Audit_OHD_Passenger;
import com.bagnet.nettracer.tracing.db.audit.Audit_OHD_Photo;
import com.bagnet.nettracer.tracing.db.audit.Audit_OHD_Remark;
import com.bagnet.nettracer.tracing.db.audit.Audit_Passenger;
import com.bagnet.nettracer.tracing.db.audit.Audit_Prorate_Itinerary;
import com.bagnet.nettracer.tracing.db.audit.Audit_Remark;
import com.bagnet.nettracer.tracing.db.audit.Audit_Station;
import com.bagnet.nettracer.tracing.db.audit.Audit_UserGroup;
import com.bagnet.nettracer.tracing.db.audit.Audit_Work_Shift;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.db.WT_FWD_Log;
import com.bagnet.nettracer.tracing.db.WT_FWD_Log_Itinerary;
import com.bagnet.nettracer.tracing.db.WT_TTY;
import com.bagnet.nettracer.tracing.db.WT_Queue;
import com.bagnet.nettracer.tracing.db.WT_ROH;
/**
 * @author Ankur Gupta
 * 
 * This class provides helper routine to obtain hibernate sessions.
 */
public class HibernateWrapper {

	private static Logger logger = Logger.getLogger(HibernateWrapper.class);
	private static Configuration cfg_prod = new Configuration();
	private static Configuration cfg_demo = new Configuration();
	private static Configuration cfg_qa = new Configuration();
	
	private static Configuration cfg_ntbak = new Configuration();
	private static Configuration cfg_nt = new Configuration();

	private static SessionFactory sf_prod;
	private static SessionFactory sf_demo;
	private static SessionFactory sf_qa;
	
	private static SessionFactory sf_ntbak;
	private static SessionFactory sf_nt;
	private static String hibernate_main_path = HibernateWrapper.class.getResource("/hibernate_main.cfg.xml").getPath();
	

	static {
		try {
			// start the move to lz cron
			NettracerCron cron = new NettracerCron();
			
			
			if (TracerUtils.getTracerProperty("app_type").equals("qa")) {
				addClasses(cfg_qa);
				String hibernate_qa_path = HibernateWrapper.class.getResource("/hibernate_qa.cfg.xml").getPath();
				sf_qa = cfg_qa.configure(new File(hibernate_qa_path)).buildSessionFactory();
			
				cron.runCron(cfg_qa.getProperties());
			} else if (TracerUtils.getTracerProperty("app_type").equals("demo")) {
				addClasses(cfg_demo);
				String hibernate_demo_path = HibernateWrapper.class.getResource("/hibernate_demo.cfg.xml").getPath();
				sf_demo = cfg_demo.configure(new File(hibernate_demo_path)).buildSessionFactory();
				
				cron.runCron(cfg_demo.getProperties());
			} else {
				addClasses(cfg_prod);
				sf_prod = cfg_prod.configure(new File(hibernate_main_path)).buildSessionFactory();
				
				cron.runCron(cfg_prod.getProperties());
			}

			

		} catch (Exception e) {
			logger.fatal("Unable to initiate hibernate: " + e);
		}
	}

	/**
	 * Retrieve the session from the session factory
	 * 
	 * @return session factory
	 */
	public static SessionFactory getSession() {
		try {
			// Obtain the correct session factory.
			

			
			if (TracerUtils.getTracerProperty("app_type").equals("qa")) {
				if (sf_qa == null) {
					addClasses(cfg_qa);
					String hibernate_qa_path = HibernateWrapper.class.getResource("/hibernate_qa.cfg.xml").getPath();
					sf_qa = cfg_qa.configure(new File(hibernate_qa_path)).buildSessionFactory();
				}
				return sf_qa;
			} else if (TracerUtils.getTracerProperty("app_type").equals("demo")) {
				if (sf_demo == null) {
					addClasses(cfg_demo);
					String hibernate_demo_path = HibernateWrapper.class.getResource("/hibernate_demo.cfg.xml").getPath();
					sf_demo = cfg_demo.configure(new File(hibernate_demo_path)).buildSessionFactory();
				}
				return sf_demo;
			} else {
				if (sf_prod == null) {
					addClasses(cfg_prod);
					sf_prod = cfg_prod.configure(new File(hibernate_main_path)).buildSessionFactory();
				}
				return sf_prod;
			}
		} catch (Exception e) {
			logger.fatal("unable to initiate hibernate: " + e);
			return null;
		}
		
	}

	public static Configuration getConfig() {
		if (TracerUtils.getTracerProperty("app_type").equals("qa")) {
			return cfg_qa;
		} else if (TracerUtils.getTracerProperty("app_type").equals("demo")) {
			return cfg_demo;
		} else {
			return cfg_prod;
		}
	}
	public static SessionFactory getNtSession() {
		try {
			// Obtain the correct session factory.	
			        if (sf_nt==null){
					addClasses(cfg_nt);			
					sf_nt = cfg_nt.configure(new File(HibernateWrapper.class.getResource("/hibernate_main.cfg.xml").getPath())).buildSessionFactory();
			        }
				    return sf_nt;
		} catch (Exception e) {
			logger.fatal("unable to initiate nt hibernate: " + e);
			return null;
		}		
	}
	public static SessionFactory getNtBakSession() {
		try {
			// Obtain the correct session factory.	
			        if(sf_ntbak==null){
					addClasses(cfg_ntbak);			
					sf_ntbak = cfg_ntbak.configure(new File(HibernateWrapper.class.getResource("/hibernate_main_bak.cfg.xml").getPath())).buildSessionFactory();
			        }
				    return sf_ntbak;
		} catch (Exception e) {
			logger.fatal("unable to initiate nt bak hibernate: " + e);
			return null;
		}		
	}
	public static Configuration getNtConfig() {
		return cfg_nt;
    }
    public static Configuration getNtBakConfig() {
	    return cfg_ntbak;
    }
	private static void addClasses(Configuration cfg) throws Exception {

		cfg.addClass(Audit_Work_Shift.class);
		cfg.addClass(Audit_Company.class);
		cfg.addClass(Audit_Company_Specific_Variable.class);
		cfg.addClass(Audit_Company_specific_irregularity_code.class);
		cfg.addClass(Audit_Airport.class);
	
		cfg.addClass(Audit_Deliver_ServiceLevel.class);
		cfg.addClass(Audit_DeliverCo_Station.class);
		cfg.addClass(Audit_DeliverCompany.class);
		
		cfg.addClass(Audit_Agent.class);
		cfg.addClass(Audit_Station.class);
		cfg.addClass(Audit_UserGroup.class);
		cfg.addClass(Audit_GroupComponentPolicy.class);

		cfg.addClass(Audit_Remark.class);
		cfg.addClass(Audit_OHD_Remark.class);
		cfg.addClass(Audit_AirlineMembership.class);

		// Audit OHD's
		cfg.addClass(Audit_OHD.class);
		cfg.addClass(Audit_OHD_Passenger.class);
		cfg.addClass(Audit_OHD_Address.class);
		cfg.addClass(Audit_OHD_Inventory.class);
		cfg.addClass(Audit_OHD_Photo.class);
		cfg.addClass(Audit_OHD_Itinerary.class);
		cfg.addClass(Audit_ControlLog.class);
        

		// WT FWD
		cfg.addClass(WT_FWD_Log.class);
		cfg.addClass(WT_FWD_Log_Itinerary.class);
		// Audit Unchecked property
		cfg.addClass(Audit_LostAndFoundIncident.class);
		cfg.addClass(Audit_LostAndFound_Photo.class);
		// Audit Claims

		cfg.addClass(Audit_Claim.class);
		cfg.addClass(Audit_ExpensePayout.class);
		cfg.addClass(Audit_ClaimProrate.class);
		cfg.addClass(Audit_Prorate_Itinerary.class);

		// Audit Incidents
		cfg.addClass(Audit_Address.class);
		cfg.addClass(Audit_Articles.class);
		cfg.addClass(Audit_Incident_Claimcheck.class);
		cfg.addClass(Audit_Incident.class);
		cfg.addClass(Audit_Item_Photo.class);
		cfg.addClass(Audit_Item_Inventory.class);
		cfg.addClass(Audit_Item.class);
		cfg.addClass(Audit_Itinerary.class);
		cfg.addClass(Audit_Passenger.class);

		cfg.addClass(SystemComponent.class);
		cfg.addClass(Airport.class);
		cfg.addClass(LostAndFound_Range.class);
		cfg.addClass(LostAndFoundIncident.class);
		cfg.addClass(LostAndFound_Photo.class);
		cfg.addClass(GroupComponentPolicy.class);
		cfg.addClass(UserGroup.class);
		cfg.addClass(Company.class);
		cfg.addClass(Agent.class);
		cfg.addClass(Agent_Logger.class);
		cfg.addClass(Passenger.class);
		cfg.addClass(Address.class);
		cfg.addClass(Incident.class);
		cfg.addClass(Station.class);
		cfg.addClass(Item.class);
		cfg.addClass(Resolution.class);
		cfg.addClass(AirlineMembership.class);
		cfg.addClass(Articles.class);
		cfg.addClass(Currency.class);
		cfg.addClass(ItemType.class);
		cfg.addClass(Manufacturer.class);
		cfg.addClass(CountryCode.class);
		cfg.addClass(State.class);
		cfg.addClass(Status.class);
		cfg.addClass(XDescElement.class);
		cfg.addClass(Remark.class);
		cfg.addClass(Itinerary.class);
		cfg.addClass(OHD.class);
		cfg.addClass(OHD_Inventory.class);
		cfg.addClass(OHD_CategoryType.class);
		cfg.addClass(OHD_ItemType.class);
		cfg.addClass(OHD_Passenger.class);
		cfg.addClass(OHD_Log.class);
		cfg.addClass(OHD_Address.class);
		cfg.addClass(Incident_Assoc.class);
		cfg.addClass(Incident_Range.class);
		cfg.addClass(OHD_Range.class);
		cfg.addClass(Claim.class);
		cfg.addClass(ExpensePayout.class);
		cfg.addClass(ExpenseType.class);

		cfg.addClass(LogEvent.class);
		cfg.addClass(LogEventThrowable.class);
		cfg.addClass(ClaimProrate.class);
		cfg.addClass(Prorate_Itinerary.class);
		cfg.addClass(NTDateFormat.class);
		cfg.addClass(OHD_Photo.class);
		cfg.addClass(NTTimeFormat.class);
		cfg.addClass(Task.class);
		cfg.addClass(Priority.class);
		cfg.addClass(ControlLog.class);
		cfg.addClass(Match.class);
		cfg.addClass(Match_Detail.class);
		cfg.addClass(OHDRequest.class);
		cfg.addClass(OHD_Itinerary.class);
		cfg.addClass(Item_Photo.class);
		cfg.addClass(Item_Inventory.class);
		cfg.addClass(Incident_Claimcheck.class);
		cfg.addClass(DbLocale.class);
		cfg.addClass(OHD_Log_Itinerary.class);
		cfg.addClass(Work_Shift.class);
		cfg.addClass(Message.class);
		cfg.addClass(Recipient.class);
		cfg.addClass(MessageCopy.class);
		cfg.addClass(Company_Specific_Variable.class);
		cfg.addClass(IATA_irregularity_code.class);
		cfg.addClass(Company_specific_irregularity_code.class);
		cfg.addClass(TimeZone.class);
		cfg.addClass(Billing.class);
		cfg.addClass(BDO.class);
		cfg.addClass(BDO_Passenger.class);
		cfg.addClass(Deliver_ServiceLevel.class);
		cfg.addClass(DeliverCo_Station.class);
		cfg.addClass(DeliverCompany.class);
		
		cfg.addClass(Webservice_Session.class);
		cfg.addClass(Worldtracer_Actionfiles.class);
		
		//TTY
		cfg.addClass(WT_TTY.class);
		
		cfg.addClass(Lz.class);
		cfg.addClass(WT_Queue.class);
		cfg.addClass(WT_ROH.class);
	}


}