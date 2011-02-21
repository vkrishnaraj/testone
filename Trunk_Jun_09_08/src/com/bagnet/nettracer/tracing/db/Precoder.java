package com.bagnet.nettracer.tracing.db;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="precoder")
public class Precoder {
	long precoder_id;
	Incident incident;
	Date createdate;
	int losscode;
	Station faultStation;
	String remark;
	
	@Id
	@GeneratedValue
	public long getPrecoder_id() {
		return precoder_id;
	}
	public void setPrecoder_id(long precoder_id) {
		this.precoder_id = precoder_id;
	}
	
	@OneToOne(targetEntity = com.bagnet.nettracer.tracing.db.Incident.class)
	@JoinColumn(name = "Incident_ID")
	public Incident getIncident() {
		return incident;
	}
	public void setIncident(Incident incident) {
		this.incident = incident;
	}
	
	@Temporal(value = TemporalType.DATE)
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	
	@Basic
	public int getLosscode() {
		return losscode;
	}
	public void setLosscode(int losscode) {
		this.losscode = losscode;
	}
	
	@ManyToOne
	@JoinColumn(name = "faultstation_id")
	public Station getFaultStation() {
		return faultStation;
	}
	public void setFaultStation(Station faultStation) {
		this.faultStation = faultStation;
	}
	
	@Column(length = 10)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
