package com.bagnet.nettracer.tracing.db.lf;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

import aero.nettracer.security.AES;

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
	
	@Transient
	private String decryptedNumber;
	
	/* Primary/Secondary */
	private int numberType;
	
	/* Home/Mobile/Work/Alternate */
	private int phoneType;
	
	@ManyToOne
	@JoinColumn(name = "person_id", nullable = true)
	@Fetch(FetchMode.SELECT)
	private LFPerson person;
	
	@OneToOne
	@JoinColumn(name = "item_id", nullable = true)
	@Fetch(FetchMode.SELECT)
	private LFItem item;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Deprecated
	/**For use by hibernate only, use getDecryptedPhoneNumber instead*/
	public String getPhoneNumber() {
		return phoneNumber;
	}

	@Deprecated
	/**For use by hibernate only, use setDecryptedPhoneNumber instead*/
	public void setPhoneNumber(String phoneNumber) {
		this.decryptedNumber = null;
		this.phoneNumber = phoneNumber;
	}
	
	public String getDecryptedPhoneNumber(){
		if(this.decryptedNumber == null){
			try {
				this.decryptedNumber = AES.decrypt(this.phoneNumber);
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		return this.decryptedNumber;
	}
	
	public void setDecryptedPhoneNumber(String phoneNumber){
		this.decryptedNumber = null; 
		try {
			this.phoneNumber = AES.encrypt(normalizePhone(phoneNumber));
		} catch (Exception e){
			e.printStackTrace();
			this.phoneNumber = null;
		}
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
	
	public static String normalizePhone(String phone){
		if (phone == null) return null;
		StringBuffer sb = new StringBuffer(100);
		for (int i = 0; i < phone.length(); i++) {
			char c = phone.charAt(i);
			if (Character.isDigit(c)) {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public void setItem(LFItem item) {
		this.item = item;
	}

	public LFItem getItem() {
		return item;
	}
	
}
