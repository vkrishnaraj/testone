package com.bagnet.nettracer.cronjob.tracing;

import java.util.ArrayList;

import com.bagnet.nettracer.cronjob.tracing.dto.MatchResult;
import com.bagnet.nettracer.match.StringCompare;

public class MatchUtils {
	/**
	 * Helper method written to automatically create a match element from the
	 * two given strings using the string compare.
	 * 
	 * @param e
	 *            MatchElement
	 * @param inc
	 *            Incident String
	 * @param ohd
	 *            Ohd String
	 * @return Returns a MatchResult element.
	 */
	public static MatchResult stringCompare(MatchElement e, String inc,
			String ohd) {
		return stringCompare(e, inc, ohd, 0);
	}

	/**
	 * Helper method written to automatically create a match element from the
	 * two given strings using the string compare.
	 * 
	 * @param e
	 *            matchElement
	 * @param inc
	 *            Incident string
	 * @param ohd
	 *            Ohd string
	 * @param bagNumber
	 *            Bag tag number
	 * @return
	 */
	public static MatchResult stringCompare(MatchElement e, String inc,
			String ohd, int bagNumber) {
		double score = StringCompare.compareStrings(inc, ohd);
		if (score > 0) {
			MatchResult result = new MatchResult(e, score, inc, ohd);
			result.setBagNumber(bagNumber);
			return result;
		} else {
			return null;
		}
	}

	/**
	 * Creates an ArrayList containing a single MatchResult that is created
	 * using the StringCompare class to determine the score of that element.
	 * 
	 * @param e
	 *            MatchElement
	 * @param a
	 *            String a
	 * @param b
	 *            String b
	 * @return Returns an ArrayList of MatchResults
	 */
	public static ArrayList<MatchResult> stringCompareToArray(MatchElement e,
			String a, String b) {
		ArrayList<MatchResult> al = new ArrayList<MatchResult>();
		al.add(stringCompare(e, a, b));
		return al;
	}

	public static boolean secondaryColorMatch(String mbrcolor, String ohdcolor) {
		mbrcolor = mbrcolor.trim().toUpperCase();
		ohdcolor = ohdcolor.trim().toUpperCase();
		boolean retVal = false;
		
		if (ohdcolor.equals("BE")) {
			if (mbrcolor.equals("BN") || mbrcolor.equals("WT")
					|| mbrcolor.equals("YW") || mbrcolor.equals("MC")
					|| mbrcolor.equals("PR") || mbrcolor.equals("TD"))
				retVal = true;
		}
		if (ohdcolor.equals("BK")) {
			if (mbrcolor.equals("BU") || mbrcolor.equals("GY")
					|| mbrcolor.equals("MC") || mbrcolor.equals("PR")
					|| mbrcolor.equals("TD"))
				retVal = true;
		}
		if (ohdcolor.equals("BN")) {
			if (mbrcolor.equals("BE") || mbrcolor.equals("RD")
					|| mbrcolor.equals("MC") || mbrcolor.equals("PR")
					|| mbrcolor.equals("TD"))
				retVal = true;
		}
		if (ohdcolor.equals("BU")) {
			if (mbrcolor.equals("BK") || mbrcolor.equals("GY")
					|| mbrcolor.equals("MC") || mbrcolor.equals("PR")
					|| mbrcolor.equals("TD"))
				retVal = true;
		}
		if (ohdcolor.equals("GN")) {
			if (mbrcolor.equals("MC") || mbrcolor.equals("PR")
					|| mbrcolor.equals("TD"))
				retVal = true;
		}
		if (ohdcolor.equals("GY")) {
			if (mbrcolor.equals("BK") || mbrcolor.equals("BU")
					|| mbrcolor.equals("MC") || mbrcolor.equals("PR")
					|| mbrcolor.equals("TD"))
				retVal = true;
		}
		if (ohdcolor.equals("MC")) {
			retVal = true;
		}
		if (ohdcolor.equals("PR")) {
			retVal = true;
		}
		if (ohdcolor.equals("PU")) {
			if (mbrcolor.equals("RD") || mbrcolor.equals("BU")
					|| mbrcolor.equals("MC") || mbrcolor.equals("TD")
					|| mbrcolor.equals("PR"))
				retVal = true;
		}
		if (ohdcolor.equals("RD")) {
			if (mbrcolor.equals("BN") || mbrcolor.equals("MC")
					|| mbrcolor.equals("PR") || mbrcolor.equals("TD"))
				retVal = true;
		}
		if (ohdcolor.equals("TD")) {
			retVal = true;
		}
		if (ohdcolor.equals("WT")) {
			if (mbrcolor.equals("BE") || mbrcolor.equals("MC")
					|| mbrcolor.equals("PR") || mbrcolor.equals("TD"))
				retVal = true;
		}
		if (ohdcolor.equals("YW")) {
			if (mbrcolor.equals("BE") || mbrcolor.equals("MC")
					|| mbrcolor.equals("PR") || mbrcolor.equals("TD"))
				retVal = true;
		}
		return retVal;
	}

