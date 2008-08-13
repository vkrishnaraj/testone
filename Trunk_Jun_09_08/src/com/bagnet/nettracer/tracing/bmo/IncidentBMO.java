/*
 * Created on Jul 15, 2004
 *
 * Administrator
 */
package com.bagnet.nettracer.tracing.bmo;

import java.awt.image.DataBufferUShort;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.StaleObjectStateException;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Address;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Articles;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Incident_Assoc;
import com.bagnet.nettracer.tracing.db.Incident_Claimcheck;
import com.bagnet.nettracer.tracing.db.Incident_Range;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.Item_Inventory;
import com.bagnet.nettracer.tracing.db.Item_Photo;
import com.bagnet.nettracer.tracing.db.Itinerary;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.db.Remark;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.audit.Audit_Incident;
import com.bagnet.nettracer.tracing.dto.SearchIncident_DTO;
import com.bagnet.nettracer.tracing.forms.SearchIncidentForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.IncidentUtils;
import com.bagnet.nettracer.tracing.utils.MBRActionUtils;
import com.bagnet.nettracer.tracing.utils.MatchUtils;
import com.bagnet.nettracer.tracing.utils.StringUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.audit.AuditIncidentUtils;

/**
 * @author Administrator
 * 
 * create date - Jul 15, 2004
 */
public class IncidentBMO {
	private static Logger logger = Logger.getLogger(IncidentBMO.class);


	
	public int insertIncident(Incident iDTO, String assoc_ID, Agent mod_agent) throws HibernateException {
		return insertIncident(iDTO, assoc_ID, mod_agent, false);
	}
	
	public int insertIncident(Incident iDTO, String assoc_ID, Agent mod_agent, boolean checkClosedStatus) throws HibernateException {
		boolean oldStatusKept = false;
		Transaction t = null;
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			
			Incident oldinc = null;
			String incident_id = iDTO.getIncident_ID();
			boolean isnew = false;
			if (incident_id == null || incident_id.length() <= 0) {
				isnew = true;
				iDTO.setIncident_ID(getIncidentID(iDTO.getStationcreated()));
			} else {
				oldinc = findIncidentByID(incident_id);
				if (oldinc == null)
					isnew = true;
			}
			
			t = sess.beginTransaction();

			// save incident
			if (iDTO.getIncident_ID() != null) {
				iDTO.setLastupdated(TracerDateTime.getGMTDate());
				if (isnew)
					sess.save(iDTO);
				else {
					if (checkClosedStatus) {
						Status oldStatus = oldinc.getStatus();
						iDTO.setStatus(oldStatus);
						iDTO.setClosedate(oldinc.getClosedate());
						oldStatusKept = true;
					} 
					
					// delete first then insert
					sess.delete(oldinc);
					iDTO = clearIncidentIds(iDTO);
					sess.save(iDTO);
				}
				t.commit();
			} else {
				return 0;
			}

			// change the photo names from temppath_ to incident_ID _

			if (isnew) {
				// update airtran
				if (MBRActionUtils.updateCommentOn()) {
					String formateddatetime = DateUtils.formatDate(TracerDateTime.getGMTDate(), TracingConstants.DB_DATETIMEFORMAT, null, iDTO.get_TIMEZONE());

					MBRActionUtils.updateComment("Baggage Claim (" + iDTO.getIncident_ID() + ") created for this customer on "
							+ formateddatetime, iDTO.getRecordlocator());
				}
				
			}

			// check to see if we opened the report, if we did, then open all matches
			//if (iDTO.getStatus().getStatus_ID() ==
			// TracingConstants.MBR_STATUS_OPEN) {
			//	MatchUtils.openMatches(iDTO.getIncident_ID(), null);
			//}

			// check to see if we closed the report, if we did, then close all matches
			if (iDTO.getStatus().getStatus_ID() == TracingConstants.MBR_STATUS_CLOSED) {
				MatchUtils.closeMatches(iDTO.getIncident_ID(), null);
			}

			// association report
			if (assoc_ID == null || assoc_ID.length() == 0)
				assoc_ID = iDTO.getIncident_ID();
			Incident_Assoc ia = new Incident_Assoc();
			ia.setAssoc_ID(assoc_ID);
			ia.setIncident_ID(iDTO.getIncident_ID());
			ia.setItemtype_ID(iDTO.getItemtype().getItemType_ID());

			List list = sess.createCriteria(Incident_Assoc.class).add(Example.create(ia)).list();

			if (list == null || list.size() == 0) {
				t = sess.beginTransaction();
				sess.save(ia);
				t.commit();
			}

			//check if audit is enabled for this company....
			if ((iDTO.getItemtype().getItemType_ID() == TracingConstants.LOST_DELAY && iDTO.getAgent().getStation().getCompany().getVariable()
					.getAudit_lost_delayed() == 1)
					|| (iDTO.getItemtype().getItemType_ID() == TracingConstants.DAMAGED_BAG && iDTO.getAgent().getStation().getCompany().getVariable()
							.getAudit_damaged() == 1)
					|| (iDTO.getItemtype().getItemType_ID() == TracingConstants.MISSING_ARTICLES && iDTO.getAgent().getStation().getCompany().getVariable()
							.getAudit_missing_articles() == 1)) {
				Audit_Incident audit_dto = AuditIncidentUtils.getAuditIncident(iDTO, mod_agent);

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
			return -1;
		} catch (Exception e) {
			logger.error("unable to insert into database: " + e);

			if (t != null)
				t.rollback();
			return 0;
		} finally {

			sess.close();
		}
		if (oldStatusKept) {
			return 2;
		}
		return 1;
	}

