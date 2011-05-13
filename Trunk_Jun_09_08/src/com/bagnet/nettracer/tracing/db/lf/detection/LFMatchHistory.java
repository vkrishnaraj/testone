package com.bagnet.nettracer.tracing.db.lf.detection;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Proxy;

import com.bagnet.nettracer.tracing.db.Status;
import com.bagnet.nettracer.tracing.db.lf.LFFound;
import com.bagnet.nettracer.tracing.db.lf.LFLost;

@Entity
@Proxy(lazy = true)
public class LFMatchHistory {

	@Id
	@GeneratedValue
	private long id;
	
	@OneToOne(targetEntity = com.bagnet.nettracer.tracing.db.lf.LFLost.class, cascade = CascadeType.ALL)
	private LFLost lost;
	
	@OneToOne(targetEntity = com.bagnet.nettracer.tracing.db.lf.LFFound.class, cascade = CascadeType.ALL)
	private LFFound found;
	
	@OneToMany(mappedBy = "matchHistory", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<LFMatchDetail> details;
	
	@OneToOne(targetEntity = com.bagnet.nettracer.tracing.db.Status.class, cascade = CascadeType.ALL)
	private Status status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LFLost getLost() {
		return lost;
	}

	public void setLost(LFLost lost) {
		this.lost = lost;
	}

	public LFFound getFound() {
		return found;
	}

	public void setFound(LFFound found) {
		this.found = found;
	}

	public Set<LFMatchDetail> getDetails() {
		return details;
	}

	public void setDetails(Set<LFMatchDetail> details) {
		this.details = details;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
}
