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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Configurable;

import com.bagnet.nettracer.aop.WorldTracerTx;
import com.bagnet.nettracer.exceptions.BagtagException;
import com.bagnet.nettracer.tracing.bmo.CategoryBMO;
import com.bagnet.nettracer.tracing.bmo.XDescElementsBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Address;
import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.BDO;
import com.bagnet.nettracer.tracing.db.BDO_Passenger;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Incident_Claimcheck;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.Item_Inventory;
import com.bagnet.nettracer.tracing.db.Itinerary;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.OHD_Address;
import com.bagnet.nettracer.tracing.db.OHD_Itinerary;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles;
import com.bagnet.nettracer.tracing.db.WorldTracerFile.WTStatus;
import com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles.ActionFileType;
import com.bagnet.nettracer.tracing.db.wt.ActionFileCount;
import com.bagnet.nettracer.tracing.db.wtq.WtqFwd;
import com.bagnet.nettracer.tracing.db.wtq.WtqFwdGeneral;
import com.bagnet.nettracer.tracing.db.wtq.WtqFwdOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqQoh;
import com.bagnet.nettracer.tracing.db.wtq.WtqRequestOhd;
import com.bagnet.nettracer.tracing.db.wtq.WtqRequestPxf;
import com.bagnet.nettracer.tracing.db.wtq.WtqRequestQoh;
import com.bagnet.nettracer.tracing.db.wtq.WtqSegment;
import com.bagnet.nettracer.tracing.utils.StringUtils;
import com.bagnet.nettracer.tracing.utils.lookup.LookupAirlineCodes;
import com.bagnet.nettracer.wt.WorldTracerException;
import com.bagnet.nettracer.wt.WorldTracerRecordNotFoundException;
import com.bagnet.nettracer.wt.connector.CaptchaException;
import com.bagnet.nettracer.wt.connector.WebServiceDto;
import com.bagnet.nettracer.wt.connector.WorldTracerConnector;
import com.bagnet.nettracer.wt.connector.WorldTracerWebService;
import com.bagnet.nettracer.wt.svc.WorldTracerRule.Format;
import com.bagnet.nettracer.wt.utils.ActionFileDto;

@Configurable("wtService")
public class DefaultWorldTracerService implements WorldTracerService {

	private static final DateFormat ITIN_DATE_FORMAT = new SimpleDateFormat("ddMMM", Locale.US);

	private static final Logger logger = Logger.getLogger(DefaultWorldTracerService.class);

	private static final Pattern FLIGHTNUM_FORMAT = Pattern.compile("[1-9]\\d{0,3}[a-zA-Z]?");

	private WorldTracerConnector wtConnector;

	@SuppressWarnings("unused")
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

	public DefaultWorldTracerService() {
		this.setWtConnector(WorldTracerWebService.getInstance());
	}
	
	@WorldTracerTx(type = TxType.CREATE_AHL)
	public String insertIncident(Agent agent, Incident incident, WebServiceDto dto) throws WorldTracerException, CaptchaException {
		String wt_id = null;

		try {
			String companyCode=incident.getStationassigned().getCompany().getCompanyCode_ID();
			String stationCode=incident.getStationassigned().getWt_stationcode();
			
			wt_id = wtConnector.insertIncident(incident, companyCode, stationCode, dto);

		} catch (CaptchaException e) {
			throw e;
		}catch (Exception e) {
			logger.error("Full exception: ", e);
			e.printStackTrace();
			if (e instanceof WorldTracerException) {
				throw (WorldTracerException) e;
			} else {
				throw new WorldTracerException(e.getMessage(), e);
			}
		}
		return wt_id;
	}

	@WorldTracerTx(type = TxType.CLOSE_AHL)
	public String closeIncident(Agent agent, Incident incident, WebServiceDto dto) throws WorldTracerException, CaptchaException {

		if (incident.getWt_id() == null) {
			throw new WorldTracerException("no associated worldtracer file");
		}
		String wt_id = wtConnector.closeIncident(incident, incident.getWtFile().getWt_id(), incident
				.getStationassigned().getWt_stationcode(), dto);

		return wt_id;

	}

