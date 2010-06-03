package com.bagnet.nettracer.tracing.db.onlineclaims;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "oc_address")
@Proxy(lazy = false)
public class OCAddress {
	@Id
	@GeneratedValue
	private long id;
	
	
//	@ManyToOne(targetEntity = com.bagnet.nettracer.tracing.db.onlineclaims.OnlineClaim.class)
//	@JoinColumn(name = "claimId", nullable = false)
//	private OnlineClaim claim;
	
	@Column(length = 50)
	private String address1;
	
	@Column(length = 50)
	private String address2;
	
	@Column(length = 50)
	private String city;
	
	@Column(length = 50)
	private String stateProvince;
	
	@Column(length = 20)
	private String postalCode;
	
	@Column(length = 3)
	private String country;

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStateProvince() {
		return stateProvince;
	}

	public void setStateProvince(String stateProvince) {
		this.stateProvince = stateProvince;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public long getId() {
  	return id;
  }

	public void setId(long id) {
  	this.id = id;
  }
//
//	public OnlineClaim getClaim() {
//		return claim;
//	}
//
//	public void setClaim(OnlineClaim claim) {
//		this.claim = claim;
//	}

}
