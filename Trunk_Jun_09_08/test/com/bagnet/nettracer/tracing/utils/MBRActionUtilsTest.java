package com.bagnet.nettracer.tracing.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.junit.Test;

import com.bagnet.nettracer.tracing.constant.TracingConstants;


public class MBRActionUtilsTest {
	@Test
	public void addToMap() {
		HashMap<String, ArrayList<Integer>> hashMap = new HashMap();
		MBRActionUtils.addToStringMap(hashMap, TracingConstants.JSP_DELETE_ITINERARY, "1");
		MBRActionUtils.addToStringMap(hashMap, TracingConstants.JSP_DELETE_ITINERARY, "7");
		MBRActionUtils.addToStringMap(hashMap, TracingConstants.JSP_DELETE_ITINERARY, "3");
		MBRActionUtils.addToStringMap(hashMap, TracingConstants.JSP_DELETE_ITINERARY, "5");
		MBRActionUtils.addToStringMap(hashMap, TracingConstants.JSP_DELETE_ITINERARY, "0");
		
		ArrayList<Integer> list = hashMap.get(TracingConstants.JSP_DELETE_ITINERARY);
		Collections.sort(list, Collections.reverseOrder());
		for (Integer x: list) {
			System.out.println(x);
		}
	} 
}
