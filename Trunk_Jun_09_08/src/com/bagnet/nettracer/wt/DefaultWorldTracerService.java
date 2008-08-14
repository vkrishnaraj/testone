package com.bagnet.nettracer.wt;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.bmo.XDescElementsBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Address;
import com.bagnet.nettracer.tracing.db.Claim;
import com.bagnet.nettracer.tracing.db.Company_specific_irregularity_code;
import com.bagnet.nettracer.tracing.db.ExpensePayout;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.Item_Inventory;
import com.bagnet.nettracer.tracing.db.Itinerary;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.OHD_Address;
import com.bagnet.nettracer.tracing.db.OHD_Inventory;
import com.bagnet.nettracer.tracing.db.OHD_Itinerary;
import com.bagnet.nettracer.tracing.db.OHD_Passenger;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.db.WT_FWD_Log;
import com.bagnet.nettracer.tracing.db.WT_FWD_Log_Itinerary;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles.ActionFileType;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.StringUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;

public class DefaultWorldTracerService implements WorldTracerService {

	private static final DateFormat ITIN_DATE_FORMAT = new SimpleDateFormat("ddMMM");

	private static final Logger logger = Logger.getLogger(DefaultWorldTracerService.class);

	private WorldTracerConnector wtConnector;

	private String wtCompanyCode;

	public static final String FIELD_SEP = ".";
	public static final String ENTRY_SEP = "/";
	public static final String CONTINUATION = "-";
	
	

	public static enum RepeatType {
		NONE, SAME_LINE, MANY_LINES, MULTIPLE
	}

	public static enum WorldTracerField {
		CT("color.type"), FD("flight.date"), IT("initials"), RT("routing"), NM("name"), PT("pax.title"), PS(
				"pax.status"), FL("freqflier.id"), PA("perm.address"), EA("email.address"), CO("country"), PN(
				"home.phone"), TP("work.phone"), CP("mobile.phone"), FX("fax.number"), NP("num.pax"), BR("bag.itin"), TN(
				"bag.tagnum"), BI("bag.brand"), HC("handled.copy"), FS("fault.station"), TK("ticket.num"), PB(
				"pooled.ticket"), CC("contents"), AG("agent"), AB("bag.address"), PR("pnr.locator"), CS("costs"), RL(
				"reason.loss"), RC("loss.comments"), SL("storage.location"), XT("expidite.tag"), FO("fwd.date"), FW(
				"fwd.stations"), FB("fwd.bag.count"), FT("fault.terminal"), SI("sup.info"), TX("teletype.address");

		private String description_id;

		private WorldTracerField(String desc) {
			this.description_id = desc;
		}

		public String description() {
			return description_id;
		}
	}

	public static final EnumMap<WorldTracerField, Object[]> INC_FIELD_RULES;
	public static final EnumMap<WorldTracerField, Object[]> OHD_FIELD_RULES;

	public static final EnumMap<WorldTracerField, Object[]> CAH_FIELD_RULES;
	public static final EnumMap<WorldTracerField, Object[]> COH_FIELD_RULES;

	private static EnumMap<WorldTracerField, Object[]> FWD_FIELD_RULES;

