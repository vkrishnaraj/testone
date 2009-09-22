package aero.nettracer.serviceprovider.ws_1_0;

import junit.framework.Assert;
import aero.nettracer.serviceprovider.ws_1_0.res.sabre.Reservation;
import aero.nettracer.serviceprovider.ws_1_0.res.sabre.dto.SabreParseDTO;

public class SabreCommandParsingTest {

	@org.junit.Test
	public void test1() {

		StringBuilder sb = new StringBuilder();
		String newline = "\n";
		sb
				.append("BAGGAGE INFORMATION                                         "
						+ newline);
		sb
				.append("ROUTING-B6   130 BUF                                        "
						+ newline);
		sb
				.append(" CONNOR/JOHN                                                "
						+ newline);
		sb
				.append("  BUF  B6 000194 - BY JFK5SG1 1245/03SEP09                  "
						+ newline);
		sb
				.append("  BUF  B6 000195 - BY JFK5SG1 1245/03SEP09                  "
						+ newline);
		sb
				.append(" CONNOR/SARAH                                               "
						+ newline);
		sb
				.append("  BUF  B6 000196 - BY JFK5SG1 1247/03SEP09                  "
						+ newline);
		sb.append("JFK.HDQ5SG1 1039/03SEP09 HHVJOP H" + newline);
		String s = sb.toString();

		SabreParseDTO parsedData = Reservation.parseForBaggageInfo(s);
		Assert.assertTrue(parsedData.getBags().size() == 3);
		Assert.assertTrue(parsedData.getItin().size() == 1);
	}

	@org.junit.Test
	public void test2() {

		SabreParseDTO parsedData = Reservation.parseForBaggageInfo("");
		Assert.assertTrue(parsedData.getBags().size() == 0);
		Assert.assertTrue(parsedData.getItin().size() == 0);
	}

	@org.junit.Test
	public void test3() {

		StringBuilder sb = new StringBuilder();
		String newline = "\n";
		sb
				.append("BAGGAGE INFORMATION                                         "
						+ newline);
		sb
				.append("ROUTING-B6   130 BUF                                        "
						+ newline);
		sb
				.append(" CONNOR/JOHN                                                "
						+ newline);
		sb
				.append(" CONNOR/SARAH                                               "
						+ newline);
		sb.append("JFK.HDQ5SG1 1039/03SEP09 HHVJOP H" + newline);
		String s = sb.toString();

		SabreParseDTO parsedData = Reservation.parseForBaggageInfo(s);
		Assert.assertTrue(parsedData.getBags().size() == 0);
		Assert.assertTrue(parsedData.getItin().size() == 0);
	}

