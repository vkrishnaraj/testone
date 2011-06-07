package com.bagnet.nettracer.tracing.db.lf;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OrderBy;
import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy = false)
public class LFPerson {

	@Id
	@GeneratedValue
	private long id;
	
	private String lastName;
	
	private String firstName;
	
	private String middleName;
	
	private String email;
	
	@OneToOne(targetEntity = com.bagnet.nettracer.tracing.db.lf.LFAddress.class, cascade = CascadeType.ALL)
	private LFAddress address;
	
	@OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@OrderBy(clause = "id")
	@Fetch(FetchMode.SELECT)
	@Cascade(value = org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private Set<LFPhone> phones;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public LFAddress getAddress() {
		return address;
	}

	public void setAddress(LFAddress address) {
		this.address = address;
	}

	public Set<LFPhone> getPhones() {
		return phones;
	}

	public void setPhones(Set<LFPhone> phones) {
		this.phones = phones;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
