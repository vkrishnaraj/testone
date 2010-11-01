/*
 * Created on Jul 15, 2004
 *
 * Administrator
 */
package com.bagnet.clients.airtran;

import java.util.ArrayList;
import java.util.Arrays;
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

			//String companylimit = " and station.company.companyCode_ID = :companyCode_ID ";
			StringBuffer sbCompanyQuery = new StringBuffer("");
			String[] myCompanyList = srDTO.getCompany_ID();
			
			boolean reset2AllStations = true;
			if (myCompanyList != null) {
				sbCompanyQuery.append(" AND station.company.companyCode_ID IN ");
				
				List<String> myCompanyIdList = Arrays.asList(myCompanyList);
				
				//TODO: when companyList contains only this airline, stationq stays unchanged
				//boolean reset2AllStations = true;
				int numOfCompanyInList = myCompanyIdList.size();
				if (numOfCompanyInList == 1) {
					String myCompanyId = myCompanyIdList.get(0);
					if (myCompanyId.equalsIgnoreCase(user.getCompanycode_ID())) {
						reset2AllStations = false;
					}
				} 
				
				if (reset2AllStations) {
					stationq = "";
				}
				
				String myCompanySql = "(";
				
				for (String item : myCompanyIdList) {
					myCompanySql += "'" + item + "',";

				}
				//replace last character with )
				int myLastIndex = myCompanySql.length() - 2;
				if (myLastIndex > 0) {
					myCompanySql = myCompanySql.substring(0, myLastIndex);
					myCompanySql += "')";
				}
				sbCompanyQuery.append(myCompanySql);
			}
			//logger.error(">>>><<<< entire company query:" + sbCompanyQuery.toString());
			
			

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

			String sql = "SELECT station.stationcode, station.station_region,station.station_region_mgr, station.goal, station.station_ID, station.company.companyCode_ID "
					+ " FROM com.bagnet.nettracer.tracing.db.Station station "
					+ " where station.active = :active "
					+ stationq
					//+ companylimit
					+ sbCompanyQuery.toString()
					+ " group by station.company.companyCode_ID,station.station_region,station.stationcode,station.station_region_mgr,station.goal, station.station_ID "
					+ "order by station.company.companyCode_ID,station.station_region,station.stationcode";

			logger.error("( test station query to verify ):" + sql);
			
			Query q = sess.createQuery(sql);

/*			if (srDTO.getStation_ID() != null
					&& !srDTO.getStation_ID()[0].equals("0"))
				q.setParameterList("station_ID", srDTO.getStation_ID());*/
			
			if (!reset2AllStations) {
				if (srDTO.getStation_ID() != null
						&& !srDTO.getStation_ID()[0].equals("0")) {
						ArrayList<Integer> stationList = new ArrayList<Integer>();
						for (String s: srDTO.getStation_ID()) {
							stationList.add(Integer.parseInt(s));
						}
							
						q.setParameterList("station_ID", stationList);
				}
			}

			q.setBoolean("active", true);
			//q.setString("companyCode_ID", user.getStation().getCompany()
			//		.getCompanyCode_ID());
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
				custom1.setCompanycode((String) o[5]);

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

				//TODO: temporarily setting it without WS
				// webservice call to retrieve enplanement information