	@org.junit.Test
	public void test4() {

		StringBuilder sb = new StringBuilder();
		String newline = "\n";
		sb.append("LCTOFR" + newline);
		sb.append(" 1.1JOHNSON/RICHARD" + newline);
		sb.append(" 1 B61010K 03SEP Q JFKBOS HK1  1400  1523  /E" + newline);
		sb.append(" 2 B6 489K 03SEP Q BOSLGB HK1  1756  2105  /E" + newline);
		sb.append(" 3 B6 482K 05SEP J LGBBOS HK1  0800  1634  /E" + newline);
		sb.append(" 4 B61017K 05SEP J BOSJFK HK1  1720  1845  /E" + newline);
		sb.append("TKT/TIME LIMIT" + newline);
		sb.append("  1.T-03SEP-JFK5SG1" + newline);
		sb.append("  2.TE 2792140002539 JOHNS/R JFK5SG1 1340/03SEP" + newline);
		sb.append("  3.TK 2790610000197 JOHNS/R JFK5SG1 1344/03SEP - AS"
				+ newline);
		sb.append("VCR COUPON DATA EXISTS  *VI TO DISPLAY" + newline);
		sb.append("PHONES" + newline);
		sb.append("  1.JFK5512369856-C" + newline);
		sb.append("  2.JFK2012562535-H" + newline);
		sb.append("PASSENGER EMAIL DATA EXISTS  *PE TO DISPLAY ALL" + newline);
		sb.append("ADDRESS" + newline);
		sb.append("    N/RICHARD" + newline);
		sb.append("    N/JOHNSON" + newline);
		sb.append("    A/115 BROADWAY" + newline);
		sb.append("    C/NEW YORK, NY" + newline);
		sb.append("    Z/10019" + newline);
		sb.append("PRICE QUOTE RECORD EXISTS - *PQS" + newline);
		sb.append("B6 FACTS" + newline);
		sb.append("  1.SSR DOCS B6 HK1/DB/15SEP1978/M/JOHNSON/RICHARD"
				+ newline);
		sb
				.append("  2.SSR APAY B6 HK1 JFKBOS1010K03SEP/SEAT/ACCPT/USD4000 CA 2790"
						+ newline);
		sb.append("    610000197  " + newline);
		sb.append("REMARKS" + newline);
		sb.append("  1.H-THIS IS A CONNECTING FLIGHT" + newline);
		sb.append("  2.H-THIS IS ALSO A TWO SEGMENT FLIGHT" + newline);
		sb.append("  3.H-01 ANCILLARY SEAT FEE   USD   40.00            "
				+ newline);
		sb.append("  4.H-   2792140002539" + newline);
		sb
				.append("BAGGAGE INFORMATION                                         "
						+ newline);
		sb
				.append("ROUTING-B6  1010 BOS  /B6   489 LGB                         "
						+ newline);
		sb
				.append(" JOHNSON/RICHARD                                            "
						+ newline);
		sb
				.append("  LGB  B6 200266 - BY JFK5SG1 1343/03SEP09                  "
						+ newline);
		sb
				.append("       BAG STATUS VOL SEP                                   "
						+ newline);
		sb
				.append("  LGB  B6 200267 - BY JFK5SG1 1343/03SEP09                  "
						+ newline);
		sb
				.append("       BAG STATUS VOL SEP                                   "
						+ newline);
		sb.append("RECEIVED FROM - AGENT" + newline);
		sb.append("JFK.HDQ5SG1 1240/03SEP09 LCTOFR H" + newline);
		String s = sb.toString();

		SabreParseDTO parsedData = Reservation.parseForBaggageInfo(s);
		System.out.println("Num Bag Segments: " + parsedData.getItin().size());
		Assert.assertTrue(parsedData.getBags().size() == 2);
		Assert.assertTrue(parsedData.getItin().size() == 2);
	}
	
	@org.junit.Test
	public void test5() {

		StringBuilder sb = new StringBuilder();
		String newline = "\n";
		sb.append("ITHNPB" + newline);
		sb.append("**PAST DATE PNR - NO UPDATES ALLOWED**" + newline);
		sb.append(" 1.1JACK/SMITH" + newline);
		sb.append("TKT/TIME LIMIT" + newline);
		sb.append("  1.T-14SEP-JFK5NHS" + newline);
		sb.append("  2.TE 2792140005707 JACK/S JFK5NHS 1017/14SEP" + newline);
		sb.append("VCR COUPON DATA EXISTS  *VI TO DISPLAY" + newline);
		sb.append("PHONES" + newline);
		sb.append("  1.JFKPWCT" + newline);
		sb.append("B6 FACTS" + newline);
		sb.append("  1.SSR DOCS B6 HK1/DB/14SEP1980/M/JACK/SMITH" + newline);
		sb.append("REMARKS" + newline);
		sb.append("  1.H-BÂ?A/123 MAIN STREETÂ?C/BEVERLY HILLS, CA USÂ?Z/90210" + newline);
		sb.append("  2.XXAUTH/1234   *Z" + newline);
		sb.append("RECEIVED FROM - VB" + newline);
		sb.append("JFK.HDQ5NHS 0917/14SEP09 ITHNPB H" + newline);
		sb.append("" + newline);

		sb.append("RECEIVED FROM - AGENT" + newline);
		sb.append("JFK.HDQ5SG1 1240/03SEP09 LCTOFR H" + newline);
		String s = sb.toString();

		SabreParseDTO parsedData = Reservation.parseForBaggageInfo(s);
		Assert.assertTrue(parsedData.getBags().size() == 0);
		Assert.assertTrue(parsedData.getItin().size() == 0);
	}
	
