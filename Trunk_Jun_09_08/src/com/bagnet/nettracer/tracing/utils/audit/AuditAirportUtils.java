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

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Airport;
import com.bagnet.nettracer.tracing.db.audit.Audit_Airport;
import com.bagnet.nettracer.tracing.forms.audit.AuditAirportForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.IncidentUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;

/**
 * @author Administrator
 * 
 * create date - Jul 6, 2005
 */
public class AuditAirportUtils {

	private static Logger logger = Logger.getLogger(AuditAirportUtils.class);

	public static List getAuditsForComparison(String audit_id) {

		Session sess = null;

		try {
			sess = HibernateWrapper.getSession().openSession();
			StringBuffer sql = new StringBuffer(512);

			sql.append("select aa from com.bagnet.nettracer.tracing.db.audit.Audit_Airport aa ");
			sql.append(" where 1=1 ");

			List audit_airport_list = new ArrayList();

			StringTokenizer st = new StringTokenizer(audit_id, ",");

			int i = 0;
			while (st.hasMoreTokens()) {
				audit_airport_list.add(st.nextToken());
				if (i < 1) {
					sql.append(" and audit_id = :audit_id" + i);
				} else {
					sql.append(" or audit_id = :audit_id" + i);
				}
				i++;
			}
			sql.append(" order by aa.audit_id ");

			Query q = sess.createQuery(sql.toString());

			for (int j = 0; j < i; j++) {
				q.setString("audit_id" + j, (String) audit_airport_list.get(j));
			}
			return q.list();
		} catch (Exception e) {
			logger.error("unable to retrieve airport: " + e);
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
	public static List findAirportsByAuditSearchCriteria(AuditAirportForm form, Agent user,
			int rowsperpage, int currpage, boolean iscount) {

		Session sess = null;

		try {
			sess = HibernateWrapper.getSession().openSession();
			StringBuffer sql = new StringBuffer(512);

			if (iscount) sql
					.append("select count(distinct aa.id) from com.bagnet.nettracer.tracing.db.audit.Audit_Airport aa ");
			else sql
					.append("select distinct aa from com.bagnet.nettracer.tracing.db.audit.Audit_Airport aa ");

			sql.append(" where 1=1 and aa.companyCode_ID = :companycode");

			if (form.getAirport_code() != null && !form.getAirport_code().equals("")) {
				sql.append(" and aa.airport_code like :airport_code ");
			}

			if (form.getAgent().length() > 0) sql.append(" and aa.modifying_agent.username like :agent ");

			Date sdate = null, edate = null;
			TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone());
			ArrayList dateal = null;
			if ((dateal = IncidentUtils.calculateDateTimeDiff(form.getS_createtime(),form.getE_createtime(),tz,user)) == null) {
				return null;
			} 
			sdate = (Date)dateal.get(0);edate = (Date)dateal.get(1);
			
			if (sdate != null && !sdate.equals("")) {
				sql.append(" and aa.time_modified between :sdate and :edate ");
			}

			sql
					.append(" and aa.audit_id in (select max(ax.audit_id) from com.bagnet.nettracer.tracing.db.audit.Audit_Airport ax group by ax.id) ");
			if (!iscount) sql.append(" order by aa.id desc ");

			Query q = sess.createQuery(sql.toString());
			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				q.setFirstResult(startnum);
				q.setMaxResults(rowsperpage);
			}

			if (form.getAirport_code() != null && !form.getAirport_code().equals("")) {
				q.setString("airport_code", form.getAirport_code());
			}

			if (sdate != null && !sdate.equals("")) {
				q.setDate("sdate", sdate);
				q.setDate("edate", edate);
			}

			if (form.getAgent().trim().length() > 0) {
				q.setString("agent", form.getAgent().trim());
			}

			q.setString("companycode", user.getStation().getCompany().getCompanyCode_ID());

			return q.list();
		} catch (Exception e) {
			logger.error("unable to retrieve airport: " + e);
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
	public static List getAudits(String id, int rowsperpage, int currpage, boolean iscount) {

		Session sess = null;

		try {
			sess = HibernateWrapper.getSession().openSession();
			StringBuffer sql = new StringBuffer(512);
			if (iscount) sql
					.append("select count(aa.id) from com.bagnet.nettracer.tracing.db.audit.Audit_Airport aa ");
			else sql
					.append("select distinct aa from com.bagnet.nettracer.tracing.db.audit.Audit_Airport aa ");

			sql.append(" where 1=1 and aa.id like :id ");

			if (!iscount) sql.append(" order by aa.time_modified");
			Query q = sess.createQuery(sql.toString());
			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				q.setFirstResult(startnum);
				q.setMaxResults(rowsperpage);
			}
			q.setString("id", id);
			return q.list();
		} catch (Exception e) {
			logger.error("unable to retrieve audit airport: " + e);
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
	public static Audit_Airport getAuditAirport(Airport airport, Agent mod_agent) throws Exception {
		Audit_Airport audit_airport = new Audit_Airport();
		BeanUtils.copyProperties(audit_airport, airport);
		audit_airport.setModifying_agent(mod_agent);
		audit_airport.setTime_modified(TracerDateTime.getGMTDate());
		return audit_airport;
	}

}