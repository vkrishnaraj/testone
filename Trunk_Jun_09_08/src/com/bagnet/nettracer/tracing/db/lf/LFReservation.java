package com.bagnet.nettracer.tracing.db.lf;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

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
	
	@OneToOne(targetEntity = com.bagnet.nettracer.tracing.db.Station.class, cascade = CascadeType.ALL)
	private Station pickupLocation;
	
	@OneToOne(targetEntity = com.bagnet.nettracer.tracing.db.Station.class, cascade = CascadeType.ALL)
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
