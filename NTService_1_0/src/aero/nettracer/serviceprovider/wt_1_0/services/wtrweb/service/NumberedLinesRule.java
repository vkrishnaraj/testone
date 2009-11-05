package aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service;

import java.util.ArrayList;
import java.util.List;

import aero.nettracer.serviceprovider.common.utils.StringUtils;
import aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service.WorldTracerService.WorldTracerField;

public class NumberedLinesRule extends BasicRule{

	public NumberedLinesRule() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NumberedLinesRule(int minLength, int maxLength, int maxAllowed, Format format) {
		this(minLength, maxLength, maxAllowed, format, false);
	}
	
	public NumberedLinesRule(int minLength, int maxLength, int maxAllowed, Format format, boolean doDelete) {
		super(minLength, maxLength, maxAllowed, format, doDelete, false);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getFieldString(WorldTracerField field, List<String> resultSet) throws WorldTracerException {
		List<String> workingSet;
		if (resultSet == null || resultSet.size() == 0) {
			workingSet = new ArrayList<String>();
		}
		else if (resultSet.size() > this.getMaxAllowed()) {
			workingSet = resultSet.subList(0, this.getMaxAllowed());
		} else {
			workingSet = resultSet;
		}

		ArrayList<String> temp = new ArrayList<String>();
		int i = 1;
		for (String result : workingSet) {
			temp.add(String.format("%s%02d %s", field.name(), i, formatEntry(result)));
			i++;
		}
		if(this.isDoDelete()) {
			for(; i <= this.getMaxAllowed(); i++) {
				temp.add(String.format("%s%02d", field.name(), i));
			}
		}
		return StringUtils.join(temp, DefaultWorldTracerService.FIELD_SEP);
	}
}
