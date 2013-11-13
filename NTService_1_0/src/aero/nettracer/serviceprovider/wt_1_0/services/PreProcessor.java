package aero.nettracer.serviceprovider.wt_1_0.services;

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

import aero.nettracer.serviceprovider.common.exceptions.BagtagException;
import aero.nettracer.serviceprovider.common.utils.BagTagConversion;
import aero.nettracer.serviceprovider.common.utils.StringUtils;
import aero.nettracer.serviceprovider.wt_1_0.common.Address;
import aero.nettracer.serviceprovider.wt_1_0.common.Agent;
import aero.nettracer.serviceprovider.wt_1_0.common.Ahl;
import aero.nettracer.serviceprovider.wt_1_0.common.ClaimCheck;
import aero.nettracer.serviceprovider.wt_1_0.common.Content;
import aero.nettracer.serviceprovider.wt_1_0.common.Expenses;
import aero.nettracer.serviceprovider.wt_1_0.common.ForwardMessage;
import aero.nettracer.serviceprovider.wt_1_0.common.ForwardOhd;
import aero.nettracer.serviceprovider.wt_1_0.common.Item;
import aero.nettracer.serviceprovider.wt_1_0.common.Itinerary;
import aero.nettracer.serviceprovider.wt_1_0.common.Ohd;
import aero.nettracer.serviceprovider.wt_1_0.common.Passenger;
import aero.nettracer.serviceprovider.wt_1_0.common.RequestOhd;
import aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse;
import aero.nettracer.serviceprovider.wt_1_0.dto.WorldTracerActionDTO;
import aero.nettracer.serviceprovider.wt_1_0.services.DefaultWorldTracerService.WorldTracerField;
import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service.BasicRule;
import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service.ContentRule;
import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service.WorldTracerRule;
import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service.WorldTracerRule.Format;

public class PreProcessor {
	
	public static final DateFormat ITIN_DATE_FORMAT = new SimpleDateFormat("ddMMM", Locale.US);
	public static final String UNKNOWN_AIRLINE = "YY";
	private static final Pattern FLIGHTNUM_FORMAT = Pattern.compile("[1-9]\\d{0,3}[a-zA-Z]?");
	public static final String FIELD_SEP = ".";
	public static final String ENTRY_SEP = "/";
	
	public static final String DEFAULT_BAG_TYPE = "99";
	
	
	public static final List<String> VALID_BAG_TYPES = Arrays.asList(new String[] { "01", "02", "03", "05", "06",
			"07", "08", "09", "10", "12", "20", "22", "23", "25", "26", "27", "28", "29", "50", "51", "52", "53", "54",
			"55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "71", "72", "73",
			"74", "75", "81", "82", "83", "85", "89", "90", "92", "93", "94", "95", "96", "97", "98", "99" });
	
	public static final List<String> wt_mats = Arrays.asList("D", "L", "M", "R", "T");
	public static final List<String> wt_descs = Arrays.asList("D", "L", "M", "R", "T", "B", "K", "C", "H", "S", "W",
			"X");

	
	public static Map<WorldTracerField, List<String>> requestQuickOhd(WorldTracerActionDTO dto, RequestOhd data,
      WorldTracerResponse response) throws WorldTracerException {
		Map<WorldTracerField, List<String>> fieldMap = createFieldMap(data, dto);
		
		if (data.getAhl() == null || data.getAhl().getAhlId() == null) {
			throw new WorldTracerException("No WorldTracer AHL ID provided.");
		}
		
		if (data.getBagTagNumber() == null || data.getBagTagNumber().trim().length() < 1 || data.getFromAirline() == null
				|| data.getFromAirline().trim().length() < 1 || data.getFromStation() == null
				|| data.getFromStation().trim().length() < 1) {
			throw new WorldTracerException("Unable to request QOH. BagTag, Airline, and Station are required");
		}

	  return fieldMap;
  }
	
	public static Map<WorldTracerField, List<String>> requestOhd(WorldTracerActionDTO dto, RequestOhd data,
		      WorldTracerResponse response) throws WorldTracerException {
				Map<WorldTracerField, List<String>> fieldMap = createFieldMap(data, dto);
				
				if (data.getAhl() == null || data.getAhl().getAhlId() == null) {
					throw new WorldTracerException("No WorldTracer AHL ID provided.");
				}
				
			  return fieldMap;
		  }
			

