package com.bagnet.nettracer.wt.svc;

import com.bagnet.nettracer.wt.WorldTracerException;

public class EmailRule extends BasicRule {

	public EmailRule() {
		super();
		replace_char = "";
		// TODO Auto-generated constructor stub
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