	static {
		INC_FIELD_RULES = new EnumMap<WorldTracerField, Object[]>(WorldTracerField.class);
		INC_FIELD_RULES.put(WorldTracerField.CT, new Object[] { 10, RepeatType.MULTIPLE });
		INC_FIELD_RULES.put(WorldTracerField.FD, new Object[] { 4, RepeatType.SAME_LINE });
		INC_FIELD_RULES.put(WorldTracerField.IT, new Object[] { 3, RepeatType.SAME_LINE });
		INC_FIELD_RULES.put(WorldTracerField.RT, new Object[] { 15, RepeatType.SAME_LINE });
		INC_FIELD_RULES.put(WorldTracerField.NM, new Object[] { 3, RepeatType.SAME_LINE });
		INC_FIELD_RULES.put(WorldTracerField.PT, new Object[] { 1, RepeatType.NONE });
		INC_FIELD_RULES.put(WorldTracerField.PS, new Object[] { 1, RepeatType.NONE });
		INC_FIELD_RULES.put(WorldTracerField.FL, new Object[] { 1, RepeatType.NONE });
		INC_FIELD_RULES.put(WorldTracerField.PA, new Object[] { 1, RepeatType.MULTIPLE });
		INC_FIELD_RULES.put(WorldTracerField.EA, new Object[] { 1, RepeatType.NONE });
		INC_FIELD_RULES.put(WorldTracerField.CO, new Object[] { 1, RepeatType.NONE });
		INC_FIELD_RULES.put(WorldTracerField.PN, new Object[] { 2, RepeatType.MULTIPLE });
		INC_FIELD_RULES.put(WorldTracerField.TP, new Object[] { 2, RepeatType.MULTIPLE });
		INC_FIELD_RULES.put(WorldTracerField.CP, new Object[] { 2, RepeatType.MULTIPLE });
		INC_FIELD_RULES.put(WorldTracerField.FX, new Object[] { 2, RepeatType.MULTIPLE });
		INC_FIELD_RULES.put(WorldTracerField.NP, new Object[] { 1, RepeatType.NONE });
		INC_FIELD_RULES.put(WorldTracerField.BR, new Object[] { 4, RepeatType.SAME_LINE });
		INC_FIELD_RULES.put(WorldTracerField.TN, new Object[] { 10, RepeatType.MULTIPLE });
		INC_FIELD_RULES.put(WorldTracerField.BI, new Object[] { 10, RepeatType.MULTIPLE });
		INC_FIELD_RULES.put(WorldTracerField.HC, new Object[] { 1, RepeatType.NONE });
		INC_FIELD_RULES.put(WorldTracerField.FS, new Object[] { 1, RepeatType.NONE });
		INC_FIELD_RULES.put(WorldTracerField.TK, new Object[] { 1, RepeatType.NONE });
		INC_FIELD_RULES.put(WorldTracerField.PB, new Object[] { 1, RepeatType.NONE });
		INC_FIELD_RULES.put(WorldTracerField.CC, new Object[] { 10, RepeatType.MULTIPLE });
		INC_FIELD_RULES.put(WorldTracerField.AG, new Object[] { 1, RepeatType.NONE });

		OHD_FIELD_RULES = new EnumMap<WorldTracerField, Object[]>(WorldTracerField.class);
		OHD_FIELD_RULES.put(WorldTracerField.CT, new Object[] { 1, RepeatType.NONE });
		OHD_FIELD_RULES.put(WorldTracerField.TN, new Object[] { 1, RepeatType.NONE });
		OHD_FIELD_RULES.put(WorldTracerField.PA, new Object[] { 1, RepeatType.MULTIPLE });
		OHD_FIELD_RULES.put(WorldTracerField.FD, new Object[] { 4, RepeatType.SAME_LINE });
		OHD_FIELD_RULES.put(WorldTracerField.RT, new Object[] { 5, RepeatType.SAME_LINE });
		OHD_FIELD_RULES.put(WorldTracerField.AG, new Object[] { 1, RepeatType.NONE });
		OHD_FIELD_RULES.put(WorldTracerField.CC, new Object[] { 1, RepeatType.NONE });
		OHD_FIELD_RULES.put(WorldTracerField.CP, new Object[] { 2, RepeatType.MULTIPLE });
		OHD_FIELD_RULES.put(WorldTracerField.FL, new Object[] { 1, RepeatType.NONE });
		OHD_FIELD_RULES.put(WorldTracerField.NM, new Object[] { 3, RepeatType.SAME_LINE });
		OHD_FIELD_RULES.put(WorldTracerField.EA, new Object[] { 1, RepeatType.NONE });
		OHD_FIELD_RULES.put(WorldTracerField.SL, new Object[] { 1, RepeatType.NONE });
		OHD_FIELD_RULES.put(WorldTracerField.BI, new Object[] { 1, RepeatType.NONE });

		CAH_FIELD_RULES = new EnumMap<WorldTracerField, Object[]>(WorldTracerField.class);
		CAH_FIELD_RULES.put(WorldTracerField.CS, new Object[] { 5, RepeatType.MULTIPLE });
		CAH_FIELD_RULES.put(WorldTracerField.NM, new Object[] { 3, RepeatType.SAME_LINE });
		CAH_FIELD_RULES.put(WorldTracerField.RL, new Object[] { 1, RepeatType.NONE });
		CAH_FIELD_RULES.put(WorldTracerField.FS, new Object[] { 1, RepeatType.NONE });
		CAH_FIELD_RULES.put(WorldTracerField.RC, new Object[] { 1, RepeatType.NONE });
		CAH_FIELD_RULES.put(WorldTracerField.AG, new Object[] { 1, RepeatType.NONE });

		COH_FIELD_RULES = new EnumMap<WorldTracerField, Object[]>(WorldTracerField.class);
		COH_FIELD_RULES.put(WorldTracerField.CS, new Object[] { 5, RepeatType.MULTIPLE });
		COH_FIELD_RULES.put(WorldTracerField.AG, new Object[] { 1, RepeatType.NONE });

		FWD_FIELD_RULES = new EnumMap<WorldTracerField, Object[]>(WorldTracerField.class);
		FWD_FIELD_RULES.put(WorldTracerField.AG, new Object[] { 1, RepeatType.NONE });
		FWD_FIELD_RULES.put(WorldTracerField.TN, new Object[] { 18, RepeatType.SAME_LINE });
		FWD_FIELD_RULES.put(WorldTracerField.XT, new Object[] { 18, RepeatType.SAME_LINE });
		FWD_FIELD_RULES.put(WorldTracerField.FO, new Object[] { 4, RepeatType.SAME_LINE });
		FWD_FIELD_RULES.put(WorldTracerField.FW, new Object[] { 5, RepeatType.SAME_LINE });
		FWD_FIELD_RULES.put(WorldTracerField.FB, new Object[] { 1, RepeatType.NONE });
		FWD_FIELD_RULES.put(WorldTracerField.FD, new Object[] { 4, RepeatType.SAME_LINE });
		FWD_FIELD_RULES.put(WorldTracerField.FS, new Object[] { 1, RepeatType.NONE });
		FWD_FIELD_RULES.put(WorldTracerField.FT, new Object[] { 1, RepeatType.NONE });
		FWD_FIELD_RULES.put(WorldTracerField.HC, new Object[] { 1, RepeatType.NONE });
		FWD_FIELD_RULES.put(WorldTracerField.NM, new Object[] { 10, RepeatType.SAME_LINE });
		FWD_FIELD_RULES.put(WorldTracerField.RC, new Object[] { 1, RepeatType.NONE });
		FWD_FIELD_RULES.put(WorldTracerField.RL, new Object[] { 1, RepeatType.NONE });
		FWD_FIELD_RULES.put(WorldTracerField.SI, new Object[] { 1, RepeatType.MANY_LINES });
		FWD_FIELD_RULES.put(WorldTracerField.TX, new Object[] { 10, RepeatType.SAME_LINE });
	}

