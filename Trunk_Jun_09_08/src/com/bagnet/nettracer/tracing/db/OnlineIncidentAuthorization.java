package com.bagnet.nettracer.tracing.db;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "online_incident_authorization")
@Proxy(lazy = false)
public class OnlineIncidentAuthorization {

	@Id
	@GeneratedValue
	private int id;

	@Column(name="first_name", length = 20)
	private String firstName;

	@Column(name="last_name", length = 20)
	private String lastName;

	@Column(length = 10)
	private String pnr;

	@Column(name="incident_ID", length = 13)
	private String incidentID;

	@Column(name="expire_time")
	private Date expireTime;
	
	@OneToOne(mappedBy = "oia", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	private ProactiveNotification pcn;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *          the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return Returns the firstName.
	 * 
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *          The firstName to set.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return Returns the lastName.
	 * 
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *          The lastName to set.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return Returns the pnr.
	 * 
	 */
	public String getPnr() {
		return pnr;
	}

	/**
	 * @param pnr
	 *          The pnr to set.
	 */
	public void setPnr(String pnr) {
		this.pnr = pnr;
	}

	/**
	 * @return Returns the incidentID.
	 * 
	 */
	public String getIncidentID() {
		return incidentID;
	}

	/**
	 * @param incidentID
	 *          The incidentID to set.
	 */
	public void setIncidentID(String incidentID) {
		this.incidentID = incidentID;
	}

	/**
	 * @return Returns the expireTime.
	 * 
	 */
	public Date getExpireTime() {
		return expireTime;
	}

	/**
	 * @param expireTime
	 *          The expireTime to set.
	 */
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	public ProactiveNotification getPcn() {
		return pcn;
	}

	public void setPcn(ProactiveNotification pcn) {
		this.pcn = pcn;
	}

}