	public static boolean tertiaryColorMatch(String mbrcolor, String ohdcolor) {
		mbrcolor = mbrcolor.trim().toUpperCase();
		ohdcolor = ohdcolor.trim().toUpperCase();
		boolean retVal = false;
		if (ohdcolor.equals("BE")) {
			if (mbrcolor.equals("GN"))
				retVal = true;
		}
		if (ohdcolor.equals("BK")) {
			if (mbrcolor.equals("BN") || mbrcolor.equals("GN"))
				retVal = true;
		}
		if (ohdcolor.equals("BN")) {
			if (mbrcolor.equals("BK") || mbrcolor.equals("GN")
					|| mbrcolor.equals("GY"))
				retVal = true;
		}
		if (ohdcolor.equals("BU")) {
			if (mbrcolor.equals("GN"))
				retVal = true;
		}
		if (ohdcolor.equals("GN")) {
			if (mbrcolor.equals("GY") || mbrcolor.equals("BN")
					|| mbrcolor.equals("BE") || mbrcolor.equals("BU")
					|| mbrcolor.equals("BK"))
				retVal = true;
		}
		if (ohdcolor.equals("GY")) {
			if (mbrcolor.equals("BN") || mbrcolor.equals("GN"))
				retVal = true;
		}
		if (ohdcolor.equals("PU")) {
			if (mbrcolor.equals("BK") || mbrcolor.equals("BN"))
				retVal = true;
		}
		if (ohdcolor.equals("RD")) {
			if (mbrcolor.equals("YW"))
				retVal = true;
		}
		if (ohdcolor.equals("YW")) {
			if (mbrcolor.equals("RD"))
				retVal = true;
		}
		return retVal;
	}

