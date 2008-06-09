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
import com.bagnet.nettracer.tracing.db.Work_Shift;
import com.bagnet.nettracer.tracing.db.audit.Audit_Work_Shift;
import com.bagnet.nettracer.tracing.forms.audit.AuditWorkShiftForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.IncidentUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;

/**
 * @author Administrator
 * 
 * create date - Jul 6, 2005
 */
public class AuditWorkshiftUtils {

	private static Logger logger = Logger.getLogger(AuditWorkshiftUtils.class);

	public static List getAuditsForComparison(String audit_shift_id) {

		Session sess = null;

		try {
			sess = HibernateWrapper.getSession().openSession();
			StringBuffer sql = new StringBuffer(512);

			sql.append("select aw from com.bagnet.nettracer.tracing.db.audit.Audit_Work_Shift aw ");
			sql.append(" where 1=1 ");

			List audit_shift_list = new ArrayList();

			StringTokenizer st = new StringTokenizer(audit_shift_id, ",");

			int i = 0;
			while (st.hasMoreTokens()) {
				audit_shift_list.add(st.nextToken());
				if (i < 1) {
					sql.append(" and audit_shift_id = :audit_shift_id" + i);
				} else {
					sql.append(" or audit_shift_id = :audit_shift_id" + i);
				}
				i++;
			}
			sql.append(" order by aw.audit_shift_id ");

			Query q = sess.createQuery(sql.toString());

			for (int j = 0; j < i; j++) {
				q.setString("audit_shift_id" + j, (String) audit_shift_list.get(j));
			}
			return q.list();
		} catch (Exception e) {
			logger.error("unable to retrieve shift: " + e);
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
	public static List findShiftsByAuditSearchCriteria(AuditWorkShiftForm form, Agent user,
			int rowsperpage, int currpage, boolean iscount) {

		Session sess = null;

		try {
			sess = HibernateWrapper.getSession().openSession();
			StringBuffer sql = new StringBuffer(512);

			if (iscount) sql
					.append("select count(distinct aw.shift_id) from com.bagnet.nettracer.tracing.db.audit.Audit_Work_Shift aw ");
			else sql
					.append("select distinct aw from com.bagnet.nettracer.tracing.db.audit.Audit_Work_Shift aw ");

			sql.append(" where 1=1 and aw.company.companyCode_ID = :companycode");

			if (form.getShiftcode() != null && !form.getShiftcode().equals("")) {
				sql.append(" and aw.shift_code like :shiftcode ");
			}

			if (form.getAgent().length() > 0) sql.append(" and aw.modifying_agent.username like :agent ");

			Date sdate = null, edate = null;
			TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone());
			ArrayList dateal = null;
			if ((dateal = IncidentUtils.calculateDateTimeDiff(form.getS_createtime(),form.getE_createtime(),tz,user)) == null) {
				return null;
			} 
			sdate = (Date)dateal.get(0);edate = (Date)dateal.get(1);
			
			if (sdate != null && !sdate.equals("")) {
				sql.append(" and aw.time_modified between :sdate and :edate ");
			}

			sql
					.append(" and aw.audit_shift_id in (select max(ax.audit_shift_id) from com.bagnet.nettracer.tracing.db.audit.Audit_Work_Shift ax group by ax.shift_id) ");
			if (!iscount) sql.append(" order by aw.shift_id desc ");

			Query q = sess.createQuery(sql.toString());
			if (form.getShiftcode() != null && !form.getShiftcode().equals("")) {
				q.setString("shift_code", form.getShiftcode());
			}
			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				q.setFirstResult(startnum);
				q.setMaxResults(rowsperpage);
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
			logger.error("unable to retrieve shift: " + e);
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
	public static List getAudits(String shift_id, int rowsperpage, int currpage, boolean iscount) {

		Session sess = null;

		try {
			sess = HibernateWrapper.getSession().openSession();
			StringBuffer sql = new StringBuffer(512);
			if (iscount) sql
					.append("select count(aw.shift_id) from com.bagnet.nettracer.tracing.db.audit.Audit_Work_Shift aw ");
			else sql
					.append("select distinct aw from com.bagnet.nettracer.tracing.db.audit.Audit_Work_Shift aw ");

			sql.append(" where 1=1 and aw.shift_id like :shift_id ");

			if (!iscount) sql.append(" order by aw.time_modified");
			Query q = sess.createQuery(sql.toString());
			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				q.setFirstResult(startnum);
				q.setMaxResults(rowsperpage);
			}

			q.setString("shift_id", shift_id);
			return q.list();
		} catch (Exception e) {
			logger.error("unable to retrieve audit shift: " + e);
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
	public static Audit_Work_Shift getAuditShift(Work_Shift shift, Agent mod_agent) throws Exception {
		Audit_Work_Shift audit_shift = new Audit_Work_Shift();
		BeanUtils.copyProperties(audit_shift, shift);
		audit_shift.setModifying_agent(mod_agent);
		audit_shift.setTime_modified(TracerDateTime.getGMTDate());
		return audit_shift;
	}

}