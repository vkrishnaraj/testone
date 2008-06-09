/*
 * Created on Jul 20, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.List;

/**
 * @author Ankur Gupta
 * 
 * @hibernate.class table="recipient_list"
 */
public class Recipient implements Serializable {

	private int recipient_id;
	private Station station;
	private int station_id = 0;
	private String company_code = "";
	
	private Message message;



	/**
	 * @return Returns the message.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Message"
	 *                        column="message_id"
	 */
	public Message getMessage() {
		return message;
	}
	/**
	 * @param message The message to set.
	 */
	public void setMessage(Message message) {
		this.message = message;
	}
	/**
	 * @return Returns the company_code.
	 */
	public String getCompany_code() {
		return company_code;
	}

	/**
	 * @param company_code
	 *          The company_code to set.
	 */
	public void setCompany_code(String company_code) {
		this.company_code = company_code;
	}

	private List stationList;

	/**
	 * @return Returns the stationList.
	 */
	public List getStationList() {
		return stationList;
	}

	/**
	 * @param stationList
	 *          The stationList to set.
	 */
	public void setStationList(List stationList) {
		this.stationList = stationList;
	}

	/**
	 * @return Returns the station_id.
	 */
	public int getStation_id() {
		return station_id;
	}

	/**
	 * @param station_id
	 *          The station_id to set.
	 */
	public void setStation_id(int station_id) {
		this.station_id = station_id;
	}

	/**
	 * @hibernate.id generator-class="native" type="integer" column="recipient_id"
	 *               unsaved-value="0"
	 * 
	 * @hibernate.generator-param name="sequence" value="recipient_list_0"
	 * 
	 * @return Returns the recipient_id.
	 */
	public int getRecipient_id() {
		return recipient_id;
	}

	/**
	 * @param recipient_id
	 *          The recipient_id to set.
	 */
	public void setRecipient_id(int recipient_id) {
		this.recipient_id = recipient_id;
	}

	/**
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Station"
	 *                        column="station_ID"
	 * @return Returns the station.
	 */
	public Station getStation() {
		return station;
	}

	/**
	 * @param station
	 *          The station to set.
	 */
	public void setStation(Station station) {
		this.station = station;
	}
}