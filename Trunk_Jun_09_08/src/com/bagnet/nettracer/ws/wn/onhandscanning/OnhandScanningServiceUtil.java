package com.bagnet.nettracer.ws.wn.onhandscanning;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.hibernate.Query;
import org.hibernate.Session;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.SecurityUtils;
import com.bagnet.nettracer.tracing.utils.lookup.LookupAirlineCodes;

public class OnhandScanningServiceUtil {
	
	private static Logger logger = Logger.getLogger(OnhandScanningServiceUtil.class);
	
	/**
	 * returns agent if properly authorized as by the following criteria:
	 *   Has valid username, password and companycode
	 *   Is a webservice user
	 *   Ignores account locks
	 * 
	 * @param auth
	 * @return
	 * @throws Exception
	 */
	protected static Agent getAgent(com.bagnet.nettracer.ws.wn.pojo.xsd.Authentication auth) throws Exception{
		if(auth == null || auth.getSystemName() == null || auth.getSystemName().length() == 0 ||
				auth.getSystemPassword() == null || auth.getSystemPassword().length() == 0 ||
				auth.getAirlineCode() == null ||  auth.getAirlineCode().length() == 0){
			throw new Exception("Must provide username, password and airline code");
		} else {
			Agent agent = null;
			ActionMessages errors = new ActionMessages();
			agent = SecurityUtils.authUserNoPassword(auth.getSystemName(), auth.getAirlineCode(), 0, errors, false, null);
			if(!errors.isEmpty()){
				@SuppressWarnings("rawtypes")
				Iterator i = errors.get();
				while(i.hasNext()){
					ActionMessage message = (ActionMessage)i.next();
					if("error.user.lockedout".equals(message.getKey())){
						//As per Southwest requirements, account locks are not to be considered, skip and continue
						continue;
					} else {
						throw new Exception("Invalid username/password");
					}
				}
			}
			
			if(agent == null){
				throw new Exception("unable to authenticate user, please contact NetTracer");
			} else {
				if(agent.isWs_enabled()){
					return agent;
				} else {
					throw new Exception("user is not authorized for web services");
				}
			}
		}
	}
	
	
	/**
	 * Returns the OHD_ID of any OHD that was closed in any SWA station in the previous 5 days
	 * If multiple OHD_ID found, return the oldest
	 * 
	 * @param bagtag
	 * @return
	 */
	protected static String lzDeleteLast5Days(String bagtag, String companycode){
		if(bagtag == null || bagtag.isEmpty()){
			return null;
		}
		
		Session sess = null;
		try {
			String query = "select ohd_id from ohd o " +
					"left outer join station s on o.holding_station_ID = s.station_ID " +
					"where (claimnum=:claimnum1 or claimnum=:claimnum2) " +
					"and status_id =:status " +
					"and s.companycode_id =:companycode " +
					"and close_date > :date " +
					"order by founddate asc";
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createSQLQuery(query);
			q.setParameter("claimnum1", bagtag);
			String twoCharBagTag = null;
			try{
				twoCharBagTag = LookupAirlineCodes.getTwoCharacterBagTag(bagtag);
			} catch (Exception e){
				twoCharBagTag = bagtag;
			}
			q.setString("claimnum2", twoCharBagTag);
			q.setParameter("status", TracingConstants.OHD_STATUS_CLOSED);
			q.setParameter("companycode", companycode);
			
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(DateUtils.convertToGMTDate(new Date()));
			c.add(Calendar.DATE, -5);
			
			q.setParameter("date", c.getTime());
			@SuppressWarnings("unchecked")
			List<String> list = (List<String>)q.list();
			if (list.size() == 0) {
				logger.debug("unable to find ohd with bagtag: " + bagtag);
				return null;
			}
			return (String) list.get(0);
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
	
	/**
	 * Searches damage, non-closed incidents at LZ by either bagtag or expedite tag 
	 * 
	 * @param bagtag
	 * @return
	 */
	protected static String lzDamagedList(String bagtag, int assignedStationID){
		if(bagtag == null || bagtag.isEmpty() || assignedStationID == 0){
			return null;
		}
		
		Session sess = null;
		try {
			String query = "select i.incident_id from incident i " +
					"left outer join item it on i.incident_ID = it.incident_ID  " +
					"where (it.claimchecknum=:claimnum1 or it.claimchecknum=:claimnum2 " +
					"or it.expediteTagNum=:claimnum1 or it.expediteTagNum=:claimnum2) " +
					"and i.status_id !=:status " +
					"and stationassigned_id =:assignedStationID " +
					"and i.itemtype_ID = :type " +
					"order by createdate desc";
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createSQLQuery(query);
			q.setParameter("claimnum1", bagtag);
			String twoCharBagTag = null;
			try{
				twoCharBagTag = LookupAirlineCodes.getTwoCharacterBagTag(bagtag);
			} catch (Exception e){
				twoCharBagTag = bagtag;
			}
			q.setString("claimnum2", twoCharBagTag);
			q.setParameter("status", TracingConstants.MBR_STATUS_CLOSED);
			q.setParameter("assignedStationID", assignedStationID);
			q.setParameter("type", TracingConstants.DAMAGED_BAG);
			
			@SuppressWarnings("unchecked")
			List<String> list = (List<String>)q.list();
			if (list.size() == 0) {
				logger.debug("unable to find incident with bagtag: " + bagtag);
				return null;
			}
			return (String) list.get(0);
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
	
	
	/**
	 * Searches damaged, non-closed incidents at any station by bagtag or expedite
	 * 
	 * @param bagtag
	 * @return
	 */
	protected static String lzDamagedBagBSO(String bagtag, String companycode){
		if(bagtag == null || bagtag.isEmpty()){
			return null;
		}
		
		Session sess = null;
		try {
			String query = "select i.incident_id from incident i " +
					"left outer join station s on i.stationassigned_id = s.station_ID " +
					"left outer join item it on i.incident_ID = it.incident_ID  " +
					"where (it.claimchecknum=:claimnum1 or it.claimchecknum=:claimnum2 " +
					"or it.expediteTagNum=:claimnum1 or it.expediteTagNum=:claimnum2) "  +
					"and i.status_id !=:status " +
					"and s.companycode_id =:companycode " +
					"and i.itemtype_ID = :type " +
					"order by createdate desc";
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createSQLQuery(query);
			q.setParameter("claimnum1", bagtag);
			String twoCharBagTag = null;
			try{
				twoCharBagTag = LookupAirlineCodes.getTwoCharacterBagTag(bagtag);
			} catch (Exception e){
				twoCharBagTag = bagtag;
			}
			q.setString("claimnum2", twoCharBagTag);
			q.setParameter("status", TracingConstants.MBR_STATUS_CLOSED);
			q.setParameter("companycode", companycode);
			q.setParameter("type", TracingConstants.DAMAGED_BAG);
			
			@SuppressWarnings("unchecked")
			List<String> list = (List<String>)q.list();
			if (list.size() == 0) {
				logger.debug("unable to find incident with bagtag: " + bagtag);
				return null;
			}
			return (String) list.get(0);
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
	
	
	/**
	 * Returns the OHD_ID of an onhand for the given bagtag that has a status of TBI (To be inventory)
	 * 
	 * @param bagtag
	 * @return
	 */
	protected static String lzTBI(String bagtag, int holdingStationID){
		if(bagtag == null || bagtag.isEmpty() || holdingStationID == 0){
			return null;
		}
		
		Session sess = null;
		try {
			String query = "select ohd_id from ohd " +
					"where (claimnum=:claimnum1 or claimnum=:claimnum2) " +
					"and status_id =:status " +
					"and holding_station_ID =:holding_station_ID " +
					"order by founddate desc";
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createSQLQuery(query);
			q.setParameter("claimnum1", bagtag);
			String twoCharBagTag = null;
			try{
				twoCharBagTag = LookupAirlineCodes.getTwoCharacterBagTag(bagtag);
			} catch (Exception e){
				twoCharBagTag = bagtag;
			}
			q.setString("claimnum2", twoCharBagTag);
			q.setParameter("status", TracingConstants.OHD_STATUS_TO_BE_INVENTORIED);
			q.setParameter("holding_station_ID", holdingStationID);
			@SuppressWarnings("unchecked")
			List<String> list = (List<String>)q.list();
			if (list.size() == 0) {
				logger.debug("unable to find ohd with bagtag: " + bagtag);
				return null;
			}
			return (String) list.get(0);
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
	
	/**
	 * Returns the OHD_ID of an onhand that has been created less than 45 days ago that is not closed and in the LZ
	 * 
	 * @param bagtag
	 * @return
	 */
	protected static String lz45Days(String bagtag, int holdingStationID){
		if(bagtag == null || bagtag.isEmpty() || holdingStationID == 0){
			return null;
		}
		
		Session sess = null;
		try {
			String query = "select ohd_id from ohd o " +
					"left outer join station s on o.holding_station_ID = s.station_ID " +
					"where (claimnum=:claimnum1 or claimnum=:claimnum2) " +
					"and status_id !=:status " +
					"and holding_station_ID =:holdingStationID " +
					"and founddate > :date " +
					"order by founddate desc";
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createSQLQuery(query);
			q.setParameter("claimnum1", bagtag);
			String twoCharBagTag = null;
			try{
				twoCharBagTag = LookupAirlineCodes.getTwoCharacterBagTag(bagtag);
			} catch (Exception e){
				twoCharBagTag = bagtag;
			}
			q.setString("claimnum2", twoCharBagTag);
			q.setParameter("status", TracingConstants.OHD_STATUS_CLOSED);
			q.setParameter("holdingStationID", holdingStationID);
			
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(DateUtils.convertToGMTDate(new Date()));
			c.add(Calendar.DATE, -45);
			
			q.setParameter("date", c.getTime());
			@SuppressWarnings("unchecked")
			List<String> list = (List<String>)q.list();
			if (list.size() == 0) {
				logger.debug("unable to find ohd with bagtag: " + bagtag);
				return null;
			}
			return (String) list.get(0);
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
	
	/**
	 * Search for existing bagtag. An OHD is said to exist if there is a matching bagtag, matching holdingStation and with a status of not closed
	 * 
	 * @param bagtag
	 * @param holdingStation
	 * @return
	 */
	protected static String lookupBagtag(String bagtag, int holdingStation){
		if(bagtag == null || bagtag.isEmpty() || holdingStation == 0){
			return null;
		}
		
		Session sess = null;
		try {
			String query = "select ohd_id from ohd " +
					"where (claimnum=:claimnum1 or claimnum=:claimnum2) " +
					"and status_id !=:status " +
					"and holding_station_ID =:holding_station_ID " +
					"order by founddate desc";
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createSQLQuery(query);
			q.setParameter("claimnum1", bagtag);
			String twoCharBagTag = null;
			try{
				twoCharBagTag = LookupAirlineCodes.getTwoCharacterBagTag(bagtag);
			} catch (Exception e){
				twoCharBagTag = bagtag;
			}
			q.setString("claimnum2", twoCharBagTag);
			q.setParameter("status", TracingConstants.OHD_STATUS_CLOSED);
			q.setParameter("holding_station_ID", holdingStation);
			@SuppressWarnings("unchecked")
			List<String> list = (List<String>)q.list();
			if (list.size() == 0) {
				logger.debug("unable to find ohd with bagtag: " + bagtag);
				return null;
			}
			return (String) list.get(0);
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
	
	

	/**
	 * Finds an non-closed incident for the given assigned station for the given bagtag
	 * 
	 * @param bagtag
	 * @param holdingStation
	 * @return
	 */
	protected static String findAssociatedIncident(String bagtag, int assignedStationID){
		Session sess = null;
			try {
				String query = "select i.incident_id from incident i " +
						"left outer join incident_claimcheck it on i.incident_ID = it.incident_ID  " +
						"where (it.claimchecknum=:claimnum1 or it.claimchecknum=:claimnum2) " +
						"and i.status_id !=:status " +
						"and stationassigned_id =:assignedStationID " +
						"and i.itemtype_ID = :type " +
						"order by createdate desc";
				sess = HibernateWrapper.getSession().openSession();
				Query q = sess.createSQLQuery(query);
				q.setParameter("claimnum1", bagtag);
				String twoCharBagTag = null;
				try{
					twoCharBagTag = LookupAirlineCodes.getTwoCharacterBagTag(bagtag);
				} catch (Exception e){
					twoCharBagTag = bagtag;
				}
				q.setString("claimnum2", twoCharBagTag);
				q.setParameter("status", TracingConstants.MBR_STATUS_CLOSED);
				q.setParameter("assignedStationID", assignedStationID);
				q.setParameter("type", TracingConstants.LOST_DELAY);

				@SuppressWarnings("unchecked")
				List<String> list = (List<String>)q.list();
				if (list.size() == 0) {
					logger.debug("unable to find incident with bagtag: " + bagtag);
					return null;
				}
				return (String) list.get(0);
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
	
}
