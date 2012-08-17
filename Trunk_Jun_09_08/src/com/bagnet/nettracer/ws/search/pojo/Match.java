package com.bagnet.nettracer.ws.search.pojo;

import java.util.Calendar;

public class Match {
	private String matchingPnr;
	private String[] matchingTagNumber;
	private String incident;
	private String type;
	private Calendar created;
	private String pnr;
	private String[] allTagsInIncident;
	
	public String getMatchingPnr() {
		return matchingPnr;
	}
	public void setMatchingPnr(String matchingPnr) {
		this.matchingPnr = matchingPnr;
	}
	public String[] getMatchingTagNumber() {
		return matchingTagNumber;
	}
	public void setMatchingTagNumber(String[] matchingTagNumber) {
		this.matchingTagNumber = matchingTagNumber;
	}
	public String getIncident() {
		return incident;
	}
	public void setIncident(String incident) {
		this.incident = incident;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Calendar getCreated() {
		return created;
	}
	public void setCreated(Calendar created) {
		this.created = created;
	}
	public String getPnr() {
		return pnr;
	}
	public void setPnr(String pnr) {
		this.pnr = pnr;
	}
	public String[] getAllTagsInIncident() {
		return allTagsInIncident;
	}
	public void setAllTagsInIncident(String[] allTagsInIncident) {
		this.allTagsInIncident = allTagsInIncident;
	}
	
}
