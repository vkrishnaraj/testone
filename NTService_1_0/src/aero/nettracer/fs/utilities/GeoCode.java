package aero.nettracer.fs.utilities;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;

public class GeoCode {

	public static final int ACCURACY_STREET = 1; // Street level
	public static final int ACCURACY_ZIP = 2; // Zip level
	public static final int ACCURACY_CITY = 3; // City level
	public static final int STREET_SEARCH_LEVEL_HIGH = 1; // Search with
	public static final int STREET_SEARCH_LEVEL_MED = 2; // Search with number
	public static final int STREET_SEARCH_LEVEL_LOW = 3; // Search with name
	public static final double MILES_PER_LATITUDE = 69;

	public static HashMap<String, String> fips_to_states;
	public static HashMap<String, String> states_to_fips;
	private static Logger logger = Logger.getLogger(GeoCode.class);

	// TODO: HANDLE INTERNATIONAL ADDRESSES
	public static GeoLocation locate(String address, String city, String state,
			String zip, String province, String country, Session sess)
			throws InternationalException {
		boolean nullSession = (sess == null);

		try {
			if (nullSession) {
				sess = HibernateWrapper.getGeoSession().openSession();
			}
			if (country == null || country.trim().length() == 0
					|| country.equalsIgnoreCase("US")) {

				GeoParsedAddress parsed = parse(address, city, state, zip);
				if (parsed != null) {
					return lookupLoc(parsed, city, state, zip, sess);
				}
			} else {
				throw new InternationalException();
			}
		} catch (InternationalException e) {
			throw e;
		} catch (Exception e) {

			logger.error("Unable to geocode address: " + e);
			e.printStackTrace();
			return null;
		} finally {
			if (sess != null && nullSession) {
				try {
					sess.close();
				} catch (Exception e) {
					logger.error("unable to close connection: " + e);
				}
			}
		}
		return null;
	}

	private static GeoLocation lookupLoc(GeoParsedAddress parsed, String city,
			String state, String zip, Session sess) {
		GeoLocation returnMe = null;
		boolean haveZip = (zip != null && !zip.trim().equals(""));
		if (haveZip) {
			if (state == null || state.trim().equals("") || state.length() != 2) {
				state = lookupState(zip, sess);
			}
			if (state != null) {
				returnMe = lookupStreet(parsed, zip, state, false,
						STREET_SEARCH_LEVEL_HIGH, sess);
			}
		}

		if (returnMe == null) { // No Loc for zip search, lets try county
			String county = lookupCounty(city, state, sess);
			if (county != null) {
				returnMe = lookupStreet(parsed, county, state, true,
						STREET_SEARCH_LEVEL_HIGH, sess);
			}
		}

		if (returnMe == null) { // Street name didn't pan out, its zip time
			if (haveZip && state != null) {
				returnMe = lookupZip(zip, state, sess);
			}
		}

		if (returnMe == null) { // No Loc for zip either, guess we try city.
			returnMe = lookupCity(city, state, sess);
		}

		return returnMe;
	}

