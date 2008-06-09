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
import com.bagnet.nettracer.tracing.db.Company_specific_irregularity_code;
import com.bagnet.nettracer.tracing.db.audit.Audit_Company_specific_irregularity_code;
import com.bagnet.nettracer.tracing.forms.audit.AuditLosscodeForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.IncidentUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;

/**
 * @author Administrator
 * 
 * create date - Jul 6, 2005
 */
public class AuditLosscodeUtils {

	private static Logger logger = Logger.getLogger(AuditLosscodeUtils.class);

	public static List getAuditsForComparison(String audit_id) {

		Session sess = null;

		try {
			sess = HibernateWrapper.getSession().openSession();
			StringBuffer sql = new StringBuffer(512);

			sql
					.append("select aa from com.bagnet.nettracer.tracing.db.audit.Audit_Company_specific_irregularity_code aa ");
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
			logger.error("unable to retrieve loss code: " + e);
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
	public static List findLosscodesByAuditSearchCriteria(AuditLosscodeForm form, Agent user,
			int rowsperpage, int currpage, boolean iscount) {

		Session sess = null;

		try {
			sess = HibernateWrapper.getSession().openSession();
			StringBuffer sql = new StringBuffer(512);

			if (iscount) sql
					.append("select count(distinct aa.code_id) from com.bagnet.nettracer.tracing.db.audit.Audit_Company_specific_irregularity_code aa ");
			else sql
					.append("select distinct aa from com.bagnet.nettracer.tracing.db.audit.Audit_Company_specific_irregularity_code aa ");

			sql.append(" where 1=1 and aa.companycode_ID = :companycode");

			if (form.getLoss_code() != null && !form.getLoss_code().equals("")) {
				sql.append(" and aa.loss_code like :loss_code ");
			}

			if (form.getReport_type() != null && !form.getReport_type().equals("0")) {
				sql.append(" and aa.report_type = :report_type ");
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
					.append(" and aa.audit_id in (select max(ax.audit_id) from com.bagnet.nettracer.tracing.db.audit.Audit_Company_specific_irregularity_code ax group by ax.code_id) ");
			if (!iscount) sql.append(" order by aa.code_id desc ");

			Query q = sess.createQuery(sql.toString());
			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				q.setFirstResult(startnum);
				q.setMaxResults(rowsperpage);
			}

			if (form.getLoss_code() != null && !form.getLoss_code().equals("")) {
				q.setString("loss_code", form.getLoss_code());
			}

			if (sdate != null && !sdate.equals("")) {
				q.setDate("sdate", sdate);
				q.setDate("edate", edate);
			}

			if (form.getReport_type() != null && !form.getReport_type().equals("0")) {
				q.setInteger("report_type", Integer.parseInt(form.getReport_type()));
			}

			if (form.getAgent().trim().length() > 0) {
				q.setString("agent", form.getAgent().trim());
			}

			q.setString("companycode", user.getStation().getCompany().getCompanyCode_ID());

			return q.list();
		} catch (Exception e) {
			logger.error("unable to retrieve loss code: " + e);
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
	public static List getAudits(String code_id, int rowsperpage, int currpage, boolean iscount) {

		Session sess = null;

		try {
			sess = HibernateWrapper.getSession().openSession();
			StringBuffer sql = new StringBuffer(512);
			if (iscount) sql
					.append("select count(aa.code_id) from com.bagnet.nettracer.tracing.db.audit.Audit_Company_specific_irregularity_code aa ");
			else sql
					.append("select distinct aa from com.bagnet.nettracer.tracing.db.audit.Audit_Company_specific_irregularity_code aa ");

			sql.append(" where 1=1 and aa.code_id like :code_id ");

			if (!iscount) sql.append(" order by aa.time_modified");
			Query q = sess.createQuery(sql.toString());
			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				q.setFirstResult(startnum);
				q.setMaxResults(rowsperpage);
			}
			q.setString("code_id", code_id);
			return q.list();
		} catch (Exception e) {
			logger.error("unable to retrieve audit loss code: " + e);
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
	public static Audit_Company_specific_irregularity_code getAuditLossCode(
			Company_specific_irregularity_code var, Agent mod_agent) throws Exception {
		Audit_Company_specific_irregularity_code audit_code = new Audit_Company_specific_irregularity_code();
		BeanUtils.copyProperties(audit_code, var);
		audit_code.setModifying_agent(mod_agent);
		audit_code.setCompanycode_ID(mod_agent.getStation().getCompany().getCompanyCode_ID());
		audit_code.setTime_modified(TracerDateTime.getGMTDate());
		return audit_code;
	}

}