package com.bagnet.nettracer.reporting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.reporting.AbstractNtJasperReport;
import com.bagnet.nettracer.tracing.bmo.ReportBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.dto.StatReportDTO;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

public class WHDailyStatusReport extends AbstractNtJasperReport {

	static {
		logger = Logger.getLogger(WHDailyStatusReport.class);
	}
	
	private final int MBR = 0;
	private final int TYPE = 1;
	private final int STATUS = 2;
	private final int NAME = 3;
	private final int AIRLINE = 4;
	private final int FLIGHT = 5;
	private final int ORIGIN = 6;
	private final int DESTINATION = 7;
	private final int ROUTE = 8;
	private final int OPEN_STATION = 9;
	private final int OPEN_USER = 10;
	private final int OPEN_DATE = 11;
	private final int OPEN_TIME = 12;
	private final int CLOSE_STATION = 13;
	private final int CLOSE_USER = 14;
	private final int CLOSE_DATE = 15;
	private final int CLOSE_TIME = 16;
	private final int TOTAL_TIME = 17;
	private final int FAULT_STATION = 18;
	private final int FAULT_CODE = 19;
	private final int FAULT_DESC = 20;

	public WHDailyStatusReport(ResourceBundle resources) {
		super(resources);
	}

	public WHDailyStatusReport(ResourceBundle resources, Agent agent) {
		super(resources, agent);
	}

	@Override
	protected String getMySqlSqlString(StatReportDTO srDTO) {

		TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone());
		
		/*** START ***/
		
		StringBuffer sbCompanyQuery = new StringBuffer("");
		String[] myCompanyList = srDTO.getCompany_ID();
		