	private static Map<WorldTracerField, List<String>> createFieldMap(RequestOhd data, WorldTracerActionDTO dto) {
		if (data == null) {
			return null;
		}

		Map<WorldTracerField, List<String>> result = new EnumMap<WorldTracerField, List<String>>(WorldTracerField.class);
		if (data.getAhl() != null && data.getAhl().getPax() != null
				&& data.getAhl().getPax().length > 0) {
			addIncidentFieldEntry(WorldTracerField.NM, ((Passenger) data.getAhl().getPax()[0])
					.getLastname(), result);
		}
		if (data.getTeletype() != null) {
			for (String tt : data.getTeletype()) {
				addIncidentFieldEntry(WorldTracerField.TX, tt, result);
			}
		}

		if (data.getFurtherInfo() != null && data.getFurtherInfo().trim().length() > 0) {
			addIncidentFieldEntry(WorldTracerField.FI, data.getFurtherInfo(), result);
			addIncidentFieldEntry(WorldTracerField.TI, data.getFurtherInfo(), result);
			
		}

		addIncidentFieldEntry(WorldTracerField.AG, getAgentEntry(data.getAgent()), result);
		if (data.getBagTagNumber() != null) {
			addConvertedTag(data.getBagTagNumber(), WorldTracerField.TN, result, dto.getUser().getProfile().getAirline());
		}
		return result;

	}
	
	

	public static Map<WorldTracerField, List<String>> forwardOhd(
      WorldTracerActionDTO dto, ForwardOhd data, WorldTracerResponse response) throws WorldTracerException {
		if (data == null) {
			return null;
		}

		Map<WorldTracerField, List<String>> result = new EnumMap<WorldTracerField, List<String>>(WorldTracerField.class);
		addIncidentFieldEntry(WorldTracerField.AG, getAgentEntry(data.getAgent()), result);

		addConvertedTag(data.getExpediteNumber(), WorldTracerField.XT, result, dto.getUser().getProfile().getAirline());

		if (data.getFwdName() != null) {
			for (String name : data.getFwdName()) {
				addIncidentFieldEntry(WorldTracerField.NM, name.trim(), result);
			}
		}

		if (data.getTeletype() != null) {
			for (String tt : data.getTeletype()) {
				addIncidentFieldEntry(WorldTracerField.TX, tt.trim(), result);
			}
		}

		addIncidentFieldEntry(WorldTracerField.SI, data.getSupplementaryInfo(), result);
		addIncidentFieldEntry(WorldTracerField.TI, data.getSupplementaryInfo(), result);

		String fw = null;
		if (data.getItinerary() != null) {
			for (Itinerary itin : data.getItinerary()) {
				if (itin.getAirline() == null || itin.getAirline().trim().length() <= 0 || itin.getFlightNumber() == null
						|| itin.getFlightNumber().trim().length() <= 0 || itin.getDepartureCity() == null
						|| itin.getDepartureCity().trim().length() <= 0 || itin.getArrivalCity() == null
						|| itin.getArrivalCity().trim().length() <= 0 || itin.getFlightDate() == null) {
					continue;
				}
				
				String fnum = wtFlightNumber(itin.getFlightNumber());
				String fd = null;
				if (fnum.length() == 0) {
					fd = UNKNOWN_AIRLINE + "/" + ITIN_DATE_FORMAT.format(itin.getFlightDate().getTime());
				} else {
					fd = itin.getAirline() + fnum + "/" + ITIN_DATE_FORMAT.format(itin.getFlightDate().getTime());
				}
				addIncidentFieldEntry(WorldTracerField.FO, fd, result);
				addIncidentFieldEntry(WorldTracerField.NF, fd, result);
				List<String> routing = result.get(WorldTracerField.NR);
				if (routing == null || !routing.get(routing.size() - 1).equalsIgnoreCase(itin.getDepartureCity().trim())) {
					addIncidentFieldEntry(WorldTracerField.NR, itin.getDepartureCity().trim(), result);
				}
				addIncidentFieldEntry(WorldTracerField.NR, itin.getArrivalCity().trim(), result);
				fw = itin.getArrivalCity() + itin.getAirline();
				addIncidentFieldEntry(WorldTracerField.FW, fw, result);
			}
		}
		List<String> foo = result.get(WorldTracerField.FW);
		if (foo != null && foo.size() > 0) {
			foo.set(foo.size() - 1, data.getDestinationStation() + data.getDestinationAirline());
		} else {
			throw new WorldTracerException("invalid forward itinerary");
		}

		return result;
  }

	
	
	// TODO: HELPER METHODS BELOW
	
