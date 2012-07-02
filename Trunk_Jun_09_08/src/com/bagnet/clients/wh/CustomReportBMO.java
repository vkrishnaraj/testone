package com.bagnet.clients.wh;

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
import com.bagnet.nettracer.reporting.WHDailyStatusReport;
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
		case ReportingConstants.RPT_20_CUSTOM_202:
			creportdata = createWHDailyStatusReport(srDTO, ReportBMO.getCustomReport(89).getResource_key(), rootpath, request, user);
			break;
		default:
			break;

		}
		return creportdata;
	}
	
	@SuppressWarnings({ "rawtypes" })
	private String createWHDailyStatusReport(StatReportDTO srDTO, String resourceKey, String rootpath, HttpServletRequest request, Agent user) {
		srDTO.setDateFormat(user.getDateformat().getFormat());
		srDTO.setCompanyCode(user.getCompanycode_ID());
		String runDate = DateUtils.formatDate(new Date(), user.getDateformat().getFormat(), user.getDefaultlocale(), null);
		ResourceBundle resources = ResourceBundle.getBundle("com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(user.getCurrentlocale()));
		List reportData = new WHDailyStatusReport(resources, user).getData(srDTO);
		if (reportData == null) {
			return null;
		}
		
		String fileName = ReportingConstants.RPT_20_CUSTOM_202_NAME + "_" + (new SimpleDateFormat(ReportingConstants.DATETIME_FORMAT).format(TracerDateTime.getGMTDate())) + ReportingConstants.EXCEL_FILE_TYPE;
		String outputpath = rootpath + ReportingConstants.REPORT_TMP_PATH + fileName;
		JRGovernedFileVirtualizer virtualizer = new JRGovernedFileVirtualizer(100, rootpath + ReportingConstants.REPORT_TMP_PATH, 501);
		virtualizer.setReadOnly(false);
		
		FastReportBuilder drb = new FastReportBuilder();
		drb.setTitle(resources.getString("report.wh.daily.status.report.header") + " " + runDate);
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
			drb.addColumn(resources.getString("report.wh.daily.status.mbr"), "mbr", String.class.getName(), 50, detailStyle, header);
			drb.addColumn(resources.getString("report.wh.daily.status.type"), "type", String.class.getName(), 50, detailStyle, header);
			drb.addColumn(resources.getString("report.wh.daily.status.status"), "status", String.class.getName(), 50, detailStyle, header);
			drb.addColumn(resources.getString("report.wh.daily.status.name"), "name", String.class.getName(), 50, detailStyle, header);
			drb.addColumn(resources.getString("report.wh.daily.status.airline"), "airline", String.class.getName(), 50, detailStyle, header);
			drb.addColumn(resources.getString("report.wh.daily.status.flight"), "flight", Integer.class.getName(), 50, detailStyle, header);
			drb.addColumn(resources.getString("report.wh.daily.status.origin"), "origin", String.class.getName(), 50, detailStyle, header);
			drb.addColumn(resources.getString("report.wh.daily.status.destination"), "destination", String.class.getName(), 50, detailStyle, header);
			drb.addColumn(resources.getString("report.wh.daily.status.route"), "route", String.class.getName(), 50, detailStyle, header);
			drb.addColumn(resources.getString("report.wh.daily.status.open.station"), "openStation", String.class.getName(), 50, detailStyle, header);
			drb.addColumn(resources.getString("report.wh.daily.status.open.user"), "openUser", String.class.getName(), 50, detailStyle, header);
			drb.addColumn(resources.getString("report.wh.daily.status.open.date"), "openDate", String.class.getName(), 50, detailStyle, header);
			drb.addColumn(resources.getString("report.wh.daily.status.open.time"), "openTime", String.class.getName(), 50, detailStyle, header);
			drb.addColumn(resources.getString("report.wh.daily.status.close.station"), "closeStation", String.class.getName(), 50, detailStyle, header);
			drb.addColumn(resources.getString("report.wh.daily.status.close.user"), "closeUser", String.class.getName(), 50, detailStyle, header);
			drb.addColumn(resources.getString("report.wh.daily.status.close.date"), "closeDate", String.class.getName(), 50, detailStyle, header);
			drb.addColumn(resources.getString("report.wh.daily.status.close.time"), "closeTime", String.class.getName(), 50, detailStyle, header);
			drb.addColumn(resources.getString("report.wh.daily.status.total.time"), "total", String.class.getName(), 50, detailStyle, header);
			drb.addColumn(resources.getString("report.wh.daily.status.fault.station"), "faultStation", String.class.getName(), 50, detailStyle, header);
			drb.addColumn(resources.getString("report.wh.daily.status.fault.code"), "faultCode", Integer.class.getName(), 50, detailStyle, header);
			drb.addColumn(resources.getString("report.wh.daily.status.fault.desc"), "faultDesc", String.class.getName(), 50, detailStyle, header);

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
		sb.append(resources.getString("lf.ir.report.created.on") + " " + runDate + " ");
		sb.append("by " + user.getUsername() + " for: " + srDto.getStarttime() + " - " + srDto.getEndtime() + " ");
		return sb.toString();
	}
	
}