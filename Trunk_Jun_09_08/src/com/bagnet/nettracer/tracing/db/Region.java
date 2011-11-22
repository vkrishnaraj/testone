package com.bagnet.nettracer.tracing.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy = false)
public class Region {
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public double getTarget() {
		return target;
	}
	public void setTarget(double target) {
		this.target = target;
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}
	public Company getCompany() {
		return company;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	public boolean isActive() {
		return active;
	}

	@Id
	@GeneratedValue
	private long id;
	private String name;
	private String director;
	private double target;
	private boolean active;
	
	@ManyToOne
	@JoinColumn(name = "companycode_ID", nullable = false)
	private Company company;
}
