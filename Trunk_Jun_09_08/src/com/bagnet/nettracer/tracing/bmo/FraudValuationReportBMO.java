package com.bagnet.nettracer.tracing.bmo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JExcelApiExporterParameter;

import org.apache.log4j.Logger;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Page;

import com.bagnet.nettracer.other.JRGovernedFileVirtualizer;
import com.bagnet.nettracer.other.JRMaxFilesException;
import com.bagnet.nettracer.reporting.ReportingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;

//helper class to ReportBMO

public class FraudValuationReportBMO {
	private static Logger logger = Logger.getLogger(FraudValuationReportBMO.class);
	
	public static String getReportFileDj(JRDataSource ds, Map parameters, String reportname, String rootpath, int outputtype, HttpServletRequest request, ReportBMO rbmo) throws Exception {
		/** look for compiled reports, if can't find it, compile xml report * */

		// added virtualizer to reduce memory
		String outfile = reportname + "_" + (new SimpleDateFormat("MMddyyyyhhmmss").format(TracerDateTime.getGMTDate()));
		
		JRGovernedFileVirtualizer virtualizer = new JRGovernedFileVirtualizer(100, rootpath + "/reports/tmp", 501);
		virtualizer.setReadOnly(false);
		
		parameters.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
		parameters.put(JRParameter.IS_IGNORE_PAGINATION, true);
		
		DynamicReport dr = null;
		JasperPrint jasperPrint;
		try {
			
			dr = buildExcelReport(parameters);
			
			JasperReport jasperReport = DynamicJasperHelper.generateJasperReport(dr, new ClassicLayoutManager(), parameters);
			if (ds != null) {
				jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);
			} else {
				jasperPrint = JasperFillManager.fillReport(jasperReport, parameters);
			}
			
			virtualizer.setReadOnly(true);
	
			outfile += ".xls";				
	
			String outputpath = rootpath + ReportingConstants.REPORT_TMP_PATH + outfile;
			JExcelApiExporter alternativeExporter = new JExcelApiExporter();
			Map<JRExporterParameter, Object> excelParameters = new HashMap<JRExporterParameter, Object>();
				
			excelParameters.put(JExcelApiExporterParameter.JASPER_PRINT,  jasperPrint);
			excelParameters.put(JExcelApiExporterParameter.OUTPUT_FILE_NAME, outputpath);
			excelParameters.put(JExcelApiExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			excelParameters.put(JExcelApiExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			excelParameters.put(JExcelApiExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
			excelParameters.put(JExcelApiExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
			excelParameters.put(JExcelApiExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			excelParameters.put(JExcelApiExporterParameter.IS_FONT_SIZE_FIX_ENABLED, Boolean.TRUE); 
			excelParameters.put(JExcelApiExporterParameter.IS_COLLAPSE_ROW_SPAN, Boolean.TRUE);
				
			alternativeExporter.setParameters(excelParameters);
			alternativeExporter.exportReport();
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
	
	private static DynamicReport buildExcelReport(Map parameters) throws Exception {
				
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
		String reportHeadingClaimID = "Claim ID";
		String reportHeadingIncidentID = "Incident ID";
		String reportHeadingClaimDate = "Claim Date";
		String reportHeadingStatus = "Status";
		String reportHeadingAmountClaimed = "Amount Claimed";
		String reportHeadingAmountClaimedCurrency = "Amount Claimed Currency";
		String reportHeadingAmountPaidCurrency = "Amount Paid Currency";
		String reportHeadingAmountPaid = "Amount Paid";
		String reportHeadingMatches = "# of Claims Matched";

		String myClaimID = resourceBundle.getString("report.fraudValue.heading.claimId");
		if (!( myClaimID == null || myClaimID.equalsIgnoreCase("") )) {
			reportHeadingClaimID = myClaimID;
		}
		String myIncidentID = resourceBundle.getString("report.fraudValue.heading.incidentId");
		if (!( myIncidentID == null || myIncidentID.equalsIgnoreCase("") )) {
			reportHeadingIncidentID = myIncidentID;
		}
		String myClaimDate = resourceBundle.getString("report.fraudValue.heading.claimDate");
		if (!( myClaimDate == null || myClaimDate.equalsIgnoreCase("") )) {
			reportHeadingClaimDate = myClaimDate;
		}
		String myStatus = resourceBundle.getString("report.fraudValue.heading.status");
		if (!( myStatus == null || myStatus.equalsIgnoreCase("") )) {
			reportHeadingStatus = myStatus;
		}
		String myAmountClaimed = resourceBundle.getString("report.fraudValue.heading.amountClaimed");
		if (!( myAmountClaimed == null || myAmountClaimed.equalsIgnoreCase("") )) {
			reportHeadingAmountClaimed = myAmountClaimed;
		}
		String myAmountClaimedCurr = resourceBundle.getString("report.fraudValue.heading.amountClaimedCurr");
		if (!( myAmountClaimedCurr == null || myAmountClaimedCurr.equalsIgnoreCase("") )) {
			reportHeadingAmountClaimedCurrency = myAmountClaimedCurr;
		}
		String myAmountPaid = resourceBundle.getString("report.fraudValue.heading.amountPaid");
		if (!( myAmountPaid == null || myAmountPaid.equalsIgnoreCase("") )) {
			reportHeadingAmountPaid = myAmountPaid;
		}
		String myAmountPaidCurr = resourceBundle.getString("report.fraudValue.heading.amountPaidCurr");
		if (!( myAmountPaidCurr == null || myAmountPaidCurr.equalsIgnoreCase("") )) {
			reportHeadingAmountPaidCurrency = myAmountPaidCurr;
		}
		String myMatches = resourceBundle.getString("report.fraudValue.heading.matches");
		if (!( myMatches == null || myMatches.equalsIgnoreCase("") )) {
			reportHeadingMatches = myMatches;
		}
		
		drb = drb.addColumn(reportHeadingClaimID, "claimID", String.class.getName(), 30);
    	drb = drb.addColumn(reportHeadingIncidentID, "incidentID", String.class.getName(), 54);
    	drb = drb.addColumn(reportHeadingClaimDate, "reportClaimDate", String.class.getName(), 54);
		drb = drb.addColumn(reportHeadingStatus,"status",String.class.getName(),30);
		drb = drb.addColumn(reportHeadingAmountClaimed,"amountClaimed",String.class.getName(),24);
		drb = drb.addColumn(reportHeadingAmountClaimedCurrency,"amountClaimedCurrency",String.class.getName(),24);
		drb = drb.addColumn(reportHeadingAmountPaid,"amountPaid",String.class.getName(),24);
		drb = drb.addColumn(reportHeadingAmountPaidCurrency,"amountPaidCurrency",String.class.getName(),24);
		drb = drb.addColumn(reportHeadingMatches,"matches",Integer.class.getName(),30);
        
		//get the general information of the report, such as Report Title
		String myReportTitle = "Fraud Valuation Report";
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
		drb.setPrintBackgroundOnOddRows(false);
		drb.setIgnorePagination(true);
		DynamicReport dr = drb.setTitle(myReportTitle).setUseFullPageWidth(true).build();

		return dr;
	}

}
