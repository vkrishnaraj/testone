package com.bagnet.nettracer.wt.svc;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Configurable;

import com.bagnet.nettracer.aop.WorldTracerTx;
import com.bagnet.nettracer.exceptions.BagtagException;
import com.bagnet.nettracer.tracing.bmo.CategoryBMO;
import com.bagnet.nettracer.tracing.bmo.LossCodeBMO;
import com.bagnet.nettracer.tracing.bmo.StationBMO;
import com.bagnet.nettracer.tracing.bmo.XDescElementsBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Address;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.BDO;
import com.bagnet.nettracer.tracing.db.BDO_Passenger;
import com.bagnet.nettracer.tracing.db.Company_specific_irregularity_code;
import com.bagnet.nettracer.tracing.db.ExpensePayout;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Incident_Claimcheck;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.Item_Inventory;
import com.bagnet.nettracer.tracing.db.Itinerary;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.OHD_Address;
import com.bagnet.nettracer.tracing.db.OHD_CategoryType;
import com.bagnet.nettracer.tracing.db.OHD_Inventory;
import com.bagnet.nettracer.tracing.db.OHD_Itinerary;
import com.bagnet.nettracer.tracing.db.OHD_Passenger;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles;
import com.bagnet.nettracer.tracing.db.WorldTracerFile.WTStatus;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles.ActionFileType;
import com.bagnet.nettracer.tracing.db.wt.ActionFileCount;
import com.bagnet.nettracer.tracing.db.wtq.WtqFwd;
import com.bagnet.nettracer.tracing.db.wtq.WtqFwdGeneral;
import com.bagnet.nettracer.tracing.db.wtq.WtqFwdOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqRequestOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqRequestQoh;
import com.bagnet.nettracer.tracing.db.wtq.WtqSegment;
import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.StringUtils;
import com.bagnet.nettracer.tracing.utils.lookup.LookupAirlineCodes;
import com.bagnet.nettracer.wt.WTIncident;
import com.bagnet.nettracer.wt.WTOHD;
import com.bagnet.nettracer.wt.WorldTracerException;
import com.bagnet.nettracer.wt.connector.WorldTracerConnector;
import com.bagnet.nettracer.wt.svc.WorldTracerRule.Format;
import com.bagnet.nettracer.wt.utils.ActionFileDto;

@Configurable("wtService")
public class DefaultWorldTracerService implements WorldTracerService {

	private static final DateFormat ITIN_DATE_FORMAT = new SimpleDateFormat("ddMMM", Locale.US);

	private static final Logger logger = Logger.getLogger(DefaultWorldTracerService.class);

	private static final Pattern FLIGHTNUM_FORMAT = Pattern.compile("[1-9]\\d{0,3}[a-zA-Z]?");

	private WorldTracerConnector wtConnector;

	private String wtCompanyCode;

	private static final List<String> wt_mats = Arrays.asList("D", "L", "M", "R", "T");
	private static final List<String> wt_descs = Arrays.asList("D", "L", "M", "R", "T", "B", "K", "C", "H", "S", "W",
			"X");

	public static final String FIELD_SEP = ".";
	public static final String ENTRY_SEP = "/";
	public static final String CONTINUATION = "-";

	private static final List<String> VALID_BAG_TYPES = Arrays.asList(new String[] { "01", "02", "03", "05", "06",
			"07", "08", "09", "10", "12", "20", "22", "23", "25", "26", "27", "28", "29", "50", "51", "52", "53", "54",
			"55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "71", "72", "73",
			"74", "75", "81", "82", "83", "85", "89", "90", "92", "93", "94", "95", "96", "97", "98", "99" });

	public static enum RepeatType {
		NONE, SAME_LINE, MANY_LINES, MULTIPLE
	}
	public static final WorldTracerRule<String> BASIC_RULE = new BasicRule(1, 57, 1, Format.FREE_FLOW);
	private static final String DEFAULT_BAG_TYPE = "99";

	private static final String UNKNOWN_AIRLINE = "YY";

	@WorldTracerTx(type = TxType.CREATE_AHL)
	public String insertIncident(Incident incident) throws WorldTracerException {
		String wt_id = null;

		try {
			Map<WorldTracerField, List<String>> fieldMap = createFieldMap(incident);  //Create error, corrected field IT

			if (fieldMap == null) {
				throw new WorldTracerException("Unable to generate incident mapping");
			}
			wt_id = wtConnector.insertIncident(fieldMap,incident.getStationassigned().getCompany().getCompanyCode_ID(), incident.getStationassigned().getWt_stationcode());

		} catch (Exception e) {
			if (e instanceof WorldTracerException) {
				throw (WorldTracerException) e;
			} else {
				throw new WorldTracerException(e.getMessage(), e);
			}
		}
		return wt_id;
	}

	@WorldTracerTx(type = TxType.CLOSE_AHL)
	public String closeIncident(Incident incident) throws WorldTracerException {

		if (incident.getWt_id() == null) {
			throw new WorldTracerException("no associated worldtracer file");
		}
		Map<WorldTracerField, List<String>> fieldMap = createCloseFieldMap(incident);

		String wt_id = wtConnector.closeIncident(fieldMap, incident.getWtFile().getWt_id(), incident
				.getStationassigned().getWt_stationcode());

		return wt_id;

	}

	@WorldTracerTx(type = TxType.CREATE_OHD)
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

	@WorldTracerTx(type = TxType.CLOSE_OHD)
	public String closeOHD(OHD ohd) throws WorldTracerException {
		if (ohd.getWt_id() == null) {
			throw new WorldTracerException("Can't close ohd, no associated wt file");
		}
		Map<WorldTracerField, List<String>> fieldMap = createCloseFieldMap(ohd);

		if (fieldMap == null) {
			throw new WorldTracerException("Unable to generate close ohd mapping");
		}

		String wt_id = null;

		wt_id = wtConnector.closeOhd(fieldMap, ohd.getWt_id(), ohd.getHoldingStation().getWt_stationcode());

		return wt_id;
	}

