package com.bagnet.nettracer.tracing.db.wtq;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.hibernate.annotations.Proxy;

@Entity
@DiscriminatorValue("REQUEST_QOH")
@Proxy(lazy = false)
public class WtqRequestQoh extends WtqRequestOhd {
	
	
	private String bagTagNumber;
	private String fromAirline;
	private String fromStation;


	@Override
	@Transient
	public Object[] getExistsParameters() {
		return new Object[] {this.getBagTagNumber(), this.getStatus()};
	}

	@Override
	@Transient
	public String getExistsQuery() {
		return "from WtqRequestOhd roh where roh.bagTagNumber = ? and status = ?";
	}

	@Column(length=15, name="bag_tag")
	public String getBagTagNumber() {
		return bagTagNumber;
	}

	public void setBagTagNumber(String bagTagNumber) {
		this.bagTagNumber = bagTagNumber;
	}

	@Column(length=5, name="from_airline")
	public String getFromAirline() {
		return fromAirline;
	}

	public void setFromAirline(String fromAirline) {
		this.fromAirline = fromAirline;
	}

	@Column(length=5, name="from_station")
	public String getFromStation() {
		return fromStation;
	}

	public void setFromStation(String fromStation) {
		this.fromStation = fromStation;
	}
	
	

}
