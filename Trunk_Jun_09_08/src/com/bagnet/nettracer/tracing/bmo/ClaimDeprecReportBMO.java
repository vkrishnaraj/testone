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

//helper class to ReportBMO for Claim Depreciation Reports

public class ClaimDeprecReportBMO {
	private static Logger logger = Logger.getLogger(OHDReportBMO.class);
	
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

		//getting report header title from reource bundle
		String reportHeadingClaimId = "Claim ID";
		String reportHeadingDateCalculate = "Date Calculated";
		String reportHeadingClaimType = "Claim Type";
		String reportHeadingTotalWeight = "Total Weight";

		String reportHeadingItemDescription = "Item Description";
		String reportHeadingAmountClaimed = "Amount Claimed";
		String reportHeadingDatePurchased = "Date Purchased";
		String reportHeadingCategory = "Category";
		String reportHeadingProof = "Proof of Ownership";
		String reportHeadingNotCoveredCoc = "Not Covered by Coc";
		String reportHeadingCalcValue = "Calculated Value";
		String reportHeadingClaimValue= "Claim Value";

		String myClaimFromResource = resourceBundle.getString("deprec.report.claim");
		if (!( myClaimFromResource == null || myClaimFromResource.equalsIgnoreCase("") )) {
			reportHeadingClaimId = myClaimFromResource;
		}
		
		String myItemDescriptionFromResource = resourceBundle.getString("deprec.report.item.description");
		if (!( myItemDescriptionFromResource == null || myItemDescriptionFromResource.equalsIgnoreCase("") )) {
			reportHeadingItemDescription = myItemDescriptionFromResource;
		}

		String myAmountClaimedFromResource = resourceBundle.getString("deprec.report.amount.claimed");
		if (!( myAmountClaimedFromResource == null || myAmountClaimedFromResource.equalsIgnoreCase("") )) {
			reportHeadingAmountClaimed = myAmountClaimedFromResource;
		}

		String myDatePurchasedFromResource = resourceBundle.getString("deprec.report.date.purchased");
		if (!( myDatePurchasedFromResource == null || myDatePurchasedFromResource.equalsIgnoreCase("") )) {
			reportHeadingDatePurchased = myDatePurchasedFromResource;
		}
		
		String myCategoryFromResource = resourceBundle.getString("deprec.report.category");
		if (!( myCategoryFromResource == null || myCategoryFromResource.equalsIgnoreCase("") )) {
			reportHeadingCategory = myCategoryFromResource;
		}

		String myProofOwnershipFromResource = resourceBundle.getString("deprec.report.proof.ownership");
		if (!( myProofOwnershipFromResource == null || myProofOwnershipFromResource.equalsIgnoreCase("") )) {
			reportHeadingProof = myProofOwnershipFromResource;
		}

		String myCalcValueFromResource = resourceBundle.getString("deprec.report.calculated.value");
		if (!( myCalcValueFromResource == null || myCalcValueFromResource.equalsIgnoreCase("") )) {
			reportHeadingCalcValue = myCalcValueFromResource;
		}

		String myCocCoveredFromResource = resourceBundle.getString("deprec.report.coc.covered");
		if (!( myCocCoveredFromResource == null || myCocCoveredFromResource.equalsIgnoreCase("") )) {
			reportHeadingNotCoveredCoc = myCocCoveredFromResource;
		}

		String myClaimValueFromResource = resourceBundle.getString("deprec.report.claim.value");
		if (!( myClaimValueFromResource == null || myClaimValueFromResource.equalsIgnoreCase("") )) {
			reportHeadingClaimValue = myClaimValueFromResource;
		}
		
		String myDateCalcFromResource = resourceBundle.getString("deprec.report.date.calculation");
		if (!( myDateCalcFromResource == null || myDateCalcFromResource.equalsIgnoreCase("") )) {
			reportHeadingDateCalculate = myDateCalcFromResource;
		}

