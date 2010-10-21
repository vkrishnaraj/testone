/*
 * Created on Jul 15, 2004
 *
 * Administrator
 */
package com.bagnet.nettracer.tracing.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.Globals;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.util.MessageResources;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import com.bagnet.nettracer.hibernate.HibernateWrapper;
import com.bagnet.nettracer.tracing.bmo.IncidentBMO;
import com.bagnet.nettracer.tracing.bmo.OhdBMO;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.bmo.StatusBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Articles;
import com.bagnet.nettracer.tracing.db.Claim;
import com.bagnet.nettracer.tracing.db.ClaimProrate;
import com.bagnet.nettracer.tracing.db.Company;
import com.bagnet.nettracer.tracing.db.Company_Specific_Variable;
import com.bagnet.nettracer.tracing.db.ControlLog;
import com.bagnet.nettracer.tracing.db.CountryCode;
import com.bagnet.nettracer.tracing.db.DbLocale;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Incident_Claimcheck;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.ItemType;
import com.bagnet.nettracer.tracing.db.Manufacturer;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.OHD_CategoryType;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.db.Prorate_Itinerary;
import com.bagnet.nettracer.tracing.db.ReceiptDbLocale;
import com.bagnet.nettracer.tracing.db.Remark;
import com.bagnet.nettracer.tracing.db.State;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles;
import com.bagnet.nettracer.tracing.db.XDescElement;
import com.bagnet.nettracer.tracing.db.i8n.KeyValueBean;
import com.bagnet.nettracer.tracing.db.i8n.LocaleBasedObject;
import com.bagnet.nettracer.tracing.forms.ClaimForm;
import com.bagnet.nettracer.tracing.forms.ClaimProrateForm;
import com.bagnet.nettracer.tracing.forms.IncidentForm;
import com.bagnet.nettracer.tracing.forms.LostFoundIncidentForm;
import com.bagnet.nettracer.tracing.forms.OnHandForm;
import com.bagnet.nettracer.wt.WorldTracerUtils;

import edu.emory.mathcs.backport.java.util.Collections;

/**
 * @author Administrator
 * 
 * create date - Jul 15, 2004
 */

public class TracerUtils {
	public static final int COMPANY_LIST_BY_NAME_INDEX = 0;
	public static final int COMPANY_LIST_BY_ID_INDEX = 1;

	private static Logger logger = Logger.getLogger(TracerUtils.class);
	private static ConcurrentHashMap<Integer, String> cachedManufacturerMap = new ConcurrentHashMap<Integer, String>();
	private static ConcurrentHashMap<Integer, String> cachedXDescElementMap = new ConcurrentHashMap<Integer, String>();
	
	private static MessageResources messages = null;
	
	static {
		try {
			messages = MessageResources.getMessageResources("com.bagnet.nettracer.tracing.resources.ApplicationResources");
		} catch (Exception e) {
			// Ignore
		}
	}

