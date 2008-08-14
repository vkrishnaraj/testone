/*
 * Created on Aug 12, 2004
 *
 * Administrator
 */
package com.bagnet.nettracer.reporting;

import java.io.File;
import java.io.FileInputStream;
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
import com.bagnet.nettracer.tracing.forms.BDOForm;
import com.bagnet.nettracer.tracing.forms.ClaimForm;
import com.bagnet.nettracer.tracing.forms.ClaimProrateForm;
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
		int outputtype = TracingConstants.REPORT_OUTPUT_PDF;
		
		try {
			
			if (request.getParameter("language") != null) {
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
			
			byte[] bytes = null;
			ServletContext sc = getServletContext();

			// file already generated
			String file = null;
			

			if ((file = request.getParameter("reportfile")) != null) {
				bytes = readBytes(file, sc, response);
				if (bytes == null || bytes.length == 0) response.sendRedirect("statReport.do?error=print");
			}

			// print receipt or claim forms
			if ((request.getParameter("print")) != null) {
				int type = Integer.parseInt(request.getParameter("print"));
				switch (type) {
					case ReportingConstants.CLAIM_PAYOUT_RPT:
						ClaimForm cform = (ClaimForm) session.getAttribute("claimForm");
						bytes = readBytes(ClaimPayoutRpt.createReport(cform.getClaim_ID(), sc, request), sc,
								response);
						break;
					case ReportingConstants.CLAIM_PRORATE_RPT:
						ClaimProrateForm cpform = (ClaimProrateForm) session.getAttribute("claimProrateForm");
						bytes = readBytes(ClaimProrateRpt.createReport(cpform, sc, request), sc, response);
						break;
					case ReportingConstants.LOST_RECEIPT_RPT:
						IncidentForm theform = (IncidentForm) session.getAttribute("incidentForm");
						
						bytes = readBytes(LostDelayReceipt.createReport(theform, sc, request, outputtype,
								language), sc, response);
						break;
					case ReportingConstants.DAMAGE_RECEPIT_RPT:
						IncidentForm theform2 = (IncidentForm) session.getAttribute("incidentForm");
						
						bytes = readBytes(DamageReceipt.createReport(theform2, sc, request, outputtype,
								language), sc, response);
						break;
					case ReportingConstants.MISSING_RECEPIT_RPT:
						IncidentForm theform3 = (IncidentForm) session.getAttribute("incidentForm");
						
						bytes = readBytes(MissingReceipt.createReport(theform3, sc, request, outputtype,
								language), sc, response);
						break;
					case ReportingConstants.BDO_RECEIPT_RPT:
						BDOForm bdoform = (BDOForm) session.getAttribute("BDOForm");
						
						bytes = readBytes(BDOReceipt.createReport(bdoform, sc, request, outputtype,
								language), sc, response);
						break;
						
					default:
						break;
				}

				if (bytes == null || bytes.length == 0) {
					response.sendRedirect("claim_resolution.do?error=nodata");
					return;
				}
			}

			if (bytes != null && bytes.length > 0) {
				if (outputtype == TracingConstants.REPORT_OUTPUT_PDF) response
						.setContentType("application/pdf");
				else if (outputtype == TracingConstants.REPORT_OUTPUT_HTML) response
						.setContentType("text/html");
				else if (outputtype == TracingConstants.REPORT_OUTPUT_XLS) response
						.setContentType("application/vnd.ms-excel");
				else if (outputtype == TracingConstants.REPORT_OUTPUT_CSV) response
						.setContentType("text/plain");
				else if (outputtype == TracingConstants.REPORT_OUTPUT_XML) response
						.setContentType("text/xml");

				response.setContentLength(bytes.length);
				OutputStream ouputStream = response.getOutputStream();
				ouputStream.write(bytes, 0, bytes.length);
				ouputStream.flush();
				ouputStream.close();
			}

		} catch (Exception e) {
			logger.error("print report error:" + e);
			e.printStackTrace();
			response.sendRedirect("claim_payout.do?error=print");
		}
	}

	private byte[] readBytes(String filepath, ServletContext sc, HttpServletResponse response)
			throws Exception {
		filepath = sc.getRealPath("/") + ReportingConstants.REPORT_TMP_PATH + filepath;
		File f = new File(filepath);
		if (!f.exists()) {
			logger.error("no report file exists");
			return null;
		}
		InputStream is = new FileInputStream(f);
		long length = f.length();

		byte[] bytes = new byte[(int) length];

		/*
		 * is.read( data, 0, size ); // read into byte array is.close();
		 */

		// Read in the bytes
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}

		// Ensure all the bytes have been read in
		if (offset < bytes.length) {
			logger.error("error reading file into bytes");
			return null;
		}
		return bytes;
	}
}