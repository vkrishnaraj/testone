package com.bagnet.nettracer.tracing.db.lf;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

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
	
	private String category;
	
	private String subCategory;
	
	private String color;
	
	@ManyToOne
	@JoinColumn(name = "status_ID", nullable = false)
	private Status status;

	@OneToOne(targetEntity = com.bagnet.nettracer.tracing.db.Status.class, cascade = CascadeType.ALL)
	private Status disposition;
	
	@ManyToOne
	@JoinColumn(name = "lost_id", nullable = false)
	@Fetch(FetchMode.SELECT)
	private LFLost lost;

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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
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
	
}
