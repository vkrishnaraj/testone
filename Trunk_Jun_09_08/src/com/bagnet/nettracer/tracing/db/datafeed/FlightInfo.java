package com.bagnet.nettracer.tracing.db.datafeed;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;

@Entity
@Table(name = "z_ws_flight_info")
public class FlightInfo implements Serializable {

	private long id;


	private String flightNum;


	private Date departureDate;

	private Date arrivalDate;


	private String departureCity;

	private String arrivalCity;

	private int totalPax;

	private int connPax;

	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	
	public void setId(long id) {
		this.id = id;
	}

	@Column(length = 10)
	public String getFlightNum() {
		return flightNum;
	}

	public void setFlightNum(String flightNum) {
		this.flightNum = flightNum;
	}

	@Basic
	public Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	@Basic
	public Date getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	@Column(length = 5)
	public String getDepartureCity() {
		return departureCity;
	}

	public void setDepartureCity(String departureCity) {
		this.departureCity = departureCity;
	}

	@Column(length = 5)
	public String getArrivalCity() {
		return arrivalCity;
	}

	public void setArrivalCity(String arrivalCity) {
		this.arrivalCity = arrivalCity;
	}

	@Basic
	public int getTotalPax() {
		return totalPax;
	}

	public void setTotalPax(int totalPax) {
		this.totalPax = totalPax;
	}

	@Basic
	public int getConnPax() {
		return connPax;
	}

	public void setConnPax(int connPax) {
		this.connPax = connPax;
	}
	
	//TODO
	//Warning this is MySQL specific, if any company other than WestJet uses this, it will have to be moved to compnay specific code
	@Formula("DATE(departureDate)")
	public Date getTruncDepartDate() {
		if (this.departureDate == null)
			return null;
		Calendar c = new GregorianCalendar();
		c.setTime(this.departureDate);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
	
	private void setTruncDepartDate(Date truncDepartDate) {
		
	}

}
