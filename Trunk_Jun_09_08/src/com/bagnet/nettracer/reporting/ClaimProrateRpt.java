/*
 * Created on Aug 10, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.reporting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.tracing.bmo.ReportBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Incident_Claimcheck;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.forms.ClaimProrateForm;

/**
 * @author Matt
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class ClaimProrateRpt {
	private static Logger logger = Logger.getLogger(ClaimProrateRpt.class);

	/**
	 *  
	 */
	public ClaimProrateRpt() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static String createReport(ClaimProrateForm cpform, ServletContext sc,
			HttpServletRequest request) {
		try {
			HttpSession session = request.getSession();
			List itinerarylist = cpform.getItinerarylist();
			Map parameters = new HashMap();
			parameters.put("title", ReportingConstants.PRORATE_NOTICE_TITLE);
			parameters.put("airline", cpform.getCompanycode_ID());
			parameters.put("createdate", cpform.getDisplaydate());
			parameters.put("pass_name", cpform.getPassname());
			parameters.put("file_reference", cpform.getFile_reference());
			parameters.put("claim_type", cpform.getClaimtype());
			parameters.put("ticket_number", cpform.getTicketnumber());
			StringBuffer sb = new StringBuffer();
			Item item = null;
			if (cpform.getClaimtype_ID() == TracingConstants.LOST_DELAY) {
				// get the claimcheck from the incident_claimcheck

				for (int i = 0; i < cpform.getClaimchecknumlist().size(); i++) {
					sb
							.append(((Incident_Claimcheck) cpform.getClaimchecknumlist().get(i))
									.getClaimchecknum());
					sb.append(", ");
				}

			} else {
				for (int i = 0; i < cpform.getClaimchecknumlist().size(); i++) {
					item = (Item) cpform.getClaimchecknumlist().get(i);
					sb.append(item.getClaimchecknum());
					sb.append(", ");
				}

			}

			if (sb.toString().length() > 0) parameters.put("claim_check_num", sb.toString().substring(0,
					sb.toString().length() - 2));

			parameters.put("pir_attached", Integer.toString(cpform.getPir_attached()));
			parameters.put("claim_attached", Integer.toString(cpform.getClaim_attached()));
			parameters.put("payment_attached", Integer.toString(cpform.getConfirmpayment_attached()));
			parameters.put("all_prorate", Integer.toString(cpform.getAll_prorate()));
			parameters.put("all_prorate_reason", cpform.getAll_prorate_reason());
			parameters.put("remit", Integer.toString(cpform.getRemit()));
			parameters.put("remit_amount", new Double(cpform.getRemit_amount()));
			parameters.put("currency_ID", cpform.getCurrency_ID());
			parameters.put("remit_to", cpform.getRemit_to());
			parameters.put("clearing_bill", Integer.toString(cpform.getClearing_bill()));
			parameters.put("prorate_officer", cpform.getProrate_officer());
			parameters.put("sita_address", cpform.getSita_address());
			parameters.put("fax_number", cpform.getFax_number());
			parameters.put("total_percentage", new Double(cpform.getTotal_percentage()));
			parameters.put("total_share", new Double(cpform.getTotal_share()));

			ReportBMO rbmo = new ReportBMO(request);
			return rbmo.getReportFile(itinerarylist, parameters, "ClaimProrate", sc.getRealPath("/"),
					TracingConstants.REPORT_OUTPUT_PDF);

		} catch (Exception e) {
			logger.error("unable to create claim prorate report: " + e);
			e.printStackTrace();
			return null;
		}
	}
}