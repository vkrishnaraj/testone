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

import com.bagnet.nettracer.other.JRGovernedFileVirtualizer;
import com.bagnet.nettracer.other.JRMaxFilesException;
import com.bagnet.nettracer.reporting.ReportingConstants;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.TracerProperties;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

//helper class to ReportBMO

public class MBRReportBMO {
	private static Logger logger = Logger.getLogger(MBRReportBMO.class);
	
	public static String getReportFileDj(JRDataSource ds, Map parameters, String reportname, String rootpath, int outputtype, HttpServletRequest request, ReportBMO rbmo) throws Exception {
		/** look for compiled reports, if can't find it, compile xml report * */
		HttpSession session = request.getSession(); // check session/user
		TracerUtils.checkSession(session);
		
		Agent user = (Agent) session.getAttribute("user");
		
		// added virtualizer to reduce memory
		String outfile = reportname + "_" + (new SimpleDateFormat("MMddyyyyhhmmss").format(TracerDateTime.getGMTDate()));
		
		JRGovernedFileVirtualizer virtualizer = new JRGovernedFileVirtualizer(100, rootpath + "/reports/tmp", 501);
		//JRGovernedFileVirtualizer virtualizer = new JRGovernedFileVirtualizer(10, rootpath + "/reports/tmp", 501);
		virtualizer.setReadOnly(false);
		
		//parameters.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
		
		if (outputtype == TracingConstants.REPORT_OUTPUT_XLS) {
			parameters.put(JRParameter.IS_IGNORE_PAGINATION, true);
		} else {
			parameters.put(JRParameter.IS_IGNORE_PAGINATION, false);
		}
		
		DynamicReport dr = null;
		JasperPrint jasperPrint;
		try {
			
			//reportStyle : first digit represents GroupBy; second digit represents Option
			//ex : 00-Groupby Assigned Station, Summary; 01-Groupby Assigned Station, Detail;
			//     10-Groupby Fault Station, Summary; 11-Groupby Fault Station, Detail
			int reportStyle = 11;
			if (parameters.get("reportStyle") != null) {
				String myReportStyle = (String) parameters.get("reportStyle");
				reportStyle = Integer.parseInt(myReportStyle);
			}
			
			Locale reportLocale = new Locale("en", "US");
			if (parameters.get("reportLocale") != null) {
				reportLocale = (Locale) parameters.get("reportLocale");
			}
			
			ResourceBundle myResources = null;
			if (parameters.get("REPORT_RESOURCE_BUNDLE") != null) {
				myResources = (ResourceBundle) parameters.get("REPORT_RESOURCE_BUNDLE");
			}
			
			if (outputtype == TracingConstants.REPORT_OUTPUT_XLS) {
				dr = buildExcelReport(parameters);
			} else {
				if(parameters.get("showall")!=null || (parameters.get("showassigncity")!=null && parameters.get("groupassigncity")!=null) || (parameters.get("showfaultcity")!=null && parameters.get("groupfaultcity")!=null))
					dr = buildReport(parameters);
				else{
					logger.error("Neither Assign City or Fault City are available for grouping");
				}
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
				exporter.setParameter(JRHtmlExporterParameter.HTML_HEADER, "<style>table{border-collapse:collapse}</style>");

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
				Map excelParameters = new HashMap();
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
		//reportStyle : first digit represents GroupBy; second digit represents Option
		//ex : 00-Groupby Assigned Station, Summary; 01-Groupby Assigned Station, Detail;
		//     10-Groupby Fault Station, Summary; 11-Groupby Fault Station, Detail
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
		
		//getting report header title from resource bundle
		String reportHeadingSortByFaultStation = "Fault";
		String reportHeadingSortByAssignedStation = "Assigned";
		String reportHeadingSortByType = "Type";
		
		String reportHeadingLastNameFirstName = "Last Name, First Name";
		String reportHeadingIncidentNumber = "Report ID";
		String reportHeadingDate = "Date";
		String reportHeadingTime = "Time";
		String reportHeadingItinerary = "Itinerary";
		String reportHeadingFinal = "Destination";
		String reportHeadingStatus = "Status";
		String reportHeadingFaultStation = "Fault";
		String reportHeadingLossCode = "Loss Code";
		String reportHeadingAssignedStation = "Assigned";
		
		String myLastNameFirstNameFromResource = resourceBundle.getString("report.mbr.heading.last.name.first.name");
		if (!( myLastNameFirstNameFromResource == null || myLastNameFirstNameFromResource.equalsIgnoreCase("") )) {
			reportHeadingLastNameFirstName = myLastNameFirstNameFromResource;
		}

		String myIncidentNumber = resourceBundle.getString("report.mbr.heading.incident.number");
		if (!( myIncidentNumber == null || myIncidentNumber.equalsIgnoreCase("") )) {
			reportHeadingIncidentNumber = myIncidentNumber;
		}
		
		String myDate = resourceBundle.getString("report.mbr.heading.date");
		if (!( myDate == null || myDate.equalsIgnoreCase("") )) {
			reportHeadingDate = myDate;
		}
		
		String myTime = resourceBundle.getString("report.mbr.heading.time");
		if (!( myTime == null || myTime.equalsIgnoreCase("") )) {
			reportHeadingTime = myTime;
		}
		
		String myItinerary = resourceBundle.getString("report.mbr.heading.itinerary");
		if (!( myItinerary == null || myItinerary.equalsIgnoreCase("") )) {
			reportHeadingItinerary = myItinerary;
		}
		
		String myFinal = resourceBundle.getString("report.mbr.heading.final");
		if (!( myFinal == null || myFinal.equalsIgnoreCase("") )) {
			reportHeadingFinal = myFinal;
		}
		
		String myStatus = resourceBundle.getString("report.mbr.heading.status");
		if (!( myStatus == null || myStatus.equalsIgnoreCase("") )) {
			reportHeadingStatus = myStatus;
		}
		
		String myFaultStation = resourceBundle.getString("report.mbr.heading.fault.station");
		if (!( myFaultStation == null || myFaultStation.equalsIgnoreCase("") )) {
			reportHeadingFaultStation = myFaultStation;
		}
		
		String myLossCode = resourceBundle.getString("report.mbr.heading.loss.code");
		if (!( myLossCode == null || myLossCode.equalsIgnoreCase("") )) {
			reportHeadingLossCode = myLossCode;
		}
		
		String myAssignedStation = resourceBundle.getString("report.mbr.heading.assigned.station");
		if (!( myAssignedStation == null || myAssignedStation.equalsIgnoreCase("") )) {
			reportHeadingAssignedStation = myAssignedStation;
		}

		int columnchecked=Integer.valueOf(parameters.get("checknum").toString());
		int fontsize;
		if(columnchecked<=12 && columnchecked>=9){
			fontsize=10;
		} else if (columnchecked>=4&& columnchecked<9){
			fontsize=11;
		} else {
			fontsize=12;
		}
		
		Style detailStyle = new Style("detail");
		detailStyle.setVerticalAlign(VerticalAlign.TOP);
		detailStyle.setFont(new Font(fontsize, Font._FONT_ARIAL, false));

		Style headerStyle = new Style("header");
		headerStyle.setBackgroundColor(Color.LIGHT_GRAY);
		headerStyle.setVerticalAlign(VerticalAlign.MIDDLE);
		headerStyle.setTransparency(Transparency.OPAQUE);
		headerStyle.setFont(new Font(fontsize, Font._FONT_TIMES_NEW_ROMAN, false));

		Style headerVariables = new Style("headerVariables");
		headerVariables.setHorizontalAlign(HorizontalAlign.RIGHT);
		headerVariables.setVerticalAlign(VerticalAlign.TOP);
		headerVariables.setFont(new Font(fontsize, Font._FONT_TIMES_NEW_ROMAN, true));

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
		//oddRowStyle.setBackgroundColor(Color.LIGHT_GRAY);
		Color myRowColor = new Color(0xd3d3d3);
		oddRowStyle.setBackgroundColor(myRowColor);
		oddRowStyle.setTransparency(Transparency.OPAQUE);

		DynamicReportBuilder drb = new DynamicReportBuilder();
		
		//testing for USAIR issue
//		drb.setPageSizeAndOrientation(Page.Page_A4_Landscape());
		drb.setPageSizeAndOrientation(Page.Page_A4_Portrait());
		drb.setUseFullPageWidth(true);
		
		//get the general information of the report, such as Report Title
		String myReportTitle = "MBR Report";
		if (parameters.get("title") != null) {
			myReportTitle = (String) parameters.get("title");
		}
		
		String mySubtitle = "This report was generated on " + new Date();
		
		if (parameters.get("sdate") != null && parameters.get("edate") != null) {
			mySubtitle += "   Date: from " + parameters.get("sdate") + " to " + parameters.get("edate");
		}
		if (parameters.get("mbr_subtitle_status") != null) {
			myStatus = (String) parameters.get("mbr_subtitle_status");
			if (myStatus.equals("")) {
				mySubtitle += "   Status: All";
			} else {
				mySubtitle += "   Status: " + parameters.get("mbr_subtitle_status");
			}
		} else {
			mySubtitle += "   Status: All";
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
		
		AbstractColumn columnSortByAssignedStation = ColumnBuilder.getNew()
				.setColumnProperty("stationcode", String.class.getName())
				.setTitle(reportHeadingAssignedStation).setWidth(new Integer(30)).setStyle(
				detailStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnSortByFaultStation = ColumnBuilder.getNew()
				.setColumnProperty("faultstationcode", String.class.getName()).setTitle(
				reportHeadingSortByFaultStation).setWidth(new Integer(30))
				.setStyle(detailStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnSortByType = ColumnBuilder.getNew()
				.setColumnProperty("typedesc", String.class.getName()).setTitle(
				reportHeadingSortByType).setWidth(new Integer(60))
				.setStyle(detailStyle).setHeaderStyle(headerStyle).build();
		
		AbstractColumn columnLastNameFirstName = ColumnBuilder.getNew()
				.setColumnProperty("customer_name", String.class.getName()).setTitle(
				reportHeadingLastNameFirstName).setWidth(new Integer(80))
				.setStyle(detailStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnClaimNumber = ColumnBuilder.getNew()
				.setColumnProperty("incident_ID", String.class.getName()).setTitle(
				reportHeadingIncidentNumber).setWidth(new Integer(81)).setStyle(
				detailStyle).setHeaderStyle(headerStyle).build();
		
		AbstractColumn columnDate = ColumnBuilder.getNew()
				.setColumnProperty("rcreatedate", String.class.getName()).setTitle(
				reportHeadingDate).setWidth(new Integer(43)).setStyle(detailStyle)
				.setHeaderStyle(headerStyle).build();
		
		AbstractColumn columnTime = ColumnBuilder.getNew()
				.setColumnProperty("rcreatetime", String.class.getName()).setTitle(
				reportHeadingTime).setWidth(new Integer(30)).setStyle(detailStyle)
				.setHeaderStyle(headerStyle).build();	
		
		AbstractColumn columnItinerary = ColumnBuilder.getNew()
				.setColumnProperty("itinerary", String.class.getName()).setTitle(
				reportHeadingItinerary).setWidth(new Integer(40)).setStyle(detailStyle)
				.setHeaderStyle(headerStyle).build();
		
		AbstractColumn columnFinal = ColumnBuilder.getNew()
				.setColumnProperty("final_destination", String.class.getName())
				.setTitle(reportHeadingFinal).setWidth(new Integer(50)).setStyle(
				detailStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnStatus = ColumnBuilder.getNew()
				.setColumnProperty("statusdesc", String.class.getName()).setTitle(
				reportHeadingStatus).setWidth(new Integer(30)).setStyle(detailStyle)
				.setHeaderStyle(headerStyle).build();
		
		AbstractColumn columnFaultStationCode = ColumnBuilder.getNew()
				.setColumnProperty("faultstationcode", String.class.getName())
				.setTitle(reportHeadingFaultStation).setWidth(new Integer(30)).setStyle(
				detailStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnLossCode = ColumnBuilder.getNew()
				.setColumnProperty("loss_code", Integer.class.getName()).setTitle(
				reportHeadingLossCode).setWidth(new Integer(30)).setStyle(detailStyle)
				.setHeaderStyle(headerStyle).build();

		Map<String, AbstractColumn> reportColumns = new HashMap<String, AbstractColumn>();
		if(parameters.get("showall")!=null || parameters.get("showassigncity")!=null) reportColumns.put("columnSortByAssignedStation", columnSortByAssignedStation);
		if(parameters.get("showall")!=null || parameters.get("showfaultcity")!=null) reportColumns.put("columnSortByFaultStation", columnSortByFaultStation);
		if(parameters.get("showall")!=null || parameters.get("showtype")!=null) reportColumns.put("columnSortByType", columnSortByType);
		
		if(parameters.get("showall")!=null || parameters.get("showlastname")!=null || parameters.get("showfirstname")!=null) reportColumns.put("columnLastNameFirstName", columnLastNameFirstName);
		if(parameters.get("showall")!=null || parameters.get("showreportid")!=null) reportColumns.put("columnClaimNumber", columnClaimNumber);
		if(parameters.get("showall")!=null || parameters.get("showdate")!=null) reportColumns.put("columnDate", columnDate);
		if(parameters.get("showall")!=null || parameters.get("showtime")!=null) reportColumns.put("columnTime", columnTime);
		if(parameters.get("showall")!=null || parameters.get("showitinerary")!=null) reportColumns.put("columnItinerary", columnItinerary);
		if(parameters.get("showall")!=null || parameters.get("showdestination")!=null) reportColumns.put("columnFinal", columnFinal);
		if(parameters.get("showall")!=null || parameters.get("showstatus")!=null) reportColumns.put("columnStatus", columnStatus);
		if(parameters.get("showall")!=null || parameters.get("showfaultcity")!=null) reportColumns.put("columnFaultStationCode", columnFaultStationCode);
		if(parameters.get("showall")!=null || parameters.get("showlosscode")!=null) reportColumns.put("columnLossCode", columnLossCode);

		drb = reportStyleSelector(drb, reportColumns, reportStyle);

		// grand total count
		
		if(drb.getColumn(2)!=null) drb.addGlobalFooterVariable(drb.getColumn(2), DJCalculation.COUNT, headerVariables);
		drb.setGlobalFooterVariableHeight(new Integer(45));
		
		//drb.setAllowDetailSplit(false);

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
		String reportHeadingSortByFaultStation = "Fault";
		String reportHeadingSortByAssignedStation = "Assigned";
		String reportHeadingSortByType = "Type";
		
		String reportHeadingLastNameFirstName = "Last Name, First Name";
		String reportHeadingIncidentNumber = "Report ID";
		String reportHeadingDate = "Date";
		String reportHeadingTime = "Time";
		String reportHeadingItinerary = "Itinerary";
		String reportHeadingFinal = "Destination";
		String reportHeadingStatus = "Status";
		String reportHeadingFaultStation = "Fault";
		String reportHeadingLossCode = "Loss Code";
		String reportHeadingAssignedStation = "Assigned";
		
		String myLastNameFirstNameFromResource = resourceBundle.getString("report.mbr.heading.last.name.first.name");
		if (!( myLastNameFirstNameFromResource == null || myLastNameFirstNameFromResource.equalsIgnoreCase("") )) {
			reportHeadingLastNameFirstName = myLastNameFirstNameFromResource;
		}

		String myIncidentNumber = resourceBundle.getString("report.mbr.heading.incident.number");
		if (!( myIncidentNumber == null || myIncidentNumber.equalsIgnoreCase("") )) {
			reportHeadingIncidentNumber = myIncidentNumber;
		}
		
		String myDate = resourceBundle.getString("report.mbr.heading.date");
		if (!( myDate == null || myDate.equalsIgnoreCase("") )) {
			reportHeadingDate = myDate;
		}
		
		String myTime = resourceBundle.getString("report.mbr.heading.time");
		if (!( myTime == null || myTime.equalsIgnoreCase("") )) {
			reportHeadingTime = myTime;
		}
		
		String myItinerary = resourceBundle.getString("report.mbr.heading.itinerary");
		if (!( myItinerary == null || myItinerary.equalsIgnoreCase("") )) {
			reportHeadingItinerary = myItinerary;
		}
		
		String myFinal = resourceBundle.getString("report.mbr.heading.final");
		if (!( myFinal == null || myFinal.equalsIgnoreCase("") )) {
			reportHeadingFinal = myFinal;
		}
		
		String myStatus = resourceBundle.getString("report.mbr.heading.status");
		if (!( myStatus == null || myStatus.equalsIgnoreCase("") )) {
			reportHeadingStatus = myStatus;
		}
		
		String myFaultStation = resourceBundle.getString("report.mbr.heading.fault.station");
		if (!( myFaultStation == null || myFaultStation.equalsIgnoreCase("") )) {
			reportHeadingFaultStation = myFaultStation;
		}
		
		String myLossCode = resourceBundle.getString("report.mbr.heading.loss.code");
		if (!( myLossCode == null || myLossCode.equalsIgnoreCase("") )) {
			reportHeadingLossCode = myLossCode;
		}
		
		String myAssignedStation = resourceBundle.getString("report.mbr.heading.assigned.station");
		if (!( myAssignedStation == null || myAssignedStation.equalsIgnoreCase("") )) {
			reportHeadingAssignedStation = myAssignedStation;
		}
		int columnchecked=Integer.valueOf(parameters.get("checknum").toString());
		int fontsize;
		if(columnchecked==12){
			fontsize=10;
		} else if (columnchecked<12&& columnchecked>=8){
			fontsize=11;
		} else {
			fontsize=12;
		}
		Style detailStyle = new Style("titleStyle");
		detailStyle.setFont(new Font(fontsize, Font._FONT_ARIAL, false));
		

//		Style headerStyle = new Style("header");
//		headerStyle.setBackgroundColor(Color.LIGHT_GRAY);
//		headerStyle.setVerticalAlign(VerticalAlign.MIDDLE);
//		headerStyle.setTransparency(Transparency.OPAQUE);
//		headerStyle.setFont(new Font(fontsize, Font._FONT_TIMES_NEW_ROMAN, true));

		//sort out the report styles
		//ex : 00-Groupby Assigned Station, Summary; 01-Groupby Assigned Station, Detail;
		//     10-Groupby Fault Station, Summary; 11-Groupby Fault Station, Detail
        switch (reportStyle) {
            case 00:  
            	drb = drb.addColumn(reportHeadingSortByAssignedStation, "stationcode", String.class.getName(), 24);
            	drb = drb.addColumn(reportHeadingSortByType, "typedesc", String.class.getName(), 30);
        		drb = drb.addColumn(reportHeadingLastNameFirstName,"customer_name",String.class.getName(),54);
        		drb = drb.addColumn(reportHeadingIncidentNumber,"incident_ID",String.class.getName(),40);
        		drb = drb.addColumn(reportHeadingDate,"rcreatedate",String.class.getName(),30);
        		drb = drb.addColumn(reportHeadingTime,"rcreatetime",String.class.getName(),30);
        		drb = drb.addColumn(reportHeadingItinerary,"itinerary",String.class.getName(),30);
        		drb = drb.addColumn(reportHeadingFinal,"final_destination",String.class.getName(),30);
        		drb = drb.addColumn(reportHeadingStatus,"statusdesc",String.class.getName(),30);
        		drb = drb.addColumn(reportHeadingFaultStation,"faultstationcode",String.class.getName(),14);
        		drb = drb.addColumn(reportHeadingLossCode,"loss_code",Integer.class.getName(),24);
        
    			break;
            case 01: 
            	if(parameters.get("showall")!=null || parameters.get("showassigncity")!=null) drb = drb.addColumn(reportHeadingSortByAssignedStation, "stationcode", String.class.getName(), 24,detailStyle);
            	if(parameters.get("showall")!=null || parameters.get("showtype")!=null) drb = drb.addColumn(reportHeadingSortByType, "typedesc", String.class.getName(), 30,detailStyle);
            	if(parameters.get("showall")!=null || parameters.get("showlastname")!=null || parameters.get("showfirstname")!=null) drb = drb.addColumn(reportHeadingLastNameFirstName,"customer_name",String.class.getName(),54,detailStyle);
            	if(parameters.get("showall")!=null || parameters.get("showreportid")!=null) drb = drb.addColumn(reportHeadingIncidentNumber,"incident_ID",String.class.getName(),40,detailStyle);
            	if(parameters.get("showall")!=null || parameters.get("showdate")!=null) drb = drb.addColumn(reportHeadingDate,"rcreatedate",String.class.getName(),30,detailStyle);
            	if(parameters.get("showall")!=null || parameters.get("showtime")!=null) drb = drb.addColumn(reportHeadingTime,"rcreatetime",String.class.getName(),30,detailStyle);
            	if(parameters.get("showall")!=null || parameters.get("showitinerary")!=null) drb = drb.addColumn(reportHeadingItinerary,"itinerary",String.class.getName(),30,detailStyle);
            	if(parameters.get("showall")!=null || parameters.get("showdestination")!=null) drb = drb.addColumn(reportHeadingFinal,"final_destination",String.class.getName(),30,detailStyle);
            	if(parameters.get("showall")!=null || parameters.get("showstatus")!=null) drb = drb.addColumn(reportHeadingStatus,"statusdesc",String.class.getName(),30,detailStyle);
            	if(parameters.get("showall")!=null || parameters.get("showfaultcity")!=null) drb = drb.addColumn(reportHeadingFaultStation,"faultstationcode",String.class.getName(),14,detailStyle);
            	if(parameters.get("showall")!=null || parameters.get("showlosscode")!=null) drb = drb.addColumn(reportHeadingLossCode,"loss_code",Integer.class.getName(),24,detailStyle);
        		
    			break;
            case 10: 
            	drb = drb.addColumn(reportHeadingSortByFaultStation, "faultstationcode", String.class.getName(), 14);
            	drb = drb.addColumn(reportHeadingSortByType, "typedesc", String.class.getName(), 30);
        		drb = drb.addColumn(reportHeadingLastNameFirstName,"customer_name",String.class.getName(),54);
        		drb = drb.addColumn(reportHeadingIncidentNumber,"incident_ID",String.class.getName(),40);
        		drb = drb.addColumn(reportHeadingDate,"rcreatedate",String.class.getName(),30);
        		drb = drb.addColumn(reportHeadingTime,"rcreatetime",String.class.getName(),30);
        		drb = drb.addColumn(reportHeadingItinerary,"itinerary",String.class.getName(),30);
        		drb = drb.addColumn(reportHeadingFinal,"final_destination",String.class.getName(),30);
        		drb = drb.addColumn(reportHeadingStatus,"statusdesc",String.class.getName(),30);
        		drb = drb.addColumn(reportHeadingLossCode,"loss_code",Integer.class.getName(),24);
        
    			break;
            case 11: 
            	if(parameters.get("showall")!=null || parameters.get("showfaultcity")!=null) drb = drb.addColumn(reportHeadingSortByFaultStation, "faultstationcode", String.class.getName(), 14,detailStyle);
            	if(parameters.get("showall")!=null || parameters.get("showtype")!=null) drb = drb.addColumn(reportHeadingSortByType, "typedesc", String.class.getName(), 30,detailStyle);
            	if(parameters.get("showall")!=null || parameters.get("showlastname")!=null) drb = drb.addColumn(reportHeadingLastNameFirstName,"customer_name",String.class.getName(),54,detailStyle);
            	if(parameters.get("showall")!=null || parameters.get("showreportid")!=null) drb = drb.addColumn(reportHeadingIncidentNumber,"incident_ID",String.class.getName(),40,detailStyle);
            	if(parameters.get("showall")!=null || parameters.get("showdate")!=null) drb = drb.addColumn(reportHeadingDate,"rcreatedate",String.class.getName(),30,detailStyle);
            	if(parameters.get("showall")!=null || parameters.get("showtime")!=null) drb = drb.addColumn(reportHeadingTime,"rcreatetime",String.class.getName(),30,detailStyle);
            	if(parameters.get("showall")!=null || parameters.get("showitinerary")!=null) drb = drb.addColumn(reportHeadingItinerary,"itinerary",String.class.getName(),30,detailStyle);
            	if(parameters.get("showall")!=null || parameters.get("showdestination")!=null) drb = drb.addColumn(reportHeadingFinal,"final_destination",String.class.getName(),30,detailStyle);
            	if(parameters.get("showall")!=null || parameters.get("showstatus")!=null) drb = drb.addColumn(reportHeadingStatus,"statusdesc",String.class.getName(),30,detailStyle);
            	if(parameters.get("showall")!=null || parameters.get("showlosscode")!=null) drb = drb.addColumn(reportHeadingLossCode,"loss_code",Integer.class.getName(),24,detailStyle);
        		
    			break;
            default: 	    			
            	drb = drb.addColumn(reportHeadingSortByAssignedStation, "stationcode", String.class.getName(), 14);
	        	drb = drb.addColumn(reportHeadingSortByType, "typedesc", String.class.getName(), 30);
	    		drb = drb.addColumn(reportHeadingLastNameFirstName,"customer_name",String.class.getName(),54);
	    		drb = drb.addColumn(reportHeadingIncidentNumber,"incident_ID",String.class.getName(),40);
	    		drb = drb.addColumn(reportHeadingDate,"rcreatedate",String.class.getName(),30);
	    		drb = drb.addColumn(reportHeadingTime,"rcreatetime",String.class.getName(),30);
	    		drb = drb.addColumn(reportHeadingItinerary,"itinerary",String.class.getName(),30);
	    		drb = drb.addColumn(reportHeadingFinal,"final_destination",String.class.getName(),30);
	    		drb = drb.addColumn(reportHeadingStatus,"statusdesc",String.class.getName(),30);
	    		drb = drb.addColumn(reportHeadingFaultStation,"faultstationcode",String.class.getName(),14);
	    		drb = drb.addColumn(reportHeadingLossCode,"loss_code",Integer.class.getName(),24);
	    		
    			break;
        }
		//get the general information of the report, such as Report Title
		String myReportTitle = "MBR Report";
		if (parameters.get("title") != null) {
			myReportTitle = (String) parameters.get("title");
		}
		
		String mySubtitle = "This report was generated on " + new Date();
		if (parameters.get("sdate") != null && parameters.get("edate") != null) {
			mySubtitle += "   Date: from " + parameters.get("sdate") + " to " + parameters.get("edate");
		}
		if (parameters.get("mbr_subtitle_status") != null) {
			myStatus = (String) parameters.get("mbr_subtitle_status");
			if (myStatus.equals("")) {
				mySubtitle += "   Status: All";
			} else {
				mySubtitle += "   Status: " + parameters.get("mbr_subtitle_status");
			}
		}
		if (parameters.get("agent_username") != null) {
			mySubtitle += "   Agent: " + parameters.get("agent_username");
		} else {
			mySubtitle += "   Agent: All Agents";
		}	
		
		
        drb.setReportLocale(reportLocale);
//		drb.setSubtitleStyle(subtitleStyle)
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
			AbstractColumn columnSortByAssignedStation = reportColumns.get("columnSortByAssignedStation");
			
			AbstractColumn columnSortByFaultStation = reportColumns.get("columnSortByFaultStation");
			AbstractColumn columnSortByType = reportColumns.get("columnSortByType");
			
			AbstractColumn columnLastNameFirstName = reportColumns.get("columnLastNameFirstName");
			AbstractColumn columnClaimNumber = reportColumns.get("columnClaimNumber");
			AbstractColumn columnDate = reportColumns.get("columnDate");
			AbstractColumn columnTime = reportColumns.get("columnTime");
			AbstractColumn columnItinerary = reportColumns.get("columnItinerary");
			AbstractColumn columnFinal = reportColumns.get("columnFinal");
			AbstractColumn columnStatus = reportColumns.get("columnStatus");
			AbstractColumn columnFaultStationCode = reportColumns.get("columnFaultStationCode");
			AbstractColumn columnLossCode = reportColumns.get("columnLossCode");
			
			DJGroup g1 = null;
			GroupBuilder gb1 = null;
			AbstractColumn groupByColumn = columnSortByFaultStation;  
			String invisibleFieldName = "faultstationcode";
			
			//sort out the report styles
			//by fault station or last assigned station
			
			if ((reportStyle == 00 || reportStyle == 01) && columnSortByAssignedStation!=null) {
				groupByColumn = columnSortByAssignedStation;	//default
				invisibleFieldName = "stationcode";
			}
			
			//test 1: this way is not working quite right
			//invisible field for subtotal
			drb.addField(invisibleFieldName, String.class.getName());
			CustomExpression mySubTotalExpression = getSubTotalCustomExpression();
			
			if(groupByColumn!=null) drb.addColumn(groupByColumn);
			if(columnSortByType!=null) drb.addColumn(columnSortByType);
			if(columnLastNameFirstName!=null) drb.addColumn(columnLastNameFirstName);
			if(columnClaimNumber!=null) drb.addColumn(columnClaimNumber);
			if(columnDate!=null) drb.addColumn(columnDate);
			if(columnTime!=null) drb.addColumn(columnTime);
			if(columnItinerary!=null) drb.addColumn(columnItinerary);
			if(columnFinal!=null) drb.addColumn(columnFinal);
			if(columnStatus!=null) drb.addColumn(columnStatus);
			if(columnFaultStationCode!=null) drb.addColumn(columnFaultStationCode);
			if(columnLossCode!=null) drb.addColumn(columnLossCode);
	        
	        gb1 = new GroupBuilder();
	        gb1 = gb1.setCriteriaColumn((PropertyColumn) groupByColumn)
	        .addVariable("myGroupLabel", groupByColumn, DJCalculation.FIRST);
	        if(drb.getColumn(2)!=null) gb1.addFooterVariable(drb.getColumn(2), mySubTotalExpression,groupVariablesLegend);
	        if(drb.getColumn(3)!=null) gb1.addFooterVariable(drb.getColumn(3), DJCalculation.COUNT, groupVariables);   //sub-totals
			
	        g1 = gb1.setGroupLayout(GroupLayout.DEFAULT)
	    			.setFooterVariablesHeight(new Integer(20))
	    			.setFooterHeight(new Integer(50))
	    			.setHeaderVariablesHeight(new Integer(35))
	    			.build();
			drb.addGroup(g1);
			
			//deal with summary and detail options
			if (reportStyle == 00 || reportStyle == 10) {
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
				String subTotal = myGroupLabel + " Total :   ";
				return subTotal;
			}

			public String getClassName() {
				return String.class.getName();
			}

		};
	}
}