	@org.junit.Test
	public void test6() {

		StringBuilder sb = new StringBuilder();
		String newline = "\n";

		sb.append("ITNQKT" + newline);
		sb.append(" 1.1JOHNSON/MIKE  2.1PARKER/FRED" + newline);
		sb.append(" 2 B6 482Y 16SEP W LGBBOS HK2  0800  1628  /E" + newline);
		sb.append(" 3 B61021Y 16SEP W BOSJFK HK2  1945  2105  /E" + newline);
		sb.append("TKT/TIME LIMIT" + newline);
		sb.append("  1.T-14SEP-JFK5NHS" + newline);
		sb.append("  2.TE 2792140006249 JOHNS/M JFK5NHS 1157/14SEP" + newline);
		sb.append("  3.TE 2792140006250 PARKE/F JFK5NHS 1157/14SEP" + newline);
		sb.append("VCR COUPON DATA EXISTS  *VI TO DISPLAY" + newline);
		sb.append("PHONES" + newline);
		sb.append("  1.HDQ551236598-S" + newline);
		sb.append("  2.HDQ2254548515-H" + newline);
		sb.append("PROFILE INDEX DATA EXISTS *PI TO DISPLAY ALL" + newline);
		sb.append("B6 FACTS" + newline);
		sb.append("  1.SSR DOCS B6 HK1/DB/15SEP1980/M/JOHNSON/MIKE" + newline);
		sb.append("  2.SSR DOCS B6 HK1/DB/15APR1982/M/PARKER/FRED" + newline);
		sb.append("REMARKS" + newline);
		sb.append("  1.H-BÂ?A/123 MAIN STREETÂ?C/JERSEY CITY, NJ USÂ?Z/07306" + newline);
		sb.append("  2.XXAUTH/1234   *Z" + newline);
		sb.append("BAGGAGE INFORMATION                                         " + newline);
		sb.append("ROUTING-B6   711 LGB                                        " + newline);
		sb.append(" JOHNSON/MIKE                                               " + newline);
		sb.append("  LGB  B6 200295 - BY JFK5NHS 1700/14SEP09                  " + newline);
		sb.append("  LGB  B6 200296 - BY JFK5NHS 1700/14SEP09                  " + newline);
		sb.append(" PARKER/FRED                                                " + newline);
		sb.append("  LGB  B6 200297 - BY JFK5NHS 1700/14SEP09                  " + newline);
		sb.append("  LGB  B6 200298 - BY JFK5NHS 1700/14SEP09                  " + newline);
		sb.append("RECEIVED FROM - VB" + newline);
		sb.append("HDQ.HDQ5NHS 1045/14SEP09 ITNQKT H" + newline);
		sb.append("" + newline);

		
		String s = sb.toString();
		SabreParseDTO parsedData = Reservation.parseForBaggageInfo(s);
		Assert.assertTrue(parsedData.getBags().size() == 4);
		Assert.assertTrue(parsedData.getItin().size() == 1);
	}
	
