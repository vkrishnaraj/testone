package com.bagnet.nettracer.tracing.bmo;

import java.awt.Color;
import java.text.DecimalFormat;
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

//helper class to ReportBMO

public class ClosedRecoveryReportBMO {
	private static Logger logger = Logger.getLogger(ClosedRecoveryReportBMO.class);
	public static String newline = System.getProperty("line.separator");
	
	public static String getReportFileDj(JRDataSource ds, Map parameters, String reportname, String rootpath, int outputtype, HttpServletRequest request, ReportBMO rbmo) throws Exception {
		/** look for compiled reports, if can't find it, compile xml report * */

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
		int reportStyle = 00;
		
		Locale reportLocale = new Locale("en", "US");
		if (parameters.get("reportLocale") != null) {
			reportLocale = (Locale) parameters.get("reportLocale");
		}
		
		ResourceBundle resourceBundle = null;
		if (parameters.get("REPORT_RESOURCE_BUNDLE") != null) {
			resourceBundle = (ResourceBundle) parameters.get("REPORT_RESOURCE_BUNDLE");
		}
		
		//getting report header title from resource bundle
		String reportHeadingSortByCreateDate = "Create Date";
		String reportHeadingSortByCloseDate = "Close Date";
		
		String reportHeadingAssignedStation = "Station Assigned";
		String reportHeadingReportsClosed = "Reports Closed";
		
		String myCreateDateFromResource = resourceBundle.getString("report.recovery.heading.create.date");
		if (!( myCreateDateFromResource == null || myCreateDateFromResource.equalsIgnoreCase("") )) {
			reportHeadingSortByCreateDate = myCreateDateFromResource;
		}
		
		String myCloseDateFromResource = resourceBundle.getString("report.recovery.heading.close.date");
		if (!( myCloseDateFromResource == null || myCloseDateFromResource.equalsIgnoreCase("") )) {
			reportHeadingSortByCloseDate = myCloseDateFromResource;
		}

		String myStationAssigned = resourceBundle.getString("report.recovery.heading.station.assigned");
		if (!( myStationAssigned == null || myStationAssigned.equalsIgnoreCase("") )) {
			reportHeadingAssignedStation = myStationAssigned;
		}

		String myReportsClosed = resourceBundle.getString("report.recovery.heading.reports.closed");
		if (!( myReportsClosed == null || myReportsClosed.equalsIgnoreCase("") )) {
			reportHeadingReportsClosed = myReportsClosed;
		}
		
		Style detailStyle = new Style("detail");
		detailStyle.setVerticalAlign(VerticalAlign.TOP);
		detailStyle.setFont(new Font(11, Font._FONT_ARIAL, false));
		
		Style detailStyle2 = new Style("detail2");
		detailStyle2.setVerticalAlign(VerticalAlign.TOP);
		detailStyle2.setFont(new Font(11, Font._FONT_ARIAL, false));
		detailStyle2.setHorizontalAlign(HorizontalAlign.RIGHT);
		
		

		Style headerStyle = new Style("header");
		headerStyle.setBackgroundColor(Color.LIGHT_GRAY);
		headerStyle.setVerticalAlign(VerticalAlign.MIDDLE);
		headerStyle.setTransparency(Transparency.OPAQUE);
		
		Style headerStyle2 = new Style("header2");
		headerStyle2.setBackgroundColor(Color.LIGHT_GRAY);
		headerStyle2.setVerticalAlign(VerticalAlign.MIDDLE);
		headerStyle2.setTransparency(Transparency.OPAQUE);
		headerStyle2.setHorizontalAlign(HorizontalAlign.RIGHT);

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
		oddRowStyle.setBackgroundColor(Color.LIGHT_GRAY);
		oddRowStyle.setTransparency(Transparency.OPAQUE);

		DynamicReportBuilder drb = new DynamicReportBuilder();
		drb.setPageSizeAndOrientation(Page.Page_A4_Landscape());
		
		//get the general information of the report, such as Report Title
		String myReportTitle = "Recovery";
		if (parameters.get("title") != null) {
			myReportTitle = (String) parameters.get("title");
		}
		
		String mySubtitle = "";
		
		// handle Report Type here
	
		mySubtitle += "This report was generated on " + new Date();
		mySubtitle += "\\r";
		
		if (parameters.get("itemtype") != null) {
			mySubtitle += "   Report Type: " + parameters.get("itemtype");
		} else {
			mySubtitle += "   Report Type: All Report Types";
		}	
		
		if (parameters.get("sdate") != null && parameters.get("edate") != null) {
			mySubtitle += "   Create Date: " + parameters.get("sdate") + " - " + parameters.get("edate");
		}

		mySubtitle += "\\r";

		
		if (parameters.get("agent_username") != null) {
			mySubtitle += "   Agent: " + parameters.get("agent_username");
		} else {
			mySubtitle += "   Agent: All Agents";
		}	
		
		if (parameters.get("csdate") != null && parameters.get("cedate") != null) {
			mySubtitle += "   Close Date: " + parameters.get("csdate") + " - " + parameters.get("cedate");
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
			.setGrandTotalLegend("Total :   ")
			.setGrandTotalLegendStyle(headerVariables)
			.setOddRowBackgroundStyle(oddRowStyle);
		
		AbstractColumn columnSortByCloseDate = ColumnBuilder.getNew()
				.setColumnProperty("closedate", String.class.getName()).setTitle(
				reportHeadingSortByCloseDate ).setWidth(new Integer(30))
				.setStyle(detailStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnAssignedStationCode = ColumnBuilder.getNew()
				.setColumnProperty("stationcode", String.class.getName())
				.setTitle(reportHeadingAssignedStation).setWidth(new Integer(28)).setStyle(
				detailStyle).setHeaderStyle(headerStyle).build();
		
		AbstractColumn columnReportsClosed = ColumnBuilder.getNew()
				.setColumnProperty("reportsclosed", Integer.class.getName()).setTitle(
				reportHeadingReportsClosed).setWidth(new Integer(30))
				.setStyle(detailStyle2).setHeaderStyle(headerStyle2).build();		
			

		CustomExpression myGrandTotalPercentageExpression = getGrandTotalPercentageCustomExpression();
		
		// grand total count
		drb.addGlobalFooterVariable(columnReportsClosed, DJCalculation.SUM, headerVariables);
		drb.setGlobalFooterVariableHeight(new Integer(45));
		
		
		Map<String, AbstractColumn> reportColumns = new HashMap<String, AbstractColumn>();

		reportColumns.put("columnSortByCloseDate", columnSortByCloseDate);
		
		reportColumns.put("columnAssignedStationCode", columnAssignedStationCode);
		reportColumns.put("columnReportsClosed", columnReportsClosed);
		
		if (parameters.get("showdetail") != null) {
			String mySummaryDetailSwitch = "" + parameters.get("showdetail");
			if (mySummaryDetailSwitch.equals("1")) {
				reportStyle = 10;
			}
		}	

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
		String reportHeadingSortByCloseDate = "Close Date";
		
		String reportHeadingAssignedStation = "Station Assigned";
		String reportHeadingReportsClosed = "Reports Closed";
		
		String myCloseDateFromResource = resourceBundle.getString("report.recovery.heading.close.date");
		if (!( myCloseDateFromResource == null || myCloseDateFromResource.equalsIgnoreCase("") )) {
			reportHeadingSortByCloseDate = myCloseDateFromResource;
		}

		String myStationAssigned = resourceBundle.getString("report.recovery.heading.station.assigned");
		if (!( myStationAssigned == null || myStationAssigned.equalsIgnoreCase("") )) {
			reportHeadingAssignedStation = myStationAssigned;
		}

		String myReportsClosed = resourceBundle.getString("report.recovery.heading.reports.closed");
		if (!( myReportsClosed == null || myReportsClosed.equalsIgnoreCase("") )) {
			reportHeadingReportsClosed = myReportsClosed;
		}
		
		//sort out the report styles
		if (parameters.get("showdetail") != null) {
			String mySummaryDetailSwitch = "" + parameters.get("showdetail");
			if (mySummaryDetailSwitch.equals("1")) {
				drb = drb.addColumn(reportHeadingSortByCloseDate, "closedate", String.class.getName(), 30);
			}
		}
		
		drb = drb.addColumn(reportHeadingAssignedStation,"stationcode",String.class.getName(),54);
		drb = drb.addColumn(reportHeadingReportsClosed,"reportsclosed",Integer.class.getName(),30);
        
		//get the general information of the report, such as Report Title
		String myReportTitle = "Recovery";
		if (parameters.get("title") != null) {
			myReportTitle = (String) parameters.get("title");
		}
		
		String mySubtitle = "";
		
		// handling report type here
		
		mySubtitle += "This report was generated on " + new Date();
		mySubtitle += "\\r";
		
		if (parameters.get("itemtype") != null) {
			mySubtitle += "   Report Type: " + parameters.get("itemtype");
		} else {
			mySubtitle += "   Report Type: All Report Types";
		}	
		
		if (parameters.get("sdate") != null && parameters.get("edate") != null) {
			mySubtitle += "   Create Date: " + parameters.get("sdate") + " - " + parameters.get("edate");
		}

		mySubtitle += "\\r";

		
		if (parameters.get("agent_username") != null) {
			mySubtitle += "   Agent: " + parameters.get("agent_username");
		} else {
			mySubtitle += "   Agent: All Agents";
		}	
		
		if (parameters.get("csdate") != null && parameters.get("cedate") != null) {
			mySubtitle += "   Close Date: " + parameters.get("csdate") + " - " + parameters.get("cedate");
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
			groupVariables.setHorizontalAlign(HorizontalAlign.RIGHT);
			groupVariables.setVerticalAlign(VerticalAlign.BOTTOM);
			
			//a style switch
			AbstractColumn columnSortByCloseDate = reportColumns.get("columnSortByCloseDate");
		
			AbstractColumn columnAssignedStationCode = reportColumns.get("columnAssignedStationCode");
			AbstractColumn columnReportsClosed = reportColumns.get("columnReportsClosed");
			
			DJGroup g1 = null;
			GroupBuilder gb1 = null;
			AbstractColumn groupByColumn = columnSortByCloseDate;  
			
			//invisible field for subtotal
			CustomExpression mySubTotalExpression = getSubTotalCustomExpression();
			
			GroupLayout myGroupLayout = GroupLayout.DEFAULT;
			if (reportStyle == 00) {
				myGroupLayout = GroupLayout.VALUE_IN_HEADER;	//in case of no breakdown hide this column
			} else {
				
			}
			drb.addColumn(groupByColumn);			
			drb.addColumn(columnAssignedStationCode);
			drb.addColumn(columnReportsClosed);
			
			
	        gb1 = new GroupBuilder();
	        
	        PropertyColumn myExpressionColumn = (PropertyColumn) groupByColumn;
	        
	        if (reportStyle == 00) {
		        g1 = gb1.setCriteriaColumn(myExpressionColumn)
		        .addVariable("myGroupLabel", groupByColumn, DJCalculation.FIRST)
				.setGroupLayout(myGroupLayout)
				.setFooterVariablesHeight(new Integer(20))
				.setFooterHeight(new Integer(50))
				.setHeaderVariablesHeight(new Integer(35))
				.build();	        	
	        } else {
		        g1 = gb1.setCriteriaColumn(myExpressionColumn)
		        .addVariable("myGroupLabel", groupByColumn, DJCalculation.FIRST)
		        .addFooterVariable(columnAssignedStationCode, mySubTotalExpression,groupVariablesLegend)
		        .addFooterVariable(columnReportsClosed, DJCalculation.SUM, groupVariables)   //sub-totals
				.setGroupLayout(myGroupLayout)
				.setFooterVariablesHeight(new Integer(20))
				.setFooterHeight(new Integer(50))
				.setHeaderVariablesHeight(new Integer(35))
				.build();
	        }

			drb.addGroup(g1);
		
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

	private static CustomExpression getGrandTotalPercentageCustomExpression() {
		return new CustomExpression() {

			public Object evaluate(Map fields, Map variables, Map parameters) {
				String totalPercentage = "";
				if (parameters.get("ratiototal") != null) {
					Double myRatioTotal = (Double) parameters.get("ratiototal");
					DecimalFormat df = new DecimalFormat("0.00");
					totalPercentage += df.format(myRatioTotal) + " %";
				}	
				
				return totalPercentage;
			}

			public String getClassName() {
				return String.class.getName();
			}

		};
	}
}
