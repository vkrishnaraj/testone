package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Session;

/**
 * 
 * @author matt
 *
 * @hibernate.class table="worldtracer_actionfiles"
 * @hibernate.typedef name="actionFileType" class="org.hibernate.type.EnumType"
 * @hibernate.typedef-param typedef-name="actionFileType" name="enumClass"
 * 			value="com.bagnet.nettracer.tracing.db.Worldtracer_Actionfiles$ActionFileType"
 * @hibernate.typedef-param typedef-name="actionFileType" name="type"
 * 			value="12"
 */
public class Worldtracer_Actionfiles implements Serializable {
	private int id;
	private ActionFileType action_file_type;
	private String seq;
	private String action_file_summary;
	private String action_file_text;
	private int day;
	private String wt_station;
	private String airline;
	private String wt_incident_id;
	private String wt_ohd_id;
	private double percent_match;
	private int item_number;
	private boolean deleted;
	
	private static final String id_pattern = "^\\w{2}-\\w{3}-[A-Z]{2}-[0-9A-Z]{0,3}-\\d+-\\d+$";

	public Worldtracer_Actionfiles() {}
	
	/**
	 * Takes a string in a particular format and parses out the fields to create 
	 * @param type_id
	 * @throws Exception 
	 */
	public Worldtracer_Actionfiles(String type_id) throws Exception {
		
		
		
		if (Pattern.matches(id_pattern, type_id)) {
			String [] strList = type_id.split("-");
			this.airline = strList[0];
			this.wt_station = strList[1];
			this.action_file_type = ActionFileType.valueOf(strList[2]);
			this.seq = strList[3];
			this.day = Integer.parseInt(strList[4]);
			this.item_number = Integer.parseInt(strList[5]);
		}
		else {
			throw new Exception("invalid action file id");
		}
	}
	/**
	 * @return the id
	 * 
	 * @hibernate.id generator-class="native" type="integer" column="id"
	 *               unsaved-value="0"
	 * 
	 * @hibernate.generator-param name="sequence"
	 *                            value="worldtracer_actionfiles_0"
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
	 * @hibernate.property type="actionFileType"
	 */
	public ActionFileType getAction_file_type() {
		return action_file_type;
	}
	/**
	 * @param action_file_type the action_file_type to set
	 */
	public void setAction_file_type(ActionFileType action_file_type) {
		this.action_file_type = action_file_type;
	}
	
	/**
	 * @return the seq
	 * 
	 * @hibernate.property type="string"
	 */	
	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
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
	 * @return the world tracer station
	 * 
	 * @hibernate.property type="string"
	 */
	public String getStation() {
		return wt_station;
	}
	/**
	 * @param station the station to set
	 */
	public void setStation(String station) {
		this.wt_station = station;
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
	/**
	 * @return the percent
	 * 
	 * @hibernate.property type="double"
	 */
	public double getPercent_match() {
		return percent_match;
	}
	public void setPercent_match(double percent) {
		this.percent_match = percent;
	}
	
	/**
	 * @return the percent
	 * 
	 * @hibernate.property type="int"
	 */
	public int getItem_number() {
		return item_number;
	}
	public void setItem_number(int item_number) {
		this.item_number = item_number;
	}


	public String generateId() {
		return String.format("%s-%s-%s-%s-%d-%d", airline, this.wt_station, this.action_file_type.name(), this.seq, this.day, this.item_number);
	}

	/**
	 * @return the percent
	 * 
	 * @hibernate.property type="boolean"
	 * @hibernate.column name="delete_trigger"
	 */
	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	/**
	 * @hibernate.property type="string"
	 * @hibernate.column length="512" name="summary"
	 * @return
	 */
	public String getAction_file_summary() {
		return action_file_summary;
	}

	public void setAction_file_summary(String action_file_summary) {
		this.action_file_summary = action_file_summary;
	}
	
	//these have to be down here or they muss up the xdoclet hibernate mapping generator.
	public static enum ActionFileType {
		FW("fm", "FORWARD_AREA"), AA("am", "ACTION_AREA"), WM("sm", "SYSTEM_MATCH_AREA"), EM("em", "EXTENDED_MATCH_AREA"), SP("sp", "SYSTEM_PROMPT_AREA"), AP("ap", "ADDITIONAL_PROMPT_AREA"), CM("cm", "CLAIMS_MATCH_AREA"), LM("lm", "LOCAL_MESSAGE_AREA"), PR("pr", "RETIRED_AREA"), XX("xx","ADMIN_MESSAGES"), HQ("hq", "MANAGEMENT"), IN("in","INSURANCE");
		
		private String htmlId;
		private String areaId;
		
		private ActionFileType(String htmlId, String areaId) {
			this.htmlId = htmlId;
			this.areaId = areaId;
		}
		
		public String htmlId() {
			return htmlId;
		}
		public String areaId() {
			return areaId;
		}
	};
}