	public static boolean secondaryTypeMatch(String mbrbt, String ohdbt) {
		mbrbt = mbrbt.trim().toUpperCase();
		ohdbt = ohdbt.trim().toUpperCase();
		boolean retVal = false;

		if (ohdbt.equals("01")) {
			if (mbrbt.equals("02"))
				retVal = true;
		}
		if (ohdbt.equals("02")) {
			if (mbrbt.equals("01") || mbrbt.equals("22"))
				retVal = true;
		}
		if (ohdbt.equals("03")) {
			if (mbrbt.equals("04") || mbrbt.equals("05"))
				retVal = true;
		}
		if (ohdbt.equals("04")) {
			if (mbrbt.equals("03"))
				retVal = true;
		}
		if (ohdbt.equals("05")) {
			if (mbrbt.equals("03") || mbrbt.equals("04") || mbrbt.equals("23"))
				retVal = true;
		}
		if (ohdbt.equals("06")) {
			if (mbrbt.equals("07"))
				retVal = true;
		}
		if (ohdbt.equals("07")) {
			if (mbrbt.equals("06"))
				retVal = true;
		}
		if (ohdbt.equals("08")) {
			if (mbrbt.equals("09"))
				retVal = true;
		}
		if (ohdbt.equals("09")) {
			if (mbrbt.equals("08") || mbrbt.equals("51") || mbrbt.equals("96"))
				retVal = true;
		}
		if (ohdbt.equals("11")) {
			if (mbrbt.equals("52") || mbrbt.equals("12"))
				retVal = true;
		}
		if (ohdbt.equals("12")) {
			if (mbrbt.equals("11"))
				retVal = true;
		}
		if (ohdbt.equals("22")) {
			if (mbrbt.equals("02"))
				retVal = true;
		}
		if (ohdbt.equals("23")) {
			if (mbrbt.equals("05"))
				retVal = true;
		}
		if (ohdbt.equals("24")) {
			if (mbrbt.equals("27"))
				retVal = true;
		}
		if (ohdbt.equals("27")) {
			if (mbrbt.equals("24") || mbrbt.equals("96"))
				retVal = true;
		}
		if (ohdbt.equals("50")) {
			if (mbrbt.equals("51"))
				retVal = true;
		}
		if (ohdbt.equals("51")) {
			if (mbrbt.equals("09") || mbrbt.equals("50"))
				retVal = true;
		}
		if (ohdbt.equals("52")) {
			if (mbrbt.equals("11") || mbrbt.equals("12"))
				retVal = true;
		}
		if (ohdbt.equals("55")) {
			if (mbrbt.equals("93"))
				retVal = true;
		}
		if (ohdbt.equals("63")) {
			if (mbrbt.equals("75"))
				retVal = true;
		}
		if (ohdbt.equals("66")) {
			if (mbrbt.equals("67"))
				retVal = true;
		}
		if (ohdbt.equals("67")) {
			if (mbrbt.equals("66"))
				retVal = true;
		}
		if (ohdbt.equals("70")) {
			if (mbrbt.equals("73") || mbrbt.equals("74"))
				retVal = true;
		}
		if (ohdbt.equals("73")) {
			if (mbrbt.equals("70") || mbrbt.equals("74"))
				retVal = true;
		}
		if (ohdbt.equals("74")) {
			if (mbrbt.equals("70") || mbrbt.equals("73"))
				retVal = true;
		}
		if (ohdbt.equals("75")) {
			if (mbrbt.equals("63"))
				retVal = true;
		}
		if (ohdbt.equals("80")) {
			if (mbrbt.equals("81"))
				retVal = true;
		}
		if (ohdbt.equals("81")) {
			if (mbrbt.equals("80"))
				retVal = true;
		}
		if (ohdbt.equals("82")) {
			if (mbrbt.equals("84"))
				retVal = true;
		}
		if (ohdbt.equals("84")) {
			if (mbrbt.equals("82"))
				retVal = true;
		}
		if (ohdbt.equals("91")) {
			if (mbrbt.equals("94") || mbrbt.equals("95"))
				retVal = true;
		}
		if (ohdbt.equals("93")) {
			if (mbrbt.equals("55") || mbrbt.equals("96"))
				retVal = true;
		}
		if (ohdbt.equals("94")) {
			if (mbrbt.equals("91"))
				retVal = true;
		}
		if (ohdbt.equals("95")) {
			if (mbrbt.equals("91"))
				retVal = true;
		}
		if (ohdbt.equals("96")) {
			if (mbrbt.equals("93") || mbrbt.equals("27") || mbrbt.equals("9"))
				retVal = true;
		}
		return retVal;
	}

