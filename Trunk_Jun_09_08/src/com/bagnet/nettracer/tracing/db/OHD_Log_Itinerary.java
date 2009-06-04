/*
 * Created on Jul 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.Date;

import com.bagnet.nettracer.tracing.utils.DateUtils;

/**
 * @author Administrator
 * 
 * @hibernate.class table="ohd_log_itinerary"
 */
public class OHD_Log_Itinerary implements Serializable {
	private int Itinerary_ID;
	private int itinerarytype; //0-passenger, 1 - bagrouting
	private String legfrom;
	private String legto;
	private int legfrom_type;
	private int legto_type;
	private Date departdate;
	private Date arrivedate;
	private String airline;
	private String flightnum;
	private Date schdeparttime;
	private Date scharrivetime;
	private OHD_Log log;
	private boolean notify;

	private String _DATEFORMAT; //for date time format purpose only, not
	// literally part of this object
	private String _TIMEFORMAT; //for date time format purpose only, not

	// literally part of this objec

	/**
	 * @hibernate.property type="string"
	 * @return Returns the airline.
	 */
	public String getAirline() {
		return airline;
	}

	/**
	 * @param airline
	 *          The airline to set.
	 */
	public void setAirline(String airline) {
		this.airline = airline;
	}

	/**
	 * @return Returns the incident.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.OHD_Log"
	 *                        column="OHDLog_ID"
	 */
	public OHD_Log getLog() {
		return log;
	}

	/**
	 * @param incident
	 *          The incident to set.
	 */
	public void setLog(OHD_Log log) {
		this.log = log;
	}

	/**
	 * @return Returns the flightnum.
	 * 
	 * @hibernate.property type="string"
	 */
	public String getFlightnum() {
		return flightnum;
	}

	/**
	 * @param flightnum
	 *          The flightnum to set.
	 */
	public void setFlightnum(String flightnum) {
		this.flightnum = flightnum;
		if (this.flightnum != null) this.flightnum = (this.flightnum).toUpperCase();
	}

	/**
	 * @return Returns the itinerary_ID.
	 * 
	 * @hibernate.id generator-class="native" type="integer" column="Itinerary_ID"
	 *               unsaved-value="0"
	 * @hibernate.generator-param name="sequence" value="ohd_log_itinerary_0"
	 *  
	 */
	public int getItinerary_ID() {
		return Itinerary_ID;
	}

	/**
	 * @param itinerary_ID
	 *          The itinerary_ID to set.
	 */
	public void setItinerary_ID(int itinerary_ID) {
		Itinerary_ID = itinerary_ID;
	}

	/**
	 * @return Returns the itinerarytype.
	 * 
	 * @hibernate.property type="integer" length="1"
	 */
	public int getItinerarytype() {
		return itinerarytype;
	}

	/**
	 * @param itinerarytype
	 *          The itinerarytype to set.
	 */
	public void setItinerarytype(int itinerarytype) {
		this.itinerarytype = itinerarytype;
	}

	/**
	 * @return Returns the legfrom.
	 * 
	 * @hibernate.property type="string" length="3"
	 */
	public String getLegfrom() {
		return legfrom;
	}

	/**
	 * @param legfrom
	 *          The legfrom to set.
	 */
	public void setLegfrom(String legfrom) {
		this.legfrom = legfrom;
		if (this.legfrom != null) this.legfrom = (this.legfrom).toUpperCase();

	}

	/**
	 * @return Returns the legto.
	 * 
	 * @hibernate.property type="string" length="3"
	 */
	public String getLegto() {
		return legto;
	}

	/**
	 * @param legto
	 *          The legto to set.
	 */
	public void setLegto(String legto) {
		this.legto = legto;
		if (this.legto != null) this.legto = (this.legto).toUpperCase();

	}

	/**
	 * @return Returns the legfrom_type.
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getLegfrom_type() {
		return legfrom_type;
	}

	/**
	 * @param legfrom_type
	 *          The legfrom_type to set.
	 */
	public void setLegfrom_type(int legfrom_type) {
		this.legfrom_type = legfrom_type;
	}

	/**
	 * @return Returns the legto_type.
	 * 
	 * @hibernate.property type="integer"
	 */
	public int getLegto_type() {
		return legto_type;
	}

	/**
	 * @param legto_type
	 *          The legto_type to set.
	 */
	public void setLegto_type(int legto_type) {
		this.legto_type = legto_type;
	}

	/**
	 * @return Returns the arrivedate.
	 * 
	 * @hibernate.property type="date"
	 */
	public Date getArrivedate() {
		return arrivedate;
	}

	/**
	 * @param arrivedate
	 *          The arrivedate to set.
	 */
	public void setArrivedate(Date arrivedate) {
		this.arrivedate = arrivedate;
	}

	public void setDisarrivedate(String s) {
		setArrivedate(DateUtils.convertToDate(s, get_DATEFORMAT(), null));
	}

	public String getDisarrivedate() {
		return DateUtils.formatDate(getArrivedate(), get_DATEFORMAT(), null, null);
	}

	/**
	 * @return Returns the departdate.
	 * 
	 * @hibernate.property type="date"
	 */
	public Date getDepartdate() {
		return departdate;
	}

	/**
	 * @param departdate
	 *          The departdate to set.
	 */
	public void setDepartdate(Date departdate) {
		this.departdate = departdate;
	}

	public void setDisdepartdate(String s) {
		setDepartdate(DateUtils.convertToDate(s, get_DATEFORMAT(), null));
	}

	public String getDisdepartdate() {
		return DateUtils.formatDate(getDepartdate(), get_DATEFORMAT(), null, null);
	}

	/**
	 * @return Returns the scharrivetime.
	 * 
	 * @hibernate.property type="time"
	 */
	public Date getScharrivetime() {
		return scharrivetime;
	}

	/**
	 * @param scharrivetime
	 *          The scharrivetime to set.
	 */
	public void setScharrivetime(Date scharrivetime) {
		this.scharrivetime = scharrivetime;
	}

	public void setDisscharrivetime(String s) {
		setScharrivetime(DateUtils.convertToDate(s, get_TIMEFORMAT(), null));
	}

	public String getDisscharrivetime() {
		return DateUtils.formatDate(getScharrivetime(), get_TIMEFORMAT(), null, null);
	}

	/**
	 * @return Returns the schdeparttime.
	 * 
	 * @hibernate.property type="time"
	 */
	public Date getSchdeparttime() {
		return schdeparttime;
	}

	/**
	 * @param schdeparttime
	 *          The schdeparttime to set.
	 */
	public void setSchdeparttime(Date schdeparttime) {
		this.schdeparttime = schdeparttime;
	}

	public void setDisschdeparttime(String s) {
		setSchdeparttime(DateUtils.convertToDate(s, get_TIMEFORMAT(), null));
	}

	public String getDisschdeparttime() {
		return DateUtils.formatDate(getSchdeparttime(), get_TIMEFORMAT(), null, null);
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

	public boolean isNotify() {
		return notify;
	}

	public void setNotify(boolean notify) {
		this.notify = notify;
	}
}