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
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.audit.Audit_Station;
import com.bagnet.nettracer.tracing.forms.audit.AuditStationForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.IncidentUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;

/**
 * @author Administrator
 * 
 * create date - Jul 6, 2005
 */
public class AuditStationUtils {

	private static Logger logger = Logger.getLogger(AuditStationUtils.class);

	public static List getAuditsForComparison(String audit_station_id) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			StringBuffer sql = new StringBuffer(512);

			sql.append("select aa from com.bagnet.nettracer.tracing.db.audit.Audit_Station aa ");
			sql.append(" where 1=1 ");

			List audit_ohd_list = new ArrayList();

			StringTokenizer st = new StringTokenizer(audit_station_id, ",");

			int i = 0;
			while (st.hasMoreTokens()) {
				audit_ohd_list.add(st.nextToken());
				if (i < 1) {
					sql.append(" and audit_station_id = :audit_station_id" + i);
				} else {
					sql.append(" or audit_station_id = :audit_station_id" + i);
				}
				i++;
			}
			sql.append(" order by aa.audit_station_id ");

			Query q = sess.createQuery(sql.toString());

			for (int j = 0; j < i; j++) {
				q.setString("audit_station_id" + j, (String) audit_ohd_list.get(j));
			}
			return q.list();
		} catch (Exception e) {
			logger.error("unable to retrieve station: " + e);
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
	public static List findStationsByAuditSearchCriteria(AuditStationForm form, Agent user,
			int rowsperpage, int currpage, boolean iscount) {

		Session sess = null;

		try {
			sess = HibernateWrapper.getSession().openSession();
			StringBuffer sql = new StringBuffer(512);

			if (iscount) sql
					.append("select count(distinct aa.station_ID) from com.bagnet.nettracer.tracing.db.audit.Audit_Station aa ");
			else sql
					.append("select distinct aa from com.bagnet.nettracer.tracing.db.audit.Audit_Station aa ");

			sql.append(" where 1=1 and aa.companycode_ID = :companycode");

			if (form.getStationcode() != null && !form.getStationcode().equals("")) {
				sql.append(" and aa.stationcode like :stationcode ");
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
					.append(" and aa.audit_station_id in (select max(ax.audit_station_id) from com.bagnet.nettracer.tracing.db.audit.Audit_Station ax group by ax.stationcode) ");
			if (!iscount) sql.append(" order by aa.stationcode desc ");

			Query q = sess.createQuery(sql.toString());

			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				q.setFirstResult(startnum);
				q.setMaxResults(rowsperpage);
			}

			if (form.getStationcode() != null && !form.getStationcode().equals("")) {
				q.setString("stationcode", form.getStationcode());
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
			logger.error("unable to retrieve station: " + e);
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
	public static List getAudits(String station_id, int rowsperpage, int currpage, boolean iscount) {

		Session sess = null;

		try {
			sess = HibernateWrapper.getSession().openSession();
			StringBuffer sql = new StringBuffer(512);

			if (iscount) sql
					.append("select count(aa.station_ID) from com.bagnet.nettracer.tracing.db.audit.Audit_Station aa ");
			else sql
					.append("select distinct aa from com.bagnet.nettracer.tracing.db.audit.Audit_Station aa ");

			sql.append(" where 1=1 and aa.station_ID like :station_ID ");

			if (!iscount) sql.append(" order by aa.time_modified");
			Query q = sess.createQuery(sql.toString());
			if (rowsperpage > 0) {
				int startnum = currpage * rowsperpage;
				q.setFirstResult(startnum);
				q.setMaxResults(rowsperpage);
			}
			q.setString("station_ID", station_id);
			return q.list();
		} catch (Exception e) {
			logger.error("unable to retrieve audit station: " + e);
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
	public static Audit_Station getAuditStation(Station station, Agent mod_agent) throws Exception {
		Audit_Station audit_station = new Audit_Station();
		audit_station.setAddress1(station.getAddress1());
		audit_station.setAddress2(station.getAddress2());
		audit_station.setAirport_location(station.getAirport_location());
		audit_station.setAssociated_airport(station.getAssociated_airport());
		audit_station.setCity(station.getCity());
		audit_station.setCompanycode_ID(station.getCompany().getCompanyCode_ID());
		audit_station.setCountrycode_ID(station.getCountrycode_ID());
		audit_station.setOperation_hours(station.getOperation_hours());
		audit_station.setPhone(station.getPhone());
		audit_station.setState_ID(station.getState_ID());
		audit_station.setStation_ID(station.getStation_ID());
		audit_station.setStationcode(station.getStationcode());
		audit_station.setStationdesc(station.getStationdesc());
		audit_station.setZip(station.getZip());
		audit_station.setStation_region(station.getRegion()!=null?station.getRegion().getName():null);
		audit_station.setStation_region_mgr(station.getRegion()!=null?station.getRegion().getDirector():null);
		audit_station.setRegion_goal(station.getRegion()!=null?station.getRegion().getTarget():0.0);
		audit_station.setGoal(station.getGoal());
		audit_station.setTime_modified(TracerDateTime.getGMTDate());
		audit_station.setModifying_agent(mod_agent);
		audit_station.setActive(station.isActive());
		
		return audit_station;
	}

}