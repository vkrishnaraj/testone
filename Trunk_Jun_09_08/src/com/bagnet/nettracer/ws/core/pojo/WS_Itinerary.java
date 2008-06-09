/*
 * Created on Jul 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.ws.core.pojo;


/**
 * @author Administrator
 * 
 */
public class WS_Itinerary {
	private int Itinerary_ID;
	private int itinerarytype; //0-passenger, 1 - bagrouting
	private String legfrom;
	private String legto;
	private int legfrom_type;
	private int legto_type;
	private String departdate;
	private String arrivedate;
	private String airline;
	private String flightnum;
	private String schdeparttime;
	private String scharrivetime;
	private String actdeparttime;
	private String actarrivetime;
	
	/**
	 * @return the itinerary_ID
	 */
	public int getItinerary_ID() {
		return Itinerary_ID;
	}
	/**
	 * @param itinerary_ID the itinerary_ID to set
	 */
	public void setItinerary_ID(int itinerary_ID) {
		Itinerary_ID = itinerary_ID;
	}
	/**
	 * @return the itinerarytype
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
	/**
	 * @return the legfrom
	 */
	public String getLegfrom() {
		return legfrom;
	}
	/**
	 * @param legfrom the legfrom to set
	 */
	public void setLegfrom(String legfrom) {
		this.legfrom = legfrom;
	}
	/**
	 * @return the legto
	 */
	public String getLegto() {
		return legto;
	}
	/**
	 * @param legto the legto to set
	 */
	public void setLegto(String legto) {
		this.legto = legto;
	}
	/**
	 * @return the legfrom_type
	 */
	public int getLegfrom_type() {
		return legfrom_type;
	}
	/**
	 * @param legfrom_type the legfrom_type to set
	 */
	public void setLegfrom_type(int legfrom_type) {
		this.legfrom_type = legfrom_type;
	}
	/**
	 * @return the legto_type
	 */
	public int getLegto_type() {
		return legto_type;
	}
	/**
	 * @param legto_type the legto_type to set
	 */
	public void setLegto_type(int legto_type) {
		this.legto_type = legto_type;
	}
	/**
	 * @return the departdate
	 */
	public String getDepartdate() {
		return departdate;
	}
	/**
	 * @param departdate the departdate to set
	 */
	public void setDepartdate(String departdate) {
		this.departdate = departdate;
	}
	/**
	 * @return the arrivedate
	 */
	public String getArrivedate() {
		return arrivedate;
	}
	/**
	 * @param arrivedate the arrivedate to set
	 */
	public void setArrivedate(String arrivedate) {
		this.arrivedate = arrivedate;
	}
	/**
	 * @return the airline
	 */
	public String getAirline() {
		return airline;
	}
	/**
	 * @param airline the airline to set
	 */
	public void setAirline(String airline) {
		this.airline = airline;
	}
	/**
	 * @return the flightnum
	 */
	public String getFlightnum() {
		return flightnum;
	}
	/**
	 * @param flightnum the flightnum to set
	 */
	public void setFlightnum(String flightnum) {
		this.flightnum = flightnum;
	}
	/**
	 * @return the schdeparttime
	 */
	public String getSchdeparttime() {
		return schdeparttime;
	}
	/**
	 * @param schdeparttime the schdeparttime to set
	 */
	public void setSchdeparttime(String schdeparttime) {
		this.schdeparttime = schdeparttime;
	}
	/**
	 * @return the scharrivetime
	 */
	public String getScharrivetime() {
		return scharrivetime;
	}
	/**
	 * @param scharrivetime the scharrivetime to set
	 */
	public void setScharrivetime(String scharrivetime) {
		this.scharrivetime = scharrivetime;
	}
	/**
	 * @return the actdeparttime
	 */
	public String getActdeparttime() {
		return actdeparttime;
	}
	/**
	 * @param actdeparttime the actdeparttime to set
	 */
	public void setActdeparttime(String actdeparttime) {
		this.actdeparttime = actdeparttime;
	}
	/**
	 * @return the actarrivetime
	 */
	public String getActarrivetime() {
		return actarrivetime;
	}
	/**
	 * @param actarrivetime the actarrivetime to set
	 */
	public void setActarrivetime(String actarrivetime) {
		this.actarrivetime = actarrivetime;
	}


}