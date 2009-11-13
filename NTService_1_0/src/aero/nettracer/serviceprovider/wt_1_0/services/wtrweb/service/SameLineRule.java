package aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import aero.nettracer.serviceprovider.common.utils.StringUtils;
import aero.nettracer.serviceprovider.wt_1_0.services.DefaultWorldTracerService;
import aero.nettracer.serviceprovider.wt_1_0.services.WorldTracerException;
import aero.nettracer.serviceprovider.wt_1_0.services.DefaultWorldTracerService.WorldTracerField;

public class SameLineRule extends BasicRule {
	
	public SameLineRule() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SameLineRule(int minLength, int maxLength, int maxAllowed, Format format) {
		super(minLength, maxLength, maxAllowed, format);
	}

	@Override
	public String getFieldString(WorldTracerField field, List<String> resultSet) throws WorldTracerException {	
		if (resultSet == null || resultSet.size() == 0) {
			if(this.isDoDelete()) {
				return field.name();
			}
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
