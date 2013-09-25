/*
 * Created on Aug 10, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.reporting;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts.util.MessageResources;

import com.bagnet.nettracer.tracing.bmo.ReportBMO;
import com.bagnet.nettracer.tracing.forms.ClaimDeprecCalcForm;

/**
 * @author Matt
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class DeprecSummary {
	private static Logger logger = Logger.getLogger(DeprecSummary.class);

	/**
	 *  
	 */
	public DeprecSummary() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static String createReport(ClaimDeprecCalcForm theform, ServletContext sc, HttpServletRequest request, int outputtype, String language) {
		try {
			MessageResources messages = MessageResources.getMessageResources("com.bagnet.nettracer.tracing.resources.ApplicationResources");
			// If a BDO_ID is explicitly provided, populate the form with it.
			Map parameters = new HashMap();
			if (theform.getClaim_id()!=0 && request.getParameter("claimId")==null) {
				parameters.put("claimId",theform.getClaim_id());
			}
			ResourceBundle myResources = ResourceBundle.getBundle("com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(language));
			parameters.put("REPORT_RESOURCE_BUNDLE", myResources);
			parameters.put("claimId",theform.getClaim_id());
					
			ReportBMO rbmo = new ReportBMO(request);
			return rbmo.getReportFile(theform.getClaimDeprec().getItemlist(), parameters, "depreciation_summary", sc.getRealPath("/"), outputtype);

		} catch (Exception e) {
			logger.error("unable to create claim prorate report: " + e);
			e.printStackTrace();
			return null;
		}
	}
}