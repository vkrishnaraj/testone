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
import com.bagnet.nettracer.tracing.db.UserGroup;
import com.bagnet.nettracer.tracing.db.audit.Audit_UserGroup;
import com.bagnet.nettracer.tracing.forms.audit.AuditGroupForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.IncidentUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;

/**
 * @author Administrator
 * 
 * create date - Jul 6, 2005
 */
public class AuditGroupUtils {

	private static Logger logger = Logger.getLogger(AuditGroupUtils.class);

	public static List getAuditsForComparison(String audit_id) {

		Session sess = null;

		try {
			sess = HibernateWrapper.getSession().openSession();
			StringBuffer sql = new StringBuffer(512);

			sql.append("select aug from com.bagnet.nettracer.tracing.db.audit.Audit_UserGroup aug ");
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
			sql.append(" order by aug.audit_id ");

			Query q = sess.createQuery(sql.toString());

			for (int j = 0; j < i; j++) {
				q.setString("audit_id" + j, (String) audit_airport_list.get(j));
			}
			return q.list();
		} catch (Exception e) {
			logger.error("unable to retrieve group: " + e);
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
	public static List findGroupsByAuditSearchCriteria(AuditGroupForm form, Agent user,
			int rowsperpage, int currpage, boolean iscount) {

		Session sess = null;

		try {
			sess = HibernateWrapper.getSession().openSession();
			StringBuffer sql = new StringBuffer(512);

			if (iscount) sql
					.append("select count(distinct aug.userGroup_ID) from com.bagnet.nettracer.tracing.db.audit.Audit_UserGroup aug ");
			else sql
					.append("select distinct aug from com.bagnet.nettracer.tracing.db.audit.Audit_UserGroup aug ");

			sql.append(" where 1=1 and aug.companycode_ID = :companycode");

			if (form.getGroupName() != null && !form.getGroupName().equals("")) {
				sql.append(" and aug.description like :description ");
			}

			if (form.getAgent().length() > 0) sql
					.append(" and aug.modifying_agent.username like :agent ");

			Date sdate = null, edate = null;
			TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone());
			ArrayList dateal = null;
			if ((dateal = IncidentUtils.calculateDateTimeDiff(form.getS_createtime(),form.getE_createtime(),tz,user)) == null) {
				return null;
			} 
			sdate = (Date)dateal.get(0);edate = (Date)dateal.get(1);
			if (sdate != null && !sdate.equals("")) {
				sql.append(" and aug.time_modified between :sdate and :edate ");
			}

			sql
					.append(" and aug.audit_id in (select max(ax.audit_id) from com.bagnet.nettracer.tracing.db.audit.Audit_UserGroup ax group by ax.userGroup_ID) ");
			if (!iscount) sql.append(" order by aug.userGroup_ID desc ");

			Query q = sess.createQuery(sql.toString());
			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				q.setFirstResult(startnum);
				q.setMaxResults(rowsperpage);
			}

			if (form.getGroupName() != null && !form.getGroupName().equals("")) {
				q.setString("description", form.getGroupName());
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
			logger.error("unable to retrieve group: " + e);
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
	public static List getAudits(String userGroup_ID, int rowsperpage, int currpage, boolean iscount) {

		Session sess = null;

		try {
			sess = HibernateWrapper.getSession().openSession();
			StringBuffer sql = new StringBuffer(512);
			if (iscount) sql
					.append("select count(aa.audit_id) from com.bagnet.nettracer.tracing.db.audit.Audit_UserGroup aa ");
			else sql
					.append("select distinct aa from com.bagnet.nettracer.tracing.db.audit.Audit_UserGroup aa ");

			sql.append(" where 1=1 and aa.userGroup_ID like :userGroup_ID ");

			if (!iscount) sql.append(" order by aa.time_modified");
			Query q = sess.createQuery(sql.toString());
			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				q.setFirstResult(startnum);
				q.setMaxResults(rowsperpage);
			}
			q.setString("userGroup_ID", userGroup_ID);
			return q.list();
		} catch (Exception e) {
			logger.error("unable to retrieve audit group: " + e);
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
	public static Audit_UserGroup getAuditGroup(UserGroup grp, Agent mod_agent) throws Exception {
		Audit_UserGroup audit_grp = new Audit_UserGroup();
		BeanUtils.copyProperties(audit_grp, grp);
		audit_grp.setBsolimit(grp.getBsoLimit());
		audit_grp.setLuvlimit(grp.getLuvLimit());
		audit_grp.setModifying_agent(mod_agent);
		audit_grp.setTime_modified(TracerDateTime.getGMTDate());
		audit_grp.setComponentPolicies(null);
		return audit_grp;
	}

}