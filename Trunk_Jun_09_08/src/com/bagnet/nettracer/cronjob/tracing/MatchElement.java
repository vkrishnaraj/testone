/**
 * MatchElement.java
 * 
 * This enum is responsible for maintaining the list of
 * elements that we wish to compare against one another
 * as part of the tracing process as well as define the
 * method by which elements will be compared in the OHDs
 * and incidents.
 * 
 * Match Methods:  
 *  If no element to compare against exists in either the
 *  incident or the OHD, then an empty ArrayList should
 *  be returned.  Otherwise the value of the comparison
 *  should be returned.
 * 
 *  If a 1 to 1 comparison, the result of the comparison
 *  should be returned if if it is a 0% match.
 *  
 *  If a many to 1 or many to many comparison, the full
 *  cross product should be returned.  It is the scoring
 *  algorithm's responsibility to determine what will
 *  be scored and how.  
 */
package com.bagnet.nettracer.cronjob.tracing;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.bagnet.nettracer.cronjob.tracing.dto.MatchResult;
import com.bagnet.nettracer.exceptions.BagtagException;
import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.db.Address;
import com.bagnet.nettracer.tracing.db.AirlineMembership;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Incident_Claimcheck;
import com.bagnet.nettracer.tracing.db.Item;
import com.bagnet.nettracer.tracing.db.Item_Inventory;
import com.bagnet.nettracer.tracing.db.Itinerary;
import com.bagnet.nettracer.tracing.db.OHD;
import com.bagnet.nettracer.tracing.db.OHD_Address;
import com.bagnet.nettracer.tracing.db.OHD_Inventory;
import com.bagnet.nettracer.tracing.db.OHD_Itinerary;
import com.bagnet.nettracer.tracing.db.OHD_Passenger;
import com.bagnet.nettracer.tracing.db.Passenger;
import com.bagnet.nettracer.tracing.utils.StringUtils;
import com.bagnet.nettracer.tracing.utils.TracerUtils;
import com.bagnet.nettracer.tracing.utils.lookup.LookupAirlineCodes;

public enum MatchElement {
	
	RECORD_LOCATOR {
		
		String getConstant() {
			return TracingConstants.MATCH_RECORDLOC;
		}
		ArrayList<MatchResult> match(Incident incident, OHD ohd) {
			return matchRecordLocator(this, incident, ohd);
		}
	},
	MEM_NUMBER {
		String getConstant() {
			return TracingConstants.MATCH_MEMBER;
		}
		ArrayList<MatchResult> match(Incident incident, OHD ohd) {
			return matchMemberNumber(this, incident, ohd);
		}
	},
	NAME {
		String getConstant() {
			return TracingConstants.MATCH_NAME;
		}
		ArrayList<MatchResult> match(Incident incident, OHD ohd) {
			return matchName(this, incident, ohd);
		}
	},
	ADDRESS {
		String getConstant() {
			return TracingConstants.MATCH_ADDRESS;
		}
		ArrayList<MatchResult> match(Incident incident, OHD ohd) {
			return matchAddress(this, incident, ohd);
		}
	},
	ITINERARY {
		String getConstant() {
			return TracingConstants.MATCH_ITINERARY;
		}
		ArrayList<MatchResult> match(Incident incident, OHD ohd) {
			return matchItinerary(this, incident, ohd);
		}
	},
	EMAIL {
		String getConstant() {
			return TracingConstants.MATCH_EMAIL;
		}
		ArrayList<MatchResult> match(Incident incident, OHD ohd) {
			return matchEmail(this, incident, ohd);
		}
	},
	PHONE {
		String getConstant() {
			return TracingConstants.MATCH_PHONE;
		}
		ArrayList<MatchResult> match(Incident incident, OHD ohd) {
			return matchPhone(this, incident, ohd);
		}
	},
	MANUFACTURER {
		String getConstant() {
			return TracingConstants.MATCH_MANUFACTURER;
		}
		ArrayList<MatchResult> match(Incident incident, OHD ohd) {
			return matchManufacturer(this, incident, ohd);
		}
	},
	COLOR {
		String getConstant() {
			return TracingConstants.MATCH_COLOR;
		}
		ArrayList<MatchResult> match(Incident incident, OHD ohd) {
			return matchColor(this, incident, ohd);
		}
	},
	TYPE {
		String getConstant() {
			return TracingConstants.MATCH_BAGTYPE;
		}
		ArrayList<MatchResult> match(Incident incident, OHD ohd) {
			return matchType(this, incident, ohd);
		}
	},
	CLAIM_CHECK {
		String getConstant() {
			return TracingConstants.MATCH_CLAIMCHECK;
		}
		ArrayList<MatchResult> match(Incident incident, OHD ohd) {
			return matchClaimCheck(this, incident, ohd);
		}
	},
	ELEMENTS {
		String getConstant() {
			return TracingConstants.MATCH_XDESC;
		}
		ArrayList<MatchResult> match(Incident incident, OHD ohd) {
			return matchElement(this, incident, ohd);
		}
	},
	CONTENTS {
		String getConstant() {
			return TracingConstants.MATCH_CONTENTS;
		}
		ArrayList<MatchResult> match(Incident incident, OHD ohd) {
			return matchContent(this, incident, ohd);
		}
	};

