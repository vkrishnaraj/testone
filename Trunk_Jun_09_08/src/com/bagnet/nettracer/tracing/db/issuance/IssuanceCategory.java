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

import com.bagnet.nettracer.tracing.db.Company;
import com.bagnet.nettracer.tracing.db.documents.templates.Template;

@Entity
@Proxy(lazy = false)
@Table(name="issuance_category")
public class IssuanceCategory {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column(nullable=false, length=255)
	private String description;
	
	private boolean lostdelay;
	private boolean missing;
	private boolean damage;

	@ManyToOne
	@JoinColumn(name="document_id" )	
	private Template template;

	private boolean inventory;
	
	private boolean active;
	
	private boolean limitByPassenger;
	
	@ManyToOne
	@JoinColumn(name="company_code_id", nullable=false)
	private Company company;
	
	@Fetch(FetchMode.SELECT)
	@org.hibernate.annotations.OrderBy(clause="description")
	@OneToMany(mappedBy="category", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<IssuanceItem> items;

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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Set<IssuanceItem> getItems() {
		return items;
	}

	public void setItems(Set<IssuanceItem> items) {
		this.items = items;
	}

	public boolean isLostdelay() {
		return lostdelay;
	}

	public void setLostdelay(boolean lostdelay) {
		this.lostdelay = lostdelay;
	}

	public boolean isMissing() {
		return missing;
	}

	public void setMissing(boolean missing) {
		this.missing = missing;
	}

	public boolean isDamage() {
		return damage;
	}

	public void setDamage(boolean damage) {
		this.damage = damage;
	}

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public boolean isInventory() {
		return inventory;
	}

	public void setInventory(boolean inventory) {
		this.inventory = inventory;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isLimitByPassenger() {
		return limitByPassenger;
	}

	public void setLimitByPassenger(boolean limitByPassenger) {
		this.limitByPassenger = limitByPassenger;
	}

}
