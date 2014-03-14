package com.bagnet.clients.b6;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale; 
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRParameter;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.bagnet.clients.b6.reports.PAWOBReportDTO;
import com.bagnet.nettracer.exceptions.AmountOfDataOutOfRangeException;
import com.bagnet.nettracer.exceptions.MissingRequiredFieldsException;
import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.reporting.ReportingConstants;
import com.bagnet.nettracer.tracing.bmo.ReportBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.claims.ClaimSettlement;
import com.bagnet.nettracer.tracing.dto.PPLC_DTO;
import com.bagnet.nettracer.tracing.dto.StatReportDTO;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;

import edu.emory.mathcs.backport.java.util.Arrays;

public class CustomReportBMO implements com.bagnet.nettracer.integrations.reports.CustomReportBMO {

	private static final Logger logger = Logger.getLogger(CustomReportBMO.class);
	public static String newline = System.getProperty("line.separator");

	public String createCustomReport(StatReportDTO srDTO, HttpServletRequest request, Agent user, String rootpath) {
		String creportdata = null;
		switch (srDTO.getCustomreportnum()) {
		case ReportingConstants.RPT_20_CUSTOM_6:
			creportdata = createPPLCReport(srDTO, ReportBMO.getCustomReport(6).getResource_key(), request, user,
					rootpath);
			break;
		case ReportingConstants.RPT_20_CUSTOM_77:
			creportdata = createPawobReport(srDTO, ReportBMO.getCustomReport(77).getResource_key(), request, user,
					rootpath);
			break;
		case ReportingConstants.RPT_20_CUSTOM_55:
			creportdata = createDisputeResolutionReport(srDTO, ReportBMO.getCustomReport(55).getResource_key(), request, user);
			break;
		default:
			break;

		}
		return creportdata;
	}

	private String createDisputeResolutionReport(StatReportDTO srDTO, String resource_key, HttpServletRequest request, Agent user) {
		ReportBMO rbmo= new ReportBMO(request);
		rbmo.setUser(user);
		return rbmo.create_dispute_resolution_rpt(srDTO, 0, ReportingConstants.RPT_55_NAME, "Dispute Resolution Report");
	}
	
