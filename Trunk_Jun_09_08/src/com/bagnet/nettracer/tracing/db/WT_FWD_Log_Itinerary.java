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
 * @hibernate.class table="wt_fwd_log_itinerary"
 */
public class WT_FWD_Log_Itinerary implements Serializable {
	private int Itinerary_ID;
	private int itinerarytype; //0-passenger, 1 - bagrouting
	private String legfrom;
	private String legto;
	private Date departdate;
	private String airline;
	private String flightnum;
	private WT_FWD_Log log;

	private String _DATEFORMAT; //for date time format purpose only, not
	// literally part of this object
	private String _TIMEFORMAT; //for date time format purpose only, not

	// literally part of this object



	/**
	 * @return Returns the log.
	 * 
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.WT_FWD_Log"
	 *                        column="wt_fwd_log_id"
	 */
	public WT_FWD_Log getLog() {
		return log;
	}

	/**
	 * @param log
	 *          The log to set.
	 */
	public void setLog(WT_FWD_Log log) {
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
	 * @hibernate.generator-param name="sequence" value="wt_fwd_log_itinerary_0"
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
	 * @return Returns the legfrom.
	 * 
	 * @hibernate.property type="string"
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
	 * @hibernate.property type="string"
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
	 * @return Returns the itinerarytype.
	 * 
	 * @hibernate.property type="integer" length="1"
	 */
	public int getItinerarytype() {
		return itinerarytype;
	}

	/**
	 * @param itinerarytype the itinerarytype to set
	 */
	public void setItinerarytype(int itinerarytype) {
		this.itinerarytype = itinerarytype;
	}
}