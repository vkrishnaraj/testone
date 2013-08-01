
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "links")
@Proxy(lazy = false)
public class Link implements Serializable {

	@Id
	@GeneratedValue
	private int id;
	
	@Column(length=100, nullable=false)
	private String description;
	
	@Column(length=255, nullable=false)
	private String link_address;
	
	@ManyToOne
	@JoinColumn(name="companycode_id")
	private Company company;
	
	@Column(length=50, nullable=false)
	private String image;


	public String getLink_address() {
		return link_address;
	}


	public void setLink_address(String link_address) {
		this.link_address = link_address;
	}


	public Company getCompany() {
		return company;
	}


	public void setCompany(Company company) {
		this.company = company;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	public String getDescription() {
		return description;
	}

	
	public void setDescription(String description) {
		this.description = description;
	}

	
	public int getId() {
		return id;
	}

	
	public void setId(int id) {
		this.id = id;
	}
}