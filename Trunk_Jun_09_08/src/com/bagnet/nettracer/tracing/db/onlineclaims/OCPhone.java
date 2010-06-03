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
@Table(name = "oc_phone")
@Proxy(lazy = false)
public class OCPhone {
	@Id
	@GeneratedValue
	private long id;
	
	@ManyToOne(targetEntity = com.bagnet.nettracer.tracing.db.onlineclaims.OnlineClaim.class)
	@JoinColumn(name = "claimId", nullable = false)
	private OnlineClaim claim;
	
	@Column(length = 20)
	private String phoneNumber;
	
	@Column(length = 10)
	private String phoneType;
	
	public String getPhoneNumber() {
  	return phoneNumber;
  }
	public void setPhoneNumber(String phoneNumber) {
  	this.phoneNumber = phoneNumber;
  }
	public String getPhoneType() {
  	return phoneType;
  }
	public void setPhoneType(String phoneType) {
  	this.phoneType = phoneType;
  }
	public long getId() {
  	return id;
  }
	public void setId(long id) {
  	this.id = id;
  }
	public OnlineClaim getClaim() {
		return claim;
	}
	public void setClaim(OnlineClaim claim) {
		this.claim = claim;
	}
}