	public static void populateLostItem(LostFoundIncidentForm theform,
			Agent user, HttpServletRequest request) {
		theform = new LostFoundIncidentForm();
		HttpSession session = request.getSession();

		session.setAttribute("LostAndFoundForm", theform);

		theform.set_DATEFORMAT(user.getDateformat().getFormat());
		theform.set_TIMEFORMAT(user.getTimeformat().getFormat());
		theform.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(
				user.getDefaulttimezone()).getTimezone()));

		theform.setCreate_date(TracerDateTime.getGMTDate());

		theform.setFiling_agent(user);
		theform.setCreate_station(user.getStation());
		Status s = new Status();
		s.setStatus_ID(TracingConstants.LOST_FOUND_OPEN);
		theform.setReport_status(s);

		theform.setDateFoundLost(theform.getCreate_date());
		theform.setReport_type(TracingConstants.LOST_REPORT);
		theform.setCustomer_countrycode_ID(PropertyBMO.getValue(PropertyBMO.PROPERTY_DEFAULT_COUNTRY));
	}

	public static void populateFoundItem(LostFoundIncidentForm theform,
			Agent user, HttpServletRequest request) {
		theform = new LostFoundIncidentForm();
		HttpSession session = request.getSession();
		session.setAttribute("LostAndFoundForm", theform);

		theform.set_DATEFORMAT(user.getDateformat().getFormat());
		theform.set_TIMEFORMAT(user.getTimeformat().getFormat());
		theform.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(
				user.getDefaulttimezone()).getTimezone()));

		theform.setCreate_date(TracerDateTime.getGMTDate());

		theform.setFiling_agent(user);
		theform.setCreate_station(user.getStation());
		Status s = new Status();
		s.setStatus_ID(TracingConstants.LOST_FOUND_OPEN);
		theform.setReport_status(s);

		theform.setDateFoundLost(theform.getCreate_date());
		theform.setReport_type(TracingConstants.FOUND_REPORT);
		theform.setCustomer_countrycode_ID(PropertyBMO.getValue(PropertyBMO.PROPERTY_DEFAULT_COUNTRY));
	}

	public static void populateIncident(IncidentForm theform,
			HttpServletRequest request, int itemtype) {

		HttpSession session = request.getSession();
		Agent user = (Agent) session.getAttribute("user");
		theform = new IncidentForm();

		// theform = new IncidentForm();
		IncidentBMO iBMO = new IncidentBMO();

		theform.set_DATEFORMAT(user.getDateformat().getFormat());
		theform.set_TIMEFORMAT(user.getTimeformat().getFormat());
		theform.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(
				user.getDefaulttimezone()).getTimezone()));
		
		if(user != null && user.getStation() != null) {
		theform.setLanguage(user.getStation().getEmailLanguage());
		}
		session.setAttribute("incidentForm", theform);

		// session
		// set create time
		theform.setCreatedate(TracerDateTime.getGMTDate());
		theform.setCreatetime(TracerDateTime.getGMTDate());
		// set station
		theform.setStationcreated_ID(user.getStation().getStation_ID());
		theform.setStationcreated(user.getStation());
		theform.setStationassigned_ID(user.getStation().getStation_ID());
		List agentassignedlist = TracerUtils.getAgentlist(theform
				.getStationassigned_ID());
		request.setAttribute("agentassignedlist", agentassignedlist);

		// theform.setFaultstation(new Station());
		// set agent
		theform.setAgent(user);

		// set status as temp to start off
		Status status = new Status();
		status.setStatus_ID(TracingConstants.MBR_STATUS_TEMP);
		theform.setStatus(status);
		
		//set default weight unit
		String myDefaultWeightUnit = PropertyBMO.getValue(PropertyBMO.PROPERTY_NT_COMPANY_WEIGHT_UNIT_DEFAULT);
		theform.setOverall_weight_unit(myDefaultWeightUnit);

		// set report method
		theform.setReportmethod(user.getStation().getCompany().getVariable()
				.getReport_method());

		// create new fields
		theform.getPassenger(0);

		// set new remark with current time and current agent
		Remark r = theform.getRemark(theform.getRemarklist().size());
		r
				.setCreatetime(new SimpleDateFormat(
						TracingConstants.DB_DATETIMEFORMAT)
						.format(TracerDateTime.getGMTDate()));
		r.setAgent(user);
		r.set_DATEFORMAT(user.getDateformat().getFormat());
		r.set_TIMEFORMAT(user.getTimeformat().getFormat());
		r.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(
				user.getDefaulttimezone()).getTimezone()));

		if (request.getParameter("wt_af_id") != null) {
			Worldtracer_Actionfiles Action_file = null;
			Action_file = WorldTracerUtils.findActionFileByID(Integer
					.parseInt(request.getParameter("wt_af_id")));
			String remarktext = Action_file.getAction_file_text();

			r.setRemarktext(remarktext);

		}
		// create new itinerary
		theform.getItinerary(0, TracingConstants.PASSENGER_ROUTING); // passenger
		if(!PropertyBMO.isTrue(PropertyBMO.PROPERTY_BAG_ITIN_NOT_REQUIRED)){
			theform.getItinerary(1, TracingConstants.BAGGAGE_ROUTING); // bag route
		}
		// set new item
		Item i = theform.getItem(0, itemtype);
		i.set_DATEFORMAT(user.getDateformat().getFormat());
		i.setCurrency_ID(user.getDefaultcurrency());
		i.setXdescelement_ID_1(TracingConstants.XDESC_TYPE_X);
		i.setXdescelement_ID_2(TracingConstants.XDESC_TYPE_X);
		i.setXdescelement_ID_3(TracingConstants.XDESC_TYPE_X);
		i.setBagnumber(0);
		i.setStatus(StatusBMO.getStatus(TracingConstants.ITEM_STATUS_OPEN));

		// default weight unit related code here
		i.setBag_weight_unit(myDefaultWeightUnit);
		
		
		// set new claimcheck
		Incident_Claimcheck ic = theform.getClaimcheck(0);

		// create new article
		if (itemtype == TracingConstants.MISSING_ARTICLES) {
			Articles a = theform.getArticle(0);
			a.set_DATEFORMAT(user.getDateformat().getFormat());
			a.setCurrency_ID(user.getDefaultcurrency());
		}

		// set some default parameters
		theform.setNumbagchecked(1);
		theform.setNumpassengers(1);

		Company_Specific_Variable csv = AdminUtils.getCompVariable(user
				.getCompanycode_ID());
		theform.setEmail_customer(csv.isEmail_customer() ? 1 : 0);

	}

	public static void populateOnHand(OnHandForm theform,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		Agent user = (Agent) session.getAttribute("user");

		theform = new OnHandForm();

		session.setAttribute("OnHandForm", theform);

		OhdBMO bmo = new OhdBMO();
		// theform.setOhd_id(bmo.getOHD_ID(user.getStation()));

		Date x = TracerDateTime.getGMTDate();
		String date = new SimpleDateFormat(TracingConstants.DB_DATETIMEFORMAT)
				.format(x);

		// Add a new control log entry in the db as the station is to be used.
		ControlLog log = new ControlLog();
		log.setStart_date(date);
		log.setControlling_station(user.getStation());
		theform.getControlList().add(log);

		theform.setAgent(user);
		theform.set_DATEFORMAT(user.getDateformat().getFormat());
		theform.set_TIMEFORMAT(user.getTimeformat().getFormat());
		theform.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(
				user.getDefaulttimezone()).getTimezone()));

		// session
		theform.setFound_company(user.getStation().getCompany()
				.getCompanyCode_ID());
		theform.setFound_station(user.getStation().getStationcode());

		theform.setHolding_company(theform.getHolding_company());
		theform.setHolding_station(theform.getHolding_station());

		/*
		 * Status status = new Status();
		 * status.setStatus_ID(TracingConstants.OHD_STATUS_TEMP);
		 * theform.setStatus(status);
		 */

		// set new remark with current time and current agent
		Remark r = theform.getRemark(theform.getRemarklist().size());
		r.setCreatetime(date);
		r.setAgent(user);
		r.set_DATEFORMAT(user.getDateformat().getFormat());
		r.set_TIMEFORMAT(user.getTimeformat().getFormat());
		r.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils.getTimeZoneById(
				user.getDefaulttimezone()).getTimezone()));

		// if pass in action file id then store action text in the remark text
		// field
		if (request.getParameter("wt_af_id") != null) {
			Worldtracer_Actionfiles Action_file = null;
			Action_file = WorldTracerUtils.findActionFileByID(Integer
					.parseInt(request.getParameter("wt_af_id")));
			String remarktext = Action_file.getAction_file_text();

			r.setRemarktext(remarktext);

		}

		theform.setAgent_initials(user.getUsername());
		theform.setFound_station(user.getStation().getStationcode());
		theform.setFoundTime(x);
		theform.setFoundDate(x);
		theform.getPassenger(theform.getPassengerList().size());
		theform.getItinerary(theform.getItinerarylist().size());
		theform.getItem(theform.getItemlist().size());

		theform.setXDesc1(TracingConstants.XDESC_TYPE_X);
		theform.setXDesc2(TracingConstants.XDESC_TYPE_X);
		theform.setXDesc3(TracingConstants.XDESC_TYPE_X);
	}

	public static void populateLists(HttpSession session)
			throws HibernateException {
		Agent user = (Agent) session.getAttribute("user");
		Locale userLocale = new Locale(user.getCurrentlocale());
		session.setAttribute(Globals.LOCALE_KEY, userLocale);
		String locale = user.getCurrentlocale();
		Company company = user.getStation().getCompany();
		// set mbr report types
		
		
		session.setAttribute("mbrreporttypes", session
				.getAttribute("mbrreporttypes") != null ? session
				.getAttribute("mbrreporttypes") : retrieveLocaleBasedRecords(
				"com.bagnet.nettracer.tracing.db.ItemType", "itemtype",
				"itemType_ID", locale));
		
		
		session.setAttribute("expenseStatusList", session.getAttribute("expenseStatusList") != null ? session
				.getAttribute("expenseStatusList") : getStatusList(TracingConstants.TABLE_EXPENSEPAYOUT, locale, "status_ID"));

		session.setAttribute("statelist",
				session.getAttribute("statelist") != null ? session
						.getAttribute("statelist") : getStatelist());

		session.setAttribute("localelist",
				session.getAttribute("localelist") != null ? session
						.getAttribute("localelist") : getLocaleList());
		
		session.setAttribute("receiptLocaleList",
				session.getAttribute("receiptLocaleList") != null ? session
						.getAttribute("receiptLocaleList") : getReceiptLocaleList());

		// set country
		session.setAttribute("countrylist",
				session.getAttribute("countrylist") != null ? session
						.getAttribute("countrylist") : getCountryList());
		// set color
		session.setAttribute("colorlist",
				session.getAttribute("colorlist") != null ? session
						.getAttribute("colorlist") : getColorList(false, user));
		// set color for search
		session.setAttribute("colorlistforsearch", session
				.getAttribute("colorlistforsearch") != null ? session
				.getAttribute("colorlistforsearch") : getColorList(true, user));
		// set bag type
		session.setAttribute("typelist",
				session.getAttribute("typelist") != null ? session
						.getAttribute("typelist") : getTypeList(locale));
		// set xdescelementlist
		session.setAttribute("xdescelementlist", session
				.getAttribute("xdescelementlist") != null ? session
				.getAttribute("xdescelementlist") : retrieveLocaleBasedRecords(
				"com.bagnet.nettracer.tracing.db.XDescElement", "xdescelement",
				"description", locale));
		
		// set manufacturer
		session.setAttribute("manufacturerlist", session
				.getAttribute("manufacturerlist") != null ? session
				.getAttribute("manufacturerlist") : retrieveBasicRecords(
				"com.bagnet.nettracer.tracing.db.Manufacturer", "manufacturer",
				"description"));
		// set currency
		session.setAttribute("currencylist", session
				.getAttribute("currencylist") != null ? session
				.getAttribute("currencylist") : retrieveBasicRecords(
				"com.bagnet.nettracer.tracing.db.Currency", "currency",
				"description"));
		
		
		session.setAttribute("paymentTypeList", session.getAttribute("paymentTypeList") != null ?
				session.getAttribute("paymentTypeList") :
				getPaymentList(user.getCurrentlocale()));

		session.setAttribute("categorylist", session
				.getAttribute("categorylist") != null ? session
				.getAttribute("categorylist") : getCategoryList(locale));

		session.setAttribute("airportlist",
				session.getAttribute("airportlist") != null ? session
						.getAttribute("airportlist") : getAirportList(
						user.getCompanycode_ID()));

		// set status list for mbr reports
		session.setAttribute("ohdStatusList",
				session.getAttribute("ohdStatusList") != null ? session
						.getAttribute("ohdStatusList") : getStatusList(TracingConstants.TABLE_ON_HAND, locale));

		
		// set status list for mbr reports
		session.setAttribute("statuslist",
				session.getAttribute("statuslist") != null ? session
						.getAttribute("statuslist") : getStatusList(TracingConstants.TABLE_INCIDENT, locale));

		// set status list for mbr reports
		session.setAttribute("dStatusList",
				session.getAttribute("dStatusList") != null ? session
						.getAttribute("dStatusList") : getStatusList(TracingConstants.TABLE_DISPOSAL_LOST_FOUND, locale));

		// set station list
		session.setAttribute("stationlist",
				session.getAttribute("stationlist") != null ? session
						.getAttribute("stationlist") : getStationList(company.getCompanyCode_ID()));
		session.setAttribute("allstationlist", session
				.getAttribute("allstationlist") != null ? session
				.getAttribute("allstationlist") : getStationList(null));
		session
				.setAttribute(
						"airlineallstationlist",
						session.getAttribute("airlineallstationlist") != null ? session
								.getAttribute("airlineallstationlist")
								: getStationList(company
										.getCompanyCode_ID(), TracingConstants.AgentActiveStatus.ALL));

		// set company lists
		if (session.getAttribute("companylistByName") == null
				|| session.getAttribute("companylistById") == null) {
			populateCompanyLists(session);
		}

		// set expense type list
		session.setAttribute("expensetypelist", session
				.getAttribute("expensetypelist") != null ? session
				.getAttribute("expensetypelist") : retrieveLocaleBasedRecords(
				"com.bagnet.nettracer.tracing.db.ExpenseType", "expensetype",
				"description", locale));

		session.setAttribute("prioritylist", session
				.getAttribute("prioritylist") != null ? session
				.getAttribute("prioritylist") :  retrieveLocaleBasedRecords(
						"com.bagnet.nettracer.tracing.db.Priority", "priority",
						"priority_ID", locale));
	}

	private static List<LabelValueBean> getPaymentList(String locale) {
		ArrayList<LabelValueBean> result = new ArrayList<LabelValueBean>();
		result.add(new LabelValueBean(messages.getMessage(new Locale(locale), "payment.draft"), "DRAFT"));
		result.add(new LabelValueBean(messages.getMessage(new Locale(locale), "payment.voucher"), "VOUCH"));
		result.add(new LabelValueBean(messages.getMessage(new Locale(locale), "payment.mileage"), "MILE"));
		
		String m = messages.getMessage(new Locale(locale), "payment.incidental");
		if (m != null && m.length() > 0) {
			result.add(new LabelValueBean(m, "INC"));
		}
		
		m = messages.getMessage(new Locale(locale), "payment.ccrefund");
		if (m != null && m.length() > 0) {
			result.add(new LabelValueBean(messages.getMessage(new Locale(locale), "payment.ccrefund"), "CCREF"));
		}
		return result;
	}

	public static void populateCompanyLists(HttpSession session) {
		ArrayList<ArrayList<Company>> result = getCompanyLists();
		session.setAttribute("companylistByName", result
				.get(COMPANY_LIST_BY_NAME_INDEX));
		session.setAttribute("companylistById", result
				.get(COMPANY_LIST_BY_ID_INDEX));
	}

	public static ClaimForm populateClaim(ClaimForm cform,
			IncidentForm theform, HttpServletRequest request) {
		try {
			Claim claim = null;

			HttpSession session = request.getSession();

			cform = new ClaimForm();
			session.setAttribute("claimForm", cform);

			Agent user = (Agent) session.getAttribute("user");

			session.setAttribute("claimstatuslist", session
					.getAttribute("claimstatuslist") != null ? session
					.getAttribute("claimstatuslist") : getStatusList(TracingConstants.TABLE_CLAIM, user.getCurrentlocale()));

			if ((claim = theform.getClaim()) != null) {
				BeanUtils.copyProperties(cform, claim);
			}

			Passenger pa = (Passenger) theform.getPassenger(0);
			String passengername = pa.getFirstname() + " " + pa.getLastname();
			cform.setPassengername(passengername);

			if (theform.getExpenselist() != null)
				cform.setExpenselist(theform.getExpenselist());

			cform.set_DATEFORMAT(user.getDateformat().getFormat());
			cform.set_TIMEFORMAT(user.getTimeformat().getFormat());
			cform.set_TIMEZONE(TimeZone
					.getTimeZone(AdminUtils.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));

			if (cform.getClaimcurrency_ID() == null)
				cform.setClaimcurrency_ID(user.getDefaultcurrency());

			session.setAttribute("claimForm", cform);


			if (cform.getStatus() == null) {
				Status status = new Status();
				status.setStatus_ID(TracingConstants.CLAIM_STATUS_INPROCESS);
				status.setLocale(user);
				cform.setStatus(status);
			}

			return cform;

		} catch (Exception e) {
			logger.error("bean copy claim form error on populateClaim: " + e);
			e.printStackTrace();
			return cform;
		}
	}

	public static void populateClaimProrate(ClaimProrateForm cpform,
			IncidentForm theform, HttpServletRequest request) {
		try {
			HttpSession session = request.getSession();
			Agent user = (Agent) session.getAttribute("user");
			Claim claim = null;
			// cpform = new ClaimProrateForm();
			// session.setAttribute("claimForm", cpform);
			boolean createnewprorate = false;
			if ((claim = theform.getClaim()) != null) {
				ClaimProrate cp = claim.getClaimprorate();
				if (cp == null) { // no previous prorate
					cpform = new ClaimProrateForm();
					// create new itinerary list
					ArrayList al = new ArrayList();
					for (int i = 0; i < 4; i++) {
						Prorate_Itinerary pi = new Prorate_Itinerary();
						pi
								.setCurrency_ID(TracingConstants.DEFAULT_AGENT_CURRENCY);
						pi.set_DATEFORMAT(user.getDateformat().getFormat());
						pi.setAirline(user.getStation().getCompany()
								.getCompanyCode_ID());
						al.add(pi);

					}
					cpform.setItinerarylist(al);
					cpform
							.setCurrency_ID(TracingConstants.DEFAULT_AGENT_CURRENCY);
					createnewprorate = true;
				} else {
					// copy itinerarylist from existing prorate
					BeanUtils.copyProperties(cpform, cp);
					ArrayList al = new ArrayList(cp.getProrate_itineraries());
					// add dateformat to the list
					for (int i = 0; i < al.size(); i++) {
						Prorate_Itinerary pi = (Prorate_Itinerary) al.get(i);
						pi.set_DATEFORMAT(user.getDateformat().getFormat());
					}
					cpform.setItinerarylist(al);
				}
			} else {
				createnewprorate = true;
			}
			if (createnewprorate) {
				// create airline
				Company c = user.getStation().getCompany();
				cpform.setCompanycode_ID(c.getCompanyCode_ID());
				// set create date
				cpform.setCreatedate(TracerDateTime.getGMTDate());

			}

			/** do the following regardless if prorate is new or not * */

			cpform.set_DATEFORMAT(user.getDateformat().getFormat());
			cpform.set_TIMEFORMAT(user.getTimeformat().getFormat());
			cpform.set_TIMEZONE(TimeZone.getTimeZone(AdminUtils
					.getTimeZoneById(user.getDefaulttimezone()).getTimezone()));

			// file reference number
			cpform.setFile_reference(theform.getIncident_ID());

			// set passenger
			Passenger pa = (Passenger) theform.getPassenger(0);
			cpform.setPassname(pa.getFirstname() + " " + pa.getLastname());
			// set incidenttype
			List al = (List) session.getAttribute("mbrreporttypes");
			
			for (int i = 0; i < al.size(); i++) {
				ItemType it = (ItemType) al.get(i);
				if (it.getItemType_ID() == theform.getItem(0, 0)
						.getItemtype_ID()) {
					cpform.setClaimtype_ID(it.getItemType_ID());
					cpform.setClaimtype(it.getDescription());
				}

			}
			// set ticket number
			cpform.setTicketnumber(theform.getTicketnumber());
			// set baggage tag numbers

			// for lost delay, use the incident_claimcheck list
			if (cpform.getClaimtype_ID() == TracingConstants.LOST_DELAY) {
				cpform.setClaimchecknumlist(theform.getClaimchecklist());

			} else {
				// otherwise, use the item list
				al = new ArrayList();
				for (int i = 0; i < theform.getItemlist().size(); i++) {
					al.add(theform.getItem(0, 0));
				}
				cpform.setClaimchecknumlist(al);
			}

			session.setAttribute("claimProrateForm", cpform);
		} catch (Exception e) {
			logger
					.error("bean copy claimprorate form error on populateClaimProrate: "
							+ e);
			e.printStackTrace();
		}
	}

	public static ArrayList getStationList(String company, TracingConstants.AgentActiveStatus status) throws HibernateException {

		Session sess = HibernateWrapper.getSession().openSession();
		try {
			// when company is null, return all distinct stationscodes, for
			// itinerary
			// dropdown

			String sql = "";
			if (company != null) {
				sql = "select distinct station.station_ID,station.stationcode from com.bagnet.nettracer.tracing.db.Station station where "
						+ "station.company.companyCode_ID = :company ";
				if (status != TracingConstants.AgentActiveStatus.ALL)
					sql += " and station.active = :active ";
				sql += " order by stationcode";

			} else {
				sql = "select distinct station.station_ID,station.stationcode from com.bagnet.nettracer.tracing.db.Station station where 1 = 1 ";
				if (status != TracingConstants.AgentActiveStatus.ALL)
					sql += " and station.active = :active ";
				sql += " order by stationcode";
			}
			Query q = sess.createQuery(sql);
			if (status != TracingConstants.AgentActiveStatus.ALL)
				if (status == TracingConstants.AgentActiveStatus.ACTIVE)
					q.setParameter("active", true);
			if (status == TracingConstants.AgentActiveStatus.INACTIVE)
				q.setParameter("active", false);
			if (company != null)
				q.setParameter("company", company);

			List list = q.list();

			if (list.size() == 0) {
				logger.debug("unable to find station");
				return null;
			}

			Station station = null;
			ArrayList al = new ArrayList();
			Object[] o = null;
			for (int i = 0; i < list.size(); i++) {
				o = (Object[]) list.get(i);
				station = new Station();
				station.setStation_ID(((Integer) o[0]).intValue());
				station.setStationcode((String) o[1]);
				al.add(station);
			}
			return al;

		} catch (Exception e) {
			logger.error("unable to retrieve station from database: " + e);
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
		}
	}

	public static ArrayList getStationList(String company)
			throws HibernateException {
		return getStationList(company, TracingConstants.AgentActiveStatus.ACTIVE);
	}

	public static ArrayList<ArrayList<Company>> getCompanyLists()
			throws HibernateException {
		Session sess = HibernateWrapper.getSession().openSession();
		try {

			// when company is null, return all distinct stationscodes, for
			// itinerary
			// dropdown
			ArrayList<ArrayList<Company>> result = new ArrayList<ArrayList<Company>>();
			String sql = "select distinct company.companyCode_ID,company.companydesc from com.bagnet.nettracer.tracing.db.Company company order by companydesc";

			Query q = sess.createQuery(sql);

			List list = q.list();

			if (list.size() == 0) {
				logger.debug("unable to find company");
				result.add(null);
				result.add(null);
				return result;
			}

			Company company = null;
			ArrayList<Company> companyByName = new ArrayList<Company>();
			ArrayList<Company> companyById = new ArrayList<Company>();
			Object[] o = null;
			for (int i = 0; i < list.size(); i++) {
				o = (Object[]) list.get(i);
				company = new Company();
				company.setCompanyCode_ID((String) o[0]);
				company.setCompanydesc((String) o[1]);
				companyByName.add(company);
				companyById.add(company);
			}
			// Collections.sort(companyById, companyById.new
			// CompanyIdComparator<Company>());
			Collections.sort(companyById, new Comparator<Company>() {

				public int compare(Company o1, Company o2) {
					return o1.getCompanyCode_ID().compareTo(
							o2.getCompanyCode_ID());
				}

			});
			result.add(companyByName);
			result.add(companyById);
			return result;

		} catch (Exception e) {
			logger.error("unable to retrieve company from database: " + e);
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
		}
	}

	@Deprecated
	public static ArrayList retrieveRecords(String instr, String obj,
			String tablename, String colname, String orderColumn)
			throws HibernateException {
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			Query q = null;

			if (colname == null) {

				String sql = "from " + obj + " " + tablename + " where 1=1 ";
				if (orderColumn != null && !orderColumn.equals("")) {
					sql += " order by " + orderColumn + " asc";
				}

				q = sess.createQuery(sql);
			} else {

				String sql = "from " + obj + " " + tablename + " where "
						+ tablename + "." + colname + " = :" + colname;
				if (orderColumn != null && !orderColumn.equals("")) {
					sql += " order by " + orderColumn + " asc";
				}

				q = sess.createQuery(sql);
				q.setParameter(colname, instr);
			}
			List list = q.list();

			if (list == null || list.size() == 0) {
				logger.debug("unable to find " + tablename);
				return null;
			}
			return (ArrayList) list;
		} catch (Exception e) {
			logger.error("unable to retrieve " + tablename + " from database: "
					+ e);
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
		}
	}
	
	public static ArrayList retrieveBasicRecords(String obj,
			String tablename, String orderColumn)
			throws HibernateException {
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			Query q = null;

			String sql = "from " + obj + " " + tablename + " where 1=1 ";
			if (orderColumn != null && !orderColumn.equals("")) {
				sql += " order by " + orderColumn + " asc";
			}

			q = sess.createQuery(sql);

			List list = q.list();

			if (list == null || list.size() == 0) {
				logger.debug("unable to find " + tablename);
				return null;
			}
			return (ArrayList) list;
		} catch (Exception e) {
			logger.error("unable to retrieve " + tablename + " from database: "
					+ e);
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
		}
	}
	
	public static ArrayList retrieveLocaleBasedRecords(String obj,
			String tablename, String orderColumn, String setLocale)
			throws HibernateException {
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			Query q = null;

			String sql = "from " + obj + " " + tablename + " where 1=1 ";
			if (orderColumn != null && !orderColumn.equals("")) {
				sql += " order by " + orderColumn + " asc";
			}

			q = sess.createQuery(sql);
			List<LocaleBasedObject> list = q.list();

			if (list == null || list.size() == 0) {
				logger.debug("unable to find " + tablename);
				return null;
			} else {
				for (LocaleBasedObject object: list) {
					object.setLocale(setLocale);
				}
			}
			return (ArrayList) list;
		} catch (Exception e) {
			logger.error("unable to retrieve " + tablename + " from database: "
					+ e);
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
		}
	}



	public static ArrayList getStatusList(int table_ID, String locale, String orderBy)
			throws HibernateException {
		Session sess = HibernateWrapper.getSession().openSession();
		try {
			Criteria cri = sess.createCriteria(Status.class).add(
					Expression.eq("table_ID", new Integer(table_ID)));
			if (orderBy != null) {
				cri.addOrder(Order.asc(orderBy));
			}
			List<Status> list = cri.list();
			
			if (list == null || list.size() == 0) {
				logger.debug("unable to find status");
				return null;
			} else {
				for (Status status: list) {
					status.setLocale(locale);
				}
			}
			return (ArrayList) list;
		} catch (Exception e) {
			logger.error("unable to retrieve status list from database: " + e);
			e.printStackTrace();
			return null;
		} finally {
			sess.close();
		}
	}

	public static ArrayList getStatusList(int table_ID, String locale)
			throws HibernateException {
		return getStatusList(table_ID, locale, null);
	}
	
	public static ArrayList<LabelValueBean> getTypeList(String locale) {
		ArrayList<LabelValueBean> al = new ArrayList<LabelValueBean>();
		al.add(new KeyValueBean("select.please_select", "", locale));

		al.add(new LabelValueBean("01", "01"));
		al.add(new LabelValueBean("02", "02"));
		al.add(new LabelValueBean("03", "03"));
		//no 4
		al.add(new LabelValueBean("05", "05"));
		al.add(new LabelValueBean("06", "06"));
		al.add(new LabelValueBean("07", "07"));
		al.add(new LabelValueBean("08", "08"));
		al.add(new LabelValueBean("09", "09"));
		al.add(new LabelValueBean("10", "10"));
		//no 11
		al.add(new LabelValueBean("12", "12"));

		for (int i = 20; i <= 29; i++) {
			if(i == 24 || i == 21) {
				continue;
			}
			al
					.add(new LabelValueBean(Integer.toString(i), Integer
							.toString(i)));
		}
		for (int i = 50; i <= 99; i++) {
			if(i == 70 || i == 76 || i == 77 || i == 78 || i == 79 || i == 80 || i == 84 || i == 86 || i == 87 || i == 88 || i == 91) {
				continue;
			}
			al
					.add(new LabelValueBean(Integer.toString(i), Integer
							.toString(i)));
		}
		return al;
	}

	public static ArrayList<LabelValueBean> getColorList(boolean forsearch, Agent user) {
		ArrayList<LabelValueBean> al = new ArrayList<LabelValueBean>();

		if (forsearch)
			al.add(new KeyValueBean("select.all", "", user));
		else
			al.add(new KeyValueBean("select.please_select", "", user));

		al.add(new KeyValueBean("COLOR_KEY_WT", "WT", user));
		al.add(new KeyValueBean("COLOR_KEY_BK", "BK", user));
		al.add(new KeyValueBean("COLOR_KEY_GY", "GY", user));
		al.add(new KeyValueBean("COLOR_KEY_BU", "BU", user));
		al.add(new KeyValueBean("COLOR_KEY_BE", "BE", user));
		al.add(new KeyValueBean("COLOR_KEY_RD", "RD", user));
		al.add(new KeyValueBean("COLOR_KEY_YW", "YW", user));
		al.add(new KeyValueBean("COLOR_KEY_BN", "BN", user));
		al.add(new KeyValueBean("COLOR_KEY_GN", "GN", user));
		al.add(new KeyValueBean("COLOR_KEY_PU", "PU", user));
		al.add(new KeyValueBean("COLOR_KEY_MC", "MC", user));
//		al.add(new KeyValueBean("COLOR_KEY_TD", "TD", user));
		al.add(new KeyValueBean("COLOR_KEY_PR", "PR", user));

		return al;
	}

	
	public static ArrayList getCategoryList(String locale) {

		Session sess = null;
		try {
			String query = "select ohd_categorytype from com.bagnet.nettracer.tracing.db.OHD_CategoryType ohd_categorytype "
					+ "order by ohd_categorytype.categoryKey";
			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(query);
			List list = q.list();

			if (list.size() == 0) {
				logger.debug("unable to find category type");
				return null;
			} else {
				for (LocaleBasedObject x: (List<OHD_CategoryType>) list) {
					x.setLocale(locale);
				}
			}
			return (ArrayList) list;
		} catch (Exception e) {
			logger.error("unable to retrieve station from database: " + e);
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (sess != null)
					sess.close();
			} catch (Exception e) {
				logger.error("unable to close session: " + e);
				e.printStackTrace();
			}
		}
		/*
		 * ArrayList al = new ArrayList(); al.add(new LabelValueBean("Please
		 * Select", "")); al.add(new LabelValueBean("Photo", "1")); return al;
		 */
	}

	public static ArrayList getAirportList(String companycode) {

		Session sess = null;
		try {

			String sql = "select airport from "
					+ "com.bagnet.nettracer.tracing.db.Airport airport where 1=1 ";

			sql += " and airport.companyCode_ID = :companycode";

			sql += " order by airport.country,airport.airport_desc";

			sess = HibernateWrapper.getSession().openSession();
			Query q = sess.createQuery(sql);
			
			q.setString("companycode", companycode);

			List list = q.list();

			if (list.size() == 0) {
				return null;
			}
			return (ArrayList) list;
		} catch (Exception e) {
			logger.error("unable to retrieve airports from database: " + e);
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (sess != null)
					sess.close();
			} catch (Exception e) {
				logger.error("unable to close session: " + e);
				e.printStackTrace();
			}
		}
	}

	
	/**
	 * @return Returns the localelist.
	 */
	public static ArrayList getReceiptLocaleList() {
		ArrayList al = new ArrayList();

		List localeList = HibernateUtils.retrieveAll(ReceiptDbLocale.class);

		for (Iterator i = localeList.iterator(); i.hasNext();) {
			ReceiptDbLocale loc = (ReceiptDbLocale) i.next();
			al.add(new LabelValueBean(loc.getLocale_description(), loc
					.getLocale_id()));
		}
		return al;
	}

	/**
	 * @return Returns the localelist.
	 */
	public static ArrayList getLocaleList() {
		ArrayList al = new ArrayList();

		List localeList = HibernateUtils.retrieveAll(DbLocale.class);

		for (Iterator i = localeList.iterator(); i.hasNext();) {
			DbLocale loc = (DbLocale) i.next();
			al.add(new LabelValueBean(loc.getLocale_description(), loc
					.getLocale_id()));
		}
		return al;
	}

	/**
	 * @return Returns the statelist.
	 */
	public static ArrayList getStatelist() {

		ArrayList al = new ArrayList();
		List retrieval = null;

		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(State.class);
			cri.addOrder(Order.asc("state"));
			retrieval = cri.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		if (retrieval != null) {
			for (Iterator i = retrieval.iterator(); i.hasNext();) {
				State state = (State) i.next();
				al
						.add(new LabelValueBean(state.getState(), state
								.getState_ID()));
			}
		}
		return al;
	}

	public static ArrayList getCountryList() {
		ArrayList al = new ArrayList();
		List retrieval = null;

		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(CountryCode.class);
			cri.addOrder(Order.asc("country"));
			retrieval = cri.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if (retrieval != null) {
			for (Iterator i = retrieval.iterator(); i.hasNext();) {
				CountryCode country = (CountryCode) i.next();
				al.add(new LabelValueBean(country.getCountry(), country
						.getCountryCode_ID()));
			}
		}
		return al;
	}

	public static String format(double number, String f) {
		String ret = "";
		DecimalFormat format = (DecimalFormat) NumberFormat.getInstance();
		if (f.equals("%")) {
			format.applyPattern("#0.00");
			format.setMinimumFractionDigits(2);
			ret = "" + format.format(number);
		} else if (f.equals("$")) {
			format.applyPattern("#,##0.00");
			format.setMinimumFractionDigits(2);
			ret = "" + format.format(number);
		}

		return ret;
	}

	public static double convertToDouble(String s) {
		try {
			return Double.parseDouble(s);
		} catch (Exception e) {
			return 0;
		}
	}

	public static String removeSpaces(String s) {
		StringBuffer sb = new StringBuffer(20);
		StringTokenizer st = new StringTokenizer(s, " ");
		while (st.hasMoreTokens()) {
			sb.append(st.nextToken().trim());
		}
		return sb.toString();
	}

	public static void checkSession(HttpSession session) {
		if (session == null || session.getAttribute("lastupdate") == null
				|| session.getAttribute("user") == null) {
			session.removeAttribute("user");
			return;
		}

		Date lastupdate = (Date) session.getAttribute("lastupdate");
		Agent user = (Agent) session.getAttribute("user");
		int timeout = user.getTimeout() * 60000;
		Date now = new Date();
		long start = lastupdate.getTime();
		long end = now.getTime();
		if (end - start > timeout) {
			session.removeAttribute("user");
			user.setIs_online(0);
			SecurityUtils.updateAgentLogin(user, TracerDateTime.getGMTDate(), 0, false);
		} else {
			session.setAttribute("lastupdate", now);
		}
	}

	public static String getCachedManufacturerDescription(int code) {
		Integer key = new Integer(code);
		if (code != 0) {
			if (cachedManufacturerMap.containsKey(key)) {
				return cachedManufacturerMap.get(key);
			} else {
				Manufacturer manu = getManufacturer(code);
				if (manu != null) {
					cachedManufacturerMap.put(key, manu.getDescription());
					return manu.getDescription();
				}
			}
		}
		return "";
	}

	public static Manufacturer getManufacturer(int code) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Manufacturer.class).add(
					Expression.eq("manufacturer_ID", new Integer(code)));
			return (Manufacturer) cri.list().get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static String getCachedXdescelementDescription(int code) {
		Integer key = new Integer(code);
		if (code != 0) {
			if (cachedXDescElementMap.containsKey(key)) {
				return cachedXDescElementMap.get(key);
			} else {
				XDescElement element = getXdescelement(code);
				if (element != null) {
					cachedXDescElementMap.put(key, element.getDescription());
					return element.getDescription();
				}
			}
		}
		return "";
	}

	public static XDescElement getXdescelement(int id) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(XDescElement.class).add(
					Expression.eq("XDesc_ID", new Integer(id)));
			return (XDescElement) cri.list().get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static CountryCode getCountry(String code) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(CountryCode.class).add(
					Expression.eq("countryCode_ID", code));
			return (CountryCode) cri.list().get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static State getState(String code) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(State.class).add(
					Expression.eq("state_ID", code));
			return (State) cri.list().get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static Station getStationByWtCode(String stationcode,
			String companycode_id) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();

			String sql = "select station from com.bagnet.nettracer.tracing.db.Station station where "
					+ "station.wt_stationcode = :stationcode and station.company.companyCode_ID = :companycode_id";

			Query q = sess.createQuery(sql);
			q.setParameter("stationcode", stationcode);
			q.setParameter("companycode_id", companycode_id);

			List list = q.list();
			if (list != null && list.size() > 0)
				return (Station) list.get(0);
			else
				return getStationByCode(stationcode, companycode_id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static Station getStationByCode(String stationcode,
			String companycode_id) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();

			String sql = "select station from com.bagnet.nettracer.tracing.db.Station station where "
					+ "station.stationcode = :stationcode and station.company.companyCode_ID = :companycode_id";

			Query q = sess.createQuery(sql);
			q.setParameter("stationcode", stationcode);
			q.setParameter("companycode_id", companycode_id);

			List list = q.list();
			if (list != null && list.size() > 0)
				return (Station) list.get(0);
			else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static String getStationcode(int station_ID) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();

			String query = "select station.stationcode from com.bagnet.nettracer.tracing.db.Station station where "
					+ "station.station_ID = :station_ID";

			Query q = sess.createQuery(query);
			q.setInteger("station_ID", station_ID);

			return (String) q.list().get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static List getAgentlist(int station_ID) {
		Session sess = null;
		if (station_ID == 0)
			return new ArrayList();
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Agent.class);
			cri.createCriteria("station").add(
					Expression.eq("station_ID", new Integer(station_ID)));
			cri.add(Expression.eq("active", true));
			return cri.list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Retrieve an agent based on its username and company code
	 * 
	 * @param username
	 * @param companyCode
	 * @return agent; null if not found or exception
	 */
	public static Agent getAgent(String username, String companyCode) {
		Session sess = null;
		try {
			sess = HibernateWrapper.getSession().openSession();
			Criteria cri = sess.createCriteria(Agent.class);
			cri.add(Expression.eq("username", username));
			cri.add(Expression.eq("companycode_ID", companyCode));
			return (Agent) cri.list().get(0);
		} catch (Exception e) {
			logger.fatal(e.getMessage());
			return null;
		} finally {
			if (sess != null) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.fatal(e.getMessage());
				}
			}
		}
	}

	//-------------------------------------------------------
	public static Incident incidentFileReference(String fileReference)
	{
		IncidentBMO incidentBMO=new IncidentBMO();
		Incident incident=incidentBMO.findIncidentByID(fileReference);
		return incident;
	}
	public static OHD ohdFileReference(String fileReference)
	{
		OhdBMO ohdBMO=new OhdBMO();
		OHD ohd=ohdBMO.findOHDByID(fileReference);
		return ohd;
	}
	public static boolean madeSuspendWT_BAG_SELECTED(List list){
		boolean flag=true;
		try {
			Session session=HibernateWrapper.getSession().openSession();
			Transaction tx=session.beginTransaction();
			Iterator it=list.iterator();
			while(it.hasNext()){
				Item item=(Item)it.next();
				item.setWt_bag_selected(1);
				session.update(item);
				if(tx.isActive()){
					tx.commit();
				}
				else{
					tx=session.beginTransaction();
					tx.commit();
				}
			}
			session.close();
		} catch (HibernateException e) {
			flag=false;
			e.printStackTrace();
		}
		return flag;
	}
	public static Item findItemByItemID(int itemID){
		Session session=HibernateWrapper.getSession().openSession();
		Query query=session.createQuery("from com.bagnet.nettracer.tracing.db.Item as item where item.item_ID=?");
		query.setParameter(0, itemID);
		List list=query.list();
		Item item=(Item)list.get(0);
		session.close();
		return item;
	}
	public static Boolean madePartSuspendWT_BAG_SELECTED(String[] checkbox)
	{
		Boolean flag=true;
		try {
			Session session = HibernateWrapper.getSession().openSession();
			Transaction tx=session.beginTransaction();
			for(int i=0;i<checkbox.length;i++){
				Item item=TracerUtils.findItemByItemID(Integer.parseInt(checkbox[i]));
				item.setWt_bag_selected(1);
				session.update(item);
				if(tx.isActive()){
					tx.commit();
				}
				else{
					tx=session.beginTransaction();
					tx.commit();
				}
			}
			session.close();
		} catch (HibernateException e) {
			flag=false;
			e.printStackTrace();
		} 
		return flag;
	}
	

	public static String getText(String key, String locale) {
		String thisLocale = locale;
		if (thisLocale == null || ("").equals(thisLocale)) {
			thisLocale = TracingConstants.DEFAULT_LOCALE;
		}
			
		return messages.getMessage(new Locale(thisLocale), key);
	}
	
	
	public static String getText(String key, Agent user) {
		String locale = TracingConstants.DEFAULT_LOCALE;
		if (user != null) {
			locale = user.getCurrentlocale();
		}
		return messages.getMessage(new Locale(locale), key);
	}

	public static int getRowsPerPage(String key, HttpSession session) {
		ConcurrentHashMap<String, Integer> rowMap = (ConcurrentHashMap<String, Integer>) session.getAttribute(TracingConstants.ROWSPERPAGE_MAP);
		
		if(rowMap == null) {
			rowMap = new ConcurrentHashMap<String, Integer>();
			session.setAttribute(TracingConstants.ROWSPERPAGE_MAP, rowMap);
		}
		
		if(!rowMap.containsKey(key)) {
			rowMap.put(key, TracingConstants.ROWS_PER_PAGE);
			return TracingConstants.ROWS_PER_PAGE;
		}
		return rowMap.get(key);
	}

	public static void updateRowsPerPage(String key, int rowsperpage, HttpSession session) {
		ConcurrentHashMap<String, Integer> rowMap = (ConcurrentHashMap<String, Integer>) session.getAttribute(TracingConstants.ROWSPERPAGE_MAP);
		if(rowMap == null) {
			rowMap = new ConcurrentHashMap<String, Integer>();
			session.setAttribute(TracingConstants.ROWSPERPAGE_MAP, rowMap);
		}
		rowMap.put(key, rowsperpage);
	}

	public static int manageRowsPerPage(String rowsString, String rowKey, HttpSession session) {
		int rowsperpage = 0;
		if(rowsString != null) {
			rowsperpage = Integer.parseInt(rowsString);
			if(rowsperpage > 0) {
				TracerUtils.updateRowsPerPage(rowKey, rowsperpage, session);
			}
			else {
				rowsperpage = TracerUtils.getRowsPerPage(rowKey, session);
			}
		}
		else {
			rowsperpage = TracerUtils.getRowsPerPage(rowKey, session);
		}
		return rowsperpage;
	}


}
