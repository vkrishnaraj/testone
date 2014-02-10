/*
 * Created on Aug 10, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.reporting;

import java.io.File;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.util.MessageResources;

import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.ReportBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.forms.IncidentForm;

/**
 * @author Matt
 * 
 */
public class MissingReceipt {
	private static Logger logger = Logger.getLogger(MissingReceipt.class);

	/**
	 *  
	 */
	public MissingReceipt() {
		super();
	}

	public static String createReport(IncidentForm theform, ServletContext sc, HttpServletRequest request, int outputtype, String language) {
		try {

			HttpSession session = request.getSession();
			MessageResources messages = MessageResources.getMessageResources("com.bagnet.nettracer.tracing.resources.ApplicationResources");
			Agent user = (Agent) session.getAttribute("user");
			
			Map<String, Object> parameters = LostDelayReceipt.getParameters(theform, messages, language, user, "missing.receipt.title", TracingConstants.MISSING_ARTICLES);
			
			
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
			

			File itemInfo = new File(sc.getRealPath("/")+"reports/item.jpg");
			if(itemInfo.exists()){
				parameters.put("itemInfo", itemInfo.getAbsolutePath());
			}
			
			File bullet= new File(sc.getRealPath("/")+"reports/bullet.jpg");
			if(bullet.exists()){
				parameters.put("bullet", bullet.getAbsolutePath());
			}
			
			File WebLogo= new File(sc.getRealPath("/")+"reports/webLogo.jpg");
			if(WebLogo.exists()){
				parameters.put("webLogo", WebLogo.getAbsolutePath());
			}
			
			ReportBMO rbmo = new ReportBMO(request);
			
			IncidentBMO ibmo = new IncidentBMO();
			theform.setPrintedreceipt(ibmo.incrementPrintedReceipt(theform.getIncident_ID()));
			
			String filename = rbmo.getReportFileName(TracingConstants.MISSING_ARTICLES, language, theform);
			
			return rbmo.getReportFile(theform.getClaimchecklist(), parameters, filename, sc.getRealPath("/"), outputtype);

		} catch (Exception e) {
			logger.error("Unable to create missing report: " + e);
			e.printStackTrace();
			return null;
		}
	}
}