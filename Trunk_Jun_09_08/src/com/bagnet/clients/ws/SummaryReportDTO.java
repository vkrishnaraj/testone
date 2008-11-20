package com.bagnet.clients.ws;

import java.util.Date;

public class SummaryReportDTO {
	private String flightDate;
	private String stationCode;
	private int totalPax;
	private int connPax;
	private int totalFlights;
	private int totalPirCount;
	private double totalRatio;
	private int airsidePirCount;
	private double airsideRatio;
	
	public String getFlightDate() {
		return flightDate;
	}
	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}
	public String getStationCode() {
		return stationCode;
	}
	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}
	public int getTotalPax() {
		return totalPax;
	}
	public void setTotalPax(int totalPax) {
		this.totalPax = totalPax;
	}
	public int getConnPax() {
		return connPax;
	}
	public void setConnPax(int connPax) {
		this.connPax = connPax;
	}
	public int getTotalFlights() {
		return totalFlights;
	}
	public void setTotalFlights(int totalFlights) {
		this.totalFlights = totalFlights;
	}
	public int getTotalPirCount() {
		return totalPirCount;
	}
	public void setTotalPirCount(int totalPirCount) {
		this.totalPirCount = totalPirCount;
	}
	public double getTotalRatio() {
		return totalRatio;
	}
	public void setTotalRatio(double totalRatio) {
		this.totalRatio = totalRatio;
	}
	public int getAirsidePirCount() {
		return airsidePirCount;
	}
	public void setAirsidePirCount(int airsidePirCount) {
		this.airsidePirCount = airsidePirCount;
	}
	public double getAirsideRatio() {
		return airsideRatio;
	}
	public void setAirsideRatio(double airsideRatio) {
		this.airsideRatio = airsideRatio;
	}
	
	
	
}
