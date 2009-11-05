package aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Configurable;

import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service.WorldTracerRule.Format;

@Configurable("wtService")
public class DefaultWorldTracerService implements WorldTracerService {

	private static final DateFormat ITIN_DATE_FORMAT = new SimpleDateFormat("ddMMM", Locale.US);

	private static final Logger logger = Logger.getLogger(DefaultWorldTracerService.class);

	private static final Pattern FLIGHTNUM_FORMAT = Pattern.compile("[1-9]\\d{0,3}[a-zA-Z]?");

//	private WorldTracerConnector wtConnector;

	private String wtCompanyCode;

	private static final List<String> wt_mats = Arrays.asList("D", "L", "M", "R", "T");
	private static final List<String> wt_descs = Arrays.asList("D", "L", "M", "R", "T", "B", "K", "C", "H", "S", "W",
			"X");

	public static final String FIELD_SEP = ".";
	public static final String ENTRY_SEP = "/";
	public static final String CONTINUATION = "-";

	private static final List<String> VALID_BAG_TYPES = Arrays.asList(new String[] { "01", "02", "03", "05", "06",
			"07", "08", "09", "10", "12", "20", "22", "23", "25", "26", "27", "28", "29", "50", "51", "52", "53", "54",
			"55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "71", "72", "73",
			"74", "75", "81", "82", "83", "85", "89", "90", "92", "93", "94", "95", "96", "97", "98", "99" });

	public static enum RepeatType {
		NONE, SAME_LINE, MANY_LINES, MULTIPLE
	}
	public static final WorldTracerRule<String> BASIC_RULE = new BasicRule(1, 57, 1, Format.FREE_FLOW);
	private static final String DEFAULT_BAG_TYPE = "99";

	private static final String UNKNOWN_AIRLINE = "YY";


}
