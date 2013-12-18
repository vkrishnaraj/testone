package com.bagnet.nettracer.tracing.bmo;

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
import com.bagnet.nettracer.tracing.utils.TracerUtils;

/** @Author Sean Fine
 * Helper class to ReportBMO to create a dynamic report for Incoming Incidents
 */

public class PaymentApprovalReportBMO {
	private static Logger logger = Logger.getLogger(PaymentApprovalReportBMO.class);
	public static String newline = System.getProperty("line.separator");
	
	public static String getReportFileDj(JRDataSource ds, Map<String,Object> parameters,
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
	
	@SuppressWarnings("static-access")
	private static DynamicReport buildReport(Map<String,Object> parameters) throws Exception {
		
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
		String reportHeadingName= "NAME";
		String reportHeadingAddress= "Address";
		String reportHeadingApt= "Apt # or Unit #";
		String reportHeadingCity= "City";
		String reportHeadingState= "ST";
		String reportHeadingZip= "ZIP";
		String reportHeadingExpenseCheckAmt = "Amount Requested";
		String reportHeadingAirline = "Southwest or AirTran";
		String reportHeadingPNR = "PNR #";
		String reportHeadingIncidentID = "Incident #";
		String reportHeadingReason = "REASON";
		String reportHeadingSpecialist= "Specialist";
		String reportHeadingApprover= "Approver";
		String reportHeadingExpenseDraft = "Expense Draft";
		String reportHeadingLastPrinted = "Last Printed";
		
		String myNameFromResource = resourceBundle.getString("report.disburse.name");
		if (!( myNameFromResource == null || myNameFromResource.equalsIgnoreCase("") )) {
			reportHeadingName = myNameFromResource;
		}
		
		String myAddressFromResource = resourceBundle.getString("report.disburse.address");
		if (!( myAddressFromResource == null || myAddressFromResource.equalsIgnoreCase("") )) {
			reportHeadingAddress = myAddressFromResource;
		}
		
		String myAptFromResource = resourceBundle.getString("report.disburse.apt");
		if (!( myAptFromResource == null || myAptFromResource.equalsIgnoreCase("") )) {
			reportHeadingApt = myAptFromResource;
		}
		
		String myCityFromResource = resourceBundle.getString("report.disburse.city");
		if (!( myCityFromResource == null || myCityFromResource.equalsIgnoreCase("") )) {
			reportHeadingCity = myCityFromResource;
		}
		
		String myStateFromResource = resourceBundle.getString("report.disburse.state");
		if (!( myStateFromResource == null || myStateFromResource.equalsIgnoreCase("") )) {
			reportHeadingState = myStateFromResource;
		}
		String myZipFromResource = resourceBundle.getString("report.disburse.zip");
		if (!( myZipFromResource == null || myZipFromResource.equalsIgnoreCase("") )) {
			reportHeadingZip= myZipFromResource;
		}

		String myExpenseCheckAmtFromResource = resourceBundle.getString("report.disburse.expense.check.amt");
		if (!( myExpenseCheckAmtFromResource == null || myExpenseCheckAmtFromResource.equalsIgnoreCase("") )) {
			reportHeadingExpenseCheckAmt = myExpenseCheckAmtFromResource;
		}		

		String myAirlineFromResource = resourceBundle.getString("report.disburse.airline");
		if (!( myAirlineFromResource == null || myAirlineFromResource.equalsIgnoreCase("") )) {
			reportHeadingAirline = myAirlineFromResource;
		}

		String myPNRFromResource = resourceBundle.getString("report.disburse.pnr");
		if (!( myPNRFromResource == null || myPNRFromResource.equalsIgnoreCase("") )) {
			reportHeadingPNR = myPNRFromResource;
		}
		
		String myIncidentIdFromResource = resourceBundle.getString("report.disburse.incident.id");
		if (!( myIncidentIdFromResource == null || myIncidentIdFromResource.equalsIgnoreCase("") )) {
			reportHeadingIncidentID = myIncidentIdFromResource;
		}

		String myReasonFromResource = resourceBundle.getString("report.disburse.reason");
		if (!( myReasonFromResource == null || myReasonFromResource.equalsIgnoreCase("") )) {
			reportHeadingReason = myReasonFromResource;
		}

		String mySpecialistFromResource = resourceBundle.getString("report.disburse.specialist");
		if (!( mySpecialistFromResource == null || mySpecialistFromResource.equalsIgnoreCase("") )) {
			reportHeadingSpecialist= mySpecialistFromResource;
		}

		String myApproverFromResource = resourceBundle.getString("report.disburse.approver");
		if (!( myApproverFromResource == null || myApproverFromResource.equalsIgnoreCase("") )) {
			reportHeadingApprover= myApproverFromResource;
		}		

		String myExpenseDraftFromResource = resourceBundle.getString("report.disburse.expense.draft");
		if (!( myExpenseDraftFromResource == null || myExpenseDraftFromResource.equalsIgnoreCase("") )) {
			reportHeadingExpenseDraft = myExpenseDraftFromResource;
		}
		
		String myLastPrintedFromResource = resourceBundle.getString("report.disburse.last.printed");
		if (!( myLastPrintedFromResource == null || myLastPrintedFromResource.equalsIgnoreCase("") )) {
			reportHeadingLastPrinted = myLastPrintedFromResource;
		}	
		
		
		drb = drb.addColumn(reportHeadingName, "name", String.class.getName(), 100);
		drb = drb.addColumn(reportHeadingAddress, "address", String.class.getName(), 100);
		drb = drb.addColumn(reportHeadingApt, "aptnum", String.class.getName(), 60);
		drb = drb.addColumn(reportHeadingCity, "city", String.class.getName(), 100);
		drb = drb.addColumn(reportHeadingState, "state", String.class.getName(), 60);
		drb = drb.addColumn(reportHeadingZip, "zip", String.class.getName(), 80);
		drb = drb.addColumn(reportHeadingExpenseCheckAmt,"expensecheckamt",Double.class.getName(),100);
		drb = drb.addColumn(reportHeadingAirline,"airline",String.class.getName(),80);
		drb = drb.addColumn(reportHeadingPNR,"pnr",String.class.getName(),50);
    	drb = drb.addColumn(reportHeadingIncidentID, "incidentId", String.class.getName(), 100);
    	drb = drb.addColumn(reportHeadingReason, "reason", String.class.getName(), 60);
    	drb = drb.addColumn(reportHeadingSpecialist,"specialist",String.class.getName(),60);
    	drb = drb.addColumn(reportHeadingApprover,"agent",String.class.getName(),60);
    	drb = drb.addColumn(reportHeadingExpenseDraft,"expensedraft",String.class.getName(),100);
		drb = drb.addColumn(reportHeadingLastPrinted,"lastPrinted",String.class.getName(),60);
        
		//get the general information of the report, such as Report Title
		String myReportTitle = "Payment Approval Report";
		if (parameters.get("title") != null) {
			myReportTitle = (String) parameters.get("title");
		}
		
		String mySubtitle = "This report was generated on " + new Date();
		
        drb.setReportLocale(reportLocale);
		
		drb.setSubtitle(mySubtitle);
		
		Page page = new Page();
		drb.setPageSizeAndOrientation(page.Page_A4_Landscape());
		//drb.setPrintBackgroundOnOddRows(true);
		drb.setPrintBackgroundOnOddRows(false);
		drb.setIgnorePagination(true);
		drb.setDetailHeight(30);
		
		DynamicReport dr = drb.setTitle(myReportTitle).setUseFullPageWidth(false).build();
		
		return dr;
	}
}
