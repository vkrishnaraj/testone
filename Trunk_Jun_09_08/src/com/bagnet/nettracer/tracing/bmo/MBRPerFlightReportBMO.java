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

public class MBRPerFlightReportBMO {
	private static Logger logger = Logger.getLogger(MBRPerFlightReportBMO.class);
	
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
		String reportHeadingSortByStation = "Station";
		
		String reportHeadingAirlineFlightNumber = "Airline/Flight Number";
		String reportHeadingNumberOfIncidents = "# of Mishandled";
		String reportHeadingNumberOfBags = "# of Mishandled Bags";
		String reportHeadingStation = "Station";
		
		//used?
		String reportHeadingStatus = "Status";
		
		
		String myAirlineFlightNumberFromResource = resourceBundle.getString("report.mbr.per.flight.heading.airline.flight.number");
		if (!( myAirlineFlightNumberFromResource == null || myAirlineFlightNumberFromResource.equalsIgnoreCase("") )) {
			reportHeadingAirlineFlightNumber = myAirlineFlightNumberFromResource;
		}

		String myNumberOfIncidents = resourceBundle.getString("report.mbr.per.flight.heading.number.of.incidents");
		if (!( myNumberOfIncidents == null || myNumberOfIncidents.equalsIgnoreCase("") )) {
			reportHeadingNumberOfIncidents = myNumberOfIncidents;
		}
		
		String myNumberOfBags = resourceBundle.getString("report.mbr.per.flight.heading.number.of.bags");
		if (!( myNumberOfBags == null || myNumberOfBags.equalsIgnoreCase("") )) {
			reportHeadingNumberOfBags = myNumberOfBags;
		}
		
		String myStation = resourceBundle.getString("report.mbr.per.flight.heading.station");
		if (!( myStation == null || myStation.equalsIgnoreCase("") )) {
			reportHeadingStation = myStation;
		}
		
