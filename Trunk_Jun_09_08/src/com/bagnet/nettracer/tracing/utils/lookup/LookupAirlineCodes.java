/**
 * 
 */
package com.bagnet.nettracer.tracing.utils.lookup;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.bagnet.nettracer.exceptions.BagtagException;
import com.bagnet.nettracer.hibernate.HibernateWrapper;

/**
 * @author Byron
 *
 */
public class LookupAirlineCodes {

	public static final String PATTERN_10_DIGIT_BAG_TAG = "^\\d{10}$";
	public static final String PATTERN_9_DIGIT_BAG_TAG = "^\\d{9}$";
	public static final String PATTERN_8_CHAR_BAG_TAG = "^[a-zA-Z0-9]{2}\\d{6}$";
	
	public static HashMap<String, String> TWO_CHAR_TO_THREE_DIGIT = null;
	public static HashMap<String, String> THREE_DIGIT_TO_TWO_CHAR = null;
	

	/**
	 * Returns the two letter airline code when passed in the three
	 * digit ticketing code.  This was originally built to convert
	 * bag tag numbers with three digit codes to two letters.
	 * 
	 * @param threeDigitCode Three digit airline ticketing code.
	 * @return Two letter airline code.  Returns null if no match is found.
	 */
	public static String getTwoLetterAirlineCode(String threeDigitCode) {
		if (THREE_DIGIT_TO_TWO_CHAR == null) {
			reloadTable();
		}
		if (THREE_DIGIT_TO_TWO_CHAR.containsKey(threeDigitCode)) {
			return THREE_DIGIT_TO_TWO_CHAR.get(threeDigitCode);
		}
		THREE_DIGIT_TO_TWO_CHAR.put(threeDigitCode, null);
		return null;
	}
	
	/**
	 * Returns the three digit ticketing code two letter airline code
	 * when passed in the two character airline code. 
	 * 
	 * @param twoCharacterCode Two character airline code.
	 * @return Three digit airline ticketing code.  Returns null if no match is found.
	 */
	public static String getThreeDigitTicketingCode(String twoCharacterCode) {
		if (TWO_CHAR_TO_THREE_DIGIT == null) {
			reloadTable();
		}
		if (TWO_CHAR_TO_THREE_DIGIT.containsKey(twoCharacterCode)) {
			return TWO_CHAR_TO_THREE_DIGIT.get(twoCharacterCode);
		}
		TWO_CHAR_TO_THREE_DIGIT.put(twoCharacterCode, null);
		return null;
	}
	
	/**
	 * Method used to convert a 9 or 10 digit bag tag number to a bag tag
	 * format using 2 characters and 6 digits.  If the conversion fails an
	 * exception is thrown.
	 * 
	 * @param bagTag
	 * @return Returns an 8 character bag tag number.
	 */
	public static String getTwoCharacterBagTag(String bagTag) throws BagtagException {
		Pattern tenDigitPattern = Pattern.compile(PATTERN_10_DIGIT_BAG_TAG);
		Pattern nineDigitPattern = Pattern.compile(PATTERN_9_DIGIT_BAG_TAG);
		Pattern twoCharPattern = Pattern.compile(PATTERN_8_CHAR_BAG_TAG);

		String airlineCode = "";
		String suffix = "";
		
		if (tenDigitPattern.matcher(bagTag).find()) {
			airlineCode = bagTag.substring(1, 4);
			suffix = bagTag.substring(4); 
		} else if (nineDigitPattern.matcher(bagTag).find()) {
			airlineCode = bagTag.substring(0, 3);
			suffix = bagTag.substring(3);
		} else if (twoCharPattern.matcher(bagTag).find()) {
			return bagTag;
		} else {
			throw new BagtagException(BagtagException.INVALID_FORMAT_MESSAGE);
		}
		
		String twoLetterCode = getTwoLetterAirlineCode(airlineCode);
		
		if (twoLetterCode == null) {
			throw new BagtagException(BagtagException.NO_MATCH_MESSAGE);
		}
		
		return twoLetterCode + suffix;
	}
	
	/**
	 * Method used to convert an 8 character or 9 digit bag tag number to
	 * a bag tag format using 10 digits.  If the conversion fails then an
	 * exception is thrown.
	 * 
	 * @param bagTag
	 * @return Returns an 8 character bag tag number.	 */
	public static String getFullBagTag(String bagTag) throws BagtagException {
		return getFullBagTag(bagTag, null);
	}
	