	public static boolean tertiaryTypeMatch(String mbrbt, String ohdbt) {
		mbrbt = mbrbt.trim().toUpperCase();
		ohdbt = ohdbt.trim().toUpperCase();
		boolean retVal = false;
		if (ohdbt.equals("01")) {
			if (mbrbt.equals("03"))
				retVal = true;
		}
		if (ohdbt.equals("03")) {
			if (mbrbt.equals("01"))
				retVal = true;
		}
		if (ohdbt.equals("04")) {
			if (mbrbt.equals("05"))
				retVal = true;
		}
		if (ohdbt.equals("05")) {
			if (mbrbt.equals("04"))
				retVal = true;
		}
		if (ohdbt.equals("06")) {
			if (mbrbt.equals("07"))
				retVal = true;
		}
		if (ohdbt.equals("07")) {
			if (mbrbt.equals("06"))
				retVal = true;
		}
		if (ohdbt.equals("08")) {
			if (mbrbt.equals("29") || mbrbt.equals("64"))
				retVal = true;
		}
		if (ohdbt.equals("09")) {
			if (mbrbt.equals("29") || mbrbt.equals("64") || mbrbt.equals("51")
					|| mbrbt.equals("93"))
				retVal = true;
		}
		if (ohdbt.equals("10")) {
			if (mbrbt.equals("50") || mbrbt.equals("55") || mbrbt.equals("80")
					|| mbrbt.equals("81") || mbrbt.equals("82")
					|| mbrbt.equals("83") || mbrbt.equals("84")
					|| mbrbt.equals("92"))
				retVal = true;
		}
		if (ohdbt.equals("20")) {
			if (mbrbt.equals("24"))
				retVal = true;
		}
		if (ohdbt.equals("22")) {
			if (mbrbt.equals("23"))
				retVal = true;
		}
		if (ohdbt.equals("23")) {
			if (mbrbt.equals("22"))
				retVal = true;
		}
		if (ohdbt.equals("24")) {
			if (mbrbt.equals("20") || mbrbt.equals("25"))
				retVal = true;
		}
		if (ohdbt.equals("25")) {
			if (mbrbt.equals("08") || mbrbt.equals("24") || mbrbt.equals("26")
					|| mbrbt.equals("27") || mbrbt.equals("68")
					|| mbrbt.equals("93") || mbrbt.equals("97"))
				retVal = true;
		}
		if (ohdbt.equals("26")) {
			if (mbrbt.equals("25"))
				retVal = true;
		}
		if (ohdbt.equals("27")) {
			if (mbrbt.equals("25") || mbrbt.equals("93"))
				retVal = true;
		}
		if (ohdbt.equals("29")) {
			if (mbrbt.equals("08") || mbrbt.equals("09"))
				retVal = true;
		}
		if (ohdbt.equals("50")) {
			if (mbrbt.equals("10"))
				retVal = true;
		}
		if (ohdbt.equals("54")) {
			if (mbrbt.equals("60"))
				retVal = true;
		}
		if (ohdbt.equals("55")) {
			if (mbrbt.equals("10"))
				retVal = true;
		}
		if (ohdbt.equals("59")) {
			if (mbrbt.equals("06") || mbrbt.equals("07"))
				retVal = true;
		}
		if (ohdbt.equals("64")) {
			if (mbrbt.equals("08") || mbrbt.equals("09"))
				retVal = true;
		}
		if (ohdbt.equals("65")) {
			if (mbrbt.equals("97"))
				retVal = true;
		}
		if (ohdbt.equals("68")) {
			if (mbrbt.equals("25"))
				retVal = true;
		}
		if (ohdbt.equals("69")) {
			if (mbrbt.equals("99"))
				retVal = true;
		}
		if (ohdbt.equals("71")) {
			if (mbrbt.equals("72"))
				retVal = true;
		}
		if (ohdbt.equals("72")) {
			if (mbrbt.equals("71"))
				retVal = true;
		}
		if (ohdbt.equals("80")) {
			if (mbrbt.equals("10") || mbrbt.equals("81") || mbrbt.equals("82"))
				retVal = true;
		}
		if (ohdbt.equals("81")) {
			if (mbrbt.equals("10") || mbrbt.equals("80") || mbrbt.equals("82"))
				retVal = true;
		}
		if (ohdbt.equals("82")) {
			if (mbrbt.equals("10") || mbrbt.equals("81") || mbrbt.equals("80")
					|| mbrbt.equals("84"))
				retVal = true;
		}
		if (ohdbt.equals("83")) {
			if (mbrbt.equals("10"))
				retVal = true;
		}
		if (ohdbt.equals("84")) {
			if (mbrbt.equals("10") || mbrbt.equals("82"))
				retVal = true;
		}
		if (ohdbt.equals("91")) {
			if (mbrbt.equals("99"))
				retVal = true;
		}
		if (ohdbt.equals("92")) {
			if (mbrbt.equals("10"))
				retVal = true;
		}
		if (ohdbt.equals("93")) {
			if (mbrbt.equals("9") || mbrbt.equals("25") || mbrbt.equals("27"))
				retVal = true;
		}
		if (ohdbt.equals("94")) {
			if (mbrbt.equals("95"))
				retVal = true;
		}
		if (ohdbt.equals("95")) {
			if (mbrbt.equals("94"))
				retVal = true;
		}
		if (ohdbt.equals("97")) {
			if (mbrbt.equals("65") || mbrbt.equals("25"))
				retVal = true;
		}
		if (ohdbt.equals("99")) {
			if (mbrbt.equals("69") || mbrbt.equals("91"))
				retVal = true;
		}

		return retVal;
	}

}
