import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.hibernate.Session;
import org.hibernate.Transaction;

import aero.nettracer.fs.model.GreyListAddress;
import aero.nettracer.fs.utilities.GeoCode;
import aero.nettracer.fs.utilities.GeoLocation;
import aero.nettracer.fs.utilities.GeoParsedAddress;
import aero.nettracer.fs.utilities.InternationalException;
import aero.nettracer.serviceprovider.common.hibernate.HibernateWrapper;
import au.com.bytecode.opencsv.CSVReader;

public class ImportMailLocations {

	private static final Integer zero = new Integer(0);

	public static void main(String[] args) {

		// UPS STORES
		/*
		 * try { String fileName = "upsstore.csv"; CSVReader reader = new
		 * CSVReader(new FileReader(fileName));
		 * 
		 * String[] line = new String[0]; while (line != null) { line =
		 * reader.readNext(); int i = 0; if (line != null) {
		 * System.out.println(line[++i] + " " + line[++i] + " " + line[++i] +
		 * " " + line[++i]); }
		 * 
		 * } reader.close(); } catch (Exception e) { e.printStackTrace(); }
		 */

		// PAK MAIL
		
		try {
			String fileName = "pakmail.csv";
			String fileTypes = "Pak Mail";
			geoLocateAndInsertIntoGreylist(fileName, fileTypes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		// UPS STORE
		try {
			String fileName = "upsstore.csv";
			String fileTypes = "The UPS Store";
			geoLocateAndInsertIntoGreylist(fileName, fileTypes);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void geoLocateAndInsertIntoGreylist(String fileName, String fileTypes)
			throws FileNotFoundException, IOException, InternationalException {
		CSVReader reader = new CSVReader(new FileReader(fileName));

		String[] line = new String[0];
		while (line != null) {
			line = reader.readNext();
			int i = 0;
			if (line != null) {
				String address1 = line[++i];
				String city = line[++i];
				String state = line[++i];
				String zip = (line[++i].split("-"))[0];
				String province = null;
				String country = "US";
				String num = null;
				String name = null;
				
				GeoParsedAddress r = GeoCode.parse(address1, city, state, zip);
				try {
					num = r.getNum();
					name = r.getName();
					System.out.println("Num: " + r.getNum());
				System.out.println("Name: " + r.getName());
				System.out.println("Unit: " + r.getUnit());
				System.out.println("Prefix: " + r.getPrefix());
				System.out.println("Suffix: " + r.getSuffix());
				System.out.println("****************************");
				} catch (Exception e) {
					
				}
				GeoLocation loc = GeoCode.locate(address1, city, state,
						zip, province, country, null);
				
				if (loc != null) {
					GreyListAddress a = new GreyListAddress();
					a.setStreetName(name);
					a.setStreetNumber(num);
					a.setLatitude(loc.getLatitude());
					a.setLongitude(loc.getLongitude());
					a.setAddress(address1);
					a.setState(state);
					a.setCity(city);
					a.setZip(zip);
					a.setCountry(country);
					a.setGeoCodeType(loc.getType());
					a.setDescription(fileTypes);
					Session sess = HibernateWrapper.getSession().openSession();
					Transaction t = sess.beginTransaction();
					sess.save(a);
					t.commit();
					sess.close();
				}
			}

		}
		reader.close();
	}

}
