package com.bagnet.clients.ws;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperReport;

import org.apache.log4j.Logger;
import org.apache.struts.taglib.TagUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.bagnet.nettracer.exceptions.BagtagException;
import com.bagnet.nettracer.hibernate.HibernateWrapper;
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
import com.bagnet.nettracer.tracing.utils.AdminUtils;
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
		}
		return creportdata;
	}

	private String createEarlyBagReport(StatReportDTO srDTO, String resource_key, HttpServletRequest request, Agent user) {
		// TODO Auto-generated method stub
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
		if(inc.getFaultstationcode() != null);
		rdto.setFaultStation(inc.getFaultstationcode());
		if(inc.getLoss_code() > 0) {
			rdto.setLossCode(inc.getLoss_code());
			rdto.setReasonForLoss(LossCodeBMO.getCode(Integer.toString(inc.getLoss_code())).getDescription());
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

}
