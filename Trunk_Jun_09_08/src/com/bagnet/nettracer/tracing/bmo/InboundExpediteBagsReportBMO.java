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
import com.bagnet.nettracer.tracing.utils.TracerUtils;

//helper class to ReportBMO

public class InboundExpediteBagsReportBMO {
	private static Logger logger = Logger.getLogger(InboundExpediteBagsReportBMO.class);
	
	public static String getReportFileDj(JRDataSource ds, Map parameters, String reportname, String rootpath, int outputtype, HttpServletRequest request, ReportBMO rbmo) throws Exception {
		/** look for compiled reports, if can't find it, compile xml report * */
		HttpSession session = request.getSession(); // check session/user
		TracerUtils.checkSession(session);
		
		Agent user = (Agent) session.getAttribute("user");
		
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
		ResourceBundle resourceBundle = (ResourceBundle) parameters.get("REPORT_RESOURCE_BUNDLE");
		
		//getting report header title from resource bundle
		String reportHeadingOhdBaggage = "OHD Baggage";
		String reportHeadingIncidentNumber = "PIR/DPR File Number";
		String reportHeadingColor = "Color";
		String reportHeadingType = "Type";
		String reportHeadingExpediteTagNumber = "Expedite Tag Number";
		String reportHeadingBagTagNumber = "Baggage Tag Number";
		
		String myOhdBaggageFromResource = resourceBundle.getString("header.ohd");
		if (!( myOhdBaggageFromResource == null || myOhdBaggageFromResource.equalsIgnoreCase("") )) {
			reportHeadingOhdBaggage = myOhdBaggageFromResource;
		}

		String myIncidentNumber = resourceBundle.getString("report.ohd.heading.incident.number");
		if (!( myIncidentNumber == null || myIncidentNumber.equalsIgnoreCase("") )) {
			reportHeadingIncidentNumber = myIncidentNumber;
		}
		
		String myColor = resourceBundle.getString("colname.color");
		if (!( myColor == null || myColor.equalsIgnoreCase("") )) {
			reportHeadingColor = myColor;
		}
		
		String myType = resourceBundle.getString("colname.bagtype");
		if (!( myType == null || myType.equalsIgnoreCase("") )) {
			reportHeadingType = myType;
		}
		
		String myExpediteTagNumber = resourceBundle.getString("colname.expedite_number");
		if (!( myExpediteTagNumber == null || myExpediteTagNumber.equalsIgnoreCase("") )) {
			reportHeadingExpediteTagNumber = myExpediteTagNumber;
		}
		
		String myBagTagNumber = resourceBundle.getString("colname.baggage_check");
		if (!( myBagTagNumber == null || myBagTagNumber.equalsIgnoreCase("") )) {
			reportHeadingBagTagNumber = myBagTagNumber;
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
		String myReportTitle = resourceBundle.getString("header.reportnum.30");
		
		String mySubtitle = "This report was generated on " + new Date();		
		
		Integer margin = new Integer(20);
		drb
			.setTitleStyle(titleStyle)
			.setTitle(myReportTitle)
			.setSubtitleStyle(subtitleStyle)
			.setSubtitle(mySubtitle)
			.setDetailHeight(new Integer(15)).setLeftMargin(margin)
			.setRightMargin(margin).setTopMargin(margin).setBottomMargin(margin)
			.setPrintBackgroundOnOddRows(true)
			.setOddRowBackgroundStyle(oddRowStyle);
		
		AbstractColumn columnOhdBaggage = ColumnBuilder.getNew()
				.setColumnProperty("onhandId", String.class.getName())
				.setTitle(reportHeadingOhdBaggage).setWidth(new Integer(54)).setStyle(
				detailStyle).setHeaderStyle(headerStyle).build();
		
		AbstractColumn columnIncidentNumber = ColumnBuilder.getNew()
				.setColumnProperty("matchingIncident", String.class.getName()).setTitle(
				reportHeadingIncidentNumber).setWidth(new Integer(54))
				.setStyle(detailStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnColor = ColumnBuilder.getNew()
				.setColumnProperty("color", String.class.getName()).setTitle(
				reportHeadingColor).setWidth(new Integer(24)).setStyle(
				detailStyle).setHeaderStyle(headerStyle).build();
		
		AbstractColumn columnType = ColumnBuilder.getNew()
				.setColumnProperty("type", String.class.getName()).setTitle(
				reportHeadingType).setWidth(new Integer(24)).setStyle(detailStyle)
				.setHeaderStyle(headerStyle).build();
		
		AbstractColumn columnExpediteTagNumber = ColumnBuilder.getNew()
				.setColumnProperty("expediteNumber", String.class.getName()).setTitle(
				reportHeadingExpediteTagNumber).setWidth(new Integer(50)).setStyle(detailStyle)
				.setHeaderStyle(headerStyle).build();	
		
		AbstractColumn columnBagTagNumber = ColumnBuilder.getNew()
				.setColumnProperty("claimNumber", String.class.getName()).setTitle(
				reportHeadingBagTagNumber).setWidth(new Integer(50)).setStyle(detailStyle)
				.setHeaderStyle(headerStyle).build();
		
		
		Map<String, AbstractColumn> reportColumns = new HashMap<String, AbstractColumn>();

		reportColumns.put("columnOhdBaggage", columnOhdBaggage);
		reportColumns.put("columnIncidentNumber", columnIncidentNumber);
		reportColumns.put("columnColor", columnColor);
		reportColumns.put("columnType", columnType);
		reportColumns.put("columnExpediteTagNumber", columnExpediteTagNumber);
		reportColumns.put("columnBagTagNumber", columnBagTagNumber);
		
		drb = reportStyleSelector(drb, reportColumns);

		drb.setUseFullPageWidth(true);
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
			Map<String, AbstractColumn> reportColumns) throws Exception {
		
		if (reportColumns != null) {
			
			Style groupVariablesLegend = new Style("groupVariablesLegend");
			groupVariablesLegend.setHorizontalAlign(HorizontalAlign.RIGHT);
			groupVariablesLegend.setVerticalAlign(VerticalAlign.BOTTOM);
			
			Style groupVariables = new Style("groupVariables");
			groupVariables.setHorizontalAlign(HorizontalAlign.LEFT);
			groupVariables.setVerticalAlign(VerticalAlign.BOTTOM);
			
			//a style switch
			AbstractColumn columnOhdBaggage = reportColumns.get("columnOhdBaggage");
			AbstractColumn columnIncidentNumber = reportColumns.get("columnIncidentNumber");
			AbstractColumn columnColor = reportColumns.get("columnColor");
			AbstractColumn columnType = reportColumns.get("columnType");
			AbstractColumn columnExpediteTagNumber = reportColumns.get("columnExpediteTagNumber");
			AbstractColumn columnBagTagNumber = reportColumns.get("columnBagTagNumber");
			
			
			drb.addColumn(columnOhdBaggage);
			drb.addColumn(columnIncidentNumber);
			drb.addColumn(columnColor);
			drb.addColumn(columnType);
			drb.addColumn(columnExpediteTagNumber);
			drb.addColumn(columnBagTagNumber);
		
		}
		
		return drb;
	}

}
