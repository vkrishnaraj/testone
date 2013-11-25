/*
 * Created on Aug 10, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.reporting;

import java.io.File;
import java.util.HashMap;
import java.util.List;
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
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.bmo.ReportBMO;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Company;
import com.bagnet.nettracer.tracing.db.ExpensePayout;
import com.bagnet.nettracer.tracing.db.Incident_Claimcheck;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.Item_Inventory;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.forms.IncidentForm;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;
import com.bagnet.nettracer.tracing.utils.TracerDateTime;
import com.bagnet.nettracer.tracing.utils.UserPermissions;

/**
 * @author Matt

 */
public class LostDelayReceipt {
	private static Logger logger = Logger.getLogger(LostDelayReceipt.class);

	/**
	 *  
	 */
	public LostDelayReceipt() {
		super();
	}
	
	public static HashMap getParameters(IncidentForm theform, MessageResources messages, String language, Agent user, String titleProperty) {
		HashMap parameters = new HashMap();
		
		if (messages == null) {
			messages = MessageResources.getMessageResources("com.bagnet.nettracer.tracing.resources.ApplicationResources");
		}
		
		if (language == null) {
			language = TracingConstants.DEFAULT_LOCALE;
		}
		
		String airline = theform.getStationcreated().getCompany().getCompanydesc();
		parameters.put("title", messages.getMessage(new Locale(language), titleProperty));

		parameters.put("station_city", (theform.getStationassigned().getCity() != null ? theform.getStationassigned().getCity().toUpperCase() : ""));
		parameters.put("station_phone", (theform.getStationassigned().getPhone() != null ? theform.getStationassigned().getPhone() : ""));

		Company company = null;
		if (theform.getStationassigned().getCompany() != null) {
			company = theform.getStationassigned().getCompany();
		} else {
			company = StationBMO.getStation(theform.getStationassigned().getStation_ID()).getCompany();
		}
		
		parameters.put("airline", company.getCompanydesc());
		
		parameters.put("airline_addr", company.getAddress1() + "\n"
				+ company.getAddress2() + "\n" + company.getCity() + ", "
				+ company.getState_ID() + " " + company.getZip());
		parameters.put("airline_phone", company.getPhone());
		parameters.put("airline_email", company.getEmail_address());
		String prettyDate = "";
		
		String format = "MMMMMMMMMM dd, yyyy";
		prettyDate = DateUtils.formatDate(TracerDateTime.getGMTDate(), format, null, TimeZone.getTimeZone(AdminUtils
				.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));

		parameters.put("prettydate", prettyDate);

		Passenger pa = (Passenger) theform.getPassenger(0);
		
		parameters.put("creditfile_amt", "0.00");
		parameters.put("creditfile_exp", "");
		parameters.put("incidental_amt", "0.00");
		
		double totalVoucher = 0;
		double incidentalAmt = 0;
		for(ExpensePayout payout : (Iterable<ExpensePayout>)theform.getExpenselist()) {
			if(payout.getVoucheramt() > 0.05) {
				totalVoucher += payout.getVoucheramt();
				parameters.put("creditfile_amt", String.format("%01.2f", totalVoucher));
			}
			if(payout.getVoucherExpirationDate() != null) {
				parameters.put("creditfile_exp", DateUtils.formatDate(payout.getVoucherExpirationDate(), user.getDateformat().getFormat(), user.getCurrentlocale(), TimeZone.getTimeZone(user.getCurrenttimezone())));
			}
			if(payout.getIncidentalAmountAuth() > 0.05) {
				incidentalAmt += payout.getIncidentalAmountAuth();
				parameters.put("incidental_amt", String.format("%01.2f", incidentalAmt));
			}
			if(payout.getCurrency_ID() != null && payout.getCurrency_ID().trim().length() > 0) {
				parameters.put("currency_id", payout.getCurrency_ID());
			}
		}

		parameters.put("createdate", theform.getDispcreatetime());
		parameters.put("pass_name", (pa.getLastname() != null ? (pa.getLastname() + ", ") : "") + (pa.getFirstname() != null ? pa.getFirstname() : ""));
		parameters.put("pass_name2", (pa.getFirstname() != null ? (pa.getFirstname() + " ") : "") + (pa.getLastname() != null ? pa.getLastname() : ""));
		
		parameters.put("pass_lastname", (pa.getLastname() != null ? pa.getLastname() : ""));
		parameters.put("pass_firstname", (pa.getFirstname() != null ? pa.getFirstname() : ""));
		parameters.put("file_reference", theform.getIncident_ID());

		String phno = pa.getAddress(0).getHomephone();
		if (phno == null || phno.length() == 0)
			phno = pa.getAddress(0).getMobile();

		parameters.put("phone", phno);
		parameters.put("address1", pa.getAddress(0).getAddress1());
		parameters.put("address2", pa.getAddress(0).getAddress2());
		parameters.put("email_address", pa.getAddress(0).getEmail());
		parameters.put("city_st_zip", (pa.getAddress(0).getCity() != null ? (pa.getAddress(0).getCity() + ", ") : "")
				+ (pa.getAddress(0).getState_ID() != null ? (pa.getAddress(0).getState_ID() + " ") : (pa.getAddress(0).getProvince() != null ? (pa.getAddress(0).getProvince() + " ") : ""))
				+ (pa.getAddress(0).getZip() != null ? pa.getAddress(0).getZip() : ""));
		parameters.put("city", (pa.getAddress(0).getCity() != null ? pa.getAddress(0).getCity() : ""));
		parameters.put("state", (pa.getAddress(0).getState_ID() != null ? pa.getAddress(0).getState_ID() : (pa.getAddress(0).getProvince() != null ? pa.getAddress(0).getProvince() : "")));
		parameters.put("zip", (pa.getAddress(0).getZip() != null ? pa.getAddress(0).getZip() : ""));
		parameters.put("country", (pa.getAddress(0).getCountry() != null ? pa.getAddress(0).getCountry() : ""));
		
		if (pa.getAddress(0).isPermanent()) {
			parameters.put("valid_until", "Permanent");
		} else {
			if (pa.getAddress(0).getValid_edate() != null) {
				parameters.put("valid_until", 
					DateUtils.formatDate(pa.getAddress(0).getValid_edate(), format, null, TimeZone.getTimeZone(AdminUtils
							.getTimeZoneById(user.getDefaulttimezone()).getTimezone())));
			} else {
				parameters.put("valid_until", "");
			}
		}

		parameters.put("station", theform.getStationcreated().getStationcode());
		parameters.put("stationname", theform.getStationcreated().getStationdesc());

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < theform.getClaimchecklist().size(); i++) {
			if (((Incident_Claimcheck) theform.getClaimchecklist().get(i)).getClaimchecknum() != null) {
				sb.append(((Incident_Claimcheck) theform.getClaimchecklist().get(i)).getClaimchecknum().trim());
				sb.append(",");
			}
		}
		