	/**
	 * 
	 * insert incident for worldtracer
	 * @param iDTO
	 * @return
	 * @throws HibernateException
	 */
	public int insertIncidentForWT(Incident iDTO) throws HibernateException {
		Transaction t = null;
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			
			Incident oldinc = null;
			String incident_id = iDTO.getIncident_ID();
			boolean isnew = false;
			if (incident_id == null || incident_id.length() <= 0) {
				isnew = true;
				iDTO.setIncident_ID(getIncidentID(iDTO.getStationcreated()));
			} else {
				oldinc = findIncidentByID(incident_id);
				if (oldinc == null)
					isnew = true;
			}
			
			t = sess.beginTransaction();

			// save incident
			if (iDTO.getIncident_ID() != null) {
				iDTO.setLastupdated(TracerDateTime.getGMTDate());
				if (isnew)
					sess.save(iDTO);
				else {
					// delete first then insert
					sess.delete(oldinc);
					iDTO = clearIncidentIds(iDTO);
					sess.save(iDTO);
				}
				t.commit();
			} else {
				return 0;
			}

			// check to see if we closed the report, if we did, then close all matches
			if (iDTO.getStatus().getStatus_ID() == TracingConstants.MBR_STATUS_CLOSED) {
				MatchUtils.closeMatches(iDTO.getIncident_ID(), null);
			}

			Incident_Assoc ia = new Incident_Assoc();
			ia.setAssoc_ID(iDTO.getIncident_ID());
			ia.setIncident_ID(iDTO.getIncident_ID());
			ia.setItemtype_ID(iDTO.getItemtype().getItemType_ID());

			List list = sess.createCriteria(Incident_Assoc.class).add(Example.create(ia)).list();

			if (list == null || list.size() == 0) {
				t = sess.beginTransaction();
				sess.save(ia);
				t.commit();
			}

			//check if audit is enabled for this company....
			if ((iDTO.getItemtype().getItemType_ID() == TracingConstants.LOST_DELAY && iDTO.getAgent().getStation().getCompany().getVariable()
					.getAudit_lost_delayed() == 1)
					|| (iDTO.getItemtype().getItemType_ID() == TracingConstants.DAMAGED_BAG && iDTO.getAgent().getStation().getCompany().getVariable()
							.getAudit_damaged() == 1)
					|| (iDTO.getItemtype().getItemType_ID() == TracingConstants.MISSING_ARTICLES && iDTO.getAgent().getStation().getCompany().getVariable()
							.getAudit_missing_articles() == 1)) {
				Audit_Incident audit_dto = AuditIncidentUtils.getAuditIncident(iDTO, iDTO.getAgent());

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
			return -1;
		} catch (Exception e) {
			logger.error("unable to insert into database: " + e);

			if (t != null)
				t.rollback();
			return 0;
		} finally {

			sess.close();
		}
		return 1;
	}
	
