package com.bagnet.nettracer.tracing.forms;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.apache.struts.validator.ValidatorForm;

import com.bagnet.nettracer.tracing.db.WT_FWD_Log_Itinerary;

public class WorldTracerFWDForm extends ValidatorForm {

	private String ohd_ID;
	private String wt_id;
	private String placed_in_file;
	private String companyCode;
	private String destStation;
	private String bagtag;
	private String ebagtag;
	private String expeditenum;
	private String passenger1;
	private String passenger2;
	private String passenger3;
	private int loss_code;
	private String reason_for_loss;
	private String fault_station;
	private String fault_terminal;
	private String comments;
	private String supplementary_information;
	private String teletype_address1;
	private String teletype_address2;
	private String teletype_address3;
	private String teletype_address4;

	private int fwd_status;
	
	private List itinerarylist = new ArrayList();
	private String _DATEFORMAT; // current login agent's date format
	private String _TIMEFORMAT; // current login agent's time format
	private TimeZone _TIMEZONE;

	
	/**
	 * @return the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companycode the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}



	/**
	 * @return the destStation
	 */
	public String getDestStation() {
		return destStation;
	}

	/**
	 * @param destStation the destStation to set
	 */
	public void setDestStation(String destStation) {
		this.destStation = destStation;
	}

	/**
	 * @return the bagtag
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
	 * @return the expeditenum
	 */
	public String getExpeditenum() {
		return expeditenum;
	}

	/**
	 * @param expeditenum the expeditenum to set
	 */
	public void setExpeditenum(String expeditenum) {
		this.expeditenum = expeditenum;
	}

	/**
	 * @return the loss_code
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
	 */
	public String getReason_for_loss() {
		return reason_for_loss;
	}

	/**
	 * @param reason_for_los the reason_for_loss to set
	 */
	public void setReason_for_loss(String reason_for_loss) {
		this.reason_for_loss = reason_for_loss;
	}

	/**
	 * @return the fault_station
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
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the teletype_address1
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
	 * @return the fwd_status
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
	 * @return the _DATEFORMAT
	 */
	public String get_DATEFORMAT() {
		return _DATEFORMAT;
	}

	/**
	 * @param _dateformat the _DATEFORMAT to set
	 */
	public void set_DATEFORMAT(String _dateformat) {
		_DATEFORMAT = _dateformat;
	}

	/**
	 * @return the _TIMEFORMAT
	 */
	public String get_TIMEFORMAT() {
		return _TIMEFORMAT;
	}

	/**
	 * @param _timeformat the _TIMEFORMAT to set
	 */
	public void set_TIMEFORMAT(String _timeformat) {
		_TIMEFORMAT = _timeformat;
	}

	/**
	 * @return the _TIMEZONE
	 */
	public TimeZone get_TIMEZONE() {
		return _TIMEZONE;
	}

	/**
	 * @param _timezone the _TIMEZONE to set
	 */
	public void set_TIMEZONE(TimeZone _timezone) {
		_TIMEZONE = _timezone;
	}

	/**
	 * @return Returns the itinerarylist.
	 */
	public List getItinerarylist() {
		return itinerarylist;
	}

	/**
	 * @param itinerarylist
	 *          The itinerarylist to set.
	 */
	public void setItinerarylist(List itinerarylist) {
		this.itinerarylist = itinerarylist;
	}

	/**
	 * @return Returns the ohd_ID.
	 */
	public String getOhd_ID() {
		return ohd_ID;
	}

	/**
	 * @param ohd_ID
	 *          The ohd_ID to set.
	 */
	public void setOhd_ID(String ohd_ID) {
		this.ohd_ID = ohd_ID;
	}
    

	public WT_FWD_Log_Itinerary getItinerary(int index) {
		if (this.itinerarylist.size() <= index) {
			this.itinerarylist.add(new WT_FWD_Log_Itinerary());
		}
		return (WT_FWD_Log_Itinerary) this.itinerarylist.get(index);
	}


	/**
	 * @return the supplementary_information
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
	 * @return the placed_in_file
	 */
	public String getPlaced_in_file() {
		return placed_in_file;
	}

	/**
	 * @param placed_in_file the placed_in_file to set
	 */
	public void setPlaced_in_file(String placed_in_file) {
		this.placed_in_file = placed_in_file;
	}

	/**
	 * @return the wt_id
	 */
	public String getWt_id() {
		return wt_id;
	}

	/**
	 * @param wt_id the wt_id to set
	 */
	public void setWt_id(String wt_id) {
		this.wt_id = wt_id;
	}

	/**
	 * @return the passenger1
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
