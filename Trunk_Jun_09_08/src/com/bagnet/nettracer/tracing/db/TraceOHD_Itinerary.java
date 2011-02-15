/*
 * Created on Jul 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 * 
 * @hibernate.class table="ohd_itinerary"
 */
public class TraceOHD_Itinerary implements Serializable {

	private static final long serialVersionUID = 1L;
	private int Itinerary_ID;
	private int itinerarytype; //0-passenger, 1 - bagrouting
	private String legfrom = "";
	private String legto = "";
	private int legfrom_type;
	private int legto_type;
	private Date departdate;
	private Date arrivedate;
	private String airline = "";
	private String flightnum = "";
	private Date schdeparttime;
	private Date scharrivetime;
	private Date actdeparttime;
	private Date actarrivetime;
	private TraceOHD ohd;

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
	 * @hibernate.many-to-one class="com.bagnet.nettracer.tracing.db.TraceOHD"
	 *                        column="ohd_ID" not-null="true"
	 */
	public TraceOHD getOhd() {
		return ohd;
	}

	/**
	 * @param incident
	 *          The incident to set.
	 */
	public void setOhd(TraceOHD ohd) {
		this.ohd = ohd;
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
		this.flightnum = (flightnum != null ? flightnum.toUpperCase() : flightnum);
	}

	/**
	 * @return Returns the itinerary_ID.
	 * 
	 * @hibernate.id generator-class="native" type="integer" column="Itinerary_ID"
	 *               unsaved-value="0"
	 * @hibernate.generator-param name="sequence" value="ohd_itinerary_0"
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
		this.legfrom = (legfrom != null ? legfrom.toUpperCase() : legfrom);
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
		this.legto = (legto != null ? legto.toUpperCase() : legto);
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

	/**
	 * @return Returns the actarrivetime.
	 * 
	 * @hibernate.property type="time"
	 */
	public Date getActarrivetime() {
		return actarrivetime;
	}

	/**
	 * @param actarrivetime
	 *          The actarrivetime to set.
	 */
	public void setActarrivetime(Date actarrivetime) {
		this.actarrivetime = actarrivetime;
	}

	/**
	 * @return Returns the actdeparttime.
	 * 
	 * @hibernate.property type="time"
	 */
	public Date getActdeparttime() {
		return actdeparttime;
	}

	/**
	 * @param actdeparttime
	 *          The actdeparttime to set.
	 */
	public void setActdeparttime(Date actdeparttime) {
		this.actdeparttime = actdeparttime;
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

}