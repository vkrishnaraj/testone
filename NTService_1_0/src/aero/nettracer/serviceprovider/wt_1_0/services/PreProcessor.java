package aero.nettracer.serviceprovider.wt_1_0.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bagnet.nettracer.tracing.bmo.CategoryBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Address;
import com.bagnet.nettracer.tracing.db.Incident_Claimcheck;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.Item_Inventory;
import com.bagnet.nettracer.wt.WorldTracerException;
import com.bagnet.nettracer.wt.svc.ContentRule;
import com.bagnet.nettracer.wt.svc.DefaultWorldTracerService;

import aero.nettracer.serviceprovider.common.exceptions.BagtagException;
import aero.nettracer.serviceprovider.common.utils.BagTagConversion;
import aero.nettracer.serviceprovider.wt_1_0.common.Agent;
import aero.nettracer.serviceprovider.wt_1_0.common.Ahl;
import aero.nettracer.serviceprovider.wt_1_0.common.ForwardOhd;
import aero.nettracer.serviceprovider.wt_1_0.common.Itinerary;
import aero.nettracer.serviceprovider.wt_1_0.common.Passenger;
import aero.nettracer.serviceprovider.wt_1_0.common.RequestOhd;
import aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse;
import aero.nettracer.serviceprovider.wt_1_0.dto.WorldTracerActionDTO;
import aero.nettracer.serviceprovider.wt_1_0.services.DefaultWorldTracerService.WorldTracerField;

public class PreProcessor {
	
	public static final DateFormat ITIN_DATE_FORMAT = new SimpleDateFormat("ddMMM", Locale.US);
	public static final String UNKNOWN_AIRLINE = "YY";
	private static final Pattern FLIGHTNUM_FORMAT = Pattern.compile("[1-9]\\d{0,3}[a-zA-Z]?");
	
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
		addConvertedTag(data.getBagTagNumber(), WorldTracerField.TN, result, dto.getUser().getProfile().getAirline());
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
					fd = UNKNOWN_AIRLINE + "/" + ITIN_DATE_FORMAT.format(itin.getFlightDate());
				} else {
					fd = itin.getAirline() + fnum + "/" + ITIN_DATE_FORMAT.format(itin.getFlightDate());
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
			    : ag.getUsername())
			    + "/" + ag.getAirline();
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
		}
		return "";
	}


	public static Map<WorldTracerField, List<String>> createAhlFieldMap(Ahl data) {

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


}
