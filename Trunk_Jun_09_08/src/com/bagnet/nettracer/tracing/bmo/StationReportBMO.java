package com.bagnet.nettracer.tracing.bmo;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.AutoText;
import ar.com.fdvs.dj.domain.CustomExpression;
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.DynamicReport;
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
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;
import ar.com.fdvs.dj.domain.entities.DJGroup;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import ar.com.fdvs.dj.domain.entities.columns.PropertyColumn;

import com.bagnet.nettracer.other.JRGovernedFileVirtualizer;
import com.bagnet.nettracer.other.JRMaxFilesException;
import com.bagnet.nettracer.reporting.ReportingConstants;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerProperties;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

//helper class to ReportBMO

public class StationReportBMO {
	private static Logger logger = Logger.getLogger(StationReportBMO.class);
	public static String newline = System.getProperty("line.separator");
	
	public static String getReportFileDj(JRDataSource ds, Map parameters, String reportname, String rootpath, int outputtype, HttpServletRequest request, ReportBMO rbmo) throws Exception {
		/** look for compiled reports, if can't find it, compile xml report * */
		HttpSession session = request.getSession(); // check session/user
		TracerUtils.checkSession(session);
		
		Agent user = (Agent) session.getAttribute("user");
		// added virtualizer to reduce memory
		String outfile = reportname + "_" + (new SimpleDateFormat("MMddyyyyhhmmss").format(TracerDateTime.getGMTDate()));
		
		JRGovernedFileVirtualizer virtualizer = new JRGovernedFileVirtualizer(100, rootpath + "/reports/tmp", 501);
		virtualizer.setReadOnly(false);
		
//		parameters.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
		
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
				if (!TracerProperties.isTrue(user.getCompanycode_ID(),TracerProperties.SUPPRESSION_PRINTING_NONHTML)) {
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
				if (!TracerProperties.isTrue(user.getCompanycode_ID(),TracerProperties.SUPPRESSION_PRINTING_NONHTML)) {
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
			user = (Agent) request.getSession().getAttribute("user");
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
		String reportHeadingSortByType = "Report Type";
		
		String reportHeadingLastNameFirstName = "Last Name, First Name";
		String reportHeadingIncidentNumber = "Report ID";
		String reportHeadingDate = "Create Date";
		String reportHeadingCDate = "Close Date";
		String reportHeadingTime = "Time";
		String reportHeadingItinerary = "Itinerary";
		String reportHeadingFinal = "Final";
		String reportHeadingAssignedStation = "Assigned";
		String reportHeadingStatus = "Status";
		String reportHeadingFaultStation = "Fault";
		String reportHeadingLossCode = "Loss Code";
		
		
		String myLastNameFirstNameFromResource = resourceBundle.getString("report.station.heading.last.name.first.name");
		if (!( myLastNameFirstNameFromResource == null || myLastNameFirstNameFromResource.equalsIgnoreCase("") )) {
			reportHeadingLastNameFirstName = myLastNameFirstNameFromResource;
		}

		String myIncidentNumber = resourceBundle.getString("report.station.heading.incident.number");
		if (!( myIncidentNumber == null || myIncidentNumber.equalsIgnoreCase("") )) {
			reportHeadingIncidentNumber = myIncidentNumber;
		}
		
		String myDate = resourceBundle.getString("report.station.heading.date");
		if (!( myDate == null || myDate.equalsIgnoreCase("") )) {
			reportHeadingDate = myDate;
		}
		
		String myCDate = resourceBundle.getString("report.station.heading.close.date");
		if (!( myCDate == null || myCDate.equalsIgnoreCase("") )) {
			reportHeadingCDate = myCDate;
		}
		
		String myTime = resourceBundle.getString("report.station.heading.time");
		if (!( myTime == null || myTime.equalsIgnoreCase("") )) {
			reportHeadingTime = myTime;
		}
		
		String myItinerary = resourceBundle.getString("report.station.heading.itinerary");
		if (!( myItinerary == null || myItinerary.equalsIgnoreCase("") )) {
			reportHeadingItinerary = myItinerary;
		}
		
		String myFinal = resourceBundle.getString("report.station.heading.final");
		if (!( myFinal == null || myFinal.equalsIgnoreCase("") )) {
			reportHeadingFinal = myFinal;
		}
		
		String myAssignedStation = resourceBundle.getString("report.station.heading.assigned.station");
		if (!( myAssignedStation == null || myAssignedStation.equalsIgnoreCase("") )) {
			reportHeadingAssignedStation = myAssignedStation;
		}
		
		String myStatus = resourceBundle.getString("report.station.heading.status");
		if (!( myStatus == null || myStatus.equalsIgnoreCase("") )) {
			reportHeadingStatus = myStatus;
		}
		
		String myFaultStation = resourceBundle.getString("report.station.heading.fault.station");
		if (!( myFaultStation == null || myFaultStation.equalsIgnoreCase("") )) {
			reportHeadingFaultStation = myFaultStation;
		}
		
		String myLossCode = resourceBundle.getString("report.station.heading.loss.code");
		if (!( myLossCode == null || myLossCode.equalsIgnoreCase("") )) {
			reportHeadingLossCode = myLossCode;
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
		
		Style footerVariables = new Style("footerVariables");
		footerVariables.setHorizontalAlign(HorizontalAlign.CENTER);
		footerVariables.setVerticalAlign(VerticalAlign.TOP);
		footerVariables.setFont(new Font(14, Font._FONT_TIMES_NEW_ROMAN, true));

		Style groupVariables = new Style("groupVariables");
		groupVariables.setHorizontalAlign(HorizontalAlign.LEFT);
		groupVariables.setVerticalAlign(VerticalAlign.BOTTOM);

		Style titleStyle = new Style("titleStyle");
		titleStyle.setFont(new Font(16, Font._FONT_TIMES_NEW_ROMAN, true));
		titleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
		
		Style subtitleStyle = new Style("subtitleStyle");
		subtitleStyle.setFont(new Font(12, Font._FONT_TIMES_NEW_ROMAN, false));
		subtitleStyle.setHorizontalAlign(HorizontalAlign.LEFT);
		subtitleStyle.setStretchWithOverflow(true);
		
		Style oddRowStyle = new Style();
		oddRowStyle.setBorder(Border.NO_BORDER);
		//oddRowStyle.setBackgroundColor(Color.LIGHT_GRAY);
		Color myRowColor = new Color(0xd3d3d3);
		oddRowStyle.setBackgroundColor(myRowColor);
		oddRowStyle.setTransparency(Transparency.OPAQUE);

		DynamicReportBuilder drb = new DynamicReportBuilder();
		drb.setPageSizeAndOrientation(Page.Page_A4_Landscape());
		
		//get the general information of the report, such as Report Title
		String myReportTitle = "Station Report";
		if (parameters.get("title") != null) {
			myReportTitle = (String) parameters.get("title");
		}
		
		String mySubtitle = "";
		
		if(parameters.get("numtop") != null) {
			mySubtitle += "Top " + parameters.get("numtop") + " Flights   ";
		}
		
		mySubtitle += "This report was generated on " + new Date();
		
		if (parameters.get("sdate") != null && parameters.get("edate") != null) {
			mySubtitle += "   Create Date: " + parameters.get("sdate") + " - " + parameters.get("edate");
		}
		if (parameters.get("status") != null) {
			myStatus = (String) parameters.get("status");
			if (myStatus.equals("")) {
				mySubtitle += "   Status: All";
			} else {
				mySubtitle += "   Status: " + parameters.get("status");
			}
		} else {
			mySubtitle += "   Status: All";
		}
		mySubtitle += "\\r";
		
		if (parameters.get("ddate") != null) {
			String myDepartDate = "" + parameters.get("ddate");
			if (!myDepartDate.equalsIgnoreCase("Any Date")) {
				mySubtitle += "   Depart Date: " + myDepartDate;
			}
		}	
		if (parameters.get("adate") != null) {
			String myArrivalDate = "" + parameters.get("adate");
			if (!myArrivalDate.equalsIgnoreCase("Any Date")) {
				mySubtitle += "   Arrival Date: " + myArrivalDate;
			}
		}
		
		if (parameters.get("b_station") != null) {
			String myOriginStation = "" + parameters.get("b_station");
			if (!myOriginStation.equals("")) {
				mySubtitle += "   Origin Station: " + myOriginStation;
			}
		}
		if (parameters.get("t_station") != null) {
			String myTransferStation = "" + parameters.get("t_station");
			if (!myTransferStation.equals("")) {
				mySubtitle += "   Transfer Station: " + myTransferStation;
			}
		}
		if (parameters.get("e_station") != null) {
			String myTerminateStation = "" + parameters.get("e_station");
			if (!myTerminateStation.equals("")) {
				mySubtitle += "   Terminate Station: " + myTerminateStation;
			}
		}
		
		if (parameters.get("agent_username") != null) {
			mySubtitle += "   Agent: " + parameters.get("agent_username");
		} else {
			mySubtitle += "   Agent: All Agents";
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
		
		AbstractColumn columnSortByType = ColumnBuilder.getNew()
				.setColumnProperty("typedesc", String.class.getName()).setTitle(
				reportHeadingSortByType).setWidth(new Integer(30))
				.setStyle(detailStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnLastNameFirstName = ColumnBuilder.getNew()
				.setColumnProperty("customer_name", String.class.getName()).setTitle(
				reportHeadingLastNameFirstName).setWidth(new Integer(80))
				.setStyle(detailStyle).setHeaderStyle(headerStyle).build();
		
		AbstractColumn columnClaimNumber = ColumnBuilder.getNew()
				.setColumnProperty("incident_ID", String.class.getName()).setTitle(
				reportHeadingIncidentNumber).setWidth(new Integer(84)).setStyle(
				detailStyle).setHeaderStyle(headerStyle).build();
		
		AbstractColumn columnDate = ColumnBuilder.getNew()
				.setColumnProperty("rcreatedate", String.class.getName()).setTitle(
				reportHeadingDate).setWidth(new Integer(40)).setStyle(detailStyle)
				.setHeaderStyle(headerStyle).build();
		
		AbstractColumn columnTime = ColumnBuilder.getNew()
				.setColumnProperty("rcreatetime", String.class.getName()).setTitle(
				reportHeadingTime).setWidth(new Integer(30)).setStyle(detailStyle)
				.setHeaderStyle(headerStyle).build();	

		AbstractColumn columnCDate = ColumnBuilder.getNew()
				.setColumnProperty("rclosedate", String.class.getName()).setTitle(
				reportHeadingCDate).setWidth(new Integer(40)).setStyle(detailStyle)
				.setHeaderStyle(headerStyle).build();
		
		AbstractColumn columnItinerary = ColumnBuilder.getNew()
				.setColumnProperty("itinerary", String.class.getName()).setTitle(
				reportHeadingItinerary).setWidth(new Integer(40)).setStyle(detailStyle)
				.setHeaderStyle(headerStyle).build();
		
		AbstractColumn columnFinal = ColumnBuilder.getNew()
				.setColumnProperty("final_destination", String.class.getName())
				.setTitle(reportHeadingFinal).setWidth(new Integer(24)).setStyle(
				detailStyle).setHeaderStyle(headerStyle).build();
		
		AbstractColumn columnAssignedStationCode = ColumnBuilder.getNew()
				.setColumnProperty("stationcode", String.class.getName())
				.setTitle(reportHeadingAssignedStation).setWidth(new Integer(28)).setStyle(
				detailStyle).setHeaderStyle(headerStyle).build();
		
		AbstractColumn columnStatus = ColumnBuilder.getNew()
				.setColumnProperty("statusdesc", String.class.getName()).setTitle(
				reportHeadingStatus).setWidth(new Integer(24)).setStyle(detailStyle)
				.setHeaderStyle(headerStyle).build();
		
		AbstractColumn columnFaultStationCode = ColumnBuilder.getNew()
				.setColumnProperty("faultstationcode", String.class.getName())
				.setTitle(reportHeadingFaultStation).setWidth(new Integer(24)).setStyle(
				detailStyle).setHeaderStyle(headerStyle).build();
		
		AbstractColumn columnLossCode = ColumnBuilder.getNew()
				.setColumnProperty("loss_code", Integer.class.getName()).setTitle(
				reportHeadingLossCode).setWidth(new Integer(30)).setStyle(detailStyle)
				.setHeaderStyle(headerStyle).build();
		
	

		// grand total count
		drb.addGlobalFooterVariable(columnClaimNumber, DJCalculation.COUNT, headerVariables);
		drb.setGlobalFooterVariableHeight(new Integer(45));
		
		
		Map<String, AbstractColumn> reportColumns = new HashMap<String, AbstractColumn>();

		reportColumns.put("columnSortByType", columnSortByType);
		
		reportColumns.put("columnLastNameFirstName", columnLastNameFirstName);
		reportColumns.put("columnClaimNumber", columnClaimNumber);
		reportColumns.put("columnDate", columnDate);
		reportColumns.put("columnCDate", columnCDate);
		reportColumns.put("columnTime", columnTime);
		reportColumns.put("columnItinerary", columnItinerary);
		reportColumns.put("columnFinal", columnFinal);
		reportColumns.put("columnAssignedStationCode", columnAssignedStationCode);
		reportColumns.put("columnStatus", columnStatus);
		reportColumns.put("columnFaultStationCode", columnFaultStationCode);
		reportColumns.put("columnLossCode", columnLossCode);

		drb = reportStyleSelector(drb, reportColumns, reportStyle);

		//drb.addAutoText(AutoText.AUTOTEXT_PAGE_X_OF_Y, AutoText.POSITION_FOOTER, AutoText.ALIGMENT_CENTER);
		drb.setUseFullPageWidth(true);
		//drb.addAutoText(AutoText.AUTOTEXT_PAGE_X_OF_Y, AutoText.POSITION_FOOTER, AutoText.ALIGMENT_CENTER);
		//Style atStyle = new StyleBuilder(true).setFont(Font.COMIC_SANS_SMALL).setTextColor(Color.red).build();
		Style atStyle2 = new StyleBuilder(true).setFont(new Font(9, Font._FONT_TIMES_NEW_ROMAN, false, true, false)).setTextColor(Color.DARK_GRAY).build();

		drb.addAutoText(AutoText.AUTOTEXT_PAGE_X_OF_Y, AutoText.POSITION_FOOTER, AutoText.ALIGNMENT_RIGHT,60,30,atStyle2);
		
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
		String reportHeadingSortByType = "Report Type";
		
		String reportHeadingLastNameFirstName = "Last Name, First Name";
		String reportHeadingIncidentNumber = "Report ID";
		String reportHeadingDate = "Date";
		String reportHeadingTime = "Time";
		String reportHeadingClDate = "ClDate";
		String reportHeadingItinerary = "Itinerary";
		String reportHeadingFinal = "Final";
		String reportHeadingAssigned = "Assigned";
		String reportHeadingStatus = "Status";
		String reportHeadingFaultStation = "Fault";
		String reportHeadingLossCode = "Loss Code";
		
		
		String myLastNameFirstNameFromResource = resourceBundle.getString("report.station.heading.last.name.first.name");
		if (!( myLastNameFirstNameFromResource == null || myLastNameFirstNameFromResource.equalsIgnoreCase("") )) {
			reportHeadingLastNameFirstName = myLastNameFirstNameFromResource;
		}

		String myIncidentNumber = resourceBundle.getString("report.station.heading.incident.number");
		if (!( myIncidentNumber == null || myIncidentNumber.equalsIgnoreCase("") )) {
			reportHeadingIncidentNumber = myIncidentNumber;
		}
		
		String myDate = resourceBundle.getString("report.station.heading.date");
		if (!( myDate == null || myDate.equalsIgnoreCase("") )) {
			reportHeadingDate = myDate;
		}
		
		String myTime = resourceBundle.getString("report.station.heading.time");
		if (!( myTime == null || myTime.equalsIgnoreCase("") )) {
			reportHeadingTime = myTime;
		}

		String myClDate = resourceBundle.getString("report.station.heading.close.date");
		if (!( myClDate == null || myClDate.equalsIgnoreCase("") )) {
			reportHeadingClDate = myClDate;
		}
		
		String myItinerary = resourceBundle.getString("report.station.heading.itinerary");
		if (!( myItinerary == null || myItinerary.equalsIgnoreCase("") )) {
			reportHeadingItinerary = myItinerary;
		}
		
		String myFinal = resourceBundle.getString("report.station.heading.final");
		if (!( myFinal == null || myFinal.equalsIgnoreCase("") )) {
			reportHeadingFinal = myFinal;
		}
		
		String myAssignedStation = resourceBundle.getString("report.station.heading.assigned.station");
		if (!( myAssignedStation == null || myAssignedStation.equalsIgnoreCase("") )) {
			reportHeadingAssigned = myAssignedStation;
		}
		
		String myStatus = resourceBundle.getString("report.station.heading.status");
		if (!( myStatus == null || myStatus.equalsIgnoreCase("") )) {
			reportHeadingStatus = myStatus;
		}
		
		String myFaultStation = resourceBundle.getString("report.station.heading.fault.station");
		if (!( myFaultStation == null || myFaultStation.equalsIgnoreCase("") )) {
			reportHeadingFaultStation = myFaultStation;
		}
		
		String myLossCode = resourceBundle.getString("report.station.heading.loss.code");
		if (!( myLossCode == null || myLossCode.equalsIgnoreCase("") )) {
			reportHeadingLossCode = myLossCode;
		}
		
		//sort out the report styles
    	drb = drb.addColumn(reportHeadingSortByType, "typedesc", String.class.getName(), 30);
		drb = drb.addColumn(reportHeadingLastNameFirstName,"customer_name",String.class.getName(),54);
		drb = drb.addColumn(reportHeadingIncidentNumber,"incident_ID",String.class.getName(),40);
		drb = drb.addColumn(reportHeadingDate,"rcreatedate",String.class.getName(),30);
		drb = drb.addColumn(reportHeadingTime,"rcreatetime",String.class.getName(),30);
		drb = drb.addColumn(reportHeadingClDate,"rclosedate",String.class.getName(),30);
		drb = drb.addColumn(reportHeadingItinerary,"itinerary",String.class.getName(),30);
		drb = drb.addColumn(reportHeadingFinal,"final_destination",String.class.getName(),24);
		drb = drb.addColumn(reportHeadingAssigned,"stationcode",String.class.getName(),24);
		drb = drb.addColumn(reportHeadingStatus,"statusdesc",String.class.getName(),24);
		drb = drb.addColumn(reportHeadingFaultStation,"faultstationcode",String.class.getName(),24);
		drb = drb.addColumn(reportHeadingLossCode,"loss_code",Integer.class.getName(),24);
        
		//get the general information of the report, such as Report Title
		String myReportTitle = "MBRs Per Flight";
		if (parameters.get("title") != null) {
			myReportTitle = (String) parameters.get("title");
		}
		
		String mySubtitle = "";
		
		if(parameters.get("numtop") != null) {
			mySubtitle += "Top " + parameters.get("numtop") + " Flights   ";
		}
		
		mySubtitle += "This report was generated on " + new Date();
		if (parameters.get("sdate") != null && parameters.get("edate") != null) {
			mySubtitle += "   Date: from " + parameters.get("sdate") + " to " + parameters.get("edate");
		}
		
		
		if (parameters.get("station_subtitle_status") != null) {
			myStatus = (String) parameters.get("station_subtitle_status");
			if (myStatus.equals("")) {
				mySubtitle += "   Status: All";
			} else {
				mySubtitle += "   Status: " + parameters.get("station_subtitle_status");
			}
		} else {
			mySubtitle += "   Status: All";
		}
		
		if (parameters.get("ddate") != null) {
			String myDepartDate = "" + parameters.get("ddate");
			if (!myDepartDate.equalsIgnoreCase("Any Date")) {
				mySubtitle += "   Depart Date: " + myDepartDate;
			}
		}	
		if (parameters.get("adate") != null) {
			String myArrivalDate = "" + parameters.get("adate");
			if (!myArrivalDate.equalsIgnoreCase("Any Date")) {
				mySubtitle += "   Arrival Date: " + myArrivalDate;
			}
		}
		
		if (parameters.get("b_station") != null) {
			String myOriginStation = "" + parameters.get("b_station");
			if (!myOriginStation.equals("")) {
				mySubtitle += "   Origin Station: " + myOriginStation;
			}
		}
		if (parameters.get("t_station") != null) {
			String myTransferStation = "" + parameters.get("t_station");
			if (!myTransferStation.equals("")) {
				mySubtitle += "   Transfer Station: " + myTransferStation;
			}
		}
		if (parameters.get("e_station") != null) {
			String myTerminateStation = "" + parameters.get("e_station");
			if (!myTerminateStation.equals("")) {
				mySubtitle += "   Terminate Station: " + myTerminateStation;
			}
		}
		
		if (parameters.get("agent_username") != null) {
			mySubtitle += "   Agent: " + parameters.get("agent_username");
		} else {
			mySubtitle += "   Agent: All Agents";
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
			AbstractColumn columnSortByType = reportColumns.get("columnSortByType");
		
			AbstractColumn columnLastNameFirstName = reportColumns.get("columnLastNameFirstName");
			AbstractColumn columnClaimNumber = reportColumns.get("columnClaimNumber");
			AbstractColumn columnDate = reportColumns.get("columnDate");
			AbstractColumn columnTime = reportColumns.get("columnTime");
			AbstractColumn columnCDate = reportColumns.get("columnCDate");
			AbstractColumn columnItinerary = reportColumns.get("columnItinerary");
			AbstractColumn columnFinal = reportColumns.get("columnFinal");
			AbstractColumn columnAssignedStationCode = reportColumns.get("columnAssignedStationCode");
			AbstractColumn columnStatus = reportColumns.get("columnStatus");
			AbstractColumn columnFaultStationCode = reportColumns.get("columnFaultStationCode");
			AbstractColumn columnLossCode = reportColumns.get("columnLossCode");
			
			DJGroup g1 = null;
			GroupBuilder gb1 = null;
			AbstractColumn groupByColumn = columnSortByType;  
			
			//invisible field for subtotal
			CustomExpression mySubTotalExpression = getSubTotalCustomExpression();
			
			drb.addColumn(groupByColumn);
			drb.addColumn(columnLastNameFirstName);
			drb.addColumn(columnClaimNumber);
			drb.addColumn(columnDate);
			drb.addColumn(columnTime);
			drb.addColumn(columnCDate);
			drb.addColumn(columnItinerary);
			drb.addColumn(columnFinal);
			drb.addColumn(columnAssignedStationCode);
			drb.addColumn(columnStatus);
			drb.addColumn(columnFaultStationCode);
			drb.addColumn(columnLossCode);
			
			//  experimenting
//			drb.setPrintColumnNames(false);
			
	        gb1 = new GroupBuilder();
	        
	        PropertyColumn myExpressionColumn = (PropertyColumn) groupByColumn;
//	        myExpressionColumn.setExpressionToGroupBy(mySubTotalExpression);
	        
	        g1 = gb1.setCriteriaColumn(myExpressionColumn)
	        .addVariable("myGroupLabel", groupByColumn, DJCalculation.FIRST)
	        .addFooterVariable(columnLastNameFirstName, mySubTotalExpression,groupVariablesLegend)
	        .addFooterVariable(columnClaimNumber, DJCalculation.COUNT, groupVariables)   //sub-totals
	        //  experimenting
			.setGroupLayout(GroupLayout.DEFAULT)
//	        .setGroupLayout(GroupLayout.VALUE_IN_HEADER_WITH_HEADERS_AND_COLUMN_NAME)
			.setFooterVariablesHeight(new Integer(20))
			.setFooterHeight(new Integer(50))
			.setHeaderVariablesHeight(new Integer(35))
			.build();

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
				String myGroupLabel = "";
				myGroupLabel += (String) variables.get("myGroupLabel"); 
				String subTotal = myGroupLabel + " Sub Total :   ";
				return subTotal;
			}

			public String getClassName() {
				return String.class.getName();
			}

		};
	}

}
