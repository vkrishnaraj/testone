package com.bagnet.nettracer.tracing.db.salvage;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "salvage_item")
@Proxy(lazy = false)
public class SalvageItem {

	private int itemId;
	private SalvageBox box;
	private String description = "";
	private int quantity = 1;
	private int type; // valid values are: 0 - default, 1-low value, 2-high value
	private String lostAndFoundId;
	
	@Id
	@GeneratedValue
	@Column(name="item_id")
	public int getItemId() {
		return itemId;
	}
	
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	
	@ManyToOne
	@JoinColumn(name = "box_id", nullable = false)
	@Fetch(FetchMode.SELECT)
	public SalvageBox getBox() {
		return box;
	}
	
	public void setBox(SalvageBox box) {
		this.box = box;
	}
	
	@Column(name="description")
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name="quantity")
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	@Column(name="type", length=1)
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	@Column(name="lostandfound_id", length=13, nullable=true)
	public String getLostAndFoundId() {
		return this.lostAndFoundId;
	}
	
	public void setLostAndFoundId(String lostAndFoundId) {
		this.lostAndFoundId = lostAndFoundId;
	}
	
}