	public String insertIncident(Incident incident) throws WorldTracerException {

		Map<WorldTracerField, List<String>> fieldMap = createFieldMap(incident);

		if (fieldMap == null) {
			throw new WorldTracerException("Unable to generate incident mapping");
		}

		String wt_id = null;

		wt_id = wtConnector.insertIncident(fieldMap, incident.getStationassigned().getCompany().getCompanyCode_ID(),
				incident.getStationassigned().getWt_stationcode());

		return wt_id;
	}

	public String closeIncident(Incident incident) throws WorldTracerException {
		Map<WorldTracerField, List<String>> fieldMap = createCloseFieldMap(incident);

		String wt_id = wtConnector.closeIncident(fieldMap, incident.getWt_id(), incident.getStationassigned()
				.getWt_stationcode());

		return wt_id;

	}

	public String insertOhd(OHD ohd) throws WorldTracerException {

		Map<WorldTracerField, List<String>> fieldMap = createFieldMap(ohd);

		if (fieldMap == null) {
			throw new WorldTracerException("Unable to generate ohd mapping");
		}

		String wt_id = null;

		wt_id = wtConnector.insertOhd(fieldMap, ohd.getFoundAtStation().getCompany().getCompanyCode_ID(), ohd
				.getHoldingStation().getWt_stationcode());

		return wt_id;
	}

	public String closeOHD(OHD ohd) throws WorldTracerException {
		Map<WorldTracerField, List<String>> fieldMap = createCloseFieldMap(ohd);

		if (fieldMap == null) {
			throw new WorldTracerException("Unable to generate close ohd mapping");
		}

		String wt_id = null;

		wt_id = wtConnector.closeOhd(fieldMap, ohd.getWt_id(), ohd.getHoldingStation().getWt_stationcode());

		return wt_id;
	}

	public void sendFwd(WT_FWD_Log fwd) throws WorldTracerException {
		Map<WorldTracerField, List<String>> fieldMap = createFieldMap(fwd);
		if (fieldMap == null) {
			throw new WorldTracerException("Unable to generate fwd mapping");
		}
		wtConnector.sendFwd(fieldMap, wtCompanyCode);
	}

