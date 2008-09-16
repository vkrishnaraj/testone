package com.bagnet.nettracer.wt.svc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.bagnet.nettracer.tracing.utils.StringUtils;
import com.bagnet.nettracer.wt.svc.WorldTracerRule.Format;
import com.bagnet.nettracer.wt.svc.WorldTracerService.WorldTracerField;

public class ContentRule extends BasicRule {
	
	public ContentRule() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ContentRule(int minLength, int maxLength, int maxAllowed, Format format) {
		super(minLength, maxLength, maxAllowed, format);
		// TODO Auto-generated constructor stub
	}

	private static final int CATEGORY_LENGTH = 12;
	private static final int CONTENT_LENGTH = 45;
	private static final Format CONTENT_FORMAT = Format.FREE_FLOW;
	private static final int MAX_CATEGORIES = 10;

	private static String formatField(String field, int maxLength, String replaceChar) {
		if(field == null) return null;
		
		//replace bad characters
		String result = field.replaceAll(CONTENT_FORMAT.replaceChars(), replaceChar);
		
		//replace space groups with one space
		result = result.trim().replaceAll("\\s+", " ");

		if(result.length() < 1) {
			return null;
		}
		if(result.length() > maxLength) {
			return result.substring(0, maxLength);
		}
		return result;
	}

	public static String buildEntry(Map<String, List<String>> categories, int bagNum) {
		List<String> temp1 = new ArrayList<String>();
		for(Map.Entry<String, List<String>>	cat : categories.entrySet()) {
			String category = formatField(cat.getKey(), CATEGORY_LENGTH, "");
			String contents = formatField(StringUtils.join(cat.getValue(), DefaultWorldTracerService.ENTRY_SEP), CONTENT_LENGTH, " ");
			if(category != null && contents != null) {
				temp1.add(String.format(category + "/" + contents));
				if(temp1.size() >= MAX_CATEGORIES) break;
			}
		}
		if(temp1.size() > 0) {
			return String.format("%02d %s", bagNum, StringUtils.join(temp1, DefaultWorldTracerService.FIELD_SEP + DefaultWorldTracerService.CONTINUATION + " "));
		}
		return null;
	}
}
