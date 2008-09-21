package com.bagnet.nettracer.wt.svc;

import java.util.ArrayList;
import java.util.List;

import com.bagnet.nettracer.tracing.utils.StringUtils;
import com.bagnet.nettracer.wt.WorldTracerException;
import com.bagnet.nettracer.wt.svc.WorldTracerService.WorldTracerField;

public class BasicRule implements WorldTracerRule<String> {

	private static final String DEFAULT_REPLACE_CHAR = " ";
	private int minLength;
	private int maxLength;
	private int maxAllowed;
	private Format format;
	protected String replace_char;
	private boolean doDelete;

	public BasicRule() {
		this(1, 58, 1, Format.FREE_FLOW, false);
	}
	
	public BasicRule(int minLength, int maxLength, int maxAllowed, Format format) {
		this(minLength, maxLength, maxAllowed, format, false);
	}
	
	public BasicRule(int minLength, int maxLength, int maxAllowed, Format format, boolean doDelete) {
		this.minLength = minLength;
		this.maxLength = maxLength;
		this.maxAllowed = maxAllowed;
		this.format = format;
		replace_char = DEFAULT_REPLACE_CHAR;
		this.doDelete = doDelete;
	}

	protected String formatEntry(String entry) throws WorldTracerException {
		if(entry == null) return null;
		
		String result;
		//replace bad characters
		if(format.replaceChars() != null) {
			result = entry.replaceAll(format.replaceChars(), replace_char);
		}
		else
			result = entry;
		
		//replace space groups with one space
		result = result.trim().replaceAll("\\s+", " ");

		if(result.length() < this.getMinLength()) {
			return null;
		}
		if(result.length() > this.getMaxLength()) {
			return result.substring(0, this.getMaxLength()).trim();
		}
		return result;
	}
	
	public int getMinLength() {
		return minLength;
	}

	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}
	
	public int getMaxAllowed() {
		return maxAllowed;
	}

	public String getFieldString(WorldTracerField field, List<String> resultSet) throws WorldTracerException {
		List<String> workingSet;
		if (resultSet == null || resultSet.size() == 0) {
			if(!isDoDelete()) {
				return null;
			}
			workingSet = new ArrayList<String>();
		}
		else if(resultSet.size() > maxAllowed) {
			workingSet = resultSet.subList(0, maxAllowed);
		}
		else {
			workingSet = resultSet;
		}
		
		ArrayList<String> temp = new ArrayList<String>();
		for (String result : workingSet) {
			temp.add(field.name() + formatEntry(result));
		}
		if(this.isDoDelete()) {
			for(int i = 0; i <= this.getMaxAllowed() - workingSet.size(); i++) {
				temp.add(field.name());
			}
		}
		return StringUtils.join(temp, DefaultWorldTracerService.FIELD_SEP);
	}

	public boolean isDoDelete() {
		return doDelete;
	}
}
