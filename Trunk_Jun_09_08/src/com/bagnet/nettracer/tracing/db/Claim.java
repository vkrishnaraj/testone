/*
 * Created on Jul 14, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

/**
 * @author noah
 * 
 */
@Entity
@Table(name = "Claim")
@Proxy(lazy = false)
public class Claim implements Serializable {
	private int Claim_ID;
	private double claimamount;
	private String claimcurrency_ID;
	private double total;
	private String ssn;
	private String driverslicense;
	private String dlstate;
	private String commonnum;
	private String countryofissue;
	
	private Incident incident;
	private Status status;
	private ClaimProrate claimprorate;



	/**
	 * @return Returns the claimprorate.
	 * 
	 */
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "claimprorate_ID")
	public ClaimProrate getClaimprorate() {
		return claimprorate;
	}

	/**
	 * @param claimprorate
	 *          The claimprorate to set.
	 */
	public void setClaimprorate(ClaimProrate claimprorate) {
		this.claimprorate = claimprorate;
	}

	/**
	 * @return Returns the incident.
	 * 
	 */
	@OneToOne
	@JoinColumn(name = "incident_id")
	public Incident getIncident() {
		return incident;
	}

	/**
	 * @param incident
	 *          The incident to set.
	 */
	public void setIncident(Incident incident) {
		this.incident = incident;
	}

	/**
	 * @return Returns the status.
	 * 
	 */
	@ManyToOne
	@JoinColumn(name = "status_ID", nullable = false)
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
	 * @return Returns the claim_ID.
	 * 
	 */
	@Id
	@GeneratedValue
	public int getClaim_ID() {
		return Claim_ID;
	}

	/**
	 * @param claim_ID
	 *          The claim_ID to set.
	 */
	public void setClaim_ID(int claim_ID) {
		Claim_ID = claim_ID;
	}

	/**
	 * @return Returns the claimamount.
	 * 
	 */
	@Basic
	public double getClaimamount() {
		return claimamount;
	}

	/**
	 * @param claimamount
	 *          The claimamount to set.
	 */
	public void setClaimamount(double claimamount) {
		this.claimamount = claimamount;
	}

	/**
	 * @return Returns the total.
	 * 
	 */
	@Basic
	public double getTotal() {
		return total;
	}

	/**
	 * @param total
	 *          The total to set.
	 */
	public void setTotal(double total) {
		this.total = total;
	}

	/**
	 * @return Returns the ssn.
	 * 
	 */
	@Column(length = 9)
	public String getSsn() {
		return ssn;
	}

	/**
	 * @param ssn
	 *          The ssn to set.
	 */
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	/**
	 * @return Returns the dlstate.
	 * 
	 */
	@Column(length = 2)
	public String getDlstate() {
		return dlstate;
	}

	/**
	 * @param dlstate
	 *          The dlstate to set.
	 */
	public void setDlstate(String dlstate) {
		this.dlstate = dlstate;
	}

	/**
	 * @return Returns the driverslicense.
	 * 
	 */
	@Column(length = 10)
	public String getDriverslicense() {
		return driverslicense;
	}

	/**
	 * @param driverslicense
	 *          The driverslicense to set.
	 */
	public void setDriverslicense(String driverslicense) {
		this.driverslicense = driverslicense;
	}
	
	/**
	 * @return Returns the commonnum.
	 * 
	 */
	@Column(length = 20)
	public String getCommonnum() {
		return commonnum;
	}

	/**
	 * @param commonnum
	 *          The commonnum to set.
	 */
	public void setCommonnum(String commonnum) {
		this.commonnum = commonnum;
	}

	/**
	 * @return Returns the countryofissue.
	 * 
	 */
	@Column(length = 3)
	public String getCountryofissue() {
		return countryofissue;
	}

	/**
	 * @param countryofissue
	 *          The countryofissue to set.
	 */
	public void setCountryofissue(String countryofissue) {
		this.countryofissue = countryofissue;
	}
	
	/**
	 * @return Returns the claimcurrency_ID.
	 */
	@Column(length = 3, name = "currency_ID")
	public String getClaimcurrency_ID() {
		return claimcurrency_ID;
	}

	/**
	 * @param claimcurrency_ID
	 *          The claimcurrency_ID to set.
	 */
	public void setClaimcurrency_ID(String claimcurrency_ID) {
		this.claimcurrency_ID = claimcurrency_ID;
	}
}