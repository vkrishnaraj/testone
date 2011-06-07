package com.bagnet.nettracer.tracing.db.lf;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Proxy;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.utils.DateUtils;

@Entity
@Proxy(lazy = true)
public class LFLost {
	
	@Id
	@GeneratedValue
	private long id;
	
	@OneToOne(targetEntity = com.bagnet.nettracer.tracing.db.Agent.class, cascade = CascadeType.ALL)
	private Agent agent;
	
	@OneToOne(targetEntity = com.bagnet.nettracer.tracing.db.Station.class, cascade = CascadeType.ALL)
	private Station location;
	
	@OneToOne(targetEntity = com.bagnet.nettracer.tracing.db.Status.class, cascade = CascadeType.ALL)
	private Status status;
	
	private Date openDate;
	
	private Date closeDate;
	
	private Date emailSentDate;
	
	private String remarks;
	
	@OneToOne(targetEntity = com.bagnet.nettracer.tracing.db.lf.LFPerson.class, cascade = CascadeType.ALL)
	private LFPerson client;
	
	@OneToOne(targetEntity = com.bagnet.nettracer.tracing.db.lf.LFReservation.class, cascade = CascadeType.ALL)
	private LFReservation reservation;
	
	@OneToMany(mappedBy = "lost", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<LFItem> items;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public Station getLocation() {
		return location;
	}

	public void setLocation(Station location) {
		this.location = location;
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

	public LFPerson getClient() {
		return client;
	}

	public void setClient(LFPerson client) {
		this.client = client;
	}

	public LFReservation getReservation() {
		return reservation;
	}

	public void setReservation(LFReservation reservation) {
		this.reservation = reservation;
	}

	public Set<LFItem> getItems() {
		return items;
	}

	public void setItems(Set<LFItem> items) {
		this.items = items;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getEmailSentDate() {
		return emailSentDate;
	}

	public void setEmailSentDate(Date emailSentDate) {
		this.emailSentDate = emailSentDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getDisOpenDate(String dateFormat) {
		return DateUtils.formatDate(openDate, dateFormat, "", null);
	}
	
}
