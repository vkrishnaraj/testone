package com.bagnet.nettracer.cronjob.tracing.dto;

import java.util.Date;

import com.bagnet.nettracer.tracing.db.TraceIncident;

public class ConsumerDTO {
	private TraceIncident incident;
	private String ohdId;
	private Date lastUpdated;
	private boolean lastIncident;

	public ConsumerDTO(TraceIncident incident, String ohdId, Date lastUpdated) {
		this.incident = incident;
		this.ohdId = ohdId;
		this.lastUpdated = lastUpdated;
	}

	public String getOhdId() {
		return ohdId;
	}

	public void setOhdId(String ohdId) {
		this.ohdId = ohdId;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public TraceIncident getIncident() {
		return incident;
	}

	public void setIncident(TraceIncident incident) {
		this.incident = incident;
	}

	/**
	 * @return the lastIncident
	 */
	public boolean isLastIncident() {
		return lastIncident;
	}

	/**
	 * @param lastIncident
	 *          the lastIncident to set
	 */
	public void setLastIncident(boolean lastIncident) {
		this.lastIncident = lastIncident;
	}
}
