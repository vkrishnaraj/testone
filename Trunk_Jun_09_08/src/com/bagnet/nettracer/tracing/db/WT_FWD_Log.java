/*
 * Created on Aug 9, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.bagnet.nettracer.tracing.utils.AdminUtils;
import com.bagnet.nettracer.tracing.utils.DateUtils;

/**
 * @author Ankur Gupta
 * 
 * @hibernate.class table="wt_fwd_log"
 */
public class WT_FWD_Log implements Serializable {
	public int wt_fwd_log_id;
	public OHD ohd;
	public String place_in_file;
	public int fwd_station_id;
	public String bagtag;
	public String ebagtag;
	public String expeditenum;
	public String passenger1;
	public String passenger2;
	public String passenger3;
	public int loss_code;
	public String reason_for_loss;
	public String fault_station;
	public String fault_terminal;
	public String supplementary_information;
	public String teletype_address1;
	public String teletype_address2;
	public String teletype_address3;
	public String teletype_address4;
	
	public Agent forwarding_agent;
	public Date forward_date;
	public int fwd_status;
	
	private Set itinerary;
	
	
	private String _DATEFORMAT;
	private String _TIMEFORMAT;
	private TimeZone _TIMEZONE;

	public List getItinerarylist() {
		if (itinerary == null || itinerary.size() < 1) return null;

		return new ArrayList(itinerary);
	}


	/**
	 * @return Returns the _DATEFORMAT.
	 */
	public String get_DATEFORMAT() {
		return _DATEFORMAT;
	}

	/**
	 * @param _dateformat
	 *          The _DATEFORMAT to set.
	 */
	public void set_DATEFORMAT(String _dateformat) {
		_DATEFORMAT = _dateformat;
	}

	/**
	 * @return Returns the _TIMEFORMAT.
	 */
	public String get_TIMEFORMAT() {
		return _TIMEFORMAT;
	}

	/**
	 * @param _timeformat
	 *          The _TIMEFORMAT to set.
	 */
	public void set_TIMEFORMAT(String _timeformat) {
		_TIMEFORMAT = _timeformat;
	}

	/**
	 * @return Returns the _TIMEZONE.
	 */
	public TimeZone get_TIMEZONE() {
		return _TIMEZONE;
	}

	/**
	 * @param _timezone
	 *          The _TIMEZONE to set.
	 */
	public void set_TIMEZONE(TimeZone _timezone) {
		_TIMEZONE = _timezone;
	}


	public String getDispForwardTime() {
		return DateUtils.formatDate(this.getForward_date(), _DATEFORMAT + " " + _TIMEFORMAT, null,
				_TIMEZONE);
	}

	/**
	 * @hibernate.set cascade="all"
	 *                order-by="departdate"
	 * @hibernate.key column="wt_fwd_log_ID"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.WT_FWD_Log_Itinerary"
	 * 
	 * @return Returns the itinerary.
	 */
	public Set getItinerary() {
		return itinerary;
	}

	/**
	 * @param itinerary
	 *          The itinerary to set.
	 */
	public void setItinerary(Set itinerary) {
		this.itinerary = itinerary;
	}


	/**
	 * @hibernate.property type="string"
	 * @return Returns the expeditenum.
	 */
	public String getExpeditenum() {
		return expeditenum;
	}

	/**
	 * @param expeditenum
	 *          The expeditenum to set.
	 */
	public void setExpeditenum(String expeditenum) {
		this.expeditenum = expeditenum;
	}



	/**
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.Agent"
	 *                        column="fwd_agent_id" foreign-key="fwd_agent_id"
	 * @return Returns the forwarding_agent.
	 */
	public Agent getForwarding_agent() {
		return forwarding_agent;
	}

	/**
	 * @param forwarding_agent
	 *          The forwarding_agent to set.
	 */
	public void setForwarding_agent(Agent forwarding_agent) {
		this.forwarding_agent = forwarding_agent;
	}


	/**
	 * @hibernate.id generator-class="native" type="integer" column="wt_fwd_log_id"
	 *               unsaved-value="0"
	 * @hibernate.generator-param name="sequence" value="wt_fwd_log_0"
	 * 
	 * 
	 * @return Returns the oHDLog_ID.
	 */	
	public int getWt_fwd_log_id() {
		return wt_fwd_log_id;
	}


	/**
	 * @param wt_fwd_log_id the wt_fwd_log_id to set
	 */
	public void setWt_fwd_log_id(int wt_fwd_log_id) {
		this.wt_fwd_log_id = wt_fwd_log_id;
	}


