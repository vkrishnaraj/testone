/*
 * Created on Jul 6, 2005
 *
 * Administrator
 */
package com.bagnet.nettracer.tracing.utils.audit;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TimeZone;

import org.hibernate.Query;
import org.hibernate.Session;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.CategoryBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Address;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Articles;
import com.bagnet.nettracer.tracing.db.Category;
import com.bagnet.nettracer.tracing.db.DeliveryInstructions;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Incident_Claimcheck;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.Item_Inventory;
import com.bagnet.nettracer.tracing.db.Item_Photo;
import com.bagnet.nettracer.tracing.db.Itinerary;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.db.Remark;
import com.bagnet.nettracer.tracing.db.audit.Audit_Address;
import com.bagnet.nettracer.tracing.db.audit.Audit_AirlineMembership;
import com.bagnet.nettracer.tracing.db.audit.Audit_Articles;
import com.bagnet.nettracer.tracing.db.audit.Audit_Incident;
import com.bagnet.nettracer.tracing.db.audit.Audit_Incident_Claimcheck;
import com.bagnet.nettracer.tracing.db.audit.Audit_Item;
import com.bagnet.nettracer.tracing.db.audit.Audit_Item_Inventory;
import com.bagnet.nettracer.tracing.db.audit.Audit_Item_Photo;
import com.bagnet.nettracer.tracing.db.audit.Audit_Itinerary;
import com.bagnet.nettracer.tracing.db.audit.Audit_Passenger;
import com.bagnet.nettracer.tracing.db.audit.Audit_Remark;
import com.bagnet.nettracer.tracing.forms.audit.AuditMBRForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.HibernateUtils;
import com.bagnet.nettracer.tracing.utils.IncidentUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;

/**
 * @author Administrator
 * 
 * create date - Jul 6, 2005
 */
public class AuditIncidentUtils {

	private static Logger logger = Logger.getLogger(AuditIncidentUtils.class);

