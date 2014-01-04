package aero.nettracer.portal.model;

import java.util.Date;

public class Itinerary{

	
	private String airline;
	private String departureCity ;
	private String arrivalCity ;
	private String flightNum ;
	private Date journeyDate;

	private String deptCityFormText;
	private String arrvCityFormText;
	
	public String getAirline() {
		return airline;
	}
	public void setAirline(String airline) {
		this.airline = airline;
	}
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
	public String getFlightNum() {
		return flightNum;
	}
	public void setFlightNum(String flightNum) {
		this.flightNum = flightNum;
	}
	public Date getJourneyDate() {
		return journeyDate;
	}
	public void setJourneyDate(Date journeyDate) {
		this.journeyDate = journeyDate;
	}
	
	public String getDeptCityFormText() {
		return deptCityFormText;
	}
	public void setDeptCityFormText(String deptCityFormText) {
		this.deptCityFormText = deptCityFormText;
	}
	public String getArrvCityFormText() {
		return arrvCityFormText;
	}
	public void setArrvCityFormText(String arrvCityFormText) {
		this.arrvCityFormText = arrvCityFormText;
	}
	
	
	
}
