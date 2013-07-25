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
	private long auditID;
	
	private long id;
	
	private int quantity;
	
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
}
