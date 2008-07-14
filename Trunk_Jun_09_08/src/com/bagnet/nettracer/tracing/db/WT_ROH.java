package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;

/**
 * @author 
 * 
 * @hibernate.class table="wt_roh"
 */
public class WT_ROH implements Serializable{
	
	private int wt_roh_id;
	private String wt_ahl_id;
	private String wt_ohd_id;
	private String fi;
	private String ag;
	private String teletype_address1;
	private String teletype_address2;
	private String teletype_address3;
	private String teletype_address4;
	private String lname;
	private int roh_station_id;
	private Agent roh_agent;
	private int roh_status;
	/**
	 * @return the roh_status
	 * @hibernate.property type="int"
	 */
	public int getRoh_status() {
		return roh_status;
	}
	/**
	 * @param roh_status the roh_status to set
	 */
	public void setRoh_status(int roh_status) {
		this.roh_status = roh_status;
	}
	/**
	 * @return the roh_station_id
	 * @hibernate.property type="int"
	 */
	public int getRoh_station_id() {
		return roh_station_id;
	}
	/**
	 * @param roh_station_id the roh_station_id to set
	 */
	public void setRoh_station_id(int roh_station_id) {
		this.roh_station_id = roh_station_id;
	}
	/**
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Agent"
	 *                        column="roh_agent_id"
	 * @return the roh_agent_id
	 */
	public Agent getRoh_agent_id() {
		return roh_agent;
	}
	/**
	 * @param roh_agent_id the roh_agent_id to set
	 */
	public void setRoh_agent_id(Agent roh_agent) {
		this.roh_agent = roh_agent;
	}
	/**
	 * @hibernate.id generator-class="native" type="integer" column="wt_roh_id"
	 *               unsaved-value="0"
	 * @hibernate.generator-param name="sequence" value="wt_roh_id"
	 * 
	 * 
	 * @return the wt_roh_id
	 */
	public int getWt_roh_id() {
		return wt_roh_id;
	}
	/**
	 * @param wt_roh_id the wt_roh_id to set
	 */
	public void setWt_roh_id(int wt_roh_id) {
		this.wt_roh_id = wt_roh_id;
	}
	/**
	 * @return the wt_ahl_id
	 * @hibernate.property type="string"
	 */
	public String getWt_ahl_id() {
		return wt_ahl_id;
	}
	/**
	 * @param wt_ahl_id the wt_ahl_id to set
	 */
	public void setWt_ahl_id(String wt_ahl_id) {
		this.wt_ahl_id = wt_ahl_id;
	}
	/**
	 * @return the wt_ohd_id
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
	 * @return the fi
	 * @hibernate.property type="string"
	 */
	public String getFi() {
		return fi;
	}
	/**
	 * @param fi the fi to set
	 */
	public void setFi(String fi) {
		this.fi = fi;
	}
	/**
	 * @return the ag
	 * @hibernate.property type="string"
	 */
	public String getAg() {
		return ag;
	}
	/**
	 * @param ag the ag to set
	 */
	public void setAg(String ag) {
		this.ag = ag;
	}
	/**
	 * @return the teletype_address1
	 * @hibernate.property type="string"
	 */
	public String getTeletype_address1() {
		return teletype_address1;
	}
	/**
	 * @param teletype_address1 the teletype_address1 to set
	 */
	public void setTeletype_address1(String teletype_address1) {
		this.teletype_address1 = teletype_address1;
	}
	/**
	 * @return the teletype_address2
	 * @hibernate.property type="string"
	 */
	public String getTeletype_address2() {
		return teletype_address2;
	}
	/**
	 * @param teletype_address2 the teletype_address2 to set
	 */
	public void setTeletype_address2(String teletype_address2) {
		this.teletype_address2 = teletype_address2;
	}
	/**
	 * @return the teletype_address3
	 * @hibernate.property type="string"
	 */
	public String getTeletype_address3() {
		return teletype_address3;
	}
	/**
	 * @param teletype_address3 the teletype_address3 to set
	 */
	public void setTeletype_address3(String teletype_address3) {
		this.teletype_address3 = teletype_address3;
	}
	/**
	 * @return the teletype_address4
	 * @hibernate.property type="string"
	 */
	public String getTeletype_address4() {
		return teletype_address4;
	}
	/**
	 * @param teletype_address4 the teletype_address4 to set
	 */
	public void setTeletype_address4(String teletype_address4) {
		this.teletype_address4 = teletype_address4;
	}
	/**
	 * @return the lname
	 * @hibernate.property type="string"
	 */
	public String getLname() {
		return lname;
	}
	/**
	 * @param lname the lname to set
	 */
	public void setLname(String lname) {
		this.lname = lname;
	}
	/**
	 * @return the roh_agent
	 */
	public Agent getRoh_agent() {
		return roh_agent;
	}
	/**
	 * @param roh_agent the roh_agent to set
	 */
	public void setRoh_agent(Agent roh_agent) {
		this.roh_agent = roh_agent;
	}

}