//				custom1.setBoarded(0.00);
//				custom1.setBoarded(2.10);

  				if (custom1.getCompanycode().equalsIgnoreCase("FL")) {
	 				double callresult = eiw.getEnplanement(srDTO.getStarttime(), (srDTO
							.getEndtime() == null ? srDTO.getStarttime() : srDTO.getEndtime()),
							custom1.getStationcode());
					
					if (callresult < 0) {
						return null;
	
					} else {
						custom1.setBoarded(callresult / 1000);
					}
				} else {
					custom1.setBoarded(0.00);
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

/*				if (custom1.getStationcode().equalsIgnoreCase("ATL")) {
					list.add(0, custom1);
					custom1.setStation_region("");
					custom1.setStation_region_mgr("");
				} else
					list.add(custom1);*/
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
	
	/*****************************************************************************
	 * custom report MBR report for airtran - using JasperDesign
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
/*	public String create_custom_report_by_jd(StatReportDTO srDTO, int reportnum,
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
					+ " where station.active = :active "
					+ stationq
					+ companylimit
					+ " group by station.station_region,station.stationcode,station.station_region_mgr,station.goal, station.station_ID "
					+ "order by station.station_region,station.stationcode";

			Query q = sess.createQuery(sql);

			if (srDTO.getStation_ID() != null
					&& !srDTO.getStation_ID()[0].equals("0"))
				q.setParameterList("station_ID", srDTO.getStation_ID());

			q.setBoolean("active", true);
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
			StatReport_Custom_Dynamic_DTO custom1 = null;

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

				custom1 = new StatReport_Custom_Dynamic_DTO();
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

				//TODO: temporarily setting it without WS
				// webservice call to retrieve enplanement information
				custom1.setBoarded(2.33);

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

					int myCodeIndex = ((Integer) o2[1]).intValue();
					
					String errComment;  
					switch (myCodeIndex) {
						case 1: custom1.setLoss01(incidents); break;
						case 2: custom1.setLoss02(incidents); break;
						case 3: custom1.setLoss03(incidents); break;
						case 4: custom1.setLoss04(incidents); break;
						case 5: custom1.setLoss05(incidents); break;	
						case 6: custom1.setLoss06(incidents); break;		
						case 7: custom1.setLoss07(incidents); break;
						case 8: custom1.setLoss08(incidents); break;
						case 9: custom1.setLoss09(incidents); break;
						case 10: custom1.setLoss10(incidents); break;

						case 11: custom1.setLoss11(incidents); break;
						case 12: custom1.setLoss12(incidents); break;
						case 13: custom1.setLoss13(incidents); break;
						case 14: custom1.setLoss14(incidents); break;
						case 15: custom1.setLoss15(incidents); break;
						case 16: custom1.setLoss16(incidents); break;
						case 17: custom1.setLoss17(incidents); break;
						case 18: custom1.setLoss18(incidents); break;
						case 19: custom1.setLoss19(incidents); break;
						case 20: custom1.setLoss20(incidents); break;

						case 21: custom1.setLoss21(incidents); break;
						case 22: custom1.setLoss22(incidents); break;
						case 23: custom1.setLoss23(incidents); break;
						case 24: custom1.setLoss24(incidents); break;
						case 25: custom1.setLoss25(incidents); break;
						case 26: custom1.setLoss26(incidents); break;
						case 27: custom1.setLoss27(incidents); break;
						case 28: custom1.setLoss28(incidents); break;
						case 29: custom1.setLoss29(incidents); break;
						case 30: custom1.setLoss30(incidents); break;
								
						case 31: custom1.setLoss31(incidents); break;
						case 32: custom1.setLoss32(incidents); break;
						case 33: custom1.setLoss33(incidents); break;
						case 34: custom1.setLoss34(incidents); break;
						case 35: custom1.setLoss35(incidents); break;
						case 36: custom1.setLoss36(incidents); break;
						case 37: custom1.setLoss37(incidents); break;
						case 38: custom1.setLoss38(incidents); break;
						case 39: custom1.setLoss39(incidents); break;
						case 40: custom1.setLoss40(incidents); break;
							
						case 41: custom1.setLoss41(incidents); break;
						case 42: custom1.setLoss42(incidents); break;
						case 43: custom1.setLoss43(incidents); break;
						case 44: custom1.setLoss44(incidents); break;
						case 45: custom1.setLoss45(incidents); break;
						case 46: custom1.setLoss46(incidents); break;
						case 47: custom1.setLoss47(incidents); break;
						case 48: custom1.setLoss48(incidents); break;
						case 49: custom1.setLoss49(incidents); break;
						case 50: custom1.setLoss50(incidents); break;
						
						case 51: custom1.setLoss51(incidents); break;
						case 52: custom1.setLoss52(incidents); break;
						case 53: custom1.setLoss53(incidents); break;
						case 54: custom1.setLoss54(incidents); break;
						case 55: custom1.setLoss55(incidents); break;
						case 56: custom1.setLoss56(incidents); break;
						case 57: custom1.setLoss57(incidents); break;
						case 58: custom1.setLoss58(incidents); break;
						case 59: custom1.setLoss59(incidents); break;
						case 60: custom1.setLoss60(incidents); break;
						
						case 61: custom1.setLoss61(incidents); break;
						case 62: custom1.setLoss62(incidents); break;
						case 63: custom1.setLoss63(incidents); break;
						case 64: custom1.setLoss64(incidents); break;
						case 65: custom1.setLoss65(incidents); break;
						case 66: custom1.setLoss66(incidents); break;
						case 67: custom1.setLoss67(incidents); break;
						case 68: custom1.setLoss68(incidents); break;
						case 69: custom1.setLoss69(incidents); break;
						case 70: custom1.setLoss70(incidents); break;
						
						case 71: custom1.setLoss71(incidents); break;
						case 72: custom1.setLoss72(incidents); break;
						case 73: custom1.setLoss73(incidents); break;
						case 74: custom1.setLoss74(incidents); break;
						case 75: custom1.setLoss75(incidents); break;
						case 76: custom1.setLoss76(incidents); break;
						case 77: custom1.setLoss77(incidents); break;
						case 78: custom1.setLoss78(incidents); break;
						case 79: custom1.setLoss79(incidents); break;
						case 80: custom1.setLoss80(incidents); break;
						
						case 81: custom1.setLoss81(incidents); break;
						case 82: custom1.setLoss82(incidents); break;
						case 83: custom1.setLoss83(incidents); break;
						case 84: custom1.setLoss84(incidents); break;
						case 85: custom1.setLoss85(incidents); break;
						case 86: custom1.setLoss86(incidents); break;
						case 87: custom1.setLoss87(incidents); break;
						case 88: custom1.setLoss88(incidents); break;
						case 89: custom1.setLoss89(incidents); break;
						case 90: custom1.setLoss90(incidents); break;
						
						case 91: custom1.setLoss91(incidents); break;
						case 92: custom1.setLoss92(incidents); break;
						case 93: custom1.setLoss93(incidents); break;
						case 94: custom1.setLoss94(incidents); break;
						case 95: custom1.setLoss95(incidents); break;
						case 96: custom1.setLoss96(incidents); break;
						case 97: custom1.setLoss97(incidents); break;
						case 98: custom1.setLoss98(incidents); break;
						case 99: custom1.setLoss99(incidents); break;
						
						default: errComment = "Oops - something is wrong with this code.";
					}
	
				}

				if (custom1.getStationcode().equalsIgnoreCase("ATL")) {
					list.add(0, custom1);
					custom1.setStation_region("");
					custom1.setStation_region_mgr("");
				} else
					list.add(custom1);

			}
			
			logger.info(">>>>>>just finished long switch ...");
			logger.info(">>>>>>printing custom1 obj one by one ..." + "\n");
			StatReport_Custom_Dynamic_DTO myCustom1;
			for (int m = 0; m < list.size(); m++) {
				myCustom1 = (StatReport_Custom_Dynamic_DTO) list.get(m);
				logger.info(myCustom1.toString2());	
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
	}*/


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
						
	//					ReportingConstants.RPT_20_CUSTOM_1_NAME, messages.getMessage(
						ReportingConstants.RPT_20_CUSTOM_1_NAME, messages.getMessage(
								new Locale(user.getCurrentlocale()), "header.reportnum.20"), request);
				if (creportdata == null) {
					return null;
				} else {
					return creportdata;
				}

			case ReportingConstants.RPT_20_CUSTOM_55:
				creportdata = createDisputeResolutionReport(srDTO, ReportBMO.getCustomReport(55).getResource_key(), request, user);
				break;
			
		}
		return null;
	}
	
	private String createDisputeResolutionReport(StatReportDTO srDTO, String resource_key, HttpServletRequest request, Agent user) {
		// TODO Auto-generated method stub
		ReportBMO rbmo= new ReportBMO(request);
		rbmo.setUser(user);
		return rbmo.create_dispute_resolution_rpt(srDTO, 0, ReportingConstants.RPT_55_NAME, "Dispute Resolution Report");
	}
	
	/*****************************************************************************
	 * dynamic custom report MBR report for airtran - using JasperDesign
	 * 
	 * 
	 * @param srDTO
	 * @param reportnum
	 * @param reportname
	 * @param reporttitle
	 * @return
	 * @throws HibernateException
	 */
