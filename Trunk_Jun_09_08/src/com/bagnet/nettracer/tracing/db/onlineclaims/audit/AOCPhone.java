package com.bagnet.nettracer.tracing.db.onlineclaims.audit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "oc_audit_phone")
@Proxy(lazy = false)
public class AOCPhone {
	@Id
	@GeneratedValue
	private long aid;

	@ManyToOne(targetEntity = com.bagnet.nettracer.tracing.db.onlineclaims.audit.AOCClaim.class)
	@JoinColumn(name = "auditClaimId", nullable = false)
	private AOCClaim claim;

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

	public AOCClaim getClaim() {
		return claim;
	}

	public void setClaim(AOCClaim claim) {
		this.claim = claim;
	}

	public long getAid() {
		return aid;
	}

	public void setAid(long aid) {
		this.aid = aid;
	}
}