	abstract ArrayList<MatchResult> match(Incident incident, OHD ohd);
	abstract String getConstant();

	/**
	 * Returns the stringCompare result of the two record locators.
	 * 
	 * @param e
	 * @param incident
	 * @param ohd
	 * @return
	 */
	private static ArrayList<MatchResult> matchRecordLocator(MatchElement e,
			Incident incident, OHD ohd) {
		return MatchUtils.stringCompareToArray(e, incident.getRecordlocator(),
				ohd.getRecord_locator());
	}

	/**
	 * Compares all incident memberships to the singular OHD membership. Returns
	 * the cross product of the string compare in all cases where both the
	 * number and the airline are present.
	 * 
	 * @param e
	 * @param incident
	 * @param ohd
	 * @return
	 */
	private static ArrayList<MatchResult> matchMemberNumber(MatchElement e,
			Incident incident, OHD ohd) {
		ArrayList<MatchResult> al = new ArrayList<MatchResult>();

		AirlineMembership om = ohd.getMembership();
		if (om != null && om.getCompanycode_ID() != null && om.getMembershipnum() != null
				&& om.getMembershipnum().trim().length() > 0) {
			for (Passenger pax : (Set<Passenger>) incident.getPassengers()) {
				AirlineMembership im = pax.getMembership();
				if (im != null) {
					if (im.getCompanycode_ID() != null
							&& im.getMembershipnum() != null
							&& im.getMembershipnum().trim().length() > 0) {
						al.add(MatchUtils.stringCompare(e, im.getCompanycode_ID()
								+ im.getMembershipnum(), om.getCompanycode_ID()
								+ om.getMembershipnum()));
					}
				}
			}
		}
		return al;
	}

	/**
	 * Compares all incident names to all ohd names. This includes the names on
	 * bags. If the first name is not present in either place being compared,
	 * then it will not be included in the string that will be passed to the
	 * stringCompare method.
	 * 
	 * @param e
	 * @param incident
	 * @param ohd
	 * @return
	 */
	private static ArrayList<MatchResult> matchName(MatchElement e,
			Incident incident, OHD ohd) {

		ArrayList<MatchResult> results = new ArrayList<MatchResult>();
		ArrayList<Name> incNames = new ArrayList<Name>();
		ArrayList<Name> ohdNames = new ArrayList<Name>();

		// Get incident names
		for (Passenger pax : (Set<Passenger>) incident.getPassengers()) {
			incNames.add(new Name(pax.getFirstname(), pax.getMiddlename(), pax
					.getLastname()));
		}
		for (Item item : (List<Item>) incident.getItemlist()) {
			if (item != null) {
				incNames.add(new Name(item.getFnameonbag(), item.getMnameonbag(),
						item.getLnameonbag()));
			}
		}

		// Get ohd names
		for (OHD_Passenger pax : (Set<OHD_Passenger>) ohd.getPassengers()) {
			ohdNames.add(new Name(pax.getFirstname(), pax.getMiddlename(), pax
					.getLastname()));
		}
		ohdNames.add(new Name(ohd.getFirstname(), ohd.getMiddlename(), ohd
				.getLastname()));

		for (Name incName : incNames) {
			for (Name ohdName : ohdNames) {
				// This next line gets the comparable version of the respective
				// strings in respect to the string being compared to. Thus, we
				// only compare last name if one bag has a first and last name
				// and the other only has a last name.
				MatchResult result = MatchUtils.stringCompare(e, incName
						.getMatchString(ohdName), ohdName
						.getMatchString(incName));
				if (result != null) {
					results.add(result);
				}
			}
		}
		return results;
	}

