package com.bagnet.nettracer.tracing.db.issuance;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy = false)
@Table(name="issuance_item")
public class IssuanceItem {

	@Id
	@GeneratedValue
	private long id;
	
	@Column(length=50)
	private String description;
	
	boolean active;
	
	@ManyToOne
	@JoinColumn(name="issuance_category_id")
	private IssuanceCategory category;
	
	@Fetch(FetchMode.SELECT)
	@org.hibernate.annotations.OrderBy(clause="station_id")
	@OneToMany(mappedBy="issuanceItem", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<IssuanceItemQuantity> quantityItems;
	
	@Fetch(FetchMode.SELECT)
	@org.hibernate.annotations.OrderBy(clause="station_id")
	@OneToMany(mappedBy="issuanceItem", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<IssuanceItemInventory> inventoryItems;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public IssuanceCategory getCategory() {
		return this.category;
	}
	
	public void setCategory(IssuanceCategory category) {
		this.category = category;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Set<IssuanceItemQuantity> getQuantityItems() {
		return quantityItems;
	}

	public void setQuantityItems(Set<IssuanceItemQuantity> quantityItems) {
		this.quantityItems = quantityItems;
	}

	public Set<IssuanceItemInventory> getInventoryItems() {
		return inventoryItems;
	}

	public void setInventoryItems(Set<IssuanceItemInventory> inventoryItems) {
		this.inventoryItems = inventoryItems;
	}
	
}