	@WorldTracerTx(type = TxType.REINSTATE_AHL)
	public String reinstateIncident(Incident incident) throws WorldTracerException {
		if (incident.getWt_id() == null) {
			throw new WorldTracerException("no associated worldtracer file");
		}
		wtConnector.reinstateAHL(incident.getWt_id(), getAgentEntry(incident.getAgent()));
		return incident.getWt_id();
	}

	@WorldTracerTx(type = TxType.REINSTATE_OHD)
	public String reinstateOhd(OHD ohd) throws WorldTracerException {
		if (ohd.getWt_id() == null) {
			throw new WorldTracerException("Can't reinstate ohd, no associated wt file");
		}
		wtConnector.reinstateOHD(ohd.getWt_id(), getAgentEntry(ohd.getAgent()));
		return ohd.getWt_id();
	}

	@WorldTracerTx(type = TxType.SUSPEND_AHL)
	public String suspendIncident(Incident incident) throws WorldTracerException {
		if (incident.getWt_id() == null) {
			throw new WorldTracerException("no associated worldtracer file");
		}
		wtConnector.suspendAHL(incident.getWt_id(), getAgentEntry(incident.getAgent()));
		return incident.getWt_id();
	}

	@WorldTracerTx(type = TxType.SUSPEND_OHD)
	public String suspendOhd(OHD ohd) throws WorldTracerException {
		if (ohd.getWt_id() == null) {
			throw new WorldTracerException("Can't suspend ohd, no associated wt file");
		}
		wtConnector.suspendOHD(ohd.getWt_id(), getAgentEntry(ohd.getAgent()));
		return ohd.getWt_id();
	}

	@WorldTracerTx(type = TxType.CREATE_BDO)
	public String insertBdo(BDO bdo) throws WorldTracerException {

		if (bdo.getStation().getWt_stationcode() == null || bdo.getStation().getWt_stationcode().trim().length() < 1) {
			throw new WorldTracerException("BDO station " + bdo.getStation().getStationcode()
					+ " does not have a world tracer station");
		}
		if (bdo.getIncident() == null || bdo.getIncident().getWt_id() == null) {
			throw new WorldTracerException("Cannot export BDO: " + bdo.getBDO_ID()
					+ " - no associated WorldTracer Files");
		}
		Map<WorldTracerField, List<String>> fieldMap = createBdoFieldMap(bdo);

		if (fieldMap == null) {
			throw new WorldTracerException("Unable to generate Bdo mapping, ID: " + bdo.getBDO_ID());
		}

		return wtConnector.createBdo(fieldMap, bdo.getIncident() != null ? bdo.getIncident().getWt_id() : null, bdo
				.getOhd() != null ? bdo.getOhd().getWt_id() : null, bdo.getDelivercompany(), bdo.getStation());
	}

	private Map<WorldTracerField, List<String>> createBdoFieldMap(BDO bdo) throws WorldTracerException {
		Map<WorldTracerField, List<String>> result = new EnumMap<WorldTracerField, List<String>>(WorldTracerField.class);

		if (bdo.getItems() == null || bdo.getItems().size() < 1) {
			throw new WorldTracerException("Can't send a bdo with no Items for bdo: " + bdo.getBDO_ID());
		}
		for (Item item : (Iterable<Item>) bdo.getItems()) {
			getItemInfo(item, result, false);
		}

		if (bdo.getDeliverydate() != null) {
			addIncidentFieldEntry(WorldTracerField.DD, ITIN_DATE_FORMAT.format(bdo.getDeliverydate()), result);
		}

		if (result.get(WorldTracerField.DD) == null) {
			throw new WorldTracerException("Could not export BDO due to invalid delivery date. Id: " + bdo.getBDO_ID());
		}

		addIncidentFieldEntry(WorldTracerField.LD, bdo.getDelivery_comments(), result);

		addIncidentFieldEntry(WorldTracerField.AG, getAgentEntry(bdo.getAgent()), result);

		if (bdo.getPassengers() != null && bdo.getPassengers().size() > 0) {
			for (BDO_Passenger p : (Iterable<BDO_Passenger>) bdo.getPassengers()) {
				getBdoPaxInfo(p, result);
			}
		}

		return result;
	}

	private void getBdoPaxInfo(BDO_Passenger pax, Map<WorldTracerField, List<String>> result) {
		addIncidentFieldEntry(WorldTracerField.NM, pax.getLastname(), result);
		String addy = StringUtils.join(" ", elimNulls(pax.getAddress1()), elimNulls(pax.getAddress2()),
				elimNulls(pax.getCity()),
				elimNulls(pax.getState_ID()).equals("") ? elimNulls(pax.getProvince()) : elimNulls(pax.getState_ID()),
				elimNulls(pax.getZip())).trim().replaceAll("\\s+", " ");
		addIncidentFieldEntry(WorldTracerField.DA, addy, result);

		addIncidentFieldEntry(WorldTracerField.PN, wtPhone(pax.getHomephone()), result);
		addIncidentFieldEntry(WorldTracerField.TP, wtPhone(pax.getWorkphone()), result);
		addIncidentFieldEntry(WorldTracerField.CP, wtPhone(pax.getMobile()), result);
	}

	private String elimNulls(String str) {
		if (str != null)
			return str.trim();
		return "";
	}

	@WorldTracerTx(type = TxType.ERASE_AF)
	public void eraseActionFile(Worldtracer_Actionfiles waf) throws WorldTracerException {
		wtConnector.eraseActionFile(waf.getStation(), waf.getAirline(), waf.getAction_file_type(),
				waf.getDay(), waf.getItem_number());
	}

