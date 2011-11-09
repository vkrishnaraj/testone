package com.bagnet.clients.ab;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import aero.nettracer.lf.services.LFLogUtil;
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.AutoText;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;

import com.bagnet.nettracer.other.JRGovernedFileVirtualizer;
import com.bagnet.nettracer.reporting.LostFoundJasperReport;
import com.bagnet.nettracer.reporting.ReportingConstants;
import com.bagnet.nettracer.tracing.bmo.CompanyBMO;
import com.bagnet.nettracer.tracing.bmo.ReportBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.dto.StatReportDTO;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;

public class CustomReportBMO implements com.bagnet.nettracer.integrations.reports.CustomReportBMO {

	private static final Logger logger = Logger.getLogger(CustomReportBMO.class);
	public static String newline = System.getProperty("line.separator");

	public String createCustomReport(StatReportDTO srDTO, HttpServletRequest request, Agent user, String rootpath) {
		String creportdata = null;
		switch (srDTO.getCustomreportnum()) {
		case ReportingConstants.RPT_20_CUSTOM_78:
			creportdata = createLostFoundReport(srDTO, ReportBMO.getCustomReport(78).getResource_key(), rootpath, request, user);
			break;
		case ReportingConstants.RPT_20_CUSTOM_86:
			creportdata = createLostFoundLogReport(srDTO, ReportBMO.getCustomReport(86).getResource_key(), rootpath, request, user);
			break;
		case ReportingConstants.RPT_20_CUSTOM_87:
			creportdata = createLFItemRecoveryReport(srDTO, ReportBMO.getCustomReport(87).getResource_key(), rootpath, request, user);
			break;
		default:
			break;

		}
		return creportdata;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String createLostFoundReport(StatReportDTO srDTO, String resourceKey, String rootpath, HttpServletRequest request, Agent user) {
		String dateFormat = user.getDateformat().getFormat();
		srDTO.setDateFormat(dateFormat);
		String runDate = DateUtils.formatDate(new Date(), dateFormat, user.getDefaultlocale(), null);
		ResourceBundle resources = ResourceBundle.getBundle("com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(user.getCurrentlocale()));
		List lostFoundReportData = new LostFoundJasperReport().getReportData(srDTO);
		if (lostFoundReportData == null) {
			return null;
		}
		
		Map parameters = new HashMap();
		
		String fileName = ReportingConstants.LOST_FOUND_REPORT_NAME + "_" + (new SimpleDateFormat(ReportingConstants.DATETIME_FORMAT).format(TracerDateTime.getGMTDate())) + ReportingConstants.EXCEL_FILE_TYPE;
		String outputpath = rootpath + ReportingConstants.REPORT_TMP_PATH + fileName;
		JRGovernedFileVirtualizer virtualizer = new JRGovernedFileVirtualizer(100, rootpath + ReportingConstants.REPORT_TMP_PATH, 501);
		virtualizer.setReadOnly(false);
		
		String company = CompanyBMO.getCompany(user.getCompanycode_ID()).getCompanydesc();
		FastReportBuilder drb = new FastReportBuilder();
		drb.setTitle(company + " " + resources.getString("report.lost.found.header") + " " + runDate);
		drb.setSubtitle(getSubTitle(srDTO, resources));
		try {
			Style header = new Style();
			header.setHorizontalAlign(HorizontalAlign.CENTER);
			header.setVerticalAlign(VerticalAlign.MIDDLE);
			Style detailStyle = new Style("detail");
			
			drb.addColumn(resources.getString("report.lost.found.id"), "id", Long.class.getName(), 50, detailStyle, header);
			drb.addColumn(resources.getString("report.lost.found.date"), "date", String.class.getName(), 60, detailStyle, header);
			drb.addColumn(resources.getString("report.lost.found.station"), "station", String.class.getName(), 50, detailStyle, header);
			if (srDTO.getType() == TracingConstants.LF_TYPE_LOST) {
				// MAKE NAME FOR DROP OFF STATION
				drb.addColumn(resources.getString("report.lost.found.dropoff"), "dropoff", String.class.getName(), 50, detailStyle, header);
			}
			drb.addColumn(resources.getString("report.lost.found.status"), "status", String.class.getName(), 50, detailStyle, header);			
			drb.addColumn(resources.getString("report.lost.found.disposition"), "disposition", String.class.getName(), 125, detailStyle, header);			
			drb.addColumn(resources.getString("report.lost.found.tracking.number"), "trackingNumber", String.class.getName(), 125, detailStyle, header);			
			
			drb.setIgnorePagination(true);
			drb.setUseFullPageWidth(true);

			DynamicReport report = drb.build();
			JRDataSource data = new JRBeanCollectionDataSource(lostFoundReportData);
			
			JasperPrint jp = DynamicJasperHelper.generateJasperPrint(report, new ClassicLayoutManager(), data);
			parameters.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
			parameters.put(JExcelApiExporterParameter.JASPER_PRINT, jp);
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
		
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String createLostFoundLogReport(StatReportDTO srDTO, String resourceKey, String rootpath, HttpServletRequest request, Agent user) {
		String dateFormat = user.getDateformat().getFormat();
		srDTO.setDateFormat(dateFormat);
		String runDate = DateUtils.formatDate(new Date(), dateFormat, user.getDefaultlocale(), null);
		ResourceBundle resources = ResourceBundle.getBundle("com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(user.getCurrentlocale()));
		List lostFoundReportData = LFLogUtil.searchLog(srDTO, user);
		if (lostFoundReportData == null) {
			return null;
		}
		
		Map parameters = new HashMap();
		
		String fileName = ReportingConstants.RPT_20_CUSTOM_86_NAME + "_" + (new SimpleDateFormat(ReportingConstants.DATETIME_FORMAT).format(TracerDateTime.getGMTDate())) + ReportingConstants.EXCEL_FILE_TYPE;
		String outputpath = rootpath + ReportingConstants.REPORT_TMP_PATH + fileName;
		JRGovernedFileVirtualizer virtualizer = new JRGovernedFileVirtualizer(100, rootpath + ReportingConstants.REPORT_TMP_PATH, 501);
		virtualizer.setReadOnly(false);
		
		String company = CompanyBMO.getCompany(user.getCompanycode_ID()).getCompanydesc();
		FastReportBuilder drb = new FastReportBuilder();
		drb.setTitle(company + " " + resources.getString("header.customreportnum.86") + " " + runDate);
		try {
			Style header = new Style();
			header.setHorizontalAlign(HorizontalAlign.CENTER);
			header.setVerticalAlign(VerticalAlign.MIDDLE);
			Style detailStyle = new Style("detail");
			
			drb.addColumn(resources.getString("custom.report.column.86.1"), "stamp", Date.class.getName(), 50, detailStyle, header);
			drb.addColumn(resources.getString("custom.report.column.86.2"), "agent", String.class.getName(), 60, detailStyle, header);
			drb.addColumn(resources.getString("custom.report.column.86.3"), "event", String.class.getName(), 50, detailStyle, header);
			drb.addColumn(resources.getString("custom.report.column.86.4"), "lflost_id", Integer.class.getName(), 50, detailStyle, header);			
			drb.addColumn(resources.getString("custom.report.column.86.5"), "lffound_id", Integer.class.getName(), 125, detailStyle, header);		
			
			drb.setIgnorePagination(true);
			drb.setUseFullPageWidth(true);

			DynamicReport report = drb.build();
			JRDataSource data = new JRBeanCollectionDataSource(lostFoundReportData);
			
			JasperPrint jp = DynamicJasperHelper.generateJasperPrint(report, new ClassicLayoutManager(), data);
			parameters.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
			parameters.put(JExcelApiExporterParameter.JASPER_PRINT, jp);
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
	
	private String getSubTitle(StatReportDTO srDto, ResourceBundle resources) {
		String subtitle = "";
		if (srDto.getType() == TracingConstants.LF_TYPE_LOST) {
			subtitle += " " + resources.getString("report.lost.found.lost.reports") + ",";
		} else {
			subtitle += " " + resources.getString("report.lost.found.found.items") + ",";
		}
		
		subtitle += " " + srDto.getStarttime() + " - " + srDto.getEndtime() + ",";
		
		String station = srDto.getStation_ID()[0];
		if (station.equals("0")) {
			station = resources.getString("report.lost.found.all");
		}
		subtitle += " " + resources.getString("colname.lf.report.station") + ": " + station + ",";
		subtitle += " " + resources.getString("report.lost.found.status") + ": " + getStatusDescription(srDto.getStatus_ID(), resources) + ",";
		subtitle += " " + resources.getString("report.lost.found.disposition") + ": " + getStatusDescription(srDto.getDispositionId(), resources) + ",";
		
		subtitle = subtitle.substring(0, subtitle.lastIndexOf(','));
		
		return subtitle;
	}
	
	private String getStatusDescription(int statusId, ResourceBundle resources) {
		switch (statusId) {
			case TracingConstants.LF_STATUS_ALL:
				return resources.getString("search.option.all");
			case TracingConstants.LF_STATUS_OPEN:
				return resources.getString("STATUS_KEY_" + String.valueOf(TracingConstants.LF_STATUS_OPEN));
			case TracingConstants.LF_STATUS_CLOSED:
				return resources.getString("STATUS_KEY_" + String.valueOf(TracingConstants.LF_STATUS_CLOSED));
			case TracingConstants.LF_DISPOSITION_OTHER:
				return resources.getString("STATUS_KEY_" + String.valueOf(TracingConstants.LF_DISPOSITION_OTHER));
			case TracingConstants.LF_DISPOSITION_TO_BE_DELIVERED:
				return resources.getString("STATUS_KEY_" + String.valueOf(TracingConstants.LF_DISPOSITION_TO_BE_DELIVERED));
			case TracingConstants.LF_DISPOSITION_DELIVERED:
				return resources.getString("STATUS_KEY_" + String.valueOf(TracingConstants.LF_DISPOSITION_DELIVERED));
			case TracingConstants.LF_DISPOSITION_PICKED_UP:
				return resources.getString("STATUS_KEY_" + String.valueOf(TracingConstants.LF_DISPOSITION_PICKED_UP));
			case TracingConstants.LF_DISPOSITION_SALVAGED:
				return resources.getString("STATUS_KEY_" + String.valueOf(TracingConstants.LF_DISPOSITION_SALVAGED));
			default:
				return "";
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String createLFItemRecoveryReport(StatReportDTO srDTO, String resourceKey, String rootpath, HttpServletRequest request, Agent user) {
		String dateFormat = user.getDateformat().getFormat();
		srDTO.setDateFormat(dateFormat);
		ResourceBundle resources = ResourceBundle.getBundle("com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(user.getCurrentlocale()));
		List lostFoundReportData = new LostFoundJasperReport().getItemRecoveryReportData(srDTO);
		if (lostFoundReportData == null) {
			return null;
		}
		
		Map parameters = new HashMap();
		String reportName;
		if (srDTO.getType() == TracingConstants.LF_TYPE_LOST) {
			reportName = ReportingConstants.RPT_20_CUSTOM_87_LOST_NAME;
		} else {
			reportName = ReportingConstants.RPT_20_CUSTOM_87_FOUND_NAME;
		}
		String fileName = reportName + "_" + (new SimpleDateFormat(ReportingConstants.DATETIME_FORMAT).format(TracerDateTime.getGMTDate())) + ReportingConstants.EXCEL_FILE_TYPE;
		String outputpath = rootpath + ReportingConstants.REPORT_TMP_PATH + fileName;
		JRGovernedFileVirtualizer virtualizer = new JRGovernedFileVirtualizer(200, rootpath + ReportingConstants.REPORT_TMP_PATH, 501);
		virtualizer.setReadOnly(false);
		
		FastReportBuilder drb = new FastReportBuilder();
		boolean isLostReport = srDTO.getType() == TracingConstants.LF_TYPE_LOST;
		if (isLostReport) {
			drb.setTitle(resources.getString("header.customreportnum.87.lost"));
		} else {
			drb.setTitle(resources.getString("header.customreportnum.87.found"));
		}

		if (isLostReport) {
			drb.setPrintBackgroundOnOddRows(true);
		}
		drb.setSubtitle(getItemRecoverySubTitle(srDTO, user, resources));
		drb.setPageSizeAndOrientation(Page.Page_Legal_Landscape());
		try {
			Style header = new Style();
			header.setHorizontalAlign(HorizontalAlign.CENTER);
			header.setVerticalAlign(VerticalAlign.MIDDLE);
			Style detailStyle = new Style("detail");
			detailStyle.setHorizontalAlign(HorizontalAlign.CENTER);
			detailStyle.setVerticalAlign(VerticalAlign.MIDDLE);
			
			drb.addColumn(resources.getString("lf.ir.report.station"), "station", String.class.getName(), 75, detailStyle, header);
			drb.addColumn(resources.getString("lf.ir.report.company"), "company", String.class.getName(), 75, detailStyle, header);
			if (isLostReport) {
				drb.addColumn(resources.getString("lf.ir.report.total.lost.reported"), "itemsReported", Integer.class.getName(), 150, detailStyle, header);
			} else {
				drb.addColumn(resources.getString("lf.ir.report.total.found.reported"), "itemsReported", Integer.class.getName(), 150, detailStyle, header);
			}
			drb.addColumn(resources.getString("lf.ir.report.total.open"), "openItems", Integer.class.getName(), 100, detailStyle, header);
			drb.addColumn(resources.getString("lf.ir.report.matched.pending"), "matchedPendingAction", Integer.class.getName(), 150, detailStyle, header);
			
			if (!isLostReport) {
				drb.addColumn(resources.getString("lf.ir.report.to.be.salvaged"), "toBeSalvaged", Integer.class.getName(), 130, detailStyle, header);
			}
			drb.addColumn(resources.getString("lf.ir.report.closed"), "closed", Integer.class.getName(), 100, detailStyle, header);
			drb.addColumn(resources.getString("lf.ir.report.delivered"), "delivered", Integer.class.getName(), 85, detailStyle, header);
			drb.addColumn(resources.getString("lf.ir.report.picked.up"), "pickedUpByCustomer", Integer.class.getName(), 120, detailStyle, header);
			
			if (!isLostReport) {
				drb.addColumn(resources.getString("lf.ir.report.salvaged"), "salvaged", Integer.class.getName(), 100, detailStyle, header);
			}
			
			drb.addColumn(resources.getString("lf.ir.report.closed.other"), "closedOther", Integer.class.getName(), 100, detailStyle, header);
			
			if (isLostReport) {
				drb.addColumn(resources.getString("lf.ir.report.return.rate"), "returnRate", String.class.getName(), 125, detailStyle, header);
			} else {
				drb.addColumn(resources.getString("lf.ir.report.salvaged.rate"), "salvagedRate", String.class.getName(), 125, detailStyle, header);
			}
			drb.addColumn(resources.getString("lf.ir.report.closed.matched.by.nt"), "closedMatchedByNt", Integer.class.getName(), 135, detailStyle, header);
			
			drb.setIgnorePagination(true);
			drb.setUseFullPageWidth(true);

			DynamicReport report = drb.build();
			JRDataSource data = new JRBeanCollectionDataSource(lostFoundReportData);
			
			JasperPrint jp = DynamicJasperHelper.generateJasperPrint(report, new ClassicLayoutManager(), data);
			parameters.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
			parameters.put(JExcelApiExporterParameter.JASPER_PRINT, jp);
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
	
	private String getItemRecoverySubTitle(StatReportDTO srDto, Agent user, ResourceBundle resources) {
		StringBuilder sb = new StringBuilder();
		String runDate = DateUtils.formatDate(new Date(), user.getDateformat().getFormat(), user.getDefaultlocale(), null);
		sb.append(resources.getString("lf.ir.report.created.on") + runDate + " ");
		sb.append("by " + user.getUsername() + " for: " + srDto.getStarttime() + " - " + srDto.getEndtime() + " ");
		return sb.toString();
	}
}