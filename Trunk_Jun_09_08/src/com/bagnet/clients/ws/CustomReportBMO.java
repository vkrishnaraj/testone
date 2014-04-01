package com.bagnet.clients.ws;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JExcelApiExporterParameter;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;

import com.bagnet.nettracer.exceptions.BagtagException;
import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.other.JRGovernedFileVirtualizer;
import com.bagnet.nettracer.reporting.CustomWestJetReports;
import com.bagnet.nettracer.reporting.ReportingConstants;
import com.bagnet.nettracer.tracing.bmo.LossCodeBMO;
import com.bagnet.nettracer.tracing.bmo.ReportBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Incident_Claimcheck;
import com.bagnet.nettracer.tracing.db.Itinerary;
import com.bagnet.nettracer.tracing.db.datafeed.FlightInfo;
import com.bagnet.nettracer.tracing.dto.StatReportDTO;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.lookup.LookupAirlineCodes;

public class CustomReportBMO implements com.bagnet.nettracer.integrations.reports.CustomReportBMO {

	private static final Logger logger = Logger.getLogger(CustomReportBMO.class);
	private static final Integer[] airsideCodes = {21,23,25,31,32,33,34,35,42,52};
	private String rootpath;

	public String createCustomReport(StatReportDTO srDTO, HttpServletRequest request, Agent user, String rootpath) {
		this.rootpath = rootpath;
		String creportdata = null;
		switch (srDTO.getCustomreportnum()) {
		case ReportingConstants.RPT_20_CUSTOM_3:
			creportdata = createDetailReport(srDTO, ReportBMO.getCustomReport(3).getResource_key(), request, user);
			break;
		case ReportingConstants.RPT_20_CUSTOM_4:
			creportdata = createSummaryReport(srDTO, ReportBMO.getCustomReport(4).getResource_key(), request, user);
			break;
		case ReportingConstants.RPT_20_CUSTOM_5:
			creportdata = createEarlyBagReport(srDTO, ReportBMO.getCustomReport(5).getResource_key(), request, user);
			break;
		case ReportingConstants.RPT_20_CUSTOM_55:
			creportdata = createDisputeResolutionReport(srDTO, ReportBMO.getCustomReport(55).getResource_key(), request, user);
			break;
		case ReportingConstants.RPT_20_CUSTOM_81:
			creportdata = createLossReport(srDTO, ReportBMO.getCustomReport(81).getResource_key(), request, user);
			break;
		case ReportingConstants.RPT_20_CUSTOM_82:
			creportdata = createPilferageReport(srDTO, ReportBMO.getCustomReport(82).getResource_key(), request, user);
			break;
		case ReportingConstants.RPT_20_CUSTOM_83:
			creportdata = createDamageReport(srDTO, ReportBMO.getCustomReport(83).getResource_key(), request, user);
			break;
		case ReportingConstants.RPT_20_CUSTOM_84:
			creportdata = createOtherAirlineReport(srDTO, ReportBMO.getCustomReport(84).getResource_key(), request, user);
			break;
		case ReportingConstants.RPT_20_CUSTOM_85:
			creportdata = createDelayedReport(srDTO, ReportBMO.getCustomReport(85).getResource_key(), request, user);
			break;
		case ReportingConstants.RPT_20_CUSTOM_97:
			creportdata = createChildRestraintReport(srDTO, ReportBMO.getCustomReport(97).getResource_key(), request, user);
			break;
		case ReportingConstants.RPT_20_CUSTOM_210:
			creportdata = createDisputeCountReport(srDTO, ReportBMO.getCustomReport(210).getResource_key(), request, user);
			break;

		}
		return creportdata;
	}
	


	private String createDisputeResolutionReport(StatReportDTO srDTO, String resource_key, HttpServletRequest request, Agent user) {
		ReportBMO rbmo= new ReportBMO(request);
		rbmo.setUser(user);
		return rbmo.create_dispute_resolution_rpt(srDTO, 0, ReportingConstants.RPT_55_NAME, "Dispute Resolution Report");
	}

	private String createEarlyBagReport(StatReportDTO srDTO, String resource_key, HttpServletRequest request, Agent user) {
		ReportBMO rbmo= new ReportBMO(request);
		rbmo.setUser(user);
		return rbmo.create_earlyBag_rpt(srDTO, 0, ReportingConstants.RPT_10_NAME, "Early Baggage Report");
	}

