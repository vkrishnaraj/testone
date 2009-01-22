package com.bagnet.nettracer.tracing.db.claims;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "z_b6_claim_settlement_bag")
@Proxy(lazy = false)
public class ClaimSettlementBag {

	@Id
	@GeneratedValue
	private long bagId;

	@Column(length = 2)
	private String color;

	@Column(length = 2)
	private String type;

	@Column(length = 100)
	private String manufacturer;

	@ManyToOne(targetEntity = com.bagnet.nettracer.tracing.db.claims.ClaimSettlement.class)
	@JoinColumn(name = "claimSettlementId", nullable = false)
	private ClaimSettlement claimSettlement;

	@OneToMany(mappedBy = "claimSettlementBag")
	private Set<SettlementBagInventory> inventory;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public ClaimSettlement getClaimSettlement() {
		return claimSettlement;
	}

	public void setClaimSettlement(ClaimSettlement claimSettlement) {
		this.claimSettlement = claimSettlement;
	}

	public long getBagId() {
		return bagId;
	}

	public void setBagId(long bagId) {
		this.bagId = bagId;
	}

	public Set<SettlementBagInventory> getInventory() {
		return inventory;
	}

	public void setInventory(Set<SettlementBagInventory> inventory) {
		this.inventory = inventory;
	}

}
