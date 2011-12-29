package com.bagnet.nettracer.tracing.db;

import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Embeddable
public class GeneralRemark {
	public Agent getAgent() {
		return agent;
	}
	public void setAgent(Agent agent) {
		this.agent = agent;
	}
	public String getRemarktext() {
		return remarktext;
	}
	public void setRemarktext(String remarktext) {
		this.remarktext = remarktext;
	}
	public Date getRemarkdate() {
		return remarkdate;
	}
	public void setRemarkdate(Date remarkdate) {
		this.remarkdate = remarkdate;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getType() {
		return type;
	}
	
	@ManyToOne
	@JoinColumn(name = "agent_ID", nullable = false)
	private Agent agent;
	private String remarktext;
	private Date remarkdate;
	private int type;

	
}
