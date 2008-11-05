package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

import com.bagnet.nettracer.tracing.db.wtq.WorldTracerQueue;

@Entity
@Table(name="other_system_information")
@Proxy(lazy=false)
public class OtherSystemInformation implements Serializable {

	private int id;
	private Incident incident;
	private String info;

	/**
	 * @return the id
	 */
	@Id @GeneratedValue
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
	 * @return the text
	 */
	@Basic
	public String getInfo() {
		return info;
	}

	/**
	 * @param text
	 *          the text to set
	 */
	public void setInfo(String info) {
		this.info = info;
	}

	/**
	 * @return the incident
	 */
	@ManyToOne(targetEntity = Incident.class)
	@JoinColumn(name = "incident_id", nullable = true)
	public Incident getIncident() {
		return incident;
	}

	/**
	 * @param incident the incident to set
	 */
	public void setIncident(Incident incident) {
		this.incident = incident;
	}
}
