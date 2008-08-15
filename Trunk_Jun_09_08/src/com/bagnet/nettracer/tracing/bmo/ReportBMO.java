/*
 * Created on Jul 15, 2004
 *
 * Administrator
 */
package com.bagnet.nettracer.tracing.bmo;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.fill.JRFileVirtualizer;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.log4j.Logger;
import org.apache.struts.util.MessageResources;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.reporting.ReportingConstants;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Incident_Claimcheck;
import com.bagnet.nettracer.tracing.db.ItemType;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.dto.SearchIncidentReportDTO;
import com.bagnet.nettracer.tracing.dto.StatReportDTO;
import com.bagnet.nettracer.tracing.dto.StatReport_3_DTO;
import com.bagnet.nettracer.tracing.dto.StatReport_ExpDTO;
import com.bagnet.nettracer.tracing.dto.StatReport_OHD_DTO;
import com.bagnet.nettracer.tracing.dto.StatReport_RecoveryDTO;
import com.bagnet.nettracer.tracing.dto.StatReport_TopFlightDTO;
import com.bagnet.nettracer.tracing.forms.SearchIncidentForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.BagService;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.IncidentUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.reporting.JRIncidentDataSource;
import com.bagnet.nettracer.tracing.utils.reporting.JROnhandDataSource;

/**
 * @author Administrator
 * 
 * create date - Jul 15, 2004
 */


public class ReportBMO {
	private static Logger logger = Logger.getLogger(ReportBMO.class);
	private static String rootpath;
	private Agent user;
	private HttpServletRequest req;
	private String errormsg = null;

	public ReportBMO(HttpServletRequest request) {
		this.req = request;
	}

	public String createReport(String rootpath, StatReportDTO srDTO, Agent user) {
		ReportBMO.rootpath = rootpath;
		this.user = user;
		try {
			MessageResources messages = MessageResources.getMessageResources("com.bagnet.nettracer.tracing.resources.ApplicationResources");

			switch (srDTO.getReportnum()) {
			case ReportingConstants.RPT_3:
				return create_mbr_rpt(srDTO, ReportingConstants.RPT_3, ReportingConstants.RPT_3_NAME, messages.getMessage(
						new Locale(user.getCurrentlocale()), "header.reportnum.3"));
			case ReportingConstants.RPT_4:
				return create_passboarding_rpt(srDTO, ReportingConstants.RPT_4, ReportingConstants.RPT_4_NAME, messages.getMessage(new Locale(user
						.getCurrentlocale()), "header.reportnum.4"));
			case ReportingConstants.RPT_5:
				return create_flt_rpt(srDTO, ReportingConstants.RPT_5, ReportingConstants.RPT_5_NAME, messages.getMessage(
						new Locale(user.getCurrentlocale()), "header.reportnum.5"));
			case ReportingConstants.RPT_6:
				return create_exp_rpt(srDTO, ReportingConstants.RPT_6, ReportingConstants.RPT_6_NAME, messages.getMessage(
						new Locale(user.getCurrentlocale()), "header.reportnum.6"));
			case ReportingConstants.RPT_7:
				return create_station_rpt(srDTO, ReportingConstants.RPT_7, ReportingConstants.RPT_7_NAME, messages.getMessage(new Locale(user
						.getCurrentlocale()), "header.reportnum.7"));
			case ReportingConstants.RPT_8:
				if (srDTO.getCstarttime() != null && srDTO.getCstarttime().length() > 0)
					// recovery report with user entered close date to query
					return create_crecovery_rpt(srDTO, ReportingConstants.RPT_9, ReportingConstants.RPT_9_NAME, messages.getMessage(new Locale(user
							.getCurrentlocale()), "header.reportnum.9"));
				else
					// recovery report with user didnt' enter close date
					return create_recovery_rpt(srDTO, ReportingConstants.RPT_8, ReportingConstants.RPT_8_NAME, messages.getMessage(new Locale(user
							.getCurrentlocale()), "header.reportnum.8"));
			case ReportingConstants.RPT_10:
				return create_onhand_rpt(srDTO, ReportingConstants.RPT_10, ReportingConstants.RPT_10_NAME, messages.getMessage(new Locale(user
						.getCurrentlocale()), "header.reportnum.10"));
			case ReportingConstants.RPT_20:
				CustomReportBMO cbmo = new CustomReportBMO(req, user, rootpath);
				switch (srDTO.getCustomreportnum()) {
				case ReportingConstants.RPT_20_CUSTOM_1:
					String creportdata = cbmo.create_custom_report_1(srDTO, ReportingConstants.RPT_20_CUSTOM_1, ReportingConstants.RPT_20_CUSTOM_1_NAME,
							messages.getMessage(new Locale(user.getCurrentlocale()), "header.reportnum.20"));
					if (creportdata == null) {
						setErrormsg(cbmo.getErrormsg());
						return null;
					} else {
						return creportdata;
					}
				}
			default:
				return null;
			}
		} catch (Exception e) {
			logger.error("hibernate exception: " + e);
			return null;
		}
	}

	
	private String create_mbr_rpt(StatReportDTO srDTO, int reportnum, String reportname, String reporttitle) throws HibernateException {
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			Map parameters = new HashMap();
			parameters.put("title", reporttitle);
			//Criteria cri = sess.createCriteria(Incident.class);

			TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone());



			/*************** use direct jdbc sql statement **************/
			Connection conn = sess.connection();
			Statement stmt = conn.createStatement();
			ResultSet rs = null;
			String sql = "select incident.stationassigned_ID,s1.stationcode as ascode,faultstation_ID,s2.stationcode as fscode, "
					 + "incident.incident_ID,incident.itemtype_ID,incident.loss_code,incident.createdate,incident.createtime,status.description as stdesc,itemtype.description as itdesc, "
					 + "p.firstname,p.lastname,it.legfrom,it.legto,it.flightnum "
					 + "from station s1,station s2,status,itemtype,agent,incident " 
					 + "left outer join passenger p on p.incident_ID = incident.incident_ID "
					 + "left outer join itinerary it on it.incident_ID = incident.incident_ID and it.itinerarytype = 0 "
					 + "where s1.station_ID = incident.stationassigned_ID and s2.station_ID = incident.faultstation_ID "
					 + "and status.status_ID = incident.status_ID and agent.Agent_ID = incident.agent_ID and itemtype.ItemType_ID = incident.itemtype_ID and "
					 + "s1.companycode_ID = '" + user.getStation().getCompany().getCompanyCode_ID() + "' ";
	
			if (srDTO.getItemType_ID() >= 1)
				sql += " and incident.itemtype_ID=" + srDTO.getItemType_ID();

			if (srDTO.getStatus_ID() >= 1)
				sql += " and incident.status_ID=" + srDTO.getStatus_ID();
			
			if (srDTO.getLoss_code() > 0) 
				sql += " and incident.loss_code=" + srDTO.getLoss_code();
			
			if (srDTO.getAgent() != null && srDTO.getAgent().length() > 0) {
				sql += " and agent.username like '" + srDTO.getAgent() + "'";
				parameters.put("agent_username", srDTO.getAgent());
			}

			
			String intc = "";
			if (srDTO.getStation_ID() != null) {
				for (int i = 0; i < srDTO.getStation_ID().length; i++) {
					intc += srDTO.getStation_ID()[i] + ",";
				}
			}

			if (intc.length() > 0 && srDTO.getStation_ID() != null && !srDTO.getStation_ID()[0].equals("0")) {
				intc = intc.substring(0,intc.length() - 1);
				sql += " and incident.stationassigned_ID in (" + intc + ") ";
			}
			
			intc = "";
			if (srDTO.getFaultstation_ID() != null) {
				for (int i = 0; i < srDTO.getFaultstation_ID().length; i++) {
					intc += srDTO.getFaultstation_ID()[i] + ",";
				}
			}

			if (intc.length() > 0 && srDTO.getFaultstation_ID() != null && !srDTO.getFaultstation_ID()[0].equals("0")) {
				intc = intc.substring(0,intc.length() - 1);
				sql += " and incident.faultstation_ID in (" + intc + ") ";
			}

			Date sdate = null, edate = null;
			Date sdate1 = null, edate1 = null; // add one for timezone
			Date stime = null; // time to compare (04:00 if eastern, for example)
			String dateq = "";
			
			ArrayList dateal = null;
			if ((dateal = calculateDateDiff(srDTO,tz,user)) == null) {
				return null;
			} 
			sdate = (Date)dateal.get(0);sdate1 = (Date)dateal.get(1);
			edate = (Date)dateal.get(2);edate1 = (Date)dateal.get(3);
			stime = (Date)dateal.get(4);
			
			parameters.put("sdate", "All Dates");
			if (sdate != null && edate != null) {
				parameters.put("sdate", srDTO.getStarttime());
				if (sdate.equals(edate)) {
					// need to add the timezone diff here
					sql += " and ((incident.createdate = '" 
							+ DateUtils.formatDate(sdate,TracingConstants.getDBDateFormat(HibernateWrapper.getConfig().getProperties()),null,null)  
							+ "' and incident.createtime >= '" 
							+ DateUtils.formatDate(stime,TracingConstants.getDBTimeFormat(HibernateWrapper.getConfig().getProperties()),null,null)
							+ "') or (incident.createdate= '"
							+ DateUtils.formatDate(sdate1,TracingConstants.getDBDateFormat(HibernateWrapper.getConfig().getProperties()),null,null)
							+ "' and incident.createtime <= '"
							+ DateUtils.formatDate(stime,TracingConstants.getDBTimeFormat(HibernateWrapper.getConfig().getProperties()),null,null)
							+ "'))";

				} else {

					// first get the beginning and end dates using date and time, then get
					// dates in between
					sql += " and ((incident.createdate = '" 
						+ DateUtils.formatDate(sdate,TracingConstants.getDBDateFormat(HibernateWrapper.getConfig().getProperties()),null,null)  
						+ "' and incident.createtime >= '" 
						+ DateUtils.formatDate(stime,TracingConstants.getDBTimeFormat(HibernateWrapper.getConfig().getProperties()),null,null)
						+ "') or (incident.createdate= '"
						+ DateUtils.formatDate(edate1,TracingConstants.getDBDateFormat(HibernateWrapper.getConfig().getProperties()),null,null)
						+ "' and incident.createtime <= '"
						+ DateUtils.formatDate(stime,TracingConstants.getDBTimeFormat(HibernateWrapper.getConfig().getProperties()),null,null)
						+ "') or (incident.createdate > '"
						+ DateUtils.formatDate(sdate,TracingConstants.getDBDateFormat(HibernateWrapper.getConfig().getProperties()),null,null)
						+ "' and incident.createdate <= '"
						+ DateUtils.formatDate(edate,TracingConstants.getDBDateFormat(HibernateWrapper.getConfig().getProperties()),null,null)
						+ "'))";


					parameters.put("edate", srDTO.getEndtime());
				}
			} else if (sdate != null) {
				parameters.put("sdate", srDTO.getStarttime());
				sql += " and ((incident.createdate = '" 
					+ DateUtils.formatDate(sdate,TracingConstants.getDBDateFormat(HibernateWrapper.getConfig().getProperties()),null,null)  
					+ "' and incident.createtime >= '" 
					+ DateUtils.formatDate(stime,TracingConstants.getDBTimeFormat(HibernateWrapper.getConfig().getProperties()),null,null)
					+ "') or (incident.createdate= '"
					+ DateUtils.formatDate(sdate1,TracingConstants.getDBDateFormat(HibernateWrapper.getConfig().getProperties()),null,null)
					+ "' and incident.createtime <= '"
					+ DateUtils.formatDate(stime,TracingConstants.getDBTimeFormat(HibernateWrapper.getConfig().getProperties()),null,null)
					+ "'))";

				edate = null;
			}

			
			
			sql += " order by incident.stationassigned_ID,incident.itemtype_ID,incident.incident_ID,it.itinerary_ID";
			
			rs = stmt.executeQuery(sql);

			
			//		 put in date and timeformat and timezone of current user

			Object[] o = null, o2 = null, o3 = null;
			StatReport_3_DTO sr = null;
			List list2 = new ArrayList();

			List templist = null;
			StringBuffer tempsb = null;
			int i = 0;
			String lastincid = null;
			String lastpassname = "";
			String lastit = "";
			String temppassname = "",tempit = "";

			while (rs.next()) {
				if (lastincid == null || !(rs.getString("incident_ID").equals(lastincid))) {
					if (lastincid != null) {
						list2.add(sr);
						lastpassname = "";
						lastit = "";
					}
					sr = new StatReport_3_DTO();

					sr.setStation_ID(rs.getInt("stationassigned_ID"));
					sr.setStationcode(rs.getString("ascode"));
					sr.setFaultstationcode(rs.getString("fscode"));
					sr.setIncident_ID(rs.getString("incident_ID"));
					lastincid = sr.getIncident_ID();
					sr.setItemtype_ID(rs.getInt("itemtype_ID"));
					sr.setLoss_code(rs.getInt("loss_code"));
					sr.setCreatedate(rs.getDate("createdate"));
					sr.setCreatetime(rs.getTime("createtime"));
					sr.setStatusdesc(rs.getString("stdesc"));
					sr.setTypedesc(rs.getString("itdesc"));
					
					// passenger
					if (rs.getString("lastname") != null && rs.getString("lastname").length() > 0)
						temppassname += rs.getString("lastname");
					if (rs.getString("firstname") != null && rs.getString("firstname").length() > 0)
						temppassname += ", " + rs.getString("firstname");
					
					if (lastpassname.length() > 0) {
						if (lastpassname.indexOf(temppassname) < 0) lastpassname += "\n" + temppassname;
					} else lastpassname = temppassname;
					temppassname = "";
					
					sr.setCustomer_name(lastpassname);
					
					// itinerary
					if (rs.getString("legfrom") != null && rs.getString("legfrom").length() > 0)
						tempit += rs.getString("legfrom");
					if (rs.getString("flightnum") != null && rs.getString("flightnum").length() > 0)
						tempit += " " + rs.getString("flightnum");
					
					if (lastit.length() > 0) {
						if (lastit.indexOf(tempit) < 0) lastit += "\n" + tempit;
					} else lastit = tempit;
					tempit = "";
					
					sr.setItinerary(lastit);
					
					if (rs.getString("legto") != null && rs.getString("legto").length() > 0)
						sr.setFinal_destination(rs.getString("legto"));
					
					
				} else {
					// equal to last incident, simply attach passenger and leg to
					// passenger
					if (rs.getString("lastname") != null && rs.getString("lastname").length() > 0)
						temppassname += rs.getString("lastname");
					if (rs.getString("firstname") != null && rs.getString("firstname").length() > 0)
						temppassname += ", " + rs.getString("firstname");
					
					if (lastpassname.length() > 0) {
						if (lastpassname.indexOf(temppassname) < 0) lastpassname += "\n" + temppassname;
					} else lastpassname = temppassname;
					temppassname = "";
					
					sr.setCustomer_name(lastpassname);
					
					// itinerary
					if (rs.getString("legfrom") != null && rs.getString("legfrom").length() > 0)
						tempit += rs.getString("legfrom");
					if (rs.getString("flightnum") != null && rs.getString("flightnum").length() > 0)
						tempit += " " + rs.getString("flightnum");
					
					if (lastit.length() > 0) {
						if (lastit.indexOf(tempit) < 0) lastit += "\n" + tempit;
					} else lastit = tempit;
					tempit = "";
					
					sr.setItinerary(lastit);
					
					if (rs.getString("legto") != null && rs.getString("legto").length() > 0)
						sr.setFinal_destination(rs.getString("legto"));
				}
				
				if (sr.getFinal_destination() == null)
					sr.setFinal_destination("");

				sr.set_DATEFORMAT(user.getDateformat().getFormat());
				sr.set_TIMEFORMAT(user.getTimeformat().getFormat());
				sr.set_TIMEZONE(tz);

			}
			
