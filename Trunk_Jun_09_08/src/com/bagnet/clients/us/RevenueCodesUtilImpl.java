package com.bagnet.clients.us;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

import com.bagnet.nettracer.tracing.bmo.PropertyBMO;
import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.utils.StringUtils;

/**
 * 
 * @author Mike Sanders
 *
 */
public class RevenueCodesUtilImpl {

	// MJS: regex strings for parsing the revenue code from the pnr contents.
	private String regexStart = "^[1-9]{1}\\.{1}[A-Z]{2}[0-9]{1}[A-Z]{1}[0-9]*/[0-9]{1}";
	private String regexEnd = "/?([A-Z]+)?";
	private String regexRevCode = "[A-Z]{2}[0-9]{1}[A-Z]{1}";
	
	private static RevenueCodesUtilImpl instance = null;
	private static Set<String> nonRevenueCodes = null;
	
	private RevenueCodesUtilImpl() {
		loadNonRevenueCodes();
	}
	
	/**
	 * loadRevenueCodes - retrieves the revenue code string from the database
	 * and loads the individual codes into a Set.
	 */
	private final void loadNonRevenueCodes() {
		try {
			String revCodeString = PropertyBMO.getValue(TracingConstants.NON_REVENUE_CODES_KEY);
			if (revCodeString != null) {
				nonRevenueCodes = new TreeSet<String>(Arrays.asList(revCodeString.split(",")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * getInstance - retrieves the single instance of this class or creates
	 * it if it doesn't exist yet.
	 * @return - the single instance of the RevenueCodesUtilImpl class.
	 */
	public static RevenueCodesUtilImpl getInstance() {
		if (instance == null) {
			instance = new RevenueCodesUtilImpl();
		}
		return instance;
	}
	
	/**
	 * isNonRevenueCode - returns true if the candidate String is found in 
	 * the nonRevenueCodes set or false otherwise.
	 * @param candidate - the candidate String to look up in the nonRevenueCodes Set.
	 * @return - true if the candidate String is found in the Set or false 
	 * otherwise.
	 */
	public boolean isNonRevenueCode(String candidate) {
		if (nonRevenueCodes == null || candidate == null) {
			return false;
		}
		return nonRevenueCodes.contains(candidate);
	}

	/**
	 * getNonRevenueCodeFromPnr - searches the given pnr String based on the given 
	 * searchKey String and the regex Strings defined above. If any line segment
	 * matches the combined regex then a candidate revenue code is returned based
	 * on the revenue code regex defined above.
	 * @param pnr - is a String containing the pnr contents to search.
	 * @param searchKey - is a String containing a value to assist in the revenue
	 * code search.
	 * @return - a candidate String found in the pnr that matches the revenue regex
	 * defined above or null if no candidate is found.
	 */
	public String getNonRevenueCodeFromPnr(String pnr, String searchKey) {
		
		if (pnr == null || searchKey == null) {
			return null;
		}
		
		String regex = regexStart + StringUtils.convertToRegexSafe(searchKey) + regexEnd;
		String candidate;
		String[] tokens;
		String[] lines = pnr.split("\n");
		// mjs: search each line for the revenue code
		for (int i = 0; i < lines.length; ++i) {
			tokens = lines[i].split(" ");
			// mjs: search each line segment for the revenue code
			for (int j = 0; j < tokens.length; ++j) {
				if (Pattern.matches(regex, tokens[j])) {
					candidate = tokens[j].substring(2, 6);
					if (Pattern.matches(regexRevCode, candidate) && isNonRevenueCode(candidate)) {
						return candidate;
					}
				}
			}
		}	
		return null;
	}

}