	private String createSummaryReport(StatReportDTO srDTO, String resource_key, HttpServletRequest request, Agent user) {
		Map parameters = new HashMap();
		List<SummaryReportDTO> dataList = new ArrayList();
		Session sess = HibernateWrapper.getSession().openSession();
		
		try {
			ResourceBundle bundle = ResourceBundle.getBundle("com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(user.getCurrentlocale()));
			parameters.put(JRParameter.REPORT_RESOURCE_BUNDLE, bundle);
			parameters.put(JRParameter.REPORT_LOCALE, new Locale(user.getCurrentlocale()));
			
			//get flight count numbers
			Criteria cri = sess.createCriteria(FlightInfo.class);
			
			Date start = null;
			Date end = null;

			if(srDTO.getStarttime() != null) {
				start = DateUtils.convertToDate(srDTO.getStarttime(), user.getDateformat().getFormat(), user
						.getCurrentlocale());
				cri.add(Restrictions.ge("departureDate", start));
			}
			if(srDTO.getEndtime() != null) {
				end = DateUtils.convertToDate(srDTO.getEndtime(), user.getDateformat().getFormat(), user
						.getCurrentlocale());
				Calendar c = new GregorianCalendar();
				c.setTime(end);
				c.add(Calendar.HOUR_OF_DAY, 24);
				end = c.getTime();
				cri.add(Restrictions.le("departureDate", end));
			}
			
			if(srDTO.getFaultstation_ID() != null && srDTO.getFaultstation_ID().length > 0
					&& Integer.parseInt(srDTO.getFaultstation_ID()[0]) != 0) {
				List<Integer> ids = new ArrayList<Integer>();
				for(String s : srDTO.getFaultstation_ID()) {
					ids.add(Integer.parseInt(s));
				}
				cri.add(Restrictions.in("departureCity", ids));
			}
			
			if(srDTO.getStatus_ID() != 0) {
				cri.add(Restrictions.eq("status.status_ID", srDTO.getStatus_ID()));
			}
			
			cri.setProjection(Projections.projectionList()
					.add(Property.forName("truncDepartDate").group())
					.add(Property.forName("departureCity").group())
					.add(Property.forName("id").count())
					.add(Projections.sum("totalPax"))
					.add(Projections.sum("connPax"))
					).addOrder(Order.asc("truncDepartDate")).addOrder(Order.asc("departureCity"));
			
			List<Object[]> foo = cri.list();
			
			if(foo == null || foo.size() < 1) {
				return null;
			}
			
			for (Object[] fi : foo) {
				Criteria c = sess.createCriteria(Incident.class);
				c.add(Restrictions.eq("createdate", (Date)fi[0]));
				c.createCriteria("faultstation").add(Restrictions.like("stationcode", (String)fi[1]));
				c.setProjection(Projections.rowCount());
				int totalFiles = (Integer)c.uniqueResult();
				c.add(Restrictions.in("loss_code", airsideCodes));
				int airsideFiles = (Integer)c.uniqueResult();
				SummaryReportDTO sd = new SummaryReportDTO();
				
				sd.setAirsidePirCount(airsideFiles);
				sd.setAirsideRatio(((double)airsideFiles * 1000) / (Integer)fi[3]);
				sd.setConnPax((Integer)fi[4]);
				sd.setFlightDate(DateUtils.formatDate((Date)fi[0], user.getDateformat().getFormat(), null, null));
				sd.setStationCode((String) fi[1]);
				sd.setTotalFlights((Integer) fi[2]);
				sd.setTotalPax((Integer) fi[3]);
				sd.setTotalPirCount(totalFiles);
				sd.setTotalRatio(((double)totalFiles* 1000) / (Integer)fi[3]);
				dataList.add(sd);
			}
			
			return ReportBMO.getReportFile(dataList, parameters, "wsSummaryReport", rootpath, srDTO.getOutputtype(),
					request);
		}
		catch (Exception e) {
			logger.error("unable to create report " + e);
			return null;
		}
		finally {
			if(sess != null) sess.close();
		}
	}

	private String createDetailReport(StatReportDTO srDTO, String resource_key, HttpServletRequest request, Agent user) {
		Map<String, Object> parameters = new HashMap();
		List<DetailReportDTO> dataList = new ArrayList();
		// TODO Auto-generated method stub


		Session sess = HibernateWrapper.getSession().openSession();

		try {
			ResourceBundle bundle = ResourceBundle.getBundle("com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(user.getCurrentlocale()));
			parameters.put(JRParameter.REPORT_RESOURCE_BUNDLE, bundle);
			parameters.put(JRParameter.REPORT_LOCALE, new Locale(user.getCurrentlocale()));
			//parameters.put(JRParameter.REPORT_TIME_ZONE, TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getCurrenttimezone()).getTimezone()));
			
			Criteria cri = sess.createCriteria(Incident.class);
			Date start = null;
			Date end = null;

			if(srDTO.getStarttime() != null) {
				start = DateUtils.convertToDate(srDTO.getStarttime(), user.getDateformat().getFormat(), user
						.getCurrentlocale());
				cri.add(Restrictions.ge("createdate", start));
			}
			if(srDTO.getEndtime() != null) {
				end = DateUtils.convertToDate(srDTO.getEndtime(), user.getDateformat().getFormat(), user
						.getCurrentlocale());
				cri.add(Restrictions.le("createdate", end));
			}
			if(srDTO.getItemType_ID() != 0) {
				cri.add(Restrictions.eq("itemtype.itemType_ID", srDTO.getItemType_ID()));
			}
			if(srDTO.getFaultstation_ID() != null && srDTO.getFaultstation_ID().length > 0
					&& Integer.parseInt(srDTO.getFaultstation_ID()[0]) != 0) {
				List<Integer> ids = new ArrayList<Integer>();
				for(String s : srDTO.getFaultstation_ID()) {
					ids.add(Integer.parseInt(s));
				}
				cri.add(Restrictions.in("faultstation.station_ID", ids));
			}
			if(srDTO.getStatus_ID() != 0) {
				cri.add(Restrictions.eq("status.status_ID", srDTO.getStatus_ID()));
			}
			List<Incident> result = cri.list();

			if(result == null || result.size() == 0) {
				return null;
			}
			for(Incident inc : result) {
				if(inc.getClaimcheck_list() == null || inc.getClaimcheck_list().size() < 1 ) {
					DetailReportDTO rdto = createDetailDTO(inc, user, "");
					if(rdto != null) dataList.add(rdto);
				}
				else {
					for(Incident_Claimcheck cc : (List<Incident_Claimcheck>)inc.getClaimcheck_list()) {
						
						DetailReportDTO rdto;
						try {
							rdto = createDetailDTO(inc, user, LookupAirlineCodes.getFullBagTag(cc.getClaimchecknum()));
						}
						catch (BagtagException e) {
							logger.warn("unable to convert bagtag");
							rdto = createDetailDTO(inc, user, cc.getClaimchecknum());
						}
						if(rdto != null) dataList.add(rdto);
					}
				}
			}

			return ReportBMO.getReportFile(dataList, parameters, "wsDetailReport", rootpath, srDTO.getOutputtype(),
					request);
		}
		catch (Exception e) {
			logger.error("unable to create report " + e);
			return null;
		}
		finally {
			sess.close();
		}
	}

	private DetailReportDTO createDetailDTO(Incident inc, Agent user, String bagTag) {
		DetailReportDTO rdto = new DetailReportDTO();
		rdto.setBagTag(bagTag);

		Itinerary i = null;
		if(inc.getItinerary_list() != null && inc.getItinerary_list().size() > 0) {
			for(Itinerary itin : (List<Itinerary>) inc.getItinerary_list()) {
				if(itin.getItinerarytype() == TracingConstants.PASSENGER_ROUTING) {
					i = itin;
					break;
				}
			}
		}
		if(i != null) {
			String depCity = "---";
			String arrCity = "---";
			if(i.getLegfrom() != null && i.getLegfrom().trim().length() > 0) {
				depCity = i.getLegfrom().trim();
			}
			if(i.getLegto() != null && i.getLegto().trim().length() > 0) {
				arrCity = i.getLegto().trim();
			}
			rdto.setCityPair(depCity + arrCity);
			rdto.setFlightDate(i.getDisdepartdate());
			rdto.setFlightNum(i.getFlightnum());
		}
		rdto.setFaultStation(inc.getFaultstationcode());
		if(inc.getLoss_code() > 0) {
			try{
			rdto.setLossCode(inc.getLoss_code());
			rdto.setReasonForLoss(LossCodeBMO.getCode(Integer.toString(inc.getLoss_code())).getDescription());
			} catch (Exception e){
				e.printStackTrace();

				rdto.setLossCode(0);
				rdto.setReasonForLoss("");
			}
			
		}
		else {
			rdto.setLossCode(0);
			rdto.setReasonForLoss("");
		}
		if(inc.getRecordlocator() != null) {
		rdto.setRecordLocator(inc.getRecordlocator());
		}
		else {
			rdto.setRecordLocator("");
		}
		return rdto;
	}

	private String createLossReport(StatReportDTO srDTO, String resource_key,
			HttpServletRequest request, Agent user) {
		srDTO.setDateFormat(user.getDateformat().getFormat());
		ResourceBundle resources = ResourceBundle.getBundle("com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(user.getCurrentlocale()));
		CustomWestJetReports custom = new CustomWestJetReports();
		
		List reportData = custom.getLossReportData(srDTO, resources);
		
		if (reportData == null) {
			return null;
		}
		
		Map parameters = new HashMap();
		
		String fileName = ReportingConstants.RPT_20_CUSTOM_81_NAME + "_" + (new SimpleDateFormat(ReportingConstants.DATETIME_FORMAT).format(TracerDateTime.getGMTDate())) + ReportingConstants.EXCEL_FILE_TYPE;
		String outputpath = rootpath + ReportingConstants.REPORT_TMP_PATH + fileName;
		JRGovernedFileVirtualizer virtualizer = new JRGovernedFileVirtualizer(100, rootpath + ReportingConstants.REPORT_TMP_PATH, 501);
		virtualizer.setReadOnly(false);
		
		FastReportBuilder drb = new FastReportBuilder();
		drb.setTitle(resources.getString("header.customreportnum.81") + "(" + srDTO.getStarttime() + " - " + srDTO.getEndtime() + ")");
		
		String stationCode = srDTO.getStationCode();
		if (stationCode !=null) {
			stationCode = stationCode.trim();
		}
		
		if (stationCode == null || stationCode.length() == 0) {
			String subtitle = resources.getString("custom.report.subtitle.totalPirs") + " = " + custom.getLossReportTotalPirs(srDTO) + ", " + 
			resources.getString("custom.report.subtitle.totalLoss") + " = " + custom.getLossReportLosses(srDTO);
			drb.setSubtitle(subtitle);
		}
		try {
			Style header = setupHeader(drb);
			drb.addColumn(resources.getString("custom.report.column.bso"), "column1", String.class.getName(), 200, header, header);
			drb.addColumn(resources.getString("custom.report.column.files.routing"), "column2", String.class.getName(), 200, header, header);
			
			generateDynamicReport(reportData, parameters, outputpath, drb);
			
		} catch (JRException jre) {
			jre.printStackTrace();
		} catch (ColumnBuilderException cbe) {
			cbe.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		} 
		virtualizer.cleanup();
		return fileName;
	}
	
	private String createPilferageReport(StatReportDTO srDTO, String resource_key,
			HttpServletRequest request, Agent user) {
		srDTO.setDateFormat(user.getDateformat().getFormat());
		ResourceBundle resources = ResourceBundle.getBundle("com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(user.getCurrentlocale()));
		CustomWestJetReports custom = new CustomWestJetReports();
		
		List reportData = custom.getPilferageReportData(srDTO, resources);
		
		if (reportData == null) {
			return null;
		}
		
		Map parameters = new HashMap();
		
		String fileName = ReportingConstants.RPT_20_CUSTOM_82_NAME + "_" + (new SimpleDateFormat(ReportingConstants.DATETIME_FORMAT).format(TracerDateTime.getGMTDate())) + ReportingConstants.EXCEL_FILE_TYPE;
		String outputpath = rootpath + ReportingConstants.REPORT_TMP_PATH + fileName;
		JRGovernedFileVirtualizer virtualizer = new JRGovernedFileVirtualizer(100, rootpath + ReportingConstants.REPORT_TMP_PATH, 501);
		virtualizer.setReadOnly(false);
		
		FastReportBuilder drb = new FastReportBuilder();
		drb.setTitle(resources.getString("header.customreportnum.82") + "(" + srDTO.getStarttime() + " - " + srDTO.getEndtime() + ")");
		
		String stationCode = srDTO.getStationCode();
		if (stationCode !=null) {
			stationCode = stationCode.trim();
		}
		
		if (stationCode == null || stationCode.trim().length() == 0) {

			String subtitle = resources.getString("custom.report.subtitle.totalPilferage") + " = " + custom.getTotalPilferage(srDTO);
			drb.setSubtitle(subtitle);
		}
		try {
			Style header = setupHeader(drb);
			drb.addColumn(resources.getString("custom.report.column.bso"), "column1", String.class.getName(), 200, header, header);
			drb.addColumn(resources.getString("custom.report.column.files.routing"), "column2", String.class.getName(), 200, header, header);
			drb.addColumn("", "column3", String.class.getName(), 200, header, header);
			drb.addColumn("", "column4", String.class.getName(), 200, header, header);
			drb.addColumn("", "column5", String.class.getName(), 200, header, header);
			
			generateDynamicReport(reportData, parameters, outputpath, drb);
			
		} catch (JRException jre) {
			jre.printStackTrace();
		} catch (ColumnBuilderException cbe) {
			cbe.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		} 
		virtualizer.cleanup();
		return fileName;
	}
	
	private String createDamageReport(StatReportDTO srDTO, String resource_key,
			HttpServletRequest request, Agent user) {
		srDTO.setDateFormat(user.getDateformat().getFormat());
		ResourceBundle resources = ResourceBundle.getBundle("com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(user.getCurrentlocale()));
		CustomWestJetReports custom = new CustomWestJetReports();
		
		List reportData = custom.getDamageReportData(srDTO, resources);
		
		if (reportData == null) {
			return null;
		}
		
		Map parameters = new HashMap();
		
		String fileName = ReportingConstants.RPT_20_CUSTOM_83_NAME + "_" + (new SimpleDateFormat(ReportingConstants.DATETIME_FORMAT).format(TracerDateTime.getGMTDate())) + ReportingConstants.EXCEL_FILE_TYPE;
		String outputpath = rootpath + ReportingConstants.REPORT_TMP_PATH + fileName;
		JRGovernedFileVirtualizer virtualizer = new JRGovernedFileVirtualizer(100, rootpath + ReportingConstants.REPORT_TMP_PATH, 501);
		virtualizer.setReadOnly(false);
		
		FastReportBuilder drb = new FastReportBuilder();
		drb.setTitle(resources.getString("header.customreportnum.83") + "(" + srDTO.getStarttime() + " - " + srDTO.getEndtime() + ")");
		
		
		String stationCode = srDTO.getStationCode();
		
		if (stationCode.equals("0")) {
			stationCode = null;
		}
		
		if (stationCode !=null) {
			stationCode = stationCode.trim();
		}
		
		if (stationCode == null || stationCode.trim().length() == 0) {

			String subtitle = resources.getString("custom.report.subtitle.totalDamage") + " = " + custom.getTotalDamage(srDTO);
			drb.setSubtitle(subtitle);
		}
		
		try {
			Style header = setupHeader(drb);
			drb.addColumn(resources.getString("custom.report.column.bso"), "column1", String.class.getName(), 100, header, header);
			drb.addColumn(resources.getString("custom.report.column.totalDpr"), "column2", String.class.getName(), 100, header, header);
			drb.addColumn(resources.getString("custom.report.column.repairs"), "column3", String.class.getName(), 100, header, header);
			drb.addColumn(resources.getString("custom.report.column.replace"), "column4", String.class.getName(), 100, header, header);
			
			generateDynamicReport(reportData, parameters, outputpath, drb);
			
		} catch (JRException jre) {
			jre.printStackTrace();
		} catch (ColumnBuilderException cbe) {
			cbe.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		} 
		virtualizer.cleanup();
		return fileName;
	}

	// TODO: HERE
	private String createOtherAirlineReport(StatReportDTO srDTO, String resource_key,
			HttpServletRequest request, Agent user) {
		srDTO.setDateFormat(user.getDateformat().getFormat());
		ResourceBundle resources = ResourceBundle.getBundle("com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(user.getCurrentlocale()));
		CustomWestJetReports custom = new CustomWestJetReports();
		
		List reportData = custom.getOtherAirlineReportData(srDTO, resources);
		
		if (reportData == null) {
			return null;
		}
		
		Map parameters = new HashMap();
		
		String fileName = ReportingConstants.RPT_20_CUSTOM_84_NAME + "_" + (new SimpleDateFormat(ReportingConstants.DATETIME_FORMAT).format(TracerDateTime.getGMTDate())) + ReportingConstants.EXCEL_FILE_TYPE;
		String outputpath = rootpath + ReportingConstants.REPORT_TMP_PATH + fileName;
		JRGovernedFileVirtualizer virtualizer = new JRGovernedFileVirtualizer(100, rootpath + ReportingConstants.REPORT_TMP_PATH, 501);
		virtualizer.setReadOnly(false);
		
		FastReportBuilder drb = new FastReportBuilder();
		drb.setTitle(resources.getString("header.customreportnum.84") + "(" + srDTO.getStarttime() + " - " + srDTO.getEndtime() + ")");
		
		String airlineCode = srDTO.getStationCode();
		if (airlineCode !=null) {
			airlineCode = airlineCode.trim();
		}
		
		if (airlineCode == null || airlineCode.length() == 0) {

			String subtitle = resources.getString("custom.report.subtitle.84.totalPir") + " = " + custom.getLossReportTotalPirs(srDTO) + ", "
			+ resources.getString("custom.report.subtitle.84.totalOalPirs") + " = " + custom.getTotalOALPirs(srDTO) + " "
			+ resources.getString("custom.report.subtitle.84.basedOnTag");
			drb.setSubtitle(subtitle);
		}
		try {
			Style header = setupHeader(drb);
			drb.addColumn(resources.getString("custom.report.column.airline"), "column1", String.class.getName(), 200, header, header);
			drb.addColumn(resources.getString("custom.report.column.numFile"), "column2", String.class.getName(), 200, header, header);
			drb.addColumn("", "column3", String.class.getName(), 200, header, header);
			drb.addColumn("", "column4", String.class.getName(), 200, header, header);
			drb.addColumn("", "column5", String.class.getName(), 200, header, header);
			drb.addColumn("", "column6", String.class.getName(), 200, header, header);
			
			generateDynamicReport(reportData, parameters, outputpath, drb);
			
		} catch (JRException jre) {
			jre.printStackTrace();
		} catch (ColumnBuilderException cbe) {
			cbe.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		} 
		virtualizer.cleanup();
		return fileName;
	}
	
	private String createChildRestraintReport(StatReportDTO srDTO, String resource_key,
			HttpServletRequest request, Agent user) {
		srDTO.setDateFormat(user.getDateformat().getFormat());
		ResourceBundle resources = ResourceBundle.getBundle("com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(user.getCurrentlocale()));
		CustomWestJetReports custom = new CustomWestJetReports();
		String runDate = DateUtils.formatDate(new Date(), user.getDateformat().getFormat(), user.getDefaultlocale(), null);
		
		List reportData = custom.getChildRestraintReportData(srDTO, resources);
		
		if (reportData == null) {
			return null;
		}
		
		Map parameters = new HashMap();
		
		String fileName = ReportingConstants.RPT_20_CUSTOM_97_NAME + "_" + (new SimpleDateFormat(ReportingConstants.DATETIME_FORMAT).format(TracerDateTime.getGMTDate())) + ReportingConstants.EXCEL_FILE_TYPE;
		String outputpath = rootpath + ReportingConstants.REPORT_TMP_PATH + fileName;
		JRGovernedFileVirtualizer virtualizer = new JRGovernedFileVirtualizer(100, rootpath + ReportingConstants.REPORT_TMP_PATH, 501);
		virtualizer.setReadOnly(false);
		
		FastReportBuilder drb = new FastReportBuilder();
		drb.setTitle(resources.getString("header.customreportnum.97") + " (" + srDTO.getStarttime() + " - " + srDTO.getEndtime() + ")");
		
		String stationCode = srDTO.getStationCode();
		if (stationCode !=null) {
			stationCode = stationCode.trim();
		}
		
			String subtitle = resources.getString("lf.ir.report.created.on") + " " + runDate+" by "+user.getUsername()+" for: " + srDTO.getStarttime() + " - " + srDTO.getEndtime();
			drb.setSubtitle(subtitle);
		try {
			Style header = setupHeader(drb);

			if (stationCode == null || stationCode.length() == 0) {
				drb.addColumn(resources.getString("custom.report.column.bso"), "column1", String.class.getName(), 100, header, header);
				drb.addColumn(resources.getString("custom.report.column.carseat"), "column2", String.class.getName(), 100, header, header);
				drb.addColumn(resources.getString("custom.report.column.booster"), "column3", String.class.getName(), 100, header, header);
				drb.addColumn(resources.getString("custom.report.column.none"), "column4", String.class.getName(), 100, header, header);
			} else {

				drb.addColumn(resources.getString("custom.report.column.datecreated"), "column1", String.class.getName(), 100, header, header);
				drb.addColumn(resources.getString("custom.report.column.station"), "column2", String.class.getName(), 50, header, header);
				drb.addColumn(resources.getString("custom.report.column.incident.id"), "column3", String.class.getName(), 150, header, header);
				drb.addColumn(resources.getString("custom.report.column.carseat"), "column4", String.class.getName(), 100, header, header);
				drb.addColumn(resources.getString("custom.report.column.booster"), "column5", String.class.getName(), 100, header, header);
				drb.addColumn(resources.getString("custom.report.column.none"), "column6", String.class.getName(), 100, header, header);
			}
			
			generateDynamicReport(reportData, parameters, outputpath, drb);
			
		} catch (JRException jre) {
			jre.printStackTrace();
		} catch (ColumnBuilderException cbe) {
			cbe.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		} 
		virtualizer.cleanup();
		return fileName;
	}

	private String createDelayedReport(StatReportDTO srDTO, String resource_key,
			HttpServletRequest request, Agent user) {
		srDTO.setDateFormat(user.getDateformat().getFormat());
		ResourceBundle resources = ResourceBundle.getBundle("com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(user.getCurrentlocale()));
		CustomWestJetReports custom = new CustomWestJetReports();
		
		List reportData = custom.getDelayedReportData(srDTO, resources);
		
		if (reportData == null) {
			return null;
		}
		
		Map parameters = new HashMap();
		
		String fileName = ReportingConstants.RPT_20_CUSTOM_85_NAME + "_" + (new SimpleDateFormat(ReportingConstants.DATETIME_FORMAT).format(TracerDateTime.getGMTDate())) + ReportingConstants.EXCEL_FILE_TYPE;
		String outputpath = rootpath + ReportingConstants.REPORT_TMP_PATH + fileName;
		JRGovernedFileVirtualizer virtualizer = new JRGovernedFileVirtualizer(100, rootpath + ReportingConstants.REPORT_TMP_PATH, 501);
		virtualizer.setReadOnly(false);
		
		FastReportBuilder drb = new FastReportBuilder();
		drb.setTitle(resources.getString("header.customreportnum.85") + "(Files Closed; " + srDTO.getStarttime() + " - " + srDTO.getEndtime() + ")");
		
		String stationCode = srDTO.getStationCode();
		
		if (stationCode !=null) {
			stationCode = stationCode.trim();
			if (stationCode.equals("0")) {
				stationCode = null;
			}
		}
		
		if (stationCode == null || stationCode.length() > 0) {

			String subtitle = resources.getString("custom.report.subtitle.84.totalPir") + " = " + custom.getLossReportTotalPirs(srDTO);
			drb.setSubtitle(subtitle);
		}
		
		try {
			Style header = setupHeader(drb);
			Style detailStyle = new Style("detail");
			drb.addColumn(resources.getString("custom.report.column.bso"), "column1", String.class.getName(), 40, detailStyle, header);
			drb.addColumn(resources.getString("custom.report.column.84.2"), "column2", Integer.class.getName(), 40, detailStyle, header);
			drb.addColumn(resources.getString("custom.report.column.84.3"), "column3", Integer.class.getName(), 40, detailStyle, header);
			drb.addColumn(resources.getString("custom.report.column.84.4"), "column4", Integer.class.getName(), 40, detailStyle, header);
			drb.addColumn(resources.getString("custom.report.column.84.5"), "column5", Integer.class.getName(), 40, detailStyle, header);
			drb.addColumn(resources.getString("custom.report.column.84.6"), "column6", Integer.class.getName(), 40, detailStyle, header);
			drb.addColumn(resources.getString("custom.report.column.84.7"), "column7", Integer.class.getName(), 40, detailStyle, header);
			drb.addColumn(resources.getString("custom.report.column.84.8"), "column8", Integer.class.getName(), 40, detailStyle, header);
			drb.addColumn(resources.getString("custom.report.column.84.9"), "column9", Integer.class.getName(), 40, detailStyle, header);
			drb.addColumn(resources.getString("custom.report.column.84.10"), "column10", Integer.class.getName(), 40, detailStyle, header);
			drb.addGlobalFooterVariable(2, DJCalculation.SUM, header);
			drb.addGlobalFooterVariable(3, DJCalculation.SUM, header);
			drb.addGlobalFooterVariable(4, DJCalculation.SUM, header);
			drb.addGlobalFooterVariable(5, DJCalculation.SUM, header);
			drb.addGlobalFooterVariable(6, DJCalculation.SUM, header);
			drb.addGlobalFooterVariable(7, DJCalculation.SUM, header);
			drb.addGlobalFooterVariable(8, DJCalculation.SUM, header);
			drb.addGlobalFooterVariable(9, DJCalculation.SUM, header);
			drb.addGlobalFooterVariable(10, DJCalculation.SUM, header);
			drb.setGrandTotalLegend("Grand Total :");
			drb.setUseFullPageWidth(true);
			generateDynamicReport(reportData, parameters, outputpath, drb);
			
		} catch (JRException jre) {
			jre.printStackTrace();
		} catch (ColumnBuilderException cbe) {
			cbe.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		} 
		virtualizer.cleanup();
		return fileName;
	}
	
	private String createDisputeCountReport(StatReportDTO srDTO, String resource_key,
			HttpServletRequest request, Agent user) {
		srDTO.setDateFormat(user.getDateformat().getFormat());
		ResourceBundle resources = ResourceBundle.getBundle("com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(user.getCurrentlocale()));
		CustomWestJetReports custom = new CustomWestJetReports();
		
		List reportData = custom.getDisputeCountData(srDTO, resources);
		
		if (reportData == null) {
			return null;
		}
		
		Map parameters = new HashMap();
		
		String fileName = ReportingConstants.RPT_20_CUSTOM_210_NAME + "_" + (new SimpleDateFormat(ReportingConstants.DATETIME_FORMAT).format(TracerDateTime.getGMTDate())) + ReportingConstants.EXCEL_FILE_TYPE;
		String outputpath = rootpath + ReportingConstants.REPORT_TMP_PATH + fileName;
		JRGovernedFileVirtualizer virtualizer = new JRGovernedFileVirtualizer(100, rootpath + ReportingConstants.REPORT_TMP_PATH, 501);
		virtualizer.setReadOnly(false);
		
		FastReportBuilder drb = new FastReportBuilder();
		drb.setTitle(resources.getString("header.customreportnum.210") + "(" + srDTO.getStarttime() + " - " + srDTO.getEndtime() + ")");
				
		try {
			Style header = setupHeader(drb);
			drb.addColumn(resources.getString("custom.report.210.column.station"), "column1", String.class.getName(), 50, header, header);
			drb.addColumn(resources.getString("custom.report.210.column.total_d"), "column2", String.class.getName(), 100, header, header);
			drb.addColumn(resources.getString("custom.report.210.column.both_d"), "column3", String.class.getName(), 100, header, header);
			drb.addColumn(resources.getString("custom.report.210.column.station_d"), "column4", String.class.getName(), 100, header, header);
			drb.addColumn(resources.getString("custom.report.210.column.code_d"), "column5", String.class.getName(), 100, header, header);
			drb.addColumn(resources.getString("custom.report.210.column.none_d"), "column6", String.class.getName(), 150, header, header);
			
			generateDynamicReport(reportData, parameters, outputpath, drb);
			
		} catch (JRException jre) {
			jre.printStackTrace();
		} catch (ColumnBuilderException cbe) {
			cbe.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		} 
		virtualizer.cleanup();
		return fileName;
	}



	private Style setupHeader(FastReportBuilder drb) {
		Style header = new Style();
//		header.setHorizontalAlign(HorizontalAlign.CENTER);
		header.setVerticalAlign(VerticalAlign.MIDDLE);
//		header.setOverridesExistingStyle(true);
//		drb.addAutoText(AutoText.AUTOTEXT_CREATED_ON, AutoText.POSITION_HEADER, AutoText.ALIGMENT_LEFT);
		return header;
	}



	private void generateDynamicReport(List reportData, Map parameters,
			String outputpath, FastReportBuilder drb) throws JRException {
		drb.setIgnorePagination(true);
		DynamicReport report = drb.build();
		JRDataSource data = new JRBeanCollectionDataSource(reportData);
		
		JasperPrint jp = DynamicJasperHelper.generateJasperPrint(report, new ClassicLayoutManager(), data);
		parameters.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
		parameters.put(JExcelApiExporterParameter.JASPER_PRINT,  jp);
		parameters.put(JExcelApiExporterParameter.OUTPUT_FILE_NAME, outputpath);
		parameters.put(JExcelApiExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
		parameters.put(JExcelApiExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
		parameters.put(JExcelApiExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
		parameters.put(JExcelApiExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
		parameters.put(JExcelApiExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		parameters.put(JExcelApiExporterParameter.IS_FONT_SIZE_FIX_ENABLED, Boolean.TRUE); 
		parameters.put(JExcelApiExporterParameter.IS_COLLAPSE_ROW_SPAN, Boolean.TRUE);
		
		JExcelApiExporter exporter = new JExcelApiExporter();
		exporter.setParameters(parameters);
		exporter.exportReport();
	}
	
}
