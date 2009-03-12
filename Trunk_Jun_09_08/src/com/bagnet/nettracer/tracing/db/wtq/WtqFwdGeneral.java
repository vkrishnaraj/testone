package com.bagnet.nettracer.tracing.db.wtq;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.annotations.Proxy;

@Entity
@DiscriminatorValue(value = "FWD_GENERAL")
@Proxy(lazy = false)
public class WtqFwdGeneral extends WtqFwd {

	public WtqFwdGeneral() {
	}

	private String lossComments;
	private int lossCode;
	private String from_station;



	@Column
	public String getLossComments() {
		return lossComments;
	}

	public void setLossComments(String lossComments) {
		this.lossComments = lossComments;
	}
	
	@Basic
	public int getLossCode() {
		return lossCode;
	}
	
	public void setLossCode(int lossCode) {
		this.lossCode = lossCode;
	}

	@Column(length = 5)
	public String getFrom_station() {
		return from_station;
	}

	public void setFrom_station(String from_station) {
		this.from_station = from_station;
	}

}
