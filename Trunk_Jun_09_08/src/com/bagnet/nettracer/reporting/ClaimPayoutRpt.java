/*
 * Created on Aug 10, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.reporting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import aero.nettracer.fs.model.Claim;

import com.bagnet.nettracer.tracing.bmo.ClaimBMO;
import com.bagnet.nettracer.tracing.bmo.ReportBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;

/**
 * @author Matt
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ClaimPayoutRpt {
	private static Logger logger = Logger.getLogger(ClaimPayoutRpt.class);

	/**
	 *  
	 */
	public ClaimPayoutRpt() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static String createReport(long claim_ID, ServletContext sc, HttpServletRequest request) {
		try {
			ClaimBMO cBMO = new ClaimBMO();
			Claim claim = cBMO.findClaimByID(claim_ID);
			if (claim == null || claim.getIncident() == null || claim.getNtIncident().getExpenses() == null) {
				return null;
			}
			List expenselist = new ArrayList(claim.getNtIncident().getExpenses());

			Map parameters = new HashMap();
			parameters.put("title", ReportingConstants.CLAIM_PAYOUT_TITLE);

			ReportBMO rbmo = new ReportBMO(request);
			return rbmo.getReportFile(expenselist, parameters, "ClaimPayout", sc.getRealPath("/"),
					TracingConstants.REPORT_OUTPUT_UNDECLARED);

			//JasperPrint jasperPrint = JasperManager.fillReport(jasperReport,
			// parameters, ds);
			//return JasperManager.printReportToPdf(jasperPrint);

		} catch (Exception e) {
			logger.error("unable to create claim payout report: " + e);
			e.printStackTrace();
			return null;
		}
	}

}