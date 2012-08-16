/*
 * Created on May 7, 2007
 *
 * matt
 */
package com.bagnet.nettracer.tracing.dto;

import java.util.Date;
import java.util.TimeZone;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.utils.DateUtils;

/**
 * @author matt
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class StatReport_OHD_DTO {

	private int station_ID;
	private String stationcode;
	private String holdingstation;
	private String OHD_ID;
	private Date founddate;
	private Date foundtime;
	private String rfounddate;
	private String rfoundtime;
	private String statusdesc;
	private String bagdispdesc;
	private String customer_name;
	private String itinerary;
	private String final_destination;
	
	private String _DATEFORMAT; // current login agent's date format
	private String _TIMEFORMAT; // current login agent's time format
	private TimeZone _TIMEZONE;
	
	private String faultstationcode;
	private int loss_code;

	
	public String getFaultstationcode() {
		return faultstationcode;
	}
	public void setFaultstationcode(String faultstationcode) {
		this.faultstationcode = faultstationcode;
	}
	public int getLoss_code() {
		return loss_code;
	}
	public void setLoss_code(int loss_code) {
		this.loss_code = loss_code;
	}
	
	/**
	 * @return the holdingstation
	 */
	public String getHoldingstation() {
		return holdingstation;
	}
	/**
	 * @param holdingstation the holdingstation to set
	 */
	public void setHoldingstation(String holdingstation) {
		this.holdingstation = holdingstation;
	}
	/**
	 * @return the oHD_ID
	 */
	public String getOHD_ID() {
		return OHD_ID;
	}
	/**
	 * @param ohd_id the oHD_ID to set
	 */
	public void setOHD_ID(String ohd_id) {
		OHD_ID = ohd_id;
	}
	/**
	 * @return the founddate
	 */
	public Date getFounddate() {
		return founddate;
	}
	/**
	 * @param founddate the founddate to set
	 */
	public void setFounddate(Date founddate) {
		this.founddate = founddate;
	}
	/**
	 * @return the foundtime
	 */
	public Date getFoundtime() {
		return foundtime;
	}
	/**
	 * @param foundtime the foundtime to set
	 */
	public void setFoundtime(Date foundtime) {
		this.foundtime = foundtime;
	}
	/**
	 * @return the rfounddate
	 */
	public String getRfounddate() {
		Date tempdate = DateUtils.convertToDate( DateUtils.formatDate(getFounddate(), TracingConstants.DB_DATEFORMAT, null, null) + " "
				+ DateUtils.formatDate(getFoundtime(), TracingConstants.DB_TIMEFORMAT, null, null),TracingConstants.DB_DATETIMEFORMAT,null);
		
		return DateUtils.formatDate(tempdate, _DATEFORMAT, null, _TIMEZONE);

	}
	/**
	 * @param rfounddate the rfounddate to set
	 */
	public void setRfounddate(String rfounddate) {
		this.rfounddate = rfounddate;
	}
	/**
	 * @return the rfoundtime
	 */
	public String getRfoundtime() {
		Date tempdate = DateUtils.convertToDate( DateUtils.formatDate(getFounddate(), TracingConstants.DB_DATEFORMAT, null, null) + " "
				+ DateUtils.formatDate(getFoundtime(), TracingConstants.DB_TIMEFORMAT, null, null),TracingConstants.DB_DATETIMEFORMAT,null);
		
		return DateUtils.formatDate(tempdate, _TIMEFORMAT, null, _TIMEZONE);

	}
	/**
	 * @param rfoundtime the rfoundtime to set
	 */
	public void setRfoundtime(String rfoundtime) {
		this.rfoundtime = rfoundtime;
	}



	/**
	 * @return the station_ID
	 */
	public int getStation_ID() {
		return station_ID;
	}
	/**
	 * @param station_ID the station_ID to set
	 */
	public void setStation_ID(int station_ID) {
		this.station_ID = station_ID;
	}
	/**
	 * @return the stationcode
	 */
	public String getStationcode() {
		return stationcode;
	}
	/**
	 * @param stationcode the stationcode to set
	 */
	public void setStationcode(String stationcode) {
		this.stationcode = stationcode;
	}
	/**
	 * @return the statusdesc
	 */
	public String getStatusdesc() {
		return statusdesc;
	}
	/**
	 * @param statusdesc the statusdesc to set
	 */
	public void setStatusdesc(String statusdesc) {
		this.statusdesc = statusdesc;
	}
	/**
	 * @return the bagdispdesc
	 */
	public String getBagdispdesc() {
		return bagdispdesc;
	}
	/**
	 * @param statusdesc the statusdesc to set
	 */
	public void setBagdispdesc(String bagdispdesc) {
		this.bagdispdesc = bagdispdesc;
	}
	/**
	 * @return the customer_name
	 */
	public String getCustomer_name() {
		return customer_name;
	}
	/**
	 * @param customer_name the customer_name to set
	 */
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	/**
	 * @return the itinerary
	 */
	public String getItinerary() {
		return itinerary;
	}
	/**
	 * @param itinerary the itinerary to set
	 */
	public void setItinerary(String itinerary) {
		this.itinerary = itinerary;
	}
	/**
	 * @return the final_destination
	 */
	public String getFinal_destination() {
		return final_destination;
	}
	/**
	 * @param final_destination the final_destination to set
	 */
	public void setFinal_destination(String final_destination) {
		this.final_destination = final_destination;
	}
	/**
	 * @return Returns the _DATEFORMAT.
	 */
	public String get_DATEFORMAT() {
		return _DATEFORMAT;
	}
	/**
	 * @param _dateformat The _DATEFORMAT to set.
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
	 * @param _timeformat The _TIMEFORMAT to set.
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
	 * @param _timezone The _TIMEZONE to set.
	 */
	public void set_TIMEZONE(TimeZone _timezone) {
		_TIMEZONE = _timezone;
	}
	
}