	@SuppressWarnings("unchecked")
	private static String lookupState(String zip, Session sess) {
		String returnMe = null;
		SQLQuery query = sess.createSQLQuery("SELECT statefp "
				+ "FROM geotest_zip_to_state WHERE " + "zip = '" + zip + "'");
		List<Object> list = query.list();
		if (list != null && list.size() > 0) {
			for (int i = 0, j = list.size(); i < j; i++) {
				returnMe = list.get(i) + "";
			}
			loadFips();
			return fips_to_states.get(returnMe);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	private static String lookupCounty(String city, String state, Session sess) {
		String returnMe = null;
		loadStates();
		SQLQuery query = sess.createSQLQuery("SELECT countyfp10 "
				+ "FROM coutest WHERE name10 = '" + city
				+ "' AND statefp10 = '"
				+ states_to_fips.get(state.toUpperCase()) + "'");
		List<Object> list = query.list();
		if (list != null && list.size() > 0) {
			returnMe = "('";
			for (int i = 0, j = list.size(); i < j; i++) {
				if (i != 0) {
					returnMe += ", '";
				}
				returnMe += list.get(i) + "'";
			}
			return returnMe + ")";
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	private static GeoLocation lookupStreet(GeoParsedAddress parsed,
			String zip, String state, boolean useFIPs, int searchLevel,
			Session sess) {
		GeoLocation returnMe = null;
		try {
			boolean usePrefix = (searchLevel == STREET_SEARCH_LEVEL_HIGH);
			boolean useNumber = (searchLevel <= STREET_SEARCH_LEVEL_MED);
			boolean prefixExists = false;
			boolean suffixExists = false;

			String address = null;

			if (parsed.getType() != null) {
				address = parsed.getName() + " " + parsed.getType();
			} else {
				address = parsed.getName();
			}

			if (usePrefix) {
				if (parsed.getPrefix() != null
						&& !parsed.getPrefix().trim().equals("")) {
					prefixExists = true;
					address = parsed.getPrefix() + " " + address;
				}
				if (parsed.getSuffix() != null
						&& !parsed.getSuffix().trim().equals("")) {
					suffixExists = true;
					address = address + " " + parsed.getSuffix();
				}
			}

			if (address == null) {
				return null;
			}

			SQLQuery query = sess.createSQLQuery("select longitude, latitude "
					+ "from geotest_"
					+ state.toLowerCase()
					+ "_min where fullname like '%"
					+ address
					+ "%' and "
					+ (useFIPs ? "countyfp in " + zip + " " : "(zipl = '" + zip
							+ "' or zipr = '" + zip + "') ")
					+ (useNumber ? "and (((rfromadd <= " + parsed.getNum()
							+ " and rtoadd >= " + parsed.getNum()
							+ ") or (lfromadd <= " + parsed.getNum()
							+ " and ltoadd >= " + parsed.getNum()
							+ ")) or ((rtoadd <= " + parsed.getNum()
							+ " and rfromadd >= " + parsed.getNum()
							+ ") or (ltoadd <= " + parsed.getNum()
							+ " and lfromadd >= " + parsed.getNum() + ")))"
							: ""));
			query.addScalar("longitude", Hibernate.DOUBLE);
			query.addScalar("latitude", Hibernate.DOUBLE);
			List<Object[]> list = query.list();
			if (list != null && list.size() > 0) {
				for (int i = 0, j = list.size(); i < j; i++) {
					Object[] theResult = list.get(i);
					returnMe = new GeoLocation(
							((Double) theResult[0]).doubleValue(),
							((Double) theResult[1]).doubleValue(),
							ACCURACY_STREET);
				}
				return returnMe;
			} else {
				if (usePrefix && (prefixExists || suffixExists)) {
					return lookupStreet(parsed, zip, state, useFIPs,
							STREET_SEARCH_LEVEL_MED, sess);
				} else {
					if (useNumber) {
						return lookupStreet(parsed, zip, state, useFIPs,
								STREET_SEARCH_LEVEL_LOW, sess);
					} else {
						return null;
					}
				}
			}
		} catch (Exception ex) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	private static GeoLocation lookupZip(String zip, String state, Session sess) {
		GeoLocation returnMe = null;
		try {
			SQLQuery query = sess
					.createSQLQuery("select (min(longitude) + max(longitude)) / 2 as longitude, "
							+ "(min(latitude) + max(latitude)) /2 as latitude from "
							+ "geotest_"
							+ state.toLowerCase()
							+ "_min where "
							+ "zipl = " + zip + " or zipr = " + zip);
			query.addScalar("longitude", Hibernate.DOUBLE);
			query.addScalar("latitude", Hibernate.DOUBLE);
			List<Object[]> list = query.list();
			if (list != null && list.size() > 0) {
				for (int i = 0, j = list.size(); i < j; i++) {
					Object[] theResult = list.get(i);
					returnMe = new GeoLocation(
							((Double) theResult[0]).doubleValue(),
							((Double) theResult[1]).doubleValue(), ACCURACY_ZIP);
				}
				return returnMe;
			} else {
				return null;
			}
		} catch (Exception ex) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	private static GeoLocation lookupCity(String city, String state,
			Session sess) {
		GeoLocation returnMe = null;
		loadStates();
		SQLQuery query = sess
				.createSQLQuery("SELECT (min(intptlon10) + max(intptlon10)) / 2 as longitude, "
						+ "(min(intptlat10) + max(intptlat10)) /2 as latitude "
						+ "FROM coutest WHERE name10 = '"
						+ city
						+ "' AND statefp10 = '"
						+ states_to_fips.get(state.toUpperCase()) + "'");
		List<Object[]> list = query.list();
		if (list != null && list.size() > 0) {
			for (int i = 0, j = list.size(); i < j; i++) {
				Object[] theResult = list.get(i);
				returnMe = new GeoLocation(
						Double.parseDouble((String) theResult[0]),
						Double.parseDouble((String) theResult[1]),
						ACCURACY_CITY);
			}
			return returnMe;
		} else {
			return null;
		}
	}

	private static GeoParsedAddress parse(String address, String city,
			String state, String zip) {
		if ((city == null || state == null) && zip == null) {
			return null;
		}
		GeoParsedAddress toReturn = null;
		try {
			String line;
			String fullAdd = address + ",";
			if (city != null && state != null) {
				fullAdd += " " + city + ", " + state;
			}
			if (zip != null) {
				fullAdd += " " + zip;
			}

			fullAdd = fullAdd.replaceAll("['\\*\\-\\/]+", " ");

			String totalAddress = "perl C:\\geo_perl\\getLoc.pl \"" + fullAdd
					+ "\"";

			Process p = Runtime.getRuntime().exec(totalAddress);

			BufferedReader input = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			while ((line = input.readLine()) != null) {
				// System.out.println("SYSTEM: " + line);
				if (toReturn == null) {
					toReturn = new GeoParsedAddress();
				}
				toReturn = parseLine(line, toReturn);
			}
			input.close();
		} catch (Exception err) {
			err.printStackTrace();
		}
		// System.out.println("DONE!!!");
		return toReturn;
	}

	private static GeoParsedAddress parseLine(String line,
			GeoParsedAddress parsed) {
		if (line.startsWith("NUM:")) {
			parsed.setNum(line.substring(4));
		} else if (line.startsWith("PRE:")) {
			parsed.setPrefix(line.substring(4));
		} else if (line.startsWith("STR:")) {
			parsed.setName(line.substring(4));
		} else if (line.startsWith("TYP:")) {
			parsed.setType(line.substring(4));
		} else if (line.startsWith("SUF:")) {
			parsed.setSuffix(line.substring(4));
		}
		return parsed;
	}

	public static List<Object> findAddresses() {
		return null;
	}

	public static double distanceBetweenPoints(GeoLocation point1,
			GeoLocation point2) {
		double lat1 = point1.getLatitude();
		double lon1 = point1.getLongitude();
		double lat2 = point2.getLatitude();
		double lon2 = point2.getLongitude();

		return distanceBetweenPoints(lat1, lon1, lat2, lon2);
	}

	public static double distanceBetweenPoints(double lat1, double lon1,
			double lat2, double lon2) {
		int radiusEarth = 3959; // mi 6371; // km
		double dLat = Math.toRadians(lat2 - lat1);
		double dLon = Math.toRadians(lon2 - lon1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
				+ Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
				* Math.sin(dLon / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double d = radiusEarth * c;
		return d;
	}

	public static double getLongRadius(double latitude, double distance) {
		int radiusEarth = 3959; // mi 6371; // km
		double drRadians = (distance / radiusEarth);
		double brng = Math.toRadians(90D);
		double lat1 = Math.toRadians(latitude);
		double lat2 = Math.asin(Math.sin(lat1) * Math.cos(drRadians)
				+ Math.cos(lat1) * Math.sin(drRadians) * Math.cos(brng));
		double lon2 = Math.atan2(
				Math.sin(brng) * Math.sin(drRadians) * Math.cos(lat1),
				Math.cos(drRadians) - Math.sin(lat1) * Math.sin(lat2));
		return Math.toDegrees(lon2);
	}

	public static double getLatRadius(double distance) {
		return distance / MILES_PER_LATITUDE;
	}

	private static void loadStates() {
		if (states_to_fips == null) {
			states_to_fips = new HashMap<String, String>();
			states_to_fips.put("AK", "02");
			states_to_fips.put("AL", "01");
			states_to_fips.put("AR", "05");
			states_to_fips.put("AZ", "04");
			states_to_fips.put("CA", "06");
			states_to_fips.put("CO", "08");
			states_to_fips.put("CT", "09");
			states_to_fips.put("DC", "11");
			states_to_fips.put("DE", "10");
			states_to_fips.put("FL", "12");
			states_to_fips.put("GA", "13");
			states_to_fips.put("HI", "15");
			states_to_fips.put("IA", "19");
			states_to_fips.put("ID", "16");
			states_to_fips.put("IL", "17");
			states_to_fips.put("IN", "18");
			states_to_fips.put("KS", "20");
			states_to_fips.put("KY", "21");
			states_to_fips.put("LA", "22");
			states_to_fips.put("MA", "25");
			states_to_fips.put("MD", "24");
			states_to_fips.put("ME", "23");
			states_to_fips.put("MI", "26");
			states_to_fips.put("MN", "27");
			states_to_fips.put("MO", "29");
			states_to_fips.put("MS", "28");
			states_to_fips.put("MT", "30");
			states_to_fips.put("NC", "37");
			states_to_fips.put("ND", "38");
			states_to_fips.put("NE", "31");
			states_to_fips.put("NH", "33");
			states_to_fips.put("NJ", "34");
			states_to_fips.put("NM", "35");
			states_to_fips.put("NV", "32");
			states_to_fips.put("NY", "36");
			states_to_fips.put("OH", "39");
			states_to_fips.put("OK", "40");
			states_to_fips.put("OR", "41");
			states_to_fips.put("PA", "42");
			states_to_fips.put("PR", "72");
			states_to_fips.put("RI", "44");
			states_to_fips.put("SC", "45");
			states_to_fips.put("SD", "46");
			states_to_fips.put("TN", "47");
			states_to_fips.put("TX", "48");
			states_to_fips.put("UT", "49");
			states_to_fips.put("VA", "51");
			states_to_fips.put("VT", "50");
			states_to_fips.put("WA", "53");
			states_to_fips.put("WI", "55");
			states_to_fips.put("WV", "54");
			states_to_fips.put("WY", "56");
		}
	}

	private static void loadFips() {
		if (fips_to_states == null) {
			fips_to_states = new HashMap<String, String>();
			fips_to_states.put("02", "AK");
			fips_to_states.put("01", "AL");
			fips_to_states.put("05", "AR");
			fips_to_states.put("04", "AZ");
			fips_to_states.put("06", "CA");
			fips_to_states.put("08", "CO");
			fips_to_states.put("09", "CT");
			fips_to_states.put("11", "DC");
			fips_to_states.put("10", "DE");
			fips_to_states.put("12", "FL");
			fips_to_states.put("13", "GA");
			fips_to_states.put("15", "HI");
			fips_to_states.put("19", "IA");
			fips_to_states.put("16", "ID");
			fips_to_states.put("17", "IL");
			fips_to_states.put("18", "IN");
			fips_to_states.put("20", "KS");
			fips_to_states.put("21", "KY");
			fips_to_states.put("22", "LA");
			fips_to_states.put("25", "MA");
			fips_to_states.put("24", "MD");
			fips_to_states.put("23", "ME");
			fips_to_states.put("26", "MI");
			fips_to_states.put("27", "MN");
			fips_to_states.put("29", "MO");
			fips_to_states.put("28", "MS");
			fips_to_states.put("30", "MT");
			fips_to_states.put("37", "NC");
			fips_to_states.put("38", "ND");
			fips_to_states.put("31", "NE");
			fips_to_states.put("33", "NH");
			fips_to_states.put("34", "NJ");
			fips_to_states.put("35", "NM");
			fips_to_states.put("32", "NV");
			fips_to_states.put("36", "NY");
			fips_to_states.put("39", "OH");
			fips_to_states.put("40", "OK");
			fips_to_states.put("41", "OR");
			fips_to_states.put("42", "PA");
			fips_to_states.put("72", "PR");
			fips_to_states.put("44", "RI");
			fips_to_states.put("45", "SC");
			fips_to_states.put("46", "SD");
			fips_to_states.put("47", "TN");
			fips_to_states.put("48", "TX");
			fips_to_states.put("49", "UT");
			fips_to_states.put("51", "VA");
			fips_to_states.put("50", "VT");
			fips_to_states.put("53", "WA");
			fips_to_states.put("55", "WI");
			fips_to_states.put("54", "WV");
			fips_to_states.put("56", "WY");
		}
	}

}
