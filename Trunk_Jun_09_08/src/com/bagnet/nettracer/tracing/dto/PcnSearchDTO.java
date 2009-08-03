package com.bagnet.nettracer.tracing.dto;

public class PcnSearchDTO {
	private int status_ID;
	private int destinationStation;
	private String missedFlightDate;
	private String missedFlightNumber;
	private String missedFlightAirline;

	public int getStatus_ID() {
		return status_ID;
	}

	public void setStatus_ID(int status_ID) {
		this.status_ID = status_ID;
	}

	public int getDestinationStation() {
		return destinationStation;
	}

	public void setDestinationStation(int destinationStation) {
		this.destinationStation = destinationStation;
	}

	public String getMissedFlightDate() {
		return missedFlightDate;
	}

	public void setMissedFlightDate(String missedFlightDate) {
		this.missedFlightDate = missedFlightDate;
	}

	public String getMissedFlightNumber() {
		return missedFlightNumber;
	}

	public void setMissedFlightNumber(String missedFlightNumber) {
		this.missedFlightNumber = missedFlightNumber;
	}

	public String getMissedFlightAirline() {
		return missedFlightAirline;
	}

	public void setMissedFlightAirline(String missedFlightAirline) {
		this.missedFlightAirline = missedFlightAirline;
	}
}