	/**
	 * Compares all incident addresses to all ohd addresses. The compared
	 * address includes address1, city, state, province, and zip.
	 * 
	 * @param e
	 * @param incident
	 * @param ohd
	 * @return
	 */
	private static ArrayList<MatchResult> matchAddress(MatchElement e,
			Incident incident, OHD ohd) {

		ArrayList<MatchResult> results = new ArrayList<MatchResult>();

		for (Passenger incPax : (Set<Passenger>) incident.getPassengers()) {
			for (Address ia : (Set<Address>) incPax.getAddresses()) {
				for (OHD_Passenger ohdPax : (Set<OHD_Passenger>) ohd
						.getPassengers()) {
					for (OHD_Address oa : (Set<OHD_Address>) ohdPax
							.getAddresses()) {
						if (ia.getAddress1() != null && ia.getAddress2() != null && ia.getState_ID() != null && ia.getZip() != null) {
							String incString = StringUtils.join(" ", ia
									.getAddress1().trim(), ia.getAddress2().trim(), ia.getCity().trim(), ia
									.getState_ID(), ia.getProvince(), ia.getZip());
	
							String ohdString = StringUtils.join(" ", oa
									.getAddress1().trim(), oa.getAddress2().trim(), oa.getCity().trim(), oa
									.getState_ID(), oa.getProvince(), oa.getZip());
	
							MatchResult result = MatchUtils.stringCompare(e,
									incString, ohdString);
	
							if (result != null) {
								results.add(result);
							}
						}
					}
				}
			}
		}
		return results;
	}

	/**
	 * Compares both passenger and baggage routing itineraries in the incident
	 * to the itinerary in the ohd.
	 * 
	 * @param e
	 * @param incident
	 * @param ohd
	 * @return
	 */
	private static ArrayList<MatchResult> matchItinerary(MatchElement e,
			Incident incident, OHD ohd) {

		ArrayList<MatchResult> results = new ArrayList<MatchResult>();

		// Two approaches have been considered - picking the highest scoring
		// segment
		// and comparing overall itineraries against each other. They appear in
		// that order below, one should be commented out.

		// Pick the highest percent matching segment.
		for (Itinerary i : (Set<Itinerary>) incident.getItinerary()) {
			String incItinSeg = i.getLegfrom() + " " + i.getLegto() + " "
					+ i.getFlightnum();
			for (OHD_Itinerary it : (Set<OHD_Itinerary>) ohd.getItinerary()) {
				String ohdItinSeg = it.getLegfrom() + " " + it.getLegto() + " "
						+ it.getFlightnum();
				MatchResult result = MatchUtils.stringCompare(e, incItinSeg,
						ohdItinSeg);
				if (result != null) {
					if (i.getDepartdate()!= null && it.getDepartdate() != null && i.getDepartdate().equals(it.getDepartdate())) {
						result.setDateMatched(true);
					}
					result.setItineraryType(i.getItinerarytype());
					results.add(result);
				}
			}
		}

		/*
		 * // Build full itineraries and then compare them. StringBuffer
		 * incidentPaxItinerary = new StringBuffer(); StringBuffer
		 * incidentBagItinerary = new StringBuffer(); StringBuffer ohdItinerary =
		 * new StringBuffer();
		 * 
		 * for (Itinerary it : (Set<Itinerary>) incident.getItinerary()) { if
		 * (it.getItinerarytype() == TracingConstants.PASSENGER_ROUTING) {
		 * incidentPaxItinerary.append(it.getLegfrom() + " " + it.getLegto() + " " +
		 * it.getFlightnum() + " "); } else if (it.getItinerarytype() ==
		 * TracingConstants.BAGGAGE_ROUTING) {
		 * incidentBagItinerary.append(it.getLegfrom() + " " + it.getLegto() + " " +
		 * it.getFlightnum() + " "); } }
		 *  // Build the OHD itinerary for (OHD_Itinerary it : (Set<OHD_Itinerary>)
		 * ohd.getItinerary()) { ohdItinerary.append(it.getLegfrom() + " " +
		 * it.getLegto() + " " + it.getFlightnum() + " "); }
		 *  // Compare itineraries MatchResult result1 =
		 * MatchUtils.stringCompare(e, incidentPaxItinerary .toString(),
		 * ohdItinerary.toString());
		 * 
		 * if (result1 != null) {
		 * result1.setItineraryType(TracingConstants.PASSENGER_ROUTING);
		 * results.add(result1);
		 *  }
		 * 
		 * MatchResult result2 = MatchUtils.stringCompare(e,
		 * incidentBagItinerary .toString(), ohdItinerary.toString());
		 * 
		 * if (result2 != null) {
		 * result2.setItineraryType(TracingConstants.BAGGAGE_ROUTING);
		 * results.add(result2); }
		 */

		return results;
	}

