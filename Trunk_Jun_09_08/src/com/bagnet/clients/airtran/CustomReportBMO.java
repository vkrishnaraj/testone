/*
 * Created on Jul 15, 2004
 *
 * Administrator
 */
package com.bagnet.clients.airtran;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.util.MessageResources;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.reporting.ReportingConstants;
import com.bagnet.nettracer.tracing.bmo.ReportBMO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.dto.StatReportDTO;
import com.bagnet.nettracer.tracing.dto.StatReport_Custom_1_DTO;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;

/**
 * @author Administrator
 * 
 * create date - Jul 15, 2004
 */
public class CustomReportBMO implements
		com.bagnet.nettracer.integrations.reports.CustomReportBMO {
	private static Logger logger = Logger.getLogger(CustomReportBMO.class);
	private String rootpath;
	private Agent user;



	/*****************************************************************************
	 * custom report MBR report for airtran
	 * 
	 * this report displays count of incidents per loss codes compared to goals
	 * 
	 * @param srDTO
	 * @param reportnum
	 * @param reportname
	 * @param reporttitle
	 * @return
	 * @throws HibernateException
	 */
	public String create_custom_report_1(StatReportDTO srDTO, int reportnum,
			String reportname, String reporttitle, HttpServletRequest request) throws HibernateException {
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			Map parameters = new HashMap();
			parameters.put("title", reporttitle);

			TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(
					user.getDefaulttimezone()).getTimezone());

			String goal = DateUtils.formatDate(new Date(), "yyyy", null, null);
			parameters.put("goalyear", goal);
			parameters.put("companyname", user.getStation().getCompany()
					.getCompanydesc().toUpperCase());

			// mbr report type
			String mbrtypeq = "";
			if (srDTO.getItemType_ID() >= 1) {
				mbrtypeq = " and incident.itemtype.itemType_ID = :itemType_ID ";
			}

			String stationq = "";
			if (srDTO.getStation_ID() != null
					&& !srDTO.getStation_ID()[0].equals("0")) {
				stationq = " and station.station_ID in (:station_ID) ";
			}

			String statusq = "";
			if (srDTO.getStatus_ID() >= 1) {
				statusq = " and incident.status.status_ID = :status_ID ";
			}

			String companylimit = " and station.company.companyCode_ID = :companyCode_ID ";

			Date sdate = null, edate = null;
			Date sdate1 = null, edate1 = null; // add one for timezone
			Date stime = null; // time to compare (04:00 if eastern, for example)
			String dateq = "";

			ArrayList dateal = null;
			if ((dateal = ReportBMO.calculateDateDiff(srDTO, tz, user)) == null) {
				return null;
			}
			sdate = (Date) dateal.get(0);
			sdate1 = (Date) dateal.get(1);
			edate = (Date) dateal.get(2);
			edate1 = (Date) dateal.get(3);
			stime = (Date) dateal.get(4);

			parameters.put("sdate", "All Dates");
			if (sdate != null && edate != null) {
				parameters.put("sdate", srDTO.getStarttime());
				if (sdate.equals(edate)) {
					// need to add the timezone diff here
					dateq = " and ((incident.createdate= :startdate and incident.createtime >= :starttime) "
							+ " or (incident.createdate= :startdate1 and incident.createtime <= :starttime))";

					edate = null;
				} else {

					// first get the beginning and end dates using date and time, then get
					// dates in between
					dateq = " and ((incident.createdate= :startdate and incident.createtime >= :starttime) "
							+ " or (incident.createdate= :enddate1 and incident.createtime <= :starttime)"
							+ " or (incident.createdate > :startdate and incident.createdate <= :enddate))";

					parameters.put("edate", srDTO.getEndtime());
				}
			} else if (sdate != null) {
				parameters.put("sdate", srDTO.getStarttime());
				dateq = " and ((incident.createdate= :startdate and incident.createtime >= :starttime) "
						+ " or (incident.createdate= :startdate1 and incident.createtime <= :starttime))";
				edate = null;
			}

			String sql = "SELECT station.stationcode, station.station_region,station.station_region_mgr, station.goal, station.station_ID "
					+ " FROM  com.bagnet.nettracer.tracing.db.Station station "
					+ " where 1=1 "
					+ stationq
					+ companylimit
					+ " group by station.station_region,station.stationcode,station.station_region_mgr,station.goal, station.station_ID "
					+ "order by station.station_region,station.stationcode";

			Query q = sess.createQuery(sql);

			if (srDTO.getStation_ID() != null
					&& !srDTO.getStation_ID()[0].equals("0"))
				q.setParameterList("station_ID", srDTO.getStation_ID());

			q.setString("companyCode_ID", user.getStation().getCompany()
					.getCompanyCode_ID());
			List templist = q.list();
			List list = new ArrayList();
			if (templist.size() == 0) {
				logger.debug("no data for report");
				return null;
			}

			Object[] o = null;
			Object[] o2 = null;
			StatReport_Custom_1_DTO custom1 = null;

			int totalloss = 0;

			int incidents;
			NewSkiesIntegrationWrapper eiw = new NewSkiesIntegrationWrapper();
			ArrayList alparam = null;
			boolean b1 = eiw.readEnplaneProps("enplane.endpoint", "enplane.calltype");
			if (!b1) {
				logger.error(eiw.getErrormsg());
				return null;
			}

			for (int i = 0; i < templist.size(); i++) {
				o = (Object[]) templist.get(i);

				// add last loss code together first

				custom1 = new StatReport_Custom_1_DTO();
				custom1.setStationcode((String) o[0]);
				custom1.setStation_region((String) o[1]);
				custom1.setStation_region_mgr((String) o[2]);
				custom1.setGoal(((Double) o[3]).doubleValue());

				int curr_station_id = (((Integer) o[4]).intValue());

				// call airtran webservice
				alparam = new ArrayList();
				alparam.add(srDTO.getStarttime());

				if (srDTO.getEndtime() == null) {
					alparam.add(srDTO.getStarttime());
				} else {
					alparam.add(srDTO.getEndtime());
				}
				alparam.add(custom1.getStationcode());

				// webservice call to retrieve enplanement information
				// custom1.setBoarded(2.33);

				double callresult = eiw.getEnplanement(srDTO.getStarttime(), (srDTO
						.getEndtime() == null ? srDTO.getStarttime() : srDTO.getEndtime()),
						custom1.getStationcode());
				if (callresult < 0) {
					return null;

				} else {
					custom1.setBoarded(callresult / 1000);
				}

				// get count of incidents for each loss code for this station

				sql = "SELECT count(incident.loss_code), incident.loss_code "
						+ " FROM com.bagnet.nettracer.tracing.db.Incident incident"
						+ " where loss_code <> 0 and incident.faultstation.station_ID  = :station_ID "
						+ dateq + mbrtypeq + statusq + " group by loss_code ";

				q = sess.createQuery(sql);

				q.setInteger("station_ID", curr_station_id);

				if (srDTO.getStatus_ID() >= 1)
					q.setInteger("status_ID", srDTO.getStatus_ID());
				if (srDTO.getItemType_ID() >= 1)
					q.setInteger("itemType_ID", srDTO.getItemType_ID());

				if (sdate != null) {
					q.setDate("startdate", sdate);
					if (edate == null)
						q.setDate("startdate1", sdate1);
					q.setTime("starttime", stime);
				}
				if (edate != null) {
					q.setDate("enddate1", edate1);
					q.setDate("enddate", edate);
				}

				List inc_list = q.list();

				for (int j = 0; j < inc_list.size(); j++) {
					o2 = (Object[]) inc_list.get(j);

					incidents = ((Long) o2[0]).intValue();

					if (((Integer) o2[1]).intValue() == 10)
						custom1.setLoss10(incidents);
					else if (((Integer) o2[1]).intValue() == 12)
						custom1.setLoss12(incidents);
					else if (((Integer) o2[1]).intValue() == 15)
						custom1.setLoss15(incidents);
					else if (((Integer) o2[1]).intValue() == 18)
						custom1.setLoss18(incidents);
					else if (((Integer) o2[1]).intValue() == 23)
						custom1.setLoss23(incidents);
					else if (((Integer) o2[1]).intValue() == 24)
						custom1.setLoss24(incidents);
					else if (((Integer) o2[1]).intValue() == 25)
						custom1.setLoss25(incidents);
					else if (((Integer) o2[1]).intValue() == 26)
						custom1.setLoss26(incidents);
					else if (((Integer) o2[1]).intValue() == 30)
						custom1.setLoss30(incidents);
					else if (((Integer) o2[1]).intValue() == 32)
						custom1.setLoss32(incidents);
					else if (((Integer) o2[1]).intValue() == 33)
						custom1.setLoss33(incidents);
					else if (((Integer) o2[1]).intValue() == 35)
						custom1.setLoss35(incidents);
					else if (((Integer) o2[1]).intValue() == 42)
						custom1.setLoss42(incidents);
					else if (((Integer) o2[1]).intValue() == 51)
						custom1.setLoss51(incidents);
					else if (((Integer) o2[1]).intValue() == 54)
						custom1.setLoss54(incidents);
					else if (((Integer) o2[1]).intValue() == 56)
						custom1.setLoss56(incidents);
					else if (((Integer) o2[1]).intValue() == 63)
						custom1.setLoss63(incidents);
					else if (((Integer) o2[1]).intValue() == 64)
						custom1.setLoss64(incidents);
					else if (((Integer) o2[1]).intValue() == 73)
						custom1.setLoss73(incidents);
					else if (((Integer) o2[1]).intValue() == 74)
						custom1.setLoss74(incidents);
					else if (((Integer) o2[1]).intValue() == 76)
						custom1.setLoss76(incidents);
					else if (((Integer) o2[1]).intValue() == 78)
						custom1.setLoss78(incidents);
					else if (((Integer) o2[1]).intValue() == 80)
						custom1.setLoss80(incidents);
					else if (((Integer) o2[1]).intValue() == 81)
						custom1.setLoss81(incidents);
					else if (((Integer) o2[1]).intValue() == 82)
						custom1.setLoss82(incidents);
					else if (((Integer) o2[1]).intValue() == 90)
						custom1.setLoss90(incidents);
				}

				if (custom1.getStationcode().equalsIgnoreCase("ATL")) {
					list.add(0, custom1);
					custom1.setStation_region("");
					custom1.setStation_region_mgr("");
				} else
					list.add(custom1);

			}

			return ReportBMO.getReportFile(list, parameters, reportname, rootpath, srDTO
					.getOutputtype(), request);
		} catch (Exception e) {
			logger.error("unable to create report " + e);
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
		}
	}


	public String createCustomReport(StatReportDTO srDTO,
			HttpServletRequest request, Agent user, String rootpath) {
		MessageResources messages = MessageResources
				.getMessageResources("com.bagnet.nettracer.tracing.resources.ApplicationResources");
		this.rootpath = rootpath;
		this.user = user;

		switch (srDTO.getCustomreportnum()) {
		case ReportingConstants.RPT_20_CUSTOM_1:
			String creportdata = create_custom_report_1(srDTO,
					ReportingConstants.RPT_20_CUSTOM_1,
					ReportingConstants.RPT_20_CUSTOM_1_NAME, messages.getMessage(
							new Locale(user.getCurrentlocale()), "header.reportnum.20"), request);
			if (creportdata == null) {
				return null;
			} else {
				return creportdata;
			}
		}
		return null;
	}

}