	@WorldTracerTx(type = TxType.IMPORT_AHL)
	public Incident getIncidentForAHL(String wt_id, WTStatus status, Agent user) throws WorldTracerException {
		String result = wtConnector.findAHL(wt_id);
		//Incident foundinc = WTIncident.parseWTIncident(result, status, user);
		Incident foundinc = WTIncident.parseWTIncident(result, status, user);
		return foundinc;
	}

	@WorldTracerTx(type = TxType.IMPORT_OHD)
	public OHD getOhdforOhd(String wt_id, WTStatus status, Agent user) throws WorldTracerException {
		String result = wtConnector.findOHD(wt_id);
		// for now show all as active
		//OHD foundohd = WTOHD.parseWTOHD(result, WTStatus.ACTIVE);
		OHD foundohd = WTOHD.parseWTOHD(result, WTStatus.ACTIVE, user);
		return foundohd;
	}

	@WorldTracerTx(type = TxType.FWD_OHD)
	public String forwardOhd(WtqFwdOhd fwd) throws WorldTracerException {

		if (fwd.getOhd() == null || fwd.getOhd().getWt_id() == null) {
			throw new WorldTracerException("No associated worldtracer file");
		}
		Map<WorldTracerField, List<String>> fieldMap = createFwdFieldMap(fwd);

		if (fieldMap == null) {
			throw new WorldTracerException("Unable to generate fwdOHD mapping");
		}

		String result = null;

		result = wtConnector.forwardOhd(fieldMap, fwd.getOhd().getWt_id(), fwd.getMatchingAhl());

		return result;
	}

	@WorldTracerTx(type = TxType.FWD_GENERAL)
	public String sendFwdMsg(WtqFwdGeneral fwd, Agent defaultAgent) throws WorldTracerException {
		Map<WorldTracerField, List<String>> fieldMap = createFwdFieldMap(fwd);

		if (fieldMap == null) {
			throw new WorldTracerException("Unable to generate fwdOHD mapping");
		}

		if (fwd.getLossCode() != 0) {
			addIncidentFieldEntry(WorldTracerField.RL, Integer.toString(fwd.getLossCode()), fieldMap);
		}

		addIncidentFieldEntry(WorldTracerField.RC, fwd.getLossComments(), fieldMap);
		
		String from_station = fwd.getFrom_station();
		if(from_station == null || from_station.trim().length() < 1) {
			from_station = defaultAgent.getStation().getWt_stationcode();
		}

		String result = wtConnector.sendFwd(fieldMap, from_station, fwd.getAgent()
				.getCompanycode_ID());
		return result;
	}

	@WorldTracerTx(type = TxType.AMEND_AHL)
	public String amendAhl(Incident incident) throws WorldTracerException {
		if (incident.getWt_id() == null) {
			throw new WorldTracerException("no associated worldtracer file");
		}
		String wt_id = null;

		try {
			Map<WorldTracerField, List<String>> fieldMap = createFieldMap(incident);

			if (fieldMap == null) {
				throw new WorldTracerException("Unable to generate incident mapping");
			}

			wt_id = wtConnector.amendAhl(fieldMap, incident.getWt_id());

		} catch (Exception e) {
			if (e instanceof WorldTracerException) {
				throw (WorldTracerException) e;
			} else {
				throw new WorldTracerException(e.getMessage(), e);
			}
		}
		return wt_id;
	}

	@WorldTracerTx(type = TxType.AMEND_OHD)
	public String amendOhd(OHD ohd) throws WorldTracerException {
		if (ohd.getWt_id() == null) {
			throw new WorldTracerException("no associated worldtracer file");
		}
		Map<WorldTracerField, List<String>> fieldMap = createFieldMap(ohd);

		if (fieldMap == null) {
			throw new WorldTracerException("Unable to generate ohd mapping");
		}

		String result = null;

		result = wtConnector.amendOhd(fieldMap, ohd.getWt_id());

		return result;
	}

	@WorldTracerTx(type = TxType.REQUEST_QOH)
	public String requestQoh(WtqRequestQoh wtq) throws WorldTracerException {
		if (wtq.getIncident() == null || wtq.getIncident().getWt_id() == null) {
			throw new WorldTracerException("no associated WT file");
		}
		if (wtq.getBagTagNumber() == null || wtq.getBagTagNumber().trim().length() < 1 || wtq.getFromAirline() == null
				|| wtq.getFromAirline().trim().length() < 1 || wtq.getFromStation() == null
				|| wtq.getFromStation().trim().length() < 1) {
			throw new WorldTracerException("Unable to request QOH. BagTag, Airline, and Station are required");
		}
		Map<WorldTracerField, List<String>> fieldMap = createFieldMap(wtq);
		addConvertedTag(wtq.getBagTagNumber(), WorldTracerField.TN, fieldMap, wtq.getAgent().getCompanycode_ID());

		return wtConnector.requestQoh(wtq.getFromStation().toUpperCase(), wtq.getFromAirline().toUpperCase(), wtq
				.getIncident().getWt_id(), fieldMap);
	}

	@WorldTracerTx(type = TxType.REQUEST_OHD)
	public String requestOhd(WtqRequestOhd roh) throws WorldTracerException {
		if (roh.getIncident() == null || roh.getIncident().getWt_id() == null) {
			throw new WorldTracerException("no associated WT file");
		}
		Map<WorldTracerField, List<String>> fieldMap = createFieldMap(roh);
		String result = wtConnector.requestOhd(roh.getWt_ohd(), roh.getIncident().getWt_id(), fieldMap);
		return result;
	}

