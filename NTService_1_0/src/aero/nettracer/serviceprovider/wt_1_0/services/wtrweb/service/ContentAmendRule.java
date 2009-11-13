package aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service;

import java.util.ArrayList;
import java.util.List;

import aero.nettracer.serviceprovider.common.utils.StringUtils;
import aero.nettracer.serviceprovider.wt_1_0.services.DefaultWorldTracerService;
import aero.nettracer.serviceprovider.wt_1_0.services.WorldTracerException;
import aero.nettracer.serviceprovider.wt_1_0.services.DefaultWorldTracerService.WorldTracerField;

public class ContentAmendRule extends ContentRule {

	private boolean useNums;

	public ContentAmendRule() {
		super();
	}

	public ContentAmendRule(int minLength, int maxLength, int maxAllowed, Format format, boolean useNums) {
		super(minLength, maxLength, maxAllowed, format);
		this.useNums = useNums;
	}

	@Override
	public String getFieldString(WorldTracerField field, List<String> resultSet) throws WorldTracerException {
		List<String> workingSet;
		if (resultSet == null || resultSet.size() == 0) {
			return null;
		}
		if (resultSet.size() > this.getMaxAllowed()) {
			workingSet = resultSet.subList(0, this.getMaxAllowed());
		} else {
			workingSet = resultSet;
		}

		// ok, reformat the strings
		ArrayList<String> temp = new ArrayList<String>();
		for (String result : workingSet) {
			// strip off the number at the frontA
			String num = "";
			String stuff = "";
			if (useNums) {
				num = result.substring(0, 2);
				stuff = result.substring(2, result.length());
			} else {
				stuff = result;
			}

			String[] pieces = stuff.split("\\" + DefaultWorldTracerService.FIELD_SEP + DefaultWorldTracerService.CONTINUATION);
			for (String piece : pieces) {
				//skip continuation lines
				if (!piece.matches("^\\s*/.*$")) {
					temp.add(field.name() + (useNums ? num : "") + formatEntry(piece));
				}
			}
		}
		return StringUtils.join(temp, DefaultWorldTracerService.FIELD_SEP);
	}
}