	/**
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.OHD"
	 *                        column="OHD_ID" not-null="true"
	 * @return
	 */
	public OHD getOhd() {
		return ohd;
	}

	public void setOhd(OHD ohd) {
		this.ohd = ohd;
	}


	/**
	 * @return the fwd_station_id
	 * 
	 * @hibernate.property type="int"
	 */
	public int getFwd_station_id() {
		return fwd_station_id;
	}


	/**
	 * @param fwd_station_id the fwd_station_id to set
	 */
	public void setFwd_station_id(int fwd_station_id) {
		this.fwd_station_id = fwd_station_id;
	}


	/**
	 * @return the bagtag
	 * @hibernate.property type="string"
	 */
	public String getBagtag() {
		return bagtag;
	}


	/**
	 * @param bagtag the bagtag to set
	 */
	public void setBagtag(String bagtag) {
		this.bagtag = bagtag;
	}


	/**
	 * @return the loss_code
	 * @hibernate.property type="int"
	 */
	public int getLoss_code() {
		return loss_code;
	}


	/**
	 * @param loss_code the loss_code to set
	 */
	public void setLoss_code(int loss_code) {
		this.loss_code = loss_code;
	}


	/**
	 * @return the reason_for_loss
	 * @hibernate.property type="string"
	 */
	public String getReason_for_loss() {
		return reason_for_loss;
	}


	/**
	 * @param reason_for_los the reason_for_los to set
	 */
	public void setReason_for_loss(String reason_for_loss) {
		this.reason_for_loss = reason_for_loss;
	}


	/**
	 * @return the fault_station
	 * @hibernate.property type="string"
	 */
	public String getFault_station() {
		return fault_station;
	}


	/**
	 * @param fault_station the fault_station to set
	 */
	public void setFault_station(String fault_station) {
		this.fault_station = fault_station;
	}


	/**
	 * @return the fault_terminal
	 * @hibernate.property type="string"
	 */
	public String getFault_terminal() {
		return fault_terminal;
	}


	/**
	 * @param fault_terminal the fault_terminal to set
	 */
	public void setFault_terminal(String fault_terminal) {
		this.fault_terminal = fault_terminal;
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
	 * @return the forward_date
	 * @hibernate.property type="timestamp"
	 */
	public Date getForward_date() {
		return forward_date;
	}


	/**
	 * @param forward_date the forward_date to set
	 */
	public void setForward_date(Date forward_date) {
		this.forward_date = forward_date;
	}


	/**
	 * @return the fwd_status
	 * @hibernate.property type="int"
	 */
	public int getFwd_status() {
		return fwd_status;
	}


	/**
	 * @param fwd_status the fwd_status to set
	 */
	public void setFwd_status(int fwd_status) {
		this.fwd_status = fwd_status;
	}


	/**
	 * @return the place_in_file
	 * @hibernate.property type="string"
	 * 
	 */
	public String getPlace_in_file() {
		return place_in_file;
	}


	/**
	 * @param place_in_file the place_in_file to set
	 */
	public void setPlace_in_file(String place_in_file) {
		this.place_in_file = place_in_file;
	}




	/**
	 * @return the supplementary_information
	 * @hibernate.property type="string"
	 */
	public String getSupplementary_information() {
		return supplementary_information;
	}


	/**
	 * @param supplementary_information the supplementary_information to set
	 */
	public void setSupplementary_information(String supplementary_information) {
		this.supplementary_information = supplementary_information;
	}


	/**
	 * @return the passenger1
	 * @hibernate.property type="string"
	 */
	public String getPassenger1() {
		return passenger1;
	}


	/**
	 * @param passenger1 the passenger1 to set
	 */
	public void setPassenger1(String passenger1) {
		this.passenger1 = passenger1;
	}


	/**
	 * @return the passenger2
	 * @hibernate.property type="string"
	 */
	public String getPassenger2() {
		return passenger2;
	}


	/**
	 * @param passenger2 the passenger2 to set
	 */
	public void setPassenger2(String passenger2) {
		this.passenger2 = passenger2;
	}


	/**
	 * @return the passenger3
	 * @hibernate.property type="string"
	 */
	public String getPassenger3() {
		return passenger3;
	}


	/**
	 * @param passenger3 the passenger3 to set
	 */
	public void setPassenger3(String passenger3) {
		this.passenger3 = passenger3;
	}


	/**
	 * @return the ebagtag
	 * @hibernate.property type="string"
	 */
	public String getEbagtag() {
		return ebagtag;
	}


	/**
	 * @param ebagtag the ebagtag to set
	 */
	public void setEbagtag(String ebagtag) {
		this.ebagtag = ebagtag;
	}



}