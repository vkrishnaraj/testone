package com.bagnet.nettracer.tracing.dto;

import java.util.Date;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;

public class LFSearchDTO {
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getOpenDate() {
		return openDate;
	}
	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}
	public Date getCloseDate() {
		return closeDate;
	}
	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}
	public void setStation(Station station) {
		this.station = station;
	}
	public Station getStation() {
		return station;
	}
	public void setAgent(Agent agent) {
		this.agent = agent;
	}
	public Agent getAgent() {
		return agent;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Status getStatus() {
		return status;
	}
	private String firstName;
	private String lastName;
	private long id;
	private Date openDate;
	private Date closeDate;
	private Station station;
	private Agent agent;
	private Status status;
}
