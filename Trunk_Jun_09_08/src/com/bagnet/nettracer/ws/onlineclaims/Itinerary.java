package com.bagnet.nettracer.ws.onlineclaims;

import java.util.Date;

public class Itinerary {
	
//	private long id;
	private String departureCity;
	private String arrivalCity;
	private String airline;
	private String flightNum;
	private Date date;
	
	public String getDepartureCity() {
  	return departureCity;
  }
	public void setDepartureCity(String departureCity) {
  	this.departureCity = departureCity;
  }
	public String getArrivalCity() {
  	return arrivalCity;
  }
	public void setArrivalCity(String arrivalCity) {
  	this.arrivalCity = arrivalCity;
  }
	public String getAirline() {
  	return airline;
  }
	public void setAirline(String airline) {
  	this.airline = airline;
  }
	public String getFlightNum() {
  	return flightNum;
  }
	public void setFlightNum(String flightNum) {
  	this.flightNum = flightNum;
  }
	public Date getDate() {
  	return date;
  }
	public void setDate(Date date) {
  	this.date = date;
  }
//	public long getId() {
//  	return id;
//  }
//	public void setId(long id) {
//  	this.id = id;
//  }
}
