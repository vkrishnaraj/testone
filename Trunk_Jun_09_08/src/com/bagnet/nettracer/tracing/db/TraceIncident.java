/*
 * Created on Jul 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import com.bagnet.nettracer.tracing.constant.TracingConstants;
import com.bagnet.nettracer.tracing.utils.DateUtils;

/**
 * @author Administrator
 * 
 * @hibernate.class table="Incident"
 */
public class TraceIncident implements Serializable {

	private int version;
	private String Incident_ID;
	private Date createdate;
	private Date createtime;
	private String recordlocator;

	private Date lastupdated;

	private Date ohd_lasttraced;

	private Set passengers;

	private List itemlist;
	private Set itinerary;
	private Set claimchecks;


	private String _DATEFORMAT; // current login agent's date format
	private String _TIMEFORMAT; // current login agent's time format
	private TimeZone _TIMEZONE;

	/**
	 * @return Returns the version.
	 * @hibernate.version
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * @param version
	 *          The version to set.
	 */
	public void setVersion(int version) {
		this.version = version;
	}

	/**
	 * @return Returns the claimchecks.
	 * 
	 * @hibernate.set cascade="all" inverse="true" order-by="claimcheck_ID"
	 * @hibernate.key column="incident_ID"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.Incident_Claimcheck"
	 */
	public Set getClaimchecks() {
		return claimchecks;
	}

	/**
	 * @param claimchecks
	 *          The claimchecks to set.
	 */
	public void setClaimchecks(Set claimchecks) {
		this.claimchecks = claimchecks;
	}

	/**
	 * @return Returns the itemlist.
	 * @hibernate.list cascade="all" inverse="true"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.Item"
	 * @hibernate.index column="bagnumber"
	 * @hibernate.key column="incident_ID"
	 * 
	 */

	public List getItemlist() {
		return itemlist;
	}

	/**
	 * @param itemlist
	 *          The itemlist to set.
	 */
	public void setItemlist(List itemlist) {
		this.itemlist = itemlist;
	}

	/**
	 * @hibernate.set cascade="all" inverse="true" order-by="passenger_id"
	 * @hibernate.key column="incident_ID"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.Passenger"
	 * @return Returns the passengers.
	 */
	public Set getPassengers() {
		return passengers;
	}

	/**
	 * @param passengers
	 *          The passengers to set.
	 */
	public void setPassengers(Set passengers) {
		this.passengers = passengers;
	}

	/**
	 * @return Returns the itinerary.
	 * 
	 * @hibernate.set cascade="all" inverse="true"
	 *                order-by="departdate,schdeparttime,itinerary_ID"
	 * @hibernate.key column="incident_ID"
	 * @hibernate.one-to-many class="com.bagnet.nettracer.tracing.db.Itinerary"
	 * 
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
	 * @return Returns the createdate.
	 * 
	 * @hibernate.property type="date"
	 */
	public Date getCreatedate() {
		return createdate;
	}

	/**
	 * @param createdate
	 *          The createdate to set.
	 */
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	/**
	 * @return Returns the lastupdated.
	 * 
	 * @hibernate.property type="timestamp"
	 */
	public Date getLastupdated() {
		return lastupdated;
	}

	/**
	 * @param lastupdated
	 *          The lastupdated to set.
	 */
	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}

	/**
	 * @return Returns the createtime.
	 * 
	 * @hibernate.property type="time"
	 */
	public Date getCreatetime() {
		return createtime;
	}

	/**
	 * @param createtime
	 *          The createtime to set.
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getDisplaydate() {
		Date completedate = DateUtils.convertToDate(getCreatedate().toString()
				+ " " + getCreatetime().toString(), TracingConstants.DB_DATETIMEFORMAT,
				null);
		return DateUtils.formatDate(completedate, _DATEFORMAT + " " + _TIMEFORMAT,
				null, _TIMEZONE);
	}

	public Date getFullCreateDate() {
		return DateUtils.convertToDate(getCreatedate().toString() + " "
				+ getCreatetime().toString(), TracingConstants.DB_DATETIMEFORMAT, null);
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

	/**
	 * @return Returns the incident_ID.
	 * 
	 * @hibernate.id generator-class="assigned" type="string" column="Incident_ID"
	 */
	public String getIncident_ID() {
		return Incident_ID;
	}

	/**
	 * @param incident_ID
	 *          The incident_ID to set.
	 */
	public void setIncident_ID(String incident_ID) {
		Incident_ID = incident_ID;
	}

	/**
	 * @return Returns the recordlocator.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getRecordlocator() {
		return recordlocator;
	}

	/**
	 * @param recordlocator
	 *          The recordlocator to set.
	 */
	public void setRecordlocator(String recordlocator) {
		this.recordlocator = recordlocator;
	}

	/**
	 * @return Returns the ohd_lasttraced.
	 * 
	 * @hibernate.property type="timestamp"
	 */
	public Date getOhd_lasttraced() {
		return ohd_lasttraced;
	}

	/**
	 * @param ohd_lasttraced
	 *          The ohd_lasttraced to set.
	 */
	public void setOhd_lasttraced(Date ohd_lasttraced) {
		this.ohd_lasttraced = ohd_lasttraced;
	}

	public String getRcreatedate() {
		Date completedate = DateUtils.convertToDate(getCreatedate().toString(),
				TracingConstants.DB_DATEFORMAT, null);
		return DateUtils.formatDate(completedate, _DATEFORMAT, null, _TIMEZONE);
	}

	public String getRcreatetime() {
		Date completedate = DateUtils.convertToDate(getCreatetime().toString(),
				TracingConstants.DB_TIMEFORMAT, null);
		return DateUtils.formatDate(completedate, _TIMEFORMAT, null, _TIMEZONE);
	}

}