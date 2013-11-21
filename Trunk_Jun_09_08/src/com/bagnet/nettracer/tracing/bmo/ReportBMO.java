/*
 * Created on Jul 15, 2004
 *
 * Administrator
 */
package com.bagnet.nettracer.tracing.bmo;

import java.awt.Color;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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

import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JExcelApiExporterParameter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.log4j.Logger;
import org.apache.struts.util.MessageResources;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import aero.nettracer.selfservice.fraud.client.ClaimClientRemote;
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.CustomExpression;
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.builders.GroupBuilder;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.GroupLayout;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;

import com.bagnet.nettracer.datasources.JRFoundItemDataSource;
import com.bagnet.nettracer.datasources.JRIncidentDataSource;
import com.bagnet.nettracer.datasources.JROnhandDataSource;
import com.bagnet.nettracer.exceptions.AmountOfDataOutOfRangeException;
import com.bagnet.nettracer.exceptions.MissingRequiredFieldsException;
import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.other.JRGovernedFileVirtualizer;
import com.bagnet.nettracer.other.JRMaxFilesException;
import com.bagnet.nettracer.reporting.ReplacementBagIssuanceReport;
import com.bagnet.nettracer.reporting.ReportingConstants;
import com.bagnet.nettracer.reporting.RonKitIssuanceReport;
import com.bagnet.nettracer.reporting.SalvageReport;
import com.bagnet.nettracer.reporting.SimpleReportRow;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.dao.SalvageDAO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.ItemType;
import com.bagnet.nettracer.tracing.db.Report;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.db.salvage.Salvage;
import com.bagnet.nettracer.tracing.dto.DisputeResolutionReportDTO;
import com.bagnet.nettracer.tracing.dto.FraudValuationReportDTO;
import com.bagnet.nettracer.tracing.dto.IncomingIncDTO;
import com.bagnet.nettracer.tracing.dto.ScannerDTO;
import com.bagnet.nettracer.tracing.dto.StatReportDTO;
import com.bagnet.nettracer.tracing.dto.StatReport_3_DTO;
import com.bagnet.nettracer.tracing.dto.StatReport_ExpDTO;
import com.bagnet.nettracer.tracing.dto.StatReport_OHD_DTO;
import com.bagnet.nettracer.tracing.dto.StatReport_RecoveryDTO;
import com.bagnet.nettracer.tracing.dto.StatReport_TopFlightDTO;
import com.bagnet.nettracer.tracing.forms.IncidentForm;
import com.bagnet.nettracer.tracing.forms.SearchIncidentForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.BagService;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.IncidentUtils;
import com.bagnet.nettracer.tracing.utils.SpringUtils;
import com.bagnet.nettracer.tracing.utils.StringUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerProperties;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.UserPermissions;
import com.bagnet.nettracer.tracing.utils.ntfs.ConnectionUtil;

/**
 * @author Administrator
 * 
 *         create date - Jul 15, 2004
 */

public class ReportBMO {
	private static final int GROUP_BY_FAULT_STATION = 1;
	private static Logger logger = Logger.getLogger(ReportBMO.class);
	private static String rootpath;
	private Agent user;
	private HttpServletRequest req;
	private String errormsg = null;

	public ReportBMO(HttpServletRequest request) {
		this.req = request;
	}

	public void setUser(Agent user) {
		this.user = user;
	}

	public String createReport(String rootpath, StatReportDTO srDTO, Agent user) {
		ReportBMO.rootpath = rootpath;
		this.user = user;
		try {
			MessageResources messages = MessageResources
					.getMessageResources("com.bagnet.nettracer.tracing.resources.ApplicationResources");

			switch (srDTO.getReportnum()) {
			case ReportingConstants.RPT_3:
				return create_mbr_rpt(srDTO, ReportingConstants.RPT_3,
						ReportingConstants.RPT_3_NAME, messages.getMessage(
								new Locale(user.getCurrentlocale()),
								"header.reportnum.3"));
			case ReportingConstants.RPT_4:
				return create_passboarding_rpt(srDTO, ReportingConstants.RPT_4,
						ReportingConstants.RPT_4_NAME, messages.getMessage(
								new Locale(user.getCurrentlocale()),
								"header.reportnum.4"));
			case ReportingConstants.RPT_5:
				return create_flt_rpt(srDTO, ReportingConstants.RPT_5,
						ReportingConstants.RPT_5_NAME, messages.getMessage(
								new Locale(user.getCurrentlocale()),
								"header.reportnum.5"));
			case ReportingConstants.RPT_6:
				return create_exp_rpt(srDTO, ReportingConstants.RPT_6,
						ReportingConstants.RPT_6_NAME, messages.getMessage(
								new Locale(user.getCurrentlocale()),
								"header.reportnum.6"));
			case ReportingConstants.RPT_7:
				return create_station_rpt(srDTO, ReportingConstants.RPT_7,
						ReportingConstants.RPT_7_NAME, messages.getMessage(
								new Locale(user.getCurrentlocale()),
								"header.reportnum.7"));
			case ReportingConstants.RPT_8:
				if (srDTO.getCstarttime() != null
						&& srDTO.getCstarttime().length() > 0)
					// recovery report with user entered close date to query
					return create_crecovery_rpt(srDTO,
							ReportingConstants.RPT_9,
							ReportingConstants.RPT_9_NAME, messages.getMessage(
									new Locale(user.getCurrentlocale()),
									"header.reportnum.9"));
				else
					// recovery report with user didnt' enter close date
					return create_recovery_rpt(srDTO, ReportingConstants.RPT_8,
							ReportingConstants.RPT_8_NAME, messages.getMessage(
									new Locale(user.getCurrentlocale()),
									"header.reportnum.8"));
			case ReportingConstants.RPT_10:
				return create_onhand_rpt(srDTO, ReportingConstants.RPT_10,
						ReportingConstants.RPT_10_NAME, messages.getMessage(
								new Locale(user.getCurrentlocale()),
								"header.reportnum.10"));
			case ReportingConstants.RPT_20:
				if (srDTO.getCustomreportnum() == ReportingConstants.RPT_20_CUSTOM_95) {
					return createRonKitIssuanceReport(srDTO, ReportBMO
							.getCustomReport(95).getResource_key(), rootpath,
							user);
				} else if (srDTO.getCustomreportnum() == ReportingConstants.RPT_20_CUSTOM_96) {
					return createReplacementBagIssuanceReport(srDTO, ReportBMO
							.getCustomReport(96).getResource_key(), rootpath,
							user);
				} else if (srDTO.getCustomreportnum() == ReportingConstants.RPT_20_CUSTOM_201) {
					return create_fraud_valuation_rpt(srDTO,
							ReportingConstants.RPT_20_CUSTOM_201_NAME,
							"Fraud Valuation Report");
				} else {
					return SpringUtils.getCustomReportBMO().createCustomReport(
							srDTO, req, user, rootpath);
				}
			case ReportingConstants.RPT_INBOUND_EXPEDITE_BAGS:
				return create_onhand_rpt(srDTO,
						ReportingConstants.RPT_INBOUND_EXPEDITE_BAGS,
						ReportingConstants.RPT_INBOUND_EXPEDITE_BAGS_NAME,
						messages.getMessage(
								new Locale(user.getCurrentlocale()),
								"header.reportnum.30"));
			default:
				return null;
			}
		} catch (MissingRequiredFieldsException e) {
			setErrormsg("error.missingRequired");
			return null;
		} catch (AmountOfDataOutOfRangeException e) {
			setErrormsg("error.amount.of.data.exceeds.limit");
			return null;
		} catch (Exception e) {
			logger.error("hibernate exception: " + e, e);
			return null;
		}
	}