/*	public String create_dynamic_custom_report(StatReportDTO srDTO, int reportnum,
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
					+ " where station.active = :active "
					+ stationq
					//+ companylimit
					+ " group by station.station_region,station.stationcode,station.station_region_mgr,station.goal, station.station_ID "
					+ "order by station.station_region,station.stationcode";

			Query q = sess.createQuery(sql);

			if (srDTO.getStation_ID() != null
					&& !srDTO.getStation_ID()[0].equals("0"))
				q.setParameterList("station_ID", srDTO.getStation_ID());

			q.setBoolean("active", true);
			//q.setString("companyCode_ID", user.getStation().getCompany()
			//		.getCompanyCode_ID());
			List templist = q.list();
			List list = new ArrayList();
			if (templist.size() == 0) {
				logger.debug("no data for report");
				return null;
			}

			Object[] o = null;
			Object[] o2 = null;
			StatReport_Custom_Dynamic_DTO custom1 = null;

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

				custom1 = new StatReport_Custom_Dynamic_DTO();
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

				//TODO: temporarily setting it without WS
				// webservice call to retrieve enplanement information
				custom1.setBoarded(2.33);


 				
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

				HashMap<String, String> lossCodeMap;
				
				for (int j = 0; j < inc_list.size(); j++) {
					o2 = (Object[]) inc_list.get(j);

					incidents = ((Long) o2[0]).intValue();

					int myCodeIndex = ((Integer) o2[1]).intValue();
					
					lossCodeMap = new HashMap<String, String>();
					String errComment;  
					switch (myCodeIndex) {
						case 1: lossCodeMap.put("01", "" + incidents); break;
						case 2: lossCodeMap.put("02", "" + incidents); break;
						case 3: lossCodeMap.put("03", "" + incidents); break;
						case 4: lossCodeMap.put("04", "" + incidents); break;
						case 5: lossCodeMap.put("05", "" + incidents); break;	
						case 6: lossCodeMap.put("06", "" + incidents); break;		
						case 7: lossCodeMap.put("07", "" + incidents); break;
						case 8: lossCodeMap.put("08", "" + incidents); break;
						case 9: lossCodeMap.put("09", "" + incidents); break;
						case 10: lossCodeMap.put("10", "" + incidents); break;

						case 11: lossCodeMap.put("11", "" + incidents); break;
						case 12: lossCodeMap.put("12", "" + incidents); break;
						case 13: lossCodeMap.put("13", "" + incidents); break;
						case 14: lossCodeMap.put("14", "" + incidents); break;
						case 15: lossCodeMap.put("15", "" + incidents); break;
						case 16: lossCodeMap.put("16", "" + incidents); break;
						case 17: lossCodeMap.put("17", "" + incidents); break;
						case 18: lossCodeMap.put("18", "" + incidents); break;
						case 19: lossCodeMap.put("19", "" + incidents); break;
						case 20: lossCodeMap.put("20", "" + incidents); break;

						case 21: lossCodeMap.put("21", "" + incidents); break;
						case 22: lossCodeMap.put("22", "" + incidents); break;
						case 23: lossCodeMap.put("23", "" + incidents); break;
						case 24: lossCodeMap.put("24", "" + incidents); break;
						case 25: lossCodeMap.put("25", "" + incidents); break;
						case 26: lossCodeMap.put("26", "" + incidents); break;
						case 27: lossCodeMap.put("27", "" + incidents); break;
						case 28: lossCodeMap.put("28", "" + incidents); break;
						case 29: lossCodeMap.put("29", "" + incidents); break;
						case 30: lossCodeMap.put("30", "" + incidents); break;
								
						case 31: lossCodeMap.put("31", "" + incidents); break;
						case 32: lossCodeMap.put("32", "" + incidents); break;
						case 33: lossCodeMap.put("33", "" + incidents); break;
						case 34: lossCodeMap.put("34", "" + incidents); break;
						case 35: lossCodeMap.put("35", "" + incidents); break;
						case 36: lossCodeMap.put("36", "" + incidents); break;
						case 37: lossCodeMap.put("37", "" + incidents); break;
						case 38: lossCodeMap.put("38", "" + incidents); break;
						case 39: lossCodeMap.put("39", "" + incidents); break;
						case 40: lossCodeMap.put("40", "" + incidents); break;
							
						case 41: lossCodeMap.put("41", "" + incidents); break;
						case 42: lossCodeMap.put("42", "" + incidents); break;
						case 43: lossCodeMap.put("43", "" + incidents); break;
						case 44: lossCodeMap.put("44", "" + incidents); break;
						case 45: lossCodeMap.put("45", "" + incidents); break;
						case 46: lossCodeMap.put("46", "" + incidents); break;
						case 47: lossCodeMap.put("47", "" + incidents); break;
						case 48: lossCodeMap.put("48", "" + incidents); break;
						case 49: lossCodeMap.put("49", "" + incidents); break;
						case 50: lossCodeMap.put("50", "" + incidents); break;
						
						case 51: lossCodeMap.put("51", "" + incidents); break;
						case 52: lossCodeMap.put("52", "" + incidents); break;
						case 53: lossCodeMap.put("53", "" + incidents); break;
						case 54: lossCodeMap.put("54", "" + incidents); break;
						case 55: lossCodeMap.put("55", "" + incidents); break;
						case 56: lossCodeMap.put("56", "" + incidents); break;
						case 57: lossCodeMap.put("57", "" + incidents); break;
						case 58: lossCodeMap.put("58", "" + incidents); break;
						case 59: lossCodeMap.put("59", "" + incidents); break;
						case 60: lossCodeMap.put("60", "" + incidents); break;
						
						case 61: lossCodeMap.put("61", "" + incidents); break;
						case 62: lossCodeMap.put("62", "" + incidents); break;
						case 63: lossCodeMap.put("63", "" + incidents); break;
						case 64: lossCodeMap.put("64", "" + incidents); break;
						case 65: lossCodeMap.put("65", "" + incidents); break;
						case 66: lossCodeMap.put("66", "" + incidents); break;
						case 67: lossCodeMap.put("67", "" + incidents); break;
						case 68: lossCodeMap.put("68", "" + incidents); break;
						case 69: lossCodeMap.put("69", "" + incidents); break;
						case 70: lossCodeMap.put("70", "" + incidents); break;
						
						case 71: lossCodeMap.put("71", "" + incidents); break;
						case 72: lossCodeMap.put("72", "" + incidents); break;
						case 73: lossCodeMap.put("73", "" + incidents); break;
						case 74: lossCodeMap.put("74", "" + incidents); break;
						case 75: lossCodeMap.put("75", "" + incidents); break;
						case 76: lossCodeMap.put("76", "" + incidents); break;
						case 77: lossCodeMap.put("77", "" + incidents); break;
						case 78: lossCodeMap.put("78", "" + incidents); break;
						case 79: lossCodeMap.put("79", "" + incidents); break;
						case 80: lossCodeMap.put("80", "" + incidents); break;
						
						case 81: lossCodeMap.put("81", "" + incidents); break;
						case 82: lossCodeMap.put("82", "" + incidents); break;
						case 83: lossCodeMap.put("83", "" + incidents); break;
						case 84: lossCodeMap.put("84", "" + incidents); break;
						case 85: lossCodeMap.put("85", "" + incidents); break;
						case 86: lossCodeMap.put("86", "" + incidents); break;
						case 87: lossCodeMap.put("87", "" + incidents); break;
						case 88: lossCodeMap.put("88", "" + incidents); break;
						case 89: lossCodeMap.put("89", "" + incidents); break;
						case 90: lossCodeMap.put("90", "" + incidents); break;
						
						case 91: lossCodeMap.put("91", "" + incidents); break;
						case 92: lossCodeMap.put("92", "" + incidents); break;
						case 93: lossCodeMap.put("93", "" + incidents); break;
						case 94: lossCodeMap.put("94", "" + incidents); break;
						case 95: lossCodeMap.put("95", "" + incidents); break;
						case 96: lossCodeMap.put("96", "" + incidents); break;
						case 97: lossCodeMap.put("97", "" + incidents); break;
						case 98: lossCodeMap.put("98", "" + incidents); break;
						case 99: lossCodeMap.put("99", "" + incidents); break;
						
						default: errComment = "Oops - something is wrong with this code.";
					}
					custom1.setLossCodeMap(lossCodeMap);
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
	}*/

}