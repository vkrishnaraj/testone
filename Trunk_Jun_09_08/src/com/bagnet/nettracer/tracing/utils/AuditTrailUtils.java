/*
 * Created on Oct 28, 2004
 *
 * Administrator
 */
package com.bagnet.nettracer.tracing.utils;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.db.Agent;

/**
 * @author Administrator
 * 
 * create date - Oct 28, 2004
 */
public class AuditTrailUtils {

	private static Logger logger = Logger.getLogger(AuditTrailUtils.class);

	// constant vars
	public final static int AUD_INCIDENT = 1;

	public final static int AUD_OHD = 2;

	public final static int AUD_CLAIM = 3;

	public final static int AUD_PAYOUT = 4;

	public static List getAuditClaim(String incident_id, int rowsperpage, int currpage,
			boolean isCount, Agent user) throws HibernateException {
		Session sess = HibernateWrapper.getSession().openSession();
		Query q = null;
		try {
			StringBuffer s = new StringBuffer(512);

			if (isCount) s
					.append("select count(audit_claim.audit_Claim_ID) from com.bagnet.nettracer.tracing.db.Audit_Claim audit_claim ");
			else s
					.append("select audit_claim from com.bagnet.nettracer.tracing.db.Audit_Claim audit_claim ");

			s
					.append(" where audit_claim.claim.incident.stationassigned.company.companyCode_ID = :companyCode_ID "
							+ " and audit_claim.claim.incident.incident_ID = :incident_ID ");

			/*
			 * Date sdate = null, edate = null; if (siDTO.getS_createtime().length() >
			 * 0) sdate = DateUtils.convertToDate(siDTO.getS_createtime(),
			 * user.getDateformat().getFormat(), null); if
			 * (siDTO.getE_createtime().length() > 0) edate =
			 * DateUtils.convertToDate(siDTO.getE_createtime(),
			 * user.getDateformat().getFormat(), null);
			 * 
			 * if (sdate != null) { if (edate != null && sdate != edate) s.append("
			 * and incident.createdate between :sdate and :edate"); else s.append("
			 * and incident.createdate = :sdate"); }
			 */

			if (!isCount) s.append(" order by audit_claim.changetime ");

			q = sess.createQuery(s.toString());
			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				q.setFirstResult(startnum);
				q.setMaxResults(rowsperpage);
			}

			q.setString("incident_ID", incident_id);
			q.setString("companyCode_ID", user.getStation().getCompany().getCompanyCode_ID());

			/*
			 * if (sdate != null) { q.setDate("sdate", sdate); if (edate != null &&
			 * sdate != edate) q.setDate("edate", edate); }
			 */

			List list = q.list();

			// return count in a new list as element 0
			if (isCount) {
				List templist = new ArrayList();
				if (list.size() > 0) {
					templist.add((Integer) list.get(0));
				} else {
					templist.add(new Integer(0));
				}
				return templist;
			}

			/*
			 * Audit_Claim ac = null; for (int i = 0; i < list.size(); i++) { ac =
			 * (Audit_Claim) list.get(i);
			 * ac.set_DATEFORMAT(user.getDateformat().getFormat());
			 * ac.set_TIMEFORMAT(user.getTimeformat().getFormat());
			 * ac.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone())); }
			 */
			return list;
		} catch (Exception e) {
			logger.error("unable to retrieve audit trail for claim: " + e);
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
		}
	}

	/**
	 * EXPENSE PAYOUT
	 * 
	 * @param incident_id
	 * @param rowsperpage
	 * @param currpage
	 * @param isCount
	 * @return @throws
	 *         HibernateException
	 */
	public static List getAuditPayout(String incident_id, int rowsperpage, int currpage,
			boolean isCount, Agent user) throws HibernateException {
		Session sess = HibernateWrapper.getSession().openSession();
		Query q = null;
		try {
			StringBuffer s = new StringBuffer(512);

			if (isCount) s
					.append("select count(audit_exp.audit_Expensepayout_ID) from com.bagnet.nettracer.tracing.db.Audit_ExpensePayout audit_exp ");
			else s
					.append("select audit_exp from com.bagnet.nettracer.tracing.db.Audit_ExpensePayout audit_exp ");

			s
					.append(" where audit_exp.expensepayout.claim.incident.stationassigned.company.companyCode_ID = :companyCode_ID "
							+ " and audit_exp.expensepayout.claim.incident.incident_ID = :incident_ID ");

			/*
			 * Date sdate = null, edate = null; if (siDTO.getS_createtime().length() >
			 * 0) sdate = DateUtils.convertToDate(siDTO.getS_createtime(),
			 * user.getDateformat().getFormat(), null); if
			 * (siDTO.getE_createtime().length() > 0) edate =
			 * DateUtils.convertToDate(siDTO.getE_createtime(),
			 * user.getDateformat().getFormat(), null);
			 * 
			 * if (sdate != null) { if (edate != null && sdate != edate) s.append("
			 * and incident.createdate between :sdate and :edate"); else s.append("
			 * and incident.createdate = :sdate"); }
			 */
			if (!isCount) s.append(" order by audit_exp.changetime ");

			q = sess.createQuery(s.toString());
			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				q.setFirstResult(startnum);
				q.setMaxResults(rowsperpage);
			}

			q.setString("incident_ID", incident_id);

			q.setString("companyCode_ID", user.getStation().getCompany().getCompanyCode_ID());

			/*
			 * if (sdate != null) { q.setDate("sdate", sdate); if (edate != null &&
			 * sdate != edate) q.setDate("edate", edate); }
			 */

			List list = q.list();

			// return count in a new list as element 0
			if (isCount) {
				List templist = new ArrayList();
				if (list.size() > 0) {
					templist.add((Integer) list.get(0));
				} else {
					templist.add(new Integer(0));
				}
				return templist;
			}

			/*
			 * Audit_ExpensePayout ac = null; for (int i = 0; i < list.size(); i++) {
			 * ac = (Audit_ExpensePayout) list.get(i);
			 * ac.set_DATEFORMAT(user.getDateformat().getFormat());
			 * ac.set_TIMEFORMAT(user.getTimeformat().getFormat());
			 * ac.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone())); }
			 */
			return list;
		} catch (Exception e) {
			logger.error("unable to retrieve audit trail for payout: " + e);
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
		}
	}
}