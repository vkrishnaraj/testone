package com.bagnet.nettracer.hibernate;

import java.io.File;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import com.bagnet.nettracer.tracing.utils.TracerProperties;
/**
 * @author Ankur Gupta
 * 
 * This class provides helper routine to obtain hibernate sessions.
 */
public class HibernateWrapper {

	private static Logger logger = Logger.getLogger(HibernateWrapper.class);
	private static AnnotationConfiguration cfg_prod = new AnnotationConfiguration();
	private static AnnotationConfiguration cfg_prod_dirty = new AnnotationConfiguration();
	private static AnnotationConfiguration cfg_demo = new AnnotationConfiguration();
	private static AnnotationConfiguration cfg_qa = new AnnotationConfiguration();
	
	private static AnnotationConfiguration cfg_ntbak = new AnnotationConfiguration();
	private static AnnotationConfiguration cfg_nt = new AnnotationConfiguration();

	private static SessionFactory sf_prod;
	private static SessionFactory sf_prod_dirty;
	private static SessionFactory sf_demo;
	private static SessionFactory sf_qa;
	
	private static SessionFactory sf_ntbak;
	private static SessionFactory sf_nt;
	private static String hibernate_main_path = HibernateWrapper.class.getResource("/hibernate_main.cfg.xml").getPath();
	private static String hibernate_dirty_path = HibernateWrapper.class.getResource("/hibernate_dirty.cfg.xml").getPath();
	

	static {
		try {

			if (TracerProperties.get("app_type").equals("qa")) {
//				addClasses(cfg_qa);
				sf_qa = cfg_qa.configure(HibernateWrapper.class.getResource("/hibernate_qa.cfg.xml")).buildSessionFactory();

			} else if (TracerProperties.get("app_type").equals("demo")) {
//				addClasses(cfg_demo);
				sf_demo = cfg_demo.configure(HibernateWrapper.class.getResource("/hibernate_demo.cfg.xml")).buildSessionFactory();
			} else {
//				addClasses(cfg_prod);
				sf_prod = cfg_prod.configure(HibernateWrapper.class.getResource("/hibernate_main.cfg.xml")).buildSessionFactory();
				sf_prod_dirty = cfg_prod_dirty.configure(HibernateWrapper.class.getResource("/hibernate_dirty.cfg.xml")).buildSessionFactory();
//				sf_prod = cfg_prod.configure(new File(hibernate_main_path)).buildSessionFactory();
			}
		} catch (Exception e) {
			logger.fatal("Unable to initiate hibernate: " + e);
			e.printStackTrace();
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
			

			
			if (TracerProperties.get("app_type").equals("qa")) {
				if (sf_qa == null) {
//					addClasses(cfg_qa);
					String hibernate_qa_path = HibernateWrapper.class.getResource("/hibernate_qa.cfg.xml").getPath();
					sf_qa = cfg_qa.configure(new File(hibernate_qa_path)).buildSessionFactory();
				}
				return sf_qa;
			} else if (TracerProperties.get("app_type").equals("demo")) {
				if (sf_demo == null) {
//					addClasses(cfg_demo);
					String hibernate_demo_path = HibernateWrapper.class.getResource("/hibernate_demo.cfg.xml").getPath();
					sf_demo = cfg_demo.configure(new File(hibernate_demo_path)).buildSessionFactory();
				}
				return sf_demo;
			} else {
				if (sf_prod == null) {
//					addClasses(cfg_prod);
					sf_prod = cfg_prod.configure(new File(hibernate_main_path)).buildSessionFactory();
				}
				return sf_prod;
			}
		} catch (Exception e) {
			logger.fatal("unable to initiate hibernate: " + e);
			return null;
		}
		
	}
	
	/**
	 * Retrieve the session from the session factory
	 * 
	 * @return session factory
	 */
	public static SessionFactory getDirtySession() {
		try {
			// Obtain the correct session factory.
			

			
			if (TracerProperties.get("app_type").equals("qa")) {
				if (sf_qa == null) {
//					addClasses(cfg_qa);
					String hibernate_qa_path = HibernateWrapper.class.getResource("/hibernate_qa.cfg.xml").getPath();
					sf_qa = cfg_qa.configure(new File(hibernate_qa_path)).buildSessionFactory();
				}
				return sf_qa;
			} else if (TracerProperties.get("app_type").equals("demo")) {
				if (sf_demo == null) {
//					addClasses(cfg_demo);
					String hibernate_demo_path = HibernateWrapper.class.getResource("/hibernate_demo.cfg.xml").getPath();
					sf_demo = cfg_demo.configure(new File(hibernate_demo_path)).buildSessionFactory();
				}
				return sf_demo;
			} else {
				if (sf_prod_dirty == null) {
//					addClasses(cfg_prod);
					sf_prod_dirty = cfg_prod_dirty.configure(new File(hibernate_dirty_path)).buildSessionFactory();
				}
				return sf_prod_dirty;
			}
		} catch (Exception e) {
			logger.fatal("unable to initiate hibernate: " + e);
			return null;
		}
		
	}

	public static AnnotationConfiguration getConfig() {
		if (TracerProperties.get("app_type").equals("qa")) {
			return cfg_qa;
		} else if (TracerProperties.get("app_type").equals("demo")) {
			return cfg_demo;
		} else {
			return cfg_prod;
		}
	}
	public static SessionFactory getNtSession() {
		try {
			// Obtain the correct session factory.	
			        if (sf_nt==null){
//					addClasses(cfg_nt);			
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
//					addClasses(cfg_ntbak);			
					sf_ntbak = cfg_ntbak.configure(new File(HibernateWrapper.class.getResource("/hibernate_main_bak.cfg.xml").getPath())).buildSessionFactory();
			        }
				    return sf_ntbak;
		} catch (Exception e) {
			logger.fatal("unable to initiate nt bak hibernate: " + e);
			return null;
		}		
	}
	public static AnnotationConfiguration getNtConfig() {
		return cfg_nt;
    }
    public static AnnotationConfiguration getNtBakConfig() {
	    return cfg_ntbak;
    }
    
	/*private static void addClasses(AnnotationConfiguration cfg) throws Exception {

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
		cfg.addClass(Lz.class);
		
		//WorldTracer Stuff
		cfg.addClass(Worldtracer_Actionfiles.class);
		cfg.addAnnotatedClass(WorldTracerQueue.class);
		cfg.addClass(WT_ROH.class);
		cfg.addClass(WT_Info.class);
		cfg.addClass(WT_TTY.class);


		cfg.addClass(Lz.class);
		cfg.addClass(WT_Queue.class);
		cfg.addClass(WT_ROH.class);
		cfg.addClass(WT_Info.class);
		cfg.addClass(Rule.class);
		cfg.addClass(Property.class);
	}
*/

}