	public void eraseActionFile(Worldtracer_Actionfiles waf) throws WorldTracerException {
		Worldtracer_Actionfiles temp = wtConnector.getActionFile(waf.getAirline(), waf.getStation(), waf.getAction_file_type(), waf.getDay(), waf.getItem_number());

		if(temp != null && waf.getAction_file_text() != null && waf.getAction_file_text().equals(temp.getAction_file_text())) {
			wtConnector.eraseActionFile(waf.getStation(), waf.getAirline(), waf.getAction_file_type(), waf.getDay(), waf
					.getItem_number());
		}
	}

	public List<Worldtracer_Actionfiles> getActionFiles(String airline, String station, ActionFileType actionFileType,
			int day) throws WorldTracerException {
		return wtConnector.getActionFiles(airline, station, actionFileType, day);
	}

	private Map<WorldTracerField, List<String>> createFieldMap(WT_FWD_Log fwd) {
		// TODO Auto-generated method stub
		// required fields
		Map<WorldTracerField, List<String>> result = new EnumMap<WorldTracerField, List<String>>(WorldTracerField.class);

		if (fwd.getItinerarylist() != null) {
			for (WT_FWD_Log_Itinerary itin : (List<WT_FWD_Log_Itinerary>) fwd.getItinerarylist()) {
				getFwdItineraryInfo(itin, result);
			}
		}
		return null;
	}

	private Map<WorldTracerField, List<String>> createCloseFieldMap(OHD ohd) {
		Map<WorldTracerField, List<String>> result = new EnumMap<WorldTracerField, List<String>>(WorldTracerField.class);
		addIncidentFieldEntry(WorldTracerField.AG, ohd.getAgent().getUsername().length() > 9 ? ohd.getAgent()
				.getUsername().substring(0, 9) : ohd.getAgent().getUsername() + "/"
				+ ohd.getAgent().getCompanycode_ID(), result);
		return null;
	}

	private Map<WorldTracerField, List<String>> createFieldMap(OHD ohd) throws WorldTracerException {
		if (ohd == null) {
			return null;
		}

		Map<WorldTracerField, List<String>> result = new EnumMap<WorldTracerField, List<String>>(WorldTracerField.class);

		if (ohd.getColor() == null || ohd.getColor().trim().length() <= 0 || ohd.getType() == null
				|| ohd.getType().trim().length() != 2) {
			throw new WorldTracerException("OHD missing color / type");
		}

		String colorType = ohd.getColor().trim() + ohd.getType().trim();
		colorType += XDescElementsBMO.getXdescelementcode(ohd.getXdescelement_ID_1());
		colorType += XDescElementsBMO.getXdescelementcode(ohd.getXdescelement_ID_2());
		colorType += XDescElementsBMO.getXdescelementcode(ohd.getXdescelement_ID_3());
		addIncidentFieldEntry(WorldTracerField.CT, colorType, result);

		if (ohd.getClaimnum() != null && ohd.getClaimnum().trim().length() > 0) {
			addClaimCheckNum(ohd.getClaimnum(), result, ohd.getAgent().getCompanycode_ID());
		}

		addIncidentFieldEntry(WorldTracerField.BI, ohd.getManufacturer(), result);

		addIncidentFieldEntry(WorldTracerField.PR, ohd.getRecord_locator(), result);

		addIncidentFieldEntry(WorldTracerField.SL, ohd.getStorage_location(), result);

		if (ohd.getMembership() != null) {
			addIncidentFieldEntry(WorldTracerField.FL, ohd.getMembership().getMembershipnum(), result);
		}

		if (ohd.getItinerary() == null || ohd.getItinerary().size() == 0) {
			throw new WorldTracerException("OHD missing itinerary");
		}
		for (OHD_Itinerary itin : (Iterable<OHD_Itinerary>) ohd.getItinerary()) {
			getOhdItineraryInfo(itin, result);
		}

		for (OHD_Passenger p : (Iterable<OHD_Passenger>) ohd.getPassengers()) {
			getOhdPaxInfo(p, result);
		}

		if (ohd.getItems() != null) {
			ArrayList<String> temp = new ArrayList<String>();
			for (OHD_Inventory inv : (Iterable<OHD_Inventory>) ohd.getItems()) {
				temp.add(inv.getCategory() + "/" + wtClear(inv.getDescription()));
			}
			String entry = StringUtils.join(temp, FIELD_SEP + CONTINUATION + " ");
			addIncidentFieldEntry(WorldTracerField.CC, entry, result);
		}

		addIncidentFieldEntry(WorldTracerField.AG, ohd.getAgent().getUsername().length() > 9 ? ohd.getAgent()
				.getUsername().substring(0, 9) : ohd.getAgent().getUsername() + "/"
				+ ohd.getAgent().getCompanycode_ID(), result);
		return result;

	}