	@WorldTracerTx(type = TxType.CREATE_OHD)
	public String insertOhd(Agent agent, OHD ohd, WebServiceDto dto) throws WorldTracerException, CaptchaException {

		String wt_id = null;

		wt_id = wtConnector.insertOhd(ohd, ohd.getFoundAtStation().getCompany().getCompanyCode_ID(), ohd
				.getHoldingStation().getWt_stationcode(), dto);

		return wt_id;
	}

	@WorldTracerTx(type = TxType.CLOSE_OHD)
	public String closeOHD(Agent agent, OHD ohd, WebServiceDto dto) throws WorldTracerException, CaptchaException {
		if (ohd.getWt_id() == null) {
			throw new WorldTracerException("Can't close ohd, no associated wt file");
		}

		String wt_id = null;

		wt_id = wtConnector.closeOhd(ohd, ohd.getWt_id(), ohd.getHoldingStation().getWt_stationcode(), dto);

		return wt_id;
	}

	@WorldTracerTx(type = TxType.REINSTATE_AHL)
	public String reinstateIncident(Agent agent, Incident incident, WebServiceDto dto) throws WorldTracerException, CaptchaException {
		if (incident.getWt_id() == null) {
			throw new WorldTracerException("no associated worldtracer file");
		}
		wtConnector.reinstateAHL(incident.getWt_id(), getAgentEntry(incident.getAgent()), dto);
		return incident.getWt_id();
	}

	@WorldTracerTx(type = TxType.REINSTATE_OHD)
	public String reinstateOhd(Agent agent, OHD ohd, WebServiceDto dto) throws WorldTracerException, CaptchaException {
		if (ohd.getWt_id() == null) {
			throw new WorldTracerException("Can't reinstate ohd, no associated wt file");
		}
		wtConnector.reinstateOHD(ohd.getWt_id(), getAgentEntry(ohd.getAgent()), dto);
		return ohd.getWt_id();
	}

	@WorldTracerTx(type = TxType.SUSPEND_AHL)
	public String suspendIncident(Agent agent, Incident incident, WebServiceDto dto) throws WorldTracerException, CaptchaException {
		if (incident.getWt_id() == null) {
			throw new WorldTracerException("no associated worldtracer file");
		}
		wtConnector.suspendAHL(incident.getWt_id(), getAgentEntry(incident.getAgent()), dto);
		return incident.getWt_id();
	}

	@WorldTracerTx(type = TxType.SUSPEND_OHD)
	public String suspendOhd(Agent agent, OHD ohd, WebServiceDto dto) throws WorldTracerException, CaptchaException {
		if (ohd.getWt_id() == null) {
			throw new WorldTracerException("Can't suspend ohd, no associated wt file");
		}
		wtConnector.suspendOHD(ohd.getWt_id(), getAgentEntry(ohd.getAgent()), dto);
		return ohd.getWt_id();
	}

