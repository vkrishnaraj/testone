package com.bagnet.nettracer.tracing.bmo;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

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

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.AutoText;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.constants.Transparency;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;

import com.bagnet.nettracer.other.JRGovernedFileVirtualizer;
import com.bagnet.nettracer.other.JRMaxFilesException;
import com.bagnet.nettracer.reporting.ReportingConstants;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerProperties;

//helper class to ReportBMO

public class DisputeResolutionReportBMO {
	private static Logger logger = Logger.getLogger(DisputeResolutionReportBMO.class);
	
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
		String reportHeadingIncidentDate = "Incident Date";
		String reportHeadingDisputeDate = "Dispute Date";
		String reportHeadingResolutionDate = "Resolution Date";
		String reportHeadingIncidentNumber = "Report ID";
		String reportHeadingStatus = "Status";
		String reportHeadingDisputingAgent = "Disputing Agent";
		String reportHeadingWorkingAgent = "Working Agent";
		String reportHeadingTypeOfChange = "Type of Change";
		String reportHeadingPreviousFaultStation = "Previous Fault Station";
		String reportHeadingPreviousLossCode = "Previous Fault Code";	
		String reportHeadingSuggestedFaultStation = "Suggested Fault Station";
		String reportHeadingSuggestedLossCode = "Suggested Fault Code";
		String reportHeadingNewFaultStation = "New Fault Station";
		String reportHeadingNewLossCode = "New Fault Code";
		
		String myIncidentDateFromResource = resourceBundle.getString("report.dispute.heading.incident.date");
		if (!( myIncidentDateFromResource == null || myIncidentDateFromResource.equalsIgnoreCase("") )) {
			reportHeadingIncidentDate = myIncidentDateFromResource;
		}

		String myDisputeDateFromResource = resourceBundle.getString("report.dispute.heading.dispute.date");
		if (!( myDisputeDateFromResource == null || myDisputeDateFromResource.equalsIgnoreCase("") )) {
			reportHeadingDisputeDate = myDisputeDateFromResource;
		}
		
		String myResolutionDateFromResource = resourceBundle.getString("report.dispute.heading.resolution.date");
		if (!( myResolutionDateFromResource == null || myResolutionDateFromResource.equalsIgnoreCase("") )) {
			reportHeadingResolutionDate = myResolutionDateFromResource;
		}

		String myIncidentNumber = resourceBundle.getString("report.dispute.heading.incident.number");
		if (!( myIncidentNumber == null || myIncidentNumber.equalsIgnoreCase("") )) {
			reportHeadingIncidentNumber = myIncidentNumber;
		}
		
		String myStatus = resourceBundle.getString("report.dispute.heading.status");
		if (!( myStatus == null || myStatus.equalsIgnoreCase("") )) {
			reportHeadingStatus = myStatus;
		}

		String myDisputingAgentFromResource = resourceBundle.getString("report.dispute.heading.disputing.agent");
		if (!( myDisputingAgentFromResource == null || myDisputingAgentFromResource.equalsIgnoreCase("") )) {
			reportHeadingDisputingAgent = myDisputingAgentFromResource;
		}	
		
		String myWorkingAgentFromResource = resourceBundle.getString("report.dispute.heading.working.agent");
		if (!( myWorkingAgentFromResource == null || myWorkingAgentFromResource.equalsIgnoreCase("") )) {
			reportHeadingWorkingAgent = myWorkingAgentFromResource;
		}
		
		String myTypeOfChangeFromResource = resourceBundle.getString("report.dispute.heading.type.of.change");
		if (!( myTypeOfChangeFromResource == null || myTypeOfChangeFromResource.equalsIgnoreCase("") )) {
			reportHeadingTypeOfChange = myTypeOfChangeFromResource;
		}	
		
		String myPreviousFaultStationFromResource = resourceBundle.getString("report.dispute.heading.previous.fault.station");
		if (!( myPreviousFaultStationFromResource == null || myPreviousFaultStationFromResource.equalsIgnoreCase("") )) {
			reportHeadingPreviousFaultStation = myPreviousFaultStationFromResource;
		}	
		