	@org.junit.Test
	public void test7() {

		StringBuilder sb = new StringBuilder();
		String newline = "\n";


		sb.append("LPETPI" + newline);
		sb.append("**PAST DATE PNR - NO UPDATES ALLOWED**" + newline);
		sb.append(" 1.1NINEHAN/NICK  2.1NINEHAN/REY" + newline);
		sb.append("TKT/TIME LIMIT" + newline);
		sb.append("  1.T-14SEP-JFK5NHS" + newline);
		sb.append("  2.TE 2792140006308 NINEH/N JFK5NHS 1203/14SEP" + newline);
		sb.append("  3.TE 2792140006309 NINEH/R JFK5NHS 1203/14SEP" + newline);
		sb.append("  4.TK 2790610000588 NINEH/N JFK5NHS 1708/14SEP - AS" + newline);
		sb.append("  5.TK 2790610000589 NINEH/R JFK5NHS 1708/14SEP - AS" + newline);
		sb.append("VCR COUPON DATA EXISTS  *VI TO DISPLAY" + newline);
		sb.append("PHONES" + newline);
		sb.append("  1.JFK55136589-H" + newline);
		sb.append("B6 FACTS" + newline);
		sb.append("  1.SSR DOCS B6 HK1/DB/01APR1982/M/NINEHAN/NICK" + newline);
		sb.append("  2.SSR DOCS B6 HK1/DB/30APR1982/M/NINEHAN/REY" + newline);
		sb.append("  5.SSR APAY B6 HK1 JFKFLL0015K15SEP/SEAT/TWOFP/USD1337 CA/USD1" + newline);
		sb.append("    163 CCAXXXXXXXXXXXX5550EXPXXXX " + newline);
		sb.append("  6.SSR APAY B6 HK1 JFKFLL0015K15SEP/SEAT/TWOFP/USD1337 CA/USD1" + newline);
		sb.append("    163 CCAXXXXXXXXXXXX5550EXPXXXX " + newline);
		sb.append("REMARKS" + newline);
		sb.append("  1.H-BÂ?A/1025 JOHN F KENNDY BLVD.Â?C/JERSEY CITY, NJ USÂ?Z/07306" + newline);
		sb.append("  2.H-01 ANCILLARY SEAT FEE   USD   25.00     410168 " + newline);
		sb.append("  3.H-   2792140006308" + newline);
		sb.append("  4.H-01 ANCILLARY SEAT FEE   USD   25.00     410168 " + newline);
		sb.append("  5.H-   2792140006309" + newline);
		sb.append("BAGGAGE INFORMATION                                         " + newline);
		sb.append("ROUTING-B6    15 FLL                                        " + newline);
		sb.append(" NINEHAN/NICK                                               " + newline);
		sb.append("  FLL  B6 100196 - BY JFK5NHS 1705/14SEP09                  " + newline);
		sb.append("  FLL  B6 100197 - BY JFK5NHS 1705/14SEP09                  " + newline);
		sb.append(" NINEHAN/REY                                                " + newline);
		sb.append("  FLL  B6 100198 - BY JFK5NHS 1705/14SEP09                  " + newline);
		sb.append("  FLL  B6 100199 - BY JFK5NHS 1705/14SEP09                  " + newline);
		sb.append("RECEIVED FROM - VB" + newline);
		sb.append("JFK.HDQ5NHS 1103/14SEP09 LPETPI H" + newline);
		sb.append("" + newline);


		
		String s = sb.toString();
		SabreParseDTO parsedData = Reservation.parseForBaggageInfo(s);

		Assert.assertTrue(parsedData.getBags().size() == 4);
		Assert.assertTrue(parsedData.getItin().size() == 1);
	}
	
	@org.junit.Test
	public void test8() {

		StringBuilder sb = new StringBuilder();
		String newline = "\n";


		sb.append("IVYEOT" + newline);
		sb.append(" 1.1JOHNSON/MIKE" + newline);
		sb.append(" 1 B6 201K 16SEP W JFKLGB HK1  0800  1059  /E" + newline);
		sb.append("TKT/TIME LIMIT" + newline);
		sb.append("  1.T-15SEP-JFK5NHS" + newline);
		sb.append("  2.TE 2792140006622 JOHNS/M JFK5NHS 1007/15SEP" + newline);
		sb.append("VCR COUPON DATA EXISTS  *VI TO DISPLAY" + newline);
		sb.append("PHONES" + newline);
		sb.append("  1.JFK225-4548515-HOME" + newline);
		sb.append("PASSENGER EMAIL DATA EXISTS  *PE TO DISPLAY ALL" + newline);
		sb.append("ADDRESS" + newline);
		sb.append("    N/MIKE JOHNSON" + newline);
		sb.append("    A/123 MAIN STREET" + newline);
		sb.append("    C/JERSEY CITY, NJ         " + newline);
		sb.append("    Z/07306" + newline);
		sb.append("PRICE QUOTE RECORD EXISTS - *PQS" + newline);
		sb.append("PROFILE INDEX DATA EXISTS *PI TO DISPLAY ALL" + newline);
		sb.append("B6 FACTS" + newline);
		sb.append("  1.SSR DOCS B6 HK1/DB/15SEP1982/M/JOHNSON/MIKE" + newline);
		sb.append("REMARKS" + newline);
		sb.append("  1.H-BÂ?A/123 MAIN STREETÂ?C/JERSEY CITY, NJ USÂ?Z/07306" + newline);
		sb.append("  2.XXAUTH/1234   *Z" + newline);
		sb.append("BAGGAGE INFORMATION                                         " + newline);
		sb.append("ROUTING-B6   201 LGB                                        " + newline);
		sb.append(" JOHNSON/MIKE                                               " + newline);
		sb.append("  LGB  B6 200299 - BY JFK5NHS 1157/15SEP09                  " + newline);
		sb.append("RECEIVED FROM - VB" + newline);
		sb.append("JFK.HDQ5NHS 0907/15SEP09 IVYEOT H" + newline);
		sb.append("" + newline);

		
		String s = sb.toString();
		SabreParseDTO parsedData = Reservation.parseForBaggageInfo(s);

		Assert.assertTrue(parsedData.getBags().size() == 1);
		Assert.assertTrue(parsedData.getItin().size() == 1);
	}
	
