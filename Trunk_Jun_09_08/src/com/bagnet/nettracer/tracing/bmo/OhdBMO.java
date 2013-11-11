package com.bagnet.nettracer.tracing.bmo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.StaleObjectStateException;
import org.hibernate.Transaction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Expression;

import com.bagnet.nettracer.exceptions.BagtagException;
import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.constant.TracingConstants.SortParam;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.ControlLog;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.OHDRequest;
import com.bagnet.nettracer.tracing.db.OHD_Address;
import com.bagnet.nettracer.tracing.db.OHD_Inventory;
import com.bagnet.nettracer.tracing.db.OHD_Itinerary;
import com.bagnet.nettracer.tracing.db.OHD_Log;
import com.bagnet.nettracer.tracing.db.OHD_Passenger;
import com.bagnet.nettracer.tracing.db.OHD_Photo;
import com.bagnet.nettracer.tracing.db.OHD_Range;
import com.bagnet.nettracer.tracing.db.Remark;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Task;
import com.bagnet.nettracer.tracing.db.TraceOHD;
import com.bagnet.nettracer.tracing.db.audit.Audit_OHD;
import com.bagnet.nettracer.tracing.db.wtq.WorldTracerQueue.WtqStatus;
import com.bagnet.nettracer.tracing.dto.Ohd_DTO;
import com.bagnet.nettracer.tracing.forms.SearchIncidentForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.IncidentUtils;
import com.bagnet.nettracer.tracing.utils.MatchUtils;
import com.bagnet.nettracer.tracing.utils.StringUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.audit.AuditOHDUtils;
import com.bagnet.nettracer.tracing.utils.lookup.LookupAirlineCodes;

/**
 * This class provides utility methods for on-hand related functionality.
 * 
 * @author Ankur Gupta
 */
public class OhdBMO {

	private static Logger logger = Logger.getLogger(OhdBMO.class);


	/**
	 * Perform the insert based on passed in object
	 * 
	 * @param iDTO
	 *          the ohd data to be inserted
	 * @param mod_agent
	 *          agent creating the OHD.
	 * @return true if succesful; false otherwise
	 */
	public boolean insertOHD(OHD iDTO, Agent mod_agent) {
		return insertOHD(iDTO, mod_agent, (Station)null);
	}
//	
	public boolean insertOHD(OHD iDTO, Agent mod_agent, Session sess) {
		return simpleSaveAndAuditOhd(iDTO, mod_agent, null, sess);
	}
	
	/**
	 * Perform the insert based on passed in object
	 * 
	 * @param iDTO
	 *          the ohd data to be inserted
	 * @param mod_agent
	 *          agent creating the OHD.
	 * @param createStation
	 *          Station that we would like object associated with in ID(???DA00001)
	 * @return true if succesful; false otherwise
	 */
	
	
	public boolean simpleSaveAndAuditOhd(OHD iDTO, Agent mod_agent, Station createStation, Session sess) {
		if (sess == null) {
			return insertOHD(iDTO, mod_agent, createStation);
		} else {
			Transaction t = null;
			try {
				t = sess.beginTransaction();
				sess.saveOrUpdate(iDTO);
				t.commit();
				
				if (iDTO.getAgent().getStation().getCompany().getVariable().getAudit_ohd() == 1) {
					//System.out.println(iDTO.getAgent().getStation().getCompany().getVariable().getAudit_ohd());
					Audit_OHD audit_dto = AuditOHDUtils.getAuditOHD(iDTO, mod_agent);
					if (audit_dto != null) {
						t = sess.beginTransaction();
						sess.save(audit_dto);
						t.commit();
					}
				}
				return true;
			} catch (Exception e) {
				logger.error("Error saving and auditing: ", e);
				return false;
				
			}
		}
	}
	
