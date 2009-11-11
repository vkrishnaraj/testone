package aero.nettracer.serviceprovider.wt_1_0.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import aero.nettracer.serviceprovider.common.exceptions.BagtagException;
import aero.nettracer.serviceprovider.common.utils.BagTagConversion;
import aero.nettracer.serviceprovider.wt_1_0.common.Agent;
import aero.nettracer.serviceprovider.wt_1_0.common.ForwardOhd;
import aero.nettracer.serviceprovider.wt_1_0.common.Itinerary;
import aero.nettracer.serviceprovider.wt_1_0.common.Passenger;
import aero.nettracer.serviceprovider.wt_1_0.common.RequestOhd;
import aero.nettracer.serviceprovider.wt_1_0.common.WorldTracerResponse;
import aero.nettracer.serviceprovider.wt_1_0.dto.WorldTracerActionDTO;
import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service.WorldTracerException;
import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service.WorldTracerService.WorldTracerField;

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


}
