/*
 * Created on Jul 13, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bagnet.nettracer.tracing.db;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "PassengerExp")
@Proxy(lazy = false)
public class PassengerExp implements Serializable {
	private static final long serialVersionUID = 1L;
	private int Passengerexp_ID;
	private String firstname;
	private String middlename;
	private String lastname;
	private String address1;
	private String address2;
	private String city;
	private String zip;
	private String homephone;
	private String workphone;
	private String mobile;
	private String email;
	private String state_ID;
	private String countrycode_ID;
	private String province;
	private ExpensePayout expensepayout;
	
	@Id
	@GeneratedValue
	public int getPassengerexp_ID() {
		return Passengerexp_ID;
	}
	public void setPassengerexp_ID(int passengerexp_ID) {
		Passengerexp_ID = passengerexp_ID;
	}
	
	@Column(length = 20)
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	@Column(length = 20)
	public String getMiddlename() {
		return middlename;
	}
	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}
	
	@Column(length = 20)
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	@Column(length = 50)
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	
	@Column(length = 50)
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	
	@Column(length = 50)
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	@Column(length = 11)
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	@Column(length = 50)	
	public String getHomephone() {
		return homephone;
	}
	public void setHomephone(String homephone) {
		this.homephone = homephone;
	}
	
	@Column(length = 50)	
	public String getWorkphone() {
		return workphone;
	}
	public void setWorkphone(String workphone) {
		this.workphone = workphone;
	}
	
	@Column(length = 50)	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Column(length = 100)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(length = 2)
	public String getState_ID() {
		return state_ID;
	}
	public void setState_ID(String state_ID) {
		this.state_ID = state_ID;
	}
	
	@Column(length = 3)
	public String getCountrycode_ID() {
		return countrycode_ID;
	}
	public void setCountrycode_ID(String countrycode_ID) {
		this.countrycode_ID = countrycode_ID;
	}
	
	@Column(length = 100)
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	
	@ManyToOne
	@JoinColumn(name = "expensepayout_ID", nullable = false)
	@Fetch(FetchMode.SELECT)
	public ExpensePayout getExpensepayout() {
		return expensepayout;
	}
	public void setExpensepayout(ExpensePayout expensepayout) {
		this.expensepayout = expensepayout;
	}

}