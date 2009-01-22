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
@Table(name = "z_b6_claim_settlement_inventory")
@Proxy(lazy = false)
public class SettlementBagInventory {

	@Id
	@GeneratedValue
	private long inventoryId;

	@ManyToOne(targetEntity = com.bagnet.nettracer.tracing.db.claims.ClaimSettlement.class)
	@JoinColumn(name = "claimSettlementId", nullable = false)
	private ClaimSettlementBag claimSettlementId;

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

	public ClaimSettlementBag getClaimSettlementId() {
		return claimSettlementId;
	}

	public void setClaimSettlementId(ClaimSettlementBag claimSettlementId) {
		this.claimSettlementId = claimSettlementId;
	}
}
