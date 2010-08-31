package com.bagnet.nettracer.tracing.db.dr;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.Station;
import com.bagnet.nettracer.tracing.db.Status;

@Entity
@Table(name="dispute")
public class Dispute {

	private long dispute_res_id;
	private Status status;
	private Date created_timestamp;
	private Incident incident;
	private Station suggested_fault_station;
	private int suggested_loss_code;
	private Station determined_fault_station;
	private int determined_loss_code;
	
	@Id
	@GeneratedValue
	public long getDispute_res_id() {
		return dispute_res_id;
	}
	public void setDispute_res_id(long dispute_res_id) {
		this.dispute_res_id = dispute_res_id;
	}
	
	@ManyToOne
	@JoinColumn(name = "status_ID", nullable = false)
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreated_timestamp() {
		return created_timestamp;
	}
	public void setCreated_timestamp(Date created_timestamp) {
		this.created_timestamp = created_timestamp;
	}
	public Incident getIncident() {
		return incident;
	}
	public void setIncident(Incident incident) {
		this.incident = incident;
	}
	public Station getSuggested_fault_station() {
		return suggested_fault_station;
	}
	public void setSuggested_fault_station(Station suggested_fault_station) {
		this.suggested_fault_station = suggested_fault_station;
	}
	public int getSuggested_loss_code() {
		return suggested_loss_code;
	}
	public void setSuggested_loss_code(int suggested_loss_code) {
		this.suggested_loss_code = suggested_loss_code;
	}
	public Station getDetermined_fault_station() {
		return determined_fault_station;
	}
	public void setDetermined_fault_station(Station determined_fault_station) {
		this.determined_fault_station = determined_fault_station;
	}
	public int getDetermined_loss_code() {
		return determined_loss_code;
	}
	public void setDetermined_loss_code(int determined_loss_code) {
		this.determined_loss_code = determined_loss_code;
	}
	
	
	
}