	private void getOhdPaxInfo(OHD_Passenger p, Map<WorldTracerField, List<String>> result) {
		if (p.getLastname() == null || p.getLastname().trim().length() <= 0) {
			return;
		}
		// add the name
		addIncidentFieldEntry(WorldTracerField.NM, p.getLastname().trim(), result);

		// add the initials
		String initials = null;
		if (p.getFirstname() != null && p.getFirstname().trim().length() > 0) {
			initials = p.getFirstname().trim().substring(0, 1) + p.getLastname().trim().substring(0, 1);
		} else {
			initials = p.getLastname().trim().substring(0, 1);
		}
		addIncidentFieldEntry(WorldTracerField.IT, initials, result);

		// add permanent address
		OHD_Address address = p.getAddress(0);
		if (address != null) {
			ArrayList<String> addrPieces = new ArrayList<String>();
			if (address.getAddress1() != null)
				addrPieces.add(address.getAddress1().trim());
			if (address.getCity() != null)
				addrPieces.add(address.getCity().trim());
			if (address.getState() != null)
				addrPieces.add(address.getState().trim());
			else if (address.getProvince() != null)
				addrPieces.add(address.getProvince().trim());
			if (address.getZip() != null)
				addrPieces.add(address.getZip().trim());
			String value = StringUtils.join(addrPieces, " ").replaceAll("\\s+", " ");
			addIncidentFieldEntry(WorldTracerField.PA, wtClear(value), result);

			// add email
			addIncidentFieldEntry(WorldTracerField.EA, wtEscape(address.getEmail()), result);

			// add home phone
			addIncidentFieldEntry(WorldTracerField.PN, wtPhone(address.getHomephone()), result);

			// add work phone
			addIncidentFieldEntry(WorldTracerField.TP, wtPhone(address.getWorkphone()), result);

			// add cell phone
			addIncidentFieldEntry(WorldTracerField.CP, wtPhone(address.getMobile()), result);

			// add fax num
			addIncidentFieldEntry(WorldTracerField.FX, wtPhone(address.getAltphone()), result);

			// add country
			addIncidentFieldEntry(WorldTracerField.CO, address.getCountrycode_ID(), result);
		}

	}

	protected Map<WorldTracerField, List<String>> createFieldMap(Incident ntIncident) {

		if (ntIncident == null) {
			return null;
		}
		Map<WorldTracerField, List<String>> result = new EnumMap<WorldTracerField, List<String>>(WorldTracerField.class);

		if (ntIncident.getPassenger_list() != null) {
			for (Passenger p : (List<Passenger>) ntIncident.getPassenger_list()) {
				getPassengerInfo(p, result);
			}
		}

		// num passengers
		addIncidentFieldEntry(WorldTracerField.NP, Integer.toString(ntIncident.getNumpassengers()), result);

		if (ntIncident.getItinerary_list() != null) {
			for (Itinerary i : (List<Itinerary>) ntIncident.getItinerary_list()) {
				getItineraryInfo(i, result);
			}
		}

		if (ntIncident.getItemlist() != null) {
			for (Item i : (List<Item>) ntIncident.getItemlist()) {
				getItemInfo(i, result, ntIncident.getStationassigned().getCompany().getCompanyCode_ID());
				if (i.getInventorylist() != null) {
					ArrayList<String> temp = new ArrayList<String>();
					for (Item_Inventory inv : (List<Item_Inventory>) i.getInventorylist()) {
						temp.add(inv.getCategory() + "/" + wtClear(inv.getDescription()));
					}
					String entry = String.format("%02d%s", i.getBagnumber() + 1, StringUtils.join(temp, FIELD_SEP
							+ CONTINUATION + " "));
					addIncidentFieldEntry(WorldTracerField.CC, entry, result);
				}
			}
		}

		if (ntIncident.getTicketnumber() != null && ntIncident.getTicketnumber().trim().length() > 0) {
			addIncidentFieldEntry(WorldTracerField.TK, ntIncident.getTicketnumber().trim(), result);
			addIncidentFieldEntry(WorldTracerField.PB, ntIncident.getTicketnumber().trim(), result);

		} else {
			addIncidentFieldEntry(WorldTracerField.PB, "0000", result);
		}
		addIncidentFieldEntry(WorldTracerField.AG, ntIncident.getAgent().getUsername().length() > 9 ? ntIncident
				.getAgent().getUsername().substring(0, 9) : ntIncident.getAgent().getUsername() + "/"
				+ ntIncident.getAgent().getCompanycode_ID(), result);
		return result;
	}

