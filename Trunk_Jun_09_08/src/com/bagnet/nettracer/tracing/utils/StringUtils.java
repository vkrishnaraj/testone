/*
 * Created on Dec 9, 2004
 *
 * Administrator
 */
package com.bagnet.nettracer.tracing.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Matcher;

import org.apache.log4j.Logger;

import aero.nettracer.fs.model.Person;

import com.bagnet.nettracer.tracing.constant.TracingConstants;

/**
 * @author Administrator
 * 
 * create date - Dec 9, 2004
 */
public class StringUtils {
	
	private static final Logger logger = Logger.getLogger(StringUtils.class);

	//DO NOT CHANGE SALT VALUE
	private static final String salt = "qze5791K";
	
	public static String removePronouns(String s) {
		if (s == null) return "";
		ArrayList ar = new ArrayList();
		ar.add(" a ");
		ar.add(" an ");
		ar.add(" the ");
		ar.add(" that ");
		ar.add(" and ");
		ar.add(" none ");
		ArrayList headar = new ArrayList();
		headar.add("a ");
		headar.add("an ");
		headar.add("the ");
		headar.add("that ");
		headar.add("and ");
		headar.add("none ");
		ArrayList endar = new ArrayList();
		endar.add(" a");
		endar.add(" an");
		endar.add(" the");
		endar.add(" that");
		endar.add(" and");
		endar.add(" none");
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
	
    public static <T extends Appendable> T join(Iterable<? extends CharSequence> src, CharSequence pattern, T dst) throws IOException {
        Iterator<? extends CharSequence> it = src.iterator();
        if (it.hasNext()) {
            dst.append(it.next());
        }
        while (it.hasNext()) {
            dst.append(pattern).append(it.next());
        }
        return dst;
    }
    
    
    public static String join(Iterable<? extends CharSequence> src, CharSequence pattern) {
        try {
            return join(src, pattern, new StringBuilder()).toString();
        } catch (IOException excpt) {
            throw new Error("StringBuilder should not throw IOExceptions!");
        }
    }
    
    public static String join (String pattern, String... strings) {
    	StringBuffer sb = new StringBuffer();
    	for (int i = 0; i< strings.length; ++i) {
    		if (strings[i] != null) {
	    		sb.append(strings[i].trim());
	    		if (i < strings.length - 1) {
	    			sb.append(pattern);
	    		}
    		}
    	}
    	return sb.toString();
    }
    
    
    public static <T extends Appendable> T joinIntegers(Iterable<? extends Number> src, CharSequence pattern, T dst) throws IOException {
      Iterator<? extends Number> it = src.iterator();
      if (it.hasNext()) {
          dst.append(it.next().toString());
      }
      while (it.hasNext()) {
          dst.append(pattern).append(it.next().toString());
      }
      return dst;
  }
  
  
  public static String joinIntegers(Iterable<? extends Number> src, CharSequence pattern) {
      try {
          return joinIntegers(src, pattern, new StringBuilder()).toString();
      } catch (IOException excpt) {
          throw new Error("StringBuilder should not throw IOExceptions!");
      }
  }
  
  public static ArrayList<String> splitOnWordBreak(String srcWord, int maxLength) {
  	ArrayList<String> list = new ArrayList<String>();
  	maxLength +=1;
  	int divide = 0;
  	int endIndex = 0;
  	for (int i=0; i<srcWord.length();) {
  		endIndex = java.lang.Math.min(i + maxLength, srcWord.length());
  		divide = getIndexToDivide(srcWord.trim().substring(i, endIndex), " ", maxLength - 1);
  		list.add(srcWord.trim().substring(i, i+divide).trim());
  		i += divide;
  	}
  	
  	return list;
  }
  
  private static int getIndexToDivide(String srcStr, String delimiter, int maxLength) {
//  	if (srcStr.length() < maxLength) {
//  		return srcStr.length();
//  	}
//  	int index = srcStr.lastIndexOf(delimiter);
//  	if (index > 0) {
//  		return index;
//  	} else {
//  		return srcStr.length() - 1;
//  	}
	  return getIndexToDivide(srcStr, delimiter, maxLength, false, 0);
  }
  
  public static void appendIfNotNull(StringBuilder sb, String x) {
  	if (x!= null && x.trim().length() > 0) {
  		sb.append(x + " ");
  	}
  }
    
  public static int[] convertStringArray2IntArray(String[] sArray) throws Exception {
	  int result[] = null;
	  if (sArray != null) {
		  result = new int[sArray.length];
		  for (int i = 0; i < sArray.length; i++) {
			  result[i] = Integer.parseInt(sArray[i]);
		  }
	  }
	  
	  return result;
  }
  
  public static List<Integer> convertStringArrayList2IntegerArrayList(List<String> sArrayList) throws Exception {
	  List<Integer> result = null;
	  if (sArrayList != null) {
		  result = new ArrayList<Integer>(sArrayList.size());
		  for (String myStr: sArrayList) {
			  result.add(Integer.parseInt(myStr));
		  }
	  }
	  
	  return result;
  }
  
  public boolean isNotNullAndEmpty(String str) {
	  
    if((null != str) && (str.length() == 0)) {
        return true;
    }
    else {
        return false;
    }
  }
  
  public static ArrayList<String> divideUpBigString(String srcWord, int maxLength, String delimiter) {
	  	ArrayList<String> list = new ArrayList<String>();
	  	
	  	maxLength +=1;
	  		
	  	int divide = 0;
	  	int endIndex = 0;
	  	for (int i=0; i<srcWord.length();) {
	  		endIndex = java.lang.Math.min(i + maxLength, srcWord.length());
	  		
	  		divide = getIndexToDivide(srcWord.trim().substring(i, endIndex), delimiter, maxLength - 1, true, 88);
	  		list.add(srcWord.trim().substring(i, i+divide).trim());
	  		i += divide;
	  	}
	  	
	  	return list;
  }
  
  private static int getIndexToDivide(String srcStr, String delimiter, int maxLength, boolean useSpace, int minimumPercentage) {
	  	if (srcStr.length() < maxLength) {
	  		return srcStr.length();
	  	}
	  	int myMinimum = maxLength * minimumPercentage / 100;
	  	
	  	int index = srcStr.lastIndexOf(delimiter);
	  	if (index > myMinimum) {
	  		return index;
	  	} else {
	  		if (useSpace) {
	  			index = srcStr.lastIndexOf(" ");
	  			if (index > myMinimum) {
	  				return index;
	  			}
	  			
	  		}
	  		return srcStr.length() - 1;
	  	}
  }
  
  public static String convertToRegexSafe(String str) {
		if (str == null) {
			return null;
		}
		
		String[] regexChars = {".", "{", "}", "[", "]", "&", "|", "^", "?", "*", "+", "(", ")"};
		str = Matcher.quoteReplacement(str);
		for (int i = 0; i < regexChars.length; ++i) {
			if (str.contains(regexChars[i])) {
				str = str.replace(regexChars[i], ("\\" + regexChars[i]));
			}
		}
		return str;
	}
  
  private static String getHex( byte [] raw ) {
	  String HEXES = "0123456789ABCDEF";
	  if ( raw == null ) {
		  return null;
	  }
	  final StringBuilder hex = new StringBuilder( 2 * raw.length );
	  for ( final byte b : raw ) {
		  hex.append(HEXES.charAt((b & 0xF0) >> 4))
		  .append(HEXES.charAt((b & 0x0F)));
	  }
	  return hex.toString();
  }
  
  public static String sha1(String pass, boolean useSalt){
	  try{
		  if(useSalt){
			  pass = salt + pass;
		  }
		  MessageDigest md = MessageDigest.getInstance("SHA-1");
		  byte [] h = md.digest(pass.getBytes());
		  return getHex(h);
	  } catch (Exception e){
			e.printStackTrace();
	  }
	  return null;
  }
  
  public static String sha1(String pass){
	  return sha1(pass, false);
  }
  
  public static String sha1_256(String pass, boolean useSalt){
	  if(useSalt){
		  pass = salt + pass;
	  }
	  pass = sha1(pass);
	  return sha256(pass);
  }
  
  public static String sha1_256(String pass){
	  return sha1_256(pass, false);
  }
  
  public static String sha256(String pass, boolean useSalt){
	  try{
		  if(useSalt){
			  pass = salt + pass;
		  }
		  MessageDigest md = MessageDigest.getInstance("SHA-256");
		  byte [] h = md.digest(pass.getBytes());
		  return getHex(h);
	  } catch (Exception e){
			e.printStackTrace();
	  }
	  return null;
  }
  
  public static String sha256(String pass){
	  return sha256(pass, false);
  }

  public static String keyGen(){  
	  Date d = new Date();
	  SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
	  df.setTimeZone(TimeZone.getTimeZone("GMT"));
	  Random ran = new Random();
	  int i = ran.nextInt(Integer.MAX_VALUE);
	  return df.format(d) + String.format("%8s", Integer.toHexString(i)).replace(' ', '0').toUpperCase();
  }
  
  public static String hmacHash(String key, String pass){
	  try{
		  return sha1(key + sha1(pass));
	  } catch (Exception e){
			e.printStackTrace();
	  }
	  return null;
  }
  
  public static long getLong(String longString){
		try {
			return Long.parseLong(longString);

		} catch (NumberFormatException nfe) {
			logger.error(nfe.getMessage());
			return -1;
		}
  }

	public static String sanitizeForSingleQuoteJavascript(String toSanitize) {
		if (toSanitize != null) {
			return toSanitize.replace("'", "\\\\'").replace("/", "\\\\/");
		}
		return "";
	}

	public static String sanitizeForDoubleQuoteJavascript(String toSanitize) {
		if (toSanitize != null) {
			return toSanitize.replace("\"", "\\\\\"").replace("/", "\\\\/");
		}
		return "";
	}
  
}