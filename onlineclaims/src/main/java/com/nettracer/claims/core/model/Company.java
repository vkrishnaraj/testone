package com.nettracer.claims.core.model;

import java.io.Serializable;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @author Utpal Description: This is needed for binding the Application Data
 *         with the front end
 */

@Entity
@Table(name="company")
public class Company implements Serializable{
	
	private static final long serialVersionUID = 9163267518157784238L;
	
	@Id
	private String code;  
	private String name;  
	private String address;  
	private String city;  
	private String state;  
	private String country;  
	private String replyAddress;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getReplyAddress() {
		return replyAddress;
	}
	public void setReplyAddress(String replyAddress) {
		this.replyAddress = replyAddress;
	}
	
	
	
}
