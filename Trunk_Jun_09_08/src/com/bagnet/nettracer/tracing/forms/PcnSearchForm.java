package com.bagnet.nettracer.tracing.forms;

import org.apache.struts.action.ActionForm;

public class PcnSearchForm extends ActionForm {
	private int status_ID;
	private int destinationStation;
	private String missedFlightDate;
	private String missedFlightNumber;
	private String missedFlightAirline;
	private String select;
	private String printerAddress;

	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}

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

	public String getPrinterAddress() {
		return printerAddress;
	}

	public void setPrinterAddress(String printerAddress) {
		this.printerAddress = printerAddress;
	}
}
