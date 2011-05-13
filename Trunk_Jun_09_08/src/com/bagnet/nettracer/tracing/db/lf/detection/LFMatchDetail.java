package com.bagnet.nettracer.tracing.db.lf.detection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy = true)
public class LFMatchDetail {
	
	@Id
	@GeneratedValue
	private long id;
	
	private String description;
	
	@ManyToOne(targetEntity = com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory.class)
	private LFMatchHistory matchHistory;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LFMatchHistory getMatchHistory() {
		return matchHistory;
	}

	public void setMatchHistory(LFMatchHistory matchHistory) {
		this.matchHistory = matchHistory;
	}
	
}
