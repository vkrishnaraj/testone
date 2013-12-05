package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "label")
public class Label implements Serializable {
	private static final long serialVersionUID = -8553022266983189284L;

	@Id
	@GeneratedValue
	private long id;

	@Column(length = 100)
	private String text;

	@Temporal(value = TemporalType.TIMESTAMP)
	private Date lastUpdate;

	@ManyToOne
	@JoinColumn(name = "agent_id")
	private Agent agent;

	public long getId() {
		return id;
	}

	public void setId(long labelId) {
		this.id = labelId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	@Transient
	public String getHtmlFormatedText() {
		return (text == null || text.trim().isEmpty()) ? "</br>" : text.replaceAll("[\n\r]", "</br>");
	}
}
