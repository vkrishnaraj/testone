package com.bagnet.nettracer.tracing.db.lf;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy = false)
public class LFLog {

	@Id
	@GeneratedValue
	private long id;

    @Temporal(TemporalType.TIMESTAMP)
	private Date stamp;
	
	@Column(length = 50)
	private String agent;

	@Column(length = 10)
	private String stationcode;

	@Column(length = 50)
	private String event;
	
	@Basic
	private int lflost_id;
	
	@Basic
	private int lffound_id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getStamp() {
		return stamp;
	}

	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getStationcode() {
		return stationcode;
	}

	public void setStationcode(String stationcode) {
		this.stationcode = stationcode;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public int getLflost_id() {
		return lflost_id;
	}

	public void setLflost_id(int lflost_id) {
		this.lflost_id = lflost_id;
	}

	public int getLffound_id() {
		return lffound_id;
	}

	public void setLffound_id(int lffound_id) {
		this.lffound_id = lffound_id;
	}
	
}