	private Map<WorldTracerField, List<String>> createCloseFieldMap(Incident incident) {
		Map<WorldTracerField, List<String>> result = new EnumMap<WorldTracerField, List<String>>(WorldTracerField.class);
		String cs_fmt = "%02d %s/%s%1.2f";
		int claimCount = 0;
		if (incident.getClaims() != null) {
			String cost;

			for (Claim claim : (Iterable<Claim>) incident.getClaims()) {
				if (claim.getExpenses() != null) {
					for (ExpensePayout expense : (Iterable<ExpensePayout>) claim.getExpenses()) {
						if (expense.getApproval_date() != null && expense.getCurrency_ID() != null) {
							claimCount++;
							if ("ADV".equals(expense.getPaycode())) {
								cost = String.format(cs_fmt, claimCount, "A", expense.getCurrency_ID(), expense
										.getCheckamt());
							} else if ("DEL".equals(expense.getPaycode())) {
								cost = String.format(cs_fmt, claimCount, "D", expense.getCurrency_ID(), expense
										.getCheckamt());
							} else if ("FIN".equals(expense.getPaycode())) {
								cost = String.format(cs_fmt, claimCount, "F", expense.getCurrency_ID(), expense
										.getCheckamt());
							} else if ("INS".equals(expense.getPaycode())) {
								cost = String.format(cs_fmt, claimCount, "I", expense.getCurrency_ID(), expense
										.getCheckamt());
							} else {
								cost = String.format(cs_fmt, claimCount, "X", expense.getCurrency_ID(), expense
										.getCheckamt());
							}
							addIncidentFieldEntry(WorldTracerField.CS, cost, result);
						}
					}
				}
			}
		}
		// see if we added a CS
		if (claimCount == 0) {
			addIncidentFieldEntry(WorldTracerField.CS, "01 X/USD0.00", result);
		}

		if (incident.getLoss_code() != 0) {
			addIncidentFieldEntry(WorldTracerField.RL, Integer.toString(incident.getLoss_code()), result);
			Company_specific_irregularity_code csic = AdminUtils.getLossCode(incident.getLoss_code(),
					TracingConstants.LOST_DELAY, TracingConstants.DEFAULT_LOCALE, AdminUtils.getCompany(wtCompanyCode));
			addIncidentFieldEntry(WorldTracerField.RC, csic.getDescription(), result);
		} else {
			addIncidentFieldEntry(WorldTracerField.RL, "79", result);
			addIncidentFieldEntry(WorldTracerField.RC, "Created in error", result);
		}
		if (incident.getFaultstation() != null) {
			addIncidentFieldEntry(WorldTracerField.FS, StationBMO.getStationByCode(incident.getFaultstationcode())
					.getWt_stationcode(), result);
		} else {
			addIncidentFieldEntry(WorldTracerField.FS, incident.getStationassigned().getWt_stationcode(), result);
		}
		addIncidentFieldEntry(WorldTracerField.AG, incident.getAgent().getUsername().length() > 9 ? incident.getAgent()
				.getUsername().substring(0, 9) : incident.getAgent().getUsername() + "/"
				+ incident.getAgent().getCompanycode_ID(), result);
		return result;
	}

	protected void getItemInfo(Item item, Map<WorldTracerField, List<String>> result, String companyCode) {

		if (item.getColor() == null || item.getColor().trim().length() <= 0 || item.getBagtype() == null
				|| item.getBagtype().trim().length() != 2) {
			return;
		}
		String colorType = item.getColor().trim() + item.getBagtype().trim();
		colorType += XDescElementsBMO.getXdescelementcode(item.getXdescelement_ID_1());
		colorType += XDescElementsBMO.getXdescelementcode(item.getXdescelement_ID_2());
		colorType += XDescElementsBMO.getXdescelementcode(item.getXdescelement_ID_3());
		addIncidentFieldEntry(WorldTracerField.CT, colorType, result);

		addIncidentFieldEntry(WorldTracerField.BI, item.getManufacturer(), result);

		if (item.getClaimchecknum() != null && item.getClaimchecknum().trim().length() > 0) {
			addClaimCheckNum(item.getClaimchecknum(), result, companyCode);
		}

	}