	/**
	 * Matches all available email addresses in the incident against all
	 * available email addresses in the onhand.
	 * 
	 * @param e
	 * @param incident
	 * @param ohd
	 * @return
	 */
	private static ArrayList<MatchResult> matchEmail(MatchElement e,
			Incident incident, OHD ohd) {
		ArrayList<MatchResult> results = new ArrayList<MatchResult>();

		for (Passenger incPax : (Set<Passenger>) incident.getPassengers()) {
			for (Address ia : (Set<Address>) incPax.getAddresses()) {
				for (OHD_Passenger ohdPax : (Set<OHD_Passenger>) ohd
						.getPassengers()) {
					for (OHD_Address oa : (Set<OHD_Address>) ohdPax
							.getAddresses()) {
						MatchResult result = MatchUtils.stringCompare(e, ia
								.getEmail(), oa.getEmail());

						if (result != null) {
							results.add(result);
						}
					}
				}
			}
		}

		return results;
	}

	/**
	 * Matches all available incident phone numbers against all available ohd
	 * phone numbers.
	 * 
	 * @param e
	 * @param incident
	 * @param ohd
	 * @return
	 */
	private static ArrayList<MatchResult> matchPhone(MatchElement e,
			Incident incident, OHD ohd) {
		ArrayList<MatchResult> results = new ArrayList<MatchResult>();
		ArrayList<String> incPhones = new ArrayList<String>();
		ArrayList<String> ohdPhones = new ArrayList<String>();

		// Get all incident phone numbers
		for (Passenger incPax : (Set<Passenger>) incident.getPassengers()) {
			for (Address a : (Set<Address>) incPax.getAddresses()) {
				if (a.getHomephone() != null)
					incPhones.add(a.getHomephone());
				if (a.getWorkphone() != null)
					incPhones.add(a.getWorkphone());
				if (a.getMobile() != null)
					incPhones.add(a.getMobile());
				if (a.getPager() != null)
					incPhones.add(a.getPager());
				if (a.getAltphone() != null)
					incPhones.add(a.getAltphone());
			}
		}

		// Get all OHD phone numbers
		for (OHD_Passenger ohdPax : (Set<OHD_Passenger>) ohd.getPassengers()) {
			for (OHD_Address a : (Set<OHD_Address>) ohdPax.getAddresses()) {
				if (a.getHomephone() != null)
					ohdPhones.add(a.getHomephone());
				if (a.getWorkphone() != null)
					ohdPhones.add(a.getWorkphone());
				if (a.getMobile() != null)
					ohdPhones.add(a.getMobile());
				if (a.getPager() != null)
					ohdPhones.add(a.getPager());
				if (a.getAltphone() != null)
					ohdPhones.add(a.getAltphone());
			}
		}

		// Produce cross-product of comparisons
		String tmpIncPhone;
		String tmpOhdPhone;
		for (String incPhone : incPhones) {
			for (String ohdPhone : ohdPhones) {
				tmpIncPhone = StringUtils.removeNonNumeric(incPhone);
				tmpOhdPhone = StringUtils.removeNonNumeric(ohdPhone);

				MatchResult result = MatchUtils.stringCompare(e, tmpIncPhone,
						tmpOhdPhone);
				if (result != null) {
					results.add(result);
				}
			}
		}

		return results;
	}

