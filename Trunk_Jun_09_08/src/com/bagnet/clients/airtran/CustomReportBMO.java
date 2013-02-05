/*
 * Created on Jul 15, 2004
 *
 * Administrator
 */
package com.bagnet.clients.airtran;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JExcelApiExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.apache.log4j.Logger;
import org.apache.struts.util.MessageResources;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.other.JRGovernedFileVirtualizer;
import com.bagnet.nettracer.reporting.ReportingConstants;
import com.bagnet.nettracer.tracing.bmo.ReportBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.dto.StatReportDTO;
import com.bagnet.nettracer.tracing.dto.StatReportElement;
import com.bagnet.nettracer.tracing.dto.StatReport_Custom_1_DTO;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;

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


	public String create_custom_report_501(StatReportDTO srDTO, int reportnum, String reportname, String reporttitle, HttpServletRequest request){
		Session sess = HibernateWrapper.getSession().openSession();
		try{
			Map parameters = new HashMap();
			parameters.put("title", reporttitle);
			
			String goal = DateUtils.formatDate(new Date(), "yyyy", null, null);
			parameters.put("goalyear", goal);
			parameters.put("companyname", user.getStation().getCompany()
					.getCompanydesc().toUpperCase());
			

			List<Object[]> stationList = get501StationList(srDTO);
			List<Object[]> lossList = get501LossList(srDTO, parameters);
			if(stationList == null || lossList == null){
				logger.error("unable to create report: result list is null");
				return null;
			}
			
			
			Map<Long, StatReport_Custom_1_DTO> stationMap = new LinkedHashMap<Long, StatReport_Custom_1_DTO>();
			Map<Long, StatReport_Custom_1_DTO> regionMap = new LinkedHashMap<Long, StatReport_Custom_1_DTO>();
			
			NewSkiesIntegrationWrapper eiw = new NewSkiesIntegrationWrapper();
			boolean b1 = eiw.readEnplaneProps("enplane.endpoint", "enplane.calltype");
			if (!b1) {
				logger.error(eiw.getErrormsg());
				return null;
			}
			
			for(Object[] station:stationList){
				StatReport_Custom_1_DTO addStation = new StatReport_Custom_1_DTO();
				addStation.setStationcode((String)station[0]);
				addStation.setRegionid(station[1]!=null?(Long)station[1]:0);
				addStation.setStation_region(station[2]!=null?(String)station[2]:"NO REGION");
				addStation.setStation_region_mgr(station[3]!=null?(String)station[3]:null);
				addStation.setGoal((Double)station[4]);
				addStation.setCompanycode(station[5]!=null?(String)station[5]:null);
				addStation.setRegion_goal(station[7]!=null?(Double)station[7]:0.0);
				addStation.setDomestic(station[8]!=null?(((String)station[8]).equalsIgnoreCase("US")?true:false):false);
				stationMap.put((Long)station[6], addStation);
					
				double callresult = eiw.getEnplanement(srDTO.getStarttime(), (srDTO
						.getEndtime() == null ? srDTO.getStarttime() : srDTO.getEndtime()),
						addStation.getStationcode());

				if (callresult < 0) {
					addStation.setBoarded(0.00);

				} else {
					addStation.setBoarded(callresult);
				}
				
				if(!regionMap.containsKey(addStation.getRegionid())){
					StatReport_Custom_1_DTO addRegion = new StatReport_Custom_1_DTO();
					addRegion.setStationcode("Region Total");
					addRegion.setRegionid(addStation.getRegionid());
					addRegion.setStation_region(station[2]!=null?(String)station[2]:"NO REGION");
					addRegion.setStation_region_mgr((String)station[3]);
					addRegion.setGoal(station[7]!=null?(Double)station[7]:0.0);
					addRegion.setCompanycode((String)station[5]);
					regionMap.put(addRegion.getRegionid(), addRegion);
				}
			}
			
			StatReport_Custom_1_DTO companyTotal = new StatReport_Custom_1_DTO();
			companyTotal.setStationcode("System Total");
			companyTotal.setStation_region("Total");
			
			StatReport_Custom_1_DTO domesticTotal = new StatReport_Custom_1_DTO();
			domesticTotal.setStation_region("Total");
			domesticTotal.setStationcode("Domestic Total");
			
			for(Object[] loss:lossList){
				if(!stationMap.containsKey((Long)loss[0])){
					//Loss station is not part of the filtered station list
					continue;
				}
				StatReport_Custom_1_DTO station = stationMap.get((Long)loss[0]);
				StatReport_Custom_1_DTO region = regionMap.get(station.getRegionid());
				
				Integer incidents = (Integer)loss[2];
				if (((Integer) loss[1]).intValue() == 10){
					station.setLoss10(incidents);
					region.setLoss10(region.getLoss10() + incidents);
					companyTotal.setLoss10(companyTotal.getLoss10() + incidents);
					if (station.isDomestic()) {
						domesticTotal.setLoss10(domesticTotal.getLoss10() + incidents);
					}
				} else if (((Integer) loss[1]).intValue() == 12){
					station.setLoss12(incidents);
					region.setLoss12(region.getLoss12() + incidents);
					companyTotal.setLoss12(companyTotal.getLoss12() + incidents);
					if (station.isDomestic()) {
						domesticTotal.setLoss12(domesticTotal.getLoss12() + incidents);
					}
				} else if (((Integer) loss[1]).intValue() == 15){
					station.setLoss15(incidents);
					region.setLoss15(region.getLoss15() + incidents);
					companyTotal.setLoss15(companyTotal.getLoss15() + incidents);
					if (station.isDomestic()) {
						domesticTotal.setLoss15(domesticTotal.getLoss15() + incidents);
					}
			    } else if (((Integer) loss[1]).intValue() == 18){
					station.setLoss18(incidents);
					region.setLoss18(region.getLoss18() + incidents);
					companyTotal.setLoss18(companyTotal.getLoss18() + incidents);
					if (station.isDomestic()) {
						domesticTotal.setLoss18(domesticTotal.getLoss18() + incidents);
					}
			    } else if (((Integer) loss[1]).intValue() == 23){
					station.setLoss23(incidents);
					region.setLoss23(region.getLoss23() + incidents);
					companyTotal.setLoss23(companyTotal.getLoss23() + incidents);
					if (station.isDomestic()) {
						domesticTotal.setLoss23(domesticTotal.getLoss23() + incidents);
					}
			    } else if (((Integer) loss[1]).intValue() == 24){
					station.setLoss24(incidents);
					region.setLoss24(region.getLoss24() + incidents);
					companyTotal.setLoss24(companyTotal.getLoss24() + incidents);
					if (station.isDomestic()) {
						domesticTotal.setLoss24(domesticTotal.getLoss24() + incidents);
					}
			    } else if (((Integer) loss[1]).intValue() == 25){
					station.setLoss25(incidents);
					region.setLoss25(region.getLoss25() + incidents);
					companyTotal.setLoss25(companyTotal.getLoss25() + incidents);
					if (station.isDomestic()) {
						domesticTotal.setLoss25(domesticTotal.getLoss25() + incidents);
					}
			    } else if (((Integer) loss[1]).intValue() == 26){
					station.setLoss26(incidents);
					region.setLoss26(region.getLoss26() + incidents);
					companyTotal.setLoss26(companyTotal.getLoss26() + incidents);
					if (station.isDomestic()) {
						domesticTotal.setLoss26(domesticTotal.getLoss26() + incidents);
					}
			    } else if (((Integer) loss[1]).intValue() == 30){
					station.setLoss30(incidents);
					region.setLoss30(region.getLoss30() + incidents);
					companyTotal.setLoss30(companyTotal.getLoss30() + incidents);
					if (station.isDomestic()) {
						domesticTotal.setLoss30(domesticTotal.getLoss30() + incidents);
					}
			    } else if (((Integer) loss[1]).intValue() == 32){
					station.setLoss32(incidents);
					region.setLoss32(region.getLoss32() + incidents);
					companyTotal.setLoss32(companyTotal.getLoss32() + incidents);
					if (station.isDomestic()) {
						domesticTotal.setLoss32(domesticTotal.getLoss32() + incidents);
					}
			    } else if (((Integer) loss[1]).intValue() == 33){
					station.setLoss33(incidents);
					region.setLoss33(region.getLoss33() + incidents);
					companyTotal.setLoss33(companyTotal.getLoss33() + incidents);
					if (station.isDomestic()) {
						domesticTotal.setLoss33(domesticTotal.getLoss33() + incidents);
					}
			    } else if (((Integer) loss[1]).intValue() == 35){
					station.setLoss35(incidents);
					region.setLoss35(region.getLoss35() + incidents);
					companyTotal.setLoss35(companyTotal.getLoss35() + incidents);
					if (station.isDomestic()) {
						domesticTotal.setLoss35(domesticTotal.getLoss35() + incidents);
					}
			    } else if (((Integer) loss[1]).intValue() == 42){
					station.setLoss42(incidents);
					region.setLoss42(region.getLoss42() + incidents);
					companyTotal.setLoss42(companyTotal.getLoss42() + incidents);
					if (station.isDomestic()) {
						domesticTotal.setLoss42(domesticTotal.getLoss42() + incidents);
					}
			    } else if (((Integer) loss[1]).intValue() == 51){
					station.setLoss51(incidents);
					region.setLoss51(region.getLoss51() + incidents);
					companyTotal.setLoss51(companyTotal.getLoss51() + incidents);
					if (station.isDomestic()) {
						domesticTotal.setLoss51(domesticTotal.getLoss51() + incidents);
					}
			    } else if (((Integer) loss[1]).intValue() == 54){
					station.setLoss54(incidents);
					region.setLoss54(region.getLoss54() + incidents);
					companyTotal.setLoss54(companyTotal.getLoss54() + incidents);
					if (station.isDomestic()) {
						domesticTotal.setLoss54(domesticTotal.getLoss54() + incidents);
					}
			    } else if (((Integer) loss[1]).intValue() == 56){
					station.setLoss56(incidents);
					region.setLoss56(region.getLoss56() + incidents);
					companyTotal.setLoss56(companyTotal.getLoss56() + incidents);
					if (station.isDomestic()) {
						domesticTotal.setLoss56(domesticTotal.getLoss56() + incidents);
					}
			    } else if (((Integer) loss[1]).intValue() == 63){
					station.setLoss63(incidents);
					region.setLoss63(region.getLoss63() + incidents);
					companyTotal.setLoss63(companyTotal.getLoss63() + incidents);
					if (station.isDomestic()) {
						domesticTotal.setLoss63(domesticTotal.getLoss63() + incidents);
					}
			    } else if (((Integer) loss[1]).intValue() == 64){
					station.setLoss64(incidents);
					region.setLoss64(region.getLoss64() + incidents);
					companyTotal.setLoss64(companyTotal.getLoss64() + incidents);
					if (station.isDomestic()) {
						domesticTotal.setLoss64(domesticTotal.getLoss64() + incidents);
					}
			    } else if (((Integer) loss[1]).intValue() == 73){
					station.setLoss73(incidents);
					region.setLoss73(region.getLoss73() + incidents);
					companyTotal.setLoss73(companyTotal.getLoss73() + incidents);
					if (station.isDomestic()) {
						domesticTotal.setLoss73(domesticTotal.getLoss73() + incidents);
					}
			    } else if (((Integer) loss[1]).intValue() == 74){
					station.setLoss74(incidents);
					region.setLoss74(region.getLoss74() + incidents);
					companyTotal.setLoss74(companyTotal.getLoss74() + incidents);
					if (station.isDomestic()) {
						domesticTotal.setLoss74(domesticTotal.getLoss74() + incidents);
					}
			    } else if (((Integer) loss[1]).intValue() == 76){
					station.setLoss76(incidents);
					region.setLoss76(region.getLoss76() + incidents);
					companyTotal.setLoss76(companyTotal.getLoss76() + incidents);
					if (station.isDomestic()) {
						domesticTotal.setLoss76(domesticTotal.getLoss76() + incidents);
					}
			    } else if (((Integer) loss[1]).intValue() == 78){
					station.setLoss78(incidents);
					region.setLoss78(region.getLoss78() + incidents);
					companyTotal.setLoss78(companyTotal.getLoss78() + incidents);
					if (station.isDomestic()) {
						domesticTotal.setLoss78(domesticTotal.getLoss78() + incidents);
					}
			    } else if (((Integer) loss[1]).intValue() == 80){
					station.setLoss80(incidents);
					region.setLoss80(region.getLoss80() + incidents);
					companyTotal.setLoss80(companyTotal.getLoss80() + incidents);
					if (station.isDomestic()) {
						domesticTotal.setLoss80(domesticTotal.getLoss80() + incidents);
					}
			    } else if (((Integer) loss[1]).intValue() == 81){
					station.setLoss81(incidents);
					region.setLoss81(region.getLoss81() + incidents);
					companyTotal.setLoss81(companyTotal.getLoss81() + incidents);
					if (station.isDomestic()) {
						domesticTotal.setLoss81(domesticTotal.getLoss81() + incidents);
					}
			    } else if (((Integer) loss[1]).intValue() == 82){
					station.setLoss82(incidents);
					region.setLoss82(region.getLoss82() + incidents);
					companyTotal.setLoss82(companyTotal.getLoss82() + incidents);
					if (station.isDomestic()) {
						domesticTotal.setLoss82(domesticTotal.getLoss82() + incidents);
					}
			    } else if (((Integer) loss[1]).intValue() == 90){
					station.setLoss90(incidents);
					region.setLoss90(region.getLoss90() + incidents);
					companyTotal.setLoss90(companyTotal.getLoss90() + incidents);
					if (station.isDomestic()) {
						domesticTotal.setLoss90(domesticTotal.getLoss90() + incidents);
					}
			    }
				
			}
			
			ArrayList<StatReportElement> stationDataList = new ArrayList<StatReportElement>();
			ArrayList<StatReportElement> summaryList = new ArrayList<StatReportElement>();
			double companyBoarded = 0;
			double domesticBoarded = 0;

			for(StatReport_Custom_1_DTO station:stationMap.values()){
				stationDataList.add(createStatReportElement(station, false));
				StatReport_Custom_1_DTO r = regionMap.get(station.getRegionid());
				r.setBoarded(r.getBoarded() + station.getBoarded());
				companyBoarded += station.getBoarded();
				if (station.isDomestic()) {
					domesticBoarded += station.getBoarded();
				}
			}
			
			for(StatReport_Custom_1_DTO region:regionMap.values()){
				if(srDTO.getOutputtype() == TracingConstants.REPORT_OUTPUT_XLS){
					summaryList.add(createStatReportElement(region, false));
				} else {
					summaryList.add(createStatReportElement(region, false));
				}
			}
			
			companyTotal.setBoarded(companyBoarded);
			domesticTotal.setBoarded(domesticBoarded);
			summaryList.add(createStatReportElement(companyTotal, false));
			summaryList.add(createStatReportElement(domesticTotal, true));
			
			if(srDTO.getOutputtype() == TracingConstants.REPORT_OUTPUT_XLS){
				return create501ExcelReport(rootpath, 0, user, summaryList, stationDataList, parameters);
			} else {
				return ReportBMO.getReportFile(new ArrayList(stationMap.values()), parameters, reportname, rootpath, srDTO
						.getOutputtype(), request);
			}
			
		} catch (Exception e) {
			logger.error("unable to create report " + e);
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
		}
	}
	
	private List<Object[]> get501LossList(StatReportDTO srDTO, Map parameters){
		TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(
				user.getDefaulttimezone()).getTimezone());
		
		Date sdate = null, edate = null;
		Date sdate1 = null, edate1 = null; // add one for timezone
		Date stime = null; // time to compare (04:00 if eastern, for example)
		String dateq = "";

		ArrayList dateal = null;
		if ((dateal = ReportBMO.calculateDateDiff(srDTO, tz, user)) == null) {
			logger.error("unable to create report: unable to calculate date range");
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
				dateq = " and ((i.createdate= :startdate and i.createtime >= :starttime) "
						+ " or (i.createdate= :startdate1 and i.createtime <= :starttime))";

				edate = null;
			} else {

				// first get the beginning and end dates using date and time, then get
				// dates in between
				dateq = " and ((i.createdate= :startdate and i.createtime >= :starttime) "
						+ " or (i.createdate= :enddate1 and i.createtime <= :starttime)"
						+ " or (i.createdate > :startdate and i.createdate <= :enddate))";

				parameters.put("edate", srDTO.getEndtime());
			}
		} else if (sdate != null) {
			parameters.put("sdate", srDTO.getStarttime());
			parameters.put("edate", srDTO.getStarttime());
			dateq = " and ((incident.createdate= :startdate and incident.createtime >= :starttime) "
					+ " or (incident.createdate= :startdate1 and incident.createtime <= :starttime))";
			edate = null;
		}
		
		String mbrtypeq = "";
		if (srDTO.getItemType_ID() >= 1) {
			mbrtypeq = " and i.itemType_ID = " + srDTO.getItemType_ID() + " ";
		}

		String statusq = "";
		if (srDTO.getStatus_ID() >= 1) {
			statusq = " and i.status_ID = " + srDTO.getStatus_ID() + " ";
		}
		
		String lossSQL = "select i.faultstation_ID as station_id, i.loss_code as loss_code, count(i.loss_code) as count " +
		"from incident i " +
		"where i.loss_code != 0 " + dateq + mbrtypeq + statusq +
		" group by i.loss_code, i.faultstation_ID order by i.loss_code, i.faultstation_ID";

		Session sess = HibernateWrapper.getSession().openSession();
		try{
			SQLQuery lossQuery = sess.createSQLQuery(lossSQL);

			if (sdate != null) {
				lossQuery.setDate("startdate", sdate);
				if (edate == null)
					lossQuery.setDate("startdate1", sdate1);
				lossQuery.setTime("starttime", stime);
			}
			if (edate != null) {
				lossQuery.setDate("enddate1", edate1);
				lossQuery.setDate("enddate", edate);
			}
			
			lossQuery.addScalar("station_id", StandardBasicTypes.LONG);
			lossQuery.addScalar("loss_code", StandardBasicTypes.INTEGER);
			lossQuery.addScalar("count", StandardBasicTypes.INTEGER);
			
			List<Object[]> lossList = lossQuery.list();
			return lossList;
		} catch (Exception e) {
			logger.error("unable to create report " + e);
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
		}
	}
	
	private List<Object[]> get501StationList(StatReportDTO srDTO){
		String stationq = "";
		if (srDTO.getStation_ID() != null
				&& !srDTO.getStation_ID()[0].equals("0")) {
			stationq = " and s.station_ID in (:station_ID) ";
		}
		String stationSQL = "select s.stationcode as stationcode, r.id as region_id, r.name as region_name" +
		", r.director as region_director, s.goal as station_goal, s.companycode_id as companycode, s.station_ID as station_id, r.target as region_target, s.countrycode_ID as countryCode " +
		"from station s left outer join region r on s.region_id = r.id " +
		"where s.active = 1 and s.companycode_ID = :companycode_ID " +
		stationq +
		"order by r.name asc, s.stationcode asc";
		Session sess = HibernateWrapper.getSession().openSession();
		try{
			SQLQuery stationQuery = sess.createSQLQuery(stationSQL);
			stationQuery.setParameter("companycode_ID", user.getCompanycode_ID());
			
			if (srDTO.getStation_ID() != null
					&& !srDTO.getStation_ID()[0].equals("0")) {
					ArrayList<Integer> stationList = new ArrayList<Integer>();
					for (String s: srDTO.getStation_ID()) {
						stationList.add(Integer.parseInt(s));
					}
						
					stationQuery.setParameterList("station_ID", stationList);
			}
			
			stationQuery.addScalar("stationcode", StandardBasicTypes.STRING);
			stationQuery.addScalar("region_id", StandardBasicTypes.LONG);
			stationQuery.addScalar("region_name", StandardBasicTypes.STRING);
			stationQuery.addScalar("region_director", StandardBasicTypes.STRING);
			stationQuery.addScalar("station_goal", StandardBasicTypes.DOUBLE);
			stationQuery.addScalar("companycode", StandardBasicTypes.STRING);
			stationQuery.addScalar("station_id", StandardBasicTypes.LONG);
			stationQuery.addScalar("region_target", StandardBasicTypes.DOUBLE);
			stationQuery.addScalar("countryCode", StandardBasicTypes.STRING);
			
			List<Object[]> stationList = stationQuery.list();
			return stationList;
		
		} catch (Exception e) {
			logger.error("unable to create report " + e);
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
		}
	}
	
	private StatReportElement createStatReportElement(StatReport_Custom_1_DTO stat, boolean dot){
		DecimalFormat df = new DecimalFormat("#.##");
		DecimalFormat dfBoarded = new DecimalFormat("#");
		
		StatReportElement toAdd = new StatReportElement();
		toAdd.setDot(stat.getDomesticStr());
		toAdd.setE1(stat.getStationcode());
		toAdd.setE2(stat.getLoss10());
		toAdd.setE3(stat.getLoss12());
		toAdd.setE4(stat.getLoss15());
		toAdd.setE5(stat.getLoss18());
		toAdd.setE6(stat.getLoss23());
		toAdd.setE7(stat.getLoss24());
		toAdd.setE8(stat.getLoss25());
		toAdd.setE9(stat.getLoss26());
		toAdd.setE10(stat.getLoss30());
		toAdd.setE11(stat.getLoss32());
		toAdd.setE12(stat.getLoss33());
		toAdd.setE13(stat.getLoss35());
		toAdd.setE14(stat.getLoss42());
		toAdd.setE15(stat.getLoss51());
		toAdd.setE16(stat.getLoss54());
		toAdd.setE17(stat.getLoss56());
		toAdd.setE18(stat.getLoss63());
		toAdd.setE19(stat.getLoss64());
		toAdd.setE20(stat.getLoss73());
		toAdd.setE21(stat.getLoss74());
		toAdd.setE22(stat.getLoss76());
		toAdd.setE23(stat.getLoss78());
		toAdd.setE24(stat.getLoss80());
		toAdd.setE25(stat.getLoss81());
		toAdd.setE26(stat.getLoss82());
		toAdd.setE27(stat.getLoss90());
		toAdd.setE28(new Long(stat.getTotal()).toString());
		toAdd.setE29(dfBoarded.format(stat.getBoarded()));
		if(dot){
			toAdd.setE30(df.format(stat.getDOTRatio()));
		} else {
			toAdd.setE30(df.format(stat.getRatio501()));
		}
		toAdd.setE31(df.format(stat.getGoal()));
		toAdd.setE32(stat.getStation_region());
		toAdd.setE33(df.format(stat.getRegion_goal()));
		return toAdd;
	}
	
	
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

			case ReportingConstants.RPT_20_CUSTOM_501:
				creportdata = create_custom_report_501(srDTO,
						ReportingConstants.RPT_20_CUSTOM_501,
						
	//					ReportingConstants.RPT_20_CUSTOM_1_NAME, messages.getMessage(
						ReportingConstants.RPT_20_CUSTOM_501_NAME, messages.getMessage(
								new Locale(user.getCurrentlocale()), "header.reportnum.501"), request);
				if (creportdata == null) {
					return null;
				} else {
					return creportdata;
				}
				
			case ReportingConstants.RPT_20_CUSTOM_55:
				creportdata = createDisputeResolutionReport(srDTO, ReportBMO.getCustomReport(55).getResource_key(), request, user);
				return creportdata;
			
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

	public String create501ExcelReport(String root, int salvageId, Agent user, List summaryList, List stationDataList, Map parameters) {

		ResourceBundle resources = ResourceBundle.getBundle("com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(user.getCurrentlocale()));
		String fileName = ReportingConstants.RPT_20_CUSTOM_501_NAME + "_" + (new SimpleDateFormat(ReportingConstants.DATETIME_FORMAT).format(TracerDateTime.getGMTDate())) + ReportingConstants.EXCEL_FILE_TYPE;
		String outputpath = root + ReportingConstants.REPORT_TMP_PATH + fileName;
		JRGovernedFileVirtualizer virtualizer = new JRGovernedFileVirtualizer(100, rootpath + ReportingConstants.REPORT_TMP_PATH, 501);
		virtualizer.setReadOnly(false);
				
		try {
			JasperPrint summary = generateReportTab(summaryList, parameters, true);
			JasperPrint stations = generateReportTab(stationDataList, parameters, false);
			
			
			ArrayList<JasperPrint> tabs = new ArrayList<JasperPrint>();
			summary.setPageWidth(2000);
			stations.setPageWidth(2000);
			tabs.add(summary);
			tabs.add(stations);
			
			String[] sheetNames = { "System Summary", "Stations" };
			
			parameters.put(JRParameter.IS_IGNORE_PAGINATION, true);
			parameters.put(JRXlsExporterParameter.SHEET_NAMES, sheetNames);
			parameters.put(JExcelApiExporterParameter.JASPER_PRINT_LIST,  tabs);
			parameters.put(JExcelApiExporterParameter.OUTPUT_FILE_NAME, outputpath);
			parameters.put(JExcelApiExporterParameter.IS_ONE_PAGE_PER_SHEET, false);
			parameters.put(JExcelApiExporterParameter.IS_WHITE_PAGE_BACKGROUND, false);
			parameters.put(JExcelApiExporterParameter.IGNORE_PAGE_MARGINS, true);
			parameters.put(JExcelApiExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, true);
			parameters.put(JExcelApiExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, true);
			parameters.put(JExcelApiExporterParameter.IS_FONT_SIZE_FIX_ENABLED, true); 
			parameters.put(JExcelApiExporterParameter.IS_COLLAPSE_ROW_SPAN, true);
			parameters.put(JExcelApiExporterParameter.IS_DETECT_CELL_TYPE, true);
			
			
			JExcelApiExporter exporter = new JExcelApiExporter();
			exporter.setParameters(parameters);
			exporter.exportReport();
		} catch (ColumnBuilderException cbe) {
			cbe.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		} catch (JRException jre) {
			jre.printStackTrace();
		} 
		virtualizer.cleanup();
		return fileName;
	}

	private JasperPrint generateReportTab(List dlist, Map parameters,
			boolean summaryTab) throws ColumnBuilderException,
			ClassNotFoundException, JRException {
		FastReportBuilder drb = new FastReportBuilder();
		
		if (summaryTab) {
			drb.setTitle("AIRTRAN AIRWAYS " + (String)parameters.get("title"));
			drb.setSubtitle((String)parameters.get("sdate") + " TO " + (String)parameters.get("edate"));
		}
		
		Style header = new Style();
		header.setBackgroundColor(java.awt.Color.GRAY);
		header.setOverridesExistingStyle(true);
		
		Style column = new Style();
		column.setBackgroundColor(java.awt.Color.WHITE);
		column.setOverridesExistingStyle(true);
		drb.setIgnorePagination(true);
		
		drb.addColumn("REGION", "e32", String.class.getName(), 200, column, header);
		drb.addColumn("LOCN", "e1", String.class.getName(), 50, column, header);
		if (!summaryTab) {
			drb.addColumn("DOM", "dot", String.class.getName(), 50, column, header);
		}
		drb.addColumn("10", "e2", Integer.class.getName(), 50, column, header);
		drb.addColumn("12", "e3", Integer.class.getName(), 50, column, header);
		drb.addColumn("15", "e4", Integer.class.getName(), 50, column, header);
		drb.addColumn("18", "e5", Integer.class.getName(), 50, column, header);
		drb.addColumn("23", "e6", Integer.class.getName(), 50, column, header);
		drb.addColumn("24", "e7", Integer.class.getName(), 50, column, header);
		drb.addColumn("25", "e8", Integer.class.getName(), 50, column, header);
		drb.addColumn("26", "e9", Integer.class.getName(), 50, column, header);
		drb.addColumn("30", "e10", Integer.class.getName(), 50, column, header);
		drb.addColumn("32", "e11", Integer.class.getName(), 50, column, header);
		drb.addColumn("33", "e12", Integer.class.getName(), 50, column, header);
		drb.addColumn("35", "e13", Integer.class.getName(), 50, column, header);
		drb.addColumn("42", "e14", Integer.class.getName(), 50, column, header);
		drb.addColumn("51", "e15", Integer.class.getName(), 50, column, header);
		drb.addColumn("54", "e16", Integer.class.getName(), 50, column, header);
		drb.addColumn("56", "e17", Integer.class.getName(), 50, column, header);
		drb.addColumn("63", "e18", Integer.class.getName(), 50, column, header);
		drb.addColumn("64", "e19", Integer.class.getName(), 50, column, header);
		drb.addColumn("73", "e20", Integer.class.getName(), 50, column, header);
		drb.addColumn("74", "e21", Integer.class.getName(), 50, column, header);
		drb.addColumn("76", "e22", Integer.class.getName(), 50, column, header);
		drb.addColumn("78", "e23", Integer.class.getName(), 50, column, header);
		drb.addColumn("80", "e24", Integer.class.getName(), 50, column, header);
		drb.addColumn("81", "e25", Integer.class.getName(), 50, column, header);
		drb.addColumn("82", "e26", Integer.class.getName(), 50, column, header);
		drb.addColumn("90", "e27", Integer.class.getName(), 50, column, header);
		drb.addColumn("TOTAL", "e28i", Integer.class.getName(), 50, column, header);
		drb.addColumn("BOARD", "e29i", Integer.class.getName(), 50, column, header);
		drb.addColumn("RATIO", "e30f", Float.class.getName(), 50, column, header);
		drb.addColumn("GOALS", "e31f", Float.class.getName(), 50, column, header);
		drb.addColumn("REGION GOALS", "e33f", Float.class.getName(), 50, column, header);
		
		DynamicReport report = drb.build();
		JRDataSource data = new JRBeanCollectionDataSource(dlist);
		JasperPrint jp = DynamicJasperHelper.generateJasperPrint(report, new ClassicLayoutManager(), data);
		return jp;
	}
	
}