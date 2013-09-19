package com.bagnet.nettracer.tracing.db;

import java.util.Date;
import java.util.TimeZone;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Proxy;

import com.bagnet.nettracer.tracing.utils.DateUtils;
/**
 * @author Sean Fine
 * Class representing Depreciation Item object
 */
@Entity
@Table(name = "depreciation_item")
@Proxy(lazy = false)
public class Depreciation_Item {

	private int id;
	private String description;
	private double amountClaimed;
	private Date datePurchase; 
	private int category_id;
	private int proofOwnership;
	private boolean notCoveredCoc; 
	private double calcValue;
	private double claimValue;
	
	private Claim_Depreciation claimDepreciation;

	private String _DATEFORMAT; // current login agent's date format
	
	@Id @GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name = "claim_depreciation_id", nullable = false)
	@Fetch(FetchMode.SELECT)
	public Claim_Depreciation getClaimDepreciation() {
		return claimDepreciation;
	}

	public void setClaimDepreciation(Claim_Depreciation claimDepreciation) {
		this.claimDepreciation = claimDepreciation;
	}

	public double getClaimValue() {
		return claimValue;
	}

	public void setClaimValue(double claimValue) {
		this.claimValue = claimValue;
	}

	public double getCalcValue() {
		return calcValue;
	}

	public void setCalcValue(double calcValue) {
		this.calcValue = calcValue;
	}

	public boolean isNotCoveredCoc() {
		return notCoveredCoc;
	}

	public void setNotCoveredCoc(boolean notCoveredCoc) {
		this.notCoveredCoc = notCoveredCoc;
	}

	public int getProofOwnership() {
		return proofOwnership;
	}

	public void setProofOwnership(int proofOwnership) {
		this.proofOwnership = proofOwnership;
	}
	
	@Transient
	public void setProofOwnership(String proofOwnership) {
		int p=0;
		try{
			p=Integer.valueOf(proofOwnership);
		} catch (Exception e){
			
		}
		setProofOwnership(p);
	}

	public int getCategory_id() {
		return category_id;
	}

	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}

	public Date getDatePurchase() {
		return datePurchase;
	}

	public void setDatePurchase(Date datePurchase) {
		this.datePurchase = datePurchase;
	}

	public double getAmountClaimed() {
		return amountClaimed;
	}

	public void setAmountClaimed(double amountClaimed) {
		this.amountClaimed = amountClaimed;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Transient
	public String get_DATEFORMAT() {
		return _DATEFORMAT;
	}

	@Transient
	public void set_DATEFORMAT(String _DATEFORMAT) {
		this._DATEFORMAT = _DATEFORMAT;
	}

	@Transient
	public void setDispDatePurchase(String s) {
		setDatePurchase(DateUtils.convertToDate(s, _DATEFORMAT, null));
	}

	@Transient
	public String getDispDatePurchase() {
		return DateUtils.formatDate(getDatePurchase(), _DATEFORMAT, null, null);
	}
}
