package com.bagnet.nettracer.tracing.db.lf;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;


@Entity
@Proxy(lazy = false)
public class LFSubCategory {
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
	public LFCategory getParent() {
		return parent;
	}
	public void setParent(LFCategory parent) {
		this.parent = parent;
	}
	@Id
	@GeneratedValue
	private long id;
	private String description;
	@ManyToOne
	@JoinColumn(name = "parent_id", nullable = false)
	@Fetch(FetchMode.SELECT)
	private LFCategory parent;
}