		boolean reset2AllStations = true;
		if (myCompanyList != null) {
			sbCompanyQuery.append(" AND s1.companycode_ID IN ");
			
			List<String> myCompanyIdList = Arrays.asList(myCompanyList);
			
			int numOfCompanyInList = myCompanyIdList.size();
			if (numOfCompanyInList == 1) {
				String myCompanyId = myCompanyIdList.get(0);
				if (myCompanyId.equalsIgnoreCase(user.getCompanycode_ID())) {
					reset2AllStations = false;
				}
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
		/*** STOP ***/
		
		String sql = "select i.incident_ID mbr, t.description type, st.description status, CONCAT(CONCAT(p.firstname, ' '),p.lastname) name, " +
				"IFNULL(it.airline,'N/A') airline, IFNULL(it.flightnum,'0') flight, " +
				"IFNULL(it.legfrom,'N/A') origin, IFNULL(it.legto,'N/A') destination,  " +
				"IFNULL(it.theRoute,'N/A') route, s1.stationcode open_station,  " +
				"a1.username open_user, i.createdate open_date, i.createtime open_time,  " +
				"IFNULL(s2.stationcode,'N/A') close_station, IFNULL(a2.username,'N/A') close_user,  " +
				"IFNULL(DATE(i.close_date), 'N/A') close_date, IFNULL(TIME(i.close_date), 'N/A') close_time,  " +
				"IFNULL(DATEDIFF(i.close_date, i.createdate), 'N/A') total,  " +
				"IFNULL(fs.stationcode, 'N/A') fault_station, i.loss_code fault_code, IFNULL(c.description, 'N/A') fault_desc " +
				"from station s1, status st, itemtype t, agent a1, passenger p, incident i  " +
				"left join (SELECT incident_id, route as theRoute, " +
				    "if (route NOT LIKE '/%' AND CHAR_LENGTH(route) > 1, SUBSTR(route, 1, 3), 'No Data') legfrom, " +
				    "if (route NOT LIKE '%/' AND CHAR_LENGTH(route) > 1, SUBSTR(route, -3), 'No Data') legto, airline, flightnum " +
				    "FROM  " +
				    "(SELECT incident_id, GROUP_CONCAT(legs ORDER BY incident_id ASC, itinerary_id ASC) route, airline, flightnum " +
				    "FROM  " +
				    "(SELECT incident_id,itinerary_id,airline, flightnum, concat(legfrom, '/', legto) legs " +
				    "FROM itinerary " +
				    "WHERE itinerarytype = 0 " +
				    "ORDER BY incident_id, itinerary_id ASC) itin1 " +
				    "GROUP BY incident_id) routes " +
				    ") it on (i.incident_ID = it.incident_ID) " +
				"left join station fs on (i.faultstation_ID = fs.Station_ID) " +
				"left join company_irregularity_codes c on (i.loss_code = c.loss_code and c.companycode_ID = 'WH') " +
				"left join (select ai2.audit_incident_id, ai2.incident_ID, ai2.modify_agent_id from audit_incident ai2  " +
			        "where ai2.status_ID = 13 group by ai2.incident_ID order by ai2.modify_time asc) ai on (i.incident_ID = ai.incident_ID) " +
				"left join agent a2 on (ai.modify_agent_id = a2.Agent_ID) " +
				"left join station s2 on (a2.station_ID = s2.station_ID) " +
				"where 1=1 ";

			
			if (srDTO.getItemType_ID() >= 1) {
				sql += " and i.itemtype_ID=" + srDTO.getItemType_ID();
			}
			
			String statusIdList = "";
			if (srDTO.getStatus_id_combo() != null) {
				for (int i = 0; i < srDTO.getStatus_id_combo().length; i++) {
					statusIdList += srDTO.getStatus_id_combo()[i] + ",";
				}
				
				int statusIdListLength = statusIdList.length();
				if (statusIdListLength > 0) {
					if (srDTO.getStatus_id_combo()[0].intValue() >= 1) {
						statusIdList = statusIdList.substring(0,statusIdListLength - 1);
						sql += " and i.status_ID in (" + statusIdList + ") ";
					}
				}
			}
			
			String lossCodeList = "";
			if (srDTO.getLoss_code_combo() != null) {
				for (int i = 0; i < srDTO.getLoss_code_combo().length; i++) {
					lossCodeList += srDTO.getLoss_code_combo()[i] + ",";
				}
				
				int lossCodeListLength = lossCodeList.length();
				if (lossCodeListLength > 0 && srDTO.getLoss_code_combo()[0].intValue() >= 1) {
					lossCodeList = lossCodeList.substring(0,lossCodeListLength - 1);
					sql += " and i.loss_code in (" + lossCodeList + ") ";
				}
			}			
			
			if (srDTO.getAgent() != null && srDTO.getAgent().length() > 0) {
				sql += " and a1.username like '" + srDTO.getAgent() + "'";
			}

			
			String intc = "";
			if (srDTO.getStation_ID() != null) {
				for (int i = 0; i < srDTO.getStation_ID().length; i++) {
					intc += srDTO.getStation_ID()[i] + ",";
				}
			}

			if (intc.length() > 0 && srDTO.getStation_ID() != null && !srDTO.getStation_ID()[0].equals("0")) {
				intc = intc.substring(0,intc.length() - 1);
				sql += " and i.stationcreated_ID in (" + intc + ") ";
			}
			
			intc = "";
			if (!reset2AllStations && srDTO.getFaultstation_ID() != null) {
				for (int i = 0; i < srDTO.getFaultstation_ID().length; i++) {
					intc += srDTO.getFaultstation_ID()[i] + ",";
				}
			}

			if (reset2AllStations) {
				sql += sbCompanyQuery.toString();
			} else if (intc.length() > 0 && srDTO.getFaultstation_ID() != null && !srDTO.getFaultstation_ID()[0].equals("0")) {
				intc = intc.substring(0,intc.length() - 1);
				sql += " and i.faultstation_ID in (" + intc + ") ";
			}

			Date sdate = null, edate = null;
			Date sdate1 = null, edate1 = null; // add one for timezone
			Date stime = null; // time to compare (04:00 if eastern, for example)
			
			ArrayList dateal = null;
			if ((dateal = ReportBMO.calculateDateDiff(srDTO,tz,user)) == null) {
				return null;
			} 
			sdate = (Date)dateal.get(0);sdate1 = (Date)dateal.get(1);
			edate = (Date)dateal.get(2);edate1 = (Date)dateal.get(3);
			stime = (Date)dateal.get(4);
			
			if (sdate != null && edate != null) {
				if (sdate.equals(edate)) {
					// need to add the timezone diff here
					sql += " and ((i.createdate = '" 
							+ DateUtils.formatDate(sdate,TracingConstants.getDBDateFormat(HibernateWrapper.getConfig().getProperties()),null,null)  
							+ "' and i.createtime >= '" 
							+ DateUtils.formatDate(stime,TracingConstants.getDBTimeFormat(HibernateWrapper.getConfig().getProperties()),null,null)
							+ "') or (i.createdate= '"
							+ DateUtils.formatDate(sdate1,TracingConstants.getDBDateFormat(HibernateWrapper.getConfig().getProperties()),null,null)
							+ "' and i.createtime <= '"
							+ DateUtils.formatDate(stime,TracingConstants.getDBTimeFormat(HibernateWrapper.getConfig().getProperties()),null,null)
							+ "'))";

				} else {

					// first get the beginning and end dates using date and time, then get
					// dates in between
					sql += " and ((i.createdate = '" 
						+ DateUtils.formatDate(sdate,TracingConstants.getDBDateFormat(HibernateWrapper.getConfig().getProperties()),null,null)  
						+ "' and i.createtime >= '" 
						+ DateUtils.formatDate(stime,TracingConstants.getDBTimeFormat(HibernateWrapper.getConfig().getProperties()),null,null)
						+ "') or (i.createdate= '"
						+ DateUtils.formatDate(edate1,TracingConstants.getDBDateFormat(HibernateWrapper.getConfig().getProperties()),null,null)
						+ "' and i.createtime <= '"
						+ DateUtils.formatDate(stime,TracingConstants.getDBTimeFormat(HibernateWrapper.getConfig().getProperties()),null,null)
						+ "') or (i.createdate > '"
						+ DateUtils.formatDate(sdate,TracingConstants.getDBDateFormat(HibernateWrapper.getConfig().getProperties()),null,null)
						+ "' and i.createdate <= '"
						+ DateUtils.formatDate(edate,TracingConstants.getDBDateFormat(HibernateWrapper.getConfig().getProperties()),null,null)
						+ "'))";

				}
			} else if (sdate != null) {
				sql += " and ((i.createdate = '" 
					+ DateUtils.formatDate(sdate,TracingConstants.getDBDateFormat(HibernateWrapper.getConfig().getProperties()),null,null)  
					+ "' and i.createtime >= '" 
					+ DateUtils.formatDate(stime,TracingConstants.getDBTimeFormat(HibernateWrapper.getConfig().getProperties()),null,null)
					+ "') or (i.createdate= '"
					+ DateUtils.formatDate(sdate1,TracingConstants.getDBDateFormat(HibernateWrapper.getConfig().getProperties()),null,null)
					+ "' and i.createtime <= '"
					+ DateUtils.formatDate(stime,TracingConstants.getDBTimeFormat(HibernateWrapper.getConfig().getProperties()),null,null)
					+ "'))";

				edate = null;
			}
			sql +=	" and i.itemtype_ID = t.itemType_ID and i.status_ID = st.Status_ID and i.stationcreated_ID = s1.Station_ID " +
				" and i.agent_ID = a1.Agent_ID and i.incident_ID = p.incident_ID " +
				" group by i.incident_ID";
		
			logger.info("SQL for report:\n\n" + sql);
			
		return sql;
	}

	protected void setQueryArgumentsAndScalars(SQLQuery query) {
				
		query.addScalar("mbr", Hibernate.STRING);
		query.addScalar("type", Hibernate.STRING);
		query.addScalar("status", Hibernate.STRING);
		query.addScalar("name", Hibernate.STRING);
		query.addScalar("airline", Hibernate.STRING);
		query.addScalar("flight", Hibernate.INTEGER);
		query.addScalar("origin", Hibernate.STRING);
		query.addScalar("destination", Hibernate.STRING);
		query.addScalar("route", Hibernate.STRING);
		query.addScalar("open_station", Hibernate.STRING);
		query.addScalar("open_user", Hibernate.STRING);
		query.addScalar("open_date", Hibernate.STRING);
		query.addScalar("open_time", Hibernate.STRING);
		query.addScalar("close_station", Hibernate.STRING);
		query.addScalar("close_user", Hibernate.STRING);
		query.addScalar("close_date", Hibernate.STRING);
		query.addScalar("close_time", Hibernate.STRING);
		query.addScalar("total", Hibernate.STRING);
		query.addScalar("fault_station", Hibernate.STRING);
		query.addScalar("fault_code", Hibernate.INTEGER);
		query.addScalar("fault_desc", Hibernate.STRING);
		
	}
	
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected List getRowsFromQuery(List raw) {
		List toReturn = new ArrayList();
		WHDailyStatusReportRow currentRow;
		for (int i = 0; i < raw.size(); ++i) {
			currentRow = new WHDailyStatusReportRow();
			Object[] data = (Object[]) raw.get(i);
			
			currentRow.setMbr((String) data[MBR]);
			currentRow.setType((String) data[TYPE]);
			currentRow.setStatus((String) data[STATUS]);
			currentRow.setName((String) data[NAME]);
			currentRow.setAirline((String) data[AIRLINE]);
			currentRow.setFlight((Integer) data[FLIGHT]);
			currentRow.setOrigin((String) data[ORIGIN]);
			currentRow.setDestination((String) data[DESTINATION]);
			currentRow.setRoute((String) data[ROUTE]);
			currentRow.setOpenStation((String) data[OPEN_STATION]);
			currentRow.setOpenUser((String) data[OPEN_USER]);
			currentRow.setOpenDate((String) data[OPEN_DATE]);
			currentRow.setOpenTime((String) data[OPEN_TIME]);
			currentRow.setCloseStation((String) data[CLOSE_STATION]);
			currentRow.setCloseUser((String) data[CLOSE_USER]);
			currentRow.setCloseDate((String) data[CLOSE_DATE]);
			currentRow.setCloseTime((String) data[CLOSE_TIME]);
			currentRow.setTotal((String) data[TOTAL_TIME]);
			currentRow.setFaultStation((String) data[FAULT_STATION]);
			currentRow.setFaultCode((Integer) data[FAULT_CODE]);
			currentRow.setFaultDesc((String) data[FAULT_DESC]);
			
			toReturn.add(currentRow);
		}
		return toReturn;
	}

	@Override
	protected String getSqlServerSqlString(StatReportDTO srDto) {
		return this.getMySqlSqlString(srDto);
	}
	
}