	/**
	 * Matches all available claim checks in the incident against the bag tag
	 * number in the ohd.
	 * 
	 * @param e
	 * @param incident
	 * @param ohd
	 * @return
	 */
	private static ArrayList<MatchResult> matchClaimCheck(MatchElement e,
			Incident incident, OHD ohd) {
		ArrayList<MatchResult> results = new ArrayList<MatchResult>();

		if (ohd.getClaimnum() == null && ohd.getClaimnum().trim().length() > 0) {
			return results;
		}

		String ohdBagTag = ohd.getClaimnum();
		String ohdTenDigitTag = null;
		try {
			ohdTenDigitTag = LookupAirlineCodes.getFullBagTag(ohdBagTag);
		} catch (BagtagException e1) {
			ohdTenDigitTag = ohdBagTag;
		}

		for (Incident_Claimcheck iClaim : (Set<Incident_Claimcheck>) incident
				.getClaimchecks()) {
			if (iClaim.getClaimchecknum().trim().length() > 0) {
				String incTenDigitTag = null;
				try {
					incTenDigitTag = LookupAirlineCodes.getFullBagTag(iClaim
							.getClaimchecknum());
				} catch (BagtagException e1) {
					incTenDigitTag = iClaim.getClaimchecknum();
				}
	
				MatchResult result = MatchUtils.stringCompare(e, incTenDigitTag,
						ohdTenDigitTag);
	
				if (result != null) {
					results.add(result);
				}
			}
		}

		return results;
	}

	/**
	 * Matches manufacturers in the incident against manufacturers in the OHD.
	 * 
	 * @param e
	 * @param incident
	 * @param ohd
	 * @return
	 */
	private static ArrayList<MatchResult> matchManufacturer(MatchElement e,
			Incident incident, OHD ohd) {
		ArrayList<MatchResult> results = new ArrayList<MatchResult>();

		for (Item item : (List<Item>) incident.getItemlist()) {

			// We are going to use the manufacturer name and only add the result
			// if we have a 100% match.
			if (item != null) {

				MatchResult result = MatchUtils.stringCompare(e, item
						.getManufacturer(), ohd.getManufacturer(), item
						.getBagnumber());
				if (result != null && result.getPercentMatch() == 100) {
					results.add(result);
				}
			}
		}

		return results;
	}

