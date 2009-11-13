package aero.nettracer.serviceprovider.wt_1_0.services.ishares.service;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import aero.nettracer.serviceprovider.wt_1_0.common.ActionFileCount;

public class ISharesResponseParser {
	static ActionFileCount[] processActionfileResponse(String responseTxt) {
		String[] list = responseTxt.split("\n");
		ArrayList<ActionFileCount> counts = new ArrayList<ActionFileCount>();

		Pattern p = Pattern.compile("^([A-Z]{2})\\s+(\\d*)\\s+(\\d*)\\s+(\\d*)\\s+(\\d*)\\s+(\\d*)\\s+(\\d*)\\s+(\\d*)\\s+(\\d*)");

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
}