	private Map<WorldTracerField, List<String>> createFieldMap(WtqRequestOhd roh) {
		if (roh == null) {
			return null;
		}

		Map<WorldTracerField, List<String>> result = new EnumMap<WorldTracerField, List<String>>(WorldTracerField.class);
		if (roh.getIncident() != null && roh.getIncident().getPassenger_list() != null
				&& roh.getIncident().getPassenger_list().size() > 0) {
			addIncidentFieldEntry(WorldTracerField.NM, ((Passenger) roh.getIncident().getPassenger_list().get(0))
					.getLastname(), result);
		}
		if (roh.getTeletypes() != null) {
			for (String tt : roh.getTeletypes()) {
				addIncidentFieldEntry(WorldTracerField.TX, tt, result);
			}
		}

		if (roh.getFurtherInfo() != null && roh.getFurtherInfo().trim().length() > 0) {
			addIncidentFieldEntry(WorldTracerField.FI, roh.getFurtherInfo(), result);
			addIncidentFieldEntry(WorldTracerField.TI, roh.getFurtherInfo(), result);
			
		}

		addIncidentFieldEntry(WorldTracerField.AG, getAgentEntry(roh.getAgent()), result);

		return result;

	}

	private Map<WorldTracerField, List<String>> createCloseFieldMap(OHD ohd) {
		Map<WorldTracerField, List<String>> result = new EnumMap<WorldTracerField, List<String>>(WorldTracerField.class);
		addIncidentFieldEntry(WorldTracerField.AG, getAgentEntry(ohd.getAgent()), result);
		return result;
	}

	private static String getAgentEntry(Agent ag) {
				if(ag != null)
			return (ag.getUsername().length() > 7 ? ag.getUsername().substring(0, 7) : ag.getUsername()) + "/"
				+ ag.getCompanycode_ID();
		return "NTRACER";
	}

	private Map<WorldTracerField, List<String>> createFwdFieldMap(WtqFwd fwd) throws WorldTracerException {
		if (fwd == null) {
			return null;
		}

		Map<WorldTracerField, List<String>> result = new EnumMap<WorldTracerField, List<String>>(WorldTracerField.class);
		addIncidentFieldEntry(WorldTracerField.AG, getAgentEntry(fwd.getAgent()), result);

		addConvertedTag(fwd.getFwdExpediteNum(), WorldTracerField.XT, result, fwd.getAgent().getCompanycode_ID());

		if (fwd.getFwdName() != null) {
			for (String name : fwd.getFwdName()) {
				addIncidentFieldEntry(WorldTracerField.NM, name.trim(), result);
			}
		}

		if (fwd.getTeletypes() != null) {
			for (String tt : fwd.getTeletypes()) {
				addIncidentFieldEntry(WorldTracerField.TX, tt.trim(), result);
			}
		}

		addIncidentFieldEntry(WorldTracerField.SI, fwd.getSupInfo(), result);
		addIncidentFieldEntry(WorldTracerField.TI, fwd.getSupInfo(), result);

		String fw = null;
		if (fwd.getItinerary() != null) {
			for (WtqSegment itin : fwd.getItinerary()) {
				if (itin.getAirline() == null || itin.getAirline().trim().length() <= 0 || itin.getFlightnum() == null
						|| itin.getFlightnum().trim().length() <= 0 || itin.getLegfrom() == null
						|| itin.getLegfrom().trim().length() <= 0 || itin.getLegto() == null
						|| itin.getLegto().trim().length() <= 0 || itin.getDepartdate() == null) {
					continue;
				}
				String fnum = wtFlightNumber(itin.getFlightnum());
				String fd = null;
				if (fnum.length() == 0) {
					fd = UNKNOWN_AIRLINE + "/" + ITIN_DATE_FORMAT.format(itin.getDepartdate());
				} else {
					fd = itin.getAirline() + fnum + "/" + ITIN_DATE_FORMAT.format(itin.getDepartdate());
				}
				addIncidentFieldEntry(WorldTracerField.FO, fd, result);
				addIncidentFieldEntry(WorldTracerField.NF, fd, result);
				List<String> routing = result.get(WorldTracerField.NR);
				if (routing == null || !routing.get(routing.size() - 1).equalsIgnoreCase(itin.getLegfrom().trim())) {
					addIncidentFieldEntry(WorldTracerField.NR, itin.getLegfrom().trim(), result);
				}
				addIncidentFieldEntry(WorldTracerField.NR, itin.getLegto().trim(), result);
				fw = itin.getLegto() + itin.getAirline();
				addIncidentFieldEntry(WorldTracerField.FW, fw, result);
			}
		}
		List<String> foo = result.get(WorldTracerField.FW);
		if (foo != null && foo.size() > 0) {
			foo.set(foo.size() - 1, fwd.getFwdDestinationStation() + fwd.getFwdDestinationAirline());
		} else {
			throw new WorldTracerException("invalid forward itinerary");
		}

		return result;
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

		String colorType = "";
		if ("TD".equals(ohd.getColor().trim())) {
			colorType = "BN";
		} else {
			colorType = ohd.getColor().trim();
		}

		String type = ohd.getType().trim();
		if (!VALID_BAG_TYPES.contains(type)) {
			type = DEFAULT_BAG_TYPE;
		}
		colorType += type;

		String desc1 = mapXDesc(XDescElementsBMO.getXdescelementcode(ohd.getXdescelement_ID_1()));
		String desc2 = mapXDesc(XDescElementsBMO.getXdescelementcode(ohd.getXdescelement_ID_2()));
		String desc3 = mapXDesc(XDescElementsBMO.getXdescelementcode(ohd.getXdescelement_ID_3()));

		colorType += getDescString(desc1, desc2, desc3);

		addIncidentFieldEntry(WorldTracerField.CT, colorType, result);

		if (ohd.getClaimnum() != null && ohd.getClaimnum().trim().length() > 0) {
			addClaimCheckNum(ohd.getClaimnum(), result, ohd.getAgent().getCompanycode_ID());
		}

		addIncidentFieldEntry(WorldTracerField.BI, ohd.getManufacturer(), result);

		addIncidentFieldEntry(WorldTracerField.PR, ohd.getRecord_locator(), result);

		addIncidentFieldEntry(WorldTracerField.SL, ohd.getStorage_location(), result);

		if (ohd.getMembership() != null) {
			String membership = ohd.getMembership().getMembershipnum(); 
			if (ohd.getMembership().getCompanycode_ID() != null) {
				membership = ohd.getMembership().getCompanycode_ID() + membership;
			}
			addIncidentFieldEntry(WorldTracerField.FL, membership, result);
		}

		if (ohd.getItinerary() == null || ohd.getItinerary().size() == 0) {
			throw new WorldTracerException("OHD missing itinerary");
		}
		for (OHD_Itinerary itin : (Iterable<OHD_Itinerary>) ohd.getItinerary()) {
			getOhdItineraryInfo(itin, result);
		}

		addIncidentFieldEntry(WorldTracerField.NM, ohd.getLastname(), result);

		addIncidentFieldEntry(WorldTracerField.PT, ohd.getFirstname(), result);

		for (OHD_Passenger p : (Iterable<OHD_Passenger>) ohd.getPassengers()) {
			getOhdPaxInfo(p, result);
		}

		if (ohd.getItems() != null) {
			Map<String, List<String>> temp = new HashMap<String, List<String>>();
			for (OHD_Inventory inv : (Iterable<OHD_Inventory>) ohd.getItems()) {
				OHD_CategoryType cat = CategoryBMO.getCategory(inv.getOHD_categorytype_ID(), TracingConstants.DEFAULT_LOCALE);
				if (cat == null) {
					continue;
				}
				String category = cat.getWtCategory();
				String contents = inv.getDescription().trim().toUpperCase();
				if (category == null || contents == null || category.trim().length() == 0
						|| contents.trim().length() == 0)
					continue;
				if (temp.get(category) == null) {
					temp.put(category, new ArrayList<String>());
				}
				temp.get(category).add(contents);
			}
			if (temp.size() > 0) {
				// pass 0 for bagnum so no numbers on field
				String entry = ContentRule.buildEntry(temp, 0);
				if (entry != null) {
					addIncidentFieldEntry(WorldTracerField.CC, entry, result);
				}
			}
		}

		addIncidentFieldEntry(WorldTracerField.AG, DefaultWorldTracerService.getAgentEntry(ohd.getAgent()), result);
		return result;

	}

