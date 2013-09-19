package com.bagnet.nettracer.tracing.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;
/**
 * @author Sean Fine
 * Class representing Claim Type object
 */
@Entity
@Table(name = "claim_type")
@Proxy(lazy = false)
public class Claim_Type {

	private int id;
	private String description;

	@Id @GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


}