	private void addClaimCheckNum(String claimCheck, Map<WorldTracerField, List<String>> result, String companyCode) {
		Pattern wt_patt = Pattern.compile("[a-zA-Z]{2}(\\d{6})(\\D|$)");
		Matcher m = wt_patt.matcher(claimCheck.trim());
		if (m.find()) {
			addIncidentFieldEntry(WorldTracerField.TN, claimCheck.trim(), result);
		} else {
			Pattern base_patt = Pattern.compile("(\\d{6})(\\D|$)");
			m = base_patt.matcher(claimCheck.trim());
			if (m.find()) {
				addIncidentFieldEntry(WorldTracerField.TN, companyCode + m.group(1), result);
			}
		}
	}

	protected void getFwdItineraryInfo(WT_FWD_Log_Itinerary itin, Map<WorldTracerField, List<String>> result) {

		// make sure it's a valid itinerary
		if (itin.getAirline() == null || itin.getAirline().trim().length() <= 0 || itin.getFlightnum() == null
				|| itin.getFlightnum().trim().length() <= 0 || itin.getLegfrom() == null
				|| itin.getLegfrom().trim().length() <= 0 || itin.getLegto() == null
				|| itin.getLegto().trim().length() <= 0 || itin.getDepartdate() == null) {
			return;
		}

		String fo = itin.getAirline() + itin.getFlightnum() + "/" + ITIN_DATE_FORMAT.format(itin.getDepartdate());
		addIncidentFieldEntry(WorldTracerField.FO, fo, result);
		List<String> routing = result.get(WorldTracerField.FW);
		if (routing != null && !routing.get(routing.size() - 1).equalsIgnoreCase(itin.getLegfrom().trim())) {
			addIncidentFieldEntry(WorldTracerField.FW, itin.getLegfrom().trim() + "", result); // TODO
		}
		addIncidentFieldEntry(WorldTracerField.FW, itin.getLegto().trim() + "", result); // TODO

	}

	/**
	 * 
	 * @param itin
	 * @param result
	 */
	protected void getItineraryInfo(Itinerary itin, Map<WorldTracerField, List<String>> result) {

		// make sure it's a valid itinerary
		if (itin.getAirline() == null || itin.getAirline().trim().length() <= 0 || itin.getFlightnum() == null
				|| itin.getFlightnum().trim().length() <= 0 || itin.getLegfrom() == null
				|| itin.getLegfrom().trim().length() <= 0 || itin.getLegto() == null
				|| itin.getLegto().trim().length() <= 0 || itin.getDepartdate() == null) {
			return;
		}

		String fd = itin.getAirline() + itin.getFlightnum() + "/" + ITIN_DATE_FORMAT.format(itin.getDepartdate());
		if (itin.getItinerarytype() == TracingConstants.BAGGAGE_ROUTING) {
			addIncidentFieldEntry(WorldTracerField.BR, fd, result);
		} else if (itin.getItinerarytype() == TracingConstants.PASSENGER_ROUTING) {
			addIncidentFieldEntry(WorldTracerField.FD, fd, result);
			List<String> routing = result.get(WorldTracerField.RT);
			if (routing == null || !routing.get(routing.size() - 1).equalsIgnoreCase(itin.getLegfrom().trim())) {
				addIncidentFieldEntry(WorldTracerField.RT, itin.getLegfrom().trim(), result);
			}
			addIncidentFieldEntry(WorldTracerField.RT, itin.getLegto().trim(), result);
		}

	}

