package com.bagnet.nettracer.ws.core.pojo;

import java.util.Date;


/**
 * @author Administrator
 * 
 */
public class WS_ForwardItinerary {
	private String legfrom;
	private String legto;
	private Date departdate;
	private Date arrivedate;
	private String airline;
	private String flightnum;

	
	/**
	 * @return the legFrom
	 */
	public String getLegfrom() {
		return legfrom;
	}
	/**
	 * @param legfrom the legFrom to set
	 */
	public void setLegfrom(String legfrom) {
		this.legfrom = legfrom;
	}
	/**
	 * @return the legTo
	 */
	public String getLegto() {
		return legto;
	}
	/**
	 * @param legto the legTo to set
	 */
	public void setLegto(String legto) {
		this.legto = legto;
	}

	/**
	 * @return the departdate
	 */
	public Date getDepartdate() {
		return departdate;
	}
	/**
	 * @param departdate the departdate to set
	 */
	public void setDepartdate(Date departdate) {
		this.departdate = departdate;
	}
	/**
	 * @return the arrivedate
	 */
	public Date getArrivedate() {
		return arrivedate;
	}
	/**
	 * @param arrivedate the arrivedate to set
	 */
	public void setArrivedate(Date arrivedate) {
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
}