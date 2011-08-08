package com.nettracer.claims.core.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="airline")
public class Airline implements Serializable{
	private static final long serialVersionUID = 4025584788140149519L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;
	private String airlineCode ;
	private String airlineDesc ;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAirlineCode() {
		return airlineCode;
	}
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}
	public String getAirlineDesc() {
		return airlineDesc;
	}
	public void setAirlineDesc(String airlineDesc) {
		this.airlineDesc = airlineDesc;
	}

}