	@WorldTracerTx(type = TxType.CREATE_BDO)
	public String insertBdo(Agent agent, BDO bdo, WebServiceDto dto) throws WorldTracerException, CaptchaException {

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
				.getOhd() != null ? bdo.getOhd().getWt_id() : null, bdo.getDelivercompany(), bdo.getStation(), dto, bdo);
	}

	@SuppressWarnings("unchecked")
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
	public void eraseActionFile(Agent agent, Worldtracer_Actionfiles waf, WebServiceDto dto) throws WorldTracerException, CaptchaException {
		wtConnector.eraseActionFile(waf.getStation(), waf.getAirline(), waf.getAction_file_type(), waf.getSeq(),
				waf.getDay(), waf.getItem_number(), dto);
	}

	@SuppressWarnings("unused")
	@WorldTracerTx(type = TxType.IMPORT_AHL)
	public Incident getIncidentForAHL(Agent user, String wt_id, WTStatus status, WebServiceDto dto) throws WorldTracerException, CaptchaException {
//		String result = wtConnector.findAHL(wt_id, dto);
//		//Incident foundinc = WTIncident.parseWTIncident(result, status, user);
//		Incident foundinc = WTIncident.parseWTIncident(result, status, user);
//		return foundinc;
		if(true){
			throw new WorldTracerRecordNotFoundException();
		}
		return null;
	}

	@SuppressWarnings("unused")
	@WorldTracerTx(type = TxType.IMPORT_OHD)
	public OHD getOhdforOhd(Agent user, String wt_id, WTStatus status, WebServiceDto dto) throws WorldTracerException, CaptchaException {
//		String result = wtConnector.findOHD(wt_id, dto);
//		// for now show all as active
//		//OHD foundohd = WTOHD.parseWTOHD(result, WTStatus.ACTIVE);
//		OHD foundohd = WTOHD.parseWTOHD(result, WTStatus.ACTIVE, user);
		
		if(true){
			throw new WorldTracerRecordNotFoundException();
		}
		return null;
	}

	@WorldTracerTx(type = TxType.FWD_OHD)
	public String forwardOhd(Agent agent, WtqFwdOhd fwd, WebServiceDto dto) throws WorldTracerException, CaptchaException {

		if (fwd.getOhd() == null || fwd.getOhd().getWt_id() == null) {
			throw new WorldTracerException("No associated worldtracer file");
		}
		

		String result = null;

		result = wtConnector.forwardOhd(fwd, dto);

		return result;
	}

	@WorldTracerTx(type = TxType.FWD_GENERAL)
	public String sendFwdMsg(Agent defaultAgent, WtqFwdGeneral fwd, WebServiceDto dto) throws WorldTracerException, CaptchaException {
	



		String result = wtConnector.sendFwd(fwd, dto);
		return result;
	}

	@WorldTracerTx(type = TxType.AMEND_AHL)
	public String amendAhl(Agent agent, Incident incident, WebServiceDto dto) throws WorldTracerException {
		if (incident.getWt_id() == null) {
			throw new WorldTracerException("no associated worldtracer file");
		}
		String wt_id = null;

		try {
			wt_id = wtConnector.amendAhl(incident, incident.getWt_id(), dto);

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
	public String amendOhd(Agent agent, OHD ohd, WebServiceDto dto) throws WorldTracerException, CaptchaException {
		if (ohd.getWt_id() == null) {
			throw new WorldTracerException("no associated worldtracer file");
		}

		String result = null;

		result = wtConnector.amendOhd(ohd, ohd.getWt_id(), dto);

		return result;
	}

	@WorldTracerTx(type = TxType.REQUEST_QOH)
	public String requestQoh(Agent agent, WtqRequestQoh wtq, WebServiceDto dto) throws WorldTracerException, CaptchaException {
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
				.getIncident().getWt_id(), fieldMap, dto, wtq);
	}

	@WorldTracerTx(type = TxType.REQUEST_OHD)
	public String requestOhd(Agent agent, WtqRequestOhd roh, WebServiceDto dto) throws WorldTracerException, CaptchaException {
		if (roh.getIncident() == null || roh.getIncident().getWt_id() == null) {
			throw new WorldTracerException("no associated WT file");
		}
		Map<WorldTracerField, List<String>> fieldMap = createFieldMap(roh);
		String result = wtConnector.requestOhd(roh.getWt_ohd(), roh.getIncident().getWt_id(), fieldMap, dto, roh);
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

	private static String getAgentEntry(Agent ag) {
				if(ag != null)
			return (ag.getUsername().length() > 7 ? ag.getUsername().substring(0, 7) : ag.getUsername());
		return "NTRACER";
	}

	@SuppressWarnings("unused")
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

	@SuppressWarnings("unused")
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

	@SuppressWarnings("unchecked")
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
				/*
		    	 * Checking for UTB tag - We don't submit Untagged Bagtags to World Tracer
		    	 */
				if (ic.getClaimchecknum() != null && ic.getClaimchecknum().trim().length() > 0 && !(ic.getClaimchecknum().length()>3 && ic.getClaimchecknum().substring(0,3).toUpperCase().equals(TracingConstants.UTB_CHECK))) {
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
		logger.info("Setting wtConnector...");
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
	public List<ActionFileCount> getActionFileCount(Agent user, String companyCode,	String wtStation, WebServiceDto dto) throws CaptchaException {
		List<aero.nettracer.serviceprovider.wt_1_0.common.ActionFileCount> result;
		try {
			result = this.wtConnector.getActionFileCounts(companyCode, wtStation, dto);
		} catch (WorldTracerException e) {
			return null;
		}
		ArrayList<ActionFileCount> countList = new ArrayList<ActionFileCount>();
		if (result != null) {
			Map<String, ActionFileCount>countMap = new HashMap<String, ActionFileCount>();
			for(aero.nettracer.serviceprovider.wt_1_0.common.ActionFileCount count:result){
				String type = count.getType();
				String seq = count.getSeq();
				boolean enumExists=false;
				enumExists=checkEnumType(type);
				if(enumExists){
					if(count.getSeq() == null || count.getSeq().trim().length() == 0){
						seq = TracingConstants.WT_AFC_DEFAULT_SEQ;
					}
					
					ActionFileCount afc;
					if(countMap.containsKey(type+seq)){
						afc = countMap.get(type+seq);
					} else {
						afc = new ActionFileCount();
					}
					afc.setAf_type(ActionFileType.valueOf(type));
					afc.setAf_seq(seq);
					switch(count.getDay()){
					case 1: afc.setDayOne(count.getCount()); break;
					case 2: afc.setDayTwo(count.getCount()); break;
					case 3: afc.setDayThree(count.getCount()); break;
					case 4: afc.setDayFour(count.getCount()); break;
					case 5: afc.setDayFive(count.getCount()); break;
					case 6: afc.setDaySix(count.getCount()); break;
					case 7: afc.setDaySeven(count.getCount()); break;
					}
					countMap.put(type + seq, afc);
				}
			}
			for(String key:countMap.keySet()){
				countList.add(countMap.get(key));
			}
		}
		return countList;
	}
	
	private boolean checkEnumType(String type) {
		for(ActionFileType e:ActionFileType.values()){
			if(e.name().equalsIgnoreCase(type)){
				return true;
			}
		}
		return false;
		
	}

	@WorldTracerTx(type = TxType.AF_SUMMARY)
	public List<Worldtracer_Actionfiles> getActionFileSummary(Agent user, String companyCode,
			String wtStation, ActionFileType afType, String afSeq, int day, WebServiceDto dto) throws WorldTracerException, CaptchaException, WorldTracerRecordNotFoundException {
		List<ActionFileDto> stuff;
		stuff = wtConnector.getActionFileSummary(companyCode, wtStation, afType, afSeq, day, dto);
		
		if(stuff == null || stuff.size() < 1) return null;
		
		List<Worldtracer_Actionfiles> result = new ArrayList<Worldtracer_Actionfiles>();
		for (ActionFileDto afd : stuff) {
			Worldtracer_Actionfiles wa = new Worldtracer_Actionfiles();
			wa.setAction_file_summary(afd.getSummary());
			wa.setAction_file_text(afd.getDetails());
			wa.setAction_file_type(afType);
			wa.setSeq(afSeq);
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
	public String getActionFileDetail(Agent user, String companyCode,
			String wtStation, ActionFileType afType, int day, int itemNum,
			WebServiceDto dto) throws WorldTracerException, CaptchaException {
		String result = wtConnector.getActionFileDetails(companyCode, wtStation, afType, day, itemNum, dto);
		return result;
	}
	
	@WorldTracerTx(type = TxType.SEND_PXF)
	public String sendPxf(Agent agent, WtqRequestPxf wtq, WebServiceDto dto) throws WorldTracerException, CaptchaException {
		return wtConnector.sendPxf(wtq, dto);
	}

	@WorldTracerTx(type = TxType.CREATE_QOH)
	public String insertQoh(Agent agent, WtqQoh wtqQoh, WebServiceDto dto) throws WorldTracerException, CaptchaException {
		return wtConnector.sendQoh(wtqQoh, dto);
	}


}
