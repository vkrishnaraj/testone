package com.bagnet.clients.lfc;

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

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;

import com.bagnet.nettracer.other.JRGovernedFileVirtualizer;
import com.bagnet.nettracer.reporting.ReportingConstants;
import com.bagnet.nettracer.reporting.lf.LFDailyStatusReport;
import com.bagnet.nettracer.reporting.lf.LFDisbursementsReport;
import com.bagnet.nettracer.reporting.lf.LFItemizationReport;
import com.bagnet.nettracer.reporting.lf.LFSummaryReport;
import com.bagnet.nettracer.tracing.bmo.ReportBMO;
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
		case ReportingConstants.RPT_20_CUSTOM_89:
			creportdata = createDailyStatusReport(srDTO, ReportBMO.getCustomReport(89).getResource_key(), rootpath, request, user);
			break;
		case ReportingConstants.RPT_20_CUSTOM_90:
			creportdata = createItemizationReport(srDTO, ReportBMO.getCustomReport(90).getResource_key(), rootpath, request, user);
			break;
		case ReportingConstants.RPT_20_CUSTOM_91:
			creportdata = createSummaryReport(srDTO, ReportBMO.getCustomReport(91).getResource_key(), rootpath, request, user);
			break;
		case ReportingConstants.RPT_20_CUSTOM_92:
			creportdata = createDisbursementsReport(srDTO, ReportBMO.getCustomReport(92).getResource_key(), rootpath, request, user);
			break;
		default:
			break;

		}
		return creportdata;
	}
	
	@SuppressWarnings({ "rawtypes" })
	private String createDailyStatusReport(StatReportDTO srDTO, String resourceKey, String rootpath, HttpServletRequest request, Agent user) {
		srDTO.setDateFormat(user.getDateformat().getFormat());
		String runDate = DateUtils.formatDate(new Date(), user.getDateformat().getFormat(), user.getDefaultlocale(), null);
		ResourceBundle resources = ResourceBundle.getBundle("com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(user.getCurrentlocale()));
		List reportData = new LFDailyStatusReport(resources).getData(srDTO);
		if (reportData == null) {
			return null;
		}
		
		String fileName = ReportingConstants.LOST_FOUND_REPORT_NAME + "_" + (new SimpleDateFormat(ReportingConstants.DATETIME_FORMAT).format(TracerDateTime.getGMTDate())) + ReportingConstants.EXCEL_FILE_TYPE;
		String outputpath = rootpath + ReportingConstants.REPORT_TMP_PATH + fileName;
		JRGovernedFileVirtualizer virtualizer = new JRGovernedFileVirtualizer(100, rootpath + ReportingConstants.REPORT_TMP_PATH, 501);
		virtualizer.setReadOnly(false);
		
		FastReportBuilder drb = new FastReportBuilder();
		drb.setTitle(resources.getString("report.lf.daily.status.report.header") + " " + runDate);
		drb.setPrintBackgroundOnOddRows(true);
		drb.setPageSizeAndOrientation(Page.Page_Legal_Landscape());
		drb.setSubtitle(getSubTitle(srDTO, user, resources));
		
		
		Style header = new Style();
		header.setHorizontalAlign(HorizontalAlign.CENTER);
		header.setVerticalAlign(VerticalAlign.MIDDLE);
		Style detailStyle = new Style("detail");
		detailStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		detailStyle.setVerticalAlign(VerticalAlign.MIDDLE);
		
		try {
			drb.addColumn(resources.getString("report.lf.daily.status.date"), "receivedDate", String.class.getName(), 50, detailStyle, header);
			drb.addColumn(resources.getString("report.lf.daily.status.station"), "stationCode", String.class.getName(), 50, detailStyle, header);
			drb.addColumn(resources.getString("report.lf.daily.status.hvir"), "hvir", Integer.class.getName(), 50, detailStyle, header);
			drb.addColumn(resources.getString("report.lf.daily.status.lvir"), "lvir", Integer.class.getName(), 50, detailStyle, header);
			drb.addColumn(resources.getString("report.lf.daily.status.hvirwr"), "hvirwr", Integer.class.getName(), 50, detailStyle, header);
			drb.addColumn(resources.getString("report.lf.daily.status.lvirwr"), "lvirwr", Integer.class.getName(), 50, detailStyle, header);
			drb.addColumn(resources.getString("report.lf.daily.status.hvirwor"), "hvirwor", Integer.class.getName(), 50, detailStyle, header);
			drb.addColumn(resources.getString("report.lf.daily.status.lvirwor"), "lvirwor", Integer.class.getName(), 50, detailStyle, header);

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

	@SuppressWarnings("rawtypes")
	private String createItemizationReport(StatReportDTO srDTO, String resourceKey, String rootpath, HttpServletRequest request, Agent user) {
		srDTO.setDateFormat(user.getDateformat().getFormat());
		String runDate = DateUtils.formatDate(new Date(), user.getDateformat().getFormat(), user.getDefaultlocale(), null);
		ResourceBundle resources = ResourceBundle.getBundle("com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(user.getCurrentlocale()));
		List reportData = new LFItemizationReport(resources).getData(srDTO);
		if (reportData == null) {
			return null;
		}
		
		String fileName = ReportingConstants.LOST_FOUND_REPORT_NAME + "_" + (new SimpleDateFormat(ReportingConstants.DATETIME_FORMAT).format(TracerDateTime.getGMTDate())) + ReportingConstants.EXCEL_FILE_TYPE;
		String outputpath = rootpath + ReportingConstants.REPORT_TMP_PATH + fileName;
		JRGovernedFileVirtualizer virtualizer = new JRGovernedFileVirtualizer(100, rootpath + ReportingConstants.REPORT_TMP_PATH, 501);
		virtualizer.setReadOnly(false);
		
		FastReportBuilder drb = new FastReportBuilder();
		drb.setTitle(resources.getString("report.lf.itemization.report.header") + " " + runDate);
		drb.setPageSizeAndOrientation(Page.Page_Legal_Landscape());
		drb.setSubtitle(getSubTitle(srDTO, user, resources));
		
		
		Style header = new Style();
		header.setHorizontalAlign(HorizontalAlign.CENTER);
		header.setVerticalAlign(VerticalAlign.MIDDLE);
		Style detailStyle = new Style("detail");
		detailStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		detailStyle.setVerticalAlign(VerticalAlign.MIDDLE);
		
		try {
			drb.addColumn(resources.getString("report.lf.itemization.barcode"), "barcode", Long.class.getName(), 50, detailStyle, header);
			drb.addColumn(resources.getString("report.lf.itemization.date.recorded"), "dateRecorded", String.class.getName(), 50, detailStyle, header);
			drb.addColumn(resources.getString("report.lf.itemization.date.received"), "dateReceived", String.class.getName(), 50, detailStyle, header);
			drb.addColumn(resources.getString("report.lf.itemization.value"), "value", String.class.getName(), 50, detailStyle, header);
			drb.addColumn(resources.getString("report.lf.itemization.received.from"), "stationcode", String.class.getName(), 50, detailStyle, header);
			drb.addColumn(resources.getString("report.lf.itemization.category"), "category", String.class.getName(), 50, detailStyle, header);
			drb.addColumn(resources.getString("report.lf.itemization.sub.category"), "subCategory", String.class.getName(), 50, detailStyle, header);
			drb.addColumn(resources.getString("report.lf.itemization.status"), "status", String.class.getName(), 50, detailStyle, header);
			drb.addColumn(resources.getString("report.lf.itemization.disposition"), "disposition", String.class.getName(), 50, detailStyle, header);
			drb.addColumn(resources.getString("report.lf.itemization.item.title"), "title", String.class.getName(), 50, detailStyle, header);
			drb.addColumn(resources.getString("report.lf.itemization.tracking.number"), "trackingNumber", String.class.getName(), 50, detailStyle, header);
			drb.addColumn(resources.getString("report.lf.itemization.actual.return.time"), "dispReturnTime", String.class.getName(), 50, detailStyle, header);
			drb.addColumn(resources.getString("report.lf.itemization.returned.with.report.filed"), "returnedWithLostReport", String.class.getName(), 50, detailStyle, header);

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
	
	@SuppressWarnings("rawtypes")
	private String createSummaryReport(StatReportDTO srDTO, String resourceKey, String rootpath, HttpServletRequest request, Agent user) {
		srDTO.setDateFormat(user.getDateformat().getFormat());
		String runDate = DateUtils.formatDate(new Date(), user.getDateformat().getFormat(), user.getDefaultlocale(), null);
		ResourceBundle resources = ResourceBundle.getBundle("com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(user.getCurrentlocale()));
		List reportData = new LFSummaryReport(resources).getData(srDTO);
		if (reportData == null) {
			return null;
		}
		
		String fileName = ReportingConstants.LOST_FOUND_REPORT_NAME + "_" + (new SimpleDateFormat(ReportingConstants.DATETIME_FORMAT).format(TracerDateTime.getGMTDate())) + ReportingConstants.EXCEL_FILE_TYPE;
		String outputpath = rootpath + ReportingConstants.REPORT_TMP_PATH + fileName;
		JRGovernedFileVirtualizer virtualizer = new JRGovernedFileVirtualizer(100, rootpath + ReportingConstants.REPORT_TMP_PATH, 501);
		virtualizer.setReadOnly(false);
		
		FastReportBuilder drb = new FastReportBuilder();
		drb.setTitle(resources.getString("report.lf.summary.report.header") + " " + runDate);
		drb.setPrintBackgroundOnOddRows(true);
		drb.setSubtitle(getSubTitle(srDTO, user, resources));
		
		
		Style header = new Style();
		header.setHorizontalAlign(HorizontalAlign.CENTER);
		header.setVerticalAlign(VerticalAlign.MIDDLE);
		Style detailStyle = new Style("detail");
		detailStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		detailStyle.setVerticalAlign(VerticalAlign.MIDDLE);
		
		try {
			drb.addColumn(resources.getString("report.lf.summary.date"), "date", String.class.getName(), 50, detailStyle, header);
			drb.addColumn(resources.getString("report.lf.summary.nlrr"), "nlrr", Integer.class.getName(), 50, detailStyle, header);
			drb.addColumn(resources.getString("report.lf.summary.nfie"), "nfie", Integer.class.getName(), 50, detailStyle, header);

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
	
	@SuppressWarnings("rawtypes")
	private String createDisbursementsReport(StatReportDTO srDTO, String resourceKey, String rootpath, HttpServletRequest request, Agent user) {
		srDTO.setDateFormat(user.getDateformat().getFormat());
		String runDate = DateUtils.formatDate(new Date(), user.getDateformat().getFormat(), user.getDefaultlocale(), null);
		ResourceBundle resources = ResourceBundle.getBundle("com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(user.getCurrentlocale()));
		List reportData = new LFDisbursementsReport(resources).getData(srDTO);
		if (reportData == null) {
			return null;
		}
		
		String fileName = ReportingConstants.LOST_FOUND_REPORT_NAME + "_" + (new SimpleDateFormat(ReportingConstants.DATETIME_FORMAT).format(TracerDateTime.getGMTDate())) + ReportingConstants.EXCEL_FILE_TYPE;
		String outputpath = rootpath + ReportingConstants.REPORT_TMP_PATH + fileName;
		JRGovernedFileVirtualizer virtualizer = new JRGovernedFileVirtualizer(100, rootpath + ReportingConstants.REPORT_TMP_PATH, 501);
		virtualizer.setReadOnly(false);
		
		FastReportBuilder drb = new FastReportBuilder();
		drb.setTitle(resources.getString("report.lf.disbursements.report.header") + " " + runDate);
		drb.setPrintBackgroundOnOddRows(true);
		drb.setSubtitle(getSubTitle(srDTO, user, resources));
		
		
		Style header = new Style();
		header.setHorizontalAlign(HorizontalAlign.CENTER);
		header.setVerticalAlign(VerticalAlign.MIDDLE);
		Style detailStyle = new Style("detail");
		detailStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		detailStyle.setVerticalAlign(VerticalAlign.MIDDLE);
		
		try {
			drb.addColumn(resources.getString("report.lf.disbursements.date"), "date", String.class.getName(), 50, detailStyle, header);
			drb.addColumn(resources.getString("report.lf.disbursements.check.amount"), "checkAmount", Double.class.getName(), 50, detailStyle, header);
			drb.addColumn(resources.getString("report.lf.disbursements.check.number"), "checkNumber", Integer.class.getName(), 50, detailStyle, header);
			drb.addColumn(resources.getString("report.lf.disbursements.lost.id"), "lostId", Long.class.getName(), 50, detailStyle, header);
			drb.addColumn(resources.getString("report.lf.disbursements.barcode"), "barcode", Long.class.getName(), 50, detailStyle, header);
			drb.addColumn(resources.getString("report.lf.disbursements.tracking.number"), "trackingNumber", String.class.getName(), 50, detailStyle, header);

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
	private void exportJasperReport(List reportData, DynamicReport report, String outputpath) {
		JRDataSource data = new JRBeanCollectionDataSource(reportData);
		
		try {
			JasperPrint jp = DynamicJasperHelper.generateJasperPrint(report, new ClassicLayoutManager(), data);
			Map parameters = new HashMap();
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
			parameters.put(JExcelApiExporterParameter.IS_DETECT_CELL_TYPE, true);
			
			JExcelApiExporter exporter = new JExcelApiExporter();
			exporter.setParameters(parameters);
			exporter.exportReport();
		} catch (JRException e) {
			logger.error(e, e);
		}
	}
	
	private String getSubTitle(StatReportDTO srDto, Agent user, ResourceBundle resources) {
		StringBuilder sb = new StringBuilder();
		String runDate = DateUtils.formatDate(new Date(), user.getDateformat().getFormat(), user.getDefaultlocale(), null);
		sb.append(resources.getString("lf.ir.report.created.on") + runDate + " ");
		sb.append("by " + user.getUsername() + " for: " + srDto.getStarttime() + " - " + srDto.getEndtime() + " ");
		return sb.toString();
	}
	
}