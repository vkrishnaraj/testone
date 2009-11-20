package aero.nettracer.serviceprovider.wt_1_0.services.ishares.service;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import aero.nettracer.serviceprovider.wt_1_0.common.ActionFile;
import aero.nettracer.serviceprovider.wt_1_0.common.ActionFileCount;

public class ISharesResponseParser {
	
	private static Pattern p = Pattern.compile("^([A-Z]{2})\\s+(\\d*)\\s+(\\d*)\\s+(\\d*)\\s+(\\d*)\\s+(\\d*)\\s+(\\d*)\\s+(\\d*)\\s+(\\d*)");
	private static final Pattern AHL_PATT = Pattern.compile("(?:\\bAHL\\s+|A/|FILE\\s+)(\\w{5}\\d{5})\\b", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	private static final Pattern OHD_PATT = Pattern.compile("(?:\\bOHD\\s+|O/|ON-HAND\\s+)(\\w{5}\\d{5})\\b", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	private static final Pattern PERCENT_PATT = Pattern.compile("SCORE\\s*-\\s*(\\d+(\\.\\d{1,2})?)");
	
	//private static final Pattern PATT_DXF = Pattern.compile("^(\\d+)/(.*\\n)*(^\\s*$){2,}", Pattern.MULTILINE);
	private static final Pattern PATT_DXF = Pattern.compile("^(\\d+)/(.*\\n)*(^\\s*$){1,}", Pattern.MULTILINE);

	static ActionFileCount[] processActionfileResponse(String responseTxt) {
		String[] list = responseTxt.split("\n");
		ArrayList<ActionFileCount> counts = new ArrayList<ActionFileCount>();

		for (String string : list) {
			Matcher m = p.matcher(string);
			boolean b = m.find();

			if (b) {
				int groupCount = m.groupCount();
				String type = null;
				
				for (int i = 1; i < groupCount; ++i) {
					
					if (i == 1) {
						type = m.group(1);
					} else {
						int num = Integer.parseInt(m.group(i));
						if (num > 0) {
							ActionFileCount ct = new ActionFileCount();
							counts.add(ct);
							ct.setType(type);
							ct.setDay(i - 1);
							ct.setCount(num);
						}
					}
				}
			}
		}
		return counts.toArray(new ActionFileCount[counts.size()]);
	}
	
	static ActionFile processActionFileDetail(String responseTxt) {
		ActionFile result = new ActionFile();
		Matcher matcher = PATT_DXF.matcher(responseTxt);
	    if(matcher.find()) {
	    	int myItemNumber = Integer.parseInt(matcher.group(1));
	    	result.setItemNumber(myItemNumber);
	    	result.setSummary(matcher.group().trim());
	    	result.setAhlId(parseAhlId(responseTxt) + "");
	    	result.setOhdId(parseOhdId(responseTxt) + "");
	    	result.setPercentMatch(parsePercentMatch(responseTxt));
	    }		
		
		return result;
	}
	
	private static double parsePercentMatch(String content) {
		if(content == null) return 0.0D;
		Matcher m = PERCENT_PATT.matcher(content);
		if(m.find()) {
			return Double.parseDouble(m.group(1));
		}
		return 0.0D;
	}
	
	private static String parseAhlId(String content) {
		if(content == null) return null;
		Matcher m = AHL_PATT.matcher(content);
		if(m.find()) {
			return m.group(1);
		}
		return "";
	}

	private static String parseOhdId(String content) {
		if(content == null) return null;
		Matcher m = OHD_PATT.matcher(content);
		if(m.find()) {
			return m.group(1);
		}
		return "";
	}
}
