package com.bagnet.nettracer.tracing.db.lf;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

import com.bagnet.nettracer.tracing.db.Status;

@Entity
@Proxy(lazy = false)
public class LFItem {

	@Id
	@GeneratedValue
	private long id;
	
	private String serialNumber;
	
	private String brand;
	
	private String description;
	
	private int category;
	
	private int subCategory;
	
	private String color;
	
	private String trackingNumber;
	
	private int type;
	
	@ManyToOne
	@JoinColumn(name = "disposition_status_ID", nullable = true)
	private Status disposition;
	
	@ManyToOne
	@JoinColumn(name = "lost_id", nullable = true)
	@Fetch(FetchMode.SELECT)
	private LFLost lost;

	@OneToOne(targetEntity = com.bagnet.nettracer.tracing.db.lf.LFFound.class, cascade = CascadeType.ALL)
	private LFFound found;
	
	@Transient
	private boolean selected;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(int subCategory) {
		this.subCategory = subCategory;
	}

	public Status getDisposition() {
		return disposition;
	}

	public void setDisposition(Status disposition) {
		this.disposition = disposition;
	}

	public LFLost getLost() {
		return lost;
	}

	public void setLost(LFLost lost) {
		this.lost = lost;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	public int getDispositionId() {
		return getDisposition().getStatus_ID();
	}
	
	public void setDispositionId(int dispositionId) {
		if (disposition != null) {
			getDisposition().setStatus_ID(dispositionId);
		}
	}

	public void setFound(LFFound found) {
		this.found = found;
	}

	public LFFound getFound() {
		return found;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
}