		String myTotalWeightFromResource = resourceBundle.getString("deprec.report.total.weight");
		if (!( myTotalWeightFromResource == null || myTotalWeightFromResource.equalsIgnoreCase("") )) {
			reportHeadingTotalWeight = myTotalWeightFromResource;
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
		subtitleStyle.setFont(new Font(9, Font._FONT_TIMES_NEW_ROMAN, false));
		subtitleStyle.setHorizontalAlign(HorizontalAlign.RIGHT);
		
		Style oddRowStyle = new Style();
		oddRowStyle.setBorder(Border.NO_BORDER);
		oddRowStyle.setBackgroundColor(Color.LIGHT_GRAY);
		oddRowStyle.setTransparency(Transparency.OPAQUE);

		DynamicReportBuilder drb = new DynamicReportBuilder();
		drb.setPageSizeAndOrientation(Page.Page_A4_Landscape());
		
		//get the general information of the report, such as Report Title
		String myReportTitle = "Depreciation Summary";
		if (parameters.get("title") != null) {
			myReportTitle = (String) parameters.get("title");
		}
		
		String mySubtitle = "This report was generated on " + new Date();
		
		if(parameters.get("claimid")!=null){
			mySubtitle += "   Claim #: " + parameters.get("claimid");
		}

		if(parameters.get("dateCalculated")!=null){
			mySubtitle += "   Date Calculated: " + parameters.get("dateCalculated");
		}
		
		if(parameters.get("claimType")!=null){
			mySubtitle += "   Claim Type: " + parameters.get("claimType");
		}
		
		if(parameters.get("totalWeight")!=null){
			mySubtitle += "   Total Weight: " + parameters.get("totalWeight");
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
		

		AbstractColumn columnClaimId = ColumnBuilder.getNew()
				.setColumnProperty("claimID", String.class.getName())
				.setTitle(reportHeadingClaimId).setWidth(new Integer(30)).setStyle(
				detailStyle).setHeaderStyle(headerStyle).build();
		
		AbstractColumn columnItemDescription = ColumnBuilder.getNew()
				.setColumnProperty("itemDesc", String.class.getName())
				.setTitle(reportHeadingItemDescription).setWidth(new Integer(30)).setStyle(
				detailStyle).setHeaderStyle(headerStyle).build();
		
		AbstractColumn columnAmountClaimed = ColumnBuilder.getNew()
				.setColumnProperty("amountClaimed", Double.class.getName()).setTitle(
						reportHeadingAmountClaimed).setWidth(new Integer(40))
				.setStyle(detailStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnDatePurchase = ColumnBuilder.getNew()
				.setColumnProperty("datePurchase", String.class.getName()).setTitle(
						reportHeadingDatePurchased).setWidth(new Integer(40)).setStyle(
				detailStyle).setHeaderStyle(headerStyle).build();
		
		AbstractColumn columnCategory = ColumnBuilder.getNew()
				.setColumnProperty("category", String.class.getName()).setTitle(
						reportHeadingCategory).setWidth(new Integer(40)).setStyle(detailStyle)
				.setHeaderStyle(headerStyle).build();
		
		AbstractColumn columnProof = ColumnBuilder.getNew()
				.setColumnProperty("receiptProvided", String.class.getName()).setTitle(
						reportHeadingProof).setWidth(new Integer(30)).setStyle(detailStyle)
				.setHeaderStyle(headerStyle).build();	
		
		AbstractColumn columnNotCoveredCoc = ColumnBuilder.getNew()
				.setColumnProperty("notCoveredCoc", String.class.getName()).setTitle(
						reportHeadingNotCoveredCoc).setWidth(new Integer(40)).setStyle(detailStyle)
				.setHeaderStyle(headerStyle).build();
		
		AbstractColumn columnCalcValue = ColumnBuilder.getNew()
				.setColumnProperty("calculatedValue", String.class.getName())
				.setTitle(reportHeadingCalcValue).setWidth(new Integer(40)).setStyle(
				detailStyle).setHeaderStyle(headerStyle).build();

		AbstractColumn columnClaimValue = ColumnBuilder.getNew()
				.setColumnProperty("claimValue", Double.class.getName()).setTitle(
						reportHeadingClaimValue).setWidth(new Integer(30)).setStyle(detailStyle)
				.setHeaderStyle(headerStyle).build();
		
		// grand total count
//		drb.addGlobalFooterVariable(columnClaimValue, DJCalculation.SUM, headerVariables);
//		drb.setGlobalFooterVariableHeight(new Integer(45));
		
		
		Map<String, AbstractColumn> reportColumns = new HashMap<String, AbstractColumn>();
		reportColumns.put("columnClaimId", columnClaimId);
		reportColumns.put("columnItemDescription", columnItemDescription);
		reportColumns.put("columnAmountClaimed", columnAmountClaimed);
		reportColumns.put("columnDatePurchase", columnDatePurchase);
		reportColumns.put("columnCategory", columnCategory);
		reportColumns.put("columnProof", columnProof);
		reportColumns.put("columnNotCoveredCoc", columnNotCoveredCoc);
		reportColumns.put("columnCalcValue", columnCalcValue);
		reportColumns.put("columnClaimValue", columnClaimValue);

		drb = reportStyleSelector(drb, reportColumns, reportStyle);

		drb.setUseFullPageWidth(true);
		Style atStyle2 = new StyleBuilder(true).setFont(new Font(9, Font._FONT_TIMES_NEW_ROMAN, false, true, false)).setTextColor(Color.DARK_GRAY).build();

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
		String reportHeadingClaimId = "Claim #:";
		String reportHeadingDateCalculate = "Date Calculated";
		String reportHeadingClaimType = "Claim Type";
		String reportHeadingTotalWeight = "Total Weight";
		
		String reportHeadingItemDescription = "Item Description";
		String reportHeadingAmountClaimed = "Amount Claimed";
		String reportHeadingDatePurchased = "Date Purchased";
		String reportHeadingCategory = "Category";
		String reportHeadingProof = "Proof of Ownership";
		String reportHeadingNotCoveredCoc = "Not Covered by Coc";
		String reportHeadingCalcValue = "Calculated Value";
		String reportHeadingClaimValue= "Claim Value";
		
		String myItemDescriptionFromResource = resourceBundle.getString("deprec.report.item.description");
		if (!( myItemDescriptionFromResource == null || myItemDescriptionFromResource.equalsIgnoreCase("") )) {
			reportHeadingItemDescription = myItemDescriptionFromResource;
		}

		String myAmountClaimedFromResource = resourceBundle.getString("deprec.report.amount.claimed");
		if (!( myAmountClaimedFromResource == null || myAmountClaimedFromResource.equalsIgnoreCase("") )) {
			reportHeadingAmountClaimed = myAmountClaimedFromResource;
		}

		String myDatePurchasedFromResource = resourceBundle.getString("deprec.report.date.purchased");
		if (!( myDatePurchasedFromResource == null || myDatePurchasedFromResource.equalsIgnoreCase("") )) {
			reportHeadingDatePurchased = myDatePurchasedFromResource;
		}
		
		String myCategoryFromResource = resourceBundle.getString("deprec.report.category");
		if (!( myCategoryFromResource == null || myCategoryFromResource.equalsIgnoreCase("") )) {
			reportHeadingCategory = myCategoryFromResource;
		}

		String myProofOwnershipFromResource = resourceBundle.getString("deprec.report.proof.ownership");
		if (!( myProofOwnershipFromResource == null || myProofOwnershipFromResource.equalsIgnoreCase("") )) {
			reportHeadingProof = myProofOwnershipFromResource;
		}

		String myCalcValueFromResource = resourceBundle.getString("deprec.report.calculated.value");
		if (!( myCalcValueFromResource == null || myCalcValueFromResource.equalsIgnoreCase("") )) {
			reportHeadingCalcValue = myCalcValueFromResource;
		}

		String myCocCoveredFromResource = resourceBundle.getString("deprec.report.coc.covered");
		if (!( myCocCoveredFromResource == null || myCocCoveredFromResource.equalsIgnoreCase("") )) {
			reportHeadingNotCoveredCoc = myCocCoveredFromResource;
		}

		String myClaimValueFromResource = resourceBundle.getString("deprec.report.claim.value");
		if (!( myClaimValueFromResource == null || myClaimValueFromResource.equalsIgnoreCase("") )) {
			reportHeadingClaimValue = myClaimValueFromResource;
		}
		
		String myDateCalcFromResource = resourceBundle.getString("deprec.report.date.calculation");
		if (!( myDateCalcFromResource == null || myDateCalcFromResource.equalsIgnoreCase("") )) {
			reportHeadingDateCalculate = myDateCalcFromResource;
		}

		String myTotalWeightFromResource = resourceBundle.getString("deprec.report.total.weight");
		if (!( myTotalWeightFromResource == null || myTotalWeightFromResource.equalsIgnoreCase("") )) {
			reportHeadingTotalWeight = myTotalWeightFromResource;
		}
		
		//sort out the report styles
		//     00-Summary; 10-Detail
        switch (reportStyle) {
            default: 	    			
            	drb = drb.addColumn(reportHeadingItemDescription, "itemDesc", String.class.getName(), 24);
	    		drb = drb.addColumn(reportHeadingAmountClaimed,"amountClaimed",Double.class.getName(),54);
	    		drb = drb.addColumn(reportHeadingDatePurchased,"datePurchase",String.class.getName(),40);
	    		drb = drb.addColumn(reportHeadingCategory,"category",String.class.getName(),26);
	    		drb = drb.addColumn(reportHeadingProof,"receiptProvided",String.class.getName(),20);
	    		drb = drb.addColumn(reportHeadingNotCoveredCoc,"notCoveredCoc",String.class.getName(),30);
	    		drb = drb.addColumn(reportHeadingCalcValue,"calculatedValue",String.class.getName(),30);
	    		drb = drb.addColumn(reportHeadingClaimValue,"claimValue",Double.class.getName(),30);
	    		
    			break;
        }
        
		//get the general information of the report, such as Report Title
		String myReportTitle = "Depreciation Summary";
		if (parameters.get("title") != null) {
			myReportTitle = (String) parameters.get("title");
		}
		
		String mySubtitle = "This report was generated on " + new Date();
		
		if(parameters.get("claimid")!=null){
			mySubtitle += "   Claim #: " + parameters.get("claimid");
		}

		if(parameters.get("dateCalculated")!=null){
			mySubtitle += "   Date Calculated: " + parameters.get("dateCalculated");
		}
		
		if(parameters.get("claimType")!=null){
			mySubtitle += "   Claim Type: " + parameters.get("claimType");
		}
		
		if(parameters.get("totalWeight")!=null){
			mySubtitle += "   Total Weight: " + parameters.get("totalWeight");
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
			AbstractColumn columnClaimId = reportColumns.get("columnClaimId");
			AbstractColumn columnItemDescription = reportColumns.get("columnItemDescription");
			AbstractColumn columnAmountClaimed = reportColumns.get("columnAmountClaimed");
			AbstractColumn columnDatePurchase = reportColumns.get("columnDatePurchase");
			AbstractColumn columnCategory = reportColumns.get("columnCategory");
			AbstractColumn columnProof = reportColumns.get("columnProof");
			AbstractColumn columnNotCoveredCoc = reportColumns.get("columnNotCoveredCoc");
			AbstractColumn columnCalcValue = reportColumns.get("columnCalcValue");
			AbstractColumn columnClaimValue = reportColumns.get("columnClaimValue");
			
			DJGroup g1 = null;
			GroupBuilder gb1 = null;
			AbstractColumn groupByColumn = columnClaimId;  
			
			//test 1: this way is not working quite right
			//invisible field for subtotal
			CustomExpression mySubTotalExpression = getSubTotalCustomExpression();

			drb.addColumn(groupByColumn);
			drb.addColumn(columnItemDescription);
			drb.addColumn(columnAmountClaimed);
			drb.addColumn(columnDatePurchase);
			drb.addColumn(columnCategory);
			drb.addColumn(columnProof);
			drb.addColumn(columnNotCoveredCoc);
			drb.addColumn(columnCalcValue);
			drb.addColumn(columnClaimValue);
			
	        gb1 = new GroupBuilder();
	        g1 = gb1.setCriteriaColumn((PropertyColumn) groupByColumn)
	        .addVariable("myGroupStationCode", groupByColumn, DJCalculation.FIRST)
	        .addFooterVariable(columnItemDescription, mySubTotalExpression,groupVariablesLegend)
	        .addFooterVariable(columnAmountClaimed, DJCalculation.SUM, groupVariables) 
	        .addFooterVariable(columnClaimValue, DJCalculation.SUM, groupVariables)   //station-totals
			.setGroupLayout(GroupLayout.DEFAULT)
			.setFooterVariablesHeight(new Integer(20))
			.setFooterHeight(new Integer(50))
			.setHeaderVariablesHeight(new Integer(35))
			.build();

//	        drb.addField("stationcode", String.class.getName());
			drb.addGroup(g1);
			
			//deal with summary and detail options
			if (reportStyle == 00) {
				drb.setShowDetailBand(false);
			}
		
		}
		
		return drb;
	}
//	
//	//
	private static CustomExpression getSubTotalCustomExpression() {
		return new CustomExpression() {

			public Object evaluate(Map fields, Map variables, Map parameters) {
				String subTotal = "Totals:";
				return subTotal;
			}

			public String getClassName() {
				return String.class.getName();
			}

		};
	}

}
