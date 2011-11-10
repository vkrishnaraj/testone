package com.bagnet.nettracer.reporting;

import java.text.DecimalFormat;

public class LFManagementSummaryRow {
	
	private String station;
	private int reportedLost;
	private int foundItems;
	private int matchedAndReturned;
	private int salvaged;
	private int notMatchedReturned;
	
	public String getStation() {
		return station;
	}
	
	public void setStation(String station) {
		this.station = station;
	}
	
	public int getReportedLost() {
		return reportedLost;
	}
	
	public void setReportedLost(int reportedLost) {
		this.reportedLost = reportedLost;
	}
	
	public void addReportedLost(int lost) {
		this.reportedLost += lost;
	}
	
	public int getFoundItems() {
		return foundItems;
	}
	
	public void setFoundItems(int foundItems) {
		this.foundItems = foundItems;
	}
	
	public void addFoundItems(int found) {
		this.foundItems += found;
	}
	
	public int getMatchedAndReturned() {
		return matchedAndReturned;
	}
	
	public void setMatchedAndReturned(int matchedAndReturned) {
		this.matchedAndReturned = matchedAndReturned;
	}
	
	public void addMatchedAndReturned(int mar) {
		this.matchedAndReturned += mar;
	}
	
	public int getSalvaged() {
		return salvaged;
	}
	
	public void setSalvaged(int salvaged) {
		this.salvaged = salvaged;
	}
	
	public void addSalvaged(int salvaged) {
		this.salvaged += salvaged;
	}
	
	public int getNotMatchedReturned() {
		return notMatchedReturned;
	}
	
	public void setNotMatchedReturned(int notMatchedReturned) {
		this.notMatchedReturned = notMatchedReturned;
	}
	
	public void addNotMatchedReturned(int nmr) {
		this.notMatchedReturned += nmr;
	}
	
	public String getPercentMatchedAndReturned() {
		if (foundItems > 0) {
			DecimalFormat df = new DecimalFormat("#0.00");
			Double mar = new Double(getMatchedAndReturned());
			Double fi = new Double(getFoundItems());
			
			return df.format((mar / fi) * 100) + "%";
		} 
		return "0.00%";
	}

	public String getItemReturnRate() {
		if (foundItems > 0) {
			DecimalFormat df = new DecimalFormat("#0.00");
			Double mar = new Double(getMatchedAndReturned());
			Double nmr = new Double(getNotMatchedReturned());
			Double fi = new Double(getFoundItems());

			return df.format(((mar + nmr) / fi) * 100) + "%";
		} 
		return "0.00%";
	}
	

}
