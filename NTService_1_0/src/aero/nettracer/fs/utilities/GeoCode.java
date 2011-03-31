package aero.nettracer.fs.utilities;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

public class GeoCode {
	
	public static final double MILES_PER_LATITUDE = 69;

	public static GeoLocation locate(String address, String city, String state,
			String zip, String province, String country, Session sess) {
		if (country.equalsIgnoreCase("US")) {
			GeoParsedAddress parsed = parse(address, city, state, zip);
			if (parsed != null) {
				GeoLocation toReturn = null;
				if (zip != null && !zip.trim().equals("")) {
					toReturn = lookupLoc(parsed, zip, state, false, true, sess);
				} else {
					String county = lookupCounty(city, state, sess);
					if (county != null) {
						toReturn = lookupLoc(parsed, county, state, true, true, sess);
					}
				}
				return toReturn;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private static String lookupCounty(String city, String state, Session sess) {
		String returnMe = null;
		SQLQuery query = sess.createSQLQuery("SELECT countyfp10 "
				+ "FROM coutest_" + state.toLowerCase() + " WHERE "
				+ "name10 = '" + city + "'");
		List<Object> list = query.list();
		if (list != null && list.size() > 0) {
			System.out.println("NOT AT ALL EMPTY!!! " + list.size());
			for (int i = 0, j = list.size(); i < j; i++) {
				returnMe = list.get(i) + "";
				System.out.println("COUNTY RESULTS: " + list.get(i) + "\n");
			}
			return returnMe;
		} else {
			System.out.println("EMPTY!!!");
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	private static GeoLocation lookupLoc(GeoParsedAddress parsed, String zip,
			String state, boolean useFIPs, boolean usePrefix, Session sess) {
		GeoLocation returnMe = null;
		String address = parsed.getName() + " " + parsed.getType();
		if (usePrefix) {
			if (parsed.getPrefix() != null
					&& !parsed.getPrefix().trim().equals("")) {
				address = parsed.getPrefix() + " " + address;
			}
			if (parsed.getSuffix() != null
					&& !parsed.getSuffix().trim().equals("")) {
				address = address + " " + parsed.getSuffix();
			}
		}
		SQLQuery query = sess.createSQLQuery("select longitude, latitude "
				+ "from geotest_"
				+ state.toLowerCase()
				+ "_min where fullname like '%"
				+ address
				+ "%' and "
				+ (useFIPs ? "countyfp = '" + zip + "' " : "(zipl = " + zip
						+ " or zipr = " + zip + ") ")
				+ "and (((rfromadd <= "
				+ parsed.getNum()
				+ " and rtoadd >= "
				+ parsed.getNum()
				+ ") or (lfromadd <= "
				+ parsed.getNum()
				+ " and ltoadd >= "
				+ parsed.getNum()
				+ ")) or ((rtoadd <= "
				+ parsed.getNum()
				+ " and rfromadd >= "
				+ parsed.getNum()
				+ ") or (ltoadd <= "
				+ parsed.getNum()
				+ " and lfromadd >= "
				+ parsed.getNum()
				+ ")))");
		List<Object> list = query.list();
		if (list != null && list.size() > 0) {
			System.out.println("NOT AT ALL EMPTY!!! " + list.size());
			for (int i = 0, j = list.size(); i < j; i++) {
				Object[] theResult = (Object[]) list.get(i);
				returnMe = new GeoLocation(theResult[0] + "", theResult[1] + "");
				System.out.println("RESULTS: " + theResult[0] + " :: "
						+ theResult[1] + "\n");
			}
			return returnMe;
		} else {
			System.out.println("EMPTY!!!");
			if (usePrefix) {
				return lookupLoc(parsed, zip, state, useFIPs, false, sess);
			} else {
				return null;
			}
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
			Process p = Runtime.getRuntime().exec(
					"perl C:\\geo_perl\\getLoc.pl \"" + fullAdd + "\"");
			BufferedReader input = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			while ((line = input.readLine()) != null) {
				System.out.println("SYSTEM: " + line);
				if (toReturn == null) {
					toReturn = new GeoParsedAddress();
				}
				toReturn = parseLine(line, toReturn);
			}
			input.close();
		} catch (Exception err) {
			err.printStackTrace();
		}
		System.out.println("DONE!!!");
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
	
	public static double distanceBetweenPoints(GeoLocation point1, GeoLocation point2) {
		double lat1 = Double.parseDouble(point1.getLatitude());
		double lon1 = Double.parseDouble(point1.getLongitude());
		double lat2 = Double.parseDouble(point2.getLatitude());
		double lon2 = Double.parseDouble(point2.getLongitude());
		
		return distanceBetweenPoints(lat1, lon1, lat2, lon2);
	}
	
	public static double distanceBetweenPoints(double lat1, double lon1, double lat2, double lon2) {
		int radiusEarth = 3959; // mi  6371; // km
		double dLat = Math.toRadians(lat2-lat1);
		double dLon = Math.toRadians(lon2-lon1); 
		double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
	        Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * 
	        Math.sin(dLon/2) * Math.sin(dLon/2); 
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
		double d = radiusEarth * c;
		return d;
	}

	public static double getLongRadius(double latitude, double distance, int test) {
		int radiusEarth = 3959; // mi  6371; // km
		double drRadians = (distance/radiusEarth);
		double brng = Math.toRadians(90D);
		double lat1 = Math.toRadians(latitude);
		double lat2 = Math.asin(Math.sin(lat1) * Math.cos(drRadians) + Math.cos(lat1) * Math.sin(drRadians) * Math.cos(brng));
		double lon2 = Math.atan2(Math.sin(brng) * Math.sin(drRadians) * Math.cos(lat1), Math.cos(drRadians) - Math.sin(lat1) * Math.sin(lat2));
		return Math.toDegrees(lon2);
	}
	
	public static double getLatRadius(double distance) {
		return distance/MILES_PER_LATITUDE;
	}
	
}