		if (sb.length() > 0) {
			parameters.put("claim_check_num", sb.toString().substring(0, sb.toString().length() - 1));
			parameters.put("bag_description", sb.toString().substring(0, sb.toString().length() - 1));
		}
		
		//new code - reconcile later
		StringBuffer sbClaimCheckNumber = new StringBuffer();
		StringBuffer sbBagDesc = new StringBuffer();
		StringBuffer sbArticles = new StringBuffer();
		List<Item> myItemList = theform.getItemlist();
		for (int i = 0; i < myItemList.size(); i++) {
			Item item = myItemList.get(i);
			String myClaimCheckNumber = item.getClaimchecknum();
			if (!(myClaimCheckNumber == null || myClaimCheckNumber.equals(""))) {
				sbClaimCheckNumber.append(myClaimCheckNumber.trim());
				sbClaimCheckNumber.append(",");
				sbBagDesc.append(",");
				sbBagDesc.append(myClaimCheckNumber.trim());
				if (item.getDamage() != null && item.getDamage().trim().length() > 0) {
					sbBagDesc.append(": ");
					sbBagDesc.append(item.getDamage());
				}
			}
			if (item != null && item.getInventorylist() != null && item.getInventorylist().size() > 0) {
				for (Item_Inventory inven : (List<Item_Inventory>) item.getInventorylist()) {
					if (inven != null && inven.getDescription() != null && inven.getDescription().trim().length() > 0) {
						sbArticles.append(",");
						sbArticles.append(inven.getDescription().trim());
					}
				}
			}
		}
		if (sbClaimCheckNumber.length() > 0) {
			int claimCheckNumberLength = sbClaimCheckNumber.toString().length() - 1;
			String claim_check_num = sbClaimCheckNumber.toString().substring(0, claimCheckNumberLength);
			parameters.put("claim_check_num", claim_check_num);
			logger.info("claim_check_num:" + claim_check_num);
		}

		if (sbBagDesc.length() > 0) {
			parameters.put("bag_description", sbBagDesc.toString().substring(1));
		}
		
		if (sbArticles.length() > 0) {
			parameters.put("articles", sbArticles.toString().substring(1));
		}
		
		//end of new code


		parameters.put("pnr_locator", theform.getRecordlocator());

		return parameters;
	}

	public static String createReport(IncidentForm theform, ServletContext sc, HttpServletRequest request, int outputtype, String language) {
		try {

			HttpSession session = request.getSession();
			Agent user = (Agent) session.getAttribute("user");
			MessageResources messages = MessageResources.getMessageResources("com.bagnet.nettracer.tracing.resources.ApplicationResources");

			Map parameters = getParameters(theform, messages, language, user, "lostdelay.receipt.title");

			ResourceBundle myResources = ResourceBundle.getBundle("com.bagnet.nettracer.tracing.resources.ApplicationResources", new Locale(language));
			parameters.put("REPORT_RESOURCE_BUNDLE", myResources);
			

			File logo = new File(sc.getRealPath("/") + "reports/logo.jpg");
			if (logo.exists()) {
				parameters.put("logo", logo.getAbsolutePath());
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
			
			File powered = new File(sc.getRealPath("/") + "deployment/main/images/nettracer/poweredby_net_tracer.jpg");
			if (powered.exists()) {
				parameters.put("powered", powered.getAbsolutePath());
			}

			ReportBMO rbmo = new ReportBMO(request);
			
			IncidentBMO ibmo = new IncidentBMO();
			theform.setPrintedreceipt(ibmo.incrementPrintedReceipt(theform.getIncident_ID()));
						
			String filename = rbmo.getReportFileName(TracingConstants.LOST_DELAY, language, theform);
			
			return rbmo.getReportFile(theform.getClaimchecklist(), parameters, filename, sc.getRealPath("/"), outputtype);

		} catch (Exception e) {
			logger.error("unable to create claim prorate report: " + e);
			e.printStackTrace();
			return null;
		}
	}
}