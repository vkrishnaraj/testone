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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.util.MessageResources;

import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.ReportBMO;
import com.bagnet.nettracer.tracing.db.Incident_Claimcheck;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.forms.IncidentForm;

/**
 * @author Matt
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
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

			Map parameters = new HashMap();
			String airline = theform.getStationcreated().getCompany().getCompanydesc();
			parameters.put("title", messages.getMessage(new Locale(language), "damage.receipt.title"));

			ResourceBundle myResources = ResourceBundle.getBundle("com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(language));

			parameters.put("REPORT_RESOURCE_BUNDLE", myResources);

			parameters.put("station_city", (theform.getStationassigned().getCity() != null ? theform.getStationassigned().getCity().toUpperCase() : ""));
			parameters.put("station_phone", (theform.getStationassigned().getPhone() != null ? theform.getStationassigned().getPhone() : ""));

			parameters.put("airline", theform.getStationassigned().getCompany().getCompanydesc());
			parameters.put("airline_addr", theform.getStationassigned().getCompany().getAddress1() + "\n"
					+ theform.getStationassigned().getCompany().getAddress2() + "\n" + theform.getStationassigned().getCompany().getCity() + ", "
					+ theform.getStationassigned().getCompany().getState_ID() + " " + theform.getStationassigned().getCompany().getZip());
			parameters.put("airline_phone", theform.getStationassigned().getCompany().getPhone());
			parameters.put("airline_email", theform.getStationassigned().getCompany().getEmail_address());

			File logo = new File(sc.getRealPath("/") + "reports/logo.jpg");
			if (logo.exists()) {
				parameters.put("logo", logo.getAbsolutePath());
			}

			Passenger pa = (Passenger) theform.getPassenger(0);

			parameters.put("createdate", theform.getDispcreatetime());
			parameters.put("pass_name", (pa.getLastname() != null ? (pa.getLastname() + ", ") : "") + (pa.getFirstname() != null ? pa.getFirstname() : ""));
			parameters.put("file_reference", theform.getIncident_ID());

			String phno = pa.getAddress(0).getHomephone();
			if (phno == null || phno.length() == 0)
				phno = pa.getAddress(0).getMobile();

			parameters.put("phone", phno);
			parameters.put("address1", pa.getAddress(0).getAddress1());
			parameters.put("address2", pa.getAddress(0).getAddress2());
			parameters.put("city_st_zip", (pa.getAddress(0).getCity() != null ? (pa.getAddress(0).getCity() + ", ") : "")
					+ (pa.getAddress(0).getState_ID() != null ? (pa.getAddress(0).getState_ID() + " ") : "")
					+ (pa.getAddress(0).getZip() != null ? pa.getAddress(0).getZip() : ""));

			parameters.put("station", theform.getStationcreated().getStationcode());

			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < theform.getClaimchecklist().size(); i++) {
				if (((Incident_Claimcheck) theform.getClaimchecklist().get(i)).getClaimchecknum() != null) {
					sb.append(((Incident_Claimcheck) theform.getClaimchecklist().get(i)).getClaimchecknum().trim());
					sb.append(",");
				}
			}
			if (sb.length() > 0)
				parameters.put("claim_check_num", sb.toString().substring(0, sb.toString().length() - 1));
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