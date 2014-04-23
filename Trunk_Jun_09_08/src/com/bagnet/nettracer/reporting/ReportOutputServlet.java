/*
 * Created on Aug 12, 2004
 *
 * Administrator
 */
package com.bagnet.nettracer.reporting;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.forms.BDOForm;
import com.bagnet.nettracer.tracing.forms.ClaimDeprecCalcForm;
import com.bagnet.nettracer.tracing.forms.ClaimForm;
import com.bagnet.nettracer.tracing.forms.ClaimProrateForm;
import com.bagnet.nettracer.tracing.forms.ClaimSettlementForm;
import com.bagnet.nettracer.tracing.forms.ExpensePayoutForm;
import com.bagnet.nettracer.tracing.forms.IncidentForm;
import com.bagnet.nettracer.tracing.utils.FileShareUtils;

/**
 * @author Administrator
 * 
 * create date - Aug 12, 2004
 */
public class ReportOutputServlet extends HttpServlet {
	private static final long serialVersionUID = -6091206482068047512L;
	private static Logger logger = Logger.getLogger(ReportOutputServlet.class);

	/**
	 * Handles the HTTP <code>GET</code> method.
	 * 
	 * @param request
	 *          servlet request
	 * @param response
	 *          servlet response
	 * @throws ServletException
	 *           on servlet error
	 * @throws IOException
	 *           on io error
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 * 
	 * @param request
	 *          servlet request
	 * @param response
	 *          servlet response
	 * @throws ServletException
	 *           on servlet error
	 * @throws IOException
	 *           on io error
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		processRequest(request, response);
	}

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 * 
	 * @param request
	 *          servlet request
	 * @param response
	 *          servlet response
	 * @throws ServletException
	 *           on servlet error
	 * @throws IOException
	 *           on io error
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		
		String language = TracingConstants.DEFAULT_LOCALE;
		int outputtype = TracingConstants.REPORT_OUTPUT_UNDECLARED;
		
		try {
			
			if (request.getParameter("language") != null && !request.getParameter("language").equals("")) {
				language = (String) request.getParameter("language");
			}
			
			if (request.getParameter("outputtype") != null) {
				try {
					outputtype = Integer.parseInt((String) request.getParameter("outputtype"));
				} catch (Exception e) {
					// If an error is encountered we are going to ignore it.
				}
			}

			HttpSession session = request.getSession(false);
			Agent user = (Agent) session.getAttribute("user");
			if (user == null) {
				response.sendRedirect("logoff.do");
				return;
			}


			ServletContext sc = getServletContext();

			// file already generated
			String file = null;
			File iFile = null;
			
			ClaimForm cform = (ClaimForm) session.getAttribute("claimForm");

			if ((file = request.getParameter("reportfile")) != null) {
				iFile = FileShareUtils.getFile(file, sc);
				if (iFile == null) response.sendRedirect("statReport.do?error=print");
			} else if ((request.getParameter(TracingConstants.COMMAND_PRINT)) != null) {// print receipt or claim forms
				int type = Integer.parseInt(request.getParameter(TracingConstants.COMMAND_PRINT));
				switch (type) {
					case ReportingConstants.CLAIM_PAYOUT_RPT:
						iFile = FileShareUtils.getFile(ClaimPayoutRpt.createReport(cform.getClaim().getNtIncident(), sc, request), sc);
						break;
					case ReportingConstants.CLAIM_PRORATE_RPT:
						ClaimProrateForm cpform = (ClaimProrateForm) session.getAttribute("claimProrateForm");
						iFile = FileShareUtils.getFile(ClaimProrateRpt.createReport(cpform, sc, request), sc);
						break;
					case ReportingConstants.LOST_RECEIPT_RPT:
						IncidentForm theform = (IncidentForm) session.getAttribute("incidentForm");
						iFile = FileShareUtils.getFile(LostDelayReceipt.createReport(theform, sc, request, outputtype, language), sc);
						break;
					case ReportingConstants.DAMAGE_RECEPIT_RPT:
						IncidentForm theform2 = (IncidentForm) session.getAttribute("incidentForm");
						iFile = FileShareUtils.getFile(DamageReceipt.createReport(theform2, sc, request, outputtype, language), sc);
						break;
					case ReportingConstants.MISSING_RECEPIT_RPT:
						IncidentForm theform3 = (IncidentForm) session.getAttribute("incidentForm");
						iFile = FileShareUtils.getFile(MissingReceipt.createReport(theform3, sc, request, outputtype, language), sc);
						break;
					case ReportingConstants.BDO_RECEIPT_RPT:
						BDOForm bdoform = (BDOForm) session.getAttribute("BDOForm");
						iFile = FileShareUtils.getFile(BDOReceipt.createReport(bdoform, sc, request, outputtype, language), sc);
						break;
					case ReportingConstants.PPLC_RPT:
						ClaimSettlementForm theform4 = (ClaimSettlementForm) session.getAttribute("claimSettlementForm");
						iFile = FileShareUtils.getFile(PPLCReport.createReport(theform4, sc, request, language, outputtype), sc);
						break;
					case ReportingConstants.DEPREC_SUMMARY:
						ClaimDeprecCalcForm deprecForm = (ClaimDeprecCalcForm) session.getAttribute("claimDeprecCalcForm");
						iFile = FileShareUtils.getFile(DeprecSummary.createReport(deprecForm, sc, request, outputtype,language),sc);
						break;
					case ReportingConstants.CRAP_SHEET:
						iFile = FileShareUtils.getFile(CRAPReport.createReport(cform, sc, request, outputtype,language),sc);
						break;
					case ReportingConstants.EXPENSE_LUV_VOUCHER:
						ExpensePayoutForm epform = (ExpensePayoutForm) request.getSession().getAttribute("expensepayoutform");
						if (epform.getDistributemethod().equals(TracingConstants.DISTR_EMAIL) || epform.getDistributemethod().equals(TracingConstants.DISTR_MAIL)) { 
							iFile = FileShareUtils.getFile(LUVReceipt_Mail.createReport(epform, sc, request, outputtype,language),sc);
						} else if (epform.getDistributemethod().equals(TracingConstants.DISTR_IMME)) {
							iFile = FileShareUtils.getFile(LUVReceipt_Imme.createReport(epform, sc, request, outputtype,language),sc);
						}
						break;
					default:
						break;
				}
			}

			if (iFile == null || iFile.length() == 0) {
				if (cform.getClaim().getId() > 0) {
					response.sendRedirect("claim_resolution.do?claimId=" + cform.getClaim().getId() + "&error=nodata");
				} else {
					response.sendRedirect("claim_resolution.do?error=nodata");
				}
				return;
			}
			
			if (outputtype == TracingConstants.REPORT_OUTPUT_PDF) response
					.setContentType("application/pdf");
			else if (outputtype == TracingConstants.REPORT_OUTPUT_HTML) {
				response.setContentType("text/html");
				response.addHeader("Pragma", "No-cache");
				response.addHeader("Cache-Control", "no-cache");
				response.addDateHeader("Expires", -1);				
			} else if (outputtype == TracingConstants.REPORT_OUTPUT_XLS) response
					.setContentType("application/vnd.ms-excel");
			else if (outputtype == TracingConstants.REPORT_OUTPUT_CSV) response
					.setContentType("text/plain");
			else if (outputtype == TracingConstants.REPORT_OUTPUT_XML) response
					.setContentType("text/xml");
			
			response.setContentLength((int) iFile.length());

			int current = -1;	
			OutputStream ouputStream = response.getOutputStream();
			FileInputStream is = new FileInputStream(iFile);
			while ((current = is.read()) > -1) {
				ouputStream.write(current);
			}
		  
		    IOUtils.closeQuietly(is);
		    
			ouputStream.flush();
			IOUtils.closeQuietly(ouputStream);
		} catch (Exception e) {
			logger.error("print report error: " + e.getLocalizedMessage(), e);
			response.sendRedirect("claim_payout.do?error=print");
		} 
	}
}