package com.bagnet.nettracer.tracing.db;

import java.util.Date;

/**
 * 
 * @author matt
 *
 * @hibernate.class table="worldtracer_actionfiles"
 */
public class Worldtracer_Actionfiles {
	private int id;
	private String action_file_type;
	private String action_file_text;
	private int day;
	private String station;
	private String airline;
	private String wt_incident_id;
	private String wt_ohd_id;
	
	/**
	 * @return the id
	 * 
	 * @hibernate.id generator-class="native" type="integer" column="id"
	 *               unsaved-value="0"
	 * 
	 * @hibernate.generator-param name="sequence" value="worldtracer_actionfiles_0"
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the action_file_type
	 * 
	 * @hibernate.property type="string"
	 */
	public String getAction_file_type() {
		return action_file_type;
	}
	/**
	 * @param action_file_type the action_file_type to set
	 */
	public void setAction_file_type(String action_file_type) {
		this.action_file_type = action_file_type;
	}
	/**
	 * @return the action_file_text
	 * 
	 * @hibernate.property type="string"
	 */
	public String getAction_file_text() {
		return action_file_text;
	}
	/**
	 * @param action_file_text the action_file_text to set
	 */
	public void setAction_file_text(String action_file_text) {
		this.action_file_text = action_file_text;
	}

	/**
	 * @return the station
	 * 
	 * @hibernate.property type="string"
	 */
	public String getStation() {
		return station;
	}
	/**
	 * @param station the station to set
	 */
	public void setStation(String station) {
		this.station = station;
	}
	/**
	 * @return the airline
	 * 
	 * @hibernate.property type="string"
	 */
	public String getAirline() {
		return airline;
	}
	/**
	 * @param airline the airline to set
	 */
	public void setAirline(String airline) {
		this.airline = airline;
	}
	/**
	 * @return the day
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getDay() {
		return day;
	}
	/**
	 * @param day the day to set
	 */
	public void setDay(int day) {
		this.day = day;
	}
	/**
	 * @return the wt_incident_id
	 * 
	 * @hibernate.property type="string"
	 */
	public String getWt_incident_id() {
		return wt_incident_id;
	}
	/**
	 * @param wt_incident_id the wt_incident_id to set
	 */
	public void setWt_incident_id(String wt_incident_id) {
		this.wt_incident_id = wt_incident_id;
	}
	/**
	 * @return the wt_ohd_id
	 * 
	 * @hibernate.property type="string"
	 */
	public String getWt_ohd_id() {
		return wt_ohd_id;
	}
	/**
	 * @param wt_ohd_id the wt_ohd_id to set
	 */
	public void setWt_ohd_id(String wt_ohd_id) {
		this.wt_ohd_id = wt_ohd_id;
	}
	
	
}