	/**
	 * Matches colors in the incident against the colors in the ohd. Takes into
	 * account secondary and tertiary color types.
	 * 
	 * @param e
	 * @param incident
	 * @param ohd
	 * @return
	 */
	private static ArrayList<MatchResult> matchColor(MatchElement e,
			Incident incident, OHD ohd) {

		ArrayList<MatchResult> results = new ArrayList<MatchResult>();
		String ohdColor = ohd.getColor();
		
		if (ohdColor == null || ohdColor.trim().length() == 0) {
			return results;
		}

		for (Item item : (List<Item>) incident.getItemlist()) {
			if (item != null) {
				if (item.getColor() != null && item.getColor().trim().length() > 0) {
					if (item.getColor().equals(ohdColor)) {
						MatchResult result = new MatchResult(e, 100, item.getColor(),
								ohdColor);
						result.setBagNumber(item.getBagnumber());
						results.add(result);
					} else if (MatchUtils
							.secondaryColorMatch(item.getColor(), ohdColor)) {
						double secondaryColorPercent = 66;
						
						String typePercent = PropertyBMO.getValue(PropertyBMO.PROPERTY_TRACING_SECONDARY_COLOR_PERCENT);
						secondaryColorPercent = Double.parseDouble(typePercent);
						
						MatchResult result = new MatchResult(e, secondaryColorPercent,
								item.getColor(), ohdColor);
						result.setBagNumber(item.getBagnumber());
						results.add(result);
					} else if (MatchUtils.tertiaryColorMatch(item.getColor(), ohdColor)) {
						double tertiaryColorPercent = 33;
						
						String typePercent = PropertyBMO.getValue(PropertyBMO.PROPERTY_TRACING_TERTIARY_COLOR_PERCENT);
						tertiaryColorPercent = Double.parseDouble(typePercent);
						
						MatchResult result = new MatchResult(e, tertiaryColorPercent,
								item.getColor(), ohdColor);
						result.setBagNumber(item.getBagnumber());
						results.add(result);
					}
				}
			}
		}

		return results;
	}

	/**
	 * Matches bag types in the incident against bag types in the ohd. Also
	 * takes into account secondary and tertiary types.
	 * 
	 * @param e
	 * @param incident
	 * @param ohd
	 * @return
	 */
	private static ArrayList<MatchResult> matchType(MatchElement e,
			Incident incident, OHD ohd) {

		ArrayList<MatchResult> results = new ArrayList<MatchResult>();
		String ohdType = ohd.getType();
		
		if (ohdType == null || ohdType.trim().length() == 0) {
			return results;
		}

		for (Item item : (List<Item>) incident.getItemlist()) {
			if (item != null && item.getBagtype() != null && item.getBagtype().trim().length() > 0) {
				if (item.getBagtype().equals(ohdType)) {
	
					MatchResult result = new MatchResult(e, 100, item.getBagtype(),
							ohdType);
					result.setBagNumber(item.getBagnumber());
					results.add(result);
				} else if (MatchUtils
						.secondaryTypeMatch(item.getBagtype(), ohdType)) {
					double secondaryTypePercent = 66;
					
					String typePercent = PropertyBMO.getValue(PropertyBMO.PROPERTY_TRACING_SECONDARY_TYPE_PERCENT);
					secondaryTypePercent = Double.parseDouble(typePercent);
					
					MatchResult result = new MatchResult(e, secondaryTypePercent,
							item.getBagtype(), ohdType);
					result.setBagNumber(item.getBagnumber());
					results.add(result);
				} else if (MatchUtils.tertiaryTypeMatch(item.getBagtype(), ohdType)) {
					double tertiaryTypePercent = 33;
					
					String typePercent = PropertyBMO.getValue(PropertyBMO.PROPERTY_TRACING_TERTIARY_TYPE_PERCENT);
					tertiaryTypePercent = Double.parseDouble(typePercent);
					
					MatchResult result = new MatchResult(e, tertiaryTypePercent,
							item.getBagtype(), ohdType);
					result.setBagNumber(item.getBagnumber());
					results.add(result);
				}
			}
		}

		return results;
	}