	public static void addIncidentFieldEntry(WorldTracerField key, String value, Map<WorldTracerField, List<String>> result) {
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
	

	public static String getAgentEntry(Agent ag) {
		if (ag != null)
			return (ag.getUsername().length() > 7 ? ag.getUsername().substring(0, 7)
			    : ag.getUsername());
		return "NTRACER";
	}
	
	public static void addConvertedTag(String tag, WorldTracerField field, Map<WorldTracerField, List<String>> result, String companyCode) {
		String bagTagString = null;
		try {
			bagTagString = BagTagConversion.getTwoCharacterBagTag(tag.trim());
		} catch (BagtagException e) {
			// couldn't figure out the tag.
			Pattern wt_patt = Pattern.compile("([a-zA-Z0-9]{2})(\\d{1,6})");
			Matcher m = wt_patt.matcher(tag.trim());
			if (m.find() && BagTagConversion.getThreeDigitTicketingCode(m.group(1)) != null) {
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
				PreProcessor.addIncidentFieldEntry(field, bagTagString, result);
			}
		}
	}


	public static String wtFlightNumber(String flightnum) {
		if (flightnum == null)
			return "";

		Matcher m = FLIGHTNUM_FORMAT.matcher(flightnum);
		if (m.find()) {
			return m.group();
		} else if (flightnum.equals("00")){
			return "00";
		}
		return "";
	}

	private static String formatAddress(String a, WorldTracerRule<String> rule){
		if(a != null){
			try {
				if(rule != null){
					return rule.formatEntry(a.replaceAll("[" + FIELD_SEP + ENTRY_SEP + "]", " "));
				} else {
					return a.trim().replaceAll("[" + FIELD_SEP + ENTRY_SEP + "]", " ");
				}
			} catch (WorldTracerException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	private static Address getAddress(Passenger[]pax, boolean perm){
		Address a = null;
		if(pax != null){
			for(int j = 0; j < pax.length;j++){
				//loupas - if an address as a person with a last name, it is considered a permanent address
				boolean hasLastName = pax[j].getLastname() != null && pax[j].getLastname().trim().length() > 0;
				if(pax[j].getAddress() != null && 
						((perm && (!pax[j].getAddress().isTemporaryAddress() || hasLastName)) ||
						(!perm && pax[j].getAddress().isTemporaryAddress() && !hasLastName))){
					a = pax[j].getAddress();
					break;
				}
			}
			if(a != null){
				BasicRule br = new BasicRule(1, 57, 1, aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service.WorldTracerRule.Format.FREE_FLOW);
				a.setAddress1(formatAddress(a.getAddress1(), br));
				a.setAddress2(formatAddress(a.getAddress2(), br));
				a.setCity(formatAddress(a.getCity(), br));
				a.setState(formatAddress(a.getState(), br));
				a.setProvince(formatAddress(a.getProvince(), br));
				a.setZip(formatAddress(a.getZip(), br));
				a.setCountryCode(formatAddress(a.getCountryCode(), br));
			}
		}
		return a;
	}
	
	/**
	 * Returns first permanent/temp address from the passenger list
	 * @param data
	 * @param perm
	 * @return
	 */
	public static Address getAhlAddress(Ahl data, boolean perm){
		if (data != null && data.getPax() != null){
			return getAddress(data.getPax(),perm);
		}
		return null;
	}
	
	/**
	 * Returns first permanent/temp address from the passenger list
	 * @param data
	 * @param perm
	 * @return
	 */
	public static Address getOhdAddress(Ohd data, boolean perm){
		if (data != null && data.getPax() != null){
			return getAddress(data.getPax(),perm);
		}
		return null;
	}

	public static Map<WorldTracerField, List<String>> createAhlFieldMap(Ahl data) throws WorldTracerException {


		Map<WorldTracerField, List<String>> result = new EnumMap<WorldTracerField, List<String>>(WorldTracerField.class);

		// this is a little messy, but we are keeping track of the addresses
		// here.
		// ones explicitly marked permanent get highest priority for the PA
		// field
		List<Address> permAdds = new ArrayList<Address>();
		List<Address> namedAdds = new ArrayList<Address>();
		List<Address> tempAdds = new ArrayList<Address>();
		String language = null;
		if(data.getPax() != null) {
			int passengerCount = 0;
			for (Passenger p : data.getPax()) {
				if(language == null){
					if(p.getLanguageFreeFlow() != null && p.getLanguageFreeFlow().trim().length() > 0){
						addIncidentFieldEntry(WorldTracerField.LA, p.getLanguageFreeFlow(), result);
					}
				}
				if (passengerCount < 3) {
					++passengerCount;

					getPassengerInfo(p, result);
					Address a = p.getAddress();
					if (a != null) {
						if (!a.isTemporaryAddress()) {
							permAdds.add(a);
						} else if (p.getLastname() == null
						    || p.getLastname().trim().length() <= 0) {
							tempAdds.add(a);
						} else {
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
		addIncidentFieldEntry(WorldTracerField.NP, Integer.toString(data.getNumberPaxAffected()), result);

		addIncidentFieldEntry(WorldTracerField.PR, data.getPnrLocator(), result);

		addIncidentFieldEntry(WorldTracerField.FI, data.getFurtherInfo(), result);

		if (data.getBagItinerary() != null) {
			for (Itinerary i : data.getBagItinerary()) {
				getItineraryInfo(i, result, false);
			}
		}
		
		if (data.getPaxItinerary() != null) {
			for (Itinerary i : data.getPaxItinerary()) {
				getItineraryInfo(i, result, true);
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

		if (data.getClaimCheck() != null) {
			for (ClaimCheck ic : data.getClaimCheck()) {
				if (ic.getTagNumber() != null && ic.getTagNumber().trim().length() > 0) {
					addClaimCheckNum(ic.getTagNumber(), result, ic.getAirlineCode());
				}
			}
		}

		// contents category can only have one entry in WT, but you can use up
		// to two lines for each
		// you can have 12 total categories (see ContentRule class)
		if (data.getItem() != null) {
			int bagCount = 1;
			for (Item i : data.getItem()) {
				getItemInfo(i, result);
				if (i.getContent() != null) {

					Map<String, List<String>> temp = new HashMap<String, List<String>>();
					for (Content inv : i.getContent()) {
						String category = inv.getCategory();
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
		addIncidentFieldEntry(WorldTracerField.AG, getAgentEntry(data.getAgent()),
				result);
		return result;
	}
	
	private static void getPassengerInfo(Passenger p,
	    Map<WorldTracerField, List<String>> result) throws WorldTracerException {
		Address address = p.getAddress();
		if (p.getLastname() != null && p.getLastname().trim().length() > 0) {
			// add the name
			addIncidentFieldEntry(WorldTracerField.NM, p.getLastname().trim(), result);

			// add the initials
			String initials = null;
			if (p.getFirstname() != null && p.getFirstname().trim().length() > 0) {
				initials = p.getFirstname().trim().substring(0, 1)
				    + p.getLastname().trim().substring(0, 1);
			} else {
				initials = p.getLastname().trim().substring(0, 1);
			}
			addIncidentFieldEntry(WorldTracerField.IT, initials, result);

			// add the passenger title (salutation)
			if (p.getSalutation() == 0) {
				addIncidentFieldEntry(WorldTracerField.PT, p.getFirstname(), result);
			} else {
				String title = StringUtils.join(" ", p.getSalutationDescription() != null ? p
				    .getSalutationDescription() : "", p.getFirstname() != null ? p
				    .getFirstname() : "");
				addIncidentFieldEntry(WorldTracerField.PT, title, result);
			}

			if (result.get(WorldTracerField.PT) == null) {
				throw new WorldTracerException(
				    "Salutation or first name required to create AHL");
			}

			// add the frequent flier class status
			addIncidentFieldEntry(WorldTracerField.PS, p.getFfStatus(),
			    result);

			// add the frequent flier num
			String membership = p.getFfNumber();
			if (p.getFfAirline() != null
			    && p.getFfAirline().length() > 0) {
				membership = p.getFfAirline() + membership;
			}
			BasicRule rule = new BasicRule(1, 25, 10, Format.ALPHA_NUMERIC);
			addIncidentFieldEntry(WorldTracerField.FL, rule.formatEntry(membership), result);
		}
		if (address != null) {

			// add email

			addIncidentFieldEntry(WorldTracerField.EA, wtEscape(address.getEmailAddress()),
			    result);

			// add home phone
			addIncidentFieldEntry(WorldTracerField.PN,
			    wtPhone(address.getHomePhone()), result);

			// add work phone
			addIncidentFieldEntry(WorldTracerField.TP,
			    wtPhone(address.getWorkPhone()), result);

			// add cell phone
			addIncidentFieldEntry(WorldTracerField.CP, wtPhone(address.getMobilePhone()),
			    result);

			// add fax num
			addIncidentFieldEntry(WorldTracerField.FX,
			    wtPhone(address.getAltPhone()), result);

			// add country
			addIncidentFieldEntry(WorldTracerField.CO, address.getCountryCode(),
			    result);
		}
	}
	
	public static String wtPhone(String rawText) {
		return rawText != null ? rawText.replaceAll("\\D", "") : null;
	}
	

	private static String wtClear(String rawText) {
		return rawText != null ? rawText.replaceAll("[" + FIELD_SEP + ENTRY_SEP + "]", " ") : null;
	}

	private static String wtEscape(String rawText) {
		return rawText != null ? rawText.replace("@", "/A/").replace(".", "/D/").replace("_", "/U/")
				.replace("~", "/T/").replace("+", "/P/") : null;
	}

	private static void addWtIncAddress(Map<WorldTracerField, List<String>> result, Address address,
			WorldTracerField addressField) {
		ArrayList<String> addr1Pieces = new ArrayList<String>();
		if (address.getAddress1() != null)
			addr1Pieces.add(address.getAddress1().trim());
		if (address.getAddress2() != null)
			addr1Pieces.add(address.getAddress2().trim());
		if (address.getCity() != null)
			addr1Pieces.add(address.getCity().trim());
		if(address.getState() != null && address.getState().trim().length() > 0 && ("US".equals(address.getCountryCode()) || "United States".equalsIgnoreCase(address.getCountryCode()))) {
			addr1Pieces.add(address.getState().trim());
			if (addressField.equals(WorldTracerField.PA)) {
				addIncidentFieldEntry(WorldTracerField.STATE, address.getState(), result);
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

	private static void getItemInfo(Item item, Map<WorldTracerField, List<String>> result, boolean includeXdesc) {

		if (item.getColor() == null || item.getColor().trim().length() <= 0 || item.getType() == null
				|| item.getType().trim().length() != 2) {
			return;
		}

		String colorType = "";
		if ("TD".equals(item.getColor().trim())) {
			colorType = "BN";
		} else {
			colorType = item.getColor().trim();
		}

		String type = item.getType().trim();
		if (!VALID_BAG_TYPES.contains(type)) {
			type = DEFAULT_BAG_TYPE;
		}
		colorType += type;

		String desc1 = mapXDesc(item.getDesc1());
		String desc2 = mapXDesc(item.getDesc2());
		String desc3 = mapXDesc(item.getDesc3());

		colorType += getDescString(desc1, desc2, desc3);

		addIncidentFieldEntry(WorldTracerField.CT, colorType, result);

		addIncidentFieldEntry(WorldTracerField.BI, item.getManufacturer(), result);
	}
	

	public static String mapXDesc(String code) {
		if (code == null || !wt_descs.contains(code)) {
			return "X";
		}
		return code;
	}
	
	private static String getDescString(String... descs) {
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
	
	private static void getItineraryInfo(Itinerary itin, Map<WorldTracerField, List<String>> result, boolean isPassengerRouting) {

		// make sure it's a valid itinerary
		if (itin.getAirline() == null || itin.getAirline().trim().length() <= 0 || itin.getDepartureCity() == null
				|| itin.getDepartureCity().trim().length() <= 0 || itin.getArrivalCity() == null
				|| itin.getArrivalCity().trim().length() <= 0 || itin.getFlightDate() == null) {
			return;
		}

		String fnum = wtFlightNumber(itin.getFlightNumber());
		String fd = null;
		if (fnum.length() == 0 && isPassengerRouting) {
			fd = UNKNOWN_AIRLINE + "/" + ITIN_DATE_FORMAT.format(itin.getFlightDate().getTime());
		} else {
			fd = itin.getAirline() + fnum + "/" + ITIN_DATE_FORMAT.format(itin.getFlightDate().getTime().getTime());
		}

		if (!isPassengerRouting) {
			addIncidentFieldEntry(WorldTracerField.BR, fd, result);
		} else if (isPassengerRouting) {
			addIncidentFieldEntry(WorldTracerField.FD, fd, result);
			List<String> routing = result.get(WorldTracerField.RT);
			if (routing == null || !routing.get(routing.size() - 1).equalsIgnoreCase(itin.getDepartureCity().trim())) {
				addIncidentFieldEntry(WorldTracerField.RT, itin.getDepartureCity().trim(), result);
			}
			addIncidentFieldEntry(WorldTracerField.RT, itin.getArrivalCity().trim(), result);
		}
	}
	

	private static void addClaimCheckNum(String claimCheck, Map<WorldTracerField, List<String>> result, String companyCode) {
		addConvertedTag(claimCheck, WorldTracerField.TN, result, companyCode);
	}
	

	protected static void getItemInfo(Item item, Map<WorldTracerField, List<String>> result) {
		getItemInfo(item, result, true);
	}
	
	public static Map<WorldTracerField, List<String>> createCloseFieldMap(Ahl incident) {
		Map<WorldTracerField, List<String>> result = new EnumMap<WorldTracerField, List<String>>(WorldTracerField.class);
		String cs_fmt = "%02d %s/%s%1.2f";
		int claimCount = 0;
		if (incident.getExpenses() != null) {
			String cost;

			if (incident.getExpenses() != null) {
				for (Expenses expense : incident.getExpenses()) {
					if (expense.getApprovalDate() != null && expense.getCurrrency() != null) {
						claimCount++;
						if ("ADV".equals(expense.getPaycode())) {
							cost = String.format(cs_fmt, claimCount, "A", expense.getCurrrency(), expense
									.getCheckamt());
						} else if ("DEL".equals(expense.getPaycode())) {
							cost = String.format(cs_fmt, claimCount, "D", expense.getCurrrency(), expense
									.getCheckamt());
						} else if ("FIN".equals(expense.getPaycode())) {
							cost = String.format(cs_fmt, claimCount, "F", expense.getCurrrency(), expense
									.getCheckamt());
						} else if ("INS".equals(expense.getPaycode())) {
							cost = String.format(cs_fmt, claimCount, "I", expense.getCurrrency(), expense
									.getCheckamt());
						} else {
							cost = String.format(cs_fmt, claimCount, "X", expense.getCurrrency(), expense
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

		if (incident.getFaultReason() != 0 || incident.getFaultReason() > 79) {
			addIncidentFieldEntry(WorldTracerField.RL, Integer.toString(incident.getFaultReason()), result);
			addIncidentFieldEntry(WorldTracerField.RC, incident.getFaultReasonDescription(), result);
		} else {
			addIncidentFieldEntry(WorldTracerField.RL, "79", result);
			addIncidentFieldEntry(WorldTracerField.RC, "Created in error", result);
		}
		if (incident.getFaultStation() != null) {
			
			if (incident.getFaultStation() == null || incident.getFaultStation().trim().length() < 1) {
				addIncidentFieldEntry(WorldTracerField.FS, incident.getStationCode(), result);
			} else {
				addIncidentFieldEntry(WorldTracerField.FS, incident.getFaultStation(), result);
			}
		} else {
			addIncidentFieldEntry(WorldTracerField.FS, incident.getStationCode(), result);
		}
		addIncidentFieldEntry(WorldTracerField.AG, PreProcessor.getAgentEntry(incident.getAgent()), result);
		return result;
	}

	public static Map<WorldTracerField, List<String>> createOhdFieldMap(Ohd ohd) throws WorldTracerException {
		if (ohd == null) {
			return null;
		}

		Map<WorldTracerField, List<String>> result = new EnumMap<WorldTracerField, List<String>>(WorldTracerField.class);

		if (ohd.getItem() == null || ohd.getItem().getColor() == null || ohd.getItem().getColor().trim().length() <= 0 || ohd.getItem().getType() == null
				|| ohd.getItem().getType().trim().length() != 2) {
			throw new WorldTracerException("OHD missing color / type");
		}

		String colorType = "";
		if ("TD".equals(ohd.getItem().getColor().trim())) {
			colorType = "BN";
		} else {
			colorType = ohd.getItem().getColor().trim();
		}

		String type = ohd.getItem().getType().trim();
		if (!VALID_BAG_TYPES.contains(type)) {
			type = DEFAULT_BAG_TYPE;
		}
		colorType += type;

		String desc1 = mapXDesc(ohd.getItem().getDesc1());
		String desc2 = mapXDesc(ohd.getItem().getDesc2());
		String desc3 = mapXDesc(ohd.getItem().getDesc3());

		colorType += getDescString(desc1, desc2, desc3);

		addIncidentFieldEntry(WorldTracerField.CT, colorType, result);

		if (ohd.getClaimCheck() != null && ohd.getClaimCheck().getTagNumber().trim().length() > 0) {
			addClaimCheckNum(ohd.getClaimCheck().getTagNumber(), result, ohd.getClaimCheck().getAirlineCode());
		}

		addIncidentFieldEntry(WorldTracerField.BI, ohd.getItem().getManufacturer(), result);

		addIncidentFieldEntry(WorldTracerField.PR, ohd.getPnrLocator(), result);

		addIncidentFieldEntry(WorldTracerField.SL, ohd.getStorageLocation(), result);

		addIncidentFieldEntry(WorldTracerField.FI, ohd.getFurtherInfo(), result);

		if (ohd.getPax() != null && ohd.getPax().length > 0 && ohd.getPax()[0].getFfNumber() != null) {
			String membership = ohd.getPax()[0].getFfNumber(); 
			if (ohd.getPax()[0].getFfAirline() != null) {
				membership = ohd.getPax()[0].getFfAirline() + membership;
			}
			BasicRule rule = new BasicRule(1, 25, 10, Format.ALPHA_NUMERIC);
			addIncidentFieldEntry(WorldTracerField.FL, rule.formatEntry(membership), result);
		}

		if (ohd.getBagItinerary() == null || ohd.getBagItinerary().length == 0) {
			throw new WorldTracerException("OHD missing itinerary");
		}
		for (Itinerary itin : ohd.getBagItinerary()) {
			getOhdItineraryInfo(itin, result);
		}

		
		if(ohd.getItem().getLastNameOnBag() != null && ohd.getItem().getLastNameOnBag().trim().length() > 0){
			String initials;
			addIncidentFieldEntry(WorldTracerField.NM, ohd.getItem().getLastNameOnBag(), result);
			if (ohd.getItem().getFirstNameOnBag() != null && ohd.getItem().getFirstNameOnBag().trim().length() > 0) {
				addIncidentFieldEntry(WorldTracerField.PT, ohd.getItem().getFirstNameOnBag(), result);
				initials = ohd.getItem().getFirstNameOnBag().trim().substring(0, 1) 
				+ ohd.getItem().getLastNameOnBag().trim().substring(0, 1);
			} else {
				initials = ohd.getItem().getLastNameOnBag().trim().substring(0, 1);
			}
			addIncidentFieldEntry(WorldTracerField.IT, initials, result);
		}

		for (Passenger p :  ohd.getPax()) {
			getOhdPaxInfo(p, result);
		}

		if (ohd.getItem() != null) {
			Map<String, List<String>> temp = new HashMap<String, List<String>>();
			for (Content inv : ohd.getItem().getContent()) {
				String category = inv.getCategory();
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

		addIncidentFieldEntry(WorldTracerField.AG, getAgentEntry(ohd.getAgent()), result);
		return result;

	}
	
	private static void getOhdItineraryInfo(Itinerary itin, Map<WorldTracerField, List<String>> result) {
		// make sure it's a valid itinerary
		if (itin.getAirline() == null || itin.getAirline().trim().length() <= 0 || itin.getFlightNumber() == null
				|| itin.getFlightNumber().trim().length() <= 0 || itin.getDepartureCity() == null
				|| itin.getDepartureCity().trim().length() <= 0 || itin.getArrivalCity() == null
				|| itin.getArrivalCity().trim().length() <= 0 || itin.getFlightDate() == null) {
			return;
		}

		String fnum = wtFlightNumber(itin.getFlightNumber());
		String fd = null;
		if (fnum.length() == 0) {
			fd = UNKNOWN_AIRLINE + "/" + ITIN_DATE_FORMAT.format(itin.getFlightDate().getTime());
		} else {
			fd = itin.getAirline() + fnum + "/" + ITIN_DATE_FORMAT.format(itin.getFlightDate().getTime());
		}

		addIncidentFieldEntry(WorldTracerField.FD, fd, result);
		List<String> routing = result.get(WorldTracerField.RT);
		if (routing == null || !routing.get(routing.size() - 1).equalsIgnoreCase(itin.getDepartureCity().trim())) {
			addIncidentFieldEntry(WorldTracerField.RT, itin.getDepartureCity().trim(), result);
		}
		addIncidentFieldEntry(WorldTracerField.RT, itin.getArrivalCity().trim(), result);
	}
	
	private static void getOhdPaxInfo(Passenger p, Map<WorldTracerField, List<String>> result) {
		Address address = p.getAddress();
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
			addIncidentFieldEntry(WorldTracerField.EA, wtEscape(address.getEmailAddress()), result);

			// add home phone
			addIncidentFieldEntry(WorldTracerField.PN, wtPhone(address.getHomePhone()), result);

			// add work phone
			addIncidentFieldEntry(WorldTracerField.TP, wtPhone(address.getWorkPhone()), result);

			// add cell phone
			addIncidentFieldEntry(WorldTracerField.CP, wtPhone(address.getMobilePhone()), result);

			// add fax num
			addIncidentFieldEntry(WorldTracerField.FX, wtPhone(address.getAltPhone()), result);

			// add country
			addIncidentFieldEntry(WorldTracerField.CO, address.getCountryCode(), result);
		}
	}
	
	private static void addWtOhdAddress(Map<WorldTracerField, List<String>> result, Address address,
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


	public static Map<WorldTracerField, List<String>> createOhdCloseFieldMap(
      Ohd data) {
		Map<WorldTracerField, List<String>> result = new EnumMap<WorldTracerField, List<String>>(WorldTracerField.class);
		addIncidentFieldEntry(WorldTracerField.AG, getAgentEntry(data.getAgent()), result);
		return result;
  }
	
	public static Map<WorldTracerField, List<String>> createFwdFieldMap(ForwardMessage fwd, WorldTracerActionDTO dto) throws WorldTracerException {
		if (fwd == null) {
			return null;
		}

		Map<WorldTracerField, List<String>> result = new EnumMap<WorldTracerField, List<String>>(WorldTracerField.class);
		
		
		if (fwd.getFaultReason() != 0) {
			PreProcessor.addIncidentFieldEntry(WorldTracerField.RL, Integer.toString(fwd.getFaultReason()), result);
		}

		PreProcessor.addIncidentFieldEntry(WorldTracerField.RC, fwd.getFaultReasonDescription(), result);
		
		PreProcessor.addIncidentFieldEntry(WorldTracerField.AG, PreProcessor.getAgentEntry(fwd.getAgent()), result);

		PreProcessor.addConvertedTag(fwd.getExpediteTag(), WorldTracerField.XT, result, fwd.getFromAirline());
		if (fwd.getTagNum() != null) {
			PreProcessor.addConvertedTag(fwd.getTagNum(), WorldTracerField.TN, result, fwd.getFromAirline());
		}
		

		if (fwd.getName() != null) {
			for (String name : fwd.getName()) {
				PreProcessor.addIncidentFieldEntry(WorldTracerField.NM, name.trim(), result);
			}
		}

		if (fwd.getTeletype() != null) {
			for (String tt : fwd.getTeletype()) {
				PreProcessor.addIncidentFieldEntry(WorldTracerField.TX, tt.trim(), result);
			}
		}

		PreProcessor.addIncidentFieldEntry(WorldTracerField.SI, fwd.getSuplementaryInfo(), result);
		PreProcessor.addIncidentFieldEntry(WorldTracerField.TI, fwd.getTextInfo(), result);

		String fw = null;
		if (fwd.getItinerary() != null) {
			for (Itinerary itin : fwd.getItinerary()) {
				if (itin.getAirline() == null || itin.getAirline().trim().length() <= 0 || itin.getFlightNumber() == null
						|| itin.getFlightNumber().trim().length() <= 0 || itin.getDepartureCity() == null
						|| itin.getDepartureCity().trim().length() <= 0 || itin.getArrivalCity() == null
						|| itin.getArrivalCity().trim().length() <= 0 || itin.getFlightDate() == null) {
					continue;
				}
				String fnum = PreProcessor.wtFlightNumber(itin.getFlightNumber());
				String fd = null;
				if (fnum.length() == 0) {
					fd = PreProcessor.UNKNOWN_AIRLINE + "/" + PreProcessor.ITIN_DATE_FORMAT.format(itin.getFlightDate().getTime());
				} else {
					fd = itin.getAirline() + fnum + "/" + PreProcessor.ITIN_DATE_FORMAT.format(itin.getFlightDate().getTime());
				}
				PreProcessor.addIncidentFieldEntry(WorldTracerField.FO, fd, result);
				PreProcessor.addIncidentFieldEntry(WorldTracerField.NF, fd, result);
				List<String> routing = result.get(WorldTracerField.NR);
				if (routing == null || !routing.get(routing.size() - 1).equalsIgnoreCase(itin.getDepartureCity().trim())) {
					PreProcessor.addIncidentFieldEntry(WorldTracerField.NR, itin.getDepartureCity().trim(), result);
				}
				PreProcessor.addIncidentFieldEntry(WorldTracerField.NR, itin.getArrivalCity().trim(), result);
				fw = itin.getArrivalCity() + itin.getAirline();
				PreProcessor.addIncidentFieldEntry(WorldTracerField.FW, fw, result);
			}
		}
		List<String> foo = result.get(WorldTracerField.FW);
		if (foo != null && foo.size() > 0) {
			foo.set(foo.size() - 1, fwd.getDestinationStation() + fwd.getDestinationAirline());
		} else {
			throw new WorldTracerException("invalid forward itinerary");
		}

		return result;
	}

}
