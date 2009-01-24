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
@Table(name = "z_b6_claim_settlement_inventory")
@Proxy(lazy = false)
public class SettlementBagInventory {

	@Id
	@GeneratedValue
	private long inventoryId;

	@Basic
	private int position;
	
	@Transient
	private boolean flaggedForRemoval;

	@ManyToOne(targetEntity = com.bagnet.nettracer.tracing.db.claims.ClaimSettlementBag.class)
	@JoinColumn(name = "bagId", nullable = false)
	private ClaimSettlementBag claimSettlementBag;

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

	public ClaimSettlementBag getClaimSettlementBag() {
		return claimSettlementBag;
	}

	public void setClaimSettlementBag(ClaimSettlementBag claimSettlementBag) {
		this.claimSettlementBag = claimSettlementBag;
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
}
