package com.bagnet.nettracer.tracing.bmo;

import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JExcelApiExporterParameter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRXmlExporter;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.AutoText;
import ar.com.fdvs.dj.domain.CustomExpression;
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.DJGroupLabel;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.ExpressionHelper;
import ar.com.fdvs.dj.domain.StringExpression;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.builders.GroupBuilder;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.GroupLayout;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.LabelPosition;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.other.JRGovernedFileVirtualizer;
import com.bagnet.nettracer.other.JRMaxFilesException;
import com.bagnet.nettracer.reporting.ReportingConstants;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.dto.StatReportDTO;
import com.bagnet.nettracer.tracing.dto.StatReport_OHD_DTO;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerProperties;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

//helper class to ReportBMO

public class OHDReportBMO {
	private static Logger logger = Logger.getLogger(OHDReportBMO.class);
	
	public static String getReportFileDj(JRDataSource ds, Map parameters, String reportname, String rootpath, int outputtype, HttpServletRequest request, ReportBMO rbmo) throws Exception {
		/** look for compiled reports, if can't find it, compile xml report * */

		// added virtualizer to reduce memory
		String outfile = reportname + "_" + (new SimpleDateFormat("MMddyyyyhhmmss").format(TracerDateTime.getGMTDate()));
		
		JRGovernedFileVirtualizer virtualizer = new JRGovernedFileVirtualizer(100, rootpath + "/reports/tmp", 501);
		virtualizer.setReadOnly(false);
		
		parameters.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
		
		if (outputtype == TracingConstants.REPORT_OUTPUT_XLS) {
			parameters.put(JRParameter.IS_IGNORE_PAGINATION, true);
		} else {
			parameters.put(JRParameter.IS_IGNORE_PAGINATION, false);
		}
		
		DynamicReport dr = null;
		JasperPrint jasperPrint;
		try {
			
			if (outputtype == TracingConstants.REPORT_OUTPUT_XLS) {
				dr = buildExcelReport(parameters);
			} else {
				dr = buildReport(parameters);
			}
			
			JasperReport jasperReport = DynamicJasperHelper.generateJasperReport(dr, new ClassicLayoutManager(), parameters);
			if (ds != null) {
				jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);
			} else {
				jasperPrint = JasperFillManager.fillReport(jasperReport, parameters);
			}
			
			virtualizer.setReadOnly(true);
	
			if (outputtype == TracingConstants.REPORT_OUTPUT_HTML)
				outfile += ".html";
			else if (outputtype == TracingConstants.REPORT_OUTPUT_PDF)
				// In the event the file type is undeclared, we will use the default - PDF if available, HTML otherwise.
				if (!TracerProperties.isTrue(TracerProperties.SUPPRESSION_PRINTING_NONHTML)) {
					outfile += ".pdf";
				} else {
					outfile += ".html";
					outputtype = TracingConstants.REPORT_OUTPUT_HTML;
					request.setAttribute("outputtype", Integer.toString(TracingConstants.REPORT_OUTPUT_HTML));
				}
				
			else if (outputtype == TracingConstants.REPORT_OUTPUT_XLS)
				outfile += ".xls";
			else if (outputtype == TracingConstants.REPORT_OUTPUT_CSV)
				outfile += ".csv";
			else if (outputtype == TracingConstants.REPORT_OUTPUT_XML)
				outfile += ".xml";
			else if (outputtype == TracingConstants.REPORT_OUTPUT_UNDECLARED) {
				// In the event the file type is undeclared, we will use the default - PDF if available, HTML otherwise.
				if (!TracerProperties.isTrue(TracerProperties.SUPPRESSION_PRINTING_NONHTML)) {
					outfile += ".pdf";
					outputtype = TracingConstants.REPORT_OUTPUT_PDF;
				} else {
					outfile += ".html";
					outputtype = TracingConstants.REPORT_OUTPUT_HTML;
					request.setAttribute("outputtype", Integer.toString(TracingConstants.REPORT_OUTPUT_HTML));
				}
			}
				
	
			String outputpath = rootpath + ReportingConstants.REPORT_TMP_PATH + outfile;
			JRExporter exporter = null;
	
			if (outputtype == TracingConstants.REPORT_OUTPUT_PDF)
				JasperExportManager.exportReportToPdfFile(jasperPrint, outputpath);
			else if (outputtype == TracingConstants.REPORT_OUTPUT_HTML) {
				exporter = new JRHtmlExporter();
	
				Map imagesMap = new HashMap();
				
				exporter.setParameter(JRHtmlExporterParameter.BETWEEN_PAGES_HTML, "<div style=\"page-break-after: always\" border=\"0\">&nbsp;</div>");
				request.getSession().setAttribute("IMAGES_MAP", imagesMap);
				exporter.setParameter(JRHtmlExporterParameter.IMAGES_MAP, imagesMap);
				exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "image?image=");
				
				exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, false);
	
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputpath);			
				
				exporter.exportReport();
			}
	
			//: switch to JExcelApiExporter
			else if (outputtype == TracingConstants.REPORT_OUTPUT_XLS) {
				JExcelApiExporter alternativeExporter = new JExcelApiExporter();
				Map<JRExporterParameter, Object> excelParameters = new HashMap<JRExporterParameter, Object>();
				
				excelParameters.put(JExcelApiExporterParameter.JASPER_PRINT,  jasperPrint);
				excelParameters.put(JExcelApiExporterParameter.OUTPUT_FILE_NAME, outputpath);
				excelParameters.put(JExcelApiExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
				excelParameters.put(JExcelApiExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
				excelParameters.put(JExcelApiExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
				excelParameters.put(JExcelApiExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
				excelParameters.put(JExcelApiExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
				//excelParameters.put(JExcelApiExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
				excelParameters.put(JExcelApiExporterParameter.IS_FONT_SIZE_FIX_ENABLED, Boolean.TRUE); 
				excelParameters.put(JExcelApiExporterParameter.IS_COLLAPSE_ROW_SPAN, Boolean.TRUE);
				
				alternativeExporter.setParameters(excelParameters);
				alternativeExporter.exportReport();
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
		} catch (JRMaxFilesException e) {
			logger.error("JRMaxFilesException encountered...");
			logger.error("Report Name: " + reportname);
			Agent user = (Agent) request.getSession().getAttribute("user");
			logger.error("User ID: " + user.getAgent_ID() + " User Name: " + user.getUsername());
			
			for (Object obj: parameters.keySet()) {
				logger.error("Parameter: " + obj.toString() + "  Value: " + parameters.get(obj).toString());
			}
			outfile = null;
			if (rbmo != null) {
				rbmo.setErrormsg("error.maxpages");
			}
		} catch (JRException jre) {
			logger.error("JRException encountered...");
			logger.error("Report Name: " + reportname);
			logger.error(jre.getMessage());
		}
		
		virtualizer.cleanup();
		
		return outfile;
	}
	
	private static DynamicReport buildReport(Map parameters) throws Exception {
		
		//reportStyle : first digit represents GroupBy; second digit represents Report Option
		//     00-Summary; 10-Detail
		int reportStyle = 10;
		if (parameters.get("reportStyle") != null) {
			String myReportStyle = (String) parameters.get("reportStyle");
			reportStyle = Integer.parseInt(myReportStyle);
		}
		
		Locale reportLocale = new Locale("en", "US");
		if (parameters.get("reportLocale") != null) {
			reportLocale = (Locale) parameters.get("reportLocale");
		}
		
		ResourceBundle resourceBundle = null;
		if (parameters.get("REPORT_RESOURCE_BUNDLE") != null) {
			resourceBundle = (ResourceBundle) parameters.get("REPORT_RESOURCE_BUNDLE");
		}
		
		//getting report header title from resource bundle
		String reportHeadingSortByHoldingStation = "Holding Station";
		String reportHeadingSortByFoundStation = "Found Station";
		
		String reportHeadingLastNameFirstName = "Last Name, First Name";
		String reportHeadingIncidentNumber = "Report ID";
		String reportHeadingDate = "Date";
		String reportHeadingTime = "Time";
		String reportHeadingItinerary = "Itinerary";
		String reportHeadingFinal = "Destination";
		String reportHeadingStatus = "Status";
		String reportHeadingHoldingStation = "Holding Station";
		String reportHeadingFoundStation = "Found Station";
		
		String reportHeadingFaultStation = "Fault Station";
		String reportHeadingLossCode = "Loss Code";
		
		String myLastNameFirstNameFromResource = resourceBundle.getString("report.ohd.heading.last.name.first.name");
		if (!( myLastNameFirstNameFromResource == null || myLastNameFirstNameFromResource.equalsIgnoreCase("") )) {
			reportHeadingLastNameFirstName = myLastNameFirstNameFromResource;
		}

		String myIncidentNumber = resourceBundle.getString("report.ohd.heading.incident.number");
		if (!( myIncidentNumber == null || myIncidentNumber.equalsIgnoreCase("") )) {
			reportHeadingIncidentNumber = myIncidentNumber;
		}
		
		String myDate = resourceBundle.getString("report.ohd.heading.date");
		if (!( myDate == null || myDate.equalsIgnoreCase("") )) {
			reportHeadingDate = myDate;
		}
		
		String myTime = resourceBundle.getString("report.ohd.heading.time");
		if (!( myTime == null || myTime.equalsIgnoreCase("") )) {
			reportHeadingTime = myTime;
		}
		
		String myItinerary = resourceBundle.getString("report.ohd.heading.itinerary");
		if (!( myItinerary == null || myItinerary.equalsIgnoreCase("") )) {
			reportHeadingItinerary = myItinerary;
		}
		
		String myFinal = resourceBundle.getString("report.ohd.heading.final");
		if (!( myFinal == null || myFinal.equalsIgnoreCase("") )) {
			reportHeadingFinal = myFinal;
		}
		
		String myStatus = resourceBundle.getString("report.ohd.heading.status");
		if (!( myStatus == null || myStatus.equalsIgnoreCase("") )) {
			reportHeadingStatus = myStatus;
		}
		
		String myHoldingStation = resourceBundle.getString("report.ohd.heading.holding.station");
		if (!( myHoldingStation == null || myHoldingStation.equalsIgnoreCase("") )) {
			reportHeadingHoldingStation = myHoldingStation;
		}
		
		String myFoundStation = resourceBundle.getString("report.ohd.heading.found.station");
		if (!( myFoundStation == null || myFoundStation.equalsIgnoreCase("") )) {
			reportHeadingFoundStation = myFoundStation;
		}
		
		String myFaultStation = resourceBundle.getString("report.ohd.heading.fault.station");
		if (!( myFaultStation == null || myFaultStation.equalsIgnoreCase("") )) {
			reportHeadingFaultStation = myFaultStation;
		}
		
		
		Style detailStyle = new Style("detail");
		detailStyle.setVerticalAlign(VerticalAlign.TOP);
		detailStyle.setFont(new Font(11, Font._FONT_ARIAL, false));
		

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
		
		Style subtitleStyle = new Style("subtitleStyle");
		subtitleStyle.setFont(new Font(12, Font._FONT_TIMES_NEW_ROMAN, false));
		subtitleStyle.setHorizontalAlign(HorizontalAlign.RIGHT);
		
		Style oddRowStyle = new Style();
		oddRowStyle.setBorder(Border.NO_BORDER);
		oddRowStyle.setBackgroundColor(Color.LIGHT_GRAY);
		oddRowStyle.setTransparency(Transparency.OPAQUE);

		DynamicReportBuilder drb = new DynamicReportBuilder();
		drb.setPageSizeAndOrientation(Page.Page_A4_Landscape());
		
		//get the general information of the report, such as Report Title
		String myReportTitle = "On-hand Baggage Report";
		if (parameters.get("title") != null) {
			myReportTitle = (String) parameters.get("title");
		}
		
		String mySubtitle = "This report was generated on " + new Date();
		
		if (parameters.get("sdate") != null && parameters.get("edate") != null) {
			mySubtitle += "   Date: from " + parameters.get("sdate") + " to " + parameters.get("edate");
		}
		if (parameters.get("ohd_subtitle_status") != null) {
			myStatus = (String) parameters.get("ohd_subtitle_status");
			if (myStatus.equals("")) {
				mySubtitle += "   Status: All";
			} else {
				mySubtitle += "   Status: " + parameters.get("ohd_subtitle_status");
			}
		} else {
			mySubtitle += "   Status: All";
		}
		if (parameters.get("agent_username") != null) {
			mySubtitle += "   Agent: " + parameters.get("agent_username");
		}		
		
		Integer margin = new Integer(20);
		drb
			.setTitleStyle(titleStyle)
			.setTitle(myReportTitle)
			.setSubtitleStyle(subtitleStyle)
			.setSubtitle(mySubtitle)
			.setDetailHeight(new Integer(15)).setLeftMargin(margin)
			.setRightMargin(margin).setTopMargin(margin).setBottomMargin(margin)
			.setPrintBackgroundOnOddRows(true)
			.setGrandTotalLegend("Grand Total :   ")
			.setGrandTotalLegendStyle(headerVariables)
			.setOddRowBackgroundStyle(oddRowStyle);
		
		AbstractColumn columnSortByFoundStation = ColumnBuilder.getNew()
				.setColumnProperty("stationcode", String.class.getName())
				.setTitle(reportHeadingFoundStation).setWidth(new Integer(30)).setStyle(
				detailStyle).setHeaderStyle(headerStyle).build();
		
		AbstractColumn columnLastNameFirstName = ColumnBuilder.getNew()
				.setColumnProperty("customer_name", String.class.getName()).setTitle(
				reportHeadingLastNameFirstName).setWidth(new Integer(80))
				.setStyle(detailStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnOnhandNumber = ColumnBuilder.getNew()
				.setColumnProperty("OHD_ID", String.class.getName()).setTitle(
				reportHeadingIncidentNumber).setWidth(new Integer(84)).setStyle(
				detailStyle).setHeaderStyle(headerStyle).build();
		
		AbstractColumn columnDate = ColumnBuilder.getNew()
				.setColumnProperty("rfounddate", String.class.getName()).setTitle(
				reportHeadingDate).setWidth(new Integer(40)).setStyle(detailStyle)
				.setHeaderStyle(headerStyle).build();
		
		AbstractColumn columnTime = ColumnBuilder.getNew()
				.setColumnProperty("rfoundtime", String.class.getName()).setTitle(
				reportHeadingTime).setWidth(new Integer(30)).setStyle(detailStyle)
				.setHeaderStyle(headerStyle).build();	
		
		AbstractColumn columnItinerary = ColumnBuilder.getNew()
				.setColumnProperty("itinerary", String.class.getName()).setTitle(
				reportHeadingItinerary).setWidth(new Integer(40)).setStyle(detailStyle)
				.setHeaderStyle(headerStyle).build();
		
		AbstractColumn columnFinal = ColumnBuilder.getNew()
				.setColumnProperty("final_destination", String.class.getName())
				.setTitle(reportHeadingFinal).setWidth(new Integer(40)).setStyle(
				detailStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnStatus = ColumnBuilder.getNew()
				.setColumnProperty("statusdesc", String.class.getName()).setTitle(
				reportHeadingStatus).setWidth(new Integer(30)).setStyle(detailStyle)
				.setHeaderStyle(headerStyle).build();
		
		AbstractColumn columnHoldingStationCode = ColumnBuilder.getNew()
				.setColumnProperty("holdingstation", String.class.getName())
				.setTitle(reportHeadingHoldingStation).setWidth(new Integer(30)).setStyle(
				detailStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnFaultStation = ColumnBuilder.getNew()
				.setColumnProperty("faultstationcode", String.class.getName())
				.setTitle(reportHeadingFaultStation).setWidth(new Integer(30)).setStyle(
				detailStyle).setHeaderStyle(headerStyle).build();
		
		AbstractColumn columnLossCode = ColumnBuilder.getNew()
				.setColumnProperty("loss_code", Integer.class.getName())
				.setTitle(reportHeadingLossCode).setWidth(new Integer(30)).setStyle(
				detailStyle).setHeaderStyle(headerStyle).build();

		// grand total count
		drb.addGlobalFooterVariable(columnOnhandNumber, DJCalculation.COUNT, headerVariables);
		drb.setGlobalFooterVariableHeight(new Integer(45));
		
		
		Map<String, AbstractColumn> reportColumns = new HashMap<String, AbstractColumn>();

		reportColumns.put("columnSortByFoundStation", columnSortByFoundStation);
		
		reportColumns.put("columnLastNameFirstName", columnLastNameFirstName);
		reportColumns.put("columnOnhandNumber", columnOnhandNumber);
		reportColumns.put("columnDate", columnDate);
		reportColumns.put("columnTime", columnTime);
		reportColumns.put("columnItinerary", columnItinerary);
		reportColumns.put("columnFinal", columnFinal);
		reportColumns.put("columnStatus", columnStatus);
		reportColumns.put("columnHoldingStationCode", columnHoldingStationCode);
		reportColumns.put("columnFaultStation", columnFaultStation);
		reportColumns.put("columnLossCode", columnLossCode);

		drb = reportStyleSelector(drb, reportColumns, reportStyle);

		//drb.addAutoText(AutoText.AUTOTEXT_PAGE_X_OF_Y, AutoText.POSITION_FOOTER, AutoText.ALIGMENT_CENTER);
		drb.setUseFullPageWidth(true);
		//drb.addAutoText(AutoText.AUTOTEXT_PAGE_X_OF_Y, AutoText.POSITION_FOOTER, AutoText.ALIGMENT_CENTER);
		//Style atStyle = new StyleBuilder(true).setFont(Font.COMIC_SANS_SMALL).setTextColor(Color.red).build();
		Style atStyle2 = new StyleBuilder(true).setFont(new Font(9, Font._FONT_TIMES_NEW_ROMAN, false, true, false)).setTextColor(Color.DARK_GRAY).build();

		//drb.addAutoText(AutoText.AUTOTEXT_PAGE_X_OF_Y, AutoText.POSITION_FOOTER, AutoText.ALIGNMENT_RIGHT,60,30,atStyle2);
		
		DynamicReport dr = drb.build();
		return dr;
	}
	
	private static DynamicReport buildExcelReport(Map parameters) throws Exception {
		
		int reportStyle = 11;
		if (parameters.get("reportStyle") != null) {
			String myReportStyle = (String) parameters.get("reportStyle");
			reportStyle = Integer.parseInt(myReportStyle);
		}
		
		Locale reportLocale = new Locale("en", "US");
		if (parameters.get("reportLocale") != null) {
			reportLocale = (Locale) parameters.get("reportLocale");
		}
		
		ResourceBundle resourceBundle = null;
		if (parameters.get("REPORT_RESOURCE_BUNDLE") != null) {
			resourceBundle = (ResourceBundle) parameters.get("REPORT_RESOURCE_BUNDLE");
		}

		/**
		 * Creates the DynamicReportBuilder and sets the basic options for
		 * the report
		 */
		FastReportBuilder drb = new FastReportBuilder();
		
		//getting report header title from reource bundle
		String reportHeadingSortByHoldingStation = "Holding Station";
		String reportHeadingSortByFoundStation = "Found Station";
		
		String reportHeadingLastNameFirstName = "Last Name, First Name";
		String reportHeadingIncidentNumber = "Report ID";
		String reportHeadingDate = "Date";
		String reportHeadingTime = "Time";
		String reportHeadingItinerary = "Itinerary";
		String reportHeadingFinal = "Destination";
		String reportHeadingStatus = "Status";
		String reportHeadingHoldingStation = "Holding Station";
		String reportHeadingLossCode = "Loss Code";
		String reportHeadingFoundStation = "Found Station";
		
		String reportHeadingFaultStation = "Fault Station";
		
		String myLastNameFirstNameFromResource = resourceBundle.getString("report.ohd.heading.last.name.first.name");
		if (!( myLastNameFirstNameFromResource == null || myLastNameFirstNameFromResource.equalsIgnoreCase("") )) {
			reportHeadingLastNameFirstName = myLastNameFirstNameFromResource;
		}

		String myIncidentNumber = resourceBundle.getString("report.ohd.heading.incident.number");
		if (!( myIncidentNumber == null || myIncidentNumber.equalsIgnoreCase("") )) {
			reportHeadingIncidentNumber = myIncidentNumber;
		}
		
		String myDate = resourceBundle.getString("report.ohd.heading.date");
		if (!( myDate == null || myDate.equalsIgnoreCase("") )) {
			reportHeadingDate = myDate;
		}
		
		String myTime = resourceBundle.getString("report.ohd.heading.time");
		if (!( myTime == null || myTime.equalsIgnoreCase("") )) {
			reportHeadingTime = myTime;
		}
		
		String myItinerary = resourceBundle.getString("report.ohd.heading.itinerary");
		if (!( myItinerary == null || myItinerary.equalsIgnoreCase("") )) {
			reportHeadingItinerary = myItinerary;
		}
		
		String myFinal = resourceBundle.getString("report.ohd.heading.final");
		if (!( myFinal == null || myFinal.equalsIgnoreCase("") )) {
			reportHeadingFinal = myFinal;
		}
		
		String myStatus = resourceBundle.getString("report.ohd.heading.status");
		if (!( myStatus == null || myStatus.equalsIgnoreCase("") )) {
			reportHeadingStatus = myStatus;
		}
		
		String myHoldingStation = resourceBundle.getString("report.ohd.heading.holding.station");
		if (!( myHoldingStation == null || myHoldingStation.equalsIgnoreCase("") )) {
			reportHeadingHoldingStation = myHoldingStation;
		}
		
		String myFoundStation = resourceBundle.getString("report.ohd.heading.found.station");
		if (!( myFoundStation == null || myFoundStation.equalsIgnoreCase("") )) {
			reportHeadingFoundStation = myFoundStation;
		}
		
		String myFaultStation = resourceBundle.getString("report.ohd.heading.fault.station");
		if (!( myFaultStation == null || myFaultStation.equalsIgnoreCase("") )) {
			reportHeadingFaultStation = myFaultStation;
		}
		
		//sort out the report styles
		//     00-Summary; 10-Detail
        switch (reportStyle) {
            case 00:  
            	drb = drb.addColumn(reportHeadingFoundStation, "stationcode", String.class.getName(), 24);
        		drb = drb.addColumn(reportHeadingLastNameFirstName,"customer_name",String.class.getName(),54);
        		drb = drb.addColumn(reportHeadingIncidentNumber,"OHD_ID",String.class.getName(),40);
        		drb = drb.addColumn(reportHeadingDate,"rfounddate",String.class.getName(),26);
        		drb = drb.addColumn(reportHeadingTime,"rfoundtime",String.class.getName(),20);
        		drb = drb.addColumn(reportHeadingItinerary,"itinerary",String.class.getName(),30);
        		drb = drb.addColumn(reportHeadingFinal,"final_destination",String.class.getName(),30);
        		drb = drb.addColumn(reportHeadingStatus,"statusdesc",String.class.getName(),30);
        		drb = drb.addColumn(reportHeadingHoldingStation,"holdingstation",String.class.getName(),24);
        		drb = drb.addColumn(reportHeadingFaultStation,"faultstationcode",String.class.getName(),23);
        		drb = drb.addColumn(reportHeadingLossCode,"loss_code",Integer.class.getName(),20);
        
    			break;
            case 10: 
            	drb = drb.addColumn(reportHeadingFoundStation, "stationcode", String.class.getName(), 24);
        		drb = drb.addColumn(reportHeadingLastNameFirstName,"customer_name",String.class.getName(),54);
        		drb = drb.addColumn(reportHeadingIncidentNumber,"OHD_ID",String.class.getName(),40);
        		drb = drb.addColumn(reportHeadingDate,"rfounddate",String.class.getName(),26);
        		drb = drb.addColumn(reportHeadingTime,"rfoundtime",String.class.getName(),20);
        		drb = drb.addColumn(reportHeadingItinerary,"itinerary",String.class.getName(),30);
        		drb = drb.addColumn(reportHeadingFinal,"final_destination",String.class.getName(),30);
        		drb = drb.addColumn(reportHeadingStatus,"statusdesc",String.class.getName(),30);
        		drb = drb.addColumn(reportHeadingHoldingStation,"holdingstation",String.class.getName(),24);
        		drb = drb.addColumn(reportHeadingFaultStation,"faultstationcode",String.class.getName(),23);
        		drb = drb.addColumn(reportHeadingLossCode,"loss_code",Integer.class.getName(),20);
        
    			break;
            default: 	    			
            	drb = drb.addColumn(reportHeadingFoundStation, "stationcode", String.class.getName(), 24);
	    		drb = drb.addColumn(reportHeadingLastNameFirstName,"customer_name",String.class.getName(),54);
	    		drb = drb.addColumn(reportHeadingIncidentNumber,"OHD_ID",String.class.getName(),40);
	    		drb = drb.addColumn(reportHeadingDate,"rfounddate",String.class.getName(),26);
	    		drb = drb.addColumn(reportHeadingTime,"rfoundtime",String.class.getName(),20);
	    		drb = drb.addColumn(reportHeadingItinerary,"itinerary",String.class.getName(),30);
	    		drb = drb.addColumn(reportHeadingFinal,"final_destination",String.class.getName(),30);
	    		drb = drb.addColumn(reportHeadingStatus,"statusdesc",String.class.getName(),30);
	    		drb = drb.addColumn(reportHeadingHoldingStation,"holdingstation",String.class.getName(),24);
	    		drb = drb.addColumn(reportHeadingFaultStation,"faultstationcode",String.class.getName(),23);
	    		drb = drb.addColumn(reportHeadingLossCode,"loss_code",Integer.class.getName(),20);
	    		
    			break;
        }
        
		//get the general information of the report, such as Report Title
		String myReportTitle = "On-hand Baggage Report";
		if (parameters.get("title") != null) {
			myReportTitle = (String) parameters.get("title");
		}
		
		String mySubtitle = "This report was generated on " + new Date();
		if (parameters.get("sdate") != null && parameters.get("edate") != null) {
			mySubtitle += "   Date: from " + parameters.get("sdate") + " to " + parameters.get("edate");
		}
		if (parameters.get("ohd_subtitle_status") != null) {
			myStatus = (String) parameters.get("ohd_subtitle_status");
			if (myStatus.equals("")) {
				mySubtitle += "   Status: All";
			} else {
				mySubtitle += "   Status: " + parameters.get("ohd_subtitle_status");
			}
		}
		
        drb.setReportLocale(reportLocale);
		
		drb.setSubtitle(mySubtitle);
		
		Page page = new Page();
		drb.setPageSizeAndOrientation(page.Page_A4_Landscape());
		//drb.setPrintBackgroundOnOddRows(true);
		drb.setPrintBackgroundOnOddRows(false);
		drb.setIgnorePagination(true);
		DynamicReport dr = drb.setTitle(myReportTitle).setUseFullPageWidth(true).build();

		return dr;
	}
	
	private static DynamicReportBuilder reportStyleSelector(DynamicReportBuilder drb, 
			Map<String, AbstractColumn> reportColumns, int reportStyle) throws Exception {
		
		if (reportColumns != null) {
			
			Style groupVariablesLegend = new Style("groupVariablesLegend");
			groupVariablesLegend.setHorizontalAlign(HorizontalAlign.RIGHT);
			groupVariablesLegend.setVerticalAlign(VerticalAlign.BOTTOM);
			
			Style groupVariables = new Style("groupVariables");
			groupVariables.setHorizontalAlign(HorizontalAlign.LEFT);
			groupVariables.setVerticalAlign(VerticalAlign.BOTTOM);
			
			//a style switch
			AbstractColumn columnSortByFoundStation = reportColumns.get("columnSortByFoundStation");
			
			AbstractColumn columnLastNameFirstName = reportColumns.get("columnLastNameFirstName");
			AbstractColumn columnOnhandNumber = reportColumns.get("columnOnhandNumber");
			AbstractColumn columnDate = reportColumns.get("columnDate");
			AbstractColumn columnTime = reportColumns.get("columnTime");
			AbstractColumn columnItinerary = reportColumns.get("columnItinerary");
			AbstractColumn columnFinal = reportColumns.get("columnFinal");
			AbstractColumn columnStatus = reportColumns.get("columnStatus");
			AbstractColumn columnHoldingStationCode = reportColumns.get("columnHoldingStationCode");
			AbstractColumn columnFaultStation = reportColumns.get("columnFaultStation");
			AbstractColumn columnLossCode = reportColumns.get("columnLossCode");
			
			DJGroup g1 = null;
			GroupBuilder gb1 = null;
			AbstractColumn groupByColumn = columnSortByFoundStation;  
			
			//test 1: this way is not working quite right
			//invisible field for subtotal
			CustomExpression mySubTotalExpression = getSubTotalCustomExpression();
			
			drb.addColumn(groupByColumn);

			drb.addColumn(columnLastNameFirstName);
			drb.addColumn(columnOnhandNumber);
			drb.addColumn(columnDate);
			drb.addColumn(columnTime);
			drb.addColumn(columnItinerary);
			drb.addColumn(columnFinal);
			drb.addColumn(columnStatus);
			drb.addColumn(columnHoldingStationCode);
			drb.addColumn(columnFaultStation);
			drb.addColumn(columnLossCode);
			
	        gb1 = new GroupBuilder();
	        g1 = gb1.setCriteriaColumn((PropertyColumn) groupByColumn)
	        .addVariable("myGroupStationCode", groupByColumn, DJCalculation.FIRST)
	        .addFooterVariable(columnLastNameFirstName, mySubTotalExpression,groupVariablesLegend)
	        .addFooterVariable(columnOnhandNumber, DJCalculation.COUNT, groupVariables)   //station-totals
			.setGroupLayout(GroupLayout.DEFAULT)
			.setFooterVariablesHeight(new Integer(20))
			.setFooterHeight(new Integer(50))
			.setHeaderVariablesHeight(new Integer(35))
			.build();

	        drb.addField("stationcode", String.class.getName());
			drb.addGroup(g1);
			
			//deal with summary and detail options
			if (reportStyle == 00) {
				drb.setShowDetailBand(false);
			}
		
		}
		
		return drb;
	}
	
	//
	private static CustomExpression getSubTotalCustomExpression() {
		return new CustomExpression() {

			public Object evaluate(Map fields, Map variables, Map parameters) {
				String stationCode = "";
				stationCode += (String) variables.get("myGroupStationCode"); 
				String subTotal = stationCode + " Station Total :   ";
				return subTotal;
			}

			public String getClassName() {
				return String.class.getName();
			}

		};
	}

}
