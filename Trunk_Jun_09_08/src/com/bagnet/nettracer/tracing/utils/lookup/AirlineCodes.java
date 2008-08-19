/**
 * 
 */
package com.bagnet.nettracer.tracing.utils.lookup;

import java.util.List;
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
public class AirlineCodes {

	private static final String PATTERN_10_DIGIT_BAG_TAG = "^\\d{10}$";
	private static final String PATTERN_9_DIGIT_BAG_TAG = "^\\d{10}$";
	private static final String PATTERN_8_CHAR_BAG_TAG = "^[a-zA-Z]{2}\\d{6}$";

	/**
	 * Returns the two letter airline code when passed in the three
	 * digit ticketing code.  This was originally built to convert
	 * bag tag numbers with three digit codes to two letters.
	 * 
	 * @param threeDigitCode Three digit airline ticketing code.
	 * @return Two letter airline code.  Returns null if no match is found.
	 */
	public static String getTwoLetterAirlineCode(String threeDigitCode) {
		Session sess = HibernateWrapper.getSession().openSession();

		SQLQuery query = sess.createSQLQuery("SELECT * FROM LOOKUP_AIRLINE_CODES WHERE Airline_3_Digit_Ticketing_Code = :string");
		query.addScalar("Airline_2_Character_Code", Hibernate.STRING);
		//query.addScalar("Airline_3_Digit_Ticketing_Code", Hibernate.STRING);
		query.setString("string", threeDigitCode);
		
		List results = query.list();
		
		if (results != null && results.size() >0) {
			return (String)results.get(0);
		}
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
		Session sess = HibernateWrapper.getSession().openSession();

		SQLQuery query = sess.createSQLQuery("SELECT * FROM LOOKUP_AIRLINE_CODES WHERE Airline_2_Character_Code = :string");
		
		query.addScalar("Airline_3_Digit_Ticketing_Code", Hibernate.STRING);
		query.setString("string", twoCharacterCode);
		
		List results = query.list();
		
		if (results != null && results.size() >0) {
			return (String)results.get(0);
		}
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
		
		if (twoCharPattern.matcher(bagTag).find()) {
			return bagTag;
		}

		String airlineCode = "";
		String suffix = "";
		
		if (tenDigitPattern.matcher(bagTag).find()) {
			airlineCode = bagTag.substring(1, 4);
			suffix = bagTag.substring(4); 
		} else if (nineDigitPattern.matcher(bagTag).find()) {
			airlineCode = bagTag.substring(0, 3);
			suffix = bagTag.substring(3);
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

		Pattern tenDigitPattern = Pattern.compile(PATTERN_10_DIGIT_BAG_TAG);
		Pattern nineDigitPattern = Pattern.compile(PATTERN_9_DIGIT_BAG_TAG);
		Pattern twoCharPattern = Pattern.compile(PATTERN_8_CHAR_BAG_TAG);
		
		if (tenDigitPattern.matcher(bagTag).find()) {
			return bagTag;
		} else if (nineDigitPattern.matcher(bagTag).find()) {
			return "0" + bagTag;
		} else if (twoCharPattern.matcher(bagTag).find()) {
			String twoCharacterCode = bagTag.substring(0, 2);
			String threeDigitCode = getThreeDigitTicketingCode(twoCharacterCode);
			if (threeDigitCode == null) {
				throw new BagtagException(BagtagException.NO_MATCH_MESSAGE);
			}
			return threeDigitCode + bagTag.substring(2);
		} else {
			throw new BagtagException(BagtagException.INVALID_FORMAT_MESSAGE);
		}
	}
}