	@org.junit.Test
	public void test9() {
		StringBuilder sb = new StringBuilder();
		String newline = "\n";

		sb.append("MQESJZ" + newline);
		sb.append(" 1.1JOHNSON/MIKE  2.1PARKER/FRED" + newline);
		sb.append(" 1 B6 601Q 16SEP W JFKFLL HK2  0804  1111  /E" + newline);
		sb.append(" 2 B6   4L 18SEP F FLLJFK HK2  0600  0844  /E" + newline);
		sb.append("TKT/TIME LIMIT" + newline);
		sb.append("  1.T-15SEP-JFK5NHS" + newline);
		sb.append("  2.TE 2792140006626 JOHNS/M JFK5NHS 1022/15SEP" + newline);
		sb.append("  3.TE 2792140006627 PARKE/F JFK5NHS 1022/15SEP" + newline);
		sb.append("  4.TE 2792140006663 JOHNS/M JFK5NHS 1215/15SEP" + newline);
		sb.append("  5.TE 2792140006664 PARKE/F JFK5NHS 1215/15SEP" + newline);
		sb.append("  6.TK 2790520000043 JOHNS/M JFK5NHS 1224/15SEP" + newline);
		sb.append("  7.TK 2790520000044 PARKE/F JFK5NHS 1225/15SEP" + newline);
		sb.append("VCR COUPON DATA EXISTS  *VI TO DISPLAY" + newline);
		sb.append("PHONES" + newline);
		sb.append("  1.JFK225-4548515-HOME" + newline);
		sb.append("  2.JFK551-4548515-HOME" + newline);
		sb.append("PASSENGER EMAIL DATA EXISTS  *PE TO DISPLAY ALL" + newline);
		sb.append("ADDRESS" + newline);
		sb.append("    N/MIKE JOHNSON" + newline);
		sb.append("    A/123 MAIN STREET" + newline);
		sb.append("    C/JERSEY CITY, NJ         " + newline);
		sb.append("    Z/07306" + newline);
		sb.append("PRICE QUOTE RECORD EXISTS - *PQS" + newline);
		sb.append("TICKETED RETAINED REISSUE EXISTS - *PQS" + newline);
		sb.append("FREQUENT TRAVELER" + newline);
		sb.append("  1.B6 3411629550           HK B6   1.1 JOHNSON/MIKE" + newline);
		sb.append("PROFILE INDEX DATA EXISTS *PI TO DISPLAY ALL" + newline);
		sb.append("REMARKS" + newline);
		sb.append("  1.XXAUTH/1234   *Z" + newline);
		sb.append("BAGGAGE INFORMATION                                         " + newline);
		sb.append("ROUTING-B6   601 FLL                                        " + newline);
		sb.append(" JOHNSON/MIKE                                               " + newline);
		sb.append("  FLL  B6 100206 - BY JFK5NHS 1233/15SEP09                  " + newline);
		sb.append("  FLL  B6 100207 - BY JFK5NHS 1233/15SEP09                  " + newline);
		sb.append(" PARKER/FRED                                                " + newline);
		sb.append("  FLL  B6 100208 - BY JFK5NHS 1233/15SEP09                  " + newline);
		sb.append("  FLL  B6 100209 - BY JFK5NHS 1233/15SEP09                  " + newline);
		sb.append("RECEIVED FROM - VB" + newline);
		sb.append("JFK.HDQ5NHS 0922/15SEP09 MQESJZ H" + newline);
		sb.append("" + newline);


		
		String s = sb.toString();
		SabreParseDTO parsedData = Reservation.parseForBaggageInfo(s);
		Assert.assertTrue(parsedData.getBags().size() == 4);
		Assert.assertTrue(parsedData.getItin().size() == 1);
	}
	
	
	@org.junit.Test
	public void test91() {
		StringBuilder sb = new StringBuilder();
		String newline = "\n";

		sb.append("MQEMVD" + newline);
		sb.append(" 1.3GOLDMAN/NICK/JANE/PETER" + newline);
		sb.append(" 1 B61004K 16SEP W JFKBOS HK3  0745  0855  /E" + newline);
		sb.append(" 2 B6 455K 16SEP W BOSFLL HK3  1135  1451  /E" + newline);
		sb.append(" 3 B6 450Y 18SEP F FLLBOS HK3  0750  1049  /E" + newline);
		sb.append(" 4 B61009Y 18SEP F BOSJFK HK3  1135  1243  /E" + newline);
		sb.append("TKT/TIME LIMIT" + newline);
		sb.append("  1.T-15SEP-JFK5NHS" + newline);
		sb.append("  2.TE 2792140006640 GOLDM/N JFK5NHS 1054/15SEP" + newline);
		sb.append("  3.TE 2792140006641 GOLDM/J JFK5NHS 1054/15SEP" + newline);
		sb.append("  4.TE 2792140006642 GOLDM/P JFK5NHS 1054/15SEP" + newline);
		sb.append("VCR COUPON DATA EXISTS  *VI TO DISPLAY" + newline);
		sb.append("PHONES" + newline);
		sb.append("  1.JFKPWCT" + newline);
		sb.append("  2.JFK5126589558-CELL ADDED NUMBER" + newline);
		sb.append("PRICE QUOTE RECORD EXISTS - *PQS" + newline);
		sb.append("FREQUENT TRAVELER" + newline);
		sb.append("  1.B6 3755707911           HK B6   1.2 GOLDMAN/JANE" + newline);
		sb.append("PROFILE INDEX DATA EXISTS *PI TO DISPLAY ALL" + newline);
		sb.append("B6 FACTS" + newline);
		sb.append("  1.SSR DOCS B6 HK1/DB/18SEP1982/M/GOLDMAN/NICK" + newline);
		sb.append("  2.SSR DOCS B6 HK1/DB/19SEP1982/F/GOLDMAN/JANE" + newline);
		sb.append("  3.SSR DOCS B6 HK1/DB/05FEB1980/M/GOLDMAN/PETER" + newline);
		sb.append("REMARKS" + newline);
		sb.append("  1.H-BÂ?A/123 FERRER STREETÂ?C/NEW YORK, NY USÂ?Z/10018" + newline);
		sb.append("  2.XXAUTH/1234   *Z" + newline);
		sb.append("BAGGAGE INFORMATION                                         " + newline);
		sb.append("ROUTING-B6  1004 BOS  /B6   455 FLL                         " + newline);
		sb.append(" GOLDMAN/NICK                                               " + newline);
		sb.append("  FLL  B6 100211 - BY JFK5NHS 1304/15SEP09                  " + newline);
		sb.append(" GOLDMAN/JANE                                               " + newline);
		sb.append("  FLL  B6 100212 - BY JFK5NHS 1307/15SEP09                  " + newline);
		sb.append("RECEIVED FROM - VB" + newline);
		sb.append("JFK.HDQ5NHS 0953/15SEP09 MQEMVD H" + newline);
		sb.append("" + newline);


		
		String s = sb.toString();
		SabreParseDTO parsedData = Reservation.parseForBaggageInfo(s);
		Assert.assertTrue(parsedData.getBags().size() == 2);
		Assert.assertTrue(parsedData.getItin().size() == 2);
	}

}
