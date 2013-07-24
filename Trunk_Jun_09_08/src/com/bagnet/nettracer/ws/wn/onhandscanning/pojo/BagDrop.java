package com.bagnet.nettracer.ws.wn.onhandscanning.pojo;

import java.util.Date;

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
	public Date getBagDropDatetime() {
		return bagDropDatetime;
	}
	public void setBagDropDatetime(Date bagDropDatetime) {
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
	public Date getScheduleArrivalDatetime() {
		return scheduleArrivalDatetime;
	}
	public void setScheduleArrivalDatetime(Date scheduleArrivalDatetime) {
		this.scheduleArrivalDatetime = scheduleArrivalDatetime;
	}
	
	private String airlineCode;
	private String arrivalStationCode;
	private Date bagDropDatetime;
	private String flightNumber;
	private boolean previouslyEnteredFlag;
	private Date scheduleArrivalDatetime;
}
