package com.bagnet.nettracer.tracing.db.lf;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy = false)
public class LFDelivery implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 866779027387055317L;

	@Id
	@GeneratedValue
	private long id;
	
	private String trackingNumber;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "lost_id", nullable = true)
	@Fetch(FetchMode.SELECT)
	private LFLost lost;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public LFLost getLost() {
		return lost;
	}

	public void setLost(LFLost lost) {
		this.lost = lost;
	}
	
}
