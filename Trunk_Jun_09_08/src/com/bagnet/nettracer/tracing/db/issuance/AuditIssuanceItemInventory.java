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
import com.bagnet.nettracer.tracing.db.Status;

@Entity
@Proxy(lazy = false)
@Table(name="audit_issuance_item_inventory")
public class AuditIssuanceItemInventory {

	@Id
	@GeneratedValue
	@Column(name="audit_id")
	private long auditID;
	
	private long id;
	
	@Column(name = "trade_type")
	private int tradeType;
	
	@Column(length = 100)
	private String description;
	
	@Column(length = 20)
	private String barcode;

	@ManyToOne
	@JoinColumn(name = "inventory_status_id")
	private Status inventoryStatus;
	
	@ManyToOne
	@JoinColumn(name = "station_id")
	private Station station;

	@ManyToOne
	@JoinColumn(name = "issuance_item_id")
	private IssuanceItem issuanceItem;
	
	@Column(name="incident_id", length=13)
	private String incidentID;

	@Column(name="issuedate", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date issueDate;
	
	@Column(length=150)
	private String reason;

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

	public int getTradeType() {
		return tradeType;
	}

	public void setTradeType(int tradeType) {
		this.tradeType = tradeType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public Status getInventoryStatus() {
		return inventoryStatus;
	}

	public void setInventoryStatus(Status inventoryStatus) {
		this.inventoryStatus = inventoryStatus;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public boolean isVerifiedIncident() {
		return verifiedIncident;
	}

	public void setVerifiedIncident(boolean verifiedIncident) {
		this.verifiedIncident = verifiedIncident;
	}
	
}
