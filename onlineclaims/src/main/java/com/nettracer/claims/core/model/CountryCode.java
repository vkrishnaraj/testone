package com.nettracer.claims.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="countrycode")
public class CountryCode implements Serializable{

	private static final long serialVersionUID = -2568718205752172533L;

	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="CountryCode_ID")
	private String id;
	
	private String country ;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	
	
	
}