	protected void getOhdItineraryInfo(OHD_Itinerary itin, Map<WorldTracerField, List<String>> result) {
		// make sure it's a valid itinerary
		if (itin.getAirline() == null || itin.getAirline().trim().length() <= 0 || itin.getFlightnum() == null
				|| itin.getFlightnum().trim().length() <= 0 || itin.getLegfrom() == null
				|| itin.getLegfrom().trim().length() <= 0 || itin.getLegto() == null
				|| itin.getLegto().trim().length() <= 0 || itin.getDepartdate() == null) {
			return;
		}

		String fd = itin.getAirline() + itin.getFlightnum() + "/" + ITIN_DATE_FORMAT.format(itin.getDepartdate());

		addIncidentFieldEntry(WorldTracerField.FD, fd, result);
		List<String> routing = result.get(WorldTracerField.RT);
		if (routing == null || !routing.get(routing.size() - 1).equalsIgnoreCase(itin.getLegfrom().trim())) {
			addIncidentFieldEntry(WorldTracerField.RT, itin.getLegfrom().trim(), result);
		}
		addIncidentFieldEntry(WorldTracerField.RT, itin.getLegto().trim(), result);
	}

	protected void getPassengerInfo(Passenger p, Map<WorldTracerField, List<String>> result) {
		if (p.getLastname() == null || p.getLastname().trim().length() <= 0) {
			return;
		}
		// add the name
		addIncidentFieldEntry(WorldTracerField.NM, p.getLastname().trim(), result);

		// add the initials
		String initials = null;
		if (p.getFirstname() != null && p.getFirstname().trim().length() > 0) {
			initials = p.getFirstname().trim().substring(0, 1) + p.getLastname().trim().substring(0, 1);
		} else {
			initials = p.getLastname().trim().substring(0, 1);
		}
		addIncidentFieldEntry(WorldTracerField.IT, initials, result);

		// add the passenger title (salutation)
		addIncidentFieldEntry(WorldTracerField.PT, p.getSalutationdesc(), result);

		// add the frequent flier class status
		addIncidentFieldEntry(WorldTracerField.PS, p.getAirlinememstatus(), result);

		// add the frequent flier num
		addIncidentFieldEntry(WorldTracerField.FL, p.getAirlinememnumber(), result);

		// add permanent address
		Address address = p.getAddress(0);
		if (address != null) {
			ArrayList<String> addrPieces = new ArrayList<String>();
			if (address.getAddress1() != null)
				addrPieces.add(address.getAddress1().trim());
			if (address.getCity() != null)
				addrPieces.add(address.getCity().trim());
			if (address.getState() != null)
				addrPieces.add(address.getState().trim());
			else if (address.getProvince() != null)
				addrPieces.add(address.getProvince().trim());
			if (address.getZip() != null)
				addrPieces.add(address.getZip().trim());
			String value = StringUtils.join(addrPieces, " ").replaceAll("\\s+", " ");
			addIncidentFieldEntry(WorldTracerField.PA, wtClear(value), result);

			// add email
			addIncidentFieldEntry(WorldTracerField.EA, wtEscape(address.getEmail()), result);

			// add home phone
			addIncidentFieldEntry(WorldTracerField.PN, wtPhone(address.getHomephone()), result);

			// add work phone
			addIncidentFieldEntry(WorldTracerField.TP, wtPhone(address.getWorkphone()), result);

			// add cell phone
			addIncidentFieldEntry(WorldTracerField.CP, wtPhone(address.getMobile()), result);

			// add fax num
			addIncidentFieldEntry(WorldTracerField.FX, wtPhone(address.getAltphone()), result);

			// add country
			addIncidentFieldEntry(WorldTracerField.CO, address.getCountrycode_ID(), result);
		}
	}
	
	

	protected void addIncidentFieldEntry(WorldTracerField key, String value, Map<WorldTracerField, List<String>> result) {
		if (value == null || value.trim().length() <= 0) {
			return;
		}

		List<String> entryList = result.get(key);
		if (entryList == null) {
			entryList = new ArrayList<String>();
			entryList.add(value);
			result.put(key, entryList);
		} else {
			entryList.add(value);
		}
	}

	public void setWtConnector(WorldTracerConnector wtConnector) {
		this.wtConnector = wtConnector;
	}

	public static String wtPhone(String rawText) {
		return rawText.replaceAll("\\D", "");
	}

	public static String wtClear(String rawText) {
		return rawText.replaceAll("[" + FIELD_SEP + ENTRY_SEP + "]", " ");
	}

	public static String wtEscape(String rawText) {
		return rawText.replace("@", "/A/").replace(".", "/D/").replace("_", "/U/").replace("~", "/T/").replace("+",
				"/P/");
	}

	public void setWtCompanyCode(String wtCompanyCode) {
		this.wtCompanyCode = wtCompanyCode;
	}


}
