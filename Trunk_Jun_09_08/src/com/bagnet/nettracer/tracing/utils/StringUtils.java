/*
 * Created on Dec 9, 2004
 *
 * Administrator
 */
package com.bagnet.nettracer.tracing.utils;

import java.util.ArrayList;

import com.bagnet.nettracer.tracing.constant.TracingConstants;

/**
 * @author Administrator
 * 
 * create date - Dec 9, 2004
 */
public class StringUtils {

	public static String removePronouns(String s) {
		if (s == null) return "";
		ArrayList ar = new ArrayList();
		ar.add(" a ");
		ar.add(" an ");
		ar.add(" the ");
		ar.add(" that ");
		ar.add(" and ");
		ArrayList headar = new ArrayList();
		headar.add("a ");
		headar.add("an ");
		headar.add("the ");
		headar.add("that ");
		headar.add("and ");
		ArrayList endar = new ArrayList();
		endar.add(" a");
		endar.add(" an");
		endar.add(" the");
		endar.add(" that");
		endar.add(" and");
		String temps = null;
		int index = 0;
		try {
			for (int i = 0; i < ar.size(); i++) {
				temps = (String) ar.get(i);
				while ((index = s.indexOf(temps)) >= 0) {
					s = s.substring(0, index) + " " + s.substring(index + temps.length());
				}
			}
			// head
			for (int i = 0; i < headar.size(); i++) {
				temps = (String) headar.get(i);
				if ((index = s.indexOf(temps)) == 0) s = s.substring(index + temps.length());
			}
			// end
			for (int i = 0; i < endar.size(); i++) {
				temps = (String) endar.get(i);
				if ((index = s.indexOf(temps)) == (s.length() - temps.length()) && index >= 0) {
					s = s.substring(0, s.length() - temps.length());
				}
			}
		} catch (Exception e) {
			System.out.println("s1: " + s + e);
		}
		return s;
	}

	public static String removeNonNumeric(String s) {
		if (s == null) return s;
		StringBuffer sb = new StringBuffer(100);
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (Character.isDigit(c)) {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static String fillzero(String o) {

		String currval = o.trim();

		if (currval != null && currval.length() > 0) {

			// get how many zeros should be inserted
			int len = TracingConstants.INCIDENT_LEN - currval.length();
			if (len <= 0) return currval;
			String zeros = "";
			for (int i = 0; i < len; i++) {
				zeros += "0";
			}

			// find out where alpha ends
			int pos = -1;
			for (int i = 0; i < currval.length(); i++) {

				char c = currval.charAt(i);
				if (c == '%') return currval;

				if (!Character.isLetter(c)) {
					if (i - 1 <= 0)
						pos = 0;
					else
						pos = i;
					i = currval.length();
				}
			}

			if (pos > 0) currval = currval.substring(0, pos) + zeros + currval.substring(pos);
		}
		return currval;
	}

	public static boolean isAlphanumeric(String s) {
		final char[] chars = s.toCharArray();
		for (int x = 0; x < chars.length; x++) {
			final char c = chars[x];
			if ((c >= 'a') && (c <= 'z')) continue; // lowercase
			if ((c >= 'A') && (c <= 'Z')) continue; // uppercase
			if ((c >= '0') && (c <= '9')) continue; // numeric
			return false;
		}
		return true;
	}


	/**
	 * string manipulation for worldtracer giving a string, and a start string,
	 * find the string following the start string to a length
	 * 
	 * @param haystack
	 * @param start
	 * @param len
	 * @return
	 */
	public static String ParseWTString(String haystack, String start, int len) {
		String result = null;
		try {

			int loc = haystack.indexOf(start);
			if (loc > 0) {
				result = haystack.substring(loc + start.length(), loc + start.length() + len);
			}
			if (result != null) return result.trim();
		} catch (Exception e) {
			return null;
		}
		return result;
	}

	/**
	 * string manipulation for worldtracer giving a string, and a start and end
	 * string, find the string following the start string to the end string
	 * 
	 * @param haystack
	 * @param start
	 * @param len
	 * @return
	 */
	public static String ParseWTString2(String haystack, String start, String end) {
		String result = null;
		if (start == null) {
			int loc2 = haystack.indexOf(end);
			result = haystack.substring(0, loc2);
		} else {
			int loc = haystack.indexOf(start);
			if (loc >= 0) {
				if (end == null)
					result = haystack.substring(loc + start.length());
				else {
					int loc2 = haystack.indexOf(end, loc + 1);
					if (loc2 > loc) result = haystack.substring(loc + start.length(), loc2);
				}
			}
		}

		if (result != null) return result.trim();

		return result;
	}

}