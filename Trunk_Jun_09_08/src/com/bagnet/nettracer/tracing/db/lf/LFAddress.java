package com.bagnet.nettracer.tracing.db.lf;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.hibernate.annotations.Proxy;

import com.bagnet.nettracer.tracing.utils.TracerUtils;

import aero.nettracer.security.AES;

@Entity
@Proxy(lazy = false)
public class LFAddress implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4392614284865636644L;

	@Id
	@GeneratedValue
	private long id;
	
	private String address1;
	
	@Transient
	private String decryptedAddress1;
	
	private String address2;
	
	@Transient
	private String decryptedAddress2;
	
	private String city;
	
	@Transient
	private String decryptedCity;
	
	private String state;
	
	@Transient
	private String decryptedState;
	
	private String province;
	
	@Transient
	private String decryptedProvince;
	
	private String zip;
	
	@Transient
	private String decryptedZip;
	
	private String country;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Deprecated
	/**For use by hibernate only, use getDecryptedAddress1 instead*/
	public String getAddress1() {
		return address1;
	}

	@Deprecated
	/**For use by hibernate only, use setDecryptedAddress1 instead*/
	public void setAddress1(String address1) {
		this.decryptedAddress1 = null;
		this.address1 = address1;
	}

	@Deprecated
	/**For use by hibernate only, use getDecryptedAddress2 instead*/
	public String getAddress2() {
		return address2;
	}

	@Deprecated
	/**For use by hibernate only, use setDecryptedAddress2 instead*/
	public void setAddress2(String address2) {
		this.decryptedAddress2 = null;
		this.address2 = address2;
	}

	@Deprecated
	/**For use by hibernate only, use getDecryptedCity instead*/
	public String getCity() {
		return city;
	}

	@Deprecated
	/**For use by hibernate only, use setDecryptedCity instead*/
	public void setCity(String city) {
		this.city = city;
	}

	@Deprecated
	/**For use by hibernate only, use getDecryptedState instead*/
	public String getState() {
		return state;
	}

	@Deprecated
	/**For use by hibernate only, use setDecryptedState instead*/
	public void setState(String state) {
		this.state = state;
	}

	@Deprecated
	/**For use by hibernate only, use getDecryptedProvince instead*/
	public String getProvince() {
		return province;
	}

	@Deprecated
	/**For use by hibernate only, use setDecryptedProvince instead*/
	public void setProvince(String province) {
		this.province = province;
	}

	@Deprecated
	/**For use by hibernate only, use getDecryptedZip instead*/
	public String getZip() {
		return zip;
	}

	@Deprecated
	/**For use by hibernate only, use setDecryptedZip instead*/
	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountryName() {
		if (country != null && country.length() > 0) {
			return TracerUtils.getCountry(country).getCountry();
		}
		return "";
	}
	
	public String getDecryptedAddress1(){
		if(this.decryptedAddress1 == null){
			try{
				this.decryptedAddress1 = AES.decrypt(this.address1);
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		return this.decryptedAddress1;
	}
	
	public void setDecryptedAddress1(String address1){
		this.decryptedAddress1 = null;
		try {
			this.address1 = AES.encrypt(address1);
		} catch (Exception e){
			e.printStackTrace();
			this.address1 = null;
		}
	}
	
	public String getDecryptedAddress2(){
		if(this.decryptedAddress2 == null){
			try{
				this.decryptedAddress2 = AES.decrypt(this.address2);
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		return this.decryptedAddress2;
	}
	
	public void setDecryptedAddress2(String address2){
		this.decryptedAddress2 = null;
		try {
			this.address2 = AES.encrypt(address2);
		} catch (Exception e){
			e.printStackTrace();
			this.address2 = null;
		}
	}
	
	public String getDecryptedCity(){
		if(this.decryptedCity == null){
			try{
				this.decryptedCity = AES.decrypt(this.city);
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		return this.decryptedCity;
	}
	
	public void setDecryptedCity(String city){
		this.decryptedCity = null;
		try {
			this.city = AES.encrypt(city);
		} catch (Exception e){
			e.printStackTrace();
			this.city = null;
		}
	}
	
	public String getDecryptedState(){
		if(this.decryptedState == null){
			try{
				this.decryptedState = AES.decrypt(this.state);
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		return this.decryptedState;
	}
	
	public void setDecryptedState(String state){
		this.decryptedState = null;
		try {
			this.state = AES.encrypt(state);
		} catch (Exception e){
			e.printStackTrace();
			this.state = null;
		}
	}
	
	public String getDecryptedProvince(){
		if(this.decryptedProvince == null){
			try{
				this.decryptedProvince = AES.decrypt(this.province);
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		return this.decryptedProvince;
	}
	
	public void setDecryptedProvince(String province){
		this.decryptedProvince = null;
		try {
			this.province = AES.encrypt(province);
		} catch (Exception e){
			e.printStackTrace();
			this.province = null;
		}
	}
	
	public String getDecryptedZip(){
		if(this.decryptedZip == null){
			try{
				this.decryptedZip = AES.decrypt(this.zip);
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		return this.decryptedZip;
	}
	
	public void setDecryptedZip(String zip){
		this.decryptedZip = null;
		try {
			this.zip = AES.encrypt(zip);
		} catch (Exception e){
			e.printStackTrace();
			this.zip = null;
		}
	}
	
	public boolean isEmpty() {
		boolean empty = true;
		if ((getDecryptedAddress1() != null && !getDecryptedAddress1().isEmpty())
				|| (getDecryptedAddress2() != null && !getDecryptedAddress2().isEmpty())
				|| (getDecryptedCity() != null && !getDecryptedCity().isEmpty())
				|| (getDecryptedState() != null && !getDecryptedState().isEmpty())
				|| (getDecryptedProvince() != null && !getDecryptedProvince().isEmpty())
				|| (getDecryptedZip() != null && !getDecryptedZip().isEmpty())
				|| (getCountry() != null && !getCountry().isEmpty())) {
			empty = false;
		}

		return empty;
	}
		
}
