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
@Table(name="issuance_item_inventory")
public class IssuanceItemInventory {

	@Id
	@GeneratedValue
	private long id;
	
	@Column(name = "trade_type")
	private int tradeType;

	@Column(name = "cost")
	private double cost;
		
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

	public String getIncidentID() {
		return incidentID;
	}

	public void setIncidentID(String incidentID) {
		this.incidentID = incidentID;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public boolean isVerifiedIncident() {
		return verifiedIncident;
	}

	public void setVerifiedIncident(boolean verifiedIncident) {
		this.verifiedIncident = verifiedIncident;
	}
	
	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}
	
}
