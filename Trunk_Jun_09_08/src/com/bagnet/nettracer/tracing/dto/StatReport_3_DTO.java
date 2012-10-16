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
public class StatReport_3_DTO {

	private int station_ID;
	private String stationcode;
	private String faultstationcode;
	private String faultcompany;
	private String incident_ID;
	private int itemtype_ID;
	private int loss_code;
	private Date createdate;
	private Date createtime;
	private Date closedate;
	private String rcreatedate;
	private String rcreatetime;
	private String rclosedate;
	private String statusdesc;
	private String typedesc;
	private String customer_name;
	private String itinerary;
	private String final_destination;
	
	private String _DATEFORMAT; // current login agent's date format
	private String _TIMEFORMAT; // current login agent's time format
	private TimeZone _TIMEZONE;
	
	/**
	 * @return Returns the faultstationcode.
	 */
	public String getFaultstationcode() {
		return faultstationcode;
	}
	/**
	 * @param faultstationcode The faultstationcode to set.
	 */
	public void setFaultstationcode(String faultstationcode) {
		this.faultstationcode = faultstationcode;
	}
	/**
	 * @return Returns the incident_ID.
	 */
	public String getIncident_ID() {
		return incident_ID;
	}
	/**
	 * @param incident_ID The incident_ID to set.
	 */
	public void setIncident_ID(String incident_ID) {
		this.incident_ID = incident_ID;
	}
	/**
	 * @return Returns the itemtype_ID.
	 */
	public int getItemtype_ID() {
		return itemtype_ID;
	}
	/**
	 * @param itemtype_ID The itemtype_ID to set.
	 */
	public void setItemtype_ID(int itemtype_ID) {
		this.itemtype_ID = itemtype_ID;
	}
	/**
	 * @return Returns the loss_code.
	 */
	public int getLoss_code() {
		return loss_code;
	}
	/**
	 * @param loss_code The loss_code to set.
	 */
	public void setLoss_code(int loss_code) {
		this.loss_code = loss_code;
	}
	/**
	 * @return Returns the rcreatedate.
	 */
	public String getRcreatedate() {
		
		Date tempdate = DateUtils.convertToDate( DateUtils.formatDate(getCreatedate(), TracingConstants.DB_DATEFORMAT, null, null) + " "
				+ DateUtils.formatDate(getCreatetime(), TracingConstants.DB_TIMEFORMAT, null, null),TracingConstants.DB_DATETIMEFORMAT,null);
		
		return DateUtils.formatDate(tempdate, _DATEFORMAT, null, _TIMEZONE);
	}
	/**
	 * @param rcreatedate The rcreatedate to set.
	 */
	public void setRcreatedate(String rcreatedate) {
		this.rcreatedate = rcreatedate;
	}
	
	/**
	 * @return Returns the rclosedate.
	 */
	public String getRclosedate() {
		
		Date tempdate = DateUtils.convertToDate( DateUtils.formatDate(getClosedate(), TracingConstants.DB_DATEFORMAT, null, null) + " "
				+ DateUtils.formatDate(getClosedate(), TracingConstants.DB_TIMEFORMAT, null, null),TracingConstants.DB_DATETIMEFORMAT,null);
		
		return DateUtils.formatDate(tempdate, _DATEFORMAT, null, _TIMEZONE);
	}
	/**
	 * @param rclosedate The rclosedate to set.
	 */
	public void setRclosedate(String rclosedate) {
		this.rclosedate = rclosedate;
	}
	
	/**
	 * @return Returns the rcreatetime.
	 */



	public String getRcreatetime() {
		Date tempdate = DateUtils.convertToDate( DateUtils.formatDate(getCreatedate(), TracingConstants.DB_DATEFORMAT, null, null) + " "
				+ DateUtils.formatDate(getCreatetime(), TracingConstants.DB_TIMEFORMAT, null, null),TracingConstants.DB_DATETIMEFORMAT,null);
		
		return DateUtils.formatDate(tempdate, _TIMEFORMAT, null, _TIMEZONE);
	}
	
	/**
	 * @param rcreatetime The rcreatetime to set.
	 */
	public void setRcreatetime(String rcreatetime) {
		this.rcreatetime = rcreatetime;
	}
	/**
	 * @return Returns the station_ID.
	 */
	public int getStation_ID() {
		return station_ID;
	}
	/**
	 * @param station_ID The station_ID to set.
	 */
	public void setStation_ID(int station_ID) {
		this.station_ID = station_ID;
	}
	/**
	 * @return Returns the stationcode.
	 */
	public String getStationcode() {
		return stationcode;
	}
	/**
	 * @param stationcode The stationcode to set.
	 */
	public void setStationcode(String stationcode) {
		this.stationcode = stationcode;
	}
	/**
	 * @return Returns the statusdesc.
	 */
	public String getStatusdesc() {
		return statusdesc;
	}
	/**
	 * @param statusdesc The statusdesc to set.
	 */
	public void setStatusdesc(String statusdesc) {
		this.statusdesc = statusdesc;
	}
	/**
	 * @return Returns the typedesc.
	 */
	public String getTypedesc() {
		return typedesc;
	}
	/**
	 * @param typedesc The typedesc to set.
	 */
	public void setTypedesc(String typedesc) {
		this.typedesc = typedesc;
	}
	
	/**
	 * @return Returns the closedate.
	 */
	public Date getClosedate() {
		return closedate;
	}
	/**
	 * @param closedate The closedate to set.
	 */
	public void setClosedate(Date closedate) {
		this.closedate = closedate;
	}
	
	/**
	 * @return Returns the createdate.
	 */
	public Date getCreatedate() {
		return createdate;
	}
	/**
	 * @param createdate The createdate to set.
	 */
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	
	/**
	 * @return Returns the createtime.
	 */
	public Date getCreatetime() {
		return createtime;
	}
	/**
	 * @param createtime The createtime to set.
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	/**
	 * @return Returns the customer_name.
	 */
	public String getCustomer_name() {
		return customer_name;
	}
	/**
	 * @param customer_name The customer_name to set.
	 */
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	/**
	 * @return Returns the itinerary.
	 */
	public String getItinerary() {
		return itinerary;
	}
	/**
	 * @param itinerary The itinerary to set.
	 */
	public void setItinerary(String itinerary) {
		this.itinerary = itinerary;
	}
	
	/**
	 * @return Returns the final_destination.
	 */
	public String getFinal_destination() {
		return final_destination;
	}
	/**
	 * @param final_destination The final_destination to set.
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
	public String getFaultcompany() {
		return faultcompany;
	}
	public void setFaultcompany(String faultcompany) {
		this.faultcompany = faultcompany;
	}
	
}
