/*
 * Created on Jul 6, 2005
 *
 * Administrator
 */
package com.bagnet.nettracer.tracing.utils.audit;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.hibernate.Query;
import org.hibernate.Session;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Company;
import com.bagnet.nettracer.tracing.db.audit.Audit_Company;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;

/**
 * @author Administrator
 * 
 * create date - Jul 6, 2005
 */
public class AuditCompanyUtils {

	private static Logger logger = Logger.getLogger(AuditCompanyUtils.class);

	public static List getAuditsForComparison(String audit_id) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			StringBuffer sql = new StringBuffer(512);

			sql.append("select aa from com.bagnet.nettracer.tracing.db.audit.Audit_Company aa ");
			sql.append(" where 1=1 ");

			List audit_ohd_list = new ArrayList();

			StringTokenizer st = new StringTokenizer(audit_id, ",");

			int i = 0;
			while (st.hasMoreTokens()) {
				audit_ohd_list.add(st.nextToken());
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
				q.setString("audit_id" + j, (String) audit_ohd_list.get(j));
			}
			return q.list();
		} catch (Exception e) {
			logger.error("unable to retrieve company: " + e);
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
	public static List getAudits(String companyCode_ID, int rowsperpage, int currpage, boolean iscount) {

		Session sess = null;

		try {
			sess = HibernateWrapper.getSession().openSession();
			StringBuffer sql = new StringBuffer(512);

			if (iscount) sql
					.append("select count(ac.companyCode_ID) from com.bagnet.nettracer.tracing.db.audit.Audit_Company ac ");
			else sql
					.append("select distinct ac from com.bagnet.nettracer.tracing.db.audit.Audit_Company ac ");

			sql.append(" where 1=1 and ac.companyCode_ID like :companyCode_ID ");

			if (!iscount) sql.append(" order by ac.time_modified");
			Query q = sess.createQuery(sql.toString());
			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				q.setFirstResult(startnum);
				q.setMaxResults(rowsperpage);
			}

			q.setString("companyCode_ID", companyCode_ID);
			return q.list();
		} catch (Exception e) {
			logger.error("unable to retrieve audit company: " + e);
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
	public static Audit_Company getAuditCompany(Company cmp, Agent mod_agent) throws Exception {
		Audit_Company audit_cmp = new Audit_Company();
		audit_cmp.setCompanyCode_ID(cmp.getCompanyCode_ID());
		audit_cmp.setCompanydesc(cmp.getCompanydesc());
		audit_cmp.setAddress1(cmp.getAddress1());
		audit_cmp.setAddress2(cmp.getAddress2());
		audit_cmp.setCity(cmp.getCity());
		audit_cmp.setState_ID(cmp.getState_ID());
		audit_cmp.setCountrycode_ID(cmp.getCountrycode_ID());
		audit_cmp.setZip(cmp.getZip());
		audit_cmp.setPhone(cmp.getPhone());
		audit_cmp.setEmail_address(cmp.getEmail_address());
		audit_cmp.setTime_modified(TracerDateTime.getGMTDate());
		audit_cmp.setModifying_agent(mod_agent);

		return audit_cmp;
	}

}