	private String createPPLCReport(StatReportDTO srDTO, String resource_key, HttpServletRequest request, Agent user,
			String rootpath) {

		Map<String, Object> parameters = new HashMap<String, Object>();
		List<PPLC_DTO> dataList = new ArrayList<PPLC_DTO>();
		// TODO Auto-generated method stub

		Session sess = HibernateWrapper.getDirtySession().openSession();
		try {
			ResourceBundle bundle = ResourceBundle.getBundle(
					"com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(user.getCurrentlocale()));
			parameters.put(JRParameter.REPORT_RESOURCE_BUNDLE, bundle);
			parameters.put(JRParameter.REPORT_LOCALE, new Locale(user.getCurrentlocale()));
			parameters.put("dateFormat", user.getDateformat() != null ? new SimpleDateFormat(user.getDateformat()
					.getFormat()) : TracingConstants.DISPLAY_DATEFORMAT);

			if (srDTO.getStarttime() == null || srDTO.getEndtime() == null || srDTO.getStarttime().trim().length() < 1
					|| srDTO.getEndtime().trim().length() < 1) {
				if (logger.isDebugEnabled()) {
					logger.debug("tried to do pplc report with no date parameters");
				}
				throw new MissingRequiredFieldsException();
			}

			Date startDate = DateUtils.convertToDate(srDTO.getStarttime(), user.getDateformat().getFormat(), user
					.getCurrentlocale());

			Date endDate = DateUtils.convertToDate(srDTO.getEndtime(), user.getDateformat().getFormat(), user
					.getCurrentlocale());

			parameters.put("startDate", startDate);
			parameters.put("endDate", endDate);

			String queryString = "from ClaimSettlement cs where cs.incident.status.status_ID != :closedStatus "
					+ " and cs.incident.createdate between :startDate and :endDate ";

			List<Integer> stationList = null;
			if (srDTO.getStation_ID() != null) {
				for (String temp : srDTO.getStation_ID()) {
					Integer id = Integer.parseInt(temp);
					if (id == 0) {
						stationList = null;
						break;
					}
					if (stationList == null)
						stationList = new ArrayList<Integer>();
					stationList.add(id);
				}
			}

			if (stationList != null) {
				queryString += " and cs.incident.stationassigned.station_ID in (:stationList) ";
			}

			queryString += " order by cs.incident.stationassigned.stationcode, cs.pplcDue asc";

			Query q = sess.createQuery(queryString);
			q.setDate("startDate", startDate);
			q.setDate("endDate", endDate);
			q.setInteger("closedStatus", TracingConstants.MBR_STATUS_CLOSED);

			if (stationList != null) {
				q.setParameterList("stationList", stationList);
			}

			@SuppressWarnings("unchecked")
			List<ClaimSettlement> result = q.list();

			if (result.size() == 0) {
				return null;
			}

			for (ClaimSettlement cs : result) {
				PPLC_DTO pdto = new PPLC_DTO();
				
				pdto.setDueDate(cs.getPplcDue());
				pdto.setFirstName(cs.getFirstName());
				pdto.setLastName(cs.getLastName());
				pdto.setReceivedDate(cs.getPplcReceived());
				pdto.setOfferDueDate(cs.getOfferDue());
				pdto.setOfferSentDate(cs.getOfferSent());
				pdto.setSentDate(cs.getPplcSent());

				Incident incident = cs.getIncident();
				if (incident != null) {
					pdto.setRecordLocator(incident.getRecordlocator());
					pdto.setStationAssigned(incident.getStationcode());
					pdto.setIncidentId(incident.getIncident_ID());
					pdto.setAgentAssigned((incident.getAgentassigned() == null) ? null : incident.getAgentassigned().getUsername());
				}

				dataList.add(pdto);
 			}
			
			return ReportBMO.getReportFile(dataList, parameters, "b6_pplc_age", rootpath, srDTO.getOutputtype(), request);

		} catch (Exception e) {
			logger.error("unable to create report " + e);
			return null;
		} finally {
			if (sess != null) {
				sess.close();
			}
		}
	}
	
	private String createPawobReport(
			StatReportDTO srDTO,
			String resource_key,
			HttpServletRequest request, 
			Agent user,
			String rootpath) {
		
		//Session sess = HibernateWrapper.getDirtySession().openSession();
		Session sess = HibernateWrapper.getReportingSession().openSession();
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			ResourceBundle bundle = ResourceBundle.getBundle(
					"com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(user.getCurrentlocale()));
			parameters.put(JRParameter.REPORT_RESOURCE_BUNDLE, bundle);
			parameters.put(JRParameter.REPORT_LOCALE, new Locale(user.getCurrentlocale()));
			parameters.put("dateFormat", user.getDateformat() != null ? new SimpleDateFormat(user.getDateformat()
					.getFormat()) : TracingConstants.DISPLAY_DATEFORMAT);

			if (srDTO.getStarttime() == null || srDTO.getEndtime() == null || srDTO.getStarttime().trim().length() < 1
					|| srDTO.getEndtime().trim().length() < 1) {
				if (logger.isDebugEnabled()) {
					logger.debug("tried to render pawob report with no beginning or ending date");
				}
				throw new MissingRequiredFieldsException();
			} 

			Date startDate = DateUtils.convertToDate(srDTO.getStarttime(), user.getDateformat().getFormat(), user
					.getCurrentlocale());

			Date endDate = DateUtils.convertToDate(srDTO.getEndtime(), user.getDateformat().getFormat(), user
					.getCurrentlocale());
			
			//done:deal with date range not to exceed 93 days rule here
			long PAWOB_REPORT_DATE_RANGE_MAX_NUMBER_OF_DAYS = 93L;
			boolean isDateRangeOutsideLimit = DateUtils.isDateRangeOutsideLimit(startDate, endDate, PAWOB_REPORT_DATE_RANGE_MAX_NUMBER_OF_DAYS);
			if (isDateRangeOutsideLimit) {
				throw new AmountOfDataOutOfRangeException();
			}
			
			parameters.put("startDate", startDate);
			parameters.put("endDate", endDate);
			
			//to deal with pax flight date range here
			boolean isPaxFlightDateRangeAvailable = true;
			if (srDTO.getPaxflightstarttime() == null 
					|| srDTO.getPaxflightendtime() == null 
					|| srDTO.getPaxflightstarttime().trim().length() < 1
					|| srDTO.getPaxflightendtime().trim().length() < 1) {
				if (logger.isDebugEnabled()) {
					logger.debug("tried to render pawob report with no passenger flight date range...");
				}
				isPaxFlightDateRangeAvailable = false;
			}

			Date paxFlightStartDate = DateUtils.convertToDate(srDTO.getPaxflightstarttime(), user.getDateformat().getFormat(), user
					.getCurrentlocale());

			Date paxFlightEndDate = DateUtils.convertToDate(srDTO.getPaxflightendtime(), user.getDateformat().getFormat(), user
					.getCurrentlocale());

			parameters.put("paxFlightStartDate", paxFlightStartDate);
			parameters.put("paxFlightEndDate", paxFlightEndDate);
			
			List<PAWOBReportDTO> reportList = new ArrayList<PAWOBReportDTO>();

			StringBuffer sql = new StringBuffer();
			
			sql.append("SELECT * FROM pawob_report" + newline);
			sql.append(" WHERE date_created >= :startDate");
			sql.append(" AND date_created < :endDate ");
			if (isPaxFlightDateRangeAvailable) {
				sql.append(" AND finalSegmentDateTime >= :paxFlightStartDate ");
				sql.append(" AND finalSegmentDateTime < :paxFlightEndDate ");
			}

			//deal with create station here
			boolean isAllCreateStation = false;
			String[] myCreateStationId = srDTO.getCreatestation_ID();
			if (myCreateStationId != null && myCreateStationId.length>0 ) {
				@SuppressWarnings("unchecked")
				List<String> myCreateStationIdList = Arrays.asList(myCreateStationId);
				String myCreateStationSql = "(";
				for (String item : myCreateStationIdList) {
					myCreateStationSql += "'" + item + "',";
					if (item.equalsIgnoreCase("0")) {
						isAllCreateStation = true;
					}
				}
				//replace last character with )
				if( !isAllCreateStation ) {
					int myLastIndex = myCreateStationSql.length() - 2;
					if (myLastIndex > 0) {
						myCreateStationSql = myCreateStationSql.substring(0, myLastIndex);
						myCreateStationSql += "')";
						sql.append(" AND created_station IN " + myCreateStationSql + " ");
					}
				}
			}
			
			//deal with charge station here
			boolean isAllChargeStation = false;
			String[] myChargeStationId = srDTO.getFaultstation_ID();
			if (myChargeStationId != null && myChargeStationId.length>0) {
				@SuppressWarnings("unchecked")
				List<String> myChargeStationIdList = Arrays.asList(myChargeStationId);
				String myChargeStationSql = "(";
				for (String item : myChargeStationIdList) {
					myChargeStationSql += "'" + item + "',";
					if (item.equalsIgnoreCase("0")) {
						isAllChargeStation = true;
					}
				}
				//replace last character with )
				if( !isAllChargeStation ) {
					int myLastIndex = myChargeStationSql.length() - 2;
					if (myLastIndex > 0) {
						myChargeStationSql = myChargeStationSql.substring(0, myLastIndex);
						myChargeStationSql += "')";
						sql.append(" AND charge_city IN " + myChargeStationSql + " ");
					}
				}
			}
			
			//to deal with assigned station here
			boolean isAllAssignedStation = false;
			String[] myAssignedStationId = srDTO.getStation_ID();
			if (myAssignedStationId != null && myAssignedStationId.length>0) {
				@SuppressWarnings("unchecked")
				List<String> myAssignedStationIdList = Arrays.asList(myAssignedStationId);
				String myAssignedStationSql = "(";
				for (String item : myAssignedStationIdList) {
					myAssignedStationSql += "'" + item + "',";
					if (item.equalsIgnoreCase("0")) {
						isAllAssignedStation = true;
					}
				}
				//replace last character with )
				if( !isAllAssignedStation ) {
					int myLastIndex = myAssignedStationSql.length() - 2;
					if (myLastIndex > 0) {
						myAssignedStationSql = myAssignedStationSql.substring(0, myLastIndex);
						myAssignedStationSql += "')";
						sql.append(" AND Assigned_Station IN " + myAssignedStationSql + " ");
					}
				}
			}
			
			logger.error("entire query:" + sql.toString());
			

			SQLQuery query = sess.createSQLQuery(sql.toString());
							
			TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getCurrenttimezone()).getTimezone());
				
			Date sdate = DateUtils.convertToDate(srDTO.getStarttime() + " 0" + TracerDateTime.getHourDiff(tz) + ":00:00 AM", TracingConstants.DB_DATETIMEFORMAT_MSSQL, null);
			Date edate = DateUtils.convertToDate(srDTO.getEndtime() + " 0" + TracerDateTime.getHourDiff(tz) + ":00:00 AM", TracingConstants.DB_DATETIMEFORMAT_MSSQL, null);
			Date paxStartFlightDate = DateUtils.convertToDate(srDTO.getPaxflightstarttime() + " 0" + TracerDateTime.getHourDiff(tz) + ":00:00 AM", TracingConstants.DB_DATETIMEFORMAT_MSSQL, null);
			Date paxEndFlightDate = DateUtils.convertToDate(srDTO.getPaxflightendtime() + " 0" + TracerDateTime.getHourDiff(tz) + ":00:00 AM", TracingConstants.DB_DATETIMEFORMAT_MSSQL, null);
			
			// TODO: HERE
			query.setDate("startDate", sdate);
			query.setDate("endDate", edate);
			if (isPaxFlightDateRangeAvailable) {

				query.setDate("paxFlightStartDate", paxStartFlightDate);
				query.setDate("paxFlightEndDate", paxEndFlightDate);
			}
			
			@SuppressWarnings("unchecked")
			List<Object[]> results = query.list();
			

			int myResultSize = results.size();
			if (myResultSize >= ReportingConstants.RPT_20_CUSTOM_MAX_ALLOWED_ROWS) {
				//logger.error("data volumn exceeds limit : " + reportList.size());
				throw new AmountOfDataOutOfRangeException("data volumn exceeds limit : " + myResultSize);
			}
			
			if (myResultSize <= 0) {
				throw new AmountOfDataOutOfRangeException("no data to report!");
			}
				
			for (Object[] obj: results) {
				PAWOBReportDTO dto = new PAWOBReportDTO();
				dto.setName((String) obj[0]);
				dto.setIncident_id((String) obj[1]);
				
				/// ###########
				//String date_created = DateUtils.formatDate((Date) obj[2], "mm/dd/yyyy hh:mm aa", null, TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));
				//String timeCreated = date_created.split(" ")[1];
				//dto.setTime_created(timeCreated);
				/// ###########
				
				Date obj2 = (Date) obj[2];
				long difference = (tz != null ? tz.getOffset(obj2.getTime()) : 0);
				obj2 = new Date(obj2.getTime() + difference);
				
				dto.setDate_created(obj2);
				String strMyTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(obj2);
				dto.setTime_created(strMyTime);
				
				dto.setFlights((String) obj[4]);
				dto.setDestination((String) obj[5]);
				dto.setOrigin((String) obj[6]);
				dto.setCountry((String) obj[7]);
				dto.setCreated_station((String) obj[8]);
				dto.setStatus((String) obj[9]);
				dto.setCharge_city((String) obj[10]);
				dto.setCharge_code((Integer) obj[11]);
				dto.setPnr((String) obj[12]);
				dto.setBag_tags((String) obj[13]);
				dto.setCheckin_location((String) obj[14]);
				dto.setBag_colors((String) obj[15]);
				dto.setBag_types((String) obj[16]);
				dto.setFinalSegmentDateTime((Date) obj[17]);
				dto.setAssigned_station((String) obj[18]);
				reportList.add(dto);
			}
						
			// Create report
			logger.info(reportList.size());
			
			return ReportBMO.getReportFile(reportList, parameters, "b6_pawob", rootpath, srDTO
					.getOutputtype(), request, 777);
		} catch (Exception e) {
			logger.error("unable to create pawob report: " + e.getMessage());
			e.printStackTrace();
			return null;
		} finally {
			if (sess != null) {
				sess.close();
			}
		}
	
	}

}
