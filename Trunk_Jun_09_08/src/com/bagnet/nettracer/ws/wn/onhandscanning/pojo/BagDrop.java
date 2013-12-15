package com.bagnet.nettracer.ws.wn.onhandscanning.pojo;

import java.util.Calendar;

public class BagDrop {
	public String getAirlineCode() {
		return airlineCode;
	}
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}
	public String getArrivalStationCode() {
		return arrivalStationCode;
	}
	public void setArrivalStationCode(String arrivalStationCode) {
		this.arrivalStationCode = arrivalStationCode;
	}
	public Calendar getBagDropDatetime() {
		return bagDropDatetime;
	}
	public void setBagDropDatetime(Calendar bagDropDatetime) {
		this.bagDropDatetime = bagDropDatetime;
	}
	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	public boolean isPreviouslyEnteredFlag() {
		return previouslyEnteredFlag;
	}
	public void setPreviouslyEnteredFlag(boolean previouslyEnteredFlag) {
		this.previouslyEnteredFlag = previouslyEnteredFlag;
	}
	public Calendar getScheduleArrivalDatetime() {
		return scheduleArrivalDatetime;
	}
	public void setScheduleArrivalDatetime(Calendar scheduleArrivalDatetime) {
		this.scheduleArrivalDatetime = scheduleArrivalDatetime;
	}
	
	private String airlineCode;
	private String arrivalStationCode;
	private Calendar bagDropDatetime;
	private String flightNumber;
	private boolean previouslyEnteredFlag;
	private Calendar scheduleArrivalDatetime;
}
