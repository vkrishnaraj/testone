package com.bagnet.nettracer.tracing.db.salvage;

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
import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "salvage_box")
@Proxy(lazy = false)
public class SalvageBox {

	private int boxId;
	private int displayId;
	private Salvage salvage;
	private String description;
	private int type; // valid values are: 0 - default, 1-low value, 2-high value
	private Set<SalvageItem> salvageItems;
	
	@Id
	@GeneratedValue
	@Column(name="box_id")
	public int getBoxId() {
		return boxId;
	}
	
	public void setBoxId(int boxId) {
		this.boxId = boxId;
	}
	
	@Column(name="display_id", nullable=false)
	public int getDisplayId() {
		return this.displayId;
	}
	
	public void setDisplayId(int displayId) {
		this.displayId = displayId;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="salvage_id", nullable=false)
	public Salvage getSalvage() {
		return salvage;
	}
	
	public void setSalvage(Salvage salvage) {
		this.salvage = salvage;
	}

	@Column(name="description")
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name="type", length=1)
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}

	@OneToMany(mappedBy = "itemId", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@OrderBy(clause = "item_id")
	@Fetch(FetchMode.SELECT)
	public Set<SalvageItem> getSalvageItems() {
		return salvageItems;
	}
	
	public void setSalvageItems(Set<SalvageItem> salvageItems) {
		this.salvageItems = salvageItems;
	}
	
}
