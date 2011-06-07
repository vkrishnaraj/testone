package com.bagnet.nettracer.tracing.db.lf;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Proxy;

import com.bagnet.nettracer.tracing.db.Station;

@Entity
@Proxy(lazy = false)
public class LFReservation {

	@Id
	@GeneratedValue
	private long id;
	
	private String agreementNumber;
	
	private String mvaNumber;
	
	@ManyToOne
	@JoinColumn(name = "pickuplocation_station_ID", nullable = false)
	private Station pickupLocation;
	
	@ManyToOne
	@JoinColumn(name = "dropofflocation_station_ID", nullable = false)
	private Station dropoffLocation;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAgreementNumber() {
		return agreementNumber;
	}

	public void setAgreementNumber(String agreementNumber) {
		this.agreementNumber = agreementNumber;
	}

	public Station getPickupLocation() {
		return pickupLocation;
	}

	public void setPickupLocation(Station pickupLocation) {
		this.pickupLocation = pickupLocation;
	}

	public Station getDropoffLocation() {
		return dropoffLocation;
	}

	public void setDropoffLocation(Station dropoffLocation) {
		this.dropoffLocation = dropoffLocation;
	}

	public String getMvaNumber() {
		return mvaNumber;
	}

	public void setMvaNumber(String mvaNumber) {
		this.mvaNumber = mvaNumber;
	}
	
}
