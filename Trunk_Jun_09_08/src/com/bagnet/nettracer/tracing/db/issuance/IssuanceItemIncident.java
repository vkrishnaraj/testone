package com.bagnet.nettracer.tracing.db.issuance;

import java.util.Date;

import javax.persistence.CascadeType;
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
import javax.persistence.Transient;

import org.hibernate.annotations.Proxy;

import com.bagnet.nettracer.tracing.db.Agent;
import com.bagnet.nettracer.tracing.db.Incident;
import com.bagnet.nettracer.tracing.db.documents.Document;

@Entity
@Proxy(lazy = false)
@Table(name="issuance_item_incident")
public class IssuanceItemIncident {

	@Id
	@GeneratedValue
	private long id;
	
	private int quantity;
	
	private boolean returned;

	@Column(name="returndate", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date returnDate;

	@ManyToOne
	@JoinColumn(name = "issuance_item_quantity_id")
	private IssuanceItemQuantity issuanceItemQuantity;

	@ManyToOne
	@JoinColumn(name = "issuance_item_inventory_id")
	private IssuanceItemInventory issuanceItemInventory;

	@ManyToOne
	@JoinColumn(name = "incident_id")
	private Incident incident;

	@Column(name="issuedate", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date issueDate;

	@ManyToOne
	@JoinColumn(name = "issueagent_id")
	private Agent issueAgent;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "document_id")
	private Document document;
	
	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}
	
	@Transient
	boolean updated = false;

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

	public boolean isReturned() {
		return returned;
	}

	public void setReturned(boolean returned) {
		this.returned = returned;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public IssuanceItemQuantity getIssuanceItemQuantity() {
		return issuanceItemQuantity;
	}

	public void setIssuanceItemQuantity(IssuanceItemQuantity issuanceItemQuantity) {
		this.issuanceItemQuantity = issuanceItemQuantity;
	}

	public IssuanceItemInventory getIssuanceItemInventory() {
		return issuanceItemInventory;
	}

	public void setIssuanceItemInventory(IssuanceItemInventory issuanceItemInventory) {
		this.issuanceItemInventory = issuanceItemInventory;
	}

	public Incident getIncident() {
		return incident;
	}

	public void setIncident(Incident incident) {
		this.incident = incident;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public Agent getIssueAgent() {
		return issueAgent;
	}

	public void setIssueAgent(Agent issueAgent) {
		this.issueAgent = issueAgent;
	}

	public boolean isUpdated() {
		return updated;
	}

	public void setUpdated(boolean updated) {
		this.updated = updated;
	}
	
}
