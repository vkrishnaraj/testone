package com.bagnet.nettracer.tracing.db.claims;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "z_b6_audit_claim_settlement_inventory")
@Proxy(lazy = false)
public class AuditSettlementBagInventory {

	@Id
	@GeneratedValue
	private long inventoryId;

	@ManyToOne(targetEntity = com.bagnet.nettracer.tracing.db.claims.ClaimSettlementBag.class)
	@JoinColumn(name = "bagId", nullable = false)
	private AuditClaimSettlementBag claimSettlementBag;

	@Basic
	private int categoryType_ID;

	@Basic
	private String description;

	public long getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(long inventoryId) {
		this.inventoryId = inventoryId;
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

	public AuditClaimSettlementBag getClaimSettlementBag() {
		return claimSettlementBag;
	}

	public void setClaimSettlementBag(AuditClaimSettlementBag claimSettlementBag) {
		this.claimSettlementBag = claimSettlementBag;
	}

}