		String myPreviousLossCodeFromResource = resourceBundle.getString("report.dispute.heading.previous.fault.code");
		if (!( myPreviousLossCodeFromResource == null || myPreviousLossCodeFromResource.equalsIgnoreCase("") )) {
			reportHeadingPreviousLossCode = myPreviousLossCodeFromResource;
		}	
		
		String mySuggestedFaultStationFromResource = resourceBundle.getString("report.dispute.heading.suggested.fault.station");
		if (!( mySuggestedFaultStationFromResource == null || mySuggestedFaultStationFromResource.equalsIgnoreCase("") )) {
			reportHeadingSuggestedFaultStation = mySuggestedFaultStationFromResource;
		}	
		
		String mySuggestedLossCodeFromResource = resourceBundle.getString("report.dispute.heading.suggested.fault.code");
		if (!( mySuggestedLossCodeFromResource == null || mySuggestedLossCodeFromResource.equalsIgnoreCase("") )) {
			reportHeadingSuggestedLossCode = mySuggestedLossCodeFromResource;
		}
		
		String myNewFaultStationFromResource = resourceBundle.getString("report.dispute.heading.new.fault.station");
		if (!( myNewFaultStationFromResource == null || myNewFaultStationFromResource.equalsIgnoreCase("") )) {
			reportHeadingNewFaultStation = myNewFaultStationFromResource;
		}	
		