	public static boolean areBagTagsSame(String bagTag1, String bagTag2) {
		
		String convBagTag1 = null;
		String convBagTag2 = null;
		
		if (bagTag1.equals(bagTag2)) {
			return true;
		}
		
		// Convert to 10 digit and ignore first digit
		try {
			convBagTag1 = LookupAirlineCodes.getFullBagTag(bagTag1);
			convBagTag1 = convBagTag1.substring(1);
		} catch (BagtagException e) {
			convBagTag1 = bagTag1;
		}
		
		try {
			convBagTag2 = LookupAirlineCodes.getFullBagTag(bagTag2);
			convBagTag2 = convBagTag2.substring(1);
		} catch (BagtagException e) {
			convBagTag2 = bagTag2;
		}
		
		return convBagTag1.equals(convBagTag2);
	}
	
	public static String getFullBagTag(String bagTag, ConcurrentHashMap<String, String> cachedMap) throws BagtagException {

		Pattern tenDigitPattern = Pattern.compile(PATTERN_10_DIGIT_BAG_TAG);
		Pattern nineDigitPattern = Pattern.compile(PATTERN_9_DIGIT_BAG_TAG);
		Pattern twoCharPattern = Pattern.compile(PATTERN_8_CHAR_BAG_TAG);
		
		if (tenDigitPattern.matcher(bagTag).find()) {
			return bagTag;
		} else if (nineDigitPattern.matcher(bagTag).find()) {
			return "0" + bagTag;
		} else if (twoCharPattern.matcher(bagTag).find()) {
			String twoCharacterCode = bagTag.substring(0, 2);
			String threeDigitCode = null;
			if (cachedMap != null && cachedMap.containsKey(twoCharacterCode)) {
				threeDigitCode = cachedMap.get(twoCharacterCode);
			} else {
				threeDigitCode = getThreeDigitTicketingCode(twoCharacterCode);
				if (cachedMap != null && twoCharacterCode != null && threeDigitCode != null) {
					cachedMap.put(twoCharacterCode, threeDigitCode);
				}
			}
			if (threeDigitCode == null) {
				throw new BagtagException(BagtagException.NO_MATCH_MESSAGE);
			}
			return "0" + threeDigitCode + bagTag.substring(2);
		} else {
			throw new BagtagException(BagtagException.INVALID_FORMAT_MESSAGE);
		}
	}
	
	
	public static String extractTwoCharAirlineCode(String bagTag) {
		if(Pattern.compile(PATTERN_10_DIGIT_BAG_TAG).matcher(bagTag).find()) {
			return getTwoLetterAirlineCode(bagTag.substring(1, 4));
		}
		if(Pattern.compile(PATTERN_9_DIGIT_BAG_TAG).matcher(bagTag).find()) {
			return getTwoLetterAirlineCode(bagTag.substring(0, 3));
		}
		if(Pattern.compile(PATTERN_8_CHAR_BAG_TAG).matcher(bagTag).find()) {
			return bagTag.substring(0, 2);
		}
		return null;	
	}
	
	public static void reloadTable() {
		Session sess = HibernateWrapper.getSession().openSession();

		SQLQuery query = sess.createSQLQuery("SELECT Airline_2_Character_Code, Airline_3_Digit_Ticketing_Code FROM LOOKUP_AIRLINE_CODES");
		query.addScalar("Airline_2_Character_Code", Hibernate.STRING);
		query.addScalar("Airline_3_Digit_Ticketing_Code", Hibernate.STRING);
		
		List results = query.list();
		sess.close();
		
		if (results != null && results.size() >0) {
			TWO_CHAR_TO_THREE_DIGIT = new HashMap<String, String>();
			THREE_DIGIT_TO_TWO_CHAR = new HashMap<String, String>();
			for(Object result: results) {
				Object[] res = (Object[]) result;
				TWO_CHAR_TO_THREE_DIGIT.put((String) res[0], (String) res[1]);
				THREE_DIGIT_TO_TWO_CHAR.put((String) res[1], (String) res[0]);
			}
		}
		
	}
}
