/*
 * Created on Aug 12, 2004
 *
 * Administrator
 */
package com.bagnet.nettracer.reporting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Claim;
import com.bagnet.nettracer.tracing.forms.BDOForm;
import com.bagnet.nettracer.tracing.forms.ClaimForm;
import com.bagnet.nettracer.tracing.forms.ClaimProrateForm;
import com.bagnet.nettracer.tracing.forms.ClaimSettlementForm;
import com.bagnet.nettracer.tracing.forms.IncidentForm;

/**
 * @author Administrator
 * 
 * create date - Aug 12, 2004
 */
public class ReportOutputServlet extends HttpServlet {
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
				iFile = getFile(file, sc);
				if (iFile == null) response.sendRedirect("statReport.do?error=print");
			} else {
	
				// print receipt or claim forms
				if ((request.getParameter("print")) != null) {
					int type = Integer.parseInt(request.getParameter("print"));
					switch (type) {
						case ReportingConstants.CLAIM_PAYOUT_RPT:
							Claim claim = cform.getClaim();
							if (claim.getNtIncident() == null || claim.getNtIncident().getExpenselist() == null || claim.getNtIncident().getExpenselist().isEmpty()) {
								iFile = null;
							} else {
								iFile = getFile(ClaimPayoutRpt.createReport(cform.getClaim().getId(), sc, request), sc);
							}
							break;
						case ReportingConstants.CLAIM_PRORATE_RPT:
							ClaimProrateForm cpform = (ClaimProrateForm) session.getAttribute("claimProrateForm");
							iFile = getFile(ClaimProrateRpt.createReport(cpform, sc, request), sc);
							break;
						case ReportingConstants.LOST_RECEIPT_RPT:
							IncidentForm theform = (IncidentForm) session.getAttribute("incidentForm");
							
							iFile = getFile(LostDelayReceipt.createReport(theform, sc, request, outputtype,
									language), sc);
							break;
						case ReportingConstants.DAMAGE_RECEPIT_RPT:
							IncidentForm theform2 = (IncidentForm) session.getAttribute("incidentForm");
							
							iFile = getFile(DamageReceipt.createReport(theform2, sc, request, outputtype,
									language), sc);
							break;
						case ReportingConstants.MISSING_RECEPIT_RPT:
							IncidentForm theform3 = (IncidentForm) session.getAttribute("incidentForm");
							
							iFile = getFile(MissingReceipt.createReport(theform3, sc, request, outputtype,
									language), sc);
							break;
						case ReportingConstants.BDO_RECEIPT_RPT:
							BDOForm bdoform = (BDOForm) session.getAttribute("BDOForm");
							
							iFile = getFile(BDOReceipt.createReport(bdoform, sc, request, outputtype,
									language), sc);
							break;
						case ReportingConstants.PPLC_RPT:
							ClaimSettlementForm theform4 = (ClaimSettlementForm) session.getAttribute("claimSettlementForm");
							
							iFile = getFile(PPLCReport.createReport(theform4, sc, request, language, outputtype), sc);
							break;
						default:
							break;
					}
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

			int length = 0;
			

			
			response.setContentLength((int) iFile.length());
			
			FileInputStream is = new FileInputStream(iFile);
			OutputStream ouputStream = response.getOutputStream();
			
			int current = -1;
			
		  while ((current = is.read()) > -1) {
		  	ouputStream.write(current);
		  }

			ouputStream.flush();
			ouputStream.close();

		} catch (Exception e) {
			logger.error("print report error:" + e);
			e.printStackTrace();
			response.sendRedirect("claim_payout.do?error=print");
		}
	}


	private File getFile(String filepath, ServletContext sc) {
		filepath = sc.getRealPath("/") + ReportingConstants.REPORT_TMP_PATH
				+ filepath;
		File f = new File(filepath);

		if (!f.exists()) {
			logger.error("no report file exists");
			return null;
		} else {
			return f;
		}
	}


}