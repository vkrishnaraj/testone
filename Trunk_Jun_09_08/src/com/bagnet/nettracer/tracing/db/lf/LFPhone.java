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
public class LFPhone {

	public static final int PRIMARY = 1;
	public static final int SECONDARY = 2;
	
	public static final int HOME = 3;
	public static final int MOBILE = 4;
	public static final int WORK = 5;
	public static final int ALTERNATE = 6;
	
	@Id
	@GeneratedValue
	private long id;
	
	private String phoneNumber;
	
	private String extension;
	
	/* Primary/Secondary */
	private int numberType;
	
	/* Home/Mobile/Work/Alternate */
	private int phoneType;
	
	@ManyToOne
	@JoinColumn(name = "person_id", nullable = false)
	@Fetch(FetchMode.SELECT)
	private LFPerson person;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getNumberType() {
		return numberType;
	}

	public void setNumberType(int numberType) {
		this.numberType = numberType;
	}
	
	public int getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(int phoneType) {
		this.phoneType = phoneType;
	}

	public LFPerson getPerson() {
		return person;
	}

	public void setPerson(LFPerson person) {
		this.person = person;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}
	
}
