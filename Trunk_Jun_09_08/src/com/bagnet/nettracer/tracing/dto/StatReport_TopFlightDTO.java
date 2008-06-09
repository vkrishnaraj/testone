/*
 * Created on Sep 1, 2004
 *
 * Administrator
 */
package com.bagnet.nettracer.tracing.dto;

import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;

/**
 * @author Administrator
 *
 * create date - Sep 1, 2004
 */
/**
 * top flight temporary class
 * 
 * @author Administrator
 * 
 * create date - Sep 1, 2004
 */
public class StatReport_TopFlightDTO {
	private String airline;
	private String flightnum;
	private int numinc;
	private int numbag;
	private Station station;
	private Status status;

	/**
	 * @return Returns the station.
	 */
	public Station getStation() {
		return station;
	}

	/**
	 * @param station
	 *          The station to set.
	 */
	public void setStation(Station station) {
		this.station = station;
	}

	/**
	 * @return Returns the status.
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status
	 *          The status to set.
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
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
	 * @return Returns the flightnum.
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
	}

	/**
	 * @return Returns the numbag.
	 */
	public int getNumbag() {
		return numbag;
	}

	/**
	 * @param numbag
	 *          The numbag to set.
	 */
	public void setNumbag(int numbag) {
		this.numbag = numbag;
	}

	/**
	 * @return Returns the numinc.
	 */
	public int getNuminc() {
		return numinc;
	}

	/**
	 * @param numinc
	 *          The numinc to set.
	 */
	public void setNuminc(int numinc) {
		this.numinc = numinc;
	}

	public int getStationassigned_ID() {
		return station.getStation_ID();
	}

	public String getStationcode() {
		return station.getStationcode();
	}
}