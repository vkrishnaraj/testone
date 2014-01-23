/*
 * Created on Aug 10, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.reporting;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;

import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.bmo.ReportBMO;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.forms.ExpensePayoutForm;

/**
 * @author Matt

 */
public class LUVReceipt_Imme {
	private static Logger logger = Logger.getLogger(LUVReceipt_Imme.class);

	/**
	 *  
	 */
	public LUVReceipt_Imme() {
		super();
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static HashMap getParameters(ExpensePayoutForm theform, Agent user, String titleProperty) {
		HashMap parameters = new HashMap();
		
		String incident_id = theform.getIncident_ID();
		parameters.put("amount", String.format("%.2f", theform.getVoucheramt()));
		parameters.put("incident_num", incident_id);
		parameters.put("createdate1", new SimpleDateFormat("MMM dd, yyyy").format(theform.getCreatedate()));
		parameters.put("createdate", new SimpleDateFormat("MM/dd/yyyy").format(theform.getCreatedate()));
		
		Date today = new Date();
		int offsetDays = NumberUtils.toInt(PropertyBMO.getValue(PropertyBMO.ISSUE_VOUCHER_EXPIRATION_OFFSET_DAYS), 365); //default to 1 year = 365 days
		Date expdate = DateUtils.addDays(today, offsetDays);
	    parameters.put("expdate", new SimpleDateFormat("MM/dd/yyyy").format(expdate));
		
		Incident inc = IncidentBMO.getIncidentByID(incident_id, null);
		if (inc.getPassengers() != null && inc.getPassengers().size() > 0) {
			for (Passenger pa : inc.getPassengers()) {
				pa.setPassenger_ID(0);
				parameters.put("pass_name", (pa.getLastname() != null ? (pa.getLastname() + ", ") : "") + (pa.getFirstname() != null ? pa.getFirstname() : ""));
				parameters.put("address1", (pa.getAddress(0).getAddress1() != null ? pa.getAddress(0).getAddress1() : ""));
				parameters.put("city_st_zip", (pa.getAddress(0).getCity() != null ? (pa.getAddress(0).getCity() + ", ") : "")
						+ (pa.getAddress(0).getState_ID() != null ? (pa.getAddress(0).getState_ID() + " ") : (pa.getAddress(0).getProvince() != null ? (pa.getAddress(0).getProvince() + " ") : ""))
						+ (pa.getAddress(0).getZip() != null ? pa.getAddress(0).getZip() : ""));
				parameters.put("country", "UNITED STATES");
				break;
			}
		}
		parameters.put("order_num", (theform.getOrdernum() != null ? theform.getOrdernum() : ""));
		parameters.put("voucher_num", (theform.getSlvnum() != null ? theform.getSlvnum() : ""));
		parameters.put("sec_code", (theform.getSeccode() != null ? theform.getSeccode() : ""));
		return parameters;
	}

	@SuppressWarnings("unchecked")
	public static String createReport(ExpensePayoutForm theform, ServletContext sc, HttpServletRequest request, int outputtype, String language) {
		try {

			HttpSession session = request.getSession();
			Agent user = (Agent) session.getAttribute("user");

			@SuppressWarnings("rawtypes")
			Map parameters = getParameters(theform, user, "luv voucher Imme receipt");

			ResourceBundle myResources = ResourceBundle.getBundle("com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(language));
			parameters.put("REPORT_RESOURCE_BUNDLE", myResources);
			
			@SuppressWarnings("unused")
			ReportBMO rbmo = new ReportBMO(request);
			
			/* Required to populate the report */
			List<String> t = new ArrayList<String>();
			t.add("");
			ReportBMO bmo = new ReportBMO(request);
			return bmo.getReportFile(t, parameters, "VoucherReceipt_Imme", sc.getRealPath("/"), outputtype);

		} catch (Exception e) {
			logger.error("unable to create LUV Voucher Imme report: " + e);
			e.printStackTrace();
			return null;
		}
	}
}