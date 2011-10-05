package com.bagnet.nettracer.tracing.db.lf;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy = false)
public class LFCategory {
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
	public Set<LFSubCategory> getSubcategories() {
		return subcategories;
	}
	public void setSubcategories(Set<LFSubCategory> subcategories) {
		this.subcategories = subcategories;
	}
	
	@Id
	@GeneratedValue
	private long id;
	String description;
	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@org.hibernate.annotations.OrderBy(clause = "id")
	Set<LFSubCategory> subcategories;
}
