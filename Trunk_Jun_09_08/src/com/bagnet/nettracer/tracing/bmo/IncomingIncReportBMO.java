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
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;

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
import ar.com.fdvs.dj.domain.builders.GroupBuilder;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
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

/** @Author Sean Fine
 * Helper class to ReportBMO to create a dynamic report for Incoming Incidents
 */

public class IncomingIncReportBMO {
	private static Logger logger = Logger.getLogger(IncomingIncReportBMO.class);
	public static String newline = System.getProperty("line.separator");
	
	public static String getReportFileDj(JRDataSource ds, Map parameters,
			String reportname, String rootpath, int outputtype,
			HttpServletRequest request, ReportBMO rbmo) throws Exception {
		HttpSession session = request.getSession();
		TracerUtils.checkSession(session);
		
		Agent user = (Agent) session.getAttribute("user");
		String outfile = reportname + "_" + (new SimpleDateFormat("MMddyyyyhhmmss").format(TracerDateTime.getGMTDate()));
		
		JRGovernedFileVirtualizer virtualizer = new JRGovernedFileVirtualizer(100, rootpath + "/reports/tmp", 501);
		virtualizer.setReadOnly(false);
		
		parameters.put(JRParameter.IS_IGNORE_PAGINATION, false);
		
		DynamicReport dr = null;
		JasperPrint jasperPrint;
		try {
			
			dr = buildReport(parameters);
			
			JasperReport jasperReport = DynamicJasperHelper.generateJasperReport(dr, new ClassicLayoutManager(), parameters);
			if (ds != null) {
				jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);
			} else {
				jasperPrint = JasperFillManager.fillReport(jasperReport, parameters);
			}
			
			virtualizer.setReadOnly(true);
	
			if (outputtype == TracingConstants.REPORT_OUTPUT_HTML){
				outfile += ".html";
			} else if (outputtype == TracingConstants.REPORT_OUTPUT_PDF){
				// If the client doesn't have the proper property, default to HTML
				if (!TracerProperties.isTrue(user.getCompanycode_ID(),TracerProperties.SUPPRESSION_PRINTING_NONHTML)) {
					outfile += ".pdf";
				} else {
					outfile += ".html";
					outputtype = TracingConstants.REPORT_OUTPUT_HTML;
					request.setAttribute("outputtype", Integer.toString(TracingConstants.REPORT_OUTPUT_HTML));
				}
			} else if (outputtype == TracingConstants.REPORT_OUTPUT_UNDECLARED) {
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
	
			if (outputtype == TracingConstants.REPORT_OUTPUT_PDF) {
				JasperExportManager.exportReportToPdfFile(jasperPrint, outputpath);
			} else if (outputtype == TracingConstants.REPORT_OUTPUT_HTML) {
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
		
		String reportHeadingIncidentNumber = "Incident Number";
		String reportHeadingIncidentType = "Incident Type";
		String reportHeadingCreateDate = "Date/Time Created";
		String reportHeadingAirline= "Airline Created";
		String reportHeadingStationCreated = "Station Created";
		String reportHeadingAssignedStation = "Station Assigned";
		String reportHeadingStatus = "Status";
		String reportHeadingColor = "Color";
		String reportHeadingType = "Type";
		String reportHeadingPositionID = "Position ID";
		String reportHeadingClaimCheck = "ClaimCheck";
		String reportHeadingLastNameFirstName = "Passenger Name";
		String reportHeadingItinerary = "Itinerary";
		
		
		String myLastNameFirstNameFromResource = resourceBundle.getString("report.incoming.inc.heading.passenger.name");
		if (!( myLastNameFirstNameFromResource == null || myLastNameFirstNameFromResource.equalsIgnoreCase("") )) {
			reportHeadingLastNameFirstName = myLastNameFirstNameFromResource;
		}
		
		String myIncidentNumberFromResource = resourceBundle.getString("report.incoming.inc.heading.incident.num");
		if (!( myIncidentNumberFromResource == null || myIncidentNumberFromResource.equalsIgnoreCase("") )) {
			reportHeadingIncidentNumber = myIncidentNumberFromResource;
		}

		String myIncidentTypeFromResource = resourceBundle.getString("report.incoming.inc.heading.incident.type");
		if (!( myIncidentTypeFromResource == null || myIncidentTypeFromResource.equalsIgnoreCase("") )) {
			reportHeadingIncidentType = myIncidentTypeFromResource;
		}

		String myDateCreatedFromResource = resourceBundle.getString("report.incoming.inc.heading.date.created");
		if (!( myDateCreatedFromResource == null || myDateCreatedFromResource.equalsIgnoreCase("") )) {
			reportHeadingCreateDate = myDateCreatedFromResource;
		}
		
		String myAirlineFromResource = resourceBundle.getString("report.incoming.inc.heading.airline");
		if (!( myAirlineFromResource == null || myAirlineFromResource.equalsIgnoreCase("") )) {
			reportHeadingAirline = myAirlineFromResource;
		}

		String myStationCreatedFromResource = resourceBundle.getString("report.incoming.inc.heading.station.created");
		if (!( myStationCreatedFromResource == null || myStationCreatedFromResource.equalsIgnoreCase("") )) {
			reportHeadingStationCreated = myStationCreatedFromResource;
		}
		
		String myStationAssignedFromResource = resourceBundle.getString("report.incoming.inc.heading.station.assigned");
		if (!( myStationAssignedFromResource == null || myStationAssignedFromResource.equalsIgnoreCase("") )) {
			reportHeadingAssignedStation = myStationAssignedFromResource;
		}

		String myStatusFromResource = resourceBundle.getString("report.incoming.inc.heading.status");
		if (!( myStatusFromResource == null || myStatusFromResource.equalsIgnoreCase("") )) {
			reportHeadingStatus = myStatusFromResource;
		}

		String myColorFromResource = resourceBundle.getString("report.incoming.inc.heading.color");
		if (!( myColorFromResource == null || myColorFromResource.equalsIgnoreCase("") )) {
			reportHeadingColor = myColorFromResource;
		}

		String myTypeFromResource = resourceBundle.getString("report.incoming.inc.heading.type");
		if (!( myTypeFromResource == null || myTypeFromResource.equalsIgnoreCase("") )) {
			reportHeadingType = myTypeFromResource;
		}

		String myPosIDFromResource = resourceBundle.getString("report.incoming.inc.heading.pos.id");
		if (!( myPosIDFromResource == null || myPosIDFromResource.equalsIgnoreCase("") )) {
			reportHeadingPositionID = myPosIDFromResource;
		}
		
		String myClaimCheckFromResource = resourceBundle.getString("report.incoming.inc.heading.claim.check");
		if (!( myClaimCheckFromResource == null || myClaimCheckFromResource.equalsIgnoreCase("") )) {
			reportHeadingClaimCheck = myClaimCheckFromResource;
		}

		String myItineraryFromResource = resourceBundle.getString("report.incoming.inc.heading.itinerary");
		if (!( myItineraryFromResource == null || myItineraryFromResource.equalsIgnoreCase("") )) {
			reportHeadingItinerary = myItineraryFromResource;
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
		footerVariables.setFont(new Font(12, Font._FONT_TIMES_NEW_ROMAN, true));

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
		
		DynamicReportBuilder drb = new DynamicReportBuilder();
		drb.setPageSizeAndOrientation(Page.Page_A4_Landscape());
		
		//get the general information of the report, such as Report Title
		String myReportTitle = "Incoming Incident Report";
		if (parameters.get("title") != null) {
			myReportTitle = (String) parameters.get("title");
		}
		
		String mySubtitle = "";
		
		mySubtitle += "This report was generated on " + new Date();
		
		if (parameters.get("sdate") != null && parameters.get("edate") != null) {
			mySubtitle += "\\r   Create Date: " + parameters.get("sdate") + " - " + parameters.get("edate");
		}
		
		Integer margin = new Integer(20);
		drb
			.setTitleStyle(titleStyle)
			.setTitle(myReportTitle)
			.setSubtitleStyle(subtitleStyle)
			.setSubtitle(mySubtitle)
			.setDetailHeight(new Integer(15)).setLeftMargin(margin)
			.setRightMargin(margin).setTopMargin(margin).setBottomMargin(margin)
			.setGrandTotalLegend("Grand Total :   ")
			.setGrandTotalLegendStyle(headerVariables);
		

		AbstractColumn columnSortByType = ColumnBuilder.getNew()
				.setColumnProperty("incidentType", String.class.getName()).setTitle(
				reportHeadingSortByType).setWidth(new Integer(80))
				.setStyle(detailStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnIncNumber = ColumnBuilder.getNew()
				.setColumnProperty("incident_ID", String.class.getName()).setTitle(
				reportHeadingIncidentNumber).setWidth(new Integer(80)).setStyle(
				detailStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnIncType = ColumnBuilder.getNew()
				.setColumnProperty("incidentType", String.class.getName()).setTitle(
				reportHeadingIncidentType).setWidth(new Integer(55)).setStyle(
				detailStyle).setHeaderStyle(headerStyle).build();
		
		AbstractColumn columnDate = ColumnBuilder.getNew()
				.setColumnProperty("createdDate", String.class.getName()).setTitle(
				reportHeadingCreateDate).setWidth(new Integer(50)).setStyle(detailStyle)
				.setHeaderStyle(headerStyle).build();

		AbstractColumn columnCreatedAirlineCode = ColumnBuilder.getNew()
				.setColumnProperty("airlineCreated", String.class.getName())
				.setTitle(reportHeadingAirline).setWidth(new Integer(32)).setStyle(
				detailStyle).setHeaderStyle(headerStyle).build();
		
		AbstractColumn columnCreatedStationCode = ColumnBuilder.getNew()
				.setColumnProperty("stationCreated", String.class.getName())
				.setTitle(reportHeadingStationCreated).setWidth(new Integer(32)).setStyle(
				detailStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnAssignedStationCode = ColumnBuilder.getNew()
				.setColumnProperty("stationAssigned", String.class.getName())
				.setTitle(reportHeadingAssignedStation).setWidth(new Integer(40)).setStyle(
				detailStyle).setHeaderStyle(headerStyle).build();
		
		AbstractColumn columnStatus = ColumnBuilder.getNew()
				.setColumnProperty("status", String.class.getName()).setTitle(
				reportHeadingStatus).setWidth(new Integer(44)).setStyle(detailStyle)
				.setHeaderStyle(headerStyle).build();
		
		AbstractColumn columnColor = ColumnBuilder.getNew()
				.setColumnProperty("color", String.class.getName()).setTitle(
				reportHeadingColor).setWidth(new Integer(24)).setStyle(detailStyle)
				.setHeaderStyle(headerStyle).build();
		
		AbstractColumn columnType = ColumnBuilder.getNew()
				.setColumnProperty("type", String.class.getName()).setTitle(
				reportHeadingType).setWidth(new Integer(24)).setStyle(detailStyle)
				.setHeaderStyle(headerStyle).build();
		
		AbstractColumn columnItinerary = ColumnBuilder.getNew()
				.setColumnProperty("itinerary", String.class.getName()).setTitle(
				reportHeadingItinerary).setWidth(new Integer(52)).setStyle(detailStyle)
				.setHeaderStyle(headerStyle).build();
		
		AbstractColumn columnPosID = ColumnBuilder.getNew()
				.setColumnProperty("positionId", String.class.getName()).setTitle(
				reportHeadingPositionID).setWidth(new Integer(52)).setStyle(detailStyle)
				.setHeaderStyle(headerStyle).build();
		
		AbstractColumn columnClaimCheck = ColumnBuilder.getNew()
				.setColumnProperty("claimCheck", String.class.getName()).setTitle(
				reportHeadingClaimCheck).setWidth(new Integer(54)).setStyle(detailStyle)
				.setHeaderStyle(headerStyle).build();

		AbstractColumn columnLastNameFirstName = ColumnBuilder.getNew()
				.setColumnProperty("passengerName", String.class.getName()).setTitle(
				reportHeadingLastNameFirstName).setWidth(new Integer(64))
				.setStyle(detailStyle).setHeaderStyle(headerStyle).build();
	
		// grand total count
		drb.addGlobalFooterVariable(columnIncType, DJCalculation.COUNT, headerVariables);
		drb.setGlobalFooterVariableHeight(new Integer(45));
		
		
		Map<String, AbstractColumn> reportColumns = new HashMap<String, AbstractColumn>();

		reportColumns.put("columnSortByType", columnSortByType);
		reportColumns.put("columnIncNumber", columnIncNumber);
		reportColumns.put("columnIncType", columnIncType);
		reportColumns.put("columnDate", columnDate);
		reportColumns.put("columnCreatedAirlineCode", columnCreatedAirlineCode);
		reportColumns.put("columnCreatedStationCode", columnCreatedStationCode);
		reportColumns.put("columnAssignedStationCode", columnAssignedStationCode);
		reportColumns.put("columnStatus", columnStatus);
		reportColumns.put("columnColor", columnColor);
		reportColumns.put("columnType", columnType);
		reportColumns.put("columnItinerary", columnItinerary);
		if(parameters.get("showPosId")!=null)
			reportColumns.put("columnPosID", columnPosID);
		reportColumns.put("columnClaimCheck", columnClaimCheck);
		reportColumns.put("columnLastNameFirstName", columnLastNameFirstName);

		drb = reportStyleSelector(drb, reportColumns, reportStyle);

		drb.setUseFullPageWidth(true);
		Style atStyle2 = new StyleBuilder(true).setFont(new Font(9, Font._FONT_TIMES_NEW_ROMAN, false, true, false)).setTextColor(Color.DARK_GRAY).build();

		drb.addAutoText(AutoText.AUTOTEXT_PAGE_X_OF_Y, AutoText.POSITION_FOOTER, AutoText.ALIGNMENT_RIGHT,60,30,atStyle2);
		
		DynamicReport dr = drb.build();
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

			AbstractColumn columnIncNumber = reportColumns.get("columnIncNumber");
			AbstractColumn columnIncType = reportColumns.get("columnIncType");
			AbstractColumn columnDate = reportColumns.get("columnDate");
			AbstractColumn columnCreatedAirlineCode = reportColumns.get("columnCreatedAirlineCode");
			AbstractColumn columnCreatedStationCode = reportColumns.get("columnCreatedStationCode");
			AbstractColumn columnAssignedStationCode = reportColumns.get("columnAssignedStationCode");
			AbstractColumn columnStatus = reportColumns.get("columnStatus");
			AbstractColumn columnColor = reportColumns.get("columnColor");
			AbstractColumn columnType = reportColumns.get("columnType");
			AbstractColumn columnItinerary = reportColumns.get("columnItinerary");
			AbstractColumn columnPosID = reportColumns.get("columnPosID");
			AbstractColumn columnClaimCheck = reportColumns.get("columnClaimCheck");
			AbstractColumn columnLastNameFirstName = reportColumns.get("columnLastNameFirstName");
			
			DJGroup g1 = null;
			GroupBuilder gb1 = null;
			AbstractColumn groupByColumn = columnSortByType;  
			
			//invisible field for subtotal
			CustomExpression mySubTotalExpression = getSubTotalCustomExpression();
			
			drb.addColumn(columnIncNumber);
			drb.addColumn(columnIncType);
			drb.addColumn(columnDate);
			drb.addColumn(columnCreatedAirlineCode);
			drb.addColumn(columnCreatedStationCode);
			drb.addColumn(columnAssignedStationCode);
			drb.addColumn(columnStatus);
			drb.addColumn(columnColor);
			drb.addColumn(columnType);
			drb.addColumn(columnItinerary);
			if(columnPosID!=null)
				drb.addColumn(columnPosID);
			drb.addColumn(columnClaimCheck);
			drb.addColumn(columnLastNameFirstName);
			
	        gb1 = new GroupBuilder();
	        
	        PropertyColumn myExpressionColumn = (PropertyColumn) groupByColumn;
	        
	        g1 = gb1
	        	.setCriteriaColumn(myExpressionColumn)
    	        .addVariable("myGroupLabel", groupByColumn, DJCalculation.FIRST)
    	        .addFooterVariable(groupByColumn, mySubTotalExpression,groupVariablesLegend)
    	        .addFooterVariable(columnIncNumber, DJCalculation.COUNT, groupVariables)
    	        .setGroupLayout(GroupLayout.DEFAULT)
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
	
	private static CustomExpression getSubTotalCustomExpression() {
		return new CustomExpression() {

			public Object evaluate(Map fields, Map variables, Map parameters) {
				String subTotal = (String) variables.get("myGroupLabel"); 
				return subTotal;
			}

			public String getClassName() {
				return String.class.getName();
			}

		};
	}

}