	private static ArrayList<MatchResult> matchElement(MatchElement e,
			Incident incident, OHD ohd) {
		ArrayList<MatchResult> results = new ArrayList<MatchResult>();

		// Get list of ohd elements
		ArrayList<Integer> ohdElements = new ArrayList<Integer>();
		
		int traceX = TracingConstants.TRACING_ELEMENT_X;
		
		if (ohd.getXdescelement_ID_1() != traceX)
			ohdElements.add(new Integer(ohd.getXdescelement_ID_1()));
		if (ohd.getXdescelement_ID_2() != traceX)	
			ohdElements.add(new Integer(ohd.getXdescelement_ID_2()));
		if (ohd.getXdescelement_ID_3() != traceX)
			ohdElements.add(new Integer(ohd.getXdescelement_ID_3()));

		for (Item item : (List<Item>) incident.getItemlist()) {
			ArrayList<Integer> matched = new ArrayList<Integer>();
			
			if (item != null) {
				Integer int1 = new Integer(item.getXdescelement_ID_1());
				Integer int2 = new Integer(item.getXdescelement_ID_2());
				Integer int3 = new Integer(item.getXdescelement_ID_3());
	
				if (!int1.equals(new Integer(0)) && ohdElements.contains(int1) && !matched.contains(int1)) {
					String desc = TracerUtils.getXdescelement(int1)
							.getDescription();
					MatchResult mr = new MatchResult(e, 100, desc, desc);
					mr.setBagNumber(item.getBagnumber());
					results.add(mr);
					matched.add(int1);
				}
	
				if (!int2.equals(new Integer(0)) && ohdElements.contains(int2) && !matched.contains(int2)) {
					String desc = TracerUtils.getXdescelement(int2)
							.getDescription();
					MatchResult mr = new MatchResult(e, 100, desc, desc);
					mr.setBagNumber(item.getBagnumber());
					results.add(mr);
					matched.add(int2);
				}
	
				if (!int3.equals(new Integer(0)) && ohdElements.contains(int3) && !matched.contains(int3)) {
					String desc = TracerUtils.getXdescelement(int3)
							.getDescription();
					MatchResult mr = new MatchResult(e, 100, desc, desc);
					mr.setBagNumber(item.getBagnumber());
					results.add(mr);
					matched.add(int3);
				}
			}
		}

		return results;
	}

	private static ArrayList<MatchResult> matchContent(MatchElement e,
			Incident incident, OHD ohd) {

		ArrayList<MatchResult> results = new ArrayList<MatchResult>();

		for (Item item : (List<Item>) incident.getItemlist()) {
			if (item != null) {
				for (Item_Inventory i : (Set<Item_Inventory>) item.getInventory()) {
					for (OHD_Inventory oi : (Set<OHD_Inventory>) ohd.getItems()) {
						String incCatType = i.getCategory();
						String ohdCatType = oi.getCategory();
						String incContent = StringUtils.removePronouns(i
								.getDescription());
						String ohdContent = StringUtils.removePronouns(oi
								.getDescription());
	
						MatchResult result = MatchUtils.stringCompare(e,
								incContent, ohdContent, item.getBagnumber());
	
						if (result != null) {
							if (incCatType.equals(ohdCatType)) {
								result.setCategoryMatched(true);
								result.setContentCategory(i.getCategory());
							}
							results.add(result);
						}
					}
				}
			}
		}

		return results;
	}
}

class Name {
	private String firstName = null;
	private String middle = null;
	private String lastName = null;
	public final int NONE = 0;
	public final int ALL = 1;
	public final int FIRST_LAST = 2;
	public final int MIDDLE_LAST = 3;
	public final int LAST = 4;

	public Name(String firstName, String middle, String lastName) {
		if (firstName != null && firstName.trim().length() > 0) {
			this.firstName = firstName.trim();
		}
		if (lastName != null && lastName.trim().length() > 0) {
			this.lastName = lastName.trim();
		}
		if (middle != null && middle.trim().length() > 0) {
			this.middle = middle.trim();
		}
	}

	public String getMatchString(Name name) {

		if (this.getMatchType() == NONE || name.getMatchType() == NONE) {
			return null;
		} else if (this.getMatchType() == ALL && name.getMatchType() == ALL) {
			return firstName + " " + middle + " " + lastName;
		} else if (this.getMatchType() == FIRST_LAST
				&& name.getMatchType() == FIRST_LAST) {
			return firstName + " " + lastName;
		} else if (this.getMatchType() == MIDDLE_LAST
				&& name.getMatchType() == MIDDLE_LAST) {
			return middle + " " + lastName;
		} else {
			return lastName;
		}

	}

	public int getMatchType() {
		if (lastName == null)
			return NONE;
		else if (firstName != null && middle != null)
			return ALL;
		else if (firstName != null)
			return FIRST_LAST;
		else if (middle != null)
			return MIDDLE_LAST;
		else if (lastName != null)
			return LAST;
		return 0;
	}

}