		String myNewLossCodeFromResource = resourceBundle.getString("report.dispute.heading.new.fault.code");
		if (!( myNewLossCodeFromResource == null || myNewLossCodeFromResource.equalsIgnoreCase("") )) {
			reportHeadingNewLossCode = myNewLossCodeFromResource;
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
		String myReportTitle = "Dispute Resolution Report";
		if (parameters.get("title") != null) {
			myReportTitle = (String) parameters.get("title");
		}
		
		String mySubtitle = "This report was generated on " + new Date();
		
		if (parameters.get("sdate") != null && parameters.get("edate") != null) {
			mySubtitle += "   Date: from " + parameters.get("sdate") + " to " + parameters.get("edate");
		}
		if (parameters.get("dr_subtitle_status") != null) {
			myStatus = (String) parameters.get("dr_subtitle_status");
			if (myStatus.equals("")) {
				mySubtitle += "   Status: All";
			} else {
				mySubtitle += "   Status: " + parameters.get("dr_subtitle_status");
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
			.setOddRowBackgroundStyle(oddRowStyle)
			.setColumnsPerPage(new Integer(1))
			.setUseFullPageWidth(true)
			.setColumnSpace(new Integer(5));

		
		AbstractColumn columnIncidentDate = ColumnBuilder.getNew()
				.setColumnProperty("reportIncidentDateCreate", String.class.getName()).setTitle(
				reportHeadingIncidentDate).setWidth(new Integer(43))	
				.setStyle(detailStyle).setHeaderStyle(headerStyle).build(); //30
		
		AbstractColumn columnDisputeDate = ColumnBuilder.getNew()
				.setColumnProperty("reportDateCreated", String.class.getName()).setTitle(
				reportHeadingDisputeDate).setWidth(new Integer(43))	
				.setStyle(detailStyle).setHeaderStyle(headerStyle).build(); //30
		
		AbstractColumn columnResolvedDate = ColumnBuilder.getNew()
				.setColumnProperty("reportDateResolved", String.class.getName()).setTitle(
				reportHeadingResolutionDate).setWidth(new Integer(43))	
				.setStyle(detailStyle).setHeaderStyle(headerStyle).build(); //30

		AbstractColumn columnReportNumber = ColumnBuilder.getNew()
				.setColumnProperty("incident_id", String.class.getName()).setTitle(
				reportHeadingIncidentNumber).setWidth(new Integer(84)).setStyle(
				detailStyle).setHeaderStyle(headerStyle).build(); //50
		
		AbstractColumn columnStatus = ColumnBuilder.getNew()
				.setColumnProperty("statusDesc", String.class.getName()).setTitle(
				reportHeadingStatus).setWidth(new Integer(60)).setStyle(
				detailStyle).setHeaderStyle(headerStyle).build();  //24
		
		AbstractColumn columnDisputingAgent = ColumnBuilder.getNew()
				.setColumnProperty("disputeAgentName", String.class.getName()).setTitle(
				reportHeadingDisputingAgent).setWidth(new Integer(30)).setStyle(
				detailStyle).setHeaderStyle(headerStyle).build(); //20
		
		AbstractColumn columnWorkingAgent = ColumnBuilder.getNew()
				.setColumnProperty("workingAgentName", String.class.getName()).setTitle(
				reportHeadingWorkingAgent).setWidth(new Integer(30)).setStyle(
				detailStyle).setHeaderStyle(headerStyle).build(); //20
		
		AbstractColumn columnTypeOfChange = ColumnBuilder.getNew()
				.setColumnProperty("typeOfChange", String.class.getName()).setTitle(
				reportHeadingTypeOfChange).setWidth(new Integer(60)).setStyle(
				detailStyle).setHeaderStyle(headerStyle).build(); //24
	
		
		AbstractColumn columnPreviousFaultStation = ColumnBuilder.getNew()
				.setColumnProperty("beforeDisputeFaultStation", String.class.getName()).setTitle(
				reportHeadingPreviousFaultStation).setWidth(new Integer(30)).setStyle(
				detailStyle).setHeaderStyle(headerStyle).build();  //24
		
		AbstractColumn columnPreviousFaultCode = ColumnBuilder.getNew()
				.setColumnProperty("previousFaultCode", Integer.class.getName()).setTitle(
				reportHeadingPreviousLossCode).setWidth(new Integer(30)).setStyle(
				detailStyle).setHeaderStyle(headerStyle).build(); //20
		
		
		AbstractColumn columnSuggestedFaultStation = ColumnBuilder.getNew()
				.setColumnProperty("suggestedFaultStation", String.class.getName()).setTitle(
				reportHeadingSuggestedFaultStation).setWidth(new Integer(30)).setStyle(
				detailStyle).setHeaderStyle(headerStyle).build(); //24
		
		AbstractColumn columnSuggestedFaultCode = ColumnBuilder.getNew()
				.setColumnProperty("suggestedFaultCode", Integer.class.getName()).setTitle(
				reportHeadingSuggestedLossCode).setWidth(new Integer(30)).setStyle(
				detailStyle).setHeaderStyle(headerStyle).build(); //20
		
		AbstractColumn columnNewFaultStation = ColumnBuilder.getNew()
				.setColumnProperty("newFaultStation", String.class.getName()).setTitle(
				reportHeadingNewFaultStation).setWidth(new Integer(30)).setStyle(
				detailStyle).setHeaderStyle(headerStyle).build(); //24

		AbstractColumn columnNewFaultCode = ColumnBuilder.getNew()
				.setColumnProperty("newFaultCode", Integer.class.getName()).setTitle(
				reportHeadingNewLossCode).setWidth(new Integer(30)).setStyle(
				detailStyle).setHeaderStyle(headerStyle).build(); //20


		drb.addColumn(columnIncidentDate);
		drb.addColumn(columnDisputeDate);
		drb.addColumn(columnResolvedDate);
		drb.addColumn(columnReportNumber);
		drb.addColumn(columnStatus);
		drb.addColumn(columnDisputingAgent);
		drb.addColumn(columnWorkingAgent);
		drb.addColumn(columnTypeOfChange);
		drb.addColumn(columnPreviousFaultStation);
		drb.addColumn(columnPreviousFaultCode);
		drb.addColumn(columnSuggestedFaultStation);
		drb.addColumn(columnSuggestedFaultCode);
		drb.addColumn(columnNewFaultStation);
		drb.addColumn(columnNewFaultCode);

		drb.setUseFullPageWidth(true);

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
		String reportHeadingIncidentDate = "Incident Date";
		String reportHeadingDisputeDate = "Dispute Date";
		String reportHeadingResolutionDate = "Resolution Date";
		String reportHeadingIncidentNumber = "Report ID";
		String reportHeadingStatus = "Status";
		String reportHeadingDisputingAgent = "Disputing Agent";
		String reportHeadingWorkingAgent = "Working Agent";
		String reportHeadingTypeOfChange = "Type of Change";
		String reportHeadingPreviousFaultStation = "Previous Fault Station";
		String reportHeadingPreviousLossCode = "Previous Fault Code";	
		String reportHeadingSuggestedFaultStation = "Suggested Fault Station";
		String reportHeadingSuggestedLossCode = "Suggested Fault Code";
		String reportHeadingNewFaultStation = "New Fault Station";
		String reportHeadingNewLossCode = "New Fault Code";
		
		String myIncidentDateFromResource = resourceBundle.getString("report.dispute.heading.incident.date");
		if (!( myIncidentDateFromResource == null || myIncidentDateFromResource.equalsIgnoreCase("") )) {
			reportHeadingIncidentDate = myIncidentDateFromResource;
		}
		
		String myDisputeDateFromResource = resourceBundle.getString("report.dispute.heading.dispute.date");
		if (!( myDisputeDateFromResource == null || myDisputeDateFromResource.equalsIgnoreCase("") )) {
			reportHeadingDisputeDate = myDisputeDateFromResource;
		}
		
		String myResolutionDateFromResource = resourceBundle.getString("report.dispute.heading.resolution.date");
		if (!( myDisputeDateFromResource == null || myDisputeDateFromResource.equalsIgnoreCase("") )) {
			reportHeadingResolutionDate = myResolutionDateFromResource;
		}
		
		String myIncidentNumber = resourceBundle.getString("report.dispute.heading.incident.number");
		if (!( myIncidentNumber == null || myIncidentNumber.equalsIgnoreCase("") )) {
			reportHeadingIncidentNumber = myIncidentNumber;
		}
		
		String myStatus = resourceBundle.getString("report.dispute.heading.status");
		if (!( myStatus == null || myStatus.equalsIgnoreCase("") )) {
			reportHeadingStatus = myStatus;
		}

		String myDisputingAgentFromResource = resourceBundle.getString("report.dispute.heading.disputing.agent");
		if (!( myDisputingAgentFromResource == null || myDisputingAgentFromResource.equalsIgnoreCase("") )) {
			reportHeadingDisputingAgent = myDisputingAgentFromResource;
		}	
		
		String myWorkingAgentFromResource = resourceBundle.getString("report.dispute.heading.working.agent");
		if (!( myWorkingAgentFromResource == null || myWorkingAgentFromResource.equalsIgnoreCase("") )) {
			reportHeadingWorkingAgent = myWorkingAgentFromResource;
		}
		
		String myTypeOfChangeFromResource = resourceBundle.getString("report.dispute.heading.type.of.change");
		if (!( myTypeOfChangeFromResource == null || myTypeOfChangeFromResource.equalsIgnoreCase("") )) {
			reportHeadingTypeOfChange = myTypeOfChangeFromResource;
		}	
		
		String myPreviousFaultStationFromResource = resourceBundle.getString("report.dispute.heading.previous.fault.station");
		if (!( myPreviousFaultStationFromResource == null || myPreviousFaultStationFromResource.equalsIgnoreCase("") )) {
			reportHeadingPreviousFaultStation = myPreviousFaultStationFromResource;
		}	
		
		String myPreviousLossCodeFromResource = resourceBundle.getString("report.dispute.heading.previous.fault.code");
		if (!( myPreviousLossCodeFromResource == null || myPreviousLossCodeFromResource.equalsIgnoreCase("") )) {
			reportHeadingPreviousLossCode = myPreviousLossCodeFromResource;
		}	
		
		String mySuggestedFaultStationFromResource = resourceBundle.getString("report.dispute.heading.suggested.fault.station");
		if (!( mySuggestedFaultStationFromResource == null || mySuggestedFaultStationFromResource.equalsIgnoreCase("") )) {
			reportHeadingSuggestedFaultStation = mySuggestedFaultStationFromResource;
		}	
		
		String mySuggestedLossCodeFromResource = resourceBundle.getString("report.dispute.heading.suggested.fault.code");
		if (!( mySuggestedLossCodeFromResource == null || mySuggestedLossCodeFromResource.equalsIgnoreCase("") )) {
			reportHeadingSuggestedLossCode = mySuggestedLossCodeFromResource;
		}
		
		String myNewFaultStationFromResource = resourceBundle.getString("report.dispute.heading.new.fault.station");
		if (!( myNewFaultStationFromResource == null || myNewFaultStationFromResource.equalsIgnoreCase("") )) {
			reportHeadingNewFaultStation = myNewFaultStationFromResource;
		}	
		
		String myNewLossCodeFromResource = resourceBundle.getString("report.dispute.heading.new.fault.code");
		if (!( myNewLossCodeFromResource == null || myNewLossCodeFromResource.equalsIgnoreCase("") )) {
			reportHeadingNewLossCode = myNewLossCodeFromResource;
		}
		
		drb = drb.addColumn(reportHeadingIncidentDate, "reportIncidentDateCreate", String.class.getName(), 30);
    	drb = drb.addColumn(reportHeadingDisputeDate, "reportDateCreated", String.class.getName(), 30);
    	drb = drb.addColumn(reportHeadingResolutionDate, "reportDateResolved", String.class.getName(), 30);
		drb = drb.addColumn(reportHeadingIncidentNumber,"incident_id",String.class.getName(),54);
		drb = drb.addColumn(reportHeadingStatus,"statusDesc",String.class.getName(),30);
		drb = drb.addColumn(reportHeadingDisputingAgent,"disputeAgentName",String.class.getName(),24);
		drb = drb.addColumn(reportHeadingWorkingAgent,"workingAgentName",String.class.getName(),24);
		drb = drb.addColumn(reportHeadingTypeOfChange,"typeOfChange",String.class.getName(),24);
		drb = drb.addColumn(reportHeadingPreviousFaultStation,"beforeDisputeFaultStation",String.class.getName(),20);
		drb = drb.addColumn(reportHeadingPreviousLossCode,"previousFaultCode",Integer.class.getName(),24);
		drb = drb.addColumn(reportHeadingSuggestedFaultStation,"suggestedFaultStation",String.class.getName(),20);
		drb = drb.addColumn(reportHeadingSuggestedLossCode,"suggestedFaultCode",Integer.class.getName(),24);
		drb = drb.addColumn(reportHeadingNewFaultStation,"newFaultStation",String.class.getName(),20);
		drb = drb.addColumn(reportHeadingNewLossCode,"newFaultCode",Integer.class.getName(),24);
        
		//get the general information of the report, such as Report Title
		String myReportTitle = "Dispute Resolution Report";
		if (parameters.get("title") != null) {
			myReportTitle = (String) parameters.get("title");
		}
		
		String mySubtitle = "This report was generated on " + new Date();
		if (parameters.get("sdate") != null && parameters.get("edate") != null) {
			mySubtitle += "   Date: from " + parameters.get("sdate") + " to " + parameters.get("edate");
		}
		if (parameters.get("dr_subtitle_status") != null) {
			myStatus = (String) parameters.get("dr_subtitle_status");
			if (myStatus.equals("")) {
				mySubtitle += "   Status: All";
			} else {
				mySubtitle += "   Status: " + parameters.get("dr_subtitle_status");
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

}
