/*
 * Created on Jul 15, 2004
 *
 * Administrator
 */
package com.bagnet.clients.us;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.util.MessageResources;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.bagnet.clients.us.reports.LzReportDTO;
import com.bagnet.clients.us.reports.PerformanceReportDTO;
import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.reporting.ReportingConstants;
import com.bagnet.nettracer.tracing.bmo.ReportBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.dto.StatReportDTO;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.LzUtils;
import com.bagnet.nettracer.tracing.utils.StringUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

/**
 * @author Administrator
 * 
 * create date - Jul 15, 2004
 */
public class CustomReportBMO implements
		com.bagnet.nettracer.integrations.reports.CustomReportBMO {
	private static Logger logger = Logger.getLogger(CustomReportBMO.class);
	private static String rootpath;
	private Agent user;

	public String createCustomReport(StatReportDTO srDTO,
			HttpServletRequest request, Agent user, String rootpath) {
		this.rootpath = rootpath;
		switch (srDTO.getCustomreportnum()) {
			case ReportingConstants.RPT_20_CUSTOM_2:
				String creportdata = createLzReport(srDTO, ReportBMO.getCustomReport(2).getResource_key(), request);
				if (creportdata == null) {
					return null;
				} else {
					return creportdata;
				}
				
			case ReportingConstants.RPT_20_CUSTOM_3:
				String creportdata2 = createPerformanceReport(srDTO, ReportBMO.getCustomReport(3).getResource_key(), request);
				if (creportdata2 == null) {
					return null;
				} else {
					return creportdata2;
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

	private String createPerformanceReport(StatReportDTO srDTO,
			String resource_key,HttpServletRequest request) {
		
		MessageResources messages = MessageResources.getMessageResources("com.bagnet.nettracer.tracing.resources.ApplicationResources");
		
		Map parameters = new HashMap();
		parameters.put("title", messages.getMessage(resource_key));
		parameters.put("showdetail", "1");
		
		Agent user = (Agent) request.getSession().getAttribute("user");
		
		Session sess = HibernateWrapper.getDirtySession().openSession();
		try {
			
			List<PerformanceReportDTO> reportList = new ArrayList<PerformanceReportDTO>();

			StringBuffer sql = new StringBuffer();
			sql.append("select innerquery.c agentcount, agent.username, checklist_task.description task, checklist_task_option.description taskOption, count(checklist_task_option.description) optionCounts, incident_checklist.Task_ID, incident_checklist.Option_ID");
			sql.append(" from incident_checklist ");
			sql.append(" join agent on incident_checklist.agent_agent_id = agent.agent_id ");
			sql.append(" join checklist_task on incident_checklist.task_id = checklist_task.task_id");
			sql.append(" join checklist_task_option on incident_checklist.option_id = checklist_task_option.option_id");
			sql.append(" join (select agent_agent_id, COUNT(*) c from incident_checklist where incident_checklist.timestamp >= :startDate and incident_checklist.timestamp < :endDate group by agent_agent_id) innerquery on agent.agent_id = innerquery.agent_agent_id");
			sql.append(" where incident_checklist.timestamp >= :startDate and incident_checklist.timestamp < :endDate ");
			sql.append(" group by agent.username, checklist_task.description, checklist_task_option.description, innerquery.c, incident_checklist.Task_ID, incident_checklist.Option_ID");
			sql.append(" order by agent.username, incident_checklist.task_id, incident_checklist.option_id");
			

			SQLQuery query = sess.createSQLQuery(sql.toString());
							
			Date sdate = null, edate = null;
			Date sdate1 = null, edate1 = null; // add one for timezone
			Date stime = null; // time to compare (04:00 if eastern, for example)
			String dateq = "";

			ArrayList dateal = null;
			
			TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getCurrenttimezone()).getTimezone());
				
			if ((dateal = ReportBMO.calculateDateDiff(srDTO, tz, user)) == null) {
				return null;
			}
			sdate = (Date) dateal.get(0);
			sdate1 = (Date) dateal.get(1);
			edate = (Date) dateal.get(2);
			edate1 = (Date) dateal.get(3);
			stime = (Date) dateal.get(4);

			query.setDate("startDate", sdate1);
			edate1.setTime(edate1.getTime() + 1000*60*60*24);
			query.setDate("endDate", edate1);
			
			List<Object[]> results = query.list();
				
			PerformanceReportDTO prevNameObj = null;
			PerformanceReportDTO prevTaskObj = null;
			for (Object[] obj: results) {
				
				PerformanceReportDTO dto = new PerformanceReportDTO();
				String username = (String) obj[1];
				String task = TracerUtils.getText((String) obj[2], user);
				
				if (prevNameObj== null || !username.equals(prevNameObj.getUsername())) {
					dto.setUsername(username);
					dto.setAgentcount((Integer) obj[0]);
					prevNameObj = dto;
					prevTaskObj = dto;
					dto.setTask(task);
				} else if (prevTaskObj == null || !task.equals(prevTaskObj.getTask())) {
					dto.setTask(task);
					prevTaskObj = dto;
				}
				
				dto.setOptionCounts((Integer) obj[4]);
				dto.setTaskOption(TracerUtils.getText((String) obj[3], user));
				
				reportList.add(dto);
		}
						
			// Create report
			logger.info(reportList.size());
			
			return ReportBMO.getReportFile(reportList, parameters, "ChecklistPerformance", rootpath, srDTO
					.getOutputtype(), request);
		} catch (Exception e) {
			logger.error("Exception occurred trying to print performance report: " + e.getMessage());
			e.printStackTrace();
		} finally {
			sess.close();
		}
		return null;
	
	}
	

	private String createLzReport(StatReportDTO srDTO,
			String resource_key,HttpServletRequest request) {
		
		MessageResources messages = MessageResources.getMessageResources("com.bagnet.nettracer.tracing.resources.ApplicationResources");
		
		Map parameters = new HashMap();
		parameters.put("title", messages.getMessage(resource_key));
		parameters.put("lz", LzUtils.getLz(Integer.parseInt(srDTO.getLz_id())).getStation().getStationcode());
		parameters.put("showdetail", "1");
		Agent user = (Agent) request.getSession().getAttribute("user");
		
		Session sess = HibernateWrapper.getDirtySession().openSession();
		try {
			
			HashMap<String, TimeZone> hm = new HashMap<String, TimeZone>();
			List<Station> stationList = LzUtils.getStationsForLz(Integer.parseInt(srDTO.getLz_id()));
			List<Integer> stationCodeList = new ArrayList<Integer>();
			List<LzReportDTO> reportList = new ArrayList<LzReportDTO>();

			StringBuffer query = new StringBuffer();
			query.append("select incident.incident_ID, incident.agent.username, " +
					"incident.stationassigned.stationcode, incident.stationcreated.stationcode, " +
					"incident.createdate, incident.createtime " +
					"from com.bagnet.nettracer.tracing.db.Incident incident where (");
				
			for (Station station: stationList) {
				stationCodeList.add(station.getStation_ID());
			}
			String stationIdString = StringUtils.joinIntegers(stationCodeList, ", ");
							
			Date sdate = null, edate = null;
			Date sdate1 = null, edate1 = null; // add one for timezone
			Date stime = null; // time to compare (04:00 if eastern, for example)
			String dateq = "";

			ArrayList dateal = null;
			
			TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getCurrenttimezone()).getTimezone());
				
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
				
			query.append(" (incident.stationassigned.station_ID in (" + stationIdString + ") ");
			query.append(" " + dateq + ") ");
			
			query.append(" ) and incident.status.status_ID <> :closedStatus ");
			query.append(" and incident.itemtype.itemType_ID = :itemType ");
			query.append(" order by incident.stationassigned.stationcode, incident.createdate, incident.createtime ");
			
			
			Query q = sess.createQuery(query.toString());
			
			q.setInteger("closedStatus", TracingConstants.MBR_STATUS_CLOSED);
			q.setInteger("itemType", TracingConstants.LOST_DELAY);
			
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
			
			List<Object[]> results = q.list();
				
			for (Object[] obj: results) {
				
				LzReportDTO dto = new LzReportDTO();
				dto.setIncidentId((String) obj[0]);
				dto.setAgent((String) obj[1]);			
				dto.setStationAssigned((String) obj[2]);
				dto.setStationCreated((String) obj[3]);
				String createDate = DateUtils.formatDate((Date) obj[4], TracingConstants.DISPLAY_DATEFORMAT, null, tz);
				String createTime = DateUtils.formatDate((Date) obj[5], TracingConstants.DISPLAY_TIMEFORMAT, null, tz);
				dto.setCreateDate(createDate);
				dto.setCreateTime(createTime);
				reportList.add(dto);
		}
						
			// Create report
			logger.info(reportList.size());
			
			return ReportBMO.getReportFile(reportList, parameters, "lzreport", rootpath, srDTO
					.getOutputtype(), request);
		} catch (Exception e) {
			logger.error("Exception occurred trying to print LZ report: " + e.getMessage());
			e.printStackTrace();
		} finally {
			sess.close();
		}
		return null;
	
	}


	/**
	 * Gets the timezone from the first agent associated with the station and
	 * active or the provided agent if one is not available.
	 * 
	 * @param station
	 * @param agent
	 * @return
	 */
	private TimeZone getTzFromFirstActiveAgentInStation(Station station, Agent agent) {
		String tz = null;
		List<Agent> agents = (List<Agent>)AdminUtils.getAgentsByStation(station.getStation_ID() + "" , "", null, 1, 1);
		if (agents.size() >= 1) {
			tz = agents.get(0).getCurrenttimezone();
		} else {
			tz = agent.getCurrenttimezone();
		}
		return TimeZone.getTimeZone(AdminUtils.getTimeZoneById(tz).getTimezone());
	}

}