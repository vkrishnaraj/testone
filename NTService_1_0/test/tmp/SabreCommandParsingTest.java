package tmp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.Assert;

import aero.nettracer.serviceprovider.ws_1_0.res.sabre.Reservation;
import aero.nettracer.serviceprovider.ws_1_0.res.sabre.dto.SabreParseDTO;

public class SabreCommandParsingTest {
//	@org.junit.Test
//	public void test() {
//		
//		String s = "2012-12-21T11:00:00";
//		SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//		Date d = null;
//		try {
//			d = inputFormat.parse(s);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		
//		System.out.println("Date: " + d);
//		
//		
//	}
	
	@org.junit.Test
	public void test1() {
		
		StringBuilder sb = new StringBuilder();
		String newline = "\n";
		sb.append("BAGGAGE INFORMATION                                         " + newline);
		sb.append("ROUTING-B6   130 BUF                                        " + newline);
		sb.append(" CONNOR/JOHN                                                " + newline);
		sb.append("  BUF  B6 000194 - BY JFK5SG1 1245/03SEP09                  " + newline);
		sb.append("  BUF  B6 000195 - BY JFK5SG1 1245/03SEP09                  " + newline);
		sb.append(" CONNOR/SARAH                                               " + newline);
		sb.append("  BUF  B6 000196 - BY JFK5SG1 1247/03SEP09                  " + newline);
		sb.append("JFK.HDQ5SG1 1039/03SEP09 HHVJOP H" + newline);
		String s = sb.toString();

		SabreParseDTO parsedData = Reservation.parseForBaggageInfo(s);
		Assert.assertTrue(parsedData.getBags().size() == 3);
		Assert.assertTrue(parsedData.getItin().size() == 1);
	}
	
	@org.junit.Test
	public void test2() {
		
		StringBuilder sb = new StringBuilder();
		String newline = "\n";
		sb.append("BAGGAGE INFORMATION                                         " + newline);
		sb.append("ROUTING-B6   130 BUF                                        " + newline);
		sb.append(" CONNOR/JOHN                                                " + newline);
		sb.append("  BUF  B6 000194 - BY JFK5SG1 1245/03SEP09                  " + newline);
		sb.append("  BUF  B6 000195 - BY JFK5SG1 1245/03SEP09                  " + newline);
		sb.append(" CONNOR/SARAH                                               " + newline);
		sb.append("  BUF  B6 000196 - BY JFK5SG1 1247/03SEP09                  " + newline);
		sb.append("JFK.HDQ5SG1 1039/03SEP09 HHVJOP H" + newline);
		String s = sb.toString();

		SabreParseDTO parsedData = Reservation.parseForBaggageInfo("");
		Assert.assertTrue(parsedData.getBags().size() == 0);
		Assert.assertTrue(parsedData.getItin().size() == 0);
	}
	
	@org.junit.Test
	public void test3() {
		
		StringBuilder sb = new StringBuilder();
		String newline = "\n";
		sb.append("BAGGAGE INFORMATION                                         " + newline);
		sb.append("ROUTING-B6   130 BUF                                        " + newline);
		sb.append(" CONNOR/JOHN                                                " + newline);
		sb.append(" CONNOR/SARAH                                               " + newline);
		sb.append("JFK.HDQ5SG1 1039/03SEP09 HHVJOP H" + newline);
		String s = sb.toString();

		SabreParseDTO parsedData = Reservation.parseForBaggageInfo(s);
		Assert.assertTrue(parsedData.getBags().size() == 0);
		Assert.assertTrue(parsedData.getItin().size() == 0);
	}
}