	private String getDescString(String... descs) {
		// need to remove duplicates
		Set<String> foo = new HashSet<String>(Arrays.asList(descs));
		String result = "";
		boolean hasMat = false;
		for (String desc : foo) {
			if (wt_mats.contains(desc)) {
				if (!hasMat) {
					result += desc;
					hasMat = true;
				}
			} else {
				result += desc;
			}
		}
		if (result.length() > 3) {
			return result.substring(0, 3);
		} else if (result.length() == 3) {
			return result;
		} else if (result.length() == 2) {
			return result + "X";
		} else if (result.length() == 1) {
			return result + "XX";
		} else {
			return "XXX";
		}
	}

	private String mapXDesc(String code) {
		if (code == null || !wt_descs.contains(code)) {
			return "X";
		}
		return code;
	}

	private void getOhdPaxInfo(OHD_Passenger p, Map<WorldTracerField, List<String>> result) {
		OHD_Address address = p.getAddress(0);
		if (p.getLastname() == null || p.getLastname().trim().length() <= 0) {
			if (address != null) {
				addWtOhdAddress(result, address, WorldTracerField.TA);
			}
		} else {
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
			addIncidentFieldEntry(WorldTracerField.PT, p.getFirstname(), result);

			// add permanent address
			if (address != null) {
				addWtOhdAddress(result, address, WorldTracerField.PA);
			}
		}
		if (address != null) {
			if (!result.containsKey(WorldTracerField.AB)) {
				addWtOhdAddress(result, address, WorldTracerField.AB);
			}
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


	private void addWtOhdAddress(Map<WorldTracerField, List<String>> result, OHD_Address address,
			WorldTracerField addressField) {
		ArrayList<String> addr1Pieces = new ArrayList<String>();
		if (address.getAddress1() != null)
			addr1Pieces.add(address.getAddress1().trim());
		if (address.getAddress2() != null)
			addr1Pieces.add(address.getAddress2().trim());
		if (address.getCity() != null)
			addr1Pieces.add(address.getCity().trim());
		if (address.getState() != null)
			addr1Pieces.add(address.getState().trim());
		else if (address.getProvince() != null)
			addr1Pieces.add(address.getProvince().trim());
		if (address.getZip() != null)
			addr1Pieces.add(address.getZip().trim());
		String value = StringUtils.join(addr1Pieces, " ").trim().replaceAll("\\s+", " ");
		addIncidentFieldEntry(addressField, wtClear(value), result);
	}

	private void addWtIncAddress(Map<WorldTracerField, List<String>> result, Address address,
			WorldTracerField addressField) {
		ArrayList<String> addr1Pieces = new ArrayList<String>();
		if (address.getAddress1() != null)
			addr1Pieces.add(address.getAddress1().trim());
		if (address.getAddress2() != null)
			addr1Pieces.add(address.getAddress2().trim());
		if (address.getCity() != null)
			addr1Pieces.add(address.getCity().trim());
		if(address.getState() != null && address.getState().trim().length() > 0 && "US".equals(address.getCountrycode_ID())) {
			addr1Pieces.add(address.getState().trim());
			if (addressField.equals(WorldTracerField.PA)) {
				addIncidentFieldEntry(WorldTracerField.STATE, address.getState_ID(), result);
			}
		}	else if(address.getProvince() != null)
			addr1Pieces.add(address.getProvince().trim());
		if(address.getZip() != null) {
			addr1Pieces.add(address.getZip().trim());
			if (addressField.equals(WorldTracerField.PA)) {
				addIncidentFieldEntry(WorldTracerField.ZIP, address.getZip(), result);
			}
		}
		String value = StringUtils.join(addr1Pieces, " ").trim().replaceAll("\\s+", " ");
		addIncidentFieldEntry(addressField, wtClear(value), result);
	}

	protected Map<WorldTracerField, List<String>> createFieldMap(Incident ntIncident) throws WorldTracerException {

		if (ntIncident == null) {
			return null;
		}
		Map<WorldTracerField, List<String>> result = new EnumMap<WorldTracerField, List<String>>(WorldTracerField.class);

		// this is a little messy, but we are keeping track of the addresses
		// here.
		// ones explicitly marked permanent get highest priority for the PA
		// field
		List<Address> permAdds = new ArrayList<Address>();
		List<Address> namedAdds = new ArrayList<Address>();
		List<Address> tempAdds = new ArrayList<Address>();
		if(ntIncident.getPassenger_list() != null) {
			int passengerCount = 0;
			for(Passenger p : (List<Passenger>) ntIncident.getPassenger_list()) {
					if (passengerCount < 3) {
					++passengerCount;
					
					getPassengerInfo(p, result);
					Address a = p.getAddress(0);
					if(a != null) {
						if(a.isPermanent()) {
							permAdds.add(a);
						}
						else if(p.getLastname() == null || p.getLastname().trim().length() <= 0) {
							tempAdds.add(a);
						}
						else {
							namedAdds.add(a);
						}
					}
				}
			}
			for (Address addr : permAdds) {
				addWtIncAddress(result, addr, WorldTracerField.PA);
			}
			for (Address addr : namedAdds) {
				addWtIncAddress(result, addr, WorldTracerField.PA);
			}
			for (Address addr : tempAdds) {
				addWtIncAddress(result, addr, WorldTracerField.TA);
			}
		}

		// num passengers
		addIncidentFieldEntry(WorldTracerField.NP, Integer.toString(ntIncident.getNumpassengers()), result);

		addIncidentFieldEntry(WorldTracerField.PR, ntIncident.getRecordlocator(), result);

		if (ntIncident.getItinerary_list() != null) {
			for (Itinerary i : ntIncident.getItinerary_list()) {
				getItineraryInfo(i, result);
			}
		}

		if (result.get(WorldTracerField.BR) == null && result.get(WorldTracerField.FD) == null) {
			throw new WorldTracerException("No valid itinerary");
		}
		if (result.get(WorldTracerField.BR) == null) {
			result.put(WorldTracerField.BR, result.get(WorldTracerField.FD));
		} else if (result.get(WorldTracerField.FD) == null) {
			result.put(WorldTracerField.FD, result.get(WorldTracerField.BR));
		}

		if (ntIncident.getClaimcheck_list() != null) {
			for (Incident_Claimcheck ic : ntIncident.getClaimcheck_list()) {
				if (ic.getClaimchecknum() != null && ic.getClaimchecknum().trim().length() > 0) {
					addClaimCheckNum(ic.getClaimchecknum(), result, ntIncident.getStationassigned().getCompany()
							.getCompanyCode_ID());
				}
			}
		}

		// contents category can only have one entry in WT, but you can use up
		// to two lines for each
		// you can have 12 total categories (see ContentRule class)
		if (ntIncident.getItemlist() != null) {
			int bagCount = 1;
			for (Item i : ntIncident.getItemlist()) {
				getItemInfo(i, result);
				if (i.getInventorylist() != null) {

					Map<String, List<String>> temp = new HashMap<String, List<String>>();
					for (Item_Inventory inv : (List<Item_Inventory>) i.getInventorylist()) {
						String category = CategoryBMO.getCategory(inv.getCategorytype_ID(), TracingConstants.DEFAULT_LOCALE).getWtCategory();
						String contents = inv.getDescription().trim().toUpperCase();
						if (category == null || contents == null || category.trim().length() == 0
								|| contents.trim().length() == 0)
							continue;
						if (temp.get(category) == null) {
							temp.put(category, new ArrayList<String>());
						}
						temp.get(category).add(contents);
					}
					if (temp.size() > 0) {
						String entry = ContentRule.buildEntry(temp, bagCount);
						if (entry != null) {
							addIncidentFieldEntry(WorldTracerField.CC, entry, result);
							bagCount += 1;
						}
					}
				}
			}
		}
		addIncidentFieldEntry(WorldTracerField.PB, "0000", result);
		addIncidentFieldEntry(WorldTracerField.AG, DefaultWorldTracerService.getAgentEntry(ntIncident.getAgent()),
				result);
		return result;
	}

	private Map<WorldTracerField, List<String>> createCloseFieldMap(Incident incident) {
		Map<WorldTracerField, List<String>> result = new EnumMap<WorldTracerField, List<String>>(WorldTracerField.class);
		String cs_fmt = "%02d %s/%s%1.2f";
		int claimCount = 0;
		if (incident.getClaim() != null) {
			String cost;

			if (incident.getExpenses() != null) {
				for (ExpensePayout expense : incident.getExpenses()) {
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
		// see if we added a CS
		if (claimCount == 0) {
			addIncidentFieldEntry(WorldTracerField.CS, "01 X/USD0.00", result);
		}

		if (incident.getLoss_code() != 0) {
			addIncidentFieldEntry(WorldTracerField.RL, Integer.toString(incident.getLoss_code()), result);
			Company_specific_irregularity_code csic = LossCodeBMO.getLossCode(incident.getLoss_code(),
					TracingConstants.LOST_DELAY, AdminUtils.getCompany(wtCompanyCode));
			addIncidentFieldEntry(WorldTracerField.RC, csic.getDescription(), result);
		} else {
			addIncidentFieldEntry(WorldTracerField.RL, "79", result);
			addIncidentFieldEntry(WorldTracerField.RC, "Created in error", result);
		}
		if (incident.getFaultstation() != null) {
			Station fs = StationBMO.getStationByCode(incident.getFaultstationcode());
			if (fs.getWt_stationcode() == null || fs.getWt_stationcode().trim().length() < 1) {
				addIncidentFieldEntry(WorldTracerField.FS, incident.getStationcreated().getWt_stationcode(), result);
			} else {
				addIncidentFieldEntry(WorldTracerField.FS, fs.getWt_stationcode(), result);
			}
		} else {
			addIncidentFieldEntry(WorldTracerField.FS, incident.getStationcreated().getWt_stationcode(), result);
		}
		addIncidentFieldEntry(WorldTracerField.AG, DefaultWorldTracerService.getAgentEntry(incident.getAgent()), result);
		return result;
	}

	protected void getItemInfo(Item item, Map<WorldTracerField, List<String>> result) {
		getItemInfo(item, result, true);
	}

	protected void getItemInfo(Item item, Map<WorldTracerField, List<String>> result, boolean includeXdesc) {

		if (item.getColor() == null || item.getColor().trim().length() <= 0 || item.getBagtype() == null
				|| item.getBagtype().trim().length() != 2) {
			return;
		}

		String colorType = "";
		if ("TD".equals(item.getColor().trim())) {
			colorType = "BN";
		} else {
			colorType = item.getColor().trim();
		}

		String type = item.getBagtype().trim();
		if (!VALID_BAG_TYPES.contains(type)) {
			type = DEFAULT_BAG_TYPE;
		}
		colorType += type;

		String desc1 = mapXDesc(XDescElementsBMO.getXdescelementcode(item.getXdescelement_ID_1()));
		String desc2 = mapXDesc(XDescElementsBMO.getXdescelementcode(item.getXdescelement_ID_2()));
		String desc3 = mapXDesc(XDescElementsBMO.getXdescelementcode(item.getXdescelement_ID_3()));

		colorType += getDescString(desc1, desc2, desc3);

		addIncidentFieldEntry(WorldTracerField.CT, colorType, result);

		addIncidentFieldEntry(WorldTracerField.BI, item.getManufacturer(), result);
	}

	private void addClaimCheckNum(String claimCheck, Map<WorldTracerField, List<String>> result, String companyCode) {
		addConvertedTag(claimCheck, WorldTracerField.TN, result, companyCode);
	}
	
	private void addConvertedTag(String tag, WorldTracerField field, Map<WorldTracerField, List<String>> result, String companyCode) {
		String bagTagString = null;
		try {
			bagTagString = LookupAirlineCodes.getTwoCharacterBagTag(tag.trim());
		} catch (BagtagException e) {
			// couldn't figure out the tag.
			Pattern wt_patt = Pattern.compile("([a-zA-Z0-9]{2})(\\d{1,6})");
			Matcher m = wt_patt.matcher(tag.trim());
			if (m.find() && LookupAirlineCodes.getThreeDigitTicketingCode(m.group(1)) != null) {
				bagTagString = String.format("%s%06d", m.group(1), Integer.parseInt(m.group(2)));
			} else {
				Pattern base_patt = Pattern.compile("(\\d{1,6})(\\D|$)");
				m = base_patt.matcher(tag.trim());
				if (m.find()) {
					bagTagString = companyCode + m.group(1);
				}
			}
		}
		if (bagTagString != null && bagTagString.matches(".*[1-9].*")) {
			if (result.get(field) == null || !(result.get(field).contains(bagTagString))) {
				addIncidentFieldEntry(field, bagTagString, result);
			}
		}
	}

	/**
	 * 
	 * @param itin
	 * @param result
	 */
	protected void getItineraryInfo(Itinerary itin, Map<WorldTracerField, List<String>> result) {

		// make sure it's a valid itinerary
		if (itin.getAirline() == null || itin.getAirline().trim().length() <= 0 || itin.getLegfrom() == null
				|| itin.getLegfrom().trim().length() <= 0 || itin.getLegto() == null
				|| itin.getLegto().trim().length() <= 0 || itin.getDepartdate() == null) {
			return;
		}

		String fnum = wtFlightNumber(itin.getFlightnum());
		String fd = null;
		if (fnum.length() == 0 && itin.getItinerarytype() == TracingConstants.PASSENGER_ROUTING) {
			fd = UNKNOWN_AIRLINE + "/" + ITIN_DATE_FORMAT.format(itin.getDepartdate());
		} else {
			fd = itin.getAirline() + fnum + "/" + ITIN_DATE_FORMAT.format(itin.getDepartdate());
		}

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

	private String wtFlightNumber(String flightnum) {
		if (flightnum == null)
			return "";

		Matcher m = FLIGHTNUM_FORMAT.matcher(flightnum);
		if (m.find()) {
			return m.group();
		}
		return "";
	}

	protected void getOhdItineraryInfo(OHD_Itinerary itin, Map<WorldTracerField, List<String>> result) {
		// make sure it's a valid itinerary
		if (itin.getAirline() == null || itin.getAirline().trim().length() <= 0 || itin.getFlightnum() == null
				|| itin.getFlightnum().trim().length() <= 0 || itin.getLegfrom() == null
				|| itin.getLegfrom().trim().length() <= 0 || itin.getLegto() == null
				|| itin.getLegto().trim().length() <= 0 || itin.getDepartdate() == null) {
			return;
		}

		String fnum = wtFlightNumber(itin.getFlightnum());
		String fd = null;
		if (fnum.length() == 0) {
			fd = UNKNOWN_AIRLINE + "/" + ITIN_DATE_FORMAT.format(itin.getDepartdate());
		} else {
			fd = itin.getAirline() + fnum + "/" + ITIN_DATE_FORMAT.format(itin.getDepartdate());
		}

		addIncidentFieldEntry(WorldTracerField.FD, fd, result);
		List<String> routing = result.get(WorldTracerField.RT);
		if (routing == null || !routing.get(routing.size() - 1).equalsIgnoreCase(itin.getLegfrom().trim())) {
			addIncidentFieldEntry(WorldTracerField.RT, itin.getLegfrom().trim(), result);
		}
		addIncidentFieldEntry(WorldTracerField.RT, itin.getLegto().trim(), result);
	}

	protected void getPassengerInfo(Passenger p, Map<WorldTracerField, List<String>> result)
			throws WorldTracerException {
		Address address = p.getAddress(0);
		if (p.getLastname() != null && p.getLastname().trim().length() > 0) {
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
			if (p.getSalutation() == 0) {
				addIncidentFieldEntry(WorldTracerField.PT, p.getFirstname(), result);
			} else {
				String title = StringUtils.join(" ", p.getSalutationdesc() != null ? p.getSalutationdesc() : "", p
						.getFirstname() != null ? p.getFirstname() : "");
				addIncidentFieldEntry(WorldTracerField.PT, title, result);
			}

			if (result.get(WorldTracerField.PT) == null) {
				throw new WorldTracerException("Salutation or first name required to create AHL");
			}

			// add the frequent flier class status
			addIncidentFieldEntry(WorldTracerField.PS, p.getAirlinememstatus(), result);

			// add the frequent flier num
			String membership = p.getAirlinememnumber();
			if (p.getAirlinememcompany() != null && p.getAirlinememcompany().length() > 0) {
				membership = p.getAirlinememcompany() + membership;
			}
			addIncidentFieldEntry(WorldTracerField.FL, membership, result);
		}
		if (address != null) {

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

	private String wtPhone(String rawText) {
		return rawText != null ? rawText.replaceAll("\\D", "") : null;
	}

	private String wtClear(String rawText) {
		return rawText != null ? rawText.replaceAll("[" + FIELD_SEP + ENTRY_SEP + "]", " ") : null;
	}

	private String wtEscape(String rawText) {
		return rawText != null ? rawText.replace("@", "/A/").replace(".", "/D/").replace("_", "/U/")
				.replace("~", "/T/").replace("+", "/P/") : null;
	}

	public void setWtCompanyCode(String wtCompanyCode) {
		this.wtCompanyCode = wtCompanyCode;
	}

	@WorldTracerTx(type = TxType.AF_COUNT)
	public Map<ActionFileType, ActionFileCount> getActionFileCount(String companyCode,	String wtStation, Agent user) {
		EnumMap<ActionFileType, int[]> result;
		try {
			result = this.wtConnector.getActionFileCounts(companyCode, wtStation);
		} catch (WorldTracerException e) {
			return null;
		}
		Map<ActionFileType, ActionFileCount> countMap = new EnumMap<ActionFileType, ActionFileCount>(ActionFileType.class);
		if (result != null) {
			for (Entry<ActionFileType, int[]> entry : result.entrySet()) {
				ActionFileCount afCount = new ActionFileCount();
				afCount.setDayOne(entry.getValue()[0]);
				afCount.setDayTwo(entry.getValue()[1]);
				afCount.setDayThree(entry.getValue()[2]);
				afCount.setDayFour(entry.getValue()[3]);
				afCount.setDayFive(entry.getValue()[4]);
				afCount.setDaySix(entry.getValue()[5]);
				afCount.setDaySeven(entry.getValue()[6]);
				countMap.put(entry.getKey(), afCount);
			}
		}
		return countMap;
	}
	
	@WorldTracerTx(type = TxType.AF_SUMMARY)
	public List<Worldtracer_Actionfiles> getActionFileSummary(String companyCode,
			String wtStation, ActionFileType afType, int day, Agent user) throws WorldTracerException {
		List<ActionFileDto> stuff;
		stuff = wtConnector.getActionFileSummary(companyCode, wtStation, afType, day);
		
		if(stuff == null || stuff.size() < 1) return null;
		
		List<Worldtracer_Actionfiles> result = new ArrayList<Worldtracer_Actionfiles>();
		for (ActionFileDto afd : stuff) {
			Worldtracer_Actionfiles wa = new Worldtracer_Actionfiles();
			wa.setAction_file_summary(afd.getSummary());
			wa.setAction_file_text(afd.getDetails());
			wa.setAction_file_type(afType);
			wa.setAirline(companyCode);
			wa.setDay(day);
			wa.setDeleted(false);
			wa.setItem_number(afd.getItemNumber());
			wa.setPercent_match(afd.getPercentMatch());
			wa.setStation(wtStation);
			wa.setWt_incident_id(afd.getAhlId());
			wa.setWt_ohd_id(afd.getOhdId());
			result.add(wa);
		}
		
		return result;
	}

	public WorldTracerConnector getWtConnector() {
		return wtConnector;
	}

	@WorldTracerTx(type = TxType.AF_DETAIL)
	public String getActionFileDetail(String companyCode,
			String wtStation, ActionFileType afType, int day, int itemNum,
			Agent user) throws WorldTracerException {
		String result = wtConnector.getActionFileDetails(companyCode, wtStation, afType, day, itemNum);
		return result;
	}


}
