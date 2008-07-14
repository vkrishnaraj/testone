package com.bagnet.nettracer.tracing.bmo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.StaleObjectStateException;
import org.hibernate.Transaction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Expression;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
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
import com.bagnet.nettracer.tracing.db.audit.Audit_OHD;
import com.bagnet.nettracer.tracing.dto.Ohd_DTO;
import com.bagnet.nettracer.tracing.forms.SearchIncidentForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.IncidentUtils;
import com.bagnet.nettracer.tracing.utils.MatchUtils;
import com.bagnet.nettracer.tracing.utils.StringUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.audit.AuditOHDUtils;

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
	 * @return true if succesful; false otherwise
	 */
	public boolean insertOHD(OHD iDTO, Agent mod_agent) {
		Transaction t = null;
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			
			OHD ohd = null;
			OHD oldinc = null;
			String incident_id = iDTO.getOHD_ID();
			boolean isnew = false;
			if (incident_id == null || incident_id.length() <= 0) {
				isnew = true;
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
			}

			//check if audit is enabled for this company....
			if (iDTO.getAgent().getStation().getCompany().getVariable().getAudit_ohd() == 1) {
				System.out.println(iDTO.getAgent().getStation().getCompany().getVariable().getAudit_ohd());
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
	public OHD findOHDByID(String ohd_ID) {
		Session sess = null;
		try {
			String query = "select ohd from com.bagnet.nettracer.tracing.db.OHD ohd "
					+ "where ohd.OHD_ID=:ohd_ID";
			sess = HibernateWrapper.getSession().openSession();
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
					sess.close();
				} catch (Exception e) {
					logger.error("unable to close connection: " + e);
				}
			}
		}
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
			int currpage, boolean iscount) {

		Session sess = null;

		try {
			sess = HibernateWrapper.getSession().openSession();
			StringBuffer sql = new StringBuffer(512);
			
			TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone());


			if (iscount) sql
					.append("select count(ohd.OHD_ID) from com.bagnet.nettracer.tracing.db.OHD ohd ");
			else sql.append("select distinct ohd from com.bagnet.nettracer.tracing.db.OHD ohd ");

			if (oDTO.getFirstname().trim().length() > 0 || oDTO.getLastname().trim().length() > 0
					|| oDTO.getMiddlename().trim().length() > 0) {
				sql.append(" join ohd.passengers passengers ");
			}

			if ((oDTO.getOHD_categorytype_ID() != null && !oDTO.getOHD_categorytype_ID().equals("0"))
					|| (oDTO.getDescription() != null && !oDTO.getDescription().equals(""))) {
				sql.append(" join ohd.items items ");
			}

			if (oDTO.getAirline().length() > 0 || oDTO.getFlightnum().trim().length() > 0) {
				sql.append(" join ohd.itinerary itinerary ");
			}

			sql.append(" where 1=1 ");

			if (oDTO.getOhd_ID() != null && !oDTO.getOhd_ID().equals("")) {
				sql.append(" and ohd.OHD_ID like :OHD_ID ");
			}
			if (oDTO.getTicketnumber() != null && !oDTO.getTicketnumber().equals("")) {
				sql.append(" and ohd.claimnum like :claimnum ");
			}

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
				sql.append(" and passengers.firstname like :firstname ");
			}

			if (oDTO.getLastname() != null && oDTO.getLastname().trim().length() > 0) {
				sql.append(" and passengers.lastname like :lastname ");
			}

			if (oDTO.getMiddlename() != null && oDTO.getMiddlename().trim().length() > 0) {
				sql.append(" and passengers.middlename like :middlename ");
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

			if (oDTO.getStatus_ID() != null && !oDTO.getStatus_ID().equals("")) {
				sql.append(" and ohd.status.status_ID = :status_ID ");
			}
			
			if (oDTO.getAirline() != null && oDTO.getAirline().length() > 0) {
				sql.append(" and itinerary.airline like :airline");
			}
			if (oDTO.getFlightnum() != null && oDTO.getFlightnum().length() > 0) {
				sql.append(" and itinerary.flightnum like :flightnum");
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

			
			if (!iscount) sql.append(" order by ohd.OHD_ID");

			Query q = sess.createQuery(sql.toString());

			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				q.setFirstResult(startnum);
				q.setMaxResults(rowsperpage);
			}

			if (oDTO.getOhd_ID() != null && !oDTO.getOhd_ID().equals("")) {
				q.setString("OHD_ID", oDTO.getOhd_ID());
			}
			if (oDTO.getTicketnumber() != null && !oDTO.getTicketnumber().equals("")) {
				q.setString("claimnum", oDTO.getTicketnumber());
			}

			if (oDTO.getFoundCompany() != null && !oDTO.getFoundCompany().equals("")) {
				q.setString("fcompanyCode", oDTO.getFoundCompany());
			}
			
			if (oDTO.getFoundStation() != null && !oDTO.getFoundStation().equals("")) {
				q.setString("fstationcode", oDTO.getFoundStation().trim().toUpperCase());
			}
			

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

			if (oDTO.getStatus_ID() != null && !oDTO.getStatus_ID().equals("")) {
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
		Session sess = HibernateWrapper.getSession().openSession();
		Query q = null;
		try {
			StringBuffer s = new StringBuffer(512);
			
			TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone());


			if (iscount) s
					.append("select count(ohd.OHD_ID) from com.bagnet.nettracer.tracing.db.OHD ohd ");
			else s.append("select distinct ohd from com.bagnet.nettracer.tracing.db.OHD ohd ");

			if (siDTO.getFlightnum().length() > 0 || siDTO.getAirline().length() > 0) s
					.append(" join ohd.itinerary itinerary ");

			if (siDTO.getDescription().length() > 0 || siDTO.getCategory_ID() > 0) s
					.append(" join ohd.items item");

			if (siDTO.getFirstname().length() > 0 || siDTO.getMiddlename().length() > 0
					|| siDTO.getLastname().length() > 0 || siDTO.getAddress().length() > 0
					|| siDTO.getCity().length() > 0 || siDTO.getState_ID().length() > 0
					|| siDTO.getZip().length() > 0 || siDTO.getPhone().length() > 0
					|| siDTO.getEmail().length() > 0) {
				s.append(" join ohd.passengers passenger ");
				s.append(" join passenger.addresses address");
			}

			s.append(" where 1=1 ");

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

			// record locator
			if (siDTO.getRecordlocator().length() > 0) {
				s.append(" and ohd.recordlocator like :recordlocator");
			}
			
			// passenger
			if (siDTO.getFirstname().length() > 0 || siDTO.getMiddlename().length() > 0
					|| siDTO.getLastname().length() > 0) {
				s.append(" and passenger.firstname like :firstname");
				s.append(" and passenger.middlename like :middlename");
				s.append(" and passenger.lastname like :lastname");
			}

			if (siDTO.getCompanycode_ID().length() > 0 || siDTO.getMembershipnum().length() > 0) {
				s.append(" and (ohd.membership.membershipnum like :membershipnum");
				s.append(" and ohd.membership.companycode_ID like :companyCode_ID)");
			}

			// addresses

			if (siDTO.getAddress().length() > 0) s
					.append(" and (address.address1 like :addr or address.address2 like :addr)");
			if (siDTO.getCity().length() > 0) s.append(" and address.city like :city");
			if (siDTO.getState_ID().length() > 0) s.append(" and address.state_ID like :state_ID");
			if (siDTO.getZip().length() > 0) s.append(" and address.zip like :zip");
			if (siDTO.getPhone().length() > 0) s
					.append(" and (address.homephone like :phone or address.workphone like :phone "
							+ " or address.mobile like :phone or address.pager like :phone or address.altphone like :phone)");
			if (siDTO.getEmail().length() > 0) s.append(" and address.email like :email");

			// bag
			if (siDTO.getClaimchecknum().length() > 0) s.append(" and ohd.claimnum like :claimchecknum");

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

			if (siDTO.getAirline().length() > 0) s.append(" and itinerary.airline like :airline");
			
			if (siDTO.getStatus_ID() > 0) s.append(" and ohd.status.status_ID = :status_ID");

			if (!iscount) s.append(" order by ohd.OHD_ID");
			
			q = sess.createQuery(s.toString());
			
			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				q.setFirstResult(startnum);
				q.setMaxResults(rowsperpage);
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

			if (siDTO.getStatus_ID() > 0) {
				q.setInteger("status_ID", siDTO.getStatus_ID());
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
			if (siDTO.getAddress().length() > 0) q.setString("addr", siDTO.getAddress().toUpperCase());
			if (siDTO.getCity().length() > 0) q.setString("city", siDTO.getCity().toUpperCase());
			if (siDTO.getState_ID().length() > 0) q.setString("state_ID", siDTO.getState_ID()
					.toUpperCase());
			if (siDTO.getZip().length() > 0) q.setString("zip", siDTO.getZip().toUpperCase());
			if (siDTO.getPhone().length() > 0) q.setString("phone", siDTO.getPhone().toUpperCase());
			if (siDTO.getEmail().length() > 0) q.setString("email", siDTO.getEmail().toUpperCase());

			if (siDTO.getCompanycode_ID().length() > 0 || siDTO.getMembershipnum().length() > 0) {
				q.setString("membershipnum", siDTO.getMembershipnum());
				q.setString("companyCode_ID", siDTO.getCompanycode_ID());
			}

			if (siDTO.getClaimchecknum().length() > 0) q.setString("claimchecknum", siDTO
					.getClaimchecknum());

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

			List results = q.list();
			return results;
		} catch (Exception e) {
			logger.error("unable to retrieve incident in findIncident: " + e);
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
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
			Query q = sess
					.createQuery("select count(ohd_range.current_num) from com.bagnet.nettracer.tracing.db.OHD_Range ohd_range "
							+ "where ohd_range.companycode_ID = :companycode_ID group by ohd_range.companycode_ID");
			q.setString("companycode_ID", companycode);
			List list = q.list();

			if (list == null || list.size() == 0) {
				// starts off at 1
				newnum = 1;
			} else {
				newnum = ((Long) list.get(0)).intValue();
			}

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
		try {
			String query = "select station from com.bagnet.nettracer.tracing.db.Station station "
					+ "where station.station_ID=:station_ID";
			Session sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(query);
			q.setInteger("station_ID", station_ID);
			List list = q.list();

			if (list.size() == 0) {
				logger.debug("unable to find station: " + station_ID);
				return null;
			}
			Station station = (Station) list.get(0);
			sess.close();
			return station;
		} catch (Exception e) {
			logger.error("unable to retrieve station: " + e);
			return null;
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
}