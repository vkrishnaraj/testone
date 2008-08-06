/*
 * Created on Aug 10, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.reporting;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.util.MessageResources;

import com.bagnet.nettracer.tracing.bmo.ReportBMO;
import com.bagnet.nettracer.tracing.db.BDO_Passenger;
import com.bagnet.nettracer.tracing.db.DeliverCompany;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.dto.BDO_Receipt_DTO;
import com.bagnet.nettracer.tracing.forms.BDOForm;
import com.bagnet.nettracer.tracing.forms.SearchIncidentForm;
import com.bagnet.nettracer.tracing.utils.BDOUtils;

/**
 * @author Matt
 * 
 */
public class SearchIncidentReport {
	private static Logger logger = Logger.getLogger(BDOReceipt.class);

	/**
	 *  
	 */
	public SearchIncidentReport() {
		super();
	}

	public static String createReport(SearchIncidentForm theform, ServletContext sc, HttpServletRequest request, int outputtype, String language) {
		try {

			HttpSession session = request.getSession();
			MessageResources messages = MessageResources.getMessageResources("com.bagnet.nettracer.tracing.resources.ApplicationResources");

			Map parameters = new HashMap();

			ResourceBundle myResources = ResourceBundle.getBundle("com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(language));

			parameters.put("REPORT_RESOURCE_BUNDLE", myResources);
			
			ArrayList al = new ArrayList();

			ReportBMO rbmo = new ReportBMO(request);
			return rbmo.getReportFile(al, parameters, ReportingConstants.SEARCH_INCIDENT_RPT_NAME, sc.getRealPath("/"), outputtype);

		} catch (Exception e) {
			logger.error("unable to create claim prorate report: " + e);
			e.printStackTrace();
			return null;
		}
	}
}