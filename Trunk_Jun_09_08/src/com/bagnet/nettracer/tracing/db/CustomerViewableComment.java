package com.bagnet.nettracer.tracing.db;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name="customer_viewable_comments")
@Proxy(lazy=false)
public class CustomerViewableComment {

	private int id;
	private Incident incident;
	private String comment;

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
	public String getComment() {
		return comment;
	}

	/**
	 * @param text
	 *          the text to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
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