	@SuppressWarnings({ "rawtypes" })
	private String createRonKitIssuanceReport(StatReportDTO srDTO,
			String resourceKey, String rootpath, Agent user) {
		srDTO.setDateFormat(user.getDateformat().getFormat());
		srDTO.setCompanyCode(user.getCompanycode_ID());
		String runDate = DateUtils.formatDate(new Date(), user.getDateformat()
				.getFormat(), user.getDefaultlocale(), null);
		ResourceBundle resources = ResourceBundle.getBundle(
				"com.bagnet.nettracer.tracing.resources.ApplicationResources",
				new Locale(user.getCurrentlocale()));
		List reportData = new RonKitIssuanceReport(resources).getData(srDTO);
		if (reportData == null) {
			return null;
		}

		String fileName = ReportingConstants.RPT_20_CUSTOM_95_NAME
				+ "_"
				+ (new SimpleDateFormat(ReportingConstants.DATETIME_FORMAT)
						.format(TracerDateTime.getGMTDate()))
				+ ReportingConstants.EXCEL_FILE_TYPE;
		String outputpath = rootpath + ReportingConstants.REPORT_TMP_PATH
				+ fileName;
		JRGovernedFileVirtualizer virtualizer = new JRGovernedFileVirtualizer(
				100, rootpath + ReportingConstants.REPORT_TMP_PATH, 501);
		virtualizer.setReadOnly(false);

		FastReportBuilder drb = new FastReportBuilder();
		drb.setTitle(resources
				.getString("report.ron.kit.issuance.report.header")
				+ " "
				+ runDate);
		drb.setPrintBackgroundOnOddRows(true);
		drb.setSubtitle(getSubTitle(srDTO, user, resources));

		Style header = new Style();
		header.setHorizontalAlign(HorizontalAlign.CENTER);
		header.setVerticalAlign(VerticalAlign.MIDDLE);
		Style detailStyle = new Style("detail");
		detailStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		detailStyle.setVerticalAlign(VerticalAlign.MIDDLE);

		try {
			drb.addColumn(resources.getString("report.rki.create.date"),
					"createDate", String.class.getName(), 50, detailStyle,
					header);
			drb.addColumn(resources.getString("report.rki.station"),
					"stationCode", String.class.getName(), 50, detailStyle,
					header);
			drb.addColumn(resources.getString("report.rki.incident.id"),
					"incidentId", String.class.getName(), 50, detailStyle,
					header);
			drb.addColumn(resources.getString("report.rki.num.issued"),
					"numIssued", Integer.class.getName(), 50, detailStyle,
					header);

			drb.setIgnorePagination(true);
			drb.setUseFullPageWidth(true);

			DynamicReport report = drb.build();
			exportJasperReport(reportData, report, outputpath);

		} catch (ColumnBuilderException cbe) {
			cbe.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		virtualizer.cleanup();
		return fileName;
	}

	@SuppressWarnings({ "rawtypes" })
	private String createReplacementBagIssuanceReport(StatReportDTO srDTO,
			String resourceKey, String rootpath, Agent user) {
		srDTO.setDateFormat(user.getDateformat().getFormat());
		srDTO.setCompanyCode(user.getCompanycode_ID());
		String runDate = DateUtils.formatDate(new Date(), user.getDateformat()
				.getFormat(), user.getDefaultlocale(), null);
		ResourceBundle resources = ResourceBundle.getBundle(
				"com.bagnet.nettracer.tracing.resources.ApplicationResources",
				new Locale(user.getCurrentlocale()));
		List reportData = new ReplacementBagIssuanceReport(resources)
				.getData(srDTO);
		if (reportData == null) {
			return null;
		}

		String fileName = ReportingConstants.RPT_20_CUSTOM_96_NAME
				+ "_"
				+ (new SimpleDateFormat(ReportingConstants.DATETIME_FORMAT)
						.format(TracerDateTime.getGMTDate()))
				+ ReportingConstants.EXCEL_FILE_TYPE;
		String outputpath = rootpath + ReportingConstants.REPORT_TMP_PATH
				+ fileName;
		JRGovernedFileVirtualizer virtualizer = new JRGovernedFileVirtualizer(
				100, rootpath + ReportingConstants.REPORT_TMP_PATH, 501);
		virtualizer.setReadOnly(false);

		FastReportBuilder drb = new FastReportBuilder();
		drb.setTitle(resources
				.getString("report.replacement.bag.issuance.report.header")
				+ " " + runDate);
		drb.setPrintBackgroundOnOddRows(true);
		drb.setSubtitle(getSubTitle(srDTO, user, resources));

		Style header = new Style();
		header.setHorizontalAlign(HorizontalAlign.CENTER);
		header.setVerticalAlign(VerticalAlign.MIDDLE);
		Style detailStyle = new Style("detail");
		detailStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		detailStyle.setVerticalAlign(VerticalAlign.MIDDLE);

		try {
			drb.addColumn(resources.getString("report.rbi.create.date"),
					"createDate", String.class.getName(), 50, detailStyle,
					header);
			drb.addColumn(resources.getString("report.rbi.station"),
					"stationCode", String.class.getName(), 50, detailStyle,
					header);
			drb.addColumn(resources.getString("report.rbi.incident.id"),
					"incidentId", String.class.getName(), 50, detailStyle,
					header);
			drb.addColumn(resources.getString("report.rbi.num.affected"),
					"numBagsAffected", Integer.class.getName(), 50,
					detailStyle, header);
			drb.addColumn(resources.getString("report.rbi.num.issued"),
					"numBagsIssued", Integer.class.getName(), 50, detailStyle,
					header);
			drb.addColumn(resources.getString("report.rbi.level.of.damage"),
					"levelOfDamage", String.class.getName(), 50, detailStyle,
					header);

			drb.setIgnorePagination(true);
			drb.setUseFullPageWidth(true);

			DynamicReport report = drb.build();
			exportJasperReport(reportData, report, outputpath);

		} catch (ColumnBuilderException cbe) {
			cbe.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		virtualizer.cleanup();
		return fileName;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void exportJasperReport(List reportData, DynamicReport report,
			String outputpath) {
		JRDataSource data = new JRBeanCollectionDataSource(reportData);

		try {
			JasperPrint jp = DynamicJasperHelper.generateJasperPrint(report,
					new ClassicLayoutManager(), data);
			Map parameters = new HashMap();
			parameters.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
			parameters.put(JExcelApiExporterParameter.JASPER_PRINT, jp);
			parameters.put(JExcelApiExporterParameter.OUTPUT_FILE_NAME,
					outputpath);
			parameters.put(JExcelApiExporterParameter.IS_ONE_PAGE_PER_SHEET,
					Boolean.FALSE);
			parameters.put(JExcelApiExporterParameter.IS_WHITE_PAGE_BACKGROUND,
					Boolean.FALSE);
			parameters.put(JExcelApiExporterParameter.IGNORE_PAGE_MARGINS,
					Boolean.TRUE);
			parameters
					.put(JExcelApiExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS,
							Boolean.TRUE);
			parameters
					.put(JExcelApiExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
							Boolean.TRUE);
			parameters.put(JExcelApiExporterParameter.IS_FONT_SIZE_FIX_ENABLED,
					Boolean.TRUE);
			parameters.put(JExcelApiExporterParameter.IS_COLLAPSE_ROW_SPAN,
					Boolean.TRUE);
			parameters
					.put(JExcelApiExporterParameter.IS_DETECT_CELL_TYPE, true);

			JExcelApiExporter exporter = new JExcelApiExporter();
			exporter.setParameters(parameters);
			exporter.exportReport();
		} catch (JRException e) {
			logger.error(e, e);
		}
	}

	private String getSubTitle(StatReportDTO srDto, Agent user,
			ResourceBundle resources) {
		StringBuilder sb = new StringBuilder();
		String runDate = DateUtils.formatDate(new Date(), user.getDateformat()
				.getFormat(), user.getDefaultlocale(), null);
		sb.append(resources.getString("lf.ir.report.created.on") + " "
				+ runDate + " ");
		sb.append("by " + user.getUsername() + " for: " + srDto.getStarttime()
				+ " - " + srDto.getEndtime() + " ");
		return sb.toString();
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	private String create_mbr_rpt(StatReportDTO srDTO, int reportnum,
			String reportname, String reporttitle) throws HibernateException {
		Session sess = HibernateWrapper.getDirtySession().openSession();
		try {
			Map parameters = new HashMap();
			ResourceBundle myResources = ResourceBundle
					.getBundle(
							"com.bagnet.nettracer.tracing.resources.ApplicationResources",
							new Locale(user.getCurrentlocale()));
			parameters.put("REPORT_RESOURCE_BUNDLE", myResources);

			parameters.put("title", reporttitle);
			// Criteria cri = sess.createCriteria(Incident.class);

			TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(
					user.getDefaulttimezone()).getTimezone());

			/*** START ***/

			StringBuffer sbCompanyQuery = new StringBuffer("");
			String[] myCompanyList = srDTO.getCompany_ID();

			boolean reset2AllStations = true;
			if (myCompanyList != null) {
				sbCompanyQuery.append(" AND s2.companycode_ID IN ");

				List<String> myCompanyIdList = Arrays.asList(myCompanyList);

				// when companyList contains only this airline, stationq stays
				// unchanged
				// boolean reset2AllStations = true;
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
				// replace last character with )
				int myLastIndex = myCompanySql.length() - 2;
				if (myLastIndex > 0) {
					myCompanySql = myCompanySql.substring(0, myLastIndex);
					myCompanySql += "')";
				}
				sbCompanyQuery.append(myCompanySql);
			}
			/*** STOP ***/

			/*************** use direct jdbc sql statement **************/
			// Connection conn = sess.connection();
			// Statement stmt = conn.createStatement();
//			ResultSet rs = null;
			String sql = "select incident.stationassigned_ID,s1.stationcode as ascode,faultstation_ID,s2.stationcode as fscode, s2.station_id as fsid, s2.companycode_ID as fccode, "
					// testing my theory for Assigned Station related problem
					// String sql =
					// "select incident.stationassigned_ID,s1.stationcode as ascode,s1.station_id as asid,faultstation_ID,s2.stationcode as fscode, s2.station_id as fsid, s2.companycode_ID as fccode, "
					+ "incident.incident_ID,incident.itemType_ID,incident.loss_code,incident.createdate,incident.createtime,status.status_ID as stdesc,itemtype.description as itdesc, "
					+ "p.firstname,p.lastname,it.legfrom,it.legto,it.flightnum "
					+ "from station s1,station s2,status,itemtype,agent,incident "
					+ "left outer join passenger p on p.incident_ID = incident.incident_ID "
					+ "left outer join itinerary it on it.incident_ID = incident.incident_ID and it.itinerarytype = 0 "
					+ "where s1.station_ID = incident.stationassigned_ID and s2.station_ID = incident.faultstation_ID "
					+ "and status.status_ID = incident.status_ID and agent.Agent_ID = incident.agent_ID and itemtype.ItemType_ID = incident.itemType_ID and "
					+ "s1.companycode_ID = '"
					+ user.getStation().getCompany().getCompanyCode_ID() + "' ";

			if (srDTO.getItemType_ID() >= 1)
				sql += " and incident.itemtype_ID=" + srDTO.getItemType_ID();

			// change status to multi-select approach
			// if (srDTO.getStatus_ID() >= 1)
			// sql += " and incident.status_ID=" + srDTO.getStatus_ID();

			String statusIdList = "";
			String dispositionIdList = "";
			String myMbrSubtitleStatus = "";
			if (srDTO.getStatus_id_combo() != null) {
				for (int i = 0; i < srDTO.getStatus_id_combo().length; i++) {
					statusIdList += srDTO.getStatus_id_combo()[i] + ",";
					myMbrSubtitleStatus += TracerUtils.getText(
							Status.getKey(srDTO.getStatus_id_combo()[i]), user)
							+ ",";
				}

				int statusIdListLength = statusIdList.length();
				if (statusIdListLength > 0) {
					if (srDTO.getStatus_id_combo()[0].intValue() >= 1) {
						statusIdList = statusIdList.substring(0,
								statusIdListLength - 1);
						sql += " and incident.status_ID in (" + statusIdList
								+ ") ";

						myMbrSubtitleStatus = myMbrSubtitleStatus.substring(0,
								myMbrSubtitleStatus.length() - 1);
					} else {
						myMbrSubtitleStatus = "";
					}
				}
			}

			parameters.put("mbr_subtitle_status", myMbrSubtitleStatus);

			// change loss code to multi-select approach
			// if (srDTO.getLoss_code() > 0)
			// sql += " and incident.loss_code=" + srDTO.getLoss_code();
			String lossCodeList = "";
			if (srDTO.getLoss_code_combo() != null) {
				for (int i = 0; i < srDTO.getLoss_code_combo().length; i++) {
					lossCodeList += srDTO.getLoss_code_combo()[i] + ",";
				}

				int lossCodeListLength = lossCodeList.length();
				if (lossCodeListLength > 0
						&& srDTO.getLoss_code_combo()[0].intValue() >= 1) {
					lossCodeList = lossCodeList.substring(0,
							lossCodeListLength - 1);
					sql += " and incident.loss_code in (" + lossCodeList + ") ";
				}
			}

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

			if (intc.length() > 0 && srDTO.getStation_ID() != null
					&& !srDTO.getStation_ID()[0].equals("0")) {
				intc = intc.substring(0, intc.length() - 1);
				sql += " and incident.stationassigned_ID in (" + intc + ") ";
			}

			intc = "";
			if (!reset2AllStations && srDTO.getFaultstation_ID() != null) {
				for (int i = 0; i < srDTO.getFaultstation_ID().length; i++) {
					intc += srDTO.getFaultstation_ID()[i] + ",";
				}
			}

			if (reset2AllStations) {
				sql += sbCompanyQuery.toString();
			} else if (intc.length() > 0 && srDTO.getFaultstation_ID() != null
					&& !srDTO.getFaultstation_ID()[0].equals("0")) {
				intc = intc.substring(0, intc.length() - 1);
				sql += " and incident.faultstation_ID in (" + intc + ") ";
			}

			Date sdate = null, edate = null;
			Date sdate1 = null, edate1 = null; // add one for timezone
			Date stime = null; // time to compare (04:00 if eastern, for
								// example)
			String dateq = "";

			ArrayList dateal = null;
			if ((dateal = calculateDateDiff(srDTO, tz, user)) == null) {
				return null;
			}
			sdate = (Date) dateal.get(0);
			sdate1 = (Date) dateal.get(1);
			edate = (Date) dateal.get(2);
			edate1 = (Date) dateal.get(3);
			stime = (Date) dateal.get(4);

			parameters.put("sdate", TracerUtils.getText("reports.dates", user));
			if (sdate != null && edate != null) {
				parameters.put("sdate", srDTO.getStarttime());
				if (sdate.equals(edate)) {
					// need to add the timezone diff here
					sql += " and ((incident.createdate = '"
							+ DateUtils.formatDate(sdate, TracingConstants
									.getDBDateFormat(HibernateWrapper
											.getConfig().getProperties()),
									null, null)
							+ "' and incident.createtime >= '"
							+ DateUtils.formatDate(stime, TracingConstants
									.getDBTimeFormat(HibernateWrapper
											.getConfig().getProperties()),
									null, null)
							+ "') or (incident.createdate= '"
							+ DateUtils.formatDate(sdate1, TracingConstants
									.getDBDateFormat(HibernateWrapper
											.getConfig().getProperties()),
									null, null)
							+ "' and incident.createtime <= '"
							+ DateUtils.formatDate(stime, TracingConstants
									.getDBTimeFormat(HibernateWrapper
											.getConfig().getProperties()),
									null, null) + "'))";

				} else {

					// first get the beginning and end dates using date and
					// time, then get
					// dates in between
					sql += " and ((incident.createdate = '"
							+ DateUtils.formatDate(sdate, TracingConstants
									.getDBDateFormat(HibernateWrapper
											.getConfig().getProperties()),
									null, null)
							+ "' and incident.createtime >= '"
							+ DateUtils.formatDate(stime, TracingConstants
									.getDBTimeFormat(HibernateWrapper
											.getConfig().getProperties()),
									null, null)
							+ "') or (incident.createdate= '"
							+ DateUtils.formatDate(edate1, TracingConstants
									.getDBDateFormat(HibernateWrapper
											.getConfig().getProperties()),
									null, null)
							+ "' and incident.createtime <= '"
							+ DateUtils.formatDate(stime, TracingConstants
									.getDBTimeFormat(HibernateWrapper
											.getConfig().getProperties()),
									null, null)
							+ "') or (incident.createdate > '"
							+ DateUtils.formatDate(sdate, TracingConstants
									.getDBDateFormat(HibernateWrapper
											.getConfig().getProperties()),
									null, null)
							+ "' and incident.createdate <= '"
							+ DateUtils.formatDate(edate, TracingConstants
									.getDBDateFormat(HibernateWrapper
											.getConfig().getProperties()),
									null, null) + "'))";

					parameters.put("edate", srDTO.getEndtime());
				}
			} else if (sdate != null) {
				parameters.put("sdate", srDTO.getStarttime());
				sql += " and ((incident.createdate = '"
						+ DateUtils.formatDate(sdate, TracingConstants
								.getDBDateFormat(HibernateWrapper.getConfig()
										.getProperties()), null, null)
						+ "' and incident.createtime >= '"
						+ DateUtils.formatDate(stime, TracingConstants
								.getDBTimeFormat(HibernateWrapper.getConfig()
										.getProperties()), null, null)
						+ "') or (incident.createdate= '"
						+ DateUtils.formatDate(sdate1, TracingConstants
								.getDBDateFormat(HibernateWrapper.getConfig()
										.getProperties()), null, null)
						+ "' and incident.createtime <= '"
						+ DateUtils.formatDate(stime, TracingConstants
								.getDBTimeFormat(HibernateWrapper.getConfig()
										.getProperties()), null, null) + "'))";

				edate = null;
			}

			if (srDTO.getGroupby() == GROUP_BY_FAULT_STATION) {
				sql += " order by fsid, incident.itemtype_ID,incident.incident_ID,it.itinerary_ID";

				// reportStyle : first digit represents GroupBy; second digit
				// represents Option
				// ex : 00-Groupby Assigned Station, Summary; 01-Groupby
				// Assigned Station, Detail;
				// 10-Groupby Fault Station, Summary; 11-Groupby Fault Station,
				// Detail
				// determine report style for dj
				// summary or detail; summary = 0; detail = 1
				if (srDTO.getSumordet() == 1) {
					parameters.put("reportStyle", "11");
				} else {
					parameters.put("reportStyle", "10");
				}

			} else {
				// DEFAULT (0) is grouped by station assigned
				sql += " order by incident.stationassigned_ID,incident.itemtype_ID,incident.incident_ID,it.itinerary_ID";
				// sql +=
				// " order by asid,incident.itemtype_ID,incident.incident_ID,it.itinerary_ID";

				// determine report style for dj
				if (srDTO.getSumordet() == 1) {
					parameters.put("reportStyle", "01");
				} else {
					parameters.put("reportStyle", "00");
				}
			}

			SQLQuery q = sess.createSQLQuery(sql);

			// rs = stmt.executeQuery(sql);

			// logger.error(">>>>Entire sql : " + sql);

			// put in date and timeformat and timezone of current user

			Object[] o = null;
			Object[] o2 = null;
			Object[] o3 = null;
			StatReport_3_DTO sr = null;
			List list2 = new ArrayList();

			List templist = null;
			StringBuffer tempsb = null;
			int i = 0;
			String lastincid = null;
			String lastpassname = "";
			String lastit = "";
			String temppassname = "", tempit = "";
			List results = q.list();

			if (results.isEmpty()) {
				return null;
			}
			SimpleReportRow reportRow;
			Object[] row;

			for (int j = 0; j < results.size(); ++j) {
				row = (Object[]) results.get(j);
				if (lastincid == null
						|| !((String)row[6]).equals(lastincid)) {
					if (lastincid != null) {
						list2.add(sr);
						lastpassname = "";
						lastit = "";
					}
					sr = new StatReport_3_DTO();
					if (srDTO.getGroupby() == GROUP_BY_FAULT_STATION) {
						sr.setStation_ID((Integer) row[4]);
						sr.setStationcode((String) row[3]);
					} else {
						// DEFAULT (0) is grouped by station assigned
						sr.setStation_ID((Integer) row[0]);
						sr.setStationcode((String) row[1]);
					}
					if (row.length > 3) {
						sr.setFaultstationcode((String) row[3]);
						if (row.length > 5) {
							sr.setFaultcompany((String) row[5]);
							if (row.length > 6) {
								sr.setIncident_ID((String) row[6]);
								lastincid = sr.getIncident_ID();
								if (row.length > 7) {
									sr.setItemtype_ID((Integer) row[7]);
									if (row.length > 8) {
										sr.setLoss_code((Integer) row[8]);
										if (row.length > 9) {
											sr.setCreatedate((Date) row[9]);
											if (row.length > 10) {
												sr.setCreatetime((Date) row[10]);
												if (row.length > 11) {
													Status a = new Status();
													a.setStatus_ID((Integer) row[11]);
													a.setLocale(user);
													sr.setStatusdesc(a
															.getDescription());
													if (row.length > 12) {
														sr.setTypedesc((String) row[12]);

														if (row.length > 14) {
															if (row[14] != null && ((String) row[14]).length() > 0 && (srDTO.isShowLastName() || srDTO.isShowAll()))
																temppassname += (String) row[14];
															if (row[13] != null && ((String) row[13]).length() > 0  && (srDTO.isShowFirstName() || srDTO.isShowAll())){
																if( (srDTO.isShowLastName( )&& srDTO.isShowFirstName()) || srDTO.isShowAll())
																	temppassname += ", ";
																temppassname +=(String) row[13];
																}
															if (lastpassname.length() > 0) {
																if (lastpassname.indexOf(temppassname) < 0)
																	lastpassname += "\n"+ temppassname;
															} else
																lastpassname = temppassname;
															temppassname = "";
															sr.setCustomer_name(lastpassname);
															
															if (row.length > 16) {
																if ((String) row[15] != null
																		&& ((String) row[15])
																				.length() > 0)
																	tempit += (String) row[15];
																if ((String) row[17] != null
																		&& ((String) row[17])
																				.length() > 0)
																	tempit += " "
																			+ (String) row[17];
																if (lastit
																		.length() > 0) {
																	if (lastit
																			.indexOf(tempit) < 0)
																		lastit += "\n"
																				+ tempit;
																} else
																	lastit = tempit;
																tempit = "";
																sr.setItinerary(lastit);
																if (row[16] != null
																		&& ((String) row[16])
																				.length() > 0)
																	sr.setFinal_destination((String) row[16]);
																else
																	sr.setFinal_destination("");
															}

														}
													}

												}
											}
										}
									}
								}
							}
						}
					}

				} else {
					if (row.length > 14) {
					if (row[14] != null
							&& ((String) row[14])
									.length() > 0)
						temppassname += (String) row[14];
					if (row[13] != null
							&& ((String) row[13])
									.length() > 0)
						temppassname += ", "
								+ (String) row[13];
					if (lastpassname
							.length() > 0) {
						if (lastpassname
								.indexOf(temppassname) < 0)
							lastpassname += "\n"
									+ temppassname;
					} else
						lastpassname = temppassname;
					temppassname = "";
					sr.setCustomer_name(lastpassname);
					if (row.length > 16) {
						if ((String) row[15] != null
								&& ((String) row[15])
										.length() > 0)
							tempit += (String) row[15];
						if ((String) row[17] != null
								&& ((String) row[17])
										.length() > 0)
							tempit += " "
									+ (String) row[17];
						if (lastit
								.length() > 0) {
							if (lastit
									.indexOf(tempit) < 0)
								lastit += "\n"
										+ tempit;
						} else
							lastit = tempit;
						tempit = "";
						sr.setItinerary(lastit);
						if (row[16] != null
								&& ((String) row[16])
										.length() > 0)
							sr.setFinal_destination((String) row[16]);
						else
							sr.setFinal_destination("");
					}

					}
				}
				if (sr.getFinal_destination() == null)
					sr.setFinal_destination("");

				sr.set_DATEFORMAT(user.getDateformat().getFormat());
				sr.set_TIMEFORMAT(user.getTimeformat().getFormat());
				sr.set_TIMEZONE(tz);

				// toReturn.add(reportRow);
			}

			if (sr == null) {
				logger.debug("no data for report");
				return "";
			}

			// add the last one
			if (sr != null)
				list2.add(sr);

			if (srDTO.getStatus_ID() >= 1) {
				if (sr != null)
					parameters.put("status", sr.getStatusdesc());
			} else {
				parameters.put("status",
						TracerUtils.getText("reports.all", user));
			}

			// summary or detail; summary = 0; detail = 1
			boolean checked=false;
			if (!srDTO.isShowAll()){
				if(srDTO.isShowAssignCity()){
					checked=true;
					parameters.put("showassigncity", true);
					if(srDTO.getGroupby()!=GROUP_BY_FAULT_STATION){
						parameters.put("groupassigncity", "1");
					}
				}
				if(srDTO.isShowDate()){
					checked=true;
					parameters.put("showdate", true);
				}
				if(srDTO.isShowTime()){
					checked=true;
					parameters.put("showtime", true);
				}
				if(srDTO.isShowType()){
					checked=true;
					parameters.put("showtype", true);
				}
				if(srDTO.isShowReportID()){
					checked=true;
					parameters.put("showreportid", true);
				}
				if(srDTO.isShowLastName()){
					checked=true;
					parameters.put("showlastname", true);
				}
				if(srDTO.isShowFirstName()){
					checked=true;
					parameters.put("showfirstname", true);
				}
				if(srDTO.isShowItinerary()){
					checked=true;
					parameters.put("showitinerary", true);
				}
				if(srDTO.isShowDestination()){
					checked=true;
					parameters.put("showdestination", true);
				}
				if(srDTO.isShowStatus()){
					checked=true;
					parameters.put("showstatus", true);
				}
				if(srDTO.isShowFaultCity()){
					checked=true;
					parameters.put("showfaultcity", true);
					if(srDTO.getGroupby()==GROUP_BY_FAULT_STATION){
						parameters.put("groupfaultcity", "1");
					}
				}
				if(srDTO.isShowLossCode()){
					checked=true;
					parameters.put("showlosscode", true);
				}
			} else {
				checked=true;
				parameters.put("showall", true);
			}
			if (srDTO.getSumordet() == 1) {
				parameters.put("showdetail", "1");
			}
			parameters.put("checknum", srDTO.getNumChecked());

			// toggle between old and new implementations
			// return getReportFile(list2, parameters, reportname, rootpath,
			// srDTO.getOutputtype());

			parameters.put("reportLocale", new Locale(user.getCurrentlocale()));
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(
					list2);
			if(checked)
				return MBRReportBMO.getReportFileDj(ds, parameters, reportname,
					rootpath, srDTO.getOutputtype(), req, this);
			else
				return null;

		} catch (Exception e) {
			logger.error("unable to create report " + e);
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String create_passboarding_rpt(StatReportDTO srDTO, int reportnum,
			String reportname, String reporttitle) throws HibernateException {
		Session sess = HibernateWrapper.getDirtySession().openSession();
		try {
			if (srDTO.getBoarded() <= 0)
				return null;
			Map parameters = new HashMap();
			ResourceBundle myResources = ResourceBundle
					.getBundle(
							"com.bagnet.nettracer.tracing.resources.ApplicationResources",
							new Locale(user.getCurrentlocale()));
			parameters.put("REPORT_RESOURCE_BUNDLE", myResources);

			parameters.put("title", reporttitle);

			TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(
					user.getDefaulttimezone()).getTimezone());

			// mbr report type
			String mbrtypeq = "";
			if (srDTO.getItemType_ID() >= 1) {
				mbrtypeq = " and incident.itemtype.itemType_ID = :itemType_ID ";
			}
			String stationq = "";
			if (srDTO.getStation_ID() != null
					&& !srDTO.getStation_ID()[0].equals("0")) {
				stationq = " and incident.stationassigned.station_ID in (:station_ID) ";
			}

			String fstationq = "";
			if (srDTO.getFaultstation_ID() != null
					&& !srDTO.getFaultstation_ID()[0].equals("0")) {
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
			Date stime = null; // time to compare (04:00 if eastern, for
								// example)
			String dateq = "";

			ArrayList dateal = null;
			if ((dateal = calculateDateDiff(srDTO, tz, user)) == null) {
				return null;
			}
			sdate = (Date) dateal.get(0);
			sdate1 = (Date) dateal.get(1);
			edate = (Date) dateal.get(2);
			edate1 = (Date) dateal.get(3);
			stime = (Date) dateal.get(4);

			long numdays = 0;

			parameters.put("sdate", TracerUtils.getText("reports.dates", user));
			if (sdate != null && edate != null) {
				parameters.put("sdate", srDTO.getStarttime());
				if (sdate.equals(edate)) {
					// need to add the timezone diff here
					dateq = " and ((incident.createdate= :startdate and incident.createtime >= :starttime) "
							+ " or (incident.createdate= :startdate1 and incident.createtime <= :starttime))";
					edate = null;
					numdays = 1;
				} else {
					// first get the beginning and end dates using date and
					// time, then get
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

			String sql = "select count(incident.incident_ID) "
					+ " from com.bagnet.nettracer.tracing.db.Incident incident where 1=1 "
					+ stationq + dateq + statusq + fstationq + agentq
					+ mbrtypeq + companylimit;

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

			if (srDTO.getStation_ID() != null
					&& !srDTO.getStation_ID()[0].equals("0")) {
				q.setParameterList("station_ID", intc);
			}

			Integer[] intfc = null;
			if (srDTO.getFaultstation_ID() != null) {
				intfc = new Integer[srDTO.getFaultstation_ID().length];

				for (int i = 0; i < intfc.length; i++) {
					intfc[i] = new Integer(srDTO.getFaultstation_ID()[i]);
				}
			}
			if (srDTO.getFaultstation_ID() != null
					&& !srDTO.getFaultstation_ID()[0].equals("0")) {
				q.setParameterList("fstation_ID", intfc);
			}

			if (srDTO.getLoss_code() > 0) {
				q.setInteger("loss_code", srDTO.getLoss_code());
			}

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

			if (srDTO.getAgent() != null && srDTO.getAgent().length() > 0)
				q.setString("agent_username", srDTO.getAgent());

			q.setString("companyCode_ID", user.getStation().getCompany()
					.getCompanyCode_ID());

			List list = q.list();

			if (list.size() == 0) {
				logger.debug("no data for report");
				return "";
			}
			int numpirs = 0;
			numpirs = ((Long) list.get(0)).intValue();

			// populate station, faultstation and status
			if (srDTO.getStation_ID() != null
					&& !srDTO.getStation_ID()[0].equals("0")) {
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < srDTO.getStation_ID().length; i++) {
					sb.append((TracerUtils.getStationcode(Integer
							.parseInt(srDTO.getStation_ID()[i]))));
					sb.append("  ");
				}
				parameters.put("station", sb.toString());
			}
			if (srDTO.getFaultstation_ID() != null
					&& !srDTO.getFaultstation_ID()[0].equals("0")) {
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < srDTO.getFaultstation_ID().length; i++) {
					sb.append((TracerUtils.getStationcode(Integer
							.parseInt(srDTO.getFaultstation_ID()[i]))));
					sb.append("  ");
				}
				parameters.put("fault_station", sb.toString());
			}

			if (srDTO.getStatus_ID() >= 1) {
				parameters.put("status", "");
			} else {
				parameters.put("status",
						TracerUtils.getText("reports.all", user));
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

			return getReportFile(list, parameters, reportname, rootpath,
					srDTO.getOutputtype());
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
	 * @return @throws HibernateException
	 */
	@SuppressWarnings({ "unchecked", "unused", "rawtypes" })
	private String create_flt_rpt(StatReportDTO srDTO, int reportnum,
			String reportname, String reporttitle) throws HibernateException {
		Session sess = HibernateWrapper.getDirtySession().openSession();
		try {
			Map parameters = new HashMap();
			ResourceBundle myResources = ResourceBundle
					.getBundle(
							"com.bagnet.nettracer.tracing.resources.ApplicationResources",
							new Locale(user.getCurrentlocale()));
			parameters.put("REPORT_RESOURCE_BUNDLE", myResources);

			parameters.put("title", reporttitle);

			TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(
					user.getDefaulttimezone()).getTimezone());

			// mbr report type
			String mbrtypeq = "";
			if (srDTO.getItemType_ID() >= 1) {
				mbrtypeq = " and itinerary.incident.itemtype.itemType_ID = :itemType_ID ";
			}
			if (srDTO.getStation_ID() == null
					|| srDTO.getStation_ID()[0].equals("0")) {
				parameters.put("showsubtotal", "show");
			}
			String statusq = "";
			if (srDTO.getStatus_ID() >= 1) {
				statusq = " and itinerary.incident.status.status_ID= :status_ID ";
			}

			// new status multiple selection approach
			String multiStatusQ = "";

			String myMbrPerFlightSubtitleStatus = "";
			if (srDTO.getStatus_id_combo() != null) {
				for (int i = 0; i < srDTO.getStatus_id_combo().length; i++) {
					myMbrPerFlightSubtitleStatus += TracerUtils.getText(
							Status.getKey(srDTO.getStatus_id_combo()[i]), user)
							+ ",";
				}

				if (srDTO.getStatus_id_combo()[0].intValue() >= 1) {
					multiStatusQ = " and itinerary.incident.status.status_ID in (:status_ID) ";

					myMbrPerFlightSubtitleStatus = myMbrPerFlightSubtitleStatus
							.substring(0,
									myMbrPerFlightSubtitleStatus.length() - 1);
				} else {
					myMbrPerFlightSubtitleStatus = "";
				}

			}

			if (srDTO.getStatus_ID() >= 1) {
				myMbrPerFlightSubtitleStatus += TracerUtils.getText(
						Status.getKey(srDTO.getStatus_ID()), user);
			}

			parameters.put("mbr_per_flight_subtitle_status",
					myMbrPerFlightSubtitleStatus);

			// logger.error("mbr_per_flight_subtitle_status is set to : " +
			// myMbrPerFlightSubtitleStatus);
			// logger.error("multiStatusQ=" + multiStatusQ);

			String companylimit = " and itinerary.incident.stationassigned.company.companyCode_ID = :companyCode_ID ";

			Date sdate = null, edate = null;
			Date sdate1 = null, edate1 = null; // add one for timezone
			Date stime = null; // time to compare (04:00 if eastern, for
								// example)
			String dateq = "";

			ArrayList dateal = null;
			if ((dateal = calculateDateDiff(srDTO, tz, user)) == null) {
				return null;
			}
			sdate = (Date) dateal.get(0);
			sdate1 = (Date) dateal.get(1);
			edate = (Date) dateal.get(2);
			edate1 = (Date) dateal.get(3);
			stime = (Date) dateal.get(4);

			parameters.put("sdate", TracerUtils.getText("reports.dates", user));
			if (sdate != null && edate != null) {
				parameters.put("sdate", srDTO.getStarttime());
				if (sdate.equals(edate)) {
					// need to add the timezone diff here
					dateq = " and ((itinerary.incident.createdate= :startdate and itinerary.incident.createtime >= :starttime) "
							+ " or (itinerary.incident.createdate= :startdate1 and itinerary.incident.createtime <= :starttime))";

					edate = null;
				} else {
					// first get the beginning and end dates using date and
					// time, then get
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
			if (srDTO.getStation_ID() != null
					&& !srDTO.getStation_ID()[0].equals("0")) {
				stationq2 = " and incident.stationassigned.station_ID in (:station_ID) ";
			}

			Integer[] intc = null;
			if (srDTO.getStation_ID() != null) {
				intc = new Integer[srDTO.getStation_ID().length];

				for (int i = 0; i < intc.length; i++) {
					intc[i] = new Integer(srDTO.getStation_ID()[i]);
				}
			}

			String sql = "select distinct incident.stationassigned.station_ID from com.bagnet.nettracer.tracing.db.Incident incident "
					+ " where incident.stationassigned.company.companyCode_ID = :companyCode_ID "
					+ stationq2
					+ " order by incident.stationassigned.station_ID";
			Query q = sess.createQuery(sql);
			if (srDTO.getStation_ID() != null
					&& !srDTO.getStation_ID()[0].equals("0"))
				q.setParameterList("station_ID", intc);
			q.setString("companyCode_ID", user.getStation().getCompany()
					.getCompanyCode_ID());

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
						+ statusq // old approach to status
						// + multiStatusQ
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

				// new approach to support multiple status selection
				if (srDTO.getStatus_id_combo() != null) {
					if (srDTO.getStatus_id_combo()[0].intValue() >= 1) {
						q.setParameterList("status_ID",
								srDTO.getStatus_id_combo());
					}
				}

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
				q.setString("companyCode_ID", user.getStation().getCompany()
						.getCompanyCode_ID());
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
				// get a set of flight numbers used by the previous query to
				// limit the
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
						+ "where itinerary.itinerarytype = 0 and item.incident.incident_ID = itinerary.incident.incident_ID "
						+ stationq
						+ statusq
						+ dateq
						+ mbrtypeq
						+ companylimit
						+ flightq
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
					if (edate == null)
						q.setDate("startdate1", sdate1);
					q.setTime("starttime", stime);
				}
				if (edate != null) {
					q.setDate("enddate1", edate1);
					q.setDate("enddate", edate);
				}
				q.setString("companyCode_ID", user.getStation().getCompany()
						.getCompanyCode_ID());
				q.setMaxResults(srDTO.getNumtop());
				templist = q.list();
				// set list to be list of topflight objects
				tf = null;
				o = null;
				for (int i = 0; i < list.size(); i++) {
					for (int j = 0; j < templist.size(); j++) {
						o = (Object[]) templist.get(j);
						tf = (StatReport_TopFlightDTO) list.get(i);
						if (((String) o[1]).equalsIgnoreCase(tf.getAirline())
								&& ((String) o[2]).equalsIgnoreCase(tf
										.getFlightnum())
								&& ((Integer) o[3]).intValue() == tf
										.getStation().getStation_ID()) {
							tf.setNumbag(((Long) o[0]).intValue());
							break;
						}
					}
				}
			}
			// populate status
			if (srDTO.getStatus_ID() >= 1) {
				parameters.put("status", getStatus(srDTO.getStatus_ID())
						.getTextDescription(user));
			} else {
				parameters.put("status",
						TracerUtils.getText("reports.all", user));
			}

			// get the incident type if any
			String incidentType = "";
			if (srDTO.getItemType_ID() >= 1) {
				ItemType it = IncidentUtils.retrieveItemTypeWithId(
						srDTO.getItemType_ID(), user.getCurrentlocale());
				incidentType = it.getDescription();
			}
			parameters.put("incidentType", incidentType);

			if (list.size() == 0) {
				logger.debug("no data for report");
				return "";
			}
			parameters.put("numtop", new Integer(srDTO.getNumtop()));

			// toggle between old and new implementations
			// return getReportFile(list, parameters, reportname, rootpath,
			// srDTO.getOutputtype());

			parameters.put("reportLocale", new Locale(user.getCurrentlocale()));
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(list);

			return MBRPerFlightReportBMO.getReportFileDj(ds, parameters,
					reportname, rootpath, srDTO.getOutputtype(), req, this);

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
	 * @return @throws HibernateException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String create_exp_rpt(StatReportDTO srDTO, int reportnum,
			String reportname, String reporttitle) throws HibernateException {
		Session sess = HibernateWrapper.getDirtySession().openSession();
		try {
			Map parameters = new HashMap();
			ResourceBundle myResources = ResourceBundle
					.getBundle(
							"com.bagnet.nettracer.tracing.resources.ApplicationResources",
							new Locale(user.getCurrentlocale()));
			parameters.put("REPORT_RESOURCE_BUNDLE", myResources);

			parameters.put("title", reporttitle);

			TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(
					user.getDefaulttimezone()).getTimezone());

			// mbr report type
			String mbrtypeq = "";
			if (srDTO.getItemType_ID() >= 1) {
				mbrtypeq = " and exp.incident.itemtype.itemType_ID = :itemType_ID ";
			}
			String stationq = "";
			if (srDTO.getStation_ID() != null
					&& !srDTO.getStation_ID()[0].equals("0")) {
				stationq = " and exp.expenselocation.station_ID in (:station_ID) ";
			}

			// fault station and loss code
			String faultq = "";
			if (srDTO.getFaultstation_ID() != null
					&& !srDTO.getFaultstation_ID()[0].equals("0")) {
				faultq = " and fault.station_ID in (:fault_ID) ";
			}
			String losscodeq = "";
			// if (srDTO.getLoss_code() > 0) {
			// losscodeq = " and exp.incident.loss_code = :loss_code_combo ";
			// }
			if (srDTO.getLoss_code_combo() != null
					&& srDTO.getLoss_code_combo()[0].intValue() > 0) {
				losscodeq = " and exp.incident.loss_code in (:loss_code_combo) ";
			}

			String statusq = "";
			// if (srDTO.getStatus_ID() >= 1) {
			// statusq = " and exp.incident.status.status_ID= :status_ID ";
			// }
			if (srDTO.getStatus_id_combo() != null
					&& srDTO.getStatus_id_combo()[0].intValue() >= 1) {
				statusq = " and exp.incident.status.status_ID in (:status_id_combo) ";
			}

			String agentq = "";
			if (srDTO.getAgent() != null && srDTO.getAgent().length() > 0) {
				agentq = " and exp.agent.username like :agent_username ";
			}

			String companylimit = " and exp.incident.stationassigned.company.companyCode_ID = :companyCode_ID ";

			Date sdate = null, edate = null;
			Date sdate1 = null, edate1 = null; // add one for timezone
			Date stime = null; // time to compare (04:00 if eastern, for
								// example)
			String dateq = "";

			ArrayList dateal = null;
			if ((dateal = calculateDateDiff(srDTO, tz, user)) == null) {
				return null;
			}
			sdate = (Date) dateal.get(0);
			sdate1 = (Date) dateal.get(1);
			edate = (Date) dateal.get(2);
			edate1 = (Date) dateal.get(3);
			stime = (Date) dateal.get(4);

			parameters.put("sdate", TracerUtils.getText("reports.dates", user));
			if (sdate != null && edate != null) {
				parameters.put("sdate", srDTO.getStarttime());
				if (sdate.equals(edate)) {
					// need to add the timezone diff here
					dateq = " and ((exp.incident.createdate= :startdate and exp.incident.createtime >= :starttime) "
							+ " or (exp.incident.createdate= :startdate1 and exp.incident.createtime <= :starttime))";

					edate = null;
				} else {

					// first get the beginning and end dates using date and
					// time, then get
					// dates in between
					dateq = " and ((exp.incident.createdate= :startdate and exp.incident.createtime >= :starttime) "
							+ " or (exp.incident.createdate= :enddate1 and exp.incident.createtime <= :starttime)"
							+ " or (exp.incident.createdate > :startdate and exp.incident.createdate <= :enddate))";

					parameters.put("edate", srDTO.getEndtime());
				}
			} else if (sdate != null) {
				parameters.put("sdate", srDTO.getStarttime());
				dateq = " and ((exp.incident.createdate= :startdate and exp.incident.createtime >= :starttime) "
						+ " or (exp.incident.createdate= :startdate1 and exp.incident.createtime <= :starttime))";
				edate = null;
			}

			// draft paid dates
			Date psdate = null, pedate = null;
			if (srDTO.getCstarttime() != null
					&& srDTO.getCstarttime().length() > 0) {
				psdate = DateUtils.convertToDate(srDTO.getCstarttime(), user
						.getDateformat().getFormat(), null);
				if (psdate == null) // invalid date
					return null;
			}
			if (srDTO.getCendtime() != null && srDTO.getCendtime().length() > 0) {
				pedate = DateUtils.convertToDate(srDTO.getCendtime(), user
						.getDateformat().getFormat(), null);
				if (pedate == null) // invalid date
					return null;
			}
			parameters
					.put("psdate", TracerUtils.getText("reports.dates", user));
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
			// if (srDTO.getExpensetype_ID() > 0) {
			// typeq = " and exp.expensetype.expensetype_ID = :expensetype_ID ";
			// }
			if (srDTO.getExpensetype_id_combo() != null
					&& srDTO.getExpensetype_id_combo()[0].intValue() > 0) {
				typeq = " and exp.expensetype.expensetype_ID in (:expensetype_id_combo) ";
			}

			String addspart = "";
			if (srDTO.getStatus_ID() >= 1) {
				addspart = "exp.incident.status.description,";
			}
			String sql = "SELECT sum(exp.checkamt), exp.currency, sum(exp.voucheramt), sum(exp.mileageamt),"
					+ " exp.expensetype.expensetype_ID, exp.expenselocation.station_ID,exp.expenselocation.stationcode,exp.draftpaiddate,"
					+ addspart
					+ " fault.station_ID,fault.stationcode,exp.incident.loss_code,exp.agent.username,exp.incident.incident_ID,exp.agent.station.stationcode,exp.status.status_ID "
					+ "from com.bagnet.nettracer.tracing.db.ExpensePayout exp left outer join exp.incident.faultstation fault where 1=1  "
					+ mbrtypeq
					+ stationq
					+ faultq
					+ losscodeq
					+ statusq
					+ agentq
					+ dateq
					+ typeq
					+ companylimit
					+ " group by exp.expenselocation.stationcode, exp.expenselocation.station_ID, fault.station_ID,fault.stationcode, exp.incident.incident_ID, exp.expensetype.expensetype_ID,exp.draftpaiddate,exp.status.status_ID, "
					+ addspart
					+ " exp.currency, exp.incident.loss_code,exp.agent.username ";
			// +
			// " order by exp.expenselocation.stationcode,exp.expensetype.expensetype_ID";

			String myPrimarySortOrder = srDTO.getPrimary_sort_order();
			String mySecondarySortOrder = srDTO.getSecondary_sort_order();
			String myReportStyle = "11";

			if (mySecondarySortOrder.equalsIgnoreCase("by_incident_id")) {
				if (myPrimarySortOrder.equalsIgnoreCase("by_create_station")) {
					myReportStyle = "11";
					sql += " order by exp.expenselocation.stationcode, exp.incident.incident_ID";
				} else if (myPrimarySortOrder
						.equalsIgnoreCase("by_agent_payment_station")) {
					myReportStyle = "20";
					sql += " order by exp.agent.station.stationcode, exp.incident.incident_ID";
				} else if (myPrimarySortOrder
						.equalsIgnoreCase("by_fault_station")) {
					myReportStyle = "12";
					sql += " order by exp.incident.faultstation.stationcode, exp.incident.incident_ID";
				}
			} else if (mySecondarySortOrder
					.equalsIgnoreCase("by_expense_agent_username")) {
				if (myPrimarySortOrder.equalsIgnoreCase("by_create_station")) {
					myReportStyle = "31";
					sql += " order by exp.expenselocation.stationcode, exp.agent.username";
				} else if (myPrimarySortOrder
						.equalsIgnoreCase("by_agent_payment_station")) {
					myReportStyle = "40";
					sql += " order by exp.agent.station.stationcode, exp.agent.username";
				} else if (myPrimarySortOrder
						.equalsIgnoreCase("by_fault_station")) {
					myReportStyle = "32";
					sql += " order by exp.incident.faultstation.stationcode, exp.agent.username";
				}
			}

			// print the entire sql to debug
			// logger.error("create_exp_rpt - entire sql: " + sql);
			Query q = sess.createQuery(sql);
			if (srDTO.getItemType_ID() >= 1)
				q.setInteger("itemType_ID", srDTO.getItemType_ID());
			if (srDTO.getStation_ID() != null
					&& !srDTO.getStation_ID()[0].equals("0")) {
				// q.setParameterList("station_ID", srDTO.getStation_ID());
				String sArray[] = srDTO.getStation_ID();
				List<String> stationIdStringList = Arrays.asList(sArray);
				List<Integer> stationIdIntegerList = StringUtils
						.convertStringArrayList2IntegerArrayList(stationIdStringList);
				q.setParameterList("station_ID", stationIdIntegerList);
			}

			if (srDTO.getFaultstation_ID() != null
					&& !srDTO.getFaultstation_ID()[0].equals("0")) {
				// q.setParameterList("fault_ID", srDTO.getFaultstation_ID());
				String sArray[] = srDTO.getFaultstation_ID();
				List<String> faultStationIdStringList = Arrays.asList(sArray);
				List<Integer> faultStationIdIntegerList = StringUtils
						.convertStringArrayList2IntegerArrayList(faultStationIdStringList);
				q.setParameterList("fault_ID", faultStationIdIntegerList);
			}

			// if (srDTO.getLoss_code() > 0)
			// q.setInteger("loss_code", srDTO.getLoss_code());
			Integer[] myLoss_code_combo = srDTO.getLoss_code_combo();
			if (myLoss_code_combo != null
					&& myLoss_code_combo[0].intValue() > 0) {
				q.setParameterList("loss_code_combo",
						Arrays.asList(myLoss_code_combo));
				parameters.put("showsubtotal", "show");
			}

			// if (srDTO.getStatus_ID() >= 1)
			// q.setInteger("status_ID", srDTO.getStatus_ID());
			Integer[] myStatus_id_combo = srDTO.getStatus_id_combo();
			if (myStatus_id_combo != null
					&& myStatus_id_combo[0].intValue() >= 1) {
				q.setParameterList("status_id_combo",
						Arrays.asList(myStatus_id_combo));
				// parameters.put("showsubtotal", "show");
			}

			Integer[] myDisposition_id_combo = srDTO.getDisposition_id_combo();
			if (myDisposition_id_combo != null
					&& myDisposition_id_combo[0].intValue() >= 1) {
				q.setParameterList("disposition_id_combo",
						Arrays.asList(myDisposition_id_combo));
			}

			if (srDTO.getAgent() != null && srDTO.getAgent().length() > 0) {
				q.setString("agent_username", srDTO.getAgent());
			}

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

			if (psdate != null)
				q.setDate("pstartdate", psdate);
			if (pedate != null)
				q.setDate("penddate", pedate);

			// if (srDTO.getExpensetype_ID() > 0)
			// q.setInteger("expensetype_ID", srDTO.getExpensetype_ID());
			Integer[] myExpensetype_id_combo = srDTO.getExpensetype_id_combo();
			if (myExpensetype_id_combo != null
					&& myExpensetype_id_combo[0].intValue() > 0) {
				q.setParameterList("expensetype_id_combo",
						Arrays.asList(myExpensetype_id_combo));
				// parameters.put("showsubtotal", "show");
			}

			q.setString("companyCode_ID", user.getStation().getCompany()
					.getCompanyCode_ID());

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
				exp.setCurrency_ID(((java.util.Currency) o[1])
						.getCurrencyCode());
				exp.setVoucheramt(((Double) o[2]).doubleValue());
				exp.setMileageamt(((Long) o[3]).intValue());
				exp.setExpenseType_ID((Integer) o[4]);

				Station st = new Station();
				st.setStation_ID(((Integer) o[5]).intValue());
				st.setStationcode((String) o[6]);
				exp.setStation(st);

				exp.setDraftpaiddate((Date) o[7]);

				int j = 8;
				if (srDTO.getStatus_ID() >= 1) {
					Status st1 = new Status();
					// st1.setDescription((String) o[7]);
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

				// deal with claim number here
				exp.setClaim_number((String) o[++j]);

				// deal with agent station code here
				exp.setAgent_station_code((String) o[++j]);

				// deal with expense type description here
				// exp.setExpenseType_description((String) o[++j]);
				int statusId = ((Integer) o[++j]).intValue();
				String expenseStatus = myResources.getString("STATUS_KEY_"
						+ statusId);
				exp.setExpenseStatus(expenseStatus);

				list.add(exp);
			}
			// populate station and status
			exp = (StatReport_ExpDTO) list.get(0);
			if (srDTO.getStation_ID() == null
					|| srDTO.getStation_ID()[0].equals("0")) {
				parameters.put("showsubtotal", "show");
			}
			if (srDTO.getStatus_ID() >= 1) {
				parameters.put("status",
						exp.getStatus().getTextDescription(user));
			} else {
				parameters.put("status",
						TracerUtils.getText("reports.all", user));
			}

			parameters.put("reportStyle", myReportStyle);
			parameters.put("reportLocale", new Locale(user.getCurrentlocale()));
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(list);

			return getReportFileDj(ds, parameters, reportname, rootpath,
					srDTO.getOutputtype(), req, this);
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
	 * @return @throws HibernateException
	 */
	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	private String create_station2_rpt(StatReportDTO srDTO, int reportnum,
			String reportname, String reporttitle) throws HibernateException {
		Session sess = HibernateWrapper.getDirtySession().openSession();
		try {
			boolean hasb = false, hasc = false;

			TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(
					user.getDefaulttimezone()).getTimezone());

			Map parameters = new HashMap();
			parameters.put("title", reporttitle);

			/*************** use direct jdbc sql statement **************/
			
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
			if (srDTO.getB_stationcode() != null
					&& srDTO.getB_stationcode().length() > 0) {
				sb.append(
						" and (i_a.legfrom = :b_stationcode and i_a.legfrom_type = ")
						.append(TracingConstants.LEG_B_STATION).append(") ");
				// terminating station
				if (srDTO.getE_stationcode() != null
						&& srDTO.getE_stationcode().length() > 0) {
					sb.append(
							" and (i_b.legto = :e_stationcode and i_b.legto_type = ")
							.append(TracingConstants.LEG_E_STATION)
							.append(") ");
					hasb = true;
					// orignating station + terminating station + transfer
					// station
					if (srDTO.getT_stationcode() != null
							&& srDTO.getT_stationcode().length() > 0) {
						sb.append(
								" and ((i_c.legfrom = :t_stationcode and i_c.legfrom_type = ")
								.append(TracingConstants.LEG_T_STATION)
								.append(") ");
						sb.append(
								" or (i_c.legto = :t_stationcode and i_c.legto_type = ")
								.append(TracingConstants.LEG_T_STATION)
								.append(")) ");
						sqlselect = "from "
								+ "com.bagnet.nettracer.tracing.db.Itinerary i_a, "
								+ "com.bagnet.nettracer.tracing.db.Itinerary i_b, "
								+ "com.bagnet.nettracer.tracing.db.Itinerary i_c "
								+ "where i_a.incident.incident_ID = i_b.incident.incident_ID and"
								+ " i_a.incident.incident_ID = i_c.incident.incident_ID and i_a.itinerarytype = 0 ";
						hasc = true;
					} else {
						// orignating station + terminating station
						sqlselect = "from "
								+ "com.bagnet.nettracer.tracing.db.Itinerary i_a, "
								+ "com.bagnet.nettracer.tracing.db.Itinerary i_b where "
								+ " i_a.incident.incident_ID = i_b.incident.incident_ID and i_a.itinerarytype = 0 ";
					}
				} else {
					// orignating station + transfer station
					if (srDTO.getT_stationcode() != null
							&& srDTO.getT_stationcode().length() > 0) {
						sb.append(
								" and ((i_b.legfrom = :t_stationcode and i_b.legfrom_type = ")
								.append(TracingConstants.LEG_T_STATION)
								.append(") ");
						sb.append(
								" or (i_b.legto = :t_stationcode and i_b.legto_type = ")
								.append(TracingConstants.LEG_T_STATION)
								.append(")) ");
						sqlselect = "from "
								+ "com.bagnet.nettracer.tracing.db.Itinerary i_a, "
								+ "com.bagnet.nettracer.tracing.db.Itinerary i_b where "
								+ "  i_a.incident.incident_ID = i_b.incident.incident_ID and i_a.itinerarytype = 0 ";
						hasb = true;
					}
				}
			} else {
				// terminating station
				if (srDTO.getE_stationcode() != null
						&& srDTO.getE_stationcode().length() > 0) {
					sb.append(
							" and (i_a.legto = :e_stationcode and i_a.legto_type = ")
							.append(TracingConstants.LEG_E_STATION)
							.append(") ");
					// terminating station + transfer station
					if (srDTO.getT_stationcode() != null
							&& srDTO.getT_stationcode().length() > 0) {
						sb.append(
								" and ((i_b.legfrom = :t_stationcode and i_b.legfrom_type = ")
								.append(TracingConstants.LEG_T_STATION)
								.append(") ");
						sb.append(
								" or (i_b.legto = :t_stationcode and i_b.legto_type = ")
								.append(TracingConstants.LEG_T_STATION)
								.append(")) ");
						sqlselect = "from "
								+ "com.bagnet.nettracer.tracing.db.Itinerary i_a, "
								+ "com.bagnet.nettracer.tracing.db.Itinerary i_b where "
								+ " i_a.incident.incident_ID = i_b.incident.incident_ID and i_a.itinerarytype = 0 ";
						hasb = true;
					}
				} else {
					// transfer station only
					if (srDTO.getT_stationcode() != null
							&& srDTO.getT_stationcode().length() > 0) {
						sb.append(
								" and ((i_a.legfrom = :t_stationcode and i_a.legfrom_type = ")
								.append(TracingConstants.LEG_T_STATION)
								.append(") ");
						sb.append(
								" or (i_a.legto = :t_stationcode and i_a.legto_type = ")
								.append(TracingConstants.LEG_T_STATION)
								.append(")) ");
					}
				}
			}
			stationq = sb.toString();

			// fault station and loss code
			String faultq = "";
			if (srDTO.getFaultstation_ID() != null
					&& !srDTO.getFaultstation_ID()[0].equals("0")) {
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
			Date stime = null; // time to compare (04:00 if eastern, for
								// example)
			String dateq = "";

			ArrayList dateal = null;
			if ((dateal = calculateDateDiff(srDTO, tz, user)) == null) {
				return null;
			}
			sdate = (Date) dateal.get(0);
			sdate1 = (Date) dateal.get(1);
			edate = (Date) dateal.get(2);
			edate1 = (Date) dateal.get(3);
			stime = (Date) dateal.get(4);

			parameters.put("sdate", TracerUtils.getText("reports.dates", user));
			if (sdate != null && edate != null) {
				parameters.put("sdate", srDTO.getStarttime());
				if (sdate.equals(edate)) {
					// need to add the timezone diff here
					dateq = " and ((i_a.incident.createdate= :startdate and i_a.incident.createtime >= :starttime) "
							+ " or (i_a.incident.createdate= :startdate1 and i_a.incident.createtime <= :starttime))";

					edate = null;
				} else {

					// first get the beginning and end dates using date and
					// time, then get
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
			if (srDTO.getDepartdate() != null
					&& srDTO.getDepartdate().length() > 0) {
				ddate = DateUtils.convertToDate(srDTO.getDepartdate(), user
						.getDateformat().getFormat(), null);
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
			if (srDTO.getArrivaldate() != null
					&& srDTO.getArrivaldate().length() > 0) {
				adate = DateUtils.convertToDate(srDTO.getArrivaldate(), user
						.getDateformat().getFormat(), null);
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
					+ " i_a.incident.createdate,i_a.incident.createtime,i_a.incident.status.description,i_a.incident.itemtype.description "
					+ sqlselect
					+ mbrtypeq
					+ stationq
					+ faultq
					+ losscodeq
					+ statusq
					+ agentq
					+ dateq
					+ traveldateq
					+ companylimit
					+ " order by i_a.incident.itemtype.itemType_ID, i_a.incident.incident_ID ";
			Query q = sess.createQuery(sql);
			if (srDTO.getItemType_ID() >= 1)
				q.setInteger("itemType_ID", srDTO.getItemType_ID());
			if (srDTO.getStatus_ID() >= 1)
				q.setInteger("status_ID", srDTO.getStatus_ID());
			if (srDTO.getB_stationcode() != null
					&& srDTO.getB_stationcode().length() > 0)
				q.setString("b_stationcode", srDTO.getB_stationcode());
			if (srDTO.getT_stationcode() != null
					&& srDTO.getT_stationcode().length() > 0)
				q.setString("t_stationcode", srDTO.getT_stationcode());
			if (srDTO.getE_stationcode() != null
					&& srDTO.getE_stationcode().length() > 0)
				q.setString("e_stationcode", srDTO.getE_stationcode());

			if (srDTO.getFaultstation_ID() != null
					&& !srDTO.getFaultstation_ID()[0].equals("0"))
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

			q.setString("companyCode_ID", user.getStation().getCompany()
					.getCompanyCode_ID());

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
				sql = "select passenger.lastname,passenger.firstname "
						+ " from com.bagnet.nettracer.tracing.db.Passenger passenger where "
						+ " passenger.incident.incident_ID = :incident_id order by "
						+ " passenger.passenger_ID";

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

				// get passengers
				sql = "select itinerary.legfrom,itinerary.flightnum,itinerary.legto "
						+ " from com.bagnet.nettracer.tracing.db.Itinerary itinerary where "
						+ " itinerary.incident.incident_ID = :incident_id and itinerary.itinerarytype = 0 order by "
						+ " itinerary.itinerary_ID";

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
				parameters.put("status",
						TracerUtils.getText("reports.all", user));
			}

			return getReportFile(list2, parameters, reportname, rootpath,
					srDTO.getOutputtype());
		} catch (Exception e) {
			logger.error("unable to create report " + e);
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	private String create_station_rpt(StatReportDTO srDTO, int reportnum,
			String reportname, String reporttitle) throws HibernateException {
		Session sess = HibernateWrapper.getDirtySession().openSession();
		try {
			boolean hasb = false, hasc = false;

			TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(
					user.getDefaulttimezone()).getTimezone());

			Map parameters = new HashMap();
			ResourceBundle myResources = ResourceBundle
					.getBundle(
							"com.bagnet.nettracer.tracing.resources.ApplicationResources",
							new Locale(user.getCurrentlocale()));
			parameters.put("REPORT_RESOURCE_BUNDLE", myResources);

			parameters.put("title", reporttitle);
			String sqlselect = "from com.bagnet.nettracer.tracing.db.Incident i_a left outer join i_a.itinerary as itin where 1=1 "; // left
																																		// outer
																																		// join
																																		// i_a.itinerary
																																		// as
																																		// itin
			String companylimit = " and i_a.stationassigned.company.companyCode_ID = :companyCode_ID ";
			// mbr report type
			String mbrtypeq = "";
			if (srDTO.getItemType_ID() >= 1) {
				mbrtypeq = " and i_a.itemtype.itemType_ID = :itemType_ID ";
			}

			StringBuffer sb = new StringBuffer(512);
			String stationq = "";
			// orignating station
			if (srDTO.getB_stationcode() != null
					&& srDTO.getB_stationcode().length() > 0) {
				sb.append(
						" and (itin.legfrom = :b_stationcode and itin.legfrom_type = ")
						.append(TracingConstants.LEG_B_STATION).append(") ");
				// terminating station
				if (srDTO.getE_stationcode() != null
						&& srDTO.getE_stationcode().length() > 0) {
					sb.append(
							" and (i_b.legto = :e_stationcode and i_b.legto_type = ")
							.append(TracingConstants.LEG_E_STATION)
							.append(") ");
					hasb = true;
					// orignating station + terminating station + transfer
					// station
					if (srDTO.getT_stationcode() != null
							&& srDTO.getT_stationcode().length() > 0) {
						sb.append(
								" and ((i_c.legfrom = :t_stationcode and i_c.legfrom_type = ")
								.append(TracingConstants.LEG_T_STATION)
								.append(") ");
						sb.append(
								" or (i_c.legto = :t_stationcode and i_c.legto_type = ")
								.append(TracingConstants.LEG_T_STATION)
								.append(")) ");
						sqlselect = "from "
								+ "com.bagnet.nettracer.tracing.db.Incident i_a left outer join i_a.Itinerary as itin, "
								+ "com.bagnet.nettracer.tracing.db.Itinerary i_b, "
								+ "com.bagnet.nettracer.tracing.db.Itinerary i_c "
								+ "where i_a.incident_ID = i_b.incident.incident_ID and"
								+ " i_a.incident_ID = i_c.incident.incident_ID and itin.itinerarytype = 0 ";
						hasc = true;
					} else {
						// orignating station + terminating station
						sqlselect = "from "
								+ "com.bagnet.nettracer.tracing.db.Incident i_a left outer join i_a.Itinerary as itin, "
								+ "com.bagnet.nettracer.tracing.db.Itinerary i_b where "
								+ " i_a.incident_ID = i_b.incident.incident_ID and itin.itinerarytype = 0 ";
					}
				} else {
					// orignating station + transfer station
					if (srDTO.getT_stationcode() != null
							&& srDTO.getT_stationcode().length() > 0) {
						sb.append(
								" and ((i_b.legfrom = :t_stationcode and i_b.legfrom_type = ")
								.append(TracingConstants.LEG_T_STATION)
								.append(") ");
						sb.append(
								" or (i_b.legto = :t_stationcode and i_b.legto_type = ")
								.append(TracingConstants.LEG_T_STATION)
								.append(")) ");
						sqlselect = "from "
								+ "com.bagnet.nettracer.tracing.db.Incident i_a left outer join i_a.Itinerary as itin, "
								+ "com.bagnet.nettracer.tracing.db.Itinerary i_b where "
								+ "  i_a.incident_ID = i_b.incident.incident_ID and itin.itinerarytype = 0 ";
						hasb = true;
					}
				}
			} else {
				// terminating station
				if (srDTO.getE_stationcode() != null
						&& srDTO.getE_stationcode().length() > 0) {
					sb.append(
							" and (itin.legto = :e_stationcode and itin.legto_type = ")
							.append(TracingConstants.LEG_E_STATION)
							.append(") ");
					// terminating station + transfer station
					if (srDTO.getT_stationcode() != null
							&& srDTO.getT_stationcode().length() > 0) {
						sb.append(
								" and ((i_b.legfrom = :t_stationcode and i_b.legfrom_type = ")
								.append(TracingConstants.LEG_T_STATION)
								.append(") ");
						sb.append(
								" or (i_b.legto = :t_stationcode and i_b.legto_type = ")
								.append(TracingConstants.LEG_T_STATION)
								.append(")) ");
						sqlselect = "from "
								+ "com.bagnet.nettracer.tracing.db.Itinerary itin, "
								+ "com.bagnet.nettracer.tracing.db.Itinerary i_b where "
								+ " i_a.incident_ID = i_b.incident.incident_ID and itin.itinerarytype = 0 ";
						hasb = true;
					}
				} else {
					// transfer station only
					if (srDTO.getT_stationcode() != null
							&& srDTO.getT_stationcode().length() > 0) {
						sb.append(
								" and ((itin.legfrom = :t_stationcode and itin.legfrom_type = ")
								.append(TracingConstants.LEG_T_STATION)
								.append(") ");
						sb.append(
								" or (itin.legto = :t_stationcode and itin.legto_type = ")
								.append(TracingConstants.LEG_T_STATION)
								.append(")) ");
					}
				}
			}
			stationq = sb.toString();

			// logger.error("entire stationq=" + stationq);

			// fault station and loss code
			String faultq = "";
			/*
			 * if (srDTO.getFaultstation_ID() != null &&
			 * !srDTO.getFaultstation_ID()[0].equals("0")) { faultq =
			 * " and i_a.incident.faultstation.station_ID in (:fault_ID) "; }
			 */

			// BEGIN NEW
			String intc = "";
			if (srDTO.getFaultstation_ID() != null) {
				for (int i = 0; i < srDTO.getFaultstation_ID().length; i++) {
					intc += srDTO.getFaultstation_ID()[i] + ",";
				}
			}

			if (intc.length() > 0 && srDTO.getFaultstation_ID() != null
					&& !srDTO.getFaultstation_ID()[0].equals("0")) {
				intc = intc.substring(0, intc.length() - 1);
				faultq += " and i_a.faultstation.station_ID in (" + intc + ") ";
			}
			// END NEW

			String losscodeq = "";
			if (srDTO.getLoss_code() > 0) {
				losscodeq = " and i_a.loss_code = :loss_code";
			}

			String statusq = "";
			if (srDTO.getStatus_ID() >= 1) {
				statusq = " and i_a.status.status_ID= :status_ID ";
			}

			String agentq = "";
			if (srDTO.getAgent() != null && srDTO.getAgent().length() > 0) {
				agentq = " and i_a.agent.username like :agent_username ";
				parameters.put("agent_username", srDTO.getAgent());
			}

			Date sdate = null, edate = null;
			Date sdate1 = null, edate1 = null; // add one for timezone
			Date stime = null; // time to compare (04:00 if eastern, for
								// example)
			Date csdate = null, cedate = null;
			String dateq = "";
			String dateq2 = "";

			ArrayList dateal = null;
			if ((dateal = calculateDateDiff(srDTO, tz, user)) == null) {
				return null;
			}
			sdate = (Date) dateal.get(0);
			sdate1 = (Date) dateal.get(1);
			edate = (Date) dateal.get(2);
			edate1 = (Date) dateal.get(3);
			stime = (Date) dateal.get(4);
			csdate = (Date) dateal.get(5);
			cedate = (Date) dateal.get(6);

			parameters.put("sdate", TracerUtils.getText("reports.dates", user));
			if (sdate != null && edate != null) {
				parameters.put("sdate", srDTO.getStarttime());
				if (sdate.equals(edate)) {
					// need to add the timezone diff here
					dateq = " and ((i_a.createdate= :startdate and i_a.createtime >= :starttime) "
							+ " or (i_a.createdate= :startdate1 and i_a.createtime <= :starttime))";

					edate = null;
				} else {

					// first get the beginning and end dates using date and
					// time, then get
					// dates in between
					dateq = " and ((i_a.createdate= :startdate and i_a.createtime >= :starttime) "
							+ " or (i_a.createdate= :enddate1 and i_a.createtime <= :starttime)"
							+ " or (i_a.createdate > :startdate and i_a.createdate <= :enddate))";

					parameters.put("edate", srDTO.getEndtime());
				}
			} else if (sdate != null) {
				parameters.put("sdate", srDTO.getStarttime());
				dateq = " and ((i_a.createdate= :startdate and i_a.createtime >= :starttime) "
						+ " or (i_a.createdate= :startdate1 and i_a.createtime <= :starttime))";
				edate = null;
			}
			// Close dates
			if (csdate != null && cedate != null) {
				parameters.put("csdate", srDTO.getCstarttime());
				if (csdate.equals(cedate)) {
					// need to add the timezone diff here
					dateq2 = " and ((i_a.closedate= :clstartdate ) ";
					// +
					// " or (i_a.closedate= :clstartdate1 and i_a.closetime <= :clstarttime))";

					edate = null;
				} else {

					// first get the beginning and end dates using date and
					// time, then get
					// dates in between
					dateq2 = " and ((i_a.closedate= :clstartdate ) "
					// +
					// " or (i_a.createdate= :clenddate1 and i_a.createtime <= :clstarttime)"
							+ " or (i_a.closedate > :clstartdate and i_a.closedate <= :clenddate))";

					parameters.put("cedate", srDTO.getCendtime());
				}
			} else if (csdate != null) {
				parameters.put("clsdate", srDTO.getCstarttime());
				dateq2 = " and (i_a.closedate= :clstartdate ) ";
				// +
				// " or (i_a.closedate= :clstartdate1 and i_a.closetime <= :clstarttime))";
				cedate = null;
			}

			// travel date departdate and arrive date
			String traveldateq = "";
			Date ddate = null;
			Date adate = null;
			parameters.put("ddate", "Any Date");
			parameters.put("adate", "Any Date");
			if (srDTO.getDepartdate() != null
					&& srDTO.getDepartdate().length() > 0) {
				ddate = DateUtils.convertToDate(srDTO.getDepartdate(), user
						.getDateformat().getFormat(), null);
				if (ddate != null) {
					traveldateq = "and (itin.departdate = :departdate ";
					if (hasb)
						traveldateq += " or i_b.departdate = :departdate ";
					if (hasc)
						traveldateq += " or i_c.departdate = :departdate ";
					traveldateq += ") ";
					parameters.put("ddate", srDTO.getDepartdate());
				}
			}
			if (srDTO.getArrivaldate() != null
					&& srDTO.getArrivaldate().length() > 0) {
				adate = DateUtils.convertToDate(srDTO.getArrivaldate(), user
						.getDateformat().getFormat(), null);
				if (adate != null) {
					traveldateq += "and (itin.arrivedate = :arrivedate ";
					if (hasb)
						traveldateq += " or i_b.arrivedate = :arrivedate ";
					if (hasc)
						traveldateq += " or i_c.arrivedate = :arrivedate ";
					traveldateq += ") ";
					parameters.put("adate", srDTO.getArrivaldate());
				}
			}

			String sql = "select distinct i_a.stationassigned.station_ID,i_a.stationassigned.stationcode,"
					+ " i_a.faultstation.station_ID,i_a.faultstation.stationcode,"
					+ " i_a.incident_ID, i_a.itemtype.itemType_ID,i_a.loss_code,"
					+ " i_a.createdate,i_a.createtime,i_a.status.status_ID,i_a.itemtype.itemType_ID, i_a.closedate "
					+ sqlselect
					+ mbrtypeq
					+ stationq
					+ faultq
					+ losscodeq
					+ statusq
					+ agentq
					+ dateq
					+ dateq2
					+ traveldateq
					+ companylimit
					+ " order by i_a.itemtype.itemType_ID, i_a.incident_ID ";

			Query q = sess.createQuery(sql);

			// logger.error("station report final sql=" + sql);

			if (srDTO.getItemType_ID() >= 1)
				q.setInteger("itemType_ID", srDTO.getItemType_ID());
			if (srDTO.getStatus_ID() >= 1)
				q.setInteger("status_ID", srDTO.getStatus_ID());
			if (srDTO.getB_stationcode() != null
					&& srDTO.getB_stationcode().length() > 0)
				q.setString("b_stationcode", srDTO.getB_stationcode());
			if (srDTO.getT_stationcode() != null
					&& srDTO.getT_stationcode().length() > 0)
				q.setString("t_stationcode", srDTO.getT_stationcode());
			if (srDTO.getE_stationcode() != null
					&& srDTO.getE_stationcode().length() > 0)
				q.setString("e_stationcode", srDTO.getE_stationcode());

			// if (srDTO.getFaultstation_ID() != null &&
			// !srDTO.getFaultstation_ID()[0].equals("0"))
			// q.setParameterList("fault_ID", srDTO.getFaultstation_ID());
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

			if (csdate != null) {
				q.setDate("clstartdate", csdate);
			}

			if (cedate != null) {
				q.setDate("clenddate", cedate);
			}

			// traveldate
			if (ddate != null)
				q.setDate("departdate", ddate);
			if (adate != null)
				q.setDate("arrivedate", adate);

			q.setString("companyCode_ID", user.getStation().getCompany()
					.getCompanyCode_ID());

			q.setMaxResults(TracerProperties.getMaxReportRows() + 1);
			List list = q.list();
			if (list.size() == 0) {
				logger.debug("no data for report");
				return "";
			} else if (list.size() == TracerProperties.getMaxReportRows() + 1) {
				this.setErrormsg("error.maxdata");
				return "";
			}
			// populate station and status
			parameters.put("b_station", srDTO.getB_stationcode());
			parameters.put("t_station", srDTO.getT_stationcode());
			parameters.put("e_station", srDTO.getE_stationcode());

			// summary or detail; summary = 0; detail = 1
			if (srDTO.getSumordet() == 1) {
				parameters.put("showdetail", "1");
				parameters.put("reportStyle", "10");
			} else {
				parameters.put("reportStyle", "00");
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

				sr.setStatusdesc(TracerUtils.getText(
						Status.getKey((Integer) o[9]), user));
				sr.setTypedesc(TracerUtils.getText(
						ItemType.getKey((Integer) o[10]), user));
				sr.setClosedate((Date) o[11]);

				// get passengers
				sql = "select passenger.lastname,passenger.firstname "
						+ " from com.bagnet.nettracer.tracing.db.Passenger passenger where "
						+ " passenger.incident.incident_ID = :incident_id order by "
						+ " passenger.passenger_ID";

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

				// get passengers
				sql = "select itinerary.legfrom,itinerary.flightnum,itinerary.legto "
						+ " from com.bagnet.nettracer.tracing.db.Itinerary itinerary where "
						+ " itinerary.incident.incident_ID = :incident_id and itinerary.itinerarytype = 0 order by "
						+ " itinerary.itinerary_ID";

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
				parameters.put("status",
						TracerUtils.getText("reports.all", user));
			}

			// return getReportFile(list2, parameters, reportname, rootpath,
			// srDTO.getOutputtype());

			parameters.put("reportLocale", new Locale(user.getCurrentlocale()));
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(
					list2);

			return StationReportBMO.getReportFileDj(ds, parameters, reportname,
					rootpath, srDTO.getOutputtype(), req, this);
		} catch (Exception e) {
			logger.error("unable to create station report " + e);
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
	 * @return @throws HibernateException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String create_recovery_rpt(StatReportDTO srDTO, int reportnum,
			String reportname, String reporttitle) throws HibernateException {
		Session sess = HibernateWrapper.getDirtySession().openSession();
		try {
			Map parameters = new HashMap();
			ResourceBundle myResources = ResourceBundle
					.getBundle(
							"com.bagnet.nettracer.tracing.resources.ApplicationResources",
							new Locale(user.getCurrentlocale()));
			parameters.put("REPORT_RESOURCE_BUNDLE", myResources);

			TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(
					user.getDefaulttimezone()).getTimezone());

			// mbr report type
			String mbrtypeq = "";
			if (srDTO.getItemType_ID() >= 1) {
				mbrtypeq = " and incident.itemtype.itemType_ID = :itemType_ID ";
			}
			String stationq = "";
			if (srDTO.getStation_ID() != null
					&& !srDTO.getStation_ID()[0].equals("0")) {
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
			Date stime = null; // time to compare (04:00 if eastern, for
								// example)
			String dateq = "";

			ArrayList dateal = null;
			if ((dateal = calculateDateDiff(srDTO, tz, user)) == null) {
				return null;
			}
			sdate = (Date) dateal.get(0);
			sdate1 = (Date) dateal.get(1);
			edate = (Date) dateal.get(2);
			edate1 = (Date) dateal.get(3);
			stime = (Date) dateal.get(4);

			parameters.put("sdate", TracerUtils.getText("reports.dates", user));
			if (sdate != null && edate != null) {
				parameters.put("sdate", srDTO.getStarttime());
				if (sdate.equals(edate)) {
					// need to add the timezone diff here
					dateq = " and ((incident.createdate= :startdate and incident.createtime >= :starttime) "
							+ " or (incident.createdate= :startdate1 and incident.createtime <= :starttime))";

					edate = null;
				} else {

					// first get the beginning and end dates using date and
					// time, then get
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
				parameters.put(
						"title",
						reporttitle + " ("
								+ TracerUtils.getText("reports.by24hour", user)
								+ ")");
			} else {
				parameters.put("title", reporttitle);
			}

			// will use this for hibernate 3.0

			// String sql = "select
			// incident.stationcreated,count(incident.incident_ID)," +
			// "count(closeinc.incident_ID),incident.itemtype.itemType_ID " +
			// "from
			// com.bagnet.nettracer.tracing.db.Incident incident left outer join
			// " +
			// "com.bagnet.nettracer.tracing.db.Incident closeinc on
			// incident.incident_ID = closeinc.incident_ID and
			// closeinc.status.status_ID =
			// " + TracingConstants.STATUS_CLOSED + "
			// where 1=1 " + stationq + dateq + mbrtypeq + " group
			// byincident.stationcreated.stationcode " + " order by
			// incident.stationcreated.stationcode";

			String sql = "select "
					+ breakdown
					+ " incident.stationassigned.stationcode,count(incident.incident_ID)"
					+ " from com.bagnet.nettracer.tracing.db.Incident incident where 1=1 "
					+ stationq + dateq + agentq + mbrtypeq + companylimit
					+ " group by " + breakdown
					+ " incident.stationassigned.stationcode " + " order by "
					+ breakdown + " incident.stationassigned.stationcode";

			Query q = sess.createQuery(sql);
			if (srDTO.getItemType_ID() >= 1)
				q.setInteger("itemType_ID", srDTO.getItemType_ID());
			if (srDTO.getStation_ID() != null
					&& !srDTO.getStation_ID()[0].equals("0")) {
				// q.setParameterList("station_ID", srDTO.getStation_ID());
				String sArray[] = srDTO.getStation_ID();
				List<String> stationIdStringList = Arrays.asList(sArray);
				List<Integer> stationIdIntegerList = StringUtils
						.convertStringArrayList2IntegerArrayList(stationIdStringList);
				q.setParameterList("station_ID", stationIdIntegerList);
			}

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

			if (srDTO.getAgent() != null && srDTO.getAgent().length() > 0)
				q.setString("agent_username", srDTO.getAgent());

			q.setString("companyCode_ID", user.getStation().getCompany()
					.getCompanyCode_ID());
			List alllist = q.list();
			sql = "select "
					+ breakdown
					+ " incident.stationassigned.stationcode,count(incident.incident_ID) "
					+ "from com.bagnet.nettracer.tracing.db.Incident incident where incident.status.status_ID = "
					+ TracingConstants.MBR_STATUS_CLOSED + stationq + agentq
					+ dateq + mbrtypeq + companylimit + " group by "
					+ breakdown + " incident.stationassigned.stationcode "
					+ " order by " + breakdown
					+ " incident.stationassigned.stationcode";
			q = sess.createQuery(sql);
			if (srDTO.getItemType_ID() >= 1)
				q.setInteger("itemType_ID", srDTO.getItemType_ID());
			if (srDTO.getStation_ID() != null
					&& !srDTO.getStation_ID()[0].equals("0")) {
				// q.setParameterList("station_ID", srDTO.getStation_ID());
				String sArray[] = srDTO.getStation_ID();
				List<String> stationIdStringList = Arrays.asList(sArray);
				List<Integer> stationIdIntegerList = StringUtils
						.convertStringArrayList2IntegerArrayList(stationIdStringList);
				q.setParameterList("station_ID", stationIdIntegerList);
			}
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

			if (srDTO.getAgent() != null && srDTO.getAgent().length() > 0)
				q.setString("agent_username", srDTO.getAgent());

			q.setString("companyCode_ID", user.getStation().getCompany()
					.getCompanyCode_ID());
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
				num = ((Long) o[ss + 1]).intValue();
				numclose = 0;
				// find out the corresponding station with number of close count
				for (int j = 0; j < closelist.size(); j++) {
					o2 = (Object[]) closelist.get(j);
					int ss2 = 0;
					if (srDTO.getSumordet() == 1)
						ss2 = 1;

					if (srDTO.getSumordet() == 1) {
						if (sr.getStationcode().equals((String) o2[ss2])
								&& DateUtils.formatDate((Date) o[0],
										TracingConstants.DB_DATEFORMAT, null,
										null).equals(
										DateUtils.formatDate((Date) o2[0],
												TracingConstants.DB_DATEFORMAT,
												null, null))) {
							numclose = ((Long) o2[ss2 + 1]).intValue();
							break;

						}
					} else {
						if (sr.getStationcode().equals((String) o2[ss2])) {
							numclose = ((Long) o2[ss2 + 1]).intValue();
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
					ItemType it = IncidentUtils.retrieveItemTypeWithId(
							srDTO.getItemType_ID(), user.getCurrentlocale());
					reporttype = it.getDescription();
				}

				if (srDTO.getSumordet() == 1)
					sr.setCreatedate(DateUtils.formatDate((Date) o[0],
							TracingConstants.DB_DATEFORMAT, null, tz));

				list.add(sr);
			}
			if (totalnum != 0)
				parameters.put("ratiototal", new Double(
						(((double) totalnumclose) / totalnum) * 100));
			// populate station and status
			sr = (StatReport_RecoveryDTO) list.get(0);
			if (srDTO.getStation_ID() == null
					|| srDTO.getStation_ID()[0].equals("0")) {
				parameters.put("station",
						TracerUtils.getText("reports.stations", user));
			}

			if (reporttype != null)
				parameters.put("itemtype", reporttype);
			// return getReportFile(list, parameters, reportname, rootpath,
			// srDTO.getOutputtype());

			parameters.put("reportLocale", new Locale(user.getCurrentlocale()));
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(list);

			return RecoveryReportBMO.getReportFileDj(ds, parameters,
					reportname, rootpath, srDTO.getOutputtype(), req, this);
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
	 * @return @throws HibernateException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	private String create_crecovery_rpt(StatReportDTO srDTO, int reportnum,
			String reportname, String reporttitle) throws HibernateException {
		Session sess = HibernateWrapper.getDirtySession().openSession();
		try {
			Map parameters = new HashMap();
			ResourceBundle myResources = ResourceBundle
					.getBundle(
							"com.bagnet.nettracer.tracing.resources.ApplicationResources",
							new Locale(user.getCurrentlocale()));
			parameters.put("REPORT_RESOURCE_BUNDLE", myResources);
			TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(
					user.getDefaulttimezone()).getTimezone());

			// mbr report type
			String typebreakdown = "";
			String mbrtypeq = "";
			if (srDTO.getItemType_ID() >= 1) {
				typebreakdown = ", incident.itemtype.description";
				mbrtypeq = " and incident.itemtype.itemType_ID = :itemType_ID ";
			}
			String stationq = "";
			if (srDTO.getStation_ID() != null
					&& !srDTO.getStation_ID()[0].equals("0")) {
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
			Date stime = null; // time to compare (04:00 if eastern, for
								// example)
			String dateq = "";

			ArrayList dateal = null;
			if ((dateal = calculateDateDiff(srDTO, tz, user)) == null) {
				return null;
			}
			sdate = (Date) dateal.get(0);
			sdate1 = (Date) dateal.get(1);
			edate = (Date) dateal.get(2);
			edate1 = (Date) dateal.get(3);
			stime = (Date) dateal.get(4);

			parameters.put("sdate", TracerUtils.getText("reports.dates", user));
			if (sdate != null && edate != null) {
				parameters.put("sdate", srDTO.getStarttime());
				if (sdate.equals(edate)) {
					// need to add the timezone diff here
					dateq = " and ((incident.createdate= :startdate and incident.createtime >= :starttime) "
							+ " or (incident.createdate= :startdate1 and incident.createtime <= :starttime))";

					edate = null;
				} else {

					// first get the beginning and end dates using date and
					// time, then get
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

			csdate = (Date) dateal.get(5);
			cedate = (Date) dateal.get(6);

			parameters
					.put("csdate", TracerUtils.getText("reports.dates", user));
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

			String sql = "select incident.stationassigned.stationcode,count(incident.incident_ID)"
					+ breakdown
					+ typebreakdown
					+ " from com.bagnet.nettracer.tracing.db.Incident incident where incident.status.status_ID = "
					+ TracingConstants.MBR_STATUS_CLOSED
					+ stationq
					+ agentq
					+ dateq
					+ cdateq
					+ mbrtypeq
					+ companylimit
					+ " group by incident.stationassigned.stationcode"
					+ breakdown
					+ typebreakdown
					+ " order by "
					+ breakdownorder
					+ " incident.stationassigned.stationcode";

			Query q = sess.createQuery(sql);
			if (srDTO.getItemType_ID() >= 1)
				q.setInteger("itemType_ID", srDTO.getItemType_ID());
			if (srDTO.getStation_ID() != null
					&& !srDTO.getStation_ID()[0].equals("0")) {
				// q.setParameterList("station_ID", srDTO.getStation_ID());
				String sArray[] = srDTO.getStation_ID();
				List<String> stationIdStringList = Arrays.asList(sArray);
				List<Integer> stationIdIntegerList = StringUtils
						.convertStringArrayList2IntegerArrayList(stationIdStringList);
				q.setParameterList("station_ID", stationIdIntegerList);
			}

			if (srDTO.getAgent() != null && srDTO.getAgent().length() > 0)
				q.setString("agent_username", srDTO.getAgent());

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
			// close date
			if (csdate != null)
				q.setDate("cstartdate", csdate);
			if (cedate != null)
				q.setDate("cenddate", cedate);

			q.setString("companyCode_ID", user.getStation().getCompany()
					.getCompanyCode_ID());
			q.setMaxResults(TracerProperties.getMaxReportRows() + 1);
			List alllist = q.list();

			if (alllist.size() == 0) {
				logger.debug("no data for report");
				return "";
			} else if (alllist.size() == TracerProperties.getMaxReportRows() + 1) {
				this.setErrormsg("error.maxdata");
				return "";
			}

			Object[] o = null, o2 = null;
			StatReport_RecoveryDTO sr = null;
			int num;
			String reporttype = null;
			int totalnum = 0;
			ArrayList list = new ArrayList();
			// String laststationcode = null;
			String lastclosedate = null;
			String temps = null, tempc = null;

			TreeMap hm = new TreeMap();

			for (int i = 0; i < alllist.size(); i++) {
				o = (Object[]) alllist.get(i);
				temps = (String) o[0];
				num = ((Long) o[1]).intValue();

				if (breakdown.length() > 0) {
					// tempc = DateUtils.formatDate((String) o[2],
					// TracingConstants.DB_DATETIMEFORMAT,
					// TracingConstants.DB_DATEFORMAT, null, tz);
					tempc = DateUtils.formatDate((Date) o[2],
							TracingConstants.DB_DATEFORMAT, null, tz);
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
						if (entryset != null && entryset.size() > 0) {
							// loop through hashmap stations
							for (Iterator ite = entryset.iterator(); ite
									.hasNext();) {
								String sta = (String) ite.next();
								sr = new StatReport_RecoveryDTO();
								sr.setStationcode(sta);
								if (breakdown.length() > 0)
									sr.setClosedate(lastclosedate);
								sr.setReportsclosed(((Integer) hm.get(sta))
										.intValue());
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
				if (hm.containsKey(temps))
					totalnum = ((Integer) hm.get(temps)).intValue() + num;
				hm.put(temps, new Integer(totalnum));
			}

			// come out the loop, go ahead and set the last sets of list
			Set entryset = hm.keySet();
			if (entryset != null && entryset.size() > 0) {
				// loop through hashmap stations
				for (Iterator ite = entryset.iterator(); ite.hasNext();) {
					String sta = (String) ite.next();
					sr = new StatReport_RecoveryDTO();
					sr.setStationcode(sta);
					if (breakdown.length() > 0)
						sr.setClosedate(lastclosedate);
					sr.setReportsclosed(((Integer) hm.get(sta)).intValue());
					list.add(sr);
				}
			}

			// populate station and status
			if (list != null && list.size() > 0) {
				if (srDTO.getStation_ID() == null
						|| srDTO.getStation_ID()[0].equals("0")) {
					parameters.put("station",
							TracerUtils.getText("reports.stations", user));
				}
			}

			if (reporttype != null)
				parameters.put("itemtype", reporttype);
			// return getReportFile(list, parameters, reportname, rootpath,
			// srDTO.getOutputtype());

			parameters.put("reportLocale", new Locale(user.getCurrentlocale()));
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(list);

			return ClosedRecoveryReportBMO.getReportFileDj(ds, parameters,
					reportname, rootpath, srDTO.getOutputtype(), req, this);
		} catch (Exception e) {
			logger.error("unable to create report " + e);
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
		}
	}

	private String create_onhand_rpt(StatReportDTO srDTO, int reportnum,
			String reportname, String reporttitle) throws HibernateException {
		return this.create_onhand_rpt(srDTO, reportnum, reportname,
				reporttitle, false);
	}
	
	public String create_earlyBag_rpt(StatReportDTO srDTO, int reportnum,
			String reportname, String reporttitle) throws HibernateException {
		return this.create_onhand_rpt(srDTO, reportnum, reportname,
				reporttitle, true);
	}

	public String create_dispute_resolution_rpt(StatReportDTO srDTO,
			int reportnum, String reportname, String reporttitle)
			throws HibernateException {
		// return this.create_onhand_rpt(srDTO, reportnum, reportname,
		// reporttitle, true);
		return this.create_dispute_resolution_rpt(srDTO, reportnum, reportname,
				reporttitle, true);
	}

	// dispute resolution report begins
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	private String create_dispute_resolution_rpt(StatReportDTO srDTO,
			int reportnum, String reportname, String reporttitle,
			boolean earlyBag) throws HibernateException {
		Session sess = HibernateWrapper.getDirtySession().openSession();
		try {
			Map parameters = new HashMap();
			ResourceBundle myResources = ResourceBundle
					.getBundle(
							"com.bagnet.nettracer.tracing.resources.ApplicationResources",
							new Locale(user.getCurrentlocale()));
			parameters.put("REPORT_RESOURCE_BUNDLE", myResources);

			parameters.put("title", reporttitle);
			// Criteria cri = sess.createCriteria(Incident.class);

			TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(
					user.getDefaulttimezone()).getTimezone());

			/*************** use direct jdbc sql statement **************/
			// Connection conn = sess.connection();
			// Statement stmt = conn.createStatement();
			//ResultSet rs = null;
			String sql = "select * from dispute d where 1=1";

			sql = "select i.createdate, d.created_timestamp,d.resolution_timestamp,d.Incident_ID,d.status_ID,"
					+ " dagent.username disputingagentusername,wagent.username workingagentusername,"
					+ " d.typeOfChange,"
					+ " d.beforeDisputeLossCode,prestation.stationcode beforedisputefaultstation,"
					+ " d.suggestedLossCode,suggestedstation.stationcode suggestedfaultstation,"
					+ " d.determinedLossCode,d.determined_station_ID,determinedfaultstation.stationcode newfaultstation"
					+ " from dispute d,agent dagent,agent wagent,station prestation,station suggestedstation,station determinedfaultstation, incident i"
					+ " where 1=1"
					+ " and d.dispute_agent_ID = dagent.Agent_ID"
					+ " and d.resolution_agent_ID = wagent.Agent_ID"
					+ " and d.before_dispute_fault_station_ID = prestation.Station_ID"
					+ " and d.suggested_station_ID = suggestedstation.Station_ID"
					+ " and d.determined_station_ID = determinedfaultstation.Station_ID"
					+ " and d.Incident_ID = i.Incident_ID";

			String intc = "";
			if (srDTO.getStation_ID() != null) {
				for (int i = 0; i < srDTO.getStation_ID().length; i++) {
					intc += srDTO.getStation_ID()[i] + ",";
				}
			}

			if (intc.length() > 0 && srDTO.getStation_ID() != null
					&& !srDTO.getStation_ID()[0].equals("0")) {
				intc = intc.substring(0, intc.length() - 1);
				sql += " and d.determined_station_ID in (" + intc + ") ";
			}

			String intPreviousFaultStation = "";
			if (srDTO.getPreviousFaultStation_ID() != null) {
				for (int i = 0; i < srDTO.getPreviousFaultStation_ID().length; i++) {
					intPreviousFaultStation += srDTO
							.getPreviousFaultStation_ID()[i] + ",";
				}
			}

			if (intPreviousFaultStation.length() > 0
					&& srDTO.getPreviousFaultStation_ID() != null
					&& !srDTO.getPreviousFaultStation_ID()[0].equals("0")) {
				intPreviousFaultStation = intPreviousFaultStation.substring(0,
						intPreviousFaultStation.length() - 1);
				sql += " and d.before_dispute_fault_station_ID in ("
						+ intPreviousFaultStation + ") ";
			}

			if (srDTO.getAgent() != null
					&& srDTO.getAgent().trim().length() > 0) {
				String agent = srDTO.getAgent();
				agent = agent.replaceAll("\'", "");
				sql += " and dagent.username = \'" + agent + "\' ";
			}

			// logger.error("sql segment is : " + sql);

			Date sdate = null, edate = null;
			Date sdate1 = null, edate1 = null; // add one for timezone
			Date stime = null; // time to compare (04:00 if eastern, for
								// example)

			String simpleDateQ = "";

			ArrayList dateal = null;
			if ((dateal = calculateDateDiff(srDTO, tz, user)) == null) {
				return null;
			}
			sdate = (Date) dateal.get(0);
			sdate1 = (Date) dateal.get(1);
			edate = (Date) dateal.get(2);
			edate1 = (Date) dateal.get(3);
			stime = (Date) dateal.get(4);

			parameters.put("sdate", TracerUtils.getText("reports.dates", user));
			if (sdate != null && edate != null) {
				parameters.put("sdate", srDTO.getStarttime());
				if (sdate.equals(edate)) {
					// need to add the timezone diff here

				} else {

					// first get the beginning and end dates using date and
					// time, then get
					// dates in between
					parameters.put("edate", srDTO.getEndtime());
				}
				simpleDateQ += " and d.created_timestamp >= '"
						+ DateUtils.formatDate(sdate, TracingConstants
								.getDBDateFormat(HibernateWrapper.getConfig()
										.getProperties()), null, null)
						+ " "
						+ DateUtils.formatDate(stime, TracingConstants
								.getDBTimeFormat(HibernateWrapper.getConfig()
										.getProperties(), true), null, null)
						+ "' and d.created_timestamp <= '"
						+ DateUtils.formatDate(edate1, TracingConstants
								.getDBDateFormat(HibernateWrapper.getConfig()
										.getProperties()), null, null)
						+ " "
						+ DateUtils.formatDate(stime, TracingConstants
								.getDBTimeFormat(HibernateWrapper.getConfig()
										.getProperties(), true), null, null)
						+ "'";

			} else if (sdate != null) {
				parameters.put("sdate", srDTO.getStarttime());
				edate = null;

				simpleDateQ += " and d.created_timestamp >= '"
						+ DateUtils.formatDate(sdate, TracingConstants
								.getDBDateFormat(HibernateWrapper.getConfig()
										.getProperties()), null, null)
						+ " "
						+ DateUtils.formatDate(stime, TracingConstants
								.getDBTimeFormat(HibernateWrapper.getConfig()
										.getProperties(), true), null, null)
						+ "'";
			}

			sql += simpleDateQ;

			// sql += " order by d.determined_station_ID,d.created_timestamp";

			// rs = stmt.executeQuery(sql);

			// put in date and timeformat and timezone of current user

			Object[] o = null, o2 = null, o3 = null;
			DisputeResolutionReportDTO sr = null;
			List disputeList = new ArrayList();
			Object[] row;

			SQLQuery query = sess.createSQLQuery(sql);
			List results=query.list();
			//while (rs.next()) {
			for(int i=0;i<results.size();++i){
				row=(Object[])results.get(i);
				sr = new DisputeResolutionReportDTO();

				sr.setDate_incident_created((Date) row[0]);
				sr.setDate_created((Date) row[1]);
				if(row[2]!=null){
					sr.setDate_resolved((Date) row[2]);
				}
				sr.setIncident_id((String)row[3]);
				sr.setStatus((Integer)row[4]);
				sr.setStatusDesc(TracerUtils.getText(
						Status.getKey((Integer) sr.getStatus()), user));
				sr.setDisputeAgentName((String)row[5]);
				sr.setWorkingAgentName((String)row[6]);
				sr.setTypeOfChange((String)row[7]);

				sr.setPreviousFaultCode((Integer)row[8]);
				sr.setBeforeDisputeFaultStation((String)row[9]);

				sr.setSuggestedFaultCode((Integer)row[10]);
				sr.setSuggestedFaultStation((String)row[11]);

				sr.setNewFaultCode((Integer)row[12]);
				sr.setNewFaultStation((String)row[14]);

				sr.set_DATEFORMAT(user.getDateformat().getFormat());
				sr.set_TIMEFORMAT(user.getTimeformat().getFormat());
				sr.set_TIMEZONE(tz);

				disputeList.add(sr);
			}

			// stmt.close();
			//rs.close();

			if (sr == null) {
				logger.debug("no data for report");
				return "";
			}

			parameters.put("reportLocale", new Locale(user.getCurrentlocale()));
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(
					disputeList);

			// logger.error(">>>>>finished retrieving dispute data for reporting!!!");
			return DisputeResolutionReportBMO.getReportFileDj(ds, parameters,
					reportname, rootpath, srDTO.getOutputtype(), req, this);
		} catch (Exception e) {
			logger.error("unable to create report " + e);
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
		}
	} // end of dispute resolution report

	// fraud valuation report begins
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	public String create_fraud_valuation_rpt(StatReportDTO srDTO,
			String reportname, String reporttitle) throws HibernateException {
		Session sess = HibernateWrapper.getDirtySession().openSession();
		try {
			Map parameters = new HashMap();
			ResourceBundle myResources = ResourceBundle
					.getBundle(
							"com.bagnet.nettracer.tracing.resources.ApplicationResources",
							new Locale(user.getCurrentlocale()));
			parameters.put("REPORT_RESOURCE_BUNDLE", myResources);

			parameters.put("title", reporttitle);

			TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(
					user.getDefaulttimezone()).getTimezone());

			/*************** use direct jdbc sql statement **************/
			// Connection conn = sess.connection();
			// Statement stmt = conn.createStatement();
			//ResultSet rs = null;
			String sql = "select c.id, i.airlineIncidentId, c.claimDate, c.amountClaimed, c.amountClaimedCurrency, c.amountPaid, c.amountPaidCurrency, s.description "
					+ "from fsfile f, fsclaim c, fsincident i, status s where 1=1 ";

			Date sdate = null, edate = null;
			Date sdate1 = null, edate1 = null; // add one for timezone
			Date stime = null; // time to compare (04:00 if eastern, for
								// example)

			String simpleDateQ = "";

			ArrayList dateal = null;
			if ((dateal = calculateDateDiff(srDTO, tz, user)) == null) {
				return null;
			}
			sdate = (Date) dateal.get(0);
			sdate1 = (Date) dateal.get(1);
			edate = (Date) dateal.get(2);
			edate1 = (Date) dateal.get(3);
			stime = (Date) dateal.get(4);

			parameters.put("sdate", TracerUtils.getText("reports.dates", user));
			if (sdate != null && edate != null) {
				parameters.put("sdate", srDTO.getStarttime());
				if (!sdate.equals(edate)) {
					parameters.put("edate", srDTO.getEndtime());
				}
				simpleDateQ += " and c.claimDate >= '"
						+ DateUtils.formatDate(sdate, TracingConstants
								.getDBDateFormat(HibernateWrapper.getConfig()
										.getProperties()), null, null)
						+ " "
						+ DateUtils.formatDate(stime, TracingConstants
								.getDBTimeFormat(HibernateWrapper.getConfig()
										.getProperties(), true), null, null)
						+ "' and c.claimDate <= '"
						+ DateUtils.formatDate(edate1, TracingConstants
								.getDBDateFormat(HibernateWrapper.getConfig()
										.getProperties()), null, null)
						+ " "
						+ DateUtils.formatDate(stime, TracingConstants
								.getDBTimeFormat(HibernateWrapper.getConfig()
										.getProperties(), true), null, null)
						+ "'";

			} else if (sdate != null) {
				parameters.put("sdate", srDTO.getStarttime());
				edate = null;

				simpleDateQ += " and c.claimDate >= '"
						+ DateUtils.formatDate(sdate, TracingConstants
								.getDBDateFormat(HibernateWrapper.getConfig()
										.getProperties()), null, null)
						+ " "
						+ DateUtils.formatDate(stime, TracingConstants
								.getDBTimeFormat(HibernateWrapper.getConfig()
										.getProperties(), true), null, null)
						+ "'";
			}

			sql += simpleDateQ;

			sql += " and c.file_id = f.id and c.statusId = s.Status_ID and f.id = i.file_id";

			// rs = stmt.executeQuery(sql);
			
			FraudValuationReportDTO sr = null;
			List claimList = new ArrayList();
			List<String> idList = new ArrayList<String>();

			SQLQuery query = sess.createSQLQuery(sql);
			List results=query.list();
			Object[] row;
			//while (rs.next()) {
			for(int i=0;i<results.size();i++){
				row=(Object[])results.get(i);
				sr = new FraudValuationReportDTO();

				sr.setClaimID(((Number)row[0]).toString());
				sr.setIncidentID((String)row[1]);
				sr.setClaimDate((Date)row[2]);
				sr.setStatus((String)row[7]);
				sr.setAmountClaimed(((Double)row[3]).toString());
				sr.setAmountClaimedCurrency((String)row[4]);
				sr.setAmountPaid(((Double)row[5]).toString());
				sr.setAmountPaidCurrency((String)row[6]);

				sr.set_DATEFORMAT(user.getDateformat().getFormat());
				sr.set_TIMEZONE(tz);

				claimList.add(sr);
				idList.add(((Number)row[0]).toString());
			}

			// stmt.close();
			//rs.close();

			if (sr == null) {
				logger.debug("no data for report");
				return "";
			}

			Map<String, Integer> matches = null;
			Context ctx = null;
			ClaimClientRemote remote = null;
			try {
				ctx = ConnectionUtil.getInitialContext();
				remote = (ClaimClientRemote) ConnectionUtil
						.getRemoteEjb(
								ctx,
								PropertyBMO
										.getValue(PropertyBMO.CENTRAL_FRAUD_SERVICE_NAME));
				if (remote != null) {
					matches = remote.getMatches(idList,
							user.getCompanycode_ID());
				}
			} catch (Exception e) {
				logger.error(e, e);
			} finally {
				if (ctx != null) {
					ctx.close();
				}
			}

			if (matches != null) {
				for (int i = 0; i < claimList.size(); i++) {
					sr = (FraudValuationReportDTO) claimList.get(i);
					if (matches.containsKey(sr.getClaimID())) {
						sr.setMatches(matches.get(sr.getClaimID()));
						claimList.set(i, sr);
					}
				}
			}

			parameters.put("reportLocale", new Locale(user.getCurrentlocale()));
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(
					claimList);

			return FraudValuationReportBMO.getReportFileDj(ds, parameters,
					reportname, rootpath, srDTO.getOutputtype(), req, this);
		} catch (Exception e) {
			logger.error("unable to create report " + e);
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
		}
	} // end of fraud valuation report


	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	private String create_onhand_rpt(StatReportDTO srDTO, int reportnum,
			String reportname, String reporttitle, boolean earlyBag)
			throws HibernateException {
		// Session sess = HibernateWrapper.getDirtySession().openSession();
		ArrayList toReturn = null;
		Session session = null;

		try {
			Map parameters = new HashMap();
			ResourceBundle myResources = ResourceBundle
					.getBundle(
							"com.bagnet.nettracer.tracing.resources.ApplicationResources",
							new Locale(user.getCurrentlocale()));
			parameters.put("REPORT_RESOURCE_BUNDLE", myResources);

			parameters.put("title", reporttitle);
			// Criteria cri = sess.createCriteria(Incident.class);

			TimeZone tz = TimeZone.getTimeZone(AdminUtils.getTimeZoneById(
					user.getDefaulttimezone()).getTimezone());

			/*************** use direct jdbc sql statement **************/
			// Connection conn = sess.connection();
			// Statement stmt = conn.createStatement();
			// ResultSet rs = null;
			String sql = "select ohd.found_station_ID,s1.stationcode as fscode,holding_station_ID,s2.stationcode as hscode, "
					+ "ohd.OHD_ID,ohd.founddate,ohd.foundtime,st1.status_ID as stdesc, st2.status_ID as bdesc,"
					+ "p.firstname,p.lastname,it.legfrom,it.legto,it.flightnum,s3.stationcode as faultstationcode,cic.loss_code "
					+ "from station s1,station s2,status st1,agent,ohd "
					+ "left outer join ohd_passenger p on p.OHD_ID = ohd.OHD_ID "
					+ "left outer join ohd_itinerary it on it.OHD_ID = ohd.OHD_ID "
					+ "left outer join station s3 on s3.station_ID = ohd.faultStation_ID "
					+ "left outer join company_irregularity_codes cic on cic.code_id = ohd.loss_code "
					+ "left outer join status st2 on st2.status_ID=ohd.disposal_status_ID "
					+ "where s1.station_ID = ohd.found_station_ID and s2.station_ID = ohd.holding_station_ID "
					+ "and st1.status_ID = ohd.status_ID and agent.Agent_ID = ohd.agent_ID and " // and
																									// st2.status_ID
																									// =
																									// ohd.disposal_status_ID
					+ "s1.companycode_ID = '"
					+ user.getStation().getCompany().getCompanyCode_ID() + "' ";

			// if (srDTO.getStatus_ID() >= 1)
			// sql += " and ohd.status_ID=" + srDTO.getStatus_ID();
			String dispositionIdList = "";
			String statusIdList = "";
			String myOhdSubtitleStatus = "";
			String myOhdDispositionStatus = "";
			if (srDTO.getStatus_id_combo() != null) {
				for (int i = 0; i < srDTO.getStatus_id_combo().length; i++) {
					statusIdList += srDTO.getStatus_id_combo()[i] + ",";
					myOhdSubtitleStatus += TracerUtils.getText(
							Status.getKey(srDTO.getStatus_id_combo()[i]), user)
							+ ",";
				}

				int statusIdListLength = statusIdList.length();
				if (statusIdListLength > 0) {
					if (srDTO.getStatus_id_combo()[0].intValue() >= 1) {
						statusIdList = statusIdList.substring(0,
								statusIdListLength - 1);
						sql += " and ohd.status_ID in (" + statusIdList + ") ";

						myOhdSubtitleStatus = myOhdSubtitleStatus.substring(0,
								myOhdSubtitleStatus.length() - 1);
					} else {
						myOhdSubtitleStatus = "";
					}
				}
			}

			if (srDTO.getDisposition_id_combo() != null) {
				for (int i = 0; i < srDTO.getDisposition_id_combo().length; i++) {
					dispositionIdList += srDTO.getDisposition_id_combo()[i]
							+ ",";
					myOhdDispositionStatus += TracerUtils.getText(
							Status.getKey(srDTO.getDisposition_id_combo()[i]),
							user) + ",";
				}

				int dispositionIdListLength = dispositionIdList.length();
				if (dispositionIdListLength > 0) {
					if (srDTO.getDisposition_id_combo()[0].intValue() >= 1) {
						dispositionIdList = dispositionIdList.substring(0,
								dispositionIdListLength - 1);
						sql += " and ohd.disposal_status_id in ("
								+ dispositionIdList + ") ";

						myOhdDispositionStatus = myOhdDispositionStatus
								.substring(0,
										myOhdDispositionStatus.length() - 1);
					} else {
						myOhdDispositionStatus = "";
					}
				}
			}

			parameters.put("ohd_subtitle_status", myOhdSubtitleStatus);

			if (earlyBag) {
				sql += " and ohd.earlyBag = true ";
			}

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

			if (intc.length() > 0 && srDTO.getStation_ID() != null
					&& !srDTO.getStation_ID()[0].equals("0")) {
				intc = intc.substring(0, intc.length() - 1);
				sql += " and ohd.found_station_ID in (" + intc + ") ";
			}

			intc = "";
			if (srDTO.getHoldingstation_ID() != null) {
				for (int i = 0; i < srDTO.getHoldingstation_ID().length; i++) {
					intc += srDTO.getHoldingstation_ID()[i] + ",";
				}
			}

			if (intc.length() > 0 && srDTO.getHoldingstation_ID() != null
					&& !srDTO.getHoldingstation_ID()[0].equals("0")) {
				intc = intc.substring(0, intc.length() - 1);
				sql += " and ohd.holding_station_ID in (" + intc + ") ";
			}

			Date sdate = null, edate = null;
			Date sdate1 = null, edate1 = null; // add one for timezone
			Date stime = null; // time to compare (04:00 if eastern, for
								// example)
			String dateq = "";

			ArrayList dateal = null;
			if ((dateal = calculateDateDiff(srDTO, tz, user)) == null) {
				return null;
			}
			sdate = (Date) dateal.get(0);
			sdate1 = (Date) dateal.get(1);
			edate = (Date) dateal.get(2);
			edate1 = (Date) dateal.get(3);
			stime = (Date) dateal.get(4);

			parameters.put("sdate", TracerUtils.getText("reports.dates", user));
			if (sdate != null && edate != null) {
				parameters.put("sdate", srDTO.getStarttime());
				if (sdate.equals(edate)) {
					// need to add the timezone diff here
					sql += " and ((ohd.founddate = '"
							+ DateUtils.formatDate(sdate, TracingConstants
									.getDBDateFormat(HibernateWrapper
											.getConfig().getProperties()),
									null, null)
							+ "' and ohd.foundtime >= '"
							+ DateUtils.formatDate(stime, TracingConstants
									.getDBTimeFormat(HibernateWrapper
											.getConfig().getProperties()),
									null, null)
							+ "') or (ohd.founddate= '"
							+ DateUtils.formatDate(sdate1, TracingConstants
									.getDBDateFormat(HibernateWrapper
											.getConfig().getProperties()),
									null, null)
							+ "' and ohd.foundtime <= '"
							+ DateUtils.formatDate(stime, TracingConstants
									.getDBTimeFormat(HibernateWrapper
											.getConfig().getProperties()),
									null, null) + "'))";

				} else {

					// first get the beginning and end dates using date and
					// time, then get
					// dates in between
					sql += " and ((ohd.founddate = '"
							+ DateUtils.formatDate(sdate, TracingConstants
									.getDBDateFormat(HibernateWrapper
											.getConfig().getProperties()),
									null, null)
							+ "' and ohd.foundtime >= '"
							+ DateUtils.formatDate(stime, TracingConstants
									.getDBTimeFormat(HibernateWrapper
											.getConfig().getProperties()),
									null, null)
							+ "') or (ohd.founddate= '"
							+ DateUtils.formatDate(edate1, TracingConstants
									.getDBDateFormat(HibernateWrapper
											.getConfig().getProperties()),
									null, null)
							+ "' and ohd.foundtime <= '"
							+ DateUtils.formatDate(stime, TracingConstants
									.getDBTimeFormat(HibernateWrapper
											.getConfig().getProperties()),
									null, null)
							+ "') or (ohd.founddate > '"
							+ DateUtils.formatDate(sdate, TracingConstants
									.getDBDateFormat(HibernateWrapper
											.getConfig().getProperties()),
									null, null)
							+ "' and ohd.founddate <= '"
							+ DateUtils.formatDate(edate, TracingConstants
									.getDBDateFormat(HibernateWrapper
											.getConfig().getProperties()),
									null, null) + "'))";

					parameters.put("edate", srDTO.getEndtime());
				}
			} else if (sdate != null) {
				parameters.put("sdate", srDTO.getStarttime());
				sql += " and ((ohd.founddate = '"
						+ DateUtils.formatDate(sdate, TracingConstants
								.getDBDateFormat(HibernateWrapper.getConfig()
										.getProperties()), null, null)
						+ "' and ohd.foundtime >= '"
						+ DateUtils.formatDate(stime, TracingConstants
								.getDBTimeFormat(HibernateWrapper.getConfig()
										.getProperties()), null, null)
						+ "') or (ohd.founddate= '"
						+ DateUtils.formatDate(sdate1, TracingConstants
								.getDBDateFormat(HibernateWrapper.getConfig()
										.getProperties()), null, null)
						+ "' and ohd.foundtime <= '"
						+ DateUtils.formatDate(stime, TracingConstants
								.getDBTimeFormat(HibernateWrapper.getConfig()
										.getProperties()), null, null) + "'))";

				edate = null;
			}

			sql += " order by ohd.found_station_ID,ohd.OHD_ID,it.itinerary_ID";

			// rs = stmt.executeQuery(sql);

			session = HibernateWrapper.getSession().openSession();
			SQLQuery query = session.createSQLQuery(sql);

			// put in date and timeformat and timezone of current user

			Object[] o = null, o2 = null, o3 = null;
			StatReport_OHD_DTO sr = null;
			List list2 = new ArrayList();

			List templist = null;
			StringBuffer tempsb = null;
			int i = 0;
			String lastohdid = null;
			String lastpassname = "";
			String lastit = "";
			String temppassname = "", tempit = "";
			ResourceBundle resources = ResourceBundle
					.getBundle(
							"com.bagnet.nettracer.tracing.resources.ApplicationResources",
							new Locale(user.getCurrentlocale()));

			List results = query.list();

			if (results.isEmpty()) {
				return null;
			}
			SimpleReportRow reportRow;
			Object[] row;

			for (int j = 0; j < results.size(); ++j) {
				row = (Object[]) results.get(j);
				if (lastohdid == null || !(((String) row[4]).equals(lastohdid))) {
					if (lastohdid != null) {
						list2.add(sr);
						lastpassname = "";
						lastit = "";
					}
					sr = new StatReport_OHD_DTO();
					sr.setStation_ID((Integer) row[0]);
					sr.setStationcode((String) row[1]);
					if (row.length > 3) {
						sr.setHoldingstation((String) row[3]);
						if (row.length > 4) {
							sr.setOHD_ID((String) row[4]);
							lastohdid = sr.getOHD_ID();
							if (row.length > 5) {
								sr.setFounddate((Date) row[5]);
								if (row.length > 6) {
									sr.setFoundtime((Date) row[6]);
									if (row.length > 7) {
										sr.setStatusdesc(resources
												.getString("STATUS_KEY_"
														+ (Integer) row[7]));
										if (row.length > 8) {
											if (row[8] != null) {
												sr.setBagdispdesc(resources
														.getString("STATUS_KEY_"
																+ (Integer) row[8]));
											} else {
												sr.setBagdispdesc("");
											}
											if (row.length > 10) {
												if (row[9] != null
														&& ((String) row[9])
																.length() > 0)
													temppassname += (String) row[9];
												if (row[10] != null
														&& ((String) row[10])
																.length() > 0)
													temppassname += ", "
															+ (String) row[10];
												if (lastpassname.length() > 0) {
													if (lastpassname
															.indexOf(temppassname) < 0)
														lastpassname += "\n"
																+ temppassname;
												} else
													lastpassname = temppassname;
												temppassname = "";
												sr.setCustomer_name(lastpassname);
												if (row.length > 13) {
													if ((String) row[11] != null
															&& ((String) row[11])
																	.length() > 0)
														tempit += (String) row[11];
													if ((String) row[13] != null
															&& ((String) row[13])
																	.length() > 0)
														tempit += " "
																+ (String) row[13];
													if (lastit.length() > 0) {
														if (lastit
																.indexOf(tempit) < 0)
															lastit += "\n"
																	+ tempit;
													} else
														lastit = tempit;
													tempit = "";
													sr.setItinerary(lastit);
													if (row[12] != null
															&& ((String) row[11])
																	.length() > 0)
														sr.setFinal_destination((String) row[11]);
													else
														sr.setFinal_destination("");
													if (row.length > 14) {
														sr.setFaultstationcode((String) row[14]);
														if (row.length > 15) {
															if (row[15] != null)
																sr.setLoss_code((Integer) row[15]);
														}
													}

												}

											}
										}

									}
								}
							}
						}
					}
				} else {
					if (row.length > 10) {
						if (row[9] != null && ((String) row[9]).length() > 0)
							temppassname += (String) row[9];
						if (row[10] != null && ((String) row[10]).length() > 0)
							temppassname += ", " + (String) row[10];
						if (lastpassname.length() > 0) {
							if (lastpassname.indexOf(temppassname) < 0)
								lastpassname += "\n" + temppassname;
						} else
							lastpassname = temppassname;

						temppassname = "";
						sr.setCustomer_name(lastpassname);
						if (row.length > 13) {
							if ((String) row[11] != null
									&& ((String) row[11]).length() > 0)
								tempit += (String) row[11];
							if ((String) row[13] != null
									&& ((String) row[13]).length() > 0)
								tempit += " " + (String) row[13];
							if (lastit.length() > 0) {
								if (lastit.indexOf(tempit) < 0)
									lastit += "\n" + tempit;
							} else
								lastit = tempit;
							tempit = "";
							sr.setItinerary(lastit);
							if (row[12] != null
									&& ((String) row[11]).length() > 0)
								sr.setFinal_destination((String) row[11]);
							else
								sr.setFinal_destination("");
							if (row.length > 14) {
								sr.setFaultstationcode((String) row[14]);
								if (row.length > 15 && row[15]!=null) {
									sr.setLoss_code((Integer) row[15]);
								}
							}

						}

					}
				}
				if (sr.getFinal_destination() == null)
					sr.setFinal_destination("");

				sr.set_DATEFORMAT(user.getDateformat().getFormat());
				sr.set_TIMEFORMAT(user.getTimeformat().getFormat());
				sr.set_TIMEZONE(tz);

				// toReturn.add(reportRow);
			}

			if (sr == null) {
				logger.debug("no data for report");
				return "";
			}

			// add the last one
			if (sr != null)
				list2.add(sr);

			if (srDTO.getStatus_ID() >= 1) {
				if (sr != null) {
					parameters.put("status", sr.getStatusdesc());
				}
			} else {
				parameters.put("status",
						TracerUtils.getText("reports.all", user));
			}

			if (srDTO.getDispositionId() >= 1) {
				if (sr != null) {
					parameters.put("bagdisp", sr.getBagdispdesc());
				}
			} else {
				parameters.put("bagdisp",
						TracerUtils.getText("reports.all", user));
			}

			// summary or detail; summary = 0; detail = 1
			if (srDTO.getSumordet() == 1) {
				parameters.put("showdetail", "1");
				parameters.put("reportStyle", "10");
			} else {
				parameters.put("reportStyle", "00");
			}

			// toggle between old and new implementations
			// return getReportFile(list2, parameters, reportname, rootpath,
			// srDTO.getOutputtype());

			parameters.put("reportLocale", new Locale(user.getCurrentlocale()));
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(
					list2);

			return OHDReportBMO.getReportFileDj(ds, parameters, reportname,
					rootpath, srDTO.getOutputtype(), req, this);
		} catch (Exception e) {
			logger.error("unable to create report " + e);
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}
	}

	@SuppressWarnings("rawtypes")
	public Status getStatus(int status_ID) throws HibernateException {
		Session sess = HibernateWrapper.getDirtySession().openSession();
		try {
			Query q = sess
					.createQuery("from com.bagnet.nettracer.tracing.db.Status status where status_ID= :status_ID");
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

	public static JasperReport getCompiledReport(String reportname,
			String rootpath) throws Exception {
		/** look for compiled reports, if can't find it, compile xml report * */
		File reportFile = new File(rootpath + "/reports/" + reportname
				+ ".jasper");
		if (!reportFile.exists()) {
			/** used for runtime compilation * */
			System.setProperty(
					ReportingConstants.CLASS_PATH,
					rootpath + ReportingConstants.JASPER_JAR
							+ System.getProperty("path.separator") + rootpath
							+ ReportingConstants.IREPORT_JAR
							+ System.getProperty("path.separator") + rootpath
							+ "/WEB-INF/classes/");
			System.setProperty(ReportingConstants.COMPILE, rootpath
					+ "/reports/");
			String xmlpath = rootpath + "/reports/" + reportname + ".jrxml";
			JasperCompileManager.compileReportToFile(xmlpath);
		}
		return (JasperReport) JRLoader.loadObject(reportFile.getPath());
	}

	@SuppressWarnings("rawtypes")
	public String getReportFile(List list, Map parameters, String reportname,
			String rootpath, int outputtype) throws Exception {
		JasperReport jasperReport = getCompiledReport(reportname, rootpath);
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(list);
		return getReportFile(jasperReport, ds, parameters, reportname,
				rootpath, outputtype);
	}

	@SuppressWarnings("rawtypes")
	public static String getReportFile(List list, Map parameters,
			String reportname, String rootpath, int outputtype,
			HttpServletRequest request) throws Exception {
		JasperReport jasperReport = getCompiledReport(reportname, rootpath);
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(list);
		return getReportFile(jasperReport, ds, parameters, reportname,
				rootpath, outputtype, request, null);
	}

	// overload starts
	@SuppressWarnings("rawtypes")
	public static String getReportFile(List list, Map parameters,
			String reportname, String rootpath, int outputtype,
			HttpServletRequest request, int MAX_NUMBER_OF_PAGES)
			throws Exception {
		JasperReport jasperReport = getCompiledReport(reportname, rootpath);
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(list);
		return getReportFile(jasperReport, ds, parameters, reportname,
				rootpath, outputtype, request, null, MAX_NUMBER_OF_PAGES);
	} // overload ends

	@SuppressWarnings("rawtypes")
	private String getReportFile(JasperReport jasperReport, JRDataSource ds,
			Map parameters, String reportname, String rootpath, int outputtype)
			throws Exception {
		return getReportFile(jasperReport, ds, parameters, reportname,
				rootpath, outputtype, req, this);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String getReportFile(JasperReport jasperReport,
			JRDataSource ds, Map parameters, String reportname,
			String rootpath, int outputtype, HttpServletRequest request,
			ReportBMO rbmo) throws Exception {
		/** look for compiled reports, if can't find it, compile xml report * */

		// added virtualizer to reduce memory
		String outfile = reportname
				+ "_"
				+ (new SimpleDateFormat("MMddyyyyhhmmss").format(TracerDateTime
						.getGMTDate()));
		JRGovernedFileVirtualizer virtualizer = new JRGovernedFileVirtualizer(
				2, rootpath + "/reports/tmp", 501);

		parameters.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);

		if (outputtype == TracingConstants.REPORT_OUTPUT_XLS) {
			parameters.put(JRParameter.IS_IGNORE_PAGINATION, true);
		}

		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport, parameters, ds);

			virtualizer.setReadOnly(true);

			if (outputtype == TracingConstants.REPORT_OUTPUT_HTML)
				outfile += ".html";
			else if (outputtype == TracingConstants.REPORT_OUTPUT_PDF)
				// In the event the file type is undeclared, we will use the
				// default - PDF if available, HTML otherwise.
				if (!TracerProperties
						.isTrue(TracerProperties.SUPPRESSION_PRINTING_NONHTML)) {
					outfile += ".pdf";
				} else {
					outfile += ".html";
					outputtype = TracingConstants.REPORT_OUTPUT_HTML;
					request.setAttribute("outputtype", Integer
							.toString(TracingConstants.REPORT_OUTPUT_HTML));
				}

			else if (outputtype == TracingConstants.REPORT_OUTPUT_XLS)
				outfile += ".xls";
			else if (outputtype == TracingConstants.REPORT_OUTPUT_CSV)
				outfile += ".csv";
			else if (outputtype == TracingConstants.REPORT_OUTPUT_XML)
				outfile += ".xml";
			else if (outputtype == TracingConstants.REPORT_OUTPUT_UNDECLARED) {
				// In the event the file type is undeclared, we will use the
				// default - PDF if available, HTML otherwise.
				if (!TracerProperties
						.isTrue(TracerProperties.SUPPRESSION_PRINTING_NONHTML)) {
					outfile += ".pdf";
					outputtype = TracingConstants.REPORT_OUTPUT_PDF;
				} else {
					outfile += ".html";
					outputtype = TracingConstants.REPORT_OUTPUT_HTML;
					request.setAttribute("outputtype", Integer
							.toString(TracingConstants.REPORT_OUTPUT_HTML));
				}
			}

			String outputpath = rootpath + ReportingConstants.REPORT_TMP_PATH
					+ outfile;
			JRExporter exporter = null;

			if (outputtype == TracingConstants.REPORT_OUTPUT_PDF)
				JasperExportManager.exportReportToPdfFile(jasperPrint,
						outputpath);
			else if (outputtype == TracingConstants.REPORT_OUTPUT_HTML) {
				exporter = new JRHtmlExporter();

				Map imagesMap = new HashMap();

				String myBetweenPagesHtml = "<div style=\"page-break-after: always\" border=\"0\">&nbsp;</div>";
				String myHistoryReportLongKey = ""
						+ parameters.get("history_report_long");
				if ("Yes".equalsIgnoreCase(myHistoryReportLongKey)) { // for
																		// history
																		// report
																		// only:
																		// removing
																		// large
																		// space
																		// between
																		// pages
					myBetweenPagesHtml = "";
				}

				// exporter.setParameter(JRHtmlExporterParameter.BETWEEN_PAGES_HTML,
				// "<div style=\"page-break-after: always\" border=\"0\">&nbsp;</div>");
				request.getSession().setAttribute("IMAGES_MAP", imagesMap);
				exporter.setParameter(JRHtmlExporterParameter.IMAGES_MAP,
						imagesMap);
				exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI,
						"image?image=");

				// exporter.setParameter(JRHtmlExporterParameter.HTML_HEADER,
				// "");
				exporter.setParameter(
						JRHtmlExporterParameter.BETWEEN_PAGES_HTML,
						myBetweenPagesHtml);
				// exporter.setParameter(JRHtmlExporterParameter.HTML_FOOTER,
				// "");

				exporter.setParameter(JRExporterParameter.JASPER_PRINT,
						jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
						outputpath);
				// exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
				// Boolean.TRUE);
				exporter.exportReport();
			}

			// switch to JExcelApiExporter
			else if (outputtype == TracingConstants.REPORT_OUTPUT_XLS) {
				/*
				 * exporter = new JRXlsExporter();
				 * 
				 * exporter.setParameter(JRExporterParameter.JASPER_PRINT,
				 * jasperPrint);
				 * exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
				 * outputpath); exporter.setParameter(JRXlsExporterParameter.
				 * IS_ONE_PAGE_PER_SHEET, false);
				 * exporter.setParameter(JRXlsExporterParameter
				 * .IS_WHITE_PAGE_BACKGROUND, false);
				 * exporter.setParameter(JRXlsExporterParameter
				 * .IGNORE_PAGE_MARGINS, true);
				 * exporter.setParameter(JRXlsExporterParameter
				 * .IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, true);
				 * exporter.setParameter
				 * (JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS
				 * , true); //exporter.setParameter(JRXlsExporterParameter.
				 * IS_DETECT_CELL_TYPE, true); // 01-13-2010 attempt to fix
				 * excel problem //exporter.setParameter(JRXlsExporterParameter.
				 * MAXIMUM_ROWS_PER_SHEET, 65536); exporter.exportReport();
				 */
				JExcelApiExporter alternativeExporter = new JExcelApiExporter();
				Map excelParameters = new HashMap();
				excelParameters.put(JExcelApiExporterParameter.JASPER_PRINT,
						jasperPrint);
				excelParameters
						.put(JExcelApiExporterParameter.OUTPUT_FILE_NAME,
								outputpath);
				excelParameters.put(
						JExcelApiExporterParameter.IS_ONE_PAGE_PER_SHEET,
						Boolean.FALSE);
				excelParameters.put(
						JExcelApiExporterParameter.IS_WHITE_PAGE_BACKGROUND,
						Boolean.FALSE);
				excelParameters.put(
						JExcelApiExporterParameter.IGNORE_PAGE_MARGINS,
						Boolean.TRUE);
				excelParameters
						.put(JExcelApiExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS,
								Boolean.TRUE);
				excelParameters
						.put(JExcelApiExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
								Boolean.TRUE);
				// excelParameters.put(JExcelApiExporterParameter.IS_DETECT_CELL_TYPE,
				// Boolean.TRUE);
				excelParameters.put(
						JExcelApiExporterParameter.IS_FONT_SIZE_FIX_ENABLED,
						Boolean.TRUE);
				excelParameters.put(
						JExcelApiExporterParameter.IS_COLLAPSE_ROW_SPAN,
						Boolean.TRUE);

				alternativeExporter.setParameters(excelParameters);
				alternativeExporter.exportReport();
			} else if (outputtype == TracingConstants.REPORT_OUTPUT_CSV) {
				exporter = new JRCsvExporter();

				exporter.setParameter(JRExporterParameter.JASPER_PRINT,
						jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
						outputpath);
				exporter.exportReport();
			} else if (outputtype == TracingConstants.REPORT_OUTPUT_XML) {
				exporter = new JRXmlExporter();

				exporter.setParameter(JRExporterParameter.JASPER_PRINT,
						jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
						outputpath);
				exporter.exportReport();
			}
		} catch (JRMaxFilesException e) {
			logger.error("JRMaxFilesException encountered...");
			logger.error("Report Name: " + reportname);
			Agent user = (Agent) request.getSession().getAttribute("user");
			logger.error("User ID: " + user.getAgent_ID() + " User Name: "
					+ user.getUsername());

			for (Object obj : parameters.keySet()) {
				logger.error("Parameter: " + obj.toString() + "  Value: "
						+ parameters.get(obj).toString());
			}
			outfile = null;
			if (rbmo != null) {
				rbmo.setErrormsg("error.maxpages");
			}
		}

		virtualizer.cleanup();

		return outfile;
	}

	// method overload
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String getReportFile(JasperReport jasperReport,
			JRDataSource ds, Map parameters, String reportname,
			String rootpath, int outputtype, HttpServletRequest request,
			ReportBMO rbmo, int MAX_NUMBER_OF_PAGES) throws Exception {
		/** look for compiled reports, if can't find it, compile xml report * */

		// added virtualizer to reduce memory
		String outfile = reportname
				+ "_"
				+ (new SimpleDateFormat("MMddyyyyhhmmss").format(TracerDateTime
						.getGMTDate()));
		JRGovernedFileVirtualizer virtualizer = new JRGovernedFileVirtualizer(
				2, rootpath + "/reports/tmp", MAX_NUMBER_OF_PAGES);

		parameters.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);

		if (outputtype == TracingConstants.REPORT_OUTPUT_XLS) {
			parameters.put(JRParameter.IS_IGNORE_PAGINATION, true);
		}

		try {
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport, parameters, ds);

			virtualizer.setReadOnly(true);

			if (outputtype == TracingConstants.REPORT_OUTPUT_HTML)
				outfile += ".html";
			else if (outputtype == TracingConstants.REPORT_OUTPUT_PDF)
				// In the event the file type is undeclared, we will use the
				// default - PDF if available, HTML otherwise.
				if (!TracerProperties
						.isTrue(TracerProperties.SUPPRESSION_PRINTING_NONHTML)) {
					outfile += ".pdf";
				} else {
					outfile += ".html";
					outputtype = TracingConstants.REPORT_OUTPUT_HTML;
					request.setAttribute("outputtype", Integer
							.toString(TracingConstants.REPORT_OUTPUT_HTML));
				}

			else if (outputtype == TracingConstants.REPORT_OUTPUT_XLS)
				outfile += ".xls";
			else if (outputtype == TracingConstants.REPORT_OUTPUT_CSV)
				outfile += ".csv";
			else if (outputtype == TracingConstants.REPORT_OUTPUT_XML)
				outfile += ".xml";
			else if (outputtype == TracingConstants.REPORT_OUTPUT_UNDECLARED) {
				// In the event the file type is undeclared, we will use the
				// default - PDF if available, HTML otherwise.
				if (!TracerProperties
						.isTrue(TracerProperties.SUPPRESSION_PRINTING_NONHTML)) {
					outfile += ".pdf";
					outputtype = TracingConstants.REPORT_OUTPUT_PDF;
				} else {
					outfile += ".html";
					outputtype = TracingConstants.REPORT_OUTPUT_HTML;
					request.setAttribute("outputtype", Integer
							.toString(TracingConstants.REPORT_OUTPUT_HTML));
				}
			}

			String outputpath = rootpath + ReportingConstants.REPORT_TMP_PATH
					+ outfile;
			JRExporter exporter = null;

			if (outputtype == TracingConstants.REPORT_OUTPUT_PDF)
				JasperExportManager.exportReportToPdfFile(jasperPrint,
						outputpath);
			else if (outputtype == TracingConstants.REPORT_OUTPUT_HTML) {
				exporter = new JRHtmlExporter();

				Map imagesMap = new HashMap();

				String myBetweenPagesHtml = "<div style=\"page-break-after: always\" border=\"0\">&nbsp;</div>";
				String myHistoryReportLongKey = ""
						+ parameters.get("history_report_long");
				if ("Yes".equalsIgnoreCase(myHistoryReportLongKey)) { // for
																		// history
																		// report
																		// only:
																		// removing
																		// large
																		// space
																		// between
																		// pages
					myBetweenPagesHtml = "";
				}

				// exporter.setParameter(JRHtmlExporterParameter.BETWEEN_PAGES_HTML,
				// "<div style=\"page-break-after: always\" border=\"0\">&nbsp;</div>");

				request.getSession().setAttribute("IMAGES_MAP", imagesMap);
				exporter.setParameter(JRHtmlExporterParameter.IMAGES_MAP,
						imagesMap);
				exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI,
						"image?image=");

				// exporter.setParameter(JRHtmlExporterParameter.HTML_HEADER,
				// "");
				exporter.setParameter(
						JRHtmlExporterParameter.BETWEEN_PAGES_HTML,
						myBetweenPagesHtml);
				// exporter.setParameter(JRHtmlExporterParameter.HTML_FOOTER,
				// "");

				exporter.setParameter(JRExporterParameter.JASPER_PRINT,
						jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
						outputpath);
				// exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
				// Boolean.TRUE);
				exporter.exportReport();
			}

			else if (outputtype == TracingConstants.REPORT_OUTPUT_XLS) {

				// done: want to use JExcelApiExporter instead, for large data
				// set
				JExcelApiExporter alternativeExporter = new JExcelApiExporter();
				Map excelParameters = new HashMap();
				excelParameters.put(JExcelApiExporterParameter.JASPER_PRINT,
						jasperPrint);
				excelParameters
						.put(JExcelApiExporterParameter.OUTPUT_FILE_NAME,
								outputpath);
				excelParameters.put(
						JExcelApiExporterParameter.IS_ONE_PAGE_PER_SHEET,
						Boolean.FALSE);
				excelParameters.put(
						JExcelApiExporterParameter.IS_WHITE_PAGE_BACKGROUND,
						Boolean.FALSE);
				excelParameters.put(
						JExcelApiExporterParameter.IGNORE_PAGE_MARGINS,
						Boolean.TRUE);
				excelParameters
						.put(JExcelApiExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS,
								Boolean.TRUE);
				excelParameters
						.put(JExcelApiExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
								Boolean.TRUE);
				// excelParameters.put(JExcelApiExporterParameter.IS_DETECT_CELL_TYPE,
				// Boolean.TRUE);
				excelParameters.put(
						JExcelApiExporterParameter.IS_FONT_SIZE_FIX_ENABLED,
						Boolean.TRUE); // ???
				excelParameters.put(
						JExcelApiExporterParameter.IS_COLLAPSE_ROW_SPAN,
						Boolean.TRUE);

				alternativeExporter.setParameters(excelParameters);
				alternativeExporter.exportReport();

			} else if (outputtype == TracingConstants.REPORT_OUTPUT_CSV) {
				exporter = new JRCsvExporter();

				exporter.setParameter(JRExporterParameter.JASPER_PRINT,
						jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
						outputpath);
				exporter.exportReport();
			} else if (outputtype == TracingConstants.REPORT_OUTPUT_XML) {
				exporter = new JRXmlExporter();

				exporter.setParameter(JRExporterParameter.JASPER_PRINT,
						jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
						outputpath);
				exporter.exportReport();
			}
		} catch (JRMaxFilesException e) {
			logger.error("JRMaxFilesException encountered...");
			logger.error("Report Name: " + reportname);
			Agent user = (Agent) request.getSession().getAttribute("user");
			logger.error("User ID: " + user.getAgent_ID() + " User Name: "
					+ user.getUsername());

			for (Object obj : parameters.keySet()) {
				logger.error("Parameter: " + obj.toString() + "  Value: "
						+ parameters.get(obj).toString());
			}
			outfile = null;
			if (rbmo != null) {
				rbmo.setErrormsg("error.maxpages");
			}
		}

		virtualizer.cleanup();

		return outfile;
	}// method overload end

	/**
	 * @return Returns the errormsg.
	 */
	public String getErrormsg() {
		return errormsg;
	}

	// dj reporting starts
	@SuppressWarnings("serial")
	private static CustomExpression getSubTotalCustomExpression() {
		return new CustomExpression() {

			@SuppressWarnings("rawtypes")
			public Object evaluate(Map fields, Map variables, Map parameters) {
				String subTotal = "Sub-Total: ";
				return subTotal;
			}

			public String getClassName() {
				return String.class.getName();
			}

		};
	}

	@SuppressWarnings("static-access")
	public static DynamicReport buildExcelReport(int reportStyle,
			Locale reportLocale, ResourceBundle resourceBundle)
			throws Exception {

		/**
		 * Creates the DynamicReportBuilder and sets the basic options for the
		 * report
		 */
		FastReportBuilder drb = new FastReportBuilder();

		// getting report header title from reource bundle
		String reportHeadingCreateStation = "Create Station Default";
		String reportHeadingIncidentNumber = "Incident #";
		String reportHeadingFaultStation = "Fault Station";
		String reportHeadingLossCode = "Loss Code";
		String reportHeadingAgent = "Agent";
		String reportHeadingAgentStation = "Agent Station";
		String reportHeadingPaidDate = "Paid Date";
		String reportHeadingPaymentType = "Payment Type";
		String reportHeadingPaymentStatus = "Payment Status";
		String reportHeadingCheckAmount = "Check Amount";
		String reportHeadingVoucher = "Voucher";
		String reportHeadingMileage = "Mileage";

		String myCreateStation = resourceBundle
				.getString("report.disbursement.heading.create.station");
		if (!(myCreateStation == null || myCreateStation.equalsIgnoreCase(""))) {
			reportHeadingCreateStation = myCreateStation;
		}

		String myIncidentNumber = resourceBundle
				.getString("report.disbursement.heading.incident.number");
		if (!(myIncidentNumber == null || myIncidentNumber.equalsIgnoreCase(""))) {
			reportHeadingIncidentNumber = myIncidentNumber;
		}

		String myFaultStation = resourceBundle
				.getString("report.disbursement.heading.fault.station");
		if (!(myFaultStation == null || myFaultStation.equalsIgnoreCase(""))) {
			reportHeadingFaultStation = myFaultStation;
		}

		String myLossCode = resourceBundle
				.getString("report.disbursement.heading.loss.code");
		if (!(myLossCode == null || myLossCode.equalsIgnoreCase(""))) {
			reportHeadingLossCode = myLossCode;
		}

		String myAgent = resourceBundle
				.getString("report.disbursement.heading.agent");
		if (!(myAgent == null || myAgent.equalsIgnoreCase(""))) {
			reportHeadingAgent = myAgent;
		}

		String myAgentStation = resourceBundle
				.getString("report.disbursement.heading.agent.station");
		if (!(myAgentStation == null || myAgentStation.equalsIgnoreCase(""))) {
			reportHeadingAgentStation = myAgentStation;
		}

		String myPaidDate = resourceBundle
				.getString("report.disbursement.heading.paid.date");
		if (!(myPaidDate == null || myPaidDate.equalsIgnoreCase(""))) {
			reportHeadingPaidDate = myPaidDate;
		}

		String myPaymentType = resourceBundle
				.getString("report.disbursement.heading.payment.type");
		if (!(myPaymentType == null || myPaymentType.equalsIgnoreCase(""))) {
			reportHeadingPaymentType = myPaymentType;
		}

		String myPaymentStatus = resourceBundle
				.getString("report.disbursement.heading.payment.status");
		if (!(myPaymentStatus == null || myPaymentStatus.equalsIgnoreCase(""))) {
			reportHeadingPaymentStatus = myPaymentStatus;
		}

		String myCheckAmount = resourceBundle
				.getString("report.disbursement.heading.check.amount");
		if (!(myCheckAmount == null || myCheckAmount.equalsIgnoreCase(""))) {
			reportHeadingCheckAmount = myCheckAmount;
		}

		String myVoucher = resourceBundle
				.getString("report.disbursement.heading.voucher");
		if (!(myVoucher == null || myVoucher.equalsIgnoreCase(""))) {
			reportHeadingVoucher = myVoucher;
		}

		String myMileage = resourceBundle
				.getString("report.disbursement.heading.mileage");
		if (!(myMileage == null || myMileage.equalsIgnoreCase(""))) {
			reportHeadingMileage = myMileage;
		}

		// sort out the report styles
		switch (reportStyle) {
		case 11:
			drb = drb.addColumn(reportHeadingCreateStation, "stationcode",
					String.class.getName(), 14);
			drb = drb.addColumn(reportHeadingIncidentNumber, "claim_number",
					String.class.getName(), 30);
			drb = drb.addColumn(reportHeadingFaultStation, "faultstationcode",
					String.class.getName(), 14);
			drb = drb.addColumn(reportHeadingLossCode, "loss_code",
					Integer.class.getName(), 10);
			drb = drb.addColumn(reportHeadingAgent, "agent_username",
					String.class.getName(), 20);
			drb = drb.addColumn(reportHeadingAgentStation,
					"agent_station_code", String.class.getName(), 14);
			drb = drb.addColumn(reportHeadingPaidDate, "draftpaiddate",
					Date.class.getName(), 12);
			drb = drb.addColumn(reportHeadingPaymentType,
					"expenseType_description", String.class.getName(), 18);
			drb = drb.addColumn(reportHeadingPaymentStatus, "expenseStatus",
					String.class.getName(), 18);
			drb = drb.addColumn(reportHeadingCheckAmount, "checkamt",
					Double.class.getName(), 18, false, "$ ##,##0.00");
			drb = drb.addColumn(" ", "currency_ID", String.class.getName(), 10);
			drb = drb.addColumn(reportHeadingVoucher, "voucheramt",
					Double.class.getName(), 18);
			drb = drb.addColumn(reportHeadingMileage, "mileageamt",
					Integer.class.getName(), 12);
			break;
		case 12:
			drb = drb.addColumn(reportHeadingFaultStation, "faultstationcode",
					String.class.getName(), 14);
			drb = drb.addColumn(reportHeadingIncidentNumber, "claim_number",
					String.class.getName(), 30);
			drb = drb.addColumn(reportHeadingCreateStation, "stationcode",
					String.class.getName(), 14);
			drb = drb.addColumn(reportHeadingLossCode, "loss_code",
					Integer.class.getName(), 10);
			drb = drb.addColumn(reportHeadingAgent, "agent_username",
					String.class.getName(), 20);
			drb = drb.addColumn(reportHeadingAgentStation,
					"agent_station_code", String.class.getName(), 14);
			drb = drb.addColumn(reportHeadingPaidDate, "draftpaiddate",
					Date.class.getName(), 12);
			drb = drb.addColumn(reportHeadingPaymentType,
					"expenseType_description", String.class.getName(), 18);
			drb = drb.addColumn(reportHeadingPaymentStatus, "expenseStatus",
					String.class.getName(), 18);
			drb = drb.addColumn(reportHeadingCheckAmount, "checkamt",
					Double.class.getName(), 18, false, "$ ##,##0.00");
			drb = drb.addColumn(" ", "currency_ID", String.class.getName(), 10);
			drb = drb.addColumn(reportHeadingVoucher, "voucheramt",
					Double.class.getName(), 18);
			drb = drb.addColumn(reportHeadingMileage, "mileageamt",
					Integer.class.getName(), 12);
			break;
		case 20:
			drb = drb.addColumn(reportHeadingAgentStation,
					"agent_station_code", String.class.getName(), 14);
			drb = drb.addColumn(reportHeadingIncidentNumber, "claim_number",
					String.class.getName(), 30);
			drb = drb.addColumn(reportHeadingCreateStation, "stationcode",
					String.class.getName(), 14);
			drb = drb.addColumn(reportHeadingFaultStation, "faultstationcode",
					String.class.getName(), 14);
			drb = drb.addColumn(reportHeadingLossCode, "loss_code",
					Integer.class.getName(), 10);
			drb = drb.addColumn(reportHeadingAgent, "agent_username",
					String.class.getName(), 20);
			drb = drb.addColumn(reportHeadingPaidDate, "draftpaiddate",
					Date.class.getName(), 12);
			drb = drb.addColumn(reportHeadingPaymentType,
					"expenseType_description", String.class.getName(), 18);
			drb = drb.addColumn(reportHeadingPaymentStatus, "expenseStatus",
					String.class.getName(), 18);
			drb = drb.addColumn(reportHeadingCheckAmount, "checkamt",
					Double.class.getName(), 18, false, "$ ##,##0.00");
			drb = drb.addColumn(" ", "currency_ID", String.class.getName(), 10);
			drb = drb.addColumn(reportHeadingVoucher, "voucheramt",
					Double.class.getName(), 18);
			drb = drb.addColumn(reportHeadingMileage, "mileageamt",
					Integer.class.getName(), 12);
			break;
		case 31:
			drb = drb.addColumn(reportHeadingCreateStation, "stationcode",
					String.class.getName(), 14);
			drb = drb.addColumn(reportHeadingAgent, "agent_username",
					String.class.getName(), 20);
			drb = drb.addColumn(reportHeadingAgentStation,
					"agent_station_code", String.class.getName(), 14);
			drb = drb.addColumn(reportHeadingIncidentNumber, "claim_number",
					String.class.getName(), 30);
			drb = drb.addColumn(reportHeadingFaultStation, "faultstationcode",
					String.class.getName(), 14);
			drb = drb.addColumn(reportHeadingLossCode, "loss_code",
					Integer.class.getName(), 10);
			drb = drb.addColumn(reportHeadingPaidDate, "draftpaiddate",
					Date.class.getName(), 12);
			drb = drb.addColumn(reportHeadingPaymentType,
					"expenseType_description", String.class.getName(), 18);
			drb = drb.addColumn(reportHeadingPaymentStatus, "expenseStatus",
					String.class.getName(), 18);
			drb = drb.addColumn(reportHeadingCheckAmount, "checkamt",
					Double.class.getName(), 18, false, "$ ##,##0.00");
			drb = drb.addColumn(" ", "currency_ID", String.class.getName(), 10);
			drb = drb.addColumn(reportHeadingVoucher, "voucheramt",
					Double.class.getName(), 18);
			drb = drb.addColumn(reportHeadingMileage, "mileageamt",
					Integer.class.getName(), 12);
			break;
		case 32:
			drb = drb.addColumn(reportHeadingFaultStation, "faultstationcode",
					String.class.getName(), 14);
			drb = drb.addColumn(reportHeadingAgent, "agent_username",
					String.class.getName(), 20);
			drb = drb.addColumn(reportHeadingAgentStation,
					"agent_station_code", String.class.getName(), 14);
			drb = drb.addColumn(reportHeadingIncidentNumber, "claim_number",
					String.class.getName(), 30);
			drb = drb.addColumn(reportHeadingCreateStation, "stationcode",
					String.class.getName(), 14);
			drb = drb.addColumn(reportHeadingLossCode, "loss_code",
					Integer.class.getName(), 10);
			drb = drb.addColumn(reportHeadingPaidDate, "draftpaiddate",
					Date.class.getName(), 12);
			drb = drb.addColumn(reportHeadingPaymentType,
					"expenseType_description", String.class.getName(), 18);
			drb = drb.addColumn(reportHeadingPaymentStatus, "expenseStatus",
					String.class.getName(), 18);
			drb = drb.addColumn(reportHeadingCheckAmount, "checkamt",
					Double.class.getName(), 18, false, "$ ##,##0.00");
			drb = drb.addColumn(" ", "currency_ID", String.class.getName(), 10);
			drb = drb.addColumn(reportHeadingVoucher, "voucheramt",
					Double.class.getName(), 18);
			drb = drb.addColumn(reportHeadingMileage, "mileageamt",
					Integer.class.getName(), 12);
			break;
		case 40:
			drb = drb.addColumn(reportHeadingAgentStation,
					"agent_station_code", String.class.getName(), 14);
			drb = drb.addColumn(reportHeadingAgent, "agent_username",
					String.class.getName(), 20);
			drb = drb.addColumn(reportHeadingIncidentNumber, "claim_number",
					String.class.getName(), 30);
			drb = drb.addColumn(reportHeadingCreateStation, "stationcode",
					String.class.getName(), 14);
			drb = drb.addColumn(reportHeadingFaultStation, "faultstationcode",
					String.class.getName(), 14);
			drb = drb.addColumn(reportHeadingLossCode, "loss_code",
					Integer.class.getName(), 10);
			drb = drb.addColumn(reportHeadingPaidDate, "draftpaiddate",
					Date.class.getName(), 12);
			drb = drb.addColumn(reportHeadingPaymentType,
					"expenseType_description", String.class.getName(), 18);
			drb = drb.addColumn(reportHeadingPaymentStatus, "expenseStatus",
					String.class.getName(), 18);
			drb = drb.addColumn(reportHeadingCheckAmount, "checkamt",
					Double.class.getName(), 18, false, "$ ##,##0.00");
			drb = drb.addColumn(" ", "currency_ID", String.class.getName(), 10);
			drb = drb.addColumn(reportHeadingVoucher, "voucheramt",
					Double.class.getName(), 18);
			drb = drb.addColumn(reportHeadingMileage, "mileageamt",
					Integer.class.getName(), 12);
			break;
		default:
			drb = drb.addColumn(reportHeadingCreateStation, "stationcode",
					String.class.getName(), 14);
			drb = drb.addColumn(reportHeadingIncidentNumber, "claim_number",
					String.class.getName(), 30);
			drb = drb.addColumn(reportHeadingFaultStation, "faultstationcode",
					String.class.getName(), 14);
			drb = drb.addColumn(reportHeadingLossCode, "loss_code",
					Integer.class.getName(), 10);
			drb = drb.addColumn(reportHeadingAgent, "agent_username",
					String.class.getName(), 20);
			drb = drb.addColumn(reportHeadingAgentStation,
					"agent_station_code", String.class.getName(), 14);
			drb = drb.addColumn(reportHeadingPaidDate, "draftpaiddate",
					Date.class.getName(), 12);
			drb = drb.addColumn(reportHeadingPaymentType,
					"expenseType_description", String.class.getName(), 18);
			drb = drb.addColumn(reportHeadingPaymentStatus, "expenseStatus",
					String.class.getName(), 18);
			drb = drb.addColumn(reportHeadingCheckAmount, "checkamt",
					Double.class.getName(), 18, false, "$ ##,##0.00");
			drb = drb.addColumn(" ", "currency_ID", String.class.getName(), 10);
			drb = drb.addColumn(reportHeadingVoucher, "voucheramt",
					Double.class.getName(), 18);
			drb = drb.addColumn(reportHeadingMileage, "mileageamt",
					Integer.class.getName(), 12);
			break;
		}

		// drb.setReportLocale(new Locale("pt","BR"));
		// drb.setReportLocale(new Locale("fr","FR"));
		drb.setReportLocale(reportLocale);

		drb.setSubtitle("This report was generated on " + new Date());

		Page page = new Page();
		drb.setPageSizeAndOrientation(page.Page_A4_Landscape());
		// drb.setPrintBackgroundOnOddRows(true);
		drb.setPrintBackgroundOnOddRows(false);
		drb.setIgnorePagination(true);
		DynamicReport dr = drb.setTitle("Disbursement Report")
				.setUseFullPageWidth(true).build();

		return dr;
	}

	@SuppressWarnings("unused")
	private static DynamicReportBuilder excelReportStyleSelector() {
		DynamicReportBuilder result = null;

		return result;
	}

	private static DynamicReportBuilder reportStyleSelector(
			DynamicReportBuilder drb,
			Map<String, AbstractColumn> reportColumns, int reportStyle)
			throws Exception {

		if (reportColumns != null) {
			Style groupVariables = new Style("groupVariables");
			groupVariables.setHorizontalAlign(HorizontalAlign.LEFT);
			groupVariables.setVerticalAlign(VerticalAlign.BOTTOM);

			AbstractColumn columnCreateStation = reportColumns
					.get("columnCreateStation");
			AbstractColumn columnClaimNumber = reportColumns
					.get("columnClaimNumber");
			AbstractColumn columnaFaultStationCode = reportColumns
					.get("columnaFaultStationCode");
			AbstractColumn columnaLossCode = reportColumns
					.get("columnaLossCode");
			AbstractColumn columnAgentCode = reportColumns
					.get("columnAgentCode");
			AbstractColumn columnaAgentStationCode = reportColumns
					.get("columnaAgentStationCode");
			AbstractColumn columnaPaidDate = reportColumns
					.get("columnaPaidDate");
			AbstractColumn columnaPayType = reportColumns.get("columnaPayType");
			AbstractColumn columnaPayStatus = reportColumns
					.get("columnaPayStatus");
			AbstractColumn columnCheckAmount = reportColumns
					.get("columnCheckAmount");
			AbstractColumn columnCurrencyType = reportColumns
					.get("columnCurrencyType");
			AbstractColumn columnVoucher = reportColumns.get("columnVoucher");
			AbstractColumn columnMileage = reportColumns.get("columnMileage");

			DJGroup g1 = null;
			GroupBuilder gb1 = null;
			AbstractColumn groupByColumn = columnCreateStation; // default

			// sort out the report styles
			switch (reportStyle) {
			case 11:
				drb.addColumn(columnCreateStation);
				drb.addColumn(columnClaimNumber);
				drb.addColumn(columnaFaultStationCode);
				drb.addColumn(columnaLossCode);
				drb.addColumn(columnAgentCode);
				drb.addColumn(columnaAgentStationCode);
				drb.addColumn(columnaPaidDate);
				drb.addColumn(columnaPayType);
				drb.addColumn(columnaPayStatus);
				drb.addColumn(columnCheckAmount);
				drb.addColumn(columnCurrencyType);
				drb.addColumn(columnVoucher);
				drb.addColumn(columnMileage);

				groupByColumn = columnCreateStation;
				break;
			case 12:
				drb.addColumn(columnaFaultStationCode);
				drb.addColumn(columnClaimNumber);
				drb.addColumn(columnCreateStation);
				drb.addColumn(columnaLossCode);
				drb.addColumn(columnAgentCode);
				drb.addColumn(columnaAgentStationCode);
				drb.addColumn(columnaPaidDate);
				drb.addColumn(columnaPayType);
				drb.addColumn(columnaPayStatus);
				drb.addColumn(columnCheckAmount);
				drb.addColumn(columnCurrencyType);
				drb.addColumn(columnVoucher);
				drb.addColumn(columnMileage);

				groupByColumn = columnaFaultStationCode;
				break;
			case 20:
				drb.addColumn(columnaAgentStationCode);
				drb.addColumn(columnClaimNumber);
				drb.addColumn(columnCreateStation);
				drb.addColumn(columnaFaultStationCode);
				drb.addColumn(columnaLossCode);
				drb.addColumn(columnAgentCode);
				drb.addColumn(columnaPaidDate);
				drb.addColumn(columnaPayType);
				drb.addColumn(columnaPayStatus);
				drb.addColumn(columnCheckAmount);
				drb.addColumn(columnCurrencyType);
				drb.addColumn(columnVoucher);
				drb.addColumn(columnMileage);

				groupByColumn = columnaAgentStationCode;
				break;
			case 31:
				drb.addColumn(columnCreateStation);
				drb.addColumn(columnAgentCode);
				drb.addColumn(columnaAgentStationCode);
				drb.addColumn(columnClaimNumber);
				drb.addColumn(columnaFaultStationCode);
				drb.addColumn(columnaLossCode);
				drb.addColumn(columnaPaidDate);
				drb.addColumn(columnaPayType);
				drb.addColumn(columnaPayStatus);
				drb.addColumn(columnCheckAmount);
				drb.addColumn(columnCurrencyType);
				drb.addColumn(columnVoucher);
				drb.addColumn(columnMileage);

				groupByColumn = columnCreateStation;
				break;
			case 32:
				drb.addColumn(columnaFaultStationCode);
				drb.addColumn(columnAgentCode);
				drb.addColumn(columnaAgentStationCode);
				drb.addColumn(columnClaimNumber);
				drb.addColumn(columnCreateStation);
				drb.addColumn(columnaLossCode);
				drb.addColumn(columnaPaidDate);
				drb.addColumn(columnaPayType);
				drb.addColumn(columnaPayStatus);
				drb.addColumn(columnCheckAmount);
				drb.addColumn(columnCurrencyType);
				drb.addColumn(columnVoucher);
				drb.addColumn(columnMileage);

				groupByColumn = columnaFaultStationCode;
				break;
			case 40:
				drb.addColumn(columnaAgentStationCode);
				drb.addColumn(columnAgentCode);
				drb.addColumn(columnClaimNumber);
				drb.addColumn(columnCreateStation);
				drb.addColumn(columnaFaultStationCode);
				drb.addColumn(columnaLossCode);
				drb.addColumn(columnaPaidDate);
				drb.addColumn(columnaPayType);
				drb.addColumn(columnaPayStatus);
				drb.addColumn(columnCheckAmount);
				drb.addColumn(columnCurrencyType);
				drb.addColumn(columnVoucher);
				drb.addColumn(columnMileage);

				groupByColumn = columnaAgentStationCode;
				break;
			default:
				drb.addColumn(columnCreateStation);
				drb.addColumn(columnClaimNumber);
				drb.addColumn(columnaFaultStationCode);
				drb.addColumn(columnaLossCode);
				drb.addColumn(columnAgentCode);
				drb.addColumn(columnaAgentStationCode);
				drb.addColumn(columnaPaidDate);
				drb.addColumn(columnaPayType);
				drb.addColumn(columnaPayStatus);
				drb.addColumn(columnCheckAmount);
				drb.addColumn(columnCurrencyType);
				drb.addColumn(columnVoucher);
				drb.addColumn(columnMileage);

				groupByColumn = columnCreateStation;
				break;
			}

			gb1 = new GroupBuilder();
			g1 = gb1.setCriteriaColumn((PropertyColumn) groupByColumn)
					.addFooterVariable(columnMileage, DJCalculation.SUM,
							groupVariables)
					// idem for the columnaQuantity column
					.addFooterVariable(columnVoucher, DJCalculation.SUM,
							groupVariables)
					.addFooterVariable(columnCheckAmount, DJCalculation.SUM,
							groupVariables)
					// tell the group place a variable footer of the column
					// "columnAmount" with the SUM of allvalues of the
					// columnAmount in this group.
					.addFooterVariable(columnaPayType,
							getSubTotalCustomExpression(), groupVariables)
					.setGroupLayout(GroupLayout.DEFAULT)
					.setFooterVariablesHeight(new Integer(20))
					.setFooterHeight(new Integer(50))
					.setHeaderVariablesHeight(new Integer(35)).build();

			drb.addGroup(g1);

			// //: playing with charts
			// DJChartBuilder cb = new DJChartBuilder();
			// //DJChart chart = cb.addType(DJChart.BAR_CHART)
			// DJChart chart = cb.setType(DJChart.BAR_CHART)
			// .setOperation(DJChart.CALCULATION_SUM)
			// .setColumnsGroup(g1)
			// .addColumn(columnCheckAmount)
			// .build();
			//
			// drb.addChart(chart); //add chart

		}

		return drb;
	}

	private static DynamicReport buildReport(int reportStyle,
			Locale reportLocale, ResourceBundle resourceBundle)
			throws Exception {

		// getting report header title from resource bundle
		String reportHeadingCreateStation = "Create Station";
		String reportHeadingIncidentNumber = "Incident #";
		String reportHeadingFaultStation = "Fault Station";
		String reportHeadingLossCode = "Loss Code";
		String reportHeadingAgent = "Agent";
		String reportHeadingAgentStation = "Agent Station";
		String reportHeadingPaidDate = "Paid Date";
		String reportHeadingPaymentType = "Payment Type";
		String reportHeadingPaymentStatus = "Payment Status";
		String reportHeadingCheckAmount = "Check Amount";
		String reportHeadingVoucher = "Voucher";
		String reportHeadingMileage = "Mileage";

		String myCreateStationFromResource = resourceBundle
				.getString("report.disbursement.heading.create.station");
		if (!(myCreateStationFromResource == null || myCreateStationFromResource
				.equalsIgnoreCase(""))) {
			reportHeadingCreateStation = myCreateStationFromResource;
		}

		String myIncidentNumber = resourceBundle
				.getString("report.disbursement.heading.incident.number");
		if (!(myIncidentNumber == null || myIncidentNumber.equalsIgnoreCase(""))) {
			reportHeadingIncidentNumber = myIncidentNumber;
		}

		String myFaultStation = resourceBundle
				.getString("report.disbursement.heading.fault.station");
		if (!(myFaultStation == null || myFaultStation.equalsIgnoreCase(""))) {
			reportHeadingFaultStation = myFaultStation;
		}

		String myLossCode = resourceBundle
				.getString("report.disbursement.heading.loss.code");
		if (!(myLossCode == null || myLossCode.equalsIgnoreCase(""))) {
			reportHeadingLossCode = myLossCode;
		}

		String myAgent = resourceBundle
				.getString("report.disbursement.heading.agent");
		if (!(myAgent == null || myAgent.equalsIgnoreCase(""))) {
			reportHeadingAgent = myAgent;
		}

		String myAgentStation = resourceBundle
				.getString("report.disbursement.heading.agent.station");
		if (!(myAgentStation == null || myAgentStation.equalsIgnoreCase(""))) {
			reportHeadingAgentStation = myAgentStation;
		}

		String myPaidDate = resourceBundle
				.getString("report.disbursement.heading.paid.date");
		if (!(myPaidDate == null || myPaidDate.equalsIgnoreCase(""))) {
			reportHeadingPaidDate = myPaidDate;
		}

		String myPaymentType = resourceBundle
				.getString("report.disbursement.heading.payment.type");
		if (!(myPaymentType == null || myPaymentType.equalsIgnoreCase(""))) {
			reportHeadingPaymentType = myPaymentType;
		}

		String myPaymentStatus = resourceBundle
				.getString("report.disbursement.heading.payment.status");
		if (!(myPaymentStatus == null || myPaymentStatus.equalsIgnoreCase(""))) {
			reportHeadingPaymentStatus = myPaymentStatus;
		}

		String myCheckAmount = resourceBundle
				.getString("report.disbursement.heading.check.amount");
		if (!(myCheckAmount == null || myCheckAmount.equalsIgnoreCase(""))) {
			reportHeadingCheckAmount = myCheckAmount;
		}

		String myVoucher = resourceBundle
				.getString("report.disbursement.heading.voucher");
		if (!(myVoucher == null || myVoucher.equalsIgnoreCase(""))) {
			reportHeadingVoucher = myVoucher;
		}

		String myMileage = resourceBundle
				.getString("report.disbursement.heading.mileage");
		if (!(myMileage == null || myMileage.equalsIgnoreCase(""))) {
			reportHeadingMileage = myMileage;
		}

		Style detailStyle = new Style("detail");

		Style headerStyle = new Style("header");
		headerStyle.setBackgroundColor(Color.LIGHT_GRAY);
		headerStyle.setVerticalAlign(VerticalAlign.MIDDLE);
		headerStyle.setTransparency(Transparency.OPAQUE);

		Style headerVariables = new Style("headerVariables");
		headerVariables.setHorizontalAlign(HorizontalAlign.RIGHT);
		headerVariables.setVerticalAlign(VerticalAlign.TOP);
		headerVariables.setFont(new Font(12, Font._FONT_TIMES_NEW_ROMAN, true));

		Style groupVariables = new Style("groupVariables");
		groupVariables.setHorizontalAlign(HorizontalAlign.LEFT);
		groupVariables.setVerticalAlign(VerticalAlign.BOTTOM);

		Style titleStyle = new Style("titleStyle");
		titleStyle.setFont(new Font(16, Font._FONT_TIMES_NEW_ROMAN, true));
		titleStyle.setHorizontalAlign(HorizontalAlign.CENTER);

		Style oddRowStyle = new Style();
		// oddRowStyle.setBorder(Border.NO_BORDER);
		// oddRowStyle.setBackgroundColor(Color.LIGHT_GRAY);
		// oddRowStyle.setTransparency(Transparency.OPAQUE);

		DynamicReportBuilder drb = new DynamicReportBuilder();
		drb.setPageSizeAndOrientation(Page.Page_A4_Landscape());

		// drb.setReportLocale(new Locale("pt","BR"));

		Integer margin = new Integer(20);
		drb.setTitleStyle(titleStyle).setTitle("Disbursement Report")
				.setSubtitle("This report was generated on " + new Date())
				.setDetailHeight(new Integer(15)).setLeftMargin(margin)
				.setRightMargin(margin).setTopMargin(margin)
				.setBottomMargin(margin).setPrintBackgroundOnOddRows(true)
				.setGrandTotalLegend("Grand Total :   ")
				.setGrandTotalLegendStyle(headerVariables)
				.setOddRowBackgroundStyle(oddRowStyle);

		AbstractColumn columnCreateStation = ColumnBuilder.getNew()
				.setColumnProperty("stationcode", String.class.getName())
				.setTitle(reportHeadingCreateStation).setWidth(new Integer(40))
				.setStyle(detailStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnClaimNumber = ColumnBuilder.getNew()
				.setColumnProperty("claim_number", String.class.getName())
				.setTitle(reportHeadingIncidentNumber)
				.setWidth(new Integer(84)).setStyle(detailStyle)
				.setHeaderStyle(headerStyle).build();

		AbstractColumn columnaFaultStationCode = ColumnBuilder.getNew()
				.setColumnProperty("faultstationcode", String.class.getName())
				.setTitle(reportHeadingFaultStation).setWidth(new Integer(40))
				.setStyle(detailStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnaLossCode = ColumnBuilder.getNew()
				.setColumnProperty("loss_code", Integer.class.getName())
				.setTitle(reportHeadingLossCode).setWidth(new Integer(30))
				.setStyle(detailStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnAgentCode = ColumnBuilder.getNew()
				.setColumnProperty("agent_username", String.class.getName())
				.setTitle(reportHeadingAgent).setWidth(new Integer(44))
				.setStyle(detailStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnaAgentStationCode = ColumnBuilder
				.getNew()
				.setColumnProperty("agent_station_code", String.class.getName())
				.setTitle(reportHeadingAgentStation).setWidth(new Integer(40))
				.setStyle(detailStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnaPaidDate = ColumnBuilder.getNew()
				.setColumnProperty("draftpaiddate", Date.class.getName())
				.setTitle(reportHeadingPaidDate).setWidth(new Integer(50))
				.setPattern("MM/dd/yyyy").setStyle(detailStyle)
				.setHeaderStyle(headerStyle).build();

		AbstractColumn columnaPayType = ColumnBuilder
				.getNew()
				.setColumnProperty("expenseType_description",
						String.class.getName())
				.setTitle(reportHeadingPaymentType).setWidth(new Integer(40))
				.setStyle(detailStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnaPayStatus = ColumnBuilder.getNew()
				.setColumnProperty("expenseStatus", String.class.getName())
				.setTitle(reportHeadingPaymentStatus).setWidth(new Integer(40))
				.setStyle(detailStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnCheckAmount = ColumnBuilder.getNew()
				.setColumnProperty("checkamt", Double.class.getName())
				.setTitle(reportHeadingCheckAmount).setWidth(new Integer(54))
				.setPattern("$ 0.00").setStyle(detailStyle)
				.setHeaderStyle(headerStyle).build();

		AbstractColumn columnCurrencyType = ColumnBuilder.getNew()
				.setColumnProperty("currency_ID", String.class.getName())
				.setTitle(" ").setWidth(new Integer(20)).setStyle(detailStyle)
				.setHeaderStyle(headerStyle).build();

		AbstractColumn columnVoucher = ColumnBuilder.getNew()
				.setColumnProperty("voucheramt", Double.class.getName())
				.setTitle(reportHeadingVoucher).setWidth(new Integer(54))
				.setPattern("$ 0.00").setStyle(detailStyle)
				.setHeaderStyle(headerStyle).build();

		AbstractColumn columnMileage = ColumnBuilder.getNew()
				.setColumnProperty("mileageamt", Integer.class.getName())
				.setTitle(reportHeadingMileage).setWidth(new Integer(32))
				.setStyle(detailStyle).setHeaderStyle(headerStyle).build();

		// : grand totals
		drb.addGlobalFooterVariable(columnCheckAmount, DJCalculation.SUM,
				headerVariables);
		drb.addGlobalFooterVariable(columnVoucher, DJCalculation.SUM,
				headerVariables);
		drb.addGlobalFooterVariable(columnMileage, DJCalculation.SUM,
				headerVariables);
		drb.setGlobalFooterVariableHeight(new Integer(45));

		Map<String, AbstractColumn> reportColumns = new HashMap<String, AbstractColumn>();
		reportColumns.put("columnCreateStation", columnCreateStation);
		reportColumns.put("columnClaimNumber", columnClaimNumber);
		reportColumns.put("columnaFaultStationCode", columnaFaultStationCode);
		reportColumns.put("columnaLossCode", columnaLossCode);
		reportColumns.put("columnAgentCode", columnAgentCode);
		reportColumns.put("columnaAgentStationCode", columnaAgentStationCode);
		reportColumns.put("columnaPaidDate", columnaPaidDate);
		reportColumns.put("columnaPayType", columnaPayType);
		reportColumns.put("columnaPayStatus", columnaPayStatus);
		reportColumns.put("columnCheckAmount", columnCheckAmount);
		reportColumns.put("columnCurrencyType", columnCurrencyType);
		reportColumns.put("columnVoucher", columnVoucher);
		reportColumns.put("columnMileage", columnMileage);

		drb = reportStyleSelector(drb, reportColumns, reportStyle);

		// drb.addAutoText(AutoText.AUTOTEXT_PAGE_X_OF_Y,
		// AutoText.POSITION_FOOTER, AutoText.ALIGMENT_CENTER,90,30);
		drb.setUseFullPageWidth(true);
		// drb.addAutoText(AutoText.AUTOTEXT_PAGE_X_OF_Y,
		// AutoText.POSITION_FOOTER, AutoText.ALIGMENT_CENTER);

		DynamicReport dr = drb.build();
		return dr;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String getReportFileDj(JRDataSource ds, Map parameters,
			String reportname, String rootpath, int outputtype,
			HttpServletRequest request, ReportBMO rbmo) throws Exception {
		/** look for compiled reports, if can't find it, compile xml report * */

		// added virtualizer to reduce memory
		String outfile = reportname
				+ "_"
				+ (new SimpleDateFormat("MMddyyyyhhmmss").format(TracerDateTime
						.getGMTDate()));
		// JRGovernedFileVirtualizer virtualizer = new
		// JRGovernedFileVirtualizer(2, rootpath + "/reports/tmp", 501);

		JRGovernedFileVirtualizer virtualizer = new JRGovernedFileVirtualizer(
				100, rootpath + "/reports/tmp", 501);
		virtualizer.setReadOnly(false);

		parameters.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
		// if (outputtype == TracingConstants.REPORT_OUTPUT_XLS) {
		// parameters.put(JRParameter.IS_IGNORE_PAGINATION, true);
		// }

		DynamicReport dr;
		JasperPrint jasperPrint;
		try {

			int reportStyle = 11;
			if (parameters.get("reportStyle") != null) {
				String myReportStyle = (String) parameters.get("reportStyle");
				reportStyle = Integer.parseInt(myReportStyle);
			}

			Locale reportLocale = new Locale("en", "US");
			if (parameters.get("reportLocale") != null) {
				reportLocale = (Locale) parameters.get("reportLocale");
			}

			ResourceBundle myResources = null;
			if (parameters.get("REPORT_RESOURCE_BUNDLE") != null) {
				myResources = (ResourceBundle) parameters
						.get("REPORT_RESOURCE_BUNDLE");
			}

			if (outputtype == TracingConstants.REPORT_OUTPUT_XLS) {
				dr = buildExcelReport(reportStyle, reportLocale, myResources);
			} else {
				dr = buildReport(reportStyle, reportLocale, myResources);
			}

			JasperReport jasperReport = DynamicJasperHelper
					.generateJasperReport(dr, new ClassicLayoutManager(),
							parameters);
			if (ds != null) {
				jasperPrint = JasperFillManager.fillReport(jasperReport,
						parameters, ds);
			} else {
				jasperPrint = JasperFillManager.fillReport(jasperReport,
						parameters);
			}

			virtualizer.setReadOnly(true);

			if (outputtype == TracingConstants.REPORT_OUTPUT_HTML)
				outfile += ".html";
			else if (outputtype == TracingConstants.REPORT_OUTPUT_PDF)
				// In the event the file type is undeclared, we will use the
				// default - PDF if available, HTML otherwise.
				if (!TracerProperties
						.isTrue(TracerProperties.SUPPRESSION_PRINTING_NONHTML)) {
					outfile += ".pdf";
				} else {
					outfile += ".html";
					outputtype = TracingConstants.REPORT_OUTPUT_HTML;
					request.setAttribute("outputtype", Integer
							.toString(TracingConstants.REPORT_OUTPUT_HTML));
				}

			else if (outputtype == TracingConstants.REPORT_OUTPUT_XLS)
				outfile += ".xls";
			else if (outputtype == TracingConstants.REPORT_OUTPUT_CSV)
				outfile += ".csv";
			else if (outputtype == TracingConstants.REPORT_OUTPUT_XML)
				outfile += ".xml";
			else if (outputtype == TracingConstants.REPORT_OUTPUT_UNDECLARED) {
				// In the event the file type is undeclared, we will use the
				// default - PDF if available, HTML otherwise.
				if (!TracerProperties
						.isTrue(TracerProperties.SUPPRESSION_PRINTING_NONHTML)) {
					outfile += ".pdf";
					outputtype = TracingConstants.REPORT_OUTPUT_PDF;
				} else {
					outfile += ".html";
					outputtype = TracingConstants.REPORT_OUTPUT_HTML;
					request.setAttribute("outputtype", Integer
							.toString(TracingConstants.REPORT_OUTPUT_HTML));
				}
			}

			String outputpath = rootpath + ReportingConstants.REPORT_TMP_PATH
					+ outfile;
			JRExporter exporter = null;

			if (outputtype == TracingConstants.REPORT_OUTPUT_PDF)
				JasperExportManager.exportReportToPdfFile(jasperPrint,
						outputpath);
			else if (outputtype == TracingConstants.REPORT_OUTPUT_HTML) {
				exporter = new JRHtmlExporter();

				Map imagesMap = new HashMap();

				exporter.setParameter(
						JRHtmlExporterParameter.BETWEEN_PAGES_HTML,
						"<div style=\"page-break-after: always\" border=\"0\">&nbsp;</div>");
				request.getSession().setAttribute("IMAGES_MAP", imagesMap);
				exporter.setParameter(JRHtmlExporterParameter.IMAGES_MAP,
						imagesMap);
				exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI,
						"image?image=");

				exporter.setParameter(
						JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, false);

				exporter.setParameter(JRExporterParameter.JASPER_PRINT,
						jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
						outputpath);

				// File outputFile = new File(outputpath);
				// FileOutputStream fos = new FileOutputStream(outputFile);
				//
				// exporter.setParameter(JRExporterParameter.JASPER_PRINT,
				// jasperPrint);
				// exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
				// fos);

				exporter.exportReport();
			}

			// : switch to JExcelApiExporter
			else if (outputtype == TracingConstants.REPORT_OUTPUT_XLS) {
				JExcelApiExporter alternativeExporter = new JExcelApiExporter();
				Map excelParameters = new HashMap();
				excelParameters.put(JExcelApiExporterParameter.JASPER_PRINT,
						jasperPrint);
				excelParameters
						.put(JExcelApiExporterParameter.OUTPUT_FILE_NAME,
								outputpath);
				excelParameters.put(
						JExcelApiExporterParameter.IS_ONE_PAGE_PER_SHEET,
						Boolean.FALSE);
				excelParameters.put(
						JExcelApiExporterParameter.IS_WHITE_PAGE_BACKGROUND,
						Boolean.FALSE);
				excelParameters.put(
						JExcelApiExporterParameter.IGNORE_PAGE_MARGINS,
						Boolean.TRUE);
				excelParameters
						.put(JExcelApiExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS,
								Boolean.TRUE);
				excelParameters
						.put(JExcelApiExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
								Boolean.TRUE);
				// excelParameters.put(JExcelApiExporterParameter.IS_DETECT_CELL_TYPE,
				// Boolean.TRUE);
				excelParameters.put(
						JExcelApiExporterParameter.IS_FONT_SIZE_FIX_ENABLED,
						Boolean.TRUE);
				excelParameters.put(
						JExcelApiExporterParameter.IS_COLLAPSE_ROW_SPAN,
						Boolean.TRUE);

				alternativeExporter.setParameters(excelParameters);
				alternativeExporter.exportReport();
			} else if (outputtype == TracingConstants.REPORT_OUTPUT_CSV) {
				exporter = new JRCsvExporter();

				exporter.setParameter(JRExporterParameter.JASPER_PRINT,
						jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
						outputpath);
				exporter.exportReport();
			} else if (outputtype == TracingConstants.REPORT_OUTPUT_XML) {
				exporter = new JRXmlExporter();

				exporter.setParameter(JRExporterParameter.JASPER_PRINT,
						jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
						outputpath);
				exporter.exportReport();
			}
		} catch (JRMaxFilesException e) {
			logger.error("JRMaxFilesException encountered...");
			logger.error("Report Name: " + reportname);
			Agent user = (Agent) request.getSession().getAttribute("user");
			logger.error("User ID: " + user.getAgent_ID() + " User Name: "
					+ user.getUsername());

			for (Object obj : parameters.keySet()) {
				logger.error("Parameter: " + obj.toString() + "  Value: "
						+ parameters.get(obj).toString());
			}
			outfile = null;
			if (rbmo != null) {
				rbmo.setErrormsg("error.maxpages");
			}
		}

		virtualizer.cleanup();

		return outfile;
	}

	// dj reporting ends

	/**
	 * @param errormsg
	 *            The errormsg to set.
	 */
	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}

	/**
	 * method to calculate the agent timezone based date/time for comparing
	 * dates in db
	 * 
	 * @param sdate
	 * @param sdate1
	 * @param edate
	 * @param edate1
	 * @param stime
	 * @param srDTO
	 * @param tz
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList calculateDateDiff(StatReportDTO srDTO, TimeZone tz,
			Agent u) {
		ArrayList al = new ArrayList();
		Date sdate = null, sdate1 = null, edate = null, edate1 = null, stime = null, sdate2 = null, edate2 = null;
		if (srDTO.getStarttime() != null && srDTO.getStarttime().length() > 0) {
			sdate = DateUtils.convertToDate(srDTO.getStarttime(), u
					.getDateformat().getFormat(), null);
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
			stime = DateUtils
					.convertToDate(
							Integer.toString(TracerDateTime.getHourDiff(tz)),
							"H", null);

		}
		if (srDTO.getEndtime() != null && srDTO.getEndtime().length() > 0) {
			edate = DateUtils.convertToDate(srDTO.getEndtime(), u
					.getDateformat().getFormat(), null);
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
			sdate2 = DateUtils.convertToDate(srDTO.getCstarttime() + " 0"
					+ TracerDateTime.getHourDiff(tz) + ":00:00 AM",
					TracingConstants.DB_DATETIMEFORMAT_MSSQL, null);

		if (srDTO.getCendtime() != null && srDTO.getCendtime().length() > 0) {
			edate2 = DateUtils.convertToDate(srDTO.getCendtime() + " 0"
					+ TracerDateTime.getHourDiff(tz) + ":00:00 AM",
					TracingConstants.DB_DATETIMEFORMAT_MSSQL, null);
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

	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	public static String createSearchIncidentReport(ArrayList incidentArray,
			HttpServletRequest request, int outputtype, String language,
			String reportPath, ReportBMO rbmo) {
		try {

			Map parameters = new HashMap();
			HttpSession session = request.getSession();
			Agent user = (Agent) session.getAttribute("user");

			ResourceBundle myResources = ResourceBundle
					.getBundle(
							"com.bagnet.nettracer.tracing.resources.ApplicationResources",
							new Locale(language));
			parameters.put("REPORT_RESOURCE_BUNDLE", myResources);
			parameters.put("showdetail", "1");
			parameters.put("form", request.getAttribute("searchIncidentForm"));
			IncidentBMO bmo = new IncidentBMO();
			BagService bs = new BagService();

			JasperReport jasperReport = getCompiledReport(
					ReportingConstants.SEARCH_INCIDENT_RPT_NAME, reportPath);
			JRDataSource ds = new JRIncidentDataSource(jasperReport,
					incidentArray, (Agent) request.getSession().getAttribute(
							"user"));
			return rbmo.getReportFile(jasperReport, ds, parameters,
					ReportingConstants.SEARCH_INCIDENT_RPT_NAME, reportPath,
					outputtype);

		} catch (Exception e) {
			logger.error("unable to search incident report: " + e, e);
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	public static String createSearchOnhandReport(List onhandArray,
			HttpServletRequest request, int outputtype, String language,
			String reportPath, ReportBMO rbmo) {
		try {

			Map parameters = new HashMap();
			HttpSession session = request.getSession();
			Agent user = (Agent) session.getAttribute("user");

			ResourceBundle myResources = ResourceBundle
					.getBundle(
							"com.bagnet.nettracer.tracing.resources.ApplicationResources",
							new Locale(language));
			parameters.put("REPORT_RESOURCE_BUNDLE", myResources);
			parameters.put("showdetail", "1");
			parameters.put("form", request.getAttribute("searchIncidentForm"));

			IncidentBMO bmo = new IncidentBMO();
			BagService bs = new BagService();

			JasperReport jasperReport = getCompiledReport(
					ReportingConstants.SEARCH_ONHAND_RPT_NAME, reportPath);
			JRDataSource ds = new JROnhandDataSource(jasperReport, onhandArray,
					(Agent) request.getSession().getAttribute("user"));
			return rbmo.getReportFile(jasperReport, ds, parameters,
					ReportingConstants.SEARCH_ONHAND_RPT_NAME, reportPath,
					outputtype);

		} catch (Exception e) {
			logger.error("unable to search incident report: " + e);
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	public static String createStationOnhandReport(List onhandArray,
			HttpServletRequest request, int outputtype, String language,
			String reportPath, ReportBMO rbmo) {
		try {

			Map parameters = new HashMap();
			HttpSession session = request.getSession();
			Agent user = (Agent) session.getAttribute("user");

			ResourceBundle myResources = ResourceBundle
					.getBundle(
							"com.bagnet.nettracer.tracing.resources.ApplicationResources",
							new Locale(language));
			parameters.put("REPORT_RESOURCE_BUNDLE", myResources);
			parameters.put("showdetail", "1");
			parameters.put("form", request.getAttribute("searchIncidentForm"));

			IncidentBMO bmo = new IncidentBMO();
			BagService bs = new BagService();

			JasperReport jasperReport = getCompiledReport(
					ReportingConstants.STATION_ONHAND_RPT_NAME, reportPath);
			JRDataSource ds = new JROnhandDataSource(jasperReport, onhandArray,user);
			return rbmo.getReportFile(jasperReport, ds, parameters,
					ReportingConstants.STATION_ONHAND_RPT_NAME, reportPath,
					outputtype);

		} catch (Exception e) {
			logger.error("unable to create station onhand report: " + e);
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	public static String createSearchScandataReport(ScannerDTO dto,
			HttpServletRequest request, int outputtype, String language,
			String reportPath) {
		try {

			Map parameters = new HashMap();
			HttpSession session = request.getSession();
			Agent user = (Agent) session.getAttribute("user");

			ResourceBundle myResources = ResourceBundle
					.getBundle(
							"com.bagnet.nettracer.tracing.resources.ApplicationResources",
							new Locale(language));
			parameters.put("REPORT_RESOURCE_BUNDLE", myResources);
			parameters.put("form", request.getAttribute("scannerDataForm"));

			IncidentBMO bmo = new IncidentBMO();
			BagService bs = new BagService();

			ReportBMO rbmo = new ReportBMO(request);
			JasperReport jasperReport = getCompiledReport(
					ReportingConstants.SEARCH_SCANDATA_RPT_NAME, reportPath);
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(
					dto.getScannerDataDTOs());
			return rbmo.getReportFile(jasperReport, ds, parameters,
					ReportingConstants.SEARCH_ONHAND_RPT_NAME, reportPath,
					outputtype);

		} catch (Exception e) {
			logger.error("unable to search incident report: " + e);
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static List<Report> getAllCustomReports() {
		Session sess = null;
		try {
			sess = HibernateWrapper.getDirtySession().openSession();
			Criteria cri = sess.createCriteria(Report.class);
			return cri.list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@SuppressWarnings({ "unchecked" })
	public static Report getCustomReport(int number) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getDirtySession().openSession();
			Query query = sess.createQuery("from Report where number = :num");
			query.setParameter("num", number);

			List<Report> list = query.list();
			if (list.size() == 1) {
				return list.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public JasperPrint getJasperPrint(Map<String, Object> parameters,
			String reportName, String rootPath, JRBeanCollectionDataSource ds)
			throws Exception {
		// TODO Auto-generated method stub
		JasperReport jasperReport = getCompiledReport(reportName, rootPath);
		if (ds == null) {
			return JasperFillManager.fillReport(jasperReport, parameters);
		} else {
			return JasperFillManager.fillReport(jasperReport, parameters, ds);
		}

	}

	// custom dynamic report for AirTran MBR Report
	@SuppressWarnings("rawtypes")
	public static String getDynamicReportFile(List list, Map parameters,
			String reportname, String rootpath, int outputtype,
			HttpServletRequest request) throws Exception {
		// JasperReport jasperReport = getCompiledReport(reportname, rootpath);

		// compose JasperReport and prepare list
		JasperReport jasperReport = null;
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(list);
		return getDynamicReportFile(jasperReport, ds, parameters, reportname,
				rootpath, outputtype, request, null);
	}

	// custom dynamic report for AirTran MBR Report
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String getDynamicReportFile(JasperReport jasperReport,
			JRDataSource ds, Map parameters, String reportname,
			String rootpath, int outputtype, HttpServletRequest request,
			ReportBMO rbmo) throws Exception {
		/** look for compiled reports, if can't find it, compile xml report * */

		// added virtualizer to reduce memory
		String outfile = reportname
				+ "_"
				+ (new SimpleDateFormat("MMddyyyyhhmmss").format(TracerDateTime
						.getGMTDate()));
		JRGovernedFileVirtualizer virtualizer = new JRGovernedFileVirtualizer(
				2, rootpath + "/reports/tmp", 501);

		parameters.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);

		if (outputtype == TracingConstants.REPORT_OUTPUT_XLS) {
			parameters.put(JRParameter.IS_IGNORE_PAGINATION, true);
		}

		try {

			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport, parameters, ds);

			virtualizer.setReadOnly(true);

			if (outputtype == TracingConstants.REPORT_OUTPUT_HTML)
				outfile += ".html";
			else if (outputtype == TracingConstants.REPORT_OUTPUT_PDF)
				// In the event the file type is undeclared, we will use the
				// default - PDF if available, HTML otherwise.
				if (!TracerProperties
						.isTrue(TracerProperties.SUPPRESSION_PRINTING_NONHTML)) {
					outfile += ".pdf";
				} else {
					outfile += ".html";
					outputtype = TracingConstants.REPORT_OUTPUT_HTML;
					request.setAttribute("outputtype", Integer
							.toString(TracingConstants.REPORT_OUTPUT_HTML));
				}

			else if (outputtype == TracingConstants.REPORT_OUTPUT_XLS)
				outfile += ".xls";
			else if (outputtype == TracingConstants.REPORT_OUTPUT_CSV)
				outfile += ".csv";
			else if (outputtype == TracingConstants.REPORT_OUTPUT_XML)
				outfile += ".xml";
			else if (outputtype == TracingConstants.REPORT_OUTPUT_UNDECLARED) {
				// In the event the file type is undeclared, we will use the
				// default - PDF if available, HTML otherwise.
				if (!TracerProperties
						.isTrue(TracerProperties.SUPPRESSION_PRINTING_NONHTML)) {
					outfile += ".pdf";
					outputtype = TracingConstants.REPORT_OUTPUT_PDF;
				} else {
					outfile += ".html";
					outputtype = TracingConstants.REPORT_OUTPUT_HTML;
					request.setAttribute("outputtype", Integer
							.toString(TracingConstants.REPORT_OUTPUT_HTML));
				}
			}

			String outputpath = rootpath + ReportingConstants.REPORT_TMP_PATH
					+ outfile;
			JRExporter exporter = null;

			if (outputtype == TracingConstants.REPORT_OUTPUT_PDF)
				JasperExportManager.exportReportToPdfFile(jasperPrint,
						outputpath);
			else if (outputtype == TracingConstants.REPORT_OUTPUT_HTML) {
				exporter = new JRHtmlExporter();

				Map imagesMap = new HashMap();

				exporter.setParameter(
						JRHtmlExporterParameter.BETWEEN_PAGES_HTML,
						"<div style=\"page-break-after: always\" border=\"0\">&nbsp;</div>");
				request.getSession().setAttribute("IMAGES_MAP", imagesMap);
				exporter.setParameter(JRHtmlExporterParameter.IMAGES_MAP,
						imagesMap);
				exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI,
						"image?image=");

				exporter.setParameter(JRExporterParameter.JASPER_PRINT,
						jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
						outputpath);
				// exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
				// Boolean.TRUE);
				exporter.exportReport();
			}

			else if (outputtype == TracingConstants.REPORT_OUTPUT_XLS) {
				exporter = new JRXlsExporter();

				exporter.setParameter(JRExporterParameter.JASPER_PRINT,
						jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
						outputpath);
				exporter.setParameter(
						JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, false);
				exporter.setParameter(
						JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, false);
				exporter.setParameter(
						JRXlsExporterParameter.IGNORE_PAGE_MARGINS, true);
				exporter.setParameter(
						JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
						true);
				exporter.setParameter(
						JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS,
						true);
				// exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE,
				// true); // 01-13-2010 attempt to fix excel problem
				// exporter.setParameter(JRXlsExporterParameter.MAXIMUM_ROWS_PER_SHEET,
				// 65536);
				exporter.exportReport();
			} else if (outputtype == TracingConstants.REPORT_OUTPUT_CSV) {
				exporter = new JRCsvExporter();

				exporter.setParameter(JRExporterParameter.JASPER_PRINT,
						jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
						outputpath);
				exporter.exportReport();
			} else if (outputtype == TracingConstants.REPORT_OUTPUT_XML) {
				exporter = new JRXmlExporter();

				exporter.setParameter(JRExporterParameter.JASPER_PRINT,
						jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
						outputpath);
				exporter.exportReport();
			}
		} catch (JRMaxFilesException e) {
			logger.error("JRMaxFilesException encountered...");
			logger.error("Report Name: " + reportname);
			Agent user = (Agent) request.getSession().getAttribute("user");
			logger.error("User ID: " + user.getAgent_ID() + " User Name: "
					+ user.getUsername());

			for (Object obj : parameters.keySet()) {
				logger.error("Parameter: " + obj.toString() + "  Value: "
						+ parameters.get(obj).toString());
			}
			outfile = null;
			if (rbmo != null) {
				rbmo.setErrormsg("error.maxpages");
			}
		}

		virtualizer.cleanup();

		return outfile;
	}

	public String getReportFileName(int incidentType, String reportLocale, IncidentForm theform) {
		String customFiles = null;
		String locale = "";
		String customType = getCustomType(incidentType, theform);
		switch (incidentType) {
		case TracingConstants.LOST_DELAY:
			customFiles = PropertyBMO.getValue(PropertyBMO.CUSTOM_DELAY_RECEIPT_FILES);
			locale = customFileCheck(reportLocale, customFiles, locale);
			return "LostDelayReceipt" + customType + locale;

		case TracingConstants.DAMAGED_BAG:
			customFiles = PropertyBMO.getValue(PropertyBMO.CUSTOM_DAMAGE_RECEIPT_FILES);
			locale = customFileCheck(reportLocale, customFiles, locale);
			return "DamageReceipt" + customType + locale;

		case TracingConstants.MISSING_ARTICLES:
			customFiles = PropertyBMO.getValue(PropertyBMO.CUSTOM_MISSING_RECEIPT_FILES);
			locale = customFileCheck(reportLocale, customFiles, locale);
			return "MissingReceipt" + customType + locale;

		}
		return null;
	}
	
	private String getCustomType(int incidentType, IncidentForm theform) {
		String customType = "";
		if (PropertyBMO.isTrue(PropertyBMO.RECEIPT_CUSTOM_TYPES) && theform != null) {
			if (incidentType != TracingConstants.MISSING_ARTICLES && theform.getItemlist() != null && theform.getItemlist().size() > 0) {
				for (Item item : theform.getItemlist()) {
					if ("94".equals(item.getBagtype()) || "95".equals(item.getBagtype())) {
						customType = "_assist";
						break;
					}
				}
			}
			if (theform.getCourtesyreport() == 1) {
				customType += "_courtesy";
			}
			if (customType.length() == 0 && theform.getNonrevenue() == 1) {
				customType = "_nonrevenue";
			}
		}
		return customType;
	}

	private String customFileCheck(String reportLocale, String customFiles,
			String locale) {
		String[] list = customFiles.split(",");
		if (reportLocale != null) {
			for (String x : list) {
				if (x.equalsIgnoreCase(reportLocale)) {
					locale = "_" + reportLocale.toLowerCase();
				}
			}
		}
		return locale;
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	public String createInboundExpediteBagsReport(String rootpath,
			List bagsList, Agent user, StatReportDTO srDTO) {
		String result = "";

		try {
			logger.error("ready to print expedite baggage report in "
					+ srDTO.getOutputtype());
			Map parameters = new HashMap();
			ResourceBundle resourceBundle = ResourceBundle
					.getBundle(
							"com.bagnet.nettracer.tracing.resources.ApplicationResources",
							new Locale(user.getCurrentlocale()));
			parameters.put("REPORT_RESOURCE_BUNDLE", resourceBundle);

			String reporttitle = resourceBundle
					.getString("header.reportnum.30");
			String reportname = ReportingConstants.RPT_INBOUND_EXPEDITE_BAGS_NAME;

			parameters.put("reportLocale", new Locale(user.getCurrentlocale()));

			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(
					bagsList);

			result = InboundExpediteBagsReportBMO.getReportFileDj(ds,
					parameters, reportname, rootpath, srDTO.getOutputtype(),
					req, this);

		} catch (Exception e) {
			logger.error("unable to create report " + e);
			e.printStackTrace();
			result = null;
		}

		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String createSalvageReport(String root, int salvageId, Agent user) {
		Salvage salvage = SalvageDAO.loadSalvage(salvageId);
		if (salvage == null || root == null) {
			return null;
		}
		ResourceBundle resources = ResourceBundle.getBundle(
				"com.bagnet.nettracer.tracing.resources.ApplicationResources",
				new Locale(user.getCurrentlocale()));
		Map parameters = new HashMap();

		String fileName = ReportingConstants.SALVAGE_REPORT_NAME
				+ "_"
				+ salvageId
				+ "_"
				+ (new SimpleDateFormat(ReportingConstants.DATETIME_FORMAT)
						.format(TracerDateTime.getGMTDate()))
				+ ReportingConstants.EXCEL_FILE_TYPE;
		String outputpath = root + ReportingConstants.REPORT_TMP_PATH
				+ fileName;
		JRGovernedFileVirtualizer virtualizer = new JRGovernedFileVirtualizer(
				100, rootpath + ReportingConstants.REPORT_TMP_PATH, 501);
		virtualizer.setReadOnly(false);

		String airLine = CompanyBMO.getCompany(salvage.getCompanyCodeId())
				.getCompanydesc();

		FastReportBuilder drb = new FastReportBuilder();
		drb.setTitle(resources.getString("report.salvage.header") + " "
				+ airLine + " " + salvage.getSalvageId());
		drb.setSubtitle(resources.getString("report.salvage.date")
				+ " "
				+ (DateUtils.formatDate(salvage.getSalvageDate(), user
						.getDateformat().getFormat(), "", null)));
		try {
			Style header = new Style();
			header.setBackgroundColor(java.awt.Color.WHITE);
			header.setOverridesExistingStyle(true);
			drb.addColumn("", "col1", String.class.getName(), 50, header,
					header);
			drb.addColumn("", "col2", String.class.getName(), 200, header,
					header);
			drb.addColumn("", "col3", String.class.getName(), 50, header,
					header);
			drb.addColumn("", "col4", String.class.getName(), 50, header,
					header);

			DynamicReport report = drb.build();
			JRDataSource data = new JRBeanCollectionDataSource(
					new SalvageReport(resources).getSalvageReportItems(salvage));

			JasperPrint jp = DynamicJasperHelper.generateJasperPrint(report,
					new ClassicLayoutManager(), data);
			parameters.put(JRParameter.IS_IGNORE_PAGINATION, true);
			parameters.put(JExcelApiExporterParameter.JASPER_PRINT, jp);
			parameters.put(JExcelApiExporterParameter.OUTPUT_FILE_NAME,
					outputpath);
			parameters.put(JExcelApiExporterParameter.IS_ONE_PAGE_PER_SHEET,
					false);
			parameters.put(JExcelApiExporterParameter.IS_WHITE_PAGE_BACKGROUND,
					false);
			parameters.put(JExcelApiExporterParameter.IGNORE_PAGE_MARGINS,
					false);
			parameters
					.put(JExcelApiExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS,
							false);
			parameters
					.put(JExcelApiExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
							false);
			parameters.put(JExcelApiExporterParameter.IS_FONT_SIZE_FIX_ENABLED,
					true);
			parameters.put(JExcelApiExporterParameter.IS_COLLAPSE_ROW_SPAN,
					true);

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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String createSearchFoundItemReport(List<LFFound> lfcArray,
			HttpServletRequest request, int outputtype, String language,
			String reportPath, ReportBMO rbmo) {
		try {

			Map parameters = new HashMap();
			HttpSession session = request.getSession();
			Agent user = (Agent) session.getAttribute("user");

			ResourceBundle myResources = ResourceBundle
					.getBundle(
							"com.bagnet.nettracer.tracing.resources.ApplicationResources",
							new Locale(language));
			parameters.put("REPORT_RESOURCE_BUNDLE", myResources);
			parameters.put("showdetail", "1");
			parameters.put("form", request.getAttribute("searchLostFoundForm"));

			JasperReport jasperReport = getCompiledReport(
					ReportingConstants.SEARCH_FOUNDITEM_RPT_NAME, reportPath);
			JRDataSource ds = new JRFoundItemDataSource(jasperReport, lfcArray, user);
			return rbmo.getReportFile(jasperReport, ds, parameters,
					ReportingConstants.SEARCH_FOUNDITEM_RPT_NAME, reportPath,
					outputtype);

		} catch (Exception e) {
			logger.error("unable to search incident report: " + e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @author Sean Fine
	 * Public Method that uses the Search Incident Form to get results and
	 * create a list of IncomingIncDTOs to create a Dynamic Jasper Report.
	 * @param daform - Form taking in to get results for report
	 * @param request - Request to get the session, user and other necessary information
	 * @param outputtype - the selected output type for the report
	 * @param language - User's language
	 * @param reportPath - Path to where the temp report is stored and retrieved
	 * @param sort - The Sort for the Results
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String createIncomingIncidentReport(SearchIncidentForm daform,
			HttpServletRequest request, int outputtype, String language,
			String reportPath, String sort) {

		try {
			BagService bs=new BagService();
			Map parameters = new HashMap();

			HttpSession session = request.getSession();
			Agent user = (Agent) session.getAttribute("user");

			ResourceBundle myResources = ResourceBundle
					.getBundle(
							"com.bagnet.nettracer.tracing.resources.ApplicationResources",
							new Locale(language));

			myResources.getString("header.report.incoming.incident");
			String title=myResources.getString("header.report.incoming.incident");
			parameters.put("title", title);
			parameters.put("REPORT_RESOURCE_BUNDLE", myResources);
			
			if(daform.getS_createtime()!=null && !daform.getS_createtime().isEmpty())
				parameters.put("sdate", daform.getS_createtime());
			if(daform.getE_createtime()!=null && !daform.getE_createtime().isEmpty())
				parameters.put("edate", daform.getE_createtime());
			
			parameters.put("reportLocale", new Locale(user.getCurrentlocale()));
			ArrayList resultlist=bs.findIncident(daform, user, 0, 0, false, true, sort);
			
			List<IncomingIncDTO> incIncList=new ArrayList();
			boolean showPosId=UserPermissions.hasPermission(TracingConstants.SYSTEM_COMPONENT_NAME_COLLECT_POS_ID, user);
			if(resultlist!=null){
				for(Object obj:resultlist){
					com.bagnet.nettracer.tracing.db.Incident inc=(com.bagnet.nettracer.tracing.db.Incident)obj;
					IncomingIncDTO iid=new IncomingIncDTO();
					iid.setIncident_ID(inc.getIncident_ID());
					iid.setIncidentType(inc.getItemtype().getDescription());
					if(inc.getItemtype_ID()==TracingConstants.DAMAGED_BAG || inc.getItemtype_ID()==TracingConstants.MISSING_ARTICLES){
						showPosId=false;
					}
					iid.setCreatedDate(inc.getDisplaydate());
					if(inc.getStationcreated()!=null && inc.getStationcreated().getCompany()!=null){
						iid.setAirlineCreated(inc.getStationcreated().getCompany().getCompanyCode_ID());
						iid.setStationCreated(inc.getStationcreated().getStationcode());
					}
					
					if(inc.getStationassigned()!=null){
						iid.setStationAssigned(inc.getStationassigned().getStationcode());
					}
					
					iid.setStatus(inc.getStatus().getDescription());
					String colorList="";
					String typeList="";
					String posList="";
					String claimCheckList="";
					String passengerList="";
					String itineraryList="";
					int i=0;
					if(inc.getItemlist()!=null){
						for(com.bagnet.nettracer.tracing.db.Item item:inc.getItemlist()){
							if(i!=0){
								if(!colorList.isEmpty()){
									colorList+="\n";
								}
								if(!typeList.isEmpty()){
									typeList+="\n";
								}
								if(!posList.isEmpty()){
									posList+="\n";
								}
								if(!claimCheckList.isEmpty()){
									claimCheckList+="\n";
								}
							}
							if(item.getColor()!=null && !item.getColor().isEmpty())
								colorList+=item.getColor();
							if(item.getBagtype()!=null && !item.getBagtype().isEmpty())
								typeList+=item.getBagtype();
							if(item.getPosId()!=null && !item.getPosId().isEmpty())
								posList+=item.getPosId();
							if(item.getClaimchecknum()!=null && !item.getClaimchecknum().isEmpty())
								claimCheckList+=item.getClaimchecknum();
							i++;
						}
					}
					
					if(inc.getClaimcheck_list()!=null){
						for(com.bagnet.nettracer.tracing.db.Incident_Claimcheck incCC:inc.getClaimcheck_list()){
							if(incCC.getClaimchecknum()!=null && !incCC.getClaimchecknum().isEmpty()){
								if(!claimCheckList.isEmpty()){
									claimCheckList+="\n";
								}
								claimCheckList+=incCC.getClaimchecknum();
							}
						}
						
					}
					
					if(inc.getPassenger_list()!=null){
						i=0;
						for(com.bagnet.nettracer.tracing.db.Passenger pass:inc.getPassenger_list()){
							boolean passLast=false;
							boolean passFirst=false;

							if(i!=0 && !passengerList.isEmpty()){
								passengerList+="\n";
							}

							if(pass.getLastname()!=null && !pass.getLastname().isEmpty()){
								passLast=true;
							}
							if(pass.getFirstname()!=null && !pass.getFirstname().isEmpty()){
								passFirst=true;
							}
							if(passLast){
								passengerList+=pass.getLastname();
							}
							if(passLast && passFirst){
								passengerList+=", ";
							}
							if(passFirst){
								passengerList+=pass.getFirstname();
							}
						}
					}
					
					if(inc.getItinerary_list()!=null){
						i=0;
						for(com.bagnet.nettracer.tracing.db.Itinerary itin:inc.getItinerary_list()){
							if(itin.getItinerarytype()==TracingConstants.PASSENGER_ROUTING){
								if(i!=0 && !itineraryList.isEmpty()){
									itineraryList+="\n";
								}
								if(itin.getAirline()!=null && !itin.getAirline().isEmpty()){
									itineraryList+=itin.getAirline()+" ";
								}
								if(itin.getFlightnum()!=null && !itin.getFlightnum().isEmpty()){
									itineraryList+=itin.getFlightnum()+" ";
								}
								if(itin.getDisdepartdate()!=null && !itin.getDisdepartdate().isEmpty()){
									itineraryList+=itin.getDisdepartdate();
								}
							}
						}
					}
					
					iid.setColor(colorList);
					iid.setType(typeList);
					iid.setPositionId(posList);
					iid.setClaimCheck(claimCheckList);
					iid.setItinerary(itineraryList);
					iid.setPassengerName(passengerList);
					
					incIncList.add(iid);
				}
			}
			if(showPosId){
				parameters.put("showPosId", showPosId);
			}
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(incIncList);
			return IncomingIncReportBMO.getReportFileDj(ds, parameters, ReportingConstants.RPT_INCOMING_INCIDENT_NAME,
					reportPath, outputtype, req, this);
		} catch (MissingRequiredFieldsException e) {
			setErrormsg("error.missingRequired");
			return null;
		} catch (AmountOfDataOutOfRangeException e) {
			setErrormsg("error.amount.of.data.exceeds.limit");
			return null;
		} catch (Exception e){
			logger.error("unable to incoming incident report: " + e);
			e.printStackTrace();
			return null;
		} 
		
	}

}