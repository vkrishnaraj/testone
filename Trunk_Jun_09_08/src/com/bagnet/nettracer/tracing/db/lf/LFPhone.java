package com.bagnet.nettracer.tracing.db.lf;

import java.io.Serializable;

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
public class LFPhone implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2558668661501822735L;
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
	private String countryNumber;
	private String areaNumber;
	private String exchangeNumber;
	private String lineNumber;
	
	private String extension;
	
	@Transient
	private String decryptedNumber;
	@Transient
	private String decryptedCountry;
	@Transient
	private String decryptedArea;
	@Transient
	private String decryptedExchange;
	@Transient
	private String decryptedLine;
	
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
			if(phoneNumber!=null && phoneNumber.length()>0){
				phoneNumber=phoneNumber.replaceAll("[^\\d.]", "");
			} else {
				phoneNumber="";
			}
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

		if(extension.length()>0){
			extension=extension.replaceAll("[^\\d.]", "");
		}
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
	
	public boolean isEmpty() {
		boolean empty = true;
		if (getDecryptedPhoneNumber() != null && !getDecryptedPhoneNumber().isEmpty()) {
			empty = false;
		}
		return empty;
	}

	public String getCountryNumber() {
		return countryNumber;
	}
	
	public String getDecryptedCountry(){
		if(this.decryptedCountry == null){
			try {
				this.decryptedCountry = AES.decrypt(this.countryNumber);
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		return this.decryptedCountry;
	}
	
	public void setDecryptedCountry(String countryNumber){
		this.decryptedCountry = null; 
		try {
			if(countryNumber!=null && countryNumber.length()>0){
				countryNumber=countryNumber.replaceAll("[^\\d.]", "");
			} else {
				countryNumber="";
			}
			this.countryNumber = AES.encrypt(normalizePhone(countryNumber));
		} catch (Exception e){
			e.printStackTrace();
			this.countryNumber = null;
		}
	}

	public void setCountryNumber(String countryNumber) {
		this.countryNumber = countryNumber;
	}

	public String getAreaNumber() {
		return areaNumber;
	}
	
	public String getDecryptedArea(){
		if(this.decryptedArea == null){
			try {
				this.decryptedArea = AES.decrypt(this.areaNumber);
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		return this.decryptedArea;
	}

	public void setDecryptedArea(String areaNumber){
		this.decryptedArea = null; 
		try {
			if(areaNumber!=null && areaNumber.length()>0){
				areaNumber=areaNumber.replaceAll("[^\\d.]", "");
			} else {
				areaNumber="";
			}
			this.areaNumber = AES.encrypt(normalizePhone(areaNumber));
		} catch (Exception e){
			e.printStackTrace();
			this.areaNumber = null;
		}
	}

	public void setAreaNumber(String areaNumber) {
		this.areaNumber = areaNumber;
	}

	public String getExchangeNumber() {
		return exchangeNumber;
	}
	
	public String getDecryptedExchange(){
		if(this.decryptedExchange== null){
			try {
				this.decryptedExchange = AES.decrypt(this.exchangeNumber);
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		return this.decryptedExchange;
	}

	public void setDecryptedExchange(String exchangeNumber){
		this.decryptedExchange = null; 
		try {
			if(exchangeNumber!=null && exchangeNumber.length()>0){
				exchangeNumber=exchangeNumber.replaceAll("[^\\d.]", "");
			} else {
				exchangeNumber="";
			}
			this.exchangeNumber = AES.encrypt(normalizePhone(exchangeNumber));
		} catch (Exception e){
			e.printStackTrace();
			this.exchangeNumber = null;
		}
	}	

	public void setExchangeNumber(String exchangeNumber) {
		this.exchangeNumber = exchangeNumber;
	}

	public String getLineNumber() {
		return lineNumber;
	}
	
	public String getDecryptedLine(){
		if(this.decryptedLine== null){
			try {
				this.decryptedLine = AES.decrypt(this.lineNumber);
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		return this.decryptedLine;
	}
	
	public void setDecryptedLine(String lineNumber){
		this.decryptedLine= null; 
		try {
			if(lineNumber!=null && lineNumber.length()>0){
				lineNumber=lineNumber.replaceAll("[^\\d.]", "");
			}  else {
				lineNumber="";
			}
			this.lineNumber = AES.encrypt(normalizePhone(lineNumber));
		} catch (Exception e){
			e.printStackTrace();
			this.lineNumber = null;
		}
	}	

	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}
	
}
