package com.bagnet.clients.b6.reports;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PAWOBReportDTO {

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIncident_id() {
		return incident_id;
	}
	public void setIncident_id(String incident_id) {
		this.incident_id = incident_id;
	}
/*	public String getDate_created() {
		return date_created;
	}
	public void setDate_created(String date_created) {
		this.date_created = date_created;
	}
	public String getTime_created() {
		return time_created;
	}
	public void setTime_created(String time_created) {
		this.time_created = time_created;
	}*/
	public String getFlights() {
		return flights;
	}
	public void setFlights(String flights) {
		this.flights = flights;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getCreated_station() {
		return created_station;
	}
	public void setCreated_station(String created_station) {
		this.created_station = created_station;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCharge_city() {
		return charge_city;
	}
	public void setCharge_city(String charge_city) {
		this.charge_city = charge_city;
	}
	public int getCharge_code() {
		return charge_code;
	}
	public void setCharge_code(int charge_code) {
		this.charge_code = charge_code;
	}
	public String getPnr() {
		return pnr;
	}
	public void setPnr(String pnr) {
		this.pnr = pnr;
	}
	public String getBag_tags() {
		return bag_tags;
	}
	public void setBag_tags(String bag_tags) {
		this.bag_tags = bag_tags;
	}
	public String getCheckin_location() {
		return checkin_location;
	}
	public void setCheckin_location(String checkin_location) {
		this.checkin_location = checkin_location;
	}
	public String getBag_colors() {
		return bag_colors;
	}
	public void setBag_colors(String bag_colors) {
		this.bag_colors = bag_colors;
	}
	public String getBag_types() {
		return bag_types;
	}
	public void setBag_types(String bag_types) {
		this.bag_types = bag_types;
	}
/*	public String getFinalSegmentDateTime() {
		return finalSegmentDateTime;
	}
	public void setFinalSegmentDateTime(String finalSegmentDateTime) {
		finalSegmentDateTime = finalSegmentDateTime;
	}*/
	public String getAssigned_station() {
		return assigned_station;
	}
	public void setAssigned_station(String assigned_station) {
		this.assigned_station = assigned_station;
	}
	
	public Date getDate_created() {
		return date_created;
	}
	public void setDate_created(Date date_created) {
		this.date_created = date_created;
	}
	public String getTime_created() {
		return time_created;
	}
	public void setTime_created(String time_created) {
		// wierd from here
		//DateFormat sdformat = new SimpleDateFormat("hh:mm:ss");
		//String strMyTime = sdformat.format(time_created);
		//Time myTime = Time.valueOf(strMyTime);
		this.time_created = time_created;
	}
	public Date getFinalSegmentDateTime() {
		return finalSegmentDateTime;
	}
	public void setFinalSegmentDateTime(Date finalSegmentDateTime) {
		this.finalSegmentDateTime = finalSegmentDateTime;
	}
	
	private String name;
	private String incident_id;
	//private String date_created;
	//private String time_created;
	private Date date_created;
	private String time_created;
	private String flights;
	private String destination;
	private String origin;
	private String created_station;
	private String status;
	private String charge_city;
	private int charge_code;
	private String pnr;
	private String bag_tags;
	private String checkin_location;
	private String bag_colors;
	private String bag_types;
	//private String finalSegmentDateTime;
	private Date finalSegmentDateTime;
	private String assigned_station;

}