	public static List getAuditsForComparison(String audit_incident_id) {

		Session sess = null;

		try {
			sess = HibernateWrapper.getSession().openSession();
			StringBuffer sql = new StringBuffer(512);

			sql
					.append("select aincident from com.bagnet.nettracer.tracing.db.audit.Audit_Incident aincident ");
			sql.append(" where 1=1 ");

			List audit_incident_list = new ArrayList();

			StringTokenizer st = new StringTokenizer(audit_incident_id, ",");

			int i = 0;
			while (st.hasMoreTokens()) {
				audit_incident_list.add(st.nextToken());
				if (i < 1) {
					sql.append(" and audit_incident_id = :audit_incident" + i);
				} else {
					sql.append(" or audit_incident_id = :audit_incident" + i);
				}
				i++;
			}
			sql.append(" order by aincident.audit_incident_id ");

			Query q = sess.createQuery(sql.toString());

			for (int j = 0; j < i; j++) {
				q.setString("audit_incident" + j, (String) audit_incident_list.get(j));
			}
			return q.list();
		} catch (Exception e) {
			logger.error("unable to retrieve audit incidents: " + e);
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

	public static List getClaimAuditsForComparison(String audit_claim_id) {

		Session sess = null;

		try {
			sess = HibernateWrapper.getSession().openSession();
			StringBuffer sql = new StringBuffer(512);

			sql.append("select aclaim from com.bagnet.nettracer.tracing.db.audit.Audit_Claim aclaim ");
			sql.append(" where 1=1 ");

			List audit_claim_list = new ArrayList();

			StringTokenizer st = new StringTokenizer(audit_claim_id, ",");

			int i = 0;
			while (st.hasMoreTokens()) {
				audit_claim_list.add(st.nextToken());
				if (i < 1) {
					sql.append(" and audit_claim_id = :audit_claim" + i);
				} else {
					sql.append(" or audit_claim_id = :audit_claim" + i);
				}
				i++;
			}
			sql.append(" order by aclaim.audit_claim_id ");

			Query q = sess.createQuery(sql.toString());

			for (int j = 0; j < i; j++) {
				q.setString("audit_claim" + j, (String) audit_claim_list.get(j));
			}
			return q.list();
		} catch (Exception e) {
			logger.error("unable to retrieve audit claims: " + e);
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
	 * 
	 * @param form
	 * @param user
	 * @param rowsperpage
	 * @param currpage
	 * @param iscount
	 * @return
	 */
	public static List findIncidentByAuditSearchCriteria(AuditMBRForm form, Agent user,
			int rowsperpage, int currpage, boolean iscount) {

		Session sess = null;

		try {
			sess = HibernateWrapper.getSession().openSession();
			StringBuffer sql = new StringBuffer(512);

			if (iscount) sql
					.append("select count(distinct incident.incident_ID) from com.bagnet.nettracer.tracing.db.Incident incident, com.bagnet.nettracer.tracing.db.audit.Audit_Incident aincident ");
			else sql
					.append("select distinct incident from com.bagnet.nettracer.tracing.db.Incident incident, com.bagnet.nettracer.tracing.db.audit.Audit_Incident aincident ");

			sql.append(" where 1=1 and incident.incident_ID=aincident.incident_ID ");

			sql.append(" and incident.stationassigned.company.companyCode_ID = :companyCode_ID ");

			if (form.getIncident_ID() != null && !form.getIncident_ID().equals("")) {
				sql.append(" and aincident.incident_ID like :incident_id ");
			}

			if (form.getAgent().length() > 0) sql
					.append(" and aincident.modify_agent.username like :agent ");

			if (form.getItemType_ID() > 0) {
				sql.append(" and aincident.itemtype.itemType_ID = :itemType_ID ");
			}

			Date sdate = null, edate = null;
			TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone());
			ArrayList dateal = null;
			if ((dateal = IncidentUtils.calculateDateTimeDiff(form.getS_createtime(),form.getE_createtime(),tz,user)) == null) {
				return null;
			} 
			sdate = (Date)dateal.get(0);edate = (Date)dateal.get(1);
			
			if (sdate != null && !sdate.equals("")) {
				sql.append(" and aincident.modify_time between :sdate and :edate ");
			}
			if (!iscount) sql.append(" order by incident.incident_ID");

			Query q = sess.createQuery(sql.toString());
			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				q.setFirstResult(startnum);
				q.setMaxResults(rowsperpage);
			}

			if (form.getIncident_ID() != null && !form.getIncident_ID().equals("")) {
				q.setString("incident_id", form.getIncident_ID());
			}

			if (form.getItemType_ID() > 0) {
				q.setInteger("itemType_ID", form.getItemType_ID());
			}

			if (sdate != null && !sdate.equals("")) {
				q.setDate("sdate", sdate);
				q.setDate("edate", edate);
			}

			if (form.getAgent().trim().length() > 0) {
				q.setString("agent", form.getAgent().trim());
			}

			q.setString("companyCode_ID", user.getStation().getCompany().getCompanyCode_ID());

			return q.list();
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
	 * 
	 * @param incident_id
	 * @param rowsperpage
	 * @param currpage
	 * @param iscount
	 * @return
	 */
	public static List getAudits(String incident_id, int rowsperpage, int currpage, boolean iscount) {

		Session sess = null;

		try {
			sess = HibernateWrapper.getSession().openSession();
			StringBuffer sql = new StringBuffer(512);
			if (iscount) sql
					.append("select count(aincident.incident_ID) from com.bagnet.nettracer.tracing.db.audit.Audit_Incident aincident ");
			else sql
					.append("select distinct aincident from com.bagnet.nettracer.tracing.db.audit.Audit_Incident aincident ");
			sql.append(" where 1=1 and aincident.incident_ID like :incident_id ");

			if (!iscount) sql.append(" order by aincident.modify_time");
			Query q = sess.createQuery(sql.toString());
			q.setString("incident_id", incident_id);
			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				q.setFirstResult(startnum);
				q.setMaxResults(rowsperpage);
			}

			return q.list();
		} catch (Exception e) {
			logger.error("unable to retrieve audit incident: " + e);
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

	public static List getClaimAudits(String incident_id, int rowsperpage, int currpage,
			boolean iscount) {

		Session sess = null;

		try {
			sess = HibernateWrapper.getSession().openSession();
			StringBuffer sql = new StringBuffer(512);
			if (iscount) sql
					.append("select count(aclaim.audit_claim_id) from com.bagnet.nettracer.tracing.db.audit.Audit_Claim aclaim ");
			else sql
					.append("select distinct aclaim from com.bagnet.nettracer.tracing.db.audit.Audit_Claim aclaim ");
			sql.append(" where 1=1 and aclaim.incident.incident_ID like :incident_id ");

			if (!iscount) sql.append(" order by aclaim.modify_time");
			Query q = sess.createQuery(sql.toString());
			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				q.setFirstResult(startnum);
				q.setMaxResults(rowsperpage);
			}

			q.setString("incident_id", incident_id);
			return q.list();
		} catch (Exception e) {
			logger.error("unable to retrieve audit claim: " + e);
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
	 * 
	 * @param ohd
	 * @param mod_agent
	 * @return @throws
	 *         Exception
	 */
	public static Audit_Incident getAuditIncident(Incident incident, Agent mod_agent) {
		try {
			Audit_Incident audit_inc = new Audit_Incident();
			BeanUtils.copyProperties(audit_inc, incident);
			audit_inc.setModify_time(TracerDateTime.getGMTDate());
			audit_inc.setModify_agent(mod_agent);

			Item item = null;
			Audit_Item a_item = null;
			Item_Photo ip = null;
			Audit_Item_Photo a_ip = null;
			
			Item_Inventory ii = null;
			Audit_Item_Inventory a_ii = null;
			
			ArrayList itemlist = new ArrayList();
			ArrayList iplist = null;
			ArrayList iilist = null;
			if (incident.getItemlist() != null) {
				for (int i = 0; i < incident.getItemlist().size(); i++) {
					item = (Item) incident.getItemlist().get(i);
					a_item = new Audit_Item();
					BeanUtils.copyProperties(a_item, item);
					a_item.setAudit_incident(audit_inc);
					Category c=CategoryBMO.getCategory(item.getAssistDeviceType());
					if(c!=null)
						a_item.setAssistDeviceType(c.getDescription());

					// convert photo
					iplist = new ArrayList();
					if (item.getPhotoes() != null) {
						for (int j = 0; j < item.getPhotolist().size(); j++) {
							ip = (Item_Photo) item.getPhotolist().get(j);
							a_ip = new Audit_Item_Photo();
							BeanUtils.copyProperties(a_ip, ip);
							a_ip.setAudit_item(a_item);
							iplist.add(a_ip);
						}
						a_item.setPhotoes(new HashSet(iplist));

					}
					
					// convert inventory
					iilist = new ArrayList();
					if (item.getInventory() != null) {
						for (int j = 0; j < item.getInventorylist().size(); j++) {
							ii = (Item_Inventory) item.getInventorylist().get(j);
							a_ii = new Audit_Item_Inventory();
							BeanUtils.copyProperties(a_ii, ii);
							a_ii.setAudit_item(a_item);
							iilist.add(a_ii);
						}
						a_item.setInventory(new HashSet(iilist));

					}

					itemlist.add(a_item);
				}
				audit_inc.setItemlist(itemlist);
			}

			Articles ar = null;
			Audit_Articles a_ar = null;
			ArrayList arlist = new ArrayList();
			if (incident.getArticles() != null) {
				ArrayList articles = new ArrayList(incident.getArticles());
				for (int i = 0; i < articles.size(); i++) {
					ar = (Articles) articles.get(i);
					a_ar = new Audit_Articles();
					BeanUtils.copyProperties(a_ar, ar);
					a_ar.setAudit_incident(audit_inc);
					arlist.add(a_ar);
				}
				audit_inc.setArticles(new HashSet(arlist));
			}

			Incident_Claimcheck ic = null;
			Audit_Incident_Claimcheck a_ic = null;
			ArrayList cllist = new ArrayList();
			if (incident.getClaimchecks() != null) {
				for (int i = 0; i < incident.getClaimcheck_list().size(); i++) {
					ic = (Incident_Claimcheck) incident.getClaimcheck_list().get(i);
					a_ic = new Audit_Incident_Claimcheck();
					BeanUtils.copyProperties(a_ic, ic);
					a_ic.setAudit_incident(audit_inc);
					cllist.add(a_ic);
				}
				audit_inc.setClaimchecks(new HashSet(cllist));
			}

			Itinerary it = null;
			Audit_Itinerary a_it = null;
			ArrayList itlist = new ArrayList();
			if (incident.getItinerary() != null) {
				for (int i = 0; i < incident.getItinerary_list().size(); i++) {
					it = (Itinerary) incident.getItinerary_list().get(i);
					a_it = new Audit_Itinerary();
					BeanUtils.copyProperties(a_it, it);
					a_it.setAudit_incident(audit_inc);
					itlist.add(a_it);
				}
				audit_inc.setItinerary(new HashSet(itlist));
			}

			Passenger p = null;
			Audit_Passenger a_p = null;
			Address a = null;
			Audit_Address a_a = null;
			Audit_AirlineMembership audit_mem;

			ArrayList alist = null;
			ArrayList plist = new ArrayList();
			if (incident.getPassengers() != null) {
				for (int i = 0; i < incident.getPassenger_list().size(); i++) {
					p = (Passenger) incident.getPassenger_list().get(i);
					a_p = new Audit_Passenger();
					BeanUtils.copyProperties(a_p, p);
					a_p.setAudit_incident(audit_inc);

					if (p.getMembership() != null) {
						//copy the airline membership as a new entry
						audit_mem = new Audit_AirlineMembership();
						BeanUtils.copyProperties(audit_mem, p.getMembership());
						a_p.setAudit_membership(audit_mem);
					}

					// convert addresses
					alist = new ArrayList();
					if (p.getAddresses() != null) {
						for (int j = 0; j < p.getAddresses().size(); j++) {
							a = (Address) p.getAddress(j);
							a_a = new Audit_Address();
							BeanUtils.copyProperties(a_a, a);
							a_a.setAudit_passenger(a_p);
							alist.add(a_a);
						}
						a_p.setAddresses(new HashSet(alist));

					}

					plist.add(a_p);

				}
				audit_inc.setPassengers(new HashSet(plist));
			}

			Remark r = null;
			Audit_Remark a_r = null;
			ArrayList rlist = new ArrayList();
			if (incident.getRemarks() != null) {
				ArrayList remarks = new ArrayList(incident.getRemarks());
				for (int i = 0; i < remarks.size(); i++) {
					r = (Remark) remarks.get(i);
					a_r = new Audit_Remark();
					a_r.setAudit_incident(audit_inc);
					BeanUtils.copyProperties(a_r, r);
					rlist.add(a_r);
				}
				audit_inc.setRemarks(new HashSet(rlist));
			}
			
			//DeliveryInstructions
			if(PropertyBMO.isTrue(PropertyBMO.PROPERTY_DELIVERY_INSTRUCTIONS) && incident.getItemtype().getItemType_ID()==TracingConstants.LOST_DELAY){
				DeliveryInstructions DI = incident.getDeliveryInstructions();
				if(DI!=null){
					audit_inc.setInstructions(DI.getInstructions());
				} else {
					audit_inc.setInstructions("");
				}
			}

			audit_inc.setOc_claim_id(incident.getOc_claim_id());
			return audit_inc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}