	public boolean updateIncidentNoAudit(Incident iDTO) throws HibernateException {
		Transaction t = null;
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			t = sess.beginTransaction();

			sess.saveOrUpdate(iDTO);
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

	public int updateRemarksOnly(String incident_id, Set remarks, Agent mod_agent) throws HibernateException {
		Transaction t = null;
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			
			Incident oldinc = null;
			if (incident_id == null || incident_id.length() <= 0) {
				return -1;
			} else {
				oldinc = findIncidentByID(incident_id);
				if (oldinc == null)
					return -1;
			}
			
			t = sess.beginTransaction();
			// save incident
			if (oldinc.getIncident_ID() != null) {
				oldinc.setLastupdated(TracerDateTime.getGMTDate());

				if (remarks != null) {
					oldinc.setRemarks(remarks);
					for (Iterator i = oldinc.getRemarks().iterator(); i.hasNext();) {
						Remark rm = (Remark) i.next();
						rm.setIncident(oldinc);
					}
				} else {
					return -1;
				}
				sess.saveOrUpdate(oldinc);
				t.commit();
			} else {
				return 0;
			}

			//check if audit is enabled for this company....
			if ((oldinc.getItemtype().getItemType_ID() == TracingConstants.LOST_DELAY && oldinc.getAgent().getStation().getCompany().getVariable()
					.getAudit_lost_delayed() == 1)
					|| (oldinc.getItemtype().getItemType_ID() == TracingConstants.DAMAGED_BAG && oldinc.getAgent().getStation().getCompany().getVariable()
							.getAudit_damaged() == 1)
					|| (oldinc.getItemtype().getItemType_ID() == TracingConstants.MISSING_ARTICLES && oldinc.getAgent().getStation().getCompany().getVariable()
							.getAudit_missing_articles() == 1)) {
				Audit_Incident audit_dto = AuditIncidentUtils.getAuditIncident(oldinc, mod_agent);

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
			return -1;
		} catch (Exception e) {
			logger.error("unable to insert into database: " + e);

			if (t != null)
				t.rollback();
			return 0;
		} finally {

			sess.close();
		}
		return 1;
	}

	public Incident findIncidentByID(String incident_ID) {
		Session sess = null;

		try {
			sess = HibernateWrapper.getSession().openSession();
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

	/**
	 * find passenger read only information
	 * 
	 * @param incident_ID
	 * @param name
	 * @return @throws
	 *         HibernateException
	 */
	public Incident findIncidentForPVO(String incident_ID, String name) throws HibernateException {
		Session sess = HibernateWrapper.getSession().openSession();

		try {
			if (name == null || name.length() == 0)
				return null;

			Query q = sess.createQuery("select distinct incident from com.bagnet.nettracer.tracing.db.Incident incident join incident.passengers passenger"
					+ " where incident.incident_ID= :incident_ID and (passenger.lastname like :name or passenger.firstname like :name)");
			q.setString("incident_ID", incident_ID);
			q.setString("name", name);
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
			sess.close();
		}
	}




	
	public List findIncident(SearchIncident_DTO siDTO, Agent user, int rowsperpage, int currpage, boolean iscount) throws HibernateException {
		Session sess = HibernateWrapper.getSession().openSession();
		Query q = null;
		try {
			StringBuffer s = new StringBuffer(512);
			
			TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone());

			
			if (iscount)
				s.append("select count(incident.incident_ID) from com.bagnet.nettracer.tracing.db.Incident incident ");
			else
				s.append("select distinct incident from com.bagnet.nettracer.tracing.db.Incident incident ");

			if (siDTO.getClaimchecknum().length() > 0)
				s.append(" left outer join incident.itemlist item ");
			if (siDTO.getAirline().length() > 0 || siDTO.getFlightnum().length() > 0)
				s.append(" join incident.itinerary itinerary ");
			if (siDTO.getClaimchecknum().length() > 0)
				s.append(" left outer join incident.claimchecks claimcheck ");
			if (siDTO.getFirstname().length() > 0 || siDTO.getMiddlename().length() > 0 || siDTO.getLastname().length() > 0
					|| siDTO.getCompanycode_ID().length() > 0 || siDTO.getMembershipnum().length() > 0)
				s.append(" join incident.passengers passenger ");

			s.append(" where 1=1 ");

			if (siDTO.getIncident_ID().length() > 0)
				s.append(" and incident.incident_ID like :incident_ID ");

			if (siDTO.getTicketnumber().length() > 0)
				s.append("and incident.ticketnumber like :ticketnumber ");

			if (siDTO.getItemType_ID() > 0) {
				s.append(" and incident.itemtype.itemType_ID = :itemType_ID ");
			}

			if (siDTO.getAgent().length() > 0)
				s.append(" and incident.agent.username like :agent ");

			if (siDTO.getStatus_ID() > 0) {
				s.append(" and incident.status.status_ID = :status_ID");
			} else if (siDTO.getStatus_IDs() != null && siDTO.getStatus_IDs().length > 0) {
				StringBuffer sb = new StringBuffer();
				sb.append("(");
				for (int i = 0; i < siDTO.getStatus_IDs().length; i++) {
					if (i > 0)
						sb.append(",");
					sb.append(":status_ID" + i);
				}
				sb.append(")");
				s.append(" and incident.status.status_ID in " + sb.toString());
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
					s.append(" and ((incident.createdate= :startdate and incident.createtime >= :starttime) "
						+ " or (incident.createdate= :enddate1 and incident.createtime <= :starttime)"
						+ " or (incident.createdate > :startdate and incident.createdate <= :enddate))");

				} else {
					s.append(" and ((incident.createdate= :startdate and incident.createtime >= :starttime) "
							+ " or (incident.createdate= :startdate1 and incident.createtime <= :starttime))");
				}
			}

			if (siDTO.getCompanycreated_ID().length() > 0)
				s.append(" and incident.stationcreated.company.companyCode_ID = :companycreated_ID");
			if (siDTO.getStationcreated_ID() > 0)
				s.append(" and incident.stationcreated.station_ID = :stationcreated_ID");
			if (siDTO.getStationassigned_ID() > 0)
				s.append(" and incident.stationassigned.station_ID = :stationassigned_ID");
			if (siDTO.getAgentassigned() != null && siDTO.getAgentassigned().trim().length() > 0)
				s.append(" and incident.agentassigned.username like :agentassigned");

			if (siDTO.getFirstname().length() > 0 || siDTO.getMiddlename().length() > 0 || siDTO.getLastname().length() > 0) {
				s.append(" and passenger.firstname like :firstname");
				s.append(" and passenger.middlename like :middlename");
				s.append(" and passenger.lastname like :lastname");
			}

			if (siDTO.getCompanycode_ID().length() > 0 || siDTO.getMembershipnum().length() > 0) {
				s.append(" and (passenger.membership.membershipnum like :membershipnum");
				s.append(" and passenger.membership.companycode_ID like :companyCode_ID)");
			}

			if (siDTO.getClaimchecknum().length() > 0) {
				s.append(" and (item.claimchecknum like :claimchecknum");
				s.append(" or claimcheck.claimchecknum like :claimchecknum)");
			}

			if (siDTO.getAirline().length() > 0) {
				s.append(" and itinerary.airline like :airline");
			}
			if (siDTO.getFlightnum().length() > 0) {
				s.append(" and itinerary.flightnum like :flightnum");
			}
			
			if (siDTO.getNoAssignedAgent() == 1) {
				s.append(" and incident.agentassigned is null");
			}
			

			if (!iscount)
				s.append(" order by incident.incident_ID");

			q = sess.createQuery(s.toString());

			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				q.setFirstResult(startnum);
				q.setMaxResults(rowsperpage);
			}

			if (siDTO.getIncident_ID().length() > 0)
				q.setString("incident_ID", siDTO.getIncident_ID());

			if (siDTO.getItemType_ID() > 0) {
				q.setInteger("itemType_ID", siDTO.getItemType_ID());
			}
			if (siDTO.getStatus_ID() > 0) {
				q.setInteger("status_ID", siDTO.getStatus_ID());
			} else if (siDTO.getStatus_IDs() != null && siDTO.getStatus_IDs().length > 0) {

				for (int i = 0; i < siDTO.getStatus_IDs().length; i++) {
					q.setInteger("status_ID" + i, siDTO.getStatus_IDs()[i]);
				}

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

			if (siDTO.getCompanycreated_ID().length() > 0)
				q.setString("companycreated_ID", siDTO.getCompanycreated_ID());
			if (siDTO.getStationcreated_ID() > 0)
				q.setInteger("stationcreated_ID", siDTO.getStationcreated_ID());
			if (siDTO.getStationassigned_ID() > 0)
				q.setInteger("stationassigned_ID", siDTO.getStationassigned_ID());
			if (siDTO.getAgentassigned() != null && siDTO.getAgentassigned().trim().length() > 0)
				q.setString("agentassigned", siDTO.getAgentassigned());

			if (siDTO.getTicketnumber() != null & !siDTO.getTicketnumber().equals(""))
				q.setString("ticketnumber", siDTO.getTicketnumber());

			if (siDTO.getFirstname().length() > 0 || siDTO.getMiddlename().length() > 0 || siDTO.getLastname().length() > 0) {

				String a = siDTO.getFirstname().length() == 0 ? "%" : siDTO.getFirstname();
				String b = siDTO.getMiddlename().length() == 0 ? "%" : siDTO.getMiddlename();
				String c = siDTO.getLastname().length() == 0 ? "%" : siDTO.getLastname();

				q.setString("firstname", a);
				q.setString("middlename", b);
				q.setString("lastname", c);
			}

			if (siDTO.getCompanycode_ID().length() > 0 || siDTO.getMembershipnum().length() > 0) {
				q.setString("membershipnum", siDTO.getMembershipnum());
				q.setString("companyCode_ID", siDTO.getCompanycode_ID());
			}

			if (siDTO.getClaimchecknum().length() > 0)
				q.setString("claimchecknum", siDTO.getClaimchecknum().trim());

			if (siDTO.getAirline().length() > 0) {
				q.setString("airline", siDTO.getAirline().toUpperCase());
			}
			if (siDTO.getFlightnum().length() > 0) {
				q.setString("flightnum", siDTO.getFlightnum().toUpperCase());

			}

			if (siDTO.getAgent().length() > 0)
				q.setString("agent", siDTO.getAgent());

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
	 * automatically generate 10 character incident number 3 alpha/7 numeric
	 * 
	 * @return
	 */
	public String getIncidentID(Station stationcreated) throws HibernateException {
		Session sess = HibernateWrapper.getSession().openSession();
		// get highest num from Incident_Range table
		try {

			stationcreated = findStationByID(stationcreated.getStation_ID());

			if (stationcreated == null) {
				logger.error("invalid station");
				return null;
			}
			String stationcode = stationcreated.getStationcode();
			String companycode = stationcreated.getCompany().getCompanyCode_ID();

			// insert into the incident_range with this companycode
			Transaction t = sess.beginTransaction();
			Incident_Range ir = new Incident_Range();
			ir.setCompanycode_ID(companycode);
			sess.save(ir);
			t.commit();
			long newnum = ir.getCurrent_num();
			if (newnum == 0) {
				logger.error("unable to create a new incident number");
				return null;
			}

			// get the last record with this companycode_ID;
			Query q = sess.createQuery("select count(incident_range.current_num) from com.bagnet.nettracer.tracing.db.Incident_Range incident_range "
					+ "where incident_range.companycode_ID = :companycode_ID group by incident_range.companycode_ID");

			q.setString("companycode_ID", companycode);

			List list = q.list();

			if (list == null || list.size() == 0) {
				// starts off at 1
				newnum = 1;
			} else {
				newnum = ((Long) list.get(0)).intValue();
				// get the next incident_ID number (-1 because not counting this newly
				// inserted one)
				//newnum = ((Long) o[0]).longValue() - 1;
			}

			StringBuffer s = new StringBuffer();
			s.append(stationcode);
			s.append(companycode);
			String num = Long.toString(newnum);
			// padd new number to # digits that total length will equal to
			// tracingconstants.incident_len
			for (int i = 0; i < TracingConstants.INCIDENT_LEN - companycode.length() - stationcode.length() - num.length(); i++) {
				s.append("0");
			}
			s.append(num);
			String incident_num = s.toString().toUpperCase();

			// making sure there isn't another incident id in the table already
			// this would have never happen in production
			q = sess.createQuery("from com.bagnet.nettracer.tracing.db.Incident incident " + "where incident.incident_ID = :incident_ID");

			q.setString("incident_ID", incident_num);

			list = q.list();
			if (list != null && list.size() > 0) {
				return getIncidentID(stationcreated);
			} else {
				return incident_num;
			}

		} catch (Exception e) {
			logger.error("unable to create a new incident number: " + e);
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
		}
	}

	public Station findStationByID(int station_ID) throws HibernateException {
		Session sess = HibernateWrapper.getSession().openSession();
		try {

			Query q = sess.createQuery("select station from com.bagnet.nettracer.tracing.db.Station station " + "where station.station_ID= :station_ID");
			q.setParameter("station_ID", new Integer(station_ID));

			List list = q.list();
			if (list.size() == 0) {
				logger.debug("unable to find station: " + station_ID);
				return null;
			}
			Station station = (Station) list.get(0);

			return station;
		} catch (Exception e) {
			logger.error("unable to retrieve station: " + e);
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
		}
	}

	public ArrayList getAssocReports(String incident_ID) throws HibernateException {
		Session sess = HibernateWrapper.getSession().openSession();

		try {
			Query q = sess.createQuery("from com.bagnet.nettracer.tracing.db.Incident_Assoc a " + "where a.incident_ID= :incident_ID");
			q.setParameter("incident_ID", incident_ID);
			List list = q.list();

			if (list.size() == 0) {
				logger.debug("unable to find association: " + incident_ID);
				return null;
			}
			Incident_Assoc ia = (Incident_Assoc) list.get(0);
			q = sess.createQuery("from com.bagnet.nettracer.tracing.db.Incident_Assoc a " + "where a.assoc_ID= :assoc_ID");
			q.setParameter("assoc_ID", ia.getAssoc_ID());
			list = q.list();

			if (list.size() == 0) {
				logger.debug("unable to find association: " + ia.getAssoc_ID());
				return null;
			}
			return (ArrayList) list;
		} catch (Exception e) {
			logger.error("unable to retrieve incident: " + e);
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
		}
	}

	/**
	 * custom query script
	 * 
	 * @param siDTO
	 * @param user
	 * @param rowsperpage
	 * @param currpage
	 * @param iscount
	 * @return @throws
	 *         HibernateException
	 */
	public List customQuery(SearchIncidentForm siDTO, Agent user, int rowsperpage, int currpage, boolean iscount, String searchType) throws HibernateException {
		Session sess = HibernateWrapper.getSession().openSession();
		Query q = null;
		try {
			StringBuffer s = new StringBuffer(512);
			
			TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone());

			
			if (iscount)
				s.append("select count(incident.incident_ID) from com.bagnet.nettracer.tracing.db.Incident incident ");
			else
				s.append("select distinct incident from com.bagnet.nettracer.tracing.db.Incident incident ");
			

			if (siDTO.getClaimchecknum().length() > 0 || siDTO.getColor().length() > 0 || siDTO.getBagtype().length() > 0
					|| siDTO.getXdescelement_ID1() > 0 || siDTO.getXdescelement_ID2() > 0 || siDTO.getXdescelement_ID3() > 0 || siDTO.getManufacturer_ID() > 0
					|| siDTO.getCategory_ID() > 0 || siDTO.getDescription().length() > 0)
				s.append(" join incident.itemlist item ");
			if (siDTO.getCategory_ID() > 0 || siDTO.getDescription().length() > 0)
				s.append(" join item.inventory inventory ");
			if (siDTO.getFlightnum().length() > 0 || siDTO.getAirline().length() > 0)
				s.append(" join incident.itinerary itinerary ");
			if (siDTO.getClaimchecknum().length() > 0)
				s.append(" left outer join incident.claimchecks claimcheck ");
			if (siDTO.getFirstname().length() > 0 || siDTO.getMiddlename().length() > 0 || siDTO.getLastname().length() > 0
					|| siDTO.getCompanycode_ID().length() > 0 || siDTO.getMembershipnum().length() > 0 || siDTO.getAddress1().length() > 0 || siDTO.getAddress2().length() > 0
					|| siDTO.getCity().length() > 0 || (siDTO.getState_ID() != null && siDTO.getState_ID().length() > 0) || (siDTO.getProvince() != null && siDTO.getProvince().length() > 0) || siDTO.getZip().length() > 0 || siDTO.getPhone().length() > 0 || siDTO.getCountrycode_ID().length() > 0
					|| siDTO.getEmail().length() > 0) {
				s.append(" join incident.passengers passenger ");
				s.append(" join passenger.addresses address");
			}

			if (searchType.equals("1")) {
				s.append(" where 1=1 and incident.itemtype.itemType_ID = " + TracingConstants.LOST_DELAY);
			}
			else if (searchType.equals("2")) {
				s.append(" where 1=1 and incident.itemtype.itemType_ID = " + TracingConstants.DAMAGED_BAG);
			}
			else if (searchType.equals("3")) {
				s.append(" where 1=1 and incident.itemtype.itemType_ID = " + TracingConstants.MISSING_ARTICLES);
			} else {
				// Includes searchType 4.
				s.append(" where 1=1 ");
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
					s.append(" and ((incident.createdate= :startdate and incident.createtime >= :starttime) "
						+ " or (incident.createdate= :enddate1 and incident.createtime <= :starttime)"
						+ " or (incident.createdate > :startdate and incident.createdate <= :enddate))");

				} else {
					s.append(" and ((incident.createdate= :startdate and incident.createtime >= :starttime) "
							+ " or (incident.createdate= :startdate1 and incident.createtime <= :starttime))");
				}
			}

			// record locator
			if (siDTO.getRecordlocator().length() > 0) {
				s.append(" and incident.recordlocator like :recordlocator");
			}
			
			// passenger
			if (siDTO.getFirstname().length() > 0 || siDTO.getMiddlename().length() > 0 || siDTO.getLastname().length() > 0) {
				s.append(" and passenger.firstname like :firstname");
				s.append(" and passenger.middlename like :middlename");
				s.append(" and passenger.lastname like :lastname");
			}

			if (siDTO.getCompanycode_ID().length() > 0 || siDTO.getMembershipnum().length() > 0) {
				s.append(" and (passenger.membership.membershipnum like :membershipnum");
				s.append(" and passenger.membership.companycode_ID like :companyCode_ID)");
			}

			// addresses

			if (siDTO.getAddress1().length() > 0)
				s.append(" and (address.address1 like :addr1)");
			if (siDTO.getAddress2().length() > 0)
				s.append(" and (address.address2 like :addr2)");
			if (siDTO.getCity().length() > 0)
				s.append(" and address.city like :city");
			if (siDTO.getState_ID() != null && siDTO.getState_ID().length() > 0)
				s.append(" and address.state_ID like :state_ID");
			if (siDTO.getProvince() != null && siDTO.getProvince().length() > 0)
				s.append(" and address.province like :province");
			if (siDTO.getCountrycode_ID().length() > 0) {
				s.append(" and address.countrycode_ID like :countrycode_ID");
			}
			if (siDTO.getZip().length() > 0)
				s.append(" and address.zip like :zip");
			if (siDTO.getPhone().length() > 0)
				s.append(" and (address.homephone like :phone or address.workphone like :phone "
						+ " or address.mobile like :phone or address.pager like :phone or address.altphone like :phone)");
			if (siDTO.getEmail().length() > 0)
				s.append(" and address.email like :email");

			// bag
			if (siDTO.getClaimchecknum().length() > 0) {
				s.append(" and (item.claimchecknum like :claimchecknum");
				s.append(" or claimcheck.claimchecknum like :claimchecknum)");
			}

			if (siDTO.getColor().length() > 0)
				s.append(" and item.color like :color");
			if (siDTO.getBagtype().length() > 0)
				s.append(" and item.bagtype like :bagtype");
			if (siDTO.getXdescelement_ID1() > 0)
				s.append(" and (item.xdescelement_ID_1 = :xdesc1 or item.xdescelement_ID_2 = :xdesc1 or item.xdescelement_ID_3 = :xdesc1)");
			if (siDTO.getXdescelement_ID2() > 0)
				s.append(" and (item.xdescelement_ID_1 = :xdesc2 or item.xdescelement_ID_2 = :xdesc2 or item.xdescelement_ID_3 = :xdesc2)");
			if (siDTO.getXdescelement_ID3() > 0)
				s.append(" and (item.xdescelement_ID_1 = :xdesc3 or item.xdescelement_ID_2 = :xdesc3 or item.xdescelement_ID_3 = :xdesc3)");
			if (siDTO.getManufacturer_ID() > 0)
				s.append(" and item.manufacturer_ID = :manu_ID");
			if (siDTO.getManufacturer_other().length() > 0)
				s.append(" and item.manufacturer_other like :manu_other");

			if (siDTO.getCategory_ID() > 0)
				s.append(" and inventory.categorytype_ID = :category_ID ");

			if (siDTO.getDescription().length() > 0) {

				//String[] words =
				// StringUtils.removePronouns(siDTO.getDescription()).split("\\s");
				String[] words = StringUtils.removePronouns(siDTO.getDescription().trim()).replace(' ', ',').split("\\,");
				int tempx = 0;
				for (int x = 0; x < words.length; x++) {
					if (words[x].trim().length() > 0) {
						if (tempx == 0)
							s.append(" and (");
						else
							s.append(" or ");
						s.append(" inventory.description like :description" + x + " ");
						if (x == words.length - 1)
							s.append(") ");
						tempx++;
					}
				}
			}

			if (siDTO.getFlightnum().length() > 0)
				s.append(" and itinerary.flightnum like :flightnum");

			if (siDTO.getAirline().length() > 0)
				s.append(" and itinerary.airline like :airline");
			
			if (siDTO.getStatus_ID() > 0)
				s.append(" and incident.status.status_ID = :status_ID");

			if (!iscount)
				s.append(" order by incident.incident_ID");

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
			
			if (siDTO.getStatus_ID() > 0)
				q.setInteger("status_ID", siDTO.getStatus_ID());
			
			if (siDTO.getRecordlocator().length() > 0) {
				q.setString("recordlocator", siDTO.getRecordlocator());
			}

			if (siDTO.getFirstname().length() > 0 || siDTO.getMiddlename().length() > 0 || siDTO.getLastname().length() > 0) {

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
			if (siDTO.getCity().length() > 0)
				q.setString("city", siDTO.getCity().toUpperCase());
			if (siDTO.getState_ID() != null && siDTO.getState_ID().length() > 0)
				q.setString("state_ID", siDTO.getState_ID().toUpperCase());
			if (siDTO.getProvince() != null && siDTO.getProvince().length() > 0)
				q.setString("province", siDTO.getProvince().toUpperCase());
			if (siDTO.getCountrycode_ID().length() > 0)
				q.setString("countrycode_ID", siDTO.getCountrycode_ID().toUpperCase());
			if (siDTO.getZip().length() > 0)
				q.setString("zip", siDTO.getZip().toUpperCase());
			if (siDTO.getPhone().length() > 0)
				q.setString("phone", siDTO.getPhone().toUpperCase());
			if (siDTO.getEmail().length() > 0)
				q.setString("email", siDTO.getEmail().toUpperCase());

			if (siDTO.getCompanycode_ID().length() > 0 || siDTO.getMembershipnum().length() > 0) {
				q.setString("membershipnum", siDTO.getMembershipnum());
				q.setString("companyCode_ID", siDTO.getCompanycode_ID());
			}

			if (siDTO.getClaimchecknum().length() > 0)
				q.setString("claimchecknum", siDTO.getClaimchecknum().trim());

			if (siDTO.getColor().length() > 0)
				q.setString("color", siDTO.getColor().toUpperCase());
			if (siDTO.getBagtype().length() > 0)
				q.setString("bagtype", siDTO.getBagtype().toUpperCase());
			if (siDTO.getXdescelement_ID1() > 0)
				q.setInteger("xdesc1", siDTO.getXdescelement_ID1());
			if (siDTO.getXdescelement_ID2() > 0)
				q.setInteger("xdesc2", siDTO.getXdescelement_ID2());
			if (siDTO.getXdescelement_ID3() > 0)
				q.setInteger("xdesc3", siDTO.getXdescelement_ID3());
			if (siDTO.getManufacturer_ID() > 0)
				q.setInteger("manu_ID", siDTO.getManufacturer_ID());
			if (siDTO.getManufacturer_other().length() > 0)
				q.setString("manu_other", siDTO.getManufacturer_other().toUpperCase().trim());

			if (siDTO.getCategory_ID() > 0)
				q.setInteger("category_ID", siDTO.getCategory_ID());
			if (siDTO.getDescription().length() > 0) {
				//String[] words =
				// StringUtils.removePronouns(siDTO.getDescription()).split("\\s");
				String[] words = StringUtils.removePronouns(siDTO.getDescription().trim()).replace(' ', ',').split("\\,");
				for (int x = 0; x < words.length; x++) {
					if (words[x].trim().length() > 0) {
						q.setString("description" + x, "%" + (words[x]).trim() + "%");
					}
				}
			}

			if (siDTO.getFlightnum().length() > 0)
				q.setString("flightnum", siDTO.getFlightnum().toUpperCase());
			if (siDTO.getAirline().length() > 0)
				q.setString("airline", siDTO.getAirline().toUpperCase());

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

	public List findBDOList(String incident_ID) {
		Session sess = null;
		try {
			String query = "select bdo from com.bagnet.nettracer.tracing.db.BDO bdo "
					+ "where bdo.incident.incident_ID = :incident_ID  order by bdo.BDO_ID";
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(query);
			q.setString("incident_ID", incident_ID);
			List list = q.list();
			if (list.size() == 0) {
				logger.debug("unable to find bdo from incident_ID: " + incident_ID);
				return null;
			}
			return list;
		} catch (Exception e) {
			logger.error("unable to retrieve bdo: " + e);
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
	 * 
	 * @param inc
	 * @return
	 */
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
	
	public void incrementPrintedReceipt(String incident_id) {
		Incident inc = this.findIncidentByID(incident_id);
		if (inc.getPrintedreceipt() == null) {
			inc.setPrintedreceipt(TracerDateTime.getGMTDate());
			this.updateIncidentNoAudit(inc);
		}
	}
}