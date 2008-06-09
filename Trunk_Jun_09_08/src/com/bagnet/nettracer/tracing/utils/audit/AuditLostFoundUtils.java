/*
 * Created on Jul 6, 2005
 *
 * Administrator
 */
package com.bagnet.nettracer.tracing.utils.audit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TimeZone;

import org.hibernate.Query;
import org.hibernate.Session;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.LostAndFoundIncident;
import com.bagnet.nettracer.tracing.db.audit.Audit_LostAndFoundIncident;
import com.bagnet.nettracer.tracing.forms.audit.AuditLostFoundForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.IncidentUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;

/**
 * @author Administrator
 * 
 * create date - Jul 6, 2005
 */
public class AuditLostFoundUtils {

	private static Logger logger = Logger.getLogger(AuditLostFoundUtils.class);

	public static List getAuditsForComparison(String audit_lost_found_id) {

		Session sess = null;

		try {
			sess = HibernateWrapper.getSession().openSession();
			StringBuffer sql = new StringBuffer(512);

			sql
					.append("select alfi from com.bagnet.nettracer.tracing.db.audit.Audit_LostAndFoundIncident alfi ");
			sql.append(" where 1=1 ");

			List audit_ohd_list = new ArrayList();

			StringTokenizer st = new StringTokenizer(audit_lost_found_id, ",");

			int i = 0;
			while (st.hasMoreTokens()) {
				audit_ohd_list.add(st.nextToken());
				if (i < 1) {
					sql.append(" and audit_lost_found_id = :audit_lost_found_id" + i);
				} else {
					sql.append(" or audit_lost_found_id = :audit_lost_found_id" + i);
				}
				i++;
			}
			sql.append(" order by alfi.audit_lost_found_id ");

			Query q = sess.createQuery(sql.toString());

			for (int j = 0; j < i; j++) {
				q.setString("audit_lost_found_id" + j, (String) audit_ohd_list.get(j));
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

	/**
	 * 
	 * @param form
	 * @param user
	 * @param rowsperpage
	 * @param currpage
	 * @param iscount
	 * @return
	 */
	public static List findLostFoundByAuditSearchCriteria(AuditLostFoundForm form, Agent user,
			int rowsperpage, int currpage, boolean iscount) {

		Session sess = null;

		try {
			sess = HibernateWrapper.getSession().openSession();
			StringBuffer sql = new StringBuffer(512);

			if (iscount) sql
					.append("select count(distinct lfi.file_ref_number) from com.bagnet.nettracer.tracing.db.LostAndFoundIncident lfi, com.bagnet.nettracer.tracing.db.audit.Audit_LostAndFoundIncident alfi ");
			else sql
					.append("select distinct lfi from com.bagnet.nettracer.tracing.db.LostAndFoundIncident lfi, com.bagnet.nettracer.tracing.db.audit.Audit_LostAndFoundIncident alfi ");

			sql.append(" where 1=1 and lfi.file_ref_number=alfi.file_ref_number ");

			if (form.getFile_ref_number() != null && !form.getFile_ref_number().equals("")) {
				sql.append(" and alfi.file_ref_number like :file_ref_number ");
			}

			if (form.getAgent().length() > 0) sql
					.append(" and alfi.modifying_agent.username like :agent ");

			Date sdate = null, edate = null;
			TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone());
			ArrayList dateal = null;
			if ((dateal = IncidentUtils.calculateDateTimeDiff(form.getS_createtime(),form.getE_createtime(),tz,user)) == null) {
				return null;
			} 
			sdate = (Date)dateal.get(0);edate = (Date)dateal.get(1);
			
			if (sdate != null && !sdate.equals("")) {
				sql.append(" and alfi.time_modified between :sdate and :edate ");
			}

			if (!iscount) sql.append(" order by lfi.file_ref_number");

			Query q = sess.createQuery(sql.toString());

			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				q.setFirstResult(startnum);
				q.setMaxResults(rowsperpage);
			}

			if (form.getFile_ref_number() != null && !form.getFile_ref_number().equals("")) {
				q.setString("file_ref_number", form.getFile_ref_number());
			}

			if (sdate != null && !sdate.equals("")) {
				q.setDate("sdate", sdate);
				q.setDate("edate", edate);
			}

			if (form.getAgent().trim().length() > 0) {
				q.setString("agent", form.getAgent().trim());
			}

			return q.list();
		} catch (Exception e) {
			logger.error("unable to retrieve lost/found: " + e);
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
	 * @param ohd_id
	 * @param rowsperpage
	 * @param currpage
	 * @param iscount
	 * @return
	 */
	public static List getAudits(String file_ref_number, int rowsperpage, int currpage,
			boolean iscount) {

		Session sess = null;

		try {
			sess = HibernateWrapper.getSession().openSession();
			StringBuffer sql = new StringBuffer(512);
			if (iscount) sql
					.append("select count(alfi.file_ref_number) from com.bagnet.nettracer.tracing.db.audit.Audit_LostAndFoundIncident alfi ");
			else sql
					.append("select distinct alfi from com.bagnet.nettracer.tracing.db.audit.Audit_LostAndFoundIncident alfi ");

			sql.append(" where 1=1 and alfi.file_ref_number like :file_ref_number ");

			if (!iscount) sql.append(" order by alfi.time_modified");

			Query q = sess.createQuery(sql.toString());
			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				q.setFirstResult(startnum);
				q.setMaxResults(rowsperpage);
			}
			q.setString("file_ref_number", file_ref_number);
			return q.list();
		} catch (Exception e) {
			logger.error("unable to retrieve audit lost and found: " + e);
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
	public static Audit_LostAndFoundIncident getAuditLostAndFound(LostAndFoundIncident lfi,
			Agent mod_agent) throws Exception {
		Audit_LostAndFoundIncident alfi = new Audit_LostAndFoundIncident();

		alfi.set_DATEFORMAT(lfi.get_DATEFORMAT());
		alfi.set_TIMEFORMAT(lfi.get_TIMEFORMAT());
		alfi.set_TIMEZONE(lfi.get_TIMEZONE());

		alfi.setClose_date(lfi.getClose_date());
		alfi.setClosing_agent(lfi.getClosing_agent());
		alfi.setCreate_date(lfi.getCreate_date());
		alfi.setCreate_station(lfi.getCreate_station());
		alfi.setCustomer_address1(lfi.getCustomer_address1());
		alfi.setCustomer_address2(lfi.getCustomer_address2());
		alfi.setCustomer_city(lfi.getCustomer_city());
		alfi.setCustomer_countrycode_ID(lfi.getCustomer_countrycode_ID());
		alfi.setCustomer_email(lfi.getCustomer_email());
		alfi.setCustomer_firstname(lfi.getCustomer_firstname());
		alfi.setCustomer_lastname(lfi.getCustomer_lastname());
		alfi.setCustomer_mname(lfi.getCustomer_mname());
		alfi.setCustomer_province(lfi.getCustomer_province());
		alfi.setCustomer_state_ID(lfi.getCustomer_state_ID());
		alfi.setCustomer_tel(lfi.getCustomer_tel());
		alfi.setCustomer_zip(lfi.getCustomer_zip());
		alfi.setDateFoundLost(lfi.getDateFoundLost());
		alfi.setDisposal_status(lfi.getDisposal_status());
		alfi.setFile_ref_number(lfi.getFile_ref_number());
		alfi.setFiling_agent(lfi.getFiling_agent());
		alfi.setFinding_agent_name(lfi.getFinding_agent_name());
		alfi.setItem_description(lfi.getItem_description());
		alfi.setLocation(lfi.getLocation());
		alfi.setModifying_agent(mod_agent);
		alfi.setReadonly(lfi.getReadonly());
		alfi.setRemark(lfi.getRemark());
		alfi.setReport_type(lfi.getReport_type());
		alfi.setReport_status(lfi.getReport_status());

		alfi.setTime_modified(TracerDateTime.getGMTDate());

		return alfi;
	}

	public static boolean notEqualObjects(Object val1, Object val2) {
		boolean ret = false;

		if (val1 != val2) {
			if (val1 == null || val2 == null || !val1.equals(val2)) {
				ret = true;
			}
		}
		return ret;
	}

}