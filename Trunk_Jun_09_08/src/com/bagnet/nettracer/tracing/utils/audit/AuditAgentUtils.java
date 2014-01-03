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
import com.bagnet.nettracer.tracing.db.audit.Audit_Agent;
import com.bagnet.nettracer.tracing.forms.audit.AuditAgentForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.IncidentUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;

/**
 * @author Administrator
 * 
 * create date - Jul 6, 2005
 */
public class AuditAgentUtils {

	private static Logger logger = Logger.getLogger(AuditAgentUtils.class);

	public static List getAuditsForComparison(String audit_agent_id) {

		Session sess = null;

		try {
			sess = HibernateWrapper.getSession().openSession();
			StringBuffer sql = new StringBuffer(512);

			sql.append("select aa from com.bagnet.nettracer.tracing.db.audit.Audit_Agent aa ");
			sql.append(" where 1=1 ");

			List audit_ohd_list = new ArrayList();

			StringTokenizer st = new StringTokenizer(audit_agent_id, ",");

			int i = 0;
			while (st.hasMoreTokens()) {
				audit_ohd_list.add(st.nextToken());
				if (i < 1) {
					sql.append(" and audit_agent_id = :audit_agent_id" + i);
				} else {
					sql.append(" or audit_agent_id = :audit_agent_id" + i);
				}
				i++;
			}
			sql.append(" order by aa.audit_agent_id ");

			Query q = sess.createQuery(sql.toString());

			for (int j = 0; j < i; j++) {
				q.setString("audit_agent_id" + j, (String) audit_ohd_list.get(j));
			}
			return q.list();
		} catch (Exception e) {
			logger.error("unable to retrieve agent: " + e);
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
	public static List findAgentsByAuditSearchCriteria(AuditAgentForm form, Agent user,
			int rowsperpage, int currpage, boolean iscount) {

		Session sess = null;

		try {
			sess = HibernateWrapper.getSession().openSession();
			StringBuffer sql = new StringBuffer(512);

			if (iscount) sql
					.append("select count(distinct aa.agent_ID) from com.bagnet.nettracer.tracing.db.audit.Audit_Agent aa ");
			else sql
					.append("select distinct aa from com.bagnet.nettracer.tracing.db.audit.Audit_Agent aa ");

			sql.append(" where 1=1 and aa.station.company.companyCode_ID = :companycode");

			//SELECT * FROM `audit_companycode` group by companycode_ID order by
			// audit_id desc

			if (form.getUsername() != null && !form.getUsername().equals("")) {
				sql.append(" and aa.username like :username ");
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
					.append(" and aa.audit_agent_id in (select max(ax.audit_agent_id) from com.bagnet.nettracer.tracing.db.audit.Audit_Agent ax group by ax.agent_ID) ");
			if (!iscount) sql.append(" order by aa.agent_ID desc ");

			Query q = sess.createQuery(sql.toString());
			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				q.setFirstResult(startnum);
				q.setMaxResults(rowsperpage);
			}

			if (form.getUsername() != null && !form.getUsername().equals("")) {
				q.setString("username", form.getUsername());
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
			logger.error("unable to retrieve agent: " + e);
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
	public static List getAudits(String agent_id, int rowsperpage, int currpage, boolean iscount) {

		Session sess = null;

		try {
			sess = HibernateWrapper.getSession().openSession();
			StringBuffer sql = new StringBuffer(512);
			if (iscount) sql
					.append("select count(aa.agent_ID) from com.bagnet.nettracer.tracing.db.audit.Audit_Agent aa ");
			else sql
					.append("select distinct aa from com.bagnet.nettracer.tracing.db.audit.Audit_Agent aa ");

			sql.append(" where 1=1 and aa.agent_ID like :agent_ID ");
			if (!iscount) sql.append(" order by aa.time_modified");
			Query q = sess.createQuery(sql.toString());
			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				q.setFirstResult(startnum);
				q.setMaxResults(rowsperpage);
			}

			q.setString("agent_ID", agent_id);
			return q.list();
		} catch (Exception e) {
			logger.error("unable to retrieve audit agent: " + e);
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
	public static Audit_Agent getAuditAgent(Agent agent, Agent mod_agent) throws Exception {
		Audit_Agent audit_agent = new Audit_Agent();

		audit_agent.setActive(agent.isActive());
		audit_agent.setAgent_ID(agent.getAgent_ID());
		audit_agent.setCompanycode_ID(agent.getCompanycode_ID());
		audit_agent.setCurrentlocale(agent.getCurrentlocale());
		audit_agent.setCurrenttimezone(agent.getCurrenttimezone());
		audit_agent.setDateformat(agent.getDateformat());
		audit_agent.setDefaultcurrency(agent.getDefaultcurrency());
		audit_agent.setDefaultlocale(agent.getDefaultlocale());
		audit_agent.setDefaulttimezone(agent.getDefaulttimezone());
		audit_agent.setFirstname(agent.getFirstname());
		audit_agent.setGroup(agent.getGroup());
		audit_agent.setIs_online(agent.getIs_online());
		audit_agent.setLast_logged_on(agent.getLast_logged_on());
		audit_agent.setLastname(agent.getLastname());
		audit_agent.setMname(agent.getMname());
		audit_agent.setModifying_agent(mod_agent);
		audit_agent.setPassword(agent.getPassword());
		audit_agent.setReset_password(agent.isReset_password());
		audit_agent.setShift(agent.getShift());
		audit_agent.setStation(agent.getStation());
		audit_agent.setTime_modified(TracerDateTime.getGMTDate());
		audit_agent.setTimeformat(agent.getTimeformat());
		audit_agent.setTimeout(agent.getTimeout());
		audit_agent.setUsername(agent.getUsername());
		audit_agent.setAccount_locked(agent.isAccount_locked());
		audit_agent.setWeb_enabled(agent.isWs_enabled());
		audit_agent.setWs_enabled(agent.isWs_enabled());
		audit_agent.setMax_ws_sessions(agent.getMax_ws_sessions());
		audit_agent.setInboundQueue(agent.isInboundQueue());
		

		return audit_agent;
	}

}