	public boolean insertOHD(OHD iDTO, Agent mod_agent, Station createStation) {
		Transaction t = null;
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			
			OHD oldinc = null;
			String incident_id = iDTO.getOHD_ID();
			boolean isnew = false;
			if (incident_id == null || incident_id.length() <= 0) {
				isnew = true;
				if (createStation != null) 
					iDTO.setOHD_ID(getOHD_ID(createStation));
				else
					iDTO.setOHD_ID(getOHD_ID(iDTO.getAgent().getStation()));
			} else {
				oldinc = findOHDByID(incident_id);
				if (oldinc == null) isnew = true;
			}
			
			t = sess.beginTransaction();
			
			// save incident
			if (iDTO.getOHD_ID() != null) {
				iDTO.setLastupdated(TracerDateTime.getGMTDate());
				if (isnew) sess.save(iDTO);
				else {
					// delete first then insert
					if(oldinc.getWtFile() != null && iDTO.getWtFile() == null) {
						iDTO.setWtFile(oldinc.getWtFile());
					}
					sess.delete(oldinc);
					iDTO = clearOHDIds(iDTO);
					sess.save(iDTO);
				}
				t.commit();
			} else {
				return false;
			}

			// check to see if we closed the ohd, if we did, then close all matches
			if (iDTO.getStatus().getStatus_ID() == TracingConstants.OHD_STATUS_CLOSED) {
				MatchUtils.closeMatches(null, iDTO.getOHD_ID());
				ProactiveNotificationBMO.closedOhd(iDTO);
			}

			//check if audit is enabled for this company....
			if (iDTO.getAgent().getStation().getCompany().getVariable().getAudit_ohd() == 1) {
				//System.out.println(iDTO.getAgent().getStation().getCompany().getVariable().getAudit_ohd());
				Audit_OHD audit_dto = AuditOHDUtils.getAuditOHD(iDTO, mod_agent);
				if (audit_dto != null) {
					t = sess.beginTransaction();
					sess.save(audit_dto);
					t.commit();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("unable to insert ohd into database: " + e);
			try {
				t.rollback();
			} catch (Exception e1) {
			}
			return false;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.error("unable to close connection: " + e);
				}
			}
		}
		return true;
	}


	/** 
	 * insert ohd for worldtracer parser
	 * @param iDTO
	 * @return
	 */
	public boolean insertOHDForWT(OHD iDTO) {
		Transaction t = null;
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			
			OHD ohd = null;
			OHD oldinc = null;
			String ohd_id = iDTO.getOHD_ID();
			boolean isnew = false;
			if (ohd_id == null || ohd_id.length() <= 0) {
				isnew = true;
				iDTO.setOHD_ID(getOHD_ID(iDTO.getFoundAtStation()));
			} else {
				oldinc = findOHDByID(ohd_id);
				if (oldinc == null) isnew = true;
			}

			t = sess.beginTransaction();
			
			// save incident
			if (iDTO.getOHD_ID() != null) {
				iDTO.setLastupdated(TracerDateTime.getGMTDate());
				if (isnew) sess.save(iDTO);
				else {
					// delete first then insert
					sess.delete(oldinc);
					iDTO = clearOHDIds(iDTO);
					sess.save(iDTO);
				}
				t.commit();
			} else {
				return false;
			}

			// check to see if we closed the ohd, if we did, then close all matches
			if (iDTO.getStatus().getStatus_ID() == TracingConstants.OHD_STATUS_CLOSED) {
				MatchUtils.closeMatches(null, iDTO.getOHD_ID());
				ProactiveNotificationBMO.closedOhd(ohd);
			}

			//check if audit is enabled for this company....
			if (iDTO.getAgent().getStation().getCompany().getVariable().getAudit_ohd() == 1) {
				Audit_OHD audit_dto = AuditOHDUtils.getAuditOHD(iDTO, iDTO.getAgent());
				if (audit_dto != null) {
					t = sess.beginTransaction();
					sess.save(audit_dto);
					t.commit();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("unable to insert ohd into database: " + e);
			try {
				t.rollback();
			} catch (Exception e1) {
			}
			return false;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.error("unable to close connection: " + e);
				}
			}
		}
		return true;
	}
	
	public boolean updateRemarksOnly(String incident_id, Set remarks, Agent mod_agent) throws HibernateException {
		Transaction t = null;
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			
			OHD oldinc = null;
			if (incident_id == null || incident_id.length() <= 0) {
				return false;
			} else {
				oldinc = findOHDByID(incident_id);
				if (oldinc == null)
					return false;
			}
			
			t = sess.beginTransaction();
			// save incident
			if (oldinc.getOHD_ID() != null) {
				oldinc.setLastupdated(TracerDateTime.getGMTDate());
				
				if (remarks != null) {
					oldinc.setRemarks(remarks);
					for (Iterator i = oldinc.getRemarks().iterator(); i.hasNext();) {
						Remark rm = (Remark) i.next();
						rm.setOhd(oldinc);
					}
				} else {
					return false;
				}
				sess.saveOrUpdate(oldinc);
				t.commit();
			} else {
				return false;
			}

			//check if audit is enabled for this company....
			if (oldinc.getAgent().getStation().getCompany().getVariable()
					.getAudit_ohd() == 1) {
				Audit_OHD audit_dto = AuditOHDUtils.getAuditOHD(oldinc, mod_agent);

				if (audit_dto != null) {
					t = sess.beginTransaction();
					sess.save(audit_dto);
					t.commit();
				}
			}

		} catch (StaleObjectStateException e) {
			logger.error("unable to insert into database because someone else updated the table already: " + e);

			if (t != null)
				t.rollback();
			return false;
		} catch (Exception e) {
			logger.error("unable to insert into database: " + e);

			if (t != null)
				t.rollback();
			return false;
		} finally {

			sess.close();
		}
		return true;
	}
	
	/**
	 * Forward the on hand
	 * 
	 * @param log
	 *          the log that needs to be saved
	 * @param ohd
	 *          the corresponding on hand that is forwarded
	 * @param mod_agent
	 * 					Agent forwarding the bag.
	 * @return true if successful, false otherwise
	 */
	public boolean forwardOHD(OHD_Log log, OHD ohd, Agent mod_agent) {
		Transaction t = null;
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();
			if (log != null)
				sess.save(log);
			sess.update(ohd);
			t.commit();
			
			if (ohd.getAgent().getStation().getCompany().getVariable().getAudit_ohd() == 1) {
				Audit_OHD audit_dto = AuditOHDUtils.getAuditOHD(ohd, mod_agent);
				if (audit_dto != null) {
					t = sess.beginTransaction();
					sess.save(audit_dto);
					t.commit();
				}
			}

			return true;
		} catch (Exception e) {
			logger.error("unable to forward onhand bag: " + e);
			try {
				t.rollback();
			} catch (Exception e1) {
			}
			return false;
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
	 * Performs saved of the request on hand.
	 * 
	 * @param request
	 *          the request details
	 * @param ohd
	 *          the corresponding on hand
	 * @param mod_agent
	 * 					Agent forwarding the bag.
	 * @return true if successful; false otherwise
	 */
	public boolean requestOHD(OHDRequest request, OHD ohd, Agent mod_agent) {
		Transaction t = null;
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			t = sess.beginTransaction();
			sess.save(request);
			sess.update(ohd);
			t.commit();
			
			if (ohd.getAgent().getStation().getCompany().getVariable().getAudit_ohd() == 1) {
				Audit_OHD audit_dto = AuditOHDUtils.getAuditOHD(ohd, mod_agent);
				if (audit_dto != null) {
					t = sess.beginTransaction();
					sess.save(audit_dto);
					t.commit();
				}
			}
			
			return true;
		} catch (Exception e) {
			logger.error("unable to request onhand bag: " + e);
			try {
				t.rollback();
			} catch (Exception e1) {
			}
			return false;
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
	 * Finds the request object based on its identifier
	 * 
	 * @param ID
	 *          the on hand request id
	 * @return null if not found.
	 */
	public OHDRequest findOHDRequestById(String ID) {
		Session sess = null;
		try {
			String query = "select ohd_request from com.bagnet.nettracer.tracing.db.OHD_Request ohd_request "
					+ "where ohd_request.ohd_request_id=:ohd_request_id";
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(query);
			q.setString("ohd_request_id", ID);
			List list = q.list();
			if (list.size() == 0) {
				logger.debug("unable to find ohd: " + ID);
				return null;
			}
			OHDRequest iDTO = (OHDRequest) list.get(0);
			return iDTO;
		} catch (Exception e) {
			logger.error("unable to retrieve incident: " + e);
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
	 * Find on hand based on the identifier.
	 * 
	 * @param ohd_ID
	 *          the on hand id
	 * @return ohd based on id, null otherwise.
	 */
	public static OHD getOHDByID(String ohd_ID, Session sess) {
		boolean sessionNull = (sess == null);
		try {
			String query = "select ohd from com.bagnet.nettracer.tracing.db.OHD ohd "
					+ "where ohd.OHD_ID=:ohd_ID";
			if (sessionNull) {
				sess = HibernateWrapper.getSession().openSession();
			}
			Query q = sess.createQuery(query);
			q.setString("ohd_ID", ohd_ID);
			List list = q.list();
			if (list.size() == 0) {
				logger.debug("unable to find ohd: " + ohd_ID);
				return null;
			}
			OHD iDTO = (OHD) list.get(0);
			return iDTO;
		} catch (Exception e) {
			logger.error("unable to retrieve incident: " + e);
			return null;
		} finally {
			if (sess != null) {
				try {
					if (sessionNull) {
						sess.close();
					}
				} catch (Exception e) {
					logger.error("unable to close connection: " + e);
				}
			}
		}
	}
	
	/**
	 * Find on hand based on the matched incident id.
	 * 
	 * @param incident_ID
	 *          the incident id
	 * @return incident_ID based on id, null otherwise.
	 */
	public static OHD getOHDByMatchIncidentID(String incident_ID, Session sess) {
		boolean sessionNull = (sess == null);
		try {
			String query = "select ohd from com.bagnet.nettracer.tracing.db.OHD ohd "
					+ "where ohd.matched_incident=:incident_ID";
			if (sessionNull) {
				sess = HibernateWrapper.getSession().openSession();
			}
			Query q = sess.createQuery(query);
			q.setString("incident_ID", incident_ID);
			List list = q.list();
			if (list.size() == 0) {
				logger.debug("unable to find ohd with matched incident: " + incident_ID);
				return null;
			}
			OHD iDTO = (OHD) list.get(0);
			return iDTO;
		} catch (Exception e) {
			logger.error("unable to retrieve incident: " + e);
			return null;
		} finally {
			if (sess != null) {
				try {
					if (sessionNull) {
						sess.close();
					}
				} catch (Exception e) {
					logger.error("unable to close connection: " + e);
				}
			}
		}
	}


	/**
	 * Find on hand based on the identifier.
	 * 
	 * @param ohd_ID
	 *          the on hand id
	 * @return ohd based on id, null otherwise.
	 */
	public OHD findOHDByID(String ohd_ID) {
		return findOHDByID(ohd_ID, null);

	}
	
	public OHD findOHDByID(String ohd_ID, Session sess) {
		if (ohd_ID == null || ohd_ID.length() == 0) {
			return null;
		}
		boolean sessionNull = (sess == null);
		
		if (sessionNull) {
			sess = HibernateWrapper.getSession().openSession();
		}
		
		try {
			return (OHD) sess.load(OHD.class, ohd_ID);
		} catch (Exception e) {
			logger.error("unable to retrieve incident: " + e);
			return null;
		} finally {
			if (sess != null && sessionNull) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.error("unable to close connection: " + e);
				}
			}
		}
	}
	

	/**
	 * Find on hand based on the identifier.
	 * 
	 * @param ohd_ID
	 *          the on hand id
	 * @return ohd based on id, null otherwise.
	 */
	public TraceOHD findTraceOHDByID(String ohd_ID) {
		Session sess = null;
		try {
			String query = "select ohd from com.bagnet.nettracer.tracing.db.TraceOHD ohd "
					+ "where ohd.OHD_ID=:ohd_ID";
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(query);
			q.setString("ohd_ID", ohd_ID);
			List list = q.list();
			if (list.size() == 0) {
				logger.debug("unable to find ohd: " + ohd_ID);
				return null;
			}
			TraceOHD iDTO = (TraceOHD) list.get(0);
			return iDTO;
		} catch (Exception e) {
			logger.error("unable to retrieve incident: " + e);
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

	
	public List findOnHandBagsBySearchCriteria(Ohd_DTO oDTO, Agent user, int rowsperpage,
			int currpage, boolean iscount, boolean notClosed) {
		return findOnHandBagsBySearchCriteria(oDTO, user, rowsperpage, currpage, iscount, notClosed, false, null);
	}
	/**
	 * Find on hands based on the search criteria
	 * 
	 * @param oDTO
	 *          the dto containing search data
	 * @param user
	 *          agent requesting the on hands
	 * @param rowsperpage
	 *          no. of rows to display
	 * @param currpage
	 *          size of current page
	 * @return list of on hands, null if none found.
	 */
	public List findOnHandBagsBySearchCriteria(Ohd_DTO oDTO, Agent user, int rowsperpage,
			int currpage, boolean iscount, boolean notClosed, boolean dirtyRead, String sort) {

		Session sess = null;

		try {
			if(dirtyRead) {
				sess = HibernateWrapper.getDirtySession().openSession();
			}
			else {
				sess = HibernateWrapper.getSession().openSession();
			}
			StringBuffer sql = new StringBuffer(512);
			
			TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone());


			if (iscount) sql
					.append("select count(ohd.OHD_ID) from com.bagnet.nettracer.tracing.db.OHD ohd ");
			else sql.append("select distinct ohd from com.bagnet.nettracer.tracing.db.OHD ohd ");

			if (oDTO.getFirstname().trim().length() > 0 || oDTO.getLastname().trim().length() > 0
					|| oDTO.getMiddlename().trim().length() > 0 || (sort != null && (sort.equalsIgnoreCase(SortParam.OHD_NAME.getParamString()) || sort.equalsIgnoreCase(SortParam.OHD_NAMEREV.getParamString())))) {
				sql.append(" left outer join ohd.passengers passengers ");
			}

			if ((oDTO.getOHD_categorytype_ID() != null && !oDTO.getOHD_categorytype_ID().equals("0"))
					|| (oDTO.getDescription() != null && !oDTO.getDescription().equals(""))) {
				sql.append(" join ohd.items items ");
			}

			if (oDTO.getAirline().length() > 0 || oDTO.getFlightnum().trim().length() > 0 
					|| (oDTO.getRoutingstation()!=null && oDTO.getRoutingstation().trim().length()>0)
					|| (oDTO.getRoutingdate()!=null && oDTO.getRoutingdate().length()>0) 
					|| (sort != null && (sort.equalsIgnoreCase(SortParam.OHD_DESTINATION.getParamString()) || sort.equalsIgnoreCase(SortParam.OHD_DESTINATIONREV.getParamString())))) {
				sql.append(" join ohd.itinerary itinerary ");
			}

			sql.append(" where 1=1 ");
//
//			if (oDTO.getOhd_ID() != null && !oDTO.getOhd_ID().equals("")) {
//				sql.append(" and ohd.OHD_ID like :OHD_ID ");
//			}
//			
//			
			if (oDTO.getOhd_ID() != null && !oDTO.getOhd_ID().equals("")) {
				sql.append(" and (ohd.OHD_ID like :OHD_ID ");


				if (oDTO.getWt_id().length() > 0) {

					if (oDTO.isWtConditionOr()) {
						sql.append(" or ohd.wtFile.wt_id like :wt_id )");
					} else {
						sql.append(" and ohd.wtFile.wt_id like :wt_id )");
					}
				} else {
					sql.append(") ");
				}
			} else if (oDTO.getWt_id() != null && !oDTO.getWt_id().equals("")) {
				sql.append(" and ohd.wtFile.wt_id like :wt_id ");
			}
			
//			if (oDTO.getTicketnumber() != null && !oDTO.getTicketnumber().equals("")) {
//				sql.append(" and ohd.claimnum like :claimnum ");
//			}

			if (oDTO.getAgent() != null && oDTO.getAgent().length() > 0) sql.append(" and ohd.agent.username like :agent ");

			if (oDTO.getFoundCompany() != null && !oDTO.getFoundCompany().equals("")) {
				sql.append(" and ohd.foundAtStation.company.companyCode_ID like :fcompanyCode ");
			}
			
			if (oDTO.getFoundStation() != null && !oDTO.getFoundStation().equals("")) {
				sql.append(" and ohd.foundAtStation.stationcode like :fstationcode ");
			}
			

			if (oDTO.getHeldCompany() != null && !oDTO.getHeldCompany().equals("")) {
				sql.append(" and ohd.holdingStation.company.companyCode_ID like :hcompanyCode ");
			}
			
			if (oDTO.getHeldStation() != null && !oDTO.getHeldStation().equals("")) {
				sql.append(" and ohd.holdingStation.stationcode like :hstationcode ");
			}

			if (oDTO.getFirstname() != null && oDTO.getFirstname().trim().length() > 0) {
				sql.append(" and (passengers.firstname like :firstname or ohd.firstname like :firstname) ");
			}

			if (oDTO.getLastname() != null && oDTO.getLastname().trim().length() > 0) {
				sql.append(" and (passengers.lastname like :lastname or ohd.lastname like :lastname) ");
			}

			if (oDTO.getMiddlename() != null && oDTO.getMiddlename().trim().length() > 0) {
				sql.append(" and (passengers.middlename like :middlename or ohd.middlename like :middlename)");
			}

			//check if description is not empty
			if (oDTO.getDescription() != null && !oDTO.getDescription().equals("")) {
				String[] words = StringUtils.removePronouns(oDTO.getDescription()).replace(' ',',').split("\\,");
				Disjunction desc = Expression.disjunction();
				for (int x = 0; x < words.length; x++) {
					if (x == 0) sql.append(" and (");
					else sql.append(" or ");
					sql.append(" items.description like :description" + x + " ");
					if (x == words.length - 1) sql.append(") ");
					
				}
			}

			if (oDTO.getOHD_categorytype_ID() != null && !oDTO.getOHD_categorytype_ID().equals("0")) {
				sql.append(" and items.OHD_categorytype_ID = :OHD_categorytype_ID ");
			}

			if (notClosed == true) {
				sql.append(" and ohd.status.status_ID != :status_ID ");				
			} else if (oDTO.getStatus_ID() != null && !oDTO.getStatus_ID().equals("") && !oDTO.getStatus_ID().equals("0")) {
				sql.append(" and ohd.status.status_ID = :status_ID ");
			}
			
			oDTO.setClaimcheck(oDTO.getTicketnumber().trim());
			intelligentSearchProcessing(oDTO, sql);
			
			if (oDTO.getAirline() != null && oDTO.getAirline().length() > 0) {
				sql.append(" and itinerary.airline like :airline");
			}
			if (oDTO.getFlightnum() != null && oDTO.getFlightnum().length() > 0) {
				sql.append(" and itinerary.flightnum like :flightnum");
			}
			if(oDTO.getRoutingstation()!=null && oDTO.getRoutingstation().length()>0){
				sql.append(" and (itinerary.legfrom = :routingstation or itinerary.legto = :routingstation) ");
			}
			
			if (!oDTO.getPosId().isEmpty()) {
				sql.append(" and ohd.posId like :posId");
			}


			Date sdate = null, edate = null;
			Date sdate1 = null, edate1 = null; // add one for timezone
			Date stime = null; // time to compare (04:00 if eastern, for example)
			String dateq = "";
			
			ArrayList dateal = null;
			if ((dateal = IncidentUtils.calculateDateDiff(oDTO.getS_createtime(),oDTO.getE_createtime(),tz,user)) == null) {
				return null;
			} 
			sdate = (Date)dateal.get(0);sdate1 = (Date)dateal.get(1);
			edate = (Date)dateal.get(2);edate1 = (Date)dateal.get(3);
			stime = (Date)dateal.get(4);
			
			
			if (sdate != null) {
				if (edate != null && sdate != edate) {
					sql.append(" and ((ohd.founddate= :startdate and ohd.foundtime >= :starttime) "
						+ " or (ohd.founddate= :enddate1 and ohd.foundtime <= :starttime)"
						+ " or (ohd.founddate > :startdate and ohd.founddate <= :enddate))");

				} else {
					sql.append(" and ((ohd.founddate= :startdate and ohd.foundtime >= :starttime) "
							+ " or (ohd.founddate= :startdate1 and ohd.foundtime <= :starttime))");
				}
			}

			Date sidate = null, eidate = null;
			Date eidate1 = null; // add one for timezone
			ArrayList dateal2 = null;
			if ((dateal2 = IncidentUtils.calculateDateDiff(oDTO.getS_inventorydate(),oDTO.getE_inventorydate(),tz,user)) == null) {
				return null;
			} 
			sidate = (Date)dateal2.get(0);
			eidate = (Date)dateal2.get(2);eidate1 = (Date)dateal2.get(3);
			
			
			if (sidate != null) {
				if (eidate != null && sidate != eidate) {
					sql.append(" and ((ohd.inventoryDate= :startinvdate) "
						+ " or (ohd.inventoryDate= :endinvdate)"
						+ " or (ohd.inventoryDate > :startinvdate and ohd.inventoryDate <= :endinvdate1))");

				} else {
					sql.append(" and ohd.inventoryDate > :startinvdate ");
				}
			} else if (eidate!=null){
				sql.append(" and ohd.inventoryDate < :endinvdate1");
			}

			//Routing Date
			Date srdate = null, erdate = null;
			if (oDTO.getRoutingdate().length()>0) {
				srdate = DateUtils.convertToDate(oDTO.getRoutingdate(), user.getDateformat().getFormat(), null);
				Calendar c=Calendar.getInstance();
				c.setTime(srdate);
				c.add(Calendar.DATE, 1);
				erdate = c.getTime();
				sql.append(" and (itinerary.departdate >= :startroutedate and itinerary.departdate< :endroutedate) ");
			}
			
			if (!iscount) {
				if (sort == null || sort.equals("")) {
					sql.append(" order by ohd.OHD_ID");
				} else {
					String sortq = " order by ";
					if (sort.equalsIgnoreCase(SortParam.OHD_NUM.getParamString()))
						sortq += " ohd.OHD_ID asc, ";
					if (sort.equalsIgnoreCase(SortParam.OHD_NUMREV.getParamString()))
						sortq += " ohd.OHD_ID desc, ";
					if (sort.equalsIgnoreCase(SortParam.OHD_INCIDENT.getParamString()))
						sortq += " ohd.matched_incident asc, ";
					if (sort.equalsIgnoreCase(SortParam.OHD_INCIDENTREV.getParamString()))
						sortq += " ohd.matched_incident desc, ";
					if (sort.equalsIgnoreCase(SortParam.OHD_DATE.getParamString()))
						sortq += " ohd.founddate asc";
					if (sort.equalsIgnoreCase(SortParam.OHD_DATEREV.getParamString()))
						sortq += " ohd.founddate desc";
					if (sort.equalsIgnoreCase(SortParam.OHD_MODDATE.getParamString()))
						sortq += " ohd.modifiedDate asc, ";
					if (sort.equalsIgnoreCase(SortParam.OHD_MODDATEREV.getParamString()))
						sortq += " ohd.modifiedDate desc, ";
					if (sort.equalsIgnoreCase(SortParam.OHD_BAGTAG.getParamString()))
						sortq += " ohd.claimnum asc, ";
					if (sort.equalsIgnoreCase(SortParam.OHD_BAGTAGREV.getParamString()))
						sortq += "ohd.claimnum desc, ";
					if (sort.equalsIgnoreCase(SortParam.OHD_STATUS.getParamString()))
						sortq += " ohd.status.status_ID asc, ";
					if (sort.equalsIgnoreCase(SortParam.OHD_STATUSREV.getParamString()))
						sortq += " ohd.status.status_ID desc, ";
					if (sort.equalsIgnoreCase(SortParam.OHD_COLOR.getParamString()))
						sortq += " ohd.color asc, ";
					if (sort.equalsIgnoreCase(SortParam.OHD_COLORREV.getParamString()))
						sortq += " ohd.color desc, ";
					if (sort.equalsIgnoreCase(SortParam.OHD_TYPE.getParamString()))
						sortq += " ohd.type asc, ";
					if (sort.equalsIgnoreCase(SortParam.OHD_TYPEREV.getParamString()))
						sortq += " ohd.type desc, ";
					if (sort.equalsIgnoreCase(SortParam.OHD_POSID.getParamString()))
						sortq += " ohd.posId asc, ";
					if (sort.equalsIgnoreCase(SortParam.OHD_POSIDREV.getParamString()))
						sortq += " ohd.posId desc, ";
					if (sort.equalsIgnoreCase(SortParam.OHD_AIRLINEFOUND.getParamString()))
						sortq += " ohd.foundAtStation.company.companyCode_ID asc, ";
					if (sort.equalsIgnoreCase(SortParam.OHD_AIRLINEFOUNDREV.getParamString()))
						sortq += " ohd.foundAtStation.company.companyCode_ID desc, ";
					if (sort.equalsIgnoreCase(SortParam.OHD_STATIONFOUND.getParamString()))
						sortq += " ohd.foundAtStation.stationcode asc, ";
					if (sort.equalsIgnoreCase(SortParam.OHD_STATIONFOUNDREV.getParamString()))
						sortq += " ohd.foundAtStation.stationcode desc, ";
					if (sort.equalsIgnoreCase(SortParam.OHD_HOLDSTATION.getParamString()))
						sortq += " ohd.holdingStation.stationcode asc, ";
					if (sort.equalsIgnoreCase(SortParam.OHD_HOLDSTATIONREV.getParamString()))
						sortq += " ohd.holdingStation.stationcode desc, ";
					if (sort.equalsIgnoreCase(SortParam.OHD_NAME.getParamString()))
						sortq += " passengers.lastname asc, ";
					if (sort.equalsIgnoreCase(SortParam.OHD_NAMEREV.getParamString()))
						sortq += " passengers.lastname desc, ";
					if (sort.equalsIgnoreCase(SortParam.OHD_DESTINATION.getParamString()))
						sortq += " itinerary.legto asc, ";
					if (sort.equalsIgnoreCase(SortParam.OHD_DESTINATIONREV.getParamString()))
						sortq += " itinerary.legto desc, ";
					if (sort.equalsIgnoreCase(SortParam.OHD_COMMENTS.getParamString()))
						sortq += " ohd.storage_location asc, ";
					if (sort.equalsIgnoreCase(SortParam.OHD_COMMENTSREV.getParamString()))
						sortq += " ohd.storage_location desc, ";
					if (sort.equalsIgnoreCase(SortParam.OHD_WTID.getParamString()))
						sortq += " ohd.wtFile.wt_id asc, ";
					if (sort.equalsIgnoreCase(SortParam.OHD_WTIDREV.getParamString()))
						sortq += " ohd.wtFile.wt_id desc, ";
					
					if(!((sort.equals("ohdCreateDate")) || (sort.equals("ohdCreateDateRev"))))
						sortq += " ohd.founddate asc";
					
					sql.append(sortq);
				}
			}
			
			Query q = sess.createQuery(sql.toString());

			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				q.setFirstResult(startnum);
				q.setMaxResults(rowsperpage);
			}

			if (oDTO.getOhd_ID() != null && !oDTO.getOhd_ID().equals("")) {
				q.setString("OHD_ID", oDTO.getOhd_ID());
			}
			
			if (oDTO.getWt_id().length() > 0)
				q.setString("wt_id", oDTO.getWt_id());

			
			if (oDTO.getClaimcheck() != null && !oDTO.getClaimcheck().equals("")) {
				q.setString("claimchecknum", oDTO.getClaimcheck().trim());
			}
			

			if (oDTO.getFoundCompany() != null && !oDTO.getFoundCompany().equals("")) {
				q.setString("fcompanyCode", oDTO.getFoundCompany());
			}
			
			if (oDTO.getFoundStation() != null && !oDTO.getFoundStation().equals("")) {
				q.setString("fstationcode", oDTO.getFoundStation().trim().toUpperCase());
			}
			
			if (oDTO.getClaimcheck2() != null)
				q.setString("claimchecknum2", oDTO.getClaimcheck2().trim());

			
			if (oDTO.getHeldCompany() != null && !oDTO.getHeldCompany().equals("")) {
				q.setString("hcompanyCode", oDTO.getHeldCompany());
			}
			
			if (oDTO.getHeldStation() != null && !oDTO.getHeldStation().equals("")) {
				q.setString("hstationcode", oDTO.getHeldStation().trim().toUpperCase());
			}
			
			
			if (oDTO.getFirstname() != null && oDTO.getFirstname().trim().length() > 0) {
				q.setString("firstname", oDTO.getFirstname());
			}

			if (oDTO.getLastname() != null && oDTO.getLastname().trim().length() > 0) {
				q.setString("lastname", oDTO.getLastname());
			}

			if (oDTO.getMiddlename() != null && oDTO.getMiddlename().trim().length() > 0) {
				q.setString("middlename", oDTO.getMiddlename());
			}
			
			if (!oDTO.getPosId().isEmpty()) {
				q.setString("posId", oDTO.getPosId());
			}

			//check if description is not empty
			if (oDTO.getDescription() != null && !oDTO.getDescription().equals("")) {
				String[] words = StringUtils.removePronouns(oDTO.getDescription()).replace(' ',',').split("\\,");
				Disjunction desc = Expression.disjunction();
				for (int x = 0; x < words.length; x++) {
					q.setString("description" + x, "%" + words[x] + "%");
				}
			}
			if (oDTO.getOHD_categorytype_ID() != null && !oDTO.getOHD_categorytype_ID().equals("0")) {
				q.setInteger("OHD_categorytype_ID", Integer.parseInt(oDTO.getOHD_categorytype_ID()));
			}

			if (sdate != null) {
				if (edate != null && sdate != edate) {
					q.setDate("startdate", sdate);
					q.setTime("starttime", stime);
					q.setDate("enddate", edate);
					q.setDate("enddate1", edate1);
					
				} else {
					q.setDate("startdate", sdate);
					q.setDate("startdate1", sdate1);
					q.setTime("starttime", stime);
				}
			}

			if(oDTO.getRoutingstation()!=null && !oDTO.getRoutingstation().isEmpty()){
				q.setString("routingstation", oDTO.getRoutingstation());
			}
			
			if (sidate != null) {
				if (eidate != null && sidate != eidate) {
					q.setDate("startinvdate", sidate);
					q.setDate("endinvdate", eidate);
					q.setDate("endinvdate1", eidate1);
					
				} else {
					q.setDate("startinvdate", sidate);
				}
			} else if (eidate !=null ){
				q.setDate("endinvdate1", eidate1);
			}
			
			if (oDTO.getRoutingdate().length()>0) {
				q.setDate("startroutedate", srdate);
				q.setDate("endroutedate", erdate);
			}

			if (notClosed) {
				q.setInteger("status_ID", TracingConstants.OHD_STATUS_CLOSED);
			}	else if (oDTO.getStatus_ID() != null && !oDTO.getStatus_ID().equals("") && !oDTO.getStatus_ID().equals("0")) {
				q.setInteger("status_ID", Integer.parseInt(oDTO.getStatus_ID()));
			} 
			
			if (oDTO.getAirline() != null && oDTO.getAirline().length() > 0) {
				q.setString("airline", oDTO.getAirline().toUpperCase());
			}
			if (oDTO.getFlightnum() != null && oDTO.getFlightnum().trim().length() > 0) {
				q.setString("flightnum", oDTO.getFlightnum().trim().toUpperCase());
				
			}

			if (oDTO.getAgent() != null && oDTO.getAgent().trim().length() > 0) {
				q.setString("agent", oDTO.getAgent().trim());
			}

			return q.list();
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
	
	public List customQuery(SearchIncidentForm siDTO, Agent user, int rowsperpage, int currpage,
			boolean iscount) throws HibernateException {
		return customQuery(siDTO, user, rowsperpage, currpage, iscount, false);
	}

	public List customQuery(SearchIncidentForm siDTO, Agent user, int rowsperpage, int currpage,
			boolean iscount, boolean dirtyRead) throws HibernateException {
		Session sess = null;

		HashMap<String, ArrayList<String>> eightDig=new HashMap<String, ArrayList<String>>();
		HashMap<String, ArrayList<String>> nineDig=new HashMap<String, ArrayList<String>>();
		HashMap<String, ArrayList<String>> tenDig=new HashMap<String, ArrayList<String>>();
		
		if(dirtyRead) {
			sess = HibernateWrapper.getDirtySession().openSession();
		}
		else {
			sess = HibernateWrapper.getSession().openSession();
		}
		
		Query q = null;
		
		try {
			StringBuffer s = new StringBuffer(512);
			
			TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone());


			if (iscount) {
				s.append("select count(ohd.OHD_ID) from com.bagnet.nettracer.tracing.db.OHD ohd ");
			}
			else {
				s.append("select distinct ohd from com.bagnet.nettracer.tracing.db.OHD ohd ");
			}

			if (siDTO.getFlightnum().length() > 0 || siDTO.getAirline().length() > 0
					|| (siDTO.getRoutingstation()!=null && siDTO.getRoutingstation().length()>0)
					|| (siDTO.getRoutingdate()!=null && siDTO.getRoutingdate().length()>0)){ 
				s.append(" join ohd.itinerary itinerary ");
			}

			if (siDTO.getDescription().length() > 0 || siDTO.getCategory_ID() > 0){ 
				s.append(" join ohd.items item");
			}

			if (siDTO.getFirstname().length() > 0 || siDTO.getMiddlename().length() > 0
					|| siDTO.getLastname().length() > 0 || siDTO.getAddress1().length() > 0 || siDTO.getAddress2().length() > 0
					|| siDTO.getCity().length() > 0 || (siDTO.getState_ID() != null && siDTO.getState_ID().length() > 0) || (siDTO.getProvince() != null && siDTO.getProvince().length() > 0) || siDTO.getCountrycode_ID().length() > 0
					|| siDTO.getZip().length() > 0 || siDTO.getPhone().length() > 0
					|| siDTO.getEmail().length() > 0) {
				s.append(" left outer join ohd.passengers passenger ");
				s.append(" left outer join passenger.addresses address");
			}

			s.append(" where 1=1 ");

			
			if (siDTO.getOhd_id().length() > 0) {
				s.append(" and (ohd.OHD_ID like :ohd_ID ");

				if (siDTO.getWt_id().length() > 0) {

					if (siDTO.isWtConditionOr()) {
						s.append(" or ohd.wtFile.wt_id like :wt_id )");
					} else {
						s.append(" and ohd.wtFile.wt_id like :wt_id )");
					}
				} else {
					s.append(") ");
				}
			} else if (siDTO.getWt_id().length() > 0) {
				s.append(" and ohd.wtFile.wt_id like :wt_id ");
			}
			
			if (!siDTO.getPosId().isEmpty()) {
				s.append(" and ohd.posId like :posId");
			}
			
			Date sdate = null, edate = null;
			Date sdate1 = null, edate1 = null; // add one for timezone
			Date stime = null; // time to compare (04:00 if eastern, for example)
			String dateq = "";
			
			ArrayList dateal = null;
			if ((dateal = IncidentUtils.calculateDateDiff(siDTO.getS_createtime(),siDTO.getE_createtime(),tz,user)) == null) {
				return null;
			} 
			sdate = (Date)dateal.get(0);sdate1 = (Date)dateal.get(1);
			edate = (Date)dateal.get(2);edate1 = (Date)dateal.get(3);
			stime = (Date)dateal.get(4);
			
			
			if (sdate != null) {
				if (edate != null && sdate != edate) {
					s.append(" and ((ohd.founddate= :startdate and ohd.foundtime >= :starttime) "
						+ " or (ohd.founddate= :enddate1 and ohd.foundtime <= :starttime)"
						+ " or (ohd.founddate > :startdate and ohd.founddate <= :enddate))");

				} else {
					s.append(" and ((ohd.founddate= :startdate and ohd.foundtime >= :starttime) "
							+ " or (ohd.founddate= :startdate1 and ohd.foundtime <= :starttime))");
				}
			}
			
			//Invetory Date
			Date sidate = null, eidate = null;
			Date eidate1 = null; // add one for timezone
			ArrayList dateal2 = null;
			if ((dateal2 = IncidentUtils.calculateDateDiff(siDTO.getS_inventorydate(),siDTO.getE_inventorydate(),tz,user)) == null) {
				return null;
			} 
			sidate = (Date)dateal2.get(0);
			eidate = (Date)dateal2.get(2);eidate1 = (Date)dateal2.get(3);
			
			
			if (sidate != null) {
				if (eidate != null && sidate != eidate) {
					s.append(" and ((ohd.inventoryDate= :startinvdate) "
						+ " or (ohd.inventoryDate= :endinvdate)"
						+ " or (ohd.inventoryDate > :startinvdate and ohd.inventoryDate <= :endinvdate1))");

				} else {
					s.append(" and ohd.inventoryDate > :startinvdate ");
				}
			} else if (eidate!=null){
				s.append(" and ohd.inventoryDate < :endinvdate1");
			}

			//Routing Date
			Date srdate = null, erdate = null;
			
			if (siDTO.getRoutingdate().length()>0) {
				srdate = DateUtils.convertToDate(siDTO.getRoutingdate(), user.getDateformat().getFormat(), null);
				Calendar c=Calendar.getInstance();
				c.setTime(srdate);
				c.add(Calendar.DATE, 1);
				erdate = c.getTime();
				s.append(" and (itinerary.departdate>= :startroutedate and itinerary.departdate< :endroutedate) ");
			}
			
			// record locator
			if (siDTO.getRecordlocator().length() > 0) {
				s.append(" and ohd.record_locator like :recordlocator");
			}
			
			// passenger
			if (siDTO.getFirstname().length() > 0 || siDTO.getMiddlename().length() > 0
					|| siDTO.getLastname().length() > 0) {
				s.append(" and (");
				s.append(" ( passenger.firstname like :firstname");
				s.append(" and passenger.middlename like :middlename");
				s.append(" and passenger.lastname like :lastname)");
				s.append(" or ( ohd.firstname like :firstname");
				s.append(" and ohd.middlename like :middlename");
				s.append(" and ohd.lastname like :lastname)");
				s.append(" )");
			}

			if (siDTO.getCompanycode_ID().length() > 0 || siDTO.getMembershipnum().length() > 0) {
				s.append(" and (ohd.membership.membershipnum like :membershipnum");
				s.append(" and ohd.membership.companycode_ID like :companyCode_ID)");
			}

			// addresses

			if (siDTO.getAddress1().length() > 0)
				s.append(" and (address.address1 like :addr1)");
			if (siDTO.getAddress2().length() > 0)
				s.append(" and (address.address2 like :addr2)");
			if (siDTO.getCity().length() > 0) s.append(" and address.city like :city");
			if (siDTO.getState_ID() != null && siDTO.getState_ID().length() > 0) s.append(" and address.state_ID like :state_ID");
			if (siDTO.getProvince() != null && siDTO.getProvince().length() > 0)
				s.append(" and (address.province like :province)");
			if (siDTO.getCountrycode_ID().length() > 0) {
				s.append(" and (address.countrycode_ID like :countrycode_ID)");
			}
			if (siDTO.getZip().length() > 0) s.append(" and address.zip like :zip");
			if (siDTO.getPhone().length() > 0) s
					.append(" and (address.homephone like :phone or address.workphone like :phone "
							+ " or address.mobile like :phone or address.pager like :phone or address.altphone like :phone)");
			if (siDTO.getEmail().length() > 0) s.append(" and address.email like :email");

			// bag

			if (siDTO.getColor().length() > 0) s.append(" and ohd.color like :color");
			if (siDTO.getBagtype().length() > 0) s.append(" and ohd.type like :bagtype");
			if (siDTO.getXdescelement_ID1() > 0) s
					.append(" and (ohd.xdescelement_ID_1 = :xdesc1 or ohd.xdescelement_ID_2 = :xdesc1 or ohd.xdescelement_ID_3 = :xdesc1)");
			if (siDTO.getXdescelement_ID2() > 0) s
					.append(" and (ohd.xdescelement_ID_1 = :xdesc2 or ohd.xdescelement_ID_2 = :xdesc2 or ohd.xdescelement_ID_3 = :xdesc2)");
			if (siDTO.getXdescelement_ID3() > 0) s
					.append(" and (ohd.xdescelement_ID_1 = :xdesc3 or ohd.xdescelement_ID_2 = :xdesc3 or ohd.xdescelement_ID_3 = :xdesc3)");
			if (siDTO.getManufacturer_ID() > 0) s.append(" and ohd.manufacturer_ID = :manu_ID");
			if (siDTO.getManufacturer_other().length() > 0) s
					.append(" and ohd.manufacturer_other like :manu_other");

			//check if description is not empty
			if (siDTO.getDescription().length() > 0) {
				//String[] words = StringUtils.removePronouns(siDTO.getDescription()).split("\\s");
				String[] words = StringUtils.removePronouns(siDTO.getDescription().trim()).replace(' ',',').split("\\,");
				int tempx = 0;
				for (int x = 0; x < words.length; x++) {
					if (words[x].trim().length() > 0) {
						if (tempx == 0) s.append(" and (");
						else s.append(" or ");
						s.append(" item.description like :description" + x + " ");
						if (x == words.length - 1) s.append(") ");
						tempx++;
					}
				}
			}

			if (siDTO.getCategory_ID() > 0) s
					.append(" and item.OHD_categorytype_ID = :OHD_categorytype_ID ");

			if (siDTO.getFlightnum().length() > 0) s.append(" and itinerary.flightnum like :flightnum");

			if(siDTO.getRoutingstation().length()>0){
				s.append(" and (itinerary.legfrom = :routingstation or itinerary.legto = :routingstation) ");
			}
			
			if (siDTO.getAirline().length() > 0) s.append(" and itinerary.airline like :airline");
			
			if (siDTO.getStatus_ID() > 0) {
				s.append(" and ohd.status.status_ID = :status_ID");
			}
			else if(siDTO.getStatus_ID() == TracingConstants.OHD_STATUS_ACTIVE) {
				s.append(" and ohd.status.status_ID != :status_ID");
			}
			

			if(siDTO.getStationcreated_ID()>0){
				s.append(" and ohd.foundAtStation.station_ID = :foundstation_ID");
			}

			if(siDTO.getStationassigned_ID()>0){
				s.append(" and ohd.holdingStation.station_ID = :holdingstation_ID");
			}

			intelligentSearchProcessing(siDTO, s, eightDig, nineDig, tenDig);
			

			if (!iscount && siDTO.getRecordlocator()!=null && siDTO.getRecordlocator().length() > 0) s.append(" order by ohd.founddate desc, ohd.foundtime desc");
			else if(!iscount ) s.append(" order by ohd.OHD_ID");
			
			q = sess.createQuery(s.toString());
			
			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				q.setFirstResult(startnum);
				q.setMaxResults(rowsperpage);
			}
			

			if (siDTO.getOhd_id().length() > 0)
				q.setString("ohd_ID", siDTO.getOhd_id());
			
			if (siDTO.getWt_id().length() > 0)
				q.setString("wt_id", siDTO.getWt_id());

			
			
			if (sdate != null) {
				if (edate != null && sdate != edate) {
					q.setDate("startdate", sdate);
					q.setTime("starttime", stime);
					q.setDate("enddate", edate);
					q.setDate("enddate1", edate1);
					
				} else {
					q.setDate("startdate", sdate);
					q.setDate("startdate1", sdate1);
					q.setTime("starttime", stime);
				}
			}
			
			if (sidate != null) {
				if (eidate != null && sidate != eidate) {
					q.setDate("startinvdate", sidate);
					q.setDate("endinvdate", eidate);
					q.setDate("endinvdate1", eidate1);
					
				} else {
					q.setDate("startinvdate", sidate);
				}
			} else if (eidate !=null ){
				q.setDate("endinvdate1", eidate1);
			}

			if (siDTO.getRoutingdate().length()>0) {
				q.setDate("startroutedate", srdate);
				q.setDate("endroutedate", erdate);
			}

			if (siDTO.getStatus_ID() > 0) {
				q.setInteger("status_ID", siDTO.getStatus_ID());
			}
			else if(siDTO.getStatus_ID() == TracingConstants.OHD_STATUS_ACTIVE) {
				q.setInteger("status_ID", TracingConstants.OHD_STATUS_CLOSED);
			}
			
			if (siDTO.getRecordlocator().length() > 0) {
				q.setString("recordlocator", siDTO.getRecordlocator());
			}
			
			if (siDTO.getFirstname().length() > 0 || siDTO.getMiddlename().length() > 0
					|| siDTO.getLastname().length() > 0) {

				String a = siDTO.getFirstname().length() == 0 ? "%" : siDTO.getFirstname();
				String b = siDTO.getMiddlename().length() == 0 ? "%" : siDTO.getMiddlename();
				String c = siDTO.getLastname().length() == 0 ? "%" : siDTO.getLastname();

				q.setString("firstname", a);
				q.setString("middlename", b);
				q.setString("lastname", c);
			}
			if (siDTO.getAddress1().length() > 0)
				q.setString("addr1", siDTO.getAddress1().toUpperCase());
			if (siDTO.getAddress2().length() > 0)
				q.setString("addr2", siDTO.getAddress2().toUpperCase());
			if (siDTO.getCity().length() > 0) q.setString("city", siDTO.getCity().toUpperCase());
			if (siDTO.getState_ID() != null && siDTO.getState_ID().length() > 0) {
				q.setString("state_ID", siDTO.getState_ID().toUpperCase());
			}
			if (siDTO.getProvince() != null && siDTO.getProvince().length() > 0) {
				q.setString("province", siDTO.getProvince().toUpperCase());
			}
			if (siDTO.getCountrycode_ID().length() > 0) {
				q.setString("countrycode_ID", siDTO.getCountrycode_ID().toUpperCase());
			}
			if (siDTO.getZip().length() > 0) q.setString("zip", siDTO.getZip().toUpperCase());
			if (siDTO.getPhone().length() > 0) q.setString("phone", siDTO.getPhone().toUpperCase());
			if (siDTO.getEmail().length() > 0) q.setString("email", siDTO.getEmail().toUpperCase());

			if (siDTO.getCompanycode_ID().length() > 0 || siDTO.getMembershipnum().length() > 0) {
				q.setString("membershipnum", siDTO.getMembershipnum());
				q.setString("companyCode_ID", siDTO.getCompanycode_ID());
			}

			if (siDTO.getClaimchecknum()!=null && siDTO.getClaimchecknum().length()>0) {
				String tag=siDTO.getClaimchecknum();
		    	/*
		    	 * Checking for UTB tag - When saving UTB as a Incident_Claimcheck, it only needs to check against the claimCheckNum field	
		    	 */
				boolean utb=false;
				if(tag!=null && tag.length()>3 &&tag.substring(0, 3).toUpperCase().equals(TracingConstants.UTB_CHECK)){
	    			q.setString("keyUTB", tag.substring(0,3));
	    			q.setString("keyUTBList", tag.substring(3));
	    			utb=true;
		    	}
				if (tag != null && tag.length() == 8 && !utb) {
    				String key = tag.substring(0, 2);
	    			q.setString("key8" + key, key);
	    			q.setString("key8" + key + "List", tag.substring(2));
	    		}
    			if (tag != null && tag.length() == 9 && !utb) {

    				String key = tag.substring(0, 3);
	    			q.setString("key9" + key, key);
	    			q.setString("key9" + key + "List", tag.substring(3));
	    		}
    			if (tag != null && tag.length() == 10 && !utb) {
    				String key = tag.substring(0, 4);
	    			q.setString("key10" + key + "One", key.substring(0, 1));
	    			q.setString("key10" + key + "Two", key.substring(1,4));
	    			q.setString("key10" + key + "List", tag.substring(4));
	    		}
    		}
			
			if (siDTO.getColor().length() > 0) q.setString("color", siDTO.getColor().toUpperCase());
			if (siDTO.getBagtype().length() > 0) q.setString("bagtype", siDTO.getBagtype().toUpperCase());
			if (siDTO.getXdescelement_ID1() > 0) q.setInteger("xdesc1", siDTO.getXdescelement_ID1());
			if (siDTO.getXdescelement_ID2() > 0) q.setInteger("xdesc2", siDTO.getXdescelement_ID2());
			if (siDTO.getXdescelement_ID3() > 0) q.setInteger("xdesc3", siDTO.getXdescelement_ID3());
			if (siDTO.getManufacturer_ID() > 0) q.setInteger("manu_ID", siDTO.getManufacturer_ID());
			if (siDTO.getManufacturer_other().length() > 0) q.setString("manu_other", siDTO
					.getManufacturer_other().toUpperCase().trim());

			if (siDTO.getDescription().length() > 0) {
				//String[] words = StringUtils.removePronouns(siDTO.getDescription()).split("\\s");	
				String[] words = StringUtils.removePronouns(siDTO.getDescription().trim()).replace(' ',',').split("\\,");
				for (int x = 0; x < words.length; x++) {
					if (words[x].trim().length() > 0) {
						q.setString("description" + x, "%" + (words[x]).trim() + "%");
					}
				}
			}
			if (siDTO.getCategory_ID() > 0) {
				q.setInteger("OHD_categorytype_ID", siDTO.getCategory_ID());
			}

			if (siDTO.getFlightnum().length() > 0) q.setString("flightnum", siDTO.getFlightnum()
					.toUpperCase());
			if (siDTO.getAirline().length() > 0) q.setString("airline", siDTO.getAirline().toUpperCase());

			if(siDTO.getStationcreated_ID()>0){
				q.setInteger("foundstation_ID", siDTO.getStationcreated_ID());
			}

			if(siDTO.getStationassigned_ID()>0){
				q.setInteger("holdingstation_ID", siDTO.getStationassigned_ID());
			}
			
			if (!siDTO.getPosId().isEmpty()) {
				q.setString("posId", siDTO.getPosId());
			}

			if (siDTO.getRoutingstation().length()>0) {
				q.setString("routingstation", siDTO.getRoutingstation());
			}
			
			List results = q.list();
			return results;
		} catch (Exception e) {
			logger.error("unable to retrieve incident in findIncident: " + e);
			e.printStackTrace();
			return null;
		} finally {
			if (sess != null) sess.close();
		}
	}
	
	
	private void intelligentSearchProcessing(SearchIncidentForm siDTO, StringBuffer s,HashMap<String, ArrayList<String>> eightDig,HashMap<String, ArrayList<String>> nineDig, HashMap<String, ArrayList<String>> tenDig) {
		String tag=siDTO.getClaimchecknum();
    	if (tag != null && tag.length() > 0) {
    		String itemSelect = "";
	    		/*
		    	 * Checking for UTB tag - When saving UTB as a Incident_Claimcheck, it only needs to check against the claimCheckNum field	
		    	 */
	    		if(tag!=null && tag.length()>3 && tag.substring(0, 3).toUpperCase().equals(TracingConstants.UTB_CHECK)){
	    			itemSelect = " and (ohd.claimchecknum_ticketingcode = :keyUTB and ohd.claimchecknum_bagnumber = :keyUTBList)";
				} else if (tag != null && tag.length() == 8) {
    				String key = tag.substring(0, 2);
    				itemSelect = " and (ohd.claimchecknum_carriercode = :key8" + key + " and ohd.claimchecknum_bagnumber = :key8" + key + "List)";
    			} else if (tag != null && tag.length() == 9) {
    				String key = tag.substring(0, 3);
    				itemSelect = " and (ohd.claimchecknum_ticketingcode = :key9" + key + " and ohd.claimchecknum_bagnumber = :key9" + key + "List)";
    			} else if (tag != null && tag.length() == 10) {
    				String key = tag.substring(0, 4);
    				itemSelect = " and ((ohd.claimchecknum_leading = :key10" + key + "One or ohd.claimchecknum_leading is null) "
        					+ "and (ohd.claimchecknum_ticketingcode = :key10" + key + "Two and ohd.claimchecknum_bagnumber = :key10" + key + "List))";
    			}
    		s.append(itemSelect );
		}
	}
	
	private void intelligentSearchProcessing(Ohd_DTO siDTO, StringBuffer s) {
		boolean tagPresent = siDTO.getClaimcheck() != null && siDTO.getClaimcheck().trim().length() > 0;
		int searchType = siDTO.getIntelligentTagSearchType();
		if (siDTO.isIntelligentTagSearch() && tagPresent && searchType == 0) {
			Pattern pattern = Pattern.compile(LookupAirlineCodes.PATTERN_10_DIGIT_BAG_TAG);
			if (pattern.matcher(siDTO.getClaimcheck()).find()) {
				siDTO.setIntelligentTagSearchType(10);
				searchType = 10;
			} else {
				pattern = Pattern.compile(LookupAirlineCodes.PATTERN_9_DIGIT_BAG_TAG);
				if (pattern.matcher(siDTO.getClaimcheck()).find()) {
					siDTO.setIntelligentTagSearchType(9);
					searchType = 9;
				} else {
					pattern = Pattern.compile(LookupAirlineCodes.PATTERN_8_CHAR_BAG_TAG);
					if (pattern.matcher(siDTO.getClaimcheck()).find()) {
						siDTO.setIntelligentTagSearchType(8);
						searchType = 8;
					}
				}
			}
		}
		
		
			
		if (siDTO.isIntelligentTagSearch() && searchType > 0) {
			String nineDigitWildcardTag = null;
			String genericTag = null;
			
			String claimcheck = siDTO.getClaimcheck().trim();
			
			if (claimcheck.indexOf("%") == -1) {
				if (searchType == 10) {
					nineDigitWildcardTag = "%" + claimcheck.substring(1);
					try {
						genericTag = LookupAirlineCodes.getTwoCharacterBagTag(claimcheck);
					} catch (BagtagException e) {
						// Ignore
						logger.error("Unable to convert bag tag: " + claimcheck);
						genericTag = claimcheck;
					}
				} else if (searchType == 9) {
					nineDigitWildcardTag = "%" + claimcheck;
					try {
						genericTag = LookupAirlineCodes.getTwoCharacterBagTag(claimcheck);
					} catch (BagtagException e) {
						// Ignore
						logger.error("Unable to convert bag tag: " + claimcheck);
						genericTag = claimcheck;
					}
				} else if (searchType == 8) {
					genericTag = claimcheck;
					try {
						nineDigitWildcardTag = "%" + (LookupAirlineCodes.getFullBagTag(claimcheck)).substring(1);
					} catch (BagtagException e) {
						// Ignore
						logger.error("Unable to convert bag tag: " + claimcheck);
						nineDigitWildcardTag = claimcheck;
					}
				}

				siDTO.setClaimcheck(nineDigitWildcardTag);
				siDTO.setClaimcheck2(genericTag);
			}
			s.append(" and (ohd.claimnum like :claimchecknum or ohd.claimnum like :claimchecknum2) ");
		} else if (siDTO.getClaimcheck() != null && siDTO.getClaimcheck().length() > 0) {
			s.append(" and ohd.claimnum like :claimchecknum ");

		}
	}
	
	/**
	 * method used to return all ohd objects after numdays days for worldtracer or other webservices intergration
	 * @param numdays
	 * @return
	 * @throws HibernateException
	 */
	public List findOHDforWT(int numdays,String companycode) throws HibernateException {
		Session sess = HibernateWrapper.getSession().openSession();
		Query q = null;
		try {
			
			Calendar c = new GregorianCalendar();
			c.setTime(TracerDateTime.getGMTDate());
			c.add(Calendar.DAY_OF_MONTH, (0-numdays));
			Date thedate = c.getTime();
			
			q = sess.createQuery("select distinct ohd from com.bagnet.nettracer.tracing.db.OHD ohd where " +
					" ohd.founddate <= :founddate and ohd.foundAtStation.company.companyCode_ID = :companycode");
			q.setDate("founddate", thedate);
			q.setString("companycode", companycode);
			List list = q.list();

			return list;
		} catch (Exception e) {
			logger.error("unable to retrieve ohds in findOHDforWT: " + e);
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
		}
	}

	/**
	 * Returns true if the record exisits
	 * 
	 * @param instr
	 * @param obj
	 * @param tablename
	 * @param column
	 * @return
	 */
	public boolean isRecordExist(String instr, String obj, String tablename, String column) {
		Session sess = null;
		try {
			String query = "select " + tablename + " from " + obj + " " + tablename + " where "
					+ tablename + "." + column + "=:" + column;
			sess = HibernateWrapper.getSession().openSession();

			Query q = sess.createQuery(query);
			q.setString(column, instr);
			List list = q.list();

			if (list.size() > 0) return true;
			else return false;
		} catch (Exception e) {
			logger.error("unable to retrieve record from database: " + e);
			return false;
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
	 * Automatically generate 10 character incident number 3 alpha/7 numeric
	 * 
	 * @param station
	 *          station for which identifier is sought
	 * @return the on hand identifier for the station
	 */
	public String getOHD_ID(Station station) {
		// get highest num from Incident_Range table
		Session sess = null;
		try {
			station = findStationByID(station.getStation_ID());
			if (station == null) {
				logger.error("invalid station");
				return null;
			}
			String companycode = station.getCompany().getCompanyCode_ID();
			String stationcode = station.getStationcode();

			sess = HibernateWrapper.getSession().openSession();
			Transaction t = sess.beginTransaction();
			OHD_Range ir = new OHD_Range();
			ir.setCompanycode_ID(companycode);
			sess.save(ir);
			t.commit();
			long newnum = ir.getCurrent_num();
			if (newnum == 0) {
				logger.error("unable to create a new ohd number");
				return null;
			}

			// get the last record with this companycode_ID;
			Query q = null;
			List list = null;


			StringBuffer s = new StringBuffer();
			s.append(stationcode);
			s.append(companycode);
			String num = Long.toString(newnum);
			// padd new number to # digits that total length will equal to
			// tracingconstants.incident_len
			for (int i = 0; i < TracingConstants.INCIDENT_LEN - companycode.length()
					- stationcode.length() - num.length(); i++) {
				s.append("0");
			}
			s.append(num);

			// making sure there isn't another incident id in the table already
			// this would have never happen in production
			String ohd_num = s.toString().toUpperCase();
			q = sess.createQuery("from com.bagnet.nettracer.tracing.db.OHD ohd "
					+ "where ohd.OHD_ID = :ohd_ID");

			q.setString("ohd_ID", ohd_num);

			list = q.list();
			if (list != null && list.size() > 0) {
				return getOHD_ID(station);
			} else {
				return ohd_num;
			}

		} catch (Exception e) {
			logger.error("unable to create a new incident number: " + e);
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
	 * Find stations by ID
	 * 
	 * @param station_ID
	 *          station's ID
	 * @return station if found; null otherwise
	 */
	public Station findStationByID(int station_ID) {
		Session sess = null;
		try {
			String query = "select station from com.bagnet.nettracer.tracing.db.Station station "
					+ "where station.station_ID=:station_ID";
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(query);
			q.setInteger("station_ID", station_ID);
			List list = q.list();

			if (list.size() == 0) {
				logger.debug("unable to find station: " + station_ID);
				return null;
			}
			Station station = (Station) list.get(0);
			return station;
		} catch (Exception e) {
			logger.error("unable to retrieve station: " + e);
			return null;
		} finally {
			if (sess != null) sess.close();
		}
	}

	/**
	 * find the list from ohd_ID
	 * 
	 * @param ohd_ID
	 * @return
	 */
	public List findBDOList(String ohd_ID) {
		Session sess = null;
		try {

			String query = "select bdo from com.bagnet.nettracer.tracing.db.BDO bdo "
					+ "where bdo.ohd.OHD_ID = :ohd_ID order by bdo.BDO_ID";
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(query);
			q.setString("ohd_ID", ohd_ID);
			List list = q.list();
			if (list.size() == 0) {
				logger.debug("unable to find bdos from ohd_ID: " + ohd_ID);
				return null;
			}
			return list;
		} catch (Exception e) {
			logger.error("unable to retrieve bdos: " + e);
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
	 * clear ids for reinsert after delete
	 * @param inc
	 * @return
	 */
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

	public boolean updateOhdNoAudit(OHD ohd) {
		Transaction t = null;
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			t = sess.beginTransaction();

			sess.saveOrUpdate(ohd);
			t.commit();

		} catch (Exception e) {
			logger.error("unable to insert into database: " + e);
			e.printStackTrace();
			if (t != null)
				t.rollback();
			return false;
		} finally {

			sess.close();
		}
		return true;
	}

	public static OHD findOhdByWtId(String wt_id) {
		Session sess = null;

		try {
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery("from com.bagnet.nettracer.tracing.db.OHD o where o.wtFile.wt_id= :wt_id");
			q.setParameter("wt_id", wt_id);
			List list = q.list();

			if (list.size() == 0) {
				logger.debug("unable to find incident with wt_id: " + wt_id);
				return null;
			}
			OHD ohd = (OHD) list.get(0);

			return ohd;
		} catch (Exception e) {
			logger.error("unable to retrieve incident: " + e);
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
	 * @param hours the number of hours old the ohd must be to move to wt
	 * @param companyCode company whose ohds are to be moved to wt
	 * @return list of OHD that need to be moved to world tracer
	 */
	public List<OHD> findMoveToWtOhd(int hours, String companyCode) {
		if (hours <= 0) {
			return null;
		}
		Calendar c = new GregorianCalendar();
		c.setTime(TracerDateTime.getGMTDate());
		c.add(Calendar.HOUR, (0-hours));
		Date ohdCutoff = c.getTime();

		String queryString = "select ohd from com.bagnet.nettracer.tracing.db.OHD ohd where " +
		" ohd.wtFile is null " +  //no worldtracer file already
		//" and ohd.OHD_ID not in (select q.ohd.OHD_ID from WtqOhdAction q where q.status = :qStatus)" +
		//" and ((ohd.lastupdated > (select max(q.createdate) from WtqOhdAction q where q.ohd = ohd)) or ((select max(q.createdate) from WtqOhdAction q where q.ohd.OHD_ID = ohd.OHD_ID) is null)) " +
		" and ((ohd.founddate < :ohdCutoff) or (ohd.founddate = :ohdCutoff and ohd.foundtime < :ohdTimeCutoff))  " + //old enough
		" and ohd.status.status_ID = :status " + // only open
		" and ohd.foundAtStation.company.companyCode_ID = :companyCode " +
		" and ohd.ohd_type = :ohd_type " +
		" order by ohd.founddate desc";
		
		Session sess = null;
		try {
			sess = HibernateWrapper.getDirtySession().openSession();
			Query q = sess.createQuery(queryString);
			q.setDate("ohdCutoff", ohdCutoff);
			q.setTime("ohdTimeCutoff", ohdCutoff);
			q.setParameter("status", TracingConstants.OHD_STATUS_OPEN);
			q.setParameter("companyCode", companyCode);
			//q.setParameter("qStatus", WtqStatus.PENDING);
			q.setParameter("ohd_type", TracingConstants.NOT_MASS_OHD_TYPE);
			return q.list();
		}
		catch (Exception e) {
			logger.error("unable to get move to WT OHD list: " + e);
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.error("unable to close hibernate session: " + e);
				}
			}
		}
	}
	
		/**
	 * This is to be used solely by the tag number trace feature that may
	 * occur when an agent selects to search by bag tag number from the 
	 * Lost/Delay prepopulation screen.
	 * 
	 * @param tagNumber
	 * @return
	 */
	public static List<OHD> queryOhdsForTagTrace(String tagNumber) {

		if (tagNumber == null || tagNumber.trim().length() == 0) {
			return null;
		}
		
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			StringBuffer queryString = new StringBuffer();
			queryString.append("from com.bagnet.nettracer.tracing.db.OHD o ");
			queryString.append(" where ");
			queryString.append(" o.status.status_ID <> :closed ");

			
			String fullTag = null;
			String basicTag = null;

			
			try {
				fullTag = LookupAirlineCodes.getFullBagTag(tagNumber);
			} catch (BagtagException e) {
				// Ignore
			}
			
			try {
				basicTag = LookupAirlineCodes.getTwoCharacterBagTag(tagNumber);
			} catch (BagtagException e) {
				// Ignore
			}
			
			
			if (fullTag != null && basicTag != null) {
				queryString.append(" and (o.claimnum like :basicTag or ");
				queryString.append(" o.claimnum like :fullTag)");
			} else if (fullTag != null) {
				queryString.append(" and o.claimnum like :fullTag");
			} else if (basicTag != null) {
				queryString.append(" and o.claimnum like :basicTag");
			}
			
			Query q = sess.createQuery(queryString.toString());
			
			q.setInteger("closed", TracingConstants.OHD_STATUS_CLOSED);
			
			
			if (basicTag != null) {
				q.setParameter("basicTag", basicTag);
			}
			if (fullTag != null) {
				q.setParameter("fullTag", fullTag);
			}
			
			List<OHD> list = (List<OHD>) q.list();

			if (list.size() == 0) {
				// No matching incidents found
				return null;
			}
			
			return list;
		} catch (Exception e) {
			logger.error("unable to retrieve ohds: " + e);
			e.printStackTrace();
			return null;
		} finally {
			try {
				sess.close();
			} catch (Exception e) {
				logger.error("unable to close connection: " + e);
			}
		}
	}

		public List<OHD> findWtEarlyMove(int hours, List<String> earlyMoveStations, String companyCode) {
			if (hours <= 0) {
				return null;
			}
			Calendar c = new GregorianCalendar();
			c.setTime(TracerDateTime.getGMTDate());
			c.add(Calendar.HOUR, (0-hours));
			Date ohdCutoff = c.getTime();

			String queryString = "select ohd from com.bagnet.nettracer.tracing.db.OHD ohd where " +
			" ohd.wtFile is null " +  //no worldtracer file already
			" and ohd.OHD_ID not in (select q.ohd.OHD_ID from WtqOhdAction q where q.status = :qStatus)" +
			" and ohd.founddate <= :ohdCutoff " + //old enough
			" and ohd.status.status_ID = :status " + // only open
			" and ohd.foundAtStation.company.companyCode_ID = :companyCode " +
			" and ohd.ohd_type = :ohd_type " +
			" and ohd.holdingStation.stationcode in (:earlyStationList) " +
			" order by ohd.founddate desc";
			
			Session sess = null;
			try {
				sess = HibernateWrapper.getSession().openSession();
				Query q = sess.createQuery(queryString);
				q.setParameter("ohdCutoff", ohdCutoff);
				q.setParameter("status", TracingConstants.OHD_STATUS_OPEN);
				q.setParameter("companyCode", companyCode);
				q.setParameter("qStatus", WtqStatus.PENDING);
				q.setParameter("ohd_type", TracingConstants.NOT_MASS_OHD_TYPE);
				q.setParameterList("earlyStationList", earlyMoveStations, StandardBasicTypes.STRING);
				return q.list();
			}
			catch (Exception e) {
				logger.error("unable to get move to WT OHD list: " + e);
				return null;
			} finally {
				if (sess != null) {
					try {
						sess.close();
					} catch (Exception e) {
						logger.error("unable to close hibernate session: " + e);
					}
				}
			}
		}
		
		
		
		
		/**
		 * Perform the insert based on passed in object
		 * 
		 * @param iDTO
		 *          the ohd data to be inserted
		 * @param mod_agent
		 *          agent creating the OHD.
		 * @param createStation
		 *          Station that we would like object associated with in ID(???DA00001)
		 * @return true if succesful; false otherwise
		 */
		public static boolean updateOHD(OHD ohd, Agent mod_agent, Session sess) {
			
			Transaction t = null;
			boolean nullSession = false;

			try {
				if (sess == null) {
					sess = HibernateWrapper.getSession().openSession();
					nullSession = true;
				}
				
				t = sess.beginTransaction();
				
				Date nowGmt = TracerDateTime.getGMTDate();
				
				ohd.setLastupdated(nowGmt);
				ohd.setClose_date(nowGmt);
				sess.saveOrUpdate(ohd);
				t.commit();

				// check to see if we closed the ohd, if we did, then close all matches
				if (ohd.getStatus().getStatus_ID() == TracingConstants.OHD_STATUS_CLOSED) {
					MatchUtils.closeMatches(null, ohd.getOHD_ID());
					ProactiveNotificationBMO.closedOhd(ohd);
				}

				//check if audit is enabled for this company....
				if (ohd.getAgent().getStation().getCompany().getVariable().getAudit_ohd() == 1) {
					Audit_OHD audit_dto = AuditOHDUtils.getAuditOHD(ohd, mod_agent);
					if (audit_dto != null) {
						t = sess.beginTransaction();
						sess.save(audit_dto);
						t.commit();
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				logger.error("unable to insert ohd into database: " + e);
				try {
					t.rollback();
				} catch (Exception e1) {
				}
				return false;
			} finally {
				if (nullSession && sess != null) {
					try {
						sess.close();
					} catch (Exception e) {
						logger.error("unable to close connection: " + e);
					}
				}
			}
			return true;
		}
		public List<OHD> findTagsMoveToWt(String companyCode) {

			Calendar c = new GregorianCalendar();
			c.setTime(TracerDateTime.getGMTDate());
			c.add(Calendar.HOUR, (0-(5*24)));
			Date ohdCutoff = c.getTime();

			String queryString = "select ohd from com.bagnet.nettracer.tracing.db.OHD ohd where " +
			" ohd.wtFile is null " +  //no worldtracer file already
			" and ohd.founddate >= :ohdCutoff " + // No older than 5 days old
			" and ohd.claimnum is not null " +
			" and (ohd.status.status_ID = :status1 or ohd.status.status_ID = :status2) " + // only open, in transit
			" and ohd.foundAtStation.company.companyCode_ID = :companyCode " +
			" and ohd.holdingStation.wt_stationcode is not null and ohd.holdingStation.wt_stationcode != '' " +
			" and (ohd.tagSentToWt = :false or (ohd.tagSentToWt = :true and ohd.tagSentToWtStationId != ohd.holdingStation.station_ID))" +
			" and ohd.tagSentToWt = :false " +
			" order by ohd.holdingStation.station_ID desc";
			
			Session sess = null;
			try {
				sess = HibernateWrapper.getDirtySession().openSession();
				Query q = sess.createQuery(queryString);
				q.setDate("ohdCutoff", ohdCutoff);
				q.setParameter("status1", TracingConstants.OHD_STATUS_OPEN);
				q.setParameter("status2", TracingConstants.OHD_STATUS_IN_TRANSIT);
				q.setParameter("companyCode", companyCode);
				q.setParameter("false", companyCode);
				q.setBoolean("false", false);
				q.setBoolean("true", true);

				return q.list();
			}
			catch (Exception e) {
				logger.error("unable to get move to WT OHD list: " + e);
				return null;
			} finally {
				if (sess != null) {
					try {
						sess.close();
					} catch (Exception e) {
						logger.error("unable to close hibernate session: " + e);
					}
				}
			}		
			}

		/**
		 * Inserts a single remark for an onhand.
		 * 
		 * Since the existing OHDBMO.updateRemarksOnly takes a set of remarks and overwrites the existing set,
		 * this method will load the existing ohd remark set and append the new remark before saving.
		 * 
		 * @param remarkText
		 * @param ohdId
		 * @param agent
		 * @param remarkType
		 * @return boolean
		 */
		
		public boolean insertRemark(String remarkText, String ohdId, Agent agent, int remarkType){
			OHD ohd = findOHDByID(ohdId);
			if(ohd == null){
				return false;
			}
			
			if(agent == null){
				//TODO - we use ogadmin for remarks for other crons such as moveToLZ, maybe we should consider a configuration system or auto agent
				agent = AdminUtils.getAgentBasedOnUsername("ogadmin", "OW");
			}
			
			Remark r = new Remark();
			r.setAgent(agent);
			r.setOhd(ohd);
			r.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(TracerDateTime.getGMTDate()));
			r.setRemarktype(remarkType);
			r.setRemarktext(remarkText);
			ohd.getRemarks().add(r);

			return updateRemarksOnly(ohd.getOHD_ID(), ohd.getRemarks(), agent);
		}
}