			stmt.close();
			rs.close();
			
			if (sr == null) {
				logger.debug("no data for report");
				return "";
			}
			
			// add the last one 
			if (sr != null) list2.add(sr);

			if (srDTO.getStatus_ID() >= 1) {
				if (sr != null)
					parameters.put("status", sr.getStatusdesc());
			} else {
				parameters.put("status", "All Status");
			}

			// summary or detail; summary = 0; detail = 1
			if (srDTO.getSumordet() == 1) {
				parameters.put("showdetail", "1");
			}

			return getReportFile(list2, parameters, reportname, rootpath, srDTO.getOutputtype());
		} catch (Exception e) {
			logger.error("unable to create report " + e);
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
		}
	}
	


	private String create_passboarding_rpt(StatReportDTO srDTO, int reportnum, String reportname, String reporttitle) throws HibernateException {
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			if (srDTO.getBoarded() <= 0)
				return null;
			Map parameters = new HashMap();
			parameters.put("title", reporttitle);

			TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone());

			// mbr report type
			String mbrtypeq = "";
			if (srDTO.getItemType_ID() >= 1) {
				mbrtypeq = " and incident.itemtype.itemType_ID = :itemType_ID ";
			}
			String stationq = "";
			if (srDTO.getStation_ID() != null && !srDTO.getStation_ID()[0].equals("0")) {
				stationq = " and incident.stationassigned.station_ID in (:station_ID) ";
			}

			String fstationq = "";
			if (srDTO.getFaultstation_ID() != null && !srDTO.getFaultstation_ID()[0].equals("0")) {
				fstationq = " and incident.faultstation.station_ID in (:fstation_ID) ";
			}

			String statusq = "";
			if (srDTO.getStatus_ID() >= 1) {
				statusq = " and incident.status.status_ID = :status_ID ";
			}

			String agentq = "";
			if (srDTO.getAgent() != null && srDTO.getAgent().length() > 0) {
				agentq = " and incident.agent.username like :agent_username ";
				parameters.put("agent_username", srDTO.getAgent());
			}

			String companylimit = "and incident.stationassigned.company.companyCode_ID = :companyCode_ID ";
			
			
			Date sdate = null, edate = null;
			Date sdate1 = null, edate1 = null; // add one for timezone
			Date stime = null; // time to compare (04:00 if eastern, for example)
			String dateq = "";
			
			ArrayList dateal = null;
			if ((dateal = calculateDateDiff(srDTO,tz,user)) == null) {
				return null;
			} 
			sdate = (Date)dateal.get(0);sdate1 = (Date)dateal.get(1);
			edate = (Date)dateal.get(2);edate1 = (Date)dateal.get(3);
			stime = (Date)dateal.get(4);

			long numdays = 0;
			
			parameters.put("sdate", "All Dates");
			if (sdate != null && edate != null) {
				parameters.put("sdate", srDTO.getStarttime());
				if (sdate.equals(edate)) {
					// need to add the timezone diff here
					dateq = " and ((incident.createdate= :startdate and incident.createtime >= :starttime) "
							+ " or (incident.createdate= :startdate1 and incident.createtime <= :starttime))";
					edate = null;
					numdays = 1;
				} else {
					// first get the beginning and end dates using date and time, then get
					// dates in between
					dateq = " and ((incident.createdate= :startdate and incident.createtime >= :starttime) "
							+ " or (incident.createdate= :enddate1 and incident.createtime <= :starttime)"
							+ " or (incident.createdate > :startdate and incident.createdate <= :enddate))";
					parameters.put("edate", srDTO.getEndtime());
					numdays = ((edate.getTime() - sdate.getTime()) / (1000 * 60 * 60 * 24)) + 1;
				}
			} else if (sdate != null) {
				parameters.put("sdate", srDTO.getStarttime());
				dateq = " and ((incident.createdate= :startdate and incident.createtime >= :starttime) "
						+ " or (incident.createdate= :startdate1 and incident.createtime <= :starttime))";
				edate = null;
				numdays = 1;
			}


			String sql = "select count(incident.incident_ID) " + " from com.bagnet.nettracer.tracing.db.Incident incident where 1=1 " + stationq + dateq
					+ statusq + fstationq + agentq + mbrtypeq + companylimit;

			Query q = sess.createQuery(sql);
			if (srDTO.getItemType_ID() >= 1)
				q.setInteger("itemType_ID", srDTO.getItemType_ID());

			if (srDTO.getStatus_ID() >= 1)
				q.setInteger("status_ID", srDTO.getStatus_ID());

			Integer[] intc = null;
			if (srDTO.getStation_ID() != null) {
				intc = new Integer[srDTO.getStation_ID().length];

				for (int i = 0; i < intc.length; i++) {
					intc[i] = new Integer(srDTO.getStation_ID()[i]);
				}
			}

			if (srDTO.getStation_ID() != null && !srDTO.getStation_ID()[0].equals("0")) {
				q.setParameterList("station_ID", intc);
			}

			Integer[] intfc = null;
			if (srDTO.getFaultstation_ID() != null) {
				intfc = new Integer[srDTO.getFaultstation_ID().length];

				for (int i = 0; i < intfc.length; i++) {
					intfc[i] = new Integer(srDTO.getFaultstation_ID()[i]);
				}
			}
			if (srDTO.getFaultstation_ID() != null && !srDTO.getFaultstation_ID()[0].equals("0")) {
				q.setParameterList("fstation_ID", intfc);
			}

			if (srDTO.getLoss_code() > 0) {
				q.setInteger("loss_code", srDTO.getLoss_code());
			}

			if (sdate != null) {
				q.setDate("startdate", sdate);
				if (edate == null) q.setDate("startdate1", sdate1);
				q.setTime("starttime", stime);
			}
			if (edate != null) {
				q.setDate("enddate1", edate1);
				q.setDate("enddate", edate);
			}

			if (srDTO.getAgent() != null && srDTO.getAgent().length() > 0)
				q.setString("agent_username", srDTO.getAgent());

			q.setString("companyCode_ID", user.getStation().getCompany().getCompanyCode_ID());
			List list = q.list();

			if (list.size() == 0) {
				logger.debug("no data for report");
				return "";
			}
			int numpirs = 0;
			numpirs = ((Long) list.get(0)).intValue();

			// populate station, faultstation and status
			if (srDTO.getStation_ID() != null && !srDTO.getStation_ID()[0].equals("0")) {
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < srDTO.getStation_ID().length; i++) {
					sb.append((TracerUtils.getStationcode(Integer.parseInt(srDTO.getStation_ID()[i]))));
					sb.append("  ");
				}
				parameters.put("station", sb.toString());
			}
			if (srDTO.getFaultstation_ID() != null && !srDTO.getFaultstation_ID()[0].equals("0")) {
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < srDTO.getFaultstation_ID().length; i++) {
					sb.append((TracerUtils.getStationcode(Integer.parseInt(srDTO.getFaultstation_ID()[i]))));
					sb.append("  ");
				}
				parameters.put("fault_station", sb.toString());
			}

			if (srDTO.getStatus_ID() >= 1) {
				parameters.put("status", "");
			} else {
				parameters.put("status", "All Status");
			}
			// passenger boarding calculation
			int boarded = srDTO.getBoarded();
			int per = srDTO.getPerpassengers();
			if (boarded <= 0)
				boarded = 1;
			if (per <= 0)
				per = 1;
			parameters.put("numpirs", new Integer(numpirs));
			parameters.put("dailyboarding", new Integer(boarded));
			parameters.put("per", new Integer(per));
			parameters.put("numdays", new Long(numdays));
			double temp = ((double) numpirs) / (boarded);
			double result = temp * per;
			parameters.put("result", new Double(result));

			return getReportFile(list, parameters, reportname, rootpath, srDTO.getOutputtype());
		} catch (Exception e) {
			logger.error("unable to create report " + e);
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
		}
	}

	/**
	 * flight generating PIR report
	 * 
	 * @param srDTO
	 * @param reportnum
	 * @param reportname
	 * @param reporttitle
	 * @return @throws
	 *         HibernateException
	 */
	private String create_flt_rpt(StatReportDTO srDTO, int reportnum, String reportname, String reporttitle) throws HibernateException {
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			Map parameters = new HashMap();
			parameters.put("title", reporttitle);
			
			TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone());

			
			// mbr report type
			String mbrtypeq = "";
			if (srDTO.getItemType_ID() >= 1) {
				mbrtypeq = " and itinerary.incident.itemtype.itemType_ID = :itemType_ID ";
			}
			if (srDTO.getStation_ID() == null || srDTO.getStation_ID()[0].equals("0")) {
				parameters.put("showsubtotal", "show");
			}
			String statusq = "";
			if (srDTO.getStatus_ID() >= 1) {
				statusq = " and itinerary.incident.status.status_ID= :status_ID ";
			}
			String companylimit = " and itinerary.incident.stationassigned.company.companyCode_ID = :companyCode_ID ";

			
			Date sdate = null, edate = null;
			Date sdate1 = null, edate1 = null; // add one for timezone
			Date stime = null; // time to compare (04:00 if eastern, for example)
			String dateq = "";
			
			ArrayList dateal = null;
			if ((dateal = calculateDateDiff(srDTO,tz,user)) == null) {
				return null;
			} 
			sdate = (Date)dateal.get(0);sdate1 = (Date)dateal.get(1);
			edate = (Date)dateal.get(2);edate1 = (Date)dateal.get(3);
			stime = (Date)dateal.get(4);
	
			parameters.put("sdate", "All Dates");
			if (sdate != null && edate != null) {
				parameters.put("sdate", srDTO.getStarttime());
				if (sdate.equals(edate)) {
					// need to add the timezone diff here
					dateq = " and ((itinerary.incident.createdate= :startdate and itinerary.incident.createtime >= :starttime) "
							+ " or (itinerary.incident.createdate= :startdate1 and itinerary.incident.createtime <= :starttime))";

					edate = null;
				} else {
					// first get the beginning and end dates using date and time, then get
					// dates in between
					dateq = " and ((itinerary.incident.createdate= :startdate and itinerary.incident.createtime >= :starttime) "
							+ " or (itinerary.incident.createdate= :enddate1 and itinerary.incident.createtime <= :starttime)"
							+ " or (itinerary.incident.createdate > :startdate and itinerary.incident.createdate <= :enddate))";

					parameters.put("edate", srDTO.getEndtime());
				}
			} else if (sdate != null) {
				parameters.put("sdate", srDTO.getStarttime());
				dateq = " and ((itinerary.incident.createdate= :startdate and itinerary.incident.createtime >= :starttime) "
						+ " or (itinerary.incident.createdate= :startdate1 and itinerary.incident.createtime <= :starttime))";
				edate = null;
			}
			
			// first get all stations that has reports
			String stationq2 = "";
			if (srDTO.getStation_ID() != null && !srDTO.getStation_ID()[0].equals("0")) {
				stationq2 = " and incident.stationassigned.station_ID in (:station_ID) ";
			}
			String sql = "select distinct incident.stationassigned.station_ID from com.bagnet.nettracer.tracing.db.Incident incident "
					+ " where incident.stationassigned.company.companyCode_ID = :companyCode_ID " + stationq2 + " order by incident.stationassigned.station_ID";
			Query q = sess.createQuery(sql);
			if (srDTO.getStation_ID() != null && !srDTO.getStation_ID()[0].equals("0"))
				q.setParameterList("station_ID", srDTO.getStation_ID());
			q.setString("companyCode_ID", user.getStation().getCompany().getCompanyCode_ID());
			List stationlist = q.list();
			if (stationlist.size() == 0) {
				logger.debug("no data for report");
				return "";
			}
			List templist = null;
			List list = new ArrayList();
			Object[] o = null;
			int loop_st_id = 0;
			// loop through all stations and get top flights specified by user
			String stationq = " and itinerary.incident.stationassigned.station_ID = :station_ID ";
			for (int z = 0; z < stationlist.size(); z++) {
				loop_st_id = ((Integer) stationlist.get(z)).intValue();
				sql = "select count(itinerary.incident.incident_ID),itinerary.airline, itinerary.flightnum, itinerary.incident.stationassigned.station_ID,itinerary.incident.stationassigned.stationcode"
						+ " from "
						+ "com.bagnet.nettracer.tracing.db.Itinerary itinerary "
						+ " where itinerary.itinerarytype = 0 and itinerary.flightnum <> '' "
						+ stationq
						+ statusq
						+ dateq
						+ mbrtypeq
						+ companylimit
						+ "group by itinerary.incident.stationassigned.station_ID,itinerary.incident.stationassigned.stationcode,itinerary.airline,itinerary.flightnum, itinerary.incident.createdate "
						+ "order by 1 desc, itinerary.incident.createdate desc ";
				q = sess.createQuery(sql);
				q.setInteger("station_ID", loop_st_id);
				if (srDTO.getItemType_ID() >= 1)
					q.setInteger("itemType_ID", srDTO.getItemType_ID());
				if (srDTO.getStatus_ID() >= 1)
					q.setInteger("status_ID", srDTO.getStatus_ID());
				if (sdate != null) {
					q.setDate("startdate", sdate);
					if (edate == null) q.setDate("startdate1", sdate1);
					q.setTime("starttime", stime);
				}
				if (edate != null) {
					q.setDate("enddate1", edate1);
					q.setDate("enddate", edate);
				}
				q.setString("companyCode_ID", user.getStation().getCompany().getCompanyCode_ID());
				q.setMaxResults(srDTO.getNumtop());
				templist = q.list();
				if (templist.size() == 0) {
					continue;
				}
				// set list to be list of topflight objects
				StatReport_TopFlightDTO tf = null;
				String flightq = "";

				for (int i = 0; i < templist.size(); i++) {
					o = (Object[]) templist.get(i);
					tf = new StatReport_TopFlightDTO();
					tf.setNuminc(((Long) o[0]).intValue());
					tf.setNumbag(((Long) o[0]).intValue());
					tf.setAirline((String) o[1]);
					tf.setFlightnum((String) o[2]);
					flightq += "'" + tf.getFlightnum() + "',";
					Station stt = new Station();
					stt.setStation_ID(((Integer) o[3]).intValue());
					stt.setStationcode((String) o[4]);
					tf.setStation(stt);
					list.add(tf);
				}
				// get a set of flight numbers used by the previous query to limit the
				// item query
				if (flightq.length() > 0) {
					// remove comma at the end
					flightq = flightq.substring(0, flightq.length() - 1);
					// create sql
					flightq = " and itinerary.flightnum in (" + flightq + ")";

				}
				/** ********************* */
				/** ** run query again to get number of bags per itinerary **** */
				/** ********************* */
				sql = "select count(item.item_ID),itinerary.airline, itinerary.flightnum,itinerary.incident.stationassigned.station_ID from "
						+ "com.bagnet.nettracer.tracing.db.Itinerary itinerary, com.bagnet.nettracer.tracing.db.Item item "
						+ "where itinerary.itinerarytype = 0 and item.incident.incident_ID = itinerary.incident.incident_ID " + stationq + statusq + dateq
						+ mbrtypeq + companylimit + flightq
						+ " group by itinerary.incident.stationassigned.station_ID,itinerary.airline,itinerary.flightnum,itinerary.incident.createdate "
						+ "order by 1 desc, itinerary.incident.createdate desc ";
				q = sess.createQuery(sql);
				q.setInteger("station_ID", loop_st_id);
				if (srDTO.getItemType_ID() >= 1)
					q.setInteger("itemType_ID", srDTO.getItemType_ID());
				if (srDTO.getStatus_ID() >= 1)
					q.setInteger("status_ID", srDTO.getStatus_ID());
				if (sdate != null) {
					q.setDate("startdate", sdate);
					if (edate == null) q.setDate("startdate1", sdate1);
					q.setTime("starttime", stime);
				}
				if (edate != null) {
					q.setDate("enddate1", edate1);
					q.setDate("enddate", edate);
				}
				q.setString("companyCode_ID", user.getStation().getCompany().getCompanyCode_ID());
				q.setMaxResults(srDTO.getNumtop());
				templist = q.list();
				// set list to be list of topflight objects
				tf = null;
				o = null;
				for (int i = 0; i < list.size(); i++) {
					for (int j = 0; j < templist.size(); j++) {
						o = (Object[]) templist.get(j);
						tf = (StatReport_TopFlightDTO) list.get(i);
						if (((String) o[1]).equalsIgnoreCase(tf.getAirline()) && ((String) o[2]).equalsIgnoreCase(tf.getFlightnum())
								&& ((Integer) o[3]).intValue() == tf.getStation().getStation_ID()) {
							tf.setNumbag(((Long) o[0]).intValue());
							break;
						}
					}
				}
			}
			// populate status
			if (srDTO.getStatus_ID() >= 1) {
				parameters.put("status", getStatus(srDTO.getStatus_ID()).getDescription());
			} else {
				parameters.put("status", "All Status");
			}

			if (list.size() == 0) {
				logger.debug("no data for report");
				return "";
			}
			parameters.put("numtop", new Integer(srDTO.getNumtop()));
			return getReportFile(list, parameters, reportname, rootpath, srDTO.getOutputtype());
		} catch (Exception e) {
			logger.error("unable to create report " + e);
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
		}
	}

	/**
	 * claim payout report
	 * 
	 * @param srDTO
	 * @param reportnum
	 * @param reportname
	 * @param reporttitle
	 * @return @throws
	 *         HibernateException
	 */
	private String create_exp_rpt(StatReportDTO srDTO, int reportnum, String reportname, String reporttitle) throws HibernateException {
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			Map parameters = new HashMap();
			parameters.put("title", reporttitle);
			
			TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone());

			// mbr report type
			String mbrtypeq = "";
			if (srDTO.getItemType_ID() >= 1) {
				mbrtypeq = " and exp.claim.incident.itemtype.itemType_ID = :itemType_ID ";
			}
			String stationq = "";
			if (srDTO.getStation_ID() != null && !srDTO.getStation_ID()[0].equals("0")) {
				stationq = " and exp.expenselocation.station_ID in (:station_ID) ";
			}

			// fault station and loss code
			String faultq = "";
			if (srDTO.getFaultstation_ID() != null && !srDTO.getFaultstation_ID()[0].equals("0")) {
				faultq = " and fault.station_ID in (:fault_ID) ";
			}
			String losscodeq = "";
			if (srDTO.getLoss_code() > 0) {
				losscodeq = " and exp.claim.incident.loss_code = :loss_code";
			}

			String statusq = "";
			if (srDTO.getStatus_ID() >= 1) {
				statusq = " and exp.claim.incident.status.status_ID= :status_ID ";
			}

			String agentq = "";
			if (srDTO.getAgent() != null && srDTO.getAgent().length() > 0) {
				agentq = " and exp.agent.username like :agent_username ";
			}

			String companylimit = " and exp.claim.incident.stationassigned.company.companyCode_ID = :companyCode_ID ";
			
			Date sdate = null, edate = null;
			Date sdate1 = null, edate1 = null; // add one for timezone
			Date stime = null; // time to compare (04:00 if eastern, for example)
			String dateq = "";
			
			ArrayList dateal = null;
			if ((dateal = calculateDateDiff(srDTO,tz,user)) == null) {
				return null;
			} 
			sdate = (Date)dateal.get(0);sdate1 = (Date)dateal.get(1);
			edate = (Date)dateal.get(2);edate1 = (Date)dateal.get(3);
			stime = (Date)dateal.get(4);
			
			parameters.put("sdate", "All Dates");
			if (sdate != null && edate != null) {
				parameters.put("sdate", srDTO.getStarttime());
				if (sdate.equals(edate)) {
					// need to add the timezone diff here
					dateq = " and ((exp.claim.incident.createdate= :startdate and exp.claim.incident.createtime >= :starttime) "
							+ " or (exp.claim.incident.createdate= :startdate1 and exp.claim.incident.createtime <= :starttime))";

					edate = null;
				} else {

					// first get the beginning and end dates using date and time, then get
					// dates in between
					dateq = " and ((exp.claim.incident.createdate= :startdate and exp.claim.incident.createtime >= :starttime) "
							+ " or (exp.claim.incident.createdate= :enddate1 and exp.claim.incident.createtime <= :starttime)"
							+ " or (exp.claim.incident.createdate > :startdate and exp.claim.incident.createdate <= :enddate))";

					parameters.put("edate", srDTO.getEndtime());
				}
			} else if (sdate != null) {
				parameters.put("sdate", srDTO.getStarttime());
				dateq = " and ((exp.claim.incident.createdate= :startdate and exp.claim.incident.createtime >= :starttime) "
						+ " or (exp.claim.incident.createdate= :startdate1 and exp.claim.incident.createtime <= :starttime))";
				edate = null;
			}


			// draft paid dates
			Date psdate = null, pedate = null;
			if (srDTO.getCstarttime() != null && srDTO.getCstarttime().length() > 0) {
				psdate = DateUtils.convertToDate(srDTO.getCstarttime(), user.getDateformat().getFormat(), null);
				if (psdate == null) // invalid date
					return null;
			}
			if (srDTO.getCendtime() != null && srDTO.getCendtime().length() > 0) {
				pedate = DateUtils.convertToDate(srDTO.getCendtime(), user.getDateformat().getFormat(), null);
				if (pedate == null) // invalid date
					return null;
			}
			parameters.put("psdate", "All Dates");
			if (psdate != null && pedate != null) {
				parameters.put("psdate", srDTO.getCstarttime());
				if (psdate.equals(pedate)) {
					dateq += " and exp.draftpaiddate = :pstartdate ";
					pedate = null;
				} else {
					dateq += " and exp.draftpaiddate between :pstartdate and :penddate ";
					parameters.put("pedate", srDTO.getCendtime());
				}
			} else if (psdate != null) {
				parameters.put("psdate", srDTO.getCstarttime());
				dateq += " and exp.draftpaiddate = :pstartdate ";
				pedate = null;
			}

			String typeq = "";
			if (srDTO.getExpensetype_ID() > 0) {
				typeq = " and exp.expensetype.expensetype_ID = :expensetype_ID ";
			}

			String addspart = "";
			if (srDTO.getStatus_ID() >= 1) {
				addspart = "exp.claim.incident.status.description,";
			}
			String sql = "SELECT sum(exp.checkamt), exp.currency_ID, sum(exp.voucheramt), sum(exp.mileageamt),"
					+ " exp.expensetype.description, exp.expenselocation.station_ID,exp.expenselocation.stationcode,exp.draftpaiddate,"
					+ addspart
					+ " fault.station_ID,fault.stationcode, exp.claim.incident.loss_code,exp.agent.username "
					+ "from com.bagnet.nettracer.tracing.db.ExpensePayout exp left outer join exp.claim.incident.faultstation fault where 1=1 "
					+ mbrtypeq
					+ stationq
					+ faultq
					+ losscodeq
					+ statusq
					+ agentq
					+ dateq
					+ typeq
					+ companylimit
					+ " group by exp.expenselocation.stationcode, exp.expenselocation.station_ID, fault.station_ID,fault.stationcode, exp.expensetype.description,exp.draftpaiddate, "
					+ addspart + " exp.currency_ID, exp.claim.incident.loss_code,exp.agent.username "
					+ " order by exp.expenselocation.stationcode,exp.expensetype.description";
			Query q = sess.createQuery(sql);
			if (srDTO.getItemType_ID() >= 1)
				q.setInteger("itemType_ID", srDTO.getItemType_ID());
			if (srDTO.getStation_ID() != null && !srDTO.getStation_ID()[0].equals("0"))
				q.setParameterList("station_ID", srDTO.getStation_ID());

			if (srDTO.getFaultstation_ID() != null && !srDTO.getFaultstation_ID()[0].equals("0"))
				q.setParameterList("fault_ID", srDTO.getFaultstation_ID());
			if (srDTO.getLoss_code() > 0)
				q.setInteger("loss_code", srDTO.getLoss_code());

			if (srDTO.getStatus_ID() >= 1)
				q.setInteger("status_ID", srDTO.getStatus_ID());

			if (srDTO.getAgent() != null && srDTO.getAgent().length() > 0) {
				q.setString("agent_username", srDTO.getAgent());
			}

			if (sdate != null) {
				q.setDate("startdate", sdate);
				if (edate == null) q.setDate("startdate1", sdate1);
				q.setTime("starttime", stime);
			}
			if (edate != null) {
				q.setDate("enddate1", edate1);
				q.setDate("enddate", edate);
			}
			
			if (psdate != null)
				q.setDate("pstartdate", psdate);
			if (pedate != null)
				q.setDate("penddate", pedate);

			if (srDTO.getExpensetype_ID() > 0)
				q.setInteger("expensetype_ID", srDTO.getExpensetype_ID());
			q.setString("companyCode_ID", user.getStation().getCompany().getCompanyCode_ID());
			List templist = q.list();
			List list = new ArrayList();
			if (templist.size() == 0) {
				logger.debug("no data for report");
				return "";
			}
			// set list to be list of topflight objects
			Object[] o = null;
			StatReport_ExpDTO exp = null;
			for (int i = 0; i < templist.size(); i++) {
				o = (Object[]) templist.get(i);
				exp = new StatReport_ExpDTO();
				exp.setCheckamt(((Double) o[0]).doubleValue());
				exp.setCurrency_ID((String) o[1]);
				exp.setVoucheramt(((Double) o[2]).doubleValue());
				exp.setMileageamt(((Long) o[3]).intValue());
				exp.setTypedesc((String) o[4]);

				Station st = new Station();
				st.setStation_ID(((Integer) o[5]).intValue());
				st.setStationcode((String) o[6]);
				exp.setStation(st);

				exp.setDraftpaiddate((java.sql.Date) o[7]);

				int j = 8;
				if (srDTO.getStatus_ID() >= 1) {
					Status st1 = new Status();
					st1.setDescription((String) o[7]);
					exp.setStatus(st1);
					j = 9;
				}

				Station st2 = new Station();
				if (o[j] != null) {
					st2.setStation_ID(((Integer) o[j]).intValue());
					st2.setStationcode((String) o[j + 1]);
				}
				j++;
				exp.setFaultstation(st2);

				exp.setLoss_code(((Integer) o[++j]).intValue());
				exp.setAgent_username((String) o[++j]);
				list.add(exp);
			}
			// populate station and status
			exp = (StatReport_ExpDTO) list.get(0);
			if (srDTO.getStation_ID() == null || srDTO.getStation_ID()[0].equals("0")) {
				parameters.put("showsubtotal", "show");
			}
			if (srDTO.getStatus_ID() >= 1) {
				parameters.put("status", exp.getStatus().getDescription());
			} else {
				parameters.put("status", "All Status");
			}
			return getReportFile(list, parameters, reportname, rootpath, srDTO.getOutputtype());
		} catch (Exception e) {
			logger.error("unable to create report " + e);
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
		}
	}

	/**
	 * mbrs generated by different stations
	 * 
	 * @param srDTO
	 * @param reportnum
	 * @param reportname
	 * @param reporttitle
	 * @return @throws
	 *         HibernateException
	 */
	private String create_station2_rpt(StatReportDTO srDTO, int reportnum, String reportname, String reporttitle) throws HibernateException {
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			boolean hasb = false, hasc = false;

			TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone());

			Map parameters = new HashMap();
			parameters.put("title", reporttitle);


			/*************** use direct jdbc sql statement **************/
			Connection conn = sess.connection();
			Statement stmt = conn.createStatement();
			ResultSet rs = null;
			/*
			String sql = "select incident.stationassigned_ID,s1.stationcode as ascode,faultstation_ID,s2.stationcode as fscode, "
				 + "incident.incident_ID,incident.itemtype_ID,incident.loss_code,incident.createdate,incident.createtime,status.description as stdesc,itemtype.description as itdesc, "
				 + "p.firstname,p.lastname,it.legfrom,it.legto,it.flightnum " 
				 + "FROM itinerary itinerary0_ INNER JOIN "
				 + "incident incident ON itinerary0_.incident_ID = incident.Incident_ID AND itinerary0_.incident_ID = incident.Incident_ID AND "
		      itinerary0_.incident_ID = incident.Incident_ID AND itinerary0_.incident_ID = incident.Incident_ID AND 
		      itinerary0_.incident_ID = incident.Incident_ID AND itinerary0_.incident_ID = incident.Incident_ID AND 
		      itinerary0_.incident_ID = incident.Incident_ID INNER JOIN
		      station station4_ ON incident.stationassigned_ID = station4_.Station_ID AND incident.stationassigned_ID = station4_.Station_ID INNER JOIN
		      station station5_ ON incident.faultstation_ID = station5_.Station_ID INNER JOIN
		      status status6_ ON incident.status_ID = status6_.Status_ID INNER JOIN
		      itemtype itemtype7_ ON incident.itemtype_ID = itemtype7_.ItemType_ID INNER JOIN
		      itinerary itinerary1_ ON itinerary0_.incident_ID = itinerary1_.incident_ID INNER JOIN
		      itinerary itinerary2_ ON itinerary0_.incident_ID = itinerary2_.incident_ID
WHERE     (itinerary0_.itinerarytype = 0) AND (itinerary0_.legfrom = ?) AND (itinerary0_.legfrom_type = 1) AND (itinerary1_.legto = ?) AND 
      (itinerary1_.legto_type = 3) AND (itinerary2_.legfrom = ?) AND (itinerary2_.legfrom_type = 2) AND (incident.createdate = ?) AND 
      (incident.createtime >= ?) AND (station4_.companycode_ID = ?) OR
      (itinerary0_.itinerarytype = 0) AND (itinerary0_.legfrom = ?) AND (itinerary0_.legfrom_type = 1) AND (itinerary1_.legto = ?) AND 
      (itinerary1_.legto_type = 3) AND (incident.createdate = ?) AND (incident.createtime >= ?) AND (station4_.companycode_ID = ?) AND 
      (itinerary2_.legto = ?) AND (itinerary2_.legto_type = 2) OR
      (itinerary0_.itinerarytype = 0) AND (itinerary0_.legfrom = ?) AND (itinerary0_.legfrom_type = 1) AND (itinerary1_.legto = ?) AND 
      (itinerary1_.legto_type = 3) AND (itinerary2_.legfrom = ?) AND (itinerary2_.legfrom_type = 2) AND (incident.createdate = ?) AND 
      (incident.createtime <= ?) AND (station4_.companycode_ID = ?) OR
      (itinerary0_.itinerarytype = 0) AND (itinerary0_.legfrom = ?) AND (itinerary0_.legfrom_type = 1) AND (itinerary1_.legto = ?) AND 
      (itinerary1_.legto_type = 3) AND (incident.createdate = ?) AND (incident.createtime <= ?) AND (station4_.companycode_ID = ?) AND 
      (itinerary2_.legto = ?) AND (itinerary2_.legto_type = 2)
ORDER BY incident.itemtype_ID, incident.Incident_ID"
				
				
				
				
				
				"select incident.stationassigned_ID,s1.stationcode as ascode,faultstation_ID,s2.stationcode as fscode, "
					 + "incident.incident_ID,incident.itemtype_ID,incident.loss_code,incident.createdate,incident.createtime,status.description as stdesc,itemtype.description as itdesc, "
					 + "p.firstname,p.lastname,it.legfrom,it.legto,it.flightnum "
					 + "from station s1,station s2,status,itemtype,agent,incident " 
					 + "left outer join passenger p on p.incident_ID = incident.incident_ID "
					 + "left outer join itinerary it on it.incident_ID = incident.incident_ID and it.itinerarytype = 0 "
					 + "where s1.station_ID = incident.stationassigned_ID and s2.station_ID = incident.faultstation_ID "
					 + "and status.status_ID = incident.status_ID and agent.Agent_ID = incident.agent_ID and itemtype.ItemType_ID = incident.itemtype_ID and "
					 + "s1.companycode_ID = '" + user.getStation().getCompany().getCompanyCode_ID() + "' ";
	*/


			String sqlselect = "from com.bagnet.nettracer.tracing.db.Itinerary i_a where 1=1 ";
			String companylimit = " and i_a.incident.stationassigned.company.companyCode_ID = :companyCode_ID ";
			// mbr report type
			String mbrtypeq = "";
			if (srDTO.getItemType_ID() >= 1) {
				mbrtypeq = " and i_a.incident.itemtype.itemType_ID = :itemType_ID ";
			}

			StringBuffer sb = new StringBuffer(512);
			String stationq = "";
			// orignating station
			if (srDTO.getB_stationcode() != null && srDTO.getB_stationcode().length() > 0) {
				sb.append(" and (i_a.legfrom = :b_stationcode and i_a.legfrom_type = ").append(TracingConstants.LEG_B_STATION).append(") ");
				// terminating station
				if (srDTO.getE_stationcode() != null && srDTO.getE_stationcode().length() > 0) {
					sb.append(" and (i_b.legto = :e_stationcode and i_b.legto_type = ").append(TracingConstants.LEG_E_STATION).append(") ");
					hasb = true;
					// orignating station + terminating station + transfer station
					if (srDTO.getT_stationcode() != null && srDTO.getT_stationcode().length() > 0) {
						sb.append(" and ((i_c.legfrom = :t_stationcode and i_c.legfrom_type = ").append(TracingConstants.LEG_T_STATION).append(") ");
						sb.append(" or (i_c.legto = :t_stationcode and i_c.legto_type = ").append(TracingConstants.LEG_T_STATION).append(")) ");
						sqlselect = "from " + "com.bagnet.nettracer.tracing.db.Itinerary i_a, " + "com.bagnet.nettracer.tracing.db.Itinerary i_b, "
								+ "com.bagnet.nettracer.tracing.db.Itinerary i_c " + "where i_a.incident.incident_ID = i_b.incident.incident_ID and"
								+ " i_a.incident.incident_ID = i_c.incident.incident_ID and i_a.itinerarytype = 0 ";
						hasc = true;
					} else {
						// orignating station + terminating station
						sqlselect = "from " + "com.bagnet.nettracer.tracing.db.Itinerary i_a, " + "com.bagnet.nettracer.tracing.db.Itinerary i_b where "
								+ " i_a.incident.incident_ID = i_b.incident.incident_ID and i_a.itinerarytype = 0 ";
					}
				} else {
					// orignating station + transfer station
					if (srDTO.getT_stationcode() != null && srDTO.getT_stationcode().length() > 0) {
						sb.append(" and ((i_b.legfrom = :t_stationcode and i_b.legfrom_type = ").append(TracingConstants.LEG_T_STATION).append(") ");
						sb.append(" or (i_b.legto = :t_stationcode and i_b.legto_type = ").append(TracingConstants.LEG_T_STATION).append(")) ");
						sqlselect = "from " + "com.bagnet.nettracer.tracing.db.Itinerary i_a, " + "com.bagnet.nettracer.tracing.db.Itinerary i_b where "
								+ "  i_a.incident.incident_ID = i_b.incident.incident_ID and i_a.itinerarytype = 0 ";
						hasb = true;
					}
				}
			} else {
				// terminating station
				if (srDTO.getE_stationcode() != null && srDTO.getE_stationcode().length() > 0) {
					sb.append(" and (i_a.legto = :e_stationcode and i_a.legto_type = ").append(TracingConstants.LEG_E_STATION).append(") ");
					// terminating station + transfer station
					if (srDTO.getT_stationcode() != null && srDTO.getT_stationcode().length() > 0) {
						sb.append(" and ((i_b.legfrom = :t_stationcode and i_b.legfrom_type = ").append(TracingConstants.LEG_T_STATION).append(") ");
						sb.append(" or (i_b.legto = :t_stationcode and i_b.legto_type = ").append(TracingConstants.LEG_T_STATION).append(")) ");
						sqlselect = "from " + "com.bagnet.nettracer.tracing.db.Itinerary i_a, " + "com.bagnet.nettracer.tracing.db.Itinerary i_b where "
								+ " i_a.incident.incident_ID = i_b.incident.incident_ID and i_a.itinerarytype = 0 ";
						hasb = true;
					}
				} else {
					// transfer station only
					if (srDTO.getT_stationcode() != null && srDTO.getT_stationcode().length() > 0) {
						sb.append(" and ((i_a.legfrom = :t_stationcode and i_a.legfrom_type = ").append(TracingConstants.LEG_T_STATION).append(") ");
						sb.append(" or (i_a.legto = :t_stationcode and i_a.legto_type = ").append(TracingConstants.LEG_T_STATION).append(")) ");
					}
				}
			}
			stationq = sb.toString();

			// fault station and loss code
			String faultq = "";
			if (srDTO.getFaultstation_ID() != null && !srDTO.getFaultstation_ID()[0].equals("0")) {
				faultq = " and i_a.incident.faultstation.station_ID in (:fault_ID) ";
			}
			String losscodeq = "";
			if (srDTO.getLoss_code() > 0) {
				losscodeq = " and i_a.incident.loss_code = :loss_code";
			}

			String statusq = "";
			if (srDTO.getStatus_ID() >= 1) {
				statusq = " and i_a.incident.status.status_ID= :status_ID ";
			}

			String agentq = "";
			if (srDTO.getAgent() != null && srDTO.getAgent().length() > 0) {
				agentq = " and i_a.incident.agent.username like :agent_username ";
				parameters.put("agent_username", srDTO.getAgent());
			}

			Date sdate = null, edate = null;
			Date sdate1 = null, edate1 = null; // add one for timezone
			Date stime = null; // time to compare (04:00 if eastern, for example)
			String dateq = "";

			ArrayList dateal = null;
			if ((dateal = calculateDateDiff(srDTO,tz,user)) == null) {
				return null;
			} 
			sdate = (Date)dateal.get(0);sdate1 = (Date)dateal.get(1);
			edate = (Date)dateal.get(2);edate1 = (Date)dateal.get(3);
			stime = (Date)dateal.get(4);
			
			parameters.put("sdate", "All Dates");
			if (sdate != null && edate != null) {
				parameters.put("sdate", srDTO.getStarttime());
				if (sdate.equals(edate)) {
					// need to add the timezone diff here
					dateq = " and ((i_a.incident.createdate= :startdate and i_a.incident.createtime >= :starttime) "
							+ " or (i_a.incident.createdate= :startdate1 and i_a.incident.createtime <= :starttime))";

					edate = null;
				} else {

					// first get the beginning and end dates using date and time, then get
					// dates in between
					dateq = " and ((i_a.incident.createdate= :startdate and i_a.incident.createtime >= :starttime) "
							+ " or (i_a.incident.createdate= :enddate1 and i_a.incident.createtime <= :starttime)"
							+ " or (i_a.incident.createdate > :startdate and i_a.incident.createdate <= :enddate))";

					parameters.put("edate", srDTO.getEndtime());
				}
			} else if (sdate != null) {
				parameters.put("sdate", srDTO.getStarttime());
				dateq = " and ((i_a.incident.createdate= :startdate and i_a.incident.createtime >= :starttime) "
						+ " or (i_a.incident.createdate= :startdate1 and i_a.incident.createtime <= :starttime))";
				edate = null;
			}

			// travel date departdate and arrive date
			String traveldateq = "";
			Date ddate = null;
			Date adate = null;
			parameters.put("ddate", "Any Date");
			parameters.put("adate", "Any Date");
			if (srDTO.getDepartdate() != null && srDTO.getDepartdate().length() > 0) {
				ddate = DateUtils.convertToDate(srDTO.getDepartdate(), user.getDateformat().getFormat(), null);
				if (ddate != null) {
					traveldateq = "and (i_a.departdate = :departdate ";
					if (hasb)
						traveldateq += " or i_b.departdate = :departdate ";
					if (hasc)
						traveldateq += " or i_c.departdate = :departdate ";
					traveldateq += ") ";
					parameters.put("ddate", srDTO.getDepartdate());
				}
			}
			if (srDTO.getArrivaldate() != null && srDTO.getArrivaldate().length() > 0) {
				adate = DateUtils.convertToDate(srDTO.getArrivaldate(), user.getDateformat().getFormat(), null);
				if (adate != null) {
					traveldateq += "and (i_a.arrivedate = :arrivedate ";
					if (hasb)
						traveldateq += " or i_b.arrivedate = :arrivedate ";
					if (hasc)
						traveldateq += " or i_c.arrivedate = :arrivedate ";
					traveldateq += ") ";
					parameters.put("adate", srDTO.getArrivaldate());
				}
			}

			String sql = "select distinct i_a.incident.stationassigned.station_ID,i_a.incident.stationassigned.stationcode,"
					+ " i_a.incident.faultstation.station_ID,i_a.incident.faultstation.stationcode,"
					+ " i_a.incident.incident_ID, i_a.incident.itemtype.itemType_ID,i_a.incident.loss_code,"
					+ " i_a.incident.createdate,i_a.incident.createtime,i_a.incident.status.description,i_a.incident.itemtype.description " + sqlselect
					+ mbrtypeq + stationq + faultq + losscodeq + statusq + agentq + dateq + traveldateq + companylimit
					+ " order by i_a.incident.itemtype.itemType_ID, i_a.incident.incident_ID ";
			Query q = sess.createQuery(sql);
			if (srDTO.getItemType_ID() >= 1)
				q.setInteger("itemType_ID", srDTO.getItemType_ID());
			if (srDTO.getStatus_ID() >= 1)
				q.setInteger("status_ID", srDTO.getStatus_ID());
			if (srDTO.getB_stationcode() != null && srDTO.getB_stationcode().length() > 0)
				q.setString("b_stationcode", srDTO.getB_stationcode());
			if (srDTO.getT_stationcode() != null && srDTO.getT_stationcode().length() > 0)
				q.setString("t_stationcode", srDTO.getT_stationcode());
			if (srDTO.getE_stationcode() != null && srDTO.getE_stationcode().length() > 0)
				q.setString("e_stationcode", srDTO.getE_stationcode());

			if (srDTO.getFaultstation_ID() != null && !srDTO.getFaultstation_ID()[0].equals("0"))
				q.setParameterList("fault_ID", srDTO.getFaultstation_ID());
			if (srDTO.getLoss_code() > 0)
				q.setInteger("loss_code", srDTO.getLoss_code());
			if (srDTO.getAgent() != null && srDTO.getAgent().length() > 0)
				q.setString("agent_username", srDTO.getAgent());

			if (sdate != null) {
				q.setDate("startdate", sdate);
				if (edate == null) {
					q.setDate("startdate1", sdate1);
				}
				q.setTime("starttime", stime);

			}
			if (edate != null) {
				q.setDate("enddate1", edate1);
				q.setDate("enddate", edate);
			}

			// traveldate
			if (ddate != null)
				q.setDate("departdate", ddate);
			if (adate != null)
				q.setDate("arrivedate", adate);

			q.setString("companyCode_ID", user.getStation().getCompany().getCompanyCode_ID());
			List list = q.list();
			if (list.size() == 0) {
				logger.debug("no data for report");
				return "";
			}
			// populate station and status
			parameters.put("b_station", srDTO.getB_stationcode());
			parameters.put("t_station", srDTO.getT_stationcode());
			parameters.put("e_station", srDTO.getE_stationcode());

			// summary or detail; summary = 0; detail = 1
			if (srDTO.getSumordet() == 1) {
				parameters.put("showdetail", "1");
			}

			Object[] o = null, o2 = null, o3 = null;
			StatReport_3_DTO sr = null;
			List list2 = new ArrayList();

			List templist = null;
			StringBuffer tempsb = null;
			for (int i = 0; i < list.size(); i++) {
				o = (Object[]) list.get(i);
				sr = new StatReport_3_DTO();

				sr.setStation_ID(((Integer) o[0]).intValue());
				sr.setStationcode((String) o[1]);
				sr.setFaultstationcode((String) o[3]);
				sr.setIncident_ID((String) o[4]);
				sr.setItemtype_ID(((Integer) o[5]).intValue());
				sr.setLoss_code(((Integer) o[6]).intValue());
				sr.setCreatedate((Date) o[7]);
				sr.setCreatetime((Date) o[8]);
				sr.setStatusdesc((String) o[9]);
				sr.setTypedesc((String) o[10]);

				// get passengers
				sql = "select passenger.lastname,passenger.firstname " + " from com.bagnet.nettracer.tracing.db.Passenger passenger where "
						+ " passenger.incident.incident_ID = :incident_id order by " + " passenger.passenger_ID";

				q = sess.createQuery(sql);
				q.setString("incident_id", sr.getIncident_ID());
				templist = q.list();
				tempsb = new StringBuffer();
				for (int j = 0; j < templist.size(); j++) {
					o3 = (Object[]) templist.get(j);
					if ((String) o3[0] != null && ((String) o3[0]).length() > 0) {
						if (j > 0)
							tempsb.append("\n");
						tempsb.append((String) o3[0]);
					}
					if ((String) o3[1] != null && ((String) o3[1]).length() > 0)
						tempsb.append(", " + (String) o3[1]);
				}
				sr.setCustomer_name(tempsb.toString());

				// get itineraries

				//			 get passengers
				sql = "select itinerary.legfrom,itinerary.flightnum,itinerary.legto " + " from com.bagnet.nettracer.tracing.db.Itinerary itinerary where "
						+ " itinerary.incident.incident_ID = :incident_id and itinerary.itinerarytype = 0 order by " + " itinerary.itinerary_ID";

				q = sess.createQuery(sql);
				q.setString("incident_id", sr.getIncident_ID());
				templist = q.list();
				tempsb = new StringBuffer();
				for (int j = 0; j < templist.size(); j++) {
					o3 = (Object[]) templist.get(j);
					if ((String) o3[0] != null && ((String) o3[0]).length() > 0) {
						if (j > 0)
							tempsb.append("\n");
						tempsb.append((String) o3[0]);
					}
					if ((String) o3[1] != null && ((String) o3[1]).length() > 0)
						tempsb.append(" " + (String) o3[1]);
					if ((String) o3[2] != null && ((String) o3[2]).length() > 0)
						sr.setFinal_destination((String) o3[2]);
				}
				sr.setItinerary(tempsb.toString());
				if (sr.getFinal_destination() == null)
					sr.setFinal_destination("");

				sr.set_DATEFORMAT(user.getDateformat().getFormat());
				sr.set_TIMEFORMAT(user.getTimeformat().getFormat());
				sr.set_TIMEZONE(tz);
				list2.add(sr);

			}

			if (srDTO.getStatus_ID() >= 1) {
				if (sr != null)
					parameters.put("status", sr.getStatusdesc());
			} else {
				parameters.put("status", "All Status");
			}

			return getReportFile(list2, parameters, reportname, rootpath, srDTO.getOutputtype());
		} catch (Exception e) {
			logger.error("unable to create report " + e);
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
		}
	}

	
	private String create_station_rpt(StatReportDTO srDTO, int reportnum, String reportname, String reporttitle) throws HibernateException {
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			boolean hasb = false, hasc = false;

			TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone());

			Map parameters = new HashMap();
			parameters.put("title", reporttitle);
			String sqlselect = "from com.bagnet.nettracer.tracing.db.Itinerary i_a where 1=1 ";
			String companylimit = " and i_a.incident.stationassigned.company.companyCode_ID = :companyCode_ID ";
			// mbr report type
			String mbrtypeq = "";
			if (srDTO.getItemType_ID() >= 1) {
				mbrtypeq = " and i_a.incident.itemtype.itemType_ID = :itemType_ID ";
			}

			StringBuffer sb = new StringBuffer(512);
			String stationq = "";
			// orignating station
			if (srDTO.getB_stationcode() != null && srDTO.getB_stationcode().length() > 0) {
				sb.append(" and (i_a.legfrom = :b_stationcode and i_a.legfrom_type = ").append(TracingConstants.LEG_B_STATION).append(") ");
				// terminating station
				if (srDTO.getE_stationcode() != null && srDTO.getE_stationcode().length() > 0) {
					sb.append(" and (i_b.legto = :e_stationcode and i_b.legto_type = ").append(TracingConstants.LEG_E_STATION).append(") ");
					hasb = true;
					// orignating station + terminating station + transfer station
					if (srDTO.getT_stationcode() != null && srDTO.getT_stationcode().length() > 0) {
						sb.append(" and ((i_c.legfrom = :t_stationcode and i_c.legfrom_type = ").append(TracingConstants.LEG_T_STATION).append(") ");
						sb.append(" or (i_c.legto = :t_stationcode and i_c.legto_type = ").append(TracingConstants.LEG_T_STATION).append(")) ");
						sqlselect = "from " + "com.bagnet.nettracer.tracing.db.Itinerary i_a, " + "com.bagnet.nettracer.tracing.db.Itinerary i_b, "
								+ "com.bagnet.nettracer.tracing.db.Itinerary i_c " + "where i_a.incident.incident_ID = i_b.incident.incident_ID and"
								+ " i_a.incident.incident_ID = i_c.incident.incident_ID and i_a.itinerarytype = 0 ";
						hasc = true;
					} else {
						// orignating station + terminating station
						sqlselect = "from " + "com.bagnet.nettracer.tracing.db.Itinerary i_a, " + "com.bagnet.nettracer.tracing.db.Itinerary i_b where "
								+ " i_a.incident.incident_ID = i_b.incident.incident_ID and i_a.itinerarytype = 0 ";
					}
				} else {
					// orignating station + transfer station
					if (srDTO.getT_stationcode() != null && srDTO.getT_stationcode().length() > 0) {
						sb.append(" and ((i_b.legfrom = :t_stationcode and i_b.legfrom_type = ").append(TracingConstants.LEG_T_STATION).append(") ");
						sb.append(" or (i_b.legto = :t_stationcode and i_b.legto_type = ").append(TracingConstants.LEG_T_STATION).append(")) ");
						sqlselect = "from " + "com.bagnet.nettracer.tracing.db.Itinerary i_a, " + "com.bagnet.nettracer.tracing.db.Itinerary i_b where "
								+ "  i_a.incident.incident_ID = i_b.incident.incident_ID and i_a.itinerarytype = 0 ";
						hasb = true;
					}
				}
			} else {
				// terminating station
				if (srDTO.getE_stationcode() != null && srDTO.getE_stationcode().length() > 0) {
					sb.append(" and (i_a.legto = :e_stationcode and i_a.legto_type = ").append(TracingConstants.LEG_E_STATION).append(") ");
					// terminating station + transfer station
					if (srDTO.getT_stationcode() != null && srDTO.getT_stationcode().length() > 0) {
						sb.append(" and ((i_b.legfrom = :t_stationcode and i_b.legfrom_type = ").append(TracingConstants.LEG_T_STATION).append(") ");
						sb.append(" or (i_b.legto = :t_stationcode and i_b.legto_type = ").append(TracingConstants.LEG_T_STATION).append(")) ");
						sqlselect = "from " + "com.bagnet.nettracer.tracing.db.Itinerary i_a, " + "com.bagnet.nettracer.tracing.db.Itinerary i_b where "
								+ " i_a.incident.incident_ID = i_b.incident.incident_ID and i_a.itinerarytype = 0 ";
						hasb = true;
					}
				} else {
					// transfer station only
					if (srDTO.getT_stationcode() != null && srDTO.getT_stationcode().length() > 0) {
						sb.append(" and ((i_a.legfrom = :t_stationcode and i_a.legfrom_type = ").append(TracingConstants.LEG_T_STATION).append(") ");
						sb.append(" or (i_a.legto = :t_stationcode and i_a.legto_type = ").append(TracingConstants.LEG_T_STATION).append(")) ");
					}
				}
			}
			stationq = sb.toString();

			// fault station and loss code
			String faultq = "";
			if (srDTO.getFaultstation_ID() != null && !srDTO.getFaultstation_ID()[0].equals("0")) {
				faultq = " and i_a.incident.faultstation.station_ID in (:fault_ID) ";
			}
			String losscodeq = "";
			if (srDTO.getLoss_code() > 0) {
				losscodeq = " and i_a.incident.loss_code = :loss_code";
			}

			String statusq = "";
			if (srDTO.getStatus_ID() >= 1) {
				statusq = " and i_a.incident.status.status_ID= :status_ID ";
			}

			String agentq = "";
			if (srDTO.getAgent() != null && srDTO.getAgent().length() > 0) {
				agentq = " and i_a.incident.agent.username like :agent_username ";
				parameters.put("agent_username", srDTO.getAgent());
			}

			Date sdate = null, edate = null;
			Date sdate1 = null, edate1 = null; // add one for timezone
			Date stime = null; // time to compare (04:00 if eastern, for example)
			String dateq = "";

			ArrayList dateal = null;
			if ((dateal = calculateDateDiff(srDTO,tz,user)) == null) {
				return null;
			} 
			sdate = (Date)dateal.get(0);sdate1 = (Date)dateal.get(1);
			edate = (Date)dateal.get(2);edate1 = (Date)dateal.get(3);
			stime = (Date)dateal.get(4);
			
			parameters.put("sdate", "All Dates");
			if (sdate != null && edate != null) {
				parameters.put("sdate", srDTO.getStarttime());
				if (sdate.equals(edate)) {
					// need to add the timezone diff here
					dateq = " and ((i_a.incident.createdate= :startdate and i_a.incident.createtime >= :starttime) "
							+ " or (i_a.incident.createdate= :startdate1 and i_a.incident.createtime <= :starttime))";

					edate = null;
				} else {

					// first get the beginning and end dates using date and time, then get
					// dates in between
					dateq = " and ((i_a.incident.createdate= :startdate and i_a.incident.createtime >= :starttime) "
							+ " or (i_a.incident.createdate= :enddate1 and i_a.incident.createtime <= :starttime)"
							+ " or (i_a.incident.createdate > :startdate and i_a.incident.createdate <= :enddate))";

					parameters.put("edate", srDTO.getEndtime());
				}
			} else if (sdate != null) {
				parameters.put("sdate", srDTO.getStarttime());
				dateq = " and ((i_a.incident.createdate= :startdate and i_a.incident.createtime >= :starttime) "
						+ " or (i_a.incident.createdate= :startdate1 and i_a.incident.createtime <= :starttime))";
				edate = null;
			}

			// travel date departdate and arrive date
			String traveldateq = "";
			Date ddate = null;
			Date adate = null;
			parameters.put("ddate", "Any Date");
			parameters.put("adate", "Any Date");
			if (srDTO.getDepartdate() != null && srDTO.getDepartdate().length() > 0) {
				ddate = DateUtils.convertToDate(srDTO.getDepartdate(), user.getDateformat().getFormat(), null);
				if (ddate != null) {
					traveldateq = "and (i_a.departdate = :departdate ";
					if (hasb)
						traveldateq += " or i_b.departdate = :departdate ";
					if (hasc)
						traveldateq += " or i_c.departdate = :departdate ";
					traveldateq += ") ";
					parameters.put("ddate", srDTO.getDepartdate());
				}
			}
			if (srDTO.getArrivaldate() != null && srDTO.getArrivaldate().length() > 0) {
				adate = DateUtils.convertToDate(srDTO.getArrivaldate(), user.getDateformat().getFormat(), null);
				if (adate != null) {
					traveldateq += "and (i_a.arrivedate = :arrivedate ";
					if (hasb)
						traveldateq += " or i_b.arrivedate = :arrivedate ";
					if (hasc)
						traveldateq += " or i_c.arrivedate = :arrivedate ";
					traveldateq += ") ";
					parameters.put("adate", srDTO.getArrivaldate());
				}
			}

			String sql = "select distinct i_a.incident.stationassigned.station_ID,i_a.incident.stationassigned.stationcode,"
					+ " i_a.incident.faultstation.station_ID,i_a.incident.faultstation.stationcode,"
					+ " i_a.incident.incident_ID, i_a.incident.itemtype.itemType_ID,i_a.incident.loss_code,"
					+ " i_a.incident.createdate,i_a.incident.createtime,i_a.incident.status.description,i_a.incident.itemtype.description " + sqlselect
					+ mbrtypeq + stationq + faultq + losscodeq + statusq + agentq + dateq + traveldateq + companylimit
					+ " order by i_a.incident.itemtype.itemType_ID, i_a.incident.incident_ID ";
			Query q = sess.createQuery(sql);
			if (srDTO.getItemType_ID() >= 1)
				q.setInteger("itemType_ID", srDTO.getItemType_ID());
			if (srDTO.getStatus_ID() >= 1)
				q.setInteger("status_ID", srDTO.getStatus_ID());
			if (srDTO.getB_stationcode() != null && srDTO.getB_stationcode().length() > 0)
				q.setString("b_stationcode", srDTO.getB_stationcode());
			if (srDTO.getT_stationcode() != null && srDTO.getT_stationcode().length() > 0)
				q.setString("t_stationcode", srDTO.getT_stationcode());
			if (srDTO.getE_stationcode() != null && srDTO.getE_stationcode().length() > 0)
				q.setString("e_stationcode", srDTO.getE_stationcode());

			if (srDTO.getFaultstation_ID() != null && !srDTO.getFaultstation_ID()[0].equals("0"))
				q.setParameterList("fault_ID", srDTO.getFaultstation_ID());
			if (srDTO.getLoss_code() > 0)
				q.setInteger("loss_code", srDTO.getLoss_code());
			if (srDTO.getAgent() != null && srDTO.getAgent().length() > 0)
				q.setString("agent_username", srDTO.getAgent());

			if (sdate != null) {
				q.setDate("startdate", sdate);
				if (edate == null) {
					q.setDate("startdate1", sdate1);
				}
				q.setTime("starttime", stime);

			}
			if (edate != null) {
				q.setDate("enddate1", edate1);
				q.setDate("enddate", edate);
			}

			// traveldate
			if (ddate != null)
				q.setDate("departdate", ddate);
			if (adate != null)
				q.setDate("arrivedate", adate);

			q.setString("companyCode_ID", user.getStation().getCompany().getCompanyCode_ID());
			List list = q.list();
			if (list.size() == 0) {
				logger.debug("no data for report");
				return "";
			}
			// populate station and status
			parameters.put("b_station", srDTO.getB_stationcode());
			parameters.put("t_station", srDTO.getT_stationcode());
			parameters.put("e_station", srDTO.getE_stationcode());

			// summary or detail; summary = 0; detail = 1
			if (srDTO.getSumordet() == 1) {
				parameters.put("showdetail", "1");
			}

			Object[] o = null, o2 = null, o3 = null;
			StatReport_3_DTO sr = null;
			List list2 = new ArrayList();

			List templist = null;
			StringBuffer tempsb = null;
			for (int i = 0; i < list.size(); i++) {
				o = (Object[]) list.get(i);
				sr = new StatReport_3_DTO();

				sr.setStation_ID(((Integer) o[0]).intValue());
				sr.setStationcode((String) o[1]);
				sr.setFaultstationcode((String) o[3]);
				sr.setIncident_ID((String) o[4]);
				sr.setItemtype_ID(((Integer) o[5]).intValue());
				sr.setLoss_code(((Integer) o[6]).intValue());
				sr.setCreatedate((Date) o[7]);
				sr.setCreatetime((Date) o[8]);
				sr.setStatusdesc((String) o[9]);
				sr.setTypedesc((String) o[10]);

				// get passengers
				sql = "select passenger.lastname,passenger.firstname " + " from com.bagnet.nettracer.tracing.db.Passenger passenger where "
						+ " passenger.incident.incident_ID = :incident_id order by " + " passenger.passenger_ID";

				q = sess.createQuery(sql);
				q.setString("incident_id", sr.getIncident_ID());
				templist = q.list();
				tempsb = new StringBuffer();
				for (int j = 0; j < templist.size(); j++) {
					o3 = (Object[]) templist.get(j);
					if ((String) o3[0] != null && ((String) o3[0]).length() > 0) {
						if (j > 0)
							tempsb.append("\n");
						tempsb.append((String) o3[0]);
					}
					if ((String) o3[1] != null && ((String) o3[1]).length() > 0)
						tempsb.append(", " + (String) o3[1]);
				}
				sr.setCustomer_name(tempsb.toString());

				// get itineraries

				//			 get passengers
				sql = "select itinerary.legfrom,itinerary.flightnum,itinerary.legto " + " from com.bagnet.nettracer.tracing.db.Itinerary itinerary where "
						+ " itinerary.incident.incident_ID = :incident_id and itinerary.itinerarytype = 0 order by " + " itinerary.itinerary_ID";

				q = sess.createQuery(sql);
				q.setString("incident_id", sr.getIncident_ID());
				templist = q.list();
				tempsb = new StringBuffer();
				for (int j = 0; j < templist.size(); j++) {
					o3 = (Object[]) templist.get(j);
					if ((String) o3[0] != null && ((String) o3[0]).length() > 0) {
						if (j > 0)
							tempsb.append("\n");
						tempsb.append((String) o3[0]);
					}
					if ((String) o3[1] != null && ((String) o3[1]).length() > 0)
						tempsb.append(" " + (String) o3[1]);
					if ((String) o3[2] != null && ((String) o3[2]).length() > 0)
						sr.setFinal_destination((String) o3[2]);
				}
				sr.setItinerary(tempsb.toString());
				if (sr.getFinal_destination() == null)
					sr.setFinal_destination("");

				sr.set_DATEFORMAT(user.getDateformat().getFormat());
				sr.set_TIMEFORMAT(user.getTimeformat().getFormat());
				sr.set_TIMEZONE(tz);
				list2.add(sr);

			}

			if (srDTO.getStatus_ID() >= 1) {
				if (sr != null)
					parameters.put("status", sr.getStatusdesc());
			} else {
				parameters.put("status", "All Status");
			}

			return getReportFile(list2, parameters, reportname, rootpath, srDTO.getOutputtype());
		} catch (Exception e) {
			logger.error("unable to create report " + e);
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
		}
	}

	/**
	 * recovery report
	 * 
	 * @param srDTO
	 * @param reportnum
	 * @param reportname
	 * @param reporttitle
	 * @return @throws
	 *         HibernateException
	 */
	private String create_recovery_rpt(StatReportDTO srDTO, int reportnum, String reportname, String reporttitle) throws HibernateException {
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			Map parameters = new HashMap();
			
			TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone());

			// mbr report type
			String mbrtypeq = "";
			if (srDTO.getItemType_ID() >= 1) {
				mbrtypeq = " and incident.itemtype.itemType_ID = :itemType_ID ";
			}
			String stationq = "";
			if (srDTO.getStation_ID() != null && !srDTO.getStation_ID()[0].equals("0")) {
				stationq = " and incident.stationassigned.station_ID in (:station_ID) ";
			}

			String agentq = "";
			if (srDTO.getAgent() != null && srDTO.getAgent().length() > 0) {
				agentq = " and incident.agent.username like :agent_username ";
				parameters.put("agent_username", srDTO.getAgent());
			}

			String companylimit = "and incident.stationassigned.company.companyCode_ID = :companyCode_ID ";

			Date sdate = null, edate = null;
			Date sdate1 = null, edate1 = null; // add one for timezone
			Date stime = null; // time to compare (04:00 if eastern, for example)
			String dateq = "";
			
			ArrayList dateal = null;
			if ((dateal = calculateDateDiff(srDTO,tz,user)) == null) {
				return null;
			} 
			sdate = (Date)dateal.get(0);sdate1 = (Date)dateal.get(1);
			edate = (Date)dateal.get(2);edate1 = (Date)dateal.get(3);
			stime = (Date)dateal.get(4);
			
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


			String breakdown = "";
			if (srDTO.getSumordet() == 1) {
				parameters.put("showdetail", "1");
				breakdown = " incident.createdate, ";
				parameters.put("title", reporttitle + " (By 24 Hour Periods)");
			} else {
				parameters.put("title", reporttitle);
			}

			// will use this for hibernate 3.0

			//String sql = "select
			// incident.stationcreated,count(incident.incident_ID)," +
			//"count(closeinc.incident_ID),incident.itemtype.itemType_ID " + "from
			// com.bagnet.nettracer.tracing.db.Incident incident left outer join " +
			// "com.bagnet.nettracer.tracing.db.Incident closeinc on
			// incident.incident_ID = closeinc.incident_ID and
			// closeinc.status.status_ID = " + TracingConstants.STATUS_CLOSED + "
			//where 1=1 " + stationq + dateq + mbrtypeq + " group
			// byincident.stationcreated.stationcode " + " order by
			//incident.stationcreated.stationcode";

			String sql = "select " + breakdown + " incident.stationassigned.stationcode,count(incident.incident_ID)"
					+ " from com.bagnet.nettracer.tracing.db.Incident incident where 1=1 " + stationq + dateq + agentq + mbrtypeq + companylimit + " group by "
					+ breakdown + " incident.stationassigned.stationcode " + " order by " + breakdown + " incident.stationassigned.stationcode";

			Query q = sess.createQuery(sql);
			if (srDTO.getItemType_ID() >= 1)
				q.setInteger("itemType_ID", srDTO.getItemType_ID());
			if (srDTO.getStation_ID() != null && !srDTO.getStation_ID()[0].equals("0"))
				q.setParameterList("station_ID", srDTO.getStation_ID());

			if (sdate != null) {
				q.setDate("startdate", sdate);
				if (edate == null) q.setDate("startdate1", sdate1);
				q.setTime("starttime", stime);
			}
			if (edate != null) {
				q.setDate("enddate1", edate1);
				q.setDate("enddate", edate);
			}
			
			if (srDTO.getAgent() != null && srDTO.getAgent().length() > 0)
				q.setString("agent_username", srDTO.getAgent());

			q.setString("companyCode_ID", user.getStation().getCompany().getCompanyCode_ID());
			List alllist = q.list();
			sql = "select " + breakdown + " incident.stationassigned.stationcode,count(incident.incident_ID) "
					+ "from com.bagnet.nettracer.tracing.db.Incident incident where incident.status.status_ID = " + TracingConstants.MBR_STATUS_CLOSED
					+ stationq + agentq + dateq + mbrtypeq + companylimit + " group by " + breakdown + " incident.stationassigned.stationcode " + " order by "
					+ breakdown + " incident.stationassigned.stationcode";
			q = sess.createQuery(sql);
			if (srDTO.getItemType_ID() >= 1)
				q.setInteger("itemType_ID", srDTO.getItemType_ID());
			if (srDTO.getStation_ID() != null && !srDTO.getStation_ID()[0].equals("0"))
				q.setParameterList("station_ID", srDTO.getStation_ID());

			if (sdate != null) {
				q.setDate("startdate", sdate);
				if (edate == null) q.setDate("startdate1", sdate1);
				q.setTime("starttime", stime);
			}
			if (edate != null) {
				q.setDate("enddate1", edate1);
				q.setDate("enddate", edate);
			}
			
			if (srDTO.getAgent() != null && srDTO.getAgent().length() > 0)
				q.setString("agent_username", srDTO.getAgent());

			q.setString("companyCode_ID", user.getStation().getCompany().getCompanyCode_ID());
			List closelist = q.list();
			List list = new ArrayList();
			if (alllist.size() == 0) {
				logger.debug("no data for report");
				return "";
			}
			Object[] o = null, o2 = null;
			StatReport_RecoveryDTO sr = null;
			int num, numclose;
			double percent = 0;
			String reporttype = null;
			int totalnum = 0, totalnumclose = 0;
			for (int i = 0; i < alllist.size(); i++) {
				o = (Object[]) alllist.get(i);
				sr = new StatReport_RecoveryDTO();
				int ss = 0;
				if (srDTO.getSumordet() == 1)
					ss = 1;
				sr.setStationcode(((String) o[ss]));
				num = ((Integer) o[ss + 1]).intValue();
				numclose = 0;
				// find out the corresponding station with number of close count
				for (int j = 0; j < closelist.size(); j++) {
					o2 = (Object[]) closelist.get(j);
					int ss2 = 0;
					if (srDTO.getSumordet() == 1)
						ss2 = 1;

					if (srDTO.getSumordet() == 1) {
						if (sr.getStationcode().equals((String) o2[ss2])
								&& DateUtils.formatDate((Date) o[0], TracingConstants.DB_DATEFORMAT, null, null).equals(
										DateUtils.formatDate((Date) o2[0], TracingConstants.DB_DATEFORMAT, null, null))) {
							numclose = ((Integer) o2[ss2 + 1]).intValue();
							break;

						}
					} else {
						if (sr.getStationcode().equals((String) o2[ss2])) {
							numclose = ((Integer) o2[ss2 + 1]).intValue();
							break;
						}

					}
				}
				totalnum += num;
				totalnumclose += numclose;
				if (num != 0)
					percent = (((double) numclose) / num) * 100;
				sr.setRecoveryratio(percent);
				sr.setReportstaken(num);
				sr.setReportsclosed(numclose);
				if (srDTO.getItemType_ID() >= 1) {
					ItemType it = IncidentUtils.retrieveItemTypeWithId(srDTO.getItemType_ID(), user.getStation().getLocale());
					reporttype = it.getDescription();
				}

				if (srDTO.getSumordet() == 1)
					sr.setCreatedate(DateUtils.formatDate((Date) o[0], TracingConstants.DB_DATEFORMAT, null, tz));

				list.add(sr);
			}
			if (totalnum != 0)
				parameters.put("ratiototal", new Double((((double) totalnumclose) / totalnum) * 100));
			// populate station and status
			sr = (StatReport_RecoveryDTO) list.get(0);
			if (srDTO.getStation_ID() == null || srDTO.getStation_ID()[0].equals("0")) {
				parameters.put("station", "All Stations");
			}

			if (reporttype != null)
				parameters.put("itemtype", reporttype);
			return getReportFile(list, parameters, reportname, rootpath, srDTO.getOutputtype());
		} catch (Exception e) {
			logger.error("unable to create report " + e);
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
		}
	}

	/**
	 * CLOSE REPORT RECOVERY METHOD
	 * 
	 * @param srDTO
	 * @param reportnum
	 * @param reportname
	 * @param reporttitle
	 * @return @throws
	 *         HibernateException
	 */
	private String create_crecovery_rpt(StatReportDTO srDTO, int reportnum, String reportname, String reporttitle) throws HibernateException {
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			Map parameters = new HashMap();
			
			TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone());

			
			// mbr report type
			String typebreakdown = "";
			String mbrtypeq = "";
			if (srDTO.getItemType_ID() >= 1) {
				typebreakdown = ", incident.itemtype.description";
				mbrtypeq = " and incident.itemtype.itemType_ID = :itemType_ID ";
			}
			String stationq = "";
			if (srDTO.getStation_ID() != null && !srDTO.getStation_ID()[0].equals("0")) {
				stationq = " and incident.stationassigned.station_ID in (:station_ID) ";
			}

			String agentq = "";
			if (srDTO.getAgent() != null && srDTO.getAgent().length() > 0) {
				stationq = " and incident.agent.username like :agent_username ";
				parameters.put("agent_username", srDTO.getAgent());
			}

			String companylimit = "and incident.stationassigned.company.companyCode_ID = :companyCode_ID ";

			
			Date sdate = null, edate = null;
			Date sdate1 = null, edate1 = null; // add one for timezone
			Date stime = null; // time to compare (04:00 if eastern, for example)
			String dateq = "";
			
			ArrayList dateal = null;
			if ((dateal = calculateDateDiff(srDTO,tz,user)) == null) {
				return null;
			} 
			sdate = (Date)dateal.get(0);sdate1 = (Date)dateal.get(1);
			edate = (Date)dateal.get(2);edate1 = (Date)dateal.get(3);
			stime = (Date)dateal.get(4);
			
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
			

			// close dates
			Date csdate = null, cedate = null;
			String cdateq = "";
			
			csdate = (Date)dateal.get(5);cedate = (Date)dateal.get(6);

			parameters.put("csdate", "All Dates");
			if (csdate != null) {
				parameters.put("csdate", srDTO.getCstarttime());
				cdateq = " and incident.closedate between :cstartdate and :cenddate ";
				if (cedate != null && !csdate.equals(edate))
					parameters.put("cedate", srDTO.getCendtime());
				else
					cedate = null;
			}

			String breakdown = "", breakdownorder = "";
			if (srDTO.getSumordet() == 1) {
				parameters.put("showdetail", "1");
				breakdown = ", incident.closedate ";
				breakdownorder = "incident.closedate, ";
				parameters.put("title", reporttitle + " (By 24 Hour Period)");
			} else {
				parameters.put("title", reporttitle);
			}

			String sql = "select incident.stationassigned.stationcode,count(incident.incident_ID)" + breakdown + typebreakdown
					+ " from com.bagnet.nettracer.tracing.db.Incident incident where incident.status.status_ID = " + TracingConstants.MBR_STATUS_CLOSED
					+ stationq + agentq + dateq + cdateq + mbrtypeq + companylimit + " group by incident.stationassigned.stationcode" + breakdown
					+ typebreakdown + " order by " + breakdownorder + " incident.stationassigned.stationcode";

			Query q = sess.createQuery(sql);
			if (srDTO.getItemType_ID() >= 1)
				q.setInteger("itemType_ID", srDTO.getItemType_ID());
			if (srDTO.getStation_ID() != null && !srDTO.getStation_ID()[0].equals("0"))
				q.setParameterList("station_ID", srDTO.getStation_ID());

			if (srDTO.getAgent() != null && srDTO.getAgent().length() > 0)
				q.setString("agent_username", srDTO.getAgent());

			if (sdate != null) {
				q.setDate("startdate", sdate);
				if (edate == null) q.setDate("startdate1", sdate1);
				q.setTime("starttime", stime);
			}
			if (edate != null) {
				q.setDate("enddate1", edate1);
				q.setDate("enddate", edate);
			}
			// close date
			if (csdate != null) q.setDate("cstartdate", csdate);
			if (cedate != null) q.setDate("cenddate", cedate);

			q.setString("companyCode_ID", user.getStation().getCompany().getCompanyCode_ID());
			List alllist = q.list();

			if (alllist.size() == 0) {
				logger.debug("no data for report");
				return "";
			}
			
			Object[] o = null, o2 = null;
			StatReport_RecoveryDTO sr = null;
			int num;
			String reporttype = null;
			int totalnum = 0;
			ArrayList list = new ArrayList();
			//String laststationcode = null;
			String lastclosedate = null;
			String temps = null,tempc = null;
			
			TreeMap hm = new TreeMap();
			
			for (int i = 0; i < alllist.size(); i++) {
				o = (Object[]) alllist.get(i);
				temps = (String) o[0];
				num = ((Integer) o[1]).intValue();

				if (breakdown.length() > 0) {
					tempc = DateUtils.formatDate((String) o[2], TracingConstants.DB_DATETIMEFORMAT, TracingConstants.DB_DATEFORMAT, null, tz);
					if (srDTO.getItemType_ID() >= 1)
						reporttype = (String) o[3];
				} else {
					if (srDTO.getItemType_ID() >= 1)
						reporttype = (String) o[2];
				}
				
				if (lastclosedate != null) {
					if (!lastclosedate.equals(tempc)) {
						// new close date, get the hashmap vars and startover
						Set entryset = hm.keySet();
						if (entryset!=null && entryset.size() > 0) {
							// loop through hashmap stations
							for (Iterator ite = entryset.iterator(); ite.hasNext();) {
								String sta = (String) ite.next();
								sr = new StatReport_RecoveryDTO();
								sr.setStationcode(sta);
								if (breakdown.length() > 0) sr.setClosedate(lastclosedate);
								sr.setReportsclosed(((Integer)hm.get(sta)).intValue());
								list.add(sr);
							}
						}
						hm = new TreeMap();
						lastclosedate = tempc;
					}
				}
				
				lastclosedate = tempc;
				// set to hm
				totalnum = num;
				if (hm.containsKey(temps)) totalnum = ((Integer)hm.get(temps)).intValue() + num;
				hm.put(temps,new Integer(totalnum));
			}
			
			// come out the loop, go ahead and set the last sets of list
			Set entryset = hm.keySet();
			if (entryset!=null && entryset.size() > 0) {
				// loop through hashmap stations
				for (Iterator ite = entryset.iterator(); ite.hasNext();) {
					String sta = (String) ite.next();
					sr = new StatReport_RecoveryDTO();
					sr.setStationcode(sta);
					if (breakdown.length() > 0) sr.setClosedate(lastclosedate);
					sr.setReportsclosed(((Integer)hm.get(sta)).intValue());
					list.add(sr);
				}
			}

			// populate station and status
			if (list != null && list.size() > 0) {
				if (srDTO.getStation_ID() == null || srDTO.getStation_ID()[0].equals("0")) {
					parameters.put("station", "All Stations");
				}
			}

			if (reporttype != null)
				parameters.put("itemtype", reporttype);
			return getReportFile(list, parameters, reportname, rootpath, srDTO.getOutputtype());
		} catch (Exception e) {
			logger.error("unable to create report " + e);
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
		}
	}

	private String create_onhand_rpt(StatReportDTO srDTO, int reportnum, String reportname, String reporttitle) throws HibernateException {
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			Map parameters = new HashMap();
			parameters.put("title", reporttitle);
			//Criteria cri = sess.createCriteria(Incident.class);

			TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone());



			/*************** use direct jdbc sql statement **************/
			Connection conn = sess.connection();
			Statement stmt = conn.createStatement();
			ResultSet rs = null;
			String sql = "select ohd.found_station_ID,s1.stationcode as fscode,holding_station_ID,s2.stationcode as hscode, "
					 + "ohd.OHD_ID,ohd.founddate,ohd.foundtime,status.description as stdesc,"
					 + "p.firstname,p.lastname,it.legfrom,it.legto,it.flightnum "
					 + "from station s1,station s2,status,agent,ohd " 
					 + "left outer join ohd_passenger p on p.OHD_ID = ohd.OHD_ID "
					 + "left outer join ohd_itinerary it on it.OHD_ID = ohd.OHD_ID "
					 + "where s1.station_ID = ohd.found_station_ID and s2.station_ID = ohd.holding_station_ID "
					 + "and status.status_ID = ohd.status_ID and agent.Agent_ID = ohd.agent_ID and "
					 + "s1.companycode_ID = '" + user.getStation().getCompany().getCompanyCode_ID() + "' ";

			if (srDTO.getStatus_ID() >= 1)
				sql += " and ohd.status_ID=" + srDTO.getStatus_ID();
			
			if (srDTO.getAgent() != null && srDTO.getAgent().length() > 0) {
				sql += " and agent.username like '" + srDTO.getAgent() + "'";
				parameters.put("agent_username", srDTO.getAgent());
			}

			
			String intc = "";
			if (srDTO.getStation_ID() != null) {
				for (int i = 0; i < srDTO.getStation_ID().length; i++) {
					intc += srDTO.getStation_ID()[i] + ",";
				}
			}

			if (intc.length() > 0 && srDTO.getStation_ID() != null && !srDTO.getStation_ID()[0].equals("0")) {
				intc = intc.substring(0,intc.length() - 1);
				sql += " and ohd.found_station_ID in (" + intc + ") ";
			}
			
			intc = "";
			if (srDTO.getHoldingstation_ID() != null) {
				for (int i = 0; i < srDTO.getHoldingstation_ID().length; i++) {
					intc += srDTO.getHoldingstation_ID()[i] + ",";
				}
			}

			if (intc.length() > 0 && srDTO.getHoldingstation_ID() != null && !srDTO.getHoldingstation_ID()[0].equals("0")) {
				intc = intc.substring(0,intc.length() - 1);
				sql += " and ohd.holding_station_ID in (" + intc + ") ";
			}

			Date sdate = null, edate = null;
			Date sdate1 = null, edate1 = null; // add one for timezone
			Date stime = null; // time to compare (04:00 if eastern, for example)
			String dateq = "";
			
			ArrayList dateal = null;
			if ((dateal = calculateDateDiff(srDTO,tz,user)) == null) {
				return null;
			} 
			sdate = (Date)dateal.get(0);sdate1 = (Date)dateal.get(1);
			edate = (Date)dateal.get(2);edate1 = (Date)dateal.get(3);
			stime = (Date)dateal.get(4);
			
			parameters.put("sdate", "All Dates");
			if (sdate != null && edate != null) {
				parameters.put("sdate", srDTO.getStarttime());
				if (sdate.equals(edate)) {
					// need to add the timezone diff here
					sql += " and ((ohd.founddate = '" 
							+ DateUtils.formatDate(sdate,TracingConstants.getDBDateFormat(HibernateWrapper.getConfig().getProperties()),null,null)  
							+ "' and ohd.foundtime >= '" 
							+ DateUtils.formatDate(stime,TracingConstants.getDBTimeFormat(HibernateWrapper.getConfig().getProperties()),null,null)
							+ "') or (ohd.founddate= '"
							+ DateUtils.formatDate(sdate1,TracingConstants.getDBDateFormat(HibernateWrapper.getConfig().getProperties()),null,null)
							+ "' and ohd.foundtime <= '"
							+ DateUtils.formatDate(stime,TracingConstants.getDBTimeFormat(HibernateWrapper.getConfig().getProperties()),null,null)
							+ "'))";

				} else {

					// first get the beginning and end dates using date and time, then get
					// dates in between
					sql += " and ((ohd.founddate = '" 
						+ DateUtils.formatDate(sdate,TracingConstants.getDBDateFormat(HibernateWrapper.getConfig().getProperties()),null,null)  
						+ "' and ohd.foundtime >= '" 
						+ DateUtils.formatDate(stime,TracingConstants.getDBTimeFormat(HibernateWrapper.getConfig().getProperties()),null,null)
						+ "') or (ohd.founddate= '"
						+ DateUtils.formatDate(edate1,TracingConstants.getDBDateFormat(HibernateWrapper.getConfig().getProperties()),null,null)
						+ "' and ohd.foundtime <= '"
						+ DateUtils.formatDate(stime,TracingConstants.getDBTimeFormat(HibernateWrapper.getConfig().getProperties()),null,null)
						+ "') or (ohd.founddate > '"
						+ DateUtils.formatDate(sdate,TracingConstants.getDBDateFormat(HibernateWrapper.getConfig().getProperties()),null,null)
						+ "' and ohd.founddate <= '"
						+ DateUtils.formatDate(edate,TracingConstants.getDBDateFormat(HibernateWrapper.getConfig().getProperties()),null,null)
						+ "'))";


					parameters.put("edate", srDTO.getEndtime());
				}
			} else if (sdate != null) {
				parameters.put("sdate", srDTO.getStarttime());
				sql += " and ((ohd.founddate = '" 
					+ DateUtils.formatDate(sdate,TracingConstants.getDBDateFormat(HibernateWrapper.getConfig().getProperties()),null,null)  
					+ "' and ohd.foundtime >= '" 
					+ DateUtils.formatDate(stime,TracingConstants.getDBTimeFormat(HibernateWrapper.getConfig().getProperties()),null,null)
					+ "') or (ohd.founddate= '"
					+ DateUtils.formatDate(sdate1,TracingConstants.getDBDateFormat(HibernateWrapper.getConfig().getProperties()),null,null)
					+ "' and ohd.foundtime <= '"
					+ DateUtils.formatDate(stime,TracingConstants.getDBTimeFormat(HibernateWrapper.getConfig().getProperties()),null,null)
					+ "'))";

				edate = null;
			}

			
			
			sql += " order by ohd.found_station_ID,ohd.OHD_ID,it.itinerary_ID";
			
			rs = stmt.executeQuery(sql);

			
			//		 put in date and timeformat and timezone of current user

			Object[] o = null, o2 = null, o3 = null;
			StatReport_OHD_DTO sr = null;
			List list2 = new ArrayList();

			List templist = null;
			StringBuffer tempsb = null;
			int i = 0;
			String lastohdid = null;
			String lastpassname = "";
			String lastit = "";
			String temppassname = "",tempit = "";

			while (rs.next()) {
				if (lastohdid == null || !(rs.getString("OHD_ID").equals(lastohdid))) {
					if (lastohdid != null) {
						list2.add(sr);
						lastpassname = "";
						lastit = "";
					}
					sr = new StatReport_OHD_DTO();

					sr.setStation_ID(rs.getInt("found_station_ID"));
					sr.setStationcode(rs.getString("fscode"));
					sr.setHoldingstation(rs.getString("hscode"));
					sr.setOHD_ID(rs.getString("OHD_ID"));
					lastohdid = sr.getOHD_ID();
					sr.setFounddate(rs.getDate("founddate"));
					sr.setFoundtime(rs.getTime("foundtime"));
					sr.setStatusdesc(rs.getString("stdesc"));
					
					// passenger
					if (rs.getString("lastname") != null && rs.getString("lastname").length() > 0)
						temppassname += rs.getString("lastname");
					if (rs.getString("firstname") != null && rs.getString("firstname").length() > 0)
						temppassname += ", " + rs.getString("firstname");
					
					if (lastpassname.length() > 0) {
						if (lastpassname.indexOf(temppassname) < 0) lastpassname += "\n" + temppassname;
					} else lastpassname = temppassname;
					temppassname = "";
					
					sr.setCustomer_name(lastpassname);
					
					// itinerary
					if (rs.getString("legfrom") != null && rs.getString("legfrom").length() > 0)
						tempit += rs.getString("legfrom");
					if (rs.getString("flightnum") != null && rs.getString("flightnum").length() > 0)
						tempit += " " + rs.getString("flightnum");
					
					if (lastit.length() > 0) {
						if (lastit.indexOf(tempit) < 0) lastit += "\n" + tempit;
					} else lastit = tempit;
					tempit = "";
					
					sr.setItinerary(lastit);
					
					if (rs.getString("legto") != null && rs.getString("legto").length() > 0)
						sr.setFinal_destination(rs.getString("legto"));
					
					
				} else {
					// equal to last incident, simply attach passenger and leg to
					// passenger
					if (rs.getString("lastname") != null && rs.getString("lastname").length() > 0)
						temppassname += rs.getString("lastname");
					if (rs.getString("firstname") != null && rs.getString("firstname").length() > 0)
						temppassname += ", " + rs.getString("firstname");
					
					if (lastpassname.length() > 0) {
						if (lastpassname.indexOf(temppassname) < 0) lastpassname += "\n" + temppassname;
					} else lastpassname = temppassname;
					temppassname = "";
					
					sr.setCustomer_name(lastpassname);
					
					// itinerary
					if (rs.getString("legfrom") != null && rs.getString("legfrom").length() > 0)
						tempit += rs.getString("legfrom");
					if (rs.getString("flightnum") != null && rs.getString("flightnum").length() > 0)
						tempit += " " + rs.getString("flightnum");
					
					if (lastit.length() > 0) {
						if (lastit.indexOf(tempit) < 0) lastit += "\n" + tempit;
					} else lastit = tempit;
					tempit = "";
					
					sr.setItinerary(lastit);
					
					if (rs.getString("legto") != null && rs.getString("legto").length() > 0)
						sr.setFinal_destination(rs.getString("legto"));
				}
				
				if (sr.getFinal_destination() == null)
					sr.setFinal_destination("");

				sr.set_DATEFORMAT(user.getDateformat().getFormat());
				sr.set_TIMEFORMAT(user.getTimeformat().getFormat());
				sr.set_TIMEZONE(tz);

			}
			
			stmt.close();
			rs.close();
			
			if (sr == null) {
				logger.debug("no data for report");
				return "";
			}
			
			// add the last one 
			if (sr != null) list2.add(sr);

			if (srDTO.getStatus_ID() >= 1) {
				if (sr != null)
					parameters.put("status", sr.getStatusdesc());
			} else {
				parameters.put("status", "All Status");
			}

			// summary or detail; summary = 0; detail = 1
			if (srDTO.getSumordet() == 1) {
				parameters.put("showdetail", "1");
			}

			return getReportFile(list2, parameters, reportname, rootpath, srDTO.getOutputtype());
		} catch (Exception e) {
			logger.error("unable to create report " + e);
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
		}
	}
	
	public Status getStatus(int status_ID) throws HibernateException {
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			Query q = sess.createQuery("from com.bagnet.nettracer.tracing.db.Status status where status_ID= :status_ID");
			q.setInteger("status_ID", status_ID);
			List list = q.list();
			if (list == null || list.size() == 0) {
				logger.debug("unable to find status");
				return null;
			}
			return (Status) list.get(0);
		} catch (Exception e) {
			logger.error("unable to retrieve station from database: " + e);
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
		}
	}

	public static JasperReport getCompiledReport(String reportname, String rootpath) throws Exception {
		/** look for compiled reports, if can't find it, compile xml report * */
		File reportFile = new File(rootpath + "/reports/" + reportname + ".jasper");
		if (!reportFile.exists()) {
			/** used for runtime compilation * */
			System.setProperty(ReportingConstants.CLASS_PATH, rootpath + ReportingConstants.JASPER_JAR + System.getProperty("path.separator") + rootpath
					+ ReportingConstants.IREPORT_JAR + System.getProperty("path.separator") + rootpath + "/WEB-INF/classes/");
			System.setProperty(ReportingConstants.COMPILE, rootpath + "/reports/");
			String xmlpath = rootpath + "/reports/" + reportname + ".jrxml";
			JasperCompileManager.compileReportToFile(xmlpath);
		}
		return (JasperReport) JRLoader.loadObject(reportFile.getPath());
	}

	public String getReportFile(List list, Map parameters, String reportname, String rootpath, int outputtype) throws Exception {
		JasperReport jasperReport = getCompiledReport(reportname, rootpath);
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(list);
		return getReportFile(jasperReport, ds, parameters, reportname, rootpath, outputtype);
	}
		
	
	private String getReportFile(JasperReport jasperReport, JRDataSource ds, Map parameters, String reportname, String rootpath, int outputtype) throws Exception {
		/** look for compiled reports, if can't find it, compile xml report * */
		

		// added virtualizer to reduce memory
		JRFileVirtualizer virtualizer = new JRFileVirtualizer(2, rootpath + "/reports/tmp");
		parameters.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);

		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);

		virtualizer.setReadOnly(true);
		
		String outfile = reportname + "_" + (new SimpleDateFormat("MMddyyyyhhmmss").format(TracerDateTime.getGMTDate()));

		if (outputtype == TracingConstants.REPORT_OUTPUT_HTML)
			outfile += ".html";
		else if (outputtype == TracingConstants.REPORT_OUTPUT_PDF)
			outfile += ".pdf";
		else if (outputtype == TracingConstants.REPORT_OUTPUT_XLS)
			outfile += ".xls";
		else if (outputtype == TracingConstants.REPORT_OUTPUT_CSV)
			outfile += ".csv";
		else if (outputtype == TracingConstants.REPORT_OUTPUT_XML)
			outfile += ".xml";

		String outputpath = rootpath + ReportingConstants.REPORT_TMP_PATH + outfile;
		JRExporter exporter = null;

		if (outputtype == TracingConstants.REPORT_OUTPUT_PDF)
			JasperExportManager.exportReportToPdfFile(jasperPrint, outputpath);
		else if (outputtype == TracingConstants.REPORT_OUTPUT_HTML) {
			exporter = new JRHtmlExporter();

			Map imagesMap = new HashMap();
			req.getSession().setAttribute("IMAGES_MAP", imagesMap);
			exporter.setParameter(JRHtmlExporterParameter.IMAGES_MAP, imagesMap);
			exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "image?image=");

			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputpath);
			//exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
			// Boolean.TRUE);
			exporter.exportReport();
		}

		else if (outputtype == TracingConstants.REPORT_OUTPUT_XLS) {
			exporter = new JRXlsExporter();

			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputpath);
			//exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
			// Boolean.TRUE);
			exporter.exportReport();
		} else if (outputtype == TracingConstants.REPORT_OUTPUT_CSV) {
			exporter = new JRCsvExporter();

			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputpath);
			exporter.exportReport();
		} else if (outputtype == TracingConstants.REPORT_OUTPUT_XML) {
			exporter = new JRXmlExporter();

			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputpath);
			exporter.exportReport();
		}
		
		virtualizer.cleanup();
		
		return outfile;
	}

	/**
	 * @return Returns the errormsg.
	 */
	public String getErrormsg() {
		return errormsg;
	}

	/**
	 * @param errormsg
	 *          The errormsg to set.
	 */
	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}
	
	/**
	 * method to calculate the agent timezone based date/time for comparing dates in db
	 * @param sdate
	 * @param sdate1
	 * @param edate
	 * @param edate1
	 * @param stime
	 * @param srDTO
	 * @param tz
	 * @return
	 */
	public static ArrayList calculateDateDiff(StatReportDTO srDTO, TimeZone tz, Agent u) {
		ArrayList al = new ArrayList();
		Date sdate=null,sdate1=null,edate=null, edate1=null, stime=null,sdate2=null,edate2=null;
		if (srDTO.getStarttime() != null && srDTO.getStarttime().length() > 0) {
			sdate = DateUtils.convertToDate(srDTO.getStarttime(), u.getDateformat().getFormat(), null);
			if (sdate == null) // invalid date
				return null;
			
			if (TracerDateTime.getHourDiff(tz) == 0) {
				sdate1 = sdate;
			} else {

				Calendar c = new GregorianCalendar();
				c.setTime(sdate);
				c.add(Calendar.DAY_OF_MONTH, 1);
				sdate1 = c.getTime();
			}
			stime = DateUtils.convertToDate(Integer.toString(TracerDateTime.getHourDiff(tz)), "H", null);

		}
		if (srDTO.getEndtime() != null && srDTO.getEndtime().length() > 0) {
			edate = DateUtils.convertToDate(srDTO.getEndtime(), u.getDateformat().getFormat(), null);
			if (edate == null) // invalid date
				return null;
			
			if (TracerDateTime.getHourDiff(tz) == 0) {
				edate1 = edate;
			} else {
				Calendar c = new GregorianCalendar();
				c.setTime(edate);
				c.add(Calendar.DAY_OF_MONTH, 1);
				edate1 = c.getTime();
			}
		}
		al.add(sdate);
		al.add(sdate1);
		al.add(edate);
		al.add(edate1);
		al.add(stime);

		
		// do it for close date as well
		if (srDTO.getCstarttime() != null && srDTO.getCstarttime().length() > 0)
			sdate2 = DateUtils.convertToDate(srDTO.getCstarttime() + " 0" + TracerDateTime.getHourDiff(tz) + ":00:00 AM", TracingConstants.DB_DATETIMEFORMAT_MSSQL, null);
		
		if (srDTO.getCendtime() != null && srDTO.getCendtime().length() > 0) {
			edate2 = DateUtils.convertToDate(srDTO.getCendtime() + " 0" + TracerDateTime.getHourDiff(tz) + ":00:00 AM", TracingConstants.DB_DATETIMEFORMAT_MSSQL, null);
			if (TracerDateTime.getHourDiff(tz) > 0) {
				Calendar c = new GregorianCalendar();
				c.setTime(edate2);
				c.add(Calendar.DAY_OF_MONTH, 1);
				edate2 = c.getTime();
			}
		}
		
		al.add(sdate2);
		al.add(edate2);
		return al;
	}

	public static String createSearchIncidentReport(ArrayList incidentArray, HttpServletRequest request, int outputtype, String language, String reportPath) {
		try {
			
			Map parameters = new HashMap();			
			HttpSession session = request.getSession();
			Agent user = (Agent) session.getAttribute("user");

			ResourceBundle myResources = ResourceBundle.getBundle("com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(language));
			parameters.put("REPORT_RESOURCE_BUNDLE", myResources);
			parameters.put("showdetail", "1");
			parameters.put("form", request.getAttribute("searchIncidentForm"));
			IncidentBMO bmo = new IncidentBMO();
			BagService bs = new BagService();
			

			ReportBMO rbmo = new ReportBMO(request);
			JasperReport jasperReport = getCompiledReport(ReportingConstants.SEARCH_INCIDENT_RPT_NAME, reportPath);
			JRDataSource ds = new JRIncidentDataSource(jasperReport, incidentArray, (Agent) request.getSession().getAttribute("user"));
			return rbmo.getReportFile(jasperReport, ds, parameters, ReportingConstants.SEARCH_INCIDENT_RPT_NAME, reportPath, outputtype);

		} catch (Exception e) {
			logger.error("unable to search incident report: " + e);
			e.printStackTrace();
			return null;
		}
	}
	
	public static String createSearchOnhandReport(List onhandArray, HttpServletRequest request, int outputtype, String language, String reportPath) {
		try {
			
			Map parameters = new HashMap();			
			HttpSession session = request.getSession();
			Agent user = (Agent) session.getAttribute("user");

			ResourceBundle myResources = ResourceBundle.getBundle("com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(language));
			parameters.put("REPORT_RESOURCE_BUNDLE", myResources);
			parameters.put("showdetail", "1");
			parameters.put("form", request.getAttribute("searchIncidentForm"));
			
			IncidentBMO bmo = new IncidentBMO();
			BagService bs = new BagService();

			ReportBMO rbmo = new ReportBMO(request);
			JasperReport jasperReport = getCompiledReport(ReportingConstants.SEARCH_ONHAND_RPT_NAME, reportPath);
			JRDataSource ds = new JROnhandDataSource(jasperReport, onhandArray, (Agent) request.getSession().getAttribute("user"));
			return rbmo.getReportFile(jasperReport, ds, parameters, ReportingConstants.SEARCH_ONHAND_RPT_NAME, reportPath, outputtype);

		} catch (Exception e) {
			logger.error("unable to search incident report: " + e);
			e.printStackTrace();
			return null;
		}
	}
	
	
}