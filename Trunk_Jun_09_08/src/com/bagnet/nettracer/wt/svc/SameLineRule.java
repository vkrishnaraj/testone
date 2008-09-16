package com.bagnet.nettracer.wt.svc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.bagnet.nettracer.tracing.utils.StringUtils;
import com.bagnet.nettracer.wt.svc.WorldTracerService.WorldTracerField;

public class SameLineRule extends BasicRule {
	
	public SameLineRule() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SameLineRule(int minLength, int maxLength, int maxAllowed, Format format) {
		super(minLength, maxLength, maxAllowed, format);
	}

	@Override
	public String getFieldString(WorldTracerField field, List<String> resultSet) {	
		if (resultSet == null || resultSet.size() == 0) {
			return null;
		}
		
		List<String> temp = new ArrayList<String>();

		int totalLength = 0;
		for(Iterator<String> iterator = resultSet.iterator(); iterator.hasNext() && temp.size() < this.getMaxAllowed();) {
			String entry = formatEntry(iterator.next());
			
			if(entry != null && (totalLength + entry.length() < WorldTracerRule.DEFAULT_LENGTH)) {
				temp.add(entry);
				totalLength += entry.length();
			}
		}
		
		return field.name() + StringUtils.join(temp, DefaultWorldTracerService.ENTRY_SEP);
	}

}