		String myStatus = resourceBundle.getString("report.mbr.per.flight.heading.status");
		if (!( myStatus == null || myStatus.equalsIgnoreCase("") )) {
			reportHeadingStatus = myStatus;
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
		subtitleStyle.setHorizontalAlign(HorizontalAlign.RIGHT);
		
		Style oddRowStyle = new Style();
		oddRowStyle.setBorder(Border.NO_BORDER);
		oddRowStyle.setBackgroundColor(Color.LIGHT_GRAY);
		oddRowStyle.setTransparency(Transparency.OPAQUE);

		DynamicReportBuilder drb = new DynamicReportBuilder();
		drb.setPageSizeAndOrientation(Page.Page_A4_Landscape());
		
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
		if (parameters.get("mbr_per_flight_subtitle_status") != null) {
			myStatus = (String) parameters.get("mbr_per_flight_subtitle_status");
			if (myStatus.equals("")) {
				mySubtitle += "   Status: All";
			} else {
				mySubtitle += "   Status: " + parameters.get("mbr_per_flight_subtitle_status");
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
//			.setGrandTotalLegend("Grand Total :   ")
			.setGrandTotalLegendStyle(headerVariables)
			.setOddRowBackgroundStyle(oddRowStyle);
		
		AbstractColumn columnSortByStation = ColumnBuilder.getNew()
				.setColumnProperty("stationcode", String.class.getName())
				.setTitle(reportHeadingSortByStation).setWidth(new Integer(30)).setStyle(
				detailStyle).setHeaderStyle(headerStyle).build();
		
		AbstractColumn columnAirlineFlightNumber = ColumnBuilder.getNew()
				.setColumnProperty("airlineAndFlightNumber", String.class.getName()).setTitle(
				reportHeadingAirlineFlightNumber).setWidth(new Integer(30))
				.setStyle(detailStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnNumberOfIncidents = ColumnBuilder.getNew()
				.setColumnProperty("numinc", Integer.class.getName()).setTitle(
				reportHeadingNumberOfIncidents).setWidth(new Integer(40)).setStyle(
				detailStyle).setHeaderStyle(headerStyle).build();
		
		AbstractColumn columnNumberOfBags = ColumnBuilder.getNew()
				.setColumnProperty("numbag", Integer.class.getName()).setTitle(
						reportHeadingNumberOfBags).setWidth(new Integer(40)).setStyle(
				detailStyle).setHeaderStyle(headerStyle).build();
		
	

		// grand total count
//		drb.addGlobalFooterVariable(columnNumberOfIncidents, DJCalculation.SUM, footerVariables);
//		drb.addGlobalFooterVariable(columnNumberOfBags, DJCalculation.SUM, footerVariables);
		
		drb.setGlobalFooterVariableHeight(new Integer(45));
		
		
		Map<String, AbstractColumn> reportColumns = new HashMap<String, AbstractColumn>();

		reportColumns.put("columnSortByStation", columnSortByStation);
		
		reportColumns.put("columnAirlineFlightNumber", columnAirlineFlightNumber);
		reportColumns.put("columnNumberOfIncidents", columnNumberOfIncidents);
		reportColumns.put("columnNumberOfBags", columnNumberOfBags);

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
		String reportHeadingSortByStation = "Station";
		
		String reportHeadingAirlineFlightNumber = "Airline/Flight Number";
		String reportHeadingNumberOfIncidents = "# of Mishandled";
		String reportHeadingNumberOfBags = "# of Mishandled Bags";
		String reportHeadingStation = "Station";
		
		//used?
		String reportHeadingStatus = "Status";
		
		String myAirlineFlightNumberFromResource = resourceBundle.getString("report.mbr.per.flight.heading.airline.flight.number");
		if (!( myAirlineFlightNumberFromResource == null || myAirlineFlightNumberFromResource.equalsIgnoreCase("") )) {
			reportHeadingAirlineFlightNumber = myAirlineFlightNumberFromResource;
		}

		String myNumberOfIncidents = resourceBundle.getString("report.mbr.per.flight.heading.number.of.incidents");
		if (!( myNumberOfIncidents == null || myNumberOfIncidents.equalsIgnoreCase("") )) {
			reportHeadingNumberOfIncidents = myNumberOfIncidents;
		}
		
		String myNumberOfBags = resourceBundle.getString("report.mbr.per.flight.heading.number.of.bags");
		if (!( myNumberOfBags == null || myNumberOfBags.equalsIgnoreCase("") )) {
			reportHeadingNumberOfBags = myNumberOfBags;
		}
		
		String myStation = resourceBundle.getString("report.mbr.per.flight.heading.station");
		if (!( myStation == null || myStation.equalsIgnoreCase("") )) {
			reportHeadingStation = myStation;
		}
		
		String myStatus = resourceBundle.getString("report.mbr.per.flight.heading.status");
		if (!( myStatus == null || myStatus.equalsIgnoreCase("") )) {
			reportHeadingStatus = myStatus;
		}
		
		//sort out the report styles
    	drb = drb.addColumn(reportHeadingStation, "stationcode", String.class.getName(), 30);
		drb = drb.addColumn(reportHeadingAirlineFlightNumber,"airlineAndFlightNumber",String.class.getName(),30);
		drb = drb.addColumn(reportHeadingNumberOfIncidents,"numinc",Integer.class.getName(),40);
		drb = drb.addColumn(reportHeadingNumberOfBags,"numbag",Integer.class.getName(),40);
        
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
		
		
		if (parameters.get("mbr_per_flight_subtitle_status") != null) {
			myStatus = (String) parameters.get("mbr_per_flight_subtitle_status");
			if (myStatus.equals("")) {
				mySubtitle += "   Status: All";
			} else {
				mySubtitle += "   Status: " + parameters.get("mbr_per_flight_subtitle_status");
			}
		} else {
			mySubtitle += "   Status: All";
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
			AbstractColumn columnSortByStation = reportColumns.get("columnSortByStation");
			
			AbstractColumn columnAirlineFlightNumber = reportColumns.get("columnAirlineFlightNumber");
			AbstractColumn columnNumberOfIncidents = reportColumns.get("columnNumberOfIncidents");
			AbstractColumn columnNumberOfBags = reportColumns.get("columnNumberOfBags");
			
			DJGroup g1 = null;
			GroupBuilder gb1 = null;
			AbstractColumn groupByColumn = columnSortByStation;  
			
			//invisible field for subtotal
			CustomExpression mySubTotalExpression = getSubTotalCustomExpression();
			
			drb.addColumn(groupByColumn);

			drb.addColumn(columnAirlineFlightNumber);
			drb.addColumn(columnNumberOfIncidents);
			drb.addColumn(columnNumberOfBags);
			
	        gb1 = new GroupBuilder();
	        g1 = gb1.setCriteriaColumn((PropertyColumn) groupByColumn)
	        .addFooterVariable(columnAirlineFlightNumber, mySubTotalExpression,groupVariablesLegend)
	        .addFooterVariable(columnNumberOfIncidents, DJCalculation.SUM, groupVariables)   //station-totals
	        .addFooterVariable(columnNumberOfBags, DJCalculation.SUM, groupVariables)   //station-totals
			.setGroupLayout(GroupLayout.DEFAULT)
			.setFooterVariablesHeight(new Integer(20))
			.setFooterHeight(new Integer(50))
			.setHeaderVariablesHeight(new Integer(35))
			.build();

			drb.addGroup(g1);
		
		}
		
		return drb;
	}
	
	//
	private static CustomExpression getSubTotalCustomExpression() {
		return new CustomExpression() {

			public Object evaluate(Map fields, Map variables, Map parameters) {
				String stationCode = (String) fields.get("stationcode");
				stationCode = "";
				String subTotal = stationCode + " Station Total :   ";
				return subTotal;
			}

			public String getClassName() {
				return String.class.getName();
			}

		};
	}

}
