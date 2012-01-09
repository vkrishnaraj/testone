package com.bagnet.nettracer.tracing.db.lf.detection;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.Proxy;

import aero.nettracer.security.AES;

@Entity
@Proxy(lazy = true)
public class LFMatchDetail implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8133424320220693299L;

	@Id
	@GeneratedValue
	private long id;
	
	private String description;
	
	private String lostValue;
	
	@Transient
	private String decryptedLostValue;
	
	private String foundValue;
	
	@Transient
	private String decryptedFoundValue;
	
	@ManyToOne(targetEntity = com.bagnet.nettracer.tracing.db.lf.detection.LFMatchHistory.class)
	private LFMatchHistory matchHistory;

	private double score;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LFMatchHistory getMatchHistory() {
		return matchHistory;
	}

	public void setMatchHistory(LFMatchHistory matchHistory) {
		this.matchHistory = matchHistory;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public double getScore() {
		return score;
	}

	@Deprecated
	/**For use by hibernate only, use getDecryptedLostValue instead*/
	public String getLostValue() {
		return lostValue;
	}

	@Deprecated
	/**For use by hibernate only, use setDecryptedLostValue instead*/
	public void setLostValue(String lostValue) {
		this.lostValue = lostValue;
	}

	@Deprecated
	/**For use by hibernate only, use getDecryptedFoundValue instead*/
	public String getFoundValue() {
		return foundValue;
	}

	@Deprecated
	/**For use by hibernate only, use setDecryptedFoundValue instead*/
	public void setFoundValue(String foundValue) {
		this.foundValue = foundValue;
	}
	
	public String getDecryptedLostValue(){
		if(this.decryptedLostValue == null){
			try {
				this.decryptedLostValue = AES.decrypt(this.lostValue);
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		return this.decryptedLostValue;
	}
	
	public void setDecryptedLostValue(String lostValue){
		this.decryptedLostValue = null; 
		try {
			this.lostValue = AES.encrypt(lostValue);
		} catch (Exception e){
			e.printStackTrace();
			this.lostValue = null;
		}
	}
	
	public String getDecryptedFoundValue(){
		if(this.decryptedFoundValue == null){
			try {
				this.decryptedFoundValue = AES.decrypt(this.foundValue);
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		return this.decryptedFoundValue;
	}
	
	public void setDecryptedFoundValue(String foundValue){
		this.decryptedFoundValue = null; 
		try {
			this.foundValue = AES.encrypt(foundValue);
		} catch (Exception e){
			e.printStackTrace();
			this.foundValue = null;
		}
	}
	
}
