package com.bagnet.nettracer.ws.core.pojo;

import java.util.Calendar;

public class WS_ClaimCheck {

	private WS_ScanPoints[] scans = null;
	private String tag;

	public WS_ScanPoints[] getScans() {
		return scans;
	}
	
	public void setScans(WS_ScanPoints[] scans) {
		this.scans = scans;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
}
