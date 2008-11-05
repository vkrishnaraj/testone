/*
 * Created on Aug 10, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.reporting;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TimeZone;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.util.MessageResources;

import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.ReportBMO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident_Claimcheck;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.forms.IncidentForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;

/**
 * @author Matt
 */
public class DamageReceipt {
	private static Logger logger = Logger.getLogger(DamageReceipt.class);

	/**
	 *  
	 */
	public DamageReceipt() {
		super();
	}

	public static String createReport(IncidentForm theform, ServletContext sc, HttpServletRequest request, int outputtype, String language) {
		try {

			HttpSession session = request.getSession();
			MessageResources messages = MessageResources.getMessageResources("com.bagnet.nettracer.tracing.resources.ApplicationResources");
			Agent user = (Agent) session.getAttribute("user");
			
			//Map parameters = new HashMap();
			
			Map parameters = LostDelayReceipt.getParameters(theform, messages, language, user, "damage.receipt.title");
			
			ResourceBundle myResources = ResourceBundle.getBundle("com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(language));

			parameters.put("REPORT_RESOURCE_BUNDLE", myResources);

			File logo = new File(sc.getRealPath("/") + "reports/logo.jpg");
			if (logo.exists()) {
				parameters.put("logo", logo.getAbsolutePath());
			}
			
			File powered = new File(sc.getRealPath("/") + "deployment/main/images/nettracer/poweredby_net_tracer.jpg");
			if (powered.exists()) {
				parameters.put("powered", powered.getAbsolutePath());
			}

			ReportBMO rbmo = new ReportBMO(request);
			
			
			IncidentBMO ibmo = new IncidentBMO();
			ibmo.incrementPrintedReceipt(theform.getIncident_ID());
			
			return rbmo.getReportFile(theform.getClaimchecklist(), parameters, "DamageReceipt", sc.getRealPath("/"), outputtype);

		} catch (Exception e) {
			logger.error("Unable to create damage report: " + e);
			e.printStackTrace();
			return null;
		}
	}
}