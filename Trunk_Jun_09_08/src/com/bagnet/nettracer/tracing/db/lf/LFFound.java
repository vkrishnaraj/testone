package com.bagnet.nettracer.tracing.db.lf;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Proxy;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;

@Entity
@Proxy(lazy = true)
public class LFFound {

	@Id
	@GeneratedValue
	private long id;
	
	private String mvaNumber;
	
	private Date foundDate;

	@OneToOne(targetEntity = com.bagnet.nettracer.tracing.db.Station.class, cascade = CascadeType.ALL)
	private Station location;
	
	@OneToOne(targetEntity = com.bagnet.nettracer.tracing.db.Agent.class, cascade = CascadeType.ALL)
	private Agent agent;
	
	@OneToOne(targetEntity = com.bagnet.nettracer.tracing.db.Status.class, cascade = CascadeType.ALL)
	private Status status;

	@OneToOne(targetEntity = com.bagnet.nettracer.tracing.db.Status.class, cascade = CascadeType.ALL)
	private Status disposition;
	
	@OneToOne(targetEntity = com.bagnet.nettracer.tracing.db.lf.LFItem.class, cascade = CascadeType.ALL)
	private LFItem item;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMvaNumber() {
		return mvaNumber;
	}

	public void setMvaNumber(String mvaNumber) {
		this.mvaNumber = mvaNumber;
	}

	public Date getFoundDate() {
		return foundDate;
	}

	public void setFoundDate(Date foundDate) {
		this.foundDate = foundDate;
	}

	public Station getLocation() {
		return location;
	}

	public void setLocation(Station location) {
		this.location = location;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	public Status getDisposition() {
		return disposition;
	}

	public void setDisposition(Status disposition) {
		this.disposition = disposition;
	}

	public LFItem getItem() {
		return item;
	}

	public void setItem(LFItem item) {
		this.item = item;
	}
	
}
