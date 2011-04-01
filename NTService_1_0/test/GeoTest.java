import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.junit.Test;

import aero.nettracer.fs.utilities.GeoCode;
import aero.nettracer.fs.utilities.GeoLocation;

public class GeoTest {

	@Test
	public void testOne() {
		Session sess = null;
		try {
			sess = FraudWrapper.getGeoSession().openSession();
			String address = "1402 Mechanic Street North";
			String zip = "36703";
			String results1, results2, results3, results4;
			results1 = results2 = results3 = results4 = "";
			Date date = new Date();
			GeoLocation loc = GeoCode.locate(address, "Selma", "AL", null, "", "US", sess);
			Date date2 = new Date();
			if (loc != null) {
				results1 = "TEST INFO: 1402 Mechanic St North 36703\n" + loc.getLongitude() + " :: " + loc.getLatitude();
			}
			
			String address2 = "4001 Southbrook Court";
			String zip2 = "30152";
			loc = GeoCode.locate(address2, "Kennesaw", "GA", zip2, "", "US", sess);
			if (loc != null) {
				results2 = "TEST INFO: 4001 Southbrook Court 30152\n" + loc.getLongitude() + " :: " + loc.getLatitude();
			}
			
			String address3 = "401 Gilmore Street";
			String zip3 = "35758";
			loc = GeoCode.locate(address3, "Madison", "AL", zip3, "", "US", sess);
			if (loc != null) {
				results3 = "TEST INFO: 401 Gilmore St 35758\n" + loc.getLongitude() + " :: " + loc.getLatitude();
			}
			
			String address4 = "1115 Woodmere Creek Trail";
			String zip4 = "35226";
			loc = GeoCode.locate(address4, null, null, zip4, "", "US", sess);
			if (loc != null) {
				results4 = "TEST INFO: 1115 Woodmere Creek Trail 35226\n" + loc.getLongitude() + " :: " + loc.getLatitude();
			}

			System.out.println("TESTING DB FETCHING...\n\n");
			System.out.println(results1 + "\n" + results2 + "\n" + results3 + "\n" + results4);
			System.out.println(date2.getTime() - date.getTime());
			System.out.println("TEST GET DISTANCE0: 0LAT 70MILES :: " + GeoCode.getLongRadius(0D, 70D, 0));
			System.out.println("TEST GET DISTANCE1: 10LAT 70MILES :: " + GeoCode.getLongRadius(10D, 70D, 1));
			System.out.println("TEST GET DISTANCE2: 40LAT 70MILES :: " + GeoCode.getLongRadius(40D, 70D, 2));
			System.out.println("TEST GET DISTANCE3: 80LAT 70MILES :: " + GeoCode.getLongRadius(80D, 70D, 3));
			System.out.println("TEST GET DISTANCE4: -10LAT 70MILES :: " + GeoCode.getLongRadius(-10D, 70D, 4));
			System.out.println("TEST GET DISTANCE5: -30LAT 70MILES :: " + GeoCode.getLongRadius(-30D, 70D, 5));
			System.out.println("TEST GET DISTANCE6: -50LAT 70MILES :: " + GeoCode.getLongRadius(-50D, 70D, 6));
			System.out.println("TEST GET DISTANCE7: -90LAT 70MILES :: " + GeoCode.getLongRadius(-90D, 70D, 7));
		} finally {
			if (sess != null) {
				sess.close();
			}
		}
		
	}

	@SuppressWarnings("unchecked")
	private String geocode(String address, String num, String zip) {
		String returnMe = "";
		Session sess = null;
		try {
			sess = FraudWrapper.getGeoSession().openSession();
			SQLQuery query = sess
					.createSQLQuery("select longitude, latitude "
							+ "from geotest_al_min where fullname like '"
							+ address
							+ "' and (zipl = "
							+ zip
							+ " or zipr = "
							+ zip
							+ ") "
							+ "and (((rfromadd <= "
							+ num
							+ " and rtoadd >= "
							+ num
							+ ") or (lfromadd <= "
							+ num + " and ltoadd >= " 
							+ num + ")) or ((rtoadd <= "
							+ num
							+ " and rfromadd >= "
							+ num
							+ ") or (ltoadd <= "
							+ num + " and lfromadd >= " 
							+ num + ")))");
			List<Object> list = query.list();
			if (list != null && list.size() > 0) {
				System.out.println("NOT AT ALL EMPTY!!! " + list.size());
				for (int i = 0, j = list.size(); i < j; i++) {
					Object[] theResult = (Object[]) list.get(i);
					returnMe += "RESULTS: " + theResult[0] + " :: " + theResult[1] + "\n";
					System.out.println("RESULTS: " + theResult[0] + " :: " + theResult[1] + "\n");
				}
				return returnMe;
			} else {
				System.out.println("EMPTY!!!");
				return "RESULTS: NONE!\n";
			}

		} finally {
			if (sess != null) {
				sess.close();
			}
		}
	}

}
