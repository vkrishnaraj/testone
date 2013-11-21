package com.bagnet.nettracer.tracing.db.issuance;

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

import org.hibernate.annotations.Proxy;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Station;

@Entity
@Proxy(lazy = false)
@Table(name="audit_issuance_item_quantity")
public class AuditIssuanceItemQuantity {

	@Id
	@GeneratedValue
	@Column(name="audit_id")
	private long auditID;
	
	private long id;
	
	private int quantity;
	
	@Column(name="minimum_active_quantity")
	private int minimuActiveQuantity;
	
	@Column(name="quantity_change")
	private int quantityChange;

	@ManyToOne
	@JoinColumn(name = "station_id")
	private Station station;

	@ManyToOne
	@JoinColumn(name = "issuance_item_id")
	private IssuanceItem issuanceItem;
	
	@Column(name="incident_id", length=13)
	private String incidentID;

	@ManyToOne
	@JoinColumn(name = "editagent_id")
	private Agent editAgent;

	@Column(name="editdate", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date editDate;
	
	@Column(name="verified_incident")
	private boolean verifiedIncident;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	public IssuanceItem getIssuanceItem() {
		return issuanceItem;
	}

	public void setIssuanceItem(IssuanceItem issuanceItem) {
		this.issuanceItem = issuanceItem;
	}

	public long getAuditID() {
		return auditID;
	}

	public void setAuditID(long auditID) {
		this.auditID = auditID;
	}

	public int getQuantityChange() {
		return quantityChange;
	}

	public void setQuantityChange(int quantityChange) {
		this.quantityChange = quantityChange;
	}

	public String getIncidentID() {
		return incidentID;
	}

	public void setIncidentID(String incidentID) {
		this.incidentID = incidentID;
	}

	public Agent getEditAgent() {
		return editAgent;
	}

	public void setEditAgent(Agent editAgent) {
		this.editAgent = editAgent;
	}

	public Date getEditDate() {
		return editDate;
	}

	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}

	public int getMinimuActiveQuantity() {
		return minimuActiveQuantity;
	}

	public void setMinimuActiveQuantity(int minimuActiveQuantity) {
		this.minimuActiveQuantity = minimuActiveQuantity;
	}

	public boolean isVerifiedIncident() {
		return verifiedIncident;
	}

	public void setVerifiedIncident(boolean verifiedIncident) {
		this.verifiedIncident = verifiedIncident;
	}
	
}
