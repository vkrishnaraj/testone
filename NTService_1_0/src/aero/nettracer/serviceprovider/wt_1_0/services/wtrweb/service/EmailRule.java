package aero.nettracer.serviceprovider.wt_1_0.services.wtrweb.service;

import aero.nettracer.serviceprovider.wt_1_0.services.WorldTracerException;


public class EmailRule extends BasicRule {

	public EmailRule() {
		super();
		replace_char = "";
	}

	public EmailRule(int minLength, int maxLength, int maxAllowed, Format format) {
		super(minLength, maxLength, maxAllowed, format);
		replace_char = "";
	}
	
	@Override
	public String formatEntry(String entry) throws WorldTracerException {
		String result = entry.replace("@", "/A/").replace(".", "/D/").replace("_", "/U/").replace("~", "/T/").replace("+",
		"/P/");
		return super.formatEntry(entry);
	}

}
