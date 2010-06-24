package com.nettracer.claims.core.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="airport")
public class Airport implements Serializable{
	private static final long serialVersionUID = 4025584788140149519L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	
	private String airportCode ;
	private String airportDesc ;
	private Integer country;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAirportCode() {
		return airportCode;
	}
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}
	public String getAirportDesc() {
		return airportDesc;
	}
	public void setAirportDesc(String airportDesc) {
		this.airportDesc = airportDesc;
	}
	public Integer getCountry() {
		return country;
	}
	public void setCountry(Integer country) {
		this.country = country;
	}

	
	
}
