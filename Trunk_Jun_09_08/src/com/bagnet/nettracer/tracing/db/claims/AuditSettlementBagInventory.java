package com.bagnet.nettracer.tracing.db.claims;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "z_b6_audit_claim_settlement_inventory")
@Proxy(lazy = false)
public class AuditSettlementBagInventory {

	@Id
	@GeneratedValue
	private long auditInventoryId;

	@Basic
	private int position;

	@Transient
	private boolean flaggedForRemoval;

	@ManyToOne(targetEntity = com.bagnet.nettracer.tracing.db.claims.AuditClaimSettlementBag.class)
	@JoinColumn(name = "auditBagId", nullable = false)
	private AuditClaimSettlementBag auditClaimSettlementBag;

	@Basic
	private int categoryType_ID;

	@Basic
	private String description;

	public long getAuditInventoryId() {
		return auditInventoryId;
	}

	public void setAuditInventoryId(long auditInventoryId) {
		this.auditInventoryId = auditInventoryId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCategoryType_ID() {
		return categoryType_ID;
	}

	public void setCategoryType_ID(int categoryType_ID) {
		this.categoryType_ID = categoryType_ID;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public boolean isFlaggedForRemoval() {
		return flaggedForRemoval;
	}

	public void setFlaggedForRemoval(boolean flaggedForRemoval) {
		this.flaggedForRemoval = flaggedForRemoval;
	}

	public AuditClaimSettlementBag getAuditClaimSettlementBag() {
		return auditClaimSettlementBag;
	}

	public void setAuditClaimSettlementBag(
			AuditClaimSettlementBag auditClaimSettlementBag) {
		this.auditClaimSettlementBag = auditClaimSettlementBag;
	}
}
