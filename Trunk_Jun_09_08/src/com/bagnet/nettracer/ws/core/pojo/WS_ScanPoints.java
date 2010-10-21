package com.bagnet.nettracer.ws.core.pojo;

import java.util.Calendar;

public class WS_ScanPoints {

	private Calendar timestamp;
	private String location;
	private String type;

	public Calendar getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Calendar timestamp) {
		this.timestamp = timestamp;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
